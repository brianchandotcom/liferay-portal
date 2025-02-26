/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {rolesPagesTest} from '../../fixtures/rolesPagesTest';
import {usersAndOrganizationsPagesTest} from '../../fixtures/usersAndOrganizationsPagesTest';
import {TRole} from '../../helpers/HeadlessAdminUserApiHelper';
import getRandomString from '../../utils/getRandomString';
import {setItemsPerPage} from '../../utils/pagination';
import {
	performLoginViaApi,
	performLogout,
	userData,
} from '../../utils/performLogin';
import {waitForAlert} from '../../utils/waitForAlert';

export const test = mergeTests(
	dataApiHelpersTest,
	isolatedSiteTest,
	loginTest(),
	rolesPagesTest,
	usersAndOrganizationsPagesTest
);

test(
	'Can add roles with same title but different key',
	{tag: ['@LPD-50065']},
	async ({apiHelpers, page, rolePage, rolesPage}) => {
		const key1 = getRandomString();
		const key2 = getRandomString();
		const title = getRandomString();

		await rolesPage.goto();

		await expect(rolesPage.rolesTable.searchInput).toBeEditable();

		await rolesPage.rolesTable.newButton.click();
		await rolePage.addRole(apiHelpers, {name: key1, title});
		await rolePage.backButton.click();

		await rolesPage.rolesTable.newButton.click();
		await rolePage.addRole(apiHelpers, {name: key2, title});
		await rolePage.backButton.click();

		await rolesPage.rolesTable.changeView('Table');
		await page.waitForLoadState('networkidle');
		await rolesPage.rolesTable.search(key1);

		await expect(rolesPage.rolesTable.cell(title)).toHaveCount(1);

		await rolesPage.rolesTable.search(key2);

		await expect(rolesPage.rolesTable.cell(title)).toHaveCount(1);

		await rolesPage.rolesTable.search(title);

		await expect(rolesPage.roleCell(title)).toHaveCount(2);
	}
);

test(
	'Can not add roles with same key',
	{tag: ['@LPD-50065']},
	async ({apiHelpers, page, rolePage, rolesPage}) => {
		const role = await apiHelpers.headlessAdminUser.postRole({
			name: getRandomString(),
			roleType: 'regular',
		});

		await rolesPage.goto();

		await expect(rolesPage.rolesTable.searchInput).toBeEditable();

		await rolesPage.rolesTable.newButton.click();

		const title = getRandomString();

		await rolePage.keyInput.fill(role.name);
		await rolePage.nameInput.fill(title);
		await rolePage.saveButton.click();

		await waitForAlert(page, 'Your request failed to complete', {
			type: 'danger',
		});

		await expect(rolePage.uniqueNameError).toBeVisible();

		await rolePage.backButton.click();
		await rolesPage.rolesTable.search(title);

		await expect(rolesPage.rolesTable.cell(title)).toHaveCount(0);
	}
);

test(
	'Can edit a role',
	{tag: ['@LPD-50065']},
	async ({apiHelpers, page, rolePage, rolesPage}) => {
		const role = await apiHelpers.headlessAdminUser.postRole({
			name: getRandomString(),
			roleType: 'regular',
		});

		await rolesPage.goto();

		await rolesPage.rolesTable.changeView('Table');
		await page.waitForLoadState('networkidle');
		await rolesPage.rolesTable.search(role.name);

		await expect(rolesPage.rolesTable.cell(role.name)).toBeVisible();
		await (await rolesPage.rolesTable.cellLink(role.name)).click();

		const description = getRandomString();
		const name = getRandomString();
		const title = getRandomString();

		await rolePage.descriptionInput.fill(description);
		await rolePage.keyInput.fill(name);
		await rolePage.nameInput.fill(title);
		await rolePage.saveButton.click();

		await waitForAlert(page);

		await rolePage.backButton.click();

		await expect(rolesPage.rolesTable.cell(role.name)).toHaveCount(0);

		await rolesPage.rolesTable.search(title);

		await expect(
			(await rolesPage.rolesTable.row(1, title, true)).row
		).toBeVisible();
		await expect(
			(await rolesPage.rolesTable.row(2, description, true)).row
		).toBeVisible();

		await (await rolesPage.rolesTable.cellLink(title)).click();

		await expect(rolePage.descriptionInput).toHaveValue(description);
		await expect(rolePage.keyInput).toHaveValue(name);
		await expect(rolePage.nameInput).toHaveValue(title);
	}
);

