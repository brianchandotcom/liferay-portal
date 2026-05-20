/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedChannelTest} from '../../../fixtures/isolatedChannelTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginAnalyticsCloudTest} from '../../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../../fixtures/loginTest';
import {createDataSource} from '../../osb-faro-web/main/utils/data-source';
import {acceptsCookiesBanner} from '../../osb-faro-web/main/utils/portal';
import {
	connectToAnalyticsCloud,
	disconnectFromAnalyticsCloud,
	goNextStep,
	goToAnalyticsCloudInstanceSettings,
	syncAllContacts,
	toggleSiteSync,
} from './utils/analytics-settings';

export enum JobId {
	ContentRecommenderMostPopularItemsEnabled = 'contentRecommenderMostPopularItems',
	ContentRecommenderUserPersonalizationEnabled = 'contentRecommenderUserPersonalization',
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
		jobId: JobId.ContentRecommenderUserPersonalizationEnabled,
		jobTitle: 'Users Personalized Content Recommendations',
	},
];

export const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPD-20640': {enabled: true},
	}),
	isolatedChannelTest,
	isolatedSiteTest,
	loginAnalyticsCloudTest(),
	loginTest()
);

test.describe('Test All Recommendation Job', () => {
	jobs.forEach(({jobId, jobTitle}) => {
		test(`Enable / Disable "${jobTitle}" job in the recommendations screen`, async ({
			analyticsChannel: channel,
			page,
			site,
		}) => {
			const {token} = await createDataSource(page);

			await goToAnalyticsCloudInstanceSettings(page);

			await acceptsCookiesBanner(page);

			await disconnectFromAnalyticsCloud(page);

			await connectToAnalyticsCloud(page, {token});

			await toggleSiteSync({
				channelName: channel.name,
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
		});
	});
});
