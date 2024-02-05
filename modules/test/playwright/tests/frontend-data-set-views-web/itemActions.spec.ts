/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {actionsPageTest} from './fixtures/actionsPageTest';
import {dataSetsPageTest} from './fixtures/dataSetsPageTest';
import {viewsPageTest} from './fixtures/viewsPageTest';

export const test = mergeTests(
	actionsPageTest,
	dataSetsPageTest,
	featureFlagsTest({
		'LPS-164563': true,
		'LPS-194395': true,
	}),
	loginTest(),
	viewsPageTest
);

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
