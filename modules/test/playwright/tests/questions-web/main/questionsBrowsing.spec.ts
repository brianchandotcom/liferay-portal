/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import getRandomString from '../../../utils/getRandomString';
import {questionsTest} from './fixtures/questionsTest';

const test = mergeTests(questionsTest);

test(
	'This is a test for LPS-112029 and LPS-116841. Questions can be searched by title, content and tag.',
	{tag: ['@LPS-112029', '@LPS-116841']},
	async ({apiHelpers, page, questionsTopicsPage, site, widgetPagePage}) => {
		const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			title: getRandomString(),
		});

		await page.goto('/web' + site.friendlyUrlPath + layout.friendlyURL);
		await widgetPagePage.addPortlet('Questions');

		// Seed a topic and three questions through the API

		const firstQuestionBody = getRandomString();
		const firstQuestionTitle = getRandomString();
		const secondQuestionTitle = getRandomString();
		const tagName = getRandomString().replace(/-/g, '');
		const taggedQuestionTitle = getRandomString();
		const topicName = getRandomString().replace(/-/g, '');

		const messageBoardSection =
			await apiHelpers.headlessDelivery.postSiteMessageBoardSection({
				siteId: site.id,
				title: topicName,
			});

		await apiHelpers.headlessDelivery.postMessageBoardSectionMessageBoardThread(
			{
				articleBody: firstQuestionBody,
				headline: firstQuestionTitle,
				messageBoardSectionId: messageBoardSection.id,
			}
		);
		await apiHelpers.headlessDelivery.postMessageBoardSectionMessageBoardThread(
			{
				articleBody: getRandomString(),
				headline: secondQuestionTitle,
				messageBoardSectionId: messageBoardSection.id,
			}
		);
		await apiHelpers.headlessDelivery.postMessageBoardSectionMessageBoardThread(
			{
				articleBody: getRandomString(),
				headline: taggedQuestionTitle,
				keywords: [tagName],
				messageBoardSectionId: messageBoardSection.id,
			}
		);

		await page.reload();

		await questionsTopicsPage.goToTopic(topicName);

		// The search field and the filter are available

		const searchField = page.getByPlaceholder('Search', {exact: true});

		await expect(searchField).toBeVisible();
		await expect(
			page.getByRole('button', {name: 'Filter and Order'})
		).toBeVisible();

		// Questions can be searched by title

		await searchField.fill(firstQuestionTitle);
		await searchField.press('Enter');

		await expect(page.getByText('1 Result for')).toBeVisible();
		await expect(
			page.getByRole('link', {name: firstQuestionTitle})
		).toBeVisible();
		await expect(
			page.getByRole('link', {name: secondQuestionTitle})
		).toBeHidden();

		// Questions can be searched by content

		await searchField.fill(firstQuestionBody);
		await searchField.press('Enter');

		await expect(
			page.getByRole('link', {name: firstQuestionTitle})
		).toBeVisible();
		await expect(
			page.getByRole('link', {name: secondQuestionTitle})
		).toBeHidden();

		// Questions can be searched by tag

		await searchField.fill(tagName);
		await searchField.press('Enter');

		await expect(
			page.getByRole('link', {name: taggedQuestionTitle})
		).toBeVisible();
		await expect(
			page.getByRole('link', {name: firstQuestionTitle})
		).toBeHidden();

		// A search without matches shows an empty state

		await searchField.fill(getRandomString());
		await searchField.press('Enter');

		await expect(page.getByText('There are no results.')).toBeVisible();

		// Clearing the search restores the full list

		await page.getByRole('button', {name: 'Clear'}).click();

		await expect(
			page.getByRole('link', {name: firstQuestionTitle})
		).toBeVisible();
		await expect(
			page.getByRole('link', {name: secondQuestionTitle})
		).toBeVisible();
		await expect(
			page.getByRole('link', {name: taggedQuestionTitle})
		).toBeVisible();
	}
);

