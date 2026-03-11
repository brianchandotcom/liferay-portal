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

// --- Auto-generated table display tests ---

test(
	'LPD-78504 BigDecimal entry is displayed on auto-generated table',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'PrecisionDecimal',
						DBType: 'BigDecimal',
						label: {en_US: 'BigDecimal'},
						name: 'bigDecimalField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await apiHelpers.objectEntry.postObjectEntry(
			{bigDecimalField: '123.123456'},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('123.123456')).toBeVisible();
	}
);

test(
	'LPD-78504 Boolean entry is displayed on auto-generated table',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Boolean',
						DBType: 'Boolean',
						label: {en_US: 'Boolean'},
						name: 'booleanField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await apiHelpers.objectEntry.postObjectEntry(
			{booleanField: true},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Yes')).toBeVisible();
	}
);

test(
	'LPD-78504 Can access custom object portlet with access permission',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Field'},
						name: 'textField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByRole('heading', {name: objectDefinition.label['en_US']})).toBeVisible();
	}
);

test.fixme(
	'LPD-78504 Can add BigDecimal entry on layout',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'PrecisionDecimal',
						DBType: 'BigDecimal',
						label: {en_US: 'BigDecimal'},
						name: 'bigDecimalField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(objectDefinition.label['en_US']);

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldBusinessType: 'PrecisionDecimal',
			objectFieldLabel: 'BigDecimal',
			objectFieldValue: '123.123456',
		});

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);
	}
);

test(
	'LPD-78504 Can add Boolean entry on layout',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Boolean',
						DBType: 'Boolean',
						label: {en_US: 'Boolean'},
						name: 'booleanField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(objectDefinition.label['en_US']);

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);
	}
);

test.fixme(
	'LPD-78504 Can add Date entry on layout',
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

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(objectDefinition.label['en_US']);

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldBusinessType: 'Date',
			objectFieldLabel: 'Date',
			objectFieldValue: '01/01/2001',
		});

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);
	}
);

test(
	'LPD-78504 Can add Double entry on layout',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Decimal',
						DBType: 'Double',
						label: {en_US: 'Double'},
						name: 'doubleField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(objectDefinition.label['en_US']);

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldBusinessType: 'Decimal',
			objectFieldLabel: 'Double',
			objectFieldValue: '1.23',
		});

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);
	}
);

test.fixme(
	'LPD-78504 Can add entry on object scoped by site',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Field'},
						name: 'textField',
						required: false,
					},
				] as any,
				scope: 'site',
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByRole('heading', {name: objectDefinition.label['en_US']})).toBeVisible();
	}
);

test(
	'LPD-78504 Can add entry without selecting a picklist value',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Field String'},
						name: 'fieldString',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(objectDefinition.label['en_US']);

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldBusinessType: 'Text',
			objectFieldLabel: 'Field String',
			objectFieldValue: 'String Entry',
		});

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);
	}
);

test(
	'LPD-78504 Can add Integer entry on layout',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Integer',
						DBType: 'Integer',
						label: {en_US: 'Integer'},
						name: 'integerField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(objectDefinition.label['en_US']);

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldBusinessType: 'Integer',
			objectFieldLabel: 'Integer',
			objectFieldValue: '123456789',
		});

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);
	}
);

test(
	'LPD-78504 Can add Long entry on layout',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'LongInteger',
						DBType: 'Long',
						label: {en_US: 'Long'},
						name: 'longField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(objectDefinition.label['en_US']);

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldBusinessType: 'LongInteger',
			objectFieldLabel: 'Long',
			objectFieldValue: '1234567891234567',
		});

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);
	}
);

test(
	'LPD-78504 Can add object entry with add permission',
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
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(objectDefinition.label['en_US']);

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldBusinessType: 'Text',
			objectFieldLabel: 'Custom Field',
			objectFieldValue: 'Test Entry',
		});

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);
	}
);

test.fixme(
	'LPD-78504 Can add Picklist entry on layout',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Field'},
						name: 'textField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByRole('heading', {name: objectDefinition.label['en_US']})).toBeVisible();
	}
);

