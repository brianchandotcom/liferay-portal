/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {accountsPagesTest} from '../../fixtures/accountsPagesTest';
import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {serverAdministrationPageTest} from '../../fixtures/serverAdministrationPageTest';
import {usersAndOrganizationsPagesTest} from '../../fixtures/usersAndOrganizationsPagesTest';
import getRandomString from '../../utils/getRandomString';
import performLogin, {performLogout, userData} from '../../utils/performLogin';
import {waitForAlert} from '../../utils/waitForAlert';

export const test = mergeTests(
	accountsPagesTest,
	apiHelpersTest,
	applicationsMenuPageTest,
	dataApiHelpersTest,
	loginTest(),
	usersAndOrganizationsPagesTest,
	serverAdministrationPageTest
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

test('LPD-28161 Can view role and organization name escaped', async ({
	accountRolesPage,
	accountUsersPage,
	accountsPage,
	apiHelpers,
	editAccountPage,
}) => {
	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: getRandomString(),
		type: 'business',
	});

	apiHelpers.data.push({id: account.id, type: 'account'});

	const roleName = 'My title<script>confirm("compromised")</script>';

	const accountRole =
		await apiHelpers.headlessAdminUser.postAccountAccountRoles(account.id, {
			name: roleName,
		});

	apiHelpers.data.push({id: accountRole.id, type: 'role'});

	const user = await apiHelpers.headlessAdminUser.postUserAccount();

	await apiHelpers.headlessAdminUser.postAccountUserAccountByEmailAddress(
		account.id,
		[accountRole.id],
		[user.emailAddress]
	);

	const organizationName = 'My org1<script>confirm("compromised")</script>';

	const organization = await apiHelpers.headlessAdminUser.postOrganization({
		name: organizationName,
	});

	await apiHelpers.headlessAdminUser.postOrganizationAccounts(
		Number(organization.id),
		[account.id]
	);

	await accountsPage.goto();

	await expect(
		await accountsPage.organizationName(organizationName)
	).toBeVisible();

	await (await accountsPage.accountsTableRowLink(account.name)).click();
	await editAccountPage.rolesLink.click();

	await expect(await accountRolesPage.roleName(roleName)).toBeVisible();

	await editAccountPage.usersLink.click();

	await expect(await accountUsersPage.roleName(roleName)).toBeVisible();
});

test('LPD-32045 All account entry can be seen by admin user', async ({
	accountsPage,
	apiHelpers,
	page,
}) => {
	const account1 = await apiHelpers.headlessAdminUser.postAccount({
		name: getRandomString(),
		type: 'business',
	});

	apiHelpers.data.push({id: account1.id, type: 'account'});

	const account2 = await apiHelpers.headlessAdminUser.postAccount({
		name: getRandomString(),
		type: 'business',
	});

	apiHelpers.data.push({id: account2.id, type: 'account'});

	const account3 = await apiHelpers.headlessAdminUser.postAccount({
		name: getRandomString(),
		type: 'business',
	});

	apiHelpers.data.push({id: account3.id, type: 'account'});

	const userAccount = await apiHelpers.headlessAdminUser.postUserAccount();

	userData[userAccount.alternateName] = {
		name: userAccount.givenName,
		password: 'test',
		surname: userAccount.familyName,
	};

	const role =
		await apiHelpers.headlessAdminUser.getRoleByName('Administrator');

	await apiHelpers.headlessAdminUser.postRoleByExternalReferenceCodeUserAccountAssociation(
		role.externalReferenceCode,
		userAccount.id
	);

	await performLogout(page);
	await performLogin(page, userAccount.alternateName);

	try {
		await accountsPage.goto();

		await expect(
			await accountsPage.accountsTableRowLink(account1.name)
		).toHaveCount(1);
		await expect(
			await accountsPage.accountsTableRowLink(account2.name)
		).toHaveCount(1);
		await expect(
			await accountsPage.accountsTableRowLink(account3.name)
		).toHaveCount(1);
	}
	finally {
		await performLogout(page);
		await performLogin(page, 'test');
	}
});

test('LPD-33636 Email address is not deleted by saving in the UI', async ({
	accountsPage,
	apiHelpers,
	applicationsMenuPage,
	editAccountPage,
	page,
	serverAdministrationPage,
}) => {
	const account = await apiHelpers.headlessAdminUser.postAccount();

	apiHelpers.data.push({id: account.id, type: 'account'});

	await applicationsMenuPage.goToServerAdministration();

	const emailAddress = getRandomString() + '@liferay.com';

	const script = `
		import com.liferay.account.model.*;
		import com.liferay.account.service.*;
		AccountEntry account = AccountEntryLocalServiceUtil.fetchAccountEntry(${account.id});
		account.setEmailAddress("${emailAddress}");
		AccountEntryLocalServiceUtil.updateAccountEntry(account);
    `;

	await serverAdministrationPage.executeScript(script);

	await accountsPage.goto();
	await (await accountsPage.accountsTableRowLink(account.name)).click();
	await editAccountPage.saveButton.click();
	await waitForAlert(page);

	await applicationsMenuPage.goToServerAdministration();

	const fetchScript = `
		import com.liferay.account.model.*; 
		import com.liferay.account.service.*;
		AccountEntry account = AccountEntryLocalServiceUtil.fetchAccountEntry(${account.id});
		out.println(account);
	`;

	await serverAdministrationPage.executeScript(fetchScript);
	await expect(
		page.getByText('"emailAddress": "' + emailAddress)
	).toBeVisible();
});

