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
	commerceSpecificationsPage,
}) => {
	await applicationsMenuPage.goToCommerceSpecifications();

	await expect(
		commerceSpecificationsPage.createNewSpecificationsProduct
	).toBeVisible();

	await commerceSpecificationsPage.createNewSpecificationsProduct.click();

	await commerceSpecificationsPage.waitForKey('Specification 1');

	await commerceSpecificationsPage.addDescriptionSpecifications.fill(
		'Specification-1 Description'
	);

	await expect(
		commerceSpecificationsPage.addDescriptionSpecifications
	).toBeVisible();

	await commerceSpecificationsPage.keyContent.fill('specification-1');

	await expect(commerceSpecificationsPage.keyContent).toHaveValue(
		'specification-1'
	);

	await commerceSpecificationsPage.saveButton.click();

	await expect(commerceSpecificationsPage.successMessage).toBeVisible();

	await commerceSpecificationsPage.goBack.click();

	await commerceSpecificationsPage.goToSpecificationGroup.click();

	await commerceSpecificationsPage.createNewSpecificationsProductGroup.click();

	await commerceSpecificationsPage.addNewProductSpecificationsGroup.fill(
		'Specification group'
	);

	await commerceSpecificationsPage.addDescriptionSpecificationsGroup.fill(
		'Specification group Description'
	);

	await expect(commerceSpecificationsPage.keyContent).toHaveValue(
		'Specification group'
	);

	await commerceSpecificationsPage.saveButton.click();

	await expect(commerceSpecificationsPage.successMessage).toBeVisible();

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
