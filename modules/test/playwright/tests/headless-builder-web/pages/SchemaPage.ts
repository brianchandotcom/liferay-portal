/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class SchemaPage {
	readonly page: Page;
	readonly propertiesTab: Locator;

	constructor(page: Page) {
		this.page = page;
		this.propertiesTab = page.getByRole('tab', {name: 'Properties'});
	}

	async goTo(schemaName: string) {
		await this.page.getByRole('link', {name: schemaName}).click();
	}

	async goToPropertiesTab() {
		await this.propertiesTab.click();
	}
}
