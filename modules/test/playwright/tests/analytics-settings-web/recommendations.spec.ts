/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
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
import {createChannel} from '../osb-faro-web/utils/channel';
import {createDataSource} from '../osb-faro-web/utils/data-source';
import {acceptsCookiesBanner} from '../osb-faro-web/utils/portal';
import {
	connectToAnalyticsCloud,
	disconnectFromAnalyticsCloud,
	goNextStep,
	goToAnalyticsCloudInstanceSettings,
	syncAllContacts,
	syncSite,
} from './utils/analytics-settings';

export enum JobId {
	ContentRecommenderMostPopularItemsEnabled = 'contentRecommenderMostPopularItemsEnabled',
	ContentRecommenderUserPersonalizationEnabled = 'contentRecommenderUserPersonalizationEnabled',
}

const jobs: {
	jobId: JobId;
	jobTitle: string;
}[] = [
	{
		jobId: JobId.ContentRecommenderMostPopularItemsEnabled,
		jobTitle: 'Most Popular Content',
	},
	{
		jobId: JobId.ContentRecommenderMostPopularItemsEnabled,
		jobTitle: 'Users Personalized Content Recommendations',
	},
];

export const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-20640': true,
	}),
	loginAnalyticsCloudTest(),
	loginTest()
);

test.describe('Test All Recommendation Job', () => {
	jobs.forEach(({jobId, jobTitle}) => {
		test(`Enable / Disable "${jobTitle}" job in the recommendations screen`, async ({
			apiHelpers,
			page,
		}) => {
			const site = await apiHelpers.headlessSite.createSite({
				name: getRandomString(),
			});

			apiHelpers.data.push({id: site.id, type: 'site'});

			const channelName = 'My Property - ' + getRandomString();

			const {channel, project} = await createChannel({
				apiHelpers,
				channelName,
			});

			await createDataSource(page);

			await goToAnalyticsCloudInstanceSettings(page);

			await acceptsCookiesBanner(page);

			await disconnectFromAnalyticsCloud(page);

			await connectToAnalyticsCloud(page);

			await syncSite({
				channelName,
				page,
				siteName: site.name,
			});

			await goNextStep(page);

			await syncAllContacts(page);

			await goNextStep(page);

			await goNextStep(page);

			await page.waitForTimeout(1000);

			expect(page.getByTestId(jobId)).toBeTruthy();

			const toggleElement = await page.locator(
				`[data-testid=${jobId}] #toggle .toggle-switch-check`
			);

			expect(await toggleElement.isChecked()).toBeFalsy();

			await toggleElement.click();

			await page.waitForTimeout(200);

			expect(
				page.getByText(
					`${jobTitle.toLowerCase()} was updated successfully.`
				)
			).toBeTruthy();

			await page.waitForTimeout(1000);

			expect(await toggleElement.isChecked()).toBeTruthy();

			await toggleElement.click();

			await page.waitForTimeout(1000);

			expect(page.getByText(`Disable "${jobTitle}"`)).toBeTruthy();

			expect(
				page.getByText(
					/are you sure you want to disable this recommendation model\?/i
				)
			).toBeTruthy();
			expect(
				page.getByText(
					/disabling this recommendation model will stop updates for pages using it, and those pages will no longer receive new recommendations\./i
				)
			).toBeTruthy();

			const disableButton = page.getByRole('button', {
				name: /disable/i,
			});

			expect(disableButton).toBeTruthy();

			await disableButton.click();

			await page.waitForTimeout(200);

			expect(
				page.getByText(
					`${jobTitle.toLowerCase()} was disabled successfully.`
				)
			).toBeTruthy();

			expect(await toggleElement.isChecked()).toBeFalsy();

			await test.step('Delete channel and delete site on the DXP side', async () => {
				await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
					`[${channel.id}]`,
					project.groupId
				);

				await page.goto(liferayConfig.environment.baseUrl);

				await apiHelpers.headlessSite.deleteSite(String(site.id));
			});
		});
	});
});