test(
	'Can add a role without a title',
	{tag: ['@LPD-50065', '@LPS-104999', '@LPS-105001']},
	async ({apiHelpers, page, rolePage, rolesPage}) => {
		const key = getRandomString();

		await rolesPage.goto();

		await expect(rolesPage.rolesTable.searchInput).toBeEditable();

		await rolesPage.rolesTable.newButton.click();

		await rolePage.keyInput.fill(key);
		await rolePage.saveButton.click();

		await waitForAlert(page, `${key} was created successfully`);

		const roles = await apiHelpers.headlessAdminUser.getRoles(
			key,
			'rolePermissions'
		);

		if (roles && roles.items) {
			(roles.items as Array<TRole>).map((role) => {
				apiHelpers.data.push({
					id: role.id,
					type: 'role',
				});
			});
		}

		await rolePage.backButton.click();

		await expect(rolesPage.rolesTable.cell(key)).toBeVisible();

		await (await rolesPage.rolesTable.cellLink(key)).click();

		await expect(rolePage.keyInput).toHaveValue(key);
		await expect(rolePage.nameInput).toHaveValue('');
	}
);

test(
	'Can search a role',
	{tag: ['@LPD-50065']},
	async ({apiHelpers, page, rolesPage}) => {
		const role1 = await apiHelpers.headlessAdminUser.postRole({
			name: `A${getRandomString()}`,
			name_i18n: {'en-US': `A${getRandomString()}`},
			roleType: 'regular',
		});

		const role2 = await apiHelpers.headlessAdminUser.postRole({
			name: `A${getRandomString()}`,
			name_i18n: {'en-US': `A${getRandomString()}`},
			roleType: 'regular',
		});

		await rolesPage.goto();

		await rolesPage.rolesTable.changeView('Table');

		await expect(rolesPage.rolesTable.cell('Title')).toBeVisible();

		await page.reload();

		await expect(async () => {
			await rolesPage.rolesTable.search(getRandomString());

			await expect(
				rolesPage.rolesTable.cell(role1.name_i18n['en-US'])
			).toHaveCount(0);
			await expect(
				rolesPage.rolesTable.cell(role2.name_i18n['en-US'])
			).toHaveCount(0);
		}).toPass();

		await rolesPage.rolesTable.search(role1.name);

		await expect(
			rolesPage.rolesTable.cell(role1.name_i18n['en-US'])
		).toBeVisible();
		await expect(
			rolesPage.rolesTable.cell(role2.name_i18n['en-US'])
		).toHaveCount(0);

		await rolesPage.rolesTable.search(role2.name);

		await expect(
			rolesPage.rolesTable.cell(role1.name_i18n['en-US'])
		).toHaveCount(0);
		await expect(
			rolesPage.rolesTable.cell(role2.name_i18n['en-US'])
		).toBeVisible();

		await rolesPage.rolesTable.search(role1.name_i18n['en-US']);

		await expect(
			rolesPage.rolesTable.cell(role1.name_i18n['en-US'])
		).toBeVisible();
		await expect(
			rolesPage.rolesTable.cell(role2.name_i18n['en-US'])
		).toHaveCount(0);

		await rolesPage.rolesTable.search(role2.name_i18n['en-US']);

		await expect(
			rolesPage.rolesTable.cell(role1.name_i18n['en-US'])
		).toHaveCount(0);
		await expect(
			rolesPage.rolesTable.cell(role2.name_i18n['en-US'])
		).toBeVisible();

		await rolesPage.rolesTable.search('');

		await expect(
			rolesPage.rolesTable.cell(role1.name_i18n['en-US'])
		).toBeVisible();
		await expect(
			rolesPage.rolesTable.cell(role2.name_i18n['en-US'])
		).toBeVisible();
	}
);

