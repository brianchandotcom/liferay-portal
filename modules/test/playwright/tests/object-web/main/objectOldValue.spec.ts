/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ObjectActionAPI,
	ObjectValidationRuleAPI,
} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import getRandomString from '../../../utils/getRandomString';

const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	objectPagesTest
);

test(
	'LPD-78504 Can create action Add an Object Entry using oldValue with On After Delete trigger',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, site: _site}) => {
		const suffix = getRandomString().substring(0, 8);

		const objectDefinitionA =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectDefinitionExternalReferenceCode: `ObjA${suffix}`,
				objectFields: [
					{
						DBType: 'String',
						businessType: 'Text',
						externalReferenceCode: 'customObjectField',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'Custom Field'},
						listTypeDefinitionId: 0,
						localized: false,
						name: 'customObjectField',
						required: false,
						system: false,
						type: 'String',
					},
				],
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinitionA.id,
			type: 'objectDefinition',
		});

		const objectDefinitionB =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectDefinitionExternalReferenceCode: `ObjB${suffix}`,
				objectFields: [
					{
						DBType: 'String',
						businessType: 'Text',
						externalReferenceCode: 'customObjectField',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'Custom Field'},
						listTypeDefinitionId: 0,
						localized: false,
						name: 'customObjectField',
						required: false,
						system: false,
						type: 'String',
					},
				],
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinitionB.id,
			type: 'objectDefinition',
		});

		const objectActionAPIClient =
			await apiHelpers.buildRestClient(ObjectActionAPI);

		const {body: objectAction} =
			await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
				objectDefinitionA.externalReferenceCode!,
				{
					active: true,
					conditionExpression:
						"oldValue('customObjectField') == 'Entry Test'",
					label: {en_US: 'Custom Action'},
					name: 'customAction',
					objectActionExecutorKey: 'add-object-entry',
					objectActionTriggerKey: 'onAfterDelete',
					parameters: {
						objectDefinitionExternalReferenceCode:
							objectDefinitionB.externalReferenceCode,
						predefinedValues: [
							{
								businessType: 'Text',
								inputAsValue: true,
								label: {en_US: 'Custom Field'},
								name: 'customObjectField',
								value: 'Works!',
							},
						],
					},
				}
			);

		apiHelpers.data.push({id: objectAction.id, type: 'objectAction'});

		const applicationNameA =
			'c/' + objectDefinitionA.name!.toLowerCase() + 's';

		const entry = await apiHelpers.objectEntry.postObjectEntry(
			{customObjectField: 'Entry Test'},
			applicationNameA
		);

		await apiHelpers.objectEntry.deleteObjectEntry(
			applicationNameA,
			String(entry.id)
		);

		await page.waitForTimeout(2000);

		const applicationNameB =
			'c/' + objectDefinitionB.name!.toLowerCase() + 's';

		const entriesB =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				applicationNameB
			);

		expect(entriesB.items.length).toBeGreaterThanOrEqual(1);

		const addedEntry = entriesB.items.find(
			(item: ObjectEntry) => item.customObjectField === 'Works!'
		);

		expect(addedEntry).toBeTruthy();
	}
);

test(
	'LPD-78504 Can create action Add an Object Entry using oldValue with On After Update trigger and Picklist field',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, site: _site}) => {
		const suffix = getRandomString().substring(0, 8);

		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		await apiHelpers.listTypeAdmin.postListTypeEntry({
			key: 'open',
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			name_i18n: {en_US: 'Open'},
		});

		await apiHelpers.listTypeAdmin.postListTypeEntry({
			key: 'inprogress',
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			name_i18n: {en_US: 'In Progress'},
		});

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectDefinitionExternalReferenceCode: `Obj${suffix}`,
				objectFields: [
					{
						DBType: 'String',
						businessType: 'Text',
						externalReferenceCode: 'customTextField',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'Custom Text Field'},
						listTypeDefinitionId: 0,
						localized: false,
						name: 'customTextField',
						required: true,
						system: false,
						type: 'String',
					},
					{
						DBType: 'String',
						businessType: 'Picklist',
						externalReferenceCode: 'customPicklistField',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'Custom Picklist Field'},
						listTypeDefinitionExternalReferenceCode:
							listTypeDefinition.externalReferenceCode,
						listTypeDefinitionId: listTypeDefinition.id,
						localized: false,
						name: 'customPicklistField',
						required: false,
						system: false,
						type: 'String',
					},
				],
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectActionAPIClient =
			await apiHelpers.buildRestClient(ObjectActionAPI);

		const {body: objectAction} =
			await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
				objectDefinition.externalReferenceCode!,
				{
					active: true,
					conditionExpression:
						"oldValue('customPicklistField') == 'open'",
					label: {en_US: 'Custom Action'},
					name: 'customAction',
					objectActionExecutorKey: 'add-object-entry',
					objectActionTriggerKey: 'onAfterUpdate',
					parameters: {
						objectDefinitionExternalReferenceCode:
							objectDefinition.externalReferenceCode,
						predefinedValues: [
							{
								businessType: 'Text',
								inputAsValue: true,
								label: {en_US: 'Custom Text Field'},
								name: 'customTextField',
								value: 'Object entry added',
							},
						],
					},
				}
			);

		apiHelpers.data.push({id: objectAction.id, type: 'objectAction'});

		const applicationName =
			'c/' + objectDefinition.name!.toLowerCase() + 's';

		const entry = await apiHelpers.objectEntry.postObjectEntry(
			{customTextField: 'Entry Test'},
			applicationName
		);

		await apiHelpers.objectEntry.patchObjectEntry(
			{customPicklistField: {key: 'open', name: 'Open'}},
			applicationName,
			entry.id
		);

		await apiHelpers.objectEntry.patchObjectEntry(
			{customPicklistField: {key: 'inprogress', name: 'In Progress'}},
			applicationName,
			entry.id
		);

		await page.waitForTimeout(2000);

		const entries =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				applicationName
			);

		const addedEntry = entries.items.find(
			(item: ObjectEntry) =>
				item.customTextField === 'Object entry added'
		);

		expect(addedEntry).toBeTruthy();
	}
);

