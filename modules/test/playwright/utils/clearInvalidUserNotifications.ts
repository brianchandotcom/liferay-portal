/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect} from '@playwright/test';

export default async function (page: Page) {
	await page.getByTitle('User Profile Menu').click();

	await page.getByRole('menuitem', {name: 'Notifications'}).click();

	await page.waitForLoadState('networkidle');

	await expect(
		page.getByRole('heading', {name: 'Notifications'})
	).toBeVisible();

	while (
		(await page.getByText('Notification no longer applies.').count()) > 0
	) {
		await page.reload();

		if (
			(await page
				.getByText('Notification no longer applies.')
				.count()) === 0
		) {
			break;
		}
	}
}
