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
import {clickAndExpectToBeHidden} from '../../../utils/clickAndExpectToBeHidden';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../../utils/getRandomString';
import performLogin, {
	performLogout,
	userData,
} from '../../../utils/performLogin';
import {
	connectToAnalyticsCloudWithNoSiteSynced,
	disconnectFromAnalyticsCloud,
	goToAnalyticsCloudInstanceSettings,
} from '../../analytics-settings-web/main/utils/analytics-settings';
import {acceptsCookiesBanner} from '../../osb-faro-web/main/utils/portal';

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
	'Content Performance panel resets per session and can be permanently hidden',
	{tag: ['@LPS-137279', '@LPS-108856']},
	async ({apiHelpers, page, site}) => {

		// Disconnect the DXP from Analytics Cloud

		await goToAnalyticsCloudInstanceSettings(page);

		await acceptsCookiesBanner(page);

		await disconnectFromAnalyticsCloud(page);

		// Create a content page on the site

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			siteId: site.id,
			title: getRandomString(),
		});

		// Create a fresh site administrator to avoid leaking preferences

		const user = await apiHelpers.headlessAdminUser.postUserAccount();

		userData[user.alternateName] = {
			name: user.givenName,
			password: 'test',
			surname: user.familyName,
		};

		const siteAdminRole =
			await apiHelpers.headlessAdminUser.getRoleByName(
				'Site Administrator'
			);

		await apiHelpers.headlessAdminUser.assignUserToSite(
			siteAdminRole.id,
			site.id,
			user.id
		);

		await performLogout(page);

		await performLogin(page, user.alternateName);

		await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

		// Open the Content Performance panel

		await clickAndExpectToBeVisible({
			target: page.locator('.lfr-analytics-reports-panel'),
			trigger: page
				.locator('.control-menu-nav')
				.getByLabel('Content Performance'),
		});

		// Log out and back in and assert the panel does not auto-open per session

		await performLogout(page);

		await performLogin(page, user.alternateName);

		await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

		await expect(
			page.locator('.lfr-analytics-reports-panel')
		).not.toBeVisible();

		// Reopen the panel and hide it permanently

		await clickAndExpectToBeVisible({
			target: page.locator('.lfr-analytics-reports-panel'),
			trigger: page
				.locator('.control-menu-nav')
				.getByLabel('Content Performance'),
		});

		await clickAndExpectToBeHidden({
			target: page
				.locator('.control-menu-nav')
				.getByLabel('Content Performance'),
			trigger: page.getByText('Do not show me this again'),
		});
	}
);

test(
	'Content Performance panel shows sync instructions when Analytics Cloud is connected but not synced',
	{tag: '@LPS-108856'},
	async ({apiHelpers, page, site}) => {
		try {
			await connectToAnalyticsCloudWithNoSiteSynced(page);

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				siteId: site.id,
				title: getRandomString(),
			});

			await page.goto(
				`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			await clickAndExpectToBeVisible({
				target: page.getByText('Sync to Liferay Analytics Cloud'),
				trigger: page
					.locator('.control-menu-nav')
					.getByLabel('Content Performance'),
			});

			await expect(
				page.getByText('Do not show me this again')
			).not.toBeVisible();
		}
		finally {
			await goToAnalyticsCloudInstanceSettings(page);

			await disconnectFromAnalyticsCloud(page);
		}
	}
);
