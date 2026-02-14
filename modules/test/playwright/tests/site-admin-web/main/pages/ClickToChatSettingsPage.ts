/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {InstanceSettingsPage} from '../../../../pages/configuration-admin-web/InstanceSettingsPage';
import {SiteSettingsPage} from '../../../../pages/site-admin-web/SiteSettingsPage';

export class ClickToChatSettingsPage {
	readonly instanceSettingsPage: InstanceSettingsPage;
	readonly chatProvider: Locator;
	readonly chatProviderAccountId: Locator;
	readonly chatwootIcon: Locator;
	readonly crispIcon: Locator;
	readonly hubspotIcon: Locator;
	readonly jivoChatIcon: Locator;
	readonly liveChatIcon: Locator;
	readonly livePersonIcon: Locator;
	readonly smartsuppIcon: Locator;
	readonly tawkToIcon: Locator;
	readonly tidioIcon: Locator;
	readonly zendeskIcon: Locator;
	readonly saveButton: Locator;
	readonly siteSettingsPage: SiteSettingsPage;
	readonly page: Page;

	constructor(page: Page) {
		this.instanceSettingsPage = new InstanceSettingsPage(page);
		this.chatProvider = page.getByLabel('Chat Provider', {exact: true});
		this.chatProviderAccountId = page.getByLabel(
			'Chat Provider Account ID'
		);
		this.chatwootIcon = page.locator(
			`//div[contains(@class,'woot--bubble-holder')]`
		);
		this.crispIcon = page.locator(`//div[contains(@id,'crisp-chatbox')]`);
		this.hubspotIcon = page.locator(
			`//div[contains(@id,'hubspot-messages-iframe-container')]`
		);
		this.jivoChatIcon = page.locator(
			`//div[contains(@id,'jivo-iframe-container')]`
		);
		this.liveChatIcon = page.locator(
			`//div[contains(@id,'chat-widget-container')]`
		);
		this.livePersonIcon = page.locator(
			`//div[contains(@id,'LPMcontainer')]`
		);
		this.smartsuppIcon = page.locator(
			`//iframe[contains(@title,'Smartsupp')]`
		);
		this.tawkToIcon = page.locator(
			`//iframe[contains(@title, 'chat widget')]`
		);
		this.tidioIcon = page.locator(
			`//iframe[contains(@id,'tidio-chat-code')]`
		);
		this.zendeskIcon = page.locator(`//iframe[contains(@id, 'launcher')]`);
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.siteSettingsPage = new SiteSettingsPage(page);
		this.page = page;
	}

	async gotoInstanceSettings() {
		await this.instanceSettingsPage.goToInstanceSetting(
			'Click to Chat',
			'Click to Chat Configuration'
		);
	}

	async gotoSiteSettings() {
		await this.siteSettingsPage.goToSiteSetting(
			'Click to Chat',
			'Click to Chat'
		);
	}

	async enableClickToChat() {
		await this.gotoInstanceSettings();

		await this.page.getByLabel('Enable Click to Chat').check();

		await this.page
			.getByLabel('Site Settings Strategy')
			.selectOption({label: 'Always Inherit'});
	}

	async saveConfiguration() {
		await this.instanceSettingsPage.saveAndWaitForAlert();

		await this.page.reload();
	}

	async selectChatProvider(chatProvider: string) {
		await this.chatProvider.selectOption({label: chatProvider});
	}

	async setChatProviderPassword(password) {
		await this.chatProviderAccountId.fill(password);

		await this.instanceSettingsPage.saveAndWaitForAlert();

		await this.page.reload();
	}
}
