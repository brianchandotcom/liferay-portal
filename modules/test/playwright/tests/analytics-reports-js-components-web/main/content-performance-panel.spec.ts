/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginAnalyticsCloudTest} from '../../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../../fixtures/loginTest';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../../utils/getRandomString';
import {syncAnalyticsCloud} from '../../analytics-settings-web/main/utils/analytics-settings';

const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginAnalyticsCloudTest(),
	loginTest()
);

test(
	'Content Performance panel shows title, URL, language switcher, traffic channels and default time range on a content page',
	{tag: ['@LPS-108856', '@LPS-126044']},
	async ({apiHelpers, page, site}) => {
		await syncAnalyticsCloud({
			apiHelpers,
			channelName: 'My Property - ' + getRandomString(),
			page,
			siteName: site.name,
		});

		const title = getRandomString();

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			siteId: site.id,
			title,
		});

		await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

		await clickAndExpectToBeVisible({
			target: page.locator('.lfr-analytics-reports-panel'),
			trigger: page
				.locator('.control-menu-nav')
				.getByLabel('Content Performance'),
		});

		const panel = page.locator('.lfr-analytics-reports-panel');

		await expect(panel.getByText(title)).toBeVisible();
		await expect(
			panel.locator(`[title$="/web${site.friendlyUrlPath}"]`)
		).toBeVisible();
		await expect(panel.getByLabel('Select Date Range')).toHaveValue(
			'last-7-days'
		);
		await expect(panel.getByText('Total Views')).toBeVisible();
		await expect(panel.getByText('Traffic Channels')).toBeVisible();
		await expect(panel.locator('.sticker-user-icon')).toHaveCount(0);

		for (const channel of [
			'Direct',
			'Organic',
			'Paid',
			'Referral',
			'Social',
		]) {
			await expect(panel.getByText(channel)).toBeVisible();
		}
	}
);

test(
	'Content Performance panel renders on a widget page',
	{tag: '@LPS-126047'},
	async ({apiHelpers, page, site}) => {
		await syncAnalyticsCloud({
			apiHelpers,
			channelName: 'My Property - ' + getRandomString(),
			page,
			siteName: site.name,
		});

		const title = getRandomString();

		const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			options: {type: 'portlet'},
			title,
		});

		await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyURL}`);

		await clickAndExpectToBeVisible({
			target: page.locator('.lfr-analytics-reports-panel'),
			trigger: page
				.locator('.control-menu-nav')
				.getByLabel('Content Performance'),
		});

		const panel = page.locator('.lfr-analytics-reports-panel');

		await expect(panel.getByText(title)).toBeVisible();
		await expect(
			panel.locator(`[title*="/web${site.friendlyUrlPath}"]`)
		).toBeVisible();
		await expect(panel.getByLabel('Select Date Range')).toBeVisible();
		await expect(panel.getByText('Traffic Channels')).toBeVisible();
		await expect(panel.locator('.sticker-user-icon')).toHaveCount(0);
	}
);
