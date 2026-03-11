/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectActionAPI} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {editObjectDefinitionPagesTest} from '../../../fixtures/editObjectDefinitionPagesTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import {waitForAlert} from '../../../utils/waitForAlert';
import {generateObjectFields} from './utils/generateObjectFields';

const test = mergeTests(
	dataApiHelpersTest,
	editObjectDefinitionPagesTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	objectPagesTest
);

test(
	'LPD-78504 Can activate or deactivate an action',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		editObjectActionPage,
		page,
		viewObjectActionsPage,
	}) => {
		// Migrated from: CanActivateOrDeactivateAction
		// LPS-145665 - Verify that it's possible to activate and deactivate an Action

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

		const objectActionAPIClient =
			await apiHelpers.buildRestClient(ObjectActionAPI);

		const actionName = 'action' + getRandomInt();

		const {body: objectAction} =
			await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
				objectDefinition.externalReferenceCode!,
				{
					active: true,
					label: {en_US: 'Custom Action'},
					name: actionName,
					objectActionExecutorKey: 'webhook',
					objectActionTriggerKey: 'onAfterAdd',
					parameters: {
						url: 'http://localhost:8080',
					},
				}
			);

		apiHelpers.data.push({id: objectAction.id, type: 'objectAction'});

		await viewObjectActionsPage.goto(objectDefinition.label['en_US']);

		await expect(
			page.getByRole('link', {name: 'Custom Action'})
		).toBeVisible();

		await expect(page.getByText('Yes')).toBeVisible();

		await page.getByRole('link', {name: 'Custom Action'}).click();

		const iframe = page.frameLocator('iframe');

		await iframe.getByLabel('Active', {exact: true}).uncheck();

		await iframe.getByRole('button', {name: 'Save'}).click();

		await waitForAlert(iframe);

		await page.goBack();

		await viewObjectActionsPage.actionsTabItem.click();

		await expect(page.getByText('No')).toBeVisible();
	}
);

test(
	'LPD-78504 Can add account entry after creating account entry via action',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, site}) => {
		// Migrated from: CanAddAccountEntryAfterCreatingAccountEntry
		// LPS-173537 - Verify creating an Account entry triggers an action to add a second Account entry

		test.fixme(
			true,
			'Test requires Account system object action configuration and Account API infrastructure not available in the Playwright framework'
		);
	}
);

test(
	'LPD-78504 Can add account entry after creating custom object entry via action',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, site}) => {
		// Migrated from: CanAddAccountEntryAfterCreatingCustomObjectEntry
		// LPS-173537 - Verify creating a custom object entry triggers an action to add an Account entry

		test.fixme(
			true,
			'Test requires Account system object and "Add an Object Entry" action type with Account target, which requires infrastructure not available in the Playwright framework'
		);
	}
);

test(
	'LPD-78504 Can add account entry after deleting custom object entry via action',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, site}) => {
		// Migrated from: CanAddAccountEntryAfterDeletingCustomObjectEntry
		// LPS-173537 - Verify deleting a custom object entry triggers an action to add an Account entry

		test.fixme(
			true,
			'Test requires Account system object and "Add an Object Entry" action type with Account target, which requires infrastructure not available in the Playwright framework'
		);
	}
);

test(
	'LPD-78504 Can add account entry after updating custom object entry via action',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, site}) => {
		// Migrated from: CanAddAccountEntryAfterUpdatingCustomObjectEntry
		// LPS-173537 - Verify updating a custom object entry triggers an action to add an Account entry

		test.fixme(
			true,
			'Test requires Account system object and "Add an Object Entry" action type with Account target, which requires infrastructure not available in the Playwright framework'
		);
	}
);

test(
	'LPD-78504 Can add commerce product group entry after deleting commerce product entry via action',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, site}) => {
		// Migrated from: CanAddCommerceProductGroupEntryAfterDeletingCommerceProductEntry
		// LPS-173537 - Verify deleting a Commerce Product entry triggers an action to add a Commerce Product Group entry

		test.fixme(
			true,
			'Test requires Minium site accelerator, Commerce Product and Commerce Product Group system objects not available in the Playwright framework'
		);
	}
);

test(
	'LPD-78504 Can add user after creating commerce product entry via action',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, site}) => {
		// Migrated from: CanAddUserAfterCreatingCommerceProductEntry
		// LPS-180070 - Verify creating a Commerce Product entry triggers an action to add a user

		test.fixme(
			true,
			'Test requires Commerce Product system object and User target action type not available in the Playwright framework'
		);
	}
);

