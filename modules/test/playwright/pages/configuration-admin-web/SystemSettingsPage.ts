/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

export class SystemSettingsPage {
	private applicationsMenuPage;
	readonly page: Page;

	constructor(page: Page) {
		this.page = page;
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
	}

	async goto() {
		await this.applicationsMenuPage.goToSystemSettings();
	}

	async goToSystemSetting(categoryKey: string, configurationName: string) {
		await this.goto();
		await this.page
			.getByRole('link', {
				exact: true,
				name: categoryKey,
			})
			.click();
		await this.page
			.getByRole('menuitem', {
				exact: true,
				name: configurationName,
			})
			.first()
			.click();
	}
}
