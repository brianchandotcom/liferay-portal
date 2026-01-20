/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {StyleBooksPage} from '../pages/style-book-web/StyleBooksPage';
import {StyleBooksHelper} from '../tests/style-book-web/main/helpers/StyleBooksHelper';

const styleBooksPageTest = test.extend<{
	styleBooksHelper: StyleBooksHelper;
	styleBooksPage: StyleBooksPage;
}>({
	styleBooksHelper: async ({page}, use) => {
		await use(new StyleBooksHelper(page));
	},

	styleBooksPage: async ({page}, use) => {
		await use(new StyleBooksPage(page));
	},
});

export {styleBooksPageTest as styleBookPageTest};
