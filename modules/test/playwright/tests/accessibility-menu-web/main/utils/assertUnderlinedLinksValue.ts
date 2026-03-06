/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect} from '@playwright/test';

import {assertBodyClass} from './assertBodyClass';

export async function assertUnderlinedLinksValue(page: Page, enabled: boolean) {
	await assertBodyClass(page, enabled, /c-prefers-link-underline/);

	const heading = page.getByRole('link', {name: 'Liferay DXP Site'});

	if (enabled) {
		await expect(heading).toHaveCSS('text-decoration-line', 'underline');
	}
	else {
		await expect(heading).not.toHaveCSS(
			'text-decoration-line',
			'underline'
		);
	}
}
