/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {loginAnalyticsCloudTest} from '../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../fixtures/loginTest';
import {liferayConfig} from '../../liferay.config';
import getRandomString from '../../utils/getRandomString';
import {syncAnalyticsCloud} from '../analytics-settings-web/utils/analyticsSettings';
import {CardSelector} from './utils/selectors';
import {closeSessions} from './utils/sessions';
import {changeTimeFilter} from './utils/time-filter';
import {navigateToACSitesPageViaURL} from './utils/navigation';

export const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	loginAnalyticsCloudTest(),
	loginTest()
);

async function sendEventByURL(page: Page, queryParams: string) {
	await page.goto(liferayConfig.environment.baseUrl + `/home?${queryParams}`);

	await page.waitForTimeout(3000);
}

async function checkAcquisitionChannelCount(
	acquisitionChannel: string,
	count: string,
	page: Page
) {
	const acquisitionChannelElement = page.getByText(acquisitionChannel);

	await expect(acquisitionChannelElement).toBeVisible({
		timeout: 5 * 1000,
	});

	const acquisitionChannelCount = await page.evaluate(
		(acquisitionChannel) => {
			const acquisitionChannelRow = Array.from(
				document.querySelectorAll('.acquisitions-card-root tbody tr')
			).find(
				(element) =>
					element.querySelector('.table-title').textContent ===
					acquisitionChannel
			);

			return acquisitionChannelRow.querySelector('.count').textContent;
		},
		acquisitionChannel
	);

	expect(acquisitionChannelCount).toBe(count);
}

test('check if acquisition card displays PAID SEARCH channel after receiving an event', async ({
	apiHelpers,
	page,
}) => {
	const channelName = 'My Property - ' + getRandomString();

	const {channel, project} = await syncAnalyticsCloud({
		apiHelpers,
		channelName,
		page,
	});

	await test.step('send event to initialize channel followed by closing the session', async () => {
		await sendEventByURL(page, 'utm_medium=paidsearch');

		await closeSessions(apiHelpers, page);
	});

	await test.step('go to AC workspace', async () => {
		await navigateToACSitesPageViaURL({
			channelID: channel.id,
			page,
			projectID: project.groupId,
		});

		await page.waitForTimeout(3000);
	});

	await test.step('change time filter in Acquisition card to Last 24 Hours and check if channel has count as 1', async () => {
		await changeTimeFilter({
			cardSelector: CardSelector.Acquisition,
			page,
			timeFilterPeriod: 'Last 24 hours',
		});

		await checkAcquisitionChannelCount('paid search', '1', page);
	});

	await test.step('delete channel', async () => {
		await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
			`[${channel.id}]`,
			project.groupId
		);
	});
});

test('check if acquisition card displays DIRECT channel after receiving an event', async ({
	apiHelpers,
	page,
}) => {
	const channelName = 'My Property - ' + getRandomString();

	const {channel, project} = await syncAnalyticsCloud({
		apiHelpers,
		channelName,
		page,
	});

	await test.step('send event to initialize channel followed by closing the session', async () => {
		await sendEventByURL(page, '');

		await closeSessions(apiHelpers, page);
	});

	await test.step('go to AC workspace', async () => {
		await navigateToACSitesPageViaURL({
			channelID: channel.id,
			page,
			projectID: project.groupId,
		});

		await page.waitForTimeout(3000);
	});

	await test.step('change time filter in Acquisition card to Last 24 Hours and check if channel has count as 1', async () => {
		await changeTimeFilter({
			cardSelector: CardSelector.Acquisition,
			page,
			timeFilterPeriod: 'Last 24 hours',
		});

		await checkAcquisitionChannelCount('direct', '1', page);
	});

	await test.step('delete channel', async () => {
		await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
			`[${channel.id}]`,
			project.groupId
		);
	});
});

