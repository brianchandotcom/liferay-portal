/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {APIResponse, expect as baseExpect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {headlessBuilderTest} from '../headless-builder-web/fixtures/headlessBuilderTest';

export const test = mergeTests(
	dataApiHelpersTest,
	headlessBuilderTest(),
	loginTest()
);

const expect = baseExpect.extend({
	toBeSuccessful: (response: APIResponse) => ({
		message: () =>
			response.ok()
				? 'Response is successful'
				: 'Response is not successful',
		pass: response.ok(),
	}),
	toHaveItems: async (response: APIResponse, expectedItems: object[]) => {
		baseExpect((await response.json()).items).toEqual(expectedItems);

		return {
			message: () => 'The items are correct',
			pass: true,
		};
	},
});

test('can GET with API Filter', async ({apiHelpers}) => {
	const objectDefinition =
		await apiHelpers.objectAdmin.postRandomObjectDefinition({code: 0});

	apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

	const apiApplication = await apiHelpers.apiBuilder.postApiApplication({
		apiApplicationToAPIEndpoints: [
			{
				description: 'description',
				externalReferenceCode: 'ENDPOINT',
				httpMethod: 'get',
				name: 'GetEndpoint',
				path: '/path-endpoint',
				scope: 'company',
			},
		],
		apiApplicationToAPISchemas: [
			{
				apiSchemaToAPIProperties: [
					{
						description: 'description',
						name: 'textProperty',
						objectFieldERC: 'textField',
					},
				],
				description: 'description',
				externalReferenceCode: 'SCHEMA',
				mainObjectDefinitionERC: objectDefinition.externalReferenceCode,
				name: 'schema',
			},
		],
		applicationStatus: 'published',
		baseURL: 'test',
		externalReferenceCode: 'APPLICATION',
		title: 'title',
	});

	apiHelpers.data.push({id: apiApplication.id, type: 'apiApplication'});

	expect(apiApplication.status).toStrictEqual({
		code: 0,
		label: 'approved',
		label_i18n: 'Approved',
	});

	expect(
		await apiHelpers.apiBuilder.putResponse(
			'schemas/by-external-reference-code/SCHEMA/requestAPISchemaToAPIEndpoints/ENDPOINT'
		)
	).toBeSuccessful();

	expect(
		await apiHelpers.apiBuilder.putResponse(
			'schemas/by-external-reference-code/SCHEMA/responseAPISchemaToAPIEndpoints/ENDPOINT'
		)
	).toBeSuccessful();
	expect(
		await apiHelpers.apiBuilder.postApiResource(
			{
				oDataFilter: "textField eq 'value5' or textField eq 'value24'",
				r_apiEndpointToAPIFilters_l_apiEndpointERC: 'ENDPOINT',
			},
			'filters'
		)
	).toBeSuccessful();
	for (let i = 0; i <= 25; i++) {
		expect(
			await apiHelpers.objectEntry.postObjectDefinitionRandomObjectEntries(
				'textField',
				`value${i}`,
				objectDefinition.restContextPath
			)
		).toBeSuccessful();
	}

	expect(
		await apiHelpers.apiBuilder.getEndpointPage('c/test/path-endpoint')
	).toHaveItems([
		{
			textProperty: 'value5',
		},
		{
			textProperty: 'value24',
		},
	]);

	expect(
		await apiHelpers.apiBuilder.getEndpointPage(
			`c/test/path-endpoint?filter=${encodeURIComponent(
				`textProperty eq 'value5' or textProperty eq 'value20'`
			)}`
		)
	).toHaveItems([
		{
			textProperty: 'value5',
		},
	]);
});
