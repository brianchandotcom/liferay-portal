/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
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
import {
	syncAnalyticsCloud,
} from '../analytics-settings-web/utils/analyticsSettings';
import {createChannel, switchChannel} from './utils/channel';
import {createIndividuals} from './utils/individuals';
import {
	navigateTo,
	navigateToACSitesPageViaURL,
	navigateToACWorkspace,
} from './utils/navigation';
import {changeTimeFilter} from './utils/time-filter';
import {expectNotToBeVisible, expectToBeVisible} from './utils/utils';
import {createSitePage, navigateToSitePage} from './utils/portal';

export const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	loginAnalyticsCloudTest(),
	loginTest()
);

const goToWithReferrer = async function ({
	page,
	referrer,
	url,
}: {
	page: Page;
	referrer: string;
	url: string;
}) {
	await page.goto(referrer);

	await page.evaluate((url) => {
		const aTag = document.createElement('a');

		aTag.href = url;

		aTag.click();
	}, url);
};

test('shows individuals who viewed a page less than 24 hours ago', async ({
	apiHelpers,
	page,
}) => {
	const channelName = 'My Property - ' + getRandomString();
	const {channel, project} = await createChannel({
		apiHelpers,
		channelName,
	});
	const date1 = new Date();
	const individualsPresentIn24Hours = ['user1 user1', 'user2 user2'];
	const individualPresentIn30Days = ['user3 user3'];

	await test.step('Create 3 Individuals and their respective Identity directly in the AC database', async () => {
		const individualNames = ['user1', 'user2', 'user3'];
		await createIndividuals({
			apiHelpers,
			names: individualNames,
		});

		await apiHelpers.jsonWebServicesOSBAsah.createIdentities([
			{
				createDate: date1.toISOString(),
				id: '1',
				individualId: 'user1@liferay.com',
			},
			{
				createDate: date1.toISOString(),
				id: '2',
				individualId: 'user2@liferay.com',
			},
			{
				createDate: date1.toISOString(),
				id: '3',
				individualId: 'user3@liferay.com',
			},
		]);
	});

	await test.step('Create events for two of the individuals to appear within the Last 24 hours period in AC', async () => {
		await apiHelpers.jsonWebServicesOSBAsah.createEvents([
			{
				applicationId: 'Page',
				canonicalUrl: 'https://www.liferay.com',
				channelId: channel.id,
				eventDate: date1.toISOString(),
				eventId: 'pageViewed',
				title: 'Liferay',
				userId: '1',
			},
			{
				applicationId: 'Page',
				canonicalUrl: 'https://www.liferay.com',
				channelId: channel.id,
				eventDate: date1.toISOString(),
				eventId: 'pageViewed',
				title: 'Liferay',
				userId: '2',
			},
		]);
	});

	await test.step('Create events for one of the individuals to appear in periods different than the Last 24 hours in AC', async () => {
		const date2 = new Date();
		date2.setDate(date2.getDate() - 5);

		await apiHelpers.jsonWebServicesOSBAsah.createPagesDaily([
			{
				canonicalUrl: 'https://www.liferay.com',
				channelId: channel.id,
				eventDate: date2.toISOString(),
				title: 'Liferay',
				userId: '3',
				views: 1,
			},
		]);
	});

	await test.step('Go to Analytics Cloud and Switch the property', async () => {
		await navigateToACSitesPageViaURL({
			channelID: channel.id,
			page,
			projectID: project.groupId,
		});
	});

	await test.step('Go to Pages Tab', async () => {
		await navigateTo({
			page,
			pageName: 'Pages',
		});
	});

	await test.step('Access one of the pages on the list > Go to Known Individuals Tab', async () => {
		await navigateTo({
			page,
			pageName: 'Liferay',
		});
		await navigateTo({
			page,
			pageName: 'Known Individuals',
		});
	});

	await test.step('Check that User3 User3 is appearing in the list', async () => {
		await expectToBeVisible({
			itemNames: individualPresentIn30Days,
			page,
		});
	});

	await test.step('Change the time filter to Last 24 hours', async () => {
		await changeTimeFilter({
			page,
			timeFilterPeriod: 'Last 24 hours',
		});
	});

	await test.step('Check that User1 User1 and User2 User2 are appearing in the list', async () => {
		await expectToBeVisible({
			itemNames: individualsPresentIn24Hours,
			page,
		});
	});

	await test.step('Check that User3 User3 is appearing in the list', async () => {
		await expectNotToBeVisible({
			itemNames: individualPresentIn30Days,
			page,
		});
	});

	await test.step('Delete the property that was used during automation execution', async () => {
		await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
			`[${channel.id}]`,
			project.groupId
		);
	});
});

