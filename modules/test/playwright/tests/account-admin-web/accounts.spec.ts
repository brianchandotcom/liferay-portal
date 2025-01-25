/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import path from 'path';

import {accountsPagesTest} from '../../fixtures/accountsPagesTest';
import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {serverAdministrationPageTest} from '../../fixtures/serverAdministrationPageTest';
import {usersAndOrganizationsPagesTest} from '../../fixtures/usersAndOrganizationsPagesTest';
import getRandomString from '../../utils/getRandomString';
import {nextPage, setItemsPerPage} from '../../utils/pagination';
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

	await (await accountsPage.accountsTable.cellLink(account.name)).click();
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

	await (await accountsPage.accountsTable.cellLink(account.name)).click();
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

	await (await accountsPage.accountsTable.cellLink(account.name)).click();
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

	await (await accountsPage.accountsTable.cellLink(account.name)).click();
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

	await (await accountsPage.accountsTable.cellLink(account.name)).click();
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

	await (await accountsPage.accountsTable.cellLink(account.name)).click();
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
			await accountsPage.accountsTable.cellLink(account1.name)
		).toHaveCount(1);
		await expect(
			await accountsPage.accountsTable.cellLink(account2.name)
		).toHaveCount(1);
		await expect(
			await accountsPage.accountsTable.cellLink(account3.name)
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
	await (await accountsPage.accountsTable.cellLink(account.name)).click();
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

test('LPD-44526 Can activate and deactivate an account', async ({
	accountsPage,
	apiHelpers,
	page,
}) => {
	page.on('dialog', (dialog) => {
		dialog.accept().catch(() => {});
	});

	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: getRandomString(),
		type: 'business',
	});

	apiHelpers.data.push({id: account.id, type: 'account'});

	await accountsPage.goto();

	await (await accountsPage.accountsTable.rowActions(account.name)).click();
	await accountsPage.deactivateButton.click();

	await waitForAlert(page);

	await expect(accountsPage.accountsTable.cell(account.name)).toHaveCount(0);

	await accountsPage.accountsTable.filterButton.click();
	await accountsPage.accountsTable.filterMenuItem('Inactive').click();

	await expect(accountsPage.accountsTable.cell(account.name)).toBeVisible();

	await (await accountsPage.accountsTable.rowActions(account.name)).click();
	await accountsPage.activateButton.click();

	await expect(accountsPage.accountsTable.cell(account.name)).toHaveCount(0);

	await accountsPage.accountsTable.filterButton.click();
	await accountsPage.accountsTable.filterMenuItem('Active').click();

	await expect(accountsPage.accountsTable.cell(account.name)).toBeVisible();
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
		await (await accountsPage.accountsTable.rowCheckBox(name)).click();
	}

	page.on('dialog', async (dialog) => await dialog.accept());

	await accountsPage.deactivateButton.click();

	await waitForAlert(page);

	for (const name of accountNames) {
		try {
			await accountsPage.accountsTable.cellLink(name);
		}
		catch (error) {
			expect(error).toBeDefined();
		}
	}

	await accountsPage.changeFilter('Inactive');

	for (const name of accountNames) {
		await expect(
			(await accountsPage.accountsTable.row(1, name, true)).row
		).toBeVisible();
	}

	for (const name of accountNames) {
		await (await accountsPage.accountsTable.rowCheckBox(name)).click();
	}

	await accountsPage.activateButton.click();

	await waitForAlert(page);

	await expect(accountsPage.noAccountsMessage).toBeVisible();

	await accountsPage.changeFilter('Active');

	for (const account of accounts) {
		await expect(
			(await accountsPage.accountsTable.row(1, account.name, true)).row
		).toBeVisible();
	}
});

test('LPD-45897 Can delete an account', async ({
	accountsPage,
	apiHelpers,
	page,
}) => {
	page.on('dialog', (dialog) => {
		dialog.accept().catch(() => {});
	});

	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: getRandomString(),
		type: 'business',
	});

	apiHelpers.data.push({id: account.id, type: 'account'});

	await accountsPage.goto();

	await (await accountsPage.accountsTable.rowActions(account.name)).click();
	await accountsPage.deleteButton.click();

	await waitForAlert(page);

	await expect(accountsPage.accountsTable.cell(account.name)).toHaveCount(0);

	await accountsPage.accountsTable.filterButton.click();
	await accountsPage.accountsTable.filterMenuItem('Inactive').click();

	await expect(accountsPage.accountsTable.cell(account.name)).toHaveCount(0);
});

