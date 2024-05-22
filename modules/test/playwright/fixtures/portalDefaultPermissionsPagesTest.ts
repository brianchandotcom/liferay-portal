/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {PortalDefaultPermissionsConfigurationPage} from '../pages/portal-default-permissions-web/PortalDefaultPermissionsConfigurationPage';
import {PortalDefaultPermissionsSiteConfigurationPage} from '../pages/portal-default-permissions-web/PortalDefaultPermissionsSiteConfigurationPage';

const portalDefaultPermissionsPagesTest = test.extend<{
	defaultPermissionsConfigurationPage: PortalDefaultPermissionsConfigurationPage;
	defaultPermissionsSiteConfigurationPage: PortalDefaultPermissionsSiteConfigurationPage;
}>({
	defaultPermissionsConfigurationPage: async ({page}, use) => {
		await use(new PortalDefaultPermissionsConfigurationPage(page));
	},
	defaultPermissionsSiteConfigurationPage: async ({page}, use) => {
		await use(new PortalDefaultPermissionsSiteConfigurationPage(page));
	},
});

export {portalDefaultPermissionsPagesTest};
