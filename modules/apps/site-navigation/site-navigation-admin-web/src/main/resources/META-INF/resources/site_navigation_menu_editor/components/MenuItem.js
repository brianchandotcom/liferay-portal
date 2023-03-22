/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayCard from '@clayui/card';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import classNames from 'classnames';
import {fetch, objectToFormData, openToast, sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useMemo, useState} from 'react';

import {DELETION_TYPES} from '../constants/deletionTypes';
import {NESTING_MARGIN} from '../constants/nestingMargin';
import {SIDEBAR_PANEL_IDS} from '../constants/sidebarPanelIds';
import {useConstants} from '../contexts/ConstantsContext';
import {useItems, useSetItems} from '../contexts/ItemsContext';
import {
	useSelectedMenuItemId,
	useSetSelectedMenuItemId,
} from '../contexts/SelectedMenuItemIdContext';
import {useSetSidebarPanelId} from '../contexts/SidebarPanelIdContext';
import getFlatItems from '../utils/getFlatItems';
import getItemPath from '../utils/getItemPath';
import getOrder from '../utils/getOrder';
import {useDragItem, useDropTarget} from '../utils/useDragAndDrop';
import useKeyboardNavigation from '../utils/useKeyboardNavigation';
import {AddItemDropDown} from './AddItemDropdown';
import DeletionModal from './DeletionModal';

