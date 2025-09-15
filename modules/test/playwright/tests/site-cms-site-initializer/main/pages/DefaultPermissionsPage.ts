/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {waitForAlert} from '../../../../utils/waitForAlert';

export class DefaultPermissionsPage {
	readonly page: Page;
	readonly permissionsModal: Locator;
	readonly permissionsModalCancelButton: Locator;
	readonly permissionsModalSaveButton: Locator;

	constructor(page: Page) {
		this.page = page;
		this.permissionsModal = page.locator('.modal-dialog');
		this.permissionsModalCancelButton =
			this.permissionsModal.getByTestId('button-cancel');
		this.permissionsModalSaveButton =
			this.permissionsModal.getByTestId('button-save');
	}

	async checkPermissionAndSave(role: string, action: string) {
		await this.permissionsModal
			.getByTestId(`row-checkbox-${role}_${action}`)
			.check();
		await this.permissionsModalSaveButton.click();

		await waitForAlert(this.page);
	}

	async verifyPermissionIsChecked(role: string, action: string) {
		await expect(this.permissionsModal).toBeVisible();
		await expect(
			this.permissionsModal.getByTestId(`row-checkbox-${role}_${action}`)
		).toBeChecked();
	}
}