test(
	'LPD-78504 Can create action Notification using oldValue with On After Add trigger',
	{tag: '@LPD-78504'},
	async ({apiHelpers: _apiHelpers, page: _page, site: _site}) => {
		test.fixme(
			true,
			'Test requires email/notification verification infrastructure (MockMock/SMTP server)'
		);
	}
);

test(
	'LPD-78504 Can create action Notification using oldValue with On After Delete trigger',
	{tag: '@LPD-78504'},
	async ({apiHelpers: _apiHelpers, page: _page, site: _site}) => {
		test.fixme(
			true,
			'Test requires email/notification verification infrastructure (MockMock/SMTP server)'
		);
	}
);

test(
	'LPD-78504 Can create action Notification using oldValue with On After Update trigger',
	{tag: '@LPD-78504'},
	async ({apiHelpers: _apiHelpers, page: _page, site: _site}) => {
		test.fixme(
			true,
			'Test requires email/notification verification infrastructure (MockMock/SMTP server)'
		);
	}
);

test(
	'LPD-78504 Can create action Notification using oldValue with On After Update trigger on Account Object',
	{tag: '@LPD-78504'},
	async ({apiHelpers: _apiHelpers, page: _page, site: _site}) => {
		test.fixme(
			true,
			'Test requires email/notification verification infrastructure (MockMock/SMTP server)'
		);
	}
);

test(
	'LPD-78504 Can create action Notification using oldValue with On After Update trigger on User Object',
	{tag: '@LPD-78504'},
	async ({apiHelpers: _apiHelpers, page: _page, site: _site}) => {
		test.fixme(
			true,
			'Test requires email/notification verification infrastructure (MockMock/SMTP server)'
		);
	}
);

test(
	'LPD-78504 Can create action Update an Object Entry using oldValue with On After Update trigger',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, site: _site}) => {
		const suffix = getRandomString().substring(0, 8);

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectDefinitionExternalReferenceCode: `Obj${suffix}`,
				objectFields: [
					{
						DBType: 'String',
						businessType: 'Text',
						externalReferenceCode: 'customField',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'Custom Field'},
						listTypeDefinitionId: 0,
						localized: false,
						name: 'customField',
						required: false,
						system: false,
						type: 'String',
					},
				],
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectActionAPIClient =
			await apiHelpers.buildRestClient(ObjectActionAPI);

		const {body: objectAction} =
			await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
				objectDefinition.externalReferenceCode!,
				{
					active: true,
					conditionExpression:
						'oldValue("customField") == "Entry Test"',
					label: {en_US: 'Custom Action'},
					name: 'customAction',
					objectActionExecutorKey: 'update-object-entry',
					objectActionTriggerKey: 'onAfterUpdate',
					parameters: {
						objectDefinitionExternalReferenceCode:
							objectDefinition.externalReferenceCode,
						predefinedValues: [
							{
								businessType: 'Text',
								inputAsValue: true,
								label: {en_US: 'Custom Field'},
								name: 'customField',
								value: 'Object entry updated',
							},
						],
					},
				}
			);

		apiHelpers.data.push({id: objectAction.id, type: 'objectAction'});

		const applicationName =
			'c/' + objectDefinition.name!.toLowerCase() + 's';

		const entry = await apiHelpers.objectEntry.postObjectEntry(
			{customField: 'Entry Test'},
			applicationName
		);

		await apiHelpers.objectEntry.patchObjectEntry(
			{customField: 'Entry Test Edited'},
			applicationName,
			entry.id
		);

		await page.waitForTimeout(2000);

		const updatedEntry = await apiHelpers.objectEntry.getObjectEntryById(
			applicationName,
			String(entry.id)
		);

		expect(updatedEntry.customField).toBe('Object entry updated');
	}
);

