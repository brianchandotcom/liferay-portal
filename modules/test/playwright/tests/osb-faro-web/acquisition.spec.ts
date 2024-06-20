/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, Locator, mergeTests, Page} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {loginAnalyticsCloudTest} from '../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../fixtures/loginTest';
import {liferayConfig} from '../../liferay.config';
import getRandomString from '../../utils/getRandomString';
import {syncAnalyticsCloud} from '../analytics-settings-web/utils/analyticsSettings';
import {faroConfig} from './faro.config';
import {createChannel} from './utils/channel';
import {closeSessions} from './utils/sessions';

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

async function changeTimeFilterInSitesOverviewCard(
	cardName: string,
	page: Page,
	timeFilterPeriod: string,
	propertyName?: string
) {
	if (cardName === 'Activities') {
		`${propertyName} ActivitiesDWMLast 30 days`;
	}

	const cardNameSelected = page.getByText(`${cardName}Last 30 days`);

	await cardNameSelected.getByRole('button', {name: 'Last 30 days'}).click();

	let loadingAnimation;

	switch (cardName) {
		case 'Acquisitions':
			loadingAnimation = page.locator(
				'.card.card-root.acquisitions-card-root .loading-animation'
			);
			break;

		case 'Activities':
			loadingAnimation = page.locator(
				'.card.card-root.analytics-metrics-card .loading-animation'
			);
			break;

		case 'Interests':
			loadingAnimation = page.locator(
				'.card.card-root.interests-card-root .loading-animation'
			);
			break;

		case 'Search Terms':
			loadingAnimation = page.locator(
				'.card.card-root.search-terms-card-root .loading-animation'
			);
			break;

		case 'Session Technology':
			loadingAnimation = page.locator(
				'.card.card-root.analytics-devices-card .loading-animation'
			);
			break;

		case 'Sessions by Location':
			loadingAnimation = page.locator(
				'.card.card-root.analytics-locations-card .loading-animation'
			);
			break;

		case 'Top Pages':
			loadingAnimation = page.locator(
				'.card.card-root.top-pages-card-root .loading-animation'
			);
			break;

		case 'Visitors by Day and Time':
			loadingAnimation = page.locator(
				'.card.card-root.visitors-by-time-card .loading-animation'
			);
			break;
		default:
			loadingAnimation = page.locator(
				'.card.card-root .loading-animation'
			);
	}

	await page
		.getByRole('menuitem', {
			name: timeFilterPeriod,
		})
		.click();

	await loadingAnimation.waitFor({state: 'visible'});

	await loadingAnimation.waitFor({state: 'hidden'});
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

// const testAcquisitionCard = async (
// 	acquisitionChannel,
// 	apiHelpers,
// 	page,
// 	queryParams
// ) => {
// 	const {channel, project} = await createChannel(
// 		apiHelpers,
// 		'My Property - ' + getRandomString()
// 	);

// 	await syncAnalyticsCloud(apiHelpers, channel, page);

// 	// FEITO

// 	await page.goto(liferayConfig.environment.baseUrl + `/home?${queryParams}`);

// 	await page.waitForTimeout(3000);

// 	////

// 	await closeSessions(apiHelpers, page);

// 	// USAR funcao de vini

// 	await page.goto(
// 		`${faroConfig.environment.baseUrl}/workspace/${project.groupId}/${channel.id}/sites`
// 	);

// 	///

// 	await page.waitForTimeout(3000);

// 	const acquisitionsCard = page.locator('.acquisitions-card-root');

// 	await acquisitionsCard.getByRole('button', {name: 'Last 30 days'}).click();

// 	await page
// 		.getByRole('menuitem', {
// 			name: 'Last 24 hours',
// 		})
// 		.click();

// 	const loadingAnimation = acquisitionsCard.locator('.loading-animation');

// 	await loadingAnimation.waitFor();

// 	await loadingAnimation.waitFor({state: 'hidden'});

// 	const acquisitionChannelElement = page.getByText(acquisitionChannel);

// 	await expect(acquisitionChannelElement).toBeVisible({
// 		timeout: 5 * 1000,
// 	});

// 	const acquisitionChannelCount = await page.evaluate(
// 		(acquisitionChannel) => {
// 			const acquisitionChannelRow = Array.from(
// 				document.querySelectorAll('.acquisitions-card-root tbody tr')
// 			).find(
// 				(element) =>
// 					element.querySelector('.table-title').textContent ===
// 					acquisitionChannel
// 			);

// 			return acquisitionChannelRow.querySelector('.count').textContent;
// 		},
// 		acquisitionChannel
// 	);

// 	expect(acquisitionChannelCount).toBe('1');

// 	await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
// 		`[${channel.id}]`,
// 		project.groupId
// 	);

// 	await page.goto(liferayConfig.environment.baseUrl);

// };

test('check if acquisition card displays PAID SEARCH channel after receiving an event', async ({
	apiHelpers,
	page,
}) => {
	const {channel, project} = await createChannel(
		apiHelpers,
		'My Property - ' + getRandomString()
	);

	await syncAnalyticsCloud(apiHelpers, channel, page);

	await sendEventByURL(page, 'utm_medium=paidsearch');

	await closeSessions(apiHelpers, page);

	await page.goto(
		`${faroConfig.environment.baseUrl}/workspace/${project.groupId}/${channel.id}/sites`
	);

	await page.waitForTimeout(3000);

	await changeTimeFilterInSitesOverviewCard(
		'Acquisitions',
		page,
		'Last 24 hours'
	);

	await checkAcquisitionChannelCount('paid search', '1', page);

	await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
		`[${channel.id}]`,
		project.groupId
	);
});

