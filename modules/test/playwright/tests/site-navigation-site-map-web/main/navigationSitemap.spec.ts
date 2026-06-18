/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {pagesAdminPagesTest} from '../../../fixtures/pagesAdminPagesTest';
import getRandomString from '../../../utils/getRandomString';
import getPageDefinition from '../../layout-content-page-editor-web/main/utils/getPageDefinition';
import getWidgetDefinition from '../../layout-content-page-editor-web/main/utils/getWidgetDefinition';

export const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest,
	pagesAdminPagesTest
);

test("Site Map multi column layout template should respect the 'Include Root in Tree' setting", async ({
	apiHelpers,
	page,
	pageEditorPage,
	pagesAdminPage,
	site,
}) => {
	const childLayoutName = getRandomString();
	const rootLayoutName = getRandomString();
	const widgetId = getRandomString();

	let rootLayout: Layout;

	await test.step('Create a page with a Site Map Widget and its child page', async () => {
		rootLayout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				getWidgetDefinition({
					id: widgetId,
					widgetConfig: {
						displayDepth: '0',
						displayStyle:
							'ddmTemplate_SITE-MAP-MULTI-COLUMN-LAYOUT-FTL',
					},
					widgetName:
						'com_liferay_site_navigation_site_map_web_portlet_SiteNavigationSiteMapPortlet',
				}),
			]),
			siteId: site.id,
			title: rootLayoutName,
		});

		await pagesAdminPage.goto(site.friendlyUrlPath);
		await pagesAdminPage.createNewPage({
			name: childLayoutName,
			parent: rootLayoutName,
		});
	});

	await test.step('Configure the Site Map Widget to include root in tree', async () => {
		await pageEditorPage.goto(rootLayout, site.friendlyUrlPath);
		await pageEditorPage.goToWidgetConfiguration(widgetId);

		const configurationIframe = page.frameLocator(
			'iframe[title="Configuration"]'
		);

		await configurationIframe.getByRole('button', {name: 'Select'}).click();

		const selectRootPageIframe = configurationIframe.frameLocator('iframe');

		const rootLayoutTreeItem = selectRootPageIframe.locator(
			'.treeview-link',
			{hasText: rootLayoutName}
		);

		await expect(rootLayoutTreeItem).toBeVisible();

		await rootLayoutTreeItem.click();

		await configurationIframe.getByRole('button', {name: 'Save'}).click();
		await configurationIframe.getByText('Include Root in Tree').click();
		await configurationIframe.getByRole('button', {name: 'Save'}).click();

		await page.locator('.modal').getByLabel('Close', {exact: true}).click();
	});

	await test.step('Check that the root page is included in the tree', async () => {
		const siteMapFirstColumn = page
			.locator('#main-content .portlet-body .col-md-3')
			.first();

		await expect(
			siteMapFirstColumn.locator('h3').getByText(rootLayoutName)
		).toBeVisible();
		await expect(
			siteMapFirstColumn.locator('ul').getByText(childLayoutName)
		).toBeVisible();
	});
});

test('Site Map widget configuration should allow clearing the Root Page', async ({
	apiHelpers,
	page,
	pageEditorPage,
	pagesAdminPage,
	site,
}) => {
	const childLayoutName = getRandomString();
	const rootLayoutName = getRandomString();
	const widgetId = getRandomString();

	let rootLayout: Layout;

	await test.step('Create a page with a Site Map Widget and its child page', async () => {
		rootLayout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				getWidgetDefinition({
					id: widgetId,
					widgetConfig: {
						displayDepth: '0',
						displayStyle:
							'ddmTemplate_SITE-MAP-MULTI-COLUMN-LAYOUT-FTL',
					},
					widgetName:
						'com_liferay_site_navigation_site_map_web_portlet_SiteNavigationSiteMapPortlet',
				}),
			]),
			siteId: site.id,
			title: rootLayoutName,
		});

		await pagesAdminPage.goto(site.friendlyUrlPath);
		await pagesAdminPage.createNewPage({
			name: childLayoutName,
			parent: rootLayoutName,
		});
	});

	await test.step('Set a Root Page in the Site Map Widget configuration', async () => {
		await pageEditorPage.goto(rootLayout, site.friendlyUrlPath);
		await pageEditorPage.goToWidgetConfiguration(widgetId);

		const configurationIframe = page.frameLocator(
			'iframe[title="Configuration"]'
		);

		await configurationIframe.getByRole('button', {name: 'Select'}).click();

		const selectRootPageIframe = configurationIframe.frameLocator('iframe');

		const rootLayoutTreeItem = selectRootPageIframe.locator(
			'.treeview-link',
			{hasText: rootLayoutName}
		);

		await expect(rootLayoutTreeItem).toBeVisible();

		await rootLayoutTreeItem.click();

		await configurationIframe.getByRole('button', {name: 'Save'}).click();

		await expect(
			configurationIframe.getByText('Include Root in Tree')
		).toBeVisible();
	});

	await test.step('Clear the Root Page and verify it is removed', async () => {
		const configurationIframe = page.frameLocator(
			'iframe[title="Configuration"]'
		);

		await configurationIframe.getByRole('button', {name: 'Remove'}).click();

		await configurationIframe.getByRole('button', {name: 'Save'}).click();

		await expect(
			configurationIframe.getByText('Include Root in Tree')
		).toBeHidden();
		await expect(
			configurationIframe.getByText(rootLayoutName)
		).toBeHidden();
	});
});
