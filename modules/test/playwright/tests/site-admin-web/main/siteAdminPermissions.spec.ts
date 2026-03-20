/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';
import {TRole} from '../../../helpers/HeadlessAdminUserApiHelper';
import getRandomString from '../../../utils/getRandomString';
import {
	performLoginViaApi,
	performUserSwitch,
	userData,
} from '../../../utils/performLogin';
import {sitesAdminPagesTest} from './fixtures/sitesAdminPagesTest';

export const test = mergeTests(
	dataApiHelpersTest,
	loginTest(),
	sitesAdminPagesTest
);

let childSite: Site;
let role: TRole;
let site: Site;
let user: TUserAccount;

test.afterEach(async ({page}) => {
	await performLoginViaApi({page, screenName: 'test'});

	role = null;
	childSite = null;
	site = null;
	user = null;
});

test('User can add site with Add Site permission', async ({
	apiHelpers,
	page,
	sitesAdminPage,
}) => {
	user = await apiHelpers.headlessAdminUser.postUserAccount();

	userData[user.alternateName] = {
		name: user.givenName,
		password: 'test',
		surname: user.familyName,
	};

	const companyId = await page.evaluate(() => {
		return Liferay.ThemeDisplay.getCompanyId();
	});

	role = await apiHelpers.headlessAdminUser.postRole({
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
	user = await apiHelpers.headlessAdminUser.postUserAccount();

	userData[user.alternateName] = {
		name: user.givenName,
		password: 'test',
		surname: user.familyName,
	};

	const companyId = await page.evaluate(() => {
		return Liferay.ThemeDisplay.getCompanyId();
	});

	role = await apiHelpers.headlessAdminUser.postRole({
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

	await apiHelpers.headlessAdminUser.assignUserToRole(
		role.externalReferenceCode,
		user.id
	);

	await performUserSwitch(page, user.alternateName);

	await sitesAdminPage.goto();

	await expect(page.getByRole('link', {name: 'Add Site'})).not.toBeVisible();
});

test('User can view site when a member of the site', async ({
	apiHelpers,
	page,
	sitesAdminPage,
}) => {
	user = await apiHelpers.headlessAdminUser.postUserAccount();

	userData[user.alternateName] = {
		name: user.givenName,
		password: 'test',
		surname: user.familyName,
	};

	const companyId = await page.evaluate(() => {
		return Liferay.ThemeDisplay.getCompanyId();
	});

	role = await apiHelpers.headlessAdminUser.postRole({
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

	await apiHelpers.headlessAdminUser.assignUserToRole(
		role.externalReferenceCode,
		user.id
	);

	site = await apiHelpers.headlessAdminSite.postSite({
		name: getRandomString(),
	});

	const siteMemberRole =
		await apiHelpers.headlessAdminUser.getRoleByName('Site Member');

	await apiHelpers.headlessAdminUser.assignUserToSite(
		siteMemberRole.id,
		site.id,
		user.id
	);

	await performUserSwitch(page, user.alternateName);

	await sitesAdminPage.goto();

	await expect(page.getByRole('link', {name: site.name})).toBeVisible();

	await sitesAdminPage.searchSite(site.name);

	await expect(
		page.getByText(`1 Result Found for "${site.name}"`)
	).toBeVisible();

	await sitesAdminPage.assertActions(
		site.name,
		['Leave Site'],
		[
			'Add Child Site',
			'Deactivate',
			'Delete',
			'Go to Pages',
			'Go to Site Settings',
		]
	);
});

test('User can manage child site with Manage Subsites permission', async ({
	apiHelpers,
	page,
	sitesAdminPage,
}) => {
	user = await apiHelpers.headlessAdminUser.postUserAccount();

	userData[user.alternateName] = {
		name: user.givenName,
		password: 'test',
		surname: user.familyName,
	};

	const companyId = await page.evaluate(() => {
		return Liferay.ThemeDisplay.getCompanyId();
	});

	role = await apiHelpers.headlessAdminUser.postRole({
		name: getRandomString(),
		rolePermissions: [
			{
				actionIds: ['VIEW_CONTROL_PANEL'],
				primaryKey: companyId,
				resourceName: '90',
				scope: 1,
			},
			{
				actionIds: ['MANAGE_SUBGROUPS'],
				primaryKey: companyId,
				resourceName: 'com.liferay.portal.kernel.model.Group',
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

	await apiHelpers.headlessAdminUser.assignUserToRole(
		role.externalReferenceCode,
		user.id
	);

	site = await apiHelpers.headlessAdminSite.postSite({
		name: getRandomString(),
	});

	childSite = await apiHelpers.headlessAdminSite.postSite({
		name: getRandomString(),
		parentSiteExternalReferenceCode: site.externalReferenceCode,
	});

	const siteMemberRole =
		await apiHelpers.headlessAdminUser.getRoleByName('Site Member');

	await apiHelpers.headlessAdminUser.assignUserToSite(
		siteMemberRole.id,
		site.id,
		user.id
	);

	await apiHelpers.headlessAdminUser.assignUserToSite(
		siteMemberRole.id,
		childSite.id,
		user.id
	);

	await performUserSwitch(page, user.alternateName);

	await sitesAdminPage.goto();

	await sitesAdminPage.assertActions(
		site.name,
		['Leave Site'],
		[
			'Add Child Site',
			'Deactivate',
			'Delete',
			'Go to Pages',
			'Go to Site Settings',
		]
	);

	await sitesAdminPage.assertActions(
		childSite.name,
		[
			'Add Child Site',
			'Deactivate',
			'Delete',
			'Go to Site Settings',
			'Leave Site',
		],
		['Go to Pages']
	);
});

test('User can go to site pages with Manage Pages permission', async ({
	apiHelpers,
	page,
	sitesAdminPage,
}) => {
	user = await apiHelpers.headlessAdminUser.postUserAccount();

	userData[user.alternateName] = {
		name: user.givenName,
		password: 'test',
		surname: user.familyName,
	};

	const companyId = await page.evaluate(() => {
		return Liferay.ThemeDisplay.getCompanyId();
	});

	role = await apiHelpers.headlessAdminUser.postRole({
		name: getRandomString(),
		rolePermissions: [
			{
				actionIds: ['VIEW_CONTROL_PANEL'],
				primaryKey: companyId,
				resourceName: '90',
				scope: 1,
			},
			{
				actionIds: ['MANAGE_LAYOUTS'],
				primaryKey: companyId,
				resourceName: 'com.liferay.portal.kernel.model.Group',
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

	await apiHelpers.headlessAdminUser.assignUserToRole(
		role.externalReferenceCode,
		user.id
	);

	site = await apiHelpers.headlessAdminSite.postSite({
		name: getRandomString(),
	});

	await apiHelpers.jsonWebServicesLayout.addLayout({
		groupId: site.id,
		title: getRandomString(),
	});

	const siteMemberRole =
		await apiHelpers.headlessAdminUser.getRoleByName('Site Member');

	await apiHelpers.headlessAdminUser.assignUserToSite(
		siteMemberRole.id,
		site.id,
		user.id
	);

	await performUserSwitch(page, user.alternateName);

	await sitesAdminPage.goto();

	await sitesAdminPage.assertActions(
		site.name,
		['Go to Pages', 'Leave Site'],
		['Add Child Site', 'Deactivate', 'Delete', 'Go to Site Settings']
	);
});
