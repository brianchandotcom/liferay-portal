/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

interface Action<T> {
	(): Promise<T>;
}

export async function doAndGoBack<T>(page: Page, action: Action<T>) {
	const currentPageUrl = page.url();

	const result = await action();

	await page.goto(currentPageUrl);

	return result;
}
