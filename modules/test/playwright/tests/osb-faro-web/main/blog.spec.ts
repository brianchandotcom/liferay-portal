/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {assetPublisherPagesTest} from '../../../fixtures/assetPublisherPagesTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedChannelTest} from '../../../fixtures/isolatedChannelTest';
import {loginAnalyticsCloudTest} from '../../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../../utils/getRandomString';
import {createIndividuals, generateIndividual} from './utils/individuals';
import {Nanites, runNanites} from './utils/nanites';
import {ACPage, navigateToACPageViaURL} from './utils/navigation';
import {changeTimeFilter} from './utils/time-filter';
import {searchByTerm} from './utils/utils';

export const test = mergeTests(
	apiHelpersTest,
	assetPublisherPagesTest,
	pageEditorPagesTest,
	featureFlagsTest({
		'LPD-39304': {enabled: true},
		'LPS-178052': {enabled: true},
	}),
	isolatedChannelTest,
	loginAnalyticsCloudTest(),
	loginTest()
);

const pageTitle = 'My Page';

test('View all blogs in the property in assets', async ({
	analyticsChannel: channel,
	apiHelpers,
	page,
	project,
}) => {
	await test.step('Create blog events to appear within the Last 24 hours period in AC', async () => {
		const date1 = new Date();

		await apiHelpers.jsonWebServicesOSBAsah.createEvents([
			{
				applicationId: 'Blog',
				assetId: '1',
				assetTitle: 'My Blog 1',
				canonicalUrl: 'https://www.liferay.com',
				channelId: channel.id,
				eventDate: date1.toISOString(),
				eventId: 'blogViewed',
				title: pageTitle,
				userId: '1',
			},
			{
				applicationId: 'Blog',
				assetId: '2',
				assetTitle: 'My Blog 2',
				canonicalUrl: 'https://www.liferay.com',
				channelId: channel.id,
				eventDate: date1.toISOString(),
				eventId: 'blogViewed',
				title: pageTitle,
				userId: '1',
			},
		]);
	});

	await test.step('Go to Analytics Cloud asset page', async () => {
		await navigateToACPageViaURL({
			acPage: ACPage.assetPage,
			channelID: channel.id,
			page,
			projectID: project.groupId,
		});
	});

	await test.step('Go to Blogs session', async () => {
		await page.locator('.navbar-collapse').getByText('Blogs').click();
	});

	await test.step('Change the time filter to Last 24 hours', async () => {
		await changeTimeFilter({
			page,
			timeFilterPeriod: 'Last 24 hours',
		});
	});

	await test.step('Assert the blogs are appearing at the list', async () => {
		const blogTitles = await page.locator('.blogs-root .table-title').all();

		expect(blogTitles.length).toBe(2);
	});

	await test.step('Assert the blog list', async () => {
		await searchByTerm({page, searchTerm: 'My Blog 2'});

		let blogTitles = await page.locator('.blogs-root .table-title').all();

		expect(blogTitles.length).toBe(1);

		await expect(
			page.locator('.blogs-root .table-title').getByText('My Blog 2')
		).toBeVisible();

		await searchByTerm({page, searchTerm: 'My Blog 1'});

		blogTitles = await page.locator('.blogs-root .table-title').all();

		expect(blogTitles.length).toBe(1);

		await expect(
			page.locator('.blogs-root .table-title').getByText('My Blog 1')
		).toBeVisible();
	});
});

