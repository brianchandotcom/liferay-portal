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
import {ClayDropDownWithItems} from '@clayui/drop-down';
import {fetch, objectToFormData, openToast, sub} from 'frontend-js-web';
import React, {useState} from 'react';

import {DELETION_TYPES} from '../constants/deletionTypes';
import {SIDEBAR_PANEL_IDS} from '../constants/sidebarPanelIds';
import {useConstants} from '../contexts/ConstantsContext';
import {useSetItems} from '../contexts/ItemsContext';
import {useSetSelectedMenuItemId} from '../contexts/SelectedMenuItemIdContext';
import {useSetSidebarPanelId} from '../contexts/SidebarPanelIdContext';
import getFlatItems from '../utils/getFlatItems';
import DeletionModal from './DeletionModal';

export default function MenuItemOptions({
	isTarget,
	label,
	numberOfChildren,
	onMenuItemRemoved,
	siteNavigationMenuItemId,
}) {
	const {
		addSiteNavigationMenuItemOptions,
		deleteSiteNavigationMenuItemURL,
		portletNamespace,
	} = useConstants();

	const setSelectedMenuItemId = useSetSelectedMenuItemId();
	const setSidebarPanelId = useSetSidebarPanelId();
	const setItems = useSetItems();

	const [deletionType, setDeletionType] = useState(DELETION_TYPES.single);
	const [deletionModalVisible, setDeletionModalVisible] = useState(false);

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
			.catch((error) => {
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

	const items = [
		{
			items: addSiteNavigationMenuItemOptions.map((child) => ({
				...child,
				onClick: () =>
					child.onClick({
						order: numberOfChildren,
						parentSiteNavigationMenuItemId: siteNavigationMenuItemId,
					}),
			})),
			label: Liferay.Language.get('add-child'),
			symbolLeft: 'plus',
			symbolRight: 'angle-right',
			type: 'contextual',
		},
		{
			label: Liferay.Language.get('view-info'),
			onClick: () => {
				setSelectedMenuItemId(siteNavigationMenuItemId);
				setSidebarPanelId(SIDEBAR_PANEL_IDS.menuItemSettings);
			},
			symbolLeft: 'info-circle-open',
		},
		{
			type: 'divider',
		},
		{
			label: Liferay.Language.get('delete'),
			onClick: () =>
				numberOfChildren > 0
					? setDeletionModalVisible(true)
					: deleteMenuItem(),
			symbolLeft: 'trash',
		},
	];

	return (
		<>
			<ClayDropDownWithItems
				items={items}
				menuElementAttrs={{
					containerProps: {
						className: 'menu-item-dropdown',
					},
				}}
				trigger={
					<ClayButtonWithIcon
						aria-label={sub(
							Liferay.Language.get('view-x-options'),
							label
						)}
						borderless
						className="menu-item-options-button"
						displayType="secondary"
						size="sm"
						symbol="ellipsis-v"
						tabIndex={isTarget ? '0' : '-1'}
						title={sub(
							Liferay.Language.get('view-x-options'),
							label
						)}
					/>
				}
			/>

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
