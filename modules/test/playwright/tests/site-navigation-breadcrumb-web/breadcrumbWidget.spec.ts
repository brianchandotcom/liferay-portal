/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {breadcrumbWidgetPagesTest} from '../../fixtures/breadcrumbWidgetPagesTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageViewModePagesTest} from '../../fixtures/pageViewModePagesTest';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../utils/getRandomString';
import {templatesPageTest} from '../template-web/fixtures/templatesPageTest';

export const test = mergeTests(
	apiHelpersTest,
	breadcrumbWidgetPagesTest,
	isolatedSiteTest,
	loginTest(),
	pageViewModePagesTest,
	templatesPageTest
);

test(
	'Currently selected page in Breadcrumb widget has aria-current attribute',
	{
		tag: '@LPD-40431',
	},
	async ({breadcrumbWidgetPage, page, site}) => {
		await breadcrumbWidgetPage.addBreadcrumbPortlet(site);

		await expect(
			page.locator('.active.breadcrumb-text-truncate')
		).toHaveAttribute('aria-current', 'page');
	}
);

test('Select widget template in Breadcrumb widget configuration', async ({
	breadcrumbWidgetPage,
	page,
	site,
	templatesPage,
	widgetPagePage,
}) => {
	await templatesPage.gotoWidgetTemplates(site.friendlyUrlPath);

	const widgetTemplateName = getRandomString();

	await templatesPage.createWidgetTemplate(
		widgetTemplateName,
		'Breadcrumb Template'
	);

	await breadcrumbWidgetPage.addBreadcrumbPortlet(site);

	await widgetPagePage.clickOnAction('Breadcrumb', 'Configuration');

	const configurationIFrame = page.frameLocator(
		'iframe[title*="Breadcrumb"]'
	);

	await clickAndExpectToBeVisible({
		autoClick: true,
		target: configurationIFrame.getByRole('option', {
			exact: true,
			name: widgetTemplateName,
		}),
		trigger: configurationIFrame.getByLabel('Display Template'),
	});

	await widgetPagePage.saveAndClose('Breadcrumb');

	await widgetPagePage.clickOnAction('Breadcrumb', 'Configuration');

	await configurationIFrame.getByLabel('Display Template').click();

	await expect(
		configurationIFrame.locator('button[aria-selected="true"]')
	).toHaveText(widgetTemplateName);
});

test(
	'Breadcrumb widget configuration remains unchanged without clicking save',
	{
		tag: '@LPS-150908',
	},
	async ({breadcrumbWidgetPage, page, site, widgetPagePage}) => {
		const layout = await breadcrumbWidgetPage.addBreadcrumbPortlet(site);

		await widgetPagePage.clickOnAction('Breadcrumb', 'Configuration');

		const configurationIFrame = page.frameLocator(
			'iframe[title*="Breadcrumb"]'
		);

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: configurationIFrame.getByRole('option', {
				exact: true,
				name: 'Arrows',
			}),
			trigger: configurationIFrame.getByLabel('Display Template'),
		});

		await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyURL}`);

		await widgetPagePage.clickOnAction('Breadcrumb', 'Configuration');

		await configurationIFrame.getByLabel('Display Template').click();

		await expect(
			configurationIFrame.locator('button[aria-selected="true"]')
		).toHaveText('Horizontal');
	}
);

test('Configure Show Application in Breadcrumb widget', async ({
	apiHelpers,
	breadcrumbWidgetPage,
	page,
	site,
	widgetPagePage,
}) => {
	const folder = await apiHelpers.headlessDelivery.postDocumentFolder(
		site.id
	);
	const layout = await breadcrumbWidgetPage.addBreadcrumbPortlet(site);

	await widgetPagePage.addPortlet('Documents and Media');

	await page.getByRole('link', {name: folder.name}).click();

	await page.waitForTimeout(2000);

	await expect(
		page.locator(
			'[id^="_com_liferay_site_navigation_breadcrumb_web_portlet_SiteNavigationBreadcrumbPortlet_INSTANCE_"] .active.breadcrumb-text-truncate'
		)
	).toHaveText(folder.name);

	await widgetPagePage.clickOnAction('Breadcrumb', 'Configuration');

	const configurationIFrame = page.frameLocator(
		'iframe[title*="Breadcrumb"]'
	);

	await configurationIFrame.getByLabel('Show Application Breadcrumb').click();

	await widgetPagePage.saveAndClose('Breadcrumb');

	await expect(page.locator('.active.breadcrumb-text-truncate')).toHaveText(
		layout.nameCurrentValue
	);
});

test('Configure Show Current Site in Breadcrumb widget', async ({
	breadcrumbWidgetPage,
	page,
	site,
	widgetPagePage,
}) => {
	const layout = await breadcrumbWidgetPage.addBreadcrumbPortlet(site);

	let breadcrumbEntries = await page
		.locator('.breadcrumb-text-truncate')
		.allInnerTexts();

	await expect(breadcrumbEntries.length).toBe(2);

	await expect(breadcrumbEntries).toEqual(
		expect.arrayContaining([site.name, layout.nameCurrentValue])
	);

	await widgetPagePage.clickOnAction('Breadcrumb', 'Configuration');

	const configurationIFrame = page.frameLocator(
		'iframe[title*="Breadcrumb"]'
	);

	await configurationIFrame.getByLabel('Show Current Site').click();

	await widgetPagePage.saveAndClose('Breadcrumb');

	breadcrumbEntries = await page
		.locator('.breadcrumb-text-truncate')
		.allInnerTexts();

	await expect(breadcrumbEntries.length).toBe(1);

	await expect(breadcrumbEntries).toEqual(
		expect.arrayContaining([layout.nameCurrentValue])
	);
});
