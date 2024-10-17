/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginAnalyticsCloudTest} from '../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';
import {syncAnalyticsCloud} from '../analytics-settings-web/utils/analytics-settings';
import {blogsPagesTest} from '../blogs-web/fixtures/blogsPagesTest';
import {contentDashboardPagesTest} from '../content-dashboard-web/fixtures/contentDashboardPagesTest';
import {
	Individual,
	createIndividuals,
	generateIndividual,
} from '../osb-faro-web/utils/individuals';
import {Individuals, MetricType, RangeSelectors} from './types';
import {createBlogsEventsForEveryDayByRangeSelector} from './utils/events';
import {changeGlobalFilters} from './utils/filters';

const test = mergeTests(
	apiHelpersTest,
	blogsPagesTest,
	contentDashboardPagesTest,
	dataApiHelpersTest,
	isolatedSiteTest,
	featureFlagsTest({
		'LPD-28830': true,
	}),
	loginAnalyticsCloudTest(),
	loginTest()
);

const assetTitle = getRandomString();

let assetId;
let channel;
let individualIdentities;
let individuals: Individual[] | null = null;

async function expectMatchingChartData({
	expectedResult,
	individual,
	chartLegends,
	metricType = MetricType.Views,
	page,
	rangeSelector,
}: {
	chartLegends: string[];
	expectedResult: string;
	individual: Individuals;
	metricType?: MetricType;
	page: Page;
	rangeSelector: RangeSelectors;
}) {
	await changeGlobalFilters(page, {individual, metricType, rangeSelector});

	const chartElement = page.getByTestId('stacked-bar-chart-data');

	// eslint-disable-next-line @liferay/no-get-data-attribute
	const chartData = await chartElement.getAttribute('data-qa-chart-data');

	chartLegends.forEach((chartLegend) => {
		expect(page.getByText(chartLegend)).toBeVisible();
	});

	expect(chartData).toBe(expectedResult);
}

test.beforeEach(async ({apiHelpers, page, site}) => {
	const channelName = 'My Property - ' + getRandomString();

	const result = await syncAnalyticsCloud({
		apiHelpers,
		channelName,
		page,
		siteName: site.name,
	});

	channel = result.channel;

	await test.step('Create Individuals', async () => {
		individuals = [
			generateIndividual({
				name: 'userA',
			}),
		];

		await createIndividuals({
			apiHelpers,
			individuals,
		});

		individualIdentities = individuals.map(({id}) => ({
			createDate: new Date().toISOString(),
			id,
			individualId: id,
		}));

		const anonymousIndividual = {
			createDate: new Date().toISOString(),
			id: getRandomString(),
			individualId: null,
		};

		individualIdentities.push(anonymousIndividual);

		await apiHelpers.jsonWebServicesOSBAsah.createIdentities(
			individualIdentities
		);
	});

	await test.step('Create a Blog', async () => {
		const {id} = await apiHelpers.headlessDelivery.postBlog(site.id, {
			headline: assetTitle,
		});

		assetId = id;

		await page.waitForTimeout(1000);
	});
});

test('User is able to see data plotted on Technology Chart by all, anonymous and known individuals', async ({
	apiHelpers,
	contentDashboardPage,
	page,
	site,
}) => {
	await test.step('Generate Blog events for known / anonymous individuals', async () => {
		await createBlogsEventsForEveryDayByRangeSelector({
			apiHelpers,
			assetId,
			assetTitle,
			channel,
			individualIdentities,
			page,
			rangeSelector: RangeSelectors.Last30Days,
		});
	});

	await test.step('Go to Content Dashboard > Select Blog > Go to Performance Tab', async () => {
		await contentDashboardPage.goToCurrentTab({
			assetTitle,
			siteUrl: site.friendlyUrlPath,
			tabName: 'Performance',
		});
	});

	await test.step('Expect matching data on technology chart for ALL INDIVIDUALS', async () => {
		await expectMatchingChartData({
			chartLegends: ['Unknown: 60'],
			expectedResult: JSON.stringify({
				data: [{label: 'Unknown', percentage: 100, value: 60}],
				total: 60,
			}),
			individual: Individuals.AllIndividuals,
			page,
			rangeSelector: RangeSelectors.Last30Days,
		});
	});

	await test.step('Expect matching data on technology chart for KNOWN INDIVIDUALS', async () => {
		await expectMatchingChartData({
			chartLegends: ['Unknown: 30'],
			expectedResult: JSON.stringify({
				data: [{label: 'Unknown', percentage: 100, value: 30}],
				total: 30,
			}),
			individual: Individuals.KnownIndividuals,
			page,
			rangeSelector: RangeSelectors.Last30Days,
		});
	});

	await test.step('Expect matching data on technology chart for ANONYMOUS INDIVIDUALS', async () => {
		await expectMatchingChartData({
			chartLegends: ['Unknown: 30'],
			expectedResult: JSON.stringify({
				data: [{label: 'Unknown', percentage: 100, value: 30}],
				total: 30,
			}),
			individual: Individuals.AnonymousIndividuals,
			page,
			rangeSelector: RangeSelectors.Last30Days,
		});
	});

	await test.step('Expect matching data on technology chart for ALL INDIVIDUALS and selected COMMENTS metric', async () => {
		await expectMatchingChartData({
			chartLegends: ['Unknown: 120'],
			expectedResult: JSON.stringify({
				data: [{label: 'Unknown', percentage: 100, value: 120}],
				total: 120,
			}),
			individual: Individuals.AllIndividuals,
			metricType: MetricType.Comments,
			page,
			rangeSelector: RangeSelectors.Last30Days,
		});
	});
});

