/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';
import {performUserSwitch, userData} from '../../utils/performLogin';
import {sitesAdminPagesTest} from './fixtures/sitesAdminPagesTest';

export const test = mergeTests(
	apiHelpersTest,
	loginTest(),
	sitesAdminPagesTest
);

let roleId: number;
let userId: number;

test.afterEach(async ({apiHelpers, page}) => {
	await performUserSwitch(page, 'test');

	if (roleId) {
		await apiHelpers.headlessAdminUser.deleteRole(roleId);
	}

	if (userId) {
		await apiHelpers.headlessAdminUser.deleteUserAccount(userId);
	}

	roleId = null;
	userId = null;
});

test('User can add site with Add Site permission', async ({
	apiHelpers,
	page,
	sitesAdminPage,
}) => {
	const user = await apiHelpers.headlessAdminUser.postUserAccount();

	userData[user.alternateName] = {
		name: user.givenName,
		password: 'test',
		surname: user.familyName,
	};

	userId = Number(user.id);

	const companyId = await page.evaluate(() => {
		return Liferay.ThemeDisplay.getCompanyId();
	});

	const role = await apiHelpers.headlessAdminUser.postRole({
		name: getRandomString(),
		rolePermissions: [
			{
				actionIds: ['ADD_COMMUNITY', 'VIEW_CONTROL_PANEL'],
				primaryKey: companyId,
				resourceName: '90',
				scope: 1,
			},
			{
				actionIds: ['ACCESS_IN_CONTROL_PANEL'],
				primaryKey: companyId,
				resourceName:
					'com_liferay_site_admin_web_portlet_SiteAdminPortlet',
				scope: 1,
			},
		],
	});

	roleId = role.id;

	await apiHelpers.headlessAdminUser.assignUserToRole(
		role.externalReferenceCode,
		user.id
	);

	await performUserSwitch(page, user.alternateName);

	await sitesAdminPage.goto();

	await page.getByRole('link', {name: 'Add Site'}).click();

	await expect(
		page.getByRole('heading', {name: 'Select Template'})
	).toBeVisible();
});

test('User cannot add site without Add Site permission', async ({
	apiHelpers,
	page,
	sitesAdminPage,
}) => {
	const user = await apiHelpers.headlessAdminUser.postUserAccount();

	userData[user.alternateName] = {
		name: user.givenName,
		password: 'test',
		surname: user.familyName,
	};

	userId = Number(user.id);

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
					'com_liferay_site_admin_web_portlet_SiteAdminPortlet',
				scope: 1,
			},
		],
	});

	roleId = role.id;

	await apiHelpers.headlessAdminUser.assignUserToRole(
		role.externalReferenceCode,
		user.id
	);

	await performUserSwitch(page, user.alternateName);

	await sitesAdminPage.goto();

	await expect(page.getByRole('link', {name: 'Add Site'})).not.toBeVisible();
});
