/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {headlessBuilderPagesTest} from './fixtures/headlessBuilderPagesTest';
import { expectElementToHaveClass } from '../../utils/expectElementToHaveClass';
import { expectElementToNotHaveClass } from '../../utils/expectElementToNotHaveClass';

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

const objectDefinitionData = {
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
}

const objectDefinition1Data = {
	active: true,
	externalReferenceCode: `objectDefinition1`,
	label: {
		en_US: `objectDefinition1`,
	},
	name: `ObjectDefinition1`,
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
	objectRelationships: [
		{
			deletionType: 'cascade',
			externalReferenceCode: 'modifiable-system',
			label: {
				en_US: 'Test Modifiable System Object',
			},
			name: 'testModifiableSystem',
			objectDefinitionExternalReferenceCode1: 'objectDefinition1',
			objectDefinitionExternalReferenceCode2: 'L_API_APPLICATION',
			objectDefinitionName2: 'APIApplication',
			parameterObjectFieldId: 0,
			parameterObjectFieldName: '',
			reverse: false,
			system: false,
			type: 'oneToMany',
		},
		{
			deletionType: 'cascade',
			externalReferenceCode: 'unmodifiable-system',
			label: {
				en_US: 'Test Unmodifiable System Object',
			},
			name: 'testUnmodifiableSystem',
			objectDefinitionExternalReferenceCode1: 'objectDefinition1',
			objectDefinitionExternalReferenceCode2: 'L_ORGANIZATION',
			objectDefinitionName2: 'Organization',
			parameterObjectFieldId: 0,
			parameterObjectFieldName: '',
			reverse: false,
			system: false,
			type: 'oneToMany',
		},
		{
			deletionType: 'cascade',
			externalReferenceCode: 'unmodifiable-system-whitelisted',
			label: {
				en_US: 'Test Unmodifiable Whitelisted System Object',
			},
			name: 'testUnmodifiableSystemWhitelisted',
			objectDefinitionExternalReferenceCode1: 'objectDefinition1',
			objectDefinitionExternalReferenceCode2: 'L_ACCOUNT',
			objectDefinitionName2: 'AccountEntry',
			parameterObjectFieldId: 0,
			parameterObjectFieldName: '',
			reverse: false,
			system: false,
			type: 'oneToMany',
		},
		{
			deletionType: 'cascade',
			externalReferenceCode: 'custom',
			label: {
				en_US: 'Test Custom Object',
			},
			name: 'testCustom',
			objectDefinitionExternalReferenceCode1: 'objectDefinition1',
			objectDefinitionExternalReferenceCode2: 'objectDefinition',
			objectDefinitionName2: 'objectDefinition',
			parameterObjectFieldId: 0,
			parameterObjectFieldName: '',
			reverse: false,
			system: false,
			type: 'oneToMany',
		},
	],
	pluralLabel: {
		en_US: `objectDefinitions1`,
	},
	portlet: true,
	scope: 'company',
	status: {
		code: 0,
	}
}

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
}

