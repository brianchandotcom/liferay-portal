/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {InstanceSettingsPage} from '../configuration-admin-web/InstanceSettingsPage';

export class FriendlyUrlInstanceSettingsPage {
	readonly page: Page;
	readonly instanceSettingsPage: InstanceSettingsPage;

	constructor(page: Page) {
		this.page = page;
		this.instanceSettingsPage = new InstanceSettingsPage(page);
	}

	async goto() {
		await this.instanceSettingsPage.goToInstanceSetting(
			'SEO',
			'Friendly URL'
		);
	}

	async modifySeparator(inputName: string, value: string) {
		await this.page.locator('input[name="' + inputName + '"]').click();
		await this.page.locator('input[name="' + inputName + '"]').fill(value);
		await this.page.getByRole('button', {name: 'Save'}).click();
		await this.page.getByRole('button', {name: 'Save'}).waitFor();
	}

	async resetSeparator(label: string) {
		await this.page
			.getByLabel('URL Separator')
			.locator('div')
			.filter({hasText: label})
			.getByLabel('Reset to Default Value')
			.click();
		await this.page.getByRole('button', {name: 'Save'}).click();
		await this.page.getByRole('button', {name: 'Save'}).waitFor();
	}
}
