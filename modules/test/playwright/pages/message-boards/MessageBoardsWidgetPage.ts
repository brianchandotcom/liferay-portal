/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {ApiHelpers} from '../../helpers/ApiHelpers';
import getRandomString from '../../utils/getRandomString';
import {performLogout} from '../../utils/performLogin';
import {WidgetPage} from '../layout-admin-web/WidgetPage';
export class MessageBoardsWidgetPage {
	readonly apiHelpers: ApiHelpers;
	readonly page: Page;
	readonly widgetPage: WidgetPage;

	constructor(page: Page) {
		this.apiHelpers = new ApiHelpers(page);
		this.page = page;
		this.widgetPage = new WidgetPage(page);
	}

	async addMessageBoardsPortlet(site: Site) {
		const layout = await this.apiHelpers.jsonWebServicesLayout.addLayout(
			site.id,
			getRandomString()
		);

		await this.page.goto(
			'/web' + site.friendlyUrlPath + layout.friendlyURL
		);

		await this.widgetPage.addPortlet('Message Boards');

		return layout;
	}

	async addGuestReply(site: Site, layout: Layout, buttonName: string) {
		await performLogout(this.page);

		await this.page.goto(
			'/web' + site.friendlyUrlPath + layout.friendlyURL
		);

		await this.page.getByRole('link', {name: 'Thread Subject'}).click();

		await this.page.getByRole('button', {name: 'Reply'}).click();

		const frameLocator = this.page.frameLocator(
			'iframe[title*="_com_liferay_message_boards_web_portlet_MBPortlet_replyMessageBody"]'
		);

		await frameLocator.getByRole('textbox').fill('test guest');

		await this.page.getByRole('button', {name: buttonName}).click();
	}
}
