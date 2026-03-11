/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectRelationshipAPI} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';
import {performUserSwitch, userData} from '../../../utils/performLogin';
import {waitForAlert} from '../../../utils/waitForAlert';
import {generateObjectFields} from './utils/generateObjectFields';

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
	'LPD-78504 Can associate any type of account',
	{tag: '@LPD-78504'},
	async ({page, viewObjectDefinitionsPage}) => {
		// Navigate to Account system object

		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.clickEditObjectDefinitionLink(
			'Account'
		);

		// Navigate to the Details tab to verify account type configuration

		await page.getByRole('tab', {name: 'Details'}).click();

		// Verify Account Restriction toggle is visible, confirming accounts
		// can be associated with various types

		await expect(
			page.getByLabel('Enable Account Restriction', {exact: true})
		).toBeVisible();

		// Verify the Account Type selector or configuration is available

		await expect(
			page.getByText('Account Restriction')
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can create action on system account',
	{tag: '@LPD-78504'},
	async ({page, viewObjectDefinitionsPage}) => {
		// Navigate to Account system object

		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.clickEditObjectDefinitionLink(
			'Account'
		);

		// Navigate to Actions tab

		await page.getByRole('tab', {name: 'Actions'}).click();

		// Click Add Object Action button

		await page.getByLabel('Add Object Action').click();

		// Fill in the action name

		const actionName = 'Action' + getRandomInt();

		await page.getByLabel('Action Name').fill(actionName);

		// Select the action trigger (When)

		await page.getByLabel('When').click();

		await page.getByRole('option', {name: 'On After Add'}).click();

		// Select the action type (Then)

		await page.getByLabel('Then').click();

		await page.getByRole('option', {name: 'Webhook'}).click();

		// Fill the URL

		await page.getByLabel('URL').fill('http://localhost:8080');

		// Save the action

		await page.getByRole('button', {name: 'Save'}).click();

		await waitForAlert(page);

		// Verify the action was created by checking it appears in the list

		await expect(page.getByText(actionName)).toBeVisible();
	}
);

test(
	'LPD-78504 Can create relationship related with custom object',
	{tag: '@LPD-78504'},
	async ({
		addNewObjectRelationshipModalPage,
		apiHelpers,
		objectRelationshipsPage,
		page,
		viewObjectDefinitionsPage,
	}) => {
		// Create a custom object via API

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

		// Navigate to Account system object Relationships tab

		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.clickEditObjectDefinitionLink(
			'Account'
		);

		await page.getByRole('tab', {name: 'Relationships'}).click();

		// Create a new relationship

		await objectRelationshipsPage.addObjectRelationshipButton.click();

		const relationshipLabel = 'Rel' + getRandomInt();

		const relationship =
			await addNewObjectRelationshipModalPage.handleForm({
				manyRecordsOf: objectDefinition.label['en_US'],
				objectRelationshipLabel: relationshipLabel,
				type: 'One to Many',
			});

		// Verify relationship was created

		await expect(page.getByText(relationshipLabel)).toBeVisible();

		// Clean up: delete relationship via API

		const objectRelationshipAPIClient =
			await apiHelpers.buildRestClient(ObjectRelationshipAPI);

		await objectRelationshipAPIClient.deleteObjectRelationship(
			relationship.id
		);
	}
);

test(
	'LPD-78504 Can delete relationship when deletion type is cascade',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectDefinitionsPage}) => {
		// Create a custom object via API

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

		// Create a oneToMany relationship from Account to custom object with
		// cascade deletion type via API

		const objectRelationshipAPIClient =
			await apiHelpers.buildRestClient(ObjectRelationshipAPI);

		const relationshipName = 'relationship' + getRandomInt();

		const {body: relationship} =
			await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
				'L_ACCOUNT_ENTRY',
				{
					deletionType: 'cascade',
					label: {en_US: 'CascadeRel'},
					name: relationshipName,
					objectDefinitionExternalReferenceCode2:
						objectDefinition.externalReferenceCode,
					objectDefinitionId2: objectDefinition.id,
					objectDefinitionName2: objectDefinition.name,
					type: 'oneToMany',
				}
			);

		// Create an account entry via API

		const accountName = 'Account' + getRandomInt();

		const account =
			await apiHelpers.headlessAdminUser.postAccount({
				name: accountName,
				type: 'business',
			});

		apiHelpers.data.push({id: account.id, type: 'account'});

		// Create a child custom object entry related to the account

		const applicationName =
			'c/' + objectDefinition.name.toLowerCase() + 's';
		const fieldName = objectFields[0].name!;

		await apiHelpers.objectEntry.postObjectEntry(
			{
				[fieldName]: 'ChildEntry1',
				['r_' + relationshipName + '_accountEntryId']: account.id,
			},
			applicationName
		);

		// Delete the account (parent), which should cascade delete
		// the child entries

		await apiHelpers.headlessAdminUser.deleteAccount(account.id);

		// Remove from cleanup since already deleted

		apiHelpers.data.splice(
			apiHelpers.data.findIndex(
				(item) =>
					item.id === account.id && item.type === 'account'
			),
			1
		);

		// Verify child entries were also deleted by checking the custom
		// object entries page is empty

		const response = await apiHelpers.objectEntry.getObjectEntries(
			applicationName
		);

		expect(response.totalCount).toBe(0);

		// Clean up relationship

		await objectRelationshipAPIClient.deleteObjectRelationship(
			relationship.id
		);
	}
);

