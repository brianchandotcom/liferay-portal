/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginAnalyticsCloudTest} from '../../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../../utils/getRandomString';
import {syncAnalyticsCloud} from '../../analytics-settings-web/main/utils/analytics-settings';
import {createABTest, openABTesSidebar} from './utils/ab-test';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPD-78863': {enabled: true, system: true},
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginAnalyticsCloudTest(),
	loginTest(),
	pageEditorPagesTest
);

test(
	'AB Test panel context changes when the user switches between site pages',
	{tag: '@LPS-97882'},
	async ({apiHelpers, page, pageEditorPage, site}) => {
		let channel;
		let project;

		try {
			const layoutOne = await apiHelpers.headlessDelivery.createSitePage({
				siteId: site.id,
				title: 'Page One',
			});

			const layoutTwo = await apiHelpers.headlessDelivery.createSitePage({
				siteId: site.id,
				title: 'Page Two',
			});

			const result = await syncAnalyticsCloud({
				apiHelpers,
				channelName: 'My Property - ' + getRandomString(),
				page,
				siteName: site.name,
			});

			channel = result.channel;
			project = result.project;

			// Create an experience on Page Two and add an AB Test on that experience

			await page.goto(
				`/web${site.friendlyUrlPath}${layoutTwo.friendlyUrlPath}?p_l_mode=edit`
			);

			await pageEditorPage.createExperience('E1');

			await pageEditorPage.publishPage();

			await page.goto(
				`/web${site.friendlyUrlPath}${layoutTwo.friendlyUrlPath}`
			);

			await clickAndExpectToBeVisible({
				autoClick: true,
				target: page.getByRole('option', {
					name: 'E1 Segment: Anyone Inactive',
				}),
				trigger: page.getByLabel('Experience Selector'),
			});

			await openABTesSidebar(page);

			await createABTest({name: 'AB Test ' + getRandomString(), page});

			// Switch to Page One and assert the panel shows the empty state

			await page.goto(
				`/web${site.friendlyUrlPath}${layoutOne.friendlyUrlPath}`
			);

			await openABTesSidebar(page);

			await expect(page.getByText('Create Test')).toBeVisible();
		}
		finally {
			if (channel && project) {
				await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
					`[${channel.id}]`,
					project.groupId
				);
			}
		}
	}
);