test(
	'LPD-78504 Can add special characters on field named Name',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Name'},
						name: 'nameField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(objectDefinition.label['en_US']);

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldBusinessType: 'Text',
			objectFieldLabel: 'Name',
			objectFieldValue: '@~!& ^%$&_-',
		});

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);
	}
);

test(
	'LPD-78504 Can add String entry on layout',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'String'},
						name: 'stringField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(objectDefinition.label['en_US']);

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldBusinessType: 'Text',
			objectFieldLabel: 'String',
			objectFieldValue: 'Test text',
		});

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Test text')).toBeVisible();
	}
);

test(
	'LPD-78504 Can apply permission only to specific site when scoped by site',
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
				scope: 'site',
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByRole('heading', {name: objectDefinition.label['en_US']})).toBeVisible();
	}
);

test(
	'LPD-78504 Can cancel entry submission',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Field'},
						name: 'textField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(objectDefinition.label['en_US']);

		await page.getByRole('button', {name: 'Cancel'}).click();

		await expect(page.getByText('No Results Found')).toBeVisible();
	}
);

test(
	'LPD-78504 Can cancel entry update',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Field'},
						name: 'textField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await apiHelpers.objectEntry.postObjectEntry(
			{textField: 'Test Entry'},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Test Entry')).toBeVisible();
	}
);

test(
	'LPD-78504 Can change columns to be displayed on auto-generated table',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Text'},
						name: 'textField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await apiHelpers.objectEntry.postObjectEntry(
			{textField: 'Test text'},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Test text')).toBeVisible();
	}
);

test(
	'LPD-78504 Can delete an entry on auto-generated table',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Text'},
						name: 'textField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await apiHelpers.objectEntry.postObjectEntry(
			{textField: 'Test text'},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Test text')).toBeVisible();
	}
);

test(
	'LPD-78504 Can delete object entry with delete permission',
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
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await apiHelpers.objectEntry.postObjectEntry(
			{customField: 'Text test'},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Text test')).toBeVisible();
	}
);

test(
	'LPD-78504 Can edit BigDecimal entry on layout',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'PrecisionDecimal',
						DBType: 'BigDecimal',
						label: {en_US: 'Field'},
						name: 'bigDecimalField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await apiHelpers.objectEntry.postObjectEntry(
			{bigDecimalField: '123.654321'},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('123.654321')).toBeVisible();
	}
);

test(
	'LPD-78504 Can edit Boolean entry on layout',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Boolean',
						DBType: 'Boolean',
						label: {en_US: 'Field'},
						name: 'booleanField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await apiHelpers.objectEntry.postObjectEntry(
			{booleanField: false},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('No')).toBeVisible();
	}
);

test(
	'LPD-78504 Can edit Date entry on layout',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Date',
						DBType: 'Date',
						label: {en_US: 'Field'},
						name: 'dateField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await apiHelpers.objectEntry.postObjectEntry(
			{dateField: '2001-01-01'},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Jan 1, 2001')).toBeVisible();
	}
);

test(
	'LPD-78504 Can edit Double entry on layout',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Decimal',
						DBType: 'Double',
						label: {en_US: 'Field'},
						name: 'doubleField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await apiHelpers.objectEntry.postObjectEntry(
			{doubleField: 1.23},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('1.23')).toBeVisible();
	}
);

test(
	'LPD-78504 Can edit Integer entry on layout',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Integer',
						DBType: 'Integer',
						label: {en_US: 'Field'},
						name: 'integerField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await apiHelpers.objectEntry.postObjectEntry(
			{integerField: 321},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('321')).toBeVisible();
	}
);

test(
	'LPD-78504 Can edit Long entry on layout',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'LongInteger',
						DBType: 'Long',
						label: {en_US: 'Field'},
						name: 'longField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await apiHelpers.objectEntry.postObjectEntry(
			{longField: 987654321},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('987654321')).toBeVisible();
	}
);

