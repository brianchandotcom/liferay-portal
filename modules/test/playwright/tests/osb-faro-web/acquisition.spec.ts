/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginAnalyticsCloudTest} from '../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../fixtures/loginTest';
import {liferayConfig} from '../../liferay.config';
import getRandomString from '../../utils/getRandomString';
import {syncAnalyticsCloud} from '../analytics-settings-web/utils/analytics-settings';
import {ACPage, navigateToACPageViaURL} from './utils/navigation';
import {createSitePage, navigateToDXPandDeleteSite} from './utils/portal';
import {CardSelectors} from './utils/selectors';
import {closeSessions} from './utils/sessions';
import {changeTimeFilter} from './utils/time-filter';

export const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	loginAnalyticsCloudTest(),
	loginTest()
);

async function navigateToDXPByChannelViaURL({
	page,
	pageName,
	queryParams,
	siteName,
}: {
	page: Page;
	pageName: string;
	queryParams: string;
	siteName: string;
}) {
	await page.goto(
		liferayConfig.environment.baseUrl +
			`/web/${siteName}/${pageName}?${queryParams}`
	);

	// This timeout is required because the backend needs this time to process the event properly.

	await page.waitForTimeout(12000);
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

test(
	'Check if acquisition card displays PAID SEARCH channel after receiving an event',
	{
		tag: '@Legacy',
	},
	async ({apiHelpers, page}) => {
		const channelName = 'My Property - ' + getRandomString();
		const siteName = getRandomString();
		const pageTitle = 'MyPage-' + getRandomString();

		const site = await apiHelpers.headlessSite.createSite({
			name: siteName,
		});

		await createSitePage({
			apiHelpers,
			pageTitle,
			siteName,
		});

		const {channel, project} = await syncAnalyticsCloud({
			apiHelpers,
			channelName,
			page,
			siteName,
		});

		await test.step('send event to initialize channel followed by closing the session', async () => {
			await navigateToDXPByChannelViaURL({
				page,
				pageName: pageTitle,
				queryParams: 'utm_medium=paidsearch',
				siteName,
			});

			await closeSessions(apiHelpers, page);
		});

		await test.step('go to AC workspace', async () => {
			await navigateToACPageViaURL({
				acPage: ACPage.sitePage,
				channelID: channel.id,
				page,
				projectID: project.groupId,
			});
		});

		await test.step('change time filter in Acquisition card to Last 24 Hours and check if channel has count as 1', async () => {
			await changeTimeFilter({
				cardSelector: CardSelectors.Acquisition,
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

		await test.step('delete site on DXP side', async () => {
			await navigateToDXPandDeleteSite({apiHelpers, page, site});
		});
	}
);

test(
	'Check if acquisition card displays DIRECT channel after receiving an event',
	{
		tag: '@Legacy',
	},
	async ({apiHelpers, page}) => {
		const channelName = 'My Property - ' + getRandomString();
		const siteName = getRandomString();
		const pageTitle = 'MyPage-' + getRandomString();

		const site = await apiHelpers.headlessSite.createSite({
			name: siteName,
		});

		await createSitePage({
			apiHelpers,
			pageTitle,
			siteName,
		});

		const {channel, project} = await syncAnalyticsCloud({
			apiHelpers,
			channelName,
			page,
			siteName,
		});

		await test.step('send event to initialize channel followed by closing the session', async () => {
			await navigateToDXPByChannelViaURL({
				page,
				pageName: pageTitle,
				queryParams: '',
				siteName,
			});

			await closeSessions(apiHelpers, page);
		});

		await test.step('go to AC workspace', async () => {
			await navigateToACPageViaURL({
				acPage: ACPage.sitePage,
				channelID: channel.id,
				page,
				projectID: project.groupId,
			});
		});

		await test.step('change time filter in Acquisition card to Last 24 Hours and check if channel has count as 1', async () => {
			await changeTimeFilter({
				cardSelector: CardSelectors.Acquisition,
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

		await test.step('delete site on DXP side', async () => {
			await navigateToDXPandDeleteSite({apiHelpers, page, site});
		});
	}
);

test(
	'Check if acquisition card displays SOCIAL channel after receiving an event',
	{
		tag: '@Legacy',
	},
	async ({apiHelpers, page}) => {
		const channelName = 'My Property - ' + getRandomString();
		const siteName = getRandomString();
		const pageTitle = 'MyPage-' + getRandomString();

		const site = await apiHelpers.headlessSite.createSite({
			name: siteName,
		});

		await createSitePage({
			apiHelpers,
			pageTitle,
			siteName,
		});

		const {channel, project} = await syncAnalyticsCloud({
			apiHelpers,
			channelName,
			page,
			siteName,
		});

		await test.step('send event to initialize channel followed by closing the session', async () => {
			await navigateToDXPByChannelViaURL({
				page,
				pageName: pageTitle,
				queryParams: 'utm_medium=social',
				siteName,
			});

			await closeSessions(apiHelpers, page);
		});

		await test.step('go to AC workspace', async () => {
			await navigateToACPageViaURL({
				acPage: ACPage.sitePage,
				channelID: channel.id,
				page,
				projectID: project.groupId,
			});
		});

		await test.step('change time filter in Acquisition card to Last 24 Hours and check if channel has count as 1', async () => {
			await changeTimeFilter({
				cardSelector: CardSelectors.Acquisition,
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

		await test.step('delete site on DXP side', async () => {
			await navigateToDXPandDeleteSite({apiHelpers, page, site});
		});
	}
);

test(
	'Check if acquisition card displays EMAIL channel after receiving an event',
	{
		tag: '@Legacy',
	},
	async ({apiHelpers, page}) => {
		const channelName = 'My Property - ' + getRandomString();
		const siteName = getRandomString();
		const pageTitle = 'MyPage-' + getRandomString();

		const site = await apiHelpers.headlessSite.createSite({
			name: siteName,
		});

		await createSitePage({
			apiHelpers,
			pageTitle,
			siteName,
		});

		const {channel, project} = await syncAnalyticsCloud({
			apiHelpers,
			channelName,
			page,
			siteName,
		});

		await test.step('send event to initialize channel followed by closing the session', async () => {
			await navigateToDXPByChannelViaURL({
				page,
				pageName: pageTitle,
				queryParams: 'utm_medium=email',
				siteName,
			});

			await closeSessions(apiHelpers, page);
		});

		await test.step('go to AC workspace', async () => {
			await navigateToACPageViaURL({
				acPage: ACPage.sitePage,
				channelID: channel.id,
				page,
				projectID: project.groupId,
			});
		});

		await test.step('change time filter in Acquisition card to Last 24 Hours and check if channel has count as 1', async () => {
			await changeTimeFilter({
				cardSelector: CardSelectors.Acquisition,
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

		await test.step('delete site on DXP side', async () => {
			await navigateToDXPandDeleteSite({apiHelpers, page, site});
		});
	}
);

test(
	'Check if acquisition card displays AFFILIATES channel after receiving an event',
	{
		tag: '@Legacy',
	},
	async ({apiHelpers, page}) => {
		const channelName = 'My Property - ' + getRandomString();
		const siteName = getRandomString();
		const pageTitle = 'MyPage-' + getRandomString();

		const site = await apiHelpers.headlessSite.createSite({
			name: siteName,
		});

		await createSitePage({
			apiHelpers,
			pageTitle,
			siteName,
		});

		const {channel, project} = await syncAnalyticsCloud({
			apiHelpers,
			channelName,
			page,
			siteName,
		});

		await test.step('send event to initialize channel followed by closing the session', async () => {
			await navigateToDXPByChannelViaURL({
				page,
				pageName: pageTitle,
				queryParams: 'utm_medium=affiliate',
				siteName,
			});

			await closeSessions(apiHelpers, page);
		});

		await test.step('go to AC workspace', async () => {
			await navigateToACPageViaURL({
				acPage: ACPage.sitePage,
				channelID: channel.id,
				page,
				projectID: project.groupId,
			});
		});

		await test.step('change time filter in Acquisition card to Last 24 Hours and check if channel has count as 1', async () => {
			await changeTimeFilter({
				cardSelector: CardSelectors.Acquisition,
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

		await test.step('delete site on DXP side', async () => {
			await navigateToDXPandDeleteSite({apiHelpers, page, site});
		});
	}
);

test(
	'Check if acquisition card displays ORGANIC channel after receiving an event',
	{
		tag: '@Legacy',
	},
	async ({apiHelpers, page}) => {
		const channelName = 'My Property - ' + getRandomString();
		const siteName = getRandomString();
		const pageTitle = 'MyPage-' + getRandomString();

		const site = await apiHelpers.headlessSite.createSite({
			name: siteName,
		});

		await createSitePage({
			apiHelpers,
			pageTitle,
			siteName,
		});

		const {channel, project} = await syncAnalyticsCloud({
			apiHelpers,
			channelName,
			page,
			siteName,
		});

		await test.step('send event to initialize channel followed by closing the session', async () => {
			await navigateToDXPByChannelViaURL({
				page,
				pageName: pageTitle,
				queryParams: 'utm_medium=organic',
				siteName,
			});

			await closeSessions(apiHelpers, page);
		});

		await test.step('go to AC workspace', async () => {
			await navigateToACPageViaURL({
				acPage: ACPage.sitePage,
				channelID: channel.id,
				page,
				projectID: project.groupId,
			});
		});

		await test.step('change time filter in Acquisition card to Last 24 Hours and check if channel has count as 1', async () => {
			await changeTimeFilter({
				cardSelector: CardSelectors.Acquisition,
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

		await test.step('delete site on DXP side', async () => {
			await navigateToDXPandDeleteSite({apiHelpers, page, site});
		});
	}
);

test(
	'Check if acquisition card displays DISPLAY channel after receiving an event',
	{
		tag: '@Legacy',
	},
	async ({apiHelpers, page}) => {
		const channelName = 'My Property - ' + getRandomString();
		const siteName = getRandomString();
		const pageTitle = 'MyPage-' + getRandomString();

		const site = await apiHelpers.headlessSite.createSite({
			name: siteName,
		});

		await createSitePage({
			apiHelpers,
			pageTitle,
			siteName,
		});

		const {channel, project} = await syncAnalyticsCloud({
			apiHelpers,
			channelName,
			page,
			siteName,
		});

		await test.step('send event to initialize channel followed by closing the session', async () => {
			await navigateToDXPByChannelViaURL({
				page,
				pageName: pageTitle,
				queryParams: 'utm_medium=display',
				siteName,
			});

			await closeSessions(apiHelpers, page);
		});

		await test.step('go to AC workspace', async () => {
			await navigateToACPageViaURL({
				acPage: ACPage.sitePage,
				channelID: channel.id,
				page,
				projectID: project.groupId,
			});
		});

		await test.step('change time filter in Acquisition card to Last 24 Hours and check if channel has count as 1', async () => {
			await changeTimeFilter({
				cardSelector: CardSelectors.Acquisition,
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

		await test.step('delete site on DXP side', async () => {
			await navigateToDXPandDeleteSite({apiHelpers, page, site});
		});
	}
);

test(
	'Check if acquisition card displays REFERRAL channel after receiving an event',
	{
		tag: '@Legacy',
	},

	async ({apiHelpers, page}) => {
		const channelName = 'My Property - ' + getRandomString();
		const siteName = getRandomString();
		const pageTitle = 'MyPage-' + getRandomString();

		const site = await apiHelpers.headlessSite.createSite({
			name: siteName,
		});

		await createSitePage({
			apiHelpers,
			pageTitle,
			siteName,
		});

		const {channel, project} = await syncAnalyticsCloud({
			apiHelpers,
			channelName,
			page,
			siteName,
		});

		await test.step('send event to initialize channel followed by closing the session', async () => {
			await navigateToDXPByChannelViaURL({
				page,
				pageName: pageTitle,
				queryParams: 'utm_medium=referral',
				siteName,
			});

			await closeSessions(apiHelpers, page);
		});

		await test.step('go to AC workspace', async () => {
			await navigateToACPageViaURL({
				acPage: ACPage.sitePage,
				channelID: channel.id,
				page,
				projectID: project.groupId,
			});
		});

		await test.step('change time filter in Acquisition card to Last 24 Hours and check if channel has count as 1', async () => {
			await changeTimeFilter({
				cardSelector: CardSelectors.Acquisition,
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

		await test.step('delete site on DXP side', async () => {
			await navigateToDXPandDeleteSite({apiHelpers, page, site});
		});
	}
);

test(
	'Check if acquisition card displays OTHER channel after receiving an event',
	{
		tag: '@Legacy',
	},

	async ({apiHelpers, page}) => {
		const channelName = 'My Property - ' + getRandomString();
		const siteName = getRandomString();
		const pageTitle = 'MyPage-' + getRandomString();

		const site = await apiHelpers.headlessSite.createSite({
			name: siteName,
		});

		await createSitePage({
			apiHelpers,
			pageTitle,
			siteName,
		});

		const {channel, project} = await syncAnalyticsCloud({
			apiHelpers,
			channelName,
			page,
			siteName,
		});

		await test.step('send event to initialize channel followed by closing the session', async () => {
			await navigateToDXPByChannelViaURL({
				page,
				pageName: pageTitle,
				queryParams: 'utm_medium=other',
				siteName,
			});

			await closeSessions(apiHelpers, page);
		});

		await test.step('go to AC workspace', async () => {
			await navigateToACPageViaURL({
				acPage: ACPage.sitePage,
				channelID: channel.id,
				page,
				projectID: project.groupId,
			});
		});

		await test.step('change time filter in Acquisition card to Last 24 Hours and check if channel has count as 1', async () => {
			await changeTimeFilter({
				cardSelector: CardSelectors.Acquisition,
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

		await test.step('delete site on DXP side', async () => {
			await navigateToDXPandDeleteSite({apiHelpers, page, site});
		});
	}
);
