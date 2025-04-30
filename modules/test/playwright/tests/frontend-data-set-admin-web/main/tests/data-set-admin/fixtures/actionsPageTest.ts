/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {ActionsPage} from '../pages/data_set/tabs/ActionsPage';

const actionsPageTest = test.extend<{
	actionsPage: ActionsPage;
}>({
	actionsPage: async ({page}, use) => {
		await use(new ActionsPage(page));
	},
});

export {actionsPageTest};
