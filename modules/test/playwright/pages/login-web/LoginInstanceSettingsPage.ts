/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {waitForAlert} from '../../utils/waitForAlert';
import {InstanceSettingsPage} from '../configuration-admin-web/InstanceSettingsPage';

export class LoginInstanceSettingsPage {
	readonly instanceSettingsPage: InstanceSettingsPage;
	readonly page: Page;
	readonly saveButton: Locator;

	constructor(page: Page) {
		this.instanceSettingsPage = new InstanceSettingsPage(page);
		this.page = page;
		this.saveButton = page.getByRole('button', {name: /save|update/i});
	}

	async goto() {
		await this.instanceSettingsPage.goToInstanceSetting('Login', 'Login');
	}

	async enableLoginPrompt() {
		await this.page.getByRole('menuitem', {name: 'Login'}).waitFor();
		await this.page.getByLabel('Prompt Enabled').check();
		await this.saveButton.click();
		await waitForAlert(this.page);
	}

	async resetLoginPrompt() {
		await this.page
			.getByRole('button', {
				name: 'Actions',
			})
			.click();
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('link', {
				name: 'Reset Default Values',
			}),
			trigger: this.page.getByRole('button', {
				name: 'Actions',
			}),
		});
	}
}