export function MenuItem({
	isMovementEnabled,
	item,
	onMenuItemRemoved,
	setIsMovementEnabled,
	setMovementText,
}) {
	const setItems = useSetItems();
	const setSelectedMenuItemId = useSetSelectedMenuItemId();
	const setSidebarPanelId = useSetSidebarPanelId();
	const {
		deleteSiteNavigationMenuItemURL,
		editSiteNavigationMenuItemParentURL,
		languageId,
		portletNamespace,
	} = useConstants();

	const items = useItems();
	const {siteNavigationMenuItemId, title, type} = item;
	const itemPath = getItemPath(siteNavigationMenuItemId, items);
	const selected = useSelectedMenuItemId() === siteNavigationMenuItemId;

	const [deletionModalVisible, setDeletionModalVisible] = useState(false);
	const [deletionType, setDeletionType] = useState(DELETION_TYPES.single);

	const deleteMenuItem = () => {
		fetch(deleteSiteNavigationMenuItemURL, {
			body: objectToFormData({
				[`${portletNamespace}siteNavigationMenuItemId`]: siteNavigationMenuItemId,
				[`${portletNamespace}deleteChildren`]:
					deletionType === DELETION_TYPES.bulk,
			}),
			method: 'POST',
		})
			.then((response) => response.json())
			.then(({siteNavigationMenuItems}) => {
				const newItems = getFlatItems(siteNavigationMenuItems);

				setItems(newItems);

				setSidebarPanelId(null);
				onMenuItemRemoved();
			})
			.catch(({error}) => {
				openToast({
					message: Liferay.Language.get(
						'an-unexpected-error-occurred'
					),
					type: 'danger',
				});

				if (process.env.NODE_ENV === 'development') {
					console.error(error);
				}
			});
	};

	const order = useMemo(
		() =>
			items
				.filter(
					(siteNavigationMenuItem) =>
						siteNavigationMenuItem.parentSiteNavigationMenuItemId ===
						item.parentSiteNavigationMenuItemId
				)
				.findIndex(
					(siteNavigationMenuItem) =>
						siteNavigationMenuItem.siteNavigationMenuItemId ===
						item.siteNavigationMenuItemId
				),
		[items, item]
	);

	const updateMenuItemParent = (itemId, parentId) => {
		const order = getOrder({
			items,
			parentSiteNavigationMenuItemId: parentId,
			siteNavigationMenuItemId: itemId,
		});

		updateMenuItem({
			editSiteNavigationMenuItemParentURL,
			itemId,
			order,
			parentId,
			portletNamespace,
		})
			.then(({siteNavigationMenuItems}) => {
				const newItems = getFlatItems(siteNavigationMenuItems);

				setItems(newItems);
			})
			.catch(({error}) => {
				openToast({
					message: Liferay.Language.get(
						'an-unexpected-error-occurred'
					),
					type: 'danger',
				});

				if (process.env.NODE_ENV === 'development') {
					console.error(error);
				}
			});
	};

	const {handlerRef, isDragging} = useDragItem(item, updateMenuItemParent);
	const {targetRef} = useDropTarget(item);

	const rtl = Liferay.Language.direction[languageId] === 'rtl';
	const itemStyle = rtl
		? {marginRight: (itemPath.length - 1) * NESTING_MARGIN}
		: {marginLeft: (itemPath.length - 1) * NESTING_MARGIN};

	const parentItemId =
		itemPath.length > 1 ? itemPath[itemPath.length - 2] : '0';

	const {
		isTarget,
		onBlur,
		onFocus,
		onKeyDown,
		setElement,
	} = useKeyboardNavigation();

	return (
		<>
			<div
				aria-description={
					item.icon
						? sub(
								Liferay.Language.get(
									'x-does-not-have-a-display-page-available'
								),
								`${title} (${type})`
						  )
						: null
				}
				aria-label={`${title} (${type})`}
				aria-level={itemPath.length}
				className={classNames(
					'focusable-menu-item site_navigation_menu_editor_MenuItem',
					{
						active: selected,
						dragging: isDragging,
					}
				)}
				data-item-id={item.siteNavigationMenuItemId}
				data-parent-item-id={parentItemId}
				onBlur={onBlur}
				onClick={() => {
					if (!isMovementEnabled) {
						setSelectedMenuItemId(siteNavigationMenuItemId);
						setSidebarPanelId(SIDEBAR_PANEL_IDS.menuItemSettings);
					}
				}}
				onFocus={onFocus}
				onKeyDown={(event) => {
					if (
						(event.key === ' ' || event.key === 'Enter') &&
						!isMovementEnabled
					) {
						setSelectedMenuItemId(siteNavigationMenuItemId);
						setSidebarPanelId(SIDEBAR_PANEL_IDS.menuItemSettings);
					}

					onKeyDown(event);
				}}
				ref={(ref) => {
					targetRef(ref);
					setElement(ref);
				}}
				role="menuitem"
				style={itemStyle}
				tabIndex={isTarget ? '0' : '-1'}
			>
				<ClayCard className="mb-3">
					<ClayCard.Body className="px-0">
						<div ref={handlerRef}>
							<ClayCard.Row>
								<ClayLayout.ContentCol gutters>
									<ClayButtonWithIcon
										aria-label={sub(
											Liferay.Language.get('move-x'),
											`${title} (${type})`
										)}
										displayType="unstyled"
										monospaced={false}
										onBlur={() =>
											setIsMovementEnabled(false)
										}
										onKeyDown={(event) => {
											if (
												!Liferay.FeatureFlags[
													'LPS-134527'
												]
											) {
												return;
											}

											if (event.key === 'Enter') {
												event.preventDefault();
												event.stopPropagation();

												setIsMovementEnabled(
													(
														previousIsMovementEnabled
													) =>
														!previousIsMovementEnabled
												);
											}

											if (event.key === 'Escape') {
												setIsMovementEnabled(false);
											}

											if (!isMovementEnabled) {
												return;
											}

											event.stopPropagation();

											const eventKey = event.key;

											if (
												eventKey === 'ArrowDown' ||
												eventKey === 'ArrowUp'
											) {
												const computeFunction =
													eventKey === 'ArrowDown'
														? getDownPosition
														: getUpPosition;

												const result = computeFunction({
													items,
													order,
													parentSiteNavigationMenuItemId:
														item.parentSiteNavigationMenuItemId,
												});

												if (!result) {
													return;
												}

												updateMenuItem({
													editSiteNavigationMenuItemParentURL,
													itemId:
														item.siteNavigationMenuItemId,
													order: result.order,
													parentId:
														result.parentSiteNavigationMenuItemId,
													portletNamespace,
												}).then(
													({
														siteNavigationMenuItems,
													}) => {
														const newItems = getFlatItems(
															siteNavigationMenuItems
														);

														setItems(newItems);

														setMovementText(
															sub(
																eventKey ===
																	'ArrowDown'
																	? Liferay.Language.get(
																			'x-moved-down'
																	  )
																	: Liferay.Language.get(
																			'x-moved-up'
																	  ),
																`${title} (${type})`
															)
														);
													}
												);
											}
										}}
										size="sm"
										symbol="drag"
										tabIndex={
											isTarget &&
											Liferay.FeatureFlags['LPS-134527']
												? '0'
												: '-1'
										}
									/>
								</ClayLayout.ContentCol>

								<ClayLayout.ContentCol expand>
									<ClayCard.Description
										displayType="title"
										title={title}
									>
										{title}

										{item.icon && (
											<ClayIcon
												className="ml-2 text-warning"
												symbol={item.icon}
											/>
										)}
									</ClayCard.Description>

									<div className="d-flex">
										<ClayLabel
											className="mt-1"
											displayType="secondary"
										>
											{type}
										</ClayLabel>

										{item.dynamic && (
											<ClayLabel
												className="mt-1"
												displayType="info"
											>
												{Liferay.Language.get(
													'dynamic'
												)}
											</ClayLabel>
										)}
									</div>
								</ClayLayout.ContentCol>

								<AddItemDropDown
									className="position-absolute site_navigation_menu_editor_MenuItem-add-button-dropdown top-button"
									trigger={
										<ClayButtonWithIcon
											aria-label={sub(
												Liferay.Language.get(
													'add-item-before-x'
												),
												`${title} (${type})`
											)}
											className="site_navigation_menu_editor_MenuItem-add-button"
											displayType="primary"
											onClick={(event) => {
												event.preventDefault();
												event.stopPropagation();
											}}
											size="xs"
											symbol="plus"
											tabIndex={
												isTarget &&
												Liferay.FeatureFlags[
													'LPS-134527'
												]
													? '0'
													: '-1'
											}
											title={Liferay.Language.get(
												'add-item'
											)}
										/>
									}
								/>

								<AddItemDropDown
									className="bottom-button position-absolute site_navigation_menu_editor_MenuItem-add-button-dropdown"
									trigger={
										<ClayButtonWithIcon
											aria-label={sub(
												Liferay.Language.get(
													'add-item-after-x'
												),
												`${title} (${type})`
											)}
											className="site_navigation_menu_editor_MenuItem-add-button"
											displayType="primary"
											onClick={(event) => {
												event.preventDefault();
												event.stopPropagation();
											}}
											size="xs"
											symbol="plus"
											tabIndex={
												isTarget &&
												Liferay.FeatureFlags[
													'LPS-134527'
												]
													? '0'
													: '-1'
											}
											title={Liferay.Language.get(
												'add-item'
											)}
										/>
									}
								/>

								<ClayLayout.ContentCol gutters>
									<ClayButtonWithIcon
										aria-label={sub(
											Liferay.Language.get('delete-x'),
											`${title} (${type})`
										)}
										className="delete-item-button"
										displayType="unstyled"
										onClick={() =>
											item.children.length
												? setDeletionModalVisible(true)
												: deleteMenuItem()
										}
										size="sm"
										symbol="times-circle"
										tabIndex={isTarget ? '0' : '-1'}
									/>
								</ClayLayout.ContentCol>
							</ClayCard.Row>
						</div>
					</ClayCard.Body>
				</ClayCard>
			</div>

			{deletionModalVisible && (
				<DeletionModal
					deletionType={deletionType}
					onCloseModal={() => setDeletionModalVisible(false)}
					onDeleteItem={deleteMenuItem}
					setDeletionType={setDeletionType}
				/>
			)}
		</>
	);
}

