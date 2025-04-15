/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {loginAnalyticsCloudTest} from '../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';
import {
	connectToAnalyticsCloud,
	disconnectFromAnalyticsCloud,
	goPreviousStep,
	goToAnalyticsCloudInstanceSettings,
} from '../analytics-settings-web/utils/analytics-settings';
import {createChannel} from './utils/channel';
import {
	checkDataSourceStatus,
	findDataSource,
	renameDataSource,
} from './utils/data-source';
import {waitForLoading} from './utils/loading';
import {ACPage, navigateToACSettingsViaURL} from './utils/navigation';

export const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	loginAnalyticsCloudTest(),
	loginTest()
);

test(
	'Disconnect Workspace in DXP and then check in Analytics Cloud if Data Source status is DISCONNECTED',

	{
		tag: '@LPD-44493',
	},

	async ({apiHelpers, page}) => {
		const channelName = 'My Property - ' + getRandomString();

		const {channel, project} = await createChannel({
			apiHelpers,
			channelName,
		});

		await test.step('Go to Analytics Cloud settings and add a Data Source', async () => {
			await navigateToACSettingsViaURL({
				acPage: ACPage.dataSourcePage,
				page,
				projectID: project.groupId,
			});

			await page.getByRole('link', {name: 'Add Data Source'}).click();

			await page.getByRole('button', {name: 'Liferay DXP'}).click();

			await page.getByRole('button', {name: 'Click to Copy'}).click();

			await page.getByRole('link', {name: 'Done'}).click();
		});

		await test.step('Go to DXP --> Instance Settings --> Analytics Cloud and disconnect the workspace', async () => {
			await goToAnalyticsCloudInstanceSettings(page);

			const disconnectButton = page.getByRole('button', {
				name: 'Disconnect',
			});

			if (await disconnectButton.isVisible()) {
				await disconnectFromAnalyticsCloud(page);
			}

			await connectToAnalyticsCloud(page);

			await expect(
				page.getByRole('heading', {name: 'Property Assignment'})
			).toBeVisible();

			await goPreviousStep(page);

			await disconnectFromAnalyticsCloud(page);
		});

		await test.step('Go back to Analytics Cloud settings and rename Data Source previously added', async () => {
			await navigateToACSettingsViaURL({
				acPage: ACPage.dataSourcePage,
				page,
				projectID: project.groupId,
			});

			await renameDataSource({
				newDataSourceName: 'TEST_DS',
				oldDataSourceName: 'Liferay DXP',
				page,
			});
		});

		await test.step('Go back to Analytics Cloud settings and search for the renamed Data Source', async () => {
			await navigateToACSettingsViaURL({
				acPage: ACPage.dataSourcePage,
				page,
				projectID: project.groupId,
			});

			const dataSource = await findDataSource({
				dataSourceName: 'TEST_DS',
				page,
			});

			if (dataSource) {
				await dataSource.click();
			}
			else {
				throw new Error('Data Source not found');
			}
		});

		await test.step('Check if Data Source status is DISCONNECTED', async () => {
			await waitForLoading(page);

			await checkDataSourceStatus({
				dataSourceName: 'TEST_DS',
				dataSourceStatus: 'Disconnected',
				page,
			});
		});

		await test.step('delete channel', async () => {
			await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
				`[${channel.id}]`,
				project.groupId
			);
		});
	}
);