test(
	'LPD-78504 Can cancel the creation of an action',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		editObjectActionPage,
		page,
		viewObjectActionsPage,
	}) => {
		// Migrated from: CanCancelAction
		// LPS-139008 - Verify it is possible to cancel the creation of an Action

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await viewObjectActionsPage.goto(objectDefinition.label['en_US']);

		await viewObjectActionsPage.openObjectActionSidePanel();

		const iframe = page.frameLocator('iframe');

		await iframe.getByRole('button', {name: 'Cancel'}).click();

		await page.reload();

		await viewObjectActionsPage.actionsTabItem.click();

		await expect(page.getByText('No Results Found')).toBeVisible();
	}
);

test(
	'LPD-78504 Can cancel the update of an action',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		editObjectActionPage,
		page,
		viewObjectActionsPage,
	}) => {
		// Migrated from: CanCancelActionUpdate
		// LPS-139008 - Verify it is possible to cancel the update of an Action

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectActionAPIClient =
			await apiHelpers.buildRestClient(ObjectActionAPI);

		const actionName = 'action' + getRandomInt();

		const {body: objectAction} =
			await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
				objectDefinition.externalReferenceCode!,
				{
					active: true,
					label: {en_US: 'Action Label'},
					name: actionName,
					objectActionExecutorKey: 'webhook',
					objectActionTriggerKey: 'onAfterAdd',
					parameters: {
						url: 'http://localhost:8080',
					},
				}
			);

		apiHelpers.data.push({id: objectAction.id, type: 'objectAction'});

		await viewObjectActionsPage.goto(objectDefinition.label['en_US']);

		await page.getByRole('link', {name: 'Action Label'}).click();

		const iframe = page.frameLocator('iframe');

		await iframe.getByPlaceholder('Text to translate').clear();
		await iframe
			.getByPlaceholder('Text to translate')
			.fill('Update Action Label');

		await iframe.getByRole('button', {name: 'Cancel'}).click();

		await expect(
			page.getByRole('link', {name: 'Action Label'})
		).toBeVisible();

		await expect(page.getByText('Yes')).toBeVisible();
	}
);

test(
	'LPD-78504 Can create an action with webhook',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		editObjectActionPage,
		page,
		viewObjectActionsPage,
	}) => {
		// Migrated from: CanCreateAction
		// LPS-139008 - Verify it is possible to create an Action

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await viewObjectActionsPage.goto(objectDefinition.label['en_US']);

		await viewObjectActionsPage.openObjectActionSidePanel();

		const iframe = page.frameLocator('iframe');

		await iframe
			.getByPlaceholder('Text to translate')
			.fill('Action Label');

		await editObjectActionPage.openActionBuilderTab();

		await editObjectActionPage.inputWhenCombo.click();
		await iframe.getByRole('option', {name: 'On After Add'}).click();

		await editObjectActionPage.inputThenCombo.click();
		await iframe.getByRole('option', {name: 'Webhook'}).click();

		await iframe.getByLabel('URL').fill('http://localhost:8080');

		await iframe.getByRole('button', {name: 'Save'}).click();

		await waitForAlert(iframe);

		await page.goBack();

		await viewObjectActionsPage.actionsTabItem.click();

		await expect(
			page.getByRole('link', {name: 'Action Label'})
		).toBeVisible();

		await expect(page.getByText('Yes')).toBeVisible();
	}
);

test(
	'LPD-78504 Can create an action to add object entry with on order status update trigger',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, site}) => {
		// Migrated from: CanCreateActionAddObjectEntryWithOnOrderStatusUpdate
		// LPS-145665 - Verify it's possible to add an object entry with the trigger On Order Status Update

		test.fixme(
			true,
			'Test requires Commerce Order system object, Minium site accelerator, and checkout flow infrastructure not available in the Playwright framework'
		);
	}
);

