/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {commercePagesTest} from '../../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';
import {taxCategoriesPageTest} from '../../../fixtures/taxCategoriesPageTest';
import {waitForAlert} from '../../../utils/waitForAlert';

export const test = mergeTests(
	commercePagesTest,
	dataApiHelpersTest,
	taxCategoriesPageTest,
	loginTest()
);

async function verifyFixedTaxRate(
	commerceAdminChannelDetailsPage,
	name: string,
	taxAmount: string
) {
	const tableName = 'Tax Calculations';

	await (
		await commerceAdminChannelDetailsPage.generalCommerceAdminChannelTableLink(
			'Fixed Tax Rate'
		)
	).click();
	await (
		await commerceAdminChannelDetailsPage.taxRatesTab(tableName)
	).click();

	await expect(
		(
			await commerceAdminChannelDetailsPage.sidePanelFrame(tableName)
		).getByText(name)
	).toBeVisible();
	await expect(
		(
			await commerceAdminChannelDetailsPage.getRowByTextFromSidePanelTable(
				tableName,
				name
			)
		).locator('.cell-rate')
	).toHaveText(taxAmount);
}

test('LPD-31663 Activate Fixed Tax Engine and Add Tax Rate', async ({
	apiHelpers,
	commerceAdminChannelDetailsPage,
	commerceAdminChannelsPage,
	newTaxCategoryPage,
	page,
	taxCategoriesPage,
}) => {
	const taxCategories = [
		{
			description: 'Test Description 1',
			name: 'Test 1',
			referenceCode: 'Test Reference 1',
		},
		{
			description: 'Test Description 2',
			name: 'Test 2',
			referenceCode: 'Test Reference 1',
		},
	];

	try {
		await taxCategoriesPage.goto();

		for (const taxCategory of taxCategories) {
			await taxCategoriesPage.newButton.click();
			await newTaxCategoryPage.externalReferenceCodeInput.fill(
				taxCategory.referenceCode
			);
			await newTaxCategoryPage.nameInput.fill(taxCategory.name);
			await newTaxCategoryPage.descriptionInput.fill(
				taxCategory.description
			);
			await newTaxCategoryPage.saveButton.click();

			if (taxCategory.name === 'Test 1') {
				await waitForAlert(page);

				await expect(taxCategoriesPage.newButton).toBeVisible();
			}
			else {
				await expect(
					await newTaxCategoryPage.errorMessage(
						'Error:Please enter a unique external reference code.'
					)
				).toBeVisible();

				await newTaxCategoryPage.externalReferenceCodeInput.fill(
					'Test Reference 2'
				);
				await newTaxCategoryPage.saveButton.click();

				await waitForAlert(page);
			}
		}

		const site =
			await apiHelpers.headlessAdminUser.getSiteByFriendlyUrlPath(
				'guest'
			);

		const channel =
			await apiHelpers.headlessCommerceAdminChannel.postChannel({
				siteGroupId: site.id,
			});

		await commerceAdminChannelsPage.goto();

		await (
			await commerceAdminChannelsPage.channelsTableRowLink(channel.name)
		).click();

		await commerceAdminChannelDetailsPage.addFixedTaxRate(
			'7.5',
			taxCategories[0].name
		);
		await verifyFixedTaxRate(
			commerceAdminChannelDetailsPage,
			taxCategories[0].name,
			'$ 7.50'
		);
		await commerceAdminChannelDetailsPage.editFixedTaxRate(
			'10.0',
			taxCategories[0].name
		);
		await verifyFixedTaxRate(
			commerceAdminChannelDetailsPage,
			taxCategories[0].name,
			'$ 10.00'
		);
	}
	finally {
		page.on('dialog', (dialog) => {
			dialog.accept();
		});

		await taxCategoriesPage.goto();

		for (const taxCategory of taxCategories) {
			await (
				await taxCategoriesPage.taxCategoriesTableRowActions(
					taxCategory.name
				)
			).click();
			await taxCategoriesPage.deleteMenuItem.click();

			await waitForAlert(page);
		}
	}
});