test(
	'Can sort the roles',
	{tag: ['@LPD-50065']},
	async ({apiHelpers, page, rolesPage}) => {
		const role1 = await apiHelpers.headlessAdminUser.postRole({
			name: `A${getRandomString()}`,
			roleType: 'regular',
		});

		const role2 = await apiHelpers.headlessAdminUser.postRole({
			name: `Z${getRandomString()}`,
			roleType: 'regular',
		});

		await rolesPage.goto();

		await expect(rolesPage.rolesTable.searchInput).toBeEditable();

		await rolesPage.rolesTable.changeView('Table');

		await expect(rolesPage.rolesTable.cell('Title')).toBeVisible();

		await page.reload();

		await expect(async () => {
			await setItemsPerPage(page, 4);

			await expect(page.getByText('Showing 1 to 4')).toBeVisible();
		}).toPass();

		await expect(async () => {
			await rolesPage.rolesTable.orderButton.click();

			await expect(rolesPage.rolesTable.cell(role1.name)).toBeVisible({
				timeout: 300,
			});
			await expect(rolesPage.rolesTable.cell(role2.name)).toHaveCount(0, {
				timeout: 300,
			});
		}).toPass();

		await rolesPage.rolesTable.orderButton.click();

		await expect(rolesPage.rolesTable.cell(role1.name)).toHaveCount(0);
		await expect(rolesPage.rolesTable.cell(role2.name)).toBeVisible();

		await rolesPage.rolesTable.orderButton.click();
	}
);

test(
	'Roles can be viewed in table view',
	{tag: ['@LPD-50065']},
	async ({page, rolesPage}) => {
		await rolesPage.goto();

		await rolesPage.rolesTable.changeView('Table');
		await page.waitForLoadState('networkidle');

		await expect(rolesPage.rolesTable.cell('Title')).toBeVisible();
		await expect(rolesPage.rolesTable.cell('Description')).toBeVisible();
		await expect(
			rolesPage.rolesTable.cell('Number of Assignees')
		).toBeVisible();
	}
);

test(
	'Regular role can be deleted',
	{tag: ['@LPD-50065']},
	async ({apiHelpers, page, rolesPage}) => {
		page.on('dialog', (dialog) => dialog.accept());

		const role = await apiHelpers.headlessAdminUser.postRole({
			name: getRandomString(),
			roleType: 'regular',
		});

		await rolesPage.goto();

		await rolesPage.rolesTable.changeView('Table');
		await page.waitForLoadState('networkidle');
		await rolesPage.rolesTable.search(role.name);

		await expect(rolesPage.rolesTable.cell(role.name)).toBeVisible();

		await expect(async () => {
			await (await rolesPage.rolesTable.rowActions(role.name)).click();

			await expect(rolesPage.deleteButton).toBeVisible({timeout: 100});
		}).toPass();

		await rolesPage.deleteButton.click();

		await expect(rolesPage.rolesTable.cell(role.name)).toHaveCount(0);

		await page.reload();

		await rolesPage.rolesTable.search(role.name);

		await expect(rolesPage.rolesTable.cell(role.name)).toHaveCount(0);
	}
);

test(
	'Organization role can be deleted',
	{tag: ['@LPD-50065']},
	async ({apiHelpers, page, rolesPage}) => {
		page.on('dialog', (dialog) => dialog.accept());

		const role = await apiHelpers.headlessAdminUser.postRole({
			name: getRandomString(),
			roleType: 'organization',
		});

		await rolesPage.goto();

		await rolesPage.rolesTable.changeView('Table');
		await page.waitForLoadState('networkidle');

		await expect(rolesPage.rolesTable.cell(role.name)).toHaveCount(0);

		await rolesPage.organizationRolesLink.click();

		await expect(rolesPage.rolesTable.cell(role.name)).toBeVisible();

		await expect(async () => {
			await (await rolesPage.rolesTable.rowActions(role.name)).click();

			await expect(rolesPage.deleteButton).toBeVisible({timeout: 100});
		}).toPass();

		await rolesPage.deleteButton.click();

		await expect(rolesPage.rolesTable.cell(role.name)).toHaveCount(0);

		await page.reload();

		await rolesPage.rolesTable.search(role.name);

		await expect(rolesPage.rolesTable.cell(role.name)).toHaveCount(0);
	}
);

