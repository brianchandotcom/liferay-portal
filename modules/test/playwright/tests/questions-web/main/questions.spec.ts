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

const subtopicsViaWidget = mergeTests(questionsTest);

subtopicsViaWidget(
	'This is a test for LPS-116516. Subtopics can be created and navigated through the widget.',
	{tag: '@LPS-116516'},
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
			typeSettings: 'layout-template-id=1_column\n',
		});
		const layoutURL = '/web' + site.friendlyUrlPath + layout.friendlyURL;

		await page.goto(layoutURL);
		await widgetPagePage.addPortlet('Questions');

		// Topic names cannot contain hyphens, so the random UUIDs are stripped

		const rootTopicName = getRandomString().replace(/-/g, '');
		const subTopicAName = getRandomString().replace(/-/g, '');
		const subTopicBName = getRandomString().replace(/-/g, '');

		const breadcrumb = page.locator('.breadcrumb');

		// Create a root topic and nest two subtopics under it

		await questionsTopicsPage.addNewTopic(rootTopicName);

		await expect(
			page.getByRole('link', {name: rootTopicName})
		).toBeVisible();

		await questionsTopicsPage.goToTopic(rootTopicName);
		await questionsTopicsPage.addNewTopic(subTopicAName);

		await expect(breadcrumb.getByText(subTopicAName)).toBeVisible();

		await questionsTopicsPage.addNewTopic(subTopicBName);

		await expect(breadcrumb.getByText(subTopicBName)).toBeVisible();

		// The created subtopics can be navigated through the breadcrumb

		await page.goto(layoutURL);
		await questionsTopicsPage.goToTopic(rootTopicName);
		await questionsPage.goToTopicFromBreadcrumb(
			rootTopicName,
			subTopicAName
		);

		await expect(breadcrumb.getByText(subTopicAName)).toBeVisible();

		await questionsPage.goToTopicFromBreadcrumb(
			subTopicAName,
			subTopicBName
		);

		await expect(breadcrumb.getByText(subTopicBName)).toBeVisible();
	}
);

const questionsAcrossTopics = mergeTests(questionsTest);

questionsAcrossTopics(
	'This is a test for LPS-115724 and LPS-115725. Questions can be viewed across topics and after a topic is edited.',
	{tag: ['@LPS-115724', '@LPS-115725']},
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
			typeSettings: 'layout-template-id=1_column\n',
		});
		const layoutURL = '/web' + site.friendlyUrlPath + layout.friendlyURL;

		await page.goto(layoutURL);
		await widgetPagePage.addPortlet('Questions');

		// Seed a root topic with two subtopics and one question in each

		const rootTopicName = getRandomString().replace(/-/g, '');
		const subTopicAName = getRandomString().replace(/-/g, '');
		const subTopicBName = getRandomString().replace(/-/g, '');

		const rootMessageBoardSection =
			await apiHelpers.headlessDelivery.postSiteMessageBoardSection({
				siteId: site.id,
				title: rootTopicName,
			});

		const subMessageBoardSectionA =
			await apiHelpers.headlessDelivery.postMessageBoardSectionMessageBoardSection(
				{
					parentMessageBoardSectionId: rootMessageBoardSection.id,
					title: subTopicAName,
				}
			);

		const subMessageBoardSectionB =
			await apiHelpers.headlessDelivery.postMessageBoardSectionMessageBoardSection(
				{
					parentMessageBoardSectionId: rootMessageBoardSection.id,
					title: subTopicBName,
				}
			);

		const rootQuestionTitle = getRandomString();
		const subQuestionATitle = getRandomString();
		const subQuestionBTitle = getRandomString();

		await apiHelpers.headlessDelivery.postMessageBoardSectionMessageBoardThread(
			{
				articleBody: getRandomString(),
				headline: rootQuestionTitle,
				messageBoardSectionId: rootMessageBoardSection.id,
			}
		);
		await apiHelpers.headlessDelivery.postMessageBoardSectionMessageBoardThread(
			{
				articleBody: getRandomString(),
				headline: subQuestionATitle,
				messageBoardSectionId: subMessageBoardSectionA.id,
			}
		);
		await apiHelpers.headlessDelivery.postMessageBoardSectionMessageBoardThread(
			{
				articleBody: getRandomString(),
				headline: subQuestionBTitle,
				messageBoardSectionId: subMessageBoardSectionB.id,
			}
		);

		await page.reload();

		const homeIcon = page.locator(
			'.breadcrumb a:has(svg[class*="lexicon-icon-home"])'
		);

		// Each topic lists only its own questions

		await questionsTopicsPage.goToTopic(rootTopicName);

		await expect(
			page.getByRole('link', {name: rootQuestionTitle})
		).toBeVisible();

		await page.goto(layoutURL);
		await questionsTopicsPage.goToTopic(rootTopicName);
		await questionsPage.goToTopicFromBreadcrumb(
			rootTopicName,
			subTopicAName
		);

		await expect(
			page.getByRole('link', {name: subQuestionATitle})
		).toBeVisible();
		await expect(
			page.getByRole('link', {name: rootQuestionTitle})
		).toBeHidden();

		await page.goto(layoutURL);
		await questionsTopicsPage.goToTopic(rootTopicName);
		await questionsPage.goToTopicFromBreadcrumb(
			rootTopicName,
			subTopicBName
		);

		await expect(
			page.getByRole('link', {name: subQuestionBTitle})
		).toBeVisible();
		await expect(
			page.getByRole('link', {name: subQuestionATitle})
		).toBeHidden();

		// All Questions, reachable from a leaf topic, lists every question

		await questionsPage.goToTopicFromBreadcrumb(
			subTopicBName,
			'All Questions'
		);

		await expect(
			page.getByRole('link', {name: rootQuestionTitle})
		).toBeVisible();
		await expect(
			page.getByRole('link', {name: subQuestionATitle})
		).toBeVisible();
		await expect(
			page.getByRole('link', {name: subQuestionBTitle})
		).toBeVisible();

		// The home icon returns to the topics home page

		await homeIcon.click();

		await expect(
			page.getByText(rootTopicName, {exact: true})
		).toBeVisible();
		await expect(homeIcon).toBeHidden();

		// A question stays reachable after its topic is renamed

		const editedRootTopicName = getRandomString().replace(/-/g, '');

		await apiHelpers.headlessDelivery.patchMessageBoardSection({
			messageBoardSectionId: rootMessageBoardSection.id,
			title: editedRootTopicName,
		});

		await page.goto(layoutURL);

		await questionsTopicsPage.goToTopic(editedRootTopicName);

		await expect(
			page.getByRole('link', {name: rootQuestionTitle})
		).toBeVisible();
	}
);
