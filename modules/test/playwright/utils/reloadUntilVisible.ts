/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export async function reloadUntilVisible({
	maxAttempts = 5,
	myLocator,
	page,
}: {
	maxAttempts?: number;
	myLocator: Locator;
	page: Page;
}) {
	let attempts = 0;

	while (attempts < maxAttempts) {
		const element = await myLocator.first();
		if (!(await element.isVisible())) {
			await page.reload();
		}
		else {
			break;
		}
		attempts++;
	}
}