test.fixme(
	'LPD-78504 Can edit Picklist entry on layout',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Field'},
						name: 'textField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByRole('heading', {name: objectDefinition.label['en_US']})).toBeVisible();
	}
);

test(
	'LPD-78504 Can edit String entry on layout',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Field'},
						name: 'textField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await apiHelpers.objectEntry.postObjectEntry(
			{textField: 'Text test'},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Text test')).toBeVisible();
	}
);

test(
	'LPD-78504 Cannot add object entry without add permission',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Field'},
						name: 'textField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByRole('heading', {name: objectDefinition.label['en_US']})).toBeVisible();
	}
);

test(
	'LPD-78504 Cannot delete object entry without delete permission',
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
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await apiHelpers.objectEntry.postObjectEntry(
			{customField: 'Text test'},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Text test')).toBeVisible();
	}
);

test(
	'LPD-78504 Cannot update object entry without update permission',
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
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await apiHelpers.objectEntry.postObjectEntry(
			{customField: 'Text test'},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Text test')).toBeVisible();
	}
);

test(
	'LPD-78504 Cannot view other users entry with only add permission',
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
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByRole('heading', {name: objectDefinition.label['en_US']})).toBeVisible();
	}
);

test(
	'LPD-78504 Cannot view other users entry without view permission',
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
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByRole('heading', {name: objectDefinition.label['en_US']})).toBeVisible();
	}
);

test(
	'LPD-78504 Can order auto-generated table by entry',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Text'},
						name: 'textField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		for (const number of ['1', '2']) {
			await apiHelpers.objectEntry.postObjectEntry(
				{textField: `Test text ${number}`},
				'c/' + objectDefinition.name.toLowerCase() + 's'
			);
		}

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Test text 1')).toBeVisible();
		await expect(page.getByText('Test text 2')).toBeVisible();
	}
);

test(
	'LPD-78504 Can search for an entry on auto-generated table',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Text'},
						name: 'textField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await apiHelpers.objectEntry.postObjectEntry(
			{textField: 'Test 1'},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await apiHelpers.objectEntry.postObjectEntry(
			{textField: 'Entry 2'},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Test 1')).toBeVisible();
		await expect(page.getByText('Entry 2')).toBeVisible();
	}
);

test(
	'LPD-78504 Can update object entry with update permission',
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
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await apiHelpers.objectEntry.postObjectEntry(
			{customField: 'Text test'},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Text test')).toBeVisible();
	}
);

test(
	'LPD-78504 Can view BigDecimal entry and label on layout',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'PrecisionDecimal',
						DBType: 'BigDecimal',
						label: {en_US: 'Field'},
						name: 'bigDecimalField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await apiHelpers.objectEntry.postObjectEntry(
			{bigDecimalField: '123.123456'},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('123.123456')).toBeVisible();
	}
);

test(
	'LPD-78504 Can view Boolean entry and label on layout',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Boolean',
						DBType: 'Boolean',
						label: {en_US: 'Field'},
						name: 'booleanField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await apiHelpers.objectEntry.postObjectEntry(
			{booleanField: true},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Yes')).toBeVisible();
	}
);

test(
	'LPD-78504 Can view Date entry and label on layout',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Date',
						DBType: 'Date',
						label: {en_US: 'Field'},
						name: 'dateField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await apiHelpers.objectEntry.postObjectEntry(
			{dateField: '2001-01-01'},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Jan 1, 2001')).toBeVisible();
	}
);

test(
	'LPD-78504 Can view Double entry and label on layout',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Decimal',
						DBType: 'Double',
						label: {en_US: 'Field'},
						name: 'doubleField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await apiHelpers.objectEntry.postObjectEntry(
			{doubleField: 1.54},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('1.54')).toBeVisible();
	}
);

test(
	'LPD-78504 Can view Integer entry and label on layout',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Integer',
						DBType: 'Integer',
						label: {en_US: 'Field'},
						name: 'integerField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await apiHelpers.objectEntry.postObjectEntry(
			{integerField: 12345},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('12345')).toBeVisible();
	}
);

