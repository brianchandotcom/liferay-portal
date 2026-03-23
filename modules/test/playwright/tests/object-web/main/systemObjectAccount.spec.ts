/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ObjectDefinitionAPI,
	ObjectRelationshipAPI,
} from '@liferay/object-admin-rest-client-js';
import {Page, expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';
import {performUserSwitch, userData} from '../../../utils/performLogin';
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

async function navigateToObjectDefinitionEditPage(
	page: Page,
	objectDefinitionId: number
) {
	const portletId =
		'com_liferay_object_web_internal_object_definitions_portlet_ObjectDefinitionsPortlet';

	await page.goto(
		`/group/guest/~/control_panel/manage?p_p_id=${portletId}&p_p_lifecycle=0&p_p_state=maximized&p_p_mode=view&_${portletId}_mvcRenderCommandName=%2Fobject_definitions%2Fedit_object_definition&_${portletId}_objectDefinitionId=${objectDefinitionId}`,
		{waitUntil: 'networkidle'}
	);
}

async function getAccountDefinitionId(apiHelpers: any) {
	const objectDefinitionAPIClient =
		await apiHelpers.buildRestClient(ObjectDefinitionAPI);

	const {body: accountDefinitionsPage} =
		await objectDefinitionAPIClient.getObjectDefinitionsPage(
			undefined,
			"name eq 'AccountEntry'",
			1,
			1
		);

	return accountDefinitionsPage.items[0].id;
}

test(
	'LPD-78504 Can associate any type of account',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinitionAPIClient =
			await apiHelpers.buildRestClient(ObjectDefinitionAPI);
		const objectRelationshipAPIClient =
			await apiHelpers.buildRestClient(ObjectRelationshipAPI);

		// Get Account system object definition

		const {body: accountDefinitionsPage} =
			await objectDefinitionAPIClient.getObjectDefinitionsPage(
				undefined,
				"name eq 'AccountEntry'",
				1,
				1
			);

		const accountDefinition = accountDefinitionsPage.items[0];

		// Create accounts of each type (business, person, guest)

		const businessAccount =
			await apiHelpers.headlessAdminUser.postAccount({
				name: 'Business' + getRandomInt(),
				type: 'business',
			});

		const personAccount =
			await apiHelpers.headlessAdminUser.postAccount({
				name: 'Person' + getRandomInt(),
				type: 'person',
			});

		const guestAccount =
			await apiHelpers.headlessAdminUser.postAccount({
				name: 'Guest' + getRandomInt(),
				type: 'guest',
			});

		// Create a custom object with a text field

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

		// Create oneToMany relationship from AccountEntry to CustomObject

		const relationshipName = 'relationship' + getRandomInt();

		const {body: relationship} =
			await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
				'L_ACCOUNT',
				{
					label: {en_US: 'Relationship'},
					name: relationshipName,
					objectDefinitionExternalReferenceCode2:
						objectDefinition.externalReferenceCode,
					objectDefinitionId2: objectDefinition.id,
					objectDefinitionName2: objectDefinition.name,
					type: 'oneToMany',
				}
			);

		// Set Account title field to "Type"

		await objectDefinitionAPIClient.patchObjectDefinition(
			accountDefinition.id,
			{titleObjectFieldName: 'type'}
		);

		// Create custom object entries linked to each account type via API

		const applicationName =
			'c/' + objectDefinition.name.toLowerCase() + 's';
		const fieldName = objectFields[0].name!;

		for (const account of [
			businessAccount,
			personAccount,
			guestAccount,
		]) {
			await apiHelpers.objectEntry.postObjectEntry(
				{
					[fieldName]: 'Entry' + getRandomInt(),
					['r_' + relationshipName + '_accountEntryId']:
						account.id,
				},
				applicationName
			);
		}

		// Navigate to entries page and verify all 3 type labels are visible

		await viewObjectEntriesPage.goto(objectDefinition.className);

		for (const accountType of ['business', 'person', 'guest']) {
			await expect(
				page.locator('table').getByText(accountType)
			).toBeVisible();
		}

		// Clean up: revert Account title field to "Name"

		await objectDefinitionAPIClient.patchObjectDefinition(
			accountDefinition.id,
			{titleObjectFieldName: 'name'}
		);

		await objectRelationshipAPIClient.deleteObjectRelationship(
			relationship.id
		);
	}
);

