/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginAnalyticsCloudTest} from '../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../fixtures/loginTest';
import {liferayConfig} from '../../liferay.config';
import getRandomString from '../../utils/getRandomString';
import {createChannel} from './utils/channel';
import {createIndividuals} from './utils/individuals';
import {ACPage, navigateTo, navigateToACPageViaURL} from './utils/navigation';
import {CardSelectors} from './utils/selectors';
import {changeTimeFilter} from './utils/time-filter';
import {viewNameOnTableList} from './utils/utils';

export const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	loginAnalyticsCloudTest(),
	loginTest()
);

test(
	'Verify that clicking the "All Search Terms" link redirects the user to the Search Terms tab with the same time filter previously set.',

	{
		tag: '@LPD-26181',
	},

	async ({apiHelpers, page}) => {
		const channelName = 'My Property - ' + getRandomString();

		const {channel, project} = await createChannel({
			apiHelpers,
			channelName,
		});

		const generateIndividual = (name) => {
			const id = getRandomString();

			return {
				id,
				name,
			};
		};

		const individuals = [generateIndividual('ac')];

		await test.step('Create Individuals', async () => {
			await createIndividuals({
				apiHelpers,
				individuals,
			});
		});

		await test.step('Create Individuals Events', async () => {
			const date = new Date();

			await apiHelpers.jsonWebServicesOSBAsah.createEvents(
				individuals.map((individual) => ({
					applicationId: 'Page',
					canonicalUrl: `https://${liferayConfig.environment.baseUrl}/search`,
					channelId: channel.id,
					eventDate: date.toISOString(),
					eventId: 'pageViewed',
					title: 'Liferay',
					url: `https://${liferayConfig.environment.baseUrl}/search?q=test`,
					userId: individual.id,
				}))
			);
		});

		await test.step('Go to Analytics Cloud and Switch the property', async () => {
			await navigateToACPageViaURL({
				acPage: ACPage.sitePage,
				channelID: channel.id,
				page,
				projectID: project.groupId,
			});
		});

		await test.step('Change time filter in Search Terms to Last 24 Hours and click on "All Search Terms" link', async () => {
			await changeTimeFilter({
				cardSelector: CardSelectors.SearchTerms,
				page,
				timeFilterPeriod: 'Last 24 hours',
			});

			await navigateTo({
				page,
				pageName: 'All Search Terms',
			});
		});

		await test.step('Check if user is in the Search Terms tab with the same time filter previously set (Last 24h)', async () => {
			await expect(
				page.getByRole('link', {exact: true, name: 'Search Terms'})
			).toBeVisible();

			await expect(
				page.getByText(`Search Terms on ${channelName}`)
			).toBeVisible();

			await expect(
				page.getByRole('button', {name: 'Last 24 hours'})
			).toBeVisible();
		});

		await test.step('Check if count and percentage are correct in Search Terms tab', async () => {
			await viewNameOnTableList({
				itemNames: ['test', '100.00%'],
				page,
			});

			await expect(
				page.getByRole('cell', {exact: true, name: '1'})
			).toBeVisible();
		});

		await test.step('delete channel', async () => {
			await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
				`[${channel.id}]`,
				project.groupId
			);
		});
	}
);
