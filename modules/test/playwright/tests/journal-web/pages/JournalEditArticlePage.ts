/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {JournalPage} from './JournalPage';

export class JournalEditArticlePage {
	readonly page: Page;

	readonly journalPage: JournalPage;
	readonly propertiesTab: Locator;
	readonly publishButton: Locator;
	readonly titlePlaceholder: Locator;

	constructor(page: Page) {
		this.page = page;

		this.journalPage = new JournalPage(page);
		this.propertiesTab = page.getByRole('tab', {name: 'Properties'});
		this.publishButton = page.getByRole('button', {name: 'Publish'});
		this.titlePlaceholder = page.getByPlaceholder(
			'Untitled Basic Web Content'
		);
	}

	async goto({
		siteUrl,
		structureName,
	}: {
		siteUrl?: Site['friendlyUrlPath'];
		structureName?: string;
	} = {}) {
		await this.journalPage.goto(siteUrl);
		await this.journalPage.goToCreateArticle(structureName);

		// Do it twice so we decrease flakiness

		await this.journalPage.goto(siteUrl);
		await this.journalPage.goToCreateArticle(structureName);

		await this.propertiesTab.waitFor();
	}

	async fillTitle(title: string) {
		await this.titlePlaceholder.fill(title);
	}

	async editAndPublishExistingBasicArticle(title: string) {
		await this.journalPage.goToJournalArticleAction('Edit', title);

		await this.propertiesTab.waitFor();

		await this.titlePlaceholder.fill(title);

		await this.publishButton.waitFor();

		await this.publishButton.click();

		await this.page
			.getByText(`Success:${title} was updated successfully.`)
			.waitFor();
	}

	async publishNewBasicArticle(title: string) {
		await this.fillTitle(title);

		await this.publishButton.waitFor();

		await this.publishButton.click();

		await this.page
			.getByText(`Success:${title} was created successfully.`)
			.waitFor();
	}
}