test('check if acquisition card displays DIRECT channel after receiving an event', async ({
	apiHelpers,
	page,
}) => {
	const {channel, project} = await createChannel(
		apiHelpers,
		'My Property - ' + getRandomString()
	);

	await syncAnalyticsCloud(apiHelpers, channel, page);

	await sendEventByURL(page, '');

	await closeSessions(apiHelpers, page);

	await page.goto(
		`${faroConfig.environment.baseUrl}/workspace/${project.groupId}/${channel.id}/sites`
	);

	await page.waitForTimeout(3000);

	await changeTimeFilterInSitesOverviewCard(
		'Acquisitions',
		page,
		'Last 24 hours'
	);

	await checkAcquisitionChannelCount('direct', '1', page);

	await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
		`[${channel.id}]`,
		project.groupId
	);
});

test('check if acquisition card displays SOCIAL channel after receiving an event', async ({
	apiHelpers,
	page,
}) => {
	const {channel, project} = await createChannel(
		apiHelpers,
		'My Property - ' + getRandomString()
	);

	await syncAnalyticsCloud(apiHelpers, channel, page);

	await sendEventByURL(page, 'utm_medium=social');

	await closeSessions(apiHelpers, page);

	await page.goto(
		`${faroConfig.environment.baseUrl}/workspace/${project.groupId}/${channel.id}/sites`
	);

	await page.waitForTimeout(3000);

	await changeTimeFilterInSitesOverviewCard(
		'Acquisitions',
		page,
		'Last 24 hours'
	);

	await checkAcquisitionChannelCount('social', '1', page);

	await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
		`[${channel.id}]`,
		project.groupId
	);
});

test('check if acquisition card displays EMAIL channel after receiving an event', async ({
	apiHelpers,
	page,
}) => {
	const {channel, project} = await createChannel(
		apiHelpers,
		'My Property - ' + getRandomString()
	);

	await syncAnalyticsCloud(apiHelpers, channel, page);

	await sendEventByURL(page, 'utm_medium=email');

	await closeSessions(apiHelpers, page);

	await page.goto(
		`${faroConfig.environment.baseUrl}/workspace/${project.groupId}/${channel.id}/sites`
	);

	await page.waitForTimeout(3000);

	await changeTimeFilterInSitesOverviewCard(
		'Acquisitions',
		page,
		'Last 24 hours'
	);

	await checkAcquisitionChannelCount('email', '1', page);

	await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
		`[${channel.id}]`,
		project.groupId
	);
});

