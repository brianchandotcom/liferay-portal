/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {headlessDiscoveryPagesTest} from '../../fixtures/headlessDiscoveryWebPagesTest';
import {loginTest} from '../../fixtures/loginTest';
import {headlessBuilderPagesTest} from './fixtures/headlessBuilderPagesTest';

export const test = mergeTests(
	apiHelpersTest,
	loginTest(),
	headlessBuilderPagesTest(),
	headlessDiscoveryPagesTest
);

test('can associate and disassociate schema', async ({
	apiHelpers,
	applicationPage,
	headlessBuilderPage,
	page,
}) => {
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
			applicationStatus: 'unpublished',
			baseURL: 'basic-application',
			description: 'Test API Application',
			externalReferenceCode: 'basic-application',
			title: 'Basic application',
		},
		'headless-builder/applications'
	);

	const endpoint = await apiHelpers.objectEntry.postObjectEntry(
		{
			description: 'Test API Endpoint',
			externalReferenceCode: 'basic-endpoint',
			httpMethod: 'get',
			name: 'Basic API Endpoint',
			path: '/endpoint/',
			r_apiApplicationToAPIEndpoints_l_apiApplicationERC:
				application.externalReferenceCode,
			r_responseAPISchemaToAPIEndpoints_l_apiSchemaERC:
				application.apiApplicationToAPISchemas[0].externalReferenceCode,
			retrieveType: 'collection',
			scope: 'company',
		},
		'headless-builder/endpoints'
	);

	await headlessBuilderPage.goto();
	await headlessBuilderPage.goToEditApplication(application.title);
	await applicationPage.goToEndpointsTab();
	await applicationPage.goToEditEndpoint(endpoint.path);
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
	await applicationPage.goToEditEndpoint(endpoint.path);
	await applicationPage.goToEndpointConfigurationTab();

	const responseBodySchemaContent = await page
		.getByLabel('Response Body Schema')
		.textContent();

	await expect(responseBodySchemaContent).toEqual('Select a Schema');

	await apiHelpers.objectEntry.deleteObjectEntryByExternalReferenceCode(
		'headless-builder/applications',
		application.externalReferenceCode
	);
});

test('can see available path parameter properties of a singleElement endpoint', async ({
	applicationPage,
	headlessBuilderPage,
	page,
}) => {
	await headlessBuilderPage.goto();
	await headlessBuilderPage.addNewApplicationButton.click();
	await headlessBuilderPage.newApplicationTitleBox.fill('My-app');
	await headlessBuilderPage.createApplicationButton.click();

	await applicationPage.goToSchemasTab();
	await applicationPage.addSchemaButton.click();
	await applicationPage.schemaNameTextBox.fill('API Application schema');
	await applicationPage.setSchemaMainObjectDefinition('APIApplication');
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

	await headlessBuilderPage.goto();
	await headlessBuilderPage.deleteApplication('My-app');
});

test('can see path parameter property with map details', async ({
	applicationPage,
	headlessBuilderPage,
	page,
}) => {
	await headlessBuilderPage.goto();
	await headlessBuilderPage.addNewApplicationButton.click();
	await headlessBuilderPage.newApplicationTitleBox.fill('My-app');
	await headlessBuilderPage.createApplicationButton.click();

	await applicationPage.goToSchemasTab();
	await applicationPage.addSchemaButton.click();
	await applicationPage.schemaNameTextBox.fill('API Application schema');
	await applicationPage.setSchemaMainObjectDefinition('APIApplication');
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

	await headlessBuilderPage.goto();
	await headlessBuilderPage.deleteApplication('My-app');
});

test('can see schema unique fields as path parameter properties', async ({
	apiHelpers,
	applicationPage,
	headlessBuilderPage,
	page,
}) => {
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

	const endpoint = await apiHelpers.objectEntry.postObjectEntry(
		{
			description: 'Test API Endpoint',
			externalReferenceCode: 'basic-endpoint',
			httpMethod: 'get',
			name: 'Basic API Endpoint',
			path: '/endpoint/{pathParam}',
			r_apiApplicationToAPIEndpoints_l_apiApplicationERC:
				application.externalReferenceCode,
			r_responseAPISchemaToAPIEndpoints_l_apiSchemaERC:
				application.apiApplicationToAPISchemas[0].externalReferenceCode,
			retrieveType: 'singleElement',
			scope: 'company',
		},
		'headless-builder/endpoints'
	);

	await headlessBuilderPage.goto();
	await headlessBuilderPage.goToEditApplication(application.title);
	await applicationPage.goToEndpointsTab();
	await applicationPage.goToEditEndpoint(endpoint.path);
	await applicationPage.goToEndpointConfigurationTab();

	await page.getByRole('button', {name: 'Select an Option'}).click();
	await expect(page.getByRole('menuitem', {name: 'Base URL'})).toBeVisible();
	await expect(
		page.getByRole('menuitem', {name: 'External Reference Code'})
	).toBeVisible();
	await expect(page.getByRole('menuitem', {name: 'ID'})).toBeVisible();
	await expect(page.getByRole('menuitem', {name: 'Title'})).toBeVisible();

	await apiHelpers.objectEntry.deleteObjectEntryByExternalReferenceCode(
		'headless-builder/applications',
		application.externalReferenceCode
	);
});

test('can list site scoped endpoint', async ({
	apiHelpers,
	applicationPage,
	headlessBuilderPage,
	page,
}) => {
	const studentSiteDefinition =
		await apiHelpers.objectAdmin.postObjectDefinition({
			active: true,
			externalReferenceCode: 'site-student-definition',
			label: {
				en_US: 'Student',
			},
			name: 'Student',
			objectFields: [
				{
					DBType: 'String',
					businessType: 'Text',
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
					type: 'String',
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

	await apiHelpers.objectEntry.deleteObjectEntryByExternalReferenceCode(
		'headless-builder/applications',
		studentApplication.externalReferenceCode
	);
	await apiHelpers.objectAdmin.deleteObjectDefinition(
		studentSiteDefinition.id
	);
});