test(
	'LPD-78504 Can create an action with expression builder condition',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		editObjectActionPage,
		page,
		viewObjectActionsPage,
	}) => {
		// Migrated from: CanCreateActionWithExpressionBuilder
		// LPS-156312 - Assert an Action can be created with Expression Builder

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

		await viewObjectActionsPage.goto(objectDefinition.label['en_US']);

		await viewObjectActionsPage.openObjectActionSidePanel();

		const iframe = page.frameLocator('iframe');

		await iframe
			.getByPlaceholder('Text to translate')
			.fill('Custom Action');

		await editObjectActionPage.openActionBuilderTab();

		await editObjectActionPage.inputWhenCombo.click();
		await iframe.getByRole('option', {name: 'On After Add'}).click();

		await editObjectActionPage.fillExpression(
			objectFields[0].name + " == 'Entry Test'"
		);

		await editObjectActionPage.inputThenCombo.click();
		await iframe.getByRole('option', {name: 'Webhook'}).click();

		await iframe.getByLabel('URL').fill('http://localhost:8080');

		await iframe.getByRole('button', {name: 'Save'}).click();

		await waitForAlert(iframe);

		await page.goBack();

		await viewObjectActionsPage.actionsTabItem.click();

		await expect(
			page.getByRole('link', {name: 'Custom Action'})
		).toBeVisible();

		await expect(page.getByText('Yes')).toBeVisible();
	}
);

test(
	'LPD-78504 Can create an action with Groovy Script',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, site}) => {
		// Migrated from: CanCreateActionWithGroovyScript
		// LPS-156569 - Verify that it's possible to create an Action with Groovy Script

		test.fixme(
			true,
			'Test requires Groovy Script action type and server-side script execution infrastructure not available in the Playwright framework'
		);
	}
);

test(
	'LPD-78504 Can create an object entry using actions',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, site}) => {
		// Migrated from: CanCreateEntryWithActions
		// LPS-161904 - Verify that it's possible to create an object entry using Actions

		test.fixme(
			true,
			'Test requires "Add an Object Entry" action type that creates entries server-side, which requires action executor infrastructure not available in the Playwright framework'
		);
	}
);

test(
	'LPD-78504 Can delete an action',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		editObjectActionPage,
		page,
		viewObjectActionsPage,
	}) => {
		// Migrated from: CanDeleteAction
		// LPS-139008 - Verify it is possible to delete an Action

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectActionAPIClient =
			await apiHelpers.buildRestClient(ObjectActionAPI);

		const actionName = 'action' + getRandomInt();

		const {body: objectAction} =
			await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
				objectDefinition.externalReferenceCode!,
				{
					active: true,
					label: {en_US: 'Action Label'},
					name: actionName,
					objectActionExecutorKey: 'webhook',
					objectActionTriggerKey: 'onAfterAdd',
					parameters: {
						url: 'http://localhost:8080',
					},
				}
			);

		await viewObjectActionsPage.goto(objectDefinition.label['en_US']);

		await expect(
			page.getByRole('link', {name: 'Action Label'})
		).toBeVisible();

		await page
			.getByRole('row', {name: 'Action Label'})
			.getByRole('button', {name: 'Actions'})
			.click();

		await page.getByRole('menuitem', {name: 'Delete'}).click();

		await page.getByRole('button', {name: 'Delete'}).click();

		await expect(
			page.getByRole('link', {name: 'Action Label'})
		).toBeHidden();
	}
);

test(
	'LPD-78504 Can edit an action name',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		editObjectActionPage,
		page,
		viewObjectActionsPage,
	}) => {
		// Migrated from: CanEditActionName
		// LPS-145665 - Verify that you can edit the Action name

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectActionAPIClient =
			await apiHelpers.buildRestClient(ObjectActionAPI);

		const actionName = 'action' + getRandomInt();

		const {body: objectAction} =
			await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
				objectDefinition.externalReferenceCode!,
				{
					active: true,
					label: {en_US: 'Custom Action'},
					name: actionName,
					objectActionExecutorKey: 'webhook',
					objectActionTriggerKey: 'onAfterAdd',
					parameters: {
						url: 'http://www.liferay.com',
					},
				}
			);

		apiHelpers.data.push({id: objectAction.id, type: 'objectAction'});

		await viewObjectActionsPage.goto(objectDefinition.label['en_US']);

		await page.getByRole('link', {name: 'Custom Action'}).click();

		const iframe = page.frameLocator('iframe');

		await iframe.getByPlaceholder('Text to translate').clear();
		await iframe
			.getByPlaceholder('Text to translate')
			.fill('New Action Update');

		await iframe.getByLabel('Active', {exact: true}).uncheck();

		await iframe.getByRole('button', {name: 'Save'}).click();

		await waitForAlert(iframe);

		await page.goBack();

		await viewObjectActionsPage.actionsTabItem.click();

		await expect(
			page.getByRole('link', {name: 'New Action Update'})
		).toBeVisible();

		await expect(page.getByText('No')).toBeVisible();
	}
);

