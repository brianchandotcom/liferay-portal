/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';

export const test = mergeTests(apiHelpersTest, loginTest);

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

const subjectObjectDefinition = {
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
	panelCategoryKey: 'control_panel.objects',
	pluralLabel: {
		en_US: 'Subjects',
	},
	portlet: true,
	restContextPath: '/o/c/subjects',
	scope: 'company',
	status: {
		code: 0,
	},
};

const studentObjectDefinition = {
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
				externalReferenceCode: 'student-subjects-relationship-field',
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
	panelCategoryKey: 'control_panel.objects',
	pluralLabel: {
		en_US: 'Students',
	},
	portlet: true,
	restContextPath: '/o/c/students',
	scope: 'company',
	status: {
		code: 0,
	},
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
	apiHelpers,
	page,
}) => {
	await apiHelpers.featureFlag.updateFeatureFlag('LPS-178642', true);

	const subjectResponse = await apiHelpers.objectAdmin.postObjectDefinition(
		subjectObjectDefinition
	);

	const studentResponse = await apiHelpers.objectAdmin.postObjectDefinition(
		studentObjectDefinition
	);

	await apiHelpers.object.postObjectEntry(
		studentSubjectsApplication,
		'headless-builder/applications'
	);

	await page.goto(
		'/group/guest/~/control_panel/manage?p_p_id=com_liferay_headless_builder_web_internal_portlet_HeadlessBuilderPortlet'
	);
	await page.waitForLoadState();
	await page
		.getByRole('link', {name: studentSubjectsApplication.title})
		.click();
	await page.getByRole('button', {name: 'Endpoints'}).click();
	await page.getByLabel('Add API Endpoint').click();
	await page.getByLabel('Method').click();
	await page.getByRole('menuitem', {name: 'POST'}).click();
	await page.getByLabel('Select Scope').click();
	await page.getByRole('menuitem', {name: 'Company'}).click();
	await page.getByPlaceholder('Enter Path').click();
	await page.getByPlaceholder('Enter Path').fill('student');
	await page.getByRole('button', {name: 'Create'}).click();
	await page.getByRole('tab', {name: 'Configuration'}).click();
	await page.getByLabel('Request Body Schema').click();
	await page
		.getByRole('menuitem', {
			name: studentSubjectsApplication.apiApplicationToAPISchemas[0].name,
		})
		.click();
	await page.getByRole('button', {name: 'Select a Schema'}).click();
	await page
		.getByRole('menuitem', {
			name: studentSubjectsApplication.apiApplicationToAPISchemas[1].name,
		})
		.click();
	await page.getByRole('button', {name: 'Publish'}).click();

	await page.goto(
		`http://localhost:8080/o/api?endpoint=http://localhost:8080/o/c/${studentSubjectsApplication.baseURL}/openapi.json`
	);

	expect(page.getByLabel('post ​/student')).toBeDefined;

	await page.goto('http://localhost:8080');

	await apiHelpers.object.deleteObjectEntryByExternalReferenceCode(
		'headless-builder/applications',
		studentSubjectsApplication.externalReferenceCode
	);

	await apiHelpers.objectAdmin.deleteObjectRelationship(
		studentResponse.objectRelationships[0].id
	);

	await apiHelpers.objectAdmin.deleteObjectDefinition(studentResponse.id);

	await apiHelpers.objectAdmin.deleteObjectDefinition(subjectResponse.id);

	await apiHelpers.featureFlag.updateFeatureFlag('LPS-178642', false);
});

test('can create post method endpoint with company scope', async ({
	apiHelpers,
	page,
}) => {
	await apiHelpers.featureFlag.updateFeatureFlag('LPS-178642', true);

	await apiHelpers.object.postObjectEntry(
		basicApiApplication,
		'headless-builder/applications'
	);

	await page.goto(
		'/group/guest/~/control_panel/manage?p_p_id=com_liferay_headless_builder_web_internal_portlet_HeadlessBuilderPortlet'
	);
	await page.waitForLoadState();
	await page.getByRole('link', {name: basicApiApplication.title}).click();
	await page.getByRole('button', {name: 'Endpoints'}).click();
	await page.getByLabel('Add API Endpoint').click();
	await page.getByLabel('Method').click();
	await page.getByRole('menuitem', {name: 'POST'}).click();
	await page.getByLabel('Select Scope').click();
	await page.getByRole('menuitem', {name: 'Company'}).click();
	await page.getByPlaceholder('Enter Path').click();
	await page.getByPlaceholder('Enter Path').fill('test-post-endpoint');
	await page.getByRole('button', {name: 'Create'}).click();
	await page.getByRole('tab', {name: 'Configuration'}).click();
	await page.getByLabel('Request Body Schema').click();
	await page
		.getByRole('menuitem', {
			name: basicApiApplication.apiApplicationToAPISchemas[0].name,
		})
		.click();
	await page.getByRole('button', {name: 'Publish'}).click();

	await page.goto(
		`http://localhost:8080/o/api?endpoint=http://localhost:8080/o/c/${basicApiApplication.baseURL}/openapi.json`
	);

	expect(page.getByLabel('post ​/test-post-endpoint')).toBeDefined;

	await apiHelpers.featureFlag.updateFeatureFlag('LPS-178642', false);
	await apiHelpers.object.deleteObjectEntryByExternalReferenceCode(
		'headless-builder/applications',
		basicApiApplication.externalReferenceCode
	);
});