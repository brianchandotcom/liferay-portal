/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {loginTest} from '../../fixtures/loginTest';
import {dataSetsPageTest} from './fixtures/dataSetsPageTest';
import {fieldsPageTest} from './fixtures/fieldsPageTest';
import {viewsPageTest} from './fixtures/viewsPageTest';

export const test = mergeTests(
	applicationsMenuPageTest,
	dataSetsPageTest,
	fieldsPageTest,
	loginTest,
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
			await dataSetsPage.createSampleDataSetUI();
		});

		await test.step('Create sample DataSet View', async () => {
			await viewsPage.goto();
			await viewsPage.createSampleDataSetViewUI();
		});

		await test.step('Open modal to add fields', async () => {
			await fieldsPage.goto();
			await fieldsPage.openAddFieldsModal();
		});

		await test.step('Check fields in treeview', async () => {
			await fieldsPage.addParentField('dateCreated');
			await fieldsPage.addParentField('title');
			await fieldsPage.addParentField('creator');
			await fieldsPage.addChildField('creator', 'name');
			await fieldsPage.addChildField('creator', 'id');
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

	test('Add DataSet fragment', async ({applicationsMenuPage, page}) => {
		await test.step('Go to Home', async () => {
			await applicationsMenuPage.goToHome();
		});

		await test.step('Click on "Edit" button', async () => {
			const editPageButton = await page.getByRole('link', {
				name: 'Edit',
			});
			await editPageButton.click();
		});

		await test.step('Search for "Data Set" fragment', async () => {
			const fragmentSearchInput = await page.getByLabel(
				'Search Fragments and Widgets'
			);
			fragmentSearchInput.fill('Data Set');
		});

		await test.step('Drag "Data Set" fragment & Drop into the page editor w/ keyboard', async () => {
			const source = await page.getByRole('menuitem', {
				name: 'Data Set Add Data Set Mark Data Set as Favorite',
			});
			await source.focus();
			await source.press('Enter');
			await source.press('Enter');
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
			await page
				.getByRole('menuitem', {name: 'Select Data Set View...'})
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
				.filter({hasText: 'Data Set View Sample'})
				.first()
				.click();
			await page
				.frameLocator('iframe[title="Select"]')
				.getByRole('button', {name: 'Save'})
				.click();
		});

		await test.step('Publish page with Data Set View', async () => {
			await page.getByRole('button', {name: 'Publish'}).click();

			await applicationsMenuPage.goToHome();

			await expect(
				page.locator('.data-set-wrapper').first()
			).toBeVisible();
		});

		await test.step('Fields are present in fragment table heading', async () => {
			await expect(
				page.getByRole('button', {name: 'creator.*'})
			).toBeVisible();
			await expect(
				page.getByRole('button', {name: 'creator.id'})
			).toBeVisible();
			await expect(
				page.getByRole('button', {name: 'creator.name'})
			).toBeVisible();
			await expect(
				page.getByRole('button', {name: 'dateCreated'})
			).toBeVisible();
			await expect(
				page.getByRole('button', {name: 'title'})
			).toBeVisible();
		});
	});
});
