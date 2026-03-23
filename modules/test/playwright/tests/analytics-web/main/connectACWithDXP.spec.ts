/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginAnalyticsCloudTest} from '../../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../../fixtures/loginTest';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../../utils/getRandomString';
import {
	connectToAnalyticsCloud,
	disconnectFromAnalyticsCloud,
	goToAnalyticsCloudInstanceSettings,
} from '../../analytics-settings-web/main/utils/analytics-settings';
import {createDataSource} from '../../osb-faro-web/main/utils/data-source';

export const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	loginAnalyticsCloudTest(),
	loginTest()
);

test('Connect AC token authentication fails with invalid token', async ({
	page,
}) => {
	const token = getRandomString();

	await goToAnalyticsCloudInstanceSettings(page);

	await disconnectFromAnalyticsCloud(page);

	await connectToAnalyticsCloud(page, {token});

	await expect(
		page
			.locator('.alert-danger')
			.getByText(
				'Token is not valid. Please insert a valid Analytics Cloud token.'
			)
	).toBeVisible();
});

test('Connect AC token authentication passes', async ({page}) => {
	const {token} = await createDataSource(page);

	await goToAnalyticsCloudInstanceSettings(page);

	await disconnectFromAnalyticsCloud(page);

	await expect(page.getByPlaceholder('Paste token here.')).toBeEmpty();

	await expect(page.getByRole('button', {name: 'Connect'})).toBeDisabled();

	await page.getByPlaceholder('Paste token here.').fill(token);

	await clickAndExpectToBeVisible({
		autoClick: true,
		target: page.getByRole('button', {name: 'Previous'}),
		trigger: page.getByRole('button', {name: 'Connect'}),
	});

	await expect(
		page.locator('.alert-success', {hasText: 'Connected'})
	).toBeVisible();
});
