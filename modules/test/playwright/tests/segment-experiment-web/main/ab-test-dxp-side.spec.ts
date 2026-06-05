/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedChannelTest} from '../../../fixtures/isolatedChannelTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginAnalyticsCloudTest} from '../../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../../fixtures/loginTest';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../../utils/getRandomString';
import {waitForAlert} from '../../../utils/waitForAlert';
import {syncAnalyticsCloudViaAPI} from '../../analytics-settings-web/main/utils/analytics-settings';
import getFragmentDefinition from '../../layout-content-page-editor-web/main/utils/getFragmentDefinition';
import getPageDefinition from '../../layout-content-page-editor-web/main/utils/getPageDefinition';
import {
	ACPage,
	navigateToACPageViaURL,
} from '../../osb-faro-web/main/utils/navigation';
import {
	checkEmptyStateOnDXPSide,
	createABTest,
	createVariant,
	openABTesSidebar,
	runTest,
	terminateTest,
} from './utils/ab-test';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPD-78863': {enabled: true, system: true},
		'LPS-178052': {enabled: true},
	}),
	isolatedChannelTest,
	isolatedSiteTest,
	loginAnalyticsCloudTest(),
	loginTest()
);

test(
	'A terminated AB test deleted on the DXP side clears the test list in both DXP and Analytics Cloud',
	{
		tag: '@LRAC-14544',
	},
	async ({analyticsChannel: channel, apiHelpers, page, project, site}) => {
		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				getFragmentDefinition({
					id: getRandomString(),
					key: 'BASIC_COMPONENT-heading',
				}),
			]),
			siteId: site.id,
			title: 'My Page',
		});

		await syncAnalyticsCloudViaAPI({
			apiHelpers,
			channel,
			project,
			siteId: Number(site.id),
		});

		const testName = 'AB Test ' + getRandomString();

		await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

		await page.waitForSelector('.segments-experiment-icon');

		await openABTesSidebar(page);

		await createABTest({name: testName, page});

		await createVariant({name: 'Variant name', page});

		await runTest(page);

		// The running test appears in the Analytics Cloud Tests list

		await navigateToACPageViaURL({
			acPage: ACPage.testPage,
			channelID: channel.id,
			page,
			projectID: project.groupId,
		});

		await expect(page.getByText(testName)).toBeVisible();

		// Back on DXP, terminate and delete the test

		await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

		await openABTesSidebar(page);

		await terminateTest(page);

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('button', {name: 'Delete'}),
			trigger: page.getByTestId('delete-variant'),
		});

		await waitForAlert(page);

		await checkEmptyStateOnDXPSide(page);

		// The Analytics Cloud Tests list is empty again

		await navigateToACPageViaURL({
			acPage: ACPage.testPage,
			channelID: channel.id,
			page,
			projectID: project.groupId,
		});

		await expect(page.getByText('There are no tests found.')).toBeVisible();
	}
);
