/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {createCategories} from '../../helpers/CreateCategories';
import {assetCategoriesPagesTest} from './fixtures/assetCategoriesAdminPagesTest';

const test = mergeTests(
	apiHelpersTest,
	assetCategoriesPagesTest,
	isolatedSiteTest,
	loginTest()
);

test('User can add, edit, delete properties in category.', async ({
	apiHelpers,
	assetCategoriesAdminPage,
	assetCategoriesEditPage,
	page,
	site,
}) => {
	const categoryName = 'category-1';
	const properties = {
		'key 1 - Category Property': 'value 1 - Category Property',
		'key 2 - Category Property': 'value 2 - Category Property',
		'key 3 - Category Property': 'value 3 - Category Property',
	};
	await createCategories({
		apiHelpers,
		categoryNames: [{name: categoryName}],
		site,
		vocabularyName: 'test vocabulary',
	});

	await assetCategoriesAdminPage.goto(site.friendlyUrlPath);

	await test.step('add', async () => {
		await assetCategoriesEditPage.goto(categoryName);
		await assetCategoriesEditPage.addProperties(properties);

		await assetCategoriesEditPage.goToPropertiesTab(categoryName);

		await expect(page.getByLabel('key').first()).toHaveValue(
			Object.keys(properties)[0]
		);
		await expect(page.getByLabel('value').nth(1)).toHaveValue(
			Object.values(properties)[1]
		);
		await expect(page.getByLabel('value')).toHaveCount(3);
	});

	await test.step('edit', async () => {
		const editedValue = 'value 2 - EDITED Category Property';
		await page.getByLabel('value').nth(1).fill(editedValue);
		await assetCategoriesEditPage.save();

		await assetCategoriesEditPage.goToPropertiesTab(categoryName);
		await expect(page.getByLabel('value').nth(1)).toHaveValue(editedValue);
	});

	await test.step('delete', async () => {
		await page.getByRole('button', {name: 'Remove'}).nth(1).click();
		await assetCategoriesEditPage.save();

		await assetCategoriesEditPage.goToPropertiesTab(categoryName);
		await expect(page.getByLabel('value').nth(1)).toHaveValue(
			Object.values(properties)[2]
		);
		await expect(page.getByLabel('value')).toHaveCount(2);
	});
});
