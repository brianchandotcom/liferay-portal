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

const basicApiApplication = {
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

const studentSubjectsApplication = {
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
			description: 'The student schema',
			externalReferenceCode: 'Student-schema',
			mainObjectDefinitionERC: 'student-definition',
			name: 'Student schema',
		},
		{
			apiSchemaToAPIProperties: [
				{
					description: 'Name of the subject',
					externalReferenceCode: 'subject-name-property',
					name: 'subjectName',
					objectFieldERC: 'subject-name-field',
				},
			],
			description: 'The subject schema',
			externalReferenceCode: 'Subject-schema',
			mainObjectDefinitionERC: 'subject-definition',
			name: 'Subject schema',
		},
	],
	applicationStatus: 'published',
	baseURL: 'student-subjects',
	description: 'Retrive the data from the different students/subjects.',
	externalReferenceCode: 'student-subjects-application',
	title: 'Student-Subject manager',
};

test('can create post endpoint with different request and response schema', async ({
	apiApplicationPage,
	apiExplorerPage,
	apiHelpers,
	headlessBuilderPage,
	page,
}) => {
	const subjectResponse = await apiHelpers.objectAdmin.postObjectDefinition({
		active: true,
		externalReferenceCode: 'subject-definition',
		label: {
			en_US: 'Subject',
		},
		name: 'Subject',
		objectFields: [
			{
				DBType: 'String',
				businessType: 'Text',
				externalReferenceCode: 'subject-name-field',
				indexed: true,
				indexedAsKeyword: false,
				indexedLanguageId: 'en_US',
				label: {
					en_US: 'Subject name',
				},
				listTypeDefinitionId: 0,
				name: 'subjectName',
				required: false,
				state: false,
				system: false,
				type: 'String',
			},
		],
		panelCategoryKey: 'control_panel.object',
		pluralLabel: {
			en_US: 'Subjects',
		},
		portlet: true,
		restContextPath: '/o/c/subjects',
		scope: 'company',
		status: {
			code: 0,
		},
	});
	const studentResponse = await apiHelpers.objectAdmin.postObjectDefinition({
		active: true,
		externalReferenceCode: 'student-definition',
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
		objectRelationships: [
			{
				deletionType: 'cascade',
				externalReferenceCode: 'student-subjects-relationship',
				label: {
					en_US: 'Student subjects',
				},
				name: 'studentSubjects',
				objectDefinitionExternalReferenceCode1: 'student-definition',
				objectDefinitionExternalReferenceCode2: 'subject-definition',
				objectDefinitionModifiable2: true,
				objectDefinitionName2: 'Subject',
				objectDefinitionSystem2: false,
				objectField: {
					DBType: 'Long',
					businessType: 'Relationship',
					externalReferenceCode:
						'student-subjects-relationship-field',
					indexed: true,
					indexedAsKeyword: false,
					indexedLanguageId: '',
					label: {
						en_US: 'Student subjects',
					},
					name: 'r_studentSubjects_c_studentId',
					objectFieldSettings: [
						{
							name: 'objectDefinition1ShortName',
							value: 'Student',
						},
						{
							name: 'objectRelationshipERCObjectFieldName',
							value: 'r_studentSubjects_c_studentERC',
						},
					],
					relationshipType: 'oneToMany',
					required: false,
					state: false,
					system: false,
					type: 'Long',
					unique: false,
				},
				parameterObjectFieldId: 0,
				parameterObjectFieldName: '',
				reverse: false,
				system: false,
				type: 'oneToMany',
			},
		],
		panelCategoryKey: 'control_panel.object',
		pluralLabel: {
			en_US: 'Students',
		},
		portlet: true,
		restContextPath: '/o/c/students',
		scope: 'company',
		status: {
			code: 0,
		},
	});

	await waitForHeadlessBuilderReady(apiHelpers, page);
	await apiHelpers.object.postObjectEntry(
		studentSubjectsApplication,
		'headless-builder/applications'
	);

	await headlessBuilderPage.goto();
	await headlessBuilderPage.goToEditAPIApplication(
		studentSubjectsApplication.title
	);

	await apiApplicationPage.createApiEndpoint('POST', 'Company', 'student');

	await apiApplicationPage.goToEndpointConfigurationTab();
	await apiApplicationPage.selectEndpointRequestSchema(
		studentSubjectsApplication.apiApplicationToAPISchemas[0].name
	);
	await apiApplicationPage.selectEndpointResponseSchema(
		studentSubjectsApplication.apiApplicationToAPISchemas[1].name
	);

	await apiApplicationPage.publishButton.click();

	await apiExplorerPage.goToApplication(
		`c/${studentSubjectsApplication.baseURL}`
	);

	await expect(apiExplorerPage.getEndpointLocator('/student')).toBeVisible();

	await page.goto('/');
	await apiHelpers.object.deleteObjectEntryByExternalReferenceCode(
		'headless-builder/applications',
		studentSubjectsApplication.externalReferenceCode
	);
	await apiHelpers.objectAdmin.deleteObjectRelationship(
		studentResponse.objectRelationships[0].id
	);
	await apiHelpers.objectAdmin.deleteObjectDefinition(studentResponse.id);
	await apiHelpers.objectAdmin.deleteObjectDefinition(subjectResponse.id);
});

test('can create post method endpoint with company scope', async ({
	apiApplicationPage,
	apiExplorerPage,
	apiHelpers,
	headlessBuilderPage,
	page,
}) => {
	await waitForHeadlessBuilderReady(apiHelpers, page);
	await apiHelpers.object.postObjectEntry(
		basicApiApplication,
		'headless-builder/applications'
	);

	await headlessBuilderPage.goto();
	await headlessBuilderPage.goToEditAPIApplication(basicApiApplication.title);

	await apiApplicationPage.createApiEndpoint(
		'POST',
		'Company',
		'test-post-endpoint'
	);

	await apiApplicationPage.goToEndpointConfigurationTab();
	await apiApplicationPage.selectEndpointRequestSchema(
		basicApiApplication.apiApplicationToAPISchemas[0].name
	);
	await apiApplicationPage.publishButton.click();

	await apiExplorerPage.goToApplication(`c/${basicApiApplication.baseURL}`);

	await expect(
		apiExplorerPage.getEndpointLocator('/test-post-endpoint')
	).toBeVisible();

	await page.goto('/');
	await apiHelpers.object.deleteObjectEntryByExternalReferenceCode(
		'headless-builder/applications',
		basicApiApplication.externalReferenceCode
	);
});
