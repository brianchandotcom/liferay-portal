/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class EditServiceAccessPolicyPage {
	readonly enabledButton: Locator;
	readonly defaultButton: Locator;
	readonly methodNameField: Locator;
	readonly nameField: Locator;
	readonly saveButton: Locator;
	readonly serviceClassField: Locator;
	readonly successMessage: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.enabledButton = page.getByLabel('Enabled');
		this.defaultButton = page.getByLabel('Default');
		this.methodNameField = page.getByLabel('Default');
		this.nameField = page.getByLabel('Name');
		this.saveButton = page.getByRole('button').filter({
			hasText: 'Save',
		});
		this.serviceClassField = page.getByLabel('Default');
		this.successMessage = page.getByText(
			'Your request completed successfully'
		);
		this.page = page;
	}
}