test(
	'LPD-78504 Can create action on system account',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page}) => {
		const objectDefinitionAPIClient =
			await apiHelpers.buildRestClient(ObjectDefinitionAPI);

		// Get Account system object definition ID

		const {body: accountDefinitionsPage} =
			await objectDefinitionAPIClient.getObjectDefinitionsPage(
				undefined,
				"name eq 'AccountEntry'",
				1,
				1
			);

		const accountId = accountDefinitionsPage.items[0].id;

		// Navigate to Object Admin and then to Account edit page

		const portletId =
			'com_liferay_object_web_internal_object_definitions_portlet_ObjectDefinitionsPortlet';

		const editUrl =
			`/group/guest/~/control_panel/manage?p_p_id=${portletId}&p_p_lifecycle=0&p_p_state=maximized&p_p_mode=view&_${portletId}_mvcRenderCommandName=%2Fobject_definitions%2Fedit_object_definition&_${portletId}_objectDefinitionId=${accountId}`;

		await page.goto(editUrl, {waitUntil: 'networkidle'});

		// Wait for the Account edit page to fully load

		await expect(page.getByText('ERC:L_ACCOUNT')).toBeVisible();

		// Navigate to Actions tab

		await page
			.getByRole('listitem')
			.filter({hasText: 'Actions'})
			.click();

		// Click Add Object Action button

		await page.getByLabel('Add Object Action').click();

		// Side panel is inside an iframe

		const sidePanel = page.frameLocator('iframe');

		// Fill in the action label (name auto-generates)

		const actionLabel = 'Action' + getRandomInt();

		await sidePanel
			.getByPlaceholder('Text to translate...')
			.fill(actionLabel);

		// Switch to Action Builder tab to configure When/Then

		await sidePanel
			.getByRole('tab', {name: 'Action Builder'})
			.click();

		// Select the action trigger (When): click the "Choose a Trigger"
		// dropdown

		await sidePanel.getByText('Choose a Trigger').click();

		await sidePanel
			.getByRole('option', {name: 'On After Add'})
			.click();

		// Select the action type (Then): click the "Choose an Action"
		// dropdown

		await sidePanel.getByText('Choose an Action').click();

		await sidePanel
			.getByRole('option', {name: 'Webhook'})
			.click();

		// Fill the URL field (label includes mandatory marker)

		const urlInput = sidePanel.locator(
			'input[type="url"], input[id*="url" i], input[name*="url" i]'
		);

		await urlInput.scrollIntoViewIfNeeded();

		await urlInput.fill('http://localhost:8080');

		// Save the action

		await sidePanel
			.getByRole('button', {name: 'Save'})
			.first()
			.click();

		// Verify the action was created by checking it appears in the
		// actions list (panel closes and shows the list)

		await expect(
			page.getByRole('link', {name: actionLabel})
		).toBeVisible();
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

		const accountId = await getAccountDefinitionId(apiHelpers);

		await navigateToObjectDefinitionEditPage(page, accountId);

		await page
			.getByRole('listitem')
			.filter({hasText: 'Relationships'})
			.click();

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

		await expect(
			page.getByRole('link', {name: relationshipLabel})
		).toBeVisible();

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
				'L_ACCOUNT',
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

		const response =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
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
	async ({apiHelpers, page}) => {
		// Navigate to Account system object

		const accountId = await getAccountDefinitionId(apiHelpers);

		await navigateToObjectDefinitionEditPage(page, accountId);

		// Navigate to Fields tab

		await page
			.getByRole('listitem')
			.filter({hasText: 'Fields'})
			.click();

		// Verify that default system fields (Name, Description, Type)
		// do not have an Actions button (no delete option)

		for (const fieldLabel of ['Name', 'Description', 'Type']) {
			const fieldRow = page
				.getByRole('row')
				.filter({hasText: fieldLabel})
				.first();

			await expect(fieldRow).toBeVisible();

			const actionsButton = fieldRow.getByRole('button', {
				name: 'Actions',
			});

			await expect(actionsButton).toBeHidden();
		}
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
				'L_ACCOUNT',
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

		const deleteResponse =
			await apiHelpers.headlessAdminUser.deleteAccount(account.id);

		expect(deleteResponse.ok()).toBeFalsy();

		// Verify the account still exists

		const accountResponse =
			await apiHelpers.headlessAdminUser.getAccountByName(
				accountName
			);

		expect(accountResponse.name).toBe(accountName);

		// Clean up: delete child entry first, then account, then relationship

		const entries =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				applicationName
			);

		for (const entry of entries.items) {
			await apiHelpers.objectEntry.deleteObjectEntry(
				applicationName,
				entry.id
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
				'L_ACCOUNT',
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

		const entries =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
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
	async ({apiHelpers, page}) => {
		// Navigate to Account system object

		const accountId = await getAccountDefinitionId(apiHelpers);

		await navigateToObjectDefinitionEditPage(page, accountId);

		// Navigate to Fields tab

		await page
			.getByRole('listitem')
			.filter({hasText: 'Fields'})
			.click();

		// Verify that default system fields (Name, Description, Type)
		// have disabled label inputs in their details panel
		// (field side panel is NOT inside an iframe)

		const sidePanel = page.frameLocator('iframe');

		for (const fieldLabel of ['Name', 'Description', 'Type']) {
			await page
				.locator('table tbody')
				.getByRole('link')
				.filter({hasText: fieldLabel})
				.first()
				.click();

			// The field details panel is inside an iframe
			// System default fields have non-editable Field Name input

			await expect(
				sidePanel.locator('input[name="name"]')
			).not.toBeEditable();
		}
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

		await expect(
			page.getByRole('link', {exact: true, name: 'Account'})
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can view description on field entry',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinitionAPIClient =
			await apiHelpers.buildRestClient(ObjectDefinitionAPI);
		const objectRelationshipAPIClient =
			await apiHelpers.buildRestClient(ObjectRelationshipAPI);

		// Get Account system object definition

		const {body: accountDefinitionsPage} =
			await objectDefinitionAPIClient.getObjectDefinitionsPage(
				undefined,
				"name eq 'AccountEntry'",
				1,
				1
			);

		const accountDefinition = accountDefinitionsPage.items[0];

		// Create an account entry with a description

		const accountDescription = 'Description' + getRandomInt();

		await apiHelpers.headlessAdminUser.postAccount({
			description: accountDescription,
			name: 'Account' + getRandomInt(),
			type: 'business',
		});

		// Create a custom object with a text field

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

		// Create oneToMany relationship from AccountEntry to CustomObject

		const relationshipName = 'relationship' + getRandomInt();

		const {body: relationship} =
			await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
				'L_ACCOUNT',
				{
					label: {en_US: 'Relationship'},
					name: relationshipName,
					objectDefinitionExternalReferenceCode2:
						objectDefinition.externalReferenceCode,
					objectDefinitionId2: objectDefinition.id,
					objectDefinitionName2: objectDefinition.name,
					type: 'oneToMany',
				}
			);

		// Set Account title field to "Description"

		await objectDefinitionAPIClient.patchObjectDefinition(
			accountDefinition.id,
			{titleObjectFieldName: 'description'}
		);

		// Navigate to custom object entries and add an entry selecting
		// the account by description

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await viewObjectEntriesPage.selectDropdownItemWithSearch(
			accountDescription
		);

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await expect(
			viewObjectEntriesPage.successMessage
		).toBeVisible();

		// Go back and verify the description appears in entries list

		await viewObjectEntriesPage.backButton.click();

		await expect(
			page.locator('table').getByText(accountDescription)
		).toBeVisible();

		// Clean up: revert Account title field to "Name"

		await objectDefinitionAPIClient.patchObjectDefinition(
			accountDefinition.id,
			{titleObjectFieldName: 'name'}
		);

		await objectRelationshipAPIClient.deleteObjectRelationship(
			relationship.id
		);
	}
);

test(
	'LPD-78504 Can view fields label by default',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page}) => {
		// Navigate to Account system object

		const accountId = await getAccountDefinitionId(apiHelpers);

		await navigateToObjectDefinitionEditPage(page, accountId);

		// Navigate to Fields tab

		await page
			.getByRole('listitem')
			.filter({hasText: 'Fields'})
			.click();

		// Verify that default fields (Name, Description, Type) exist
		// and each has type "Text"
		// Scope to tbody to avoid matching the "Type" column header

		const tbody = page.locator('table tbody');

		for (const fieldLabel of ['Name', 'Description', 'Type']) {
			const fieldRow = tbody
				.getByRole('row')
				.filter({hasText: fieldLabel})
				.first();

			await expect(fieldRow).toBeVisible();

			await expect(fieldRow.getByText('Text')).toBeVisible();
		}
	}
);

