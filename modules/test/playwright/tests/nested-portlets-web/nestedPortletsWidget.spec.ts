/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageViewModePagesTest} from '../../fixtures/pageViewModePagesTest';
import getRandomString from '../../utils/getRandomString';

const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	loginTest(),
	pageViewModePagesTest
);

test('User can nest a widget inside nested portlets widget', async ({
	apiHelpers,
	page,
	site,
	widgetPagePage,
}) => {

	// Add widget page and navigate to view

	const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
		groupId: site.id,
		title: getRandomString(),
	});

	await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyURL}`);

	// Add nested applications widget and assert default template

	await widgetPagePage.addPortlet('Nested Applications');

	await page
		.locator('.portlet-nested-portlets')
		.first()
		.getByLabel('Options')
		.click();

	await page
		.getByRole('menuitem', {exact: true, name: 'Configuration'})
		.click();

	const configurationIFrame = page.frameLocator(
		'iframe[title*="Nested Applications"]'
	);

	await expect(
		configurationIFrame.getByLabel('2 Columns (50/50)')
	).toBeChecked();

	await page
		.locator('.modal-header')
		.getByLabel('close', {exact: true})
		.click();

	// Add web content display widget and drag into nested applications widget

	await widgetPagePage.addPortlet('Web Content Display');

	const webContentDisplayTopper = page.locator(
		'.portlet-journal-content .portlet-topper'
	);

	const nestedPortletDropZone = page
		.locator('.portlet-nested-portlets .portlet-dropzone.empty')
		.first();

	const boundingClientRect = await nestedPortletDropZone.evaluate((element) =>
		element.getBoundingClientRect()
	);

	await webContentDisplayTopper.hover();

	await page.mouse.down();

	await page.mouse.move(
		boundingClientRect.x + boundingClientRect.width / 2,
		boundingClientRect.y + boundingClientRect.height / 2,
		{steps: 10}
	);

	await page
		.locator('.sortable-layout-drag-indicator')
		.waitFor({state: 'visible'});

	await page.mouse.up();

	// Check if the web content display widget is added to the nested portlets widget

	await expect(
		page
			.locator(
				'.portlet-nested-portlets .portlet-column-content-first .portlet-title-default'
			)
			.getByText('Web Content Display')
	).toBeAttached();
});
