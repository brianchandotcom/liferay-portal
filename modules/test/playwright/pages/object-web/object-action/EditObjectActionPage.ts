/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {ViewObjectActionsPage} from './ViewObjectActionsPage';

export class EditObjectActionPage {
	readonly actionBuilderTab: Locator;
	readonly actionLabelInput: Locator;
	readonly checkbox: Locator;
	readonly iframeLocator: FrameLocator;
	readonly inputNotificationsCombo: Locator;
	readonly inputThenCombo: Locator;
	readonly inputWhenCombo: Locator;
	readonly optionNotification: Locator;
	readonly page: Page;
	readonly viewObjectActionsPage: ViewObjectActionsPage;
	readonly saveButton: Locator;
	readonly userPreferredLanguage: Locator;

	constructor(page: Page) {
		this.actionBuilderTab = page
			.frameLocator('iframe')
			.getByRole('tab', {name: 'Action Builder'});
		this.actionLabelInput = page
			.frameLocator('iframe')
			.getByPlaceholder('Text to translate');
		this.checkbox = page.frameLocator('iframe').getByRole('checkbox');
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
		this.saveButton = page
			.frameLocator('iframe')
			.getByRole('button', {name: 'Save'});
		this.userPreferredLanguage = page
			.frameLocator('iframe')
			.getByText(
				`Send email notifications in the guest user's preferred language.`
			);
		this.viewObjectActionsPage = new ViewObjectActionsPage(page);
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

	async addNewAction(
		thenOption: string,
		whenOption: string,
		notificationTemplateName?: string
	) {
		await this.viewObjectActionsPage.openObjectActionSidePanel();

		await this.actionLabelInput.fill(whenOption);

		await this.actionBuilderTab.click();

		await this.inputWhenCombo.click();
		await this.iframeLocator
			.getByRole('option', {name: whenOption})
			.click();

		await this.inputThenCombo.click();
		await this.iframeLocator
			.getByRole('option', {name: thenOption})
			.click();

		if (thenOption === 'Notification') {
			await this.inputNotificationsCombo.click();

			await this.iframeLocator
				.getByRole('option', {name: notificationTemplateName})
				.click();
		}

		await this.saveButton.click();
	}
}
