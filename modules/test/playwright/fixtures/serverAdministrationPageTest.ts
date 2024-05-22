/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {ServerAdministrationPage} from '../pages/server-admin-web/ServerAdministrationPage';

const serverAdministrationPageTest = test.extend<{
	serverAdministrationPage: ServerAdministrationPage;
}>({
	serverAdministrationPage: async ({page}, use) => {
		await use(new ServerAdministrationPage(page));
	},
});

export {serverAdministrationPageTest};
