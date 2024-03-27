/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {Locator, Page} from '@playwright/test';

export class LayoutSetPrototypePage {
	readonly addLink: Locator;
	readonly nameBox: Locator;
	readonly page: Page;
	readonly saveButton: Locator;

	constructor(page: Page) {
		this.page = page;
		this.addLink = page.getByRole('link', {name: 'Add'});
		this.nameBox = page.getByPlaceholder('Name');
		this.saveButton = page.getByRole('button', {name: 'Save'});
	}

	async addSiteTemplate(templateName: string) {
		await this.addLink.click();
		await this.nameBox.click();
		await this.nameBox.fill(templateName);
		await this.saveButton.click();
	}

	async getSiteTemplateUrl(templateName: string) {
		return await this.page.getByText(templateName).getAttribute('href');
	}
}
