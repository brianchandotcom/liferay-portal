/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, test} from '@playwright/test';

import {liferayConfig} from '../../liferay.config';

test('LPD-4254 Checking what is the first page loaded if default.admin.password is blank and passwords.default.policy.change.required is true', async ({
	page,
}) => {
	await page.goto(liferayConfig.environment.baseUrl);

	await expect(page.getByRole('heading', {name: 'Set Password'})).toBeVisible(
		{
			timeout: 10 * 1000,
		}
	);
});
