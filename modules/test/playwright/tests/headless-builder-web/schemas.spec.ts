/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {headlessBuilderPagesTest} from './fixtures/headlessBuilderPagesTest';

export const testFeatureFlagsEnabled = mergeTests(
	apiHelpersTest,
	headlessBuilderPagesTest({
		'LPD-21414': true,
	}),
	loginTest()
);

export const testFeatureFlagsDisabled = mergeTests(
	apiHelpersTest,
	headlessBuilderPagesTest({
		'LPD-21414': false,
	}),
	loginTest()
);

testFeatureFlagsDisabled(
	'can see all available object defitions on schema creation',
	async ({apiHelpers, applicationPage, headlessBuilderPage}) => {
		const objectDefinitions = [];

		for (let i = 0; i <= 21; i++) {
			objectDefinitions.push(
				await apiHelpers.objectAdmin.postObjectDefinition({
					active: true,
					externalReferenceCode: `objectDefinition${i}`,
					label: {
						en_US: `objectDefinition${i}`,
					},
					name: `ObjectDefinition${i}`,
					objectFields: [
						{
							DBType: 'String',
							businessType: 'Text',
							externalReferenceCode: 'ObjectFieldERC',
							indexed: true,
							indexedAsKeyword: false,
							indexedLanguageId: 'en_US',
							label: {
								en_US: 'Object Field',
							},
							listTypeDefinitionId: 0,
							name: 'objectField',
							required: false,
							state: false,
							system: false,
							type: 'String',
						},
					],
					pluralLabel: {
						en_US: `objectDefinitions${i}`,
					},
					portlet: true,
					scope: 'company',
					status: {
						code: 0,
					},
				})
			);
		}

		const application = await apiHelpers.objectEntry.postObjectEntry(
			{
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
			},
			'headless-builder/applications'
		);

		await headlessBuilderPage.goto();
		await headlessBuilderPage.goToEditApplication(application.title);
		await applicationPage.goToSchemasTab();
		await applicationPage.addSchemaButton.click();
		await applicationPage.schemaObjectDefinitionSelector.click();

		objectDefinitions.forEach((objectDefinition) => {
			expect(
				applicationPage.page.getByRole('menuitem', {
					exact: true,
					name: objectDefinition.name,
				})
			).toBeVisible();
		});

		for (const objectDefinition of objectDefinitions) {
			await expect
				.poll(async () =>
					(
						await apiHelpers.objectAdmin.deleteObjectDefinition(
							objectDefinition.id
						)
					).status()
				)
				.toBe(204);
		}

		await apiHelpers.objectEntry.deleteObjectEntryByExternalReferenceCode(
			'headless-builder/applications',
			application.externalReferenceCode
		);
	}
);

testFeatureFlagsDisabled(
	'can see whitelisted object definitions on schema creation',
	async ({apiHelpers, applicationPage, headlessBuilderPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postObjectDefinition({
				active: true,
				externalReferenceCode: `objectDefinition`,
				label: {
					en_US: `objectDefinition`,
				},
				name: `ObjectDefinition`,
				objectFields: [
					{
						DBType: 'String',
						businessType: 'Text',
						externalReferenceCode: 'ObjectFieldERC',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: 'en_US',
						label: {
							en_US: 'Object Field',
						},
						listTypeDefinitionId: 0,
						name: 'objectField',
						required: false,
						state: false,
						system: false,
						type: 'String',
					},
				],
				pluralLabel: {
					en_US: `objectDefinitions`,
				},
				portlet: true,
				scope: 'company',
				status: {
					code: 0,
				},
			});

		const application = await apiHelpers.objectEntry.postObjectEntry(
			{
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
			},
			'headless-builder/applications'
		);

		await headlessBuilderPage.goto();
		await headlessBuilderPage.goToEditApplication(application.title);
		await applicationPage.goToSchemasTab();
		await applicationPage.addSchemaButton.click();
		await applicationPage.schemaObjectDefinitionSelector.click();

		expect(
			(
				await applicationPage.page.getByRole('menu').allInnerTexts()
			)[0].split('\n')
		).toEqual([
			'APISort',
			'APIFilter',
			'APIEndpoint',
			'APIProperty',
			'APISchema',
			'APIApplication',
			'ObjectDefinition',
		]);

		await apiHelpers.objectEntry.deleteObjectEntryByExternalReferenceCode(
			'headless-builder/applications',
			application.externalReferenceCode
		);

		await apiHelpers.objectAdmin.deleteObjectDefinition(
			objectDefinition.id
		);
	}
);

testFeatureFlagsEnabled(
	'can see whitelisted object definitions on schema creation with feature flag',
	async ({apiHelpers, applicationPage, headlessBuilderPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postObjectDefinition({
				active: true,
				externalReferenceCode: `objectDefinition`,
				label: {
					en_US: `objectDefinition`,
				},
				name: `ObjectDefinition`,
				objectFields: [
					{
						DBType: 'String',
						businessType: 'Text',
						externalReferenceCode: 'ObjectFieldERC',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: 'en_US',
						label: {
							en_US: 'Object Field',
						},
						listTypeDefinitionId: 0,
						name: 'objectField',
						required: false,
						state: false,
						system: false,
						type: 'String',
					},
				],
				pluralLabel: {
					en_US: `objectDefinitions`,
				},
				portlet: true,
				scope: 'company',
				status: {
					code: 0,
				},
			});

		const application = await apiHelpers.objectEntry.postObjectEntry(
			{
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
			},
			'headless-builder/applications'
		);

		await headlessBuilderPage.goto();
		await headlessBuilderPage.goToEditApplication(application.title);
		await applicationPage.goToSchemasTab();
		await applicationPage.addSchemaButton.click();
		await applicationPage.schemaObjectDefinitionSelector.click();

		expect(
			(
				await applicationPage.page.getByRole('menu').allInnerTexts()
			)[0].split('\n')
		).toEqual([
			'AccountEntry',
			'User',
			'APISort',
			'APIFilter',
			'APIEndpoint',
			'APIProperty',
			'APISchema',
			'APIApplication',
			'ObjectDefinition',
		]);

		await apiHelpers.objectEntry.deleteObjectEntryByExternalReferenceCode(
			'headless-builder/applications',
			application.externalReferenceCode
		);

		await apiHelpers.objectAdmin.deleteObjectDefinition(
			objectDefinition.id
		);
	}
);
