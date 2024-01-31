/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown, {ClayDropDownWithItems} from '@clayui/drop-down';
import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import ClayLink from '@clayui/link';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {PageTemplateModal} from '@liferay/layout-js-components-web';
import classNames from 'classnames';
import {fetch, sub} from 'frontend-js-web';
import React, {useEffect, useMemo, useRef, useState} from 'react';
import {useDrag, useDrop} from 'react-dnd';
import {getEmptyImage} from 'react-dnd-html5-backend';

import ACTIONS from '../actions';
import {ACCEPTING_TYPES, ITEM_HOVER_BORDER_LIMIT} from './constants';

const DROP_ZONES = {
	BOTTOM: 'BOTTOM',
	ELEMENT: 'ELEMENT',
	TOP: 'TOP',
};

const ITEM_HOVER_TIMEOUT = 500;

const ITEM_STATES_COLORS = {
	'conversion-draft': 'info',
	'draft': 'secondary',
	'pending': 'info',
};

const isValidTarget = (sources, target, dropZone, isPrivateLayoutsEnabled) => {
	if (sources.some((item) => item.id === target.id)) {
		return false;
	}

	if (
		sources.some(
			(source) =>
				!(
					(((isPrivateLayoutsEnabled && target.parentId) ||
						!isPrivateLayoutsEnabled) &&
						target.columnIndex <= source.columnIndex) ||
					(target.columnIndex > source.columnIndex && !source.active)
				)
		)
	) {
		return false;
	}

	if (dropZone === DROP_ZONES.TOP) {
		return !sources.some(
			(source) =>
				!(
					target.columnIndex !== source.columnIndex ||
					target.itemIndex < source.itemIndex ||
					target.itemIndex > source.itemIndex + 1
				)
		);
	}
	else if (dropZone === DROP_ZONES.BOTTOM) {
		return !sources.some(
			(source) =>
				!(
					target.columnIndex !== source.columnIndex ||
					target.itemIndex > source.itemIndex ||
					target.itemIndex < source.itemIndex - 1
				)
		);
	}
	else if (dropZone === DROP_ZONES.ELEMENT) {
		return !sources.some(
			(source) => !(target.id !== source.parentId && target.parentable)
		);
	}
};

const getDropZone = (ref, monitor) => {
	if (!ref.current) {
		return;
	}

	const clientOffset = monitor.getClientOffset();
	const dropItemBoundingRect = ref.current.getBoundingClientRect();
	const hoverTopLimit = ITEM_HOVER_BORDER_LIMIT;
	const hoverBottomLimit =
		dropItemBoundingRect.height - ITEM_HOVER_BORDER_LIMIT;
	const hoverClientY = clientOffset.y - dropItemBoundingRect.top;

	let dropZone = DROP_ZONES.ELEMENT;

	if (hoverClientY < hoverTopLimit) {
		dropZone = DROP_ZONES.TOP;
	}
	else if (hoverClientY > hoverBottomLimit) {
		dropZone = DROP_ZONES.BOTTOM;
	}

	return dropZone;
};

const getItemIndex = (item = {}, items) => {
	const siblings = Array.from(items.values()).filter(
		(_item) => _item.columnIndex === item.columnIndex
	);

	return siblings.indexOf(item);
};

function addSeparators(items) {
	if (items.length < 2) {
		return items;
	}

	const separatedItems = [items[0]];

	for (let i = 1; i < items.length; i++) {
		const item = items[i];

		if (item.type === 'group' && item.separator) {
			separatedItems.push({type: 'divider'});
		}

		separatedItems.push(item);
	}

	return separatedItems.map((item) => {
		if (item.type === 'group') {
			return {
				...item,
				items: addSeparators(item.items),
			};
		}

		return item;
	});
}

function filterEmptyGroups(items) {
	return items
		.filter(
			(item) =>
				item.type !== 'group' ||
				(Array.isArray(item.items) && item.items.length)
		)
		.map((item) =>
			item.type === 'group'
				? {...item, items: filterEmptyGroups(item.items)}
				: item
		);
}

