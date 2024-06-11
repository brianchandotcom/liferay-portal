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
		'LPS-193005': true,
	}),
	isolatedSiteTest,
	loginTest()
);

test.describe('LPS-193005 ClayTable on FrontendDataSet', () => {
	test('Column sorting', async ({apiHelpers, page, site}) => {
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

		await test.step('Select Customized tab ', async () => {
			await page.goto(
				`${liferayConfig.environment.baseUrl}/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			const tabHeading = page
				.getByRole('tablist')
				.getByText('Customized');

			await expect(tabHeading).toBeInViewport();

			await tabHeading.click();
		});

		await test.step('Sorting ID and Title column in ascending order', async () => {
			const idColumnHeader = page
				.getByRole('columnheader')
				.getByText('ID');

			await expect(idColumnHeader).toBeInViewport();

			await expect(page.locator('td').nth(1)).toHaveText('34250');
			await expect(page.locator('td').nth(11)).toHaveText('34184');
			await expect(page.locator('td').nth(21)).toHaveText('34092');
			await expect(page.locator('td').nth(31)).toHaveText('34076');

			await idColumnHeader.click();

			await expect(page.locator('td').nth(1)).toHaveText('34064');
			await expect(page.locator('td').nth(11)).toHaveText('34076');
			await expect(page.locator('td').nth(21)).toHaveText('34080');
			await expect(page.locator('td').nth(31)).toHaveText('34092');

			const titleColumnHeader = page
				.getByRole('columnheader')
				.getByText('Title');

			await titleColumnHeader.click();

			await expect(page.locator('td').nth(2)).toHaveText('Sample3');
			await expect(page.locator('td').nth(12)).toHaveText('Sample9');
			await expect(page.locator('td').nth(22)).toHaveText('Sample11');
			await expect(page.locator('td').nth(32)).toHaveText('Sample17');
		});
	});

	test('Columns Visibility', async ({apiHelpers, page, site}) => {
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

		await test.step('Select Customized tab ', async () => {
			await page.goto(
				`${liferayConfig.environment.baseUrl}/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			const tabHeading = page
				.getByRole('tablist')
				.getByText('Customized');

			await expect(tabHeading).toBeInViewport();

			await tabHeading.click();
		});

		await test.step('Hide the Title column', async () => {
			const titleColumnHeader = page
				.getByRole('columnheader')
				.getByText('Title');

			await expect(titleColumnHeader).toBeAttached();

			const button = page.getByLabel('Manage Columns Visibility');

			await expect(button).toBeAttached();

			await button.click();

			const titleMenuItem = page.getByRole('menuitem').nth(1);

			await titleMenuItem.click();

			await expect(
				page.getByRole('columnheader').getByText('Title')
			).toBeHidden();
		});
	});
});
