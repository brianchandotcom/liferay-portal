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
