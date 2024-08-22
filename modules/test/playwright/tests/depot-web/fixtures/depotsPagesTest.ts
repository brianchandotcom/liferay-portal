/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {test} from '@playwright/test';

import {DocumentLibraryEditFilePage} from '../../../pages/document-library-web/DocumentLibraryEditFilePage';

const depotsPagesTest = test.extend<{
	documentLibraryEditFilePage: DocumentLibraryEditFilePage;
}>({
	documentLibraryEditFilePage: async ({page}, use) => {
		await use(new DocumentLibraryEditFilePage(page));
	},
});

export {depotsPagesTest};
