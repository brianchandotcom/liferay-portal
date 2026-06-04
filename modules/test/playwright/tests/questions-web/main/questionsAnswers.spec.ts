/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import getRandomString from '../../../utils/getRandomString';
import {hoverAndExpectToBeVisible} from '../../../utils/hoverAndExpectToBeVisible';
import {questionsTest} from './fixtures/questionsTest';

const test = mergeTests(questionsTest);

test(
	'This is a test for LPS-112174 and LPS-152316. Answers and comments show response labels and author statistics.',
	{tag: ['@LPS-112174', '@LPS-152316']},
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

		// Seed a topic and a question through the API

		const questionTitle = getRandomString();
		const topicName = getRandomString().replace(/-/g, '');

		const messageBoardSection =
			await apiHelpers.headlessDelivery.postSiteMessageBoardSection({
				siteId: site.id,
				title: topicName,
			});

		await apiHelpers.headlessDelivery.postMessageBoardSectionMessageBoardThread(
			{
				articleBody: getRandomString(),
				headline: questionTitle,
				messageBoardSectionId: messageBoardSection.id,
			}
		);

		await page.reload();

		await questionsTopicsPage.goToTopic(topicName);
		await page.getByRole('link', {name: questionTitle}).click();

		// Answering the question shows the Answered label

		await questionsPage.answerQuestion(getRandomString());

		await expect(
			page.locator('span.text-secondary', {hasText: 'Answered'})
		).toBeVisible();

		// Commenting on the answer shows the Replied label

		await questionsPage.addComment(getRandomString());

		await expect(
			page.locator('span.text-secondary', {hasText: 'Replied'})
		).toBeVisible();

		// Hovering the author shows the statistics popover

		await hoverAndExpectToBeVisible({
			target: page
				.locator('div.text-secondary p', {hasText: 'Posts'})
				.first(),
			trigger: page
				.locator('a.questions-user', {hasText: 'Test Test'})
				.first(),
		});

		for (const itemName of ['Posts', 'Join Date', 'Last Post Date']) {
			await expect(
				page
					.locator('div.text-secondary p', {hasText: itemName})
					.first()
			).toBeVisible();
		}
	}
);
