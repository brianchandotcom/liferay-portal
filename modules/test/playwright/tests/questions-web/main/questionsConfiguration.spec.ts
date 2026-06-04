/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import getRandomString from '../../../utils/getRandomString';
import {waitForAlert} from '../../../utils/waitForAlert';
import {questionsTest} from './fixtures/questionsTest';

const test = mergeTests(questionsTest);

test(
	'This is a test for LPS-116844. The topic navigation can be configured to show cards or a breadcrumb.',
	{tag: '@LPS-116844'},
	async ({apiHelpers, page, site, widgetPagePage}) => {
		const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			title: getRandomString(),
		});
		const layoutURL = '/web' + site.friendlyUrlPath + layout.friendlyURL;

		await page.goto(layoutURL);
		await widgetPagePage.addPortlet('Questions');

		// Seed a topic through the API

		const topicName = getRandomString().replace(/-/g, '');

		await apiHelpers.headlessDelivery.postSiteMessageBoardSection({
			siteId: site.id,
			title: topicName,
		});

		const configIFrame = page.frameLocator('iframe[title*="Questions"]');
		const topicCard = page.locator('.questions-card', {hasText: topicName});

		const setShowCards = async (enabled: boolean) => {
			await widgetPagePage.clickOnAction('Questions', 'Configuration');

			const checkbox = configIFrame.getByLabel(
				'Show Cards for Topic Navigation'
			);

			if (enabled) {
				await checkbox.check();
			}
			else {
				await checkbox.uncheck();
			}

			await configIFrame.getByRole('button', {name: 'Save'}).click();

			await waitForAlert(configIFrame, '');

			await page
				.locator('.modal-header')
				.getByLabel('Close', {exact: true})
				.click();
		};

		// Enabling cards shows the topics as cards on the landing page

		await setShowCards(true);

		await page.goto(layoutURL);

		await expect(topicCard).toBeVisible();

		// Disabling cards shows the topics through the breadcrumb instead

		await setShowCards(false);

		await page.goto(layoutURL);

		await expect(topicCard).toBeHidden();
		await expect(
			page.locator('.breadcrumb svg[class*="lexicon-icon-home"]')
		).toBeVisible();
	}
);

test(
	'This is a test for LPS-116845. Configuring a root topic shows its subtopics as the landing page.',
	{tag: '@LPS-116845'},
	async ({apiHelpers, page, site, widgetPagePage}) => {
		const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			title: getRandomString(),
		});
		const layoutURL = '/web' + site.friendlyUrlPath + layout.friendlyURL;

		await page.goto(layoutURL);
		await widgetPagePage.addPortlet('Questions');

		// Seed a root topic with two subtopics through the API

		const rootTopicName = getRandomString().replace(/-/g, '');
		const subTopicAName = getRandomString().replace(/-/g, '');
		const subTopicBName = getRandomString().replace(/-/g, '');

		const rootMessageBoardSection =
			await apiHelpers.headlessDelivery.postSiteMessageBoardSection({
				siteId: site.id,
				title: rootTopicName,
			});

		for (const title of [subTopicAName, subTopicBName]) {
			await apiHelpers.headlessDelivery.postMessageBoardSectionMessageBoardSection(
				{
					parentMessageBoardSectionId: rootMessageBoardSection.id,
					title,
				}
			);
		}

		const configIFrame = page.frameLocator('iframe[title*="Questions"]');

		// Show cards and configure the root topic to the seeded root

		await widgetPagePage.clickOnAction('Questions', 'Configuration');

		await configIFrame
			.getByLabel('Show Cards for Topic Navigation')
			.check();
		await configIFrame.locator('[id$="selectRootTopicButton"]').click();

		await configIFrame
			.frameLocator('iframe[title="Select Category"]')
			.locator('tr', {hasText: rootTopicName})
			.getByRole('button', {exact: true, name: 'Select'})
			.click();

		await configIFrame.getByRole('button', {name: 'Save'}).click();

		await waitForAlert(configIFrame, '');

		await page
			.locator('.modal-header')
			.getByLabel('Close', {exact: true})
			.click();

		// The landing page now shows the subtopics, not the root topic

		await page.goto(layoutURL);

		await expect(
			page.locator('.questions-card', {hasText: subTopicAName})
		).toBeVisible();
		await expect(
			page.locator('.questions-card', {hasText: subTopicBName})
		).toBeVisible();
		await expect(
			page.locator('.questions-card', {hasText: rootTopicName})
		).toBeHidden();
	}
);
