/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {EActions} from '../../helpers/ServerAdministrationHelper';
import {UIElementsPage} from '../uielements/UIElementsPage';

export class ServerAdministrationPage {
	readonly page: Page;
	readonly uiElementsPage;

	private readonly executeButton: Locator;
	private readonly scriptBox: Locator;
	private readonly scriptLink: Locator;

	constructor(page: Page) {
		this.uiElementsPage = new UIElementsPage(page);
		this.page = page;

		this.executeButton = page.getByRole('button', {name: 'Execute'});
		this.scriptBox = page.getByLabel('Script', {exact: true});
		this.scriptLink = page.getByRole('link', {name: 'Script'});
	}

	async executeAction(action: EActions) {
		await this.page
			.getByText(action)
			.getByRole('button', {name: 'Execute'})
			.click();
		await this.uiElementsPage.anySuccessAlert.waitFor({state: 'visible'});
		await this.uiElementsPage.anySuccessAlert.waitFor({state: 'hidden'});
	}

	async executeScript(script: string) {
		await this.scriptLink.click();
		await this.scriptBox.click();
		await this.scriptBox.press('Control+a');
		await this.scriptBox.fill(script);
		await this.executeButton.click();
		await this.uiElementsPage.anySuccessAlert.waitFor({state: 'visible'});
		await this.uiElementsPage.anySuccessAlert.waitFor({state: 'hidden'});
	}
}
