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
import {waitForAlert} from '../../../utils/waitForAlert';
import {syncAnalyticsCloud} from '../../analytics-settings-web/main/utils/analytics-settings';
import getFragmentDefinition from '../../layout-content-page-editor-web/main/utils/getFragmentDefinition';
import getGridDefinition from '../../layout-content-page-editor-web/main/utils/getGridDefinition';
import getPageDefinition from '../../layout-content-page-editor-web/main/utils/getPageDefinition';
import {
	createABTest,
	createVariant,
	getClickElementId,
	openABTesSidebar,
	selectClickElement,
} from './utils/ab-test';

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
	'AB Test by Click goal supports element selection, ID box interactions, edit target, and variant persistence',
	{tag: ['@LPS-119475', '@LPS-152323', '@LPS-96791']},
	async ({apiHelpers, page, site}) => {
		let channel;
		let project;

		try {
			const gridDefinition = getGridDefinition({
				columns: [
					{
						pageElements: [
							getFragmentDefinition({
								id: getRandomString(),
								key: 'BASIC_COMPONENT-button',
							}),
						],
						size: 6,
					},
					{
						pageElements: [
							getFragmentDefinition({
								id: getRandomString(),
								key: 'BASIC_COMPONENT-button',
							}),
						],
						size: 6,
					},
				],
				id: getRandomString(),
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([gridDefinition]),
				siteId: site.id,
				title: getRandomString(),
			});

			const result = await syncAnalyticsCloud({
				apiHelpers,
				channelName: 'My Property - ' + getRandomString(),
				page,
				siteName: site.name,
			});

			channel = result.channel;
			project = result.project;

			await page.goto(
				`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			await openABTesSidebar(page);

			await createABTest({
				goal: 'Click',
				name: 'AB Test ' + getRandomString(),
				page,
			});

			// Discover the IDs of both buttons via the picker

			const firstButtonId = await selectClickElement({page});

			await expect(page.locator('#clickableElement')).toHaveValue(
				firstButtonId
			);

			// Edit the target via Change Clickable Element

			await clickAndExpectToBeVisible({
				autoClick: true,
				target: page.locator(
					'.lfr-segments-experiment-click-goal-target-delete'
				),
				trigger: page.getByText('Change Clickable Element'),
			});

			await clickAndExpectToBeVisible({
				target: page
					.locator(
						'.lfr-segments-experiment-click-goal-target-popover'
					)
					.getByRole('button'),
				trigger: page
					.locator(
						'.lfr-segments-experiment-click-goal-target-overlay'
					)
					.nth(1),
			});

			const secondButtonId = await getClickElementId({page});

			await page
				.locator('.lfr-segments-experiment-click-goal-target-popover')
				.getByRole('button')
				.click();

			await waitForAlert(page);

			await expect(page.locator('#clickableElement')).toHaveValue(
				secondButtonId
			);

			// Variant ID persistence: create variant and switch between control and variant

			await createVariant({name: 'V1', page});

			await clickAndExpectToBeVisible({
				autoClick: true,
				target: page.locator('[data-title="Control"]'),
				trigger: page.locator('[data-title="V1"]'),
			});

			await expect(page.locator('#clickableElement')).toHaveValue(
				secondButtonId
			);
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
