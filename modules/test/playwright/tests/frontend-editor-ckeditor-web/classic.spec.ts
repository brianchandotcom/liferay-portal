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
import getRandomString from '../../utils/getRandomString';
import getPageDefinition from '../layout-content-page-editor-web/utils/getPageDefinition';
import getWidgetDefinition from '../layout-content-page-editor-web/utils/getWidgetDefinition';

export const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest()
);

test(
	'Dropdown menus are visible when maximized',
	{tag: '@LPD-33712'},
	async ({apiHelpers, page, site}) => {
		let layout: Layout;

		await test.step('Create a content site and the ckeditor sample widget', async () => {
			const widgetDefinition = getWidgetDefinition({
				id: getRandomString(),
				widgetName:
					'com_liferay_editor_ckeditor_sample_web_internal_portlet_CKEditorSamplePortlet',
			});

			layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([widgetDefinition]),
				siteId: site.id,
				title: getRandomString(),
			});
		});

		await test.step('Select Maximized button and check stylesCombo dropdown', async () => {
			await page.goto(
				`${liferayConfig.environment.baseUrl}/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			const ckeditorSampleWidgetClassicTab = page.getByRole('tab', {
				name: 'Classic',
			});

			await ckeditorSampleWidgetClassicTab.waitFor({state: 'visible'});
			await ckeditorSampleWidgetClassicTab.click();

			const maximizedButton = page.getByLabel('Maximize');

			await maximizedButton.waitFor({state: 'visible'});
			await maximizedButton.click();

			const stylesComboButton = page
				.getByLabel('Classic', {exact: true})
				.getByLabel('Styles');

			await stylesComboButton.click();

			const stylesComboZIndex = await page.evaluate(() => {
				const stylesComboElement = document.querySelector(
					'.cke_panel.cke_combopanel.lfr-maximized'
				);

				const stylesComboElementStyles =
					window.getComputedStyle(stylesComboElement);

				return stylesComboElementStyles.getPropertyValue('z-index');
			});

			expect(stylesComboZIndex).toEqual('10000');
		});
	}
);

test(
	'Context menu is displayed when maximized',
	{tag: '@LPD-38600'},
	async ({apiHelpers, page, site}) => {
		let layout: Layout;

		await test.step('Create a content site and the ckeditor sample widget', async () => {
			const widgetDefinition = getWidgetDefinition({
				id: getRandomString(),
				widgetName:
					'com_liferay_editor_ckeditor_sample_web_internal_portlet_CKEditorSamplePortlet',
			});

			layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([widgetDefinition]),
				siteId: site.id,
				title: getRandomString(),
			});
		});

		await test.step('Select Maximized button and check context menu', async () => {
			await page.goto(
				`${liferayConfig.environment.baseUrl}/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			const ckeditorSampleWidgetClassicTab = page.getByRole('tab', {
				name: 'Classic',
			});

			await ckeditorSampleWidgetClassicTab.waitFor({state: 'visible'});
			await ckeditorSampleWidgetClassicTab.click();

			const maximizedButton = page.getByLabel('Maximize');

			await maximizedButton.waitFor({state: 'visible'});
			await maximizedButton.click();

			const ckeditorEditorBody = page
				.getByRole('tabpanel', {name: 'Classic'})
				.frameLocator('iframe[title="editor"]')
				.getByRole('heading', {name: 'Classic Editor'});

			await ckeditorEditorBody.click({button: 'right'});

			const contextMenuZIndex = await page.evaluate(() => {
				const stylesComboElement = document.querySelector(
					'.cke_panel.cke_menu_panel'
				);

				const contextMenuElementStyles =
					window.getComputedStyle(stylesComboElement);

				return contextMenuElementStyles.getPropertyValue('z-index');
			});

			expect(contextMenuZIndex).toEqual('10001');
		});
	}
);

test(
	'Able to drag and drop images',
	{tag: '@LPD-41443'},
	async ({apiHelpers, page, site}) => {
		let layout: Layout;

		await test.step('Create a content site and the ckeditor sample widget', async () => {
			const widgetDefinition = getWidgetDefinition({
				id: getRandomString(),
				widgetName:
					'com_liferay_editor_ckeditor_sample_web_internal_portlet_CKEditorSamplePortlet',
			});

			layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([widgetDefinition]),
				siteId: site.id,
				title: getRandomString(),
			});
		});

		await test.step('Drag and drop image', async () => {
			await page.goto(
				`${liferayConfig.environment.baseUrl}/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			const ckeditorSampleWidgetClassicTab = page.getByRole('tab', {
				name: 'Classic',
			});

			await ckeditorSampleWidgetClassicTab.waitFor({state: 'visible'});
			await ckeditorSampleWidgetClassicTab.click();

			const ckeditorEditorBody = page
				.getByRole('tabpanel', {name: 'Classic'})
				.frameLocator('iframe[title="editor"]')
				.getByRole('heading', {name: 'Classic Editor'});

			await ckeditorEditorBody.click();

			await page.keyboard.press('Enter');

			const imageButton = page.getByLabel('Image', {exact: true});

			await imageButton.waitFor({state: 'visible'});
			await imageButton.click();

			const siteAndLibrariesLink = page
				.frameLocator('iframe[title="Select Item"]')
				.getByRole('link', {name: 'Sites and Libraries'});

			await siteAndLibrariesLink.waitFor({state: 'visible'});
			await siteAndLibrariesLink.click();

			const liferayLink = page
				.frameLocator('iframe[title="Select Item"]')
				.getByRole('link', {name: 'Liferay'});

			await liferayLink.waitFor({state: 'visible'});
			await liferayLink.click();

			const liferayImagesLink = page
				.frameLocator('iframe[title="Select Item"]')
				.getByRole('link', {name: 'Provided by Liferay'});

			await liferayImagesLink.waitFor({state: 'visible'});
			await liferayImagesLink.click();

			const astronautImage = page
				.frameLocator('iframe[title="Select Item"]')
				.getByText('astronaut.png');

			await astronautImage.waitFor({state: 'visible'});
			await astronautImage.click();

			const astronautEditorImage = page
				.getByRole('application', {name: 'Rich Text Editor,'})
				.frameLocator('iframe[title="editor"]')
				.locator('img')
				.first();

			await astronautEditorImage.waitFor({state: 'visible'});
			await astronautEditorImage.hover();

			const dragAndDropButton = page
				.getByRole('application', {name: 'Rich Text Editor,'})
				.frameLocator('iframe[title="editor"]')
				.getByTitle('Click and drag to move');

			await dragAndDropButton.dragTo(ckeditorEditorBody);

			const astronautImageElement = page
				.getByRole('application', {name: 'Rich Text Editor,'})
				.frameLocator('iframe[title="editor"]')
				.locator('h1 > * > img.cke_widget_element');

			await expect(astronautImageElement).toBeVisible();
		});
	}
);
