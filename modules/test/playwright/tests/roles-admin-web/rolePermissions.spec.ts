/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {rolesPagesTest} from '../../fixtures/rolesPagesTest';
import {usersAndOrganizationsPagesTest} from '../../fixtures/usersAndOrganizationsPagesTest';
import getRandomString from '../../utils/getRandomString';
import {
	performLoginViaApi,
	performLogout,
	userData,
} from '../../utils/performLogin';
import {waitForAlert} from '../../utils/waitForAlert';

export const test = mergeTests(
	dataApiHelpersTest,
	loginTest(),
	rolesPagesTest,
	usersAndOrganizationsPagesTest
);

test(
	'A user with Preview in Device permission can view the simulation button',
	{tag: ['@LPD-50352']},
	async ({apiHelpers, page}) => {
		const user = await apiHelpers.headlessAdminUser.postUserAccount();

		userData[user.alternateName] = {
			name: user.givenName,
			password: 'test',
			surname: user.familyName,
		};

		await performLogout(page);
		await performLoginViaApi(page, user.alternateName);

		await expect(page.getByTestId('simulation')).toHaveCount(0);

		await performLogout(page);
		await performLoginViaApi(page, 'test');

		const role = await apiHelpers.headlessAdminUser.postRole({
			name: getRandomString(),
			rolePermissions: [
				{
					actionIds: ['PREVIEW_IN_DEVICE'],
					primaryKey: await page.evaluate(() => {
						return Liferay.ThemeDisplay.getCompanyId();
					}),
					resourceName: 'com.liferay.portal.kernel.model.Group',
					scope: 1,
				},
			],
			roleType: 'regular',
		});

		await apiHelpers.headlessAdminUser.assignUserToRole(
			role.externalReferenceCode,
			user.id
		);

		await performLogout(page);
		await performLoginViaApi(page, user.alternateName);

		await expect(page.getByTestId('simulation')).toBeVisible();

		await page.getByTestId('simulation').click();

		await expect(page.getByLabel('Mobile')).toBeVisible();
	}
);

