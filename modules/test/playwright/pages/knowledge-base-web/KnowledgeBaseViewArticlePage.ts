/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {KnowledgeBasePage} from './KnowledgeBasePage';

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

	async goto(siteUrl: Site['friendlyUrlPath'], articleTitle: string) {
		await this.knowledgeBasePage.goto(siteUrl);

		await this.page.getByRole('link', {name: articleTitle}).click();
		await this.page
			.locator('.kb-article-title')
			.getByText(articleTitle)
			.waitFor();
	}

	async deleteKnowledgeBaseArticle() {
		clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {name: 'Delete'}),
			trigger: this.showActionsButton,
		});
	}
}
