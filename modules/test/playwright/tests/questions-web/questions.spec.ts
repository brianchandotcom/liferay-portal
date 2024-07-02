/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {isolatedLayoutTest} from '../../fixtures/isolatedLayoutTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageViewModePagesTest} from '../../fixtures/pageViewModePagesTest';
import getRandomString from '../../utils/getRandomString';
import {questionsPagesTest} from './fixtures/questionsPagesTest';

export const baseTest = mergeTests(
	apiHelpersTest,
	isolatedLayoutTest({publish: false, type: 'portlet'}),
	isolatedSiteTest,
	loginTest(),
	questionsPagesTest,
	pageViewModePagesTest
);

const tagWithSpaces = mergeTests(baseTest);

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
