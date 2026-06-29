/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {isolatedChannelTest} from '../../../fixtures/isolatedChannelTest';
import {loginAnalyticsCloudTest} from '../../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../../fixtures/loginTest';
import {
	connectToAnalyticsCloud,
	disconnectFromAnalyticsCloud,
	goPreviousStep,
	goToAnalyticsCloudInstanceSettings,
} from '../../analytics-settings-web/main/utils/analytics-settings';
import {
	createDataSource,
	gotoLatestLiferayDXPDataSource,
} from '../main/utils/data-source';

export const test = mergeTests(
	apiHelpersTest,
	isolatedChannelTest,
	loginAnalyticsCloudTest(),
	loginTest()
);

test(
	'Reconnect DXP to an existing Analytics Cloud data source',
	{
		tag: '@LPD-91300',
	},
	async ({page, project}) => {

		// Create a data source in Analytics Cloud and connect the DXP for the first time

		const {token: initialToken} = await createDataSource(page);

		await goToAnalyticsCloudInstanceSettings(page);

		const disconnectButton = page.getByRole('button', {name: 'Disconnect'});

		if (await disconnectButton.isVisible()) {
			await disconnectFromAnalyticsCloud(page);
		}

		await connectToAnalyticsCloud(page, {token: initialToken});

		await expect(
			page.getByRole('heading', {name: 'Property Assignment'})
		).toBeVisible();

		await goPreviousStep(page);

		// Disconnect the DXP

		await disconnectFromAnalyticsCloud(page);

		// Grab the regenerated token from the disconnected data source

		await gotoLatestLiferayDXPDataSource(page, project);

		const tokenInput = page.locator('#value');

		await expect(tokenInput).toHaveValue(/.{20,}/);

		const reconnectToken = await tokenInput.inputValue();

		// Reconnect the DXP using the regenerated token

		await goToAnalyticsCloudInstanceSettings(page);

		await connectToAnalyticsCloud(page, {token: reconnectToken});

		await expect(
			page.getByRole('heading', {name: 'Property Assignment'})
		).toBeVisible();
	}
);
