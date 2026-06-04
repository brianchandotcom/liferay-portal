/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import getRandomString from '../../../utils/getRandomString';
import {questionsTest} from './fixtures/questionsTest';

const test = mergeTests(questionsTest);

test(
	'This is a test for LPS-110458 and LPS-122831. The Activity tab lists responses and opens the question.',
	{tag: ['@LPS-110458', '@LPS-122831']},
	async ({apiHelpers, page, site, widgetPagePage}) => {
		const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			title: getRandomString(),
			typeSettings: 'layout-template-id=1_column\n',
		});

		await page.goto('/web' + site.friendlyUrlPath + layout.friendlyURL);
		await widgetPagePage.addPortlet('Questions');

		// Seed a topic, a question and an answer through the API. Bodies are
		// kept short and distinctive so the Activity previews are not truncated.

		const randomToken = () =>
			getRandomString().replace(/-/g, '').slice(0, 6);

		const answerBody = `answer${randomToken()}`;
		const questionTitle = getRandomString();
		const topicName = getRandomString().replace(/-/g, '');

		const messageBoardSection =
			await apiHelpers.headlessDelivery.postSiteMessageBoardSection({
				siteId: site.id,
				title: topicName,
			});

		const messageBoardThread =
			await apiHelpers.headlessDelivery.postMessageBoardSectionMessageBoardThread(
				{
					articleBody: getRandomString(),
					headline: questionTitle,
					messageBoardSectionId: messageBoardSection.id,
				}
			);

		await apiHelpers.headlessDelivery.postMessageBoardMessage({
			articleBody: answerBody,
			messageBoardThreadId: messageBoardThread.id,
		});

		await page.reload();

		// The Activity tab lists the question and its answer

		await page
			.locator('.navbar')
			.getByText('Activity', {exact: true})
			.click();

		await expect(page.getByText(questionTitle).first()).toBeVisible();
		await expect(page.getByText(answerBody)).toBeVisible();

		// The question can be opened from the Activity tab

		await page.getByRole('link', {name: questionTitle}).first().click();

		await expect(
			page.locator('.questions-container h1', {hasText: questionTitle})
		).toBeVisible();
	}
);
