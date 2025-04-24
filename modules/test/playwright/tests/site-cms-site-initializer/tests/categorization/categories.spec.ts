/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../../fixtures/loginTest';
import {clickAndExpectToBeVisible} from '../../../../utils/clickAndExpectToBeVisible';
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

let vocabularyName: string;
let vocabularyId: number;

test.beforeEach('Create Vocabulary via API', async ({apiHelpers, site}) => {
	vocabularyName = getRandomString();

	vocabularyId = await apiHelpers.headlessAdminTaxonomy
		.postSiteTaxonomyVocabulary({
			name: vocabularyName,
			siteId: site.id,
		})
		.then((response) => response.id);
});

test.afterEach(async ({apiHelpers}) => {
	await apiHelpers.headlessAdminTaxonomy.deleteTaxonomyVocabulary(
		vocabularyId
	);
});

test.describe('Category tests that focus on creation', () => {
	test(
		'Categories can be created within a Vocabulary with both the "Save and Add Another" and "Save" buttons',
		{tag: '@LPD-32753'},
		async ({categoriesPage, editCategoryPage}) => {
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

	test(
		'Validate the create Category form inputs when saving',
		{tag: '@LPD-32753'},
		async ({editCategoryPage, page}) => {
			await editCategoryPage.gotoCreateCategory(vocabularyId);

			// Shouldn't be able to save if Name field is empty

			await clickAndExpectToBeVisible({
				target: page.getByText('The Name field is required'),
				trigger: editCategoryPage.saveButton,
			});
		}
	);
});

test.describe("Category tests that don't focus on creation", () => {
	let categoryName: string;
	let categoryId: number;

	test.beforeEach('Create Category via API', async ({apiHelpers}) => {
		categoryName = getRandomString();

		categoryId = await apiHelpers.headlessAdminTaxonomy
			.postTaxonomyVocabularyTaxonomyCategory({
				name: categoryName,
				vocabularyId,
			})
			.then((response) => response.id);
	});

	test(
		"Edit a Vocabulary's Category",
		{tag: '@LPD-53252'},
		async ({categoriesPage, editCategoryPage, page}) => {
			await categoriesPage.goto(vocabularyId, vocabularyName);

			await page.getByRole('link', {name: categoryName}).click();

			await expect(page.getByText(`Edit ${categoryName}`)).toBeVisible();

			const newCategoryName = getRandomString();
			const newCategoryDescription = getRandomString();

			await editCategoryPage.fillName(newCategoryName);
			await editCategoryPage.fillDescription(newCategoryDescription);

			await editCategoryPage.clickSave();
			await editCategoryPage.handleEditConfirmationModal(true);

			await categoriesPage.assertBreadcrumbItemText(0, 'Categorization');

			await expect(categoriesPage.getItem(newCategoryName)).toBeVisible();

			await editCategoryPage.gotoEditCategory(categoryId);

			await expect(page.getByText(newCategoryDescription)).toBeVisible();
		}
	);

	test(
		"Visit the edit page of a Vocabulary's Category from dropdown actions",
		{tag: '@LPD-53252'},
		async ({categoriesPage, page}) => {
			await categoriesPage.goto(vocabularyId, vocabularyName);

			await categoriesPage.execItemAction({
				action: 'Edit',
				filter: categoryName,
			});

			await expect(page.getByText(`Edit ${categoryName}`)).toBeVisible();
		}
	);

	test(
		"Delete a Vocabulary's Category from dropdown actions",
		{tag: '@LPD-53252'},
		async ({categoriesPage, page}) => {
			await categoriesPage.goto(vocabularyId, vocabularyName);

			await categoriesPage.execItemAction({
				action: 'Delete',
				filter: categoryName,
			});

			await categoriesPage.handleDeleteConfirmationModal(true);

			await expect(
				page.getByText('Success:Your request completed successfully.')
			).toBeVisible();
			await expect(categoriesPage.getItem(categoryName)).toBeHidden();
		}
	);

	test(
		"Edit a Category's permissions from dropdown actions",
		{tag: '@LPD-53252'},
		async ({categoriesPage, page}) => {
			await categoriesPage.goto(vocabularyId, vocabularyName);

			await categoriesPage.execItemAction({
				action: 'Permissions',
				filter: categoryName,
			});

			await expect(
				page.getByRole('heading', {name: 'Permissions'})
			).toBeVisible();
		}
	);
});
