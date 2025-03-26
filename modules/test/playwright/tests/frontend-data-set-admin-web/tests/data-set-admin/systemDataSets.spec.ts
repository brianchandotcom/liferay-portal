/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../../fixtures/loginTest';
import {waitForAlert} from '../../../../utils/waitForAlert';
import {fdsSamplePageTest} from '../../../frontend-data-set-web/fixtures/fdsSamplePageTest';
import {dataSetManagerApiHelpersTest} from '../../fixtures/dataSetManagerApiHelpersTest';
import {actionsPageTest} from './fixtures/actionsPageTest';
import {systemDataSetsPageTest} from './fixtures/systemDataSetsPageTest';

export const test = mergeTests(
	actionsPageTest,
	dataSetManagerApiHelpersTest,
	fdsSamplePageTest,
	isolatedSiteTest,
	systemDataSetsPageTest,
	featureFlagsTest({
		'LPD-37531': {enabled: true},
		'LPS-164563': {enabled: true},
	}),
	loginTest()
);

const dataSetERCs: string[] = [];

test.afterEach(async ({dataSetManagerApiHelpers}) => {
	for (const erc of dataSetERCs) {
		await dataSetManagerApiHelpers.deleteDataSet({
			erc,
		});
	}
});

async function findTextIndexInLocators(
	locators: Locator[],
	textToFind: string
): Promise<number> {
	const texts = await Promise.all(
		locators.map(async (element) => {
			return await element.textContent();
		})
	);

	return texts.findIndex((text) => text?.trim().includes(textToFind));
}

