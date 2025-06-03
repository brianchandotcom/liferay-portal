/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import {
	createSitePage,
	navigateToSitePage,
} from '../../osb-faro-web/main/utils/portal';
import {Analytics, Event} from './utils/analytics';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	loginTest()
);

let site;
const siteName = getRandomString();

test.beforeEach(async ({apiHelpers}) => {
	site = await apiHelpers.headlessSite.createSite({
		name: siteName,
	});
});

test.afterEach(async ({apiHelpers}) => {
	await test.step('Delete site on de DXP side', async () => {
		await apiHelpers.headlessSite.deleteSite(site.id);
	});
});

test(
	'Verify events after navigating by SPA',
	{
		tag: '@LPD-56895',
	},
	async ({apiHelpers, page}) => {
		await test.step('test', async () => {
			const pageTitle1 = 'MyPage 1';

			await test.step('Create My Page 1', async () => {
				await createSitePage({
					apiHelpers,
					pageTitle: pageTitle1,
					siteName,
				});
			});

			const pageTitle2 = 'MyPage 2';

			await test.step('Create My Page 2', async () => {
				await createSitePage({
					apiHelpers,
					pageTitle: pageTitle2,
					siteName,
				});
			});

			await test.step('Go to My Page 1', async () => {
				await navigateToSitePage({
					page,
					pageName: pageTitle1,
					siteName,
				});
			});

			await test.step('Check the pageViewed event on My Page 1', async () => {
				const analytics = new Analytics(page);

				const pageViewedEvent = (await analytics.getEvents(
					'pageViewed'
				)) as Event;

				expect(pageViewedEvent).toBeTruthy();
			});

			await test.step('Go to My Page 2', async () => {
				await page.getByRole('menuitem', {name: 'MyPage 2'}).click();
			});

			await test.step('Check the pageViewed event on My Page 2', async () => {
				const analytics = new Analytics(page);

				const pageViewedEvent = (await analytics.getEvents(
					'pageViewed'
				)) as Event;

				expect(pageViewedEvent).toBeTruthy();
			});
		});
	}
);
