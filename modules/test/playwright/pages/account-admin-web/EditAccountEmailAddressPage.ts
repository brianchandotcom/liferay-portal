/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class EditAccountEmailAddressPage {
	readonly addressInput: Locator;
	readonly page: Page;
	readonly saveButton: Locator;

	constructor(page: Page) {
		this.addressInput = page.getByRole('textbox', {name: 'Address'});
		this.page = page;
		this.saveButton = page.getByRole('button', {name: 'Save'});
	}

	async updateEmailAddress(addressInput: string) {
		await this.addressInput.fill(addressInput);
		await this.saveButton.click();
	}
}
