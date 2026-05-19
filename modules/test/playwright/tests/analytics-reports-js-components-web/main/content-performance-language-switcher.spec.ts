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
	'Switching the Content Performance panel language does not change the user locale',
	{tag: '@LPS-110920'},
	async ({apiHelpers, page, site}) => {
		await syncAnalyticsCloud({
			apiHelpers,
			channelName: 'My Property - ' + getRandomString(),
			page,
			siteName: site.name,
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			siteId: site.id,
			title: getRandomString(),
		});

		await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

		const initialUrl = page.url();

		const initialLang = await page.locator('html').getAttribute('lang');

		// Open Content Performance panel

		await clickAndExpectToBeVisible({
			target: page.locator('.lfr-analytics-reports-panel'),
			trigger: page
				.locator('.control-menu-nav')
				.getByLabel('Content Performance'),
		});

		// Open the language dropdown and pick a non-default locale

		const panel = page.locator('.lfr-analytics-reports-panel');

		await clickAndExpectToBeVisible({
			target: page.locator('.dropdown-menu__translation'),
			trigger: panel.locator('.dropdown-toggle.btn-monospaced').first(),
		});

		const nonDefaultItem = page
			.locator('.dropdown-menu__translation .dropdown-item')
			.filter({hasNot: page.getByText('default')})
			.first();

		await nonDefaultItem.click();

		// The browser URL and the user locale must not change

		await expect.poll(() => page.url(), {timeout: 3000}).toBe(initialUrl);

		expect(await page.locator('html').getAttribute('lang')).toBe(
			initialLang
		);
	}
);
