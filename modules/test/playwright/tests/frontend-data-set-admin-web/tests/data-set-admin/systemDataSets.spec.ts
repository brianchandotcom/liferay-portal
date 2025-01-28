/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../../fixtures/loginTest';
import {waitForAlert} from '../../../../utils/waitForAlert';
import {dataSetManagerApiHelpersTest} from '../../fixtures/dataSetManagerApiHelpersTest';
import {actionsPageTest} from './fixtures/actionsPageTest';
import {systemDataSetsPageTest} from './fixtures/systemDataSetsPageTest';

export const test = mergeTests(
	actionsPageTest,
	dataSetManagerApiHelpersTest,
	systemDataSetsPageTest,
	featureFlagsTest({
		'LPD-37531': {enabled: true},
		'LPS-164563': {enabled: true},
	}),
	loginTest()
);

test(
	'Import a system data set to customize',
	{tag: ['@LPD-37531', '@LPD-40949']},
	async ({actionsPage, page, systemDataSetsPage}) => {
		await test.step('Navigate to system data sets page', async () => {
			await systemDataSetsPage.goto();
		});

		const creationModal = systemDataSetsPage.creationModal;

		const classicSampleListItem = creationModal.listItems.filter({
			hasText: 'Classic Sample',
		});
		const customizedSampleListItem = creationModal.listItems.filter({
			hasText: 'Customized Sample',
		});

		await test.step('Open creation modal and assert modal content', async () => {
			await systemDataSetsPage.createButton.click();

			await expect(
				creationModal.header.getByText(
					'Create System Data Set Customization'
				)
			).toBeVisible();

			await expect(creationModal.searchInput).toBeVisible();

			await expect(classicSampleListItem).toBeVisible();
			await expect(customizedSampleListItem).toBeVisible();
		});

		await test.step('Search system data set items', async () => {
			await creationModal.searchInput.fill('Classic');

			await creationModal.searchInput.press('Enter');

			await expect(classicSampleListItem).toBeVisible();
			await expect(customizedSampleListItem).toBeHidden();

			await creationModal.searchInput.fill('aaa');

			await creationModal.searchInput.press('Enter');

			await expect(classicSampleListItem).toBeHidden();
			await expect(customizedSampleListItem).toBeHidden();

			await expect(
				creationModal.container.getByText('No Results Found')
			).toBeVisible();

			await creationModal.searchInput.fill('');

			await creationModal.searchInput.press('Enter');

			await expect(classicSampleListItem).toBeVisible();
			await expect(customizedSampleListItem).toBeVisible();
		});

		await test.step('Select a system data set', async () => {
			await customizedSampleListItem.click();

			await expect(customizedSampleListItem).toHaveClass(/selected/);

			await creationModal.createButton.click();

			await waitForAlert(systemDataSetsPage.page);
		});

		const customizedSampleRow = systemDataSetsPage.pageContainer
			.locator('.fds tr')
			.filter({hasText: 'Customized Sample'});

		await test.step('Check system data set is imported', async () => {
			await expect(customizedSampleRow).toBeVisible();
		});

		await test.step('Check the creation modal labels the data set as created and is disabled', async () => {
			await systemDataSetsPage.createButton.click();

			await expect(customizedSampleListItem).toContainText('Created');
			await expect(customizedSampleListItem).toHaveClass(/disabled/);

			await creationModal.cancelButton.click();
		});

		await test.step('Check item actions are imported', async () => {
			await actionsPage.open({dataSetLabel: 'Customized Sample'});

			const itemActionRow = actionsPage.itemActionsTable
				.locator('tr')
				.filter({hasText: 'Nav Links'})
				.first();

			await itemActionRow.locator('.dropdown-toggle').click();

			await actionsPage.page
				.locator('.dropdown-menu.show')
				.getByRole('menuitem', {name: 'Edit'})
				.click();

			const form = actionsPage.actionForm;

			await expect(form.labelInput).toHaveValue('Nav Links');
			await expect(form.iconInput).toHaveValue('home');
			await expect(form.typeSelect).toHaveValue('link');
			await expect(form.urlInput).toHaveValue('#');
			await expect(form.headlessActionKeyInput).toHaveValue('view');
			await expect(form.confirmationMessageInput).toHaveValue(
				'Are you sure?'
			);
			await expect(form.confirmationMessageTypeSelect).toHaveValue(
				'danger'
			);

			await form.cancelButton.click();
		});

		await test.step('Check creation actions are imported', async () => {
			await actionsPage.selectTab({
				container: actionsPage.actionsTabs,
				label: 'Creation Actions',
			});

			const creationActionRow = actionsPage.creationActionsTable
				.locator('tr')
				.filter({hasText: 'Open Form'})
				.first();

			await creationActionRow.locator('.dropdown-toggle').click();

			await actionsPage.page
				.locator('.dropdown-menu.show')
				.getByRole('menuitem', {name: 'Edit'})
				.click();

			const form = actionsPage.actionForm;

			await expect(form.labelInput).toHaveValue('Open Form');
			await expect(form.iconInput).toHaveValue('bolt');
			await expect(form.typeSelect).toHaveValue('modal');
			await expect(form.variantSelect).toHaveValue('full-screen');
			await expect(form.titleInput).toHaveValue('My Products');
			await expect(form.urlInput).toHaveValue('#');
			await expect(form.headlessActionKeyInput).toHaveValue('update');

			await form.cancelButton.click();

			await page.getByTitle('Back').click();
		});

		await test.step('Delete system data set', async () => {
			await customizedSampleRow.locator('.dropdown-toggle').click();

			await systemDataSetsPage.page
				.locator('.dropdown-menu.show')
				.getByRole('menuitem', {name: 'Delete'})
				.click();

			const deleteModal = systemDataSetsPage.page.getByRole('dialog');

			await deleteModal.getByRole('button', {name: 'Delete'}).click();

			await waitForAlert(systemDataSetsPage.page);

			await expect(customizedSampleRow).toBeHidden();
		});

		await test.step('Check the creation modal that the data set is enabled', async () => {
			await systemDataSetsPage.createButton.click();

			await expect(classicSampleListItem).not.toContainText('Created');
			await expect(classicSampleListItem).not.toHaveClass(/disabled/);

			await creationModal.cancelButton.click();
		});
	}
);
