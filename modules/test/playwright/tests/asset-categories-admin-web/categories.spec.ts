/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {createCategories} from '../../helpers/CreateCategories';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {waitForAlert} from '../../utils/waitForAlert';
import {assetCategoriesPagesTest} from './fixtures/assetCategoriesAdminPagesTest';

const test = mergeTests(
	apiHelpersTest,
	assetCategoriesPagesTest,
	isolatedSiteTest,
	loginTest()
);

test('Add, edit, delete properties in category.', async ({
	apiHelpers,
	assetCategoriesAdminPage,
	assetCategoriesEditPage,
	page,
	site,
}) => {
	const categories = await createCategories({
		apiHelpers,
		categoryNames: [{name: 'category-1'}],
		site,
		vocabularyName: 'test vocabulary',
	});

	await assetCategoriesAdminPage.goto(site.friendlyUrlPath);

	await clickAndExpectToBeVisible({
		autoClick: true,
		target: page.getByRole('menuitem', {name: 'Edit'}),
		trigger: page
			.getByRole('row', {name: categories[0].name})
			.getByLabel('Show Actions'),
	});
	await assetCategoriesEditPage.addProperty(
		'key 1 - Category Property',
		'value 1 - Category Property'
	);
	await waitForAlert(page);

	await clickAndExpectToBeVisible({
		autoClick: true,
		target: page.getByRole('menuitem', {name: 'Edit'}),
		trigger: page
			.getByRole('row', {name: categories[0].name})
			.getByLabel('Show Actions'),
	});
	await assetCategoriesEditPage.addProperty(
		'key 2 - Category Property',
		'value 2 - Category Property'
	);
	await waitForAlert(page);

	await clickAndExpectToBeVisible({
		autoClick: true,
		target: page.getByRole('menuitem', {name: 'Edit'}),
		trigger: page
			.getByRole('row', {name: categories[0].name})
			.getByLabel('Show Actions'),
	});
	await assetCategoriesEditPage.propertiesTab.click();
	await expect(page.getByLabel('key').first()).toHaveValue(
		'key 1 - Category Property'
	);
	await expect(page.getByLabel('value').first()).toHaveValue(
		'value 1 - Category Property'
	);
});
