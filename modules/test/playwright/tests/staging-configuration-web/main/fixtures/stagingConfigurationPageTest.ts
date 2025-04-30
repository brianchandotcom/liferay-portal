/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {StagingConfigurationPage} from '../pages/StagingConfigurationPage';

const stagingConfigurationPageTest = test.extend<{
	stagingConfigurationPage: StagingConfigurationPage;
}>({
	stagingConfigurationPage: async ({page}, use) => {
		await use(new StagingConfigurationPage(page));
	},
});

export {stagingConfigurationPageTest};
