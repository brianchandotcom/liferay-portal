/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {AttachmentsPage} from '../pages/commerce/commerce-product-definitions-web/attachmentsPage';
import {DocumentLibraryEditDocumentTypesPage} from '../pages/document-library-web/DocumentLibraryEditDocumentTypesPage';
import {DocumentLibraryEditFilePage} from '../pages/document-library-web/DocumentLibraryEditFilePage';
import {DocumentLibraryEditFolderPage} from '../pages/document-library-web/DocumentLibraryEditFolderPage';
import {DocumentLibraryPage} from '../pages/document-library-web/DocumentLibraryPage';
import {AICreatorInstanceSettingsPage} from '../pages/product-navigation-applications-menu/AICreatorSettingsPage';
import {GogoShellPage} from '../pages/product-navigation-applications-menu/GogoShellPage';

const documentLibraryPagesTest = test.extend<{
	aiCreatorInstanceSettingsPage: AICreatorInstanceSettingsPage;
	attachmentsPage: AttachmentsPage;
	documentLibraryEditDocumentTypesPage: DocumentLibraryEditDocumentTypesPage;
	documentLibraryEditFilePage: DocumentLibraryEditFilePage;
	documentLibraryEditFolderPage: DocumentLibraryEditFolderPage;
	documentLibraryPage: DocumentLibraryPage;
	gogoShellPage: GogoShellPage;
}>({
	aiCreatorInstanceSettingsPage: async ({page}, use) => {
		await use(new AICreatorInstanceSettingsPage(page));
	},
	attachmentsPage: async ({page}, use) => {
		await use(new AttachmentsPage(page));
	},
	documentLibraryEditDocumentTypesPage: async ({page}, use) => {
		await use(new DocumentLibraryEditDocumentTypesPage(page));
	},
	documentLibraryEditFilePage: async ({page}, use) => {
		await use(new DocumentLibraryEditFilePage(page));
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
