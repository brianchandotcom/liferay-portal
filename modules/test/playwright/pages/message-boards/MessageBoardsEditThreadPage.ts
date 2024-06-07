/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {MessageBoardsPage} from './MessageBoardsPage';

export class MessageBoardsEditThreadPage {
	readonly bodyFrameLocator: FrameLocator;
	readonly bodyTextBox: Locator;
	readonly messageBoardsPage: MessageBoardsPage;
	readonly page: Page;
	readonly publishButton: Locator;
	readonly subjectSelector: Locator;
	readonly successMessage: Locator;

	constructor(page: Page) {
		this.bodyFrameLocator = page.frameLocator('iframe');
		this.bodyTextBox = this.bodyFrameLocator.getByRole('textbox');
		this.messageBoardsPage = new MessageBoardsPage(page);
		this.page = page;
		this.publishButton = page.getByRole('button', {
			exact: true,
			name: 'Publish',
		});
		this.subjectSelector = page.getByLabel('Subject');
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.messageBoardsPage.goto(siteUrl);

		await this.messageBoardsPage.goToCreateNewThread();
	}

	async publishNewBasicTread(
		subject: string,
		body: string,
		siteUrl?: Site['friendlyUrlPath']
	) {
		await this.goto(siteUrl);

		await this.subjectSelector.fill(subject);
		await this.bodyTextBox.fill(body);

		await this.publishButton.click();
	}
}
