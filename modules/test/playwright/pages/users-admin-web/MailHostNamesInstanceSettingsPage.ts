/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {InstanceSettingsPage} from '../configuration-admin-web/InstanceSettingsPage';

export class MailHostNamesInstanceSettingsPage {
	readonly instanceSettingsPage: InstanceSettingsPage;
	readonly mailHostNamesInput: Locator;
	readonly page: Page;
	readonly saveButton: Locator;

	constructor(page: Page) {
		this.instanceSettingsPage = new InstanceSettingsPage(page);
		this.mailHostNamesInput = page.getByLabel('Enter one mail host name');
		this.page = page;
		this.saveButton = page.getByRole('button', {name: 'Save'});
	}

	async goto() {
		await this.instanceSettingsPage.goToInstanceSetting(
			'Email',
			'Mail Host Names'
		);
	}
}