test('LPD-45897 Can delete an inactive account', async ({
	accountsPage,
	apiHelpers,
	page,
}) => {
	page.on('dialog', (dialog) => {
		dialog.accept().catch(() => {});
	});

	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: getRandomString(),
		type: 'business',
	});

	apiHelpers.data.push({id: account.id, type: 'account'});

	await accountsPage.goto();

	await (await accountsPage.accountsTable.rowActions(account.name)).click();
	await accountsPage.deactivateButton.click();

	await waitForAlert(page);

	await expect(accountsPage.accountsTable.cell(account.name)).toHaveCount(0);

	await accountsPage.accountsTable.filterButton.click();
	await accountsPage.accountsTable.filterMenuItem('Inactive').click();

	await expect(accountsPage.accountsTable.searchInput).toBeEnabled();
	await expect(accountsPage.accountsTable.cell(account.name)).toHaveCount(1);

	await (await accountsPage.accountsTable.rowActions(account.name)).click();
	await accountsPage.deleteButton.click();

	await waitForAlert(page);

	await expect(accountsPage.accountsTable.cell(account.name)).toHaveCount(0);
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
			await accountsPage.accountsTable.rowCheckBox(`Account ${i}`)
		).click();
	}

	await accountsPage.deleteButton.click();

	await waitForAlert(page);

	for (const i of [1, 2, 4, 6]) {
		await expect(
			accountsPage.accountsTable.cell(`Account ${i}`)
		).not.toBeVisible();
	}

	for (const i of [3, 5]) {
		await expect(
			accountsPage.accountsTable.cell(`Account ${i}`)
		).toBeVisible();
	}

	await accountsPage.changeFilter('Inactive');

	await expect(accountsPage.noAccountsMessage).toBeVisible();
});

test('LPS-195988 An account name should be limited to 250 characters', async ({
	accountsPage,
	apiHelpers,
	editAccountPage,
}) => {
	const name =
		'Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet';

	await accountsPage.goto();

	await accountsPage.accountsTable.newButton.click();

	await editAccountPage.createAccount(apiHelpers, {name});
	await editAccountPage.backButton.click();

	await expect(accountsPage.accountsTable.cell(name)).toHaveCount(0);
	await expect(
		accountsPage.accountsTable.cell(name.substring(0, 250))
	).toBeVisible();

	await (
		await accountsPage.accountsTable.cellLink(name.substring(0, 250))
	).click();

	await expect(editAccountPage.accountNameInput).toHaveValue(
		name.substring(0, 250)
	);
});

test('LPS-195988 The account external reference code should be unique', async ({
	accountsPage,
	apiHelpers,
	editAccountPage,
	page,
}) => {
	const account = await apiHelpers.headlessAdminUser.postAccount({
		externalReferenceCode: getRandomString(),
		name: getRandomString(),
		type: 'business',
	});

	apiHelpers.data.push({id: account.id, type: 'account'});

	await accountsPage.goto();

	await accountsPage.accountsTable.newButton.click();

	await editAccountPage.accountNameInput.fill(getRandomString());
	await editAccountPage.externalReferenceCodeInput.fill(
		account.externalReferenceCode
	);

	await editAccountPage.saveButton.click();

	await waitForAlert(
		page,
		'The given external reference code belongs to another account',
		{type: 'danger'}
	);
});

