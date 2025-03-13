/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {instanceSettingsPagesTest} from '../../fixtures/instanceSettingsPagesTest';
import {ldapConfigurationPagesTest} from '../../fixtures/ldapConfigurationPagesTest';
import {loginTest} from '../../fixtures/loginTest';
import {systemSettingsPageTest} from '../../fixtures/systemSettingsPageTest';
import {userGroupsPageTest} from '../../fixtures/userGroupsPageTest';
import {usersAndOrganizationsPagesTest} from '../../fixtures/usersAndOrganizationsPagesTest';
import {
	TLdapConfiguration,
	TLdapServer,
} from '../../helpers/LdapConfigurationHelper';
import {SystemSettingsPage} from '../../pages/configuration-admin-web/SystemSettingsPage';
import {LdapConfigurationPage} from '../../pages/portal-security-ldap/LdapConfigurationPage';
import {LdapServerPage} from '../../pages/portal-security-ldap/LdapServerPage';
import {ApplicationsMenuPage} from '../../pages/product-navigation-applications-menu/ApplicationsMenuPage';
import {ServerAdministrationPage} from '../../pages/server-admin-web/ServerAdministrationPage';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../utils/getRandomString';
import performLogin, {userData} from '../../utils/performLogin';
import {waitForAlert} from '../../utils/waitForAlert';

export const test = mergeTests(
	apiHelpersTest,
	loginTest(),
	instanceSettingsPagesTest,
	ldapConfigurationPagesTest,
	systemSettingsPageTest,
	usersAndOrganizationsPagesTest,
	userGroupsPageTest
);

const LDAP_GROUP_1 = 'ldapgroup1';
const LDAP_GROUP_2 = 'ldapgroup2';
const LDAP_GROUP_3 = 'ldapgroup3';
const LDAP_GROUP_3_MODIFIED = 'ldapgroup3modified';

const LDAP_USER_1: TUserAccount = {
	alternateName: 'ldapuser1',
	emailAddress: 'ldapuser1@liferay.com',
	familyName: 'last',
	givenName: 'first',
	password: 'test',
};

const LDAP_USER_2: TUserAccount = {
	alternateName: 'ldapuser2',
	emailAddress: 'ldapuser2@liferay.com',
	familyName: 'last',
	givenName: 'first',
	password: 'test',
};

const LDAP_USER_3: TUserAccount = {
	alternateName: 'ldapuser3',
	emailAddress: 'ldapuser3@liferay.com',
	familyName: 'last',
	givenName: 'first',
	password: 'test',
};

const LDAP_USER_3_MODIFIED: TUserAccount = {
	alternateName: 'ldapuser3modified',
	emailAddress: 'ldapuser3@liferay.com',
	familyName: 'lastmodified',
	givenName: 'firstmodified',
	password: 'testmodified',
};

test.afterAll(async ({browser}) => {
	const page = await browser.newPage();

	await performLogin(page, 'test');

	const systemSettingsPage = new SystemSettingsPage(page);

	await test.step('Reset System Settings LDAP configuration', async () => {
		await resetLdapImportSystemSettings(systemSettingsPage);
	});
});

test.afterEach(
	async ({
		apiHelpers,
		ldapConfigurationPage,
		ldapServerPage,
		userGroupsPage,
	}) => {
		await test.step('Delete LDAP servers from portal', async () => {
			await ldapServerPage.deleteLdapServers();
		});

		await test.step('Reset LDAP Instance Settings', async () => {
			await ldapConfigurationPage.resetLdapConfiguration();
		});

		await test.step('Delete LDAP users from portal if present', async () => {
			for (const ldapUser of [
				LDAP_USER_1,
				LDAP_USER_2,
				LDAP_USER_3,
				LDAP_USER_3_MODIFIED,
			]) {
				const user =
					await apiHelpers.headlessAdminUser.getUserAccountByEmailAddress(
						ldapUser.emailAddress
					);

				if (user.id !== undefined) {
					await apiHelpers.headlessAdminUser.deleteUserAccount(
						Number(user.id)
					);
				}
			}
		});

		await test.step('Delete LDAP groups from portal if present', async () => {
			await userGroupsPage.goto();

			const selectAllCheckbox = userGroupsPage.page.getByLabel(
				'Select All Items on the Page'
			);

			await selectAllCheckbox.waitFor();

			await userGroupsPage.page.waitForTimeout(1000);

			if (await selectAllCheckbox.isEnabled()) {
				await selectAllCheckbox.click();

				userGroupsPage.page.once('dialog', async (dialog) => {
					dialog.accept();
				});

				await userGroupsPage.page
					.getByRole('button', {name: 'Delete'})
					.click();

				await waitForAlert(
					userGroupsPage.page,
					`Success:Your request completed successfully.`
				);
			}
		});
	}
);

