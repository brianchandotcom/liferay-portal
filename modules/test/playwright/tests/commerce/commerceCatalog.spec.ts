/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {commercePagesTest} from '../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {usersAndOrganizationsPagesTest} from '../../fixtures/usersAndOrganizationsPagesTest';
import getRandomString from '../../utils/getRandomString';
import performLogin, {performLogout} from '../../utils/performLogin';
import {miniumSetUp} from './utils/commerce';

export const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	commercePagesTest,
	dataApiHelpersTest,
	loginTest(),
	usersAndOrganizationsPagesTest
);

test('LPD-3185 Search a catalog entry using global search, click on a suggested entry and get redirected to that product details page', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceCatalogPage,
	commerceLayoutsPage,
	productDetailsPage,
}) => {
	const site = await apiHelpers.headlessSite.createSite({
		name: 'Minium',
		templateKey: 'minium-initializer',
		templateType: 'site-initializer',
	});

	apiHelpers.data.push({id: site.id, type: 'site'});

	await commerceLayoutsPage.cleanupSiteInitializerData(apiHelpers, site.name);

	await applicationsMenuPage.goToSite(site.name);

	await commerceCatalogPage.focusGlobalSearchBarInput();
	await commerceCatalogPage.search('A');

	await expect(
		await commerceCatalogPage.globalSearchBarCommerceItemLink(
			'A Product designed'
		)
	).toHaveCount(0);

	await commerceCatalogPage.search('ABS Sensor');
	await (
		await commerceCatalogPage.globalSearchBarCommerceItemLink(
			'Wear Sensors Product designed'
		)
	).waitFor({state: 'visible'});

	await expect(
		await commerceCatalogPage.globalSearchBarCommerceItemLink(
			'ABS Sensor Product designed'
		)
	).toBeVisible();
	await expect(
		await commerceCatalogPage.globalSearchBarCommerceItemLink(
			'Wear Sensors Product designed'
		)
	).toBeVisible();

	await commerceCatalogPage.clearSearchButton.click();
	await commerceCatalogPage.search(`"ABS Sensor"`);
	await (
		await commerceCatalogPage.globalSearchBarCommerceItemLink(
			'ABS Sensor Product designed'
		)
	).waitFor({state: 'visible'});

	await expect(
		await commerceCatalogPage.globalSearchBarCommerceItemLink(
			'ABS Sensor Product designed'
		)
	).toHaveCount(1);

	await (
		await commerceCatalogPage.globalSearchBarCommerceItemLink(
			'ABS Sensor Product designed'
		)
	).click();

	await expect(
		await productDetailsPage.productNameHeading('ABS Sensor')
	).toBeVisible();
});

test('COMMERCE-6322. As a buyer, I want to be able to search an entry in Catalog using Global Search and I want the results to be visible in Search Results widget', async ({
	apiHelpers,
	commerceCatalogPage,
	page,
}) => {
	const {site} = await miniumSetUp(apiHelpers);

	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: getRandomString(),
		type: 'business',
	});

	apiHelpers.data.push({id: account.id, type: 'account'});

	const user =
		await apiHelpers.headlessAdminUser.getUserAccountByEmailAddress(
			'demo.unprivileged@liferay.com'
		);
	const rolesResponse = await apiHelpers.headlessAdminUser.getAccountRoles(
		account.id
	);

	const accountRoleBuyer = rolesResponse?.items?.filter((role) => {
		return role.name === 'Buyer';
	});

	await apiHelpers.headlessAdminUser.assignAccountRoles(
		account.externalReferenceCode,
		accountRoleBuyer[0].id,
		user.emailAddress
	);
	await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
		account.id,
		['demo.unprivileged@liferay.com']
	);
	const siteRole =
		await apiHelpers.headlessAdminUser.getRoleByExternalReferenceCode(
			'Site Member'
		);
	await apiHelpers.headlessAdminUser.assignUserToSite(
		siteRole.id,
		site.id,
		user.id
	);

	await performLogout(page);
	await performLogin(page, 'demo.unprivileged');

	await page.goto(`/web/${site.name}`);

	await commerceCatalogPage.catalogSearch.click();
	await commerceCatalogPage.catalogSearch.fill('U-Joint');
	await commerceCatalogPage.catalogSearch.press('Enter');

	await expect(
		await commerceCatalogPage.productLink('U-Joint')
	).toBeVisible();

	await expect(
		await commerceCatalogPage.productLink('Ball Joints')
	).toBeVisible();
});

