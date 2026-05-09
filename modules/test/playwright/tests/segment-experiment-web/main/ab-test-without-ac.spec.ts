/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {clickAndExpectToBeHidden} from '../../../utils/clickAndExpectToBeHidden';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../../utils/getRandomString';
import performLogin, {
	performLogout,
	userData,
} from '../../../utils/performLogin';
import {
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
	loginTest()
);

test(
	'AB Test sidebar resets per session and can be permanently hidden',
	{tag: ['@LPS-132406', '@LPS-101055']},
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
			await apiHelpers.headlessAdminUser.getRoleByExternalReferenceCode(
				'L_SITE_ADMINISTRATOR'
			);

		await apiHelpers.headlessAdminUser.assignUserToSite(
			siteAdminRole.id,
			site.id,
			user.id
		);

		await performLogout(page);

		await performLogin(page, user.alternateName);

		await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

		// Open the AB Test panel

		await clickAndExpectToBeVisible({
			target: page.getByText('Connect to Liferay Analytics Cloud'),
			trigger: page.getByRole('button', {exact: true, name: 'A/B Test'}),
		});

		// Log out and back in and assert the sidebar resets per session

		await performLogout(page);

		await performLogin(page, user.alternateName);

		await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

		await expect(
			page.getByText('Connect to Liferay Analytics Cloud')
		).not.toBeVisible();

		// Hide the AB Test panel permanently

		await clickAndExpectToBeVisible({
			target: page.getByText('Connect to Liferay Analytics Cloud'),
			trigger: page.getByRole('button', {exact: true, name: 'A/B Test'}),
		});

		await clickAndExpectToBeHidden({
			target: page.getByRole('button', {exact: true, name: 'A/B Test'}),
			trigger: page.getByText('Do not show me this again'),
		});
	}
);
