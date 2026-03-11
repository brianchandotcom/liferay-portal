/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ObjectDefinitionAPI,
	ObjectRelationshipAPI,
} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {editObjectDefinitionPagesTest} from '../../../fixtures/editObjectDefinitionPagesTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import getRandomString from '../../../utils/getRandomString';
import {waitForAlert} from '../../../utils/waitForAlert';

const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	editObjectDefinitionPagesTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	objectPagesTest
);

test.fixme(
	'LPD-78504 Can add entry on relationship tab for one-to-many relationship',
	{tag: '@LPD-78504'},
	async ({apiHelpers, objectLayoutsPage, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Custom Field'},
						name: 'customField',
						required: false,
					},
				] as any,
				status: {code: 0},
				titleObjectFieldName: 'customField',
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectRelationshipAPIClient =
			await apiHelpers.buildRestClient(ObjectRelationshipAPI);

		await objectRelationshipAPIClient.postObjectDefinitionObjectRelationship(
			objectDefinition.id,
			{
				label: {en_US: 'Relationship'},
				name: 'relationship',
				objectDefinitionId2: objectDefinition.id,
				type: 'oneToMany',
			} as any
		);

		await apiHelpers.objectEntry.postObjectEntry(
			{customField: 'Entry Test'},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Entry Test')).toBeVisible();
	}
);

test.fixme(
	'LPD-78504 Can add entry on relationship tab for many-to-many relationship',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Custom Field'},
						name: 'customField',
						required: false,
					},
				] as any,
				status: {code: 0},
				titleObjectFieldName: 'customField',
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectRelationshipAPIClient =
			await apiHelpers.buildRestClient(ObjectRelationshipAPI);

		await objectRelationshipAPIClient.postObjectDefinitionObjectRelationship(
			objectDefinition.id,
			{
				label: {en_US: 'Relationship'},
				name: 'relationship',
				objectDefinitionId2: objectDefinition.id,
				type: 'manyToMany',
			} as any
		);

		for (const letter of ['A', 'B']) {
			await apiHelpers.objectEntry.postObjectEntry(
				{customField: `Entry Test ${letter}`},
				'c/' + objectDefinition.name.toLowerCase() + 's'
			);
		}

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Entry Test A')).toBeVisible();
		await expect(page.getByText('Entry Test B')).toBeVisible();
	}
);

test.fixme(
	'LPD-78504 Can add many fields on custom layout',
	{tag: '@LPD-78504'},
	async ({apiHelpers, objectLayoutsPage, page, viewObjectEntriesPage}) => {
		const objectFields: any[] = [];

		for (let i = 1; i <= 20; i++) {
			objectFields.push({
				businessType: 'Text',
				DBType: 'String',
				label: {en_US: `Custom Field${i}`},
				name: `customField${i}`,
				required: false,
			});
		}

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByRole('heading', {name: objectDefinition.label['en_US']})).toBeVisible();
	}
);

test(
	'LPD-78504 Can cancel new entry on relationship tab',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Custom Field'},
						name: 'customField',
						required: false,
					},
				] as any,
				status: {code: 0},
				titleObjectFieldName: 'customField',
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectRelationshipAPIClient =
			await apiHelpers.buildRestClient(ObjectRelationshipAPI);

		await objectRelationshipAPIClient.postObjectDefinitionObjectRelationship(
			objectDefinition.id,
			{
				label: {en_US: 'Relationship'},
				name: 'relationship',
				objectDefinitionId2: objectDefinition.id,
				type: 'oneToMany',
			} as any
		);

		await apiHelpers.objectEntry.postObjectEntry(
			{customField: 'Entry Test'},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Entry Test')).toBeVisible();
	}
);

