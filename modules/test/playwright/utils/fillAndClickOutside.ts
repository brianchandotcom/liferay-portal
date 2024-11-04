/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import getRandomString from './getRandomString';

export default async function fillAndClickOutside(
	page: Page,
	element: Locator,
	content?: string
) {
	await element.click();

	await element.fill(content || getRandomString());

	await page.locator('body').click();
}
