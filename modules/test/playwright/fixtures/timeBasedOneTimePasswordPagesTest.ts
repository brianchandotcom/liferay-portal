/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {TimeBasedOneTimePasswordConfigurationPage} from '../pages/multi-factor-authentication/TimeBasedOneTimePasswordConfigurationPage';

const timeBasedOneTimePasswordConfigurationPageTest = test.extend<{
	timeBasedOneTimePasswordConfigurationPage: TimeBasedOneTimePasswordConfigurationPage;
}>({
	timeBasedOneTimePasswordConfigurationPage: async ({page}, use) => {
		await use(new TimeBasedOneTimePasswordConfigurationPage(page));
	},
});

export {timeBasedOneTimePasswordConfigurationPageTest};
