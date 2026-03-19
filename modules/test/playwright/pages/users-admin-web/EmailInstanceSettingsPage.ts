/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {InstanceSettingsPage} from '../configuration-admin-web/InstanceSettingsPage';

export class EmailInstanceSettingsPage {
	readonly accountCreatedNotificationBodyWithoutPasswordLabel: Locator;
	readonly accountCreatedNotificationBodyWithPasswordLinkLabel: Locator;
	readonly accountCreatedNotificationEnabledCheckbox: Locator;
	readonly accountCreatedNotificationSubjectInput: Locator;
	readonly accountCreatedNotificationSubjectLabel: Locator;
	readonly instanceSettingsPage: InstanceSettingsPage;
	readonly page: Page;
	readonly senderAddressInput: Locator;
	readonly senderAddressLabel: Locator;
	readonly senderNameInput: Locator;
	readonly senderNameLabel: Locator;

	constructor(page: Page) {
		this.accountCreatedNotificationBodyWithoutPasswordLabel =
			page.getByText('Body Without Password');
		this.accountCreatedNotificationBodyWithPasswordLinkLabel =
			page.getByText('Body With Password Link');
		this.accountCreatedNotificationEnabledCheckbox =
			page.getByLabel('Enabled');
		this.accountCreatedNotificationSubjectInput = page.locator(
			'[id$="adminEmailUserAddedSubject"]'
		);
		this.accountCreatedNotificationSubjectLabel = page.getByText(
			'Subject',
			{exact: true}
		);
		this.instanceSettingsPage = new InstanceSettingsPage(page);
		this.page = page;
		this.senderAddressInput = page.getByRole('textbox', {
			name: 'Address',
		});
		this.senderAddressLabel = page.getByText('Address', {exact: true});
		this.senderNameInput = page.getByRole('textbox', {name: 'Name'});
		this.senderNameLabel = page.getByText('Name', {exact: true});
	}

	async goToAccountCreatedNotification() {
		await this.instanceSettingsPage.goToInstanceSetting(
			'Email',
			'Account Created Notification'
		);
	}

	async goToEmailSender() {
		await this.instanceSettingsPage.goToInstanceSetting(
			'Email',
			'Email Sender'
		);
	}
}
