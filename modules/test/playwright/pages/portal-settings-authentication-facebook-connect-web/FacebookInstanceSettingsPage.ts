/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {waitForAlert} from '../../utils/waitForAlert';
import {InstanceSettingsPage} from '../configuration-admin-web/InstanceSettingsPage';

export class FacebookInstanceSettingsPage {
	readonly enabledCheckbox: Locator;
	readonly instanceSettingsPage: InstanceSettingsPage;
	readonly facebookConnectMenuItem: Locator;
	readonly page: Page;
	readonly saveButton: Locator;

	constructor(page: Page) {
		this.enabledCheckbox = page.getByText(' Enabled ');
		this.instanceSettingsPage = new InstanceSettingsPage(page);
		this.facebookConnectMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Facebook Connect',
		});
		this.page = page;
		this.saveButton = page.getByRole('button', {name: 'Save'});
	}

	async clickSetupFacebookConnectionMenuItem() {
		await this.facebookConnectMenuItem.click();
	}

	async disableFacebookConnect() {
		await this.clickSetupFacebookConnectionMenuItem();
		await this.enabledCheckbox.uncheck();
		await this.saveButton.click();
		await waitForAlert(this.page);
	}

	async enableFacebookConnect() {
		await this.clickSetupFacebookConnectionMenuItem();
		await this.enabledCheckbox.check();
		await this.saveButton.click();
		await waitForAlert(this.page);
	}

	async goto() {
		await this.instanceSettingsPage.goToSSO();
	}
}
