/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {test} from '@playwright/test';

import {DocumentLibraryEditFilePage} from '../../../pages/document-library-web/DocumentLibraryEditFilePage';
import {DisplayPageTemplatesPage} from '../../../pages/layout-page-template-admin-web/DisplayPageTemplatesPage';
import {BlogsEditBlogEntryPage} from '../../blogs-web/pages/BlogsEditBlogEntryPage';
import {JournalEditArticlePage} from '../../journal-web/pages/JournalEditArticlePage';
import {JournalPage} from '../../journal-web/pages/JournalPage';

const displayPageTemplatesTest = test.extend<{
	blogsEditBlogEntryPage: BlogsEditBlogEntryPage;
	displayPageTemplatesPage: DisplayPageTemplatesPage;
	documentLibraryEditFilePage: DocumentLibraryEditFilePage;
	journalEditArticlePage: JournalEditArticlePage;
	journalPage: JournalPage;
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
	journalEditArticlePage: async ({page}, use) => {
		await use(new JournalEditArticlePage(page));
	},
	journalPage: async ({page}, use) => {
		await use(new JournalPage(page));
	},
});

export {displayPageTemplatesTest};