test(
	'LPD-78504 Can edit an action with Groovy Script',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, site}) => {
		// Migrated from: CanEditActionWithGroovyScript
		// LPS-156560 - Verify that it's possible to edit an Action with Groovy Script

		test.fixme(
			true,
			'Test requires Groovy Script action type and server-side script execution infrastructure not available in the Playwright framework'
		);
	}
);

test(
	'LPD-78504 Can enable and disable condition on an action',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		editObjectActionPage,
		page,
		viewObjectActionsPage,
	}) => {
		// Migrated from: CanEnableAndDisableCondition
		// LPS-145665 - Verify that the admin user is able to enable and disable Condition

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

		const objectActionAPIClient =
			await apiHelpers.buildRestClient(ObjectActionAPI);

		const actionName = 'action' + getRandomInt();

		const {body: objectAction} =
			await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
				objectDefinition.externalReferenceCode!,
				{
					active: true,
					conditionExpression:
						objectFields[0].name + " == 'Entry with condition'",
					label: {en_US: 'Custom Action'},
					name: actionName,
					objectActionExecutorKey: 'webhook',
					objectActionTriggerKey: 'onAfterAdd',
					parameters: {
						url: 'http://localhost:8080',
					},
				}
			);

		apiHelpers.data.push({id: objectAction.id, type: 'objectAction'});

		await viewObjectActionsPage.goto(objectDefinition.label['en_US']);

		await page.getByRole('link', {name: 'Custom Action'}).click();

		const iframe = page.frameLocator('iframe');

		await editObjectActionPage.openActionBuilderTab();

		await expect(
			iframe.getByLabel('Enable Condition')
		).toBeChecked();

		await iframe.getByLabel('Enable Condition').uncheck();

		await iframe.getByRole('button', {name: 'Save'}).click();

		await waitForAlert(iframe);

		await page.goBack();

		await viewObjectActionsPage.actionsTabItem.click();

		await expect(
			page.getByRole('link', {name: 'Custom Action'})
		).toBeVisible();

		await expect(page.getByText('Yes')).toBeVisible();
	}
);

test(
	'LPD-78504 Can use formula field with user notification action',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, site}) => {
		// Migrated from: CanFormulaFieldBeUsedWithUserNotification
		// Verify that the user can use Formula Field with User Notification

		test.fixme(
			true,
			'Test requires User Notification template, Formula field configuration, and user login switching infrastructure not available in the Playwright framework'
		);
	}
);

test(
	'LPD-78504 Can inactivate an action',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		editObjectActionPage,
		page,
		viewObjectActionsPage,
	}) => {
		// Migrated from: CanInactivateAction
		// LPS-139008 - Verify that it is possible to inactivate an Action

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectActionAPIClient =
			await apiHelpers.buildRestClient(ObjectActionAPI);

		const actionName = 'action' + getRandomInt();

		const {body: objectAction} =
			await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
				objectDefinition.externalReferenceCode!,
				{
					active: true,
					label: {en_US: 'Action Label'},
					name: actionName,
					objectActionExecutorKey: 'webhook',
					objectActionTriggerKey: 'onAfterAdd',
					parameters: {
						url: 'http://localhost:8080',
					},
				}
			);

		apiHelpers.data.push({id: objectAction.id, type: 'objectAction'});

		await viewObjectActionsPage.goto(objectDefinition.label['en_US']);

		await expect(page.getByText('Yes')).toBeVisible();

		await page.getByRole('link', {name: 'Action Label'}).click();

		const iframe = page.frameLocator('iframe');

		await iframe.getByLabel('Active', {exact: true}).uncheck();

		await iframe.getByRole('button', {name: 'Save'}).click();

		await waitForAlert(iframe);

		await page.goBack();

		await viewObjectActionsPage.actionsTabItem.click();

		await expect(page.getByText('No')).toBeVisible();
	}
);

test(
	'LPD-78504 Can manage standalone permissions in roles',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, site}) => {
		// Migrated from: CanManageStandalonePermissionsInRoles
		// LPS-169994 - Verify users are able to manage standalone action permissions on the role page

		test.fixme(
			true,
			'Test requires standalone action type, role management, and permissions infrastructure not available in the Playwright framework'
		);
	}
);

