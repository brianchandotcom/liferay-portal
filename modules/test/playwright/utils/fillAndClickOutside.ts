/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getRandomString from './getRandomString';

export default async function fillAndClickOutside(page, element) {
	const text = getRandomString();

	await element.click();
	await element.fill(text);
	await page.locator('body').click();
}