const noop = () => {};

const MillerColumnsItem = ({
	createPageTemplateURL,
	getPageTemplateCollectionsURL,
	getItemActionsURL,
	isLayoutSetPrototype,
	isPrivateLayoutsEnabled,
	item: {
		active,
		bulkActions = [],
		checked,
		columnIndex,
		description,
		draggable,
		hasChild,
		hasDuplicatedFriendlyURL = false,
		hasGuestViewPermission,
		id: itemId,
		itemIndex,
		parentId,
		parentable,
		quickActions = [],
		selectable,
		states = [],
		target,
		title,
		url,
		viewUrl,
	},
	items,
	namespace,
	onDragEnd,
	onItemDrop = noop,
	onItemStayHover = noop,
	rtl,
}) => {
	const ref = useRef();
	const timeoutRef = useRef();

	const onClose = () => {
		setOpenModal(false);
	};

	const [openModal, setOpenModal] = useState(false);

	const [dropZone, setDropZone] = useState();

	const [layoutActionsActive, setLayoutActionsActive] = useState(false);

	const [dropdownActions, setDropdownActions] = useState([]);

	const loadPromiseRef = useRef();

	const [loadMessage, setLoadMessage] = useState('');

	function loadDropdownActions() {
		if (!loadPromiseRef.current) {
			let loadingMessageShown = false;
			let optionsLoaded = false;
			const url = new URL(getItemActionsURL);
			url.searchParams.append(`${namespace}plid`, itemId);

			setTimeout(() => {
				if (!optionsLoaded) {
					setLoadMessage(
						sub(Liferay.Language.get('loading-x-options'), title)
					);
					loadingMessageShown = true;
				}
			}, 500);

			loadPromiseRef.current = fetch(url, {
				method: 'GET',
			})
				.then((response) => response.json())
				.then(({actions}) => {
					optionsLoaded = true;
					if (loadingMessageShown) {
						setLoadMessage(
							sub(Liferay.Language.get('x-options-loaded'), title)
						);
					}

					const updateItem = (item) => {
						const newItem = {
							...item,
							onClick(event) {
								const action = item.data?.action;

								if (action === 'convertToPageTemplate') {
									setOpenModal(true);
								}
								else if (action) {
									event.preventDefault();

									ACTIONS[action]?.(item.data);
								}
							},
							symbolLeft: item.icon,
						};

						if (Array.isArray(item.items)) {
							newItem.items = item.items.map(updateItem);
						}

						return newItem;
					};

					const dropdownActions = actions.map((action) => {
						return {
							...action,
							items: action.items?.map(updateItem),
						};
					});

					setDropdownActions(
						addSeparators(filterEmptyGroups(dropdownActions))
					);
				});
		}
	}

	const layoutActions = useMemo(() => {
		return quickActions.filter(
			(action) => action.layoutAction && action.url
		);
	}, [quickActions]);

	const normalizedQuickActions = useMemo(() => {
		return quickActions.filter(
			(action) => action.quickAction && action.url
		);
	}, [quickActions]);

	const [{isDragging}, drag, previewRef] = useDrag({
		collect: (monitor) => ({
			isDragging: !!monitor.isDragging(),
		}),
		end: onDragEnd,
		isDragging: (monitor) => {
			const movedItems = monitor.getItem().items;

			return (
				(movedItems.some((item) => item.checked) && checked) ||
				movedItems.some((item) => item.id === itemId)
			);
		},
		item: {
			items: checked
				? Array.from(items.values())
						.filter((item) => item.checked)
						.map((item) => ({
							...item,
							itemIndex: getItemIndex(item, items),
						}))
				: [
						{
							...items.get(itemId),
							itemIndex: getItemIndex(items.get(itemId), items),
						},
				  ],
			type: ACCEPTING_TYPES.ITEM,
		},
	});

	const [{isOver}, drop] = useDrop({
		accept: ACCEPTING_TYPES.ITEM,
		canDrop(source, monitor) {
			const dropZone = getDropZone(ref, monitor);

			return isValidTarget(
				source.items,
				{columnIndex, id: itemId, itemIndex, parentId, parentable},
				dropZone,
				isPrivateLayoutsEnabled
			);
		},
		collect: (monitor) => ({
			isOver: !!monitor.isOver(),
		}),
		drop(source, monitor) {
			if (monitor.canDrop()) {
				if (dropZone === DROP_ZONES.ELEMENT) {
					const newIndex = Array.from(items.values()).filter(
						(item) => item.parentId === itemId
					).length;

					onItemDrop(source.items, itemId, newIndex);
				}
				else {
					let newIndex = itemIndex;

					if (dropZone === DROP_ZONES.BOTTOM) {
						newIndex = itemIndex + 1;
					}

					onItemDrop(source.items, parentId, newIndex);
				}
			}
		},
		hover(source, monitor) {
			let dropZone;

			if (isOver && monitor.canDrop()) {
				dropZone = getDropZone(ref, monitor);
			}

			setDropZone(dropZone);
		},
	});

	useEffect(() => {
		drag(drop(ref));
	}, [drag, drop]);

	useEffect(() => {
		previewRef(getEmptyImage(), {captureDraggingState: true});
	}, [previewRef]);

	useEffect(() => {
		if (!active && dropZone === DROP_ZONES.ELEMENT && !timeoutRef.current) {
			timeoutRef.current = setTimeout(() => {
				if (isOver) {
					onItemStayHover(itemId);
				}
			}, ITEM_HOVER_TIMEOUT);
		}
		else if (
			!isOver ||
			(dropZone !== DROP_ZONES.ELEMENT && timeoutRef.current)
		) {
			clearTimeout(timeoutRef.current);
			timeoutRef.current = null;
		}
	}, [active, dropZone, isOver, itemId, onItemStayHover]);

	const warningMessage = isLayoutSetPrototype
		? Liferay.Language.get(
				'there-is-a-page-with-the-same-friendly-url-in-a-site-using-this-site-template'
		  )
		: Liferay.Language.get(
				'there-is-a-page-with-the-same-friendly-url-in-the-site-template'
		  );

	return (
		<ClayLayout.ContentRow
			className={classNames('list-group-item-flex miller-columns-item', {
				'dragging': isDragging,
				'drop-bottom': isOver && dropZone === DROP_ZONES.BOTTOM,
				'drop-element': isOver && dropZone === DROP_ZONES.ELEMENT,
				'drop-top': isOver && dropZone === DROP_ZONES.TOP,
				'miller-columns-item--active': active,
			})}
			containerElement="li"
			data-actions={bulkActions}
			ref={ref}
			verticalAlign="center"
		>
			<a className="miller-columns-item-mask" href={url} role="button">
				<span className="c-inner sr-only">{title}</span>
			</a>

			{draggable && (
				<ClayLayout.ContentCol className="c-pl-0 miller-columns-item-drag-handler">
					<ClayIcon symbol="drag" />
				</ClayLayout.ContentCol>
			)}

			{selectable && (
				<ClayLayout.ContentCol data-qa-id="selectLayout">
					<ClayCheckbox
						aria-label={sub(
							Liferay.Language.get('select-x'),
							title
						)}
						className="c-mb-0"
						defaultChecked={checked}
						name={`${namespace}rowIds`}
						value={itemId}
					/>
				</ClayLayout.ContentCol>
			)}

			<ClayLayout.ContentCol className="c-pl-1" expand>
				<div
					className={classNames(
						'list-group-title text-truncate-inline',
						{
							'align-items-center':
								Liferay.FeatureFlags['LPS-196847'],
						}
					)}
					data-qa-id="layoutHref"
				>
					{viewUrl ? (
						<ClayLink
							aria-label={(() => {
								if (
									Liferay.FeatureFlags['LPS-196847'] &&
									!hasGuestViewPermission
								) {
									return `${title}. ${Liferay.Language.get(
										'restricted-page'
									)}`;
								}

								if (
									Liferay.FeatureFlags['LPS-174417'] &&
									hasDuplicatedFriendlyURL
								) {
									return `${title}. ${warningMessage}`;
								}

								return title;
							})()}
							className="text-truncate"
							href={viewUrl}
							target={target}
						>
							{title}
						</ClayLink>
					) : (
						<span className="text-truncate">{title}</span>
					)}

					{Liferay.FeatureFlags['LPS-196847'] &&
						!hasGuestViewPermission && (
							<ClayIcon
								className="c-ml-2 c-mt-0 lfr-portal-tooltip miller-columns-item--restricted__icon text-4 text-secondary"
								data-title={Liferay.Language.get(
									'restricted-page'
								)}
								symbol="lock"
							/>
						)}

					{Liferay.FeatureFlags['LPS-174417'] &&
					hasDuplicatedFriendlyURL ? (
						<ClayIcon
							className="align-self-center c-ml-2 flex-shrink-0 icon-warning lfr-portal-tooltip text-warning"
							data-title={warningMessage}
							symbol="warning-full"
						/>
					) : null}
				</div>

				{description && (
					<div className="d-flex h5 list-group-subtitle small">
						<span className="text-truncate">{description}</span>

						{states.map((state) => (
							<ClayLabel
								className="inline-item-after text-truncate"
								displayType={ITEM_STATES_COLORS[state.id]}
								key={state.id}
							>
								{state.label}
							</ClayLabel>
						))}
					</div>
				)}
			</ClayLayout.ContentCol>

			{!!layoutActions.length && (
				<ClayLayout.ContentCol className="miller-columns-item-actions">
					<ClayDropDown
						active={layoutActionsActive}
						onActiveChange={setLayoutActionsActive}
						renderMenuOnClick
						trigger={
							<ClayButtonWithIcon
								borderless
								displayType="secondary"
								size="sm"
								symbol="plus"
								title={Liferay.Language.get('add-child-page')}
							/>
						}
					>
						<ClayDropDown.ItemList>
							{layoutActions.map((action) => (
								<ClayDropDown.Item
									disabled={!action.url}
									href={action.url}
									id={action.id}
									key={action.id}
									onClick={action.handler}
								>
									{action.label}
								</ClayDropDown.Item>
							))}
						</ClayDropDown.ItemList>
					</ClayDropDown>
				</ClayLayout.ContentCol>
			)}

			{normalizedQuickActions.map((action) => (
				<ClayLayout.ContentCol
					className="miller-columns-item-quick-action"
					key={action.id}
				>
					<ClayLink
						borderless
						displayType="secondary"
						href={action.url}
						monospaced
						outline
					>
						<ClayIcon symbol={action.icon} />
					</ClayLink>
				</ClayLayout.ContentCol>
			))}

			{!!getItemActionsURL && itemId !== '0' ? (
				<ClayLayout.ContentCol className="miller-columns-item-actions">
					<ClayDropDownWithItems
						caption={
							!loadPromiseRef.current ? (
								<ClayLoadingIndicator />
							) : (
								''
							)
						}
						items={dropdownActions}
						trigger={
							<ClayButtonWithIcon
								borderless
								displayType="secondary"
								onClick={loadDropdownActions}
								size="sm"
								symbol="ellipsis-v"
								title={Liferay.Language.get(
									'open-page-options-menu'
								)}
							/>
						}
					/>

					<span aria-live="polite" className="sr-only">
						{loadMessage}
					</span>

					{openModal && (
						<PageTemplateModal
							createTemplateURL={createPageTemplateURL}
							getCollectionsURL={getPageTemplateCollectionsURL}
							layoutId={itemId}
							namespace={namespace}
							onClose={onClose}
						/>
					)}
				</ClayLayout.ContentCol>
			) : null}

			{hasChild && (
				<ClayLayout.ContentCol className="miller-columns-item-child-indicator text-secondary">
					<ClayIcon symbol={rtl ? 'caret-left' : 'caret-right'} />
				</ClayLayout.ContentCol>
			)}
		</ClayLayout.ContentRow>
	);
};

export default MillerColumnsItem;
