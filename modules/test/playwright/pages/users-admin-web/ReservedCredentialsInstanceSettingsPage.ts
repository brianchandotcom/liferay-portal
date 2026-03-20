/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {waitForAlert} from '../../utils/waitForAlert';
import {InstanceSettingsPage} from '../configuration-admin-web/InstanceSettingsPage';

export class ReservedCredentialsInstanceSettingsPage {
	readonly emailAddressesInput: Locator;
	readonly instanceSettingsPage: InstanceSettingsPage;
	readonly page: Page;
	readonly saveButton: Locator;

	constructor(page: Page) {
		this.emailAddressesInput = page.locator(
			'[id$="admin-reserved-email-addresses"]'
		);
		this.instanceSettingsPage = new InstanceSettingsPage(page);
		this.page = page;
		this.saveButton = page.getByRole('button', {name: 'Save'});
	}

	async goto() {
		await this.instanceSettingsPage.goToInstanceSetting(
			'User Authentication',
			'Reserved Credentials'
		);
	}

	async resetFields() {
		await this.goto();

		await this.emailAddressesInput.clear();
		await this.saveButton.click();

		await waitForAlert(this.page);
	}
}
