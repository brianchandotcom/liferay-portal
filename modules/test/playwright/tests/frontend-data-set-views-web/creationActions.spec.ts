/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {liferayConfig} from '../../liferay.config';
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
		'LPS-186871': true,
		'LPS-194395': true,
	}),
	loginTest(),
	viewsPageTest
);

test.describe('Create Creation Actions on a Data Set and show them in a fragment', () => {
	const LINK_CREATION_ACTION_NAME = 'Link creation action';
	const MODAL_CREATION_ACTION_NAME = 'Modal creation action';
	let site: Site;
	let layout: Layout;

	test('Create a Creation Action', async ({
		actionsPage,
		dataSetsPage,
		page,
		viewsPage,
	}) => {
		await test.step('Create Data Set', async () => {
			await dataSetsPage.goto();
			await dataSetsPage.createDataSet();
		});

		await test.step('Create Data Set View', async () => {
			await viewsPage.goto();
			await viewsPage.createDataSetView();
		});

		await test.step('Go to Actions tab', async () => {
			await actionsPage.goto();
		});

		await test.step('Create a creation action', async () => {
			await actionsPage.createCreationAction({
				icon: 'arrow-right-full',
				name: LINK_CREATION_ACTION_NAME,
				type: 'link',
				url: liferayConfig.environment.baseUrl,
			});
		});

		await test.step('Check that the creation action is in the list', async () => {
			await expect(
				page
					.getByRole('cell', {
						exact: true,
						name: LINK_CREATION_ACTION_NAME,
					})
					.locator('span')
					.first()
			).toBeVisible();
		});
	});

	test('Show a single Creation Action in fragment', async ({
		fdsFragmentPage,
		page,
	}) => {
		await fdsFragmentPage.goto();

		site = await fdsFragmentPage
			.createSite('FDSFragment')
			.then((response) => response);

		layout = await fdsFragmentPage
			.createPage({
				siteId: site.id,
				title: 'fdsfragmentpagetest',
			})
			.then((response) => response);

		await page.reload();

		await test.step('Edit page', async () => {
			await fdsFragmentPage.editPage({layout, site});
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
			if (
				await page
					.getByRole('menuitem', {name: 'Select Data Set View...'})
					.isVisible()
			) {
				await page
					.getByRole('menuitem', {name: 'Select Data Set View...'})
					.click();
			}

			await expect(page.getByRole('dialog')).toBeVisible();
			await expect(
				page.getByRole('heading', {name: 'Select'})
			).toBeVisible();
			await page
				.frameLocator('iframe[title="Select"]')
				.locator('li')
				.filter({hasText: 'Data Set View Sample'})
				.first()
				.click();
			await page
				.frameLocator('iframe[title="Select"]')
				.getByRole('button', {name: 'Save'})
				.click();
		});

		await test.step('Publish page with Data Set View', async () => {
			await fdsFragmentPage.publishPage();
			await fdsFragmentPage.goToPage({layout, site});

			await page.locator('.data-set-wrapper').waitFor();
		});

		await test.step('Creation action are present in fragment', async () => {
			await expect(
				page.getByLabel(LINK_CREATION_ACTION_NAME)
			).toBeVisible();
		});

		await test.step('Creation action is actionable', async () => {
			await page.getByLabel(LINK_CREATION_ACTION_NAME).click();

			await expect(page.getByText('Welcome to Liferay')).toBeVisible();
		});
	});

	test('Show Creation Actions menu in fragment', async ({
		actionsPage,
		fdsFragmentPage,
		page,
	}) => {
		await test.step('Go to Actions tab', async () => {
			await actionsPage.goto();
		});

		await test.step('Create another creation action', async () => {
			await actionsPage.createCreationAction({
				icon: 'arrow-right-full',
				name: MODAL_CREATION_ACTION_NAME,
				type: 'modal',
				url: liferayConfig.environment.baseUrl,
			});
		});

		await test.step('Check that the creation action is in the list', async () => {
			await expect(
				page
					.getByRole('cell', {
						exact: true,
						name: MODAL_CREATION_ACTION_NAME,
					})
					.locator('span')
					.first()
			).toBeVisible();
		});

		await test.step('Go to new site home page', async () => {
			await fdsFragmentPage.goToPage({layout, site});

			await page.locator('.data-set-wrapper').waitFor();
		});

		await test.step('Creation action menu are present in fragment', async () => {
			await page.getByLabel('New').click();
			await expect(
				page.getByRole('menuitem', {name: 'Link creation action'})
			).toBeVisible();
		});

		await test.step('Creation action is actionable', async () => {
			await page
				.getByRole('menuitem', {name: 'Link creation action'})
				.click();

			await expect(page.getByText('Welcome to Liferay')).toBeVisible();
		});
	});

	test('Sample data clean up', async ({dataSetsPage, fdsFragmentPage}) => {
		await fdsFragmentPage.deleteSite(site.id);

		await test.step('Delete Data Set', async () => {
			await dataSetsPage.goto();
			await dataSetsPage.deleteDataSet();
		});
	});
});
