/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {NotificationTemplatesPage} from './NotificationTemplatesPage';

export class EmailNotificationTemplatePage {
	readonly page: Page;
	readonly accountRolesGroupTitle: Locator;
	readonly backURLButton: Locator;
	readonly basicInfoName: Locator;
	readonly contentSubject: Locator;
	readonly copyButton: Locator;
	readonly definitionOfTermsEntity: Locator;
	readonly editorType: Locator;
	readonly freeMarkerEntity: Locator;
	readonly notificationTemplatesPage: NotificationTemplatesPage;
	readonly organizationRolesGroupTitle: Locator;
	readonly primaryRecipientRoles: Locator;
	readonly primaryRecipientUserEmailAddress: Locator;
	readonly primaryRecipientType: Locator;
	readonly regularRolesGroupTitle: Locator;
	readonly richTextField: Locator;
	readonly richTextSourceButton: Locator;
	readonly richTextSourceField: Locator;
	readonly saveButton: Locator;
	readonly secondaryRecipientRolesBCC: Locator;
	readonly secondaryRecipientRolesCC: Locator;
	readonly secondaryRecipientTypeBCC: Locator;
	readonly secondaryRecipientTypeCC: Locator;
	readonly senderEmailAddress: Locator;
	readonly senderName: Locator;

	constructor(page: Page) {
		this.page = page;
		this.accountRolesGroupTitle = page
			.getByText('Account Roles', {exact: true})
			.locator('visible=true');
		this.backURLButton = page.getByTitle('Back', {exact: true}).first();
		this.basicInfoName = page.getByLabel('Name' + 'Mandatory').first();
		this.contentSubject = page.getByLabel('Subject' + 'Mandatory');
		this.copyButton = page.getByRole('button', {name: 'Copy'});
		this.definitionOfTermsEntity = page.getByLabel('Entity').last();
		this.editorType = page.getByLabel('Editor Type' + 'Mandatory');
		this.freeMarkerEntity = page.getByLabel('Entity').first();
		this.notificationTemplatesPage = new NotificationTemplatesPage(page);
		this.organizationRolesGroupTitle = page
			.getByText('Organization Roles')
			.locator('visible=true');
		this.primaryRecipientType = page.getByLabel('Type' + 'Mandatory', {
			exact: true,
		});
		this.primaryRecipientRoles = page.getByLabel('Role' + 'Mandatory');
		this.primaryRecipientUserEmailAddress = page.locator(
			'input[id="primaryRecipients"]'
		);
		this.regularRolesGroupTitle = page
			.getByText('Regular Roles')
			.locator('visible=true');
		this.richTextField = page
			.getByRole('application', {name: 'Rich Text Editor,'})
			.frameLocator('iframe')
			.getByRole('textbox');
		this.richTextSourceButton = page.getByTitle('Source');
		this.richTextSourceField = page
			.getByLabel('Rich Text Editor, richTextLocalizedEditor')
			.getByRole('textbox');
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.secondaryRecipientRolesBCC = page
			.getByLabel('Role', {exact: true})
			.last();
		this.secondaryRecipientRolesCC = page
			.getByLabel('Role', {exact: true})
			.first();
		this.secondaryRecipientTypeBCC = page
			.getByLabel('Type', {exact: true})
			.last();
		this.secondaryRecipientTypeCC = page
			.getByLabel('Type', {exact: true})
			.first();
		this.senderEmailAddress = page.getByLabel(
			'Email Address' + 'Mandatory'
		);
		this.senderName = page.getByLabel('Name' + 'Mandatory').last();
	}

	async goto() {
		await this.notificationTemplatesPage.goto();

		await this.notificationTemplatesPage.newNotificationTemplateButton.click();

		await this.notificationTemplatesPage.emailNotificationDropdownItem.click();
	}
}