test(
	'This is a test for LPS-112029. Questions can be filtered by answer status.',
	{tag: '@LPS-112029'},
	async ({apiHelpers, page, questionsTopicsPage, site, widgetPagePage}) => {
		const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			title: getRandomString(),
		});

		await page.goto('/web' + site.friendlyUrlPath + layout.friendlyURL);
		await widgetPagePage.addPortlet('Questions');

		// Seed a topic, an answered question and an unanswered question

		const answeredQuestionTitle = getRandomString();
		const topicName = getRandomString().replace(/-/g, '');
		const unansweredQuestionTitle = getRandomString();

		const messageBoardSection =
			await apiHelpers.headlessDelivery.postSiteMessageBoardSection({
				siteId: site.id,
				title: topicName,
			});

		const answeredMessageBoardThread =
			await apiHelpers.headlessDelivery.postMessageBoardSectionMessageBoardThread(
				{
					articleBody: getRandomString(),
					headline: answeredQuestionTitle,
					messageBoardSectionId: messageBoardSection.id,
				}
			);

		await apiHelpers.headlessDelivery.postMessageBoardMessage({
			articleBody: getRandomString(),
			messageBoardThreadId: answeredMessageBoardThread.id,
		});

		await apiHelpers.headlessDelivery.postMessageBoardSectionMessageBoardThread(
			{
				articleBody: getRandomString(),
				headline: unansweredQuestionTitle,
				messageBoardSectionId: messageBoardSection.id,
			}
		);

		await page.reload();

		await questionsTopicsPage.goToTopic(topicName);

		await expect(
			page.getByRole('link', {name: answeredQuestionTitle})
		).toBeVisible();

		// Only unanswered questions show when filtering by No Answer

		await page.getByRole('button', {name: 'Filter and Order'}).click();
		await page.getByText('No Answer', {exact: true}).click();
		await page.getByRole('button', {name: 'Apply'}).click();

		await page.keyboard.press('Escape');

		await expect(
			page.locator('a.questions-title', {
				hasText: unansweredQuestionTitle,
			})
		).toBeVisible();
		await expect(
			page.locator('a.questions-title', {hasText: answeredQuestionTitle})
		).toBeHidden();
	}
);

test(
	'This is a test for LPS-113676. The questions of a topic can be paginated.',
	{tag: '@LPS-113676'},
	async ({apiHelpers, page, questionsTopicsPage, site, widgetPagePage}) => {
		const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			title: getRandomString(),
		});

		await page.goto('/web' + site.friendlyUrlPath + layout.friendlyURL);
		await widgetPagePage.addPortlet('Questions');

		// Seed a topic and ten questions through the API

		const topicName = getRandomString().replace(/-/g, '');

		const messageBoardSection =
			await apiHelpers.headlessDelivery.postSiteMessageBoardSection({
				siteId: site.id,
				title: topicName,
			});

		for (let i = 0; i < 10; i++) {
			await apiHelpers.headlessDelivery.postMessageBoardSectionMessageBoardThread(
				{
					articleBody: getRandomString(),
					headline: getRandomString(),
					messageBoardSectionId: messageBoardSection.id,
				}
			);
		}

		await page.reload();

		await questionsTopicsPage.goToTopic(topicName);

		// All ten questions show on a single page by default

		const paginationResults = page.locator('.pagination-results');

		await expect(paginationResults).toHaveText('Showing 1 to 10 of 10');

		// Reducing the page size paginates the questions

		await page.locator('.pagination-items-per-page button').click();
		await page
			.locator('.dropdown-menu.show')
			.getByText('4 items', {exact: true})
			.click();

		await expect(paginationResults).toHaveText('Showing 1 to 4 of 10');

		const nextPageLink = page.locator(
			'a:has(svg.lexicon-icon-angle-right)'
		);

		await nextPageLink.click();

		await expect(paginationResults).toHaveText('Showing 5 to 8 of 10');

		await nextPageLink.click();

		await expect(paginationResults).toHaveText('Showing 9 to 10 of 10');
	}
);
