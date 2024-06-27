/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../../fixtures/loginTest';
import {liferayConfig} from '../../../../liferay.config';
import getRandomString from '../../../../utils/getRandomString';
import {dataSetManagerApiHelpersTest} from '../../fixtures/dataSetManagerApiHelpersTest';
import {actionsPageTest} from './fixtures/actionsPageTest';
import {dataSetManagerSetupTest} from './fixtures/dataSetManagerSetupTest';

const LINK_ITEM_ACTION_NAME = 'Link item action';

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

test.beforeEach(async ({actionsPage, dataSetManagerApiHelpers}) => {
	dataSetERC = getRandomString();
	dataSetLabel = getRandomString();

	await test.step('Create data set', async () => {
		await dataSetManagerApiHelpers.createDataSet({
			erc: dataSetERC,
			label: dataSetLabel,
		});
	});

	await test.step('Navigate to the Actions tab', async () => {
		await actionsPage.goto({
			dataSetLabel,
		});

		await expect(actionsPage.itemActionsTab).toBeInViewport();
	});

	await test.step('Navigate to the Item Actions tab', async () => {
		await actionsPage.itemActionsTab.click();
		await actionsPage.newItemActionButton.waitFor();
	});
});

test.afterEach(async ({dataSetManagerApiHelpers}) => {
	await dataSetManagerApiHelpers.deleteDataSet({erc: dataSetERC});
});

test.describe('Item Actions in Data Set Manager', () => {
	test('There is a message if there are no Item Actions', async ({
		actionsPage,
	}) => {
		await test.step('Assert no Item Actions are created', async () => {
			await expect(actionsPage.noActionsWereCreatedMessage).toContainText(
				'No actions were created.'
			);
		});
	});

	test('Can create an Item Action of type Link', async ({
		actionsPage,
		page,
	}) => {
		await test.step('Create an item action', async () => {
			await actionsPage.createItemAction({
				icon: 'arrow-right-full',
				name: LINK_ITEM_ACTION_NAME,
				type: 'link',
				url: liferayConfig.environment.baseUrl,
			});
		});

		await test.step('Check that the item action is in the list', async () => {
			await expect(actionsPage.itemActionsTab).toBeInViewport();

			await expect(
				page.getByRole('cell', {
					exact: true,
					name: LINK_ITEM_ACTION_NAME,
				})
			).toBeVisible();
		});
	});
});
