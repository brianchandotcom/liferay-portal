/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {SystemDataSetsPage} from '../pages/SystemDataSetsPage';

const systemDataSetsPageTest = test.extend<{
	systemDataSetsPage: SystemDataSetsPage;
}>({
	systemDataSetsPage: async ({page}, use) => {
		await use(new SystemDataSetsPage(page));
	},
});

export {systemDataSetsPageTest};
