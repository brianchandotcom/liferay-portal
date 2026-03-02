/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {accountsPagesTest} from '../../../../fixtures/accountsPagesTest';
import {commercePagesTest} from '../../../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {globalMenuPagesTest} from '../../../../fixtures/globalMenuPagesTest';
import {loginTest} from '../../../../fixtures/loginTest';
import {usersAndOrganizationsPagesTest} from '../../../../fixtures/usersAndOrganizationsPagesTest';
import {getRandomInt} from '../../../../utils/getRandomInt';
import getRandomString from '../../../../utils/getRandomString';
import {waitForAlert} from '../../../../utils/waitForAlert';
import {miniumSetUp} from '../../utils/commerce';

export const test = mergeTests(
	accountsPagesTest,
	commercePagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-36105': {enabled: true},
	}),
	globalMenuPagesTest,
	loginTest(),
	usersAndOrganizationsPagesTest
);

test('COMMERCE-5839 As a system admin i want to be able to create / update and delete a new currency', async ({
	apiHelpers,
	commerceAdminCurrenciesPage,
	commerceAdminCurrencyDetailsPage,
	page,
}) => {
	await page.goto('/');
	await commerceAdminCurrenciesPage.goto();

	const currencyName = 'TC' + getRandomInt();

	try {
		await commerceAdminCurrenciesPage.addCurrencyAddButton.click();

		await commerceAdminCurrencyDetailsPage.nameInput.fill(currencyName);
		await commerceAdminCurrencyDetailsPage.codeInput.fill(currencyName);
		await commerceAdminCurrencyDetailsPage.saveButton.click();

		await waitForAlert(page);

		await expect(
			commerceAdminCurrenciesPage.lastRowCurrencyCellName(currencyName)
		).toBeVisible();

		await commerceAdminCurrenciesPage
			.currencyNameLink(currencyName)
			.click();

		await expect(commerceAdminCurrencyDetailsPage.symbol).toBeVisible();

		await commerceAdminCurrencyDetailsPage.symbol.fill('&&&');
		await commerceAdminCurrencyDetailsPage.priority.fill('99');
		await commerceAdminCurrencyDetailsPage.saveButton.click();

		await commerceAdminCurrenciesPage
			.currencyNameLink(currencyName)
			.click();

		await expect(
			commerceAdminCurrencyDetailsPage.codeInput
		).not.toBeEditable();

		await expect(commerceAdminCurrencyDetailsPage.symbol).toBeVisible();

		await expect(commerceAdminCurrencyDetailsPage.symbol).toHaveValue(
			'&&&'
		);
		await expect(commerceAdminCurrencyDetailsPage.priority).toHaveValue(
			'99.0'
		);

		await commerceAdminCurrencyDetailsPage.cancelButton.click();

		await commerceAdminCurrenciesPage.priorityButton.click();

		await expect(
			commerceAdminCurrenciesPage.firstRowCurrencyCellName('US Dollar')
		).toBeVisible();

		await commerceAdminCurrenciesPage.priorityButton.click();

		await expect(
			commerceAdminCurrenciesPage.firstRowCurrencyCellName(currencyName)
		).toBeVisible();

		await commerceAdminCurrenciesPage.addDataSetFilter(
			'Active',
			'Yes',
			false
		);

		await expect(
			commerceAdminCurrenciesPage.currencyNameLink(currencyName)
		).toHaveCount(0);

		await commerceAdminCurrenciesPage.resetFiltersButton.click();
		await commerceAdminCurrenciesPage.addDataSetFilter(
			'Active',
			'No',
			false,
			true
		);

		await expect(
			commerceAdminCurrenciesPage.currencyNameLink(currencyName)
		).toBeVisible();

		await commerceAdminCurrenciesPage.resetFiltersButton.click();
		await commerceAdminCurrenciesPage.search.click();
		await commerceAdminCurrenciesPage.search.fill(getRandomString());
		await commerceAdminCurrenciesPage.searchButton.click();

		await expect(
			commerceAdminCurrenciesPage.noResultsFoundText
		).toBeVisible();

		await commerceAdminCurrenciesPage.search.click();
		await commerceAdminCurrenciesPage.search.fill(currencyName);
		await commerceAdminCurrenciesPage.searchButton.click();

		await expect(
			commerceAdminCurrenciesPage.currencyNameLink(currencyName)
		).toBeVisible();

		await commerceAdminCurrenciesPage.actionsButton.click();
		await commerceAdminCurrenciesPage.activeToggleMenuItem.click();

		await waitForAlert(page);

		await commerceAdminCurrenciesPage.search.click();
		await commerceAdminCurrenciesPage.search.fill(currencyName);
		await commerceAdminCurrenciesPage.searchButton.click();
		await commerceAdminCurrenciesPage.actionsButton.click();
		await commerceAdminCurrenciesPage.primaryMenuItem.click();

		await waitForAlert(page);

		await commerceAdminCurrenciesPage
			.currencyNameLink(currencyName)
			.click();

		await expect(
			commerceAdminCurrencyDetailsPage.activeToggle
		).toBeChecked();
		await expect(
			commerceAdminCurrencyDetailsPage.primaryToggle
		).toBeChecked();

		await commerceAdminCurrencyDetailsPage.saveButton.click();

		await waitForAlert(page);

		await commerceAdminCurrenciesPage.currencyNameLink('US Dollar').click();

		await expect(
			commerceAdminCurrencyDetailsPage.primaryToggle
		).not.toBeChecked();

		await commerceAdminCurrencyDetailsPage.primaryToggle.setChecked(true);
		await commerceAdminCurrencyDetailsPage.saveButton.click();

		await waitForAlert(page);

		await commerceAdminCurrenciesPage.search.click();
		await commerceAdminCurrenciesPage.search.fill(currencyName);
		await commerceAdminCurrenciesPage.searchButton.click();
		await commerceAdminCurrenciesPage.actionsButton.click();

		let dialogMessage = '';

		page.on('dialog', (dialog) => {
			dialogMessage = dialog.message();
			dialog.accept();
		});

		await commerceAdminCurrenciesPage.deleteMenuItem.click();

		expect(dialogMessage).toEqual(
			'Are you sure you want to delete this entry?'
		);

		await waitForAlert(page);

		await expect(
			commerceAdminCurrenciesPage.currencyNameLink(currencyName)
		).not.toBeVisible();
	}
	finally {
		const currencies =
			await apiHelpers.headlessCommerceAdminCatalog.getCurrenciesPage(
				currencyName
			);

		if (currencies && currencies.items) {
			for (const currency of currencies.items) {
				apiHelpers.data.push({id: currency.id, type: 'currency'});
			}
		}
	}
});

