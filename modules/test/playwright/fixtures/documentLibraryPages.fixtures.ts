/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {test} from '@playwright/test';

import {AICreatorInstanceSettingsPage} from '../pages/document-library-web/aiCreatorSettings.page';
import {DocumentLibraryPage} from '../pages/document-library-web/documentLibrary.page';
import {DocumentLibraryEditFolderPage} from '../pages/document-library-web/documentLibraryEditFolder.page';
import {GogoShellPage} from '../pages/document-library-web/gogoShell.page';

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