test('LPD-44526 Can deactivate and activate accounts in bulk', async ({
	accountsPage,
	apiHelpers,
	page,
}) => {
	const accounts = [];

	for (let i = 1; i < 7; i++) {
		const account = await apiHelpers.headlessAdminUser.postAccount({
			name: `Account ${i}`,
			type: 'business',
		});

		apiHelpers.data.push({id: account.id, type: 'account'});
		accounts.push(account);
	}

	await accountsPage.goto();

	const accountNames: string[] = [
		accounts[0].name,
		accounts[2].name,
		accounts[4].name,
	];

	for (const name of accountNames) {
		await (await accountsPage.accountsTableRowCheckBox(name)).click();
	}

	page.on('dialog', async (dialog) => await dialog.accept());

	await accountsPage.deactivateButton.click();

	await waitForAlert(page);

	for (const name of accountNames) {
		try {
			await accountsPage.accountsTableRowLink(name);
		}
		catch (error) {
			expect(error).toBeDefined();
		}
	}

	await accountsPage.changeFilter('Inactive');

	for (const name of accountNames) {
		await expect(
			(await accountsPage.accountsTableRow(1, name, true)).row
		).toBeVisible();
	}

	for (const name of accountNames) {
		await (await accountsPage.accountsTableRowCheckBox(name)).click();
	}

	await accountsPage.activateButton.click();

	await waitForAlert(page);

	await expect(accountsPage.noAccountsMessage).toBeVisible();

	await accountsPage.changeFilter('Active');

	for (const account of accounts) {
		await expect(
			(await accountsPage.accountsTableRow(1, account.name, true)).row
		).toBeVisible();
	}
});

test('LPD-45545 Can add and remove organizations in bulk', async ({
	accountsPage,
	apiHelpers,
	page,
}) => {
	page.on('dialog', (dialog) => dialog.accept());

	const account = await apiHelpers.headlessAdminUser.postAccount();

	apiHelpers.data.push({id: account.id, type: 'account'});

	for (let i = 1; i < 5; i++) {
		await apiHelpers.headlessAdminUser.postOrganization({
			name: `Organization ${i}`,
		});
	}

	await accountsPage.goto();

	await (await accountsPage.accountsTableRowLink(account.name)).click();

	await accountsPage.organizationsTab.click();
	await accountsPage.newButton.click();

	for (let i = 1; i < 5; i++) {
		await (
			await accountsPage.organizationAssignmentCheckBox(
				`Organization ${i}`
			)
		).check();
	}

	await accountsPage.organizationAssignButton.click();

	await waitForAlert(page);

	for (let i = 1; i < 5; i++) {
		await expect(
			accountsPage.accountOrganizationsTableCell(`Organization ${i}`)
		).toBeVisible();
	}

	for (const index of [1, 3]) {
		await (
			await accountsPage.accountOrganizationsCheckbox(
				`Organization ${index}`
			)
		).check();
	}

	await accountsPage.removeAccountOrganizationButton.click();

	await waitForAlert(page);

	for (let i = 1; i < 5; i++) {
		if (i % 2 === 0) {
			await expect(
				accountsPage.accountOrganizationsTableCell(`Organization ${i}`)
			).toBeVisible();
		}
		else {
			await expect(
				accountsPage.accountOrganizationsTableCell(`Organization ${i}`)
			).not.toBeVisible();
		}
	}
});

test('LPD-45897 Can delete accounts in bulk', async ({
	accountsPage,
	apiHelpers,
	page,
}) => {
	page.on('dialog', async (dialog) => await dialog.accept());

	for (let i = 1; i < 7; i++) {
		if (i < 4) {
			const account = await apiHelpers.headlessAdminUser.postAccount({
				name: `Account ${i}`,
				type: 'business',
			});

			apiHelpers.data.push({id: account.id, type: 'account'});
		}
		else {
			const account = await apiHelpers.headlessAdminUser.postAccount({
				name: `Account ${i}`,
				type: 'person',
			});
			apiHelpers.data.push({id: account.id, type: 'account'});
		}
	}

	await accountsPage.goto();

	for (const i of [1, 2, 4, 6]) {
		await (
			await accountsPage.accountsTableRowCheckBox(`Account ${i}`)
		).click();
	}

	await accountsPage.deleteButton.click();

	await waitForAlert(page);

	for (const i of [1, 2, 4, 6]) {
		await expect(
			accountsPage.accountsTableCell(`Account ${i}`)
		).not.toBeVisible();
	}

	for (const i of [3, 5]) {
		await expect(
			accountsPage.accountsTableCell(`Account ${i}`)
		).toBeVisible();
	}

	await accountsPage.changeFilter('Inactive');

	await expect(accountsPage.noAccountsMessage).toBeVisible();
});
