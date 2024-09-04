/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {isolatedLayoutTest} from '../../../../fixtures/isolatedLayoutTest';
import {loginTest} from '../../../../fixtures/loginTest';
import {liferayConfig} from '../../../../liferay.config';
import getRandomString from '../../../../utils/getRandomString';
import {dataSetManagerApiHelpersTest} from '../../fixtures/dataSetManagerApiHelpersTest';
import {
	EAsyncActionMethod,
	EItemActionType,
	EModalActionVariant,
} from '../../utils/types';
import {fdsFragmentPageTest} from './fixtures/fdsFragmentPageTest';

const LINK_ITEM_ACTION_NAME = 'Link item action';
const LINK_ITEM_ACTION_CONFIRMATION_MESSAGE =
	'Do you want to navigate to http://www.liferay.com?';

let dataSetERC: string;
let dataSetLabel: string;

export const test = mergeTests(
	dataSetManagerApiHelpersTest,
	featureFlagsTest({
		'LPS-164563': true,
		'LPS-178052': true,
	}),
	isolatedLayoutTest({publish: false}),
	loginTest(),
	fdsFragmentPageTest
);

test.beforeEach(async ({dataSetManagerApiHelpers}) => {
	dataSetERC = getRandomString();
	dataSetLabel = getRandomString();

	await test.step('Create data set', async () => {
		await dataSetManagerApiHelpers.createDataSet({
			erc: dataSetERC,
			label: dataSetLabel,
		});
	});
});

test.afterEach(async ({dataSetManagerApiHelpers}) => {
	await dataSetManagerApiHelpers.deleteDataSet({erc: dataSetERC});
});

test.describe('Empty Item Actions in Data Set fragment', () => {
	test('Item Action button does not appear if there is no item action', async ({
		dataSetManagerApiHelpers,
		fdsFragmentPage,
		layout,
	}) => {
		await test.step('Create table field', async () => {
			await dataSetManagerApiHelpers.createDataSetField({
				dataSetERC,
				label_i18n: {en_US: 'Id'},
				name: 'id',
				type: 'string',
			});
		});

		await test.step('Configure Data Set in the page', async () => {
			await fdsFragmentPage.configureDataSetFragment({
				dataSetLabel,
				layout,
			});
		});

		await test.step('Check that the Item Action button is not present', async () => {
			await expect(
				fdsFragmentPage.page.getByLabel(LINK_ITEM_ACTION_NAME).first()
			).not.toBeVisible();
		});
	});
});

