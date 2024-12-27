/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {assetPublisherPagesTest} from '../../fixtures/assetPublisherPagesTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginAnalyticsCloudTest} from '../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {productMenuPageTest} from '../../fixtures/productMenuPageTest';
import {uiElementsPageTest} from '../../fixtures/uiElementsTest';
import {webContentDisplayPageTest} from '../../fixtures/webContentDisplayPageTest';
import {liferayConfig} from '../../liferay.config';
import getRandomString from '../../utils/getRandomString';
import {waitForAlert} from '../../utils/waitForAlert';
import {syncAnalyticsCloud} from '../analytics-settings-web/utils/analytics-settings';
import {journalPagesTest} from '../journal-web/fixtures/journalPagesTest';
import {ACPage, navigateToACPageViaURL} from './utils/navigation';
import {createSitePage, navigateToSitePage} from './utils/portal';
import {closeSessions} from './utils/sessions';
import {changeTimeFilter} from './utils/time-filter';

export const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	assetPublisherPagesTest,
	pageEditorPagesTest,
	productMenuPageTest,
	webContentDisplayPageTest,
	journalPagesTest,
	uiElementsPageTest,
	featureFlagsTest({
		'LPD-39304': {enabled: true},
		'LPS-178052': {enabled: true},
	}),
	loginAnalyticsCloudTest(),
	loginTest()
);

const randomString = getRandomString();

const channelName = 'My Property ' + randomString;
const pageTitle = 'My Page';
const siteName = 'My Site ' + randomString;

let channel;
let layout;
let project;
let site;

test.beforeEach(async ({apiHelpers, page}) => {
	site = await apiHelpers.headlessSite.createSite({
		name: siteName,
	});

	layout = await createSitePage({
		apiHelpers,
		pageTitle,
		siteName,
	});

	const result = await syncAnalyticsCloud({
		apiHelpers,
		channelName,
		page,
		siteName,
	});

	channel = result.channel;
	project = result.project;
});

test.afterEach(async ({apiHelpers, page}) => {
	await test.step('Delete channel and delete site on the DXP side', async () => {
		await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
			`[${channel.id}]`,
			project.groupId
		);

		await page.goto(liferayConfig.environment.baseUrl);

		await apiHelpers.headlessSite.deleteSite(String(site.id));
	});
});

test(
	'Web content appears on card shows the pages that the web content appears on.',

	{
		tag: '@LRAC-8456',
	},

	async ({
		apiHelpers,
		journalEditArticlePage,
		journalPage,
		page,
		pageEditorPage,
		uiElementsPage,
		webContentDisplayPage,
	}) => {
		await journalPage.goto(site.friendlyUrlPath);

		const webContentTitle = 'Web Content Title';
		const content = 'Basic Web Content';

		await test.step('Create a page with a web content and publish it', async () => {
			await journalEditArticlePage.goto({siteUrl: site.friendlyUrlPath});

			await journalEditArticlePage.fillTitle(webContentTitle);

			await journalEditArticlePage.fillContent(content);

			await journalEditArticlePage.publishButton.click();

			await waitForAlert(
				page,
				`Success:${webContentTitle} was created successfully.`
			);

			await pageEditorPage.goto(layout, site.friendlyUrlPath);

			await pageEditorPage.addWidget(
				'Content Management',
				'Web Content Display'
			);

			await webContentDisplayPage.addWebContentWithDisplay();

			await uiElementsPage.publishButton.click();
		});

		await test.step('Go to the site page', async () => {
			await navigateToSitePage({
				page,
				pageName: pageTitle,
				siteName,
			});

			await page.waitForTimeout(2000);

			await page.reload();

			await page.waitForTimeout(3000);

			await closeSessions(apiHelpers, page);
		});

		await test.step('Switch to new property in AC and go to WC tab', async () => {
			await navigateToACPageViaURL({
				acPage: ACPage.assetPage,
				channelID: channel.id,
				page,
				projectID: project.groupId,
			});

			await page.getByRole('link', {name: 'Web Content'}).click();
		});

		await test.step('Change the time filter to Last 24 hours', async () => {
			await changeTimeFilter({
				page,
				timeFilterPeriod: 'Last 24 hours',
			});
		});

		await test.step('Go to the WC overview and check appears on metric', async () => {
			await page.getByRole('link', {name: 'Web Content Title'}).click();

			expect(page.getByRole('button', {name: 'Views'})).toBeVisible();
			expect(page.getByText('Asset Appears On')).toBeVisible();
			expect(
				page.getByRole('link', {name: 'My Page - My Site'})
			).toBeVisible();
			expect(
				page.getByRole('cell', {
					name: '/web/my-site',
				})
			).toBeVisible();
		});
	}
);