test(
	'Site role can be deleted',
	{tag: ['@LPD-50065']},
	async ({apiHelpers, page, rolesPage}) => {
		page.on('dialog', (dialog) => dialog.accept());

		const role = await apiHelpers.headlessAdminUser.postRole({
			name: getRandomString(),
			roleType: 'site',
		});

		await rolesPage.goto();

		await rolesPage.rolesTable.changeView('Table');
		await page.waitForLoadState('networkidle');

		await expect(rolesPage.rolesTable.cell(role.name)).toHaveCount(0);

		await rolesPage.siteRolesLink.click();

		await expect(rolesPage.rolesTable.cell(role.name)).toBeVisible();

		await expect(async () => {
			await (await rolesPage.rolesTable.rowActions(role.name)).click();

			await expect(rolesPage.deleteButton).toBeVisible({timeout: 100});
		}).toPass();

		await rolesPage.deleteButton.click();

		await expect(rolesPage.rolesTable.cell(role.name)).toHaveCount(0);

		await page.reload();

		await rolesPage.rolesTable.search(role.name);

		await expect(rolesPage.rolesTable.cell(role.name)).toHaveCount(0);
	}
);

test(
	'User can be assigned to a regular role',
	{tag: ['@LPD-50065', '@LPS-109572']},
	async ({apiHelpers, page, roleAssigneesPage, rolePage, rolesPage}) => {
		const role = await apiHelpers.headlessAdminUser.postRole({
			name: getRandomString(),
			roleType: 'regular',
		});

		const user = await apiHelpers.headlessAdminUser.postUserAccount();

		await apiHelpers.headlessAdminUser.assignUserToRole(
			role.externalReferenceCode,
			user.id
		);

		await rolesPage.goto();

		await rolesPage.rolesTable.changeView('Table');
		await page.waitForLoadState('networkidle');
		await rolesPage.rolesTable.search(role.name);
		await (await rolesPage.rolesTable.cellLink(role.name)).click();
		await rolePage.assigneesLink.click();

		await expect(
			roleAssigneesPage.assigneesTable.cell(user.name)
		).toBeVisible();
	}
);

test(
	'User can be assigned / unassigned from a regular role',
	{tag: ['@LPD-50065', '@LPS-109572']},
	async ({
		apiHelpers,
		page,
		roleAssigneesPage,
		rolePage,
		roleUserSelectorPage,
		rolesPage,
	}) => {
		test.setTimeout(120000);

		const companyId = await page.evaluate(() => {
			return Liferay.ThemeDisplay.getCompanyId();
		});

		const role = await apiHelpers.headlessAdminUser.postRole({
			name: getRandomString(),
			rolePermissions: [
				{
					actionIds: ['VIEW_CONTROL_PANEL'],
					primaryKey: companyId,
					resourceName: '90',
					scope: 1,
				},
				{
					actionIds: ['ACCESS_IN_CONTROL_PANEL'],
					primaryKey: companyId,
					resourceName:
						'com_liferay_user_groups_admin_web_portlet_UserGroupsAdminPortlet',
					scope: 1,
				},
			],
			roleType: 'regular',
		});

		const user = await apiHelpers.headlessAdminUser.postUserAccount();

		userData[user.alternateName] = {
			name: user.givenName,
			password: 'test',
			surname: user.familyName,
		};

		await rolesPage.goto();

		await rolesPage.rolesTable.changeView('Table');
		await page.waitForLoadState('networkidle');
		await rolesPage.rolesTable.search(role.name);
		await (await rolesPage.rolesTable.cellLink(role.name)).click();
		await rolePage.assigneesLink.click();

		await expect(
			roleAssigneesPage.assigneesTable.cell(user.name)
		).toHaveCount(0);

		await roleAssigneesPage.assigneesTable.newButton.click();

		await expect(
			roleUserSelectorPage.usersTable.cell(user.name)
		).toBeVisible();

		await roleUserSelectorPage.assignUsers([user.name]);

		await expect(
			roleAssigneesPage.assigneesTable.cell(user.name)
		).toBeVisible();

		await performLogout(page);
		await performLoginViaApi(page, user.alternateName);

		await expect(rolesPage.applicationsMenuButton).toBeVisible();

		await performLogout(page);
		await performLoginViaApi(page, 'test');

		await rolesPage.goto();

		await rolesPage.rolesTable.changeView('Table');
		await page.waitForLoadState('networkidle');
		await rolesPage.rolesTable.search(role.name);
		await (await rolesPage.rolesTable.cellLink(role.name)).click();
		await rolePage.assigneesLink.click();

		await expect(
			roleAssigneesPage.assigneesTable.cell(user.name)
		).toBeVisible();

		await (
			await roleAssigneesPage.assigneesTable.rowCheckbox(user.name)
		).check();
		await roleAssigneesPage.removeButton.click();

		await waitForAlert(page);

		await expect(
			roleAssigneesPage.assigneesTable.cell(user.name)
		).toHaveCount(0);

		await performLogout(page);
		await performLoginViaApi(page, user.alternateName);

		await expect(rolesPage.applicationsMenuButton).toHaveCount(0);
	}
);