test(
	'A user with View and Add Role permissions can add different kinds of roles',
	{tag: ['@LPD-50352']},
	async ({apiHelpers, page, rolePage, rolesPage}) => {
		const companyId = await page.evaluate(() => {
			return Liferay.ThemeDisplay.getCompanyId();
		});

		const role1 = await apiHelpers.headlessAdminUser.postRole({
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
						'com_liferay_roles_admin_web_portlet_RolesAdminPortlet',
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

		await apiHelpers.headlessAdminUser.assignUserToRole(
			role1.externalReferenceCode,
			user.id
		);

		await performLogout(page);
		await performLoginViaApi(page, user.alternateName);

		await rolesPage.goto(false);

		await expect(async () => {
			await rolesPage.rolesTable.changeView('Table');

			await expect(rolesPage.rolesTable.cell('Title')).toBeVisible({
				timeout: 300,
			});
		}).toPass();

		await expect(rolesPage.rolesTable.searchInput).toBeEditable();
		await expect(rolesPage.rolesTable.newButton).toHaveCount(0);

		await rolesPage.organizationRolesLink.click();

		await expect(rolesPage.rolesTable.searchInput).toBeEditable();
		await expect(rolesPage.rolesTable.newButton).toHaveCount(0);

		await rolesPage.siteRolesLink.click();

		await expect(rolesPage.rolesTable.searchInput).toBeEditable();
		await expect(rolesPage.rolesTable.newButton).toHaveCount(0);

		await performLogout(page);
		await performLoginViaApi(page, 'test');

		const role2 = await apiHelpers.headlessAdminUser.postRole({
			name: getRandomString(),
			rolePermissions: [
				{
					actionIds: ['ADD_ROLE'],
					primaryKey: companyId,
					resourceName: '90',
					scope: 1,
				},
				{
					actionIds: ['VIEW'],
					primaryKey: companyId,
					resourceName: 'com.liferay.portal.kernel.model.Role',
					scope: 1,
				},
			],
			roleType: 'regular',
		});

		await apiHelpers.headlessAdminUser.assignUserToRole(
			role2.externalReferenceCode,
			user.id
		);

		await performLogout(page);
		await performLoginViaApi(page, user.alternateName);

		await rolesPage.goto(false);

		await expect(rolesPage.rolesTable.newButton).toBeVisible();
		await expect(rolesPage.rolesTable.searchInput).toBeEditable();

		let newRoleName = getRandomString();

		await rolesPage.rolesTable.newButton.click();
		await rolePage.addRole(apiHelpers, {name: newRoleName});
		await rolePage.backButton.click();

		await expect(rolesPage.rolesTable.cell(newRoleName)).toBeVisible();

		await rolesPage.organizationRolesLink.click();

		await expect(rolesPage.rolesTable.cell(newRoleName)).toHaveCount(0);
		await expect(rolesPage.rolesTable.newButton).toBeVisible();
		await expect(rolesPage.rolesTable.searchInput).toBeEditable();

		newRoleName = getRandomString();

		await rolesPage.rolesTable.newButton.click();
		await rolePage.addRole(apiHelpers, {name: newRoleName});
		await rolePage.backButton.click();

		await expect(rolesPage.rolesTable.cell(newRoleName)).toBeVisible();

		await rolesPage.siteRolesLink.click();

		await expect(rolesPage.rolesTable.cell(newRoleName)).toHaveCount(0);
		await expect(rolesPage.rolesTable.newButton).toBeVisible();
		await expect(rolesPage.rolesTable.searchInput).toBeEditable();

		newRoleName = getRandomString();

		await rolesPage.rolesTable.newButton.click();
		await rolePage.addRole(apiHelpers, {name: newRoleName});
		await rolePage.backButton.click();

		await expect(rolesPage.rolesTable.cell(newRoleName)).toBeVisible();
	}
);

test(
	'Without the View permission on Administrator role you still can assign new roles',
	{tag: ['@LPD-50352', '@LPS-144566']},
	async ({apiHelpers, editUserPage, page, usersAndOrganizationsPage}) => {
		const companyId = await page.evaluate(() => {
			return Liferay.ThemeDisplay.getCompanyId();
		});

		const role1 = await apiHelpers.headlessAdminUser.postRole({
			name: getRandomString(),
			rolePermissions: [
				{
					actionIds: ['VIEW_CONTROL_PANEL'],
					primaryKey: companyId,
					resourceName: '90',
					scope: 1,
				},
				{
					actionIds: ['ACCESS_IN_CONTROL_PANEL', 'VIEW'],
					primaryKey: companyId,
					resourceName:
						'com_liferay_users_admin_web_portlet_UsersAdminPortlet',
					scope: 1,
				},
				{
					actionIds: ['UPDATE', 'VIEW'],
					primaryKey: companyId,
					resourceName: 'com.liferay.portal.kernel.model.User',
					scope: 1,
				},
			],
			roleType: 'regular',
		});

		const role2 = await apiHelpers.headlessAdminUser.postRole({
			name: getRandomString(),
			roleType: 'regular',
		});

		await apiHelpers.jsonWebServicesResourcePermissionApiHelper.setIndividualResourcePermissions(
			['ASSIGN_MEMBERS', 'VIEW'],
			companyId,
			'0',
			'com.liferay.portal.kernel.model.Role',
			String(role2.id),
			String(role1.id)
		);

		const administratorRole =
			await apiHelpers.headlessAdminUser.getRoleByName('Administrator');
		const userRole =
			await apiHelpers.headlessAdminUser.getRoleByName('User');

		try {
			await apiHelpers.jsonWebServicesResourcePermissionApiHelper.setIndividualResourcePermissions(
				[],
				companyId,
				'0',
				'com.liferay.portal.kernel.model.Role',
				String(administratorRole.id),
				String(userRole.id)
			);

			const user1 = await apiHelpers.headlessAdminUser.postUserAccount();

			userData[user1.alternateName] = {
				name: user1.givenName,
				password: 'test',
				surname: user1.familyName,
			};

			await apiHelpers.headlessAdminUser.assignUserToRole(
				role1.externalReferenceCode,
				user1.id
			);

			const user2 = await apiHelpers.headlessAdminUser.postUserAccount();

			await performLogout(page);
			await performLoginViaApi(page, user1.alternateName);

			await usersAndOrganizationsPage.goToUsersWithLimitedAccess();

			await (
				await usersAndOrganizationsPage.usersTableRowLink(
					user2.alternateName
				)
			).click();

			await editUserPage.rolesLink.click();
			await editUserPage.selectRegularRolesButton.click();

			await expect(
				editUserPage.selectRegularRolesSearchInput
			).toBeEnabled();

			await editUserPage
				.selectRegularRolesChooseButton(role2.name)
				.click();

			await expect(
				editUserPage.regularRoleCell(role2.name)
			).toBeVisible();

			await editUserPage.saveButton.click();

			await waitForAlert(page);
		}
		finally {
			await performLogout(page);
			await performLoginViaApi(page, 'test');

			await apiHelpers.jsonWebServicesResourcePermissionApiHelper.setIndividualResourcePermissions(
				['VIEW'],
				companyId,
				'0',
				'com.liferay.portal.kernel.model.Role',
				String(administratorRole.id),
				String(userRole.id)
			);
		}
	}
);
