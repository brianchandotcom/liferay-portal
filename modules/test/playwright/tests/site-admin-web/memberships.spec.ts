/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {membershipsPagesTest} from '../site-admin-web/fixtures/membershipsPagesTest';

export const test = mergeTests(
	apiHelpersTest,
	loginTest(),
	membershipsPagesTest
);

test(
	'Confirm search bar does not display for membership requests',
	{
		tag: '@LPD-36275',
	},
	async ({membershipsPage, page}) => {
		await membershipsPage.goto();

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('menuitem', {
				name: 'View Membership Requests',
			}),
			trigger: page.getByLabel('Options', {exact: true}),
		});

		await expect(page.getByPlaceholder('Search for')).not.toBeVisible();
	}
);

test(
	'Bulk removal of roles from users',
	{
		tag: '@LPD-41737',
	},
	async ({apiHelpers, membershipsPage, page}) => {
		const user = await apiHelpers.headlessAdminUser.postUserAccount();

		const siteId = await page.evaluate(() => {
			return String(Liferay.ThemeDisplay.getSiteGroupId());
		});

		const siteRole =
			await apiHelpers.headlessAdminUser.getRoleByName('Site Member');

		await apiHelpers.headlessAdminUser.assignUserToSite(
			siteRole.id,
			siteId,
			user.id
		);

		await membershipsPage.goto();
		await membershipsPage.assignSiteAdministratorRole();
		await membershipsPage.filterBySiteAdministratorRole();
		await membershipsPage.removeSiteAdministratorRole();

		await expect(
			page.getByText(
				'No user was found that is a direct member of this site.'
			)
		).toBeVisible();

		await page.getByLabel('Remove Site Administrator').click();

		await expect(page.getByText(user.name)).toBeVisible();

		await apiHelpers.headlessAdminUser.deleteUserAccount(Number(user.id));
	}
);

test(
	'Bulk removal of roles from user groups',
	{
		tag: '@LPD-41737',
	},
	async ({apiHelpers, membershipsPage, page}) => {
		const userGroup1 = await apiHelpers.headlessAdminUser.postUserGroup();
		const userGroup2 = await apiHelpers.headlessAdminUser.postUserGroup();

		await membershipsPage.goto();

		await page.getByRole('link', {name: 'User Groups'}).click();

		await page.getByRole('button', {name: 'Add'}).click();

		await page
			.frameLocator('iframe[title="Assign User Groups to This Site"]')
			.getByLabel('Select All Items on the Page')
			.click();

		await page.getByRole('button', {name: 'Done'}).click();

		await page.waitForTimeout(500);

		await membershipsPage.assignSiteAdministratorRole();
		await membershipsPage.filterBySiteAdministratorRole();
		await membershipsPage.removeSiteAdministratorRole();

		await expect(
			page.getByText(
				' No user group was found that is a member of this site.'
			)
		).toBeVisible();

		await page.getByLabel('Remove Site Administrator').click();

		await expect(page.getByText(userGroup1.name)).toBeVisible();
		await expect(page.getByText(userGroup2.name)).toBeVisible();

		await apiHelpers.headlessAdminUser.deleteUserGroup(
			Number(userGroup1.id)
		);
		await apiHelpers.headlessAdminUser.deleteUserGroup(
			Number(userGroup2.id)
		);
	}
);

test(
	'Filter by roles shows cards as selectable',
	{
		tag: '@LPD-41741',
	},
	async ({membershipsPage, page}) => {
		await membershipsPage.goto();

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('menuitem', {name: 'Roles'}),
			timeout: 500,
			trigger: page.getByLabel('Filter'),
		});

		await expect(
			page
				.frameLocator('iframe[title="Select Role"]')
				.locator('.card-interactive')
				.first()
		).toBeVisible();
	}
);

test(
	'Confirm roles are unassign from users tab',
	{
		tag: '@LPD-42500',
	},
	async ({apiHelpers, membershipsPage, page}) => {
		const userAccount =
			await apiHelpers.headlessAdminUser.postUserAccount();

		await membershipsPage.goto();

		await page.waitForTimeout(500);

		await membershipsPage.assignAllUsersSiteMembership();

		await membershipsPage.assignAllRolesToUser(userAccount.alternateName);

		await membershipsPage.unassignAllRolesFromUser(
			userAccount.alternateName
		);

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('menuitem', {name: 'Unassign Roles'}),
			timeout: 500,
			trigger: page
				.locator(
					'[id="_com_liferay_site_memberships_web_portlet_SiteMembershipsPortlet_users_' +
						userAccount.alternateName +
						'"]'
				)
				.getByLabel('More actions'),
		});

		await expect(
			page
				.frameLocator('iframe[title="Unassign Roles"]')
				.locator(
					'[id="_com_liferay_site_memberships_web_portlet_SiteMembershipsPortlet_userGroupRoleRole_1"] label div'
				)
				.first()
		).not.toBeVisible();

		await expect(
			page
				.frameLocator('iframe[title="Unassign Roles"]')
				.locator(
					'[id="_com_liferay_site_memberships_web_portlet_SiteMembershipsPortlet_userGroupRoleRole_2"] label div'
				)
				.first()
		).not.toBeVisible();

		await expect(
			page
				.frameLocator('iframe[title="Unassign Roles"]')
				.locator(
					'[id="_com_liferay_site_memberships_web_portlet_SiteMembershipsPortlet_userGroupRoleRole_3"] label div'
				)
				.first()
		).not.toBeVisible();

		await apiHelpers.headlessAdminUser.deleteUserAccount(
			Number(userAccount.id)
		);
	}
);