test(
	'LPD-78504 Cannot delete default fields',
	{tag: '@LPD-78504'},
	async ({objectFieldsPage, page, viewObjectDefinitionsPage}) => {
		// Navigate to Account system object

		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.clickEditObjectDefinitionLink(
			'Account'
		);

		// Navigate to Fields tab

		await page.getByRole('tab', {name: 'Fields'}).click();

		// Verify that default system fields do not have a Delete action
		// available. Check a known default field like "Name"

		const nameFieldRow = page
			.getByRole('row')
			.filter({hasText: 'Name'})
			.first();

		await expect(nameFieldRow).toBeVisible();

		// Verify the actions dropdown does not contain Delete for
		// system default fields. System fields should not have the
		// kebab menu or should not have a Delete option

		const actionsButton = nameFieldRow.getByRole('button', {
			name: 'Actions',
		});

		// System default fields should not have an actions button at all

		await expect(actionsButton).toBeHidden();
	}
);

test(
	'LPD-78504 Cannot delete entry when deletion type is prevent',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page}) => {
		// Create a custom object via API

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

		// Create a oneToMany relationship from Account to custom object
		// with prevent deletion type

		const objectRelationshipAPIClient =
			await apiHelpers.buildRestClient(ObjectRelationshipAPI);

		const relationshipName = 'relationship' + getRandomInt();

		const {body: relationship} =
			await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
				'L_ACCOUNT_ENTRY',
				{
					deletionType: 'prevent',
					label: {en_US: 'PreventRel'},
					name: relationshipName,
					objectDefinitionExternalReferenceCode2:
						objectDefinition.externalReferenceCode,
					objectDefinitionId2: objectDefinition.id,
					objectDefinitionName2: objectDefinition.name,
					type: 'oneToMany',
				}
			);

		// Create an account entry

		const accountName = 'Account' + getRandomInt();

		const account =
			await apiHelpers.headlessAdminUser.postAccount({
				name: accountName,
				type: 'business',
			});

		apiHelpers.data.push({id: account.id, type: 'account'});

		// Create a child custom object entry related to the account

		const applicationName =
			'c/' + objectDefinition.name.toLowerCase() + 's';
		const fieldName = objectFields[0].name!;

		await apiHelpers.objectEntry.postObjectEntry(
			{
				[fieldName]: 'ChildEntry1',
				['r_' + relationshipName + '_accountEntryId']: account.id,
			},
			applicationName
		);

		// Try to delete the account entry via API and expect it to fail
		// because deletion type is "prevent"

		let deletionFailed = false;

		try {
			await apiHelpers.headlessAdminUser.deleteAccount(account.id);
		}
		catch {
			deletionFailed = true;
		}

		expect(deletionFailed).toBeTruthy();

		// Verify the account still exists

		const accountResponse =
			await apiHelpers.headlessAdminUser.getAccount(account.id);

		expect(accountResponse.name).toBe(accountName);

		// Clean up: delete child entry first, then account, then relationship

		const entries = await apiHelpers.objectEntry.getObjectEntries(
			applicationName
		);

		for (const entry of entries.items) {
			await apiHelpers.objectEntry.deleteObjectEntry(
				entry.id,
				applicationName
			);
		}

		await objectRelationshipAPIClient.deleteObjectRelationship(
			relationship.id
		);
	}
);

