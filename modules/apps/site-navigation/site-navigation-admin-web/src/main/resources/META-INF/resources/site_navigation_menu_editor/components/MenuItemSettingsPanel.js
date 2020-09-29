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

import React from 'react';

import {useConstants} from '../contexts/ConstantsContext';
import {useItems} from '../contexts/ItemsContext';
import {useSelectedMenuItemId} from '../contexts/SelectedMenuItemIdContext';
import {SidebarPanelContent} from './SidebarPanelContent';

export function MenuItemSettingsPanel() {
	const {editSiteNavigationMenuItemURL} = useConstants();
	const items = useItems();

	const selectedMenuItemId = useSelectedMenuItemId();

	const title = items.find(
		(item) => item.siteNavigationMenuItemId === selectedMenuItemId
	)?.title;

	return (
		<SidebarPanelContent
			contentRequestBody={{
				siteNavigationMenuItemId: selectedMenuItemId,
			}}
			contentUrl={editSiteNavigationMenuItemURL}
			title={title}
		/>
	);
}
