/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';
import {pageEditorPagesTest} from '../layout-content-page-editor-web/fixtures/pageEditorPagesTest';

export const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-164563': true,
	}),
	loginTest(),
	pageEditorPagesTest
);

test('Add the frontend data set sample widget', async ({apiHelpers, page}) => {
	let site: Site;

	await test.step('Create a content site and the frontend data set sample widget', async () => {
		site = await apiHelpers.headlessSite.createSite('Test Site');

		await apiHelpers.headlessDelivery.createSitePage(
			site.id,
			getRandomString(),
			{
				pageElement: {
					definition: {
						widgetInstance: {
							widgetName:
								'com_liferay_frontend_data_set_sample_web_internal_portlet_FDSSamplePortlet',
						},
					},
					id: getRandomString(),
					type: 'Widget',
				},
			}
		);
	});

	await test.step('Assert that the filter client extension is added', async () => {
		await page.getByRole('tablist').getByText('Customized').click();

		const filterButton = await page
			.locator('.filters-dropdown')
			.getByText('Filter');

		filterButton.click();

		const filterDropdown = await page.locator('.dropdown-menu', {
			hasText: 'Filters',
		});

		await expect(
			filterDropdown.getByText('Client Extension')
		).toBeVisible();
	});
});