test('check if acquisition card displays SOCIAL channel after receiving an event', async ({
	apiHelpers,
	page,
}) => {
	const channelName = 'My Property - ' + getRandomString();

	const {channel, project} = await syncAnalyticsCloud({
		apiHelpers,
		channelName,
		page,
	});

	await test.step('send event to initialize channel followed by closing the session', async () => {
		await sendEventByURL(page, 'utm_medium=social');

		await closeSessions(apiHelpers, page);
	});

	await test.step('go to AC workspace', async () => {
		await navigateToACSitesPageViaURL({
			channelID: channel.id,
			page,
			projectID: project.groupId,
		});

		await page.waitForTimeout(3000);
	});

	await test.step('change time filter in Acquisition card to Last 24 Hours and check if channel has count as 1', async () => {
		await changeTimeFilter({
			cardSelector: CardSelector.Acquisition,
			page,
			timeFilterPeriod: 'Last 24 hours',
		});

		await checkAcquisitionChannelCount('social', '1', page);
	});

	await test.step('delete channel', async () => {
		await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
			`[${channel.id}]`,
			project.groupId
		);
	});
});

test('check if acquisition card displays EMAIL channel after receiving an event', async ({
	apiHelpers,
	page,
}) => {
	const channelName = 'My Property - ' + getRandomString();

	const {channel, project} = await syncAnalyticsCloud({
		apiHelpers,
		channelName,
		page,
	});

	await test.step('send event to initialize channel followed by closing the session', async () => {
		await sendEventByURL(page, 'utm_medium=email');

		await closeSessions(apiHelpers, page);
	});

	await test.step('go to AC workspace', async () => {
		await navigateToACSitesPageViaURL({
			channelID: channel.id,
			page,
			projectID: project.groupId,
		});

		await page.waitForTimeout(3000);
	});

	await test.step('change time filter in Acquisition card to Last 24 Hours and check if channel has count as 1', async () => {
		await changeTimeFilter({
			cardSelector: CardSelector.Acquisition,
			page,
			timeFilterPeriod: 'Last 24 hours',
		});

		await checkAcquisitionChannelCount('email', '1', page);
	});

	await test.step('delete channel', async () => {
		await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
			`[${channel.id}]`,
			project.groupId
		);
	});
});

test('check if acquisition card displays AFFILIATES channel after receiving an event', async ({
	apiHelpers,
	page,
}) => {
	const channelName = 'My Property - ' + getRandomString();

	const {channel, project} = await syncAnalyticsCloud({
		apiHelpers,
		channelName,
		page,
	});

	await test.step('send event to initialize channel followed by closing the session', async () => {
		await sendEventByURL(page, 'utm_medium=affiliate');

		await closeSessions(apiHelpers, page);
	});

	await test.step('go to AC workspace', async () => {
		await navigateToACSitesPageViaURL({
			channelID: channel.id,
			page,
			projectID: project.groupId,
		});

		await page.waitForTimeout(3000);
	});

	await test.step('change time filter in Acquisition card to Last 24 Hours and check if channel has count as 1', async () => {
		await changeTimeFilter({
			cardSelector: CardSelector.Acquisition,
			page,
			timeFilterPeriod: 'Last 24 hours',
		});

		await checkAcquisitionChannelCount('affiliates', '1', page);
	});

	await test.step('delete channel', async () => {
		await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
			`[${channel.id}]`,
			project.groupId
		);
	});
});

test('check if acquisition card displays ORGANIC channel after receiving an event', async ({
	apiHelpers,
	page,
}) => {
	const channelName = 'My Property - ' + getRandomString();

	const {channel, project} = await syncAnalyticsCloud({
		apiHelpers,
		channelName,
		page,
	});

	await test.step('send event to initialize channel followed by closing the session', async () => {
		await sendEventByURL(page, 'utm_medium=organic');

		await closeSessions(apiHelpers, page);
	});

	await test.step('go to AC workspace', async () => {
		await navigateToACSitesPageViaURL({
			channelID: channel.id,
			page,
			projectID: project.groupId,
		});

		await page.waitForTimeout(3000);
	});

	await test.step('change time filter in Acquisition card to Last 24 Hours and check if channel has count as 1', async () => {
		await changeTimeFilter({
			cardSelector: CardSelector.Acquisition,
			page,
			timeFilterPeriod: 'Last 24 hours',
		});

		await checkAcquisitionChannelCount('organic', '1', page);
	});

	await test.step('delete channel', async () => {
		await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
			`[${channel.id}]`,
			project.groupId
		);
	});
});

