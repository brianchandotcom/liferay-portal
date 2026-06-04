/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import getRandomString from '../../../utils/getRandomString';
import {questionsTest} from './fixtures/questionsTest';

const test = mergeTests(questionsTest);

test(
	'This is a test for LPS-131941. The user can view a topic through the history router.',
	{tag: '@LPS-131941'},
	async ({
		apiHelpers,
		page,
		questionsConfigurationPage,
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

		const questionTitle = getRandomString().replace(/-/g, '');
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

		// Set the history router base path to the page

		await questionsConfigurationPage.setBasePathForHistoryRouter(
			layout.friendlyURL
		);

		try {

			// Navigating to a topic updates the URL through the history router

			await page.goto('/web' + site.friendlyUrlPath + layout.friendlyURL);

			await questionsTopicsPage.goToTopic(topicName);

			await expect(page).toHaveURL(
				new RegExp(`${layout.friendlyURL}/questions/${topicName}`)
			);

			// Navigating to a question's details updates the URL too

			await page.getByRole('link', {name: questionTitle}).click();

			await expect(page).toHaveURL(
				new RegExp(
					`${layout.friendlyURL}/questions/${topicName}/${questionTitle}`
				)
			);

			// The history router details URL can be loaded directly

			await page.goto(
				`/web${site.friendlyUrlPath}${layout.friendlyURL}/questions/${topicName}/${questionTitle}`
			);

			await expect(
				page.getByRole('heading', {name: questionTitle})
			).toBeVisible();
		}
		finally {
			await questionsConfigurationPage.setBasePathForHistoryRouter('');
		}

		// The question details remain viewable after restoring the default

		await page.goto('/web' + site.friendlyUrlPath + layout.friendlyURL);

		await questionsTopicsPage.goToTopic(topicName);
		await page.getByRole('link', {name: questionTitle}).click();

		await expect(
			page.getByRole('heading', {name: questionTitle})
		).toBeVisible();
	}
);

test(
	'This is a test for LPS-133798 and LPS-137636. The canonical URL reflects the question, sanitizes special characters and disambiguates duplicates.',
	{tag: ['@LPS-133798', '@LPS-137636']},
	async ({
		apiHelpers,
		page,
		questionsConfigurationPage,
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

		// Seed a topic, a question with special characters and two duplicates

		const duplicateQuestionTitle = getRandomString().replace(/-/g, '');
		const topicName = getRandomString().replace(/-/g, '');
		const uniqueQuestionTitle = getRandomString().replace(/-/g, '');

		const messageBoardSection =
			await apiHelpers.headlessDelivery.postSiteMessageBoardSection({
				siteId: site.id,
				title: topicName,
			});

		for (const headline of [
			`${uniqueQuestionTitle}@#`,
			duplicateQuestionTitle,
			duplicateQuestionTitle,
		]) {
			await apiHelpers.headlessDelivery.postMessageBoardSectionMessageBoardThread(
				{
					articleBody: getRandomString(),
					headline,
					messageBoardSectionId: messageBoardSection.id,
				}
			);
		}

		const canonicalLink = page.locator('link[rel="canonical"]');

		await questionsConfigurationPage.setBasePathForHistoryRouter(
			layout.friendlyURL
		);

		try {
			await page.goto('/web' + site.friendlyUrlPath + layout.friendlyURL);

			// Special characters are sanitized out of the canonical URL

			await questionsTopicsPage.goToTopic(topicName);
			await page
				.getByRole('link', {name: `${uniqueQuestionTitle}@#`})
				.click();

			await expect(canonicalLink).toHaveAttribute(
				'href',
				new RegExp(
					`${layout.friendlyURL}/questions/${topicName}/${uniqueQuestionTitle}`
				)
			);
			await expect(canonicalLink).not.toHaveAttribute('href', /[@#]/);

			// Duplicate titles are disambiguated with a numeric suffix

			await page.goto('/web' + site.friendlyUrlPath + layout.friendlyURL);
			await questionsTopicsPage.goToTopic(topicName);
			await page
				.locator('a.questions-title', {hasText: duplicateQuestionTitle})
				.last()
				.click();

			await expect(canonicalLink).toHaveAttribute(
				'href',
				new RegExp(
					`${layout.friendlyURL}/questions/${topicName}/${duplicateQuestionTitle}`
				)
			);

			// The disambiguated URL can be loaded directly

			await page.goto(
				`/web${site.friendlyUrlPath}${layout.friendlyURL}/questions/${topicName}/${duplicateQuestionTitle}-1`
			);

			await expect(canonicalLink).toHaveAttribute(
				'href',
				new RegExp(
					`${layout.friendlyURL}/questions/${topicName}/${duplicateQuestionTitle}-1`
				)
			);
		}
		finally {
			await questionsConfigurationPage.setBasePathForHistoryRouter('');
		}
	}
);