test('LPS-195988 Can create different type of accounts', async ({
	accountsPage,
	apiHelpers,
	editAccountPage,
}) => {
	await accountsPage.goto();

	const accounts = [
		{name: getRandomString(), type: 'business'},
		{name: getRandomString(), type: 'guest'},
		{name: getRandomString(), type: 'person'},
		{name: getRandomString(), type: 'supplier'},
	];

	for (const {name, type} of accounts) {
		await accountsPage.accountsTable.newButton.click();

		await editAccountPage.createAccount(apiHelpers, {name, type});
		await editAccountPage.backButton.click();

		await expect(
			await accountsPage.accountsTable.cellLink(name)
		).toBeVisible();

		await (await accountsPage.accountsTable.cellLink(name)).click();

		await expect(editAccountPage.accountNameInput).toBeVisible();
		await expect(editAccountPage.typeInput).toHaveValue(
			new RegExp(type, 'i')
		);

		await editAccountPage.backButton.click();
	}

	for (const account of accounts) {
		await expect(
			accountsPage.accountsTable.cell(account.name)
		).toBeVisible();
	}

	await accountsPage.accountsTable.filterButton.click();
	await accountsPage.accountsTable.filterMenuItem('Business').click();

	for (const account of accounts) {
		if (account.type === 'business') {
			await expect(
				accountsPage.accountsTable.cell(account.name)
			).toBeVisible();
		}
		else {
			await expect(
				accountsPage.accountsTable.cell(account.name)
			).toHaveCount(0);
		}
	}

	await accountsPage.accountsTable.filterButton.click();
	await accountsPage.accountsTable.filterMenuItem('Guest').click();

	for (const account of accounts) {
		if (account.type === 'guest') {
			await expect(
				accountsPage.accountsTable.cell(account.name)
			).toBeVisible();
		}
		else {
			await expect(
				accountsPage.accountsTable.cell(account.name)
			).toHaveCount(0);
		}
	}

	await accountsPage.accountsTable.filterButton.click();
	await accountsPage.accountsTable.filterMenuItem('Person').click();

	for (const account of accounts) {
		if (account.type === 'person') {
			await expect(
				accountsPage.accountsTable.cell(account.name)
			).toBeVisible();
		}
		else {
			await expect(
				accountsPage.accountsTable.cell(account.name)
			).toHaveCount(0);
		}
	}

	await accountsPage.accountsTable.filterButton.click();
	await accountsPage.accountsTable.filterMenuItem('Supplier').click();

	for (const account of accounts) {
		if (account.type === 'supplier') {
			await expect(
				accountsPage.accountsTable.cell(account.name)
			).toBeVisible();
		}
		else {
			await expect(
				accountsPage.accountsTable.cell(account.name)
			).toHaveCount(0);
		}
	}
});

test('LPS-195988 Multiple accounts can be added with the same domain', async ({
	accountsPage,
	apiHelpers,
	editAccountPage,
	emailDomainsInstanceSettingsPage,
}) => {
	const accounts = [
		{
			domains: ['liferay.com'],
			name: getRandomString(),
			type: 'business',
		},
		{
			domains: ['liferay.com'],
			name: getRandomString(),
			type: 'business',
		},
	];

	await emailDomainsInstanceSettingsPage.enableEmailDomainValidation();

	try {
		await accountsPage.goto();

		for (const {domains, name, type} of accounts) {
			await accountsPage.accountsTable.newButton.click();

			await editAccountPage.createAccount(apiHelpers, {
				domains,
				name,
				type,
			});

			for (const domain of domains) {
				await expect(editAccountPage.domainCell(domain)).toBeVisible();
			}

			await editAccountPage.backButton.click();

			await (await accountsPage.accountsTable.cellLink(name)).click();

			for (const domain of domains) {
				await expect(editAccountPage.domainCell(domain)).toBeVisible();
			}

			await editAccountPage.backButton.click();
		}
	}
	finally {
		await emailDomainsInstanceSettingsPage.enableEmailDomainValidation(
			false
		);
	}
});

test('LPS-195988 A business account can have more than one domain', async ({
	accountsPage,
	apiHelpers,
	editAccountPage,
	emailDomainsInstanceSettingsPage,
}) => {
	const account = {
		domains: ['liferay.com', 'google.com', 'si-na.com', '9teen.com'],
		name: getRandomString(),
		type: 'business',
	};

	const {domains, name, type} = account;

	await emailDomainsInstanceSettingsPage.enableEmailDomainValidation();

	try {
		await accountsPage.goto();

		await accountsPage.accountsTable.newButton.click();

		await editAccountPage.createAccount(apiHelpers, {domains, name, type});

		for (const domain of domains) {
			await expect(editAccountPage.domainCell(domain)).toBeVisible();
		}

		await editAccountPage.backButton.click();

		await (await accountsPage.accountsTable.cellLink(name)).click();

		await editAccountPage.domainRemoveButton(domains[0]).click();

		for (const [index, domain] of domains.entries()) {
			if (index === 0) {
				await expect(editAccountPage.domainCell(domain)).toHaveCount(0);
			}
			else {
				await expect(editAccountPage.domainCell(domain)).toBeVisible();
			}
		}
	}
	finally {
		await emailDomainsInstanceSettingsPage.enableEmailDomainValidation(
			false
		);
	}
});

