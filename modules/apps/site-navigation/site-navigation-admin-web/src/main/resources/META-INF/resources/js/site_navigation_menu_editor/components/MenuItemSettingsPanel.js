/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useMemo} from 'react';

import {useConstants} from '../contexts/ConstantsContext';
import {useItems} from '../contexts/ItemsContext';
import {useSelectedMenuItemId} from '../contexts/SelectedMenuItemIdContext';
import {SidebarPanelContent} from './SidebarPanelContent';

export function MenuItemSettingsPanel({configButtonRef, titleId}) {
	const {editSiteNavigationMenuItemURL} = useConstants();
	const items = useItems();

	const selectedMenuItemId = useSelectedMenuItemId();

	const title = items.find(
		(item) => item.siteNavigationMenuItemId === selectedMenuItemId
	)?.title;

	const contentRequestBody = useMemo(
		() => ({siteNavigationMenuItemId: selectedMenuItemId}),
		[selectedMenuItemId]
	);

	return (
		<SidebarPanelContent
			configButtonRef={configButtonRef}
			contentRequestBody={contentRequestBody}
			contentUrl={editSiteNavigationMenuItemURL}
			title={title}
			titleId={titleId}
		/>
	);
}