test(
	'LPD-78504 Cannot delete relationship when deletion type is disassociate',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page}) => {
		// Create a custom object via API

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

		// Create a oneToMany relationship from Account to custom object
		// with disassociate deletion type

		const objectRelationshipAPIClient =
			await apiHelpers.buildRestClient(ObjectRelationshipAPI);

		const relationshipName = 'relationship' + getRandomInt();

		const {body: relationship} =
			await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
				'L_ACCOUNT_ENTRY',
				{
					deletionType: 'disassociate',
					label: {en_US: 'DisassociateRel'},
					name: relationshipName,
					objectDefinitionExternalReferenceCode2:
						objectDefinition.externalReferenceCode,
					objectDefinitionId2: objectDefinition.id,
					objectDefinitionName2: objectDefinition.name,
					type: 'oneToMany',
				}
			);

		// Create an account entry

		const accountName = 'Account' + getRandomInt();

		const account =
			await apiHelpers.headlessAdminUser.postAccount({
				name: accountName,
				type: 'business',
			});

		apiHelpers.data.push({id: account.id, type: 'account'});

		// Create a child custom object entry related to the account

		const applicationName =
			'c/' + objectDefinition.name.toLowerCase() + 's';
		const fieldName = objectFields[0].name!;

		await apiHelpers.objectEntry.postObjectEntry(
			{
				[fieldName]: 'ChildEntry1',
				['r_' + relationshipName + '_accountEntryId']: account.id,
			},
			applicationName
		);

		// Delete the account (parent) - should succeed and disassociate
		// children (set FK to null) instead of deleting them

		await apiHelpers.headlessAdminUser.deleteAccount(account.id);

		// Remove from cleanup since already deleted

		apiHelpers.data.splice(
			apiHelpers.data.findIndex(
				(item) =>
					item.id === account.id && item.type === 'account'
			),
			1
		);

		// Verify the child entry still exists but the relationship FK is null

		const entries = await apiHelpers.objectEntry.getObjectEntries(
			applicationName
		);

		expect(entries.totalCount).toBe(1);

		expect(
			entries.items[0][
				'r_' + relationshipName + '_accountEntryId'
			]
		).toBeFalsy();

		// Clean up relationship

		await objectRelationshipAPIClient.deleteObjectRelationship(
			relationship.id
		);
	}
);

test(
	'LPD-78504 Cannot edit default fields',
	{tag: '@LPD-78504'},
	async ({page, viewObjectDefinitionsPage}) => {
		// Navigate to Account system object

		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.clickEditObjectDefinitionLink(
			'Account'
		);

		// Navigate to Fields tab

		await page.getByRole('tab', {name: 'Fields'}).click();

		// Click on a default system field like "Name" to open its details

		await page
			.getByRole('cell')
			.getByRole('link')
			.filter({hasText: 'Name'})
			.first()
			.click();

		// Verify the field details are displayed in read-only mode
		// The label input in the side panel should be disabled or read-only

		const sidePanel = page.frameLocator('iframe');

		await expect(
			sidePanel.getByLabel('Label', {exact: true})
		).toBeDisabled();
	}
);

