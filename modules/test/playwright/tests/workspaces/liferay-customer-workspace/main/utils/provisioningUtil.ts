/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

export async function mockProvisioningApiAssignUser(page: Page) {
	await page.route(
		'https://*/o/provisioning-rest/v1.0/accounts/*/contacts/by-email-address/*/roles*',
		async (route) => {
			await route.fulfill({
				body: '',
				status: 204,
			});
		}
	);
}
