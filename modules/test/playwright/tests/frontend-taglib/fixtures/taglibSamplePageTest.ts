/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {TaglibSamplePage} from '../pages/TaglibSamplePage';

const taglibSamplePageTest = test.extend<{
	taglibSamplePage: TaglibSamplePage;
}>({
	taglibSamplePage: async ({page}, use) => {
		await use(new TaglibSamplePage(page));
	},
});

export {taglibSamplePageTest};