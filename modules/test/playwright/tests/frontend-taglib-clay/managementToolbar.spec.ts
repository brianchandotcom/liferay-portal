/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, expect, mergeTests} from '@playwright/test';

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

test.beforeEach(
	'Setup site and Clay Sample widget',
	async ({apiHelpers, page, site}) => {
		let layout: Layout;

		await test.step('Create a content site and the frontend taglib clay widget', async () => {
			const widgetDefinition = getWidgetDefinition({
				id: getRandomString(),
				widgetName:
					'com_liferay_clay_sample_web_portlet_ClaySamplePortlet',
			});

			layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([widgetDefinition]),
				siteId: site.id,
				title: getRandomString(),
			});
		});

		await test.step('Select Management Toolbars tab', async () => {
			await page.goto(
				`${liferayConfig.environment.baseUrl}/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			const tabHeading = page
				.getByRole('tablist')
				.getByText('Management Toolbars');

			await expect(tabHeading).toBeVisible();

			await tabHeading.click();
		});
	}
);

test.describe('Management Toolbar Default State', () => {
	test('Assert the New button is displayed as a text button @LPS-144540', async ({
		page,
	}) => {
		await test.step('Check that the new button text is "New"', async () => {
			await expect(
				page
					.locator('#managementToolbarDefaultState')
					.getByRole('button')
					.filter({hasText: 'New'})
			).toBeVisible();
		});
	});

	test('Assert the tooltip message is displayed when hovered over the new button in responsive mode @LPS-144540', async ({
		page,
	}) => {
		await test.step('Set the window size to phone size', async () => {
			await page.setViewportSize({height: 720, width: 360});
		});

		await test.step('Hover over the "New" button', async () => {
			await page
				.locator('#managementToolbarDefaultState')
				.getByRole('button', {name: 'New'})
				.hover();
		});

		await test.step('Check the tooltip text displays "New"', async () => {
			await expect(
				page.locator('.tooltip-inner').getByText('New')
			).toBeVisible();
		});
	});

	test('Assert the view button displays a double caret icon @LPS-144535', async ({
		page,
	}) => {
		await test.step('Check that the double caret icon is visible', async () => {
			await expect(
				page.locator(
					'#managementToolbarDefaultState .lexicon-icon-caret-double-l'
				)
			).toBeVisible();
		});
	});

	test('Assert the tooltip message is displayed when hovered over the view button @LPS-144535', async ({
		page,
	}) => {
		await test.step('Hover over the view button', async () => {
			await page
				.locator('#managementToolbarDefaultState')
				.getByLabel(/Select View/)
				.hover();
		});

		await test.step('Check the tooltip text is displayed', async () => {
			await expect(page.getByText(/Select View/)).toBeVisible();
		});
	});
});

test.describe('Management Toolbar With Results', () => {
	test('Clear button has a cursor of type pointer', async ({page}) => {
		let clearButton: Locator;

		await test.step('Get the clear button', async () => {
			clearButton = page
				.locator('#managementToolbarWithResultsBar')
				.getByLabel('Clear');

			await expect(clearButton).toBeVisible();
		});

		await test.step('Check that cursor type is a pointer', async () => {
			const cursorType = await clearButton.evaluate((element) =>
				window.getComputedStyle(element).getPropertyValue('cursor')
			);

			await expect(cursorType).toEqual('pointer');
		});
	});
});

test.describe('Management Toolbar Active State', () => {
	test('Assert the items in the actions ellipsis are displayed @LPS-144538', async ({
		page,
	}) => {
		let actionsButton: Locator;

		await test.step('Check that the ellipsis actions button is visible', async () => {
			actionsButton = page
				.locator('#managementToolbarActiveState')
				.getByRole('button')
				.nth(3);

			await expect(actionsButton).toBeVisible();
		});
		await test.step('Click on the actions button', async () => {
			await actionsButton.click();
		});

		await test.step('Check that the "Edit" button visible', async () => {
			await expect(
				page.getByRole('menuitem', {name: 'Edit'})
			).toBeVisible();
		});

		await test.step('Check that the "Download" button and icon is visible', async () => {
			await expect(
				page.getByRole('menuitem', {name: 'Download'})
			).toBeVisible();

			await expect(
				page
					.getByRole('menuitem', {name: 'Download'})
					.locator('.lexicon-icon-download')
			).toBeVisible();
		});

		await test.step('Check that the "Delete" button and icon is visible', async () => {
			await expect(
				page.getByRole('menuitem', {name: 'Delete'})
			).toBeVisible();

			await expect(
				page
					.getByRole('menuitem', {name: 'Delete'})
					.locator('.lexicon-icon-trash')
			).toBeVisible();
		});
	});
});
