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
import {createChannel} from './utils/channel';
import {createIndividuals, generateIndividual} from './utils/individuals';
import {ACPage, navigateToACPageViaURL} from './utils/navigation';
import {CardSelectors} from './utils/selectors';
import {changeTimeFilter} from './utils/time-filter';

export const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	loginAnalyticsCloudTest(),
	loginTest()
);

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

const channelName = 'My Property ' + getRandomString();

let channel;
let project;

test.beforeEach(async ({apiHelpers}) => {
	const result = await createChannel({
		apiHelpers,
		channelName,
	});

	channel = result.channel;
	project = result.project;
});

test.afterEach(async ({apiHelpers, page}) => {
	await test.step('Delete channel and delete site on de DXP side', async () => {
		await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
			`[${channel.id}]`,
			project.groupId
		);

		await page.goto(liferayConfig.environment.baseUrl);
	});
});

test(
	'Check if acquisition card displays PAID SEARCH channel after receiving an event',
	{
		tag: '@Legacy',
	},
	async ({apiHelpers, page}) => {
		const pageTitle = 'MyPage-' + getRandomString();

		const individualName = 'user1';
		const individuals = [
			generateIndividual({
				name: individualName,
			}),
		];

		await test.step('Create an Individual directly in the AC database', async () => {
			await createIndividuals({
				apiHelpers,
				individuals,
			});
		});

		const date1 = new Date();

		await test.step('Create an event for the individual to appear within the Last 24 hours period in AC', async () => {
			const events = individuals.map((individual) => ({
				applicationId: 'Page',
				canonicalUrl: 'https://www.liferay.com?utm_medium=paidsearch',
				channelId: channel.id,
				eventDate: date1.toISOString(),
				eventId: 'pageViewed',
				title: pageTitle,
				userId: individual.id,
			}));

			await apiHelpers.jsonWebServicesOSBAsah.createEvents(events);
		});

		await test.step('Create Individual Session within the Last 24 hours period in AC', async () => {
			const sessions = individuals.map((individual) => ({
				acquisitionChannel: 'paid search',
				channelId: channel.id,
				id: individual.id,
				sessionEnd: date1.toISOString(),
				sessionStart: date1.toISOString(),
				userId: individual.id,
			}));

			await apiHelpers.jsonWebServicesOSBAsah.createSessions(sessions);
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
	}
);

test(
	'Check if acquisition card displays DIRECT channel after receiving an event',
	{
		tag: '@Legacy',
	},
	async ({apiHelpers, page}) => {
		const pageTitle = 'MyPage-' + getRandomString();

		const individualName = 'user1';
		const individuals = [
			generateIndividual({
				name: individualName,
			}),
		];

		await test.step('Create an Individual directly in the AC database', async () => {
			await createIndividuals({
				apiHelpers,
				individuals,
			});
		});

		const date1 = new Date();

		await test.step('Create an event for the individual to appear within the Last 24 hours period in AC', async () => {
			const events = individuals.map((individual) => ({
				applicationId: 'Page',
				canonicalUrl: 'https://www.liferay.com',
				channelId: channel.id,
				eventDate: date1.toISOString(),
				eventId: 'pageViewed',
				title: pageTitle,
				userId: individual.id,
			}));

			await apiHelpers.jsonWebServicesOSBAsah.createEvents(events);
		});

		await test.step('Create Individual Session within the Last 24 hours period in AC', async () => {
			const sessions = individuals.map((individual) => ({
				acquisitionChannel: 'direct',
				channelId: channel.id,
				id: individual.id,
				sessionEnd: date1.toISOString(),
				sessionStart: date1.toISOString(),
				userId: individual.id,
			}));

			await apiHelpers.jsonWebServicesOSBAsah.createSessions(sessions);
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
	}
);

test(
	'Check if acquisition card displays SOCIAL channel after receiving an event',
	{
		tag: '@Legacy',
	},
	async ({apiHelpers, page}) => {
		const pageTitle = 'MyPage-' + getRandomString();

		const individualName = 'user1';
		const individuals = [
			generateIndividual({
				name: individualName,
			}),
		];

		await test.step('Create an Individual directly in the AC database', async () => {
			await createIndividuals({
				apiHelpers,
				individuals,
			});
		});

		const date1 = new Date();

		await test.step('Create an event for the individual to appear within the Last 24 hours period in AC', async () => {
			const events = individuals.map((individual) => ({
				applicationId: 'Page',
				canonicalUrl: 'https://www.liferay.com?utm_medium=social',
				channelId: channel.id,
				eventDate: date1.toISOString(),
				eventId: 'pageViewed',
				title: pageTitle,
				userId: individual.id,
			}));

			await apiHelpers.jsonWebServicesOSBAsah.createEvents(events);
		});

		await test.step('Create Individual Session within the Last 24 hours period in AC', async () => {
			const sessions = individuals.map((individual) => ({
				acquisitionChannel: 'social',
				channelId: channel.id,
				id: individual.id,
				sessionEnd: date1.toISOString(),
				sessionStart: date1.toISOString(),
				userId: individual.id,
			}));

			await apiHelpers.jsonWebServicesOSBAsah.createSessions(sessions);
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
	}
);

test(
	'Check if acquisition card displays EMAIL channel after receiving an event',
	{
		tag: '@Legacy',
	},
	async ({apiHelpers, page}) => {
		const pageTitle = 'MyPage-' + getRandomString();

		const individualName = 'user1';
		const individuals = [
			generateIndividual({
				name: individualName,
			}),
		];

		await test.step('Create an Individual directly in the AC database', async () => {
			await createIndividuals({
				apiHelpers,
				individuals,
			});
		});

		const date1 = new Date();

		await test.step('Create an event for the individual to appear within the Last 24 hours period in AC', async () => {
			const events = individuals.map((individual) => ({
				applicationId: 'Page',
				canonicalUrl: 'https://www.liferay.com?utm_medium=email',
				channelId: channel.id,
				eventDate: date1.toISOString(),
				eventId: 'pageViewed',
				title: pageTitle,
				userId: individual.id,
			}));

			await apiHelpers.jsonWebServicesOSBAsah.createEvents(events);
		});

		await test.step('Create Individual Session within the Last 24 hours period in AC', async () => {
			const sessions = individuals.map((individual) => ({
				acquisitionChannel: 'email',
				channelId: channel.id,
				id: individual.id,
				sessionEnd: date1.toISOString(),
				sessionStart: date1.toISOString(),
				userId: individual.id,
			}));

			await apiHelpers.jsonWebServicesOSBAsah.createSessions(sessions);
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
	}
);

test(
	'Check if acquisition card displays AFFILIATES channel after receiving an event',
	{
		tag: '@Legacy',
	},
	async ({apiHelpers, page}) => {
		const pageTitle = 'MyPage-' + getRandomString();

		const individualName = 'user1';
		const individuals = [
			generateIndividual({
				name: individualName,
			}),
		];

		await test.step('Create an Individual directly in the AC database', async () => {
			await createIndividuals({
				apiHelpers,
				individuals,
			});
		});

		const date1 = new Date();

		await test.step('Create an event for the individual to appear within the Last 24 hours period in AC', async () => {
			const events = individuals.map((individual) => ({
				applicationId: 'Page',
				canonicalUrl: 'https://www.liferay.com?utm_medium=affiliates',
				channelId: channel.id,
				eventDate: date1.toISOString(),
				eventId: 'pageViewed',
				title: pageTitle,
				userId: individual.id,
			}));

			await apiHelpers.jsonWebServicesOSBAsah.createEvents(events);
		});

		await test.step('Create Individual Session within the Last 24 hours period in AC', async () => {
			const sessions = individuals.map((individual) => ({
				acquisitionChannel: 'affiliates',
				channelId: channel.id,
				id: individual.id,
				sessionEnd: date1.toISOString(),
				sessionStart: date1.toISOString(),
				userId: individual.id,
			}));

			await apiHelpers.jsonWebServicesOSBAsah.createSessions(sessions);
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
	}
);

test(
	'Check if acquisition card displays ORGANIC channel after receiving an event',
	{
		tag: '@Legacy',
	},
	async ({apiHelpers, page}) => {
		const pageTitle = 'MyPage-' + getRandomString();

		const individualName = 'user1';
		const individuals = [
			generateIndividual({
				name: individualName,
			}),
		];

		await test.step('Create an Individual directly in the AC database', async () => {
			await createIndividuals({
				apiHelpers,
				individuals,
			});
		});

		const date1 = new Date();

		await test.step('Create an event for the individual to appear within the Last 24 hours period in AC', async () => {
			const events = individuals.map((individual) => ({
				applicationId: 'Page',
				canonicalUrl: 'https://www.liferay.com?utm_medium=organic',
				channelId: channel.id,
				eventDate: date1.toISOString(),
				eventId: 'pageViewed',
				title: pageTitle,
				userId: individual.id,
			}));

			await apiHelpers.jsonWebServicesOSBAsah.createEvents(events);
		});

		await test.step('Create Individual Session within the Last 24 hours period in AC', async () => {
			const sessions = individuals.map((individual) => ({
				acquisitionChannel: 'organic',
				channelId: channel.id,
				id: individual.id,
				sessionEnd: date1.toISOString(),
				sessionStart: date1.toISOString(),
				userId: individual.id,
			}));

			await apiHelpers.jsonWebServicesOSBAsah.createSessions(sessions);
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
	}
);

test(
	'Check if acquisition card displays DISPLAY channel after receiving an event',
	{
		tag: '@Legacy',
	},
	async ({apiHelpers, page}) => {
		const pageTitle = 'MyPage-' + getRandomString();

		const individualName = 'user1';
		const individuals = [
			generateIndividual({
				name: individualName,
			}),
		];

		await test.step('Create an Individual directly in the AC database', async () => {
			await createIndividuals({
				apiHelpers,
				individuals,
			});
		});

		const date1 = new Date();

		await test.step('Create an event for the individual to appear within the Last 24 hours period in AC', async () => {
			const events = individuals.map((individual) => ({
				applicationId: 'Page',
				canonicalUrl: 'https://www.liferay.com?utm_medium=display',
				channelId: channel.id,
				eventDate: date1.toISOString(),
				eventId: 'pageViewed',
				title: pageTitle,
				userId: individual.id,
			}));

			await apiHelpers.jsonWebServicesOSBAsah.createEvents(events);
		});

		await test.step('Create Individual Session within the Last 24 hours period in AC', async () => {
			const sessions = individuals.map((individual) => ({
				acquisitionChannel: 'display',
				channelId: channel.id,
				id: individual.id,
				sessionEnd: date1.toISOString(),
				sessionStart: date1.toISOString(),
				userId: individual.id,
			}));

			await apiHelpers.jsonWebServicesOSBAsah.createSessions(sessions);
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
	}
);

test(
	'Check if acquisition card displays REFERRAL channel after receiving an event',
	{
		tag: '@Legacy',
	},

	async ({apiHelpers, page}) => {
		const pageTitle = 'MyPage-' + getRandomString();

		const individualName = 'user1';
		const individuals = [
			generateIndividual({
				name: individualName,
			}),
		];

		await test.step('Create an Individual directly in the AC database', async () => {
			await createIndividuals({
				apiHelpers,
				individuals,
			});
		});

		const date1 = new Date();

		await test.step('Create an event for the individual to appear within the Last 24 hours period in AC', async () => {
			const events = individuals.map((individual) => ({
				applicationId: 'Page',
				canonicalUrl: 'https://www.liferay.com?utm_medium=referral',
				channelId: channel.id,
				eventDate: date1.toISOString(),
				eventId: 'pageViewed',
				title: pageTitle,
				userId: individual.id,
			}));

			await apiHelpers.jsonWebServicesOSBAsah.createEvents(events);
		});

		await test.step('Create Individual Session within the Last 24 hours period in AC', async () => {
			const sessions = individuals.map((individual) => ({
				acquisitionChannel: 'referral',
				channelId: channel.id,
				id: individual.id,
				sessionEnd: date1.toISOString(),
				sessionStart: date1.toISOString(),
				userId: individual.id,
			}));

			await apiHelpers.jsonWebServicesOSBAsah.createSessions(sessions);
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
	}
);

test(
	'Check if acquisition card displays OTHER channel after receiving an event',
	{
		tag: '@Legacy',
	},

	async ({apiHelpers, page}) => {
		const pageTitle = 'MyPage-' + getRandomString();

		const individualName = 'user1';
		const individuals = [
			generateIndividual({
				name: individualName,
			}),
		];

		await test.step('Create an Individual directly in the AC database', async () => {
			await createIndividuals({
				apiHelpers,
				individuals,
			});
		});

		const date1 = new Date();

		await test.step('Create an event for the individual to appear within the Last 24 hours period in AC', async () => {
			const events = individuals.map((individual) => ({
				applicationId: 'Page',
				canonicalUrl: 'https://www.liferay.com?utm_medium=other',
				channelId: channel.id,
				eventDate: date1.toISOString(),
				eventId: 'pageViewed',
				title: pageTitle,
				userId: individual.id,
			}));

			await apiHelpers.jsonWebServicesOSBAsah.createEvents(events);
		});

		await test.step('Create Individual Session within the Last 24 hours period in AC', async () => {
			const sessions = individuals.map((individual) => ({
				acquisitionChannel: 'other',
				channelId: channel.id,
				id: individual.id,
				sessionEnd: date1.toISOString(),
				sessionStart: date1.toISOString(),
				userId: individual.id,
			}));

			await apiHelpers.jsonWebServicesOSBAsah.createSessions(sessions);
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
	}
);
