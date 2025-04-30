/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {PORTLET_URLS} from '../../../../utils/portletUrls';

export class EditCategoryPage {
	readonly page: Page;
	readonly saveButton: Locator;

	private readonly editConfirmationModal: Locator;
	private readonly descriptionInput: Locator;
	private readonly nameInput: Locator;
	private readonly saveAndAddAnotherButton: Locator;

	constructor(page: Page) {
		this.page = page;

		this.editConfirmationModal = page.locator('.modal-content');
		this.descriptionInput = page.getByTestId('description-input');
		this.nameInput = page.getByTestId('name-input');
		this.saveAndAddAnotherButton = page.getByTestId(
			'save-and-add-another-button'
		);
		this.saveButton = page.getByTestId('save-button');
	}

	async gotoCreateCategory(vocabularyId: number | string) {
		await this.page.goto(
			PORTLET_URLS.cmsNewCategory + '?vocabularyId=' + vocabularyId
		);

		await expect(this.page.getByText('Basic Info')).toBeVisible();
	}

	async gotoEditCategory(categoryId: number | string) {
		await this.page.goto(
			PORTLET_URLS.cmsEditCategory + '?categoryId=' + categoryId
		);

		await expect(this.page.getByText('Basic Info')).toBeVisible();
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

	async handleEditConfirmationModal(clickSave: boolean) {
		await expect(this.editConfirmationModal).toBeVisible();

		clickSave
			? await this.editConfirmationModal
					.getByRole('button', {name: 'Save'})
					.click()
			: await this.editConfirmationModal
					.getByRole('button', {name: 'Cancel'})
					.click();
	}
}
