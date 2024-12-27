/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {pagesAdminPagesTest} from '../../fixtures/pagesAdminPagesTest';
import {liferayConfig} from '../../liferay.config';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import createUserWithPermissions from '../../utils/createUserWithPermissions';
import getRandomString from '../../utils/getRandomString';
import {hoverAndExpectToBeVisible} from '../../utils/hoverAndExpectToBeVisible';
import {performUserSwitch} from '../../utils/performLogin';
import {openProductMenu} from '../../utils/productMenu';
import getPageDefinition from '../layout-content-page-editor-web/utils/getPageDefinition';
import {pagesPagesTest} from './fixtures/pagesPagesTest';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest,
	pagesAdminPagesTest,
	pagesPagesTest
);

const testWithPrivatePages = mergeTests(
	test,
	featureFlagsTest({
		'LPD-38869': {enabled: true},
		'LPS-178052': {enabled: true},
	})
);

test(
	'Add child page',
	{
		tag: ['@LPS-103104', '@LPS-102544'],
	},
	async ({apiHelpers, page, pageTreePage, pagesAdminPage, site}) => {

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

		// Add child page

		await page.getByRole('link', {name: layoutTitle}).hover();

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('menuitem', {name: 'Add Child Page'}),
			trigger: page
				.getByRole('treeitem')
				.filter({hasText: layoutTitle})
				.locator('button.dropdown-toggle'),
		});

		const childLayoutTitle = getRandomString();

		await pagesAdminPage.addPage({
			name: childLayoutTitle,
			template: 'Widget Page',
		});

		// Assert child page in page tree

		await page.goto(
			`${liferayConfig.environment.baseUrl}/en/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
		);

		await openProductMenu(page);

		await pageTreePage.open();

		await expect(
			page.getByRole('link', {name: childLayoutTitle})
		).toBeVisible();
	}
);

testWithPrivatePages(
	'Can navigate to pages through pages hierarchy and navigation menus',
	{
		tag: ['@LPS-102544', '@LPS-133709'],
	},
	async ({apiHelpers, page, pageTreePage, site}) => {

		// Add navigation menu

		const siteNavigationMenuName = getRandomString();

		await apiHelpers.jsonWebServicesSiteNavigationMenu.addSiteNavigationMenu(
			site.id,
			siteNavigationMenuName
		);

		// Create a public page and a private page

		const publicLayoutTitle = getRandomString();

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			siteId: site.id,
			title: publicLayoutTitle,
		});

		const privateLayoutTitle = getRandomString();

		await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			privateLayout: 'true',
			title: privateLayoutTitle,
		});

		await page.goto(
			`${liferayConfig.environment.baseUrl}/en/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
		);

		// Open the Product Menu

		await openProductMenu(page);

		// Open tree if it's not already open

		await pageTreePage.open();

		// Assert private page

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('option', {name: 'Private Pages'}),
			trigger: page.getByLabel('Pages Type'),
		});

		await expect(
			page.getByRole('link', {name: publicLayoutTitle})
		).not.toBeVisible();

		await expect(
			page.getByRole('link', {name: privateLayoutTitle})
		).toBeVisible();

		// Assert navigation menu

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('option', {name: siteNavigationMenuName}),
			trigger: page.getByLabel('Pages Type'),
		});

		await expect(
			page.getByRole('link', {name: publicLayoutTitle})
		).not.toBeVisible();

		await expect(
			page.getByRole('link', {name: privateLayoutTitle})
		).not.toBeVisible();

		// Assert public page

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('option', {name: 'Public Pages'}),
			trigger: page.getByLabel('Pages Type'),
		});

		await expect(
			page.getByRole('link', {name: publicLayoutTitle})
		).toBeVisible();

		await expect(
			page.getByRole('link', {name: privateLayoutTitle})
		).not.toBeVisible();
	}
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

test(
	'Users with only View permissions can not see draft options',
	{
		tag: '@LPS-140136',
	},
	async ({apiHelpers, page, pageEditorPage, pageTreePage, site}) => {

		// Create a page and go to edit mode

		const pageName = getRandomString();

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition(),
			siteId: site.id,
			title: pageName,
		});

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		// Add a fragment so we create a draft

		await pageEditorPage.addFragment('Basic Components', 'Heading');

		// Switch to a new user with only View page permission

		const company =
			await apiHelpers.jsonWebServicesCompany.getCompanyByWebId(
				'liferay.com'
			);

		const user = await createUserWithPermissions({
			apiHelpers,
			rolePermissions: [
				{
					actionIds: ['VIEW'],
					primaryKey: company.companyId,
					resourceName: 'com.liferay.portal.kernel.model.Layout',
					scope: 1,
				},
				{
					actionIds: ['ACCESS_IN_CONTROL_PANEL', 'VIEW'],
					primaryKey: company.companyId,
					resourceName:
						'com_liferay_layout_admin_web_portlet_GroupPagesPortlet',
					scope: 1,
				},
				{
					actionIds: ['ADD_LAYOUT', 'VIEW_SITE_ADMINISTRATION'],
					primaryKey: company.companyId,
					resourceName: 'com.liferay.portal.kernel.model.Group',
					scope: 1,
				},
			],
		});

		await performUserSwitch(page, user.alternateName);

		await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

		// Open the Product Menu

		await openProductMenu(page);

		// Open tree if it's not already open

		await pageTreePage.open();

		// Check draft actions are not present

		const treeItem = page.getByRole('treeitem', {name: pageName});

		await hoverAndExpectToBeVisible({
			autoClick: true,
			target: treeItem.locator('.dropdown-toggle'),
			trigger: treeItem,
		});

		await expect(
			page.getByRole('menuitem', {name: 'Preview Draft'})
		).not.toBeVisible();
	}
);