test(
	'Blog overview surfaces appears-on, cards, comments, and audience metrics',
	{
		tag: [
			'@LRAC-8387',
			'@LRAC-8374',
			'@LRAC-8113',
			'@LRAC-12347',
			'@LRAC-8351',
		],
	},
	async ({analyticsChannel: channel, apiHelpers, page, project}) => {

		// Seed two known individuals and one anonymous identity so the audience card splits 2 known / 1 anonymous (66.67% / 33.33%)

		const knownIndividualA = generateIndividual({name: 'ac'});
		const knownIndividualB = generateIndividual({name: 'liferay'});

		await createIndividuals({
			apiHelpers,
			individuals: [knownIndividualA, knownIndividualB],
		});

		const date = new Date();
		const anonymousIdentityId = getRandomString();

		await apiHelpers.jsonWebServicesOSBAsah.createIdentities([
			{createDate: date.toISOString(), id: anonymousIdentityId},
		]);

		// Four blogViewed events from three distinct viewers (views = 4) plus one blogCommented (comments = 1)

		await apiHelpers.jsonWebServicesOSBAsah.createEvents([
			{
				applicationId: 'Blog',
				assetId: '1',
				assetTitle: 'Blogs AC Title',
				canonicalUrl: '/web/site-name/ac-page/-/blogs/blogs-ac-title',
				channelId: channel.id,
				dataSourceId: 0,
				eventDate: date.toISOString(),
				eventId: 'blogViewed',
				title: pageTitle,
				userId: knownIndividualA.id,
			},
			{
				applicationId: 'Blog',
				assetId: '1',
				assetTitle: 'Blogs AC Title',
				canonicalUrl: '/web/site-name/ac-page/-/blogs/blogs-ac-title',
				channelId: channel.id,
				dataSourceId: 0,
				eventDate: date.toISOString(),
				eventId: 'blogViewed',
				title: pageTitle,
				userId: knownIndividualB.id,
			},
			{
				applicationId: 'Blog',
				assetId: '1',
				assetTitle: 'Blogs AC Title',
				canonicalUrl: '/web/site-name/ac-page/-/blogs/blogs-ac-title',
				channelId: channel.id,
				dataSourceId: 0,
				eventDate: date.toISOString(),
				eventId: 'blogViewed',
				title: pageTitle,
				userId: anonymousIdentityId,
			},
			{
				applicationId: 'Blog',
				assetId: '1',
				assetTitle: 'Blogs AC Title',
				canonicalUrl: '/web/site-name/ac-page/-/blogs/blogs-ac-title',
				channelId: channel.id,
				dataSourceId: 0,
				eventDate: date.toISOString(),
				eventId: 'blogViewed',
				title: pageTitle,
				userId: anonymousIdentityId,
			},
			{
				applicationId: 'Comment',
				assetId: '1',
				assetTitle: 'Blogs AC Title',
				canonicalUrl: '/web/site-name/ac-page/-/blogs/blogs-ac-title',
				channelId: channel.id,
				dataSourceId: 0,
				eventDate: date.toISOString(),
				eventId: 'posted',
				eventProperties:
					'{"className":"com.liferay.blogs.model.BlogsEntry"}',
				properties: [
					{
						name: 'className',
						value: 'com.liferay.blogs.model.BlogsEntry',
					},
				],
				title: pageTitle,
				userId: knownIndividualA.id,
			},
		]);

		// Create a batch segment that includes only knownIndividualA so the audience card second donut splits 1 segmented / 1 unsegmented (50% / 50%)

		await apiHelpers.jsonWebServicesOSBFaro.createIndividualSegment({
			channelId: channel.id,
			filter: `(firstName eq 'ac')`,
			groupId: project.groupId,
			name: 'Audience Segment ' + getRandomString(),
		});

		// Run the membership nanite so the new batch segment picks up its only matching individual

		await runNanites({
			apiHelpers,
			naniteNames: [Nanites.UpdateMembershipsNanite],
			page,
		});

		// Open the blog overview

		await navigateToACPageViaURL({
			acPage: ACPage.assetBlogsPage,
			channelID: channel.id,
			page,
			projectID: project.groupId,
		});

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('menuitem', {name: 'Last 24 hours'}),
			trigger: page.getByRole('button', {name: 'Last 30 days'}),
		});

		await page
			.getByRole('link', {exact: true, name: 'Blogs AC Title'})
			.click();

		// The overview lists the five expected cards

		for (const cardTitle of [
			'Visitors Behavior',
			'Audience',
			'Views by Location',
			'Views by Technology',
			'Asset Appears On',
		]) {
			await expect(page.getByText(cardTitle)).toBeVisible();
		}

		// Visitors behavior shows views = 4 and comments = 1

		await expect(
			page
				.locator('.analytics-metrics-tabs .card-tab')
				.filter({hasText: 'Views'})
				.locator('.metric-value')
		).toHaveText('4');

		await expect(
			page
				.locator('.analytics-metrics-tabs .card-tab')
				.filter({hasText: 'Comments'})
				.locator('.metric-value')
		).toHaveText('1');

		// Asset appears on lists the seeded blog page URL

		await expect(
			page.getByRole('cell', {
				name: '/web/site-name/ac-page/-/blogs/blogs-ac-title',
			})
		).toBeVisible();

		// Audience card first donut splits 66.67% known / 33.33% anonymous

		await expect(page.getByText('66.67%')).toBeVisible();
		await expect(page.getByText('33.33%')).toBeVisible();

		// Audience card second donut splits 50% segmented / 50% unsegmented

		await expect(
			page.locator('.audience-report-chart-donut').nth(1).getByText('50%')
		).toHaveCount(2);
	}
);
