/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../../../../fixtures/apiHelpersTest';
import {loginTest} from '../../../../../../fixtures/loginTest';
import getRandomString from '../../../../../../utils/getRandomString';
import {customerApiHelpersTest} from '../../../fixtures/customerApiHelpersTest';
import {CUSTOMER_SITE_FRIENLY_URL_PATH} from '../../../utils/constants';
import {mockOktaApiSession} from '../../../utils/oktaUtil';
import {mockProvisioningApiAssignUser} from '../../../utils/provisioningUtil';

export const test = mergeTests(
	apiHelpersTest,
	customerApiHelpersTest,
	loginTest()
);

const accountExternalReferenceCode = 'ERC-001';
let userEmailAddress: string;

test.beforeEach(async ({apiHelpers, page}) => {
	await mockOktaApiSession(page);
	await mockProvisioningApiAssignUser(page);

	const account =
		await apiHelpers.headlessAdminUser.getAccountByExternalReferenceCode(
			accountExternalReferenceCode
		);

	await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
		account.id,
		['test@liferay.com']
	);

	const rolesResponse = await apiHelpers.headlessAdminUser.getAccountRoles(
		account.id
	);

	const accountAdministratorRole = rolesResponse?.items?.filter((role) => {
		return role.name === 'Account Administrator';
	});

	await apiHelpers.headlessAdminUser.assignAccountRoles(
		accountExternalReferenceCode,
		accountAdministratorRole[0].id,
		'test@liferay.com'
	);
});

test('Account admin can assign new user to account', async ({
	customerApiHelpers,
	page,
}) => {
	await page.goto(
		CUSTOMER_SITE_FRIENLY_URL_PATH +
			'/project/#/' +
			accountExternalReferenceCode
	);

	const accountFlag = await customerApiHelpers.getAccountFlag(
		accountExternalReferenceCode
	);

	if (accountFlag === undefined) {
		await expect(
			page.getByRole('button', {name: 'Start Project Setup'})
		).toBeVisible();
	}

	await page.goto(
		CUSTOMER_SITE_FRIENLY_URL_PATH +
			'/project/#/' +
			accountExternalReferenceCode +
			'/team-members'
	);

	await page.getByRole('button', {name: 'invite'}).click();

	await page.getByLabel('First Name').fill('testfirst');
	await page.getByLabel('Last Name').fill('testlast');

	userEmailAddress = getRandomString() + '@liferay.com';

	await page.getByLabel('Email').fill(userEmailAddress);

	await page
		.locator('div.role-selector-container')
		.getByRole('button')
		.click({force: true});

	await page
		.locator('div.dropdown-menu')
		.getByText('User', {exact: true})
		.click();

	await page.getByRole('button', {name: 'Apply'}).click();

	await page.getByRole('button', {name: 'Send Invitations'}).click();

	await page.goto(
		CUSTOMER_SITE_FRIENLY_URL_PATH +
			'/project/#/' +
			accountExternalReferenceCode +
			'/team-members'
	);

	await expect(page.getByText(userEmailAddress)).toBeVisible();
});

test.afterEach(async ({apiHelpers}) => {
	const userAccount =
		await apiHelpers.headlessAdminUser.getUserAccountByEmailAddress(
			userEmailAddress
		);

	await apiHelpers.headlessAdminUser.deleteUserAccount(userAccount.id);

	const account =
		await apiHelpers.headlessAdminUser.getAccountByExternalReferenceCode(
			accountExternalReferenceCode
		);

	await apiHelpers.headlessAdminUser.deleteUserFromAccountByEmailAddress(
		account.id,
		'test@liferay.com'
	);
});
