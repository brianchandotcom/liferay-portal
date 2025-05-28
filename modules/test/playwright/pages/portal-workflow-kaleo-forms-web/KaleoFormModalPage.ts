/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

export class KaleoFormModalPage {
	readonly addNewFormButton: Locator;
	readonly formNameField: Locator;
	readonly iframe: FrameLocator;
	readonly page: Page;
	readonly saveFormButton: Locator;

	constructor(page: Page) {
		this.iframe = page.frameLocator('iframe[title="Form"]');

		this.addNewFormButton = this.iframe.getByRole('link', {name: 'Add'});
		this.formNameField = this.iframe.getByLabel('Name Required');
		this.saveFormButton = this.iframe.getByRole('button', {
			exact: true,
			name: 'Save',
		});
	}

	async chooseForm(formName: string) {
		await this.iframe.getByRole('link', {name: formName}).click();
	}
}
