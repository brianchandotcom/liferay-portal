/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import createUserWithPermissions from '../../../utils/createUserWithPermissions';
import {performUserSwitch} from '../../../utils/performLogin';
import {waitForAlert} from '../../../utils/waitForAlert';

const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	isolatedSiteTest,
	loginTest(),
	objectPagesTest
);

test('BigDecimal entry is displayed on auto-generated table',
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

test('Boolean entry is displayed on auto-generated table',
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

test('Can access custom object portlet with access permission',
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

		const company =
			await apiHelpers.jsonWebServicesCompany.getCompanyByWebId(
				'liferay.com'
			);

		const user = await createUserWithPermissions({
			apiHelpers,
			rolePermissions: [
				{
					actionIds: ['VIEW_CONTROL_PANEL'],
					primaryKey: company.companyId,
					resourceName: '90',
					scope: 1,
				},
				{
					actionIds: [
						'ACCESS_IN_CONTROL_PANEL',
						'CONFIGURATION',
						'PERMISSIONS',
						'PREFERENCES',
						'VIEW',
					] as any[],
					primaryKey: company.companyId,
					resourceName: `com_liferay_object_web_internal_object_definitions_portlet_ObjectDefinitionsPortlet_${objectDefinition.className.split('#')[1]}`,
					scope: 1,
				},
			],
		});

		await performUserSwitch(page, user.alternateName);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(
			page.getByRole('heading', {name: objectDefinition.label['en_US']})
		).toBeVisible();
	}
);

test.fixme('Can add BigDecimal entry on layout',
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

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldBusinessType: 'PrecisionDecimal',
			objectFieldLabel: 'BigDecimal',
			objectFieldValue: '123.123456',
		});

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('123.123456')).toBeVisible();
	}
);

test('Can add Boolean entry on layout',
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

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await page.getByLabel('Boolean', {exact: true}).check();

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Yes')).toBeVisible();
	}
);

test.fixme('Can add Date entry on layout',
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

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldBusinessType: 'Date',
			objectFieldLabel: 'Date',
			objectFieldValue: '01/01/2001',
		});

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Jan 1, 2001')).toBeVisible();
	}
);

test('Can add Double entry on layout',
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

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldBusinessType: 'Decimal',
			objectFieldLabel: 'Double',
			objectFieldValue: '1.23',
		});

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('1.23')).toBeVisible();
	}
);

test.fixme('Can add entry on object scoped by site',
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

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldBusinessType: 'Text',
			objectFieldLabel: 'Field',
			objectFieldValue: 'Test Entry',
		});

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Test Entry')).toBeVisible();
	}
);

test('Can add entry without selecting a picklist value',
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

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldBusinessType: 'Text',
			objectFieldLabel: 'Field String',
			objectFieldValue: 'String Entry',
		});

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('String Entry')).toBeVisible();
	}
);

test('Can add Integer entry on layout',
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

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldBusinessType: 'Integer',
			objectFieldLabel: 'Integer',
			objectFieldValue: '123456789',
		});

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('123456789')).toBeVisible();
	}
);

test('Can add Long entry on layout',
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

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldBusinessType: 'LongInteger',
			objectFieldLabel: 'Long',
			objectFieldValue: '1234567891234567',
		});

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('1234567891234567')).toBeVisible();
	}
);

test('Can add object entry with add permission',
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

		const company =
			await apiHelpers.jsonWebServicesCompany.getCompanyByWebId(
				'liferay.com'
			);

		const user = await createUserWithPermissions({
			apiHelpers,
			rolePermissions: [
				{
					actionIds: ['VIEW_CONTROL_PANEL'],
					primaryKey: company.companyId,
					resourceName: '90',
					scope: 1,
				},
				{
					actionIds: [
						'ACCESS_IN_CONTROL_PANEL',
						'CONFIGURATION',
						'PERMISSIONS',
						'PREFERENCES',
						'VIEW',
					] as any[],
					primaryKey: company.companyId,
					resourceName: `com_liferay_object_web_internal_object_definitions_portlet_ObjectDefinitionsPortlet_${objectDefinition.className.split('#')[1]}`,
					scope: 1,
				},
				{
					actionIds: ['ADD_OBJECT_ENTRY'] as any[],
					primaryKey: company.companyId,
					resourceName: `com.liferay.object#${objectDefinition.id}`,
					scope: 1,
				},
			],
		});

		await performUserSwitch(page, user.alternateName);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldBusinessType: 'Text',
			objectFieldLabel: 'Custom Field',
			objectFieldValue: 'Test Entry',
		});

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Test Entry')).toBeVisible();
	}
);

