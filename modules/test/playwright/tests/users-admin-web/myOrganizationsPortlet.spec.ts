/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {usersAndOrganizationsPagesTest} from '../../fixtures/usersAndOrganizationsPagesTest';
import {UsersAndOrganizationsPage} from '../../pages/users-admin-web/UsersAndOrganizationsPage';

export const test = mergeTests(
	dataApiHelpersTest,
	loginTest(),
	usersAndOrganizationsPagesTest
);

test('LPD-35306 Breadcrumb in My Organizations does not have a link if user does not have view permission of the Organization', async ({
	apiHelpers,
	context,
	usersAndOrganizationsPage,
}) => {
	const organization = await apiHelpers.headlessAdminUser.postOrganization();
	const organization2 = await apiHelpers.headlessAdminUser.postOrganization({
		parentOrganization: {
			externalReferenceCode: organization.externalReferenceCode,
		},
	});

	const user = await apiHelpers.headlessAdminUser.postUserAccount();

	await apiHelpers.headlessAdminUser.assignUserToOrganizationByEmailAddress(
		organization2.id,
		user.emailAddress
	);

	const role = await apiHelpers.headlessAdminUser.getRoleByName(
		'Organization Administrator'
	);

	await apiHelpers.headlessAdminUser.assignUserToOrganizationRole(
		String(role.id),
		user.id,
		organization2.id
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
			organization2.name
		)
	).click();

	await expect(
		await newPageUsersAndOrganizationsPage.myOrganizationsBreadCrumbLink(
			organization.name
		)
	).toHaveCount(0);
});
