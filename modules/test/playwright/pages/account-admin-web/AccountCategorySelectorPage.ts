/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

export class AccountCategorySelectorPage {
	readonly doneButton: Locator;
	readonly categoryCheckbox: (
		categoryName: string,
		vocabularyName: string
	) => Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.doneButton = page.getByRole('button', {name: 'Done'});
		this.categoryCheckbox = (categoryName, vocabularyName) =>
			page
				.getByRole('dialog', {name: `Select ${vocabularyName}`})
				.locator('li')
				.filter({hasText: categoryName})
				.getByRole('checkbox');
		this.page = page;
	}

	async selectCategories(
		categoryNames: Array<string>,
		vocabularyName: string
	) {
		for (const categoryName of categoryNames) {
			await expect(async () => {
				await this.categoryCheckbox(categoryName, vocabularyName).check(
					{timeout: 2000}
				);
			}).toPass({timeout: 10000});
		}

		await this.doneButton.click();
	}
}
