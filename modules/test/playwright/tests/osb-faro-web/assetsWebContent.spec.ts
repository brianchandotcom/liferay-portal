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
import {journalPagesTest} from '../journal-web/fixtures/journalPagesTest';
import {createChannel} from './utils/channel';
import {ACPage, navigateToACPageViaURL} from './utils/navigation';
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

let channel;
let project;

test.beforeEach(async ({apiHelpers}) => {
	const result = await createChannel({
		apiHelpers,
		channelName,
	});

	channel = result.channel;
	project = result.project;
});

test.afterEach(async ({apiHelpers, page}) => {
	await test.step('Delete channel and delete site on the DXP side', async () => {
		await page.goto(liferayConfig.environment.baseUrl);

		await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
			`[${channel.id}]`,
			project.groupId
		);
	});
});

test(
	'Web content appears on card shows the pages that the web content appears on.',

	{
		tag: '@LRAC-8456',
	},

	async ({apiHelpers, page}) => {
		const webContentTitle = 'Web Content Title';

		const date1 = new Date();

		await apiHelpers.jsonWebServicesOSBAsah.createEvents([
			{
				applicationId: 'WebContent',
				assetId: '1',
				assetTitle: webContentTitle,
				canonicalUrl: '/web/my-site',
				channelId: channel.id,
				eventDate: date1.toISOString(),
				eventId: 'webContentViewed',
				title: pageTitle,
				userId: 'user1',
			},
		]);

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

			await expect(
				page.getByRole('button', {name: 'Views'})
			).toBeVisible();
			await expect(page.getByText('Asset Appears On')).toBeVisible();
			await expect(
				page.getByRole('link', {name: 'My Page'})
			).toBeVisible();
			await expect(
				page.getByRole('cell', {
					name: '/web/my-site',
				})
			).toBeVisible();
		});
	}
);
