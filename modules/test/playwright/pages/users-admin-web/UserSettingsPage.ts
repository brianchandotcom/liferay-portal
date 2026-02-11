/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {waitForAlert} from '../../utils/waitForAlert';
import {InstanceSettingsPage} from '../configuration-admin-web/InstanceSettingsPage';

export class UserSettingsPage {
	readonly instanceSettingsPage: InstanceSettingsPage;
	readonly maxFileSizeInput: Locator;
	readonly page: Page;
	readonly saveButton: Locator;

	constructor(page: Page) {
		this.instanceSettingsPage = new InstanceSettingsPage(page);
		this.maxFileSizeInput = page.getByLabel('Maximum File Size');
		this.page = page;
		this.saveButton = this.page.getByRole('button', {
			name: /^(Save|Update)$/,
		});
	}

	async goToUserSettings() {
		await this.instanceSettingsPage.goToInstanceSetting(
			'Users',
			'User Images'
		);
	}

	async updateUserImageMaxFileSize(maxFileSize: string | number) {
		await this.goToUserSettings();

		await this.maxFileSizeInput.fill(String(maxFileSize));
		await this.saveButton.click();

		await waitForAlert(this.page);
	}
}
