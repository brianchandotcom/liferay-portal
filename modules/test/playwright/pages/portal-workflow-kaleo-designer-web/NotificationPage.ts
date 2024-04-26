/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class NotificationPage {
	readonly inputNotificationDescription: Locator;
	readonly inputNotificationName: Locator;
	readonly inputNotificationTemplate: Locator;
	readonly inputNotificationTemplateLanguage: Locator;
	readonly inputNotificationTypeCombo: Locator;
	readonly inputNotificationTypeEmail: Locator;
	readonly inputNotificationTypeUser: Locator;
	readonly inputRecipientType: Locator;
	readonly inputRoleName: Locator;
	readonly inputScript: Locator;
	readonly inputScriptLanguage: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.inputNotificationDescription = page.locator(
			'#notificationDescription'
		);

		this.inputNotificationName = page.locator('#notificationName');

		this.inputNotificationTemplate = page.locator('#template');
		this.inputNotificationTemplateLanguage =
			page.locator('#template-language');

		this.inputNotificationTypeCombo = page
			.locator('div')
			.filter({
				hasText:
					/^Notification Types\*Press backspace to delete the current row\.$/,
			})
			.getByRole('combobox');
		this.inputNotificationTypeEmail = page.getByRole('checkbox', {
			name: 'Email',
		});
		this.inputNotificationTypeUser = page.getByRole('checkbox', {
			name: 'User Notification',
		});
		this.inputRecipientType = page.locator('#recipient-type');
		this.inputRoleName = page.locator('#role-name');
		this.inputScriptLanguage = page.locator('#script-language');
		this.inputScript = page.locator('#nodeScript');
		this.page = page;
	}

	async fillNotificationFields({
		notificationDescription,
		notificationName,
		notificationTypeEmail,
		notificationTypeUser,
		recipientType,
		recipientTypeData,
		template,
		templateLanguage,
	}: Notification) {
		await this.inputNotificationDescription.fill(notificationDescription);
		await this.inputNotificationName.fill(notificationName);
		await this.inputNotificationTemplate.fill(template);

		await this.inputNotificationTemplateLanguage.selectOption(
			templateLanguage
		);

		await this.inputNotificationTypeCombo.click();

		if (notificationTypeEmail) {
			await this.inputNotificationTypeEmail.check();
		}
		if (notificationTypeUser) {
			await this.inputNotificationTypeUser.check();
		}

		await this.inputRecipientType.click();

		await this.inputRecipientType.selectOption(recipientType);

		if (recipientType === 'role') {
			await this.inputRoleName.click();

			await this.page
				.getByRole('menuitem', {
					name: (recipientTypeData as RoleType)?.roleName,
				})
				.click();
		}
		else if (recipientType === 'scriptedRecipient') {
			await this.inputScriptLanguage.selectOption(
				(recipientTypeData as ScriptedRecipient)?.scriptLanguage
			);

			await this.inputScript.fill(
				(recipientTypeData as ScriptedRecipient)?.script
			);
		}
	}

	async getRecipientTypeTypeOption(optionValue: string) {
		return await this.page.$(
			`#recipient-type option[value="${optionValue}"]`
		);
	}
}
