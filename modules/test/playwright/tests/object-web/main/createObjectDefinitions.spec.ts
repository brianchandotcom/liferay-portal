/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {FormBuilderPage} from '../../../pages/dynamic-data-mapping-form-web/FormBuilderPage';
import {FormBuilderSidePanelPage} from '../../../pages/dynamic-data-mapping-form-web/FormBuilderSidePanelPage';
import {FormSettingsModalPage} from '../../../pages/dynamic-data-mapping-form-web/FormSettingsModalPage';
import {PageEditorPage} from '../../../pages/layout-content-page-editor-web/PageEditorPage';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';
import {performUserSwitch, userData} from '../../../utils/performLogin';
import {waitForAlert} from '../../../utils/waitForAlert';
import getFragmentDefinition from '../../layout-content-page-editor-web/main/utils/getFragmentDefinition';
import getPageDefinition from '../../layout-content-page-editor-web/main/utils/getPageDefinition';
import {generateObjectFields} from '../../object-web/utils/generateObjectFields';

const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	objectPagesTest
);

test.describe('Object Definitions', () => {
	test(
		'Verify it is possible to view and access the Object Admin portlet with the Access in Control Panel permission',
		{tag: '@LPS-135390'},
		async ({apiHelpers, page, viewObjectDefinitionsPage}) => {
			const company =
				await apiHelpers.jsonWebServicesCompany.getCompanyByWebId(
					'liferay.com'
				);

			const role = await apiHelpers.headlessAdminUser.postRole({
				name: 'ObjRole' + getRandomInt(),
				rolePermissions: [
					{
						actionIds: ['ACCESS_IN_CONTROL_PANEL', 'VIEW'],
						primaryKey: String(company.companyId),
						resourceName:
							'com_liferay_object_web_internal_object_definitions_portlet_ObjectDefinitionsPortlet',
						scope: 1,
					},
					{
						actionIds: ['VIEW'],
						primaryKey: String(company.companyId),
						resourceName: 'com.liferay.object.model.ObjectDefinition',
						scope: 1,
					},
					{
						actionIds: ['VIEW_CONTROL_PANEL'],
						primaryKey: String(company.companyId),
						resourceName: '90',
						scope: 1,
					},
				],
			});

			apiHelpers.data.push({id: role.id, type: 'role'});

			const user = await apiHelpers.headlessAdminUser.postUserAccount();

			apiHelpers.data.push({id: user.id, type: 'userAccount'});

			userData[user.alternateName] = {
				name: user.givenName,
				password: 'test',
				surname: user.familyName,
			};

			await apiHelpers.headlessAdminUser.assignUserToRole(
				role.externalReferenceCode,
				user.id
			);

			await performUserSwitch(page, user.alternateName);

			await viewObjectDefinitionsPage.goto();

			await expect(page.getByText('User')).toBeVisible();
		}
	);

	test(
		'Verify it is possible to add an Object with the Add Object Definition permission',
		{tag: '@LPS-135390'},
		async ({
			apiHelpers,
			modalAddObjectDefinitionPage,
			page,
			viewObjectDefinitionsPage,
		}) => {
			const company =
				await apiHelpers.jsonWebServicesCompany.getCompanyByWebId(
					'liferay.com'
				);

			const role = await apiHelpers.headlessAdminUser.postRole({
				name: 'ObjRole' + getRandomInt(),
				rolePermissions: [
					{
						actionIds: ['ACCESS_IN_CONTROL_PANEL', 'VIEW'],
						primaryKey: String(company.companyId),
						resourceName:
							'com_liferay_object_web_internal_object_definitions_portlet_ObjectDefinitionsPortlet',
						scope: 1,
					},
					{
						actionIds: ['ADD_OBJECT_DEFINITION'],
						primaryKey: String(company.companyId),
						resourceName: 'com.liferay.object',
						scope: 1,
					},
					{
						actionIds: ['VIEW'],
						primaryKey: String(company.companyId),
						resourceName: 'com.liferay.object.model.ObjectDefinition',
						scope: 1,
					},
					{
						actionIds: ['VIEW_CONTROL_PANEL'],
						primaryKey: String(company.companyId),
						resourceName: '90',
						scope: 1,
					},
				],
			});

			apiHelpers.data.push({id: role.id, type: 'role'});

			const user = await apiHelpers.headlessAdminUser.postUserAccount();

			apiHelpers.data.push({id: user.id, type: 'userAccount'});

			userData[user.alternateName] = {
				name: user.givenName,
				password: 'test',
				surname: user.familyName,
			};

			await apiHelpers.headlessAdminUser.assignUserToRole(
				role.externalReferenceCode,
				user.id
			);

			await performUserSwitch(page, user.alternateName);

			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.createObjectDefinitionButton.click();

			const objectLabel = 'CustomObject' + getRandomInt();

			const objectDefinitionResponse =
				await modalAddObjectDefinitionPage.createObjectDefinition(
					objectLabel
				);

			apiHelpers.data.push({
				id: objectDefinitionResponse.id,
				type: 'objectDefinition',
			});

			await viewObjectDefinitionsPage.goto();

			await expect(page.getByText(objectLabel)).toBeVisible();
		}
	);

	test(
		'Verify it is possible to create a Custom Object',
		{tag: '@LPS-135549'},
		async ({
			apiHelpers,
			modalAddObjectDefinitionPage,
			page,
			viewObjectDefinitionsPage,
		}) => {
			await viewObjectDefinitionsPage.goto();

			const objectDefinitionLabel = 'CustomObject' + getRandomInt();

			await viewObjectDefinitionsPage.createObjectDefinitionButton.click();

			const objectDefinition =
				await modalAddObjectDefinitionPage.createObjectDefinition(
					objectDefinitionLabel
				);

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await waitForAlert(
				page,
				`Success:${objectDefinitionLabel} was created successfully.`
			);

			await expect(page.getByRole('link', {name: objectDefinitionLabel})).toBeVisible();
		}
	);

	test(
		'Verify the user can delete an inactive custom object',
		{tag: '@LPS-184370'},
		async ({apiHelpers, page, viewObjectDefinitionsPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.clickObjectDefinitionActionButton(
				objectDefinition.label['en_US']
			);

			await viewObjectDefinitionsPage.deleteObjectDefinitionOption.click();

			await page
				.getByPlaceholder('Confirm Object Definition Name', {
					exact: false,
				})
				.fill(objectDefinition.name);

			await page.getByRole('button', {name: 'Delete'}).click();

			apiHelpers.data.splice(
				apiHelpers.data.findIndex(
					(object) =>
						object.id === objectDefinition.id &&
						object.type === 'objectDefinition'
				),
				1
			);

			await page.waitForTimeout(2000);

			await expect(
				page.getByRole('heading', {name: objectDefinition.label['en_US']})
			).toBeHidden();
		}
	);

	test.fixme(
		'Verify it is possible to delete an Object with the Delete permission',
		{tag: '@LPS-135390'},
		async ({apiHelpers, page, viewObjectDefinitionsPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			const company =
				await apiHelpers.jsonWebServicesCompany.getCompanyByWebId(
					'liferay.com'
				);

			const role = await apiHelpers.headlessAdminUser.postRole({
				name: 'ObjRole' + getRandomInt(),
				rolePermissions: [
					{
						actionIds: ['ACCESS_IN_CONTROL_PANEL', 'VIEW'],
						primaryKey: String(company.companyId),
						resourceName:
							'com_liferay_object_web_internal_object_definitions_portlet_ObjectDefinitionsPortlet',
						scope: 1,
					},
					{
						actionIds: ['DELETE', 'VIEW'],
						primaryKey: String(company.companyId),
						resourceName: 'com.liferay.object.model.ObjectDefinition',
						scope: 1,
					},
					{
						actionIds: ['VIEW_CONTROL_PANEL'],
						primaryKey: String(company.companyId),
						resourceName: '90',
						scope: 1,
					},
				],
			});

			apiHelpers.data.push({id: role.id, type: 'role'});

			const user = await apiHelpers.headlessAdminUser.postUserAccount();

			apiHelpers.data.push({id: user.id, type: 'userAccount'});

			userData[user.alternateName] = {
				name: user.givenName,
				password: 'test',
				surname: user.familyName,
			};

			await apiHelpers.headlessAdminUser.assignUserToRole(
				role.externalReferenceCode,
				user.id
			);

			await performUserSwitch(page, user.alternateName);

			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.clickObjectDefinitionActionButton(
				objectDefinition.label['en_US']
			);

			await viewObjectDefinitionsPage.deleteObjectDefinitionOption.click();

			await page
				.getByPlaceholder('Confirm Object Definition Name', {
					exact: false,
				})
				.fill(objectDefinition.name);

			await page.getByRole('button', {exact: true, name: 'Delete'}).click();

			await waitForAlert(page);

			await expect(
				page.getByRole('heading', {name: objectDefinition.label['en_US']})
			).toBeHidden();
		}
	);

	test(
		'Verify it is not possible to add an Object without the Add Object Definition permission',
		{tag: '@LPS-135390'},
		async ({apiHelpers, page, viewObjectDefinitionsPage}) => {
			const company =
				await apiHelpers.jsonWebServicesCompany.getCompanyByWebId(
					'liferay.com'
				);

			const role = await apiHelpers.headlessAdminUser.postRole({
				name: 'ObjRole' + getRandomInt(),
				rolePermissions: [
					{
						actionIds: ['ACCESS_IN_CONTROL_PANEL', 'VIEW'],
						primaryKey: String(company.companyId),
						resourceName:
							'com_liferay_object_web_internal_object_definitions_portlet_ObjectDefinitionsPortlet',
						scope: 1,
					},
					{
						actionIds: ['VIEW'],
						primaryKey: String(company.companyId),
						resourceName: 'com.liferay.object.model.ObjectDefinition',
						scope: 1,
					},
					{
						actionIds: ['VIEW_CONTROL_PANEL'],
						primaryKey: String(company.companyId),
						resourceName: '90',
						scope: 1,
					},
				],
			});

			apiHelpers.data.push({id: role.id, type: 'role'});

			const user = await apiHelpers.headlessAdminUser.postUserAccount();

			apiHelpers.data.push({id: user.id, type: 'userAccount'});

			userData[user.alternateName] = {
				name: user.givenName,
				password: 'test',
				surname: user.familyName,
			};

			await apiHelpers.headlessAdminUser.assignUserToRole(
				role.externalReferenceCode,
				user.id
			);

			await performUserSwitch(page, user.alternateName);

			await viewObjectDefinitionsPage.goto();

			await expect(
				viewObjectDefinitionsPage.createObjectDefinitionButton
			).toBeHidden();
		}
	);

	test(
		'Verify that it is not possible to create an Object with a duplicated Object Name',
		{tag: '@LPS-135549'},
		async ({
			apiHelpers,
			modalAddObjectDefinitionPage,
			page,
			viewObjectDefinitionsPage,
		}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.createObjectDefinitionButton.click();

			await modalAddObjectDefinitionPage.objectLabelInput.fill(
				objectDefinition.label['en_US']
			);
			await modalAddObjectDefinitionPage.objectPluralLabelInput.fill(
				objectDefinition.pluralLabel['en_US']
			);

			await modalAddObjectDefinitionPage.objectDefinitionSaveButton.click();

			await expect(
				page.getByText('This name is already in use. Try another.')
			).toBeVisible();

			await page.getByRole('button', {name: 'Cancel'}).click();
		}
	);

	test(
		'Verify it is not possible to delete an Object without the Delete permission',
		{tag: '@LPS-135390'},
		async ({apiHelpers, page, viewObjectDefinitionsPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			const company =
				await apiHelpers.jsonWebServicesCompany.getCompanyByWebId(
					'liferay.com'
				);

			const role = await apiHelpers.headlessAdminUser.postRole({
				name: 'ObjRole' + getRandomInt(),
				rolePermissions: [
					{
						actionIds: ['ACCESS_IN_CONTROL_PANEL', 'VIEW'],
						primaryKey: String(company.companyId),
						resourceName:
							'com_liferay_object_web_internal_object_definitions_portlet_ObjectDefinitionsPortlet',
						scope: 1,
					},
					{
						actionIds: ['VIEW'],
						primaryKey: String(company.companyId),
						resourceName: 'com.liferay.object.model.ObjectDefinition',
						scope: 1,
					},
					{
						actionIds: ['VIEW_CONTROL_PANEL'],
						primaryKey: String(company.companyId),
						resourceName: '90',
						scope: 1,
					},
				],
			});

			apiHelpers.data.push({id: role.id, type: 'role'});

			const user = await apiHelpers.headlessAdminUser.postUserAccount();

			apiHelpers.data.push({id: user.id, type: 'userAccount'});

			userData[user.alternateName] = {
				name: user.givenName,
				password: 'test',
				surname: user.familyName,
			};

			await apiHelpers.headlessAdminUser.assignUserToRole(
				role.externalReferenceCode,
				user.id
			);

			await performUserSwitch(page, user.alternateName);

			await viewObjectDefinitionsPage.goto();

			await expect(
				page.getByRole('heading', {name: objectDefinition.label['en_US']})
			).toBeVisible();

			await expect(
				page
					.getByRole('row', {
						name: objectDefinition.label['en_US'],
					})
					.getByRole('button')
			).toBeHidden();
		}
	);

	test(
		'Verify it is not possible to leave the Object Label field blank',
		{tag: '@LPS-135549'},
		async ({modalAddObjectDefinitionPage, page, viewObjectDefinitionsPage}) => {
			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.createObjectDefinitionButton.click();

			await modalAddObjectDefinitionPage.objectPluralLabelInput.fill(
				'Plural Label'
			);

			await modalAddObjectDefinitionPage.objectDefinitionSaveButton.click();

			await expect(page.getByText('Required')).toBeVisible();

			await page.getByRole('button', {name: 'Cancel'}).click();
		}
	);

	test(
		'Verify it is not possible to leave the Object Name field blank',
		{tag: '@LPS-135549'},
		async ({modalAddObjectDefinitionPage, page, viewObjectDefinitionsPage}) => {
			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.createObjectDefinitionButton.click();

			await modalAddObjectDefinitionPage.objectPluralLabelInput.fill(
				'Plural Label'
			);

			await modalAddObjectDefinitionPage.objectDefinitionSaveButton.click();

			await expect(page.getByText('Required')).toBeVisible();

			await page.getByRole('button', {name: 'Cancel'}).click();
		}
	);

	test(
		'Verify it is not possible to save with special characters for the Object Name',
		{tag: '@LPS-135549'},
		async ({modalAddObjectDefinitionPage, page, viewObjectDefinitionsPage}) => {
			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.createObjectDefinitionButton.click();

			await modalAddObjectDefinitionPage.objectLabelInput.fill(
				'Object@Special!'
			);
			await modalAddObjectDefinitionPage.objectPluralLabelInput.fill(
				'Objects Special'
			);

			await modalAddObjectDefinitionPage.objectDefinitionSaveButton.click();

			await expect(
				page.getByText(
					'The object name must begin with an upper case letter and contain only alphanumeric characters.'
				)
			).toBeVisible();

			await page.getByRole('button', {name: 'Cancel'}).click();
		}
	);

	test(
		'Verify it is possible to search for a Custom Object',
		{tag: '@LPS-135547'},
		async ({apiHelpers, page, viewObjectDefinitionsPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await viewObjectDefinitionsPage.goto();

			await page
				.getByPlaceholder('Search')
				.fill(objectDefinition.label['en_US']);

			await expect(
				page.getByRole('heading', {name: objectDefinition.label['en_US']})
			).toBeVisible();
		}
	);

	test(
		'Verify it is possible to search for a System Object',
		{tag: '@LPS-135547'},
		async ({page, viewObjectDefinitionsPage}) => {
			await viewObjectDefinitionsPage.goto();

			await page.getByPlaceholder('Search').fill('User');

			await expect(page.getByText('User')).toBeVisible();
		}
	);

	test(
		'Verify it is possible to view the Details of an Object by clicking on the eye icon',
		{tag: '@LPS-135549'},
		async ({apiHelpers, page, viewObjectDefinitionsPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await viewObjectDefinitionsPage.goto();

			await page
				.getByRole('row')
				.filter({hasText: objectDefinition.label['en_US']})
				.getByRole('link')
				.first()
				.click();

			await expect(
				page.getByRole('heading', {name: objectDefinition.label['en_US']})
			).toBeVisible();
		}
	);

	test(
		'Verify it is possible to view the Details of an Object by clicking on its name',
		{tag: '@LPS-135549'},
		async ({apiHelpers, page, viewObjectDefinitionsPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.clickEditObjectDefinitionLink(
				objectDefinition.label['en_US']
			);

			await expect(
				page.getByRole('heading', {name: objectDefinition.label['en_US']})
			).toBeVisible();
		}
	);

	test(
		'Verify it is possible to view an Object with the View permission',
		{tag: '@LPS-135390'},
		async ({apiHelpers, page, viewObjectDefinitionsPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			const company =
				await apiHelpers.jsonWebServicesCompany.getCompanyByWebId(
					'liferay.com'
				);

			const role = await apiHelpers.headlessAdminUser.postRole({
				name: 'ObjRole' + getRandomInt(),
				rolePermissions: [
					{
						actionIds: ['ACCESS_IN_CONTROL_PANEL', 'VIEW'],
						primaryKey: String(company.companyId),
						resourceName:
							'com_liferay_object_web_internal_object_definitions_portlet_ObjectDefinitionsPortlet',
						scope: 1,
					},
					{
						actionIds: ['VIEW'],
						primaryKey: String(company.companyId),
						resourceName: 'com.liferay.object.model.ObjectDefinition',
						scope: 1,
					},
					{
						actionIds: ['VIEW_CONTROL_PANEL'],
						primaryKey: String(company.companyId),
						resourceName: '90',
						scope: 1,
					},
				],
			});

			apiHelpers.data.push({id: role.id, type: 'role'});

			const user = await apiHelpers.headlessAdminUser.postUserAccount();

			apiHelpers.data.push({id: user.id, type: 'userAccount'});

			userData[user.alternateName] = {
				name: user.givenName,
				password: 'test',
				surname: user.familyName,
			};

			await apiHelpers.headlessAdminUser.assignUserToRole(
				role.externalReferenceCode,
				user.id
			);

			await performUserSwitch(page, user.alternateName);

			await viewObjectDefinitionsPage.goto();

			await expect(
				page.getByRole('heading', {name: objectDefinition.label['en_US']})
			).toBeVisible();
		}
	);

	test(
		'Verify that the columns Name System and Status are displayed for the Objects table',
		{tag: '@LPS-135549'},
		async ({page, viewObjectDefinitionsPage}) => {
			await viewObjectDefinitionsPage.goto();

			await expect(
				page.getByRole('columnheader', {name: 'Name'})
			).toBeVisible();
			await expect(
				page.getByRole('columnheader', {name: 'System'})
			).toBeVisible();
			await expect(
				page.getByRole('columnheader', {name: 'Status'})
			).toBeVisible();
		}
	);

	test(
		'Verify that the columns Name System and Status displays the correct value on the Objects table when a Custom Object is created',
		{tag: '@LPS-135549'},
		async ({apiHelpers, page, viewObjectDefinitionsPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await viewObjectDefinitionsPage.goto();

			const objectRow = page
				.getByRole('row')
				.filter({hasText: objectDefinition.label['en_US']});

			await expect(objectRow).toBeVisible();
			await expect(objectRow.getByText('No')).toBeVisible();
			await expect(objectRow.getByText('Draft')).toBeVisible();
		}
	);

	test(
		'Verify that user can view custom objects',
		{tag: '@LPS-135548'},
		async ({apiHelpers, page, viewObjectDefinitionsPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await viewObjectDefinitionsPage.goto();

			await expect(
				page.getByRole('heading', {name: objectDefinition.label['en_US']})
			).toBeVisible();
		}
	);

	test(
		'Verify that user can view system objects',
		{tag: '@LPS-135548'},
		async ({page, viewObjectDefinitionsPage}) => {
			await viewObjectDefinitionsPage.goto();

			await expect(page.getByText('User')).toBeVisible();
			await expect(page.getByText('Commerce Order')).toBeVisible();
		}
	);

	test(
		'Verify the empty state when searching for an Object returns nothing',
		{tag: '@LPS-135547'},
		async ({page, viewObjectDefinitionsPage}) => {
			await viewObjectDefinitionsPage.goto();

			await page
				.getByPlaceholder('Search')
				.fill('NonExistentObject' + getRandomInt());

			await expect(page.getByText('No Results Found')).toBeVisible();
		}
	);

	test(
		'Verify that previous filled data is not kept when cancelling the creation of an Object',
		{tag: '@LPS-139418'},
		async ({modalAddObjectDefinitionPage, page, viewObjectDefinitionsPage}) => {
			await viewObjectDefinitionsPage.goto();

			const objectDefinitionLabel = 'CustomObject' + getRandomInt();

			await viewObjectDefinitionsPage.createObjectDefinitionButton.click();

			await modalAddObjectDefinitionPage.objectLabelInput.fill(
				objectDefinitionLabel
			);
			await modalAddObjectDefinitionPage.objectPluralLabelInput.fill(
				objectDefinitionLabel
			);

			await page.getByRole('button', {name: 'Cancel'}).click();

			await expect(page.getByText(objectDefinitionLabel)).toBeHidden();

			await viewObjectDefinitionsPage.createObjectDefinitionButton.click();

			await expect(modalAddObjectDefinitionPage.objectLabelInput).toHaveValue(
				''
			);
			await expect(
				modalAddObjectDefinitionPage.objectPluralLabelInput
			).toHaveValue('');

			await page.getByRole('button', {name: 'Cancel'}).click();
		}
	);

	test(
		'Verify that the Object Name is autofilled when Label is filled',
		{tag: '@LPS-135549'},
		async ({modalAddObjectDefinitionPage, page, viewObjectDefinitionsPage}) => {
			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.createObjectDefinitionButton.click();

			await modalAddObjectDefinitionPage.objectLabelInput.fill(
				'Test Object Label'
			);

			await expect(page.locator('input[name="name"]')).toHaveValue(
				'TestObjectLabel'
			);

			await page.getByRole('button', {name: 'Cancel'}).click();
		}
	);

	test(
		'Verify that is not possible to submit entries in a form with an Object that was inactivated',
		{tag: '@LPS-139005'},
		async ({apiHelpers, page, viewObjectDefinitionsPage}) => {
			const objectFields = generateObjectFields({
				objectFieldBusinessTypes: ['Text'],
			});

			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields,
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			// Create a form with Object storage type

			const formBuilderPage = new FormBuilderPage(page);
			const formSettingsModalPage = new FormSettingsModalPage(page);
			const formBuilderSidePanelPage = new FormBuilderSidePanelPage(page);

			await formBuilderPage.goToNew();

			const formName = 'Form' + getRandomInt();

			await formBuilderPage.fillFormTitle(formName);

			await formBuilderPage.formSettingsButton.click();

			await formSettingsModalPage.selectStorageType('Object');

			await formSettingsModalPage.selectObject(
				objectDefinition.label['en_US']
			);

			await formSettingsModalPage.clickDoneButton();

			// Add text field and map to object field

			await formBuilderSidePanelPage.addTextButton.dblclick();

			await formBuilderSidePanelPage.selectObjectField(
				objectFields[0].label!['en_US']
			);

			await formBuilderSidePanelPage.clickBackButton();

			await formBuilderPage.clickSaveButton();

			await waitForAlert(page);

			await formBuilderPage.clickPublishFormButton();

			// Inactivate the object

			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.changeObjectActivateStatus(
				objectDefinition.name
			);

			// Navigate to Forms list and verify warning icon on the form

			await page.goto(
				'/group/guest/~/control_panel/manage/-/dynamic_data_mapping_form'
			);

			await expect(
				page.locator('.lexicon-icon-exclamation-full').first()
			).toBeVisible();

			// Reactivate for cleanup

			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.changeObjectActivateStatus(
				objectDefinition.name
			);
		}
	);

	test(
		'Verify that is possible to submit entries in a form with an Object that was reactivated',
		{tag: '@LPS-139005'},
		async ({
			apiHelpers,
			page,
			viewObjectDefinitionsPage,
			viewObjectEntriesPage,
		}) => {
			const objectFields = generateObjectFields({
				objectFieldBusinessTypes: ['Text'],
			});

			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields,
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			// Create a form with Object storage type

			const formBuilderPage = new FormBuilderPage(page);
			const formSettingsModalPage = new FormSettingsModalPage(page);
			const formBuilderSidePanelPage = new FormBuilderSidePanelPage(page);

			await formBuilderPage.goToNew();

			const formName = 'Form' + getRandomInt();

			await formBuilderPage.fillFormTitle(formName);

			await formBuilderPage.formSettingsButton.click();

			await formSettingsModalPage.selectStorageType('Object');

			await formSettingsModalPage.selectObject(
				objectDefinition.label['en_US']
			);

			await formSettingsModalPage.clickDoneButton();

			// Add text field and map to object field

			await formBuilderSidePanelPage.addTextButton.dblclick();

			await formBuilderSidePanelPage.selectObjectField(
				objectFields[0].label!['en_US']
			);

			await formBuilderSidePanelPage.clickBackButton();

			await formBuilderPage.clickSaveButton();

			await waitForAlert(page);

			await formBuilderPage.clickPublishFormButton();

			const formSubmissionURL = await formBuilderPage.getFormSubmissionURL();

			// Inactivate then reactivate the object

			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.changeObjectActivateStatus(
				objectDefinition.name
			);

			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.changeObjectActivateStatus(
				objectDefinition.name
			);

			// Navigate to Forms list and verify no warning icon

			await page.goto(
				'/group/guest/~/control_panel/manage/-/dynamic_data_mapping_form'
			);

			await expect(
				page.locator('.lexicon-icon-exclamation-full')
			).toBeHidden();

			// Submit an entry through the form

			await page.goto(formSubmissionURL);

			await page.getByLabel(objectFields[0].label!['en_US']).fill('Entry 1');

			await page.getByRole('button', {name: 'Submit'}).click();

			await expect(
				page.getByText('Your information was successfully received')
			).toBeVisible();

			// Verify entry appears in object entries

			await viewObjectEntriesPage.goto(objectDefinition.className);

			await expect(page.getByText('Entry 1')).toBeVisible();
		}
	);

	test(
		'Verify that the Object is no longer displayed on the Collection Providers when inactivated',
		{tag: '@LPS-139005'},
		async ({apiHelpers, page, viewObjectDefinitionsPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.changeObjectActivateStatus(
				objectDefinition.name
			);

			await page.goto(
				'/group/guest/~/control_panel/manage/-/display_page_templates'
			);

			await page.getByRole('button', {name: 'New'}).click();

			await expect(
				page.getByRole('menuitem', {
					name: objectDefinition.label['en_US'],
				})
			).toBeHidden();
		}
	);

	test(
		'Verify that the Object is no longer displayed on the Form storage type when inactivated',
		{tag: '@LPS-139005'},
		async ({apiHelpers, page, viewObjectDefinitionsPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.changeObjectActivateStatus(
				objectDefinition.name
			);

			await page.goto(
				'/group/guest/~/control_panel/manage/-/dynamic_data_mapping_form'
			);

			await page.getByRole('button', {name: 'New Form'}).click();

			await page.getByLabel('Storage Type').click();

			await expect(
				page.getByRole('option', {
					name: objectDefinition.label['en_US'],
				})
			).toBeHidden();
		}
	);

	test(
		'Verify that the Object is no longer displayed on the Page Item Selector when inactivated',
		{tag: '@LPS-139005'},
		async ({apiHelpers, page, viewObjectDefinitionsPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.changeObjectActivateStatus(
				objectDefinition.name
			);

			await page.goto(
				'/group/guest/~/control_panel/manage/-/display_page_templates'
			);

			await page.getByRole('button', {name: 'New'}).click();

			await expect(
				page.getByRole('menuitem', {
					name: objectDefinition.label['en_US'],
				})
			).toBeHidden();
		}
	);

	test(
		'Verify that the Object is no longer displayed on the Page Template subtype when inactivated',
		{tag: '@LPS-139005'},
		async ({apiHelpers, page, viewObjectDefinitionsPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.changeObjectActivateStatus(
				objectDefinition.name
			);

			await page.goto(
				'/group/guest/~/control_panel/manage/-/display_page_templates'
			);

			await page.getByRole('button', {name: 'New'}).click();

			await expect(
				page.getByRole('menuitem', {
					name: objectDefinition.label['en_US'],
				})
			).toBeHidden();
		}
	);

	test(
		'Verify that the Object is displayed again on the Page Template subtype when reactivated',
		{tag: '@LPS-139005'},
		async ({apiHelpers, page, viewObjectDefinitionsPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.changeObjectActivateStatus(
				objectDefinition.name
			);

			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.changeObjectActivateStatus(
				objectDefinition.name
			);

			await page.goto(
				'/group/guest/~/control_panel/manage/-/display_page_templates'
			);

			await page.getByRole('button', {name: 'New'}).click();

			await expect(
				page.getByRole('menuitem', {
					name: objectDefinition.label['en_US'],
				})
			).toBeVisible();
		}
	);

	test(
		'Verify that the Object entries are not displayed on Page fragments from an Object that was inactivated',
		{tag: '@LPS-139005'},
		async ({apiHelpers, page, site, viewObjectDefinitionsPage}) => {
			const objectFields = generateObjectFields({
				objectFieldBusinessTypes: ['Text'],
			});

			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields,
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			// Add an entry via API

			const applicationName =
				'c/' + objectDefinition.name.toLowerCase() + 's';
			const fieldName = objectFields[0].name!;

			await apiHelpers.objectEntry.postObjectEntry(
				{[fieldName!]: 'Entry Test'},
				applicationName
			);

			// Create a content page with a Heading fragment

			const headingDefinition = getFragmentDefinition({
				id: getRandomString(),
				key: 'BASIC_COMPONENT-heading',
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([headingDefinition]),
				siteId: site.id,
				title: getRandomString(),
			});

			const pageEditorPage = new PageEditorPage(page);

			await pageEditorPage.goto(layout, site.friendlyUrlPath);

			// Map the heading to the object's item

			await page.getByText('Heading Example', {exact: true}).dblclick();

			await page.getByLabel('Select Item').click();

			const selectFrame = page.frameLocator('iframe[title="Select"]');

			await selectFrame
				.getByRole('menuitem', {name: objectDefinition.name})
				.click();

			await selectFrame.getByText('Entry Test').click();

			await pageEditorPage.publishPage();

			// Verify entry is visible on the page

			await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

			await expect(page.getByText('Entry Test')).toBeVisible();

			// Inactivate the object

			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.changeObjectActivateStatus(
				objectDefinition.name
			);

			// Verify entry is no longer visible on the page

			await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

			await expect(page.getByText('Entry Test')).toBeHidden();

			// Reactivate for cleanup

			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.changeObjectActivateStatus(
				objectDefinition.name
			);
		}
	);

	test(
		'Verify that the Object entries are displayed again on Page fragments from an Object that was reactivated',
		{tag: '@LPS-139005'},
		async ({apiHelpers, page, site, viewObjectDefinitionsPage}) => {
			const objectFields = generateObjectFields({
				objectFieldBusinessTypes: ['Text'],
			});

			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields,
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			// Add an entry via API

			const applicationName =
				'c/' + objectDefinition.name.toLowerCase() + 's';
			const fieldName = objectFields[0].name!;

			await apiHelpers.objectEntry.postObjectEntry(
				{[fieldName!]: 'Test 1'},
				applicationName
			);

			// Create a content page with a Heading fragment

			const headingDefinition = getFragmentDefinition({
				id: getRandomString(),
				key: 'BASIC_COMPONENT-heading',
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([headingDefinition]),
				siteId: site.id,
				title: getRandomString(),
			});

			const pageEditorPage = new PageEditorPage(page);

			await pageEditorPage.goto(layout, site.friendlyUrlPath);

			// Map the heading to the object's item

			await page.getByText('Heading Example', {exact: true}).dblclick();

			await page.getByLabel('Select Item').click();

			const selectFrame = page.frameLocator('iframe[title="Select"]');

			await selectFrame
				.getByRole('menuitem', {name: objectDefinition.name})
				.click();

			await selectFrame.getByText('Test 1').click();

			await pageEditorPage.publishPage();

			// Inactivate then reactivate the object

			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.changeObjectActivateStatus(
				objectDefinition.name
			);

			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.changeObjectActivateStatus(
				objectDefinition.name
			);

			// Verify entry is displayed again on the page

			await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

			await expect(page.getByText('Test 1')).toBeVisible();
		}
	);

	test(
		'Verify that the Object portlet is no longer displayed on the Open Menu when inactivated',
		{tag: '@LPS-139005'},
		async ({apiHelpers, page, viewObjectDefinitionsPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.changeObjectActivateStatus(
				objectDefinition.name
			);

			await page.goto('/');

			await page.getByRole('button', {name: 'Open Menu'}).click();

			await expect(
				page.getByRole('link', {
					name: objectDefinition.pluralLabel['en_US'],
				})
			).toBeHidden();
		}
	);

	test(
		'Verify that the Object portlet is no longer displayed on the Site Menu when inactivated',
		{tag: '@LPS-139005'},
		async ({apiHelpers, page, viewObjectDefinitionsPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					panelCategoryKey: 'site_administration.content',
					scope: 'site',
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.changeObjectActivateStatus(
				objectDefinition.name
			);

			await page.goto('/');

			await page.getByRole('button', {name: 'Product Menu'}).click();

			await expect(
				page.getByRole('link', {
					name: objectDefinition.pluralLabel['en_US'],
				})
			).toBeHidden();
		}
	);

	test(
		'Verify that the Object portlet is displayed again on the Open Menu when reactivated',
		{tag: '@LPS-139005'},
		async ({apiHelpers, page, viewObjectDefinitionsPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.changeObjectActivateStatus(
				objectDefinition.name
			);

			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.changeObjectActivateStatus(
				objectDefinition.name
			);

			await page.goto('/');

			await page.getByRole('button', {name: 'Open Menu'}).click();

			await expect(
				page.getByRole('link', {
					name: objectDefinition.pluralLabel['en_US'],
				})
			).toBeVisible();
		}
	);

	test(
		'Verify that the Object portlet is displayed again on the Site Menu when reactivated',
		{tag: '@LPS-139005'},
		async ({apiHelpers, page, viewObjectDefinitionsPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					panelCategoryKey: 'site_administration.content',
					scope: 'site',
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.changeObjectActivateStatus(
				objectDefinition.name
			);

			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.changeObjectActivateStatus(
				objectDefinition.name
			);

			await page.goto('/');

			await page.getByRole('button', {name: 'Product Menu'}).click();

			await expect(
				page.getByRole('link', {
					name: objectDefinition.pluralLabel['en_US'],
				})
			).toBeVisible();
		}
	);
});
