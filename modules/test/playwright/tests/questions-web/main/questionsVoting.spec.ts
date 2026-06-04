/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import getRandomString from '../../../utils/getRandomString';
import {performUserSwitch, userData} from '../../../utils/performLogin';
import {questionsTest} from './fixtures/questionsTest';

const test = mergeTests(questionsTest);

test(
	'This is a test for LPS-122810. The voting style shows in the questions and answers lists.',
	{tag: '@LPS-122810'},
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

		// Voting the question up shows in the questions list

		const questionVotes = page
			.locator('.questions-container span.c-px-2')
			.first();
		const voteDownButton = page.getByLabel('Downvote').first();
		const voteUpButton = page.getByLabel('Upvote').first();

		await questionsTopicsPage.goToTopic(topicName);
		await page.getByRole('link', {name: questionTitle}).click();

		await expect(questionVotes).toHaveText('0');

		await voteUpButton.click();

		await expect(questionVotes).toHaveText('1');

		await page.goto(layoutURL);
		await questionsTopicsPage.goToTopic(topicName);

		await expect(
			page.locator('li:has(svg.lexicon-icon-caret-top)', {hasText: '1'})
		).toBeVisible();

		// Voting the question down shows in the questions list

		await page.getByRole('link', {name: questionTitle}).click();

		await voteDownButton.click();

		await expect(questionVotes).toHaveText('0');

		await voteDownButton.click();

		await expect(questionVotes).toHaveText('-1');

		await page.goto(layoutURL);
		await questionsTopicsPage.goToTopic(topicName);

		await expect(
			page.locator('li:has(svg.lexicon-icon-caret-bottom)', {
				hasText: '-1',
			})
		).toBeVisible();

		// The votes of an answer can be managed the same way

		await page.getByRole('link', {name: questionTitle}).click();

		await voteUpButton.click();

		await expect(questionVotes).toHaveText('0');

		await questionsPage.answerQuestion(getRandomString());

		const answerVotes = page.locator('.questions-answer span.c-px-2');

		await expect(answerVotes).toHaveText('0');

		await page.locator('.questions-answer').getByLabel('Upvote').click();

		await expect(answerVotes).toHaveText('1');

		await page.locator('.questions-answer').getByLabel('Downvote').click();

		await expect(answerVotes).toHaveText('0');
	}
);

test(
	'This is a test for LPS-117752. Questions can be ordered by votes.',
	{tag: '@LPS-117752'},
	async ({apiHelpers, page, questionsTopicsPage, site, widgetPagePage}) => {
		const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			title: getRandomString(),
			typeSettings: 'layout-template-id=1_column\n',
		});

		const layoutURL = '/web' + site.friendlyUrlPath + layout.friendlyURL;

		await page.goto(layoutURL);
		await widgetPagePage.addPortlet('Questions');

		// Seed a topic and three questions through the API

		const firstQuestionTitle = getRandomString();
		const secondQuestionTitle = getRandomString();
		const thirdQuestionTitle = getRandomString();
		const topicName = getRandomString().replace(/-/g, '');

		const messageBoardSection =
			await apiHelpers.headlessDelivery.postSiteMessageBoardSection({
				siteId: site.id,
				title: topicName,
			});

		for (const headline of [
			firstQuestionTitle,
			secondQuestionTitle,
			thirdQuestionTitle,
		]) {
			await apiHelpers.headlessDelivery.postMessageBoardSectionMessageBoardThread(
				{
					articleBody: getRandomString(),
					headline,
					messageBoardSectionId: messageBoardSection.id,
				}
			);
		}

		await page.reload();

		// Two users vote the questions up

		const voteUpButton = page.getByLabel('Upvote').first();

		await questionsTopicsPage.goToTopic(topicName);
		await page.getByRole('link', {name: firstQuestionTitle}).click();
		await voteUpButton.click();

		await expect(
			page.locator('.questions-container span.c-px-2').first()
		).toHaveText('1');

		await page.goto(layoutURL);
		await questionsTopicsPage.goToTopic(topicName);
		await page.getByRole('link', {name: secondQuestionTitle}).click();
		await voteUpButton.click();

		const voter = await apiHelpers.headlessAdminUser.postUserAccount();

		const administratorRole =
			await apiHelpers.headlessAdminUser.getRoleByName('Administrator');

		await apiHelpers.headlessAdminUser.assignUserToRole(
			administratorRole.externalReferenceCode,
			voter.id
		);

		userData[voter.alternateName] = {
			name: voter.givenName,
			password: 'test',
			surname: voter.familyName,
		};

		await performUserSwitch(page, voter.alternateName);

		await page.goto(layoutURL);
		await questionsTopicsPage.goToTopic(topicName);
		await page.getByRole('link', {name: secondQuestionTitle}).click();
		await voteUpButton.click();

		await expect(
			page.locator('.questions-container span.c-px-2').first()
		).toHaveText('2');

		// Ordering by Most Frequent sorts the questions by votes

		await page.goto(layoutURL);
		await questionsTopicsPage.goToTopic(topicName);

		await page.getByRole('button', {name: 'Filter and Order'}).click();
		await page.getByText('Most Frequent', {exact: true}).click();
		await page.getByRole('button', {name: 'Apply'}).click();

		await page.keyboard.press('Escape');

		const questionRows = page.locator('.question-row');

		await expect(questionRows.nth(0)).toContainText(secondQuestionTitle);
		await expect(questionRows.nth(1)).toContainText(firstQuestionTitle);
		await expect(questionRows.nth(2)).toContainText(thirdQuestionTitle);
	}
);
