/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

export class EditCategoryPage {
	readonly page: Page;

	private readonly descriptionInput: Locator;
	private readonly nameInput: Locator;
	private readonly saveAndAddAnotherButton: Locator;
	private readonly saveButton: Locator;

	constructor(page: Page) {
		this.page = page;

		this.descriptionInput = page.getByTestId('description-input');
		this.nameInput = page.getByTestId('name-input');
		this.saveAndAddAnotherButton = page.getByTestId(
			'save-and-add-another-button'
		);
		this.saveButton = page.getByTestId('save-button');
	}

	async fillDescription(description: string) {
		await this.descriptionInput.waitFor();
		await this.descriptionInput.fill(description);
	}

	async fillName(name: string) {
		await this.nameInput.waitFor();
		await this.nameInput.fill(name);
	}

	async clickSaveAndAddAnother() {
		await this.saveAndAddAnotherButton.waitFor();
		await this.saveAndAddAnotherButton.click();

		await this.page.waitForLoadState();

		await expect(this.page.getByText('Basic Info')).toBeVisible();
	}

	async clickSave() {
		await this.saveButton.waitFor();
		await this.saveButton.click();

		await this.page.waitForLoadState();
	}
}
