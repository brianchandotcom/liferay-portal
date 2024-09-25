/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
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
	loginTest(),
	pageEditorPagesTest
);

test('Use cell renderer client extension in the frontend data set', async ({
	apiHelpers,
	page,
	site,
}) => {
	let layout: Layout;

	await test.step('Create a content site and the frontend data set sample widget', async () => {
		const widgetDefinition = getWidgetDefinition({
			id: getRandomString(),
			widgetName:
				'com_liferay_frontend_data_set_sample_web_internal_portlet_FDSSamplePortlet',
		});

		layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([widgetDefinition]),
			siteId: site.id,
			title: getRandomString(),
		});
	});

	await test.step('Select Customized tab', async () => {
		await page.goto(
			`${liferayConfig.environment.baseUrl}/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
		);

		const tabHeading = page.getByRole('tablist').getByText('Customized');

		await expect(tabHeading).toBeInViewport();

		await tabHeading.click();
	});

	await test.step('Assert that the cell renderer is invoked and the apple emoji is visible', async () => {
		const firstColorCell = page
			.locator('.dnd-tbody > div > div:nth-child(7)')
			.first();

		await expect(firstColorCell).toContainText('🍏');
	});
});
