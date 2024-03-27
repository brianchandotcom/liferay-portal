/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {FrameLocator, Locator, Page} from '@playwright/test';

import {liferayConfig} from '../../../liferay.config';
import {UIElementsPage} from '../../../pages/uielements/UIElementsPage';
import {reloadUntilVisible} from '../../../utils/reloadUntilVisible';

export class StaticPagesPage {
	readonly addButton: Locator;
	readonly addPageIFrame: FrameLocator;
	readonly addTemplatePageButton: Locator;
	readonly blankTypeButton: Locator;
	readonly homePageLink: Locator;
	readonly oneColumnButton: Locator;
	readonly page: Page;
	readonly pageTitleBox: Locator;
	readonly uiElementsPage: UIElementsPage;
	readonly widgetPageButton: Locator;

	constructor(page: Page) {
		this.page = page;
		this.uiElementsPage = new UIElementsPage(page);

		this.addPageIFrame = page.frameLocator('iframe[title="Add Page"]');
		this.addTemplatePageButton = page.getByRole('menuitem', {
			name: 'Add Site Template Page',
		});
		this.addButton = this.addPageIFrame.getByRole('button', {name: 'Add'});
		this.blankTypeButton = page.getByRole('button', {name: 'Blank'});
		this.homePageLink = page.getByLabel('Home', {exact: true});
		this.oneColumnButton = page.getByText('1 Column', {exact: true});
		this.pageTitleBox = this.addPageIFrame.locator(
			'input[id="_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_name"]'
		);
		this.widgetPageButton = page.getByRole('button', {name: 'Widget Page'});
	}

	async addContentPage(pageName: string) {
		await this.blankTypeButton.waitFor({state: 'visible'});
		await this.blankTypeButton.click();
		await this.pageTitleBox.fill(pageName);
		await this.addButton.click();
		await this.page
			.getByTitle(`Go to ${pageName}`)
			.waitFor({state: 'visible'});
	}

	async addWidgetPage(pageName: string) {
		await this.widgetPageButton.waitFor({state: 'visible'});
		await this.widgetPageButton.click();
		await this.pageTitleBox.waitFor({state: 'attached'});
		await this.pageTitleBox.hover();
		await this.pageTitleBox.click();
		await this.pageTitleBox.fill(pageName);
		await this.addButton.waitFor({state: 'attached'});
		await this.addButton.hover();
		await this.addButton.click();
		await this.page
			.getByText('Success:The page was created successfully.')
			.waitFor({state: 'visible'});
		await this.oneColumnButton.click();
		await this.uiElementsPage.saveButton.click();
		await this.page
			.getByText('Success:The page was updated successfully.')
			.waitFor({state: 'visible'});
	}
	async checkIfWebContentAddedToHome(
		siteName: string,
		webContentBody: string
	) {
		await this.page.goto(
			liferayConfig.environment.baseUrl + `/group/${siteName}`
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
}
