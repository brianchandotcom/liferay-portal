/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect} from '@playwright/test';

export async function openProductMenu(page: Page) {
	const button = page.getByLabel('Open Product Menu');

	try {
		await expect(button).toBeVisible();

		await button.click();

		await expect(
			page.getByRole('navigation', {name: 'Product Menu'})
		).toHaveClass(/open/);
	}
	catch (error) {
		return;
	}
}

export async function closeProductMenu(page: Page) {
	const button = page.getByLabel('Close Product Menu');

	try {
		await expect(button).toBeVisible();

		await button.click();

		await expect(
			page.getByRole('navigation', {name: 'Product Menu'})
		).toHaveClass(/closed/);
	}
	catch (error) {
		return;
	}
}
