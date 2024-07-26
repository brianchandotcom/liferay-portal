/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../../fixtures/loginTest';
import getRandomString from '../../../../utils/getRandomString';
import {dataSetManagerApiHelpersTest} from '../../fixtures/dataSetManagerApiHelpersTest';
import clickRowAction from '../../utils/clickRowAction';
import getRowByText from '../../utils/getRowByText';
import getSelectOptionLabels from '../../utils/getSelectOptionLabels';
import {ItemActionTypes} from '../../utils/types';
import {actionsPageTest} from './fixtures/actionsPageTest';
import {dataSetManagerSetupTest} from './fixtures/dataSetManagerSetupTest';

export const test = mergeTests(
	actionsPageTest,
	dataSetManagerApiHelpersTest,
	featureFlagsTest({
		'LPS-164563': true,
		'LPS-178052': true,
	}),
	loginTest(),
	dataSetManagerSetupTest
);

let dataSetERC: string;
let dataSetLabel: string;

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

test(
	'There is a message if there are no item actions',
	{tag: '@LPD-11300'},
	async ({actionsPage}) => {
		await test.step('Go to item actions tab', async () => {
			await actionsPage.gotoItemActionsTab({dataSetLabel});
		});

		await test.step('Assert message informing that there are no actions created', async () => {
			await expect(actionsPage.noActionsWereCreatedMessage).toContainText(
				'No actions were created.'
			);
		});
	}
);

test(
	'Assert available selection options in item action form',
	{tag: '@LPD-11300'},
	async ({actionsPage}) => {
		await test.step('Go to item actions tab', async () => {
			await actionsPage.gotoItemActionsTab({dataSetLabel});
		});

		await test.step('Open new item actions form', async () => {
			await actionsPage.newItemActionPlusButton.click();

			await expect(
				actionsPage.page.getByText('Display Options')
			).toBeInViewport();
		});

		await test.step('Asset action type options', async () => {
			expect(
				await getSelectOptionLabels(actionsPage.actionForm.typeSelect)
			).toEqual(['Async', 'Headless', 'Link', 'Modal', 'Side Panel']);
		});

		await test.step('Asset confirmation message type options', async () => {
			expect(
				await getSelectOptionLabels(
					actionsPage.actionForm.confirmationMessageTypeSelect
				)
			).toEqual(['Info', 'Secondary', 'Success', 'Danger', 'Warning']);
		});

		await test.step('Asset method options for `Async` action type', async () => {
			await actionsPage.actionForm.typeSelect.selectOption('Async');

			expect(
				await getSelectOptionLabels(actionsPage.actionForm.methodSelect)
			).toEqual(['DELETE', 'GET', 'PATCH', 'POST', 'PUT']);
		});

		await test.step('Asset variant options for `Modal` action type', async () => {
			await actionsPage.actionForm.typeSelect.selectOption('Modal');

			expect(
				await getSelectOptionLabels(
					actionsPage.actionForm.variantSelect
				)
			).toEqual(['Full Screen', 'Large', 'Small']);
		});
	}
);

