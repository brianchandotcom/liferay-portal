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
	'Events sent from a synced site carry the synced channel and data source',
	{
		tag: '@LRAC-8713',
	},
	async ({analyticsChannel: channel, apiHelpers, page, project, site}) => {
		const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			title: getRandomString(),
		});

		await syncAnalyticsCloudViaAPI({
			apiHelpers,
			channel,
			project,
			siteId: Number(site.id),
		});

		const capturedEvents = captureAnalyticsEvents(page);

		await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyURL}`);

		await expect
			.poll(() => capturedEvents.length, {timeout: 30000})
			.toBeGreaterThan(0);

		const event = capturedEvents[0];

		expect(event.channelId).toBe(channel.id);
		expect(event.dataSourceId).toBeTruthy();
	}
);

test(
	'Events carry a distinct channel id for each synced site',
	{
		tag: '@LRAC-12154',
	},
	async ({
		analyticsChannel: firstChannel,
		apiHelpers,
		page,
		project,
		site: firstSite,
	}) => {
		const firstLayout = await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: firstSite.id,
			title: getRandomString(),
		});

		await syncAnalyticsCloudViaAPI({
			apiHelpers,
			channel: firstChannel,
			project,
			siteId: Number(firstSite.id),
		});

		let secondChannel;

		try {
			secondChannel =
				await apiHelpers.jsonWebServicesOSBFaro.createChannel(
					'My Property - ' + getRandomString(),
					project.groupId
				);

			const secondSite = await apiHelpers.headlessAdminSite.postSite({
				name: 'AC Site ' + getRandomString(),
			});

			const secondLayout =
				await apiHelpers.jsonWebServicesLayout.addLayout({
					groupId: secondSite.id,
					title: getRandomString(),
				});

			await syncAnalyticsCloudViaAPI({
				apiHelpers,
				channel: secondChannel,
				project,
				siteId: Number(secondSite.id),
			});

			const capturedEvents = captureAnalyticsEvents(page);

			// Interact with the first site

			await page.goto(
				`/web${firstSite.friendlyUrlPath}${firstLayout.friendlyURL}`
			);

			await expect
				.poll(
					() =>
						capturedEvents.find(
							(event) => event.channelId === firstChannel.id
						),
					{timeout: 30000}
				)
				.toBeTruthy();

			// Interact with the second site

			await page.goto(
				`/web${secondSite.friendlyUrlPath}${secondLayout.friendlyURL}`
			);

			await expect
				.poll(
					() =>
						capturedEvents.find(
							(event) => event.channelId === secondChannel.id
						),
					{timeout: 30000}
				)
				.toBeTruthy();

			expect(firstChannel.id).not.toBe(secondChannel.id);
		}
		finally {
			if (secondChannel?.id) {
				await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
					`[${secondChannel.id}]`,
					project.groupId
				);
			}
		}
	}
);

test(
	'Identity sends a hashed email address for a logged-in user',
	{
		tag: '@LRAC-12153',
	},
	async ({analyticsChannel: channel, apiHelpers, page, project, site}) => {
		const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			title: getRandomString(),
		});

		await syncAnalyticsCloudViaAPI({
			apiHelpers,
			channel,
			project,
			siteId: Number(site.id),
		});

		let emailAddressHashed: string | undefined;

		page.on('request', (request) => {
			if (request.method() !== 'POST') {
				return;
			}

			const postData = request.postData();

			if (!postData || !postData.includes('"emailAddressHashed"')) {
				return;
			}

			try {
				const eventBucket = JSON.parse(postData);

				if (eventBucket.emailAddressHashed) {
					emailAddressHashed = eventBucket.emailAddressHashed;
				}
			}
			catch {

				// Ignore non-JSON bodies; only analytics POSTs are valid here.

			}
		});

		await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyURL}`);

		await expect
			.poll(() => emailAddressHashed, {timeout: 30000})
			.toBeTruthy();
	}
);