test(
	'LPD-78504 Cannot leave action name blank',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		editObjectActionPage,
		page,
		viewObjectActionsPage,
	}) => {
		// Migrated from: CannotLeaveActionNameBlank
		// LPS-139008 - Verify it is not possible to leave the Action Name field blank

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await viewObjectActionsPage.goto(objectDefinition.label['en_US']);

		await viewObjectActionsPage.openObjectActionSidePanel();

		const iframe = page.frameLocator('iframe');

		await iframe
			.getByPlaceholder('Text to translate')
			.fill('Action Label');

		await editObjectActionPage.openActionBuilderTab();

		await editObjectActionPage.inputWhenCombo.click();
		await iframe.getByRole('option', {name: 'On After Add'}).click();

		await editObjectActionPage.inputThenCombo.click();
		await iframe.getByRole('option', {name: 'Webhook'}).click();

		await iframe.getByLabel('URL').fill('http://localhost:8080');

		await iframe.getByRole('tab', {name: 'Basic Info'}).click();

		await iframe.getByPlaceholder('Text to translate').clear();

		await iframe.getByRole('button', {name: 'Save'}).click();

		await expect(iframe.getByText('Required')).toBeVisible();
	}
);

test(
	'LPD-78504 Cannot leave action then field blank',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		editObjectActionPage,
		page,
		viewObjectActionsPage,
	}) => {
		// Migrated from: CannotLeaveActionThenBlank
		// LPS-139008 - Verify it is not possible to leave the Action Then field blank

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await viewObjectActionsPage.goto(objectDefinition.label['en_US']);

		await viewObjectActionsPage.openObjectActionSidePanel();

		const iframe = page.frameLocator('iframe');

		await iframe
			.getByPlaceholder('Text to translate')
			.fill('Action Label');

		await editObjectActionPage.openActionBuilderTab();

		await editObjectActionPage.inputWhenCombo.click();
		await iframe.getByRole('option', {name: 'On After Add'}).click();

		await iframe.getByRole('button', {name: 'Save'}).click();

		await expect(iframe.getByText('Required')).toBeVisible();
	}
);

test(
	'LPD-78504 Cannot leave action when field blank',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		editObjectActionPage,
		page,
		viewObjectActionsPage,
	}) => {
		// Migrated from: CannotLeaveActionWhenBlank
		// LPS-139008 - Verify it is not possible to leave the Action When field blank

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await viewObjectActionsPage.goto(objectDefinition.label['en_US']);

		await viewObjectActionsPage.openObjectActionSidePanel();

		const iframe = page.frameLocator('iframe');

		await iframe
			.getByPlaceholder('Text to translate')
			.fill('Action Label');

		await editObjectActionPage.openActionBuilderTab();

		await editObjectActionPage.inputThenCombo.click();
		await iframe.getByRole('option', {name: 'Webhook'}).click();

		await iframe.getByLabel('URL').fill('http://localhost:8080');

		await iframe.getByRole('button', {name: 'Save'}).click();

		await expect(iframe.getByText('Required')).toBeVisible();
	}
);

test(
	'LPD-78504 Cannot leave URL blank when webhook is selected',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		editObjectActionPage,
		page,
		viewObjectActionsPage,
	}) => {
		// Migrated from: CannotLeaveURLBlank
		// LPS-139008 - Verify it is not possible to leave the URL field blank when Webhook is selected

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await viewObjectActionsPage.goto(objectDefinition.label['en_US']);

		await viewObjectActionsPage.openObjectActionSidePanel();

		const iframe = page.frameLocator('iframe');

		await iframe
			.getByPlaceholder('Text to translate')
			.fill('Action Label');

		await editObjectActionPage.openActionBuilderTab();

		await editObjectActionPage.inputWhenCombo.click();
		await iframe.getByRole('option', {name: 'On After Add'}).click();

		await editObjectActionPage.inputThenCombo.click();
		await iframe.getByRole('option', {name: 'Webhook'}).click();

		await iframe.getByRole('button', {name: 'Save'}).click();

		await expect(iframe.getByText('Required')).toBeVisible();
	}
);