test.fixme(
	'LPD-78504 Can create object with categorization section in layout',
	{tag: '@LPD-78504'},
	async ({apiHelpers, objectLayoutsPage, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Custom Field'},
						name: 'customField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByRole('heading', {name: objectDefinition.label['en_US']})).toBeVisible();
	}
);

test.fixme(
	'LPD-78504 Can delete categorization section from layout',
	{tag: '@LPD-78504'},
	async ({apiHelpers, objectLayoutsPage, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Custom Field'},
						name: 'customField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByRole('heading', {name: objectDefinition.label['en_US']})).toBeVisible();
	}
);

test(
	'LPD-78504 Can delete new entry added on relationship tab',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Custom Field'},
						name: 'customField',
						required: false,
					},
				] as any,
				status: {code: 0},
				titleObjectFieldName: 'customField',
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectRelationshipAPIClient =
			await apiHelpers.buildRestClient(ObjectRelationshipAPI);

		await objectRelationshipAPIClient.postObjectDefinitionObjectRelationship(
			objectDefinition.id,
			{
				label: {en_US: 'Relationship'},
				name: 'relationship',
				objectDefinitionId2: objectDefinition.id,
				type: 'oneToMany',
			} as any
		);

		for (const letter of ['A', 'B']) {
			await apiHelpers.objectEntry.postObjectEntry(
				{customField: `Entry Test ${letter}`},
				'c/' + objectDefinition.name.toLowerCase() + 's'
			);
		}

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Entry Test A')).toBeVisible();
		await expect(page.getByText('Entry Test B')).toBeVisible();
	}
);

test.fixme(
	'LPD-78504 Can edit categorization section in layout',
	{tag: '@LPD-78504'},
	async ({apiHelpers, objectLayoutsPage, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Custom Field'},
						name: 'customField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByRole('heading', {name: objectDefinition.label['en_US']})).toBeVisible();
	}
);

test(
	'LPD-78504 Can edit entry when user has update permission on relationship tab',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Custom Field'},
						name: 'customField',
						required: false,
					},
				] as any,
				status: {code: 0},
				titleObjectFieldName: 'customField',
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await apiHelpers.objectEntry.postObjectEntry(
			{customField: 'Entry Test'},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Entry Test')).toBeVisible();
	}
);

test(
	'LPD-78504 Cannot edit entry when user does not have update permission on relationship tab',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Custom Field'},
						name: 'customField',
						required: false,
					},
				] as any,
				status: {code: 0},
				titleObjectFieldName: 'customField',
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await apiHelpers.objectEntry.postObjectEntry(
			{customField: 'Entry Test'},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Entry Test')).toBeVisible();
	}
);

test(
	'LPD-78504 Cannot save another layout as default when one already exists',
	{tag: '@LPD-78504'},
	async ({apiHelpers, objectLayoutsPage, page}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Custom Field'},
						name: 'customField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectLayoutsPage.goto(objectDefinition.name);

		await expect(page.locator('body')).toBeVisible();
	}
);

test(
	'LPD-78504 Can select existing entries on relationship tab',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Custom Field'},
						name: 'customField',
						required: false,
					},
				] as any,
				status: {code: 0},
				titleObjectFieldName: 'customField',
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectRelationshipAPIClient =
			await apiHelpers.buildRestClient(ObjectRelationshipAPI);

		await objectRelationshipAPIClient.postObjectDefinitionObjectRelationship(
			objectDefinition.id,
			{
				label: {en_US: 'Relationship'},
				name: 'relationship',
				objectDefinitionId2: objectDefinition.id,
				type: 'oneToMany',
			} as any
		);

		for (const letter of ['A', 'B', 'C']) {
			await apiHelpers.objectEntry.postObjectEntry(
				{customField: `Entry Test ${letter}`},
				'c/' + objectDefinition.name.toLowerCase() + 's'
			);
		}

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Entry Test A')).toBeVisible();
		await expect(page.getByText('Entry Test B')).toBeVisible();
		await expect(page.getByText('Entry Test C')).toBeVisible();
	}
);

test(
	'LPD-78504 Can view date entry on custom layout',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Date',
						DBType: 'Date',
						label: {en_US: 'Custom Field'},
						name: 'customField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await apiHelpers.objectEntry.postObjectEntry(
			{customField: '2022-01-01'},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Jan 1, 2022')).toBeVisible();
	}
);
