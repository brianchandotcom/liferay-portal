/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {commercePagesTest} from '../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';

const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	commercePagesTest,
	dataApiHelpersTest,
	loginTest()
);

test('LPD-31378 Width of columns with very small header text is increased', async ({
	apiHelpers,
	applicationsMenuPage,
	page,
}) => {
	await test.step('Create commerce site', async () => {
		const site = await apiHelpers.headlessSite.createSite({
			name: 'Minium',
			templateKey: 'minium-initializer',
			templateType: 'site-initializer',
		});

		apiHelpers.data.push({id: site.id, type: 'site'});

		await applicationsMenuPage.goToSite('Minium');
	});

	await test.step('Create account', async () => {
		const accountButton = page.getByRole('button', {
			name: 'Select Account & Order',
		});

		await accountButton.waitFor({state: 'visible'});
		await accountButton.click();

		const newAccountButton = page.getByRole('button', {
			name: 'Create New Account',
		});

		await newAccountButton.waitFor({state: 'visible'});
		await newAccountButton.click();

		const accountNameField = page.locator('input[name="accountName"]');

		await accountNameField.waitFor({state: 'visible'});
		await accountNameField.fill('test');

		await page.getByRole('button', {exact: true, name: 'Create'}).click();
	});

	await test.step('Add transmission to shopping cart', async () => {
		const transmissionButton = page
			.locator('#wwxc_column_2d_2_1_add_to_cart')
			.getByRole('button', {name: 'Add to Cart'});

		await transmissionButton.waitFor({state: 'visible'});
		await transmissionButton.click();

		const cartButton = page.locator('[data-qa-id="miniCartButton"]');

		await cartButton.waitFor({state: 'visible'});
		await cartButton.click();
	});

	await test.step('Check SKU width length', async () => {
		const viewDetailsButton = page.getByRole('button', {
			name: 'View Details',
		});

		await viewDetailsButton.waitFor({state: 'visible'});
		await viewDetailsButton.click();

		const skuTableHeader = page
			.getByTestId('visualization-mode-table')
			.getByText('SKU');

		await skuTableHeader.waitFor({state: 'visible'});

		const skuTableHeaderBoundingBox = await skuTableHeader.boundingBox();

		const skuTableHeaderWidth = skuTableHeaderBoundingBox.width;

		expect(skuTableHeaderWidth).toEqual(140);
	});
});
