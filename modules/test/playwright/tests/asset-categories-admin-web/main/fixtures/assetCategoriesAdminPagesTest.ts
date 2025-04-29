/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {AssetCategoriesAdminPage} from '../pages/AssetCategoriesAdminPage';
import {AssetCategoriesEditPage} from '../pages/AssetCategoriesEditPage';
import {VocabulariesEditPage} from '../pages/VocabulariesEditPage';

export const assetCategoriesPagesTest = test.extend<{
	assetCategoriesAdminPage: AssetCategoriesAdminPage;
	assetCategoriesEditPage: AssetCategoriesEditPage;
	vocabulariesEditPage: VocabulariesEditPage;
}>({
	assetCategoriesAdminPage: async ({page}, use) => {
		await use(new AssetCategoriesAdminPage(page));
	},
	assetCategoriesEditPage: async ({page}, use) => {
		await use(new AssetCategoriesEditPage(page));
	},
	vocabulariesEditPage: async ({page}, use) => {
		await use(new VocabulariesEditPage(page));
	},
});
