/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {headlessBuilderPagesTest} from '../../fixtures/headlessBuilderPagesTest';
import {headlessDiscoveryPagesTest} from '../../fixtures/headlessDiscoveryWebPagesTest';
import {loginTest} from '../../fixtures/loginTest';
import {waitForHeadlessBuilderReady} from './utils/headlessBuilder';

export const test = mergeTests(
	apiHelpersTest,
	loginTest,
	headlessBuilderPagesTest,
	headlessDiscoveryPagesTest,
	featureFlagsTest({
		'LPS-178642': true,
	})
);

test('can see available path parameter properties of a singleElement endpoint', async ({
	apiApplicationPage,
	apiHelpers,
	headlessBuilderPage,
	page,
}) => {
	await waitForHeadlessBuilderReady(apiHelpers, page);

	await headlessBuilderPage.goto();
	await headlessBuilderPage.addNewAPIApplicationButton.click();
	await headlessBuilderPage.newAPIApplicationTitleBox.fill('My-app');
	await headlessBuilderPage.createApplicationButton.click();

	await apiApplicationPage.goToSchemasTab();
	await apiApplicationPage.addAPISchemaButton.click();
	await apiApplicationPage.schemaNameTextBox.fill('API Application schema');
	await apiApplicationPage.setSchemaMainObjectDefinition('APIApplication');
	await apiApplicationPage.createButton.click();

	await apiApplicationPage.createSingleElementApiEndpoint(
		'Company',
		'gettest',
		'entryid'
	);
	await apiApplicationPage.goToEndpointConfigurationTab();

	// TODO Change when LPD-16654 is fixed

	await page.getByLabel('Response Body Schema').click();
	await page.getByRole('menuitem', {name: 'API Application schema'}).click();

	await page.getByRole('button', {name: 'Select an Option'}).click();
	await expect(
		page.getByRole('menuitem', {name: 'External Reference Code'})
	).toBeVisible();
	await expect(page.getByRole('menuitem', {name: 'ID'})).toBeVisible();

	// TODO see how to solve same behavior between different action buttons

	await headlessBuilderPage.goto();
});

test('can see path parameter property with map details', async ({
	apiApplicationPage,
	apiHelpers,
	headlessBuilderPage,
	page,
}) => {
	await waitForHeadlessBuilderReady(apiHelpers, page);

	await headlessBuilderPage.goto();
	await headlessBuilderPage.addNewAPIApplicationButton.click();
	await headlessBuilderPage.newAPIApplicationTitleBox.fill('My-app');
	await headlessBuilderPage.createApplicationButton.click();

	await apiApplicationPage.goToSchemasTab();
	await apiApplicationPage.addAPISchemaButton.click();
	await apiApplicationPage.schemaNameTextBox.fill('API Application schema');
	await apiApplicationPage.setSchemaMainObjectDefinition('APIApplication');
	await apiApplicationPage.createButton.click();

	await apiApplicationPage.createSingleElementApiEndpoint(
		'Company',
		'gettest',
		'entryid'
	);
	await apiApplicationPage.goToEndpointConfigurationTab();

	// TODO Change when LPD-16654 is fixed

	await page.getByLabel('Response Body Schema').click();
	await page.getByRole('menuitem', {name: 'API Application schema'}).click();

	await expect(
		page.getByRole('button', {name: 'Select an Option'})
	).toBeVisible();
	await expect(
		page.getByPlaceholder('Add a description here.')
	).toBeVisible();
	await expect(
		page.getByText(
			'This property from the schema will be mapped to path Parameter: {entryid}.'
		)
	).toBeVisible();

	// TODO see how to solve same behavior between different action buttons

	await headlessBuilderPage.goto();
});