test.fixme('Can add Picklist entry on layout',
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		// Requires picklist setup (addPicklistViaAPI, addPicklistItemViaAPI)
		// and then adding entry with the picklist field selected
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

		await expect(
			page.getByRole('heading', {name: objectDefinition.label['en_US']})
		).toBeVisible();
	}
);

test('Can add special characters on field named Name',
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

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldBusinessType: 'Text',
			objectFieldLabel: 'Name',
			objectFieldValue: '@~!& ^%$&_-',
		});

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('@~!& ^%$&_-')).toBeVisible();
	}
);

test('Can add String entry on layout',
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

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

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

test.fixme('Can apply permission only to specific site when scoped by site',
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		// Requires creating 2 sites and applying site-scoped permissions
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

		await expect(
			page.getByRole('heading', {name: objectDefinition.label['en_US']})
		).toBeVisible();
	}
);

test('Can cancel entry submission',
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

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldBusinessType: 'Text',
			objectFieldLabel: 'Field',
			objectFieldValue: 'Test Entry',
		});

		await page.getByRole('button', {name: 'Cancel'}).click();

		await expect(page.getByText('No Results Found')).toBeVisible();
	}
);

test('Can cancel entry update',
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

		await viewObjectEntriesPage.frontendDatasetItems.first().click();

		await page.getByLabel('Field', {exact: true}).clear();

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldLabel: 'Field',
			objectFieldValue: 'Test Entry 2',
		});

		await viewObjectEntriesPage.cancelObjectEntryButton.click();

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Test Entry 2')).not.toBeVisible();

		await expect(page.getByText('Test Entry')).toBeVisible();
	}
);

test('Can change columns to be displayed on auto-generated table',
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

		await expect(
			page.getByRole('columnheader').getByText('ID')
		).toBeVisible();

		await page.getByLabel('Manage Columns Visibility').click();

		await page.getByRole('menuitem', {name: 'ID'}).click();

		await page.keyboard.press('Escape');

		await expect(
			page.getByRole('columnheader').getByText('ID')
		).not.toBeVisible();
	}
);

test('Can delete an entry on auto-generated table',
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

		await viewObjectEntriesPage.frontendDatasetActions.click();

		await viewObjectEntriesPage.frontendDatasetDeleteAction.click();

		await page.getByRole('button', {name: 'Delete'}).click();

		await expect(page.getByText('No Results Found')).toBeVisible();
	}
);

test('Can delete object entry with delete permission',
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

		const company =
			await apiHelpers.jsonWebServicesCompany.getCompanyByWebId(
				'liferay.com'
			);

		const user = await createUserWithPermissions({
			apiHelpers,
			rolePermissions: [
				{
					actionIds: ['ACCESS_IN_CONTROL_PANEL'] as any[],
					primaryKey: company.companyId,
					resourceName: `com_liferay_object_web_internal_object_definitions_portlet_ObjectDefinitionsPortlet_${objectDefinition.className.split('#')[1]}`,
					scope: 1,
				},
				{
					actionIds: ['VIEW', 'DELETE'] as any[],
					primaryKey: company.companyId,
					resourceName: objectDefinition.className,
					scope: 1,
				},
			],
		});

		await performUserSwitch(page, user.alternateName);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.frontendDatasetActions.click();

		await viewObjectEntriesPage.frontendDatasetDeleteAction.click();

		await page.getByRole('button', {name: 'Delete'}).click();

		await expect(page.getByText('No Results Found')).toBeVisible();
	}
);

test('Can edit BigDecimal entry on layout',
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

		await viewObjectEntriesPage.frontendDatasetItems.first().click();

		await page.getByLabel('Field', {exact: true}).clear();

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldLabel: 'Field',
			objectFieldValue: '1.12345678',
		});

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('1.12345678')).toBeVisible();

		await expect(page.getByText('123.654321')).not.toBeVisible();
	}
);

test('Can edit Boolean entry on layout',
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

		await viewObjectEntriesPage.frontendDatasetItems.first().click();

		await page.getByLabel('Field', {exact: true}).check();

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Yes')).toBeVisible();

		await expect(page.getByText('No')).not.toBeVisible();
	}
);