test(
	'LPD-78504 Cannot save action without expression builder value',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		editObjectActionPage,
		page,
		viewObjectActionsPage,
	}) => {
		// Migrated from: CannotSaveWithoutExpressionBuilder
		// LPS-156319 - Verify that the Expression Builder field is required

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

		await viewObjectActionsPage.goto(objectDefinition.label['en_US']);

		await viewObjectActionsPage.openObjectActionSidePanel();

		const iframe = page.frameLocator('iframe');

		await iframe
			.getByPlaceholder('Text to translate')
			.fill('Custom Action');

		await editObjectActionPage.openActionBuilderTab();

		await editObjectActionPage.inputWhenCombo.click();
		await iframe.getByRole('option', {name: 'On After Add'}).click();

		await iframe.getByLabel('Enable Condition').check();

		await editObjectActionPage.inputThenCombo.click();
		await iframe.getByRole('option', {name: 'Webhook'}).click();

		await iframe.getByLabel('URL').fill('http://localhost:8080');

		await iframe.getByRole('button', {name: 'Save'}).click();

		await expect(iframe.getByText('Required')).toBeVisible();

		await page.reload();

		await viewObjectActionsPage.actionsTabItem.click();

		await expect(page.getByText('No Results Found')).toBeVisible();
	}
);

test(
	'LPD-78504 Cannot see deactivated standalone action in dropdown menu',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, site}) => {
		// Migrated from: CanNotSeeDeactivatedStandaloneAction
		// LPS-169994 - Verify a deactivated standalone action is not displayed in the dropdown menu

		test.fixme(
			true,
			'Test requires standalone action type, object entry kebab menu interaction, and action visibility verification not available in the Playwright framework'
		);
	}
);

test(
	'LPD-78504 Can reactivate an action',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		editObjectActionPage,
		page,
		viewObjectActionsPage,
	}) => {
		// Migrated from: CanReactivateAction
		// LPS-139008 - Verify that it is possible to reactivate an Action

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectActionAPIClient =
			await apiHelpers.buildRestClient(ObjectActionAPI);

		const actionName = 'action' + getRandomInt();

		const {body: objectAction} =
			await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
				objectDefinition.externalReferenceCode!,
				{
					active: false,
					label: {en_US: 'Action Label'},
					name: actionName,
					objectActionExecutorKey: 'webhook',
					objectActionTriggerKey: 'onAfterAdd',
					parameters: {
						url: 'http://localhost:8080',
					},
				}
			);

		apiHelpers.data.push({id: objectAction.id, type: 'objectAction'});

		await viewObjectActionsPage.goto(objectDefinition.label['en_US']);

		await expect(page.getByText('No')).toBeVisible();

		await page.getByRole('link', {name: 'Action Label'}).click();

		const iframe = page.frameLocator('iframe');

		await iframe.getByLabel('Active', {exact: true}).check();

		await iframe.getByRole('button', {name: 'Save'}).click();

		await waitForAlert(iframe);

		await page.goBack();

		await viewObjectActionsPage.actionsTabItem.click();

		await expect(page.getByText('Yes')).toBeVisible();
	}
);

test(
	'LPD-78504 Can search for an action',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		editObjectActionPage,
		page,
		viewObjectActionsPage,
	}) => {
		// Migrated from: CanSearchAction
		// LPS-139008 - Verify it is possible to search for an Action

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectActionAPIClient =
			await apiHelpers.buildRestClient(ObjectActionAPI);

		const actionName1 = 'actionOne' + getRandomInt();
		const actionName2 = 'actionTwo' + getRandomInt();

		const {body: objectAction1} =
			await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
				objectDefinition.externalReferenceCode!,
				{
					active: true,
					label: {en_US: 'Action Label 1'},
					name: actionName1,
					objectActionExecutorKey: 'webhook',
					objectActionTriggerKey: 'onAfterAdd',
					parameters: {
						url: 'http://localhost:8080',
					},
				}
			);

		apiHelpers.data.push({id: objectAction1.id, type: 'objectAction'});

		const {body: objectAction2} =
			await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
				objectDefinition.externalReferenceCode!,
				{
					active: true,
					label: {en_US: 'Action Label 2'},
					name: actionName2,
					objectActionExecutorKey: 'webhook',
					objectActionTriggerKey: 'onAfterAdd',
					parameters: {
						url: 'http://localhost:8080',
					},
				}
			);

		apiHelpers.data.push({id: objectAction2.id, type: 'objectAction'});

		await viewObjectActionsPage.goto(objectDefinition.label['en_US']);

		await expect(
			page.getByRole('link', {name: 'Action Label 1'})
		).toBeVisible();

		await expect(
			page.getByRole('link', {name: 'Action Label 2'})
		).toBeVisible();

		await page.getByPlaceholder('Search').fill('1');
		await page.keyboard.press('Enter');

		await expect(
			page.getByRole('link', {name: 'Action Label 1'})
		).toBeVisible();

		await expect(
			page.getByRole('link', {name: 'Action Label 2'})
		).toBeHidden();
	}
);

