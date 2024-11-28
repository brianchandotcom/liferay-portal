/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ObjectDefinition,
	ObjectDefinitionApi,
	ObjectField,
} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {headlessDiscoveryPagesTest} from '../../fixtures/headlessDiscoveryWebPagesTest';
import {loginTest} from '../../fixtures/loginTest';
import {headlessBuilderPagesTest} from './fixtures/headlessBuilderPagesTest';

export const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	loginTest(),
	headlessBuilderPagesTest(),
	headlessDiscoveryPagesTest
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
	applicationStatus: 'unpublished',
	baseURL: 'basic-application',
	description: 'Test API Application',
	externalReferenceCode: 'basic-application',
	title: 'Basic application',
};

test('can associate and disassociate schema', async ({
	apiHelpers,
	applicationPage,
	headlessBuilderPage,
	page,
}) => {
	const apiApplication = await apiHelpers.objectEntry.postObjectEntry(
		{
			...applicationData,
			apiApplicationToAPIEndpoints: [
				{
					description: 'Test API Endpoint',
					externalReferenceCode: 'basic-endpoint',
					httpMethod: 'get',
					name: 'Basic API Endpoint',
					path: '/endpoint/',
					r_responseAPISchemaToAPIEndpoints_l_apiSchemaERC:
						'api-application-schema',
					retrieveType: 'collection',
					scope: 'company',
				},
			],
		},
		'headless-builder/applications'
	);

	apiHelpers.data.push({id: apiApplication.id, type: 'apiApplication'});

	await headlessBuilderPage.goto();
	await headlessBuilderPage.goToEditApplication(applicationData.title);
	await applicationPage.goToEndpointsTab();
	await applicationPage.goToEditEndpoint('/endpoint/');
	await applicationPage.goToEndpointConfigurationTab();

	await page.getByLabel('Response Body Schema').click();
	await expect(
		page.getByRole('menuitem', {name: 'Not Selected'})
	).toBeVisible();

	await page.getByRole('menuitem', {name: 'Not Selected'}).click();

	await applicationPage.publishButton.click();

	await page.waitForTimeout(1500);

	await applicationPage.goToDetailsTab();
	await applicationPage.goToEndpointsTab();
	await applicationPage.goToEditEndpoint('/endpoint/');
	await applicationPage.goToEndpointConfigurationTab();

	const responseBodySchemaContent = await page
		.getByLabel('Response Body Schema')
		.textContent();

	await expect(responseBodySchemaContent).toEqual('Select a Schema');
});

test('can see available path parameter properties of a singleElement endpoint', async ({
	apiHelpers,
	applicationPage,
	headlessBuilderPage,
	page,
}) => {
	const objectDefinition =
		(await apiHelpers.objectAdmin.postRandomObjectDefinition(
			{code: 0},
			undefined,
			'default'
		)) as ObjectDefinition;

	apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

	await headlessBuilderPage.goto();
	await headlessBuilderPage.addNewApplicationButton.click();
	await headlessBuilderPage.newApplicationTitleBox.fill('My-app');
	await headlessBuilderPage.createApplicationButton.click();

	const apiApplicationPage =
		await apiHelpers.apiBuilder.getAPIApplicationsPage();

	apiHelpers.data.push({
		id: apiApplicationPage.items[0].id,
		type: 'apiApplication',
	});

	await applicationPage.goToSchemasTab();
	await applicationPage.addSchemaButton.click();
	await applicationPage.schemaNameTextBox.fill('API Application schema');
	await applicationPage.setSchemaMainObjectDefinition('objectDefinition');
	await applicationPage.createButton.click();

	await applicationPage.createSingleElementEndpoint(
		'Company',
		'gettest',
		'entryid'
	);
	await applicationPage.goToEndpointConfigurationTab();
	await applicationPage.selectEndpointResponseSchema(
		'API Application schema'
	);

	await page.getByRole('button', {name: 'Select an Option'}).click();
	await expect(
		page.getByRole('menuitem', {name: 'External Reference Code'})
	).toBeVisible();
	await expect(page.getByRole('menuitem', {name: 'ID'})).toBeVisible();
});

test('can see path parameter property with map details', async ({
	apiHelpers,
	applicationPage,
	headlessBuilderPage,
	page,
}) => {
	const objectDefinition =
		(await apiHelpers.objectAdmin.postRandomObjectDefinition(
			{code: 0},
			undefined,
			'default'
		)) as ObjectDefinition;

	apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

	await headlessBuilderPage.goto();
	await headlessBuilderPage.addNewApplicationButton.click();
	await headlessBuilderPage.newApplicationTitleBox.fill('My-app');
	await headlessBuilderPage.createApplicationButton.click();

	const apiApplicationPage =
		await apiHelpers.apiBuilder.getAPIApplicationsPage();

	apiHelpers.data.push({
		id: apiApplicationPage.items[0].id,
		type: 'apiApplication',
	});

	await applicationPage.goToSchemasTab();
	await applicationPage.addSchemaButton.click();
	await applicationPage.schemaNameTextBox.fill('API Application schema');
	await applicationPage.setSchemaMainObjectDefinition('objectDefinition');
	await applicationPage.createButton.click();

	await applicationPage.createSingleElementEndpoint(
		'Company',
		'gettest',
		'entryid'
	);
	await applicationPage.goToEndpointConfigurationTab();
	await applicationPage.selectEndpointResponseSchema(
		'API Application schema'
	);

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
});

