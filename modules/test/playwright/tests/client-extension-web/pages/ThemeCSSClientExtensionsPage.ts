/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';
import * as path from 'path';

import {ClientExtensionsPage} from './ClientExtensionsPage';

export class ThemeCSSClientExtensionsPage {
	readonly clientExtensionsPage: ClientExtensionsPage;
	readonly editClientExtensionSubmitButton: Locator;
	readonly page: Page;
	readonly themeCSSFrontendTokenDefinitionSelectFileButton: Locator;

	constructor(page: Page) {
		this.clientExtensionsPage = new ClientExtensionsPage(page);
		this.editClientExtensionSubmitButton = page.getByRole('button', {
			name: 'Publish',
		});
		this.page = page;
		this.themeCSSFrontendTokenDefinitionSelectFileButton = page
			.getByRole('button', {exact: true, name: 'Select File'})
			.or(page.getByRole('button', {name: 'Replace File'}));
	}

	async goto() {
		await this.clientExtensionsPage.goto();

		await this.clientExtensionsPage.newClientExtensionButton.click();

		await this.clientExtensionsPage.addThemeCSSMenuItem.click();
	}

	async uploadFrontendTokenDefinitionFile(dirname: string, fileName: string) {
		const fileChooserPromise = this.page.waitForEvent('filechooser');

		await this.themeCSSFrontendTokenDefinitionSelectFileButton.click();

		const fileChooser = await fileChooserPromise;

		await fileChooser.setFiles(
			path.join(dirname, '/dependencies/' + fileName)
		);
	}
}
