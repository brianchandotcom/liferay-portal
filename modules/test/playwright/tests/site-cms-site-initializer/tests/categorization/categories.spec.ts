/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../../fixtures/loginTest';
import getRandomString from '../../../../utils/getRandomString';
import {categorizationPagesTest} from '../../fixtures/categorizationPagesTest';

const test = mergeTests(
	categorizationPagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-11232': {enabled: true},
		'LPD-17564': {enabled: true},
	}),
	loginTest(),
	isolatedSiteTest
);

test(
	'Categories can be created within a Vocabulary with both the "Save and Add Another" and "Save" buttons',
	{tag: '@LPD-32753'},
	async ({apiHelpers, categoriesPage, editCategoryPage, site}) => {
		const vocabularyName: string = getRandomString();

		const {id: vocabularyId} =
			await apiHelpers.headlessAdminTaxonomy.postSiteTaxonomyVocabulary({
				name: vocabularyName,
				siteId: site.id,
			});

		await categoriesPage.goto(vocabularyId, vocabularyName);

		await categoriesPage.clickCreateNewCategoryButton();

		const categoryName1: string = getRandomString();

		await editCategoryPage.fillName(categoryName1);
		await editCategoryPage.fillDescription(getRandomString());

		await editCategoryPage.clickSaveAndAddAnother();

		const categoryName2: string = getRandomString();

		await editCategoryPage.fillName(categoryName2);
		await editCategoryPage.fillDescription(getRandomString());

		await editCategoryPage.clickSave();

		await categoriesPage.assertBreadcrumbItemText(0, 'Categorization');

		await expect(categoriesPage.getItem(categoryName1)).toBeVisible();
		await expect(categoriesPage.getItem(categoryName2)).toBeVisible();
	}
);
