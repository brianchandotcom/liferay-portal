/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {headlessDiscoveryPagesTest} from '../../fixtures/headlessDiscoveryWebPagesTest';
import {loginTest} from '../../fixtures/loginTest';
import {waitForHeadlessBuilderReady} from './utils/headlessBuilder';

export const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178642': true,
	}),
	headlessDiscoveryPagesTest,
	loginTest
);

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

test('can see filter and sort parameters for collection endpoints', async ({
	apiExplorerPage,
	apiHelpers,
	page,
}) => {
	await waitForHeadlessBuilderReady(apiHelpers, page);
	await apiHelpers.object.postObjectEntry(
		basicApiApplication,
		'headless-builder/applications'
	);
	const collectionEndpoint = await apiHelpers.object.postObjectEntry(
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

	await apiExplorerPage.goToApplication(`c/${basicApiApplication.baseURL}`);

	await apiExplorerPage.endpointHasParameters(collectionEndpoint.path, [
		'filter',
		'sort',
	]);

	await page.goto('/');
	await apiHelpers.object.deleteObjectEntryByExternalReferenceCode(
		'headless-builder/applications',
		basicApiApplication.externalReferenceCode
	);
});

test('cannot see filter and sort parameters for singleElement endpoints', async ({
	apiExplorerPage,
	apiHelpers,
	page,
}) => {
	await waitForHeadlessBuilderReady(apiHelpers, page);
	await apiHelpers.object.postObjectEntry(
		basicApiApplication,
		'headless-builder/applications'
	);

	const singleElementEndpoint = await apiHelpers.object.postObjectEntry(
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

	await apiExplorerPage.goToApplication(`c/${basicApiApplication.baseURL}`);

	await apiExplorerPage.endpointHasNotParameters(singleElementEndpoint.path, [
		'filter',
		'sort',
	]);

	await page.goto('/');
	await apiHelpers.object.deleteObjectEntryByExternalReferenceCode(
		'headless-builder/applications',
		basicApiApplication.externalReferenceCode
	);
});
