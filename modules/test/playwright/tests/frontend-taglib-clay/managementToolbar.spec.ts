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
	test(
		'Assert the "New" button is displayed properly',
		{tag: '@LPS-144540'},
		async ({page}) => {
			await test.step('Check that the new button text is "New"', async () => {
				await expect(
					page
						.locator('#managementToolbarDefaultState')
						.getByRole('button')
						.filter({hasText: 'New'})
				).toBeVisible();
			});

			await test.step('Set the window size to phone size', async () => {
				await page.setViewportSize({height: 720, width: 360});
			});

			await test.step('Check the button is an icon', async () => {
				await expect(
					page
						.locator('#managementToolbarDefaultState')
						.getByRole('button', {name: 'New'})
						.locator('.lexicon-icon-plus')
				).toBeVisible();
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
		}
	);

	test(
		'Assert tooltip messages will be displayed when hovered over the filter and order buttons in responsive mode',
		{tag: '@LPS-144536'},
		async ({page}) => {
			await test.step('Set the window size to phone size', async () => {
				await page.setViewportSize({height: 720, width: 360});
			});

			await test.step('Hover over the "Filter" button', async () => {
				await page
					.locator('#managementToolbarDefaultState')
					.getByRole('button', {name: 'Filter'})
					.hover();
			});

			await test.step('Check the tooltip text displays "Show Filter Options"', async () => {
				await expect(
					page
						.locator('.tooltip-inner')
						.getByText('Show Filter Options')
				).toBeVisible();
			});

			await test.step('Hover over the "Order" button', async () => {
				await page
					.locator('#managementToolbarDefaultState')
					.getByRole('button', {name: 'Order'})
					.hover();
			});

			await test.step('Check the tooltip text displays "Show Order Options"', async () => {
				await expect(
					page
						.locator('.tooltip-inner')
						.getByText('Show Order Options')
				).toBeVisible();
			});
		}
	);

	test(
		'Assert the view button is properly displayed',
		{tag: '@LPS-144535'},
		async ({page}) => {
			await test.step('Check that the double caret icon is visible', async () => {
				await expect(
					page.locator(
						'#managementToolbarDefaultState .lexicon-icon-caret-double-l'
					)
				).toBeVisible();
			});

			await test.step('Hover over the view button', async () => {
				await page
					.locator('#managementToolbarDefaultState')
					.getByLabel(/Select View/)
					.hover();
			});

			await test.step('Check the tooltip text is displayed', async () => {
				await expect(page.getByText(/Select View/)).toBeVisible();
			});
		}
	);
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
	test(
		'Assert the items in the actions ellipsis are displayed',
		{tag: '@LPS-144538'},
		async ({page}) => {
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
		}
	);

	test(
		'Assert the action buttons are displayed properly in responsive mode',
		{tag: '@LPS-144538'},
		async ({page}) => {
			const managementToolbarActiveStateContainer = page.locator(
				'#managementToolbarActiveState'
			);

			await test.step('Set the window size to tablet size', async () => {
				await page.setViewportSize({height: 1024, width: 800});
			});

			await test.step('Check that the text is not visible', async () => {
				await expect(
					managementToolbarActiveStateContainer.getByText('Download')
				).not.toBeVisible();

				await expect(
					managementToolbarActiveStateContainer.getByText('Delete')
				).not.toBeVisible();
			});

			await test.step('Check that the icon is visible', async () => {

				// This counts the icons because for responsiveness there is one
				// button with an icon only and another button with icon and text
				// where one should be visible at a time.

				const downloadIconsLocator =
					managementToolbarActiveStateContainer.locator(
						'.lexicon-icon-download'
					);

				const downloadIcons = await downloadIconsLocator.all();

				let downloadIconVisibleCount = 0;

				for (const downloadIcon of downloadIcons) {
					if (await downloadIcon.isVisible()) {
						downloadIconVisibleCount++;
					}
				}

				expect(downloadIconVisibleCount).toEqual(1);

				const trashIconsLocator =
					managementToolbarActiveStateContainer.locator(
						'.lexicon-icon-trash'
					);

				const trashIcons = await trashIconsLocator.all();

				let trashIconVisibleCount = 0;

				for (const trashIcon of trashIcons) {
					if (await trashIcon.isVisible()) {
						trashIconVisibleCount++;
					}
				}

				expect(trashIconVisibleCount).toEqual(1);
			});

			await test.step('Hover over the download button', async () => {
				await page
					.locator('#managementToolbarActiveState')
					.getByRole('button', {name: 'Download'})
					.hover();
			});

			await test.step('Check the tooltip text is displayed', async () => {
				await expect(
					page.locator('.tooltip-inner').getByText('Download')
				).toBeVisible();
			});

			await test.step('Hover over the delete button', async () => {
				await page
					.locator('#managementToolbarActiveState')
					.getByRole('link', {name: 'Delete'})
					.hover();
			});

			await test.step('Check the tooltip text is displayed', async () => {
				await expect(
					page.locator('.tooltip-inner').getByText('Delete')
				).toBeVisible();
			});
		}
	);

	test(
		'Assert the clear button will be displayed properly in responsive mode',
		{tag: '@LPS-144539'},
		async ({page}) => {
			await test.step('Set the window size to phone size', async () => {
				await page.setViewportSize({height: 720, width: 360});
			});

			await test.step('Check that the times circle icon is visible', async () => {
				await expect(
					page.locator(
						'#managementToolbarActiveState .lexicon-icon-times-circle'
					)
				).toBeVisible();
			});

			await test.step('Hover over the clear button', async () => {
				await page
					.locator('#managementToolbarActiveState')
					.getByRole('button', {name: 'Clear'})
					.hover();
			});

			await test.step('Check the tooltip text is displayed', async () => {
				await expect(
					page.locator('.tooltip-inner').getByText('Clear')
				).toBeVisible();
			});
		}
	);
});

test.describe('Management Toolbar Using Display Context', () => {
	test(
		'Assert the order button can display the correct icons',
		{tag: '@LPS-144536'},
		async ({page}) => {
			const managementToolbarUsingDisplayContextContainer = page.locator(
				'#managementToolbarUsingDisplayContext'
			);

			await test.step('Open the order dropdown', async () => {
				await managementToolbarUsingDisplayContextContainer
					.getByRole('button', {name: 'Order'})
					.click();
			});

			await test.step('Check that ascending order is selected', async () => {
				await expect(
					page
						.getByRole('menuitem', {name: 'Ascending'})
						.locator('.lexicon-icon-check')
				).toBeVisible();
			});

			await test.step('Check that the icon is order-list-up', async () => {
				expect(
					page
						.getByRole('button', {name: 'Order'})
						.locator('.lexicon-icon-order-list-up')
						.first()
				).toBeVisible();
			});

			await test.step('Click on descending', async () => {
				await page.getByRole('menuitem', {name: 'Descending'}).click();
			});

			await test.step('Navigate to management toolbar tab', async () => {
				await page
					.getByRole('tablist')
					.getByText('Management Toolbars')
					.click();
			});

			await test.step('Open the order dropdown', async () => {
				await managementToolbarUsingDisplayContextContainer
					.getByRole('button', {name: 'Order'})
					.click();
			});

			await test.step('Check that descending order is selected', async () => {
				await expect(
					page
						.getByRole('menuitem', {name: 'Descending'})
						.locator('.lexicon-icon-check')
				).toBeVisible();
			});

			await test.step('Check that the icon is order-list-down', async () => {
				expect(
					page
						.getByRole('button', {name: 'Order'})
						.locator('.lexicon-icon-order-list-down')
						.first()
				).toBeVisible();
			});
		}
	);
});
