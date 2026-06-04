/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import {createReadStream} from 'fs';
import path from 'path';

import getRandomString from '../../../utils/getRandomString';
import {questionsTest} from './fixtures/questionsTest';

const test = mergeTests(questionsTest);

test(
	'This is a test for LPS-110392. A question can be created with an image from the Document Library.',
	{tag: '@LPS-110392'},
	async ({apiHelpers, page, questionsTopicsPage, site, widgetPagePage}) => {
		const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			title: getRandomString(),
		});

		await page.goto('/web' + site.friendlyUrlPath + layout.friendlyURL);
		await widgetPagePage.addPortlet('Questions');

		// Seed a topic and an image in the Document Library through the API

		const documentToken = getRandomString().replace(/-/g, '');
		const questionTitle = getRandomString();
		const topicName = getRandomString().replace(/-/g, '');

		await apiHelpers.headlessDelivery.postSiteMessageBoardSection({
			siteId: site.id,
			title: topicName,
		});

		await apiHelpers.headlessDelivery.postDocument(
			site.id,
			createReadStream(path.join(__dirname, '/dependencies/image1.jpeg')),
			{
				fileName: `${documentToken}.jpeg`,
				title: documentToken,
			}
		);

		await page.reload();

		// Ask a question with an image from the Document Library

		await questionsTopicsPage.goToTopic(topicName);
		await page.getByRole('button', {name: 'Ask Question'}).first().click();
		await page
			.getByPlaceholder('What is your question?')
			.fill(questionTitle);

		await page.getByLabel('Source').click();
		await page
			.getByLabel(/Rich Text Editor/)
			.getByRole('textbox')
			.fill(getRandomString());
		await page.getByLabel('Source').click();

		await page.getByLabel('Image', {exact: true}).click();

		const itemSelector = page.frameLocator('iframe[title="Select Item"]');

		// Selecting the image closes the item selector

		await expect(async () => {
			await itemSelector
				.locator(`[title*="${documentToken}"]`)
				.click({timeout: 3000});

			await expect(
				page.locator('iframe[title="Select Item"]')
			).toBeHidden({timeout: 3000});
		}).toPass();

		await page.getByLabel('Post Your Question').click();

		// The image is displayed in the question details

		await page.getByRole('link', {name: questionTitle}).click();

		await expect(
			page.locator(`img[src*="${documentToken}"]`)
		).toBeVisible();
	}
);
