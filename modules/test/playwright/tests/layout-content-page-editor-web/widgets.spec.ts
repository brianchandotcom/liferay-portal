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
import getRandomString from '../../utils/getRandomString';
import getPageDefinition from './utils/getPageDefinition';
import getWidgetDefinition from './utils/getWidgetDefinition';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest
);

test('Allows accessing the widget configuration easily', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {

	// Create page with Search Bar widget and go to edit mode

	const widgetId = getRandomString();

	const widgetDefinition = getWidgetDefinition({
		id: widgetId,
		widgetName:
			'com_liferay_portal_search_web_search_bar_portlet_SearchBarPortlet',
	});

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([widgetDefinition]),
		siteId: site.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	// Check widget configuration is accessible

	const topper = pageEditorPage.getTopper(widgetId);

	await topper.hover();

	await expect(topper.locator('.portlet-options')).toBeVisible();

	await topper.locator('.portlet-options').click();

	await expect(
		page.getByRole('menuitem', {exact: true, name: 'Configuration'})
	).toBeVisible();
});

test.describe('Menu Display Widget', () => {
	test('Checks that the Display Menu items have a role link with display style Bar With Links', async ({
		apiHelpers,
		page,
		site,
	}) => {
		const widgetDefinition = getWidgetDefinition({
			id: getRandomString(),
			widgetConfig: {
				displayStyle: 'ddmTemplate_NAVBAR-LINKS-FTL',
			},
			widgetName:
				'com_liferay_site_navigation_menu_web_portlet_SiteNavigationMenuPortlet',
		});

		// Create three pages, one of them with Menu Display widget

		await apiHelpers.headlessDelivery.createSitePage({
			siteId: site.id,
			title: 'First page',
		});

		const secondLayout = await apiHelpers.headlessDelivery.createSitePage({
			siteId: site.id,
			title: 'Second page',
		});

		const thirdLayout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([widgetDefinition]),
			parentSitePage: {friendlyUrlPath: secondLayout.friendlyUrlPath},
			siteId: site.id,
			title: 'Third page',
		});

		await page.goto(
			`/web${site.friendlyUrlPath}${thirdLayout.friendlyUrlPath}`
		);

		// Check that the Display Menu items have a role link

		await page.getByRole('link', {name: 'Second page'}).hover();

		await expect(
			page.getByRole('link', {name: 'First page'})
		).toBeVisible();
		await expect(
			page.getByRole('link', {name: 'Second page'})
		).toBeVisible();
		await expect(
			page.getByRole('link', {name: 'Third page'})
		).toBeVisible();
	});
});
