/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {commercePagesTest} from '../../../../fixtures/commercePagesTest';
import {customFieldsPagesTest} from '../../../../fixtures/customFieldsPagesTest';
import {dataApiHelpersTest} from '../../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../../fixtures/loginTest';
import {TCustomField} from '../../../../helpers/CustomFieldTypesHelper';
import getRandomString from '../../../../utils/getRandomString';
import {waitForAlert} from '../../../../utils/waitForAlert';

export const test = mergeTests(
	commercePagesTest,
	customFieldsPagesTest,
	dataApiHelpersTest,
	loginTest()
);

test(
	'Add a pre-existing and a new product group to a product, then remove them',
	{tag: ['@LPD-87061']},
	async ({
		apiHelpers,
		commerceAdminProductDetailsPage,
		commerceAdminProductDetailsProductGroupsPage,
		commerceAdminProductPage,
		page,
	}) => {
		const existingGroupName = 'PG1-' + getRandomString();
		const newGroupName = 'PG2-' + getRandomString();

		await apiHelpers.headlessCommerceAdminCatalog.postProductGroup({
			title: existingGroupName,
		});

		const catalog =
			await apiHelpers.headlessCommerceAdminCatalog.postCatalog();

		const product =
			await apiHelpers.headlessCommerceAdminCatalog.postProduct({
				catalogId: catalog.id,
				name: {en_US: 'Simple T-Shirt ' + getRandomString()},
			});

		try {
			await commerceAdminProductPage.gotoProduct(product.name.en_US);
			await commerceAdminProductDetailsPage.goToProductGroups();

			await test.step('Search the existing group from the autocomplete and select it', async () => {
				await commerceAdminProductDetailsProductGroupsPage.findOrCreateInput.fill(
					existingGroupName
				);
				await commerceAdminProductDetailsProductGroupsPage
					.selectExistingSuggestion(existingGroupName)
					.click();

				await expect(
					commerceAdminProductDetailsProductGroupsPage.productGroupRow(
						existingGroupName
					)
				).toBeVisible();
			});

			await test.step('Create a new group from the autocomplete', async () => {
				await commerceAdminProductDetailsProductGroupsPage.findOrCreateInput.fill(
					newGroupName
				);
				await commerceAdminProductDetailsProductGroupsPage
					.createNewSuggestion(newGroupName)
					.click();

				await expect(
					commerceAdminProductDetailsProductGroupsPage.productGroupRow(
						newGroupName
					)
				).toBeVisible();
			});

			await test.step('Remove both groups from the product', async () => {
				await page.keyboard.press('Escape');

				await commerceAdminProductDetailsProductGroupsPage
					.rowActionsButton(existingGroupName)
					.click();
				await commerceAdminProductDetailsProductGroupsPage.removeMenuItem.click();

				await expect(
					commerceAdminProductDetailsProductGroupsPage.productGroupRow(
						existingGroupName
					)
				).toBeHidden();

				await commerceAdminProductDetailsProductGroupsPage
					.rowActionsButton(newGroupName)
					.click();
				await commerceAdminProductDetailsProductGroupsPage.removeMenuItem.click();

				await expect(
					commerceAdminProductDetailsProductGroupsPage.productGroupRow(
						newGroupName
					)
				).toBeHidden();
			});
		}
		finally {
			const productGroupsPage =
				await apiHelpers.headlessCommerceAdminCatalog.getProductGroups(
					new URLSearchParams({search: newGroupName})
				);

			for (const productGroup of productGroupsPage?.items ?? []) {
				if (productGroup.title?.en_US === newGroupName) {
					apiHelpers.data.push({
						id: productGroup.id,
						type: 'productGroup',
					});
				}
			}
		}
	}
);

test(
	'Edit a custom field on a product group from the product side panel',
	{tag: ['@COMMERCE-6068', '@LPD-87061']},
	async ({
		addCustomFieldPage,
		apiHelpers,
		commerceAdminProductDetailsPage,
		commerceAdminProductDetailsProductGroupsPage,
		commerceAdminProductPage,
		page,
		viewAttributesPage,
	}) => {
		const customFieldName = 'CF-' + getRandomString();
		const customFieldValue = 'Test';
		const groupName = 'PG-EditTest-' + getRandomString();

		const customField: TCustomField = {
			fieldName: customFieldName,
			fieldType: 'inputField',
			resource: 'Product Group',
		};

		await addCustomFieldPage.addCustomField(customField);

		const catalog =
			await apiHelpers.headlessCommerceAdminCatalog.postCatalog();

		const product =
			await apiHelpers.headlessCommerceAdminCatalog.postProduct({
				catalogId: catalog.id,
				name: {en_US: 'Simple T-Shirt ' + getRandomString()},
			});

		try {
			await commerceAdminProductPage.gotoProduct(product.name.en_US);
			await commerceAdminProductDetailsPage.goToProductGroups();

			await test.step('Create a new group from the autocomplete', async () => {
				await commerceAdminProductDetailsProductGroupsPage.findOrCreateInput.fill(
					groupName
				);
				await commerceAdminProductDetailsProductGroupsPage
					.createNewSuggestion(groupName)
					.click();

				await expect(
					commerceAdminProductDetailsProductGroupsPage.productGroupRow(
						groupName
					)
				).toBeVisible();
			});

			await test.step('Edit the product group from side panel and fill the custom field', async () => {
				await page.keyboard.press('Escape');

				await commerceAdminProductDetailsProductGroupsPage
					.productGroupNameLink(groupName)
					.click();

				await commerceAdminProductDetailsProductGroupsPage
					.sidePanelCustomFieldInput(customFieldName)
					.fill(customFieldValue);

				await commerceAdminProductDetailsProductGroupsPage.sidePanelSaveButton.click();

				await waitForAlert(page, 'Item Created');

				await expect(
					commerceAdminProductDetailsProductGroupsPage.sidePanelCustomFieldInput(
						customFieldName
					)
				).toHaveValue(customFieldValue);
			});
		}
		finally {
			const productGroupsPage =
				await apiHelpers.headlessCommerceAdminCatalog.getProductGroups(
					new URLSearchParams({search: groupName})
				);

			for (const productGroup of productGroupsPage?.items ?? []) {
				if (productGroup.title?.en_US === groupName) {
					apiHelpers.data.push({
						id: productGroup.id,
						type: 'productGroup',
					});
				}
			}

			await viewAttributesPage.deleteCustomField(
				customFieldName,
				'Product Group'
			);
		}
	}
);
