/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {usersAndOrganizationsPagesTest} from '../../../fixtures/usersAndOrganizationsPagesTest';
import {liferayConfig} from '../../../liferay.config';
import createUserWithPermissions from '../../../utils/createUserWithPermissions';
import {performUserSwitch, userData} from '../../../utils/performLogin';

const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-36105': {enabled: true},
	}),
	loginTest(),
	usersAndOrganizationsPagesTest
);

test(
	'Can add user with regular role permission',
	{tag: '@LPD-81993'},
	async ({apiHelpers, page, usersAndOrganizationsPage}) => {
		const companyId = await page.evaluate(() =>
			Liferay.ThemeDisplay.getCompanyId()
		);

		const role = await apiHelpers.headlessAdminUser.postRole({
			name: 'RegRole',
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
						'com_liferay_users_admin_web_portlet_UsersAdminPortlet',
					scope: 1,
				},
			],
		});

		const user = await apiHelpers.headlessAdminUser.postUserAccount();

		userData[user.alternateName] = {
			name: user.givenName,
			password: userData['test'].password,
			surname: user.familyName,
		};

		await apiHelpers.headlessAdminUser.assignUserToRole(
			role.externalReferenceCode,
			user.id
		);

		await test.step('Verify user cannot see Add button or organizations', async () => {
			await performUserSwitch(page, user.alternateName);

			await usersAndOrganizationsPage.goToOrganizationsWithLimitedAccess();

			await expect(
				page.getByText(
					'You do not belong to an organization and are not allowed to view other organizations.'
				)
			).toBeVisible();

			await expect(
				usersAndOrganizationsPage.addUserButton
			).not.toBeVisible();
		});

		await test.step('Grant ADD_USER permission and verify user can add users', async () => {
			await performUserSwitch(page, 'test');

			await apiHelpers.jsonWebServicesResourcePermissionApiHelper.addResourcePermission(
				'ADD_USER',
				companyId,
				'0',
				'90',
				companyId,
				String(role.id),
				'1'
			);

			await performUserSwitch(page, user.alternateName);

			await usersAndOrganizationsPage.globalMenuPage.goToControlPanel(
				'Users and Organizations'
			);

			await expect(usersAndOrganizationsPage.addUserButton).toBeVisible();
		});

		await test.step('Revoke ADD_USER permission and verify Add button is gone', async () => {
			await performUserSwitch(page, 'test');

			await apiHelpers.jsonWebServicesResourcePermissionApiHelper.removeResourcePermission(
				'ADD_USER',
				companyId,
				'0',
				'90',
				companyId,
				String(role.id),
				'1'
			);

			await performUserSwitch(page, user.alternateName);

			await usersAndOrganizationsPage.goToOrganizationsWithLimitedAccess();

			await expect(
				page.getByText(
					'You do not belong to an organization and are not allowed to view other organizations.'
				)
			).toBeVisible();

			await expect(
				usersAndOrganizationsPage.addUserButton
			).not.toBeVisible();
		});
	}
);

test(
	'Sites do not display for users with only update user permissions',
	{tag: ['@LPD-81993', '@LPS-134264']},
	async ({apiHelpers, editUserPage, page, usersAndOrganizationsPage}) => {
		const companyId = await page.evaluate(() =>
			Liferay.ThemeDisplay.getCompanyId()
		);

		const user1 = await createUserWithPermissions({
			apiHelpers,
			rolePermissions: [
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
		});
		const user2 = await apiHelpers.headlessAdminUser.postUserAccount();

		await performUserSwitch(page, user1.alternateName);

		await page.goto(
			`${liferayConfig.environment.baseUrl}/group/control_panel/manage?p_p_id=com_liferay_users_admin_web_portlet_UsersAdminPortlet`
		);
		await page.waitForLoadState('networkidle');

		await (
			await usersAndOrganizationsPage.usersTableRowLink(
				user2.alternateName
			)
		).click();
		await editUserPage.membershipsLink.click();
		await editUserPage.selectSiteButton.click();

		await expect(
			editUserPage.selectSiteFrame.getByText('No sites were found.')
		).toBeVisible();
	}
);

test(
	'User cannot edit own permissions',
	{tag: ['@LPD-81993', '@LPS-141243']},
	async ({apiHelpers, page, usersAndOrganizationsPage}) => {
		const companyId = await page.evaluate(() =>
			Liferay.ThemeDisplay.getCompanyId()
		);

		const user = await createUserWithPermissions({
			apiHelpers,
			rolePermissions: [
				{
					actionIds: ['VIEW'],
					primaryKey: companyId,
					resourceName: 'com.liferay.portal.kernel.model.User',
					scope: 1,
				},
				{
					actionIds: ['ACCESS_IN_CONTROL_PANEL'],
					primaryKey: companyId,
					resourceName:
						'com_liferay_users_admin_web_portlet_UsersAdminPortlet',
					scope: 1,
				},
			],
		});

		await performUserSwitch(page, user.alternateName);

		await page.goto(
			`${liferayConfig.environment.baseUrl}/group/control_panel/manage?p_p_id=com_liferay_users_admin_web_portlet_UsersAdminPortlet`
		);
		await page.waitForLoadState('networkidle');

		await usersAndOrganizationsPage.usersSearchBar.fill(user.alternateName);
		await usersAndOrganizationsPage.usersSearchBar.press('Enter');

		await expect(async () => {
			await (
				await usersAndOrganizationsPage.usersTableRowActions(
					user.alternateName
				)
			).click();

			await expect(
				page.getByRole('menuitem', {name: 'Permissions'})
			).not.toBeVisible({timeout: 500});
		}).toPass({timeout: 5000});
	}
);
