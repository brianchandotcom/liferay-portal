/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginAnalyticsCloudTest} from '../../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import {syncAnalyticsCloud} from '../../analytics-settings-web/main/utils/analytics-settings';
import getPageDefinition from '../../layout-content-page-editor-web/main/utils/getPageDefinition';
import getWidgetDefinition from '../../layout-content-page-editor-web/main/utils/getWidgetDefinition';
import {captureAnalyticsEvents} from './utils/captureAnalyticsEvents';

const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginAnalyticsCloudTest(),
	loginTest()
);

test(
	'Blog interaction events fire with the expected entry properties and request common properties',
	{
		tag: ['@LRAC-8661', '@LRAC-8662'],
	},
	async ({apiHelpers, page, site}) => {
		const blog = await apiHelpers.headlessDelivery.postBlog(site.id, {
			articleBody:
				'The quick brown fox jumps over the lazy dog one two three four five six seven eight nine ten eleven twelve thirteen',
			headline: 'Blogs AC Title',
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				getWidgetDefinition({
					id: getRandomString(),
					widgetName: 'com_liferay_blogs_web_portlet_BlogsPortlet',
				}),
			]),
			siteId: site.id,
			title: 'Blog Page ' + getRandomString(),
		});

		const {channel} = await syncAnalyticsCloud({
			apiHelpers,
			channelName: 'My Property - ' + getRandomString(),
			page,
			siteName: site.name,
		});

		const capturedEvents = captureAnalyticsEvents(page);

		// Visit the blog detail page directly to fire blogViewed

		await page.goto(
			`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}/-/blogs/${blog.friendlyUrlPath}`
		);

		await expect
			.poll(() =>
				capturedEvents.find((event) => event.eventId === 'blogViewed')
			)
			.toBeTruthy();

		const blogViewed = capturedEvents.find(
			(event) => event.eventId === 'blogViewed'
		);

		expect(blogViewed.properties.entryId).toBe(String(blog.id));
		expect(blogViewed.properties.title).toBe('Blogs AC Title');
		expect(blogViewed.properties.numberOfWords).toBeTruthy();
		expect(blogViewed.channelId).toBe(channel.id);
		expect(blogViewed.dataSourceId).toBeTruthy();

		// Click inside the rendered blog detail to fire blogClicked.

		await page
			.locator('.widget-mode-detail')
			.getByText('Blogs AC Title')
			.first()
			.click();

		await expect
			.poll(() =>
				capturedEvents.find((event) => event.eventId === 'blogClicked')
			)
			.toBeTruthy();

		const blogClicked = capturedEvents.find(
			(event) => event.eventId === 'blogClicked'
		);

		expect(blogClicked.properties.entryId).toBe(String(blog.id));
		expect(blogClicked.properties.title).toBe('Blogs AC Title');
		expect(blogClicked.properties.tagName).toBeTruthy();
		expect(blogClicked.channelId).toBe(channel.id);
		expect(blogClicked.dataSourceId).toBeTruthy();
	}
);
