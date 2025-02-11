/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {breadcrumbWidgetPagesTest} from '../../fixtures/breadcrumbWidgetPagesTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageViewModePagesTest} from '../../fixtures/pageViewModePagesTest';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../utils/getRandomString';
import {templatesPageTest} from '../template-web/fixtures/templatesPageTest';

export const test = mergeTests(
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
