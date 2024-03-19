/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {expect, mergeTests} from '@playwright/test';

import {accountsPagesTest} from '../../fixtures/accountsPagesTest';
import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';

export const test = mergeTests(accountsPagesTest, apiHelpersTest, loginTest());

test('LPD-18485 Update account contact information fields', async ({
	accountsPage,
	apiHelpers,
	editAccountContactInformationPage,
	editAccountContactPage,
	editAccountPage,
	page,
}) => {
	await apiHelpers.featureFlag.updateFeatureFlag('LPD-10855', true);

	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: 'test',
		type: 'business',
	});

	await accountsPage.goto();

	try {
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

		await expect(
			editAccountContactInformationPage.facebookInput
		).toHaveValue('facebookInput');
	}
	finally {
		await apiHelpers.featureFlag.updateFeatureFlag('LPD-10855', false);
		await apiHelpers.headlessAdminUser.deleteAccount(account.id);
	}
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
	await apiHelpers.featureFlag.updateFeatureFlag('LPD-10855', true);

	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: 'test',
		type: 'business',
	});

	await accountsPage.goto();

	try {
		await (await accountsPage.accountsTableRowLink(account.name)).click();
		await editAccountPage.contactLink.click();
		await editAccountContactPage.contactInformationLink.click();
		await editAccountContactInformationPage.addPhoneNumbersButton.click();
		await editAccountPhonePage.updatePhoneNumber('111-111-1111');

		await expect(
			page.getByText('Success:Your request completed successfully.')
		).toBeVisible();

		await expect(
			page.getByRole('cell', {name: '111-111-1111'})
		).toBeVisible();
	}
	finally {
		await apiHelpers.featureFlag.updateFeatureFlag('LPD-10855', false);
		await apiHelpers.headlessAdminUser.deleteAccount(account.id);
	}
});