MenuItem.propTypes = {
	item: PropTypes.shape({
		children: PropTypes.array.isRequired,
		siteNavigationMenuItemId: PropTypes.string.isRequired,
		title: PropTypes.string.isRequired,
		type: PropTypes.string.isRequired,
	}),
};

function updateMenuItem({
	editSiteNavigationMenuItemParentURL,
	itemId,
	order,
	parentId,
	portletNamespace,
}) {
	return fetch(editSiteNavigationMenuItemParentURL, {
		body: objectToFormData({
			[`${portletNamespace}siteNavigationMenuItemId`]: itemId,
			[`${portletNamespace}parentSiteNavigationMenuItemId`]: parentId,
			[`${portletNamespace}order`]: order,
		}),
		method: 'POST',
	})
		.then((response) => response.json())
		.catch(({error}) => {
			openToast({
				message: Liferay.Language.get('an-unexpected-error-occurred'),
				type: 'danger',
			});

			if (process.env.NODE_ENV === 'development') {
				console.error(error);
			}
		});
}

function getDownPosition({items, order, parentSiteNavigationMenuItemId}) {
	const parent = items.find(
		(item) =>
			item.siteNavigationMenuItemId === parentSiteNavigationMenuItemId
	);

	const siblings = parent
		? parent.children
		: items.filter(
				(item) =>
					item.parentSiteNavigationMenuItemId ===
					parentSiteNavigationMenuItemId
		  );

	const sibling = siblings[order + 1];

	// If there aren't any sibling, the menu is placed as the sibling of the parent.

	if (!sibling) {

		// If there aren't any sibling and the parentSiteNavigationMenuItemId is 0,
		// there is no movement possible.

		if (parentSiteNavigationMenuItemId === '0') {
			return;
		}

		const parentOrder = getOrder({
			items,
			parentSiteNavigationMenuItemId:
				parent.parentSiteNavigationMenuItemId,
			siteNavigationMenuItemId: parent.siteNavigationMenuItemId,
		});

		return {
			order: parentOrder + 1,
			parentSiteNavigationMenuItemId:
				parent.parentSiteNavigationMenuItemId,
		};
	}

	// If there aren't any sibling, the menu is placed as its child.

	return {
		order: 0,
		parentSiteNavigationMenuItemId: sibling.siteNavigationMenuItemId,
	};
}

