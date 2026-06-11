/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedChannelTest} from '../../../fixtures/isolatedChannelTest';
import {loginAnalyticsCloudTest} from '../../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import {faroConfig} from './faro.config';
import {createIndividuals, generateIndividual} from './utils/individuals';
import {ACPage, navigateToACPageViaURL} from './utils/navigation';
import {CardSelectors} from './utils/selectors';
import {changeTimeFilter} from './utils/time-filter';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedChannelTest,
	loginAnalyticsCloudTest(),
	loginTest()
);

test(
	'Sites Overview is the AC home page after signing in',
	{
		tag: '@LRAC-8261',
	},
	async ({page, project}) => {
		await page.goto(
			`${faroConfig.environment.baseUrl}/workspace/${project.groupId}`
		);

		await expect(
			page.getByRole('heading', {exact: true, name: 'Sites'})
		).toBeVisible();
	}
);

test(
	'Sites Overview dashboard renders all expected cards',
	{
		tag: '@LRAC-8260',
	},
	async ({analyticsChannel: channel, page, project}) => {
		await navigateToACPageViaURL({
			acPage: ACPage.sitePage,
			channelID: channel.id,
			page,
			projectID: project.groupId,
		});

		for (const cardTitle of [
			'Top Pages',
			'Acquisitions',
			'Visitors by Day and Time',
			'Search Terms',
			'Interests',
			'Sessions by Location',
			'Session Technology',
			'Cohort Analysis',
		]) {
			await expect(
				page.getByText(cardTitle, {exact: true}).first()
			).toBeVisible();
		}
	}
);

test(
	'Sites Overview shows zero Unique Visitors when no data is available',
	{
		tag: '@LRAC-8266',
	},
	async ({analyticsChannel: channel, page, project}) => {
		await navigateToACPageViaURL({
			acPage: ACPage.sitePage,
			channelID: channel.id,
			page,
			projectID: project.groupId,
		});

		await expect(page.getByText('Unique Visitors').first()).toBeVisible();

		const uniqueVisitorsValue = page.locator(
			'xpath=//span[contains(text(),"Unique Visitors")]/ancestor::button//*[contains(@class,"metric-value")]'
		);

		await expect(uniqueVisitorsValue.first()).toHaveText('0');
	}
);

test(
	'Site Metrics card can switch between Sessions/Visitor and Unique Visitors tabs',
	{
		tag: ['@LRAC-8331', '@LRAC-8332'],
	},
	async ({analyticsChannel: channel, apiHelpers, page, project}) => {
		const individuals = [
			generateIndividual({name: 'user' + getRandomString()}),
		];

		await createIndividuals({apiHelpers, individuals});

		// Seed a pageViewed event so the Site Metrics card has data to render

		await apiHelpers.jsonWebServicesOSBAsah.createEvents(
			individuals.map((individual) => ({
				applicationId: 'Page',
				canonicalUrl: 'https://www.liferay.com',
				channelId: channel.id,
				eventDate: new Date().toISOString(),
				eventId: 'pageViewed',
				title: 'Site Metrics Page',
				userId: individual.id,
			}))
		);

		await navigateToACPageViaURL({
			acPage: ACPage.sitePage,
			channelID: channel.id,
			page,
			projectID: project.groupId,
		});

		const cardTabs = page.locator('.card-tabs-root').first();

		for (const tabName of ['Sessions/Visitor', 'Unique Visitors']) {
			await cardTabs.getByText(tabName, {exact: true}).click();

			await expect(
				page
					.locator('li.card-tab.active')
					.getByText(tabName, {exact: true})
			).toBeVisible();
		}
	}
);

test(
	'Searched keywords appear in the Site overview Search Terms report',
	{
		tag: '@LRAC-8298',
	},
	async ({analyticsChannel: channel, apiHelpers, page, project}) => {
		const keywords = ['liferay', 'ac'];

		const date = new Date();

		// Seed search events so the searched keywords surface in the Search Terms report

		await apiHelpers.jsonWebServicesOSBAsah.createEvents(
			keywords.map((keyword) => ({
				applicationId: 'Page',
				canonicalUrl: `https://www.liferay.com/search?q=${keyword}`,
				channelId: channel.id,
				eventDate: date.toISOString(),
				eventId: 'pageViewed',
				title: keyword,
				url: `https://www.liferay.com/search?q=${keyword}`,
				userId: 'searchUser',
			}))
		);

		await navigateToACPageViaURL({
			acPage: ACPage.sitePage,
			channelID: channel.id,
			page,
			projectID: project.groupId,
		});

		await changeTimeFilter({
			cardSelector: CardSelectors.SearchTerms,
			page,
			timeFilterPeriod: 'Last 24 hours',
		});

		for (const keyword of keywords) {
			await expect(
				page
					.locator(CardSelectors.SearchTerms)
					.getByText(keyword)
					.first()
			).toBeVisible();
		}
	}
);
