/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {styleBookPageTest} from '../../fixtures/StyleBookPageTest';
import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {fragmentsPagesTest} from '../../fixtures/fragmentPagesTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import getRandomString from '../../utils/getRandomString';
import {journalPagesTest} from '../journal-web/fixtures/journalPagesTest';
import {navigationMenusPagesTest} from '../site-navigation-admin-web/fixtures/navigationMenusPagesTest';

export const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	fragmentsPagesTest,
	journalPagesTest,
	navigationMenusPagesTest,
	pageEditorPagesTest,
	styleBookPageTest,
	loginTest()
);

async function checkBackButtonTitle(page: Page, title: string) {
	await expect(
		page.locator('.control-menu-nav-item').getByTitle(title)
	).toBeVisible();
}

test('back buttons have correct title in different sections', async ({
	fragmentsPage,
	journalEditTemplatePage,
	navigationMenusPage,
	page,
	site,
	styleBooksPage,
}) => {

	// Check back button tooltip on Style Book

	const styleBookName = getRandomString();

	await styleBooksPage.createStyleBook(styleBookName, site.friendlyUrlPath);

	await checkBackButtonTitle(page, 'Go to Style Books');

	// Check also other places
	// Journal template

	await journalEditTemplatePage.goto(site.friendlyUrlPath);

	await checkBackButtonTitle(page, 'Go to Web Content');

	// Navigation menus

	await navigationMenusPage.goto(site.friendlyUrlPath);

	await navigationMenusPage.createNavigationMenu(getRandomString());

	await checkBackButtonTitle(page, 'Go to Navigation Menus');

	// Fragment Administration

	await fragmentsPage.goto(site.friendlyUrlPath);

	const setName = getRandomString();

	await fragmentsPage.createFragmentSet(setName);

	await fragmentsPage.goto(site.friendlyUrlPath);

	await fragmentsPage.createFragment(setName, 'My Fragment');

	await checkBackButtonTitle(page, 'Go to Fragments');
});