testFeatureFlagsDisabled(
	'can see whitelisted object definitions on schema creation',
	async ({apiHelpers, applicationPage, headlessBuilderPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postObjectDefinition(objectDefinitionData);

		const application = await apiHelpers.objectEntry.postObjectEntry(
			applicationData,
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
			await apiHelpers.objectAdmin.postObjectDefinition(objectDefinitionData);

		const application = await apiHelpers.objectEntry.postObjectEntry(
			applicationData,
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

testFeatureFlagsDisabled('check related objects enablement without feature flag', async({apiHelpers, applicationPage, headlessBuilderPage, schemaPage}) => {
	const objectDefinition =
		await apiHelpers.objectAdmin.postObjectDefinition(objectDefinitionData);

	const objectDefinition1 = 
		await apiHelpers.objectAdmin.postObjectDefinition(objectDefinition1Data);

	const application = await apiHelpers.object.postObjectEntry(
		{
			apiApplicationToAPISchemas: [
				{
					description: 'objectDefinition1 Schema',
					externalReferenceCode: 'api-application-schema',
					mainObjectDefinitionERC: objectDefinition1.externalReferenceCode,
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

	await headlessBuilderPage.goto();
	await headlessBuilderPage.goToEditApplication(application.title);
	await applicationPage.goToSchemasTab();
	await schemaPage.goTo('ObjectDefinition1 Schema')
	await schemaPage.goToPropertiesTab();
	
	//Assert that principal object properties are enabled
	expect(
		await schemaPage.page.getByLabel('Add Author Property')
	). toBeEnabled();   
	
	await schemaPage.page.getByRole('button', { name: 'View Related Objects' }).click();

	//Assert that unmodifiable system object properties are disabled
	await schemaPage.page.getByRole('button', { name: 'Organization' }).click();
	expectElementToHaveClass(
		await schemaPage.page.getByRole('button', { name: 'Organization' })
				.locator('..')
				.getByLabel('Test Unmodifiable System Object')
				.getByLabel('Add Author Property')
				.getByText('Author'),
		'disabled'
	)

	//Assert that unmodifiable whitelisted system object properties are disabled without FF
	await schemaPage.page.getByRole('button', { name: 'Account' }).click();
	expectElementToHaveClass(
		await schemaPage.page.getByRole('button', { name: 'Account' })
				.locator('..')
				.getByLabel('Test Unmodifiable Whitelisted System Object')
				.getByLabel('Add Author Property')
				.getByText('Author'),
		'disabled'
	)

	//Assert that modifiable system object properties are enabled
	await schemaPage.page.getByRole('button', { name: 'API Application' }).click();
	expectElementToNotHaveClass(
		await schemaPage.page.getByRole('button', { name: 'API Application' })
				.locator('..')
				.getByLabel('Test Modifiable System Object')
				.getByLabel('Add Author Property')
				.getByText('Author'),
		'disabled'
	)

	//Assert that custom obejct properties are enabled
	await schemaPage.page.getByRole('button', { name: 'ObjectDefinition' }).click();
	expectElementToNotHaveClass(
		await schemaPage.page.getByRole('button', { name: 'ObjectDefinition' })
				.locator('..')
				.getByLabel('Test Custom Object')
				.getByLabel('Add Author Property')
				.getByText('Author'),
		'disabled'
	)

	await apiHelpers.object.deleteObjectEntryByExternalReferenceCode(
 		'headless-builder/applications',
		application.externalReferenceCode
	);

	objectDefinition1.objectRelationships.forEach(async (objectRelationship) => {
		await apiHelpers.objectAdmin.deleteObjectRelationship(
			objectRelationship.id
		)
	})

	await apiHelpers.objectAdmin.deleteObjectDefinition(
		objectDefinition.id
	);

	await apiHelpers.objectAdmin.deleteObjectDefinition(
		objectDefinition1.id
	);
})

testFeatureFlagsEnabled('check related objects enablement with feature flag', async({apiHelpers, applicationPage, headlessBuilderPage, schemaPage}) => {
	const objectDefinition =
	await apiHelpers.objectAdmin.postObjectDefinition(objectDefinitionData);

	const objectDefinition1 = 
		await apiHelpers.objectAdmin.postObjectDefinition(objectDefinition1Data);

	const application = await apiHelpers.object.postObjectEntry(
		{
			apiApplicationToAPISchemas: [
				{
					description: 'objectDefinition1 Schema',
					externalReferenceCode: 'api-application-schema',
					mainObjectDefinitionERC: objectDefinition1.externalReferenceCode,
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

	await headlessBuilderPage.goto();
	await headlessBuilderPage.goToEditApplication(application.title);
	await applicationPage.goToSchemasTab();
	await schemaPage.goTo('ObjectDefinition1 Schema')
	await schemaPage.goToPropertiesTab();
	
	//Assert that principal object properties are enabled
	expect(
		await schemaPage.page.getByLabel('Add Author Property')
	). toBeEnabled();   

	await schemaPage.page.getByRole('button', { name: 'View Related Objects' }).click();

	//Assert that unmodifiable system object properties are disabled
	await schemaPage.page.getByRole('button', { name: 'Organization' }).click();
	expectElementToHaveClass(
		await schemaPage.page.getByRole('button', { name: 'Organization' })
				.locator('..')
				.getByLabel('Test Unmodifiable System Object')
				.getByLabel('Add Author Property')
				.getByText('Author'),
		'disabled'
	)

	//Assert that unmodifiable whitelisted system object properties are disabled with FF
	await schemaPage.page.getByRole('button', { name: 'Account' }).click();
	expectElementToNotHaveClass(
		await schemaPage.page.getByRole('button', { name: 'Account' })
				.locator('..')
				.getByLabel('Test Unmodifiable Whitelisted System Object')
				.getByLabel('Add Author Property')
				.getByText('Author'),
		'disabled'
	)

	//Assert that modifiable system object properties are enabled
	await schemaPage.page.getByRole('button', { name: 'API Application' }).click();
	expectElementToNotHaveClass(
		await schemaPage.page.getByRole('button', { name: 'API Application' })
				.locator('..')
				.getByLabel('Test Modifiable System Object')
				.getByLabel('Add Author Property')
				.getByText('Author'),
		'disabled'
	)

	//Assert that custom obejct properties are enabled
	await schemaPage.page.getByRole('button', { name: 'ObjectDefinition' }).click();
	expectElementToNotHaveClass(
		await schemaPage.page.getByRole('button', { name: 'ObjectDefinition' })
				.locator('..')
				.getByLabel('Test Custom Object')
				.getByLabel('Add Author Property')
				.getByText('Author'),
		'disabled'
	)

	await apiHelpers.object.deleteObjectEntryByExternalReferenceCode(
		'headless-builder/applications',
		application.externalReferenceCode
	);

	objectDefinition1.objectRelationships.forEach(async (objectRelationship) => {
		await apiHelpers.objectAdmin.deleteObjectRelationship(
			objectRelationship.id
		)
	})

	await apiHelpers.objectAdmin.deleteObjectDefinition(
		objectDefinition.id
	);

	await apiHelpers.objectAdmin.deleteObjectDefinition(
		objectDefinition1.id
	);
})
