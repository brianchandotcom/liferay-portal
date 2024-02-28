/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {liferayConfig} from '../liferay.config';

export async function getSiteUrl(page: Page) {
	const cookie = (await page.context().cookies()).find(
		(cookie) => cookie.name === 'siteUrl'
	);

	return cookie?.value || '/guest';
}

export async function setSiteUrl(page: Page, siteFriendlyUrlPath: string) {
	await page.context().addCookies([
		{
			name: 'siteUrl',
			url: liferayConfig.environment.baseUrl,
			value: siteFriendlyUrlPath,
		},
	]);
}
