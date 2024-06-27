/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../../fixtures/applicationsMenuPageTest';
import {commercePagesTest} from '../../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';

export const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	commercePagesTest,
	dataApiHelpersTest,
	loginTest()
);
test('LPD-28891 Key is not automatically generated when writing new Specifications label', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceProductSpecificationsPage,
}) => {
	await applicationsMenuPage.goToCommerceSpecifications();

	await expect(
		commerceProductSpecificationsPage.createNewSpecificationsProduct
	).toBeVisible();

	await commerceProductSpecificationsPage.createNewSpecificationsProduct.click();

	await commerceProductSpecificationsPage.waitForKey('Specification 1');

	await commerceProductSpecificationsPage.addDescriptionSpecifications.fill(
		'Specification-1 Description'
	);

	await expect(
		commerceProductSpecificationsPage.addDescriptionSpecifications
	).toBeVisible();

	await commerceProductSpecificationsPage.keyContent.fill('specification-1');

	await expect(commerceProductSpecificationsPage.keyContent).toHaveValue(
		'specification-1'
	);

	await commerceProductSpecificationsPage.saveButton.click();

	await expect(
		commerceProductSpecificationsPage.successMessagge
	).toBeVisible();

	await commerceProductSpecificationsPage.goBack.click();

	await commerceProductSpecificationsPage.goToSpecificationGroup.click();

	await commerceProductSpecificationsPage.createNewSpecificationsProductGroup.click();

	await commerceProductSpecificationsPage.addNewProductSpecificationsGroup.fill(
		'Specification group'
	);

	await commerceProductSpecificationsPage.addDescriptionSpecificationsGroup.fill(
		'Specification group Description'
	);

	await expect(commerceProductSpecificationsPage.keyContent).toHaveValue(
		'Specification group'
	);

	await commerceProductSpecificationsPage.saveButton.click();

	await expect(
		commerceProductSpecificationsPage.successMessagge
	).toBeVisible();

	const specifications =
		await apiHelpers.headlessCommerceAdminCatalog.getSpecifications();

	for (let i = 0; i < specifications.totalCount; i++) {
		if (specifications.items[i].title.en_US === 'Specification 1') {
			apiHelpers.data.push({
				id: specifications.items[i].id,
				type: 'specification',
			});
		}
	}

	const optionCategory =
		await apiHelpers.headlessCommerceAdminCatalog.getOptionCategories();

	for (let i = 0; i < optionCategory.totalCount; i++) {
		if (optionCategory.items[i].title.en_US === 'Specification group') {
			apiHelpers.data.push({
				id: optionCategory.items[i].id,
				type: 'optionCategory',
			});
		}
	}
});
