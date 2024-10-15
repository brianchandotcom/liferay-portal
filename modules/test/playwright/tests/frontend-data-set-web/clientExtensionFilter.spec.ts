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
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
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

test('Use filter client extension in the frontend data set', async ({
	apiHelpers,
	page,
	site,
}) => {
	const clientExtensionMenuItem = page.getByRole('menuitem', {
		name: 'Client Extension',
	});

	const filterButton = page.locator('.filters-dropdown').getByText('Filter');

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

	await test.step('Assert that the filter client extension is available', async () => {
		await page.goto(
			`${liferayConfig.environment.baseUrl}/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
		);

		const tabHeading = page.getByRole('tablist').getByText('Customized');

		await expect(tabHeading).toBeInViewport();

		await tabHeading.click();

		await expect(filterButton).toBeInViewport();

		filterButton.click();

		await expect(clientExtensionMenuItem).toBeInViewport();

		filterButton.click();
	});

	await test.step('Assert that the filter client extension is working', async () => {
		await expect(filterButton).toBeInViewport();

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: clientExtensionMenuItem,
			timeout: 500,
			trigger: filterButton,
		});

		const filterInput = page.getByPlaceholder('Search with Odata');

		await expect(filterInput).toBeInViewport();

		filterInput.fill("title eq 'Sample97'");

		await expect(filterInput).toHaveValue("title eq 'Sample97'");

		const submitButton = page.getByRole('button', {name: 'Submit'});

		await expect(submitButton).toBeInViewport();

		await submitButton.click();

		await expect(page.getByText('Sample97', {exact: true})).toBeVisible();

		const rowCount = await page.locator('.dnd-tbody > .dnd-tr').count();

		expect(rowCount).toEqual(1);
	});
});
