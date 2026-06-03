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

		// Seed a topic through the API

		const topicName = getRandomString().replace(/-/g, '');

		await apiHelpers.headlessDelivery.postSiteMessageBoardSection({
			siteId: site.id,
			title: topicName,
		});

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

			// The history router URL can be loaded directly

			await page.goto(
				`/web${site.friendlyUrlPath}${layout.friendlyURL}/questions/${topicName}`
			);

			await expect(page.getByText('This topic is empty.')).toBeVisible();
		}
		finally {
			await questionsConfigurationPage.setBasePathForHistoryRouter('');
		}
	}
);