test('Can edit Date entry on layout',
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

		await viewObjectEntriesPage.frontendDatasetItems.first().click();

		await page.getByLabel('Field', {exact: true}).clear();

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldLabel: 'Field',
			objectFieldValue: '02/02/2002',
		});

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Feb 2, 2002')).toBeVisible();
	}
);

test('Can edit Double entry on layout',
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

		await viewObjectEntriesPage.frontendDatasetItems.first().click();

		await page.getByLabel('Field', {exact: true}).clear();

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldLabel: 'Field',
			objectFieldValue: '4.56',
		});

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('4.56')).toBeVisible();

		await expect(page.getByText('1.23')).not.toBeVisible();
	}
);

test('Can edit Integer entry on layout',
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

		await viewObjectEntriesPage.frontendDatasetItems.first().click();

		await page.getByLabel('Field', {exact: true}).clear();

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldLabel: 'Field',
			objectFieldValue: '123456',
		});

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('123456')).toBeVisible();

		await expect(page.getByText('321')).not.toBeVisible();
	}
);

test('Can edit Long entry on layout',
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

		await viewObjectEntriesPage.frontendDatasetItems.first().click();

		await page.getByLabel('Field', {exact: true}).clear();

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldLabel: 'Field',
			objectFieldValue: '1234567891234567',
		});

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('1234567891234567')).toBeVisible();

		await expect(page.getByText('987654321')).not.toBeVisible();
	}
);

test.fixme('Can edit Picklist entry on layout',
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		// Requires picklist setup and picklist field creation via UI
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

		await expect(
			page.getByRole('heading', {name: objectDefinition.label['en_US']})
		).toBeVisible();
	}
);

test('Can edit String entry on layout',
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

		await viewObjectEntriesPage.frontendDatasetItems.first().click();

		await page.getByLabel('Field', {exact: true}).clear();

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldLabel: 'Field',
			objectFieldValue: 'Update test',
		});

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Update test')).toBeVisible();

		await expect(page.getByText('Text test')).not.toBeVisible();
	}
);

test('Cannot add object entry without add permission',
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

		const company =
			await apiHelpers.jsonWebServicesCompany.getCompanyByWebId(
				'liferay.com'
			);

		const user = await createUserWithPermissions({
			apiHelpers,
			rolePermissions: [
				{
					actionIds: ['VIEW_CONTROL_PANEL'],
					primaryKey: company.companyId,
					resourceName: '90',
					scope: 1,
				},
				{
					actionIds: [
						'ACCESS_IN_CONTROL_PANEL',
						'CONFIGURATION',
						'PERMISSIONS',
						'PREFERENCES',
						'VIEW',
					] as any[],
					primaryKey: company.companyId,
					resourceName: `com_liferay_object_web_internal_object_definitions_portlet_ObjectDefinitionsPortlet_${objectDefinition.className.split('#')[1]}`,
					scope: 1,
				},
			],
		});

		await performUserSwitch(page, user.alternateName);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(
			page.getByLabel(`Add ${objectDefinition.label['en_US']}`)
		).not.toBeVisible();
	}
);

test('Cannot delete object entry without delete permission',
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

		const company =
			await apiHelpers.jsonWebServicesCompany.getCompanyByWebId(
				'liferay.com'
			);

		const user = await createUserWithPermissions({
			apiHelpers,
			rolePermissions: [
				{
					actionIds: ['ACCESS_IN_CONTROL_PANEL'] as any[],
					primaryKey: company.companyId,
					resourceName: `com_liferay_object_web_internal_object_definitions_portlet_ObjectDefinitionsPortlet_${objectDefinition.className.split('#')[1]}`,
					scope: 1,
				},
				{
					actionIds: ['VIEW'] as any[],
					primaryKey: company.companyId,
					resourceName: objectDefinition.className,
					scope: 1,
				},
			],
		});

		await performUserSwitch(page, user.alternateName);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Text test')).toBeVisible();

		await expect(
			viewObjectEntriesPage.frontendDatasetActions
		).not.toBeVisible();
	}
);

