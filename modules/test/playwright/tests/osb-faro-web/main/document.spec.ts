/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {assetPublisherPagesTest} from '../../../fixtures/assetPublisherPagesTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginAnalyticsCloudTest} from '../../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import getRandomString from '../../../utils/getRandomString';
import {createChannel} from './utils/channel';
import {ACPage, navigateToACPageViaURL} from './utils/navigation';
import {changeTimeFilter} from './utils/time-filter';
import {searchByTerm} from './utils/utils';

export const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	assetPublisherPagesTest,
	pageEditorPagesTest,
	featureFlagsTest({
		'LPD-39304': {enabled: true},
		'LPS-178052': {enabled: true},
	}),
	loginAnalyticsCloudTest(),
	loginTest()
);

const randomString = getRandomString();

const channelName = 'My Property ' + randomString;
const pageTitle = 'My Page';

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

test.afterEach(async ({apiHelpers}) => {
	await test.step('Delete channel and delete site on the DXP side', async () => {
		await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
			`[${channel.id}]`,
			project.groupId
		);
	});
});

test('Documents visitor behavior card shows expected amount of views', async ({
	apiHelpers,
	page,
}) => {
	await test.step('Create document events to appear within the Last 24 hours period in AC', async () => {
		const date1 = new Date();

		await apiHelpers.jsonWebServicesOSBAsah.createEvents([
			{
				applicationId: 'Document',
				assetId: '1',
				assetTitle: 'My Document 1',
				canonicalUrl: 'https://www.liferay.com',
				channelId: channel.id,
				eventDate: date1.toISOString(),
				eventId: 'documentPreviewed',
				title: pageTitle,
				userId: '1',
			},
			{
				applicationId: 'Document',
				assetId: '1',
				assetTitle: 'My Document 1',
				canonicalUrl: 'https://www.liferay.com',
				channelId: channel.id,
				eventDate: date1.toISOString(),
				eventId: 'documentPreviewed',
				title: pageTitle,
				userId: '1',
			},
			{
				applicationId: 'Document',
				assetId: '1',
				assetTitle: 'My Document 1',
				canonicalUrl: 'https://www.liferay.com',
				channelId: channel.id,
				eventDate: date1.toISOString(),
				eventId: 'documentDownloaded',
				title: pageTitle,
				userId: '1',
			},
			{
				applicationId: 'Document',
				assetId: '1',
				assetTitle: 'My Document 1',
				canonicalUrl: 'https://www.liferay.com',
				channelId: channel.id,
				eventDate: date1.toISOString(),
				eventId: 'documentDownloaded',
				title: pageTitle,
				userId: '1',
			},
			{
				applicationId: 'Document',
				assetId: '1',
				assetTitle: 'My Document 1',
				canonicalUrl: 'https://www.liferay.com',
				channelId: channel.id,
				eventDate: date1.toISOString(),
				eventId: 'documentDownloaded',
				title: pageTitle,
				userId: '1',
			},
		]);
	});

	await test.step('Go to Analytics Cloud asset page', async () => {
		navigateToACPageViaURL({
			acPage: ACPage.assetPage,
			channelID: channel.id,
			page,
			projectID: project.groupId,
		});
	});

	await test.step('Go to Documents and Media session', async () => {
		await page
			.locator('.navbar-collapse')
			.getByText('Documents and Media')
			.click();
	});

	await test.step('Change the time filter to Last 24 hours', async () => {
		await changeTimeFilter({
			page,
			timeFilterPeriod: 'Last 24 hours',
		});
	});

	await test.step('Assert the document is appearing at the list', async () => {
		const documentTitles = await page
			.locator('.documents-and-media-root .table-title')
			.all();

		expect(documentTitles.length).toBe(1);
	});

	let documentTitles;

	await test.step('Assert the document list', async () => {
		await searchByTerm({page, searchTerm: 'My Document 1'});

		documentTitles = await page
			.locator('.documents-and-media-root .table-title')
			.all();

		expect(documentTitles.length).toBe(1);

		expect(
			await page
				.locator('.documents-and-media-root .table-title')
				.getByText('My Document 1')
		).toBeVisible();
	});

	await test.step('Go into document and check Visitors Behavior metrics', async () => {
		await documentTitles[0].click();

		await expect(await page.getByText('Visitors Behavior')).toBeVisible();

		const metricTabs = await page
			.locator('.analytics-metrics-tabs .card-tab')
			.all();

		const downloadsMetricTab = metricTabs[0];

		expect(
			await downloadsMetricTab.locator('.metric-value').textContent()
		).toBe('3');

		const impressionsMetricTab = metricTabs[1];

		expect(
			await impressionsMetricTab.locator('.metric-value').textContent()
		).toBe('2');
	});
});
