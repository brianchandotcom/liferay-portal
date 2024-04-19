/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {ScriptManagementPage} from '../pages/portal-security-script-management-web/ScriptManagementPage';

export const scriptManagementPagesTest = test.extend<{
	scriptManagementPage: ScriptManagementPage;
}>({
	scriptManagementPage: async ({page}, use) => {
		await use(new ScriptManagementPage(page));
	},
});
