/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {accountSettingsPagesTest} from '../../../../../fixtures/accountSettingsPagesTest';
import {apiHelpersTest} from '../../../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../../../fixtures/loginTest';
import {EFDSVisualizationMode, waitForFDS} from '../../../../../utils/waitFor';
import {fdsSamplePageTest} from '../../fixtures/fdsSamplePageTest';

const test = mergeTests(
	apiHelpersTest,
	fdsSamplePageTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest()
);

let fdsSamplePageURL: string;

test.beforeEach(async ({fdsSamplePage, page, site}) => {
	const {url} = await fdsSamplePage.setupFDSSampleWidget({site});

	fdsSamplePageURL = url;

	await fdsSamplePage.selectTab('Advanced');

	await waitForFDS({page, visualizationMode: EFDSVisualizationMode.TABLE});
});

const accountSettingsTest = mergeTests(test, accountSettingsPagesTest);

accountSettingsTest(
	'Set time zone from theme display in a datetime renderer',
	{
		tag: ['@LPD-37756'],
	},
	async ({accountSettingsPage, fdsSamplePage, page}) => {
		await test.step('Check date in UTC time zone', async () => {
			await accountSettingsPage.goToDisplaySettings();

			await accountSettingsPage.setTimeZone('UTC');

			await page.goto(fdsSamplePageURL);

			await fdsSamplePage.selectTab('Advanced');

			await expect(
				page.getByText('Jan 1, 2020, 12:00:00 AM')
			).toBeVisible();
		});

		await test.step('Check date in a different time zone', async () => {
			await accountSettingsPage.goToDisplaySettings();

			await accountSettingsPage.setTimeZone('Europe/Paris');

			await page.goto(fdsSamplePageURL);

			await fdsSamplePage.selectTab('Advanced');

			await expect(
				page.getByText('Jan 1, 2020, 1:00:00 AM')
			).toBeVisible();
		});

		await test.step('Revert to default UTC time zone', async () => {
			await accountSettingsPage.goToDisplaySettings();

			await accountSettingsPage.setTimeZone('UTC');
		});
	}
);
