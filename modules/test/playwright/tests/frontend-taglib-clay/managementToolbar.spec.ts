/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {liferayConfig} from '../../liferay.config';
import getRandomString from '../../utils/getRandomString';
import getPageDefinition from '../layout-content-page-editor-web/utils/getPageDefinition';
import getWidgetDefinition from '../layout-content-page-editor-web/utils/getWidgetDefinition';

export const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest()
);

test.describe('Management Toolbar With Results', () => {
	test('Clear button has a cursor of type pointer', async ({
		apiHelpers,
		page,
		site,
	}) => {
		let layout: Layout;

		await test.step('Create a content site and the frontend taglib clay widget', async () => {
			const widgetDefinition = getWidgetDefinition({
				id: getRandomString(),
				widgetName:
					'com_liferay_clay_sample_web_portlet_ClaySamplePortlet',
			});

			layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([widgetDefinition]),
				siteId: site.id,
				title: getRandomString(),
			});
		});

		await test.step('Select Management Toolbars tab ', async () => {
			await page.goto(
				`${liferayConfig.environment.baseUrl}/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			const tabHeading = page
				.getByRole('tablist')
				.getByText('Management Toolbars');

			await expect(tabHeading).toBeInViewport();

			await tabHeading.click();
		});

		await test.step('Select Management Toolbar with results section', async () => {
			const clearButton = page
				.locator('#managementToolbarWithResultsBar')
				.getByLabel('Clear');

			await expect(clearButton).toBeVisible();

			const cursorType = await clearButton.evaluate((element) =>
				window.getComputedStyle(element).getPropertyValue('cursor')
			);

			await expect(cursorType).toEqual('pointer');
		});
	});
});