test(
	'User Group can be assigned / unassigned from a regular role',
	{tag: ['@LPD-50065', '@LPS-109572']},
	async ({
		apiHelpers,
		page,
		roleAssigneesPage,
		rolePage,
		roleUserGroupSelectorPage,
		rolesPage,
	}) => {
		test.setTimeout(120000);

		const companyId = await page.evaluate(() => {
			return Liferay.ThemeDisplay.getCompanyId();
		});

		const role = await apiHelpers.headlessAdminUser.postRole({
			name: getRandomString(),
			rolePermissions: [
				{
					actionIds: ['VIEW_CONTROL_PANEL'],
					primaryKey: companyId,
					resourceName: '90',
					scope: 1,
				},
				{
					actionIds: ['ACCESS_IN_CONTROL_PANEL'],
					primaryKey: companyId,
					resourceName:
						'com_liferay_user_groups_admin_web_portlet_UserGroupsAdminPortlet',
					scope: 1,
				},
			],
			roleType: 'regular',
		});

		const userGroup = await apiHelpers.headlessAdminUser.postUserGroup();

		const user = await apiHelpers.headlessAdminUser.postUserAccount();

		userData[user.alternateName] = {
			name: user.givenName,
			password: 'test',
			surname: user.familyName,
		};

		await apiHelpers.headlessAdminUser.assignUsersToUserGroup(
			userGroup.id,
			[user.id]
		);

		await rolesPage.goto();

		await rolesPage.rolesTable.changeView('Table');
		await page.waitForLoadState('networkidle');
		await rolesPage.rolesTable.search(role.name);
		await (await rolesPage.rolesTable.cellLink(role.name)).click();
		await rolePage.assigneesLink.click();

		await expect(
			roleAssigneesPage.assigneesTable.cell(userGroup.name)
		).toHaveCount(0);

		await roleAssigneesPage.userGroupsLink.click();

		await expect(
			roleAssigneesPage.noDataMessage('user groups')
		).toBeVisible();
		await expect(
			roleAssigneesPage.assigneesTable.cell(userGroup.name)
		).toHaveCount(0);

		await roleAssigneesPage.assigneesTable.newButton.click();

		await expect(
			roleUserGroupSelectorPage.userGroupsTable.cell(userGroup.name)
		).toBeVisible();

		await roleUserGroupSelectorPage.assignUserGroups([userGroup.name]);

		await expect(
			roleAssigneesPage.assigneesTable.cell(userGroup.name)
		).toBeVisible();

		await performLogout(page);
		await performLoginViaApi(page, user.alternateName);

		await expect(rolesPage.applicationsMenuButton).toBeVisible();

		await performLogout(page);
		await performLoginViaApi(page, 'test');

		await rolesPage.goto();

		await rolesPage.rolesTable.changeView('Table');
		await page.waitForLoadState('networkidle');
		await rolesPage.rolesTable.search(role.name);
		await (await rolesPage.rolesTable.cellLink(role.name)).click();
		await rolePage.assigneesLink.click();
		await roleAssigneesPage.userGroupsLink.click();

		await expect(
			roleAssigneesPage.noDataMessage('user groups')
		).toHaveCount(0);
		await expect(
			roleAssigneesPage.assigneesTable.cell(userGroup.name)
		).toBeVisible();

		await (
			await roleAssigneesPage.assigneesTable.rowCheckbox(userGroup.name)
		).check();
		await roleAssigneesPage.removeButton.click();

		await waitForAlert(page);

		await expect(
			roleAssigneesPage.assigneesTable.cell(userGroup.name)
		).toHaveCount(0);

		await performLogout(page);
		await performLoginViaApi(page, user.alternateName);

		await expect(rolesPage.applicationsMenuButton).toHaveCount(0);
	}
);

