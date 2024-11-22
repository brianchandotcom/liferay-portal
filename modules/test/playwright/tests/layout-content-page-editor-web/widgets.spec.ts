/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectDefinitionApi} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {pageManagementSiteTest} from '../../fixtures/pageManagementSiteTest';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../utils/getRandomString';
import {getObjectERC} from '../setup/page-management-site/utils/getObjectERC';
import getFormContainerDefinition from './utils/getFormContainerDefinition';
import getPageDefinition from './utils/getPageDefinition';
import getWidgetDefinition from './utils/getWidgetDefinition';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPD-32075': true,
		'LPD-40533': true,
		'LPD-40534': true,
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest,
	pageManagementSiteTest
);

test(
	'Check widget configuration is displayed in fragments topper and not in overlay',
	{tag: ['@LPD-32047']},
	async ({apiHelpers, page, pageEditorPage, site}) => {

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

		// Check widget options are accesible from fragments topper

		await pageEditorPage.selectFragment(widgetId);

		await page
			.locator('.page-editor__topper__item')
			.getByRole('button', {name: 'Options'})
			.click();

		const dropdown = page.locator('.dropdown-menu.show');

		await expect(
			dropdown.getByText('Configuration', {exact: true})
		).toBeVisible();

		await expect(dropdown.getByText('Export / Import')).toBeVisible();

		await expect(
			dropdown.getByText('Configuration Templates')
		).toBeVisible();

		await expect(dropdown.getByText('Permissions')).toBeVisible();

		// Check widget configuration is not accessible from overlay

		const topper = pageEditorPage.getTopper(widgetId);

		await topper.hover();

		await expect(topper.locator('.portlet-options')).not.toBeVisible();

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page
				.locator('.page-editor__topper__bar')
				.getByLabel('Options'),
			trigger: topper,
		});
	}
);

test('It is not possible to drag a widget inside a Form Container', async ({
	apiHelpers,
	page,
	pageEditorPage,
	pageManagementSite,
}) => {

	// Get the id of Lemon object from the site initializer

	const objectDefinitionApiClient =
		await apiHelpers.buildRestClient(ObjectDefinitionApi);

	const {className: objectDefinitionClassName} = (
		await objectDefinitionApiClient.getObjectDefinitionByExternalReferenceCode(
			getObjectERC('Lemon')
		)
	).body;

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
		objectDefinitionClassName,
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
			parentSitePage: {
				friendlyUrlPath: secondLayout.friendlyUrlPath,
			},
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

test(
	'Check that the Sharing and Supported Clients features have the deprecation badge',
	{
		tag: ['@LPD-41722', '@LPD-41723'],
	},
	async ({apiHelpers, page, pageEditorPage, site}) => {

		// Create a page with a Web Content Display Widget

		const widgetId = getRandomString();

		const widgetDefinition = getWidgetDefinition({
			id: widgetId,
			widgetName:
				'com_liferay_journal_content_web_portlet_JournalContentPortlet',
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([widgetDefinition]),
			siteId: site.id,
			title: getRandomString(),
		});

		// Access to the widget configuration

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		await pageEditorPage.selectFragment(widgetId);

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page
				.locator('.dropdown-menu.show')
				.getByText('Configuration', {exact: true}),
			trigger: page
				.locator('.page-editor__topper__item')
				.getByRole('button', {name: 'Options'}),
		});

		// Check the deprecated badges

		const configurationIFrame = page.frameLocator(
			'iframe[title="Configuration"]'
		);

		const navigationItem = configurationIFrame.getByRole('link');

		await expect(navigationItem.filter({hasText: 'Sharing'})).toContainText(
			/Deprecated/
		);

		await expect(
			navigationItem.filter({hasText: 'Supported Clients'})
		).toContainText(/Deprecated/);
	}
);
