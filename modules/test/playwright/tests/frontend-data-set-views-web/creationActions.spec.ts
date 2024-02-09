/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {liferayConfig} from '../../liferay.config';
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
	loginTest,
	viewsPageTest
);

test('Create a Creation Action', async ({
	actionsPage,
	dataSetsPage,
	page,
	viewsPage,
}) => {
	const LINK_CREATION_ACTION_NAME = 'Link creation action';

	await test.step('Create Data Set', async () => {
		await dataSetsPage.goto();
		await dataSetsPage.createDataSet();
	});

	await test.step('Create Data Set View', async () => {
		await viewsPage.goto();
		await viewsPage.createDataSetView();
	});

	await test.step('Go to Actions tab', async () => {
		await actionsPage.goto();
	});

	await test.step('Create a creation action', async () => {
		await actionsPage.createCreationAction({
			icon: 'arrow-right-full',
			name: LINK_CREATION_ACTION_NAME,
			type: 'link',
			url: liferayConfig.environment.baseUrl,
		});
	});

	await test.step('Check that the creation action is in the list', async () => {
		await expect(
			page
				.getByRole('cell', {
					exact: true,
					name: LINK_CREATION_ACTION_NAME,
				})
				.locator('span')
				.first()
		).toBeVisible();
	});

	await test.step('Delete Data Set', async () => {
		await dataSetsPage.goto();
		await dataSetsPage.deleteDataSet();
	});
});
