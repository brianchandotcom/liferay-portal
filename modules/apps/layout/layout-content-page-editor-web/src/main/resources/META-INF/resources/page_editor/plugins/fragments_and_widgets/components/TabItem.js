/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayCard from '@clayui/card';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useState} from 'react';

import {FRAGMENTS_DISPLAY_STYLES} from '../../../app/config/constants/fragmentsDisplayStyles';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../app/config/constants/layoutDataItemTypes';
import {LIST_ITEM_TYPES} from '../../../app/config/constants/listItemTypes';
import {
	useSelectItem,
	useSelectMultipleItems,
} from '../../../app/contexts/ControlsContext';
import {
	useDisableKeyboardMovement,
	useSetMovementSources,
} from '../../../app/contexts/KeyboardMovementContext';
import {useDispatch} from '../../../app/contexts/StoreContext';
import addFragment from '../../../app/thunks/addFragment';
import addItem from '../../../app/thunks/addItem';
import addStepper from '../../../app/thunks/addStepper';
import addWidget from '../../../app/thunks/addWidget';
import toggleFragmentHighlighted from '../../../app/thunks/toggleFragmentHighlighted';
import toggleWidgetHighlighted from '../../../app/thunks/toggleWidgetHighlighted';
import {useDragSymbol} from '../../../app/utils/drag_and_drop/useDragAndDrop';
import useKeyboardNavigation from '../hooks/useKeyboardNavigation';

const ITEM_PROPTYPES_SHAPE = PropTypes.shape({
	data: PropTypes.object.isRequired,
	highlighted: PropTypes.bool,
	icon: PropTypes.string.isRequired,
	label: PropTypes.string.isRequired,
	preview: PropTypes.string,
	type: PropTypes.string.isRequired,
});

export default function TabItem({displayStyle, item, onRemoveHighlighted}) {
	const dispatch = useDispatch();
	const [disabled, setDisabled] = useState(item.disabled);
	const setMovementSources = useSetMovementSources();
	const selectItem = useSelectItem();
	const selectMultipleItems = useSelectMultipleItems();

	const onMovementSource = (event) => {
		if (event.key === 'Enter' || event.key === ' ') {
			event.preventDefault();

			setMovementSources([
				{
					...item.data,
					fragmentEntryType: item.data.type,
					icon: item.icon,
					isWidget: Boolean(item.data.portletId),
					name: item.label,
					portletId: item.data.portletId,
					type: item.type,
				},
			]);
		}
	};

	const onToggleHighlighted = useCallback(() => {
		if (item.data.portletId) {
			dispatch(
				toggleWidgetHighlighted({
					groupId: item.data.groupId,
					highlighted: !item.highlighted,
					portletId: item.data.portletId,
				})
			);
		}
		else {
			dispatch(
				toggleFragmentHighlighted({
					fragmentEntryKey: item.itemId,
					groupId: item.data.groupId,
					highlighted: !item.highlighted,
				})
			);
		}

		if (item.highlighted) {
			onRemoveHighlighted();
		}
	}, [dispatch, item, onRemoveHighlighted]);

	const {isDraggingSource, sourceRef} = useDragSymbol(
		{
			fieldTypes: item.data.fieldTypes,
			fragmentEntryType: item.data.type,
			icon: item.icon,
			isWidget: Boolean(item.data.portletId),
			label: item.label,
			portletId: item.data.portletId,
			type: item.type,
		},
		(parentId, position) => {
			let thunk;

			if (item.type === LAYOUT_DATA_ITEM_TYPES.fragment) {
				if (item.data.portletId) {
					thunk = addWidget;
				}
				else if (item.data.fieldTypes?.includes('stepper')) {
					thunk = addStepper;
				}
				else {
					thunk = addFragment;
				}
			}
			else {
				thunk = addItem;
			}

			setDisabled(true);

			dispatch(
				thunk({
					...item.data,
					itemType: item.type,
					parentItemId: parentId,
					position,
					selectItems: Liferay.FeatureFlags['LPD-18221']
						? selectMultipleItems
						: selectItem,
				})
			)
				.then(() => {
					setDisabled(false);
				})
				.catch(() => {
					setDisabled(false);
				});
		}
	);

	return displayStyle === FRAGMENTS_DISPLAY_STYLES.CARDS ? (
		<CardItem
			disabled={disabled || isDraggingSource || item.disabled}
			handlerRef={item.disabled ? null : sourceRef}
			item={item}
			onMovementSource={onMovementSource}
			onToggleHighlighted={onToggleHighlighted}
		/>
	) : (
		<ListItem
			disabled={disabled || isDraggingSource || item.disabled}
			handlerRef={item.disabled || disabled ? null : sourceRef}
			item={item}
			onMovementSource={onMovementSource}
			onToggleHighlighted={onToggleHighlighted}
		/>
	);
}

