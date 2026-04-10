/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

export async function enableGlobalMenuFeatureFlag(page: Page) {
	await page.evaluate(() =>
		Liferay.Util.fetch('/o/com-liferay-feature-flag-web/set-enabled', {
			body: Liferay.Util.objectToFormData({
				companyId: Liferay.ThemeDisplay.getCompanyId(),
				enabled: true,
				key: 'LPD-36105',
				system: false,
			}),
			method: 'POST',
		})
	);

	await page.reload();
}
