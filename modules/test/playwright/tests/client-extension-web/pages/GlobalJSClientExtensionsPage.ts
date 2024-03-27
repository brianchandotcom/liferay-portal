/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import { ClientExtensionsPage } from './ClientExtensionsPage';

export class GlobalJSClientExtensionsPage {
	readonly clientExtensionsPage: ClientExtensionsPage;
	readonly editClientExtensionSubmitButton: Locator;
	readonly nameInput: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.clientExtensionsPage = new ClientExtensionsPage(page);
		this.editClientExtensionSubmitButton = page.getByRole('button', {
			name: 'Publish',
		});
		this.nameInput = page.getByLabel('Name');
		this.page = page;
	}

	async goto() {
		await this.clientExtensionsPage.goto();

		await this.clientExtensionsPage.newClientExtensionButton.click();

		await this.clientExtensionsPage.addJSMenuItem.click();
	}
	
}
