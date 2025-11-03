/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

interface Parameters {
	beforeReload?: () => Promise<void>;
	condition: (element: Locator) => Promise<boolean>;

	maxAttempts?: number;
	myLocator: Locator;
	page: Page;
}

export async function reloadUntil({
	beforeReload,
	condition,
	maxAttempts = 5,
	myLocator,
	page,
}: Parameters) {
	let attempts = 0;

	while (attempts < maxAttempts) {
		await beforeReload?.();

		const element = myLocator.first();

		if (!(await condition(element))) {
			await page.reload();
		}
		else {
			break;
		}
		attempts++;
	}
}
