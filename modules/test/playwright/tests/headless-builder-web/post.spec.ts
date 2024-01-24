/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';

export const test = mergeTests(apiHelpersTest);

const basicApiApplication = {
	apiApplicationToAPISchemas: [
		{
			description: 'API Application Schema',
			externalReferenceCode: 'api-application-schema',
			mainObjectDefinitionERC: 'L_API_APPLICATION',
			name: 'API Application Schema',
		},
	],
	applicationStatus: 'unpublished',
	baseURL: 'basic-application',
	description: 'Test API Application',
	externalReferenceCode: 'basic-application',
	title: 'Basic application',
};

test('can create post method endpoint with company scope', async ({
	apiHelpers,
	page,
}) => {
	await apiHelpers.featureFlag.updateFeatureFlag('LPS-178642', true);

	await apiHelpers.object.postObjectEntry(
		basicApiApplication,
		'headless-builder/applications'
	);

	await page.goto(
		'/group/guest/~/control_panel/manage?p_p_id=com_liferay_headless_builder_web_internal_portlet_HeadlessBuilderPortlet'
	);
	await page.waitForLoadState();
	await page.getByRole('link', {name: basicApiApplication.title}).click();
	await page.getByRole('button', {name: 'Endpoints'}).click();
	await page.getByLabel('Add API Endpoint').click();
	await page.getByLabel('Method').click();
	await page.getByRole('menuitem', {name: 'POST'}).click();
	await page.getByLabel('Select Scope').click();
	await page.getByRole('menuitem', {name: 'Company'}).click();
	await page.getByPlaceholder('Enter Path').click();
	await page.getByPlaceholder('Enter Path').fill('test-post-endpoint');
	await page.getByRole('button', {name: 'Create'}).click();
	await page.getByRole('tab', {name: 'Configuration'}).click();
	await page.getByLabel('Request Body Schema').click();
	await page
		.getByRole('menuitem', {
			name: basicApiApplication.apiApplicationToAPISchemas[0].name,
		})
		.click();
	await page.getByRole('button', {name: 'Publish'}).click();

	await page.goto(
		`http://localhost:8080/o/api?endpoint=http://localhost:8080/o/c/${basicApiApplication.baseURL}/openapi.json`
	);

	expect(page.getByLabel('post ​/test-post-endpoint')).toBeDefined;

	await apiHelpers.featureFlag.updateFeatureFlag('LPS-178642', false);
	await apiHelpers.object.deleteObjectEntryByExternalReferenceCode(
		'headless-builder/applications',
		basicApiApplication.externalReferenceCode
	);
});
