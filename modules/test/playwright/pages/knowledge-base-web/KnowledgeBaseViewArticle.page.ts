/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {KnowledgeBasePage} from './KnowledgeBase.page';

export class KnowledgeBaseViewArticlePage {
	readonly page: Page;
	showActionsButton: Locator;
	deleteMenuItem: Locator;
	knowledgeBasePage: KnowledgeBasePage;

	constructor(page: Page) {
		this.showActionsButton = page.getByLabel('Show Actions');
		this.knowledgeBasePage = new KnowledgeBasePage(page);
		this.page = page;
	}

	async goto(title: string) {
		await this.knowledgeBasePage.goto();
		await this.page.getByRole('link', {name: title}).click();
		await this.page.locator('.kb-article-title').getByText(title).waitFor();
	}

	async deleteKnowledgeBaseArticle(title: string) {
		await this.goto(title);
		await this.showActionsButton.waitFor();
		await this.showActionsButton.click();
		await this.page.getByRole('menuitem', {name: 'Delete'}).click();
	}
}