test('LPS-195988 Domain validation is not present in Person Accounts', async ({
	accountsPage,
	editAccountPage,
	emailDomainsInstanceSettingsPage,
}) => {
	await emailDomainsInstanceSettingsPage.enableEmailDomainValidation();

	try {
		await accountsPage.goto();

		await accountsPage.accountsTable.newButton.click();

		await expect(editAccountPage.validDomainsHeading).toBeVisible();

		await editAccountPage.typeInput.selectOption('person');

		await expect(editAccountPage.validDomainsHeading).toHaveCount(0);

		await editAccountPage.typeInput.selectOption('business');

		await expect(editAccountPage.validDomainsHeading).toBeVisible();
	}
	finally {
		await emailDomainsInstanceSettingsPage.enableEmailDomainValidation(
			false
		);
	}
});

test('LPS-101893 Account list should be paginated', async ({
	accountsPage,
	apiHelpers,
	page,
}) => {
	for (let i = 1; i < 6; i++) {
		const account = await apiHelpers.headlessAdminUser.postAccount({
			name: `Account ${i}`,
			type: 'business',
		});

		apiHelpers.data.push({id: account.id, type: 'account'});
	}

	await accountsPage.goto();

	await setItemsPerPage(page, 4);

	for (let i = 1; i < 6; i++) {
		if (i < 5) {
			await expect(
				accountsPage.accountsTable.cell(`Account ${i}`)
			).toBeVisible();
		}
		else {
			await expect(
				accountsPage.accountsTable.cell(`Account ${i}`)
			).toHaveCount(0);
		}
	}

	await nextPage(page);

	for (let i = 1; i < 6; i++) {
		if (i < 5) {
			await expect(
				accountsPage.accountsTable.cell(`Account ${i}`)
			).toHaveCount(0);
		}
		else {
			await expect(
				accountsPage.accountsTable.cell(`Account ${i}`)
			).toBeVisible();
		}
	}
});

test('LPS-157661 An account avatar can be added in creation', async ({
	accountsPage,
	apiHelpers,
	editAccountPage,
}) => {
	const name = getRandomString();

	await accountsPage.goto();

	await accountsPage.accountsTable.newButton.click();

	await editAccountPage.createAccount(apiHelpers, {
		avatar: path.join(__dirname, '/dependencies/liferay.png'),
		name,
	});
	await editAccountPage.backButton.click();

	await (await accountsPage.accountsTable.cellLink(name)).click();

	await expect(editAccountPage.imageInput).toHaveValue('Custom Image');
});

test('LPS-195988 An account can be updated', async ({
	accountsPage,
	apiHelpers,
	editAccountPage,
	page,
}) => {
	const account = await apiHelpers.headlessAdminUser.postAccount({
		description: getRandomString(),
		type: 'business',
	});

	apiHelpers.data.push({id: account.id, type: 'account'});

	await accountsPage.goto();

	await accountsPage.accountNameLink(account.name).click();

	await expect(editAccountPage.accountNameInput).toHaveValue(account.name);
	await expect(editAccountPage.descriptionInput).toHaveValue(
		account.description
	);
	await expect(editAccountPage.externalReferenceCodeInput).toHaveValue(
		account.externalReferenceCode
	);

	const updatedAccount = {
		description: getRandomString(),
		externalReferenceCode: getRandomString(),
		name: getRandomString(),
	};

	await editAccountPage.accountNameInput.fill(updatedAccount.name);
	await editAccountPage.descriptionInput.fill(updatedAccount.description);
	await editAccountPage.externalReferenceCodeInput.fill(
		updatedAccount.externalReferenceCode
	);
	await editAccountPage.saveButton.click();

	await waitForAlert(page);

	await editAccountPage.backButton.click();

	await expect(accountsPage.accountsTable.cell(account.name)).toHaveCount(0);

	await accountsPage.accountNameLink(updatedAccount.name).click();

	await expect(editAccountPage.accountNameInput).toHaveValue(
		updatedAccount.name
	);
	await expect(editAccountPage.descriptionInput).toHaveValue(
		updatedAccount.description
	);
	await expect(editAccountPage.externalReferenceCodeInput).toHaveValue(
		updatedAccount.externalReferenceCode
	);
});

