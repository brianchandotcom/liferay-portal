/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectDefinitionAPI} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import getRandomString from '../../../utils/getRandomString';
import {waitForAlert} from '../../../utils/waitForAlert';
import {generateObjectFields} from './utils/generateObjectFields';

const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	objectPagesTest
);

test.fixme(
	'LPD-78504 Can display empty date value on auto-generated layout',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Date',
						DBType: 'Date',
						label: {en_US: 'Date'},
						name: 'dateField',
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

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.locator('table tbody tr')).toHaveCount(1);
	}
);

test.fixme(
	'LPD-78504 Can format text in RichText field on auto-generated layout',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'RichText',
						DBType: 'Clob',
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

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await expect(page.locator('.cke_editable, [contenteditable]').first()).toBeVisible();
	}
);

test.fixme(
	'LPD-78504 Cannot add more characters than the limit set on text field',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Custom Text'},
						name: 'customText',
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

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldBusinessType: 'Text',
			objectFieldLabel: 'Custom Text',
			objectFieldValue: 'Entry',
		});

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Entry')).toBeVisible();
	}
);

test.fixme(
	'LPD-78504 Can replace file in attachment field on auto-generated layout',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Attachment',
						DBType: 'Long',
						label: {en_US: 'Custom Attachment'},
						name: 'customAttachment',
						objectFieldSettings: [
							{name: 'acceptedFileExtensions', value: 'jpeg, jpg, pdf, png'},
							{name: 'fileSource', value: 'userComputer'},
							{name: 'maximumFileSize', value: 100},
						],
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

		await expect(
			page.getByRole('button', {name: 'Add'}).or(page.locator('[data-testid="addButton"]'))
		).toBeVisible();
	}
);

test.fixme(
	'LPD-78504 Cannot upload invalid extension file in attachment field',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Attachment',
						DBType: 'Long',
						label: {en_US: 'Custom Attachment'},
						name: 'customAttachment',
						objectFieldSettings: [
							{name: 'acceptedFileExtensions', value: 'jpeg, jpg, pdf, png'},
							{name: 'fileSource', value: 'userComputer'},
							{name: 'maximumFileSize', value: 100},
						],
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
	'LPD-78504 Cannot upload file exceeding maximum file size in attachment field',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Attachment',
						DBType: 'Long',
						label: {en_US: 'Custom Attachment'},
						name: 'customAttachment',
						objectFieldSettings: [
							{name: 'acceptedFileExtensions', value: 'jpeg, jpg, pdf, png'},
							{name: 'fileSource', value: 'userComputer'},
							{name: 'maximumFileSize', value: 1},
						],
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
	'LPD-78504 Can view LongText (Clob) entry on auto-generated layout',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const clobValue =
			'By building a vibrant business, making technology useful, and investing in communities, we make it possible for people to reach their full potential to serve others.';

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'LongText',
						DBType: 'Clob',
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
			{customField: clobValue},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText(clobValue.substring(0, 50))).toBeVisible();
	}
);

test.fixme(
	'LPD-78504 Can view file name and extension in attachment field',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Attachment',
						DBType: 'Long',
						label: {en_US: 'Custom Attachment'},
						name: 'customAttachment',
						objectFieldSettings: [
							{name: 'acceptedFileExtensions', value: 'jpeg, jpg, pdf, png'},
							{name: 'fileSource', value: 'userComputer'},
							{name: 'maximumFileSize', value: 100},
						],
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
	'LPD-78504 Can view file when clicking on file name in attachment field',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Attachment',
						DBType: 'Long',
						label: {en_US: 'Custom Attachment'},
						name: 'customAttachment',
						objectFieldSettings: [
							{name: 'acceptedFileExtensions', value: 'jpeg, jpg, pdf, png'},
							{name: 'fileSource', value: 'userComputer'},
							{name: 'maximumFileSize', value: 100},
						],
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
	'LPD-78504 Can view object entry title of Clob type on relationship field',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'LongText',
						DBType: 'Clob',
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
			{customField: 'Entry A'},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Entry A')).toBeVisible();
	}
);

test(
	'LPD-78504 Character count is updated dynamically when typing in text field',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Custom Text'},
						name: 'customText',
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

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldBusinessType: 'Text',
			objectFieldLabel: 'Custom Text',
			objectFieldValue: 'Entry Test',
		});

		await expect(page.locator('body')).toBeVisible();
	}
);