test.beforeAll(async () => {

	// Add LDAP user info to userData so we can authenticate via performLogin or
	// performLoginViaApi

	for (const ldapUser of [LDAP_USER_1, LDAP_USER_2, LDAP_USER_3]) {
		userData[ldapUser.alternateName] = {
			name: ldapUser.givenName,
			password: ldapUser.password,
			surname: ldapUser.familyName,
		};
	}

	// The modified user is a special case, because it uses the existing email
	// address, but the data and credentials are different.  We can workaround
	// the performLogin constraints by using the email as the key, and passing
	// in a blank 'domain' argument.

	userData[LDAP_USER_3_MODIFIED.emailAddress] = {
		name: LDAP_USER_3_MODIFIED.givenName,
		password: LDAP_USER_3_MODIFIED.password,
		surname: LDAP_USER_3_MODIFIED.familyName,
	};
});

test.beforeEach(async ({browser}) => {
	await test.step('Enable LDAP, but prevent bulk import.  We can manually trigger bulk import via groovy script instead of waiting the import interval.', async () => {
		const page = await browser.newPage();

		await performLogin(page, 'test');

		const ldapConfigurationPage = new LdapConfigurationPage(page);

		const ldapConfiguration: TLdapConfiguration = {
			enableImport: true,
			enabled: true,
			importInterval: 0,
		};

		await ldapConfigurationPage.updateLDAPConfiguration(ldapConfiguration);
	});
});

test('LPD-47223 AC1 TC1: Verify LDAP import via authentication imports user attributes and user groups, but only for the user being authenticated', async ({
	browser,
	editUserPage,
	ldapServerPage,
	userGroupsPage,
	usersAndOrganizationsPage,
}) => {
	const ldapServer: TLdapServer = {
		defaultValues: 'OpenLDAP',
		principal: 'cn=admin,dc=example,dc=com',
		serverName: getRandomString(),
	};

	await test.step('Add LDAP server', async () => {
		await ldapServerPage.addLdapServer(ldapServer);
	});

	await testAndExpectLdapEntries(
		'group',
		[LDAP_GROUP_1, LDAP_GROUP_2],
		ldapServer.serverName,
		ldapServerPage
	);

	await testAndExpectLdapEntries(
		'user',
		[LDAP_USER_1.alternateName, LDAP_USER_2.alternateName],
		ldapServer.serverName,
		ldapServerPage,
		true
	);

	await test.step(`Authenticate with ${LDAP_USER_2.alternateName}`, async () => {
		const page = await browser.newPage();

		await performLogin(page, LDAP_USER_2.alternateName);
	});

	await test.step(`Assert only ${LDAP_USER_2.alternateName} was imported`, async () => {
		await usersAndOrganizationsPage.goToUsers(false);

		await expect(
			await usersAndOrganizationsPage.usersTableCell(
				LDAP_USER_1.alternateName
			)
		).toBeHidden();

		await expect(
			await usersAndOrganizationsPage.usersTableCell(
				LDAP_USER_2.alternateName
			)
		).toBeVisible();
	});

	await test.step('Assert user data was imported correctly', async () => {
		await (
			await usersAndOrganizationsPage.usersTableRowLink(
				LDAP_USER_2.alternateName
			)
		).click();

		await expect(editUserPage.emailAddressInput).toHaveValue(
			LDAP_USER_2.emailAddress
		);

		await expect(editUserPage.firstNameInput).toHaveValue(
			LDAP_USER_2.givenName
		);

		await expect(editUserPage.lastNameInput).toHaveValue(
			LDAP_USER_2.familyName
		);

		await expect(editUserPage.screenNameInput).toHaveValue(
			LDAP_USER_2.alternateName
		);
	});

	await test.step('Assert user membership data was imported correctly', async () => {
		await editUserPage.membershipsLink.click();

		await expect(
			(
				await editUserPage.membershipsUserGroupsTableRow(
					0,
					LDAP_GROUP_2,
					true
				)
			).row
		).toBeVisible();
	});

	await test.step(`Assert only ${LDAP_GROUP_2} was imported`, async () => {
		await userGroupsPage.goto();

		await expect(
			await userGroupsPage.page.getByRole('cell', {
				exact: true,
				name: LDAP_GROUP_1,
			})
		).toBeHidden();

		await expect(
			await userGroupsPage.page.getByRole('cell', {
				exact: true,
				name: LDAP_GROUP_2,
			})
		).toBeVisible();
	});
});

