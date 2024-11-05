/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {NewTaxCategoryPage} from '../pages/commerce/commerce-tax-engine-web/NewTaxCategoryPage';
import {TaxCategoriesPage} from '../pages/commerce/commerce-tax-engine-web/TaxCategoriesPage';

const taxCategoriesPageTest = test.extend<{
	newTaxCategoryPage: NewTaxCategoryPage;
	taxCategoriesPage: TaxCategoriesPage;
}>({
	newTaxCategoryPage: async ({page}, use) => {
		await use(new NewTaxCategoryPage(page));
	},
	taxCategoriesPage: async ({page}, use) => {
		await use(new TaxCategoriesPage(page));
	},
});

export {taxCategoriesPageTest};
