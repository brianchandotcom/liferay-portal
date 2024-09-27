/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {headlessDiscoveryPagesTest} from '../../fixtures/headlessDiscoveryWebPagesTest';
import {loginTest} from '../../fixtures/loginTest';
import {headlessBuilderTest} from './fixtures/headlessBuilderTest';

export const test = mergeTests(
	apiHelpersTest,
	headlessBuilderTest(),
	headlessDiscoveryPagesTest,
	loginTest()
);

const application = {
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

const singleElementIdEndpoint = {
	description: 'Test Single Element API Endpoint',
	externalReferenceCode: 'basic-singleElement-endpoint',
	httpMethod: 'get',
	name: 'Basic Single Element API Endpoint',
	path: '/single-element-endpoint/{id}',
	pathParameter: 'id',
	r_apiApplicationToAPIEndpoints_l_apiApplicationERC:
		application.externalReferenceCode,
	r_responseAPISchemaToAPIEndpoints_l_apiSchemaERC:
		application.apiApplicationToAPISchemas[0].externalReferenceCode,
	retrieveType: 'singleElement',
	scope: 'company',
};

test('can see filter and sort parameters for collection endpoints', async ({
	apiExplorerPage,
	apiHelpers,
	page,
}) => {
	await apiHelpers.objectEntry.postObjectEntry(
		application,
		'headless-builder/applications'
	);
	const collectionEndpoint = await apiHelpers.objectEntry.postObjectEntry(
		{
			description: 'Test collection API Endpoint',
			externalReferenceCode: 'basic-collection-endpoint',
			httpMethod: 'get',
			name: 'Basic collection API Endpoint',
			path: '/collection-endpoint',
			r_apiApplicationToAPIEndpoints_l_apiApplicationERC:
				application.externalReferenceCode,
			retrieveType: 'collection',
			scope: 'company',
		},
		'headless-builder/endpoints'
	);

	await apiExplorerPage.goToApplication(`c/${application.baseURL}`);

	await apiExplorerPage.expectEndpointWithParameters(
		collectionEndpoint.path,
		['filter', 'sort']
	);

	await page.goto('/');
	await apiHelpers.objectEntry.deleteObjectEntryByExternalReferenceCode(
		'headless-builder/applications',
		application.externalReferenceCode
	);
});

test('can see get endpoint path with erc parameter', async ({
	apiExplorerPage,
	apiHelpers,
	page,
}) => {
	await apiHelpers.objectEntry.postObjectEntry(
		application,
		'headless-builder/applications'
	);

	const singleElementEndpoint = await apiHelpers.objectEntry.postObjectEntry(
		{
			description: 'Test Single Element API Endpoint',
			externalReferenceCode: 'basic-singleElement-endpoint',
			httpMethod: 'get',
			name: 'Basic Single Element API Endpoint',
			path: '/single-element-endpoint/{erc}',
			pathParameter: 'externalReferenceCode',
			r_apiApplicationToAPIEndpoints_l_apiApplicationERC:
				application.externalReferenceCode,
			r_responseAPISchemaToAPIEndpoints_l_apiSchemaERC:
				application.apiApplicationToAPISchemas[0].externalReferenceCode,
			retrieveType: 'singleElement',
			scope: 'company',
		},
		'headless-builder/endpoints'
	);

	await apiExplorerPage.goToApplication(`c/${application.baseURL}`);

	await apiExplorerPage.expectEndpointWithParameters(
		singleElementEndpoint.path,
		['erc']
	);

	await apiExplorerPage.getEndpointLocator(singleElementEndpoint.path, {
		hasText: '{erc}',
	});

	await page.goto('/');
	await apiHelpers.objectEntry.deleteObjectEntryByExternalReferenceCode(
		'headless-builder/applications',
		application.externalReferenceCode
	);
});

test('can see get endpoint path with id parameter', async ({
	apiExplorerPage,
	apiHelpers,
	page,
}) => {
	await apiHelpers.objectEntry.postObjectEntry(
		application,
		'headless-builder/applications'
	);

	await apiHelpers.objectEntry.postObjectEntry(
		singleElementIdEndpoint,
		'headless-builder/endpoints'
	);

	await apiExplorerPage.goToApplication(`c/${application.baseURL}`);

	await apiExplorerPage.expectEndpointWithParameters(
		singleElementIdEndpoint.path,
		['id']
	);

	await apiExplorerPage.getEndpointLocator(singleElementIdEndpoint.path, {
		hasText: '{id}',
	});

	await page.goto('/');
	await apiHelpers.objectEntry.deleteObjectEntryByExternalReferenceCode(
		'headless-builder/applications',
		application.externalReferenceCode
	);
});

test('cannot see filter and sort parameters for singleElement endpoints', async ({
	apiExplorerPage,
	apiHelpers,
	page,
}) => {
	await apiHelpers.objectEntry.postObjectEntry(
		application,
		'headless-builder/applications'
	);

	await apiHelpers.objectEntry.postObjectEntry(
		singleElementIdEndpoint,
		'headless-builder/endpoints'
	);

	await apiExplorerPage.goToApplication(`c/${application.baseURL}`);

	await apiExplorerPage.expectEndpointWithoutParameters(
		singleElementIdEndpoint.path,
		['filter', 'sort']
	);

	await page.goto('/');
	await apiHelpers.objectEntry.deleteObjectEntryByExternalReferenceCode(
		'headless-builder/applications',
		application.externalReferenceCode
	);
});
