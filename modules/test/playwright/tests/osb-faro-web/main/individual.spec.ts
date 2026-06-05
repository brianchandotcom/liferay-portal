/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedChannelTest} from '../../../fixtures/isolatedChannelTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginAnalyticsCloudTest} from '../../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import {syncAnalyticsCloudViaAPI} from '../../analytics-settings-web/main/utils/analytics-settings';
import getFragmentDefinition from '../../layout-content-page-editor-web/main/utils/getFragmentDefinition';
import getPageDefinition from '../../layout-content-page-editor-web/main/utils/getPageDefinition';
import {
	addBreakdownByAttribute,
	viewBreakdownRechartsData,
} from './utils/distribution';
import {createIndividuals, generateIndividual} from './utils/individuals';
import {ACPage, navigateToACPageViaURL} from './utils/navigation';

export const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedChannelTest,
	isolatedSiteTest,
	loginAnalyticsCloudTest(),
	loginTest()
);

test.beforeEach(async ({analyticsChannel, apiHelpers, project, site}) => {
	await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([
			getFragmentDefinition({
				id: getRandomString(),
				key: 'BASIC_COMPONENT-heading',
			}),
		]),
		siteId: site.id,
		title: 'My Page',
	});

	await syncAnalyticsCloudViaAPI({
		apiHelpers,
		channel: analyticsChannel,
		project,
		siteId: Number(site.id),
	});
});

test(
	'Add a new breakdown by an attribute and assert that correct results appear',
	{
		tag: '@Legacy',
	},
	async ({analyticsChannel: channel, apiHelpers, page, project}) => {
		const individualName = 'ac';
		const individuals = [
			generateIndividual({
				name: individualName,
			}),
		];

		await test.step('Create new Individual', async () => {
			await createIndividuals({
				apiHelpers,
				individuals,
			});
		});

		const date = new Date();
		await test.step('Create Individual Event', async () => {
			const events = individuals.map((individual) => ({
				applicationId: 'Page',
				canonicalUrl: 'https://www.liferay.com',
				channelId: channel.id,
				eventDate: date.toISOString(),
				eventId: 'pageViewed',
				title: 'Liferay',
				userId: individual.id,
			}));

			await apiHelpers.jsonWebServicesOSBAsah.createEvents(events);
		});

		await test.step('Create Individual Session', async () => {
			const sessions = individuals.map((individual) => ({
				channelId: channel.id,
				id: individual.id,
				sessionEnd: date.toISOString(),
				sessionStart: date.toISOString(),
				userId: individual.id,
			}));

			await apiHelpers.jsonWebServicesOSBAsah.createSessions(sessions);
		});

		await test.step('Go to Individuals Dashboard', async () => {
			await navigateToACPageViaURL({
				acPage: ACPage.individualPage,
				channelID: channel.id,
				page,
				projectID: project.groupId,
			});
		});

		await test.step('Add a new breakdown', async () => {
			await addBreakdownByAttribute({
				attributeName: 'email',
				page,
			});
		});

		await test.step('Check if the correct results appear (email and maximum count)', async () => {
			await viewBreakdownRechartsData({
				attributeValue: `${individualName}@liferay.com`,
				maxCount: '1',
				page,
			});
		});

		await test.step('Close breakdown tab', async () => {
			await page.getByLabel('Close').click();
		});
	}
);

test(
	'Distribution page can be filtered by a specific string',
	{
		tag: '@Legacy',
	},
	async ({analyticsChannel: channel, apiHelpers, page, project}) => {
		const individualName = 'ac';
		const individuals = [
			generateIndividual({
				name: individualName,
			}),
		];

		await test.step('Create new Individual', async () => {
			await createIndividuals({
				apiHelpers,
				individuals,
			});
		});

		const date = new Date();
		await test.step('Create Individual Event', async () => {
			const events = individuals.map((individual) => ({
				applicationId: 'Page',
				canonicalUrl: 'https://www.liferay.com',
				channelId: channel.id,
				eventDate: date.toISOString(),
				eventId: 'pageViewed',
				title: 'Liferay',
				userId: individual.id,
			}));

			await apiHelpers.jsonWebServicesOSBAsah.createEvents(events);
		});

		await test.step('Create Individual Session', async () => {
			const sessions = individuals.map((individual) => ({
				channelId: channel.id,
				id: individual.id,
				sessionEnd: date.toISOString(),
				sessionStart: date.toISOString(),
				userId: individual.id,
			}));

			await apiHelpers.jsonWebServicesOSBAsah.createSessions(sessions);
		});

		await test.step('Go to Individuals Dashboard', async () => {
			await navigateToACPageViaURL({
				acPage: ACPage.individualPage,
				channelID: channel.id,
				page,
				projectID: project.groupId,
			});
		});

		await test.step('Go to Distribution tab', async () => {
			await page.getByRole('link', {name: 'Distribution'}).click();

			await expect(
				page.getByText('Distribution by attribute')
			).toBeVisible();
		});

		await test.step('Add a new breakdown', async () => {
			await page.locator('.selected-item-container').click();

			await page.getByRole('menuitem', {name: 'email'}).click();
		});

		await test.step('Check if the correct results appear (email and maximum count)', async () => {
			await expect(
				page.getByText(`${individualName}@liferay.com - 100.0%`)
			).toBeVisible();
		});
	}
);

test(
	'The Individuals dashboard overview shows all of its summary cards',
	{
		tag: '@LRAC-8903',
	},
	async ({analyticsChannel: channel, page, project}) => {
		await navigateToACPageViaURL({
			acPage: ACPage.individualPage,
			channelID: channel.id,
			page,
			projectID: project.groupId,
		});

		// The Total Individuals, Known and Anonymous trend summaries

		for (const trendName of ['Total Individuals', 'Known', 'Anonymous']) {
			await expect(
				page.locator('.trend-item-root').filter({hasText: trendName})
			).toBeVisible();
		}

		// The remaining overview cards

		for (const cardTitle of [
			'Enriched Profiles',
			'Active Individuals',
			'Top Interests',
		]) {
			await expect(page.getByText(cardTitle).first()).toBeVisible();
		}
	}
);
