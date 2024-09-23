/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../../../../fixtures/apiHelpersTest';
import {loginTest} from '../../../../../../fixtures/loginTest';
import getRandomString from '../../../../../../utils/getRandomString';
import performLogin from '../../../../../../utils/performLogin';
import {customerApiHelpersTest} from '../../../fixtures/customerApiHelpersTest';
import {customerPagesTest} from '../../../fixtures/customerPagesTest';
import {
	customerPerformLogout,
	customerPerformUserSwitch,
} from '../../../utils/customerLogin';
import {mockOktaApiSession} from '../../../utils/oktaUtil';
import {mockProvisioningApiAssignUser} from '../../../utils/provisioningUtil';

export const test = mergeTests(
	apiHelpersTest,
	customerApiHelpersTest,
	customerPagesTest,
	loginTest()
);

const accountExternalReferenceCode = 'ERC-001';
let userEmailAddress: string;

test.afterEach(async ({apiHelpers}) => {
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
});

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

test.describe('Project User Permission', () => {
	test('User can only see things related to User role', async ({
		apiHelpers,
		customerApiHelpers,
		homePage,
		page,
		projectAttachmentsPage,
		projectOverviewPage,
		projectPaaSPage,
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

		userEmailAddress = getRandomString() + '@liferay.com';

		await projectTeamMembersPage.emailField.fill(userEmailAddress);

		await projectTeamMembersPage.roleSelect.click({force: true});

		await projectTeamMembersPage.userRoleOption.click();

		await projectTeamMembersPage.applyButton.click();

		await projectTeamMembersPage.sendInvitationsButton.click();

		await projectTeamMembersPage.goto(accountExternalReferenceCode);

		await expect(page.getByText(userEmailAddress)).toBeVisible();

		const userAccount =
			await apiHelpers.headlessAdminUser.getUserAccountByEmailAddress(
				userEmailAddress
			);

		await apiHelpers.headlessAdminUser.patchUserAccount(userAccount, {
			currentPassword: 'test',
			password: 'test',
		});

		await customerPerformUserSwitch(page, userEmailAddress);

		await expect(
			page.getByRole('heading', {name: 'Test Account Liferay PaaS'})
		).toBeVisible();

		expect((await homePage.projectCard.all()).length).toBe(1);

		await projectOverviewPage.goto(accountExternalReferenceCode);

		await expect(projectOverviewPage.heading).toBeVisible();

		await expect(projectOverviewPage.paasHeading).toBeVisible();

		await expect(projectOverviewPage.subscriptionCard).toBeVisible();

		await projectPaaSPage.goto(accountExternalReferenceCode);

		await expect(projectPaaSPage.heading).toBeVisible();

		await expect(projectPaaSPage.projectName).toBeVisible();

		if (projectPaaSPage.projectNotActivatedTag) {
			await expect(
				projectPaaSPage.finishActivationButton
			).not.toBeVisible();
		}

		await expect(
			page.getByRole('button', {name: 'Product Activation'})
		).toBeDisabled();

		await projectAttachmentsPage.goto(accountExternalReferenceCode);

		await expect(projectAttachmentsPage.heading).toBeVisible();

		await expect(
			projectAttachmentsPage.attachmentIconContainer
		).toBeVisible();

		await projectTeamMembersPage.goto(accountExternalReferenceCode);

		await expect(page.getByText(userEmailAddress)).toBeVisible();

		await expect(projectTeamMembersPage.inviteButton).not.toBeVisible();

		await expect(
			projectTeamMembersPage.userActionColumnHeader
		).not.toBeVisible();

		await customerPerformLogout(page);

		await performLogin(page, 'test');
	});
});
