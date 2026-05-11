/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import getFragmentDefinition from '../../layout-content-page-editor-web/main/utils/getFragmentDefinition';
import getPageDefinition from '../../layout-content-page-editor-web/main/utils/getPageDefinition';
import {Analytics, Event} from './utils/analytics';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest()
);

test(
	'Verify events after navigating by SPA',
	{
		tag: '@LPD-56895',
	},
	async ({apiHelpers, page, site}) => {
		const layout1 = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				getFragmentDefinition({
					id: getRandomString(),
					key: 'BASIC_COMPONENT-heading',
				}),
			]),
			siteId: site.id,
			title: 'MyPage 1',
		});

		const layout2 = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				getFragmentDefinition({
					id: getRandomString(),
					key: 'BASIC_COMPONENT-heading',
				}),
			]),
			siteId: site.id,
			title: 'MyPage 2',
		});

		await test.step('Go to My Page 1', async () => {
			await page.goto(
				`/web${site.friendlyUrlPath}${layout1.friendlyUrlPath}`
			);
		});

		await test.step('Check the pageViewed event on My Page 1', async () => {
			const analytics = new Analytics(page);

			const pageViewedEvent = (await analytics.getEvents(
				'pageViewed'
			)) as Event;

			expect(pageViewedEvent).toBeTruthy();
		});

		await test.step('Go to My Page 2', async () => {
			await page.goto(
				`/web${site.friendlyUrlPath}${layout2.friendlyUrlPath}`
			);
		});

		await test.step('Check the pageViewed event on My Page 2', async () => {
			const analytics = new Analytics(page);

			const pageViewedEvent = (await analytics.getEvents(
				'pageViewed'
			)) as Event;

			expect(pageViewedEvent).toBeTruthy();
		});
	}
);
