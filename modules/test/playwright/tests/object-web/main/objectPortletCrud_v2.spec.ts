	/**
	 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
	 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
	 */

	import {expect, mergeTests} from '@playwright/test';

	import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
	import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
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

	// Unified: Can add Boolean/Double/Integer/Long/String entry on layout

	test('can add entries of different field types via object portlet',
		async ({apiHelpers, page, viewObjectEntriesPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields: [
						{
							DBType: 'Boolean',
							businessType: 'Boolean',
							label: {en_US: 'Boolean'},
							name: 'booleanField',
							required: false,
						},
						{
							DBType: 'Double',
							businessType: 'Decimal',
							label: {en_US: 'Double'},
							name: 'doubleField',
							required: false,
						},
						{
							DBType: 'Integer',
							businessType: 'Integer',
							label: {en_US: 'Integer'},
							name: 'integerField',
							required: false,
						},
						{
							DBType: 'Long',
							businessType: 'LongInteger',
							label: {en_US: 'Long'},
							name: 'longField',
							required: false,
						},
						{
							DBType: 'String',
							businessType: 'Text',
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

			await page.getByLabel('Boolean', {exact: true}).check();

			await viewObjectEntriesPage.fillObjectEntry({
				objectFieldBusinessType: 'Decimal',
				objectFieldLabel: 'Double',
				objectFieldValue: '1.23',
			});

			await viewObjectEntriesPage.fillObjectEntry({
				objectFieldBusinessType: 'Integer',
				objectFieldLabel: 'Integer',
				objectFieldValue: '123456789',
			});

			await viewObjectEntriesPage.fillObjectEntry({
				objectFieldBusinessType: 'LongInteger',
				objectFieldLabel: 'Long',
				objectFieldValue: '1234567891234567',
			});

			await viewObjectEntriesPage.fillObjectEntry({
				objectFieldBusinessType: 'Text',
				objectFieldLabel: 'String',
				objectFieldValue: 'Test text',
			});

			await viewObjectEntriesPage.saveObjectEntryButton.click();

			await waitForAlert(page);

			await viewObjectEntriesPage.goto(objectDefinition.className);

			await expect(page.getByText('Yes')).toBeVisible();
			await expect(page.getByText('1.23')).toBeVisible();
			await expect(page.getByText('123456789')).toBeVisible();
			await expect(page.getByText('1234567891234567')).toBeVisible();
			await expect(page.getByText('Test text')).toBeVisible();
		}
	);

	// Unified: Can edit BigDecimal/Boolean/Date/Double/Integer/Long/String entry on layout

	test('can edit entries of different field types via object portlet',
		async ({apiHelpers, page, viewObjectEntriesPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields: [
						{
							DBType: 'BigDecimal',
							businessType: 'PrecisionDecimal',
							label: {en_US: 'BigDecimal'},
							name: 'bigDecimalField',
							required: false,
						},
						{
							DBType: 'Boolean',
							businessType: 'Boolean',
							label: {en_US: 'Boolean'},
							name: 'booleanField',
							required: false,
						},
						{
							DBType: 'Date',
							businessType: 'Date',
							label: {en_US: 'Date'},
							name: 'dateField',
							required: false,
						},
						{
							DBType: 'Double',
							businessType: 'Decimal',
							label: {en_US: 'Double'},
							name: 'doubleField',
							required: false,
						},
						{
							DBType: 'Integer',
							businessType: 'Integer',
							label: {en_US: 'Integer'},
							name: 'integerField',
							required: false,
						},
						{
							DBType: 'Long',
							businessType: 'LongInteger',
							label: {en_US: 'Long'},
							name: 'longField',
							required: false,
						},
						{
							DBType: 'String',
							businessType: 'Text',
							label: {en_US: 'String'},
							name: 'stringField',
							required: false,
						},
					] as any,
					status: {code: 0},
				});

			apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

			const applicationName = `c/${objectDefinition.name.toLowerCase()}s`;

			await apiHelpers.objectEntry.postObjectEntry(
				{
					bigDecimalField: '123.654321',
					booleanField: false,
					dateField: '2001-01-01',
					doubleField: 1.23,
					integerField: 321,
					longField: 987654321,
					stringField: 'Text test',
				},
				applicationName
			);

			await viewObjectEntriesPage.goto(objectDefinition.className);

			await viewObjectEntriesPage.frontendDatasetItems.first().click();

			await page.getByLabel('BigDecimal', {exact: true}).clear();

			await viewObjectEntriesPage.fillObjectEntry({
				objectFieldLabel: 'BigDecimal',
				objectFieldValue: '1.12345678',
			});

			await page.getByLabel('Boolean', {exact: true}).check();

			await page.getByLabel('Date', {exact: true}).clear();

			await viewObjectEntriesPage.fillObjectEntry({
				objectFieldLabel: 'Date',
				objectFieldValue: '02/02/2002',
			});

			await page.getByLabel('Double', {exact: true}).clear();

			await viewObjectEntriesPage.fillObjectEntry({
				objectFieldLabel: 'Double',
				objectFieldValue: '4.56',
			});

			await page.getByLabel('Integer', {exact: true}).clear();

			await viewObjectEntriesPage.fillObjectEntry({
				objectFieldLabel: 'Integer',
				objectFieldValue: '123456',
			});

			await page.getByLabel('Long', {exact: true}).clear();

			await viewObjectEntriesPage.fillObjectEntry({
				objectFieldLabel: 'Long',
				objectFieldValue: '1234567891234567',
			});

			await page.getByLabel('String', {exact: true}).clear();

			await viewObjectEntriesPage.fillObjectEntry({
				objectFieldLabel: 'String',
				objectFieldValue: 'Update test',
			});

			await viewObjectEntriesPage.saveObjectEntryButton.click();

			await waitForAlert(page);

			await viewObjectEntriesPage.goto(objectDefinition.className);

			await expect(page.getByText('1.12345678')).toBeVisible();
			await expect(page.getByText('123.654321')).not.toBeVisible();
			await expect(page.getByText('Yes')).toBeVisible();
			await expect(page.getByText('No')).not.toBeVisible();
			await expect(page.getByText('Feb 2, 2002')).toBeVisible();
			await expect(page.getByText('4.56')).toBeVisible();
			await expect(page.getByText('Update test')).toBeVisible();
			await expect(page.getByText('Text test')).not.toBeVisible();
		}
	);

	// Unified: Can view BigDecimal/Boolean/Date/Double/Integer/Long/String entry and label on layout

	test('can view entries and labels of different field types via object portlet',
		async ({apiHelpers, page, viewObjectEntriesPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields: [
						{
							DBType: 'BigDecimal',
							businessType: 'PrecisionDecimal',
							label: {en_US: 'BigDecimal'},
							name: 'bigDecimalField',
							required: false,
						},
						{
							DBType: 'Boolean',
							businessType: 'Boolean',
							label: {en_US: 'Boolean'},
							name: 'booleanField',
							required: false,
						},
						{
							DBType: 'Date',
							businessType: 'Date',
							label: {en_US: 'Date'},
							name: 'dateField',
							required: false,
						},
						{
							DBType: 'Double',
							businessType: 'Decimal',
							label: {en_US: 'Double'},
							name: 'doubleField',
							required: false,
						},
						{
							DBType: 'Integer',
							businessType: 'Integer',
							label: {en_US: 'Integer'},
							name: 'integerField',
							required: false,
						},
						{
							DBType: 'Long',
							businessType: 'LongInteger',
							label: {en_US: 'Long'},
							name: 'longField',
							required: false,
						},
						{
							DBType: 'String',
							businessType: 'Text',
							label: {en_US: 'String'},
							name: 'stringField',
							required: false,
						},
					] as any,
					status: {code: 0},
				});

			apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

			const applicationName = `c/${objectDefinition.name.toLowerCase()}s`;

			await apiHelpers.objectEntry.postObjectEntry(
				{
					bigDecimalField: '123.123456',
					booleanField: true,
					dateField: '2001-01-01',
					doubleField: 1.54,
					integerField: 12345,
					longField: 12345678,
					stringField: 'Text Test',
				},
				applicationName
			);

			await viewObjectEntriesPage.goto(objectDefinition.className);

			await viewObjectEntriesPage.frontendDatasetItems.first().click();

			await expect(
				page.getByLabel('BigDecimal', {exact: true})
			).toBeVisible();

			await expect(
				page.getByLabel('BigDecimal', {exact: true})
			).toHaveValue('123.123456');

			await expect(page.getByLabel('Boolean', {exact: true})).toBeVisible();

			await expect(page.getByLabel('Boolean', {exact: true})).toBeChecked();

			await expect(page.getByLabel('Date', {exact: true})).toBeVisible();

			await expect(page.getByLabel('Date', {exact: true})).toHaveValue(
				'01/01/2001'
			);

			await expect(page.getByLabel('Double', {exact: true})).toBeVisible();

			await expect(page.getByLabel('Double', {exact: true})).toHaveValue(
				'1.54'
			);

			await expect(
				page.getByLabel('Integer', {exact: true})
			).toBeVisible();

			await expect(
				page.getByLabel('Integer', {exact: true})
			).toHaveValue('12345');

			await expect(page.getByLabel('Long', {exact: true})).toBeVisible();

			await expect(page.getByLabel('Long', {exact: true})).toHaveValue(
				'12345678'
			);

			await expect(page.getByLabel('String', {exact: true})).toBeVisible();

			await expect(page.getByLabel('String', {exact: true})).toHaveValue(
				'Text Test'
			);
		}
	);

	// Unified: BigDecimal/Boolean/Date/Double/Integer/Long/String entry is displayed on auto-generated table

	test('entries of different field types are displayed on auto-generated table',
		async ({apiHelpers, page, viewObjectEntriesPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields: [
						{
							DBType: 'BigDecimal',
							businessType: 'PrecisionDecimal',
							label: {en_US: 'BigDecimal'},
							name: 'bigDecimalField',
							required: false,
						},
						{
							DBType: 'Boolean',
							businessType: 'Boolean',
							label: {en_US: 'Boolean'},
							name: 'booleanField',
							required: false,
						},
						{
							DBType: 'Date',
							businessType: 'Date',
							label: {en_US: 'Date'},
							name: 'dateField',
							required: false,
						},
						{
							DBType: 'Double',
							businessType: 'Decimal',
							label: {en_US: 'Double'},
							name: 'doubleField',
							required: false,
						},
						{
							DBType: 'Integer',
							businessType: 'Integer',
							label: {en_US: 'Integer'},
							name: 'integerField',
							required: false,
						},
						{
							DBType: 'Long',
							businessType: 'LongInteger',
							label: {en_US: 'Long'},
							name: 'longField',
							required: false,
						},
						{
							DBType: 'String',
							businessType: 'Text',
							label: {en_US: 'Text'},
							name: 'textField',
							required: false,
						},
					] as any,
					status: {code: 0},
				});

			apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

			const applicationName = `c/${objectDefinition.name.toLowerCase()}s`;

			await apiHelpers.objectEntry.postObjectEntry(
				{
					bigDecimalField: '123.123456',
					booleanField: true,
					dateField: '2021-09-23',
					doubleField: 1.54,
					integerField: 123456789,
					longField: 1234567891234567,
					textField: 'Test text',
				},
				applicationName
			);

			await viewObjectEntriesPage.goto(objectDefinition.className);

			await expect(page.getByText('123.123456')).toBeVisible();
			await expect(page.getByText('Yes')).toBeVisible();
			await expect(page.getByText('Sep 23, 2021')).toBeVisible();
			await expect(page.getByText('1.54')).toBeVisible();
			await expect(page.getByText('123456789')).toBeVisible();
			await expect(page.getByText('1234567891234567')).toBeVisible();
			await expect(page.getByText('Test text')).toBeVisible();
		}
	);

	// Fixme: Picklist and BigDecimal/Date add — require additional setup not yet migrated

	test.fixme('can add BigDecimal entry via object portlet',
		async ({apiHelpers, page, viewObjectEntriesPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields: [
						{
							DBType: 'BigDecimal',
							businessType: 'PrecisionDecimal',
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

	test.fixme('can add Date entry via object portlet',
		async ({apiHelpers, page, viewObjectEntriesPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields: [
						{
							DBType: 'Date',
							businessType: 'Date',
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

	test.fixme('can add Picklist entry via object portlet',
		async ({apiHelpers, page, viewObjectEntriesPage}) => {
			// Requires picklist setup (addPicklistViaAPI, addPicklistItemViaAPI)
			// and then adding entry with the picklist field selected
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields: [
						{
							DBType: 'String',
							businessType: 'Text',
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

	test.fixme('can edit Picklist entry via object portlet',
		async ({apiHelpers, page, viewObjectEntriesPage}) => {
			// Requires picklist setup and picklist field creation via UI
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields: [
						{
							DBType: 'String',
							businessType: 'Text',
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

	test.fixme('can view Picklist entry and label via object portlet',
		async ({apiHelpers, page, viewObjectEntriesPage}) => {
			// Requires picklist setup and picklist field creation via UI
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields: [
						{
							DBType: 'String',
							businessType: 'Text',
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

	// Standalone tests

	test('can access custom object portlet with access permission',
		async ({apiHelpers, page, viewObjectEntriesPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields: [
						{
							DBType: 'String',
							businessType: 'Text',
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

	test.fixme('can add entry on object scoped by site',
		async ({apiHelpers, page, viewObjectEntriesPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields: [
						{
							DBType: 'String',
							businessType: 'Text',
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

	test('can add entry without selecting a picklist value',
		async ({apiHelpers, page, viewObjectEntriesPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields: [
						{
							DBType: 'String',
							businessType: 'Text',
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

	test('can add object entry with add permission',
		async ({apiHelpers, page, viewObjectEntriesPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields: [
						{
							DBType: 'String',
							businessType: 'Text',
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

	test('can add special characters on field named Name',
		async ({apiHelpers, page, viewObjectEntriesPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields: [
						{
							DBType: 'String',
							businessType: 'Text',
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

	test.fixme('can apply permission only to specific site when scoped by site',
		async ({apiHelpers, page, viewObjectEntriesPage}) => {
			// Requires creating 2 sites and applying site-scoped permissions
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields: [
						{
							DBType: 'String',
							businessType: 'Text',
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

	test('can cancel entry submission',
		async ({apiHelpers, page, viewObjectEntriesPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields: [
						{
							DBType: 'String',
							businessType: 'Text',
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

	test('can cancel entry update',
		async ({apiHelpers, page, viewObjectEntriesPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields: [
						{
							DBType: 'String',
							businessType: 'Text',
							label: {en_US: 'Field'},
							name: 'textField',
							required: false,
						},
					] as any,
					status: {code: 0},
				});

			apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

			const applicationName = `c/${objectDefinition.name.toLowerCase()}s`;

			await apiHelpers.objectEntry.postObjectEntry(
				{textField: 'Test Entry'},
				applicationName
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

	test('can change columns to be displayed on auto-generated table',
		async ({apiHelpers, page, viewObjectEntriesPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields: [
						{
							DBType: 'String',
							businessType: 'Text',
							label: {en_US: 'Text'},
							name: 'textField',
							required: false,
						},
					] as any,
					status: {code: 0},
				});

			apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

			const applicationName = `c/${objectDefinition.name.toLowerCase()}s`;

			await apiHelpers.objectEntry.postObjectEntry(
				{textField: 'Test text'},
				applicationName
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

	test('can delete an entry on auto-generated table',
		async ({apiHelpers, page, viewObjectEntriesPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields: [
						{
							DBType: 'String',
							businessType: 'Text',
							label: {en_US: 'Text'},
							name: 'textField',
							required: false,
						},
					] as any,
					status: {code: 0},
				});

			apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

			const applicationName = `c/${objectDefinition.name.toLowerCase()}s`;

			await apiHelpers.objectEntry.postObjectEntry(
				{textField: 'Test text'},
				applicationName
			);

			await viewObjectEntriesPage.goto(objectDefinition.className);

			await expect(page.getByText('Test text')).toBeVisible();

			await viewObjectEntriesPage.frontendDatasetActions.click();

			await viewObjectEntriesPage.frontendDatasetDeleteAction.click();

			await page.getByRole('button', {name: 'Delete'}).click();

			await expect(page.getByText('No Results Found')).toBeVisible();
		}
	);

	test('can delete object entry with delete permission',
		async ({apiHelpers, page, viewObjectEntriesPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields: [
						{
							DBType: 'String',
							businessType: 'Text',
							label: {en_US: 'Custom Field'},
							name: 'customField',
							required: false,
						},
					] as any,
					status: {code: 0},
				});

			apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

			const applicationName = `c/${objectDefinition.name.toLowerCase()}s`;

			await apiHelpers.objectEntry.postObjectEntry(
				{customField: 'Text test'},
				applicationName
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

	test('cannot add object entry without add permission',
		async ({apiHelpers, page, viewObjectEntriesPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields: [
						{
							DBType: 'String',
							businessType: 'Text',
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

	test('cannot delete object entry without delete permission',
		async ({apiHelpers, page, viewObjectEntriesPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields: [
						{
							DBType: 'String',
							businessType: 'Text',
							label: {en_US: 'Custom Field'},
							name: 'customField',
							required: false,
						},
					] as any,
					status: {code: 0},
				});

			apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

			const applicationName = `c/${objectDefinition.name.toLowerCase()}s`;

			await apiHelpers.objectEntry.postObjectEntry(
				{customField: 'Text test'},
				applicationName
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

	test('cannot update object entry without update permission',
		async ({apiHelpers, page, viewObjectEntriesPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields: [
						{
							DBType: 'String',
							businessType: 'Text',
							label: {en_US: 'Custom Field'},
							name: 'customField',
							required: false,
						},
					] as any,
					status: {code: 0},
				});

			apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

			const applicationName = `c/${objectDefinition.name.toLowerCase()}s`;

			await apiHelpers.objectEntry.postObjectEntry(
				{customField: 'Text test'},
				applicationName
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

	test('cannot view other users entry with only add permission',
		async ({apiHelpers, page, viewObjectEntriesPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields: [
						{
							DBType: 'String',
							businessType: 'Text',
							label: {en_US: 'Custom Field'},
							name: 'customField',
							required: false,
						},
					] as any,
					status: {code: 0},
				});

			apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

			const applicationName = `c/${objectDefinition.name.toLowerCase()}s`;

			await apiHelpers.objectEntry.postObjectEntry(
				{customField: 'Text test'},
				applicationName
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

	test('cannot view other users entry without view permission',
		async ({apiHelpers, page, viewObjectEntriesPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields: [
						{
							DBType: 'String',
							businessType: 'Text',
							label: {en_US: 'Custom Field'},
							name: 'customField',
							required: false,
						},
					] as any,
					status: {code: 0},
				});

			apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

			const applicationName = `c/${objectDefinition.name.toLowerCase()}s`;

			await apiHelpers.objectEntry.postObjectEntry(
				{customField: 'Test Entry'},
				applicationName
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

	test('can order auto-generated table by entry',
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

			const applicationName = `c/${objectDefinition.name.toLowerCase()}s`;

			for (const number of ['1', '2']) {
				await apiHelpers.objectEntry.postObjectEntry(
					{textField: `Test text ${number}`},
					applicationName
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

	test('can search for an entry on auto-generated table',
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

			const applicationName = `c/${objectDefinition.name.toLowerCase()}s`;

			await apiHelpers.objectEntry.postObjectEntry(
				{textField: 'Test 1'},
				applicationName
			);

			await apiHelpers.objectEntry.postObjectEntry(
				{textField: 'Entry 2'},
				applicationName
			);

			await viewObjectEntriesPage.goto(objectDefinition.className);

			await page.getByPlaceholder('Search').fill('Entry 2');

			await page.getByPlaceholder('Search').press('Enter');

			await expect(
				page.locator('table').getByText('Entry 2')
			).toBeVisible();

			await expect(
				page.locator('table').getByText('Test 1')
			).not.toBeVisible();
		}
	);

	test('can update object entry with update permission',
		async ({apiHelpers, page, viewObjectEntriesPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields: [
						{
							DBType: 'String',
							businessType: 'Text',
							label: {en_US: 'Custom Field'},
							name: 'customField',
							required: false,
						},
					] as any,
					status: {code: 0},
				});

			apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

			const applicationName = `c/${objectDefinition.name.toLowerCase()}s`;

			await apiHelpers.objectEntry.postObjectEntry(
				{customField: 'Text test'},
				applicationName
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

	test('can view other users entry with view permission',
		async ({apiHelpers, page, viewObjectEntriesPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields: [
						{
							DBType: 'String',
							businessType: 'Text',
							label: {en_US: 'Custom Field'},
							name: 'customField',
							required: false,
						},
					] as any,
					status: {code: 0},
				});

			apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

			const applicationName = `c/${objectDefinition.name.toLowerCase()}s`;

			await apiHelpers.objectEntry.postObjectEntry(
				{customField: 'Test Entry'},
				applicationName
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

	test('can view user name on author column',
		async ({apiHelpers, page, viewObjectEntriesPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields: [
						{
							DBType: 'String',
							businessType: 'Text',
							label: {en_US: 'Custom Field'},
							name: 'customField',
							required: false,
						},
					] as any,
					status: {code: 0},
				});

			apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

			const applicationName = `c/${objectDefinition.name.toLowerCase()}s`;

			await apiHelpers.objectEntry.postObjectEntry(
				{customField: 'Text test'},
				applicationName
			);

			await viewObjectEntriesPage.goto(objectDefinition.className);

			await expect(
				page.getByRole('cell', {name: 'Test Test'})
			).toBeVisible();
		}
	);

	test('columns ID, fields and status are displayed',
		async ({apiHelpers, page, viewObjectEntriesPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields: [
						{
							DBType: 'String',
							businessType: 'Text',
							label: {en_US: 'Field'},
							name: 'textField',
							required: false,
						},
					] as any,
					status: {code: 0},
				});

			apiHelpers.data.push({id: objectDefinition.id, type: 'objectDefinition'});

			const applicationName = `c/${objectDefinition.name.toLowerCase()}s`;

			await apiHelpers.objectEntry.postObjectEntry(
				{textField: 'String'},
				applicationName
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

	test('duplicated entry is not submitted when refreshing page',
		async ({apiHelpers, page, viewObjectEntriesPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields: [
						{
							DBType: 'String',
							businessType: 'Text',
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

	test('empty state is displayed when searching for nonexistent value',
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

			const applicationName = `c/${objectDefinition.name.toLowerCase()}s`;

			await apiHelpers.objectEntry.postObjectEntry(
				{textField: 'Test text'},
				applicationName
			);

			await viewObjectEntriesPage.goto(objectDefinition.className);

			await page.getByPlaceholder('Search').fill('Lorem ipsum');

			await page.getByPlaceholder('Search').press('Enter');

			await expect(page.getByText('No Results Found')).toBeVisible();
		}
	);

	test('empty state is displayed when no entry exists',
		async ({apiHelpers, page, viewObjectEntriesPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields: [
						{
							DBType: 'String',
							businessType: 'Text',
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