test(
	'LPD-78504 Can view name and type as mandatory fields',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page}) => {
		// Navigate to Account system object

		const accountId = await getAccountDefinitionId(apiHelpers);

		await navigateToObjectDefinitionEditPage(page, accountId);

		// Navigate to Fields tab

		await page
			.getByRole('listitem')
			.filter({hasText: 'Fields'})
			.click();

		// Verify that Name and Type fields are marked as mandatory
		// Scope to tbody to avoid matching the "Type" column header

		const tbody = page.locator('table tbody');

		for (const fieldLabel of ['Name', 'Type']) {
			const fieldRow = tbody
				.getByRole('row')
				.filter({hasText: fieldLabel})
				.first();

			await expect(fieldRow.getByText('Yes')).toBeVisible();
		}
	}
);

test(
	'LPD-78504 Can view name on field entry',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectRelationshipAPIClient =
			await apiHelpers.buildRestClient(ObjectRelationshipAPI);

		// Create an account entry

		const accountName = 'TestAccount' + getRandomInt();

		await apiHelpers.headlessAdminUser.postAccount({
			name: accountName,
			type: 'business',
		});

		// Create a custom object with a text field

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

		// Create oneToMany relationship from AccountEntry to CustomObject

		const relationshipName = 'relationship' + getRandomInt();

		const {body: relationship} =
			await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
				'L_ACCOUNT',
				{
					label: {en_US: 'Relationship'},
					name: relationshipName,
					objectDefinitionExternalReferenceCode2:
						objectDefinition.externalReferenceCode,
					objectDefinitionId2: objectDefinition.id,
					objectDefinitionName2: objectDefinition.name,
					type: 'oneToMany',
				}
			);

		// Navigate to custom object entries and add an entry selecting
		// the account by name (default title field)

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await viewObjectEntriesPage.selectDropdownItemWithSearch(
			accountName
		);

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await expect(
			viewObjectEntriesPage.successMessage
		).toBeVisible();

		// Go back and verify the account name appears in entries list

		await viewObjectEntriesPage.backButton.click();

		await expect(
			page.locator('table td').getByText(accountName).first()
		).toBeVisible();

		// Clean up relationship

		await objectRelationshipAPIClient.deleteObjectRelationship(
			relationship.id
		);
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
		viewObjectEntriesPage,
	}) => {
		// Ensure Account title field is "name" (may have been changed
		// by a prior test)

		const objectDefinitionAPIClient =
			await apiHelpers.buildRestClient(ObjectDefinitionAPI);

		const accountId = await getAccountDefinitionId(apiHelpers);

		await objectDefinitionAPIClient.patchObjectDefinition(accountId, {
			titleObjectFieldName: 'name',
		});

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
				'L_ACCOUNT',
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

		// Create an account entry and a custom object entry linked to it

		const accountName = 'Account' + getRandomInt();

		const account = await apiHelpers.headlessAdminUser.postAccount({
			name: accountName,
			type: 'business',
		});

		const applicationName =
			'c/' + objectDefinition.name.toLowerCase() + 's';
		const fieldName = objectFields[0].name!;

		await apiHelpers.objectEntry.postObjectEntry(
			{
				[fieldName]: 'EntryValue',
				['r_' + relationshipName + '_accountEntryId']: account.id,
			},
			applicationName
		);

		// Navigate to custom object Views tab and create a custom view
		// that includes the relationship column

		await navigateToObjectDefinitionEditPage(
			page,
			objectDefinition.id
		);

		await page
			.getByRole('listitem')
			.filter({hasText: 'Views'})
			.click();

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		// Wait for the view to appear in the list

		await expect(
			page.getByRole('link', {name: viewName})
		).toBeVisible();

		// Open the view and add the relationship column

		await page.getByRole('link', {name: viewName}).click();

		await editObjectViewPage.selectObjectFields(['AccountRel']);

		// Switch back to Basic Info tab to access Mark as Default

		await page
			.frameLocator('iframe')
			.getByRole('tab', {name: 'Basic Info'})
			.click();

		await editObjectViewPage.markAsDefaultButton.check();

		await editObjectViewPage.saveButton.click();

		await page.waitForLoadState('networkidle');

		// Navigate to entries page and verify the account name appears
		// in the relationship column

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(
			page.locator('table td').getByText(accountName).first()
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
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectDefinitionAPIClient =
			await apiHelpers.buildRestClient(ObjectDefinitionAPI);
		const objectRelationshipAPIClient =
			await apiHelpers.buildRestClient(ObjectRelationshipAPI);

		// Get Account system object definition

		const {body: accountDefinitionsPage} =
			await objectDefinitionAPIClient.getObjectDefinitionsPage(
				undefined,
				"name eq 'AccountEntry'",
				1,
				1
			);

		const accountDefinition = accountDefinitionsPage.items[0];

		// Create a business account entry

		const account = await apiHelpers.headlessAdminUser.postAccount({
			name: 'Account' + getRandomInt(),
			type: 'business',
		});

		// Create a custom object with a text field

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

		// Create oneToMany relationship from AccountEntry to CustomObject

		const relationshipName = 'relationship' + getRandomInt();

		const {body: relationship} =
			await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
				'L_ACCOUNT',
				{
					label: {en_US: 'Relationship'},
					name: relationshipName,
					objectDefinitionExternalReferenceCode2:
						objectDefinition.externalReferenceCode,
					objectDefinitionId2: objectDefinition.id,
					objectDefinitionName2: objectDefinition.name,
					type: 'oneToMany',
				}
			);

		// Set Account title field to "Type"

		await objectDefinitionAPIClient.patchObjectDefinition(
			accountDefinition.id,
			{titleObjectFieldName: 'type'}
		);

		// Create entry via API with relationship to account

		const applicationName =
			'c/' + objectDefinition.name.toLowerCase() + 's';
		const fieldName = objectFields[0].name!;

		await apiHelpers.objectEntry.postObjectEntry(
			{
				[fieldName]: 'Entry' + getRandomInt(),
				['r_' + relationshipName + '_accountEntryId']: account.id,
			},
			applicationName
		);

		// Navigate to entries page and verify the type value appears

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(
			page.locator('table').getByText('business')
		).toBeVisible();

		// Clean up: revert Account title field to "Name"

		await objectDefinitionAPIClient.patchObjectDefinition(
			accountDefinition.id,
			{titleObjectFieldName: 'name'}
		);

		await objectRelationshipAPIClient.deleteObjectRelationship(
			relationship.id
		);
	}
);