test(
	'LPD-78504 Can create action using oldValue with On After Add trigger and Picklist field',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, site: _site}) => {
		const suffix = getRandomString().substring(0, 8);

		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		await apiHelpers.listTypeAdmin.postListTypeEntry({
			key: 'open',
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			name_i18n: {en_US: 'Open'},
		});

		await apiHelpers.listTypeAdmin.postListTypeEntry({
			key: 'closed',
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			name_i18n: {en_US: 'Closed'},
		});

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectDefinitionExternalReferenceCode: `Obj${suffix}`,
				objectFields: [
					{
						DBType: 'String',
						businessType: 'Text',
						externalReferenceCode: 'customTextField',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'Custom Text Field'},
						listTypeDefinitionId: 0,
						localized: false,
						name: 'customTextField',
						required: true,
						system: false,
						type: 'String',
					},
					{
						DBType: 'String',
						businessType: 'Picklist',
						externalReferenceCode: 'customPicklistField',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'Custom Picklist Field'},
						listTypeDefinitionExternalReferenceCode:
							listTypeDefinition.externalReferenceCode,
						listTypeDefinitionId: listTypeDefinition.id,
						localized: false,
						name: 'customPicklistField',
						required: false,
						system: false,
						type: 'String',
					},
				],
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectActionAPIClient =
			await apiHelpers.buildRestClient(ObjectActionAPI);

		const {body: objectAction} =
			await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
				objectDefinition.externalReferenceCode!,
				{
					active: true,
					conditionExpression:
						'isEmpty(oldValue("customPicklistField"))',
					label: {en_US: 'Custom Action'},
					name: 'customAction',
					objectActionExecutorKey: 'add-object-entry',
					objectActionTriggerKey: 'onAfterAdd',
					parameters: {
						objectDefinitionExternalReferenceCode:
							objectDefinition.externalReferenceCode,
						predefinedValues: [
							{
								businessType: 'Text',
								inputAsValue: true,
								label: {en_US: 'Custom Text Field'},
								name: 'customTextField',
								value: 'Object entry added',
							},
						],
					},
				}
			);

		apiHelpers.data.push({id: objectAction.id, type: 'objectAction'});

		const applicationName =
			'c/' + objectDefinition.name!.toLowerCase() + 's';

		await apiHelpers.objectEntry.postObjectEntry(
			{customTextField: 'Entry Test'},
			applicationName
		);

		await page.waitForTimeout(2000);

		const entries =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				applicationName
			);

		const addedEntry = entries.items.find(
			(item: ObjectEntry) =>
				item.customTextField === 'Object entry added'
		);

		expect(addedEntry).toBeTruthy();
	}
);

test(
	'LPD-78504 Can create validation using oldValue function with Picklist field',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page: _page, site: _site}) => {
		const suffix = getRandomString().substring(0, 8);

		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		await apiHelpers.listTypeAdmin.postListTypeEntry({
			key: 'open',
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			name_i18n: {en_US: 'Open'},
		});

		await apiHelpers.listTypeAdmin.postListTypeEntry({
			key: 'inprogress',
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			name_i18n: {en_US: 'In Progress'},
		});

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectDefinitionExternalReferenceCode: `Obj${suffix}`,
				objectFields: [
					{
						DBType: 'String',
						businessType: 'Picklist',
						externalReferenceCode: 'customPicklistField',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'Custom Picklist Field'},
						listTypeDefinitionExternalReferenceCode:
							listTypeDefinition.externalReferenceCode,
						listTypeDefinitionId: listTypeDefinition.id,
						localized: false,
						name: 'customPicklistField',
						required: false,
						system: false,
						type: 'String',
					},
				],
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectValidationRuleAPIClient =
			await apiHelpers.buildRestClient(ObjectValidationRuleAPI);

		const {body: objectValidationRule} =
			await objectValidationRuleAPIClient.postObjectDefinitionByExternalReferenceCodeObjectValidationRule(
				objectDefinition.externalReferenceCode!,
				{
					active: false,
					engine: 'ddm',
					errorLabel: {en_US: 'Validation error'},
					name: {en_US: 'Custom validation'},
					script: "oldValue('customPicklistField') == 'open'",
				}
			);

		const applicationName =
			'c/' + objectDefinition.name!.toLowerCase() + 's';

		const entry = await apiHelpers.objectEntry.postObjectEntry(
			{customPicklistField: {key: 'open', name: 'Open'}},
			applicationName
		);

		await objectValidationRuleAPIClient.putObjectValidationRule(
			objectValidationRule.id!,
			{
				...objectValidationRule,
				active: true,
			}
		);

		const updatedEntry = await apiHelpers.objectEntry.patchObjectEntry(
			{customPicklistField: {key: 'inprogress', name: 'In Progress'}},
			applicationName,
			entry.id
		);

		expect(updatedEntry.customPicklistField.key).toBe('inprogress');
	}
);
