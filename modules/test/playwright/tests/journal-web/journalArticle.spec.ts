/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';
import {journalPagesTest} from './fixtures/journalPagesTest';

export const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	featureFlagsTest({
		'LPD-16469': true,
	}),
	loginTest(),
	journalPagesTest
);

const PERMISSIONS_LOCATORS = [
	'#guest_ACTION_DELETE',
	'#guest_ACTION_PERMISSIONS',
];

test('LPD-17782: This is a test for bulk permissions of web content', async ({
	journalEditArticlePage,
	journalPage,
	page,
}) => {
	const title1 = getRandomString();
	const title2 = getRandomString();

	await journalEditArticlePage.publishNewBasicArticle(title1);

	const article1 = page
		.locator(
			'#_com_liferay_journal_web_portlet_JournalPortlet_articlesSearchContainer .list-group-item'
		)
		.filter({hasText: title1});

	await expect(article1).toBeVisible();

	await journalEditArticlePage.publishNewBasicArticle(title2);

	const article2 = page
		.locator(
			'#_com_liferay_journal_web_portlet_JournalPortlet_articlesSearchContainer .list-group-item'
		)
		.filter({hasText: title2});

	await expect(article2).toBeVisible();

	await journalPage.setJournalArticlePermissions(
		[article1, article2],
		PERMISSIONS_LOCATORS
	);

	await journalPage.assertJournalArticlePermissions(
		title1,
		PERMISSIONS_LOCATORS
	);
	await journalPage.assertJournalArticlePermissions(
		title2,
		PERMISSIONS_LOCATORS
	);

	await journalPage.deleteJournalArticle(title1);
	await journalPage.deleteJournalArticle(title2);
});
