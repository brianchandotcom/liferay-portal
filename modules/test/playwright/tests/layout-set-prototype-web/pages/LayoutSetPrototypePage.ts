/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {liferayConfig} from '../../../liferay.config';
import {reloadUntilVisible} from '../../../utils/reloadUntilVisible';

export class LayoutSetPrototypePage {
	readonly addLink: Locator;
	readonly addTemplatePageButton: Locator;
	readonly homePageLink: Locator;
	readonly nameBox: Locator;
	readonly page: Page;
	readonly saveButton: Locator;

	constructor(page: Page) {
		this.page = page;

		this.addLink = page.getByRole('link', {name: 'Add'});
		this.addTemplatePageButton = page.getByRole('menuitem', {
			name: 'Add Site Template Page',
		});
		this.homePageLink = page.getByLabel('Home', {exact: true});
		this.nameBox = page.getByPlaceholder('Name');
		this.saveButton = page.getByRole('button', {name: 'Save'});
	}

	async addSiteTemplate(templateName: string) {
		await this.addLink.click();
		await this.nameBox.click();
		await this.nameBox.fill(templateName);
		await this.saveButton.click();
	}

	async checkIfWebContentAddedToHome(
		siteName: string,
		webContentBody: string
	) {
		await this.page.goto(
			liferayConfig.environment.baseUrl + `/web/${siteName}`
		);
		const myLocator = this.page.getByRole('link', {
			name: `Go to ${siteName}`,
		});
		await reloadUntilVisible({
			myLocator,
			page: this.page,
		});
		await this.page
			.getByText(webContentBody)
			.waitFor({state: 'visible', timeout: 3000});
		await this.page.getByText(webContentBody).isVisible();
	}

	async checkIfWebContentAdded(
		siteName: string,
		webContentName: string,
		webContentBody: string
	) {
		await this.page.goto(
			liferayConfig.environment.baseUrl + `/group/${siteName}`
		);
		const myLocator = this.page.getByText(webContentName);
		await reloadUntilVisible({
			myLocator,
			page: this.page,
		});
		await this.page
			.getByRole('menuitem', {name: webContentName})
			.waitFor({state: 'visible'});
		await this.page.getByRole('menuitem', {name: webContentName}).click();
		await this.page.getByText(webContentBody).waitFor({state: 'visible'});
		await this.page.getByText(webContentBody).isVisible();
	}

	async getSiteTemplateUrl(templateName: string) {
		return await this.page.getByText(templateName).getAttribute('href');
	}
}
