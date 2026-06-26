/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {isolatedChannelTest} from '../../../fixtures/isolatedChannelTest';
import {loginAnalyticsCloudTest} from '../../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../../fixtures/loginTest';
import {JSONWebServicesOSBFaroApiHelper} from '../../../helpers/json-web-services/JSONWebServicesOSBFaroApiHelper';
import {ACPage, navigateToACSettingsViaURL} from './utils/navigation';

export const test = mergeTests(
	apiHelpersTest,
	isolatedChannelTest,
	loginAnalyticsCloudTest(),
	loginTest()
);

test('Generate a new token after expired', async ({
	apiHelpers,
	page,
	project,
}) => {
	await test.step('Generate a new access token with instant expiration', async () => {
		const jSONWebServicesOSBFaroApiHelper =
			new JSONWebServicesOSBFaroApiHelper(apiHelpers);

		const data = await jSONWebServicesOSBFaroApiHelper.fetchApiToken(
			project.groupId,
			1
		);

		const tokenId = data.token.slice(-4);

		await page.waitForTimeout(1000);

		await navigateToACSettingsViaURL({
			acPage: ACPage.apisTokensPage,
			page,
			projectID: project.groupId,
		});

		await test.step('Check if the token was expired', async () => {
			const rowToken = page.getByTestId(`row-token-${tokenId}`);

			expect(await rowToken.textContent()).toBe(
				`Token Ending In${tokenId}Expired`
			);
		});

		await test.step('Check if it is possible to create a new token', async () => {
			const generateNewTokenButton = page.getByText('Generate Token');

			await expect(generateNewTokenButton).toBeVisible();

			await generateNewTokenButton.click();

			await expect(
				page.getByText('New token was generated.')
			).toBeVisible();
		});
	});
});
