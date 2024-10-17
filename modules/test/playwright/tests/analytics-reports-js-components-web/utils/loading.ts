import {Page} from '@playwright/test';

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export async function waitForLoading(page: Page) {
	const loadingAnimations = page.locator(
		'.analytics-reports .loading-animation'
	);
	const count = await loadingAnimations.count();

	if (count > 0) {
		for (let i = 0; i < count; i++) {
			const loadingAnimation = loadingAnimations.nth(i);

			await loadingAnimation.waitFor({state: 'hidden'});
		}
	}
}
