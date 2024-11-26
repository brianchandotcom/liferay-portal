/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ObjectDefinition,
	ObjectDefinitionApi,
	ObjectField,
	ObjectRelationship,
} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {waitForLoading} from '../osb-faro-web/utils/loading';
import {headlessBuilderPagesTest} from './fixtures/headlessBuilderPagesTest';

export const testFeatureFlagsEnabled = mergeTests(
	dataApiHelpersTest,
	headlessBuilderPagesTest({
		'LPD-21414': true,
	}),
	loginTest()
);

export const testFeatureFlagsDisabled = mergeTests(
	dataApiHelpersTest,
	headlessBuilderPagesTest({
		'LPD-21414': false,
	}),
	loginTest()
);

const objectDefinitionData: ObjectDefinition = {
	active: true,
	externalReferenceCode: `objectDefinition`,
	label: {
		en_US: `objectDefinition`,
	},
	name: `ObjectDefinition`,
	objectFields: [
		{
			DBType: ObjectField.DBTypeEnum.String,
			businessType: ObjectField.BusinessTypeEnum.Text,
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
			type: ObjectField.TypeEnum.String,
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
};

const objectDefinition1Data: ObjectDefinition = {
	active: true,
	externalReferenceCode: `customObjectDefinition1`,
	label: {
		en_US: `objectDefinition1`,
	},
	name: `ObjectDefinition1`,
	objectFields: [
		{
			DBType: ObjectField.DBTypeEnum.String,
			businessType: ObjectField.BusinessTypeEnum.Text,
			externalReferenceCode: 'ObjectField1ERC',
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
			type: ObjectField.TypeEnum.String,
		},
	],
	objectRelationships: [
		{
			deletionType: ObjectRelationship.DeletionTypeEnum.Cascade,
			externalReferenceCode: 'modifiable-system',
			label: {
				en_US: 'Test Modifiable System Object',
			},
			name: 'testModifiableSystem',
			objectDefinitionExternalReferenceCode1: 'customObjectDefinition1',
			objectDefinitionExternalReferenceCode2: 'L_API_APPLICATION',
			objectDefinitionName2: 'APIApplication',
			parameterObjectFieldId: 0,
			parameterObjectFieldName: '',
			reverse: false,
			system: false,
			type: ObjectRelationship.TypeEnum.OneToMany,
		},
		{
			deletionType: ObjectRelationship.DeletionTypeEnum.Cascade,
			externalReferenceCode: 'unmodifiable-system',
			label: {
				en_US: 'Test Unmodifiable System Object',
			},
			name: 'testUnmodifiableSystem',
			objectDefinitionExternalReferenceCode1: 'customObjectDefinition1',
			objectDefinitionExternalReferenceCode2: 'L_ORGANIZATION',
			objectDefinitionName2: 'Organization',
			parameterObjectFieldId: 0,
			parameterObjectFieldName: '',
			reverse: false,
			system: false,
			type: ObjectRelationship.TypeEnum.OneToMany,
		},
		{
			deletionType: ObjectRelationship.DeletionTypeEnum.Cascade,
			externalReferenceCode: 'unmodifiable-system-allowed',
			label: {
				en_US: 'Test Unmodifiable Allowed System Object',
			},
			name: 'testUnmodifiableSystemAllowed',
			objectDefinitionExternalReferenceCode1: 'objectDefinition1',
			objectDefinitionExternalReferenceCode2: 'L_ACCOUNT',
			objectDefinitionName2: 'AccountEntry',
			parameterObjectFieldId: 0,
			parameterObjectFieldName: '',
			reverse: false,
			system: false,
			type: ObjectRelationship.TypeEnum.OneToMany,
		},
		{
			deletionType: ObjectRelationship.DeletionTypeEnum.Cascade,
			externalReferenceCode: 'custom',
			label: {
				en_US: 'Test Custom Object',
			},
			name: 'testCustom',
			objectDefinitionExternalReferenceCode1: 'customObjectDefinition1',
			objectDefinitionExternalReferenceCode2: 'customObjectDefinition',
			objectDefinitionName2: 'objectDefinition',
			parameterObjectFieldId: 0,
			parameterObjectFieldName: '',
			reverse: false,
			system: false,
			type: ObjectRelationship.TypeEnum.OneToMany,
		},
	],
	pluralLabel: {
		en_US: `objectDefinitions1`,
	},
	portlet: true,
	scope: 'company',
	status: {
		code: 0,
	},
};

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

testFeatureFlagsDisabled(
	'can see all available object definitions on schema creation',
	async ({apiHelpers, applicationPage, headlessBuilderPage}) => {
		const objectDefinitions = [];

		const objectDefinitionAPIClient =
			await apiHelpers.buildRestClient(ObjectDefinitionApi);

		for (let i = 0; i <= 21; i++) {
			objectDefinitions.push(
				await objectDefinitionAPIClient.postObjectDefinition({
					active: true,
					externalReferenceCode: `objectDefinition${i}`,
					label: {
						en_US: `objectDefinition${i}`,
					},
					name: `ObjectDefinition${i}`,
					objectFields: [
						{
							DBType: ObjectField.DBTypeEnum.String,
							businessType: ObjectField.BusinessTypeEnum.Text,
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
							type: ObjectField.TypeEnum.String,
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

		objectDefinitions.forEach((objectDefinition) => {
			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});
		});

		const application = await apiHelpers.objectEntry.postObjectEntry(
			applicationData,
			'headless-builder/applications'
		);

		apiHelpers.data.push({id: application.id, type: 'apiApplication'});

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
	}
);

testFeatureFlagsDisabled(
	'can see allowed object definitions on schema creation',
	async ({apiHelpers, applicationPage, headlessBuilderPage}) => {
		const objectDefinitionAPIClient =
			await apiHelpers.buildRestClient(ObjectDefinitionApi);

		const {body: objectDefinition} =
			await objectDefinitionAPIClient.postObjectDefinition(
				objectDefinitionData
			);

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const application = await apiHelpers.objectEntry.postObjectEntry(
			applicationData,
			'headless-builder/applications'
		);

		apiHelpers.data.push({id: application.id, type: 'apiApplication'});

		await headlessBuilderPage.goto();
		await headlessBuilderPage.goToEditApplication(application.title);
		await applicationPage.goToSchemasTab();
		await applicationPage.addSchemaButton.click();
		await applicationPage.schemaObjectDefinitionSelector.click();

		const objectDefinitionDropdownOptions = (
			await applicationPage.page.getByRole('menu').allInnerTexts()
		)[0].split('\n');

		expect(objectDefinitionDropdownOptions).not.toContain(['Organization']);
		expect(
			objectDefinitionDropdownOptions.includes('ObjectDefinition')
		).toBeTruthy();
	}
);

testFeatureFlagsEnabled(
	'can see allowed object definitions on schema creation with feature flag',
	async ({apiHelpers, applicationPage, headlessBuilderPage}) => {
		const objectDefinitionAPIClient =
			await apiHelpers.buildRestClient(ObjectDefinitionApi);

		const {body: objectDefinition} =
			await objectDefinitionAPIClient.postObjectDefinition(
				objectDefinitionData
			);

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const application = await apiHelpers.objectEntry.postObjectEntry(
			applicationData,
			'headless-builder/applications'
		);

		apiHelpers.data.push({id: application.id, type: 'apiApplication'});

		await headlessBuilderPage.goto();
		await headlessBuilderPage.goToEditApplication(application.title);
		await applicationPage.goToSchemasTab();
		await applicationPage.addSchemaButton.click();
		await applicationPage.schemaObjectDefinitionSelector.click();

		const objectDefinitionDropdownOptions = (
			await applicationPage.page.getByRole('menu').allInnerTexts()
		)[0].split('\n');

		expect(objectDefinitionDropdownOptions).not.toContain(['Organization']);

		for (const expectedObjectDefinition of [
			'AccountEntry',
			'ObjectDefinition',
			'User',
		]) {
			expect(
				objectDefinitionDropdownOptions.includes(
					expectedObjectDefinition
				)
			).toBeTruthy();
		}
	}
);

testFeatureFlagsDisabled(
	'check related objects enablement without feature flag',
	async ({apiHelpers, applicationPage, headlessBuilderPage, schemaPage}) => {
		const objectDefinitionAPIClient =
			await apiHelpers.buildRestClient(ObjectDefinitionApi);

		const {body: objectDefinition} =
			await objectDefinitionAPIClient.postObjectDefinition(
				objectDefinitionData
			);

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const {body: objectDefinition1} =
			await objectDefinitionAPIClient.postObjectDefinition(
				objectDefinition1Data
			);

		apiHelpers.data.push({
			id: objectDefinition1.id,
			type: 'objectDefinition',
		});

		const application = await apiHelpers.objectEntry.postObjectEntry(
			{
				apiApplicationToAPISchemas: [
					{
						description: 'objectDefinition1 Schema',
						externalReferenceCode: 'api-application-schema',
						mainObjectDefinitionERC:
							objectDefinition1.externalReferenceCode,
						name: 'ObjectDefinition1 Schema',
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

		apiHelpers.data.push({id: application.id, type: 'apiApplication'});

		const {body: customObjectDefinition} =
			await objectDefinitionAPIClient.getObjectDefinitionByExternalReferenceCode(
				'customObjectDefinition'
			);
		apiHelpers.data.push({
			id: customObjectDefinition.id,
			type: 'objectDefinition',
		});

		await headlessBuilderPage.goto();
		await headlessBuilderPage.goToEditApplication(application.title);
		await applicationPage.goToSchemasTab();
		await schemaPage.goTo('ObjectDefinition1 Schema');
		await schemaPage.goToPropertiesTab();

		// Assert that principal object properties are enabled

		waitForLoading;

		expect(
			await schemaPage.page.getByLabel('Add Author Property')
		).toBeEnabled();

		await schemaPage.page
			.getByRole('button', {name: 'View Related Objects'})
			.click();

		// Assert that unmodifiable system object properties are disabled

		waitForLoading;

		await schemaPage.page
			.getByRole('button', {name: 'Organization'})
			.click();

		const systemObjectPropertiesFF = await schemaPage.page
			.getByRole('button', {name: 'Organization'})
			.locator('..')
			.getByLabel('Test Unmodifiable System Object')
			.getByLabel('Add Author Property')
			.getByText('Author')
			.all();

		for (const property of systemObjectPropertiesFF) {
			await property.waitFor({state: 'attached'});
			await expect(property).toHaveClass(/disabled/);
		}

		// Assert that unmodifiable allowed system object properties are disabled without FF

		await schemaPage.page.getByRole('button', {name: 'Account'}).click();

		const systemObjectProperties = await schemaPage.page
			.getByRole('button', {name: 'Account'})
			.locator('..')
			.getByLabel('Test Unmodifiable Allowed System Object')
			.getByLabel('Add Author Property')
			.getByText('Author')
			.all();

		for (const element of systemObjectProperties) {
			await element.waitFor({state: 'attached'});
			await expect(element).toHaveClass(/disabled/);
		}

		// Assert that modifiable system object properties are enabled

		await schemaPage.page
			.getByRole('button', {name: 'API Application'})
			.click();

		const modifiableSystemProperties = await schemaPage.page
			.getByRole('button', {name: 'API Application'})
			.locator('..')
			.getByLabel('Test Modifiable System Object')
			.getByLabel('Add Author Property')
			.getByText('Author')
			.all();

		for (const property of modifiableSystemProperties) {
			await property.waitFor({state: 'attached'});
			await expect(property).not.toHaveClass(/disabled/);
		}

		// Assert that custom object properties are enabled

		await schemaPage.page
			.getByRole('button', {name: 'ObjectDefinition'})
			.click();

		const customObjectProperties = await schemaPage.page
			.getByRole('button', {name: 'ObjectDefinition'})
			.locator('..')
			.getByLabel('Test Custom Object')
			.getByLabel('Add Author Property')
			.getByText('Author')
			.all();

		for (const property of customObjectProperties) {
			await property.waitFor({state: 'attached'});
			await expect(property).not.toHaveClass(/disabled/);
		}
	}
);

testFeatureFlagsEnabled(
	'check related objects enablement with feature flag',
	async ({apiHelpers, applicationPage, headlessBuilderPage, schemaPage}) => {
		const objectDefinitionAPIClient =
			await apiHelpers.buildRestClient(ObjectDefinitionApi);

		const {body: objectDefinition} =
			await objectDefinitionAPIClient.postObjectDefinition(
				objectDefinitionData
			);

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const {body: objectDefinition1} =
			await objectDefinitionAPIClient.postObjectDefinition(
				objectDefinition1Data
			);

		apiHelpers.data.push({
			id: objectDefinition1.id,
			type: 'objectDefinition',
		});

		const application = await apiHelpers.objectEntry.postObjectEntry(
			{
				apiApplicationToAPISchemas: [
					{
						description: 'objectDefinition1 Schema',
						externalReferenceCode: 'api-application-schema',
						mainObjectDefinitionERC:
							objectDefinition1.externalReferenceCode,
						name: 'ObjectDefinition1 Schema',
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

		apiHelpers.data.push({id: application.id, type: 'apiApplication'});

		const {body: customObjectDefinition} =
			await objectDefinitionAPIClient.getObjectDefinitionByExternalReferenceCode(
				'customObjectDefinition'
			);
		apiHelpers.data.push({
			id: customObjectDefinition.id,
			type: 'objectDefinition',
		});

		await headlessBuilderPage.goto();
		await headlessBuilderPage.goToEditApplication(application.title);
		await applicationPage.goToSchemasTab();
		await schemaPage.goTo('ObjectDefinition1 Schema');
		await schemaPage.goToPropertiesTab();

		// Assert that principal object properties are enabled

		waitForLoading;

		await expect(
			await schemaPage.page.getByLabel('Add Author Property')
		).toBeEnabled();

		await schemaPage.page
			.getByRole('button', {name: 'View Related Objects'})
			.click();

		// Assert that unmodifiable system object properties are disabled

		waitForLoading;

		await schemaPage.page
			.getByRole('button', {name: 'Organization'})
			.waitFor({state: 'visible'});

		await schemaPage.page
			.getByRole('button', {name: 'Organization'})
			.click();

		const unmodifiableSystemProperties = await schemaPage.page
			.getByRole('button', {name: 'Organization'})
			.locator('..')
			.getByLabel('Test Unmodifiable System Object')
			.getByLabel('Add Author Property')
			.getByText('Author')
			.all();

		for (const property of unmodifiableSystemProperties) {
			await property.waitFor({state: 'attached'});
			await expect(property).toHaveClass(/disabled/);
		}

		// Assert that unmodifiable allowed system object properties are enabled with FF

		await schemaPage.page.getByRole('button', {name: 'Account'}).click();

		const unmodifiableAllowedSystemProperties = await schemaPage.page
			.getByRole('button', {name: 'Account'})
			.locator('..')
			.getByLabel('Test Unmodifiable Allowed System Object')
			.getByLabel('Add Author Property')
			.getByText('Author')
			.all();

		for (const property of unmodifiableAllowedSystemProperties) {
			await property.waitFor({state: 'attached'});
			await expect(property).not.toHaveClass(/disabled/);
		}

		// Assert that modifiable system object properties are enabled

		await schemaPage.page
			.getByRole('button', {name: 'API Application'})
			.click();

		const modifiableSystemProperties = await schemaPage.page
			.getByRole('button', {name: 'API Application'})
			.locator('..')
			.getByLabel('Test Modifiable System Object')
			.getByLabel('Add Author Property')
			.getByText('Author')
			.all();

		for (const property of modifiableSystemProperties) {
			await property.waitFor({state: 'attached'});
			await expect(property).not.toHaveClass(/disabled/);
		}

		// Assert that custom object properties are enabled

		await schemaPage.page
			.getByRole('button', {name: 'ObjectDefinition'})
			.click();

		const customObjectProperties = await schemaPage.page
			.getByRole('button', {name: 'ObjectDefinition'})
			.locator('..')
			.getByLabel('Test Custom Object')
			.getByLabel('Add Author Property')
			.getByText('Author')
			.all();

		for (const property of customObjectProperties) {
			await property.waitFor({state: 'attached'});
			await expect(property).not.toHaveClass(/disabled/);
		}
	}
);
