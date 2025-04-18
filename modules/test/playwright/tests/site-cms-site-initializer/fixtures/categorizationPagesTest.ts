/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {CategoriesPage} from '../pages/CategoriesPage';
import {EditCategoryPage} from '../pages/EditCategoryPage';

const categorizationPagesTest = test.extend<{
	categoriesPage: CategoriesPage;
	editCategoryPage: EditCategoryPage;
}>({
	categoriesPage: async ({page}, use) => {
		await use(new CategoriesPage(page));
	},
	editCategoryPage: async ({page}, use) => {
		await use(new EditCategoryPage(page));
	},
});

export {categorizationPagesTest};
