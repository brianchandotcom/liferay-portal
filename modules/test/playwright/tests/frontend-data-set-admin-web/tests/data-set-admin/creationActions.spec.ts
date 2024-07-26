/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../../fixtures/loginTest';
import {liferayConfig} from '../../../../liferay.config';
import getRandomString from '../../../../utils/getRandomString';
import {dataSetManagerApiHelpersTest} from '../../fixtures/dataSetManagerApiHelpersTest';
import clickRowAction from '../../utils/clickRowAction';
import getRowByText from '../../utils/getRowByText';
import getSelectOptionLabels from '../../utils/getSelectOptionLabels';
import {actionsPageTest} from './fixtures/actionsPageTest';
import {dataSetManagerSetupTest} from './fixtures/dataSetManagerSetupTest';

const LINK_CREATION_ACTION_NAME = 'Link creation action';
const MODAL_CREATION_ACTION_NAME = 'Modal creation action';
const MODAL_CREATION_ACTION_TITLE = 'Modal creation title';
const SIDE_PANEL_CREATION_ACTION_NAME = 'Side Panel creation action';
const SIDE_PANEL_CREATION_ACTION_TITLE = 'Side Panel creation title';

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

	await test.step('Create a data set', async () => {
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
	'There is a message if there are no creation actions',
	{tag: '@LPD-11245'},
	async ({actionsPage}) => {
		await test.step('Go to creation actions tab', async () => {
			await actionsPage.gotoCreationActionsTab({dataSetLabel});
		});

		await test.step('Assert no creation actions are created', async () => {
			await expect(actionsPage.noActionsWereCreatedMessage).toContainText(
				'No actions were created.'
			);
		});
	}
);

test(
	'Assert available selection options in creation action form',
	{tag: '@LPD-11245'},
	async ({actionsPage}) => {
		await test.step('Go to creation actions tab', async () => {
			await actionsPage.gotoCreationActionsTab({dataSetLabel});
		});

		await test.step('Open new item actions form', async () => {
			await actionsPage.newCreationActionPlusButton.click();

			await expect(
				actionsPage.page.getByText('Display Options')
			).toBeInViewport();
		});

		await test.step('Asset action type options', async () => {
			expect(
				await getSelectOptionLabels(actionsPage.actionForm.typeSelect)
			).toEqual(['Link', 'Modal', 'Side Panel']);
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
	'Can create a creation action of type "Link"',
	{tag: '@LPD-11245'},
	async ({actionsPage, page}) => {
		await test.step('Go to creation actions tab', async () => {
			await actionsPage.gotoCreationActionsTab({dataSetLabel});
		});

		await test.step('Create a creation action', async () => {
			await actionsPage.createCreationAction({
				icon: 'arrow-right-full',
				label: LINK_CREATION_ACTION_NAME,
				type: 'link',
				url: liferayConfig.environment.baseUrl,
			});
		});

		await test.step('Check that the creation action is in the list', async () => {
			await expect(actionsPage.creationActionsTab).toBeInViewport();

			await expect(
				page.getByRole('cell', {
					exact: true,
					name: LINK_CREATION_ACTION_NAME,
				})
			).toBeVisible();
		});
	}
);

test(
	'Can create a creation action of type "Modal"',
	{tag: '@LPD-11245'},
	async ({actionsPage, page}) => {
		await test.step('Go to creation actions tab', async () => {
			await actionsPage.gotoCreationActionsTab({dataSetLabel});
		});

		await test.step('Create a creation action', async () => {
			await actionsPage.createCreationAction({
				icon: 'arrow-right-full',
				label: MODAL_CREATION_ACTION_NAME,
				title: MODAL_CREATION_ACTION_TITLE,
				type: 'modal',
				url: liferayConfig.environment.baseUrl,
				variant: 'sm',
			});
		});

		await test.step('Check that the creation action is in the list', async () => {
			await expect(actionsPage.creationActionsTab).toBeInViewport();

			await expect(
				page.getByRole('cell', {
					exact: true,
					name: MODAL_CREATION_ACTION_NAME,
				})
			).toBeVisible();
		});
	}
);

test(
	'Can create a creation action of type "Side Panel"',
	{tag: '@LPD-11245'},
	async ({actionsPage, page}) => {
		await test.step('Go to creation actions tab', async () => {
			await actionsPage.gotoCreationActionsTab({dataSetLabel});
		});

		await test.step('Create a creation action', async () => {
			await actionsPage.createCreationAction({
				icon: 'arrow-right-full',
				label: SIDE_PANEL_CREATION_ACTION_NAME,
				title: SIDE_PANEL_CREATION_ACTION_TITLE,
				type: 'sidePanel',
				url: liferayConfig.environment.baseUrl,
			});
		});

		await test.step('Check that the creation action is in the list', async () => {
			await expect(actionsPage.creationActionsTab).toBeInViewport();

			await expect(
				page.getByRole('cell', {
					exact: true,
					name: SIDE_PANEL_CREATION_ACTION_NAME,
				})
			).toBeVisible();
		});
	}
);

test(
	'Delete a creation action',
	{tag: '@LPD-11245'},
	async ({actionsPage, dataSetManagerApiHelpers, page}) => {
		const actionLabel = getRandomString();

		await test.step('Create an item action with API', async () => {
			await dataSetManagerApiHelpers.createDataSetCreationAction({
				dataSetERC,
				label_i18n: {en_US: actionLabel},
				type: 'link',
			});
		});

		await test.step('Go to creation actions tab', async () => {
			await actionsPage.gotoCreationActionsTab({dataSetLabel});
		});

		let actionRow: Locator;

		await test.step('Cancel deletion by not confirming it', async () => {
			actionRow = await getRowByText({
				page,
				table: actionsPage.creationActionsTable,
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
