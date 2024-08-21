/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginAnalyticsCloudTest} from '../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../fixtures/loginTest';
import {liferayConfig} from '../../liferay.config';
import getRandomString from '../../utils/getRandomString';
import {
	connectToAnalyticsCloud,
	disconnectFromAnalyticsCloud,
	goNextStep,
	goToAnalyticsCloudInstanceSettings,
} from '../analytics-settings-web/utils/analytics-settings';
import {blogsPagesTest} from '../blogs-web/fixtures/blogsPagesTest';
import {journalPagesTest} from '../journal-web/fixtures/journalPagesTest';
import {JournalPage} from '../journal-web/pages/JournalPage';
import {createDataSource} from '../osb-faro-web/utils/data-source';
import {acceptsCookiesBanner} from '../osb-faro-web/utils/portal';
import {contentDashboardPagesTest} from './fixtures/contentDashboardPagesTest';

async function connectToAnalyticsCloudWithNoSiteSynced(page: Page) {
	await createDataSource(page);

	await goToAnalyticsCloudInstanceSettings(page);

	await acceptsCookiesBanner(page);

	await disconnectFromAnalyticsCloud(page);

	await connectToAnalyticsCloud(page);

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
	journalPage,
	page,
}: {
	articleTitle: string;
	assetLibraryName: string;
	journalPage: JournalPage;
	page: Page;
}) {
	await page.getByRole('link', {name: assetLibraryName}).click();

	await page.getByRole('link', {name: 'Web Content'}).click();

	await page.getByRole('button', {name: 'New'}).click();

	await page.getByRole('menuitem', {name: 'Basic Web Content'}).click();

	await journalPage.fillArticleData(articleTitle, getRandomString());

	await page.evaluate((articleTitle) => {
		const element = document.querySelector(
			'[data-field-name=titleMapAsXML]'
		);
		if (element) {
			element.setAttribute('value', articleTitle);
		}
	}, articleTitle);

	await journalPage.publishArticle();

	await expect(
		page.locator(`dd[data-title="${articleTitle}"]`)
	).toBeVisible();
}

async function deleteAssetLibraries(page: Page) {
	await goToAssetLibraries(page);

	await page.waitForTimeout(3000);

	await page.getByLabel('Select All Items on the Page').click();

	page.once('dialog', (dialog) => {
		dialog.accept().catch(() => {});
	});
	await page.getByRole('button', {name: 'Delete'}).click();
}

async function goToAssetLibraries(page: Page) {
	await page.getByLabel('Open Applications MenuCtrl+Alt+A').click();

	await page.getByRole('tab', {name: 'Applications'}).click();

	await page.getByRole('menuitem', {name: 'Asset Libraries'}).click();
}

async function goToContentPerformanceTab(page: Page) {
	const dropDownButton = await page
		.locator('.lfr-entry-action-column .dropdown-action button')
		.first();

	await dropDownButton.click();

	const showInfoButton = await page.locator('[data-action=showInfo]').first();

	await showInfoButton.click();

	const activeTab = await page.locator('.nav-tabs .active');

	expect(await activeTab.textContent()).toBe('Details');

	await page.getByRole('tab', {name: 'Performance'}).click();
}

async function searchForContent({page, title}: {page: Page; title: string}) {
	const searchBar = await page.getByPlaceholder('Search for');

	await searchBar.fill(title);

	await page.keyboard.press('Enter');
}

export const test = mergeTests(
	blogsPagesTest,
	contentDashboardPagesTest,
	featureFlagsTest({
		'LPD-28830': true,
	}),
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

	await contentDashboardPage.goto(site.friendlyUrlPath);

	const searchBar = await page.getByPlaceholder('Search for');

	await searchBar.fill(title);

	await page.keyboard.press('Enter');

	await goToContentPerformanceTab(page);

	await expect(
		page.getByText(
			'In order to view asset performance, your Liferay DXP instance has to be connected with Liferay Analytics Cloud.'
		)
	).toBeVisible();

	await page.locator('.tab-content a').click();

	await expect(page.getByText('Connect Analytics Cloud')).toBeVisible();
});

test('Displays empty state when asset belongs to an asset library with no site connected', async ({
	contentDashboardPage,
	journalPage,
	page,
	site,
}) => {
	await connectToAnalyticsCloudWithNoSiteSynced(page);

	await page.goto(liferayConfig.environment.baseUrl);

	await goToAssetLibraries(page);

	const assetLibraryName = getRandomString();
	const articleTitle = getRandomString();

	await createAssetLibrary({name: assetLibraryName, page});

	await createWebContentIntoAssetLibrary({
		articleTitle,
		assetLibraryName,
		journalPage,
		page,
	});

	await contentDashboardPage.goto(site.friendlyUrlPath);

	await searchForContent({page, title: articleTitle});

	await goToContentPerformanceTab(page);

	await expect(
		page.getByText(
			'In order to view asset performance, connect sites that are synced to Analytics Cloud to your asset library.'
		)
	).toBeVisible();

	await page.locator('.tab-content a').click();

	await expect(page.getByText('Connected Sites')).toBeVisible();

	await deleteAssetLibraries(page);
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

	await contentDashboardPage.goto(site.friendlyUrlPath);

	await searchForContent({page, title: blogTitle});

	await goToContentPerformanceTab(page);

	await expect(
		page.getByText(
			'In order to view asset performance, your sites have to be synced to Liferay Analytics Cloud.'
		)
	).toBeVisible();

	await page.locator('.tab-content a').click();

	await expect(page.getByText('Available Properties').first()).toBeVisible();
});