test('can see schema unique fields as path parameter properties', async ({
	apiHelpers,
	applicationPage,
	headlessBuilderPage,
	page,
}) => {
	const apiApplication = await apiHelpers.objectEntry.postObjectEntry(
		{
			...applicationData,
			apiApplicationToAPIEndpoints: [
				{
					description: 'Test API Endpoint',
					externalReferenceCode: 'basic-endpoint',
					httpMethod: 'get',
					name: 'Basic API Endpoint',
					path: '/endpoint/{pathParam}',
					r_responseAPISchemaToAPIEndpoints_l_apiSchemaERC:
						'api-application-schema',
					retrieveType: 'singleElement',
					scope: 'company',
				},
			],
		},
		'headless-builder/applications'
	);

	apiHelpers.data.push({id: apiApplication.id, type: 'apiApplication'});

	await headlessBuilderPage.goto();
	await headlessBuilderPage.goToEditApplication(applicationData.title);
	await applicationPage.goToEndpointsTab();
	await applicationPage.goToEditEndpoint('/endpoint/{pathParam}');
	await applicationPage.goToEndpointConfigurationTab();

	await page.getByRole('button', {name: 'Select an Option'}).click();
	await expect(page.getByRole('menuitem', {name: 'Base URL'})).toBeVisible();
	await expect(
		page.getByRole('menuitem', {name: 'External Reference Code'})
	).toBeVisible();
	await expect(page.getByRole('menuitem', {name: 'ID'})).toBeVisible();
	await expect(page.getByRole('menuitem', {name: 'Title'})).toBeVisible();
});

test('can list site scoped endpoint', async ({
	apiHelpers,
	applicationPage,
	headlessBuilderPage,
	page,
}) => {
	const objectDefinitionAPIClient =
		await apiHelpers.buildRestClient(ObjectDefinitionApi);

	const {body: studentSiteDefinition} =
		await objectDefinitionAPIClient.postObjectDefinition({
			active: true,
			externalReferenceCode: 'site-student-definition',
			label: {
				en_US: 'Student',
			},
			name: 'Student',
			objectFields: [
				{
					DBType: ObjectField.DBTypeEnum.String,
					businessType: ObjectField.BusinessTypeEnum.Text,
					externalReferenceCode: 'student-name-field',
					indexed: true,
					indexedAsKeyword: false,
					indexedLanguageId: 'en_US',
					label: {
						en_US: 'Student name',
					},
					listTypeDefinitionId: 0,
					name: 'studentName',
					required: true,
					state: false,
					system: false,
					type: ObjectField.TypeEnum.String,
				},
			],
			pluralLabel: {
				en_US: 'Students',
			},
			portlet: true,
			restContextPath: '/o/c/students',
			scope: 'site',
			status: {
				code: 0,
			},
		});

	const studentApplication = await apiHelpers.objectEntry.postObjectEntry(
		{
			apiApplicationToAPISchemas: [
				{
					apiSchemaToAPIProperties: [
						{
							description: 'Name of the student',
							externalReferenceCode: 'student-name-property',
							name: 'studentName',
							objectFieldERC: 'student-name-field',
						},
					],
					description: 'Test site-schema',
					externalReferenceCode: 'testSchema',
					mainObjectDefinitionERC: 'site-student-definition',
					name: 'testSchema',
				},
			],
			applicationStatus: 'unpublished',
			baseURL: 'my-app',
			description: 'Retrive the data from the different students.',
			externalReferenceCode: 'my-application',
			title: 'My-app',
		},
		'headless-builder/applications'
	);

	apiHelpers.data.push({
		id: studentSiteDefinition.id,
		type: 'objectDefinition',
	});

	apiHelpers.data.push({id: studentApplication.id, type: 'apiApplication'});

	await headlessBuilderPage.goto();
	await headlessBuilderPage.goToEditApplication(studentApplication.title);
	await applicationPage.createSingleElementEndpoint(
		'Site',
		'gettest',
		'entryerc'
	);

	await applicationPage.goToEndpointConfigurationTab();
	await page.getByLabel('Response Body Schema').click();
	await page
		.getByRole('menuitem', {
			name: studentApplication.apiApplicationToAPISchemas[0].name,
		})
		.click();
	await page.getByRole('button', {name: 'Select an Option'}).click();
	await page.getByRole('menuitem', {name: 'External Reference Code'}).click();
	await applicationPage.publishButton.click();

	await headlessBuilderPage.goto();
	await headlessBuilderPage.goToEditApplication(studentApplication.title);
	await applicationPage.goToEndpointsTab();
	await applicationPage.goToEditEndpoint('/gettest/{entryerc}/');
});