test(
	'Organization can be assigned / unassigned from a regular role',
	{tag: ['@LPD-50065']},
	async ({
		apiHelpers,
		page,
		roleAssigneesPage,
		roleOrganizationSelectorPage,
		rolePage,
		rolesPage,
	}) => {
		test.setTimeout(120000);

		const companyId = await page.evaluate(() => {
			return Liferay.ThemeDisplay.getCompanyId();
		});

		const role = await apiHelpers.headlessAdminUser.postRole({
			name: getRandomString(),
			rolePermissions: [
				{
					actionIds: ['VIEW_CONTROL_PANEL'],
					primaryKey: companyId,
					resourceName: '90',
					scope: 1,
				},
				{
					actionIds: ['ACCESS_IN_CONTROL_PANEL'],
					primaryKey: companyId,
					resourceName:
						'com_liferay_user_groups_admin_web_portlet_UserGroupsAdminPortlet',
					scope: 1,
				},
			],
			roleType: 'regular',
		});

		const organization =
			await apiHelpers.headlessAdminUser.postOrganization();

		const user = await apiHelpers.headlessAdminUser.postUserAccount();

		userData[user.alternateName] = {
			name: user.givenName,
			password: 'test',
			surname: user.familyName,
		};

		await apiHelpers.headlessAdminUser.assignUserToOrganizationByEmailAddress(
			organization.id,
			user.emailAddress
		);

		await rolesPage.goto();

		await rolesPage.rolesTable.changeView('Table');
		await page.waitForLoadState('networkidle');
		await rolesPage.rolesTable.search(role.name);
		await (await rolesPage.rolesTable.cellLink(role.name)).click();
		await rolePage.assigneesLink.click();

		await expect(
			roleAssigneesPage.assigneesTable.cell(organization.name)
		).toHaveCount(0);

		await roleAssigneesPage.organizationsLink.click();

		await expect(
			roleAssigneesPage.noDataMessage('organizations')
		).toBeVisible();
		await expect(
			roleAssigneesPage.assigneesTable.cell(organization.name)
		).toHaveCount(0);

		await roleAssigneesPage.assigneesTable.newButton.click();

		await expect(
			roleOrganizationSelectorPage.organizationsTable.cell(
				organization.name
			)
		).toBeVisible();

		await roleOrganizationSelectorPage.assignOrganizations([
			organization.name,
		]);

		await expect(
			roleAssigneesPage.assigneesTable.cell(organization.name)
		).toBeVisible();

		await performLogout(page);
		await performLoginViaApi(page, user.alternateName);

		await expect(rolesPage.applicationsMenuButton).toBeVisible();

		await performLogout(page);
		await performLoginViaApi(page, 'test');

		await rolesPage.goto();

		await rolesPage.rolesTable.changeView('Table');
		await page.waitForLoadState('networkidle');
		await rolesPage.rolesTable.search(role.name);
		await (await rolesPage.rolesTable.cellLink(role.name)).click();
		await rolePage.assigneesLink.click();
		await roleAssigneesPage.organizationsLink.click();

		await expect(
			roleAssigneesPage.noDataMessage('organizations')
		).toHaveCount(0);
		await expect(
			roleAssigneesPage.assigneesTable.cell(organization.name)
		).toBeVisible();

		await (
			await roleAssigneesPage.assigneesTable.rowCheckbox(
				organization.name
			)
		).check();
		await roleAssigneesPage.removeButton.click();

		await waitForAlert(page);

		await expect(
			roleAssigneesPage.assigneesTable.cell(organization.name)
		).toHaveCount(0);

		await performLogout(page);
		await performLoginViaApi(page, user.alternateName);

		await expect(rolesPage.applicationsMenuButton).toHaveCount(0);
	}
);

