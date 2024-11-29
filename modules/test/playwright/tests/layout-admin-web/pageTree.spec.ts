/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {liferayConfig} from '../../liferay.config';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import createUserWithPermissions from '../../utils/createUserWithPermissions';
import getRandomString from '../../utils/getRandomString';
import {performUserSwitch} from '../../utils/performLogin';
import {openProductMenu} from '../../utils/productMenu';
import {pagesPagesTest} from './fixtures/pagesPagesTest';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest(),
	pagesPagesTest
);

test(
	'Check back button',
	{
		tag: ['@LPS-112992', '@LPS-116618', '@LPS-148241'],
	},
	async ({apiHelpers, page, pageTreePage, site}) => {

		// Create a new page

		const layoutTitle = getRandomString();

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			siteId: site.id,
			title: layoutTitle,
		});

		await page.goto(
			`${liferayConfig.environment.baseUrl}/en/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
		);

		// Open the Product Menu

		await openProductMenu(page);

		// Open tree if it's not already open

		await pageTreePage.open();

		// Configure page

		await page.getByRole('link', {name: layoutTitle}).hover();

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('menuitem', {name: 'Configure'}),
			trigger: page
				.getByRole('treeitem')
				.filter({hasText: layoutTitle})
				.locator('button.dropdown-toggle'),
		});

		// Click back button

		await page.getByRole('link', {name: `Go to ${layoutTitle}`}).click();

		// Assert page

		await expect(
			page.getByRole('heading', {name: layoutTitle})
		).toBeVisible();

		// Configure pages

		await page.getByLabel('Configure Pages').click();

		// Click back button

		await page
			.getByRole('link', {exact: true, name: 'Go to Pages'})
			.click();

		// Assert page

		await expect(
			page.getByRole('heading', {name: layoutTitle})
		).toBeVisible();
	}
);

test('Checks the correct label for restricted page in the Page Tree', async ({
	apiHelpers,
	page,
	pageTreePage,
	site,
}) => {

	// Create a content page with only one permission and open the edit mode

	const pageName = getRandomString();

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pagePermissions: [
			{
				actionKeys: ['VIEW'],
				roleKey: 'Owner',
			},
		],
		siteId: site.id,
		title: pageName,
	});

	await page.goto(
		`${liferayConfig.environment.baseUrl}/en/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
	);

	// Open the Product Menu

	await openProductMenu(page);

	// Open tree if it's not already open

	await pageTreePage.open();

	// Check the correct label for restricted page

	await expect(
		page
			.getByLabel('Product Menu', {exact: true})
			.locator('div', {
				hasText: pageName,
			})
			.getByLabel('Restricted Page')
	).toBeVisible();
});

test(
	'Checks unprivileged users can not add a page via Page Tree',
	{
		tag: '@LPS-129406',
	},
	async ({apiHelpers, page, pageTreePage}) => {
		await page.goto('/');

		// Open the Product Menu

		await openProductMenu(page);

		// Open tree if it's not already open

		await pageTreePage.open();

		// Assert add page button is visible for admin user

		await expect(
			page
				.locator('.page-type-selector')
				.getByTitle('Add Page', {exact: true})
		).toBeVisible();

		// Switch to a new user with update page permissions and without edit segments entry permissions

		const company =
			await apiHelpers.jsonWebServicesCompany.getCompanyByWebId(
				'liferay.com'
			);

		const user = await createUserWithPermissions({
			apiHelpers,
			rolePermissions: [
				{
					actionIds: ['ACCESS_IN_CONTROL_PANEL'],
					primaryKey: company.companyId,
					resourceName:
						'com_liferay_layout_admin_web_portlet_GroupPagesPortlet',
					scope: 1,
				},
				{
					actionIds: ['VIEW_SITE_ADMINISTRATION'],
					primaryKey: company.companyId,
					resourceName: 'com.liferay.portal.kernel.model.Group',
					scope: 1,
				},
			],
		});

		await performUserSwitch(page, user.alternateName);

		// Open the Product Menu

		await page.goto('/');

		await openProductMenu(page);

		// Open tree if it's not already open

		await pageTreePage.open();

		// Assert add page button is not visible

		await expect(
			page
				.locator('.page-type-selector')
				.getByTitle('Add Page', {exact: true})
		).not.toBeVisible();
	}
);
