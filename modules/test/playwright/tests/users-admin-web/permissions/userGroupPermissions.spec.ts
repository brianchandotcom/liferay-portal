/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {rolesPagesTest} from '../../../fixtures/rolesPagesTest';
import {usersAndOrganizationsPagesTest} from '../../../fixtures/usersAndOrganizationsPagesTest';
import getRandomString from '../../../utils/getRandomString';
import {
	performLoginViaApi,
	performLogout,
	userData,
} from '../../../utils/performLogin';

export const test = mergeTests(
	dataApiHelpersTest,
	isolatedSiteTest,
	loginTest(),
	rolesPagesTest,
	usersAndOrganizationsPagesTest
);

test(
	'Can assign role to users of different sites',
	{tag: '@LPD-55670'},
	async ({
		apiHelpers,
		blogsPage,
		page,
		roleAssigneesPage,
		rolePage,
		roleUserGroupSelectorPage,
		rolesPage,
		site,
		usersAndOrganizationsPage,
	}) => {
		const organization =
			await apiHelpers.headlessAdminUser.postOrganization();

		const user1 = await apiHelpers.headlessAdminUser.postUserAccount();

		userData[user1.alternateName] = {
			name: user1.givenName,
			password: 'test',
			surname: user1.familyName,
		};

		const user2 = await apiHelpers.headlessAdminUser.postUserAccount();

		userData[user2.alternateName] = {
			name: user2.givenName,
			password: 'test',
			surname: user2.familyName,
		};

		const user3 = await apiHelpers.headlessAdminUser.postUserAccount();

		userData[user3.alternateName] = {
			name: user3.givenName,
			password: 'test',
			surname: user3.familyName,
		};

		const userGroup = await apiHelpers.headlessAdminUser.postUserGroup();

		await apiHelpers.headlessAdminUser.assignUsersToUserGroup(
			userGroup.id,
			[user1.id, user2.id, user3.id]
		);

		await apiHelpers.jsonWebServicesUser.assignUsersToSite(
			site.id,
			user1.id
		);
		await apiHelpers.jsonWebServicesUser.assignUsersToSite(
			site.id,
			user3.id
		);

		await apiHelpers.headlessAdminUser.assignUserToOrganizationByEmailAddress(
			organization.id,
			user2.emailAddress
		);
		await apiHelpers.headlessAdminUser.assignUserToOrganizationByEmailAddress(
			organization.id,
			user3.emailAddress
		);

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
					actionIds: ['ACCESS_IN_CONTROL_PANEL', 'VIEW'],
					primaryKey: companyId,
					resourceName:
						'com_liferay_users_admin_web_portlet_UsersAdminPortlet',
					scope: 1,
				},
				{
					actionIds: ['ACCESS_IN_CONTROL_PANEL', 'VIEW'],
					primaryKey: companyId,
					resourceName:
						'com_liferay_blogs_web_portlet_BlogsAdminPortlet',
					scope: 1,
				},
				{
					actionIds: ['ADD_ENTRY'],
					primaryKey: companyId,
					resourceName: 'com.liferay.blogs',
					scope: 1,
				},
				{
					actionIds: ['VIEW'],
					primaryKey: companyId,
					resourceName: 'com.liferay.blogs.model.BlogsEntry',
					scope: 1,
				},
			],
			roleType: 'regular',
		});

		await rolesPage.goto();
		await rolesPage.rolesTable.changeView('Table');
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
		await performLoginViaApi({
			page,
			screenName: user1.alternateName,
		});

		const blog = await apiHelpers.headlessDelivery.postBlog(site.id);

		await usersAndOrganizationsPage.goToOrganizationsWithLimitedAccess();

		await expect(
			usersAndOrganizationsPage.noPermissionMessage
		).toBeVisible();

		await performLogout(page);
		await performLoginViaApi({
			page,
			screenName: user2.alternateName,
		});

		await usersAndOrganizationsPage.goToOrganizationsWithLimitedAccess();

		await expect(
			usersAndOrganizationsPage.organizationsTable.cell(organization.name)
		).toBeVisible();
		await expect(
			await usersAndOrganizationsPage.organizationsTable.rowActions(
				organization.name
			)
		).not.toBeVisible();

		await blogsPage.goto(site.friendlyUrlPath);

		await expect(blogsPage.blogTitle(blog.headline)).toBeVisible();

		await performLogout(page);
		await performLoginViaApi({
			page,
			screenName: user3.alternateName,
		});

		await usersAndOrganizationsPage.goToOrganizationsWithLimitedAccess();

		await expect(
			usersAndOrganizationsPage.organizationsTable.cell(organization.name)
		).toBeVisible();
		await expect(
			await usersAndOrganizationsPage.organizationsTable.rowActions(
				organization.name
			)
		).not.toBeVisible();

		await blogsPage.goto(site.friendlyUrlPath);

		await expect(blogsPage.blogTitle(blog.headline)).toBeVisible();
	}
);
