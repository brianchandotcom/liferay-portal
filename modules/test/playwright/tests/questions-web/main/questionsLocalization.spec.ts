/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import getRandomString from '../../../utils/getRandomString';
import {questionsTest} from './fixtures/questionsTest';

const test = mergeTests(questionsTest);

test(
	'This is a test for LPS-128639. The user can manage topics, subtopics and questions with non-Latin characters.',
	{tag: ['@LPS-128639', '@LPD-93524']},
	async ({
		apiHelpers,
		page,
		questionsPage,
		questionsTopicsPage,
		site,
		widgetPagePage,
	}) => {
		const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			title: getRandomString(),
		});

		await page.goto('/web' + site.friendlyUrlPath + layout.friendlyURL);
		await widgetPagePage.addPortlet('Questions');

		// Create a root topic with non-Latin characters

		await questionsTopicsPage.addNewTopic('質問トピック');

		await expect(
			page.getByRole('link', {name: '質問トピック'})
		).toBeVisible();

		// Create two subtopics with non-Latin characters

		await questionsTopicsPage.goToTopic('質問トピック');

		for (const subTopicName of ['かながわけん', '奈良県']) {
			await questionsTopicsPage.addNewTopic(subTopicName);

			// Creating a subtopic navigates into it

			await expect(page.getByText(subTopicName).first()).toBeVisible();

			await page.goto('/web' + site.friendlyUrlPath + layout.friendlyURL);

			await questionsTopicsPage.goToTopic('質問トピック');
		}

		// The subtopics are reachable through the breadcrumb dropdown

		await questionsPage.goToTopicFromBreadcrumb(
			'質問トピック',
			'かながわけん'
		);

		await page.goto('/web' + site.friendlyUrlPath + layout.friendlyURL);

		await questionsTopicsPage.goToTopic('質問トピック');

		// Ask a question with non-Latin characters in a subtopic

		await questionsPage.goToTopicFromBreadcrumb('質問トピック', '奈良県');

		await questionsPage.askQuestion(
			'私の好きな日本料理は寿司とすき焼きです',
			'あなたの好きな日本料理は何ですか',
			[]
		);

		await expect(
			page.getByRole('link', {name: 'あなたの好きな日本料理は何ですか'})
		).toBeVisible();

		// The question can be reached again through the subtopic

		await page.goto('/web' + site.friendlyUrlPath + layout.friendlyURL);

		await questionsTopicsPage.goToTopic('質問トピック');
		await questionsPage.goToTopicFromBreadcrumb('質問トピック', '奈良県');

		await page
			.getByRole('link', {name: 'あなたの好きな日本料理は何ですか'})
			.click();

		await expect(
			page.getByText('あなたの好きな日本料理は何ですか').first()
		).toBeVisible();
	}
);

test(
	'The user can manage the lifecycle of a question with non-Latin characters.',
	{tag: '@LPD-93524'},
	async ({
		apiHelpers,
		page,
		questionsPage,
		questionsTopicsPage,
		site,
		widgetPagePage,
	}) => {
		const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			title: getRandomString(),
		});

		await page.goto('/web' + site.friendlyUrlPath + layout.friendlyURL);
		await widgetPagePage.addPortlet('Questions');

		// Seed a topic and a question with non-Latin characters through the API

		const answerBody = '私の好きな日本料理はフグ刺しと天ぷらです';
		const commentBody = '私の好きな日本料理は焼肉と鮭の刺身です';
		const questionTitle = 'あなたの好きな日本料理は何ですか';

		const messageBoardSection =
			await apiHelpers.headlessDelivery.postSiteMessageBoardSection({
				siteId: site.id,
				title: '質問トピック',
			});

		await apiHelpers.headlessDelivery.postMessageBoardSectionMessageBoardThread(
			{
				articleBody: 'あなたの好きな日本料理は寿司とすき焼きですか',
				headline: questionTitle,
				messageBoardSectionId: messageBoardSection.id,
			}
		);

		await page.reload();

		// Answer and comment with non-Latin characters

		await questionsTopicsPage.goToTopic('質問トピック');
		await page.getByRole('link', {name: questionTitle}).click();

		await questionsPage.answerQuestion(answerBody);

		await expect(page.getByText(answerBody)).toBeVisible();

		await questionsPage.addComment(commentBody);

		await expect(page.getByText(commentBody)).toBeVisible();

		// Canceled deletions preserve the comment, the answer and the question

		const modal = page.locator('.modal-dialog');

		await page.getByRole('button', {name: 'Delete'}).last().click();
		await modal.getByRole('button', {name: 'Cancel'}).click();

		await expect(page.getByText(commentBody)).toBeVisible();

		await page.getByRole('button', {name: 'Delete'}).first().click();
		await modal.getByRole('button', {name: 'Cancel'}).click();

		await expect(page.getByText(answerBody)).toBeVisible();

		// The comment can be deleted

		await page.getByRole('button', {name: 'Delete'}).last().click();
		await modal.getByRole('button', {name: 'Delete'}).click();

		await expect(page.getByText(commentBody)).toBeHidden();

		// The answer can be edited and deleted

		const editedAnswerBody = '私の好きな日本料理は焼肉と鮭の刺身です';

		await page.getByRole('button', {exact: true, name: 'Edit'}).click();
		await page.getByLabel('Source').click();
		await page
			.getByLabel(/Rich Text Editor/)
			.getByRole('textbox')
			.fill(editedAnswerBody);
		await page.getByLabel('Source').click();
		await page.getByRole('button', {name: 'Update Your Answer'}).click();

		await expect(page.getByText(editedAnswerBody)).toBeVisible();

		await page.getByRole('button', {name: 'Delete'}).first().click();
		await modal.getByRole('button', {name: 'Delete'}).click();

		await expect(page.getByText(editedAnswerBody)).toBeHidden();

		// The question can be viewed through an edited topic

		await apiHelpers.headlessDelivery.patchMessageBoardSection({
			messageBoardSectionId: messageBoardSection.id,
			title: '質問トピックを編集する',
		});

		await page.goto('/web' + site.friendlyUrlPath + layout.friendlyURL);

		await questionsTopicsPage.goToTopic('質問トピックを編集する');

		await page.getByRole('link', {name: questionTitle}).click();

		await expect(page.getByText(questionTitle).first()).toBeVisible();

		// The question and the topic can be deleted

		await page
			.locator(
				'.questions-container button:has(svg.lexicon-icon-ellipsis-v)'
			)
			.first()
			.click();
		await page
			.getByRole('menuitem', {name: 'Delete'})
			.filter({visible: true})
			.click();
		await modal.getByRole('button', {name: 'Delete'}).click();

		await expect(
			page.getByRole('link', {name: questionTitle})
		).toBeHidden();

		await apiHelpers.headlessDelivery.deleteMessageBoardSection(
			messageBoardSection.id
		);

		await page.goto('/web' + site.friendlyUrlPath + layout.friendlyURL);

		await expect(page.getByText('This page has no topics.')).toBeVisible();
	}
);