function getUpPosition({items, order, parentSiteNavigationMenuItemId}) {

	// The first menu cannot be moved upwards

	if (order === 0 && parentSiteNavigationMenuItemId === '0') {
		return null;
	}

	const parent = items.find(
		(item) =>
			item.siteNavigationMenuItemId === parentSiteNavigationMenuItemId
	);

	const siblings = parent
		? parent.children
		: items.filter(
				(item) =>
					item.parentSiteNavigationMenuItemId ===
					parentSiteNavigationMenuItemId
		  );

	// When the menu is the first child, the menu is placed as the sibling of the parent.

	if (order === 0) {
		const nextOrder = getOrder({
			items,
			parentSiteNavigationMenuItemId:
				parent.parentSiteNavigationMenuItemId,
			siteNavigationMenuItemId: parent.siteNavigationMenuItemId,
		});

		return {
			order: nextOrder,
			parentSiteNavigationMenuItemId:
				parent.parentSiteNavigationMenuItemId,
		};
	}

	// If the previous sibling doesn't have children, place it inside.

	const sibling = siblings[order - 1];

	// If the previous sibling has children,
	// get the deeper child and place the menu as a child of it.

	const getDeeperChild = (item) => {
		if (!item.children.length) {
			return item;
		}

		return getDeeperChild(item.children.at(-1));
	};

	const deeperChild = getDeeperChild(sibling);

	if (deeperChild.children.length) {
		return {
			order: deeperChild.children.length,
			parentSiteNavigationMenuItemId:
				deeperChild.siteNavigationMenuItemId,
		};
	}
	else {
		return {
			order: 0,
			parentSiteNavigationMenuItemId:
				deeperChild.siteNavigationMenuItemId,
		};
	}
}
