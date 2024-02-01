/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {ApiHelpers} from '../../helpers/ApiHelpers';
import {liferayConfig} from '../../liferay.config';

export const test = mergeTests(apiHelpersTest, loginTest);

const basicApiApplication = {
	apiApplicationToAPISchemas: [
		{
			description: 'API Application Schema',
			externalReferenceCode: 'api-application-schema',
			mainObjectDefinitionERC: 'L_API_APPLICATION',
			name: 'API Application Schema',
		},
	],
	applicationStatus: 'published',
	baseURL: 'basic-application',
	description: 'Test API Application',
	externalReferenceCode: 'basic-application',
	title: 'Basic application',
};

async function waitForHeadlessBuilderReady(apiHelpers: ApiHelpers, page: Page) {
	for (const endpoint of [
		'applications',
		'endpoints',
		'filters',
		'properties',
		'schemas',
		'sorts',
	]) {
		await expect
			.poll(async () =>
				(
					await page.request.get(`/o/headless-builder/${endpoint}`, {
						headers: await apiHelpers.getHeader(),
					})
				).status()
			)
			.toBe(200);
	}
}

test('can see filter and sort parameters for collection but not for singleElement endpoints', async ({
	apiHelpers,
	page,
}) => {
	await waitForHeadlessBuilderReady(apiHelpers, page);
	await apiHelpers.object.postObjectEntry(
		basicApiApplication,
		'headless-builder/applications'
	);
	await apiHelpers.object.postObjectEntry(
		{
			description: 'Test collection API Endpoint',
			externalReferenceCode: 'basic-collection-endpoint',
			httpMethod: 'get',
			name: 'Basic collection API Endpoint',
			path: '/collection-endpoint',
			r_apiApplicationToAPIEndpoints_c_apiApplicationERC:
				basicApiApplication.externalReferenceCode,
			retrieveType: 'collection',
			scope: 'company',
		},
		'headless-builder/endpoints'
	);
	await apiHelpers.object.postObjectEntry(
		{
			description: 'Test Single Element API Endpoint',
			externalReferenceCode: 'basic-singleElement-endpoint',
			httpMethod: 'get',
			name: 'Basic Single Element API Endpoint',
			path: '/single-element-endpoint/{id}',
			pathParameter: 'id',
			r_apiApplicationToAPIEndpoints_c_apiApplicationERC:
				basicApiApplication.externalReferenceCode,
			r_responseAPISchemaToAPIEndpoints_c_apiSchemaERC:
				basicApiApplication.apiApplicationToAPISchemas[0]
					.externalReferenceCode,
			retrieveType: 'singleElement',
			scope: 'company',
		},
		'headless-builder/endpoints'
	);

	await page.goto(
		`/o/api?endpoint=${liferayConfig.environment.baseUrl}/o/c/${basicApiApplication.baseURL}/openapi.json`
	);

	await page
		.locator('//span[@data-path="/collection-endpoint"]/a/span')
		.click();
	await expect(
		page.getByRole('cell', {exact: true, name: 'filter'})
	).toBeVisible();
	await expect(
		page.getByRole('cell', {exact: true, name: 'sort'})
	).toBeVisible();
	await page
		.locator('//span[@data-path="/collection-endpoint"]/a/span')
		.click();

	page.waitForLoadState();
	await page
		.locator('//span[@data-path="/single-element-endpoint/{id}"]/a/span')
		.click();
	await expect(
		page.getByRole('cell', {exact: true, name: 'filter'})
	).toBeHidden();
	await expect(
		page.getByRole('cell', {exact: true, name: 'sort'})
	).toBeHidden();

	await page.goto('/');
	await apiHelpers.object.deleteObjectEntryByExternalReferenceCode(
		'headless-builder/applications',
		basicApiApplication.externalReferenceCode
	);
});