test(
	'Import a system data set to customize',
	{tag: ['@LPD-37531', '@LPD-40949', '@LPD-49128']},
	async ({actionsPage, fdsSamplePage, page, site, systemDataSetsPage}) => {
		await test.step('Add FDS Sample Widget for object definition generation', async () => {
			await fdsSamplePage.setupFDSSampleWidget({site});
		});

		await test.step('Navigate to system data sets page', async () => {
			await systemDataSetsPage.goto();
		});

		const creationModal = systemDataSetsPage.creationModal;

		const advancedSampleListItem = creationModal.listItems.filter({
			hasText: 'Advanced Sample',
		});

		const classicSampleListItem = creationModal.listItems.filter({
			hasText: 'Classic Sample',
		});

		const customInternalViewSampleListItem = creationModal.listItems.filter(
			{
				hasText: 'Custom Internal View Sample',
			}
		);

		await test.step('Open creation modal and assert modal content', async () => {
			await systemDataSetsPage.createButton.click();

			await expect(
				creationModal.header.getByText(
					'Create System Data Set Customization'
				)
			).toBeVisible();

			await expect(creationModal.searchInput).toBeVisible();

			await expect(advancedSampleListItem).toBeVisible();
			await expect(classicSampleListItem).toBeVisible();
			await expect(customInternalViewSampleListItem).toBeVisible();
		});

		await test.step('Assert the items are listed in alphabetical order', async () => {
			const systemDataSets = await creationModal.body
				.locator('.data-set-content-wrapper .list-group-title')
				.all();

			const classicSampleIndex = await findTextIndexInLocators(
				systemDataSets,
				'Classic Sample'
			);

			expect(
				await findTextIndexInLocators(systemDataSets, 'Advanced Sample')
			).toBeLessThan(classicSampleIndex);

			expect(classicSampleIndex).toBeLessThan(
				await findTextIndexInLocators(
					systemDataSets,
					'Custom Internal View Sample'
				)
			);
		});

		await test.step('Search system data set items', async () => {
			await creationModal.searchInput.fill('Classic');

			await creationModal.searchInput.press('Enter');

			await expect(advancedSampleListItem).not.toBeAttached();
			await expect(classicSampleListItem).toBeVisible();
			await expect(customInternalViewSampleListItem).not.toBeAttached();

			await creationModal.searchInput.fill('aaa');

			await creationModal.searchInput.press('Enter');

			await expect(advancedSampleListItem).not.toBeAttached();
			await expect(classicSampleListItem).not.toBeAttached();
			await expect(customInternalViewSampleListItem).not.toBeAttached();

			await expect(
				creationModal.body.getByText('No Results Found')
			).toBeVisible();

			await creationModal.searchInput.fill('');

			await creationModal.searchInput.press('Enter');

			await expect(advancedSampleListItem).toBeVisible();
			await expect(classicSampleListItem).toBeVisible();
			await expect(customInternalViewSampleListItem).toBeVisible();
		});

		await test.step('Select and import system data sets', async () => {
			await advancedSampleListItem.click();

			await expect(advancedSampleListItem).toHaveClass(/selected/);

			dataSetERCs.push(

				// eslint-disable-next-line @liferay/no-get-data-attribute
				await advancedSampleListItem.getAttribute('data-erc')
			);

			await creationModal.createButton.click();

			await waitForAlert(systemDataSetsPage.page);

			await systemDataSetsPage.createButton.click();

			await classicSampleListItem.click();

			await expect(classicSampleListItem).toHaveClass(/selected/);

			dataSetERCs.push(

				// eslint-disable-next-line @liferay/no-get-data-attribute
				await classicSampleListItem.getAttribute('data-erc')
			);

			await creationModal.createButton.click();

			await waitForAlert(systemDataSetsPage.page);

			await systemDataSetsPage.createButton.click();

			await customInternalViewSampleListItem.click();

			await expect(customInternalViewSampleListItem).toHaveClass(
				/selected/
			);

			dataSetERCs.push(

				// eslint-disable-next-line @liferay/no-get-data-attribute
				await customInternalViewSampleListItem.getAttribute('data-erc')
			);

			await creationModal.createButton.click();

			await waitForAlert(systemDataSetsPage.page);
		});

		const fdsRows = systemDataSetsPage.pageContainer.locator('.fds tr');

		const advancedSampleRow = fdsRows.filter({
			hasText: 'Advanced Sample',
		});

		await test.step('Check system data set is imported and are "Active" by default', async () => {
			await expect(advancedSampleRow).toBeVisible();

			expect(
				fdsRows.filter({
					hasText: 'Classic Sample',
				})
			).toBeVisible();
			expect(
				fdsRows.filter({
					hasText: 'Custom Internal View Sample',
				})
			).toBeVisible();

			await expect(systemDataSetsPage.activeToggle.first()).toBeVisible();
		});

		await test.step('Can deactivate the system data set', async () => {
			await systemDataSetsPage.activeToggle.first().click();

			await waitForAlert(page);

			await expect(
				systemDataSetsPage.inactiveToggle.first()
			).toBeVisible();
		});

		await test.step('Can activate the system data set', async () => {
			await systemDataSetsPage.inactiveToggle.first().click();

			await waitForAlert(page);

			await expect(systemDataSetsPage.activeToggle.first()).toBeVisible();
		});

		await test.step('Check the creation modal labels the data set as created and is disabled', async () => {
			await systemDataSetsPage.createButton.click();

			await expect(advancedSampleListItem).toContainText('Created');
			await expect(advancedSampleListItem).toHaveClass(/disabled/);

			await creationModal.cancelButton.click();
		});

		await test.step('Item actions are imported with "detached" import policy', async () => {
			await actionsPage.open({dataSetLabel: 'Advanced Sample'});

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
			await expect(form.headlessActionKeyInput).toHaveValue('get');
			await expect(form.confirmationMessageInput).toHaveValue(
				'Are you sure?'
			);
			await expect(form.confirmationMessageTypeSelect).toHaveValue(
				'danger'
			);

			await form.cancelButton.click();
		});

		await test.step('Creation actions are imported with "detached" import policy', async () => {
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

		await test.step('Item actions are imported with "item proxy" import policy', async () => {
			await actionsPage.open({dataSetLabel: 'Classic Sample'});

			const itemActionRows = actionsPage.itemActionsTable
				.locator('tr')
				.filter({hasText: 'System Action'});

			await expect(itemActionRows).toHaveCount(5);

			for (const itemActionRow of await itemActionRows.all()) {
				await expect(
					itemActionRow.locator('.dropdown-toggle')
				).not.toBeAttached();
			}
		});

		await test.step('Creation actions are imported with "item proxy" import policy', async () => {
			await actionsPage.selectTab({
				container: actionsPage.actionsTabs,
				label: 'Creation Actions',
			});

			const creationActionRows = actionsPage.creationActionsTable
				.locator('tr')
				.filter({hasText: 'System Action'});

			await expect(creationActionRows).toHaveCount(1);

			await expect(
				creationActionRows.getByText('Calendar', {exact: true})
			).toBeVisible();

			await page.getByTitle('Back').click();
		});

		await test.step('Item actions are imported with "group proxy" import policy', async () => {
			await actionsPage.open({
				dataSetLabel: 'Custom Internal View Sample',
			});

			const itemActionRows = actionsPage.itemActionsTable
				.locator('tr')
				.filter({hasText: 'Group of System Actions'});

			await expect(itemActionRows).toHaveCount(1);

			for (const itemActionRow of await itemActionRows.all()) {
				await expect(
					itemActionRow.locator('.dropdown-toggle')
				).not.toBeAttached();
			}
		});

		await test.step('Creation actions are imported with "group proxy" import policy', async () => {
			await actionsPage.selectTab({
				container: actionsPage.actionsTabs,
				label: 'Creation Actions',
			});

			const creationActionRows = actionsPage.creationActionsTable
				.locator('tr')
				.filter({hasText: 'Group of System Actions'});

			await expect(creationActionRows).toHaveCount(1);

			await page.getByTitle('Back').click();
		});

		await test.step('Delete an imported system data set', async () => {
			await advancedSampleRow.locator('.dropdown-toggle').click();

			await systemDataSetsPage.page
				.locator('.dropdown-menu.show')
				.getByRole('menuitem', {name: 'Delete'})
				.click();

			const deleteModal = systemDataSetsPage.page.getByRole('dialog');

			await deleteModal.getByRole('button', {name: 'Delete'}).click();

			await waitForAlert(systemDataSetsPage.page);

			await expect(advancedSampleRow).not.toBeAttached();
		});

		await test.step('Check that deleted data set is again available for import ', async () => {
			await systemDataSetsPage.createButton.click();

			await expect(advancedSampleListItem).not.toContainText('Created');
			await expect(advancedSampleListItem).not.toHaveClass(/disabled/);

			await creationModal.cancelButton.click();
		});
	}
);
