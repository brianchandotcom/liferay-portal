/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

export async function acceptsCookiesBanner(page: Page) {
	const cookiesBannerButton = page.getByRole('button', {name: 'Accept All'});

	if (await cookiesBannerButton.isVisible()) {
		await cookiesBannerButton.click();
	}
}