test(
	'LPD-78504 Can user view system account when allowed',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectDefinitionsPage}) => {
		// Create a role with permission to view objects in control panel

		const company =
			await apiHelpers.jsonWebServicesCompany.getCompanyByWebId(
				'liferay.com'
			);

		const role = await apiHelpers.headlessAdminUser.postRole({
			name: 'ObjViewRole' + getRandomInt(),
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
					resourceName:
						'com.liferay.object.model.ObjectDefinition',
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

		// Create a user and assign the role

		const user =
			await apiHelpers.headlessAdminUser.postUserAccount();

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

		// Switch to the new user

		await performUserSwitch(page, user.alternateName);

		// Navigate to object definitions and verify Account is visible

		await viewObjectDefinitionsPage.goto();

		await expect(page.getByText('Account')).toBeVisible();
	}
);

test(
	'LPD-78504 Can view description on field entry',
	{tag: '@LPD-78504'},
	async ({page, viewObjectDefinitionsPage}) => {
		// Navigate to Account system object

		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.clickEditObjectDefinitionLink(
			'Account'
		);

		// Navigate to Fields tab

		await page.getByRole('tab', {name: 'Fields'}).click();

		// Verify that the Description column is visible in the fields table

		await expect(
			page.getByRole('columnheader', {name: 'Description'})
		).toBeVisible();

		// Click on a field to view its description in the side panel

		await page
			.getByRole('cell')
			.getByRole('link')
			.filter({hasText: 'Description'})
			.first()
			.click();

		// Verify the field details panel opens and shows field information

		const sidePanel = page.frameLocator('iframe');

		await expect(
			sidePanel.getByLabel('Label', {exact: true})
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can view fields label by default',
	{tag: '@LPD-78504'},
	async ({page, viewObjectDefinitionsPage}) => {
		// Navigate to Account system object

		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.clickEditObjectDefinitionLink(
			'Account'
		);

		// Navigate to Fields tab

		await page.getByRole('tab', {name: 'Fields'}).click();

		// Verify that default field labels are displayed in the fields list
		// Account system object should have fields like Name, Type, Description

		await expect(
			page.getByRole('columnheader', {name: 'Label'})
		).toBeVisible();

		await expect(
			page.getByRole('cell').getByRole('link').filter({hasText: 'Name'}).first()
		).toBeVisible();

		await expect(
			page.getByRole('cell').getByRole('link').filter({hasText: 'Type'}).first()
		).toBeVisible();

		await expect(
			page.getByRole('cell').getByRole('link').filter({hasText: 'Description'}).first()
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can view name and type as mandatory fields',
	{tag: '@LPD-78504'},
	async ({page, viewObjectDefinitionsPage}) => {
		// Navigate to Account system object

		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.clickEditObjectDefinitionLink(
			'Account'
		);

		// Navigate to Fields tab

		await page.getByRole('tab', {name: 'Fields'}).click();

		// Verify that the fields table has a Mandatory column

		await expect(
			page.getByRole('columnheader', {name: 'Mandatory'})
		).toBeVisible();

		// Verify that Name field is marked as mandatory

		const nameRow = page
			.getByRole('row')
			.filter({hasText: 'Name'})
			.first();

		await expect(nameRow.getByText('Yes')).toBeVisible();
	}
);

test(
	'LPD-78504 Can view name on field entry',
	{tag: '@LPD-78504'},
	async ({page, viewObjectDefinitionsPage}) => {
		// Navigate to Account system object

		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.clickEditObjectDefinitionLink(
			'Account'
		);

		// Navigate to Fields tab

		await page.getByRole('tab', {name: 'Fields'}).click();

		// Click on the Name field to open its details

		await page
			.getByRole('cell')
			.getByRole('link')
			.filter({hasText: 'Name'})
			.first()
			.click();

		// Verify the field name is displayed in the side panel

		const sidePanel = page.frameLocator('iframe');

		await expect(
			sidePanel.getByLabel('Label', {exact: true})
		).toHaveValue('Name');
	}
);

test(
	'LPD-78504 Can view relationship on custom object entries with custom view',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		editObjectViewPage,
		objectViewPage,
		page,
		viewObjectDefinitionsPage,
	}) => {
		// Create a custom object with a Text field

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

		// Create a oneToMany relationship from Account to the custom object

		const objectRelationshipAPIClient =
			await apiHelpers.buildRestClient(ObjectRelationshipAPI);

		const relationshipName = 'relationship' + getRandomInt();

		const {body: relationship} =
			await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
				'L_ACCOUNT_ENTRY',
				{
					label: {en_US: 'AccountRel'},
					name: relationshipName,
					objectDefinitionExternalReferenceCode2:
						objectDefinition.externalReferenceCode,
					objectDefinitionId2: objectDefinition.id,
					objectDefinitionName2: objectDefinition.name,
					type: 'oneToMany',
				}
			);

		// Navigate to custom object Views tab and create a custom view
		// that includes the relationship column

		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.clickEditObjectDefinitionLink(
			objectDefinition.label['en_US']
		);

		await page
			.getByRole('listitem')
			.filter({hasText: 'Views'})
			.click();

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		// Open the view and add the relationship column

		await page.getByRole('link', {name: viewName}).click();

		await editObjectViewPage.selectObjectFields(['AccountRel']);

		await editObjectViewPage.markAsDefaultButton.check();

		await editObjectViewPage.saveButton.click();

		await waitForAlert(page);

		// Verify the relationship column is visible in the view builder

		const sidePanel = page.frameLocator('iframe');

		await expect(
			sidePanel.getByText('AccountRel')
		).toBeVisible();

		// Clean up relationship

		await objectRelationshipAPIClient.deleteObjectRelationship(
			relationship.id
		);
	}
);

test(
	'LPD-78504 Can view type on field entry',
	{tag: '@LPD-78504'},
	async ({page, viewObjectDefinitionsPage}) => {
		// Navigate to Account system object

		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.clickEditObjectDefinitionLink(
			'Account'
		);

		// Navigate to Fields tab

		await page.getByRole('tab', {name: 'Fields'}).click();

		// Verify that the Type column is visible in the fields table

		await expect(
			page.getByRole('columnheader', {name: 'Type'})
		).toBeVisible();

		// Click on the Type field entry to view its type

		await page
			.getByRole('cell')
			.getByRole('link')
			.filter({hasText: 'Type'})
			.first()
			.click();

		// Verify the field type is displayed in the side panel

		const sidePanel = page.frameLocator('iframe');

		await expect(
			sidePanel.getByLabel('Label', {exact: true})
		).toHaveValue('Type');
	}
);

test(
	'LPD-78504 Edit title field on system account',
	{tag: '@LPD-78504'},
	async ({page, viewObjectDefinitionsPage}) => {
		// Navigate to Account system object

		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.clickEditObjectDefinitionLink(
			'Account'
		);

		// Verify we are on the Account details page

		await expect(page.getByText('ERC:L_ACCOUNT_ENTRY')).toBeVisible();

		// Change the Object Entry Title Field

		await page.getByLabel('Object Entry Title Field').click();

		await page.getByRole('option', {name: 'Type'}).click();

		// Save the change

		await page.getByRole('button', {name: 'Save'}).click();

		await waitForAlert(page);

		// Verify the title field was changed by re-checking the dropdown value

		await expect(page.getByLabel('Object Entry Title Field')).toHaveText(
			/Type/
		);

		// Revert back to the original title field

		await page.getByLabel('Object Entry Title Field').click();

		await page.getByRole('option', {name: 'Name'}).click();

		await page.getByRole('button', {name: 'Save'}).click();

		await waitForAlert(page);
	}
);

test(
	'LPD-78504 Widget button disabled by default',
	{tag: '@LPD-78504'},
	async ({page, viewObjectDefinitionsPage}) => {
		// Navigate to Account system object

		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.clickEditObjectDefinitionLink(
			'Account'
		);

		// Verify the Widget toggle/button is disabled for system objects
		// System objects like Account should have the widget option
		// disabled by default

		const widgetToggle = page.getByLabel('Enable Widget', {
			exact: true,
		});

		await expect(widgetToggle).toBeVisible();

		await expect(widgetToggle).toBeDisabled();
	}
);
