/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {expect, mergeTests} from '@playwright/test';

import {accountsPagesTest} from '../../fixtures/accountsPagesTest';
import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';

export const test = mergeTests(
	accountsPagesTest,
	apiHelpersTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-10855': true,
	}),
	loginTest()
);

test('LPD-18485 Update account contact information fields', async ({
	accountsPage,
	apiHelpers,
	editAccountContactInformationPage,
	editAccountContactPage,
	editAccountPage,
	page,
}) => {
	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: 'test',
		type: 'business',
	});

	apiHelpers.data.push({id: account.id, type: 'account'});

	await accountsPage.goto();

	await (await accountsPage.accountsTableRowLink(account.name)).click();
	await editAccountPage.contactLink.click();
	await editAccountContactPage.contactInformationLink.click();
	await editAccountContactInformationPage.updateContactInformation(
		'facebookInput',
		'jabberInput',
		'skypeInput',
		'smsInput',
		'twitterInput'
	);

	await expect(
		page.getByText('Success:Your request completed successfully.')
	).toBeVisible();

	await page.reload();

	await expect(editAccountContactInformationPage.facebookInput).toHaveValue(
		'facebookInput'
	);
});

test('LPD-18484 Add account contact address', async ({
	accountContactAddressPage,
	accountsPage,
	apiHelpers,
	editAccountContactAddressPage,
	editAccountPage,
	page,
}) => {
	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: 'test',
		type: 'business',
	});

	apiHelpers.data.push({id: account.id, type: 'account'});

	await accountsPage.goto();

	await (await accountsPage.accountsTableRowLink(account.name)).click();
	await editAccountPage.contactLink.click();
	await accountContactAddressPage.addAddressesButton.click();
	await editAccountContactAddressPage.updateAddress('address1', 'city');

	await expect(
		page.getByText('Success:Your request completed successfully.')
	).toBeVisible();

	await expect(
		await editAccountContactAddressPage.addressDisplay('address1city')
	).toBeVisible();
});

test('LPD-18482 Add account phone', async ({
	accountsPage,
	apiHelpers,
	editAccountContactInformationPage,
	editAccountContactPage,
	editAccountPage,
	editAccountPhonePage,
	page,
}) => {
	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: 'test',
		type: 'business',
	});

	apiHelpers.data.push({id: account.id, type: 'account'});

	await accountsPage.goto();

	await (await accountsPage.accountsTableRowLink(account.name)).click();
	await editAccountPage.contactLink.click();
	await editAccountContactPage.contactInformationLink.click();
	await editAccountContactInformationPage.addPhoneNumbersButton.click();
	await editAccountPhonePage.updatePhoneNumber('111-111-1111');

	await expect(
		page.getByText('Success:Your request completed successfully.')
	).toBeVisible();

	await expect(page.getByRole('cell', {name: '111-111-1111'})).toBeVisible();
});

test('LPD-18483 Add account email address', async ({
	accountsPage,
	apiHelpers,
	editAccountContactInformationPage,
	editAccountContactPage,
	editAccountEmailAddressPage,
	editAccountPage,
	page,
}) => {
	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: 'test',
		type: 'business',
	});

	apiHelpers.data.push({id: account.id, type: 'account'});

	await accountsPage.goto();

	await (await accountsPage.accountsTableRowLink(account.name)).click();
	await editAccountPage.contactLink.click();
	await editAccountContactPage.contactInformationLink.click();
	await editAccountContactInformationPage.addEmailAddressesButton.click();
	await editAccountEmailAddressPage.updateEmailAddress(
		'emailAddress@liferay.com'
	);

	await expect(
		page.getByText('Success:Your request completed successfully.')
	).toBeVisible();

	await expect(
		page.getByRole('cell', {name: 'emailAddress@liferay.com'})
	).toBeVisible();
});

test('LPD-18484 Add account website', async ({
	accountsPage,
	apiHelpers,
	editAccountContactInformationPage,
	editAccountContactPage,
	editAccountPage,
	editAccountWebsitePage,
	page,
}) => {
	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: 'test',
		type: 'business',
	});

	apiHelpers.data.push({id: account.id, type: 'account'});

	await accountsPage.goto();

	await (await accountsPage.accountsTableRowLink(account.name)).click();
	await editAccountPage.contactLink.click();
	await editAccountContactPage.contactInformationLink.click();
	await editAccountContactInformationPage.addWebsitesButton.click();
	await editAccountWebsitePage.updateWebsite('https://www.website.com');

	await expect(
		page.getByText('Success:Your request completed successfully.')
	).toBeVisible();

	await expect(
		page.getByRole('cell', {name: 'https://www.website.com'})
	).toBeVisible();
});