test.describe('Item Actions in Data Set fragment', () => {
	test.beforeEach(async ({dataSetManagerApiHelpers}) => {
		await test.step('Populate Data Set', async () => {
			await dataSetManagerApiHelpers.createDataSetField({
				dataSetERC,
				label_i18n: {en_US: 'Id'},
				name: 'id',
				type: 'string',
			});
			await dataSetManagerApiHelpers.createDataSetField({
				dataSetERC,
				label_i18n: {en_US: 'Name'},
				name: 'name',
				type: 'string',
			});
		});
	});

	test('Link Item Action (single action) is shown in the fragment', async ({
		dataSetManagerApiHelpers,
		fdsFragmentPage,
		layout,
		page,
	}) => {
		await test.step('Create Item Action', async () => {
			await dataSetManagerApiHelpers.createDataSetItemAction({
				confirmationMessage_i18n: {
					en_US: LINK_ITEM_ACTION_CONFIRMATION_MESSAGE,
				},
				dataSetERC,
				label_i18n: {en_US: LINK_ITEM_ACTION_NAME},
				type: EItemActionType.LINK,
			});
		});

		await test.step('Configure Data Set in the page', async () => {
			await fdsFragmentPage.configureDataSetFragment({
				dataSetLabel,
				layout,
			});
		});

		await test.step('Check that the Item Action button is present', async () => {
			await expect(
				fdsFragmentPage.page.getByLabel(LINK_ITEM_ACTION_NAME).first()
			).toBeVisible();
		});

		await test.step('Check that the Item Action works', async () => {
			const dialogPromise = page
				.waitForEvent('dialog')
				.then(async (dialog) => {
					await dialog.accept();

					return dialog.message();
				});

			await fdsFragmentPage.page
				.getByLabel(LINK_ITEM_ACTION_NAME)
				.first()
				.click();

			const confirmationMessage = await dialogPromise;

			expect(confirmationMessage).toBe(
				LINK_ITEM_ACTION_CONFIRMATION_MESSAGE
			);

			await expect(page.getByText('Welcome to Liferay')).toBeVisible();
		});
	});

	test('Link, Modal and Side Panel Item Actions (multiple actions) are shown in fragment', async ({
		dataSetManagerApiHelpers,
		fdsFragmentPage,
		layout,
		page,
	}) => {
		const MODAL_ITEM_ACTION_NAME = 'Modal item action';
		const MODAL_ITEM_ACTION_TITLE = 'Modal title';
		const SIDE_PANEL_ITEM_ACTION_NAME = 'SidePanel item action';
		const SIDE_PANEL_ITEM_ACTION_URL = liferayConfig.environment.baseUrl;

		await test.step('Create Item Actions', async () => {
			await dataSetManagerApiHelpers.createDataSetItemAction({
				dataSetERC,
				label_i18n: {en_US: LINK_ITEM_ACTION_NAME},
				type: EItemActionType.LINK,
			});

			await dataSetManagerApiHelpers.createDataSetItemAction({
				dataSetERC,
				label_i18n: {en_US: MODAL_ITEM_ACTION_NAME},
				modalSize: EModalActionVariant.SMALL,
				title_i18n: {en_US: MODAL_ITEM_ACTION_TITLE},
				type: EItemActionType.MODAL,
				url: liferayConfig.environment.baseUrl,
			});

			await dataSetManagerApiHelpers.createDataSetItemAction({
				dataSetERC,
				label_i18n: {en_US: SIDE_PANEL_ITEM_ACTION_NAME},
				modalSize: EModalActionVariant.SMALL,
				title_i18n: {en_US: SIDE_PANEL_ITEM_ACTION_NAME},
				type: EItemActionType.SIDE_PANEL,
				url: liferayConfig.environment.baseUrl,
			});
		});

		await test.step('Configure Data Set in the page', async () => {
			await fdsFragmentPage.configureDataSetFragment({
				dataSetLabel,
				layout,
			});
		});

		const datasetRow =
			await test.step('Check that the Item Actions dropdown is present in table row', async () => {
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

			const dialog = page.getByRole('dialog');

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
	});

	test('Async and Headless Item Actions (multiple actions) are shown in fragment', async ({
		dataSetManagerApiHelpers,
		fdsFragmentPage,
		layout,
		page,
	}) => {
		const ASYNC_ITEM_ACTION_NAME = 'Async item action';
		const ASYNC_ITEM_ACTION_URL = '/o/data-set-manager/table-sections/{id}';
		const HEADLESS_ITEM_ACTION_NAME = 'Headless item action';
		const HEADLESS_ITEM_ACTION_PERMISSION_KEY = 'delete';
		const NON_AVAILABLE_HEADLESS_ITEM_ACTION_NAME =
			'Useless Headless Item Action';

		await test.step('Create Item Actions', async () => {
			await dataSetManagerApiHelpers.createDataSetItemAction({
				dataSetERC,
				label_i18n: {en_US: HEADLESS_ITEM_ACTION_NAME},
				permissionKey: HEADLESS_ITEM_ACTION_PERMISSION_KEY,
				type: EItemActionType.HEADLESS,
			});

			await dataSetManagerApiHelpers.createDataSetItemAction({
				dataSetERC,
				label_i18n: {en_US: ASYNC_ITEM_ACTION_NAME},
				method: EAsyncActionMethod.DELETE,
				type: EItemActionType.ASYNC,
				url: ASYNC_ITEM_ACTION_URL,
			});

			await dataSetManagerApiHelpers.createDataSetItemAction({
				dataSetERC,
				label_i18n: {
					en_US: NON_AVAILABLE_HEADLESS_ITEM_ACTION_NAME,
				},
				permissionKey: 'remove',
				type: EItemActionType.HEADLESS,
			});
		});

		await test.step('Configure Data Set in the page', async () => {
			await fdsFragmentPage.configureDataSetFragment({
				dataSetLabel,
				layout,
			});
		});

		const datasetRow =
			await test.step('Checkt that the Item Actions dropdown (only 2 items) is present in table row', async () => {
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

			const alert = await page.getByRole('alert').first();

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

			const alert = await page.getByRole('alert').first();

			await expect(alert).toHaveText(
				'Success:Your request completed successfully.'
			);
		});
	});

	test('Async and Headless Item Actions (multiple actions) performs UPDATE operations on items', async ({
		dataSetManagerApiHelpers,
		fdsFragmentPage,
		layout,
		page,
	}) => {
		const ASYNC_ITEM_ACTION_NAME = 'Async item action';
		const ASYNC_ITEM_ACTION_URL = '/o/data-set-manager/table-sections/{id}';
		const ASYNC_ITEM_NEW_LABEL = getRandomString();
		const HEADLESS_ITEM_ACTION_NAME = 'Headless item action';
		const HEADLESS_ITEM_ACTION_PERMISSION_KEY = 'update';
		const HEADLESS_ITEM_NEW_LABEL = getRandomString();

		await test.step('Create Item Actions', async () => {
			await dataSetManagerApiHelpers.createDataSetItemAction({
				dataSetERC,
				label_i18n: {en_US: HEADLESS_ITEM_ACTION_NAME},
				permissionKey: HEADLESS_ITEM_ACTION_PERMISSION_KEY,
				requestBody: `{"name": "${HEADLESS_ITEM_NEW_LABEL}"}`,
				type: EItemActionType.HEADLESS,
			});

			await dataSetManagerApiHelpers.createDataSetItemAction({
				dataSetERC,
				label_i18n: {en_US: ASYNC_ITEM_ACTION_NAME},
				method: EAsyncActionMethod.PATCH,
				requestBody: `{"name": "${ASYNC_ITEM_NEW_LABEL}"}`,
				type: EItemActionType.ASYNC,
				url: ASYNC_ITEM_ACTION_URL,
			});
		});

		await test.step('Configure Data Set in the page', async () => {
			await fdsFragmentPage.configureDataSetFragment({
				dataSetLabel,
				layout,
			});
		});

		const datasetRow =
			await test.step('Checkt that the Item Actions dropdown (only 2 items) is present in table row', async () => {
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
				).toHaveCount(2);

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

			const alert = await page.getByRole('alert').first();

			await expect(alert).toHaveText(
				'Success:Your request completed successfully.' 
			);

			await expect(page.getByText(HEADLESS_ITEM_NEW_LABEL)).toBeVisible();
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

			const alert = await page.getByRole('alert').first();

			await expect(alert).toHaveText(
				'Success:Your request completed successfully.'
			);

			await expect(page.getByText(ASYNC_ITEM_NEW_LABEL)).toBeVisible();
		});
	});

	test('Async Item Action shows an error toast in the fragment when a failure occurs', async ({
		dataSetManagerApiHelpers,
		fdsFragmentPage,
		layout,
		page,
	}) => {
		const ASYNC_ITEM_ACTION_NAME = 'Async item action';
		const ASYNC_ITEM_ACTION_WRONG_URL =
			'/o/data-set-manager/table-sections/{foo}';

		await test.step('Create Item Actions', async () => {
			await dataSetManagerApiHelpers.createDataSetItemAction({
				dataSetERC,
				label_i18n: {en_US: ASYNC_ITEM_ACTION_NAME},
				method: EAsyncActionMethod.DELETE,
				type: EItemActionType.ASYNC,
				url: ASYNC_ITEM_ACTION_WRONG_URL,
			});
		});

		await test.step('Configure Data Set in the page', async () => {
			await fdsFragmentPage.configureDataSetFragment({
				dataSetLabel,
				layout,
			});
		});

		const datasetRow =
			await test.step('Checkt that the Item Actions is present in table row', async () => {
				const tableRow = await page
					.locator('.dnd-td.item-actions')
					.first();

				await expect(tableRow.getByRole('button')).toBeVisible();

				return tableRow;
			});

		await test.step('Click in the async Item Action shows an error toast.', async () => {
			await datasetRow
				.getByRole('button', {name: ASYNC_ITEM_ACTION_NAME})
				.click();

			await page.getByRole('alert').waitFor();

			const alert = await page.getByRole('alert').first();

			await expect(alert).toHaveText(
				'Error:An unexpected error occurred.'
			);
		});
	});
});
