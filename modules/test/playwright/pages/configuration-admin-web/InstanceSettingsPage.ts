/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

export class InstanceSettingsPage {
	readonly page: Page;
	readonly applicationsMenuPage: ApplicationsMenuPage;

	constructor(page: Page) {
		this.page = page;
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
	}

	async goto() {
		await this.applicationsMenuPage.goToInstanceSettings();
	}

	async goToInstanceSetting(categoryKey: string, configurationName: string) {
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
			.click();
	}
}
