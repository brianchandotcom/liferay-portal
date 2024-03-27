/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {UIElementsPage} from '../uielements/UIElementsPage';

export class ContentPage {
	readonly fragmentsAndWidgetsPanel: Locator;
	readonly page: Page;
	readonly pageEditor: Locator;
	readonly panelSearch: Locator;
	readonly uiElementsPage: UIElementsPage;
	readonly webContentDisplayDraggable: Locator;

	constructor(page: Page) {
		this.page = page;
		this.uiElementsPage = new UIElementsPage(page);

		this.fragmentsAndWidgetsPanel = page
			.getByLabel('Fragments and Widgets Panel')
			.getByRole('banner');
		this.pageEditor = page.locator('#page-editor div').nth(1);
		this.panelSearch = page.getByPlaceholder('Search...');
		this.webContentDisplayDraggable = page
			.getByText('Web Content Display')
			.first();
	}

	async addWebContentDisplayToPage() {
		await this.fragmentsAndWidgetsPanel.waitFor({state: 'visible'});
		await this.panelSearch.click();
		await this.panelSearch.fill('web content');
		await this.webContentDisplayDraggable.waitFor({state: 'visible'});
		await this.webContentDisplayDraggable.dragTo(this.pageEditor);
		await this.uiElementsPage.pageCreatedAlert.waitFor({state: 'hidden'});
	}
}