test(
	'Segment can be assigned / unassigned from a regular role',
	{tag: ['@LPD-50065']},
	async ({
		apiHelpers,
		page,
		roleAssigneesPage,
		rolePage,
		roleSegmentSelectorPage,
		rolesPage,
	}) => {
		test.setTimeout(120000);

		const companyId = await page.evaluate(() => {
			return Liferay.ThemeDisplay.getCompanyId();
		});

		const globalSite = await apiHelpers.jsonWebServicesGroup.getGroupByKey(
			companyId,
			companyId
		);

		const role = await apiHelpers.headlessAdminUser.postRole({
			name: getRandomString(),
			roleType: 'regular',
		});

		const segmentsEntry =
			await apiHelpers.jsonWebServicesSegmentsEntry.addSegmentsEntry({
				criteria: {
					criteria: {
						user: {
							conjunction: 'and',
							filterString: `(emailAddress eq 'liferay.com')`,
							typeValue: 'model',
						},
					},
					filterString: {
						model: `(emailAddress eq 'liferay.com')`,
					},
				},
				groupId: globalSite.groupId,
				name: getRandomString(),
			});

		await rolesPage.goto();

		await rolesPage.rolesTable.changeView('Table');
		await page.waitForLoadState('networkidle');
		await rolesPage.rolesTable.search(role.name);
		await (await rolesPage.rolesTable.cellLink(role.name)).click();
		await rolePage.assigneesLink.click();

		await expect(
			roleAssigneesPage.assigneesTable.cell(
				segmentsEntry.nameCurrentValue
			)
		).toHaveCount(0);

		await roleAssigneesPage.segmentsLink.click();

		await expect(roleAssigneesPage.noDataMessage('segments')).toBeVisible();
		await expect(
			roleAssigneesPage.assigneesTable.cell(
				segmentsEntry.nameCurrentValue
			)
		).toHaveCount(0);

		await roleAssigneesPage.assigneesTable.newButton.click();

		await expect(
			roleSegmentSelectorPage.segmentsTable.cell(
				segmentsEntry.nameCurrentValue
			)
		).toBeVisible();

		await roleSegmentSelectorPage.assignSegments([
			segmentsEntry.nameCurrentValue,
		]);

		await expect(
			roleAssigneesPage.assigneesTable.cell(
				segmentsEntry.nameCurrentValue
			)
		).toBeVisible();

		await rolesPage.goto();

		await rolesPage.rolesTable.changeView('Table');
		await page.waitForLoadState('networkidle');
		await rolesPage.rolesTable.search(role.name);
		await (await rolesPage.rolesTable.cellLink(role.name)).click();
		await rolePage.assigneesLink.click();
		await roleAssigneesPage.segmentsLink.click();

		await expect(roleAssigneesPage.noDataMessage('segments')).toHaveCount(
			0
		);
		await expect(
			roleAssigneesPage.assigneesTable.cell(
				segmentsEntry.nameCurrentValue
			)
		).toBeVisible();

		await (
			await roleAssigneesPage.assigneesTable.rowCheckbox(
				segmentsEntry.nameCurrentValue
			)
		).check();
		await roleAssigneesPage.removeButton.click();

		await waitForAlert(page);

		await expect(
			roleAssigneesPage.assigneesTable.cell(
				segmentsEntry.nameCurrentValue
			)
		).toHaveCount(0);
	}
);

