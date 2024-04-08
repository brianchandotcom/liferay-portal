/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {BlogsEditBlogEntryPage} from '../pages/BlogsEditBlogEntryPage';
import {BlogsPage} from '../pages/BlogsPage';

const blogsPagesTest = test.extend<{
	blogsEditBlogEntryPage: BlogsEditBlogEntryPage;
	blogsPage: BlogsPage;
}>({
	blogsEditBlogEntryPage: async ({page}, use) => {
		await use(new BlogsEditBlogEntryPage(page));
	},
	blogsPage: async ({page}, use) => {
		await use(new BlogsPage(page));
	},
});

export {blogsPagesTest};