TabItem.propTypes = {
	displayStyle: PropTypes.oneOf(Object.values(FRAGMENTS_DISPLAY_STYLES)),
	item: ITEM_PROPTYPES_SHAPE.isRequired,
};

const ListItem = ({
	disabled,
	handlerRef,
	item,
	onMovementSource,
	onToggleHighlighted,
}) => {
	const {isTarget, setElement} = useKeyboardNavigation({
		type: LIST_ITEM_TYPES.listItem,
	});

	const [isActive, setIsActive] = useState(false);

	return (
		<li
			className={classNames(
				'mb-1 page-editor__fragments-widgets__tab-list-item rounded',
				{
					disabled,
					'ml-3 page-editor__fragments-widgets__tab-portlet-item':
						item.data.portletItemId,
					'page-editor__fragments-widgets__tab-list-item--active':
						isActive,
				}
			)}
			onKeyDown={onMovementSource}
			ref={setElement}
			role="menuitem"
			tabIndex={isTarget ? 0 : -1}
		>
			<div
				className="align-items-center d-flex h-100 justify-content-between w-100"
				ref={handlerRef}
			>
				<div className="align-items-center d-flex page-editor__fragments-widgets__tab-list-item-body">
					<ClayIcon className="mr-3" symbol={item.icon} />

					<div className="title">{item.label}</div>
				</div>

				{!disabled && (
					<AddButton
						isNavigationTarget={isTarget}
						item={item}
						setItemActive={setIsActive}
					/>
				)}

				<HighlightButton
					isNavigationTarget={isTarget}
					item={item}
					onToggleHighlighted={onToggleHighlighted}
					setItemActive={setIsActive}
				/>
			</div>
		</li>
	);
};

ListItem.propTypes = {
	disabled: PropTypes.bool,
	handlerRef: PropTypes.func.isRequired,
	item: ITEM_PROPTYPES_SHAPE.isRequired,
	onToggleHighlighted: PropTypes.func.isRequired,
};

const CardItem = ({
	disabled,
	handlerRef,
	item,
	onMovementSource,
	onToggleHighlighted,
}) => {
	const {isTarget, setElement} = useKeyboardNavigation({
		type: LIST_ITEM_TYPES.listItem,
	});

	const [isActive, setIsActive] = useState(false);

	return (
		<li
			className={classNames(
				'page-editor__fragments-widgets__tab-card-item',
				{
					disabled,
					'page-editor__fragments-widgets__tab-list-item--active':
						isActive,
				}
			)}
			onKeyDown={onMovementSource}
			ref={setElement}
			role="menuitem"
			tabIndex={isTarget ? 0 : -1}
		>
			<div ref={handlerRef}>
				<ClayCard
					aria-label={item.label}
					className="mb-0"
					displayType={item.preview ? 'image' : 'file'}
					selectable
				>
					<ClayCard.AspectRatio className="card-item-first">
						{item.preview ? (
							<img
								alt="thumbnail"
								className="aspect-ratio-item aspect-ratio-item-center-middle aspect-ratio-item-fluid"
								src={item.preview}
							/>
						) : (
							<div className="aspect-ratio-item aspect-ratio-item-center-middle aspect-ratio-item-fluid card-type-asset-icon">
								<ClayIcon symbol={item.icon} />
							</div>
						)}
					</ClayCard.AspectRatio>

					<ClayCard.Body className="align-items-center d-flex p-2 rounded-bottom">
						<ClayCard.Row>
							<div className="autofit-col autofit-col-expand">
								<section className="autofit-section">
									<ClayCard.Description
										displayType="title"
										title={item.label}
										truncate={false}
									>
										<span
											className="lfr-portal-tooltip text-truncate"
											data-tooltip-align="center"
										>
											{item.label}
										</span>
									</ClayCard.Description>
								</section>
							</div>

							{!disabled && (
								<AddButton
									isNavigationTarget={isTarget}
									item={item}
									setItemActive={setIsActive}
								/>
							)}

							<HighlightButton
								isNavigationTarget={isTarget}
								item={item}
								onToggleHighlighted={onToggleHighlighted}
								setItemActive={setIsActive}
							/>
						</ClayCard.Row>
					</ClayCard.Body>
				</ClayCard>
			</div>
		</li>
	);
};