test(
	'LPD-78504 Can view Long entry and label on layout',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'LongInteger',
						DBType: 'Long',
						label: {en_US: 'Field'},
						name: 'longField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await apiHelpers.objectEntry.postObjectEntry(
			{longField: 12345678},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('12345678')).toBeVisible();
	}
);

test(
	'LPD-78504 Can view other users entry with view permission',
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
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await apiHelpers.objectEntry.postObjectEntry(
			{customField: 'Test Entry'},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Test Entry')).toBeVisible();
	}
);

test.fixme(
	'LPD-78504 Can view Picklist entry and label on layout',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Field'},
						name: 'textField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByRole('heading', {name: objectDefinition.label['en_US']})).toBeVisible();
	}
);

test.fixme(
	'LPD-78504 Can view String entry and label on layout',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Field'},
						name: 'textField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await apiHelpers.objectEntry.postObjectEntry(
			{textField: 'Text Test'},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Text Test')).toBeVisible();
	}
);

test(
	'LPD-78504 Can view user name on author column',
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
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await apiHelpers.objectEntry.postObjectEntry(
			{customField: 'Text test'},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Text test')).toBeVisible();
	}
);

test(
	'LPD-78504 Columns ID, Fields and Status are displayed',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Field'},
						name: 'textField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await apiHelpers.objectEntry.postObjectEntry(
			{textField: 'String'},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('ID').first()).toBeVisible();
		await expect(page.getByText('Status').first()).toBeVisible();
	}
);

test(
	'LPD-78504 Date entry is displayed on auto-generated table',
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

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await apiHelpers.objectEntry.postObjectEntry(
			{dateField: '2021-09-23'},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Sep 23, 2021')).toBeVisible();
	}
);

test(
	'LPD-78504 Double entry is displayed on auto-generated table',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Decimal',
						DBType: 'Double',
						label: {en_US: 'Double'},
						name: 'doubleField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await apiHelpers.objectEntry.postObjectEntry(
			{doubleField: 1.54},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('1.54')).toBeVisible();
	}
);

test(
	'LPD-78504 Duplicated entry is not submitted when refreshing page',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Field'},
						name: 'textField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(objectDefinition.label['en_US']);

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldBusinessType: 'Text',
			objectFieldLabel: 'Field',
			objectFieldValue: 'Test Entry',
		});

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);

		await page.reload();

		await viewObjectEntriesPage.goto(objectDefinition.className);

		const entries = page.locator('table tbody tr');

		await expect(entries).toHaveCount(1);
	}
);

test(
	'LPD-78504 Empty state is displayed when searching for nonexistent value',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Field'},
						name: 'textField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await apiHelpers.objectEntry.postObjectEntry(
			{textField: 'Test text'},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Test text')).toBeVisible();
	}
);

test(
	'LPD-78504 Empty state is displayed when no entry exists',
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
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('No Results Found')).toBeVisible();
	}
);

test(
	'LPD-78504 Integer entry is displayed on auto-generated table',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Integer',
						DBType: 'Integer',
						label: {en_US: 'Integer'},
						name: 'integerField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await apiHelpers.objectEntry.postObjectEntry(
			{integerField: 123456789},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('123456789')).toBeVisible();
	}
);

test(
	'LPD-78504 Long entry is displayed on auto-generated table',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'LongInteger',
						DBType: 'Long',
						label: {en_US: 'Long'},
						name: 'longField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		await apiHelpers.objectEntry.postObjectEntry(
			{longField: 1234567891234567},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('1234567891234567')).toBeVisible();
	}
);

test(
	'LPD-78504 String entry is displayed on auto-generated table',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Text',
						DBType: 'String',
						label: {en_US: 'Text'},
						name: 'textField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

		const longText = 'test '.repeat(56).trim();

		await apiHelpers.objectEntry.postObjectEntry(
			{textField: longText},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText(longText.substring(0, 20))).toBeVisible();
	}
);
