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
import {waitForAlert} from '../../../utils/waitForAlert';

export const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	commercePagesTest,
	dataApiHelpersTest,
	loginTest()
);
test('LPD-46948 Unable to delete specification picklist items', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceSpecificationsPage,
	page,
}) => {
	const specification =
		await apiHelpers.headlessCommerceAdminCatalog.postSpecification();

	const picklist =
		await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

	const listTypeEntry = await apiHelpers.listTypeAdmin.postListTypeEntry(
		picklist.externalReferenceCode,
		'item1'
	);

	await apiHelpers.headlessCommerceAdminCatalog.patchSpecification(
		specification.id,
		[picklist.id]
	);

	await applicationsMenuPage.goToCommerceSpecifications();

	await commerceSpecificationsPage
		.specificationNameLink(specification.title.en_US)
		.click();

	await expect(
		page.getByRole('cell', {name: picklist.externalReferenceCode}).nth(1)
	).toBeVisible();

	await commerceSpecificationsPage.specificationPicklistActionButton.click();
	await commerceSpecificationsPage
		.specificationPicklistDropdownMenu('Edit')
		.click();

	await expect(page.frameLocator('iframe').locator('tbody')).toContainText(
		listTypeEntry.externalReferenceCode
	);

	await commerceSpecificationsPage.specificationPicklistItemsActionButton.click();
	await commerceSpecificationsPage
		.specificationPicklistDropdownMenuItems('Delete')
		.click();

	await expect(page.getByLabel('Delete Item')).toBeVisible();

	await commerceSpecificationsPage.deleteModalButtonAction('Delete').click();

	await waitForAlert(
		page,
		'Success:The picklist item was deleted successfully.'
	);
});
