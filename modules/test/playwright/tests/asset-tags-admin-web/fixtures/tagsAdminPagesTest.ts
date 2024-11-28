/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {TagsAdminPage} from '../pages/TagsAdminPage';
import {TagsEditPage} from '../pages/TagsEditPage';

export const tagsPagesTest = test.extend<{
	tagsAdminPage: TagsAdminPage;
	tagsEditPage: TagsEditPage;
}>({
	tagsAdminPage: async ({page}, use) => {
		await use(new TagsAdminPage(page));
	},
	tagsEditPage: async ({page}, use) => {
		await use(new TagsEditPage(page));
	},
});
