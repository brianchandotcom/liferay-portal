/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../../../fixtures/loginTest';
import getRandomString from '../../../../../utils/getRandomString';
import {waitForAlert} from '../../../../../utils/waitForAlert';
import {dataSetManagerApiHelpersTest} from '../../fixtures/dataSetManagerApiHelpersTest';
import {userViewsPageTest} from './fixtures/userViewsPageTest';

export const test = mergeTests(
	dataSetManagerApiHelpersTest,
	featureFlagsTest({
		'LPD-36105': {enabled: true},
	}),
	userViewsPageTest,
	loginTest()
);

const dataSetERC = getRandomString();
const snapshotName = getRandomString();

test.beforeEach(async ({dataSetManagerApiHelpers}) => {
	const response = (await dataSetManagerApiHelpers.get(
		'/o/data-set-admin/snapshots?page=1&pageSize=100'
	)) as {items?: Array<{id?: number}>};

	const snapshots = response.items || [];

	for (const snapshot of snapshots) {
		if (!snapshot.id) {
			continue;
		}

		await dataSetManagerApiHelpers.delete(
			`/o/data-set-admin/snapshots/${snapshot.id}`
		);
	}
});

test(
	'Manage User Views',
	{tag: '@LPD-71991'},
	async ({dataSetManagerApiHelpers, page, userViewsPage}) => {
		await test.step('Create User View', async () => {
			await dataSetManagerApiHelpers.createDataSetSnapshot({
				dataSetERC,
				snapshotName,
			});
		});

		await test.step('Check the User View is visible in Manage User Views Page', async () => {
			await userViewsPage.goto();

			const row = userViewsPage.table.bodyRows.filter({
				hasText: snapshotName,
			});

			const columnIndex = (
				await userViewsPage.table.headRow.locator('th').allInnerTexts()
			).indexOf('User View Name');

			const cell = row.first().locator('td').nth(columnIndex);

			await expect(cell).toHaveText(snapshotName);
		});

		await test.step('Use delete action button to delete the User view', async () => {
			page.once('dialog', async (dialog) => {
				await dialog.accept();
			});

			await userViewsPage.table.container
				.getByRole('button', {name: 'Delete'})
				.first()
				.click();

			await waitForAlert(page, 'Success:View was deleted successfully.', {
				autoClose: false,
				timeout: 10000,
			});
		});

		await test.step('Check deleted User View is no longer visible', async () => {
			await userViewsPage.emptyStateTitle.waitFor({state: 'visible'});
		});
	}
);
