/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {changeTrackingPagesTest} from '../../fixtures/changeTrackingPagesTest';
import getRandomString from '../../utils/getRandomString';

export const test = mergeTests(apiHelpersTest, changeTrackingPagesTest);

test('LPD-42406 Info alert shows up when response for expired publications is not empty', async ({
	changeTrackingPage,
	page,
}) => {
	await page.route(
		'*/**/o/change-tracking-rest/v1.0/ct-collections?status=3',
		async (route) => {
			const json = {items: [getRandomString()]};

			await route.fulfill({json});
		}
	);

	await changeTrackingPage.goto();

	const alert = page.locator('.alert');

	await expect(alert).toBeVisible();

	await expect(
		page.getByText('There is one or more out of date publications.')
	).toBeVisible();

	await page.getByLabel('Close').click();

	await expect(alert).toBeHidden();

	await page.reload();

	await expect(alert).toBeVisible();
});
