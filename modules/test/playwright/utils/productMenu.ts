/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

export async function openProductMenu(page: Page) {
	const button = page.getByLabel('Open Product Menu');

	if (await button.isVisible()) {
		await button.click();

		await button.waitFor({state: 'hidden'});
	}
}

export async function closeProductMenu(page: Page) {
	const button = page.getByLabel('Close Product Menu');

	if (await button.isVisible()) {
		await button.click();

		await button.waitFor({state: 'hidden'});
	}
}
