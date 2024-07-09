/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {usersAndOrganizationsPagesTest} from '../../fixtures/usersAndOrganizationsPagesTest';
import {getRandomInt} from '../../utils/getRandomInt';
import {waitForSuccessAlert} from '../../utils/waitForSuccessAlert';

export const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	loginTest(),
	usersAndOrganizationsPagesTest
);

test('LPS-204541 check export/import menu visibility', async ({
	usersAndOrganizationsPage,
}) => {
	await usersAndOrganizationsPage.goToUsers();
	await usersAndOrganizationsPage.openOptionsMenu();
	await expect(
		usersAndOrganizationsPage.exportImportOptionsMenuItem
	).toHaveCount(0);
	await expect(
		usersAndOrganizationsPage.exportUsersOptionsMenuItem
	).toBeVisible();
	await expect(
		usersAndOrganizationsPage.manageCustomFieldsOptionsMenuItem
	).toBeVisible();

	await usersAndOrganizationsPage.goToOrganizations();
	await usersAndOrganizationsPage.openOptionsMenu();
	await expect(
		usersAndOrganizationsPage.exportImportOptionsMenuItem
	).toBeVisible();
	await expect(
		usersAndOrganizationsPage.exportUsersOptionsMenuItem
	).toBeVisible();
	await expect(
		usersAndOrganizationsPage.manageCustomFieldsOptionsMenuItem
	).toHaveCount(0);
});

test('LPD-15224 check escape of memberships account name', async ({
	apiHelpers,
	editUserPage,
	page,
	usersAndOrganizationsPage,
}) => {
	await page.goto('/');

	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: '<img src="x" onError="alert(document.location)">',
	});

	await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
		account.id,
		['test@liferay.com']
	);

	try {
		await usersAndOrganizationsPage.goToUsers();

		await (
			await usersAndOrganizationsPage.usersTableRowLink('test')
		).click();
		await editUserPage.membershipsLink.click();

		await expect(
			(
				await editUserPage.membershipsAccountsTableRow(
					0,
					account.name,
					true
				)
			).row
		).toBeVisible();
	}
	finally {
		await apiHelpers.headlessAdminUser.deleteAccount(account.id);
	}
});

test('LPD-15423 check WebDAV password is generated', async ({
	editUserPage,
	page,
	usersAndOrganizationsPage,
}) => {
	await page.goto('/');

	await usersAndOrganizationsPage.goToUsers();
	await (await usersAndOrganizationsPage.usersTableRowLink('test')).click();

	await editUserPage.passwordLink.click();
	await editUserPage.generateWebDAVPasswordButton.click();

	await expect(editUserPage.webDAVPasswordLabel).toBeVisible();
});

test('LPD-28908 update user information', async ({
	apiHelpers,
	editUserPage,
	page,
	usersAndOrganizationsPage,
}) => {
	const user = await apiHelpers.headlessAdminUser.postUserAccount();

	await page.goto('/');

	await usersAndOrganizationsPage.goToUsers();
	await (
		await usersAndOrganizationsPage.usersTableRowLink(user.alternateName)
	).click();

	await editUserPage.screenNameInput.fill('User' + getRandomInt());
	await editUserPage.emailAddressInput.fill(
		'User' + getRandomInt() + '@liferay.com'
	);
	await editUserPage.saveButton.click();
	await editUserPage.yourPasswordInput.fill('test');
	await editUserPage.confirmButton.click();

	await expect(
		page
			.getByText('Success:Your request completed successfully.')
			.or(
				page.getByText(
					'Success:Your email verification code has been sent'
				)
			)
	).toBeVisible();
});

test('LPD-30589 Add Organization Team', async ({
	apiHelpers,
	editOrganizationPage,
	page,
	siteConfigurationDetailsPage,
	siteSettingsPage,
	teamsPage,
	usersAndOrganizationsPage,
}) => {
	const organization = await apiHelpers.headlessAdminUser.postOrganization();

	await apiHelpers.headlessAdminUser.assignUserToOrganizationByEmailAddress(
		organization.id,
		'test@liferay.com'
	);

	apiHelpers.data.push({
		id: `${organization.id}_test@liferay.com`,
		type: 'organizationUserAccountAssociation',
	});

	await usersAndOrganizationsPage.goToOrganizations();

	await (
		await usersAndOrganizationsPage.organizationActionsMenu(
			organization.name
		)
	).click();
	await editOrganizationPage.organizationEditMenuItem.click();
	await editOrganizationPage.organizationSiteLink.click();
	await editOrganizationPage.createSiteToggle.check();
	await editOrganizationPage.organizationSiteSaveButton.click();

	await siteSettingsPage.goToSiteSetting(
		'Site Configuration',
		'Details',
		'/' + organization.name
	);

	await siteConfigurationDetailsPage.allowManualMembershipManagementToggle.check();
	await siteConfigurationDetailsPage.saveButton.click();

	await waitForSuccessAlert(page);

	await teamsPage.goTo('/' + organization.name);

	const newTeamName = 'Team' + getRandomInt();

	await teamsPage.newTeamButton.click();
	await teamsPage.nameInput.fill(newTeamName);
	await teamsPage.saveButton.click();

	await waitForSuccessAlert(page);

	await expect(
		(await teamsPage.teamsTableRow(1, newTeamName, true)).row
	).toBeVisible();
});
