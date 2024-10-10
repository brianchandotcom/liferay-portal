/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {waitForAlert} from '../../utils/waitForAlert';
import {SystemSettingsPage} from '../configuration-admin-web/SystemSettingsPage';

export class OpenIdSystemSettingsPage {
	readonly page: Page;
	readonly systemSettingsPage: SystemSettingsPage;
	readonly openIdConnectMenuItem: Locator;
	readonly enabledCheckbox: Locator;
	readonly saveButton: Locator;

	constructor(page: Page) {
		this.page = page;
		this.systemSettingsPage = new SystemSettingsPage(page);
		this.openIdConnectMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'OpenID Connect',
		});
		this.enabledCheckbox = page.getByText(' Enabled ');
		this.saveButton = page.getByRole('button', {name: /save|update/i});
	}

	async goTo() {
		this.systemSettingsPage.goToSystemSetting('SSO', 'OpenID Connect');
	}

	async disableOpenIDConnect() {
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
		await waitForAlert(this.page);
	}

	async enableOpenIDConnect() {
		await this.enabledCheckbox.check();
		await this.saveButton.click();
		await waitForAlert(this.page);
	}
}
