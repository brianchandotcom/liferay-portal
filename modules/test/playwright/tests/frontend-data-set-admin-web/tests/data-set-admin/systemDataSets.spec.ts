/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../../fixtures/loginTest';
import {dataSetManagerApiHelpersTest} from '../../fixtures/dataSetManagerApiHelpersTest';
import {systemDataSetsPageTest} from './fixtures/systemDataSetsPageTest';

export const test = mergeTests(
	dataSetManagerApiHelpersTest,
	systemDataSetsPageTest,
	featureFlagsTest({
		'LPD-37531': true,
		'LPS-164563': true,
	}),
	loginTest()
);

test(
	'Select a system data set to customize',
	{tag: '@LPD-37531'},
	async ({systemDataSetsPage}) => {
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

			await expect(creationModal.search).toBeVisible();

			await expect(classicSampleListItem).toBeVisible();
			await expect(customizedSampleListItem).toBeVisible();
		});

		await test.step('Search system data set items', async () => {
			await creationModal.search.fill('Classic');

			await expect(classicSampleListItem).toBeVisible();
			await expect(customizedSampleListItem).toBeHidden();

			await creationModal.search.fill('aaa');

			await expect(classicSampleListItem).toBeHidden();
			await expect(customizedSampleListItem).toBeHidden();

			await expect(
				creationModal.container.getByText('No Results Found')
			).toBeVisible();

			await creationModal.search.fill('');

			await expect(classicSampleListItem).toBeVisible();
			await expect(customizedSampleListItem).toBeVisible();
		});

		await test.step('Select a system data set', async () => {
			await classicSampleListItem.click();

			await expect(classicSampleListItem).toHaveClass(/selected/);
		});
	}
);
