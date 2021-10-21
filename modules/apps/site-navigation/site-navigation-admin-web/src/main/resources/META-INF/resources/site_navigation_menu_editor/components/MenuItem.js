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
import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import classNames from 'classnames';
import {fetch, objectToFormData, openToast} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React from 'react';

import {NESTING_MARGIN} from '../constants/nestingMargin';
import {SIDEBAR_PANEL_IDS} from '../constants/sidebarPanelIds';
import {useConstants} from '../contexts/ConstantsContext';
import {useItems, useSetItems} from '../contexts/ItemsContext';
import {
	useSelectedMenuItemId,
	useSetSelectedMenuItemId,
} from '../contexts/SelectedMenuItemIdContext';
import {useSetSidebarPanelId} from '../contexts/SidebarPanelIdContext';
import deleteItem from '../utils/deleteItem';
import getItemPath from '../utils/getItemPath';
import {useDragItem, useDropTarget} from '../utils/useDragAndDrop';

export const MenuItem = ({item}) => {
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

	const deleteMenuItem = () => {
		fetch(deleteSiteNavigationMenuItemURL, {
			body: objectToFormData({
				[`${portletNamespace}siteNavigationMenuItemId`]: siteNavigationMenuItemId,
			}),
			method: 'POST',
		})
			.then(() => {
				const newItems = deleteItem(items, siteNavigationMenuItemId);

				setItems(newItems);
			})
			.catch(() => {
				openToast({
					message: Liferay.Language.get(
						'an-unexpected-error-occurred'
					),
					type: 'danger',
				});
			});
	};

	const updateMenuItemParent = (itemId, parentId) => {
		const order = items
			.filter((item) => item.parentSiteNavigationMenuItemId === parentId)
			.findIndex((item) => item.siteNavigationMenuItemId === itemId);

		fetch(editSiteNavigationMenuItemParentURL, {
			body: objectToFormData({
				[`${portletNamespace}siteNavigationMenuItemId`]: itemId,
				[`${portletNamespace}parentSiteNavigationMenuItemId`]: parentId,
				[`${portletNamespace}order`]: order,
			}),
			method: 'POST',
		}).catch(() => {
			openToast({
				message: Liferay.Language.get('an-unexpected-error-occurred'),
				type: 'danger',
			});
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

	return (
		<div
			aria-label={`${title} (${type})`}
			aria-level={itemPath.length}
			data-item-id={item.siteNavigationMenuItemId}
			data-parent-item-id={parentItemId}
			ref={targetRef}
			role="listitem"
		>
			<ClayCard
				className={classNames('site_navigation_menu_editor_MenuItem', {
					'dragging': isDragging,
					'site_navigation_menu_editor_MenuItem--selected': selected,
				})}
				selectable
				style={itemStyle}
			>
				<ClayCheckbox
					aria-label={Liferay.Util.sub(
						Liferay.Language.get('select-x'),
						`${title} (${type})`
					)}
					checked={selected}
					onChange={() => {
						setSelectedMenuItemId(siteNavigationMenuItemId);
						setSidebarPanelId(SIDEBAR_PANEL_IDS.menuItemSettings);
					}}
				>
					<ClayCard.Body className="px-0">
						<ClayCard.Row>
							<ClayLayout.ContentCol gutters ref={handlerRef}>
								<ClayIcon symbol="drag" />
							</ClayLayout.ContentCol>

							<ClayLayout.ContentCol expand>
								<ClayCard.Description displayType="title">
									{title}
								</ClayCard.Description>

								<ClayCard.Description displayType="subtitle">
									{type}
								</ClayCard.Description>
							</ClayLayout.ContentCol>

							<ClayLayout.ContentCol gutters>
								<ClayButtonWithIcon
									aria-label={Liferay.Util.sub(
										Liferay.Language.get('delete-x'),
										`${title} (${type})`
									)}
									displayType="unstyled"
									onClick={deleteMenuItem}
									small
									symbol="times-circle"
								/>
							</ClayLayout.ContentCol>
						</ClayCard.Row>
					</ClayCard.Body>
				</ClayCheckbox>
			</ClayCard>
		</div>
	);
};

MenuItem.propTypes = {
	item: PropTypes.shape({
		children: PropTypes.array.isRequired,
		siteNavigationMenuItemId: PropTypes.string.isRequired,
		title: PropTypes.string.isRequired,
		type: PropTypes.string.isRequired,
	}),
};
