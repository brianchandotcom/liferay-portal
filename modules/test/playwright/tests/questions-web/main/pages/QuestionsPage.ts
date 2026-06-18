/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../../../utils/clickAndExpectToBeVisible';
import fillAndClickOutside from '../../../../utils/fillAndClickOutside';

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
			this.page.getByRole('combobox', {name: 'Tags'}),
			tagName
		);
		await this.page.getByLabel('Source').click();
		await this.page.getByLabel('Post Your Question').click();
	}

	async addComment(commentBody: string) {
		await this.page
			.getByRole('button', {name: 'Add Comment'})
			.first()
			.click();
		await this.page.getByLabel('Source').click();
		await this.page
			.getByLabel(/Rich Text Editor/)
			.getByRole('textbox')
			.fill(commentBody);
		await this.page.getByLabel('Source').click();
		await this.page
			.getByRole('button', {name: 'Add Comment'})
			.last()
			.click();
	}

	async answerQuestion(answerBody: string) {
		await this.page.getByRole('button', {name: 'Add Answer'}).click();
		await this.page.getByLabel('Source').click();
		await this.page
			.getByLabel(/Rich Text Editor/)
			.getByRole('textbox')
			.fill(answerBody);
		await this.page.getByLabel('Source').click();
		await this.page.getByRole('button', {name: 'Post Answer'}).click();
	}

	async askQuestion(
		questionBody: string,
		questionTitle: string,
		tagNames: string[]
	) {
		await this.page
			.getByRole('button', {name: 'Ask Question'})
			.first()
			.click();
		await this.page
			.getByPlaceholder('What is your question?')
			.fill(questionTitle);
		await this.page.getByLabel('Source').click();
		await this.page
			.getByLabel(/Rich Text Editor/)
			.getByRole('textbox')
			.fill(questionBody);
		await this.page.getByLabel('Source').click();

		const tagsInput = this.page.getByRole('combobox', {name: 'Tags'});

		for (const tagName of tagNames) {
			await tagsInput.fill(tagName);
			await tagsInput.press('Enter');
		}

		await this.page.getByLabel('Post Your Question').click();
	}

	async clickOnTag(tagName: string) {
		await this.page.getByRole('link', {name: tagName}).click();
	}

	async goToTopicFromBreadcrumb(
		currentTopicName: string,
		targetTopicName: string
	) {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.locator('div.dropdown-menu a', {
				hasText: targetTopicName,
			}),
			trigger: this.page.locator('div.dropdown > span', {
				hasText: currentTopicName,
			}),
		});
	}

	async clickOnTagWithinTags(tagName: string) {
		await this.page.locator('a').filter({hasText: 'Tags'}).click();
		await this.clickOnTag(tagName);
	}
}