test(
	'LPD-78504 Can trigger action after disabling expression condition',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, site}) => {
		// Migrated from: CanTriggerActionAfterDisablingExpression
		// LPS-156343 - Verify that Action can be triggered after disabling the expression

		test.fixme(
			true,
			'Test requires "Add an Object Entry" action executor with expression condition toggling and server-side entry creation verification not available in the Playwright framework'
		);
	}
);

test(
	'LPD-78504 Can trigger action with expression by adding an entry',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, site}) => {
		// Migrated from: CanTriggerActionWithExpressionByAddingEntry
		// LPS-156320 - Assert an Action with an Expression can be triggered after adding an entry

		test.fixme(
			true,
			'Test requires "Add an Object Entry" action executor with expression condition and server-side entry creation verification not available in the Playwright framework'
		);
	}
);

test(
	'LPD-78504 Can trigger action with expression by deleting an entry',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, site}) => {
		// Migrated from: CanTriggerActionWithExpressionByDeletingEntry
		// LPS-173218 - Assert an Action with an Expression can be triggered after deleting an entry

		test.fixme(
			true,
			'Test requires "Add an Object Entry" action executor with expression condition on delete trigger and server-side entry creation verification not available in the Playwright framework'
		);
	}
);

test(
	'LPD-78504 Can trigger action with expression by updating an entry',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, site}) => {
		// Migrated from: CanTriggerActionWithExpressionByUpdatingEntry
		// LPS-173219 - Assert an Action with an Expression can be triggered after updating an entry

		test.fixme(
			true,
			'Test requires "Add an Object Entry" action executor with expression condition on update trigger and server-side entry creation verification not available in the Playwright framework'
		);
	}
);

test(
	'LPD-78504 Can trigger standalone action for site scoped object',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, site}) => {
		// Migrated from: CanTriggerStandaloneActionForSiteScopedObject
		// LPS-172918 - Verify the user can trigger a standalone action for a site scoped object

		test.fixme(
			true,
			'Test requires standalone action type with site-scoped object, object entry kebab menu interaction, and action trigger verification not available in the Playwright framework'
		);
	}
);

test(
	'LPD-78504 Can trigger standalone action with permission',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, site}) => {
		// Migrated from: CanTriggerStandaloneActionWithPermission
		// LPS-169994 - Verify that a permitted user can manually trigger a standalone action

		test.fixme(
			true,
			'Test requires standalone action type, role/permission management, user creation, and login switching infrastructure not available in the Playwright framework'
		);
	}
);

test(
	'LPD-78504 Can update account entry after creating account entry via action',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, site}) => {
		// Migrated from: CanUpdateAccountEntryAfterCreatingAccountEntry
		// LPS-173537 - Verify creating an Account entry triggers an action to update the Account entry

		test.fixme(
			true,
			'Test requires Account system object and "Update an Object Entry" action type with Account target, which requires infrastructure not available in the Playwright framework'
		);
	}
);

test(
	'LPD-78504 Can update an action',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		editObjectActionPage,
		page,
		viewObjectActionsPage,
	}) => {
		// Migrated from: CanUpdateAction
		// LPS-139008 - Verify it is possible to update an Action

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectActionAPIClient =
			await apiHelpers.buildRestClient(ObjectActionAPI);

		const actionName = 'action' + getRandomInt();

		const {body: objectAction} =
			await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
				objectDefinition.externalReferenceCode!,
				{
					active: true,
					label: {en_US: 'Action Label'},
					name: actionName,
					objectActionExecutorKey: 'webhook',
					objectActionTriggerKey: 'onAfterAdd',
					parameters: {
						url: 'http://localhost:8080',
					},
				}
			);

		apiHelpers.data.push({id: objectAction.id, type: 'objectAction'});

		await viewObjectActionsPage.goto(objectDefinition.label['en_US']);

		await page.getByRole('link', {name: 'Action Label'}).click();

		const iframe = page.frameLocator('iframe');

		await iframe.getByPlaceholder('Text to translate').clear();
		await iframe
			.getByPlaceholder('Text to translate')
			.fill('Update Action Label');

		await iframe.getByRole('button', {name: 'Save'}).click();

		await waitForAlert(iframe);

		await page.goBack();

		await viewObjectActionsPage.actionsTabItem.click();

		await expect(
			page.getByRole('link', {name: 'Update Action Label'})
		).toBeVisible();

		await expect(page.getByText('Yes')).toBeVisible();
	}
);

