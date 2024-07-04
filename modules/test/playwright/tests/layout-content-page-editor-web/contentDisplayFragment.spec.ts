/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import getRandomString from '../../utils/getRandomString';
import {journalPagesTest} from '../journal-web/fixtures/journalPagesTest';
import getFragmentDefinition from './utils/getFragmentDefinition';
import getPageDefinition from './utils/getPageDefinition';

export const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	journalPagesTest,
	loginTest(),
	pageEditorPagesTest
);

test('does not show alert when accessing a page with a web content display mapped to a restricted web content', async ({
	apiHelpers,
	browser,
	journalPage,
	page,
	pageEditorPage,
	site,
}) => {

	// Create a web content restricted to site members

	await journalPage.goto(site.friendlyUrlPath);
	await journalPage.goToCreateArticle();

	await journalPage.setArticleViewableBy('Site Members');

	const articleTitle = getRandomString();
	const articleContent = 'My article';

	await journalPage.fillArticleData(articleTitle, articleContent);
	await journalPage.publishArticle();

	await expect(page.getByLabel('Not Visible to Guest Users')).toBeVisible();

	// Create a page with a content display fragment and go to edit mode

	const contentDisplayId = getRandomString();

	const contentDisplayDefinition = getFragmentDefinition({
		fragmentConfig: {
			itemSelector: {
				template: {
					infoItemRendererKey:
						'com.liferay.journal.web.internal.info.item.renderer.JournalArticleFullContentInfoItemRenderer',
				},
			},
		},
		id: contentDisplayId,
		key: 'com.liferay.fragment.internal.renderer.ContentObjectFragmentRenderer',
	});

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([contentDisplayDefinition]),
		siteId: site.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	// Map the content display fragment to the created web content and publish the page

	await pageEditorPage.selectFragment(contentDisplayId);

	await pageEditorPage.setMappedItem('Web Content', articleTitle);

	await pageEditorPage.publishPage();

	// Navigate to page in incognito mode to simulate not logged user

	const context = await browser.newContext();

	const incognitoPage = await context.newPage();

	await incognitoPage.goto(
		`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
	);

	// Check the content is not displayed and no alert is shown

	await expect(incognitoPage.getByText(articleContent)).not.toBeVisible();
	await expect(incognitoPage.getByRole('alert')).not.toBeVisible();
});
