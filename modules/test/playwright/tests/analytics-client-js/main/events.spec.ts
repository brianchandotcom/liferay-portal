/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedChannelTest} from '../../../fixtures/isolatedChannelTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginAnalyticsCloudTest} from '../../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import {syncAnalyticsCloudViaAPI} from '../../analytics-settings-web/main/utils/analytics-settings';
import getFragmentDefinition from '../../layout-content-page-editor-web/main/utils/getFragmentDefinition';
import getPageDefinition from '../../layout-content-page-editor-web/main/utils/getPageDefinition';
import {captureAnalyticsEvents} from './utils/captureAnalyticsEvents';

const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedChannelTest,
	isolatedSiteTest,
	loginAnalyticsCloudTest(),
	loginTest()
);

test(
	'Verify events after navigating by SPA',
	{
		tag: ['@LPD-56895', '@LRAC-8800'],
	},
	async ({analyticsChannel: channel, apiHelpers, page, project, site}) => {
		await syncAnalyticsCloudViaAPI({
			apiHelpers,
			channel,
			project,
			siteId: Number(site.id),
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

		const capturedEvents = captureAnalyticsEvents(page);

		const pageViewedWithTitle = (titleFragment: string) =>
			capturedEvents.some(
				(event) =>
					event.eventId === 'pageViewed' &&
					typeof event.context.title === 'string' &&
					event.context.title.includes(titleFragment)
			);

		await test.step('Go to My Page 1', async () => {
			await page.goto(
				`/web${site.friendlyUrlPath}${layout1.friendlyUrlPath}`
			);

			await expect.poll(() => pageViewedWithTitle('MyPage 1')).toBe(true);
		});

		await test.step('Go to My Page 2', async () => {
			await page.goto(
				`/web${site.friendlyUrlPath}${layout2.friendlyUrlPath}`
			);

			await expect.poll(() => pageViewedWithTitle('MyPage 2')).toBe(true);
		});

		await test.step('Verify channelId and dataSourceId in pageViewed events', async () => {
			const pageViewedEvents = capturedEvents.filter(
				(event) => event.eventId === 'pageViewed'
			);

			expect(pageViewedEvents.length).toBeGreaterThan(0);

			for (const event of pageViewedEvents) {
				expect(event.channelId).toBe(channel.id);
				expect(event.dataSourceId).toBeTruthy();
			}
		});
	}
);

test(
	'A page stops sending pageViewed events once its site is desynced from the channel',
	{
		tag: '@LRAC-13010',
	},
	async ({analyticsChannel: channel, apiHelpers, page, project, site}) => {
		await syncAnalyticsCloudViaAPI({
			apiHelpers,
			channel,
			project,
			siteId: Number(site.id),
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				getFragmentDefinition({
					id: getRandomString(),
					key: 'BASIC_COMPONENT-heading',
				}),
			]),
			siteId: site.id,
			title: 'MyPage',
		});

		const pagePath = `/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`;

		const capturedEvents = captureAnalyticsEvents(page);

		const pageViewedCount = () =>
			capturedEvents.filter((event) => event.eventId === 'pageViewed')
				.length;

		await test.step('The synced site sends a pageViewed event', async () => {
			await page.goto(pagePath);

			await expect.poll(() => pageViewedCount()).toBeGreaterThan(0);
		});

		await test.step('Desyncing the site stops the pageViewed events', async () => {
			await apiHelpers.analyticsSettingsRest.syncSitesToChannel(
				channel.id,
				[]
			);

			const countBeforeReload = pageViewedCount();

			await page.goto(pagePath);

			await page.waitForLoadState('networkidle');

			// Give the analytics client time to send before asserting it did not

			await page.waitForTimeout(3000);

			expect(pageViewedCount()).toBe(countBeforeReload);
		});
	}
);
