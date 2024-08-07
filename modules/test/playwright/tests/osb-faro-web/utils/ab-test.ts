/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

export async function clickOnReviewButton(page: Page) {
	const reviewTagA = await page.locator(
		'xpath=//a[contains(text(),"Review")]'
	);

	await page.goto(await reviewTagA.getAttribute('href'));
}
