/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {GlobalMenuPage} from '../../product-navigation-applications-menu/GlobalMenuPage';

export class CommerceAdminHealthCheckPage {
	readonly globalMenuPage: GlobalMenuPage;
	readonly userRolesButton: Locator;
	readonly pageTitle: Locator;

	constructor(page: Page) {
		this.globalMenuPage = new GlobalMenuPage(page);
		this.userRolesButton = page
			.getByRole('cell', {
				exact: true,
				name: 'User Roles',
			})
			.locator('..')
			.getByRole('button');
		this.pageTitle = page
			.getByTestId('headerTitle')
			.filter({hasText: 'Health Check'});
	}

	async goto() {
		await this.globalMenuPage.goToCommerce('Health Check');
	}
}
