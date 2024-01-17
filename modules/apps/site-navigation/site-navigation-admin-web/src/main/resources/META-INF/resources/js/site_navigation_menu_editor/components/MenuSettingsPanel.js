/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useMemo} from 'react';

import {useConstants} from '../contexts/ConstantsContext';
import {SidebarPanelContent} from './SidebarPanelContent';

export function MenuSettingsPanel({configButtonRef, titleId}) {
	const {
		editSiteNavigationMenuSettingsURL,
		siteNavigationMenuId,
		siteNavigationMenuName,
	} = useConstants();

	const contentRequestBody = useMemo(() => ({siteNavigationMenuId}), [
		siteNavigationMenuId,
	]);

	return (
		<SidebarPanelContent
			configButtonRef={configButtonRef}
			contentRequestBody={contentRequestBody}
			contentUrl={editSiteNavigationMenuSettingsURL}
			title={siteNavigationMenuName}
			titleId={titleId}
		/>
	);
}
