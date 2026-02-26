/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {usersAndOrganizationsPagesTest} from '../../../fixtures/usersAndOrganizationsPagesTest';
import {EditUserPage} from '../../../pages/users-admin-web/EditUserPage';
import {UsersAndOrganizationsPage} from '../../../pages/users-admin-web/UsersAndOrganizationsPage';
import {getRandomInt} from '../../../utils/getRandomInt';

export const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-36105': {enabled: true},
	}),
	loginTest(),
	usersAndOrganizationsPagesTest
);

test(
	'User only sees the organizations they have permission to view',
	{tag: '@LPD-24824'},
	async ({apiHelpers, context, usersAndOrganizationsPage}) => {
		const organization1 =
			await apiHelpers.headlessAdminUser.postOrganization({
				name: 'Organization' + getRandomInt(),
			});
		const organization2 =
			await apiHelpers.headlessAdminUser.postOrganization({
				name: 'Organization' + getRandomInt(),
			});
		const user = await apiHelpers.headlessAdminUser.postUserAccount();
		await apiHelpers.headlessAdminUser.assignUserToOrganizationByEmailAddress(
			organization1.id,
			user.emailAddress
		);
		const organizationOwnerRole =
			await apiHelpers.headlessAdminUser.getRoleByName(
				'Organization Owner'
			);
		await apiHelpers.headlessAdminUser.assignUserToOrganizationRole(
			String(organizationOwnerRole.id),
			user.id,
			organization1.id
		);

		await usersAndOrganizationsPage.goToUsers();
		await (
			await usersAndOrganizationsPage.usersTableRowActions(
				`${user.alternateName}`
			)
		).click();

		const pagePromise = context.waitForEvent('page');
		await usersAndOrganizationsPage.impersonateUserMenuItem.click();

		const newPage = await pagePromise;
		const newPageUsersAndOrganizationsPage = new UsersAndOrganizationsPage(
			newPage
		);
		await newPageUsersAndOrganizationsPage.goToMyOrganizations();
		await (
			await newPageUsersAndOrganizationsPage.myOrganizationsTableRowLink(
				organization1.name
			)
		).click();
		await (
			await newPageUsersAndOrganizationsPage.organizationUsersTableRowLink(
				user.name
			)
		).click();

		const newPageEditUserPage = new EditUserPage(newPage);
		await newPageEditUserPage.organizationsLink.click();
		await newPageEditUserPage.myOrganizationsSelectOrganizationButton.click();

		await expect(
			newPageEditUserPage.myOrganizationsSelectOrganizationsTable.getByText(
				`${organization2.name}`
			)
		).toBeHidden();
		await expect(
			(
				await newPageEditUserPage.myOrganizationsSelectOrganizationsTableRow(
					1,
					organization1.name,
					true
				)
			).row
		).toBeVisible();
	}
);

test(
	'Test XSS vulnerability in path field in organization selector modal',
	{tag: '@LPD-71268'},
	async ({
		apiHelpers,
		editOrganizationPage,
		editUserPage,
		page,
		usersAndOrganizationsPage,
	}) => {
		const xssString = `AnyName<img src=x onerror="alert('xss')">`;

		const parentOrg = await apiHelpers.headlessAdminUser.postOrganization();
		await apiHelpers.headlessAdminUser.postOrganization({
			name: 'ChildOrg' + getRandomInt(),
			parentOrganization: parentOrg,
		});

		const user = await apiHelpers.headlessAdminUser.postUserAccount();

		await usersAndOrganizationsPage.goToOrganizations();

		await (
			await usersAndOrganizationsPage.organizationsTable.rowActions(
				parentOrg.name
			)
		).click();
		await usersAndOrganizationsPage.editOrganizationMenuItem.click();
		await editOrganizationPage.nameInput.fill(xssString);
		await editOrganizationPage.saveButton.click();

		await usersAndOrganizationsPage.goToUsers();

		await usersAndOrganizationsPage
			.usersTableCell(user.alternateName)
			.click();
		await editUserPage.organizationsLink.click();

		page.on('dialog', async (dialog) => {
			if (dialog.type() === 'alert') {
				throw new Error('XSS');
			}
		});

		await editUserPage.selectOrganizationsButton.click();

		await expect(
			editUserPage.selectOrganizationsTable.cell(xssString, false)
		).toBeVisible();
	}
);
