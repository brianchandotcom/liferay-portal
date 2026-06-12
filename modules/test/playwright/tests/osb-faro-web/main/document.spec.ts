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

test('Documents visitor behavior card shows expected amount of views', async ({
	analyticsChannel: channel,
	apiHelpers,
	page,
	project,
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
		await navigateToACPageViaURL({
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

		await expect(
			page
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

test(
	'Document overview surfaces appears-on, technology, and audience metrics',
	{
		tag: ['@LRAC-8419', '@LRAC-8403', '@LRAC-8414'],
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

		// Three documentDownloaded events from three distinct viewers (Downloads = 3, all Desktop)

		await apiHelpers.jsonWebServicesOSBAsah.createEvents([
			{
				applicationId: 'Document',
				assetId: '1',
				assetTitle: 'DM AC Title',
				browserName: 'Chrome',
				canonicalUrl:
					'/web/site-name/ac-page/-/document_library/view_file/1',
				channelId: channel.id,
				dataSourceId: 0,
				deviceType: 'Desktop',
				eventDate: date.toISOString(),
				eventId: 'documentDownloaded',
				title: 'DM AC Title',
				userId: knownIndividualA.id,
			},
			{
				applicationId: 'Document',
				assetId: '1',
				assetTitle: 'DM AC Title',
				browserName: 'Chrome',
				canonicalUrl:
					'/web/site-name/ac-page/-/document_library/view_file/1',
				channelId: channel.id,
				dataSourceId: 0,
				deviceType: 'Desktop',
				eventDate: date.toISOString(),
				eventId: 'documentDownloaded',
				title: 'DM AC Title',
				userId: knownIndividualB.id,
			},
			{
				applicationId: 'Document',
				assetId: '1',
				assetTitle: 'DM AC Title',
				browserName: 'Chrome',
				canonicalUrl:
					'/web/site-name/ac-page/-/document_library/view_file/1',
				channelId: channel.id,
				dataSourceId: 0,
				deviceType: 'Desktop',
				eventDate: date.toISOString(),
				eventId: 'documentDownloaded',
				title: 'DM AC Title',
				userId: anonymousIdentityId,
			},
		]);

		// Open the document overview

		await navigateToACPageViaURL({
			acPage: ACPage.assetDocumentsAndMediaPage,
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
			.getByRole('link', {exact: true, name: 'DM AC Title'})
			.click();

		// Visitors Behavior shows Downloads = 3

		await expect(
			page
				.locator('.analytics-metrics-tabs .card-tab')
				.filter({hasText: 'Downloads'})
				.locator('.metric-value')
		).toHaveText('3');

		// Asset Appears On lists the seeded document page URL

		await expect(
			page.getByRole('cell', {
				name: '/web/site-name/ac-page/-/document_library/view_file/1',
			})
		).toBeVisible();

		// Downloads by Technology surfaces the Desktop device

		await expect(
			page
				.locator('.card-root')
				.filter({hasText: 'Downloads by Technology'})
				.getByText('Desktop')
		).toBeVisible();

		// Audience card splits 66.67% known / 33.33% anonymous

		await expect(page.getByText('66.67%')).toBeVisible();
		await expect(page.getByText('33.33%')).toBeVisible();
	}
);

test(
	'Document downloads are tracked on the Documents and Media asset',
	{
		tag: ['@LRAC-10671', '@LRAC-10672', '@LRAC-10673'],
	},
	async ({analyticsChannel: channel, apiHelpers, page, project}) => {
		const date = new Date();

		const documentDownloadedEvent = {
			applicationId: 'Document',
			assetId: '1',
			assetTitle: 'DM AC Title',
			canonicalUrl:
				'/web/site-name/ac-page/-/document_library/view_file/1',
			channelId: channel.id,
			eventDate: date.toISOString(),
			eventId: 'documentDownloaded',
			title: 'DM AC Title',
			userId: '1',
		};

		await apiHelpers.jsonWebServicesOSBAsah.createEvents([
			documentDownloadedEvent,
			documentDownloadedEvent,
		]);

		await navigateToACPageViaURL({
			acPage: ACPage.assetDocumentsAndMediaPage,
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
			.getByRole('link', {exact: true, name: 'DM AC Title'})
			.click();

		// The Visitors Behavior card reflects the two downloads

		await expect(
			page
				.locator('.analytics-metrics-tabs .card-tab')
				.filter({hasText: 'Downloads'})
				.locator('.metric-value')
		).toHaveText('2');
	}
);