CardItem.propTypes = {
	disabled: PropTypes.bool.isRequired,
	handlerRef: PropTypes.func.isRequired,
	item: ITEM_PROPTYPES_SHAPE.isRequired,
	onToggleHighlighted: PropTypes.func.isRequired,
};

const HighlightButton = ({
	isNavigationTarget,
	item,
	onToggleHighlighted,
	setItemActive,
}) => {
	if (item.data.portletItemId) {
		return null;
	}

	const {highlighted} = item;

	const title = highlighted
		? sub(Liferay.Language.get('unmark-x-as-favorite'), item.label)
		: sub(Liferay.Language.get('mark-x-as-favorite'), item.label);

	const onFocus = (event) => {
		const addButton = event.target.previousSibling;
		const sidebarResizer = document.querySelector(
			'.page-editor__sidebar__resizer'
		);

		if ([addButton, sidebarResizer].includes(event.relatedTarget)) {
			setItemActive(true);
		}
	};

	return (
		<ClayButtonWithIcon
			aria-label={title}
			borderless
			className={classNames(
				'page-editor__fragments-widgets__tab__highlight-button',
				{highlighted}
			)}
			displayType="secondary"
			onBlur={() => setItemActive(false)}
			onClick={onToggleHighlighted}
			onFocus={onFocus}
			onKeyDown={(event) => event.stopPropagation()}
			symbol={highlighted ? 'star' : 'star-o'}
			tabIndex={isNavigationTarget ? 0 : -1}
			title={title}
		/>
	);
};

HighlightButton.propTypes = {
	isNavigationTarget: PropTypes.bool.isRequired,
	item: ITEM_PROPTYPES_SHAPE.isRequired,
	onToggleHighlighted: PropTypes.func.isRequired,
	setItemActive: PropTypes.func.isRequired,
};

const AddButton = ({isNavigationTarget, item, setItemActive}) => {
	const setMovementSources = useSetMovementSources();
	const disableMovement = useDisableKeyboardMovement();

	return (
		<ClayButtonWithIcon
			aria-label={sub(Liferay.Language.get('add-x'), item.label)}
			borderless
			className="mr-2 my-0 page-editor__fragments-widgets__tab__add-button"
			displayType="secondary"
			onBlur={() => {
				setItemActive(false);
				disableMovement();
			}}
			onClick={() =>
				setMovementSources([
					{
						...item.data,
						fragmentEntryType: item.data.type,
						icon: item.icon,
						isWidget: Boolean(item.data.portletId),
						name: item.label,
						type: item.type,
					},
				])
			}
			onFocus={() => setItemActive(true)}
			onKeyDown={(event) => event.stopPropagation()}
			symbol="plus"
			tabIndex={isNavigationTarget ? 0 : -1}
			title={sub(Liferay.Language.get('add-x'), item.label)}
		/>
	);
};

AddButton.propTypes = {
	isNavigationTarget: PropTypes.bool.isRequired,
	item: PropTypes.object.isRequired,
	setItemActive: PropTypes.func.isRequired,
};