test('check if acquisition card displays AFFILIATES channel after receiving an event', async ({
	apiHelpers,
	page,
}) => {
	const {channel, project} = await createChannel(
		apiHelpers,
		'My Property - ' + getRandomString()
	);

	await syncAnalyticsCloud(apiHelpers, channel, page);

	await sendEventByURL(page, 'utm_medium=affiliate');

	await closeSessions(apiHelpers, page);

	await page.goto(
		`${faroConfig.environment.baseUrl}/workspace/${project.groupId}/${channel.id}/sites`
	);

	await page.waitForTimeout(3000);

	await changeTimeFilterInSitesOverviewCard(
		'Acquisitions',
		page,
		'Last 24 hours'
	);

	await checkAcquisitionChannelCount('affiliates', '1', page);

	await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
		`[${channel.id}]`,
		project.groupId
	);
});

test('check if acquisition card displays ORGANIC channel after receiving an event', async ({
	apiHelpers,
	page,
}) => {
	const {channel, project} = await createChannel(
		apiHelpers,
		'My Property - ' + getRandomString()
	);

	await syncAnalyticsCloud(apiHelpers, channel, page);

	await sendEventByURL(page, 'utm_medium=organic');

	await closeSessions(apiHelpers, page);

	await page.goto(
		`${faroConfig.environment.baseUrl}/workspace/${project.groupId}/${channel.id}/sites`
	);

	await page.waitForTimeout(3000);

	await changeTimeFilterInSitesOverviewCard(
		'Acquisitions',
		page,
		'Last 24 hours'
	);

	await checkAcquisitionChannelCount('organic', '1', page);

	await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
		`[${channel.id}]`,
		project.groupId
	);
});

test('check if acquisition card displays DISPLAY channel after receiving an event', async ({
	apiHelpers,
	page,
}) => {
	const {channel, project} = await createChannel(
		apiHelpers,
		'My Property - ' + getRandomString()
	);

	await syncAnalyticsCloud(apiHelpers, channel, page);

	await sendEventByURL(page, 'utm_medium=display');

	await closeSessions(apiHelpers, page);

	await page.goto(
		`${faroConfig.environment.baseUrl}/workspace/${project.groupId}/${channel.id}/sites`
	);

	await page.waitForTimeout(3000);

	await changeTimeFilterInSitesOverviewCard(
		'Acquisitions',
		page,
		'Last 24 hours'
	);

	await checkAcquisitionChannelCount('display', '1', page);

	await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
		`[${channel.id}]`,
		project.groupId
	);
});

test('check if acquisition card displays REFERRAL channel after receiving an event', async ({
	apiHelpers,
	page,
}) => {
	const {channel, project} = await createChannel(
		apiHelpers,
		'My Property - ' + getRandomString()
	);

	await syncAnalyticsCloud(apiHelpers, channel, page);

	await sendEventByURL(page, 'utm_medium=referral');

	await closeSessions(apiHelpers, page);

	await page.goto(
		`${faroConfig.environment.baseUrl}/workspace/${project.groupId}/${channel.id}/sites`
	);

	await page.waitForTimeout(3000);

	await changeTimeFilterInSitesOverviewCard(
		'Acquisitions',
		page,
		'Last 24 hours'
	);

	await checkAcquisitionChannelCount('referral', '1', page);

	await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
		`[${channel.id}]`,
		project.groupId
	);
});

test('check if acquisition card displays OTHER channel after receiving an event', async ({
	apiHelpers,
	page,
}) => {
	const {channel, project} = await createChannel(
		apiHelpers,
		'My Property - ' + getRandomString()
	);

	await syncAnalyticsCloud(apiHelpers, channel, page);

	await sendEventByURL(page, 'utm_medium=other');

	await closeSessions(apiHelpers, page);

	await page.goto(
		`${faroConfig.environment.baseUrl}/workspace/${project.groupId}/${channel.id}/sites`
	);

	await page.waitForTimeout(3000);

	await changeTimeFilterInSitesOverviewCard(
		'Acquisitions',
		page,
		'Last 24 hours'
	);

	await checkAcquisitionChannelCount('other', '1', page);

	await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
		`[${channel.id}]`,
		project.groupId
	);
});
