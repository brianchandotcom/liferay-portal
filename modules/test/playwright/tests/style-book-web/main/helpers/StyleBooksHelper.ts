/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../../../utils/clickAndExpectToBeVisible';

export class StyleBooksHelper {
	constructor(private readonly page: Page) {}

	async assertTokenInputValue({
		label,
		section,
		value,
	}: {
		label: string;
		section: string;
		value: string;
	}) {
		const input = this.page
			.locator('.panel')
			.filter({hasText: section})
			.locator('.form-group')
			.filter({hasText: label})
			.locator('input');

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: input,
			trigger: this.page.getByRole('button', {name: section}),
		});

		await expect(input).toHaveValue(value);
	}
}
