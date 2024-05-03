import {Locator, expect} from '@playwright/test';

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
export async function expectElementToNotHaveClass(
	element: Locator,
	cssClass: string
) {
	const classList = await element.evaluate(({classList}) =>
		Array.from(classList)
	);

	await expect(classList.includes(cssClass)).toBe(false);
}
