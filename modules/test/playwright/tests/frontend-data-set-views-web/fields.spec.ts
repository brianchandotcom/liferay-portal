/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {dataSetsPageTest} from './fixtures/dataSetsPageTest';
import {fdsFragmentPageTest} from './fixtures/fdsFragmentPageTest';
import {fieldsPageTest} from './fixtures/fieldsPageTest';
import {viewsPageTest} from './fixtures/viewsPageTest';
import {DEFAULT_LABEL} from './utils/constants';

export const test = mergeTests(
	dataSetsPageTest,
	fdsFragmentPageTest,
	featureFlagsTest({
		'LPD-10735': false,
		'LPS-164563': true,
		'LPS-178052': true,
		'LPS-186871': true,
	}),
	fieldsPageTest,
	loginTest(),
	viewsPageTest
);

test.describe('Add fields to a view and show them in a fragment', () => {
	test('Add fields to a view', async ({
		dataSetsPage,
		fieldsPage,
		page,
		viewsPage,
	}) => {
		await test.step('Create sample DataSet', async () => {
			await dataSetsPage.goto();
			await dataSetsPage.createDataSet({
				name: DEFAULT_LABEL.DATA_SET,
				restApplication: '/headless-delivery/v1.0',
				restEndpoint: '/v1.0/sites/{siteId}/site-pages',
				restSchema: 'SitePage',
			});
		});

		await test.step('Create sample DataSet View', async () => {
			await viewsPage.goto();
			await viewsPage.createDataSetView();
		});

		await test.step('Open modal to add fields', async () => {
			await fieldsPage.goto();
			await fieldsPage.openAddFieldsModal();
		});

		await test.step('Check fields in treeview', async () => {
			await fieldsPage.addRootField('dateCreated');
			await fieldsPage.addRootField('title');
			await fieldsPage.addRootField('creator');
			await fieldsPage.addChildField(['creator'], 'name');
			await fieldsPage.addChildField(['creator'], 'id');
		});

		await test.step('Save changes', async () => {
			await fieldsPage.saveAddFieldsModal();
		});

		await test.step('Fields are present in table', async () => {
			await expect(page.getByText('dateCreated').first()).toBeVisible();
			await expect(page.getByText('title').first()).toBeVisible();
			await expect(page.getByText('creator.*').first()).toBeVisible();
			await expect(page.getByText('creator.id').first()).toBeVisible();
			await expect(page.getByText('creator.name').first()).toBeVisible();
		});
	});

	test('Show mapped hierarchical Fields in fragment', async ({
		dataSetsPage,
		fdsFragmentPage,
		page,
	}) => {
		await fdsFragmentPage.goto();

		const site = await fdsFragmentPage
			.createSite('FDSFragment')
			.then((response) => response);

		const layout = await fdsFragmentPage
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
				.filter({hasText: DEFAULT_LABEL.VIEW})
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

		await test.step('Fields are present in fragment table heading', async () => {
			await expect(
				page.getByRole('button', {name: 'creator.*'}).first()
			).toBeVisible();
			await expect(
				page.getByRole('button', {name: 'creator.id'}).first()
			).toBeVisible();
			await expect(
				page.getByRole('button', {name: 'creator.name'}).first()
			).toBeVisible();
			await expect(
				page.getByRole('button', {name: 'dateCreated'}).first()
			).toBeVisible();
			await expect(
				page.getByRole('button', {name: 'title'}).first()
			).toBeVisible();
		});

		await fdsFragmentPage.deleteSite(site.id);
		await dataSetsPage.deleteDataSet();
	});
});