test('LPD-47223 AC2 TC2: Verify LDAP bulk import updates user information and membership', async ({
	browser,
	editUserPage,
	ldapConfigurationPage,
	ldapServerPage,
	usersAndOrganizationsPage,
}) => {
	const ldapServer: TLdapServer = {
		authenticationSearchFilter: `(&(mail=@email_address@)(cn=${LDAP_USER_3.alternateName}))`,
		defaultValues: 'OpenLDAP',
		ignoreUserSearchFilterForAuthentication: false,
		importSearchFilterUser: `(&(objectClass=inetOrgPerson)(cn=${LDAP_USER_3.alternateName}))`,
		principal: 'cn=admin,dc=example,dc=com',
		serverName: getRandomString(),
	};

	await test.step('Add LDAP server', async () => {
		await ldapServerPage.addLdapServer(ldapServer);
	});

	await testAndExpectLdapEntries(
		'user',
		[LDAP_USER_3.alternateName],
		ldapServer.serverName,
		ldapServerPage,
		true,
		[LDAP_USER_3_MODIFIED.alternateName]
	);

	await invokeLdapImport(ldapConfigurationPage.page);

	await test.step('Assert user data and membership was imported correctly', async () => {
		await usersAndOrganizationsPage.goToUsers(false);

		await (
			await usersAndOrganizationsPage.usersTableRowLink(
				LDAP_USER_3.alternateName
			)
		).click();

		await expect(editUserPage.emailAddressInput).toHaveValue(
			LDAP_USER_3.emailAddress
		);

		await expect(editUserPage.firstNameInput).toHaveValue(
			LDAP_USER_3.givenName
		);

		await expect(editUserPage.lastNameInput).toHaveValue(
			LDAP_USER_3.familyName
		);

		await expect(editUserPage.screenNameInput).toHaveValue(
			LDAP_USER_3.alternateName
		);

		await editUserPage.membershipsLink.click();

		await expect(
			await editUserPage.page.getByRole('cell', {
				exact: true,
				name: LDAP_GROUP_3,
			})
		).toBeVisible();

		await expect(
			await editUserPage.page.getByRole('cell', {
				exact: true,
				name: LDAP_GROUP_3_MODIFIED,
			})
		).not.toBeVisible();
	});

	await test.step('Change user data and memberships on LDAP server by adjusting import filter, since we cannot modify LDAP server from playwright test.  The email will stay the same, so the portal will think the user was actually updated in LDAP.', async () => {
		ldapServer.authenticationSearchFilter = `(&(mail=@email_address@)(cn=${LDAP_USER_3_MODIFIED.alternateName}))`;
		ldapServer.importSearchFilterUser = `(&(objectClass=inetOrgPerson)(cn=${LDAP_USER_3_MODIFIED.alternateName}))`;

		await ldapServerPage.editLdapServer(ldapServer);
	});

	await invokeLdapImport(ldapServerPage.page);

	await test.step('Assert user data and membership was updated correctly', async () => {
		await usersAndOrganizationsPage.goToUsers(false);

		await expect(
			await usersAndOrganizationsPage.usersTableCell(
				LDAP_USER_3.alternateName
			)
		).toBeHidden();

		await (
			await usersAndOrganizationsPage.usersTableRowLink(
				LDAP_USER_3_MODIFIED.alternateName
			)
		).click();

		await expect(editUserPage.firstNameInput).toHaveValue(
			LDAP_USER_3_MODIFIED.givenName
		);

		await expect(editUserPage.lastNameInput).toHaveValue(
			LDAP_USER_3_MODIFIED.familyName
		);

		await expect(editUserPage.screenNameInput).toHaveValue(
			LDAP_USER_3_MODIFIED.alternateName
		);

		await editUserPage.membershipsLink.click();

		await expect(
			await editUserPage.page.getByRole('cell', {
				exact: true,
				name: LDAP_GROUP_3,
			})
		).not.toBeVisible();

		await expect(
			await editUserPage.page.getByRole('cell', {
				exact: true,
				name: LDAP_GROUP_3_MODIFIED,
			})
		).toBeVisible();
	});

	await test.step('Assert user password updated correctly', async () => {
		const page = await browser.newPage();

		await performLogin(
			page,
			LDAP_USER_3_MODIFIED.emailAddress,
			undefined,
			''
		);
	});
});

