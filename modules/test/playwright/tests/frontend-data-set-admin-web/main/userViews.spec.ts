/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataSetManagerApiHelpersTest} from '../../../fixtures/dataSetManagerApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import {settingsPageTest} from './fixtures/settingsPageTest';
import {waitForAlert} from '../../../utils/waitForAlert';
import {userViewsPageTest} from './fixtures/userViewsPageTest';

export const test = mergeTests(
	dataSetManagerApiHelpersTest,
	featureFlagsTest({
		'LPD-36105': {enabled: true},
	}),
	settingsPageTest,
	userViewsPageTest,
	loginTest()
);

interface IDataSet {
	erc: string;
	label: string;
}

let dataSetERCs: string[] = [];

const createDataSet = async ({
	dataSetManagerApiHelpers,
}: {
	dataSetManagerApiHelpers: DataSetManagerApiHelpers;
}): Promise<IDataSet> => {
	const dataSet = {
		erc: getRandomString(),
		label: getRandomString(),
	};

	await dataSetManagerApiHelpers.createDataSet({
		erc: dataSet.erc,
		label: dataSet.label,
		snapshotsEnabled: true,
	});

	dataSetERCs.push(dataSet.erc);

	return dataSet;
};

test.beforeEach(async ({dataSetManagerApiHelpers}) => {
	dataSetERCs = [];

	await test.step('Delete previous snapshots', async () => {
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
});

test.afterEach(async ({dataSetManagerApiHelpers}) => {
	for (const dataSetERC of dataSetERCs) {
		await dataSetManagerApiHelpers.deleteDataSet({erc: dataSetERC});
	}
});

test(
	'Manage User Views',
	{tag: '@LPD-71991'},
	async ({dataSetManagerApiHelpers, page, userViewsPage}) => {
		const snapshotName = getRandomString();
		const dataSet = await createDataSet({
			dataSetManagerApiHelpers,
		});

		await test.step('Create User View', async () => {
			await dataSetManagerApiHelpers.createDataSetSnapshot({
				dataSetERC: dataSet.erc,
				snapshotName,
			});
		});

		await test.step('Check the User View is visible in Manage User Views Page', async () => {
			await userViewsPage.goto();

			const row = userViewsPage.table.bodyRows.filter({
				hasText: snapshotName,
			});

			await expect(row.first()).toContainText(snapshotName);
		});

		await test.step('Use delete action button to delete the User view', async () => {
			page.once('dialog', async (dialog) => {
				await dialog.accept();
			});

			await userViewsPage.table.bodyRows
				.filter({hasText: snapshotName})
				.first()
				.getByRole('button', {name: 'Delete'})
				.click();

			await waitForAlert(page, 'Success:View was deleted successfully.', {
				autoClose: false,
			});
		});

		await test.step('Check deleted User View is no longer visible', async () => {
			await userViewsPage.emptyStateTitle.waitFor({state: 'visible'});
		});
	}
);

test('Access Manage User Views from Settings with Data Set filter preapplied', async ({
	dataSetManagerApiHelpers,
	page,
	settingsPage,
	userViewsPage,
}) => {
	const sourceDataSet = await createDataSet({
		dataSetManagerApiHelpers,
	});
	const otherDataSet = await createDataSet({
		dataSetManagerApiHelpers,
	});
	const sourceSnapshotName = getRandomString();
	const otherSnapshotName = getRandomString();

	await test.step('Create snapshots for two different data sets', async () => {
		await dataSetManagerApiHelpers.createDataSetSnapshot({
			dataSetERC: sourceDataSet.erc,
			snapshotName: sourceSnapshotName,
		});

		await dataSetManagerApiHelpers.createDataSetSnapshot({
			dataSetERC: otherDataSet.erc,
			snapshotName: otherSnapshotName,
		});
	});

	await test.step('Open Manage User Views from Settings', async () => {
		await settingsPage.goto({dataSetLabel: sourceDataSet.label});

		await Promise.all([
			page.waitForURL((url) =>
				url.toString().includes('manage_user_views')
			),
			settingsPage.gotoManageUserViews(),
		]);

		await userViewsPage.waitForReadyState();
	});

	await test.step('Validate Data Set filter is preapplied in UI and table is filtered', async () => {
		await expect(
			userViewsPage.filterResumeButton
				.filter({hasText: 'Data Set:'})
				.first()
		).toBeVisible();

		await expect(
			userViewsPage.table.bodyRows
				.filter({hasText: sourceSnapshotName})
				.first()
		).toBeVisible();

		await expect(
			userViewsPage.table.bodyRows.filter({
				hasText: otherSnapshotName,
			})
		).toHaveCount(0);
	});
});

test('Delete User Views through bulk action', async ({
	dataSetManagerApiHelpers,
	page,
	userViewsPage,
}) => {
	const dataSet = await createDataSet({
		dataSetManagerApiHelpers,
	});
	const firstSnapshotName = getRandomString();
	const secondSnapshotName = getRandomString();

	await test.step('Create two snapshots in the same data set', async () => {
		await dataSetManagerApiHelpers.createDataSetSnapshot({
			dataSetERC: dataSet.erc,
			snapshotName: firstSnapshotName,
		});

		await dataSetManagerApiHelpers.createDataSetSnapshot({
			dataSetERC: dataSet.erc,
			snapshotName: secondSnapshotName,
		});
	});

	await test.step('Go to Manage User Views and select both snapshots', async () => {
		await userViewsPage.goto();

		await userViewsPage.getRowSelectionCheckbox(firstSnapshotName).check();
		await userViewsPage.getRowSelectionCheckbox(secondSnapshotName).check();
	});

	await test.step('Delete snapshots using bulk action', async () => {
		await expect(userViewsPage.bulkActions.container).toBeVisible();
		await userViewsPage.clickBulkDeleteAction();

		const deleteModal = page.getByRole('dialog', {
			name: 'Delete User View',
		});

		await expect(deleteModal).toBeVisible();

		await deleteModal.getByRole('button', {name: 'Delete'}).click();

		await waitForAlert(page, 'Success:View was deleted successfully.', {
			autoClose: false,
		});
	});

	await test.step('Validate snapshots are deleted', async () => {
		await expect(
			userViewsPage.table.bodyRows.filter({hasText: firstSnapshotName})
		).toHaveCount(0);
		await expect(
			userViewsPage.table.bodyRows.filter({hasText: secondSnapshotName})
		).toHaveCount(0);
	});
});
