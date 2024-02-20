/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
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

	async goToCreateNewBasicArticle(title: string) {
		await this.journalPage.goto();

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.journalPage.createBasicWebContentLink,
			trigger: this.journalPage.newButton,
		});

		await this.propertiesTab.waitFor();

		await this.titlePlaceholder.fill(title);

		await this.publishButton.click();
	}
}
