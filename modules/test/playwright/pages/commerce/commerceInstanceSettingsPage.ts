/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

export class CommerceInstanceSettingsPage {
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly catalogLink: Locator;
	readonly page: Page;
	readonly showUnselectableOptionsCheckbox: Locator;
	readonly submitConfigurationButton: Locator;

	constructor(page: Page) {
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.catalogLink = page.getByRole('link', {
			exact: true,
			name: 'Catalog',
		});
		this.page = page;
		this.showUnselectableOptionsCheckbox = page.getByLabel(
			'Show Unselectable Options'
		);
		this.submitConfigurationButton = page.getByTestId(
			'submitConfiguration'
		);
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

	async toggleShowUnselectableOptions(check: boolean) {
		await this.goto();
		await this.catalogLink.click();

		if (check) {
			await this.showUnselectableOptionsCheckbox.check();
		}
		else {
			await this.showUnselectableOptionsCheckbox.uncheck();
		}

		await this.submitConfigurationButton.click();
	}
}