test(
	'Create and edit item action of type "Link"',
	{tag: '@LPD-11300'},
	async ({actionsPage, page}) => {
		let confirmationMessage: string = getRandomString();
		let confirmationMessageType: string = 'info';
		let headlessActionKey: string = getRandomString();
		let icon: string = 'arrow-right-full';
		let label: string = getRandomString();
		const type: ItemActionTypes = 'link';
		let url: string = getRandomString();

		await test.step('Go to item actions tab', async () => {
			await actionsPage.gotoItemActionsTab({dataSetLabel});
		});

		await test.step('Create an item action', async () => {
			await actionsPage.createItemAction({
				confirmationMessage,
				confirmationMessageType,
				headlessActionKey,
				icon,
				label,
				type,
				url,
			});
		});

		let itemActionRow: Locator;

		await test.step('Check that the item action is in the list', async () => {
			await expect(actionsPage.itemActionsTab).toBeInViewport();

			itemActionRow = await getRowByText({
				page,
				table: actionsPage.itemActionsTable,
				text: label,
			});

			await expect(itemActionRow.getByRole('cell')).toContainText([
				icon,
				label,
				type,
			]);
		});

		await test.step('Open edit page of the saved item', async () => {
			await clickRowAction({
				actionLabel: 'Edit',
				page,
				row: itemActionRow,
			});

			await expect(page.getByText('Display Options')).toBeInViewport();
		});

		await test.step('Assert saved values match values on creation', async () => {
			const form = actionsPage.actionForm;

			await expect(form.labelInput).toHaveValue(label);
			await expect(form.iconInput).toHaveValue(icon);
			await expect(form.typeSelect).toHaveValue(type);
			await expect(form.urlInput).toHaveValue(url);
			await expect(form.headlessActionKeyInput).toHaveValue(
				headlessActionKey
			);
			await expect(form.confirmationMessageInput).toHaveValue(
				confirmationMessage
			);
			await expect(form.confirmationMessageTypeSelect).toHaveValue(
				confirmationMessageType
			);
		});

		await test.step('Assert type cannot be changed', async () => {
			await expect(actionsPage.actionForm.typeSelect).toBeDisabled();
		});

		await test.step('Change form values and save', async () => {
			confirmationMessage = getRandomString();
			confirmationMessageType = 'warning';
			headlessActionKey = getRandomString();
			icon = 'check-circle-full';
			label = getRandomString();
			url = getRandomString();

			await actionsPage.fillItemActionFormValues({
				confirmationMessage,
				confirmationMessageType,
				headlessActionKey,
				icon,
				label,
				type,
				url,
			});

			await actionsPage.actionForm.saveButton.click();
		});

		await test.step('Open edit page of the saved item', async () => {
			itemActionRow = await getRowByText({
				page,
				table: actionsPage.itemActionsTable,
				text: label,
			});

			await clickRowAction({
				actionLabel: 'Edit',
				page,
				row: itemActionRow,
			});

			await expect(page.getByText('Display Options')).toBeInViewport();
		});

		await test.step('Assert form values have changed', async () => {
			const form = actionsPage.actionForm;

			await expect(form.labelInput).toHaveValue(label);
			await expect(form.iconInput).toHaveValue(icon);
			await expect(form.typeSelect).toHaveValue(type);
			await expect(form.urlInput).toHaveValue(url);
			await expect(form.headlessActionKeyInput).toHaveValue(
				headlessActionKey
			);
			await expect(form.confirmationMessageInput).toHaveValue(
				confirmationMessage
			);
			await expect(form.confirmationMessageTypeSelect).toHaveValue(
				confirmationMessageType
			);
		});
	}
);

test(
	'Cancel creating an item action',
	{tag: '@LPD-11300'},
	async ({actionsPage}) => {
		await test.step('Go to item actions tab', async () => {
			await actionsPage.gotoItemActionsTab({dataSetLabel});
		});

		await test.step('Click on the "New Item Action" button', async () => {
			await actionsPage.newItemActionButton.click();
		});

		await test.step('Add some information in the fields', async () => {
			await actionsPage.actionForm.labelInput.fill('Test Item Action');
			await actionsPage.actionForm.typeSelect.selectOption('link');
		});

		await test.step('Cancel the creation of the item action', async () => {
			await actionsPage.actionForm.cancelButton.click();
		});

		await test.step('Check that the item action was not created', async () => {
			await expect(actionsPage.noActionsWereCreatedMessage).toContainText(
				'No actions were created.'
			);
		});
	}
);

test(
	'Delete an item action',
	{tag: '@LPD-11300'},
	async ({actionsPage, dataSetManagerApiHelpers, page}) => {
		const actionLabel = getRandomString();

		await test.step('Create an item action with API', async () => {
			await dataSetManagerApiHelpers.createDataSetItemAction({
				confirmationMessage_i18n: {
					en_US: getRandomString(),
				},
				dataSetERC,
				label_i18n: {en_US: actionLabel},
				type: 'link',
			});
		});

		await test.step('Go to item actions tab', async () => {
			await actionsPage.gotoItemActionsTab({dataSetLabel});
		});

		let actionRow: Locator;

		await test.step('Cancel deletion by not confirming it', async () => {
			actionRow = await getRowByText({
				page,
				table: actionsPage.itemActionsTable,
				text: actionLabel,
			});

			await clickRowAction({
				actionLabel: 'Delete',
				page,
				row: actionRow,
			});

			await expect(
				actionsPage.deletionConfirmationModal
			).toBeInViewport();

			await actionsPage.deletionConfirmationModal
				.getByRole('button', {
					name: 'Cancel',
				})
				.click();

			await expect(
				actionsPage.deletionConfirmationModal
			).not.toBeInViewport();

			await expect(actionRow).toBeInViewport();
		});

		await test.step('Delete item', async () => {
			await clickRowAction({
				actionLabel: 'Delete',
				page,
				row: actionRow,
			});

			await actionsPage.deletionConfirmationModal
				.getByRole('button', {
					name: 'Delete',
				})
				.click();

			await expect(
				actionsPage.deletionConfirmationModal
			).not.toBeInViewport();

			await expect(actionRow).not.toBeInViewport();
		});
	}
);
