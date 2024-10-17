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
	createIndividuals,
	generateIndividual,
} from '../osb-faro-web/utils/individuals';
import {Individuals, MetricType, RangeSelectors} from './types';
import {formatDate} from './utils/date';
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
let individuals;

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

	const chartElement = page.getByTestId('visitors-behavior-chart-data');

	// eslint-disable-next-line @liferay/no-get-data-attribute
	const chartData = await chartElement.getAttribute('data-qa-chart-data');

	// eslint-disable-next-line @liferay/no-get-data-attribute
	const tooltipFormattedDate = await chartElement.getAttribute(
		'data-qa-tooltip-formatted-date'
	);

	chartLegends.forEach((chartLegend) => {
		expect(page.getByText(chartLegend)).toBeVisible();
	});

	expect(chartData).toBe(expectedResult);

	expect(JSON.parse(tooltipFormattedDate)).toEqual(formatDate(rangeSelector));
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

test('User is able to see data plotted on Visitors Behavior Chart by all, anonymous and known individuals', async ({
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

	await test.step('Expect matching data on visitors behavior chart for ALL INDIVIDUALS', async () => {
		await expectMatchingChartData({
			chartLegends: ['Total Views: 60', 'Published Version: 1'],
			expectedResult:
				'[2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]',
			individual: Individuals.AllIndividuals,
			page,
			rangeSelector: RangeSelectors.Last30Days,
		});
	});

	await test.step('Expect matching data on visitors behavior chart for KNOWN INDIVIDUALS', async () => {
		await expectMatchingChartData({
			chartLegends: ['Total Views: 30', 'Published Version: 1'],
			expectedResult:
				'[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1]',
			individual: Individuals.KnownIndividuals,
			page,
			rangeSelector: RangeSelectors.Last30Days,
		});
	});

	await test.step('Expect matching data on visitors behavior chart for ANONYMOUS INDIVIDUALS', async () => {
		await expectMatchingChartData({
			chartLegends: ['Total Views: 30', 'Published Version: 1'],
			expectedResult:
				'[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1]',
			individual: Individuals.AnonymousIndividuals,
			page,
			rangeSelector: RangeSelectors.Last30Days,
		});
	});

	await test.step('Expect matching data on visitors behavior chart for ALL INDIVIDUALS and selected COMMENTS metric', async () => {
		await expectMatchingChartData({
			chartLegends: ['Total Comments: 120', 'Published Version: 1'],
			expectedResult:
				'[4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4]',
			individual: Individuals.AllIndividuals,
			metricType: MetricType.Comments,
			page,
			rangeSelector: RangeSelectors.Last30Days,
		});
	});
});

test('User is able to see data plotted on Visitors Behavior Chart for the last 7 days', async ({
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

	await test.step('Expect matching data on visitors behavior chart for the last 7 days', async () => {
		await expectMatchingChartData({
			chartLegends: ['Total Views: 14', 'Published Version: 1'],
			expectedResult: '[2,2,2,2,2,2,2]',
			individual: Individuals.AllIndividuals,
			page,
			rangeSelector: RangeSelectors.Last7Days,
		});
	});
});

test('User is able to see data plotted on Visitors Behavior Chart for the last 28 days', async ({
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

	await test.step('Expect matching data on visitors behavior chart for the last 28 days', async () => {
		await expectMatchingChartData({
			chartLegends: ['Total Views: 56', 'Published Version: 1'],
			expectedResult:
				'[2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]',
			individual: Individuals.AllIndividuals,
			page,
			rangeSelector: RangeSelectors.Last28Days,
		});
	});
});

test('User is able to see data plotted on Visitors Behavior Chart for the last 30 days', async ({
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

	await test.step('Expect matching data on visitors behavior chart for the last 30 days', async () => {
		await expectMatchingChartData({
			chartLegends: ['Total Views: 60', 'Published Version: 1'],
			expectedResult:
				'[2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]',
			individual: Individuals.AllIndividuals,
			page,
			rangeSelector: RangeSelectors.Last30Days,
		});
	});
});

test('User is able to see data plotted on Visitors Behavior Chart for the last 90 days', async ({
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

	await test.step('Expect matching data on visitors behavior chart for the last 90 days', async () => {
		await expectMatchingChartData({
			chartLegends: ['Total Views: 180', 'Published Version: 1'],
			expectedResult:
				'[2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]',
			individual: Individuals.AllIndividuals,
			page,
			rangeSelector: RangeSelectors.Last90Days,
		});
	});
});
