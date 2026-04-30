/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class EditServiceAccessPolicyPage {
	readonly addRowButton: Locator;
	readonly defaultButton: Locator;
	readonly enabledButton: Locator;
	readonly nameField: Locator;
	readonly page: Page;
	readonly saveButton: Locator;
	readonly successMessage: Locator;
	readonly titleField: Locator;

	constructor(page: Page) {
		this.addRowButton = page.locator('button.add-row').first();
		this.defaultButton = page.getByLabel('Default');
		this.enabledButton = page.getByLabel('Enabled');
		this.nameField = page.getByRole('textbox', {name: 'Name Required'});
		this.page = page;
		this.saveButton = page.getByRole('button').filter({
			hasText: 'Save',
		});
		this.successMessage = page.getByText(
			'Your request completed successfully'
		);
		this.titleField = page.getByRole('textbox', {name: 'Title Required'});
	}

	methodNameField(rowIndex = 0): Locator {
		return this.page
			.locator('.lfr-form-row')
			.nth(rowIndex)
			.locator('.action-method-name');
	}

	serviceClassField(rowIndex = 0): Locator {
		return this.page
			.locator('.lfr-form-row')
			.nth(rowIndex)
			.locator('.service-class-name');
	}
}
