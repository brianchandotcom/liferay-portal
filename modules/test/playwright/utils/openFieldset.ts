/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Page, expect} from '@playwright/test';

export async function openFieldset(page: Page | FrameLocator, name: string) {
	const fieldset = page.getByRole('group', {
		name,
	});

	if (await fieldset.locator('.panel-body').isHidden()) {
		await fieldset.getByRole('button', {name}).click();
	}

	await expect(fieldset.locator('.panel-body')).toBeVisible();
}