test('LPD-47428: Verify a single LDAP user can belong to multiple User Groups imported from LDAP', async ({
	browser,
	editUserPage,
	ldapConfigurationPage,
	ldapServerPage,
	usersAndOrganizationsPage,
}) => {
	const ldapServer1: TLdapServer = {
		defaultValues: 'OpenLDAP',
		importSearchFilterGroup: `(&(objectClass=groupOfUniqueNames)(cn=${LDAP_GROUP_1}))`,
		principal: 'cn=admin,dc=example,dc=com',
		serverName: getRandomString(),
	};

	const ldapServer2: TLdapServer = {
		defaultValues: 'OpenLDAP',
		importSearchFilterGroup: `(&(objectClass=groupOfUniqueNames)(cn=${LDAP_GROUP_2}))`,
		principal: 'cn=admin,dc=example,dc=com',
		serverName: getRandomString(),
	};

	await test.step('Add the same LDAP server twice, but adjust the group import search filter so each entry adds a different group', async () => {
		await test.step('Add first LDAP server', async () => {
			await ldapServerPage.addLdapServer(ldapServer1);
		});

		await testAndExpectLdapEntries(
			'group',
			[LDAP_GROUP_1],
			ldapServer1.serverName,
			ldapServerPage,
			true,
			[LDAP_GROUP_2]
		);

		await test.step('Add second LDAP server', async () => {
			await ldapServerPage.addLdapServer(ldapServer2, false);
		});

		await testAndExpectLdapEntries(
			'group',
			[LDAP_GROUP_2],
			ldapServer2.serverName,
			ldapServerPage,
			undefined,
			[LDAP_GROUP_1]
		);
	});

	await test.step(`Change LDAP Import Method to 'Group'`, async () => {
		const ldapConfiguration: TLdapConfiguration = {
			importMethod: 'Group',
		};

		await ldapConfigurationPage.updateLDAPConfiguration(ldapConfiguration);
	});

	await invokeLdapImport(ldapConfigurationPage.page);

	await test.step('View User Groups associated with the LDAP user, and verify they were correctly imported', async () => {
		await usersAndOrganizationsPage.goToUsers(false);

		await (
			await usersAndOrganizationsPage.usersTableRowLink(
				LDAP_USER_1.alternateName
			)
		).click();

		await editUserPage.membershipsLink.click();

		await expect(
			(
				await editUserPage.membershipsUserGroupsTableRow(
					0,
					LDAP_GROUP_1,
					true
				)
			).row
		).toBeVisible();

		await expect(
			(
				await editUserPage.membershipsUserGroupsTableRow(
					0,
					LDAP_GROUP_2,
					true
				)
			).row
		).toBeVisible();
	});

	await test.step('Attempt login with ldap user, but use incorrect password.  This reproduces the bug described in LPD-47428.', async () => {
		const page = await browser.newPage();

		await page.goto('/');

		await page.getByRole('button', {name: 'Sign In'}).last().click();

		await page.getByLabel('Email Address').fill(LDAP_USER_1.emailAddress);
		await page.getByLabel('Password').fill('badPassword');

		await page.getByRole('button', {name: 'Sign In'}).last().click();

		await waitForAlert(page, 'Error:Authentication failed', {
			autoClose: false,
			type: 'danger',
		});
	});

	await test.step('Verify both User Groups are still associated with the LDAP user.', async () => {
		await editUserPage.page.reload();

		await expect(
			(
				await editUserPage.membershipsUserGroupsTableRow(
					0,
					LDAP_GROUP_1,
					true
				)
			).row
		).toBeVisible();

		await expect(
			(
				await editUserPage.membershipsUserGroupsTableRow(
					0,
					LDAP_GROUP_2,
					true
				)
			).row
		).toBeVisible();
	});
});