test('Cannot update object entry without update permission',
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

		const company =
			await apiHelpers.jsonWebServicesCompany.getCompanyByWebId(
				'liferay.com'
			);

		const user = await createUserWithPermissions({
			apiHelpers,
			rolePermissions: [
				{
					actionIds: ['VIEW_CONTROL_PANEL'],
					primaryKey: company.companyId,
					resourceName: '90',
					scope: 1,
				},
				{
					actionIds: ['ACCESS_IN_CONTROL_PANEL'] as any[],
					primaryKey: company.companyId,
					resourceName: `com_liferay_object_web_internal_object_definitions_portlet_ObjectDefinitionsPortlet_${objectDefinition.className.split('#')[1]}`,
					scope: 1,
				},
				{
					actionIds: ['VIEW'] as any[],
					primaryKey: company.companyId,
					resourceName: objectDefinition.className,
					scope: 1,
				},
			],
		});

		await performUserSwitch(page, user.alternateName);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.frontendDatasetItems.first().click();

		await expect(
			page.getByLabel('Custom Field', {exact: true})
		).toBeDisabled();
	}
);

test('Cannot view other users entry with only add permission',
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

		const company =
			await apiHelpers.jsonWebServicesCompany.getCompanyByWebId(
				'liferay.com'
			);

		const user = await createUserWithPermissions({
			apiHelpers,
			rolePermissions: [
				{
					actionIds: ['ACCESS_IN_CONTROL_PANEL'] as any[],
					primaryKey: company.companyId,
					resourceName: `com_liferay_object_web_internal_object_definitions_portlet_ObjectDefinitionsPortlet_${objectDefinition.className.split('#')[1]}`,
					scope: 1,
				},
				{
					actionIds: ['ADD_OBJECT_ENTRY'] as any[],
					primaryKey: company.companyId,
					resourceName: `com.liferay.object#${objectDefinition.id}`,
					scope: 1,
				},
			],
		});

		await performUserSwitch(page, user.alternateName);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Text test')).not.toBeVisible();
	}
);

test('Cannot view other users entry without view permission',
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

		const company =
			await apiHelpers.jsonWebServicesCompany.getCompanyByWebId(
				'liferay.com'
			);

		const user = await createUserWithPermissions({
			apiHelpers,
			rolePermissions: [
				{
					actionIds: ['ACCESS_IN_CONTROL_PANEL'] as any[],
					primaryKey: company.companyId,
					resourceName: `com_liferay_object_web_internal_object_definitions_portlet_ObjectDefinitionsPortlet_${objectDefinition.className.split('#')[1]}`,
					scope: 1,
				},
			],
		});

		await performUserSwitch(page, user.alternateName);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Test Entry')).not.toBeVisible();
	}
);

test('Can order auto-generated table by entry',
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						DBType: 'String',
						businessType: 'Text',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
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

		const textHeader = page.locator('thead th').filter({hasText: 'Text'});

		await textHeader.click();

		const rows = page.locator('table tbody tr');

		await expect(rows.first()).toContainText('Test text 1');
		await expect(rows.last()).toContainText('Test text 2');

		await textHeader.click();

		await expect(rows.first()).toContainText('Test text 2');
		await expect(rows.last()).toContainText('Test text 1');
	}
);

test('Can search for an entry on auto-generated table',
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						DBType: 'String',
						businessType: 'Text',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
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

		await page.getByPlaceholder('Search').fill('Entry 2');

		await page.getByPlaceholder('Search').press('Enter');

		await expect(page.locator('table').getByText('Entry 2')).toBeVisible();

		await expect(page.locator('table').getByText('Test 1')).not.toBeVisible();
	}
);

test('Can update object entry with update permission',
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

		const company =
			await apiHelpers.jsonWebServicesCompany.getCompanyByWebId(
				'liferay.com'
			);

		const user = await createUserWithPermissions({
			apiHelpers,
			rolePermissions: [
				{
					actionIds: ['ACCESS_IN_CONTROL_PANEL'] as any[],
					primaryKey: company.companyId,
					resourceName: `com_liferay_object_web_internal_object_definitions_portlet_ObjectDefinitionsPortlet_${objectDefinition.className.split('#')[1]}`,
					scope: 1,
				},
				{
					actionIds: ['VIEW', 'UPDATE'] as any[],
					primaryKey: company.companyId,
					resourceName: objectDefinition.className,
					scope: 1,
				},
			],
		});

		await performUserSwitch(page, user.alternateName);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.frontendDatasetItems.first().click();

		await page.getByLabel('Custom Field', {exact: true}).clear();

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldLabel: 'Custom Field',
			objectFieldValue: 'Test 2',
		});

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Text test')).not.toBeVisible();

		await expect(page.getByText('Test 2')).toBeVisible();
	}
);