test(
	'LPD-78504 Edit title field on system account',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page}) => {
		// Navigate to Account system object

		const accountId = await getAccountDefinitionId(apiHelpers);

		await navigateToObjectDefinitionEditPage(page, accountId);

		// Verify we are on the Account details page

		await expect(page.getByText('ERC:L_ACCOUNT')).toBeVisible();

		// Change the Entry Title Field (Poshi label: "Entry Title Field")
		// The dropdown is a combobox button inside a form-group

		const titleFieldGroup = page
			.locator('.form-group')
			.filter({hasText: 'Entry Title Field'});

		await titleFieldGroup.scrollIntoViewIfNeeded();

		await titleFieldGroup.getByRole('combobox').click();

		await page.getByRole('option', {name: 'Type'}).click();

		// Save the change

		await page.getByRole('button', {name: 'Save'}).click();

		await page.waitForLoadState('networkidle');

		// Verify the title field was changed

		await expect(titleFieldGroup.getByRole('combobox')).toHaveText(
			/Type/
		);

		// Revert back to the original title field

		await titleFieldGroup.getByRole('combobox').click();

		await page.getByRole('option', {name: 'Name'}).click();

		await page.getByRole('button', {name: 'Save'}).click();

		await page.waitForLoadState('networkidle');
	}
);

test(
	'LPD-78504 Widget button disabled by default',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page}) => {
		// Navigate to Account system object

		const accountId = await getAccountDefinitionId(apiHelpers);

		await navigateToObjectDefinitionEditPage(page, accountId);

		// Verify the Widget toggle is not checked for system objects
		// Poshi locator: "Show Widget in Page Builder" toggle input

		const widgetToggle = page.locator(
			"//span[text()='Show Widget in Page Builder']/preceding-sibling::span/input"
		);

		await expect(widgetToggle).not.toBeChecked();
	}
);
