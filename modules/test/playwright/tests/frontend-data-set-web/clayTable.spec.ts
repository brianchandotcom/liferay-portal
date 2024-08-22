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

test('Sort columns', {tag: '@LPS-193005'}, async ({apiHelpers, page, site}) => {
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

	await test.step('Open Customized tab ', async () => {
		await page.goto(
			`${liferayConfig.environment.baseUrl}/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
		);

		const tabHeading = page.getByRole('tablist').getByText('Customized');

		await expect(tabHeading).toBeInViewport();
	});

	await test.step('Sorting ID and Title column in ascending order', async () => {
		const idColumnHeader = page.getByRole('columnheader').getByText('ID');

		await expect(idColumnHeader).toBeInViewport();

		const cells = await page.locator('td').allInnerTexts();

		await expect(page.locator('td').nth(1)).toHaveText(cells[1]);
		await expect(page.locator('td').nth(11)).toHaveText(cells[11]);
		await expect(page.locator('td').nth(21)).toHaveText(cells[21]);
		await expect(page.locator('td').nth(31)).toHaveText(cells[31]);

		await idColumnHeader.click();

		const ascendingIDCells = [
			cells[1],
			cells[11],
			cells[21],
			cells[31],
			cells[41],
			cells[51],
			cells[61],
			cells[71],
			cells[81],
			cells[91],
		].sort();

		await expect(page.locator('td').nth(1)).toHaveText(ascendingIDCells[0]);
		await expect(page.locator('td').nth(11)).toHaveText(
			ascendingIDCells[1]
		);
		await expect(page.locator('td').nth(21)).toHaveText(
			ascendingIDCells[2]
		);
		await expect(page.locator('td').nth(31)).toHaveText(
			ascendingIDCells[3]
		);

		const titleColumnHeader = page
			.getByRole('columnheader')
			.getByText('Title');

		await titleColumnHeader.click();

		const ascendingTitleCells = [
			cells[2],
			cells[12],
			cells[22],
			cells[32],
			cells[42],
			cells[52],
			cells[62],
			cells[72],
			cells[82],
			cells[92],
		].sort((a, b) =>
			new Intl.Collator('en', {numeric: true}).compare(a, b)
		);

		await expect(page.locator('td').nth(2)).toHaveText(
			ascendingTitleCells[0]
		);
		await expect(page.locator('td').nth(12)).toHaveText(
			ascendingTitleCells[1]
		);
		await expect(page.locator('td').nth(22)).toHaveText(
			ascendingTitleCells[2]
		);
		await expect(page.locator('td').nth(32)).toHaveText(
			ascendingTitleCells[3]
		);
	});
});

test(
	'Assert columns visibility',
	{tag: '@LPS-193005'},
	async ({apiHelpers, page, site}) => {
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
	}
);
