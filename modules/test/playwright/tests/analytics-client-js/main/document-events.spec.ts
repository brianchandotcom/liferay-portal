/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import {createReadStream} from 'fs';
import path from 'path';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginAnalyticsCloudTest} from '../../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageViewModePagesTest} from '../../../fixtures/pageViewModePagesTest';
import getRandomString from '../../../utils/getRandomString';
import {syncAnalyticsCloud} from '../../analytics-settings-web/main/utils/analytics-settings';
import {captureAnalyticsEvents} from './utils/captureAnalyticsEvents';

const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginAnalyticsCloudTest(),
	loginTest(),
	pageViewModePagesTest
);

test(
	'Document interaction events fire with the expected file entry properties and request common properties',
	{
		tag: ['@LRAC-8944', '@LRAC-8969'],
	},
	async ({apiHelpers, page, site, widgetPagePage}) => {
		const documentTitle = 'AC Document ' + getRandomString();

		const document = await apiHelpers.headlessDelivery.postDocument(
			site.id,
			createReadStream(
				path.join(__dirname, '/dependencies/Document.jpg')
			),
			{
				fileName: 'Document.jpg',
				title: documentTitle,
			}
		);

		// Use a widget page with the Documents and Media portlet

		const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			options: {type: 'portlet'},
			title: getRandomString(),
		});

		await widgetPagePage.goto(layout, site.friendlyUrlPath);

		await widgetPagePage.addPortlet('Documents and Media');

		const {channel} = await syncAnalyticsCloud({
			apiHelpers,
			channelName: 'My Property - ' + getRandomString(),
			page,
			siteName: site.name,
		});

		const capturedEvents = captureAnalyticsEvents(page);

		await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyURL}`);

		// Fire documentDownloaded from the list Info Panel

		await page.getByRole('button', {name: 'Toggle Info Panel'}).click();

		await page.locator('.card', {hasText: documentTitle}).first().hover();

		await page
			.locator('.card', {hasText: documentTitle})
			.first()
			.locator('input[type="checkbox"]')
			.check();

		await page
			.locator('[data-analytics-file-entry-id]')
			.getByText('Download', {exact: true})
			.click();

		await expect
			.poll(() =>
				capturedEvents.find(
					(event) => event.eventId === 'documentDownloaded'
				)
			)
			.toBeTruthy();

		const documentDownloaded = capturedEvents.find(
			(event) => event.eventId === 'documentDownloaded'
		);

		expect(documentDownloaded.properties.fileEntryId).toBe(
			String(document.id)
		);
		expect(documentDownloaded.properties.title).toBe(documentTitle);
		expect(documentDownloaded.properties.version).toBeTruthy();
		expect(documentDownloaded.properties.groupId).toBe(String(site.id));
		expect(documentDownloaded.channelId).toBe(channel.id);
		expect(documentDownloaded.dataSourceId).toBeTruthy();

		// Open the document to fire documentPreviewed (the file entry view fires the event on load).

		await page
			.getByRole('link', {exact: true, name: documentTitle})
			.first()
			.click();

		await expect
			.poll(() =>
				capturedEvents.find(
					(event) => event.eventId === 'documentPreviewed'
				)
			)
			.toBeTruthy();

		const documentPreviewed = capturedEvents.find(
			(event) => event.eventId === 'documentPreviewed'
		);

		expect(documentPreviewed.properties.fileEntryId).toBe(
			String(document.id)
		);
		expect(documentPreviewed.properties.title).toBe(documentTitle);
		expect(documentPreviewed.properties.version).toBeTruthy();
		expect(documentPreviewed.properties.groupId).toBe(String(site.id));
		expect(documentPreviewed.channelId).toBe(channel.id);
		expect(documentPreviewed.dataSourceId).toBeTruthy();
	}
);
