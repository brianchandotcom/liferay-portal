/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class NewTaxCategoryPage {
	readonly descriptionInput: Locator;
	readonly externalReferenceCodeInput: Locator;
	readonly nameInput: Locator;
	readonly page: Page;
	readonly saveButton: Locator;

	constructor(page: Page) {
		this.descriptionInput = page.getByLabel('Description', {exact: true});
		this.externalReferenceCodeInput = page.getByLabel(
			'External Reference Code',
			{exact: true}
		);
		this.nameInput = page.getByLabel('Name', {exact: true});
		this.page = page;
		this.saveButton = page.getByRole('button', {name: 'Save'});
	}
}