test('check if acquisition card displays DISPLAY channel after receiving an event', async ({
	apiHelpers,
	page,
}) => {
	const channelName = 'My Property - ' + getRandomString();

	const {channel, project} = await syncAnalyticsCloud({
		apiHelpers,
		channelName,
		page,
	});

	await test.step('send event to initialize channel followed by closing the session', async () => {
		await sendEventByURL(page, 'utm_medium=display');

		await closeSessions(apiHelpers, page);
	});

	await test.step('go to AC workspace', async () => {
		await navigateToACSitesPageViaURL({
			channelID: channel.id,
			page,
			projectID: project.groupId,
		});

		await page.waitForTimeout(3000);
	});

	await test.step('change time filter in Acquisition card to Last 24 Hours and check if channel has count as 1', async () => {
		await changeTimeFilter({
			cardSelector: CardSelector.Acquisition,
			page,
			timeFilterPeriod: 'Last 24 hours',
		});

		await checkAcquisitionChannelCount('display', '1', page);
	});

	await test.step('delete channel', async () => {
		await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
			`[${channel.id}]`,
			project.groupId
		);
	});
});

test('check if acquisition card displays REFERRAL channel after receiving an event', async ({
	apiHelpers,
	page,
}) => {
	const channelName = 'My Property - ' + getRandomString();

	const {channel, project} = await syncAnalyticsCloud({
		apiHelpers,
		channelName,
		page,
	});

	await test.step('send event to initialize channel followed by closing the session', async () => {
		await sendEventByURL(page, 'utm_medium=referral');

		await closeSessions(apiHelpers, page);
	});

	await test.step('go to AC workspace', async () => {
		await navigateToACSitesPageViaURL({
			channelID: channel.id,
			page,
			projectID: project.groupId,
		});

		await page.waitForTimeout(3000);
	});

	await test.step('change time filter in Acquisition card to Last 24 Hours and check if channel has count as 1', async () => {
		await changeTimeFilter({
			cardSelector: CardSelector.Acquisition,
			page,
			timeFilterPeriod: 'Last 24 hours',
		});

		await checkAcquisitionChannelCount('referral', '1', page);
	});

	await test.step('delete channel', async () => {
		await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
			`[${channel.id}]`,
			project.groupId
		);
	});
});

test('check if acquisition card displays OTHER channel after receiving an event', async ({
	apiHelpers,
	page,
}) => {
	const channelName = 'My Property - ' + getRandomString();

	const {channel, project} = await syncAnalyticsCloud({
		apiHelpers,
		channelName,
		page,
	});

	await test.step('send event to initialize channel followed by closing the session', async () => {
		await sendEventByURL(page, 'utm_medium=other');

		await closeSessions(apiHelpers, page);
	});

	await test.step('go to AC workspace', async () => {
		await navigateToACSitesPageViaURL({
			channelID: channel.id,
			page,
			projectID: project.groupId,
		});

		await page.waitForTimeout(3000);
	});

	await test.step('change time filter in Acquisition card to Last 24 Hours and check if channel has count as 1', async () => {
		await changeTimeFilter({
			cardSelector: CardSelector.Acquisition,
			page,
			timeFilterPeriod: 'Last 24 hours',
		});

		await checkAcquisitionChannelCount('other', '1', page);
	});

	await test.step('delete channel', async () => {
		await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
			`[${channel.id}]`,
			project.groupId
		);
	});
});