test('shows outside pages in path analysis', async ({apiHelpers, page}) => {
	const channelName = 'My Property - ' + getRandomString();
	const pageTitle = 'My Page';
	const sitePage = await createSitePage({
		apiHelpers,
		pageTitle,
	});

	await test.step('Connect the DXP to AC', async () => {
		await syncAnalyticsCloud({
			apiHelpers,
			channelName,
			page,
		});
	});

	await test.step('Access the DXP Home Page using Google Page as a reference page', async () => {
		await goToWithReferrer({
			page,
			referrer: 'https://www.google.com',
			url: liferayConfig.environment.baseUrl,
		});

		await page.waitForTimeout(10000);
	});

	await test.step('Go to My Page', async () => {
		await page.getByText(pageTitle).first().click();
		await page.waitForTimeout(10000);
	});

	await test.step('Go to Analytics Cloud and Switch the property', async () => {
		await navigateToACWorkspace({page});
		await switchChannel({
			channelName,
			page,
		});
	});

	await test.step('Go to Pages Tab', async () => {
		await navigateTo({
			page,
			pageName: 'Pages',
		});
	});

	await test.step('Change the time filter to Last 24 hours', async () => {
		await changeTimeFilter({
			page,
			timeFilterPeriod: 'Last 24 hours',
		});
	});

	await test.step('Access one of the pages on the list > Go to Path Tab', async () => {
		await navigateTo({
			page,
			pageName: 'Home - Liferay DXP',
		});
		await navigateTo({
			page,
			pageName: 'Path',
		});
	});

	await test.step('Check that Google Page appears the referral pages and the number of views', async () => {
		await expect(page.getByText('https://www.goo...')).toBeVisible({
			timeout: 100 * 1000,
		});

		await expect(page.getByText('1', {exact: true}).first()).toBeVisible({
			timeout: 100 * 1000,
		});
	});

	await test.step('Check that Home Page appears with one view', async () => {
		await expect(page.getByText('1', {exact: true}).nth(1)).toBeVisible({
			timeout: 100 * 1000,
		});
	});

	await test.step('Check that My Page appears as exit pages and the number of views', async () => {
		await expect(page.getByText('My Page - Lifer...')).toBeVisible({
			timeout: 100 * 1000,
		});

		await expect(page.getByText('1', {exact: true}).nth(2)).toBeVisible({
			timeout: 100 * 1000,
		});
	});

	await test.step('Delete pages created in DXP during automation execution', async () => {
		await page.goto(liferayConfig.environment.baseUrl);

		await apiHelpers.jsonWebServicesLayout.deleteLayout(
			String(sitePage.id)
		);
	});
});

test('shows tracked pages in path analysis', async ({apiHelpers, page}) => {
	const channelName = 'My Property - ' + getRandomString();
	const pageTitle1 = 'My Page 1';
	const sitePage1 = await createSitePage({
		apiHelpers,
		pageTitle: pageTitle1,
	});
	const pageTitle2 = 'My Page 2';
	const sitePage2 = await createSitePage({
		apiHelpers,
		pageTitle: pageTitle2,
	});

	await test.step('Connect the DXP to AC', async () => {
		await syncAnalyticsCloud({
			apiHelpers,
			channelName,
			page,
		});
	});

	await test.step('Go to My Page 1', async () => {
		await navigateToSitePage({
			page,
			pageName: pageTitle1,
		});
		await page.waitForTimeout(10000);
	});

	await test.step('Go to My Page 2', async () => {
		await page.getByText(pageTitle2).first().click();
		await page.waitForTimeout(10000);
	});

	await test.step('Go to My Page 1', async () => {
		await page.getByText(pageTitle1).first().click();
		await page.waitForTimeout(10000);
	});

	await test.step('Go to Analytics Cloud and Switch the property', async () => {
		await navigateToACWorkspace({page});
		await switchChannel({
			channelName,
			page,
		});
	});

	await test.step('Go to Pages Tab', async () => {
		await navigateTo({
			page,
			pageName: 'Pages',
		});
	});

	await test.step('Change the time filter to Last 24 hours', async () => {
		await changeTimeFilter({
			page,
			timeFilterPeriod: 'Last 24 hours',
		});
	});

	await test.step('Access one of the pages on the list > Go to Path Tab', async () => {
		await navigateTo({
			page,
			pageName: 'My Page 1 - Liferay DXP',
		});
		await navigateTo({
			page,
			pageName: 'Path',
		});
	});

	await test.step('Check that My Page 2 and Direct Traffic appear as referral pages', async () => {
		await expect(
			page.getByText('My Page 2 - Lif...', {exact: true}).first()
		).toBeVisible({
			timeout: 100 * 1000,
		});

		await expect(page.getByText('Direct Traffic')).toBeVisible({
			timeout: 100 * 1000,
		});
	});

	await test.step('Check that My Page 2 and Drop Offs appear as exit pages', async () => {
		await expect(
			page.getByText('My Page 2 - Lif...', {exact: true}).nth(1)
		).toBeVisible({
			timeout: 100 * 1000,
		});

		await expect(page.getByText('Drop Offs')).toBeVisible({
			timeout: 100 * 1000,
		});
	});

	await test.step('Delete pages created in DXP during automation execution', async () => {
		await page.goto(liferayConfig.environment.baseUrl);

		await apiHelpers.jsonWebServicesLayout.deleteLayout(
			String(sitePage1.id)
		);
		await apiHelpers.jsonWebServicesLayout.deleteLayout(
			String(sitePage2.id)
		);
	});
});
