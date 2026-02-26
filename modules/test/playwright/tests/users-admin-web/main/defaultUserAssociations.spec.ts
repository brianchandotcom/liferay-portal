/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {instanceSettingsPagesTest} from '../../../fixtures/instanceSettingsPagesTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {usersAndOrganizationsPagesTest} from '../../../fixtures/usersAndOrganizationsPagesTest';
import getRandomString from '../../../utils/getRandomString';
import {waitForAlert} from '../../../utils/waitForAlert';

const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-36105': {enabled: true},
	}),
	instanceSettingsPagesTest,
	isolatedSiteTest,
	loginTest(),
	usersAndOrganizationsPagesTest
);

test('LPD-30006 Configure default userGroup associations', async ({
	apiHelpers,
	defaultUserAssociationsPage,
	editUserPage,
	page,
	usersAndOrganizationsPage,
}) => {
	const userGroup = await apiHelpers.headlessAdminUser.postUserGroup();

	await defaultUserAssociationsPage.goto();

	await defaultUserAssociationsPage.userGroupsInput.fill(userGroup.name);
	await defaultUserAssociationsPage.saveButton.click();

	await waitForAlert(page);

	try {
		const user = await apiHelpers.headlessAdminUser.postUserAccount();

		await usersAndOrganizationsPage.goToUsers();

		await (
			await usersAndOrganizationsPage.usersTableRowLink(
				user.alternateName
			)
		).click();

		await editUserPage.membershipsLink.click();

		await expect(
			(
				await editUserPage.membershipsUserGroupsTableRow(
					0,
					userGroup.name,
					true
				)
			).row
		).toBeVisible();
	}
	finally {
		await defaultUserAssociationsPage.resetFields();
	}
});

test(
	'Configure default user associations for organization sites, roles, and sites',
	{tag: '@LPD-81993'},
	async ({
		apiHelpers,
		defaultUserAssociationsPage,
		editUserPage,
		page,
		site,
		usersAndOrganizationsPage,
	}) => {
		const organization =
			await apiHelpers.headlessAdminUser.postOrganization();

		const role = await apiHelpers.headlessAdminUser.postRole({
			name: `Role${getRandomString()}`,
		});

		await test.step('Set default user associations for organization site, role, and site', async () => {
			await defaultUserAssociationsPage.goto();

			await defaultUserAssociationsPage.organizationSitesInput.fill(
				organization.name
			);
			await defaultUserAssociationsPage.regularRolesInput.fill(role.name);
			await defaultUserAssociationsPage.sitesInput.fill(site.name);
			await defaultUserAssociationsPage.saveButton.click();

			await waitForAlert(page);
		});

		try {
			await test.step('Verify new user has all default associations', async () => {
				const user =
					await apiHelpers.headlessAdminUser.postUserAccount();

				await usersAndOrganizationsPage.goToUsers();

				await (
					await usersAndOrganizationsPage.usersTableRowLink(
						user.alternateName
					)
				).click();

				await editUserPage.membershipsLink.click();

				await expect(
					await editUserPage.membershipsSiteTableCell(site.name)
				).toBeVisible();

				await editUserPage.rolesLink.click();

				await expect(
					editUserPage.regularRoleCell(role.name)
				).toBeVisible();
			});
		}
		finally {
			await defaultUserAssociationsPage.resetFields();
		}
	}
);

test(
	'Organization site default user associations',
	{tag: '@LPD-81993'},
	async ({
		apiHelpers,
		defaultUserAssociationsPage,
		editOrganizationPage,
		editUserPage,
		page,
		site,
		usersAndOrganizationsPage,
	}) => {
		const organization =
			await apiHelpers.headlessAdminUser.postOrganization({
				name: `Org${getRandomString()}`,
			});

		try {
			await usersAndOrganizationsPage.goToOrganizations();

			await expect(async () => {
				await (
					await usersAndOrganizationsPage.organizationsTable.rowActions(
						organization.name
					)
				).click();

				await expect(
					usersAndOrganizationsPage.editOrganizationMenuItem
				).toBeVisible({timeout: 500});
			}).toPass({timeout: 5000});

			await usersAndOrganizationsPage.editOrganizationMenuItem.click();

			await editOrganizationPage.organizationSiteLink.click();
			await editOrganizationPage.createSiteToggle.check();
			await editOrganizationPage.saveButton.click();

			await waitForAlert(page);

			await defaultUserAssociationsPage.goto();

			await defaultUserAssociationsPage.sitesInput.fill(site.name);
			await defaultUserAssociationsPage.organizationSitesInput.fill(
				organization.name
			);
			await defaultUserAssociationsPage.saveButton.click();

			await waitForAlert(page);

			const user = await apiHelpers.headlessAdminUser.postUserAccount();

			await usersAndOrganizationsPage.goToUsers();

			await (
				await usersAndOrganizationsPage.usersTableRowLink(
					user.alternateName
				)
			).click();

			await editUserPage.membershipsLink.click();

			await expect(
				await editUserPage.membershipsSiteTableCell(site.name)
			).toBeVisible();
			await expect(
				await editUserPage.membershipsSiteTableCell(organization.name)
			).toBeVisible();
		}
		finally {
			await defaultUserAssociationsPage.resetFields();
		}
	}
);