test('User is able to see data plotted on technology Chart for the last 7 days', async ({
	apiHelpers,
	contentDashboardPage,
	page,
	site,
}) => {
	await test.step('Generate Blog events for known / anonymous individuals to the last 7 days', async () => {
		await createBlogsEventsForEveryDayByRangeSelector({
			apiHelpers,
			assetId,
			assetTitle,
			channel,
			individualIdentities,
			page,
			rangeSelector: RangeSelectors.Last7Days,
		});
	});

	await test.step('Go to Content Dashboard > Select Blog > Go to Performance Tab', async () => {
		await contentDashboardPage.goToCurrentTab({
			assetTitle,
			siteUrl: site.friendlyUrlPath,
			tabName: 'Performance',
		});
	});

	await test.step('Expect matching data on technology chart for the last 7 days', async () => {
		await expectMatchingChartData({
			chartLegends: ['Unknown: 14'],
			expectedResult: JSON.stringify({
				data: [{label: 'Unknown', percentage: 100, value: 14}],
				total: 14,
			}),
			individual: Individuals.AllIndividuals,
			page,
			rangeSelector: RangeSelectors.Last7Days,
		});
	});
});

test('User is able to see data plotted on technology Chart for the last 28 days', async ({
	apiHelpers,
	contentDashboardPage,
	page,
	site,
}) => {
	await test.step('Generate Blog events for known / anonymous individuals to the last 28 days', async () => {
		await createBlogsEventsForEveryDayByRangeSelector({
			apiHelpers,
			assetId,
			assetTitle,
			channel,
			individualIdentities,
			page,
			rangeSelector: RangeSelectors.Last28Days,
		});
	});

	await test.step('Go to Content Dashboard > Select Blog > Go to Performance Tab', async () => {
		await contentDashboardPage.goToCurrentTab({
			assetTitle,
			siteUrl: site.friendlyUrlPath,
			tabName: 'Performance',
		});
	});

	await test.step('Expect matching data on technology chart for the last 28 days', async () => {
		await expectMatchingChartData({
			chartLegends: ['Unknown: 56'],
			expectedResult: JSON.stringify({
				data: [{label: 'Unknown', percentage: 100, value: 56}],
				total: 56,
			}),
			individual: Individuals.AllIndividuals,
			page,
			rangeSelector: RangeSelectors.Last28Days,
		});
	});
});

test('User is able to see data plotted on technology Chart for the last 30 days', async ({
	apiHelpers,
	contentDashboardPage,
	page,
	site,
}) => {
	await test.step('Generate Blog events for known / anonymous individuals to the last 30 days', async () => {
		await createBlogsEventsForEveryDayByRangeSelector({
			apiHelpers,
			assetId,
			assetTitle,
			channel,
			individualIdentities,
			page,
			rangeSelector: RangeSelectors.Last30Days,
		});
	});

	await test.step('Go to Content Dashboard > Select Blog > Go to Performance Tab', async () => {
		await contentDashboardPage.goToCurrentTab({
			assetTitle,
			siteUrl: site.friendlyUrlPath,
			tabName: 'Performance',
		});
	});

	await test.step('Expect matching data on technology chart for the last 30 days', async () => {
		await expectMatchingChartData({
			chartLegends: ['Unknown: 60'],
			expectedResult: JSON.stringify({
				data: [{label: 'Unknown', percentage: 100, value: 60}],
				total: 60,
			}),
			individual: Individuals.AllIndividuals,
			page,
			rangeSelector: RangeSelectors.Last30Days,
		});
	});
});

test('User is able to see data plotted on technology Chart for the last 90 days', async ({
	apiHelpers,
	contentDashboardPage,
	page,
	site,
}) => {
	await test.step('Generate Blog events for known / anonymous individuals to the last 90 days', async () => {
		await createBlogsEventsForEveryDayByRangeSelector({
			apiHelpers,
			assetId,
			assetTitle,
			channel,
			individualIdentities,
			page,
			rangeSelector: RangeSelectors.Last90Days,
		});
	});

	await test.step('Go to Content Dashboard > Select Blog > Go to Performance Tab', async () => {
		await contentDashboardPage.goToCurrentTab({
			assetTitle,
			siteUrl: site.friendlyUrlPath,
			tabName: 'Performance',
		});
	});

	await test.step('Expect matching data on technology chart for the last 90 days', async () => {
		await expectMatchingChartData({
			chartLegends: ['Unknown: 180'],
			expectedResult: JSON.stringify({
				data: [{label: 'Unknown', percentage: 100, value: 180}],
				total: 180,
			}),
			individual: Individuals.AllIndividuals,
			page,
			rangeSelector: RangeSelectors.Last90Days,
		});
	});
});
