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
import {pageManagementSiteTest} from '../../fixtures/pageManagementSiteTest';
import getRandomString from '../../utils/getRandomString';
import {LEMON_OBJECT_ERC} from '../setup/page-management-site/constants';
import getFormContainerDefinition from './utils/getFormContainerDefinition';
import getPageDefinition from './utils/getPageDefinition';
import getWidgetDefinition from './utils/getWidgetDefinition';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest,
	pageManagementSiteTest
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

test('It is not possible to drag a widget inside a Form Container', async ({
	apiHelpers,
	page,
	pageEditorPage,
	pageManagementSite,
}) => {

	// Get the id of Lemon object from the site initializer

	const {id: objectDefinitionId} =
		await apiHelpers.objectAdmin.getObjectDefinitionByExternalReferenceCode(
			LEMON_OBJECT_ERC
		);

	// Create page with Search Bar widget and a Form container

	const widgetId = getRandomString();

	const widgetDefinition = getWidgetDefinition({
		id: widgetId,
		widgetName:
			'com_liferay_portal_search_web_search_bar_portlet_SearchBarPortlet',
	});

	const formId = getRandomString();

	const formDefinition = getFormContainerDefinition({
		id: formId,
		objectDefinitionId,
		pageElements: [],
	});

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([widgetDefinition, formDefinition]),
		siteId: pageManagementSite.id,
		title: getRandomString(),
	});

	// Go to page editor

	await pageEditorPage.goto(layout, pageManagementSite.friendlyUrlPath);

	// Check it's not possible to drag the widget inside the form from topper

	const formDropzone = page.locator(
		'.page-editor__form .page-editor__container .page-editor__no-fragments-state__message'
	);

	await pageEditorPage.selectFragment(widgetId);

	await page.locator('.page-editor__topper__drag-icon').dragTo(formDropzone);

	const alert = page.locator('.alert');

	await expect(
		alert.getByText('Widgets cannot be placed inside a form container')
	).toBeVisible();

	await alert.getByLabel('Close').click();

	// Check it's not possible to drag the widget inside the form from the widget itself

	const widget = pageEditorPage.getFragment(widgetId);

	await widget.dragTo(formDropzone);

	await expect(
		alert.getByText('Widgets cannot be placed inside a form container')
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