test('LPS-101221 An inactive account can be updated', async ({
	accountsPage,
	apiHelpers,
	editAccountPage,
	page,
}) => {
	page.on('dialog', (dialog) => {
		dialog.accept().catch(() => {});
	});

	const account = await apiHelpers.headlessAdminUser.postAccount({
		description: getRandomString(),
		type: 'business',
	});

	apiHelpers.data.push({id: account.id, type: 'account'});

	await accountsPage.goto();

	await (await accountsPage.accountsTable.rowActions(account.name)).click();
	await accountsPage.deactivateButton.click();

	await waitForAlert(page);

	await accountsPage.accountsTable.filterButton.click();
	await accountsPage.accountsTable.filterMenuItem('Inactive').click();

	await accountsPage.accountNameLink(account.name).click();

	await expect(editAccountPage.accountNameInput).toHaveValue(account.name);
	await expect(editAccountPage.descriptionInput).toHaveValue(
		account.description
	);
	await expect(editAccountPage.externalReferenceCodeInput).toHaveValue(
		account.externalReferenceCode
	);

	const updatedAccount = {
		description: getRandomString(),
		externalReferenceCode: getRandomString(),
		name: getRandomString(),
	};

	await editAccountPage.accountNameInput.fill(updatedAccount.name);
	await editAccountPage.descriptionInput.fill(updatedAccount.description);
	await editAccountPage.externalReferenceCodeInput.fill(
		updatedAccount.externalReferenceCode
	);
	await editAccountPage.saveButton.click();

	await waitForAlert(page);

	await editAccountPage.backButton.click();

	await expect(accountsPage.accountsTable.cell(account.name)).toHaveCount(0);
	await expect(
		accountsPage.accountsTable.cell(updatedAccount.name)
	).toBeVisible();

	await accountsPage.accountsTable.filterButton.click();
	await accountsPage.accountsTable.filterMenuItem('Active').click();

	await expect(accountsPage.accountsTable.cell(account.name)).toHaveCount(0);
	await expect(
		accountsPage.accountsTable.cell(updatedAccount.name)
	).toHaveCount(0);

	await accountsPage.accountsTable.filterButton.click();
	await accountsPage.accountsTable.filterMenuItem('Inactive').click();

	await expect(accountsPage.accountsTable.cell(account.name)).toHaveCount(0);
	await expect(
		accountsPage.accountsTable.cell(updatedAccount.name)
	).toBeVisible();
});

test('LPS-101221 Can search an account', async ({
	accountsPage,
	apiHelpers,
	page,
}) => {
	page.on('dialog', (dialog) => {
		dialog.accept().catch(() => {});
	});

	const account1 = await apiHelpers.headlessAdminUser.postAccount({
		description: getRandomString(),
		type: 'business',
	});

	apiHelpers.data.push({id: account1.id, type: 'account'});

	const account2 = await apiHelpers.headlessAdminUser.postAccount({
		description: getRandomString(),
		type: 'business',
	});

	apiHelpers.data.push({id: account2.id, type: 'account'});

	await accountsPage.goto();

	await (await accountsPage.accountsTable.rowActions(account1.name)).click();
	await accountsPage.deactivateButton.click();

	await waitForAlert(page);

	await accountsPage.accountsTable.search(account1.name);

	await expect(accountsPage.accountsTable.cell(account1.name)).toHaveCount(0);
	await expect(accountsPage.accountsTable.cell(account2.name)).toHaveCount(0);

	await accountsPage.accountsTable.search(account2.name);

	await expect(accountsPage.accountsTable.cell(account1.name)).toHaveCount(0);
	await expect(accountsPage.accountsTable.cell(account2.name)).toBeVisible();

	await expect(accountsPage.accountsTable.searchInput).toBeEnabled();

	await accountsPage.accountsTable.filterButton.click();
	await accountsPage.accountsTable.filterMenuItem('Inactive').click();

	await accountsPage.accountsTable.search(account1.name);

	await expect(accountsPage.accountsTable.cell(account1.name)).toHaveCount(0);
	await expect(accountsPage.accountsTable.cell(account2.name)).toHaveCount(0);

	await accountsPage.accountsTable.search(account1.name);

	await expect(accountsPage.accountsTable.cell(account1.name)).toBeVisible();
	await expect(accountsPage.accountsTable.cell(account2.name)).toHaveCount(0);
});
