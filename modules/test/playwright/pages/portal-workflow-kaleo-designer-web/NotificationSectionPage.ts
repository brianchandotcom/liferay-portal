/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

export class NotificationSectionPage {
	readonly inputActionType: Locator;
	readonly inputNotificationDescription: Locator;
	readonly inputNotificationName: Locator;
	readonly inputNotificationTemplate: Locator;
	readonly inputNotificationTemplateLanguage: Locator;
	readonly inputNotificationTypeCombo: Locator;
	readonly inputNotificationTypeEmail: Locator;
	readonly inputNotificationTypeUser: Locator;
	readonly inputRecipientType: Locator;
	readonly inputRoleName: Locator;
	readonly inputRoleType: Locator;
	readonly inputScript: Locator;
	readonly inputScriptLanguage: Locator;
	readonly page: Page;

	constructor(page: Page, index: number = 0) {
		this.inputActionType = page.locator('#action-type').nth(index);
		this.inputNotificationDescription = page
			.locator('#notificationDescription')
			.nth(index);
		this.inputNotificationName = page
			.locator('#notificationName')
			.nth(index);
		this.inputNotificationTemplate = page.locator('#template').nth(index);
		this.inputNotificationTemplateLanguage = page
			.locator('#template-language')
			.nth(index);
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
		this.inputRecipientType = page.locator('#recipient-type').nth(index);
		this.inputRoleName = page.locator('#role-name');
		this.inputRoleType = page.locator('#role-type');
		this.inputScriptLanguage = page.locator('#script-language');
		this.inputScript = page.locator('#nodeScript');
		this.page = page;
	}

	async assertNotificationSectionFields(
		index: number,
		{
			notificationDescription,
			notificationName,
			recipientType,
			recipientTypeData,
			template,
			templateLanguage,
		}: Notification
	) {
		await expect(this.inputNotificationDescription).toHaveValue(
			notificationDescription
		);
		await expect(this.inputNotificationName).toHaveValue(notificationName);
		await expect(this.inputNotificationTemplate).toHaveValue(
			new RegExp(`^(${template}|${template}\\n?)$`)
		);
		await expect(this.inputNotificationTemplateLanguage).toHaveValue(
			templateLanguage
		);

		await expect(this.page.getByText(/Email/).nth(index)).toBeVisible();
		await expect(
			this.page.getByText(/User Notification/).nth(index)
		).toBeVisible();

		await expect(this.inputRecipientType).toHaveValue(recipientType);

		if (recipientType === 'role') {
			await expect(this.inputRoleName).toHaveValue(
				(recipientTypeData as Role).roleName
			);
		}
		else if (recipientType === 'roleType') {
			await expect(this.inputRoleName).toHaveValue(
				(recipientTypeData as RoleType).roleName
			);
			await expect(this.inputRoleType).toHaveValue(
				(recipientTypeData as RoleType).roleType
			);
		}
		else if (recipientType === 'scriptedRecipient') {
			const script = (recipientTypeData as ScriptedRecipient).script;

			await expect(this.inputScript).toHaveValue(
				new RegExp(`^(${script}|${script}\\n?)$`)
			);
			await expect(this.inputScriptLanguage).toHaveValue(
				(recipientTypeData as ScriptedRecipient).scriptLanguage
			);
		}
	}

	async fillNotificationSectionFields(
		isTimer: boolean,
		{
			notificationDescription,
			notificationName,
			notificationTypeEmail,
			notificationTypeUser,
			recipientType,
			recipientTypeData,
			template,
			templateLanguage,
		}: Notification
	) {
		if (isTimer) {
			await this.inputActionType.selectOption('timerNotifications');
		}
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
					name: (recipientTypeData as Role)?.roleName,
				})
				.click();
		}
		else if (recipientType === 'roleType') {
			await this.inputRoleType.click();

			await this.page
				.getByRole('menuitem', {
					name: (recipientTypeData as RoleType)?.roleType,
				})
				.click();
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
