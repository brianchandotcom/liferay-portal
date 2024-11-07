/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {headlessDiscoveryPagesTest} from '../../fixtures/headlessDiscoveryWebPagesTest';
import {loginTest} from '../../fixtures/loginTest';
import {headlessBuilderPagesTest} from './fixtures/headlessBuilderPagesTest';
import {headlessBuilderTest} from './fixtures/headlessBuilderTest';

export const test = mergeTests(
	dataApiHelpersTest,
	headlessBuilderTest(),
	headlessBuilderPagesTest(),
	headlessDiscoveryPagesTest,
	loginTest()
);

const applicationData = {
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

const singleElementIdEndpointData = {
	description: 'Test Single Element API Endpoint',
	externalReferenceCode: 'basic-singleElement-endpoint',
	httpMethod: 'get',
	name: 'Basic Single Element API Endpoint',
	path: '/single-element-endpoint/{id}',
	pathParameter: 'id',
	r_apiApplicationToAPIEndpoints_l_apiApplicationERC: 'basic-application',
	r_responseAPISchemaToAPIEndpoints_l_apiSchemaERC: 'api-application-schema',
	retrieveType: 'singleElement',
	scope: 'company',
};

test('can see filter and sort parameters for collection endpoints', async ({
	apiExplorerPage,
	apiHelpers,
}) => {
	const collectionEndpointApplication =
		await apiHelpers.objectEntry.postObjectEntry(
			{
				...applicationData,
				apiApplicationToAPIEndpoints: [
					{
						description: 'Test collection API Endpoint',
						externalReferenceCode: 'basic-collection-endpoint',
						httpMethod: 'get',
						name: 'Basic collection API Endpoint',
						path: '/collection-endpoint',
						retrieveType: 'collection',
						scope: 'company',
					},
				],
			},
			'headless-builder/applications'
		);

	apiHelpers.data.push({
		id: collectionEndpointApplication.id,
		type: 'apiApplication',
	});

	await apiExplorerPage.goToApplication(`c/${applicationData.baseURL}`);

	await apiExplorerPage.expectEndpointWithParameters('/collection-endpoint', [
		'filter',
		'sort',
	]);
});

test('can see get endpoint path with erc parameter', async ({
	apiExplorerPage,
	apiHelpers,
}) => {
	const singleElementEndpointApplication =
		await apiHelpers.objectEntry.postObjectEntry(
			{
				...applicationData,
				apiApplicationToAPIEndpoints: [
					{
						description: 'Test Single Element API Endpoint',
						externalReferenceCode: 'basic-singleElement-endpoint',
						httpMethod: 'get',
						name: 'Basic Single Element API Endpoint',
						path: '/single-element-endpoint/{erc}',
						pathParameter: 'externalReferenceCode',
						r_responseAPISchemaToAPIEndpoints_l_apiSchemaERC:
							'api-application-schema',
						retrieveType: 'singleElement',
						scope: 'company',
					},
				],
			},
			'headless-builder/applications'
		);

	apiHelpers.data.push({
		id: singleElementEndpointApplication.id,
		type: 'apiApplication',
	});

	await apiExplorerPage.goToApplication(`c/${applicationData.baseURL}`);

	await apiExplorerPage.expectEndpointWithParameters(
		'/single-element-endpoint/{erc}',
		['erc']
	);

	await apiExplorerPage.getEndpointLocator('/single-element-endpoint/{erc}', {
		hasText: '{erc}',
	});
});

test('can see get endpoint path with id parameter', async ({
	apiExplorerPage,
	apiHelpers,
}) => {
	const application = await apiHelpers.objectEntry.postObjectEntry(
		applicationData,
		'headless-builder/applications'
	);

	apiHelpers.data.push({id: application.id, type: 'apiApplication'});

	await apiHelpers.objectEntry.postObjectEntry(
		singleElementIdEndpointData,
		'headless-builder/endpoints'
	);

	await apiExplorerPage.goToApplication(`c/${applicationData.baseURL}`);

	await apiExplorerPage.expectEndpointWithParameters(
		singleElementIdEndpointData.path,
		['id']
	);

	await apiExplorerPage.getEndpointLocator(singleElementIdEndpointData.path, {
		hasText: '{id}',
	});
});

test('cannot see filter and sort parameters for singleElement endpoints', async ({
	apiExplorerPage,
	apiHelpers,
}) => {
	const application = await apiHelpers.objectEntry.postObjectEntry(
		applicationData,
		'headless-builder/applications'
	);

	apiHelpers.data.push({id: application.id, type: 'apiApplication'});

	await apiHelpers.objectEntry.postObjectEntry(
		singleElementIdEndpointData,
		'headless-builder/endpoints'
	);

	await apiExplorerPage.goToApplication(`c/${applicationData.baseURL}`);

	await apiExplorerPage.expectEndpointWithoutParameters(
		singleElementIdEndpointData.path,
		['filter', 'sort']
	);
});
