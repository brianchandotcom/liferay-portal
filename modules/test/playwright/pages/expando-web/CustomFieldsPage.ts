/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {GlobalMenuPage} from '../product-navigation-applications-menu/GlobalMenuPage';

export class CustomFieldsPage {
	readonly globalMenuPage: GlobalMenuPage;
	readonly page: Page;

	constructor(page: Page) {
		this.globalMenuPage = new GlobalMenuPage(page);
		this.page = page;
	}

	async goto() {
		await this.globalMenuPage.goToControlPanel('Custom Fields');
	}
}
