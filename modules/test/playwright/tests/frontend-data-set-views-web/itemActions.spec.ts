/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {actionsPageTest} from './fixtures/actionsPageTest';
import {dataSetsPageTest} from './fixtures/dataSetsPageTest';
import {fdsFragmentPageTest} from './fixtures/fdsFragmentPageTest';
import {viewsPageTest} from './fixtures/viewsPageTest';

export const test = mergeTests(
	actionsPageTest,
	dataSetsPageTest,
	fdsFragmentPageTest,
	featureFlagsTest({
		'LPS-164563': true,
		'LPS-178052': true,
		'LPS-194395': true,
	}),
	loginTest,
	viewsPageTest
);

test.describe('Data Set Item Actions', () => {
	test('Create a Link Item Action', async ({
		actionsPage,
		dataSetsPage,
		page,
		viewsPage,
	}) => {
		const DATASET_NAME = 'Item Actions DS';
		const DATASET_VIEW_NAME = 'Item Actions DS View';
		const LINK_ITEM_ACTION_NAME = 'Link item action';

		await test.step('Create Data Set', async () => {
			await dataSetsPage.goto();
			await dataSetsPage.createDataSet({name: DATASET_NAME});
		});

		await test.step('Create Data Set View', async () => {
			await viewsPage.goto(DATASET_NAME);
			await viewsPage.createDataSetView({name: DATASET_VIEW_NAME});
		});

		await test.step('Go to Actions tab', async () => {
			await actionsPage.goto({
				dataSetName: DATASET_NAME,
				dataSetViewName: DATASET_VIEW_NAME,
			});
		});

		await test.step('Create an item action', async () => {
			await actionsPage.createItemAction({
				icon: 'link',
				name: LINK_ITEM_ACTION_NAME,
				type: 'link',
				url: 'https://www.liferay.com/',
			});
		});

		await test.step('Check that the item action is in the list', async () => {
			await expect(
				page
					.getByRole('cell', {
						exact: true,
						name: LINK_ITEM_ACTION_NAME,
					})
					.locator('span')
					.first()
			).toBeVisible();
		});

		await test.step('Delete Data Set', async () => {
			await dataSetsPage.deleteDataSet(DATASET_NAME);
		});
	});

	test('Link Item Action is shown in fragment', async ({
		actionsPage,
		dataSetsPage,
		fdsFragmentPage,
		page,
		viewsPage,
	}) => {
		const DATASET_NAME = 'Item Actions DS';
		const DATASET_VIEW_NAME = 'Item Actions DS View';
		const LINK_ITEM_ACTION_CONFIRMATION_MESSAGE =
			'Do you want to navigate to http://www.liferay.com?';
		const LINK_ITEM_ACTION_NAME = 'Link item action';
		const PAGE_NAME = 'Test page';
		const SITE_NAME = 'FDSFragmentSite';

		await test.step('Create Data Set', async () => {
			await dataSetsPage.goto();
			await dataSetsPage.createDataSet({name: DATASET_NAME});
		});

		await test.step('Create Data Set View', async () => {
			await viewsPage.goto(DATASET_NAME);
			await viewsPage.createDataSetView({name: DATASET_VIEW_NAME});
		});

		await test.step('Go to Actions tab', async () => {
			await actionsPage.goto({
				dataSetName: DATASET_NAME,
				dataSetViewName: DATASET_VIEW_NAME,
			});
		});

		await test.step('Create a link item action', async () => {
			await actionsPage.createItemAction({
				confirmationMessage: LINK_ITEM_ACTION_CONFIRMATION_MESSAGE,
				icon: 'link',
				name: LINK_ITEM_ACTION_NAME,
				type: 'link',
				url: 'https://www.liferay.com/',
			});
		});

		const siteInfo =
			await test.step('Go home and create a new site', async () => {
				await page.goto('/');

				const newSite = await fdsFragmentPage.createSite(SITE_NAME);

				const pageLayout = await fdsFragmentPage.createPage({
					siteId: newSite.id,
					title: PAGE_NAME,
				});

				await page.reload();

				return {layout: pageLayout, site: newSite};
			});

		await test.step('Edit page', async () => {
			await fdsFragmentPage.editPage({...siteInfo});
		});

		await test.step('Search for "Data Set" fragment', async () => {
			await fdsFragmentPage.searchFragmentOrWidget('Data Set');
		});

		await test.step('Drag "Data Set" fragment & Drop into the page editor w/ keyboard', async () => {
			await fdsFragmentPage.dragAndDropFragment(
				'Data Set Add Data Set Mark Data Set as Favorite'
			);
		});

		await test.step('Select empty Data Set fragment', async () => {
			await page
				.getByText('Select a data set view. Beta')
				.first()
				.click();
		});

		await test.step('Open Data Set View Selector', async () => {
			await page
				.getByRole('button', {name: 'Select Data Set View'})
				.click();
		});

		await test.step('Select Data Set View', async () => {
			await expect(page.getByRole('dialog')).toBeVisible();

			await expect(
				page.getByRole('heading', {name: 'Select'})
			).toBeVisible();

			await page
				.frameLocator('iframe[title="Select"]')
				.locator('li')
				.filter({hasText: DATASET_VIEW_NAME})
				.first()
				.click();

			await page
				.frameLocator('iframe[title="Select"]')
				.getByRole('button', {name: 'Save'})
				.click();
		});

		await test.step('Publish page with Data Set View', async () => {
			await fdsFragmentPage.publishPage();

			await fdsFragmentPage.gotoPage({...siteInfo});

			await expect(page.locator('.data-set-wrapper')).toBeVisible();
		});

		await test.step('Item action are present in table row', async () => {
			const dialogPromise = page
				.waitForEvent('dialog')
				.then(async (dialog) => {
					await dialog.accept();

					return dialog.message();
				});

			const tableRow = await page.locator('.dnd-td.item-actions').first();

			await expect(tableRow.getByRole('link')).toBeVisible();

			await tableRow.getByRole('link').click();

			const confirmationMessage = await dialogPromise;

			expect(confirmationMessage).toBe(
				LINK_ITEM_ACTION_CONFIRMATION_MESSAGE
			);

			await page.waitForURL('https://www.liferay.com');

			await expect(page.url()).toContain('https://www.liferay.com');
		});

		await test.step('Delete Data Set and site', async () => {
			await fdsFragmentPage.deleteSite(siteInfo.site.id);
			await dataSetsPage.deleteDataSet(DATASET_NAME);
		});
	});

	test('Link, Modal and Side Panel Item Actions are shown in fragment', async ({
		actionsPage,
		dataSetsPage,
		fdsFragmentPage,
		page,
		viewsPage,
	}) => {
		const DATASET_NAME = 'Item Actions DS';
		const DATASET_VIEW_NAME = 'Item Actions DS View';
		const LINK_ITEM_ACTION_NAME = 'Link item action';
		const MODAL_ITEM_ACTION_NAME = 'Modal item action';
		const MODAL_ITEM_ACTION_TITLE = 'Modal title';
		const PAGE_NAME = 'Test page';
		const SITE_NAME = 'FDSFragmentSite';
		const SIDE_PANEL_ITEM_ACTION_NAME = 'SidePanel item action';
		const SIDE_PANEL_ITEM_ACTION_URL = '/home';

		const siteInfo =
			await test.step('Go home and create a new site and page', async () => {
				await page.goto('/');

				const newSite = await fdsFragmentPage.createSite(SITE_NAME);

				const pageLayout = await fdsFragmentPage.createPage({
					siteId: newSite.id,
					title: PAGE_NAME,
				});

				await page.reload();

				return {layout: pageLayout, site: newSite};
			});

		await test.step('Create Data Set', async () => {
			await dataSetsPage.goto();
			await dataSetsPage.createDataSet({name: DATASET_NAME});
		});

		await test.step('Create Data Set View', async () => {
			await viewsPage.goto(DATASET_NAME);
			await viewsPage.createDataSetView({name: DATASET_VIEW_NAME});
		});

		// Missing (Add fields)

		await test.step('Go to Actions tab', async () => {
			await actionsPage.goto({
				dataSetName: DATASET_NAME,
				dataSetViewName: DATASET_VIEW_NAME,
			});
		});

		await test.step('Create a link item action', async () => {
			await actionsPage.createItemAction({
				icon: 'link',
				name: LINK_ITEM_ACTION_NAME,
				type: 'link',
				url: 'https://www.liferay.com/',
			});
		});

		await test.step('Create a modal item action', async () => {
			await actionsPage.createItemAction({
				icon: 'cloud',
				name: MODAL_ITEM_ACTION_NAME,
				title: MODAL_ITEM_ACTION_TITLE,
				type: 'modal',
				url: '/home',
			});
		});

		await test.step('Create a side panel item action', async () => {
			await actionsPage.createItemAction({
				icon: 'container',
				name: SIDE_PANEL_ITEM_ACTION_NAME,
				title: SIDE_PANEL_ITEM_ACTION_NAME,
				type: 'sidePanel',
				url: SIDE_PANEL_ITEM_ACTION_URL,
			});
		});

		await test.step('Edit page', async () => {
			await fdsFragmentPage.editPage({...siteInfo});
		});

		await test.step('Search for "Data Set" fragment', async () => {
			await fdsFragmentPage.searchFragmentOrWidget('Data Set');
		});

		await test.step('Drag "Data Set" fragment & Drop into the page editor w/ keyboard', async () => {
			await fdsFragmentPage.dragAndDropFragment(
				'Data Set Add Data Set Mark Data Set as Favorite'
			);
		});

		await test.step('Select empty Data Set fragment', async () => {
			await page
				.getByText('Select a data set view. Beta')
				.first()
				.click();
		});

		await test.step('Open Data Set View Selector', async () => {
			await page
				.getByRole('button', {name: 'Select Data Set View'})
				.click();
		});

		await test.step('Select Data Set View', async () => {
			await expect(page.getByRole('dialog')).toBeVisible();

			await expect(
				page.getByRole('heading', {name: 'Select'})
			).toBeVisible();

			await page
				.frameLocator('iframe[title="Select"]')
				.locator('li')
				.filter({hasText: DATASET_VIEW_NAME})
				.first()
				.click();

			await page
				.frameLocator('iframe[title="Select"]')
				.getByRole('button', {name: 'Save'})
				.click();
		});

		await test.step('Publish page with Data Set View', async () => {
			await fdsFragmentPage.publishPage();

			await fdsFragmentPage.gotoPage({...siteInfo});

			await expect(page.locator('.data-set-wrapper')).toBeVisible();
		});

		const datasetRow =
			await test.step('Item actions dropdown is present in table row', async () => {
				const tableRow = await page
					.locator('.dnd-td.item-actions')
					.first();

				await expect(
					tableRow.getByRole('button', {exact: true, name: 'Actions'})
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
				).toHaveCount(3);

				await page.keyboard.press('Escape');

				return tableRow;
			});

		await test.step('Click the modal item action opens a modal window', async () => {
			const button = await datasetRow.getByRole('button', {
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

			await page
				.locator(`#${dropdownId}`)
				.getByRole('menuitem', {
					exact: true,
					name: MODAL_ITEM_ACTION_NAME,
				})
				.click();

			await page.getByRole('dialog').waitFor();

			const dialog = await page.getByRole('dialog');
			await expect(dialog.getByRole('heading')).toHaveText(
				MODAL_ITEM_ACTION_TITLE
			);

			await dialog.getByRole('button', {name: 'close'}).click();

			await expect(dialog).not.toBeInViewport();
		});

		await test.step('Click the side panel item action opens a side panel', async () => {
			const button = await datasetRow.getByRole('button', {
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

			await page
				.locator(`#${dropdownId}`)
				.getByRole('menuitem', {
					exact: true,
					name: SIDE_PANEL_ITEM_ACTION_NAME,
				})
				.click();

			await page.getByRole('tabpanel').waitFor();

			const sidePanel = await page.getByRole('tabpanel');

			const iframeElement = await sidePanel
				.locator('iframe')
				.elementHandle();

			const frame = await iframeElement.contentFrame();

			await frame.waitForURL(
				new RegExp(`.*${SIDE_PANEL_ITEM_ACTION_URL}`, 'i')
			);

			await page.keyboard.press('Escape');

			await expect(sidePanel).not.toBeInViewport();
		});

		await test.step('Delete Data Set and site', async () => {
			await fdsFragmentPage.deleteSite(siteInfo.site.id);
			await dataSetsPage.deleteDataSet(DATASET_NAME);
		});
	});

	test('Async and Headless Item Actions are shown in fragment', async ({
		actionsPage,
		dataSetsPage,
		fdsFragmentPage,
		page,
		viewsPage,
	}) => {
		const DATASET_NAME = 'Item Actions DS';
		const DATASET_VIEW_NAME = 'Item Actions DS View';
		const ASYNC_ITEM_ACTION_NAME = 'Async item action';
		const ASYNC_ITEM_ACTION_METHOD = 'DELETE';
		const ASYNC_ITEM_ACTION_URL = '/o/data-set-manager/fields/{id}';
		const HEADLESS_ITEM_ACTION_NAME = 'Headless item action';
		const HEADLESS_ITEM_ACTION_PERMISSION_KEY = 'delete';
		const NON_AVAILABLE_HEADLESS_ITEM_ACTION_NAME =
			'Useless Headless Item Action';
		const PAGE_NAME = 'Test page';
		const SITE_NAME = 'FDSFragmentSite';

		const siteInfo =
			await test.step('Go home and create a new site and page', async () => {
				await page.goto('/');

				const newSite = await fdsFragmentPage.createSite(SITE_NAME);

				const pageLayout = await fdsFragmentPage.createPage({
					siteId: newSite.id,
					title: PAGE_NAME,
				});

				await page.reload();

				return {layout: pageLayout, site: newSite};
			});

		await test.step('Create Data Set', async () => {
			await dataSetsPage.goto();
			await dataSetsPage.createDataSet({name: DATASET_NAME});
		});

		await test.step('Create Data Set View', async () => {
			await viewsPage.goto(DATASET_NAME);
			await viewsPage.createDataSetView({name: DATASET_VIEW_NAME});
		});

		// Manually adding fields (with LPD-10735=false / visualization modes)

		await test.step('Open Data Set Fields Tab', async () => {
			await viewsPage.gotoDataSetView(DATASET_VIEW_NAME);

			await page.getByRole('button', {name: 'Fields'}).first().waitFor();

			await page.getByRole('button', {name: 'Fields'}).first().click();
		});

		const fieldModal =
			await test.step('Open Data Set Fields Modal', async () => {
				page.getByRole('button', {
					name: 'Add Fields',
				})
					.first()
					.waitFor();

				page.getByRole('button', {
					name: 'Add Fields',
				})
					.first()
					.click();

				page.getByRole('heading', {
					name: 'Add Fields',
				}).waitFor;

				return page.getByRole('dialog');
			});

		await test.step('Select id and name fields', async () => {
			await fieldModal.getByLabel(/id/).check();
			await fieldModal.getByLabel(/name/).check();
			await fieldModal.getByRole('button', {name: 'Save'}).click();

			await expect(
				page.locator('.orderable-table-row').nth(0)
			).toContainText('id');
			await expect(
				page.locator('.orderable-table-row').nth(1)
			).toContainText('name');
		});

		// Finish manually adding fields

		await test.step('Go to Actions tab', async () => {
			await actionsPage.goto({
				dataSetName: DATASET_NAME,
				dataSetViewName: DATASET_VIEW_NAME,
			});
		});

		await test.step('Create a headless item action', async () => {
			await actionsPage.createItemAction({
				icon: 'link',
				name: HEADLESS_ITEM_ACTION_NAME,
				permissionKey: HEADLESS_ITEM_ACTION_PERMISSION_KEY,
				type: 'headless',
			});
		});

		await test.step('Create a non available headless item action', async () => {
			await actionsPage.createItemAction({
				icon: 'link',
				name: NON_AVAILABLE_HEADLESS_ITEM_ACTION_NAME,
				permissionKey: 'remove',
				type: 'headless',
			});
		});

		await test.step('Create an async item action', async () => {
			await actionsPage.createItemAction({
				icon: 'cloud',
				method: ASYNC_ITEM_ACTION_METHOD,
				name: ASYNC_ITEM_ACTION_NAME,
				type: 'async',
				url: ASYNC_ITEM_ACTION_URL,
			});
		});

		await test.step('Edit page', async () => {
			await fdsFragmentPage.editPage({...siteInfo});
		});

		await test.step('Search for "Data Set" fragment', async () => {
			await fdsFragmentPage.searchFragmentOrWidget('Data Set');
		});

		await test.step('Drag "Data Set" fragment & Drop into the page editor w/ keyboard', async () => {
			await fdsFragmentPage.dragAndDropFragment(
				'Data Set Add Data Set Mark Data Set as Favorite'
			);
		});

		await test.step('Select empty Data Set fragment', async () => {
			await page
				.getByText('Select a data set view. Beta')
				.first()
				.click();
		});

		await test.step('Open Data Set View Selector', async () => {
			await page
				.getByRole('button', {name: 'Select Data Set View'})
				.click();
		});

		await test.step('Select Data Set View', async () => {
			await expect(page.getByRole('dialog')).toBeVisible();

			await expect(
				page.getByRole('heading', {name: 'Select'})
			).toBeVisible();

			await page
				.frameLocator('iframe[title="Select"]')
				.locator('li')
				.filter({hasText: DATASET_VIEW_NAME})
				.first()
				.click();

			await page
				.frameLocator('iframe[title="Select"]')
				.getByRole('button', {name: 'Save'})
				.click();
		});

		await test.step('Publish page with Data Set View', async () => {
			await fdsFragmentPage.publishPage();

			await fdsFragmentPage.gotoPage({...siteInfo});

			await expect(page.locator('.data-set-wrapper')).toBeVisible();
		});

		const datasetRow =
			await test.step('Item actions dropdown is present in table row', async () => {
				const tableRow = await page
					.locator('.dnd-td.item-actions')
					.first();

				await expect(
					tableRow.getByRole('button', {exact: true, name: 'Actions'})
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
				).toHaveCount(2);

				await expect(
					page.locator(`#${dropdownId}`).getByRole('menuitem', {
						name: NON_AVAILABLE_HEADLESS_ITEM_ACTION_NAME,
					})
				).not.toBeVisible();

				await page.keyboard.press('Escape');

				return tableRow;
			});

		await test.step('Click in the headless item action executes the action', async () => {
			const button = await datasetRow.getByRole('button', {
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

			await page
				.locator(`#${dropdownId}`)
				.getByRole('menuitem', {
					exact: true,
					name: HEADLESS_ITEM_ACTION_NAME,
				})
				.click();

			await page.getByRole('alert').waitFor();

			const alert = await page.getByRole('alert');

			await expect(alert).toHaveText(
				'Success:Your request completed successfully.'
			);
		});

		await test.step('Click in the async item action executes the action', async () => {
			const nextTableRow = await page
				.locator('.dnd-td.item-actions')
				.first();

			const button = await nextTableRow.getByRole('button', {
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

			await page
				.locator(`#${dropdownId}`)
				.getByRole('menuitem', {
					exact: true,
					name: ASYNC_ITEM_ACTION_NAME,
				})
				.click();

			await page.getByRole('alert').waitFor();

			const alert = await page.getByRole('alert');

			await expect(alert).toHaveText(
				'Success:Your request completed successfully.'
			);
		});

		await test.step('Delete Data Set and site', async () => {
			await fdsFragmentPage.deleteSite(siteInfo.site.id);
			await dataSetsPage.deleteDataSet(DATASET_NAME);
		});
	});
});