test(
	'Site can be assigned / unassigned from a regular role',
	{tag: ['@LPD-50065']},
	async ({
		apiHelpers,
		page,
		roleAssigneesPage,
		rolePage,
		roleSiteSelectorPage,
		rolesPage,
		site,
	}) => {
		test.setTimeout(120000);

		const companyId = await page.evaluate(() => {
			return Liferay.ThemeDisplay.getCompanyId();
		});

		const role = await apiHelpers.headlessAdminUser.postRole({
			name: getRandomString(),
			rolePermissions: [
				{
					actionIds: ['VIEW_CONTROL_PANEL'],
					primaryKey: companyId,
					resourceName: '90',
					scope: 1,
				},
				{
					actionIds: ['ACCESS_IN_CONTROL_PANEL'],
					primaryKey: companyId,
					resourceName:
						'com_liferay_user_groups_admin_web_portlet_UserGroupsAdminPortlet',
					scope: 1,
				},
			],
			roleType: 'regular',
		});

		const user = await apiHelpers.headlessAdminUser.postUserAccount();

		userData[user.alternateName] = {
			name: user.givenName,
			password: 'test',
			surname: user.familyName,
		};

		await apiHelpers.jsonWebServicesUser.addGroupUsers(site.id, [user.id]);

		await rolesPage.goto();

		await rolesPage.rolesTable.changeView('Table');
		await page.waitForLoadState('networkidle');
		await rolesPage.rolesTable.search(role.name);
		await (await rolesPage.rolesTable.cellLink(role.name)).click();
		await rolePage.assigneesLink.click();

		await expect(
			roleAssigneesPage.assigneesTable.cell(site.name)
		).toHaveCount(0);

		await roleAssigneesPage.sitesLink.click();

		await expect(roleAssigneesPage.noDataMessage('sites')).toBeVisible();
		await expect(
			roleAssigneesPage.assigneesTable.cell(site.name)
		).toHaveCount(0);

		await roleAssigneesPage.assigneesTable.newButton.click();

		await expect(
			roleSiteSelectorPage.sitesTable.cell(site.name)
		).toBeVisible();

		await roleSiteSelectorPage.assignSite([site.name]);

		await expect(
			roleAssigneesPage.assigneesTable.cell(site.name)
		).toBeVisible();

		await performLogout(page);
		await performLoginViaApi(page, user.alternateName);

		await expect(rolesPage.applicationsMenuButton).toBeVisible();

		await performLogout(page);
		await performLoginViaApi(page, 'test');

		await rolesPage.goto();

		await rolesPage.rolesTable.changeView('Table');
		await page.waitForLoadState('networkidle');
		await rolesPage.rolesTable.search(role.name);
		await (await rolesPage.rolesTable.cellLink(role.name)).click();
		await rolePage.assigneesLink.click();
		await roleAssigneesPage.sitesLink.click();

		await expect(roleAssigneesPage.noDataMessage('sites')).toHaveCount(0);
		await expect(
			roleAssigneesPage.assigneesTable.cell(site.name)
		).toBeVisible();

		await (
			await roleAssigneesPage.assigneesTable.rowCheckbox(site.name)
		).check();
		await roleAssigneesPage.removeButton.click();

		await waitForAlert(page);

		await expect(
			roleAssigneesPage.assigneesTable.cell(site.name)
		).toHaveCount(0);

		await performLogout(page);
		await performLoginViaApi(page, user.alternateName);

		await expect(rolesPage.applicationsMenuButton).toHaveCount(0);
	}
);

test(
	'Segments assignee tab is not available for Administrator role',
	{tag: ['@LPD-50065', '@LPS-202614']},
	async ({page, roleAssigneesPage, rolePage, rolesPage}) => {
		await rolesPage.goto();

		await rolesPage.rolesTable.changeView('Table');
		await page.waitForLoadState('networkidle');
		await rolesPage.rolesTable.search('Administrator');
		await (await rolesPage.rolesTable.cellLink('Administrator')).click();
		await rolePage.assigneesLink.click();

		await expect(roleAssigneesPage.noDataMessage('users')).toHaveCount(0);
		await expect(roleAssigneesPage.organizationsLink).toBeVisible();
		await expect(roleAssigneesPage.segmentsLink).toHaveCount(0);
		await expect(roleAssigneesPage.sitesLink).toBeVisible();
		await expect(roleAssigneesPage.userGroupsLink).toBeVisible();
		await expect(roleAssigneesPage.usersLink).toBeVisible();
	}
);