test('Can view BigDecimal entry and label on layout',
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

		await viewObjectEntriesPage.frontendDatasetItems.first().click();

		await expect(page.getByLabel('Field', {exact: true})).toBeVisible();

		await expect(page.getByLabel('Field', {exact: true})).toHaveValue(
			'123.123456'
		);
	}
);

test('Can view Boolean entry and label on layout',
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

		await viewObjectEntriesPage.frontendDatasetItems.first().click();

		await expect(page.getByLabel('Field', {exact: true})).toBeVisible();

		await expect(page.getByLabel('Field', {exact: true})).toBeChecked();
	}
);

test('Can view Date entry and label on layout',
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

		await viewObjectEntriesPage.frontendDatasetItems.first().click();

		await expect(page.getByLabel('Field', {exact: true})).toBeVisible();

		await expect(page.getByLabel('Field', {exact: true})).toHaveValue(
			'01/01/2001'
		);
	}
);

test('Can view Double entry and label on layout',
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

		await viewObjectEntriesPage.frontendDatasetItems.first().click();

		await expect(page.getByLabel('Field', {exact: true})).toBeVisible();

		await expect(page.getByLabel('Field', {exact: true})).toHaveValue(
			'1.54'
		);
	}
);

test('Can view Integer entry and label on layout',
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

		await viewObjectEntriesPage.frontendDatasetItems.first().click();

		await expect(page.getByLabel('Field', {exact: true})).toBeVisible();

		await expect(page.getByLabel('Field', {exact: true})).toHaveValue(
			'12345'
		);
	}
);

test('Can view Long entry and label on layout',
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

		await viewObjectEntriesPage.frontendDatasetItems.first().click();

		await expect(page.getByLabel('Field', {exact: true})).toBeVisible();

		await expect(page.getByLabel('Field', {exact: true})).toHaveValue(
			'12345678'
		);
	}
);

test('Can view other users entry with view permission',
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

		const company =
			await apiHelpers.jsonWebServicesCompany.getCompanyByWebId(
				'liferay.com'
			);

		const user = await createUserWithPermissions({
			apiHelpers,
			rolePermissions: [
				{
					actionIds: ['ACCESS_IN_CONTROL_PANEL'] as any[],
					primaryKey: company.companyId,
					resourceName: `com_liferay_object_web_internal_object_definitions_portlet_ObjectDefinitionsPortlet_${objectDefinition.className.split('#')[1]}`,
					scope: 1,
				},
				{
					actionIds: ['VIEW'] as any[],
					primaryKey: company.companyId,
					resourceName: objectDefinition.className,
					scope: 1,
				},
			],
		});

		await performUserSwitch(page, user.alternateName);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Test Entry')).toBeVisible();
	}
);

test.fixme('Can view Picklist entry and label on layout',
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		// Requires picklist setup and picklist field creation via UI
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

		await expect(
			page.getByRole('heading', {name: objectDefinition.label['en_US']})
		).toBeVisible();
	}
);

test('Can view String entry and label on layout',
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

		await viewObjectEntriesPage.frontendDatasetItems.first().click();

		await expect(page.getByLabel('Field', {exact: true})).toBeVisible();

		await expect(page.getByLabel('Field', {exact: true})).toHaveValue(
			'Text Test'
		);
	}
);

test('Can view user name on author column',
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

		await expect(page.getByRole('cell', {name: 'Test Test'})).toBeVisible();
	}
);

test('Columns ID, Fields and Status are displayed',
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

		await expect(
			page.getByRole('columnheader').getByText('ID')
		).toBeVisible();

		await expect(
			page.getByRole('columnheader').getByText('Field')
		).toBeVisible();

		await expect(
			page.getByRole('columnheader').getByText('Status')
		).toBeVisible();
	}
);

test('Date entry is displayed on auto-generated table',
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

test('Double entry is displayed on auto-generated table',
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

test('Duplicated entry is not submitted when refreshing page',
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

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

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

test('Empty state is displayed when searching for nonexistent value',
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						DBType: 'String',
						businessType: 'Text',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
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

		await page.getByPlaceholder('Search').fill('Lorem ipsum');

		await page.getByPlaceholder('Search').press('Enter');

		await expect(
			page.getByText('No Results Found')
		).toBeVisible();
	}
);

test('Empty state is displayed when no entry exists',
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

test('Integer entry is displayed on auto-generated table',
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

test('Long entry is displayed on auto-generated table',
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

test('String entry is displayed on auto-generated table',
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