test('COMMERCE-6326. As a buyer, I want to be able to search an entry in All Content using Global Search and the results should be visible on Search page', async ({
	apiHelpers,
	commerceCatalogPage,
	page,
}) => {
	const {site} = await miniumSetUp(apiHelpers);

	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: getRandomString(),
		type: 'business',
	});

	apiHelpers.data.push({id: account.id, type: 'account'});

	const user =
		await apiHelpers.headlessAdminUser.getUserAccountByEmailAddress(
			'demo.unprivileged@liferay.com'
		);
	const rolesResponse = await apiHelpers.headlessAdminUser.getAccountRoles(
		account.id
	);

	const accountRoleBuyer = rolesResponse?.items?.filter((role) => {
		return role.name === 'Buyer';
	});

	await apiHelpers.headlessAdminUser.assignAccountRoles(
		account.externalReferenceCode,
		accountRoleBuyer[0].id,
		user.emailAddress
	);
	await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
		account.id,
		['demo.unprivileged@liferay.com']
	);
	const siteRole =
		await apiHelpers.headlessAdminUser.getRoleByExternalReferenceCode(
			'Site Member'
		);
	await apiHelpers.headlessAdminUser.assignUserToSite(
		siteRole.id,
		site.id,
		user.id
	);

	await performLogout(page);
	await performLogin(page, 'demo.unprivileged');

	await page.goto(`/web/${site.name}`);

	await commerceCatalogPage.focusGlobalSearchBarInput();
	await commerceCatalogPage.search('U-Joint');
	await (
		await commerceCatalogPage.globalSearchBarCommerceItemLink(
			'Search U-Joint in All Content'
		)
	).waitFor({state: 'visible'});

	await expect(
		await commerceCatalogPage.globalSearchBarCommerceItemLink(
			'Search U-Joint in All Content'
		)
	).toBeVisible();

	await (
		await commerceCatalogPage.globalSearchBarCommerceItemLink(
			'Search U-Joint in All Content'
		)
	).click();

	await expect(
		await commerceCatalogPage.productLink('U-Joint')
	).toBeVisible();

	await expect(
		await commerceCatalogPage.productLink('Ball Joints')
	).toBeVisible();
});

test('COMMERCE-6321. As a buyer, I want to be able to search an Orders entry using Global Search and I want to be able to click on a suggested entry and get redirected to that order details page', async ({
	apiHelpers,
	commerceCatalogPage,
	commerceLayoutsPage,
	page,
}) => {
	const {site} = await miniumSetUp(apiHelpers);

	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: getRandomString(),
		type: 'business',
	});

	apiHelpers.data.push({id: account.id, type: 'account'});

	const user =
		await apiHelpers.headlessAdminUser.getUserAccountByEmailAddress(
			'demo.unprivileged@liferay.com'
		);
	const rolesResponse = await apiHelpers.headlessAdminUser.getAccountRoles(
		account.id
	);

	const accountRoleBuyer = rolesResponse?.items?.filter((role) => {
		return role.name === 'Buyer';
	});

	await apiHelpers.headlessAdminUser.assignAccountRoles(
		account.externalReferenceCode,
		accountRoleBuyer[0].id,
		user.emailAddress
	);
	await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
		account.id,
		['demo.unprivileged@liferay.com']
	);
	const siteRole =
		await apiHelpers.headlessAdminUser.getRoleByExternalReferenceCode(
			'Site Member'
		);
	await apiHelpers.headlessAdminUser.assignUserToSite(
		siteRole.id,
		site.id,
		user.id
	);

	await performLogout(page);
	await performLogin(page, 'demo.unprivileged');

	await page.goto(`/web/${site.name}`);

	await commerceLayoutsPage.pendingOrdersLink.click();
	await commerceLayoutsPage.addOrderButton.click();
	await commerceLayoutsPage.catalogLink.click();
	await page.mouse.move(100, 0);

	const orders = await apiHelpers.headlessCommerceAdminOrder.getOrdersPage();

	apiHelpers.data.push({id: orders.items[0].id, type: 'order'});

	await commerceCatalogPage.focusGlobalSearchBarInput();
	await commerceCatalogPage.search(`${orders.items[0].id}`);
	await (
		await commerceCatalogPage.globalSearchBarCommerceOrderLink(
			`${orders.items[0].id}`,
			`${account.name}`
		)
	).waitFor({state: 'visible'});

	await expect(
		await commerceCatalogPage.globalSearchBarCommerceOrderLink(
			`${orders.items[0].id}`,
			`${account.name}`
		)
	).toBeVisible();

	await commerceCatalogPage.clearSearchButton.click();
	await commerceCatalogPage.search(`${user.emailAddress}`);
	await (
		await commerceCatalogPage.globalSearchBarCommerceOrderLink(
			`${orders.items[0].id}`,
			`${account.name}`
		)
	).waitFor({state: 'visible'});

	await expect(
		await commerceCatalogPage.globalSearchBarCommerceOrderLink(
			`${orders.items[0].id}`,
			`${account.name}`
		)
	).toBeVisible();

	await (
		await commerceCatalogPage.globalSearchBarCommerceOrderLink(
			`${orders.items[0].id}`,
			`${account.name}`
		)
	).click();

	await expect(
		page.getByText(`Order Id ${orders.items[0].id}`)
	).toBeVisible();
});
