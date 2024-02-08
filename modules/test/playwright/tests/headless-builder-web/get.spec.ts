/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {headlessBuilderPagesTest} from '../../fixtures/headlessBuilderPagesTest';
import {headlessDiscoveryPagesTest} from '../../fixtures/headlessDiscoveryWebPagesTest';
import {loginTest} from '../../fixtures/loginTest';
import {waitForHeadlessBuilderReady} from './utils/headlessBuilder';

export const test = mergeTests(
	apiHelpersTest,
	loginTest,
	headlessBuilderPagesTest,
	headlessDiscoveryPagesTest,
	featureFlagsTest({
		'LPS-178642': true,
	})
);

test('can see available path parameter properties of a singleElement endpoint', async ({
	apiApplicationPage,
	apiHelpers,
	headlessBuilderPage,
	page,
}) => {
	await waitForHeadlessBuilderReady(apiHelpers, page);

	await headlessBuilderPage.goto();
	await headlessBuilderPage.addNewAPIApplicationButton.click();
	await headlessBuilderPage.newAPIApplicationTitleBox.fill('My-app');
	await headlessBuilderPage.createApplicationButton.click();

	await apiApplicationPage.goToSchemasTab();
	await apiApplicationPage.addAPISchemaButton.click();
	await apiApplicationPage.schemaNameTextBox.fill('API Application schema');
	await apiApplicationPage.setSchemaMainObjectDefinition('APIApplication');
	await apiApplicationPage.createButton.click();

	await apiApplicationPage.createSingleElementAPIEndpoint(
		'Company',
		'gettest',
		'entryid'
	);
	await apiApplicationPage.goToEndpointConfigurationTab();

	// TODO Change when LPD-16654 is fixed

	await page.getByLabel('Response Body Schema').click();
	await page.getByRole('menuitem', {name: 'API Application schema'}).click();

	await page.getByRole('button', {name: 'Select an Option'}).click();
	await expect(
		page.getByRole('menuitem', {name: 'External Reference Code'})
	).toBeVisible();
	await expect(page.getByRole('menuitem', {name: 'ID'})).toBeVisible();

	await headlessBuilderPage.goto();
	await headlessBuilderPage.deleteApplicationThroughUI('My-app');
});

test('can see path parameter property with map details', async ({
	apiApplicationPage,
	apiHelpers,
	headlessBuilderPage,
	page,
}) => {
	await waitForHeadlessBuilderReady(apiHelpers, page);

	await headlessBuilderPage.goto();
	await headlessBuilderPage.addNewAPIApplicationButton.click();
	await headlessBuilderPage.newAPIApplicationTitleBox.fill('My-app');
	await headlessBuilderPage.createApplicationButton.click();

	await apiApplicationPage.goToSchemasTab();
	await apiApplicationPage.addAPISchemaButton.click();
	await apiApplicationPage.schemaNameTextBox.fill('API Application schema');
	await apiApplicationPage.setSchemaMainObjectDefinition('APIApplication');
	await apiApplicationPage.createButton.click();

	await apiApplicationPage.createSingleElementAPIEndpoint(
		'Company',
		'gettest',
		'entryid'
	);
	await apiApplicationPage.goToEndpointConfigurationTab();

	// TODO Change when LPD-16654 is fixed

	await page.getByLabel('Response Body Schema').click();
	await page.getByRole('menuitem', {name: 'API Application schema'}).click();

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
	await headlessBuilderPage.deleteApplicationThroughUI('My-app');
});

test('can list site scoped endpoint', async ({
	apiApplicationPage,
	apiHelpers,
	headlessBuilderPage,
	page,
}) => {
	await waitForHeadlessBuilderReady(apiHelpers, page);
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

	const studentApplication = await apiHelpers.object.postObjectEntry(
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
	await headlessBuilderPage.goToEditAPIApplication(studentApplication.title);
	await apiApplicationPage.createSingleElementAPIEndpoint(
		'Site',
		'gettest',
		'entryerc'
	);

	await apiApplicationPage.goToEndpointConfigurationTab();
	await page.getByLabel('Response Body Schema').click();
	await page
		.getByRole('menuitem', {
			name: studentApplication.apiApplicationToAPISchemas[0].name,
		})
		.click();
	await page.getByRole('button', {name: 'Select an Option'}).click();
	await page.getByRole('menuitem', {name: 'External Reference Code'}).click();
	await apiApplicationPage.publishButton.click();

	await headlessBuilderPage.goto();
	await headlessBuilderPage.goToEditAPIApplication(studentApplication.title);
	await apiApplicationPage.goToEndpointsTab();
	await apiApplicationPage.goToEditEndpoint('/gettest/{entryerc}/');

	await page.goto('/');
	await apiHelpers.object.deleteObjectEntryByExternalReferenceCode(
		'headless-builder/applications',
		studentApplication.externalReferenceCode
	);
	await apiHelpers.objectAdmin.deleteObjectDefinition(
		studentSiteDefinition.id
	);
});
