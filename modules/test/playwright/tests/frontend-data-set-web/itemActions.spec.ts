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

const sidePanelActionLabelWithActionTitle = 'Side Panel With Action Title';
const sidePanelActionLabelWithContentTitle = 'Side Panel With Content Title';
const sidePanelActionLabelWithActionTitleContentTitle =
	'Side Panel With Action and Content Title';
const sidePanelActionLabelWithoutTitle = 'Side Panel With No Title';
const sidePanelActionTitle = 'Side Panel Title Provided by Action';
const sidePanelContentTitle = 'Side Panel Title Provided by Page';

export const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest()
);

test.describe('Item Actions in frontend data set', () => {
	test('Side Panel Item Actions', async ({apiHelpers, page, site}) => {
		let layout: Layout;

		await test.step('Create a content site and the frontend data set sample widget', async () => {
			const widgetDefinition = getWidgetDefinition({
				id: getRandomString(),
				widgetName:
					'com_liferay_frontend_data_set_sample_web_internal_portlet_FDSSamplePortlet',
			});

			layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([widgetDefinition]),
				siteId: site.id,
				title: getRandomString(),
			});
		});

		await test.step('Select Customized tab ', async () => {
			await page.goto(
				`${liferayConfig.environment.baseUrl}/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			const tabHeading = page
				.getByRole('tablist')
				.getByText('Customized');

			await expect(tabHeading).toBeInViewport();

			await tabHeading.click();
		});

		const datasetRow =
			await test.step('Check that the Item Actions dropdown is present in table row', async () => {
				await page
					.locator('.dnd-td.item-actions')
					.first()
					.waitFor({state: 'attached'});

				const tableRow = await page
					.locator('.dnd-td.item-actions')
					.first();

				await expect(
					tableRow.getByRole('button', {
						exact: true,
						name: 'Actions',
					})
				).toBeVisible;

				const button = await tableRow.getByRole('button', {
					exact: true,
					name: 'Actions',
				});
				const dropdownId = await button.evaluate((node) =>
					node.getAttribute('aria-controls')
				);

				await button.click();

				await page
					.locator(`#${dropdownId}`)
					.filter({has: page.getByRole('menu')})
					.waitFor();

				await expect(
					page.locator(`#${dropdownId}`).getByRole('menuitem')
				).toHaveCount(13);

				await page.keyboard.press('Escape');

				return tableRow;
			});

		const itemActionButton =
			await test.step('Check that the Item Action menu is present', async () => {
				const button = await datasetRow.getByRole('button', {
					exact: true,
					name: 'Actions',
				});

				await expect(button).toBeInViewport();

				return button;
			});

		await test.step('Side Panel action opens a side panel with content title', async () => {
			const dropdownId = await itemActionButton.evaluate((node) =>
				node.getAttribute('aria-controls')
			);

			await itemActionButton.click();

			await page
				.locator(`#${dropdownId}`)
				.filter({has: page.getByRole('menu')})
				.waitFor();

			await page
				.locator(`#${dropdownId}`)
				.getByRole('menuitem', {
					exact: true,
					name: sidePanelActionLabelWithContentTitle,
				})
				.click();

			await page.getByRole('tabpanel').waitFor();

			const sidePanel = await page.getByRole('tabpanel');

			await expect(sidePanel).toBeInViewport();

			const iframeElement = await sidePanel
				.locator('iframe')
				.elementHandle();

			const frame = await iframeElement.contentFrame();

			await frame.getByText(sidePanelContentTitle).waitFor();
			await expect(frame.getByText(sidePanelContentTitle)).toHaveCount(1);

			await page.keyboard.press('Escape');

			await expect(sidePanel).not.toBeInViewport();
		});

		await test.step('Side Panel action opens a side panel with action title', async () => {
			const dropdownId = await itemActionButton.evaluate((node) =>
				node.getAttribute('aria-controls')
			);

			await itemActionButton.click();

			await page
				.locator(`#${dropdownId}`)
				.filter({has: page.getByRole('menu')})
				.waitFor();

			await page
				.locator(`#${dropdownId}`)
				.getByRole('menuitem', {
					exact: true,
					name: sidePanelActionLabelWithActionTitle,
				})
				.click();

			await page.getByRole('tabpanel').waitFor();

			const sidePanel = await page.getByRole('tabpanel');

			await expect(sidePanel).toBeInViewport();

			await page.getByText(sidePanelActionTitle).waitFor();
			await expect(page.getByText(sidePanelActionTitle)).toHaveCount(1);

			const iframeElement = await sidePanel
				.locator('iframe')
				.elementHandle();

			const frame = await iframeElement.contentFrame();

			await expect(
				frame.locator('.side-panel-iframe-header')
			).not.toBeInViewport();

			await page.keyboard.press('Escape');

			await expect(sidePanel).not.toBeInViewport();
		});

		await test.step('Side Panel action opens a side panel with duplicated title', async () => {
			const dropdownId = await itemActionButton.evaluate((node) =>
				node.getAttribute('aria-controls')
			);

			await itemActionButton.click();

			await page
				.locator(`#${dropdownId}`)
				.filter({has: page.getByRole('menu')})
				.waitFor();

			await page
				.locator(`#${dropdownId}`)
				.getByRole('menuitem', {
					exact: true,
					name: sidePanelActionLabelWithActionTitleContentTitle,
				})
				.click();

			await page.getByRole('tabpanel').waitFor();

			const sidePanel = await page.getByRole('tabpanel');

			await expect(sidePanel).toBeInViewport();

			await page.getByText(sidePanelActionTitle).waitFor();
			await expect(page.getByText(sidePanelActionTitle)).toHaveCount(1);

			const iframeElement = await sidePanel
				.locator('iframe')
				.elementHandle();

			const frame = await iframeElement.contentFrame();

			await expect(
				frame.locator('.side-panel-iframe-header')
			).toBeInViewport();
			await frame.getByText(sidePanelContentTitle).waitFor();

			await expect(frame.getByText(sidePanelContentTitle)).toHaveCount(1);

			await page.keyboard.press('Escape');

			await expect(sidePanel).not.toBeInViewport();
		});

		await test.step('Side Panel action opens a side panel without title', async () => {
			const dropdownId = await itemActionButton.evaluate((node) =>
				node.getAttribute('aria-controls')
			);

			await itemActionButton.click();

			await page
				.locator(`#${dropdownId}`)
				.filter({has: page.getByRole('menu')})
				.waitFor();

			await page
				.locator(`#${dropdownId}`)
				.getByRole('menuitem', {
					exact: true,
					name: sidePanelActionLabelWithoutTitle,
				})
				.click();

			await page.getByRole('tabpanel').waitFor();

			const sidePanel = await page.getByRole('tabpanel');

			await expect(sidePanel).toBeInViewport();

			await expect(
				page.locator('.fds-side-panel-title')
			).toBeInViewport();
			const panelTitle = await page
				.locator('.fds-side-panel-title')
				.allInnerTexts();

			await expect(panelTitle).toEqual(['']);

			const iframeElement = await sidePanel
				.locator('iframe')
				.elementHandle();

			const frame = await iframeElement.contentFrame();

			await expect(
				frame.locator('.side-panel-iframe-header')
			).not.toBeInViewport();

			await page.keyboard.press('Escape');

			await expect(sidePanel).not.toBeInViewport();
		});
	});
});
