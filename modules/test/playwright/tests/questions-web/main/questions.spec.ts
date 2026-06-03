/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import getRandomString from '../../../utils/getRandomString';
import {questionsTest} from './fixtures/questionsTest';

const allQuestionsAcrossTopics = mergeTests(questionsTest);

allQuestionsAcrossTopics(
	'This is a test for LPS-153187. The user can create a new topic and access all questions from the breadcrumb dropdown.',
	{tag: '@LPS-153187'},
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

		// Create the first topic through the UI. Topic names cannot contain
		// hyphens, so the random UUIDs are stripped.

		const firstTopicName = getRandomString().replace(/-/g, '');

		await questionsTopicsPage.addNewTopic(firstTopicName);

		await expect(
			page.getByRole('link', {name: firstTopicName})
		).toBeVisible();

		// Seed a second topic and one question per topic through the API

		const {items: messageBoardSections} =
			await apiHelpers.headlessDelivery.getSiteMessageBoardSectionsPage(
				site.id
			);

		const [firstMessageBoardSection] = messageBoardSections.filter(
			(messageBoardSection) =>
				messageBoardSection.title === firstTopicName
		);

		const secondMessageBoardSection =
			await apiHelpers.headlessDelivery.postSiteMessageBoardSection({
				siteId: site.id,
				title: getRandomString().replace(/-/g, ''),
			});

		const firstQuestionHeadline = getRandomString();
		const secondQuestionHeadline = getRandomString();

		await apiHelpers.headlessDelivery.postMessageBoardSectionMessageBoardThread(
			{
				articleBody: getRandomString(),
				headline: firstQuestionHeadline,
				messageBoardSectionId: firstMessageBoardSection.id,
			}
		);
		await apiHelpers.headlessDelivery.postMessageBoardSectionMessageBoardThread(
			{
				articleBody: getRandomString(),
				headline: secondQuestionHeadline,
				messageBoardSectionId: secondMessageBoardSection.id,
			}
		);

		// Switch to All Questions from the breadcrumb dropdown

		await questionsTopicsPage.goToTopic(firstTopicName);

		await questionsPage.goToTopicFromBreadcrumb(
			firstTopicName,
			'All Questions'
		);

		// All questions from both topics are listed

		await expect(
			page.getByRole('link', {name: firstQuestionHeadline})
		).toBeVisible();
		await expect(
			page.getByRole('link', {name: secondQuestionHeadline})
		).toBeVisible();
	}
);

const tagWithSpaces = mergeTests(questionsTest);

tagWithSpaces(
	'This is a test for LPD-26663. Questions are not returned when a space is used in a tag.',
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

		await questionsTopicsPage.addNewTopic('New topic');
		await questionsTopicsPage.goToTopic('New topic');

		const questionBody = 'This is an example question body';
		const questionTitle = 'Question title example';
		const tagName = 'tag name with spaces';

		await questionsPage.addNewQuestion(
			questionBody,
			questionTitle,
			tagName
		);

		await questionsPage.clickOnTag(tagName);
		await expect(
			page.getByRole('link', {name: questionTitle})
		).toBeVisible();

		await questionsPage.clickOnTagWithinTags(tagName);
		await expect(
			page.getByRole('link', {name: questionTitle})
		).toBeVisible();
	}
);
