/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {test} from '@playwright/test';

import {DocumentLibraryPage} from '../pages/document-library-web/DocumentLibraryPage';
import {DocumentLibraryEditFolderPage} from '../pages/document-library-web/DocumentLibraryEditFolderPage';
import {AICreatorInstanceSettingsPage} from '../pages/product-navigation-applications-menu/AICreatorSettingsPage';
import {GogoShellPage} from '../pages/product-navigation-applications-menu/GogoShellPage';

const documentLibraryPagesTest = test.extend<{
	aiCreatorInstanceSettingsPage: AICreatorInstanceSettingsPage;
	documentLibraryEditFolderPage: DocumentLibraryEditFolderPage;
	documentLibraryPage: DocumentLibraryPage;
	gogoShellPage: GogoShellPage;
}>({
	aiCreatorInstanceSettingsPage: async ({page}, use) => {
		await use(new AICreatorInstanceSettingsPage(page));
	},
	documentLibraryEditFolderPage: async ({page}, use) => {
		await use(new DocumentLibraryEditFolderPage(page));
	},
	documentLibraryPage: async ({page}, use) => {
		await use(new DocumentLibraryPage(page));
	},
	gogoShellPage: async ({page}, use) => {
		await use(new GogoShellPage(page));
	},
});

export {documentLibraryPagesTest};