test('COMMERCE-9936 A disabled default currency should not be usable', async ({
	accountsPage,
	apiHelpers,
	commerceChannelDefaultsPage,
	editAccountPage,
	globalMenuPage,
	page,
}) => {
	test.setTimeout(180000);

	const currencies =
		await apiHelpers.headlessCommerceAdminCatalog.getCurrenciesPage('EUR');

	try {
		const {site} = await miniumSetUp(apiHelpers);

		const account = await apiHelpers.headlessAdminUser.postAccount({
			name: getRandomString(),
			type: 'business',
		});

		await accountsPage.goto();
		await accountsPage.accountNameLink(account.name).click();
		await editAccountPage.channelDefaultsLink.click();

		await commerceChannelDefaultsPage.defaultCommerceCurrenciesButton.click();
		await commerceChannelDefaultsPage.editFrameCurrencySelect.selectOption(
			'Euro'
		);

		await commerceChannelDefaultsPage.editFrameSaveButton.click();

		await apiHelpers.headlessCommerceAdminCatalog.patchCurrency(
			currencies.items[0].id,
			{
				active: false,
			}
		);

		await globalMenuPage.goToSite(site.name);

		await expect(page.getByText('$ 24.00')).toBeVisible();
	}
	finally {
		await apiHelpers.headlessCommerceAdminCatalog.patchCurrency(
			currencies.items[0].id,
			{
				active: true,
			}
		);
	}
});
