/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

export class AccountCategorySelectorPage {
	readonly doneButton: Locator;
	readonly categoryCheckbox: (
		categoryName: string,
		vocabularyName: string
	) => Locator;
	readonly frame: (vocabularyName: string) => FrameLocator;
	readonly page: Page;

	constructor(page: Page) {
		this.doneButton = page.getByRole('button', {name: 'Done'});
		this.categoryCheckbox = (categoryName, vocabularyName) =>
			this.frame(vocabularyName)
				.locator('li')
				.filter({hasText: categoryName})
				.getByRole('checkbox');
		this.frame = (vocabularyName) =>
			page.frameLocator(`iframe[title="Select ${vocabularyName}"]`);
		this.page = page;
	}

	async selectCategories(categoryNames: Array<string>, vocabularyName) {
		for (const categoryName of categoryNames) {
			await this.categoryCheckbox(categoryName, vocabularyName).check();
		}

		await this.doneButton.click();
	}
}
