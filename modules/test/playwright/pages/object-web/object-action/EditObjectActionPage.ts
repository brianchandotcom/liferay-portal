/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {ViewObjectActionsPage} from './ViewObjectActionsPage';

export class EditObjectActionPage {
	readonly actionBuilderTab: Locator;
	readonly actionLabelInput: Locator;
	readonly iframeLocator: FrameLocator;
	readonly inputNotificationsCombo: Locator;
	readonly inputThenCombo: Locator;
	readonly inputWhenCombo: Locator;
	readonly optionNotification: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.actionBuilderTab = page
			.frameLocator('iframe')
			.getByRole('tab', {name: 'Action Builder'});
		this.actionLabelInput = page
			.frameLocator('iframe')
			.getByPlaceholder('Text to translate');
		this.iframeLocator = page.frameLocator('iframe');
		this.inputNotificationsCombo = page
			.frameLocator('iframe')
			.getByRole('combobox')
			.getByText('Select an Option');
		this.inputThenCombo = page
			.frameLocator('iframe')
			.getByRole('combobox')
			.getByText('Choose an Action');
		this.inputWhenCombo = page
			.frameLocator('iframe')
			.getByRole('combobox')
			.getByText('Choose a Trigger');
		this.optionNotification = page
			.frameLocator('iframe')
			.getByRole('option', {name: 'Notification'});
	}

	async chooseNotificationOption() {
		await this.inputThenCombo.click();
		await this.optionNotification.click();
	}

	async clickInputNotificationsCombo() {
		await this.inputNotificationsCombo.click();
	}

	async openActionBuilderTab() {
		await this.actionBuilderTab.click();
	}

}
