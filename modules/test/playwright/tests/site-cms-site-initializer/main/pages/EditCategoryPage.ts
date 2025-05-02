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
	private readonly permissionsFormGroup: Locator;
	private readonly permissionsTable: Locator;
	private readonly permissionsTableViewableByDropdown: Locator;

	constructor(page: Page) {
		this.page = page;

		this.editConfirmationModal = page.locator('.modal-content');
		this.descriptionInput = page.getByTestId('description-input');
		this.nameInput = page.getByTestId('name-input');
		this.saveAndAddAnotherButton = page.getByTestId(
			'save-and-add-another-button'
		);
		this.saveButton = page.getByTestId('save-button');
		this.permissionsFormGroup = page.getByTestId(
			'categorization-permissions-form-group'
		);
		this.permissionsTable = this.permissionsFormGroup.getByTestId(
			'categorization-permissions-table'
		);
		this.permissionsTableViewableByDropdown =
			this.permissionsTable.locator('#viewableBy');
	}

	async assertDefaultViewableByPermissions(roleName: string) {
		await this.permissionsTable.waitFor();
		await this.permissionsTableViewableByDropdown.waitFor();

		await expect(this.permissionsTableViewableByDropdown).toHaveValue(
			roleName
		);

		const guestUpdateCheckbox = this.permissionsTable.locator(
			'//td[@data-id="string,Guest-UPDATE"]//input[@type="checkbox"]'
		);
		const guestViewCheckbox = this.permissionsTable.locator(
			'//td[@data-id="string,Guest-VIEW"]//input[@type="checkbox"]'
		);
		const siteMemberViewCheckbox = this.permissionsTable.locator(
			'//td[@data-id="string,SiteMember-VIEW"]//input[@type="checkbox"]'
		);

		if (roleName === 'Guest') {
			await expect(guestUpdateCheckbox).toBeChecked({checked: false});
			await expect(guestViewCheckbox).toBeChecked();
			await expect(siteMemberViewCheckbox).toBeChecked();
		}
		else if (roleName === 'Site Member') {
			await expect(guestUpdateCheckbox).toBeChecked({checked: false});
			await expect(guestViewCheckbox).toBeChecked({checked: false});
			await expect(siteMemberViewCheckbox).toBeChecked();
		}
		else if (roleName === 'Owner') {
			await expect(guestUpdateCheckbox).toBeChecked({checked: false});
			await expect(guestViewCheckbox).toBeChecked({checked: false});
			await expect(siteMemberViewCheckbox).toBeChecked({checked: false});
		}

		await expect(guestUpdateCheckbox).toBeDisabled();
		await expect(guestViewCheckbox).toBeDisabled();
		await expect(siteMemberViewCheckbox).toBeDisabled();
	}

	async clickSave() {
		await this.saveButton.waitFor();
		await this.saveButton.click();

		await this.page.waitForLoadState();
	}

	async clickSaveAndAddAnother() {
		await this.saveAndAddAnotherButton.waitFor();
		await this.saveAndAddAnotherButton.click();

		await this.page.waitForLoadState();

		await expect(this.page.getByText('Basic Info')).toBeVisible();
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

	async setViewableByPermissions(roleName: string) {
		await this.permissionsTable.waitFor();
		await this.permissionsTableViewableByDropdown.waitFor();

		await this.permissionsTableViewableByDropdown.selectOption(roleName);

		await expect(this.permissionsTableViewableByDropdown).toHaveValue(
			roleName
		);
	}

	async tickPermissionCheckbox(
		roleName: string,
		permissionName: string,
		checked: boolean = true
	) {
		await this.permissionsTable.waitFor();

		const checkbox = this.permissionsTable.locator(
			`//td[@data-id="string,${roleName.replace(' ', '')}-${permissionName.toUpperCase()}"]//input[@type="checkbox"]`
		);

		if (checked) {
			await checkbox.check();

			await expect(checkbox).toBeChecked();
		}
		else {
			await checkbox.uncheck();

			await expect(checkbox).toBeChecked({checked: false});
		}
	}
}
