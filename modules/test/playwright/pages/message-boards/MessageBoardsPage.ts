/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {PORTLET_URLS} from '../../utils/portletUrls';

export class MessageBoardsPage {
	readonly actionAddMessage: Locator;
	readonly actionReplyMessage: Locator;
	readonly homeCategoryPermissionsFrame: FrameLocator;
	readonly homeCategoryPermissionsMenuItem: Locator;
	readonly page: Page;
	readonly optionsMenu: Locator;
	readonly saveButton: Locator;

	constructor(page: Page) {
		this.homeCategoryPermissionsFrame = page.frameLocator(
			'iframe[title="Home Category Permissions"]'
		);

		this.actionAddMessage = this.homeCategoryPermissionsFrame.locator(
			'#guest_ACTION_ADD_MESSAGE'
		);
		this.actionReplyMessage = this.homeCategoryPermissionsFrame.locator(
			'#guest_ACTION_REPLY_TO_MESSAGE'
		);
		this.homeCategoryPermissionsMenuItem = page.getByRole('menuitem', {
			name: 'Home Category Permissions',
		});
		this.page = page;
		this.optionsMenu = page.getByLabel('Options');
		this.saveButton = this.homeCategoryPermissionsFrame.getByRole(
			'button',
			{name: 'Save'}
		);
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.messageBoardsAdmin}`
		);
	}

	async goToCreateNewThread() {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {name: 'Thread'}),
			trigger: this.page.getByRole('button', {exact: true, name: 'New'}),
		});
	}

	async setGuestCategoryPermissions(siteUrl?: Site['friendlyUrlPath']) {
		await this.goto(siteUrl);

		await this.optionsMenu.click();
		await this.homeCategoryPermissionsMenuItem.click();

		await this.actionAddMessage.check();
		await this.actionReplyMessage.check();

		await this.saveButton.click();

		await this.page.getByLabel('close', {exact: true}).click();
	}
}