test(
	'The user can manage and view questions through tags with non-Latin characters.',
	{tag: '@LPD-93524'},
	async ({
		apiHelpers,
		page,
		questionsPage,
		questionsTopicsPage,
		site,
		widgetPagePage,
	}) => {
		const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			title: getRandomString(),
		});

		await page.goto('/web' + site.friendlyUrlPath + layout.friendlyURL);
		await widgetPagePage.addPortlet('Questions');

		// Seed a topic and three questions with non-Latin tags through the API

		const firstQuestionTitle = getRandomString();

		const messageBoardSection =
			await apiHelpers.headlessDelivery.postSiteMessageBoardSection({
				siteId: site.id,
				title: '質問トピック',
			});

		await apiHelpers.headlessDelivery.postMessageBoardSectionMessageBoardThread(
			{
				articleBody: getRandomString(),
				headline: firstQuestionTitle,
				keywords: ['おおさか'],
				messageBoardSectionId: messageBoardSection.id,
			}
		);
		await apiHelpers.headlessDelivery.postMessageBoardSectionMessageBoardThread(
			{
				articleBody: getRandomString(),
				headline: getRandomString(),
				keywords: ['おおさか'],
				messageBoardSectionId: messageBoardSection.id,
			}
		);
		await apiHelpers.headlessDelivery.postMessageBoardSectionMessageBoardThread(
			{
				articleBody: getRandomString(),
				headline: getRandomString(),
				keywords: ['とうきょう'],
				messageBoardSectionId: messageBoardSection.id,
			}
		);

		await page.reload();

		// The tags are sorted by usage and can be searched

		await page.locator('.navbar').getByText('Tags', {exact: true}).click();

		const tagCards = page.locator('div.question-tags');

		await expect(tagCards.nth(0)).toContainText('おおさか');
		await expect(tagCards.nth(0)).toContainText('Used 2 Times');
		await expect(tagCards.nth(1)).toContainText('とうきょう');

		const searchField = page.getByPlaceholder('Search', {exact: true});

		await searchField.fill('おおさか');

		await expect(tagCards.filter({hasText: 'とうきょう'})).toBeHidden();
		await expect(tagCards.filter({hasText: 'おおさか'})).toBeVisible();

		// A tag with non-Latin characters opens the questions that use it

		await tagCards.filter({hasText: 'おおさか'}).click();

		await expect(
			page.getByRole('link', {name: firstQuestionTitle})
		).toBeVisible();

		// A question can be created with multiple non-Latin tags

		await page.goto('/web' + site.friendlyUrlPath + layout.friendlyURL);

		await questionsTopicsPage.goToTopic('質問トピック');

		await questionsPage.askQuestion(
			'私の好きな日本料理は寿司とすき焼きです',
			'あなたの好きな日本料理は何ですか',
			['いち', 'に', 'さん', 'し', 'ご']
		);

		await expect(
			page.getByRole('link', {name: 'あなたの好きな日本料理は何ですか'})
		).toBeVisible();
	}
);
