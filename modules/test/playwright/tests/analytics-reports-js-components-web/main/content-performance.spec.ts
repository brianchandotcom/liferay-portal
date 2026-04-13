/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {globalMenuPagesTest} from '../../../fixtures/globalMenuPagesTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginAnalyticsCloudTest} from '../../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../../fixtures/loginTest';
import {liferayConfig} from '../../../liferay.config';
import {GlobalMenuPage} from '../../../pages/product-navigation-applications-menu/GlobalMenuPage';
import getRandomString from '../../../utils/getRandomString';
import {
	connectToAnalyticsCloud,
	disconnectFromAnalyticsCloud,
	goNextStep,
	goToAnalyticsCloudInstanceSettings,
} from '../../analytics-settings-web/main/utils/analytics-settings';
import {blogsPagesTest} from '../../blogs-web/main/fixtures/blogsPagesTest';
import {contentDashboardPagesTest} from '../../content-dashboard-web/main/fixtures/contentDashboardPagesTest';
import {journalPagesTest} from '../../journal-web/main/fixtures/journalPagesTest';
import {JournalEditArticlePage} from '../../journal-web/main/pages/JournalEditArticlePage';
import {createDataSource} from '../../osb-faro-web/main/utils/data-source';
import {acceptsCookiesBanner} from '../../osb-faro-web/main/utils/portal';

async function connectToAnalyticsCloudWithNoSiteSynced(page: Page) {
	const {token} = await createDataSource(page);

	await goToAnalyticsCloudInstanceSettings(page);

	await acceptsCookiesBanner(page);

	await disconnectFromAnalyticsCloud(page);

	await connectToAnalyticsCloud(page, {token});

	await goNextStep(page);

	await goNextStep(page);

	await page.getByRole('button', {name: 'Finish'}).click();
}

async function createAssetLibrary({name, page}: {name: string; page: Page}) {
	await page.getByRole('button', {name: 'Add'}).click();

	await page.getByLabel('Name').fill(name);

	await page.getByRole('button', {name: 'Save'}).click();

	await page.getByRole('link', {name: 'Back'}).click();
}

async function createWebContentIntoAssetLibrary({
	articleTitle,
	assetLibraryName,
	journalEditArticlePage,
	page,
}: {
	articleTitle: string;
	assetLibraryName: string;
	journalEditArticlePage: JournalEditArticlePage;
	page: Page;
}) {
	await page.getByRole('link', {name: assetLibraryName}).click();

	await page.getByRole('link', {name: 'Web Content'}).click();

	await page.getByRole('button', {name: 'New'}).click();

	await page.getByRole('menuitem', {name: 'Basic Web Content'}).click();

	await journalEditArticlePage.createAndPublishBasicArticle(articleTitle);

	await expect(
		page.locator(`dd[data-title="${articleTitle}"]`)
	).toBeVisible();
}

async function deleteAssetLibraries(
	page: Page,
	globalMenuPage: GlobalMenuPage
) {
	await goToAssetLibraries(globalMenuPage);

	await page.waitForTimeout(3000);

	await page.getByLabel('Select All Items on the Page').click();

	page.once('dialog', (dialog) => {
		dialog.accept().catch(() => {});
	});
	await page.getByRole('button', {name: 'Delete'}).click();
}

async function goToAssetLibraries(globalMenuPage: GlobalMenuPage) {
	await globalMenuPage.goToApplications('Asset Libraries');
}

export const test = mergeTests(
	blogsPagesTest,
	contentDashboardPagesTest,
	featureFlagsTest({
		'LPD-11235': {enabled: true},
		'LPD-36105': {enabled: true},
	}),
	globalMenuPagesTest,
	isolatedSiteTest,
	journalPagesTest,
	loginAnalyticsCloudTest(),
	loginTest()
);

test('Displays empty state when Analytics Cloud is not connected', async ({
	blogsEditBlogEntryPage,
	contentDashboardPage,
	page,
	site,
}) => {
	await goToAnalyticsCloudInstanceSettings(page);

	await acceptsCookiesBanner(page);

	await disconnectFromAnalyticsCloud(page);

	await blogsEditBlogEntryPage.goto(site.friendlyUrlPath);

	const title = getRandomString();

	await blogsEditBlogEntryPage.editBlogEntry({
		content: getRandomString(),
		title,
	});

	await contentDashboardPage.goToCurrentTab({
		assetTitle: title,
		siteUrl: site.friendlyUrlPath,
		tabName: 'Performance',
	});

	await expect(
		page.getByText(
			'In order to view asset performance, your Liferay instance has to be connected with Liferay Analytics Cloud.'
		)
	).toBeVisible();

	await page.locator('.tab-content a').click();

	await expect(page.getByText('Connect to Analytics Cloud')).toBeVisible();
});

test('Displays empty state when asset belongs to an asset library with no site connected', async ({
	contentDashboardPage,
	globalMenuPage,
	journalEditArticlePage,
	page,
	site,
}) => {
	await connectToAnalyticsCloudWithNoSiteSynced(page);

	await page.goto(liferayConfig.environment.baseUrl);

	await goToAssetLibraries(globalMenuPage);

	const assetLibraryName = getRandomString();
	const articleTitle = getRandomString();

	await createAssetLibrary({name: assetLibraryName, page});

	await createWebContentIntoAssetLibrary({
		articleTitle,
		assetLibraryName,
		journalEditArticlePage,
		page,
	});

	await contentDashboardPage.goToCurrentTab({
		assetTitle: articleTitle,
		siteUrl: site.friendlyUrlPath,
		tabName: 'Performance',
	});

	await expect(
		page.getByText(
			'In order to view asset performance, connect sites that are synced to Analytics Cloud to your asset library.'
		)
	).toBeVisible();

	await page.locator('.tab-content a').click();

	await expect(page.getByText('Connected Sites')).toBeVisible();

	await deleteAssetLibraries(page, globalMenuPage);
});

test('Displays empty state when site is not synced to Analytics Cloud', async ({
	blogsEditBlogEntryPage,
	contentDashboardPage,
	page,
	site,
}) => {
	await blogsEditBlogEntryPage.goto(site.friendlyUrlPath);

	const blogTitle = getRandomString();

	await blogsEditBlogEntryPage.editBlogEntry({
		content: getRandomString(),
		title: blogTitle,
	});

	await connectToAnalyticsCloudWithNoSiteSynced(page);

	await contentDashboardPage.goToCurrentTab({
		assetTitle: blogTitle,
		siteUrl: site.friendlyUrlPath,
		tabName: 'Performance',
	});

	await expect(
		page.getByText(
			'In order to view asset performance, your sites have to be synced to Liferay Analytics Cloud.'
		)
	).toBeVisible();

	await page.locator('.tab-content a').click();

	await expect(page.getByText('Available Properties').first()).toBeVisible();
});
