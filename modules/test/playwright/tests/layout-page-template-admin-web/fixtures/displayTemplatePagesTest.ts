/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {test} from '@playwright/test';

import {DocumentLibraryEditFilePage} from '../../../pages/document-library-web/DocumentLibraryEditFilePage';
import {DisplayPageTemplatesPage} from '../../../pages/layout-page-template-admin-web/DisplayPageTemplatesPage';
import {BlogsEditBlogEntryPage} from '../../blogs-web/pages/BlogsEditBlogEntryPage';

const displayPageTemplatesTest = test.extend<{
	blogsEditBlogEntryPage: BlogsEditBlogEntryPage;
	displayPageTemplatesPage: DisplayPageTemplatesPage;
	documentLibraryEditFilePage: DocumentLibraryEditFilePage;
}>({
	blogsEditBlogEntryPage: async ({page}, use) => {
		await use(new BlogsEditBlogEntryPage(page));
	},
	displayPageTemplatesPage: async ({page}, use) => {
		await use(new DisplayPageTemplatesPage(page));
	},
	documentLibraryEditFilePage: async ({page}, use) => {
		await use(new DocumentLibraryEditFilePage(page));
	},
});

export {displayPageTemplatesTest};