test(
	'LPD-78504 Can update commerce product group entry after creating commerce product entry via action',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, site}) => {
		// Migrated from: CanUpdateCommerceProductGroupEntryAfterCreatingCommerceProductEntry
		// LPS-173537 - Verify adding a Commerce Product entry triggers an action to update the Commerce Product Group entry

		test.fixme(
			true,
			'Test requires Commerce Product Group system object and Commerce Product entry creation infrastructure not available in the Playwright framework'
		);
	}
);

test(
	'LPD-78504 Can use expression with Groovy Script action',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, site}) => {
		// Migrated from: CanUseExpressionWithGroovyScript
		// LPS-156346 - Verify that the expression works with Groovy Script

		test.fixme(
			true,
			'Test requires Groovy Script action type with expression condition and server-side script execution verification not available in the Playwright framework'
		);
	}
);

test(
	'LPD-78504 Can use expression with webhook action',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		editObjectActionPage,
		page,
		viewObjectActionsPage,
	}) => {
		// Migrated from: CanUseExpressionWithWebhook
		// LPS-156347 - Verify that the expression works with Webhooks

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

		const objectActionAPIClient =
			await apiHelpers.buildRestClient(ObjectActionAPI);

		const actionName = 'action' + getRandomInt();

		const {body: objectAction} =
			await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
				objectDefinition.externalReferenceCode!,
				{
					active: true,
					conditionExpression:
						objectFields[0].name + " == 'Entry Test'",
					label: {en_US: 'Action Label'},
					name: actionName,
					objectActionExecutorKey: 'webhook',
					objectActionTriggerKey: 'onAfterAdd',
					parameters: {
						url: 'http://localhost:8080',
					},
				}
			);

		apiHelpers.data.push({id: objectAction.id, type: 'objectAction'});

		const applicationName =
			'c/' + objectDefinition.name!.toLowerCase() + 's';
		const fieldName = objectFields[0].name!;

		await apiHelpers.objectEntry.postObjectEntry(
			{[fieldName]: 'Entry Test'},
			applicationName
		);

		await viewObjectActionsPage.goto(objectDefinition.label['en_US']);

		await expect(
			page.getByRole('link', {name: 'Action Label'})
		).toBeVisible();

		await expect(page.getByText('Yes')).toBeVisible();
	}
);

test(
	'LPD-78504 Can verify unpublished object with standalone action does not show in permissions',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, site}) => {
		// Migrated from: CheckStandaloneActionPermissionOfUnpublishedObject
		// LPS-173774 - Verify that an unpublished object with a standalone action does NOT show up in permissions

		test.fixme(
			true,
			'Test requires standalone action type on unpublished object, role permissions UI navigation, and permission verification infrastructure not available in the Playwright framework'
		);
	}
);

test(
	'LPD-78504 Can verify action name is required',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		editObjectActionPage,
		page,
		viewObjectActionsPage,
	}) => {
		// Migrated from: VerifyActionNameIsRequired
		// LPS-146871 - Verify that the Action name is required

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await viewObjectActionsPage.goto(objectDefinition.label['en_US']);

		await viewObjectActionsPage.openObjectActionSidePanel();

		const iframe = page.frameLocator('iframe');

		await iframe.getByRole('button', {name: 'Save'}).click();

		await expect(iframe.getByText('Required')).toBeVisible();
	}
);

test(
	'LPD-78504 Can verify condition card is hidden when using on subscription status update trigger',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		editObjectActionPage,
		page,
		viewObjectActionsPage,
	}) => {
		// Migrated from: VerifyTheConditionCardAreHidden
		// LPS-171802 - Verify if the Condition card is hidden when using the trigger On Subscription Status Update

		await viewObjectActionsPage.goto('Commerce Order');

		await viewObjectActionsPage.openObjectActionSidePanel();

		const iframe = page.frameLocator('iframe');

		await iframe
			.getByPlaceholder('Text to translate')
			.fill('Action Label');

		await editObjectActionPage.openActionBuilderTab();

		await editObjectActionPage.inputWhenCombo.click();
		await iframe
			.getByRole('option', {name: 'On Subscription Status Update'})
			.click();

		await expect(iframe.getByText('Condition')).toBeHidden();
	}
);
