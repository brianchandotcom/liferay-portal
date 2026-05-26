/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginAnalyticsCloudTest} from '../../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import {syncAnalyticsCloud} from '../../analytics-settings-web/main/utils/analytics-settings';
import getFragmentDefinition from '../../layout-content-page-editor-web/main/utils/getFragmentDefinition';
import getPageDefinition from '../../layout-content-page-editor-web/main/utils/getPageDefinition';

const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginAnalyticsCloudTest(),
	loginTest()
);

test(
	'Verify events after navigating by SPA',
	{
		tag: ['@LPD-56895', '@LRAC-8800'],
	},
	async ({apiHelpers, page, site}) => {
		const {channel} = await syncAnalyticsCloud({
			apiHelpers,
			channelName: 'My Property - ' + getRandomString(),
			page,
			siteName: site.name,
		});

		const layout1 = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				getFragmentDefinition({
					id: getRandomString(),
					key: 'BASIC_COMPONENT-heading',
				}),
			]),
			siteId: site.id,
			title: 'MyPage 1',
		});

		const layout2 = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				getFragmentDefinition({
					id: getRandomString(),
					key: 'BASIC_COMPONENT-heading',
				}),
			]),
			siteId: site.id,
			title: 'MyPage 2',
		});

		const pageViewedTitles: string[] = [];
		const pageViewedEvents: Array<{
			channelId: string;
			dataSourceId: string;
		}> = [];

		page.on('request', (request) => {
			if (request.method() !== 'POST') {
				return;
			}

			const postData = request.postData();

			if (!postData || !postData.includes('"eventId":"pageViewed"')) {
				return;
			}

			try {
				const eventBucket = JSON.parse(postData);

				const title = eventBucket.context?.title;

				if (typeof title === 'string') {
					pageViewedTitles.push(title);
				}

				if (eventBucket.channelId && eventBucket.dataSourceId) {
					pageViewedEvents.push({
						channelId: eventBucket.channelId,
						dataSourceId: eventBucket.dataSourceId,
					});
				}
			}
			catch {

				// Ignore non-JSON bodies; only analytics POSTs are valid here.

			}
		});

		await test.step('Go to My Page 1', async () => {
			await page.goto(
				`/web${site.friendlyUrlPath}${layout1.friendlyUrlPath}`
			);

			await expect
				.poll(() =>
					pageViewedTitles.some((t) => t.includes('MyPage 1'))
				)
				.toBe(true);
		});

		await test.step('Go to My Page 2', async () => {
			await page.goto(
				`/web${site.friendlyUrlPath}${layout2.friendlyUrlPath}`
			);

			await expect
				.poll(() =>
					pageViewedTitles.some((t) => t.includes('MyPage 2'))
				)
				.toBe(true);
		});

		await test.step('Verify channelId and dataSourceId in pageViewed events', async () => {
			await expect.poll(() => pageViewedEvents.length).toBeGreaterThan(0);

			for (const event of pageViewedEvents) {
				expect(event.channelId).toBe(channel.id);
				expect(event.dataSourceId).toBeTruthy();
			}
		});
	}
);
