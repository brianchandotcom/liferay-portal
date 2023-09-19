/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayCard from '@clayui/card';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal, {useModal} from '@clayui/modal';
import ClayTabs from '@clayui/tabs';
import classNames from 'classnames';
import {useId} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useMemo, useRef, useState} from 'react';
import {useDrag, useDrop} from 'react-dnd';
import {getEmptyImage} from 'react-dnd-html5-backend';

import updateSetsOrder from '../../app/thunks/updateSetsOrder';
import {config} from '../config/index';
import {useDispatch, useSelector} from '../contexts/StoreContext';
import selectWidgetFragmentEntryLinks from '../selectors/selectWidgetFragmentEntryLinks';
import loadWidgets from '../thunks/loadWidgets';

const DRAG_OVER_POSITIONS = {
	bottom: 'bottom',
	top: 'top',
};

const FRAGMENTS_ID = 0;
const WIDGETS_ID = 1;

const ACCEPTING_ITEM_TYPE = 'acceptingItemType';

const HIGHLIGHTED_CATEGORY_ID = 'root--category-highlighted';

const HIGHLIGHTED_COLLECTION_ID = 'highlighted';

export function ReorderSetsModal({onCloseModal}) {
	const {observer, onClose} = useModal({
		onClose: onCloseModal,
	});

	const dispatch = useDispatch();

	const widgetFragmentEntryLinks = useSelector(
		selectWidgetFragmentEntryLinks
	);

	const [lists, setLists] = useState({
		[FRAGMENTS_ID]: null,
		[WIDGETS_ID]: null,
	});

	const updateLists = useCallback(
		(listId, newItems) =>
			setLists({...lists, [listId]: newItems.map(({id}) => id)}),
		[lists, setLists]
	);

	return (
		<ClayModal
			className="page-editor__reorder-set-modal"
			containerProps={{className: 'cadmin'}}
			observer={observer}
		>
			<ClayModal.Header>
				{Liferay.Language.get('reorder-sets')}
			</ClayModal.Header>

			<ClayModal.Body className="p-0">
				<p className="m-0 p-4 text-secondary">
					{Liferay.Language.get(
						'fragments-and-widgets-sets-can-be-ordered-to-give-you-easy-access-to-the-ones-you-use-the-most'
					)}
				</p>

				<Tabs updateLists={updateLists} />
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton displayType="secondary" onClick={onClose}>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							displayType="primary"
							onClick={() => {
								const orderedFragments = lists[FRAGMENTS_ID];
								const orderedWidgets = lists[WIDGETS_ID];

								if (!orderedFragments && !orderedWidgets) {
									return;
								}

								dispatch(
									updateSetsOrder({
										fragments: orderedFragments,
										widgetFragmentEntryLinks,
										widgets: orderedWidgets,
									})
								);

								onClose();
							}}
						>
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
}

ReorderSetsModal.propTypes = {
	onCloseModal: PropTypes.func.isRequired,
};

function Tabs({updateLists}) {
	const namespace = useId();

	const getTabId = (id) => `${namespace}tab${id}`;
	const getTabPanelId = (tabId) => `${namespace}tabPanel${tabId}`;

	const [activeTabId, setActiveTabId] = useState(FRAGMENTS_ID);

	const dispatch = useDispatch();
	const widgetFragmentEntryLinks = useSelector(
		selectWidgetFragmentEntryLinks
	);

	const fragments = useSelector((state) =>
		state.fragments
			.filter(
				({fragmentCollectionId}) =>
					fragmentCollectionId !== HIGHLIGHTED_COLLECTION_ID
			)
			.map(({fragmentCollectionId, name}) => ({
				id: fragmentCollectionId,
				name,
			}))
	);

	const widgets = useSelector((state) =>
		state.widgets
			? state.widgets
					.filter(({path}) => path !== HIGHLIGHTED_CATEGORY_ID)
					.map(({path, title}) => ({
						id: path,
						name: title,
					}))
			: null
	);

	const tabs = useMemo(
		() => [
			{
				id: FRAGMENTS_ID,
				items: fragments,
				label: Liferay.Language.get('fragments'),
			},
			{
				id: WIDGETS_ID,
				items: widgets,
				label: Liferay.Language.get('widgets'),
			},
		],
		[fragments, widgets]
	);

	useEffect(() => {
		if (activeTabId === WIDGETS_ID && !widgets) {
			dispatch(
				loadWidgets({
					fragmentEntryLinks: widgetFragmentEntryLinks,
				})
			);
		}
	}, [activeTabId, dispatch, widgetFragmentEntryLinks, widgets]);

	return (
		<>
			<ClayTabs
				activation="automatic"
				active={activeTabId}
				onActiveChange={setActiveTabId}
			>
				{tabs.map(({id, label}) => (
					<ClayTabs.Item
						innerProps={{
							'aria-controls': getTabPanelId(id),
							'id': getTabId(id),
						}}
						key={id}
					>
						{label}
					</ClayTabs.Item>
				))}
			</ClayTabs>

			<ClayTabs.Content activeIndex={activeTabId} fade>
				{tabs.map(({id, items}) => (
					<ClayTabs.TabPane
						aria-labelledby={getTabId(id)}
						className="p-0"
						id={getTabPanelId(id)}
						key={id}
					>
						{items ? (
							<Items
								items={items}
								listId={id}
								updateLists={updateLists}
							/>
						) : (
							<ClayLoadingIndicator size="sm" />
						)}
					</ClayTabs.TabPane>
				))}
			</ClayTabs.Content>
		</>
	);
}

Tabs.propTypes = {
	updateLists: PropTypes.func.isRequired,
};

function Items({items: initialItems, listId, updateLists}) {
	const [items, setItems] = useState(initialItems);

	const onDropItem = (itemId, nextIndex, dragOverPosition) => {
		const index = items.findIndex(({id}) => id === itemId);
		const item = items[index];
		const nextItems = [...items];

		let updatedNextIndex = nextIndex;

		if (Liferay.FeatureFlags['LPS-196420']) {
			if (dragOverPosition === DRAG_OVER_POSITIONS.bottom) {
				updatedNextIndex =
					updatedNextIndex < nextItems.length
						? updatedNextIndex + 1
						: updatedNextIndex;
			}

			if (updatedNextIndex > index) {
				updatedNextIndex =
					updatedNextIndex > 0
						? updatedNextIndex - 1
						: updatedNextIndex;
			}
		}

		nextItems.splice(index, 1);
		nextItems.splice(updatedNextIndex, 0, item);

		setItems(nextItems);
		updateLists(listId, nextItems);
	};

	return (
		<div className="p-4">
			{items.map((item, index) => (
				<CardItem
					index={index}
					item={item}
					key={item.id}
					numberOfItems={items.length}
					onDropItem={onDropItem}
				/>
			))}
		</div>
	);
}

Items.propTypes = {
	items: PropTypes.array,
	listId: PropTypes.number.isRequired,
	updateLists: PropTypes.func.isRequired,
};

function CardItem({index, item, numberOfItems, onDropItem}) {
	const {name} = item;

	const {handlerRef, isDragging} = useDragItem(item);
	const {dragOverPosition, targetRef} = useDropTarget(
		item.id,
		index,
		onDropItem
	);

	return (
		<div className="c-pb-3" ref={targetRef}>
			<div ref={handlerRef}>
				<ClayCard
					className={classNames('c-mb-0', {
						dragging: isDragging,
						draggingOver: dragOverPosition,
						draggingOverBottom:
							dragOverPosition === DRAG_OVER_POSITIONS.bottom,
						draggingOverTop:
							dragOverPosition === DRAG_OVER_POSITIONS.top,
					})}
				>
					<ClayCard.Body className="px-0">
						<ClayCard.Row className="align-items-center">
							<ClayLayout.ContentCol gutters>
								<ClayIcon
									className="text-secondary"
									symbol="drag"
								/>
							</ClayLayout.ContentCol>

							<ClayLayout.ContentCol expand>
								<ClayCard.Description
									className="text-uppercase"
									displayType="title"
									title={name}
								>
									{name}
								</ClayCard.Description>
							</ClayLayout.ContentCol>

							{Liferay.FeatureFlags['LPS-196420'] ? null : (
								<ClayLayout.ContentCol gutters>
									<ReorderDropdown
										index={index}
										item={item}
										numberOfItems={numberOfItems}
										onDropItem={onDropItem}
									/>
								</ClayLayout.ContentCol>
							)}
						</ClayCard.Row>
					</ClayCard.Body>
				</ClayCard>
			</div>
		</div>
	);
}

CardItem.propTypes = {
	index: PropTypes.number.isRequired,
	item: PropTypes.object.isRequired,
	numberOfItems: PropTypes.number.isRequired,
	onDropItem: PropTypes.func.isRequired,
};

function ReorderDropdown({index, item, numberOfItems, onDropItem}) {
	const items = [
		{
			disabled: index === 0,
			label: Liferay.Language.get('move-up'),
			onClick: () => onDropItem(item.id, index - 1),
			symbolLeft: 'angle-up',
		},
		{
			disabled: index === numberOfItems - 1,
			label: Liferay.Language.get('move-down'),
			onClick: () => onDropItem(item.id, index + 1),
			symbolLeft: 'angle-down',
		},
	];

	return (
		<ClayDropDownWithItems
			items={items}
			trigger={
				<ClayButtonWithIcon
					aria-label={sub(Liferay.Language.get('move-x'), item.name)}
					className="text-secondary"
					displayType="unstyled"
					size="sm"
					symbol="ellipsis-v"
				/>
			}
		/>
	);
}

ReorderDropdown.propTypes = {
	index: PropTypes.number.isRequired,
	item: PropTypes.object.isRequired,
	numberOfItems: PropTypes.number.isRequired,
	onDropItem: PropTypes.func.isRequired,
};

function useDragItem(item) {
	const [{isDragging}, handlerRef, previewRef] = useDrag({
		begin() {},
		collect: (monitor) => ({
			isDragging: !!monitor.isDragging(),
		}),
		item: {
			...item,
			namespace: config.portletNamespace,
			type: ACCEPTING_ITEM_TYPE,
		},
	});

	useEffect(() => {
		previewRef(getEmptyImage(), {captureDraggingState: true});
	}, [previewRef]);

	return {
		handlerRef,
		isDragging,
	};
}

export function useDropTarget(itemId, itemIndex, onDropItem) {
	const [dragOverPosition, setDragOverPosition] = useState(null);
	const targetRef = useRef(null);
	const targetRectRef = useRef(null);

	const [{isOver}, internalSetTargetRef] = useDrop({
		accept: ACCEPTING_ITEM_TYPE,
		canDrop(sourceItem, monitor) {
			return sourceItem.id !== itemId && monitor.isOver();
		},
		collect(monitor) {
			return {
				isOver: monitor.isOver(),
			};
		},
		drop(source, monitor) {
			targetRectRef.current = null;

			if (Liferay.FeatureFlags['LPS-196420'] && monitor.canDrop()) {
				onDropItem(source.id, itemIndex, dragOverPosition);
			}
		},
		hover(source, monitor) {
			if (!monitor.isOver()) {
				targetRectRef.current = null;

				return;
			}

			if (Liferay.FeatureFlags['LPS-196420']) {
				targetRectRef.current =
					targetRectRef.current ||
					targetRef.current.getBoundingClientRect();

				const targetMiddlePosition =
					targetRectRef.current.top +
					targetRectRef.current.height / 2;

				if (monitor.getClientOffset().y < targetMiddlePosition) {
					setDragOverPosition(DRAG_OVER_POSITIONS.top);
				}
				else {
					setDragOverPosition(DRAG_OVER_POSITIONS.bottom);
				}
			}
			else if (monitor.canDrop()) {
				onDropItem(source.id, itemIndex);
			}
		},
	});

	const setTargetRef = useCallback(
		(targetElement) => {
			internalSetTargetRef(targetElement);
			targetRef.current = targetElement;
		},
		[internalSetTargetRef]
	);

	return {
		dragOverPosition: isOver ? dragOverPosition : null,
		targetRef: setTargetRef,
	};
}
