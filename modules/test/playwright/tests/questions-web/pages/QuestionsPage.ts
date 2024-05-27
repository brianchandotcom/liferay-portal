/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import fillAndClickOutside from '../../../utils/fillAndClickOutside';

export class QuestionsPage {
	readonly page: Page;

	constructor(page: Page) {
		this.page = page;
	}

	async goto() {}

	async addNewQuestion(
		questionBody: string,
		questionTitle: string,
		tagName: string
	) {
		await this.goto();
		await this.page.getByRole('button', {name: 'Ask Question'}).click();
		await this.page
			.getByPlaceholder('What is your question?')
			.fill(questionTitle);
		await this.page.getByLabel('Source').click();
		await this.page
			.getByLabel('Rich Text Editor, editor1')
			.getByRole('textbox')
			.fill(questionBody);
		await fillAndClickOutside(
			this.page,
			this.page.getByLabel('Tags', {exact: true}),
			tagName
		);
		await this.page.getByLabel('Source').click();
		await this.page.getByLabel('Post Your Question').click();
	}

	async clickOnTag(tagName: string) {
		await this.page.getByRole('link', {name: tagName}).click();
	}

	async clickOnTagWithinTags(tagName: string) {
		await this.page.locator('a').filter({hasText: 'Tags'}).click();
		await this.clickOnTag(tagName);
	}
}
