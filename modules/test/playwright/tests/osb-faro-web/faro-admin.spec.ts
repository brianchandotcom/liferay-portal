/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {loginAnalyticsCloudTest} from '../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';
import {syncAnalyticsCloud} from '../analytics-settings-web/utils/analytics-settings';
import {navigateToACAdmin} from './utils/navigation';

export const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	loginAnalyticsCloudTest(),
	loginTest()
);

test('Assert disconnect data source option is enabled when connected', async ({
	apiHelpers,
	page,
}) => {
	await test.step('Check drop down item is disabled', async () => {
		await navigateToACAdmin({page});

		await page.getByText('Disconnect Data Sources').isDisabled();
	});

	const channelName = 'My Property - ' + getRandomString();

	await test.step('Connect the DXP to AC', async () => {
		await syncAnalyticsCloud({
			apiHelpers,
			channelName,
			page,
		});
	});

	await test.step('Check drop down item is enabled', async () => {
		await navigateToACAdmin({page});

		const dropDownItem = page.getByText('Disconnect Data Sources');

		await dropDownItem.isEnabled();

		await page.locator('.dropdown-action').click();

		dropDownItem.click();

		page.once('dialog', (dialog) => {
			dialog.accept();
		});

		await dropDownItem.isEnabled();
	});
});
