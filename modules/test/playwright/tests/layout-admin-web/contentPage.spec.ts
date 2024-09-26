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
import getRandomString from '../../utils/getRandomString';
import getFragmentDefinition from '../layout-content-page-editor-web/utils/getFragmentDefinition';
import getPageDefinition from '../layout-content-page-editor-web/utils/getPageDefinition';
import getWidgetDefinition from '../layout-content-page-editor-web/utils/getWidgetDefinition';
import {pagesPagesTest} from './fixtures/pagesPagesTest';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest(),
	pagesAdminPagesTest,
	pagesPagesTest,
	pageEditorPagesTest
);

test(
	'Can configure and delete content page via header ellipsis icon at edit mode',
	{
		tag: '@LPS-137155',
	},
	async ({apiHelpers, page, pageEditorPage, site}) => {

		// Create a content page

		const pageName = getRandomString();

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			siteId: site.id,
			title: pageName,
		});

		// Configure page in edit mode

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page
				.locator('.dropdown-menu')
				.getByRole('menuitem', {name: 'Configure'}),
			trigger: page
				.locator('.control-menu-nav-item')
				.getByLabel('Options', {exact: true}),
		});

		await expect(page.getByText('Basic Info')).toBeVisible();

		// Delete page in edit mode

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page
				.locator('.dropdown-menu')
				.getByRole('menuitem', {name: 'Delete'}),
			trigger: page
				.locator('.control-menu-nav-item')
				.getByLabel('Options', {exact: true}),
		});

		await page.getByRole('button', {name: 'Delete'}).click();

		await expect(page.getByText('No Pages Yet.')).toBeVisible();
	}
);

test('Does not show widget topper on hover in view mode', async ({
	apiHelpers,
	page,
	site,
}) => {

	// Create a page with a Dropdown fragment and a Breadcrumb widget below
	// This case is specific to cover LPP-45872

	const fragmentId = getRandomString();

	const fragmentDefinition = getFragmentDefinition({
		id: fragmentId,
		key: 'BASIC_COMPONENT-dropdown',
	});

	const widgetId = getRandomString();

	const widgetDefinition = getWidgetDefinition({
		id: widgetId,
		widgetName:
			'com_liferay_site_navigation_breadcrumb_web_portlet_SiteNavigationBreadcrumbPortlet',
	});

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([
			fragmentDefinition,
			widgetDefinition,
		]),
		siteId: site.id,
		title: getRandomString(),
	});

	// Go to view mode and check widget topper is not shown

	await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

	await page.locator('.portlet-breadcrumb').hover();

	await expect(
		page.locator('.portlet-topper').getByText('Breadcrumb')
	).not.toBeVisible();
});

test('Checks the correct label for restricted page in the page heading', async ({
	apiHelpers,
	page,
	site,
}) => {

	// Create a content page with only one permission

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

	// Go to the view mode and check the restricted page label

	await page.goto(
		`${liferayConfig.environment.baseUrl}/en/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
	);

	const header = page.getByRole('heading', {name: pageName});

	await header.waitFor({state: 'visible'});

	await expect(header.getByText('Restricted Page')).toBeVisible();
});

test(
	'Checks page title in view mode and in edit mode',
	{
		tag: '@LPS-146373',
	},
	async ({apiHelpers, page, site}) => {

		// Create a content page

		const pageName = getRandomString();

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			siteId: site.id,
			title: pageName,
		});

		// Check the page title in the view mode

		await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

		expect(await page.title()).toBe(
			`${pageName} - ${site.name} - Liferay DXP`
		);

		// Check the page title in the edit mode

		await page.getByTitle('Edit', {exact: true}).click();

		await page
			.getByText('Drag and drop fragments or widgets here.')
			.waitFor();

		expect(await page.title()).toBe(
			`${pageName} - ${site.name} - Liferay DXP (Editing)`
		);

		// Click back button

		await page.getByTitle(`Go to ${pageName}`).click();

		await page.getByTitle('Edit', {exact: true}).waitFor();

		expect(await page.title()).toBe(
			`${pageName} - ${site.name} - Liferay DXP`
		);
	}
);
