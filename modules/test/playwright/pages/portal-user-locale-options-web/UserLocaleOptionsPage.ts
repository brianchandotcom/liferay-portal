/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {waitForAlert} from '../../utils/waitForAlert';
export class UserLocaleOptionsPage {
	readonly languageChangeLink: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.languageChangeLink = page
			.getByRole('link', {
				name: 'Display the page in',
			})
			.first();
		this.page = page;
	}

	async changeLanguageWithAlert() {
		await waitForAlert(this.page, 'This page is displayed in', {
			autoClose: false,
			type: 'info',
		});

		await this.languageChangeLink.click();

		await this.page.waitForLoadState();
	}
}
