/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {waitForAlert} from '../../utils/waitForAlert';

export class GeneralPage {
	readonly defaultLogoutPageField: Locator;
	readonly page: Page;
	readonly saveButton: Locator;

	constructor(page: Page) {
		this.defaultLogoutPageField = page.getByLabel('Default Logout Page');
		this.page = page;
		this.saveButton = page
			.getByRole('button', {name: 'Save'})
			.or(page.getByRole('button', {name: 'Update'}));
	}

	async editDefaultLogoutPage(defaultLogoutPage: string) {
		await this.defaultLogoutPageField.fill(defaultLogoutPage);

		await this.saveButton.click();
		await waitForAlert(this.page);
	}
}
