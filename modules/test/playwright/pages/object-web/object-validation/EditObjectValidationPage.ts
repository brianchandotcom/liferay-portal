/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class EditObjectValidationPage {
	readonly addFieldsButton: Locator;
	readonly page: Page;
	readonly saveObjectFieldsButton: Locator;
	readonly saveObjectValidationButton: Locator;
	readonly selectAllFields: Locator;
	readonly uniqueCompositeKeyTab: Locator;

	constructor(page: Page) {
		this.addFieldsButton = page
			.frameLocator('iframe')
			.getByRole('button', {name: 'Add Fields'});
		this.page = page;
		this.saveObjectFieldsButton = page.getByText('Save');
		this.saveObjectValidationButton = page
			.frameLocator('iframe')
			.getByRole('button', {name: 'Save'});
		this.selectAllFields = page.locator(
			'input[name="selectAllObjectFields"]'
		);
		this.uniqueCompositeKeyTab = page
			.frameLocator('iframe')
			.getByRole('tab')
			.filter({hasText: 'Unique Composite Key'});
	}

	async clickSelectAllFields() {
		await this.selectAllFields.click();
		await this.saveObjectFieldsButton.click();
	}
}
