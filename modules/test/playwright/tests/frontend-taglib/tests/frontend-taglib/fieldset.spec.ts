/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../../fixtures/loginTest';
import {liferayConfig} from '../../../../liferay.config';
import getRandomString from '../../../../utils/getRandomString';
import {journalPagesTest} from '../../../journal-web/fixtures/journalPagesTest';
import getPageDefinition from '../../../layout-content-page-editor-web/utils/getPageDefinition';
import getWidgetDefinition from '../../../layout-content-page-editor-web/utils/getWidgetDefinition';

export const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	journalPagesTest,
	loginTest()
);

test(
	'Tooltip should be translated correctly',
	{
		tag: '@LPD-43309',
	},
	async ({apiHelpers, page, site}) => {
		let layout: Layout;

		await test.step('Add taglib sample to page', async () => {
			const widgetDefinition = getWidgetDefinition({
				id: getRandomString(),
				widgetName: 'com_liferay_sample_web_portlet_SamplePortlet',
			});

			layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([widgetDefinition]),
				siteId: site.id,
				title: getRandomString(),
			});
		});

		await test.step('Check tooltip is translated', async () => {
			await page.goto(
				`${liferayConfig.environment.baseUrl}/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			const svgElement = page
				.locator('svg[aria-label="Help Text"]')
				.first();

			await expect(svgElement).toBeHidden();
		});
	}
);
