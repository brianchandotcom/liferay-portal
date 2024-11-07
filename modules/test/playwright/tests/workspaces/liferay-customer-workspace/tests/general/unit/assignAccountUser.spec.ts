/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../../../../fixtures/apiHelpersTest';
import {getRandomInt} from '../../../../../../utils/getRandomInt';
import {customerApiHelpersTest} from '../../../fixtures/customerApiHelpersTest';
import {customerPagesTest} from '../../../fixtures/customerPagesTest';
import {
	customerPerformLogin,
	customerPerformLogout,
} from '../../../utils/customerLogin';
import {mockOktaApiSession} from '../../../utils/oktaUtil';
import {mockProvisioningApiAssignUser} from '../../../utils/provisioningUtil';

export const test = mergeTests(
	apiHelpersTest,
	customerApiHelpersTest,
	customerPagesTest
);

const accountExternalReferenceCode = 'ERC-001';
let userEmailAddress: string;

test.afterEach(async ({apiHelpers, page}) => {
	const account =
		await apiHelpers.headlessAdminUser.getAccountByExternalReferenceCode(
			accountExternalReferenceCode
		);

	await apiHelpers.headlessAdminUser.deleteUserFromAccountByEmailAddress(
		account.id,
		'test@liferay.com'
	);

	const userAccount =
		await apiHelpers.headlessAdminUser.getUserAccountByEmailAddress(
			userEmailAddress
		);

	await apiHelpers.headlessAdminUser.deleteUserAccount(userAccount.id);

	await customerPerformLogout(page);
});

test.beforeEach(async ({apiHelpers, page}) => {
	await customerPerformLogin(page, 'test@liferay.com');

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

	const accountAdministratorRole = await rolesResponse?.items?.filter(
		(role) => {
			return role.name === 'Account Administrator';
		}
	);

	await apiHelpers.headlessAdminUser.assignAccountRoles(
		accountExternalReferenceCode,
		accountAdministratorRole[0].id,
		'test@liferay.com'
	);
});

test('Account admin can assign new user to account', async ({
	customerApiHelpers,
	page,
	projectOverviewPage,
	projectTeamMembersPage,
}) => {
	await projectOverviewPage.goto(accountExternalReferenceCode);

	const accountFlag = await customerApiHelpers.getAccountFlag(
		accountExternalReferenceCode
	);

	if (accountFlag === undefined) {
		await expect(
			page.getByRole('button', {name: 'Start Project Setup'})
		).toBeVisible();
	}

	await projectTeamMembersPage.goto(accountExternalReferenceCode);

	await projectTeamMembersPage.inviteButton.click();

	await projectTeamMembersPage.firstNameField.fill('testfirst');

	await projectTeamMembersPage.lastNameField.fill('testlast');

	userEmailAddress = getRandomInt().toString() + '@liferay.com';

	await projectTeamMembersPage.emailField.fill(userEmailAddress);

	await projectTeamMembersPage.roleSelect.click({force: true});

	await projectTeamMembersPage.userRoleOption.click({force: true});

	await projectTeamMembersPage.applyButton.click({force: true});

	await projectTeamMembersPage.sendInvitationsButton.click({force: true});

	await projectTeamMembersPage.goto(accountExternalReferenceCode);

	await expect(page.getByText(userEmailAddress)).toBeVisible();
});
