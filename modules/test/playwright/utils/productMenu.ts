/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect} from '@playwright/test';

export async function openProductMenu(page: Page) {
	const productMenu = page.getByLabel('Product Menu', {exact: true});

	const isOpen = await productMenu.evaluate((element) =>
		element.classList.contains('open')
	);

	if (!isOpen) {
		const button = page.getByLabel('Open Product Menu');

		await expect(async () => {
			await button.click();

			await expect(productMenu).toHaveClass(/open/, {timeout: 2000});
		}).toPass();
	}
}

export async function closeProductMenu(page: Page) {
	const productMenu = page.getByLabel('Product Menu', {exact: true});

	const isClosed = await productMenu.evaluate((element) =>
		element.classList.contains('closed')
	);

	if (!isClosed) {
		const button = page.getByLabel('Close Product Menu');

		await expect(async () => {
			await button.click();

			await expect(productMenu).toHaveClass(/closed/, {timeout: 2000});
		}).toPass();
	}
}
