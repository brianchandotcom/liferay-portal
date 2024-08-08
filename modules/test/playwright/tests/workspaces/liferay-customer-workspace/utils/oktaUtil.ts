/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

export async function mockOktaApiSession(page: Page) {
	await page.route(
		'https://login-dev.liferay.com/api/v1/sessions/me',
		async (route) => {
			const json = {id: 'valid-session-id'};

			await route.fulfill({json});
		}
	);
}