test('smoke: Add LDAP server, verify connection, users, and groups are mapped properly, edit LDAP server, then delete LDAP server', async ({
	ldapConfigurationPage,
	ldapServerPage,
}) => {
	const serverName = getRandomString();

	const ldapServer: TLdapServer = {
		defaultValues: 'OpenLDAP',
		principal: 'cn=admin,dc=example,dc=com',
		serverName,
	};

	await test.step('Add LDAP Server', async () => {
		await ldapServerPage.addLdapServer(ldapServer);
	});

	await test.step('Test LDAP Server connections', async () => {
		await ldapServerPage.viewLdapServer(serverName, false);

		await ldapServerPage.testLdapConnection.click();

		await expect(
			await ldapServerPage.page.getByText(
				'Liferay has successfully connected to the LDAP server'
			)
		).toBeVisible();

		await ldapServerPage.closeButton.click();

		await ldapServerPage.testLdapUsers.click();

		await expect(
			await ldapServerPage.page.getByText(
				'A subset of users has been displayed for you to review'
			)
		).toBeVisible();

		await ldapServerPage.closeButton.click();

		await ldapServerPage.testLdapGroups.click();

		await expect(
			await ldapServerPage.page.getByText(
				'A subset of groups has been displayed for you to review'
			)
		).toBeVisible();

		await ldapServerPage.closeButton.click();

		await ldapServerPage.cancelButton.click();
	});

	await test.step('Edit LDAP Server by changing server name', async () => {
		ldapServer.serverName = 'newServerName';

		await ldapServerPage.editLdapServer(ldapServer, serverName, false);

		await expect(
			await ldapConfigurationPage.page.getByRole('row', {
				name: 'newServerName',
			})
		).toBeVisible();
	});

	await test.step('Delete LDAP server', async () => {
		await ldapServerPage.deleteLdapServer('newServerName', false);

		await expect(
			await ldapConfigurationPage.page.getByRole('row', {
				name: 'newServerName',
			})
		).toBeHidden();
	});
});

async function invokeLdapImport(page: Page) {
	await test.step('Manually trigger bulk import', async () => {
		const applicationsMenuPage = new ApplicationsMenuPage(page);

		await applicationsMenuPage.goToServerAdministration();

		const script = `
			import com.liferay.portal.kernel.module.util.SystemBundleUtil;
			import com.liferay.portal.security.ldap.exportimport.LDAPUserImporter;
			import org.osgi.framework.BundleContext;
			BundleContext bundleContext = SystemBundleUtil.getBundleContext();
			def ldapUserImporter = bundleContext.getService(bundleContext.getServiceReference(LDAPUserImporter.class));
			ldapUserImporter.importUsers();
		`;

		const serverAdministrationPage = new ServerAdministrationPage(page);

		await serverAdministrationPage.executeScript(script);
	});
}

async function resetLdapImportSystemSettings(
	systemSettingsPage: SystemSettingsPage
) {
	await systemSettingsPage.goToSystemSetting('LDAP', 'Import');

	await systemSettingsPage.page.getByLabel('Import Interval').waitFor();

	if (
		await systemSettingsPage.page
			.getByRole('button', {name: 'Actions'})
			.isVisible()
	) {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: systemSettingsPage.page.getByRole('menuitem', {
				name: 'Reset Default Values',
			}),
			trigger: systemSettingsPage.page.getByRole('button', {
				name: 'Actions',
			}),
		});

		await waitForAlert(systemSettingsPage.page);
	}
}

async function testAndExpectLdapEntries(
	entryType: 'group' | 'user',
	expectedVisibleEntries: string[],
	ldapServerName: string,
	ldapServerPage: LdapServerPage,
	clickCancel = false,
	expectedNotVisibleEntriess?: string[]
) {
	await ldapServerPage.viewLdapServer(ldapServerName, true);

	if (entryType === 'group') {
		await ldapServerPage.testLdapGroups.click();
	}
	else {
		await ldapServerPage.testLdapUsers.click();
	}

	if (expectedVisibleEntries.length <= 0) {
		await test.step(`Verify LDAP server test displays no ${entryType}s`, async () => {
			await expect(
				await ldapServerPage.page.getByText(
					`No ${entryType}s were found`
				)
			).toBeVisible();
		});
	}
	else {
		await test.step(`Verify LDAP server test ${entryType}s displays ${expectedVisibleEntries.join(
			', '
		)}`, async () => {
			for (const expectedVisibleEntry of expectedVisibleEntries) {
				await expect(
					await ldapServerPage.page.getByRole('cell', {
						exact: true,
						name: expectedVisibleEntry,
					})
				).toBeVisible();
			}
		});
	}

	if (expectedNotVisibleEntriess) {
		await test.step(`Verify LDAP server test ${entryType}s does not display ${expectedNotVisibleEntriess.join(
			', '
		)}`, async () => {
			for (const expectedNotVisibleEntry of expectedNotVisibleEntriess) {
				await expect(
					await ldapServerPage.page.getByRole('cell', {
						exact: true,
						name: expectedNotVisibleEntry,
					})
				).not.toBeVisible();
			}
		});
	}

	await ldapServerPage.closeButton.click();

	if (clickCancel) {
		await ldapServerPage.cancelButton.click();
	}
}
