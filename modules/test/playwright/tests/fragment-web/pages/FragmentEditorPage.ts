/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class FragmentEditorPage {
	readonly page: Page;

	readonly successMessage: Locator;

	constructor(page: Page) {
		this.page = page;

		this.successMessage = this.page.getByText(
			'Success:Your request completed successfully.'
		);
	}

	async addConfiguration(configuration: string) {

		// Go to Configuration Tab

		await this.page.getByRole('tab', {name: 'Configuration'}).click();
		await this.page.getByText('Add the JSON configuration').waitFor();

		// Delete current configuration

		const codeMirror = await this.page.locator('.CodeMirror-scroll').last();
		await codeMirror.click();

		await this.page.keyboard.press('Control+KeyA');
		await this.page.keyboard.press('Backspace');

		await this.page.getByText('Changes Saved').waitFor();

		// Fill with new configuration

		await this.page.keyboard.type(configuration);
		await this.page.getByText('Changes Saved').waitFor();

		// Publish

		await this.page.getByRole('button', {name: 'Publish'}).click();
		await this.successMessage.waitFor();
	}
}
