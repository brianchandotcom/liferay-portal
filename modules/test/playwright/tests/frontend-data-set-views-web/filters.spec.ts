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
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../utils/getRandomString';
import {pageEditorPagesTest} from '../layout-content-page-editor-web/fixtures/pageEditorPagesTest';
import getPageDefinition from '../layout-content-page-editor-web/utils/getPageDefinition';
import getWidgetDefinition from '../layout-content-page-editor-web/utils/getWidgetDefinition';

export const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-164563': true,
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest
);

test('Add the frontend data set sample widget', async ({
	apiHelpers,
	page,
	site,
}) => {
	let layout: Layout;

	await test.step('Create a content site and the frontend data set sample widget', async () => {
		await page.goto('/');

		const widgetDefinition = getWidgetDefinition({
			id: getRandomString(),
			widgetName:
				'com_liferay_frontend_data_set_sample_web_internal_portlet_FDSSamplePortlet',
		});

		layout = await apiHelpers.headlessDelivery.createSitePage(
			site.id,
			getRandomString(),
			getPageDefinition([widgetDefinition])
		);
	});

	await test.step('Assert that the filter client extension is added', async () => {
		await page.goto(
			`${liferayConfig.environment.baseUrl}/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
		);

		const tabHeading = page.getByRole('tablist').getByText('Customized');

		await expect(tabHeading).toBeInViewport();

		await tabHeading.click();

		const filterButton = page
			.locator('.filters-dropdown')
			.getByText('Filter');

		await expect(filterButton).toBeInViewport();

		filterButton.click();

		const filterDropdown = page.locator('.dropdown-menu', {
			hasText: 'Filters',
		});

		await expect(
			filterDropdown.getByText('Client Extension')
		).toBeInViewport();
	});

	await test.step('Assert that the filter client extension is working', async () => {
		const filterButton = page
			.locator('.filters-dropdown')
			.getByText('Filter');

		const clientExtensionMenuItem = page.getByRole('menuitem', {
			name: 'Client Extension',
		});

		await expect(filterButton).toBeInViewport();

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: clientExtensionMenuItem,
			timeout: 500,
			trigger: filterButton,
		});

		const filterInput = page.getByPlaceholder('Search with Odata');

		await expect(filterInput).toBeInViewport();

		filterInput.fill("title eq 'Sample2'");

		await expect(filterInput).toHaveValue("title eq 'Sample2'");

		const submitButton = page.getByRole('button', {name: 'Submit'});

		await expect(submitButton).toBeInViewport();

		await submitButton.click();

		await expect(page.getByText('Sample2', {exact: true})).toBeVisible();

		const rowCount = await page.locator('.dnd-tbody > .dnd-tr').count();

		await expect(rowCount).toEqual(1);
	});
});
