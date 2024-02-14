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
			const tableRow = await page.locator('.dnd-td.item-actions').first();

			await expect(tableRow.getByRole('link')).toBeVisible();

			await tableRow.getByRole('link').click();

			await page.waitForURL('https://www.liferay.com');
			await expect(page.url()).toContain('https://www.liferay.com');
		});

		await test.step('Delete Data Set and site', async () => {
			await fdsFragmentPage.deleteSite(siteInfo.site.id);
			await dataSetsPage.deleteDataSet(DATASET_NAME);
		});
	});
});
