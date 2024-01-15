/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {test} from '@playwright/test';

import {DocumentLibraryPage} from '../pages/document-library-web/documentLibrary.page';
import {DocumentLibraryEditFolderPage} from '../pages/document-library-web/documentLibraryEditFolder.page';

const documentLibraryPagesTest = test.extend<{
	_documentLibraryEditFolderPage: DocumentLibraryEditFolderPage;
	_documentLibraryPage: DocumentLibraryPage;
}>({
	_documentLibraryEditFolderPage: async ({page}, use) => {
		await use(new DocumentLibraryEditFolderPage(page));
	},
	_documentLibraryPage: async ({page}, use) => {
		await use(new DocumentLibraryPage(page));
	},
});

export {documentLibraryPagesTest};
