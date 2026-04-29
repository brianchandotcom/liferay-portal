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
import {rolesPagesTest} from '../../../fixtures/rolesPagesTest';
import {scriptManagementPagesTest} from '../../../fixtures/scriptManagementPagesTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import {
	performLoginViaApi,
	performLogout,
	userData,
} from '../../../utils/performLogin';
import {waitForAlert} from '../../../utils/waitForAlert';
import {miniumSetUp} from '../../commerce/utils/commerce';
import {generateObjectFields} from '../utils/generateObjectFields';

const test = mergeTests(
	dataApiHelpersTest,
	editObjectDefinitionPagesTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	objectPagesTest,
	rolesPagesTest,
	scriptManagementPagesTest
);

test(
	'LPD-78504 Can activate or deactivate an action',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectActionsPage}) => {

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
	{tag: ['@LPD-78504', '@LPS-173537']},
	async ({apiHelpers, site: _site}) => {

		// Migrated from Poshi: CanAddAccountEntryAfterCreatingAccountEntry

		const targetAccountName = `Account Name ${getRandomInt()}`;

		const objectActionAPIClient =
			await apiHelpers.buildRestClient(ObjectActionAPI);

		const {body: objectAction} =
			await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
				'L_ACCOUNT',
				{
					active: true,
					label: {en_US: 'Custom Action'},
					name: `customAction${getRandomInt()}`,
					objectActionExecutorKey: 'add-object-entry',
					objectActionTriggerKey: 'onAfterAdd',
					parameters: {
						objectDefinitionExternalReferenceCode: 'L_ACCOUNT',
						predefinedValues: [
							{
								businessType: 'Text',
								inputAsValue: true,
								label: {en_US: 'Name'},
								name: 'name',
								value: targetAccountName,
							},
							{
								businessType: 'Text',
								inputAsValue: true,
								label: {en_US: 'Type'},
								name: 'type',
								value: 'person',
							},
						],
						system: true,
					},
				}
			);

		apiHelpers.data.push({id: objectAction.id, type: 'objectAction'});

		await apiHelpers.headlessAdminUser.postAccount({
			name: `Business Account ${getRandomInt()}`,
			type: 'business',
		});

		const createdAccount =
			await apiHelpers.headlessAdminUser.getAccountByName(
				targetAccountName
			);

		if (createdAccount) {
			apiHelpers.data.push({id: createdAccount.id!, type: 'account'});
		}

		expect(createdAccount).toBeTruthy();
	}
);

test(
	'LPD-78504 Can add account entry after creating custom object entry via action',
	{tag: ['@LPD-78504', '@LPS-173537']},
	async ({apiHelpers, site: _site}) => {

		// Migrated from Poshi: CanAddAccountEntryAfterCreatingCustomObjectEntry

		const targetAccountName = `Account Name ${getRandomInt()}`;

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

		const {body: objectAction} =
			await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
				objectDefinition.externalReferenceCode!,
				{
					active: true,
					label: {en_US: 'Custom Action'},
					name: `customAction${getRandomInt()}`,
					objectActionExecutorKey: 'add-object-entry',
					objectActionTriggerKey: 'onAfterAdd',
					parameters: {
						objectDefinitionExternalReferenceCode: 'L_ACCOUNT',
						predefinedValues: [
							{
								businessType: 'Text',
								inputAsValue: true,
								label: {en_US: 'Name'},
								name: 'name',
								value: targetAccountName,
							},
							{
								businessType: 'Text',
								inputAsValue: true,
								label: {en_US: 'Type'},
								name: 'type',
								value: 'guest',
							},
						],
						system: true,
					},
				}
			);

		apiHelpers.data.push({id: objectAction.id, type: 'objectAction'});

		const applicationName =
			'c/' + objectDefinition.name!.toLowerCase() + 's';

		await apiHelpers.objectEntry.postObjectEntry(
			{[objectFields[0].name!]: 'Entry Test'},
			applicationName
		);

		const createdAccount =
			await apiHelpers.headlessAdminUser.getAccountByName(
				targetAccountName
			);

		if (createdAccount) {
			apiHelpers.data.push({id: createdAccount.id!, type: 'account'});
		}

		expect(createdAccount).toBeTruthy();
	}
);

test(
	'LPD-78504 Can add account entry after deleting custom object entry via action',
	{tag: ['@LPD-78504', '@LPS-173537']},
	async ({apiHelpers, site: _site}) => {

		// Migrated from Poshi: CanAddAccountEntryAfterDeletingCustomObjectEntry

		const targetAccountName = `Account Name ${getRandomInt()}`;

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

		const {body: objectAction} =
			await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
				objectDefinition.externalReferenceCode!,
				{
					active: true,
					label: {en_US: 'Custom Action'},
					name: `customAction${getRandomInt()}`,
					objectActionExecutorKey: 'add-object-entry',
					objectActionTriggerKey: 'onAfterDelete',
					parameters: {
						objectDefinitionExternalReferenceCode: 'L_ACCOUNT',
						predefinedValues: [
							{
								businessType: 'Text',
								inputAsValue: true,
								label: {en_US: 'Name'},
								name: 'name',
								value: targetAccountName,
							},
							{
								businessType: 'Text',
								inputAsValue: true,
								label: {en_US: 'Type'},
								name: 'type',
								value: 'guest',
							},
						],
						system: true,
					},
				}
			);

		apiHelpers.data.push({id: objectAction.id, type: 'objectAction'});

		const applicationName =
			'c/' + objectDefinition.name!.toLowerCase() + 's';

		const entry = await apiHelpers.objectEntry.postObjectEntry(
			{[objectFields[0].name!]: 'Entry Test'},
			applicationName
		);

		await apiHelpers.objectEntry.deleteObjectEntry(
			applicationName,
			String(entry.id)
		);

		const createdAccount =
			await apiHelpers.headlessAdminUser.getAccountByName(
				targetAccountName
			);

		if (createdAccount) {
			apiHelpers.data.push({id: createdAccount.id!, type: 'account'});
		}

		expect(createdAccount).toBeTruthy();
	}
);

test(
	'LPD-78504 Can add account entry after updating custom object entry via action',
	{tag: ['@LPD-78504', '@LPS-173537']},
	async ({apiHelpers, site: _site}) => {

		// Migrated from Poshi: CanAddAccountEntryAfterUpdatingCustomObjectEntry

		const targetAccountName = `Account Name ${getRandomInt()}`;

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

		const {body: objectAction} =
			await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
				objectDefinition.externalReferenceCode!,
				{
					active: true,
					label: {en_US: 'Custom Action'},
					name: `customAction${getRandomInt()}`,
					objectActionExecutorKey: 'add-object-entry',
					objectActionTriggerKey: 'onAfterUpdate',
					parameters: {
						objectDefinitionExternalReferenceCode: 'L_ACCOUNT',
						predefinedValues: [
							{
								businessType: 'Text',
								inputAsValue: true,
								label: {en_US: 'Name'},
								name: 'name',
								value: targetAccountName,
							},
							{
								businessType: 'Text',
								inputAsValue: true,
								label: {en_US: 'Type'},
								name: 'type',
								value: 'guest',
							},
						],
						system: true,
					},
				}
			);

		apiHelpers.data.push({id: objectAction.id, type: 'objectAction'});

		const applicationName =
			'c/' + objectDefinition.name!.toLowerCase() + 's';

		const fieldName = objectFields[0].name!;

		const entry = await apiHelpers.objectEntry.postObjectEntry(
			{[fieldName]: 'Entry Test'},
			applicationName
		);

		await apiHelpers.objectEntry.patchObjectEntry(
			{[fieldName]: 'Entry Test Edited'},
			applicationName,
			Number(entry.id)
		);

		const createdAccount =
			await apiHelpers.headlessAdminUser.getAccountByName(
				targetAccountName
			);

		if (createdAccount) {
			apiHelpers.data.push({id: createdAccount.id!, type: 'account'});
		}

		expect(createdAccount).toBeTruthy();
	}
);

test(
	'LPD-78504 Can add commerce product group entry after deleting commerce product entry via action',
	{tag: ['@LPD-78504', '@LPS-173537']},
	async ({apiHelpers}) => {

		// Migrated from Poshi: CanAddCommerceProductGroupEntryAfterDeletingCommerceProductEntry

		const targetGroupName = `Value Test ${getRandomInt()}`;

		const {catalog} = await miniumSetUp(apiHelpers);

		const product =
			await apiHelpers.headlessCommerceAdminCatalog.postProduct({
				catalogId: catalog.id,
				name: {en_US: `Simple T-Shirt ${getRandomInt()}`},
				productType: 'simple',
			});

		const objectActionAPIClient =
			await apiHelpers.buildRestClient(ObjectActionAPI);

		const {body: objectAction} =
			await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
				'L_COMMERCE_PRODUCT_DEFINITION',
				{
					active: true,
					label: {en_US: 'Custom Action'},
					name: `customAction${getRandomInt()}`,
					objectActionExecutorKey: 'add-object-entry',
					objectActionTriggerKey: 'onAfterDelete',
					parameters: {
						objectDefinitionExternalReferenceCode:
							'L_COMMERCE_PRODUCT_GROUP',
						predefinedValues: [
							{
								businessType: 'Text',
								inputAsValue: true,
								label: {en_US: 'Title'},
								name: 'title',
								value: targetGroupName,
							},
						],
						system: true,
					},
				}
			);

		apiHelpers.data.push({id: objectAction.id, type: 'objectAction'});

		await apiHelpers.headlessCommerceAdminCatalog.deleteProduct(
			product.productId!
		);

		const productGroups =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				'c/commerceproductgroups'
			);

		const createdGroup = productGroups.items.find(
			(item: any) => item.title === targetGroupName
		);

		expect(createdGroup).toBeTruthy();

		if (createdGroup) {
			apiHelpers.data.push({
				id: createdGroup.id,
				type: 'objectEntry',
			});
		}
	}
);

test(
	'LPD-78504 Can add user after creating commerce product entry via action',
	{tag: ['@LPD-78504', '@LPS-180070']},
	async ({apiHelpers}) => {

		// Migrated from Poshi: CanAddUserAfterCreatingCommerceProductEntry

		const newUserAlternateName = `newusertest${getRandomInt()}`;
		const newUserEmail = `${newUserAlternateName}@test.com`;

		const {catalog} = await miniumSetUp(apiHelpers);

		const objectActionAPIClient =
			await apiHelpers.buildRestClient(ObjectActionAPI);

		const {body: objectAction} =
			await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
				'L_COMMERCE_PRODUCT_DEFINITION',
				{
					active: true,
					label: {en_US: 'Custom Action'},
					name: `customAction${getRandomInt()}`,
					objectActionExecutorKey: 'add-object-entry',
					objectActionTriggerKey: 'onAfterAdd',
					parameters: {
						objectDefinitionExternalReferenceCode: 'L_USER',
						predefinedValues: [
							{
								businessType: 'Text',
								inputAsValue: true,
								label: {en_US: 'Screen Name'},
								name: 'alternateName',
								value: newUserAlternateName,
							},
							{
								businessType: 'Text',
								inputAsValue: true,
								label: {en_US: 'Email Address'},
								name: 'emailAddress',
								value: newUserEmail,
							},
							{
								businessType: 'Text',
								inputAsValue: true,
								label: {en_US: 'First Name'},
								name: 'givenName',
								value: 'newUserTest',
							},
							{
								businessType: 'Text',
								inputAsValue: true,
								label: {en_US: 'Last Name'},
								name: 'familyName',
								value: 'newUserTest',
							},
						],
						system: true,
					},
				}
			);

		apiHelpers.data.push({id: objectAction.id, type: 'objectAction'});

		await apiHelpers.headlessCommerceAdminCatalog.postProduct({
			catalogId: catalog.id,
			name: {en_US: `Simple T-Shirt ${getRandomInt()}`},
			productType: 'simple',
		});

		const createdUser =
			await apiHelpers.headlessAdminUser.getUserAccountByEmailAddress(
				newUserEmail
			);

		expect(createdUser).toBeTruthy();

		if (createdUser?.id) {
			apiHelpers.data.push({id: createdUser.id, type: 'userAccount'});
		}
	}
);

test(
	'LPD-78504 Can cancel the creation of an action',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectActionsPage}) => {

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
	async ({apiHelpers, page, viewObjectActionsPage}) => {

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
	async ({apiHelpers, editObjectActionPage, page, viewObjectActionsPage}) => {

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

		await iframe.getByPlaceholder('Text to translate').fill('Action Label');

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
	async ({apiHelpers}) => {

		// No Poshi origin — added during the auto-regen. Validates that the
		// "liferay/commerce_order_status" trigger key (registered by Commerce
		// via DestinationNames.COMMERCE_ORDER_STATUS) is accepted by the
		// object-admin REST API for an action on the Commerce Order system
		// object.

		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
		});

		const fieldName = objectFields[0].name!;
		const fieldLabel = objectFields[0].label!;
		const predefinedValue = `Order Status Test ${getRandomInt()}`;

		await miniumSetUp(apiHelpers);

		const targetObjectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: targetObjectDefinition.id,
			type: 'objectDefinition',
		});

		const objectActionAPIClient =
			await apiHelpers.buildRestClient(ObjectActionAPI);

		const {body: objectAction} =
			await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
				'L_COMMERCE_ORDER',
				{
					active: true,
					label: {en_US: 'Custom Action'},
					name: `customAction${getRandomInt()}`,
					objectActionExecutorKey: 'add-object-entry',
					objectActionTriggerKey: 'liferay/commerce_order_status',
					parameters: {
						objectDefinitionExternalReferenceCode:
							targetObjectDefinition.externalReferenceCode,
						predefinedValues: [
							{
								businessType: 'Text',
								inputAsValue: true,
								label: fieldLabel,
								name: fieldName,
								value: predefinedValue,
							},
						],
					},
				}
			);

		apiHelpers.data.push({id: objectAction.id, type: 'objectAction'});

		expect(objectAction.id).toBeTruthy();
		expect(objectAction.objectActionTriggerKey).toBe(
			'liferay/commerce_order_status'
		);
	}
);

test(
	'LPD-78504 Can create an action with expression builder condition',
	{tag: '@LPD-78504'},
	async ({apiHelpers, editObjectActionPage, page, viewObjectActionsPage}) => {

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
	{tag: ['@LPD-78504', '@LPS-156569']},
	async ({apiHelpers, scriptManagementPage, site: _site}) => {

		// Migrated from Poshi: CanCreateActionWithGroovyScript

		await scriptManagementPage.enableScriptManagementConfiguration();

		await scriptManagementPage.page.waitForTimeout(2000);

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
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
						required: true,
						system: false,
						type: 'String',
					},
					{
						DBType: 'String',
						businessType: 'Text',
						externalReferenceCode: 'customObjectFieldActionTest',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'Custom Field Action Test'},
						listTypeDefinitionId: 0,
						localized: false,
						name: 'customObjectFieldActionTest',
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

		const groovyScript = `
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;
import java.util.Map;

ObjectEntry objectEntry = ObjectEntryLocalServiceUtil.getObjectEntry(id);
Map<String, Serializable> values = objectEntry.getValues();
values.put("customObjectFieldActionTest", "Action Test Works")
ObjectEntryLocalServiceUtil.updateObjectEntry(objectEntry.getUserId(), id, 0L, values, new ServiceContext());
`;

		const objectActionAPIClient =
			await apiHelpers.buildRestClient(ObjectActionAPI);

		const {body: objectAction} =
			await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
				objectDefinition.externalReferenceCode!,
				{
					active: true,
					errorMessage: {en_US: ''},
					label: {en_US: 'Custom Action'},
					name: `customAction${getRandomInt()}`,
					objectActionExecutorKey: 'groovy',
					objectActionTriggerKey: 'onAfterAdd',
					parameters: {
						lineCount: groovyScript.split('\n').length,
						script: groovyScript,
					},
				}
			);

		apiHelpers.data.push({id: objectAction.id, type: 'objectAction'});

		const applicationName =
			'c/' + objectDefinition.name!.toLowerCase() + 's';

		const entry = await apiHelpers.objectEntry.postObjectEntry(
			{customObjectField: 'Entry Test'},
			applicationName
		);

		await expect
			.poll(
				async () => {
					const updatedEntry =
						await apiHelpers.objectEntry.getObjectEntryById(
							applicationName,
							String(entry.id)
						);

					return updatedEntry.customObjectFieldActionTest;
				},
				{timeout: 10000}
			)
			.toBe('Action Test Works');
	}
);

test(
	'LPD-78504 Can create an object entry using actions',
	{tag: ['@LPD-78504', '@LPS-161904']},
	async ({apiHelpers, site: _site}) => {

		// Migrated from Poshi: CanCreateEntryWithActions

		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
		});

		const fieldName = objectFields[0].name!;
		const fieldLabel = objectFields[0].label!;
		const predefinedValue = `Object entry using Action ${getRandomInt()}`;

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

		const {body: objectAction} =
			await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
				objectDefinition.externalReferenceCode!,
				{
					active: true,
					label: {en_US: 'Custom Action'},
					name: `customAction${getRandomInt()}`,
					objectActionExecutorKey: 'add-object-entry',
					objectActionTriggerKey: 'onAfterAdd',
					parameters: {
						objectDefinitionExternalReferenceCode:
							objectDefinition.externalReferenceCode,
						predefinedValues: [
							{
								businessType: 'Text',
								inputAsValue: true,
								label: fieldLabel,
								name: fieldName,
								value: predefinedValue,
							},
						],
					},
				}
			);

		apiHelpers.data.push({id: objectAction.id, type: 'objectAction'});

		const applicationName =
			'c/' + objectDefinition.name!.toLowerCase() + 's';

		await apiHelpers.objectEntry.postObjectEntry(
			{[fieldName]: 'Entry Test'},
			applicationName
		);

		const entries =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				applicationName
			);

		const autoCreatedEntry = entries.items.find(
			(item: any) => item[fieldName] === predefinedValue
		);

		expect(autoCreatedEntry).toBeTruthy();
	}
);

test(
	'LPD-78504 Can delete an action',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectActionsPage}) => {

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
	async ({apiHelpers, page, viewObjectActionsPage}) => {

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
	{tag: ['@LPD-78504', '@LPS-156560']},
	async ({apiHelpers, scriptManagementPage, site: _site}) => {

		// Migrated from Poshi: CanEditActionWithGroovyScript

		await scriptManagementPage.enableScriptManagementConfiguration();

		await scriptManagementPage.page.waitForTimeout(2000);

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
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
						required: true,
						system: false,
						type: 'String',
					},
					{
						DBType: 'String',
						businessType: 'Text',
						externalReferenceCode: 'customObjectFieldActionTest',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'Custom Field Action Test'},
						listTypeDefinitionId: 0,
						localized: false,
						name: 'customObjectFieldActionTest',
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

		const groovyScript = `
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;
import java.util.Map;

ObjectEntry objectEntry = ObjectEntryLocalServiceUtil.getObjectEntry(id);
Map<String, Serializable> values = objectEntry.getValues();
values.put("customObjectFieldActionTest", "Action Test Works")
ObjectEntryLocalServiceUtil.updateObjectEntry(objectEntry.getUserId(), id, 0L, values, new ServiceContext());
`;

		const objectActionAPIClient =
			await apiHelpers.buildRestClient(ObjectActionAPI);

		const {body: objectAction} =
			await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
				objectDefinition.externalReferenceCode!,
				{
					active: true,
					errorMessage: {en_US: ''},
					label: {en_US: 'Custom Action'},
					name: `customAction${getRandomInt()}`,
					objectActionExecutorKey: 'groovy',
					objectActionTriggerKey: 'onAfterUpdate',
					parameters: {
						lineCount: groovyScript.split('\n').length,
						script: groovyScript,
					},
				}
			);

		apiHelpers.data.push({id: objectAction.id, type: 'objectAction'});

		await objectActionAPIClient.patchObjectAction(objectAction.id!, {
			conditionExpression: "customObjectField == 'Entry Update'",
		});

		const applicationName =
			'c/' + objectDefinition.name!.toLowerCase() + 's';

		const entry = await apiHelpers.objectEntry.postObjectEntry(
			{customObjectField: 'Entry Test'},
			applicationName
		);

		await apiHelpers.objectEntry.patchObjectEntry(
			{customObjectField: 'Entry Update'},
			applicationName,
			Number(entry.id)
		);

		await expect
			.poll(
				async () => {
					const updatedEntry =
						await apiHelpers.objectEntry.getObjectEntryById(
							applicationName,
							String(entry.id)
						);

					return updatedEntry.customObjectFieldActionTest;
				},
				{timeout: 10000}
			)
			.toBe('Action Test Works');
	}
);

test(
	'LPD-78504 Can enable and disable condition on an action',
	{tag: '@LPD-78504'},
	async ({apiHelpers, editObjectActionPage, page, viewObjectActionsPage}) => {

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

		await expect(iframe.getByLabel('Enable Condition')).toBeChecked();

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
	{tag: ['@LPD-78504', '@LPS-176850']},
	async ({apiHelpers, page, site: _site}) => {

		// Migrated from Poshi: CanFormulaFieldBeUsedWithUserNotification

		const notificationTemplate =
			await apiHelpers.notification.postNotificationTemplate({
				editorType: 'richText',
				name: `User Notification Template ${getRandomInt()}`,
				recipientType: 'term',
				recipients: [{term: '[%CURRENT_USER_ID%]'}],
				subject: {en_US: `Subject Test ${getRandomInt()}`},
				type: 'userNotification',
			});

		apiHelpers.data.push({
			id: notificationTemplate.id,
			type: 'notificationTemplate',
		});

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						DBType: 'Integer',
						businessType: 'Integer',
						externalReferenceCode: 'customInteger',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'Custom Integer'},
						listTypeDefinitionId: 0,
						localized: false,
						name: 'customInteger',
						required: false,
						system: false,
						type: 'Integer',
					},
					{
						DBType: 'String',
						businessType: 'Formula',
						externalReferenceCode: 'customFormulaField',
						indexed: false,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'Custom Formula Field'},
						listTypeDefinitionId: 0,
						localized: false,
						name: 'customFormulaField',
						objectFieldSettings: [
							{name: 'output', value: 'Integer'} as any,
							{
								name: 'script',
								value: 'customInteger + customInteger',
							} as any,
						],
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
					label: {en_US: 'Notification action when entry is added'},
					name: `notifyOnAdd${getRandomInt()}`,
					objectActionExecutorKey: 'notification',
					objectActionTriggerKey: 'onAfterAdd',
					parameters: {
						notificationTemplateId: notificationTemplate.id,
						type: 'userNotification',
					},
				}
			);

		apiHelpers.data.push({id: objectAction.id, type: 'objectAction'});

		const applicationName =
			'c/' + objectDefinition.name!.toLowerCase() + 's';

		await apiHelpers.objectEntry.postObjectEntry(
			{customInteger: 1111},
			applicationName
		);

		await page.waitForTimeout(2000);

		const notificationQueueEntries =
			await apiHelpers.notification.getNotificationQueueEntriesPage(
				String(notificationTemplate.id)
			);

		for (const item of notificationQueueEntries.items) {
			apiHelpers.data.push({
				id: item.id,
				type: 'notificationQueueEntry',
			});
		}

		expect(notificationQueueEntries.items.length).toBeGreaterThan(0);
	}
);

test(
	'LPD-78504 Can inactivate an action',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectActionsPage}) => {

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
	{tag: ['@LPD-78504', '@LPS-173723']},
	async ({apiHelpers, roleDefinePermissionsPage, rolePage, rolesPage}) => {

		// Migrated from Poshi: CanManageStandalonePermissionsInRoles

		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
		});

		const fieldName = objectFields[0].name!;
		const fieldLabel = objectFields[0].label!;
		const actionName = `ActionName${getRandomInt()}`;

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

		const {body: objectAction} =
			await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
				objectDefinition.externalReferenceCode!,
				{
					active: true,
					errorMessage: {en_US: 'Error Message'},
					label: {en_US: 'Action Label'},
					name: actionName,
					objectActionExecutorKey: 'add-object-entry',
					objectActionTriggerKey: 'standalone',
					parameters: {
						objectDefinitionExternalReferenceCode:
							objectDefinition.externalReferenceCode,
						predefinedValues: [
							{
								businessType: 'Text',
								inputAsValue: true,
								label: fieldLabel,
								name: fieldName,
								value: 'New Entry Test',
							},
						],
					},
				}
			);

		apiHelpers.data.push({id: objectAction.id, type: 'objectAction'});

		const roleName = `Regular Role ${getRandomInt()}`;

		const role = await apiHelpers.headlessAdminUser.postRole({
			name: roleName,
		});

		apiHelpers.data.push({id: role.id, type: 'role'});

		await rolesPage.goto();

		await rolesPage.selectRole(roleName);

		await rolePage.definePermissionsLink.click();

		const objectName = objectDefinition.name!;
		const actionPermissionName = `action.${actionName}`;

		await roleDefinePermissionsPage.changePermission(
			objectName,
			actionPermissionName,
			true
		);

		await roleDefinePermissionsPage.changePermission(
			objectName,
			actionPermissionName,
			false
		);
	}
);

test(
	'LPD-78504 Cannot leave action name blank',
	{tag: '@LPD-78504'},
	async ({apiHelpers, editObjectActionPage, page, viewObjectActionsPage}) => {

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

		await iframe.getByPlaceholder('Text to translate').fill('Action Label');

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
	async ({apiHelpers, editObjectActionPage, page, viewObjectActionsPage}) => {

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

		await iframe.getByPlaceholder('Text to translate').fill('Action Label');

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
	async ({apiHelpers, editObjectActionPage, page, viewObjectActionsPage}) => {

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

		await iframe.getByPlaceholder('Text to translate').fill('Action Label');

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
	async ({apiHelpers, editObjectActionPage, page, viewObjectActionsPage}) => {

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

		await iframe.getByPlaceholder('Text to translate').fill('Action Label');

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
	async ({apiHelpers, editObjectActionPage, page, viewObjectActionsPage}) => {

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
	{tag: ['@LPD-78504', '@LPS-173773']},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {

		// Migrated from Poshi: CanNotSeeDeactivatedStandaloneAction

		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
		});

		const fieldName = objectFields[0].name!;
		const fieldLabel = objectFields[0].label!;
		const actionLabel = `Action Label ${getRandomInt()}`;

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

		const {body: objectAction} =
			await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
				objectDefinition.externalReferenceCode!,
				{
					active: false,
					errorMessage: {en_US: 'Error message'},
					label: {en_US: actionLabel},
					name: `ActionName${getRandomInt()}`,
					objectActionExecutorKey: 'add-object-entry',
					objectActionTriggerKey: 'standalone',
					parameters: {
						objectDefinitionExternalReferenceCode:
							objectDefinition.externalReferenceCode,
						predefinedValues: [
							{
								businessType: 'Text',
								inputAsValue: true,
								label: fieldLabel,
								name: fieldName,
								value: 'New Entry Test',
							},
						],
					},
				}
			);

		apiHelpers.data.push({id: objectAction.id, type: 'objectAction'});

		const applicationName =
			'c/' + objectDefinition.name!.toLowerCase() + 's';

		await apiHelpers.objectEntry.postObjectEntry(
			{[fieldName]: 'Entry Test'},
			applicationName
		);

		await viewObjectEntriesPage.goto(objectDefinition.className!);

		await viewObjectEntriesPage.frontendDatasetActions.click();

		await expect(
			page.getByRole('menuitem', {name: actionLabel})
		).toBeHidden();
	}
);

test(
	'LPD-78504 Can reactivate an action',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectActionsPage}) => {

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
	async ({apiHelpers, page, viewObjectActionsPage}) => {

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
	{tag: ['@LPD-78504', '@LPS-156343']},
	async ({apiHelpers, site: _site}) => {

		// Migrated from Poshi: CanTriggerActionAfterDisablingExpression

		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
		});

		const fieldName = objectFields[0].name!;
		const fieldLabel = objectFields[0].label!;
		const predefinedValue = `New object entry value ${getRandomInt()}`;

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

		const {body: objectAction} =
			await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
				objectDefinition.externalReferenceCode!,
				{
					active: true,
					conditionExpression: `${fieldName} == 'Entry Test'`,
					label: {en_US: 'Custom Action Label'},
					name: `customAction${getRandomInt()}`,
					objectActionExecutorKey: 'add-object-entry',
					objectActionTriggerKey: 'onAfterAdd',
					parameters: {
						objectDefinitionExternalReferenceCode:
							objectDefinition.externalReferenceCode,
						predefinedValues: [
							{
								businessType: 'Text',
								inputAsValue: true,
								label: fieldLabel,
								name: fieldName,
								value: predefinedValue,
							},
						],
					},
				}
			);

		apiHelpers.data.push({id: objectAction.id, type: 'objectAction'});

		await objectActionAPIClient.patchObjectAction(objectAction.id!, {
			conditionExpression: '',
		});

		const applicationName =
			'c/' + objectDefinition.name!.toLowerCase() + 's';

		await apiHelpers.objectEntry.postObjectEntry(
			{[fieldName]: 'Different Value'},
			applicationName
		);

		const entries =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				applicationName
			);

		const autoCreatedEntry = entries.items.find(
			(item: any) => item[fieldName] === predefinedValue
		);

		expect(autoCreatedEntry).toBeTruthy();
	}
);

test(
	'LPD-78504 Can trigger action with expression by adding an entry',
	{tag: ['@LPD-78504', '@LPS-156320']},
	async ({apiHelpers, site: _site}) => {

		// Migrated from Poshi: CanTriggerActionWithExpressionByAddingEntry

		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
		});

		const fieldName = objectFields[0].name!;
		const fieldLabel = objectFields[0].label!;
		const predefinedValue = `Add Entry Test ${getRandomInt()}`;

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

		const {body: objectAction} =
			await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
				objectDefinition.externalReferenceCode!,
				{
					active: true,
					conditionExpression: `${fieldName} == 'Entry Test'`,
					label: {en_US: 'Action Label'},
					name: `customAction${getRandomInt()}`,
					objectActionExecutorKey: 'add-object-entry',
					objectActionTriggerKey: 'onAfterAdd',
					parameters: {
						objectDefinitionExternalReferenceCode:
							objectDefinition.externalReferenceCode,
						predefinedValues: [
							{
								businessType: 'Text',
								inputAsValue: true,
								label: fieldLabel,
								name: fieldName,
								value: predefinedValue,
							},
						],
					},
				}
			);

		apiHelpers.data.push({id: objectAction.id, type: 'objectAction'});

		const applicationName =
			'c/' + objectDefinition.name!.toLowerCase() + 's';

		await apiHelpers.objectEntry.postObjectEntry(
			{[fieldName]: 'Entry Test'},
			applicationName
		);

		const entries =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				applicationName
			);

		const autoCreatedEntry = entries.items.find(
			(item: any) => item[fieldName] === predefinedValue
		);

		expect(autoCreatedEntry).toBeTruthy();
	}
);

test(
	'LPD-78504 Can trigger action with expression by deleting an entry',
	{tag: ['@LPD-78504', '@LPS-173218']},
	async ({apiHelpers, site: _site}) => {

		// Migrated from Poshi: CanTriggerActionWithExpressionByDeletingEntry

		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
		});

		const fieldName = objectFields[0].name!;
		const fieldLabel = objectFields[0].label!;
		const predefinedValue = `New Entry After Object Delete ${getRandomInt()}`;

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

		const {body: objectAction} =
			await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
				objectDefinition.externalReferenceCode!,
				{
					active: true,
					conditionExpression: `${fieldName} == 'Entry Test'`,
					label: {en_US: 'Action Label Delete'},
					name: `customAction${getRandomInt()}`,
					objectActionExecutorKey: 'add-object-entry',
					objectActionTriggerKey: 'onAfterDelete',
					parameters: {
						objectDefinitionExternalReferenceCode:
							objectDefinition.externalReferenceCode,
						predefinedValues: [
							{
								businessType: 'Text',
								inputAsValue: true,
								label: fieldLabel,
								name: fieldName,
								value: predefinedValue,
							},
						],
					},
				}
			);

		apiHelpers.data.push({id: objectAction.id, type: 'objectAction'});

		const applicationName =
			'c/' + objectDefinition.name!.toLowerCase() + 's';

		const entry = await apiHelpers.objectEntry.postObjectEntry(
			{[fieldName]: 'Entry Test'},
			applicationName
		);

		await apiHelpers.objectEntry.deleteObjectEntry(
			applicationName,
			String(entry.id)
		);

		const entries =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				applicationName
			);

		const autoCreatedEntry = entries.items.find(
			(item: any) => item[fieldName] === predefinedValue
		);

		expect(autoCreatedEntry).toBeTruthy();
	}
);

test(
	'LPD-78504 Can trigger action with expression by updating an entry',
	{tag: ['@LPD-78504', '@LPS-173219']},
	async ({apiHelpers, site: _site}) => {

		// Migrated from Poshi: CanTriggerActionWithExpressionByUpdatingEntry

		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
		});

		const fieldName = objectFields[0].name!;
		const fieldLabel = objectFields[0].label!;
		const predefinedValue = `New Entry Test ${getRandomInt()}`;

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

		const {body: objectAction} =
			await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
				objectDefinition.externalReferenceCode!,
				{
					active: true,
					conditionExpression: `${fieldName} == 'Entry Test'`,
					label: {en_US: 'Custom Update Action'},
					name: `customAction${getRandomInt()}`,
					objectActionExecutorKey: 'add-object-entry',
					objectActionTriggerKey: 'onAfterUpdate',
					parameters: {
						objectDefinitionExternalReferenceCode:
							objectDefinition.externalReferenceCode,
						predefinedValues: [
							{
								businessType: 'Text',
								inputAsValue: true,
								label: fieldLabel,
								name: fieldName,
								value: predefinedValue,
							},
						],
					},
				}
			);

		apiHelpers.data.push({id: objectAction.id, type: 'objectAction'});

		const applicationName =
			'c/' + objectDefinition.name!.toLowerCase() + 's';

		const entry = await apiHelpers.objectEntry.postObjectEntry(
			{[fieldName]: 'Entry'},
			applicationName
		);

		await apiHelpers.objectEntry.patchObjectEntry(
			{[fieldName]: 'Entry Test'},
			applicationName,
			Number(entry.id)
		);

		const entries =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				applicationName
			);

		const autoCreatedEntry = entries.items.find(
			(item: any) => item[fieldName] === predefinedValue
		);

		expect(autoCreatedEntry).toBeTruthy();
	}
);

test(
	'LPD-78504 Can trigger standalone action for site scoped object',
	{tag: ['@LPD-78504', '@LPS-172918']},
	async ({apiHelpers, page, site, viewObjectEntriesPage}) => {

		// Migrated from Poshi: CanTriggerStandaloneActionForSiteScopedObject

		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
		});

		const fieldName = objectFields[0].name!;
		const fieldLabel = objectFields[0].label!;
		const actionLabel = `Action ${getRandomInt()}`;
		const predefinedValue = `New Entry Test ${getRandomInt()}`;

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields,
				panelCategoryKey: 'site_administration.content',
				scope: 'site',
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
					errorMessage: {en_US: 'Error Message'},
					label: {en_US: actionLabel},
					name: `ActionName${getRandomInt()}`,
					objectActionExecutorKey: 'add-object-entry',
					objectActionTriggerKey: 'standalone',
					parameters: {
						objectDefinitionExternalReferenceCode:
							objectDefinition.externalReferenceCode,
						predefinedValues: [
							{
								businessType: 'Text',
								inputAsValue: true,
								label: fieldLabel,
								name: fieldName,
								value: predefinedValue,
							},
						],
					},
				}
			);

		apiHelpers.data.push({id: objectAction.id, type: 'objectAction'});

		const applicationName =
			'c/' + objectDefinition.name!.toLowerCase() + 's';

		await apiHelpers.objectEntry.postObjectEntry(
			{[fieldName]: 'Entry'},
			applicationName,
			String(site.id)
		);

		await viewObjectEntriesPage.goto(
			objectDefinition.className!,
			'en',
			site.friendlyUrlPath
		);

		await viewObjectEntriesPage.frontendDatasetActions.click();

		await page.getByRole('menuitem', {name: actionLabel}).click();

		await page.waitForTimeout(2000);

		const entries =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntriesByScope(
				applicationName,
				String(site.id)
			);

		const autoCreatedEntry = entries.items.find(
			(item: any) => item[fieldName] === predefinedValue
		);

		expect(autoCreatedEntry).toBeTruthy();
	}
);

test(
	'LPD-78504 Can trigger standalone action with permission',
	{tag: ['@LPD-78504', '@LPS-169994']},
	async ({
		apiHelpers,
		page,
		roleDefinePermissionsPage,
		rolePage,
		rolesPage,
		viewObjectEntriesPage,
	}) => {

		// Migrated from Poshi: CanTriggerStandaloneActionWithPermission

		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
		});

		const fieldName = objectFields[0].name!;
		const fieldLabel = objectFields[0].label!;
		const actionLabel = `Action Label ${getRandomInt()}`;
		const actionName = `ActionName${getRandomInt()}`;
		const predefinedValue = `New Entry Test ${getRandomInt()}`;

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

		const {body: objectAction} =
			await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
				objectDefinition.externalReferenceCode!,
				{
					active: true,
					errorMessage: {en_US: 'Error Message'},
					label: {en_US: actionLabel},
					name: actionName,
					objectActionExecutorKey: 'add-object-entry',
					objectActionTriggerKey: 'standalone',
					parameters: {
						objectDefinitionExternalReferenceCode:
							objectDefinition.externalReferenceCode,
						predefinedValues: [
							{
								businessType: 'Text',
								inputAsValue: true,
								label: fieldLabel,
								name: fieldName,
								value: predefinedValue,
							},
						],
					},
				}
			);

		apiHelpers.data.push({id: objectAction.id, type: 'objectAction'});

		const applicationName =
			'c/' + objectDefinition.name!.toLowerCase() + 's';

		await apiHelpers.objectEntry.postObjectEntry(
			{[fieldName]: 'Entry Test'},
			applicationName
		);

		const company =
			await apiHelpers.jsonWebServicesCompany.getCompanyByWebId(
				'liferay.com'
			);

		const [, classNameSuffix] = objectDefinition.className!.split('#');

		const objectPortletId = `com_liferay_object_web_internal_object_definitions_portlet_ObjectDefinitionsPortlet_${classNameSuffix}`;

		const roleName = `Regular Role ${getRandomInt()}`;

		const role = await apiHelpers.headlessAdminUser.postRole({
			name: roleName,
			rolePermissions: [
				{
					actionIds: ['VIEW_CONTROL_PANEL'],
					primaryKey: String(company.companyId),
					resourceName: '90',
					scope: 1,
				},
				{
					actionIds: ['ACCESS_IN_CONTROL_PANEL', 'VIEW'],
					primaryKey: String(company.companyId),
					resourceName: objectPortletId,
					scope: 1,
				},
				{
					actionIds: ['VIEW'],
					primaryKey: String(company.companyId),
					resourceName: `com.liferay.object.model.ObjectDefinition#${classNameSuffix}`,
					scope: 1,
				},
			],
		});

		apiHelpers.data.push({id: role.id, type: 'role'});

		await rolesPage.goto();

		await rolesPage.selectRole(roleName);

		await rolePage.definePermissionsLink.click();

		await roleDefinePermissionsPage.changePermission(
			objectDefinition.name!,
			`action.${actionName}`,
			true
		);

		const user = await apiHelpers.headlessAdminUser.postUserAccount();

		userData[user.alternateName] = {
			name: user.givenName,
			password: 'test',
			surname: user.familyName,
		};

		await apiHelpers.headlessAdminUser.assignUserToRole(
			role.externalReferenceCode,
			user.id
		);

		await performLogout(page);

		await performLoginViaApi({page, screenName: user.alternateName});

		await viewObjectEntriesPage.goto(objectDefinition.className!);

		await viewObjectEntriesPage.frontendDatasetActions.click();

		await page.getByRole('menuitem', {name: actionLabel}).click();

		await page.waitForTimeout(2000);

		await performLogout(page);

		await performLoginViaApi({page, screenName: 'test'});

		const entries =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				applicationName
			);

		const autoCreatedEntry = entries.items.find(
			(item: any) => item[fieldName] === predefinedValue
		);

		expect(autoCreatedEntry).toBeTruthy();
	}
);

test(
	'LPD-78504 Can update account entry after creating account entry via action',
	{tag: ['@LPD-78504', '@LPS-173537']},
	async ({apiHelpers, site: _site}) => {

		// Migrated from Poshi: CanUpdateAccountEntryAfterCreatingAccountEntry

		const initialAccountName = `Account Name ${getRandomInt()}`;
		const updatedAccountName = `Updated Accounts Name ${getRandomInt()}`;

		const objectActionAPIClient =
			await apiHelpers.buildRestClient(ObjectActionAPI);

		const {body: objectAction} =
			await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
				'L_ACCOUNT',
				{
					active: true,
					label: {en_US: 'Custom Action'},
					name: `customAction${getRandomInt()}`,
					objectActionExecutorKey: 'update-object-entry',
					objectActionTriggerKey: 'onAfterAdd',
					parameters: {
						objectDefinitionExternalReferenceCode: 'L_ACCOUNT',
						predefinedValues: [
							{
								businessType: 'Text',
								inputAsValue: true,
								label: {en_US: 'Name'},
								name: 'name',
								value: updatedAccountName,
							},
						],
						system: true,
					},
				}
			);

		apiHelpers.data.push({id: objectAction.id, type: 'objectAction'});

		await apiHelpers.headlessAdminUser.postAccount({
			name: initialAccountName,
			type: 'person',
		});

		const updatedAccount =
			await apiHelpers.headlessAdminUser.getAccountByName(
				updatedAccountName
			);

		expect(updatedAccount).toBeTruthy();

		const oldAccount =
			await apiHelpers.headlessAdminUser.getAccountByName(
				initialAccountName
			);

		expect(oldAccount).toBeFalsy();
	}
);

test(
	'LPD-78504 Can update an action',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectActionsPage}) => {

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
	{tag: ['@LPD-78504', '@LPS-173537']},
	async ({apiHelpers}) => {

		// Migrated from Poshi: CanUpdateCommerceProductGroupEntryAfterCreatingCommerceProductEntry

		const updatedTitle = `Product Group Updated ${getRandomInt()}`;

		await miniumSetUp(apiHelpers);

		const objectActionAPIClient =
			await apiHelpers.buildRestClient(ObjectActionAPI);

		const {body: objectAction} =
			await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
				'L_COMMERCE_PRODUCT_GROUP',
				{
					active: true,
					label: {en_US: 'Custom Action'},
					name: `customAction${getRandomInt()}`,
					objectActionExecutorKey: 'update-object-entry',
					objectActionTriggerKey: 'onAfterAdd',
					parameters: {
						objectDefinitionExternalReferenceCode:
							'L_COMMERCE_PRODUCT_GROUP',
						predefinedValues: [
							{
								businessType: 'Text',
								inputAsValue: true,
								label: {en_US: 'Title'},
								name: 'title',
								value: updatedTitle,
							},
						],
						system: true,
					},
				}
			);

		apiHelpers.data.push({id: objectAction.id, type: 'objectAction'});

		const productGroup = await apiHelpers.objectEntry.postObjectEntry(
			{name: `Product Group ${getRandomInt()}`, title: 'Original Title'},
			'c/commerceproductgroups'
		);

		apiHelpers.data.push({id: productGroup.id, type: 'objectEntry'});

		const updatedGroup = await apiHelpers.objectEntry.getObjectEntryById(
			'c/commerceproductgroups',
			String(productGroup.id)
		);

		expect(updatedGroup.title).toBe(updatedTitle);
	}
);

test(
	'LPD-78504 Can use expression with Groovy Script action',
	{tag: ['@LPD-78504', '@LPS-156346']},
	async ({
		apiHelpers,
		page,
		scriptManagementPage,
		site: _site,
		viewObjectActionsPage,
	}) => {

		// Migrated from Poshi: CanUseExpressionWithGroovyScript

		await scriptManagementPage.enableScriptManagementConfiguration();

		await scriptManagementPage.page.waitForTimeout(2000);

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						DBType: 'Integer',
						businessType: 'Integer',
						externalReferenceCode: 'customInteger',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'Custom Integer'},
						listTypeDefinitionId: 0,
						localized: false,
						name: 'customInteger',
						required: false,
						system: false,
						type: 'Integer',
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
					conditionExpression: 'customInteger == 5',
					errorMessage: {en_US: ''},
					label: {en_US: 'Action Label'},
					name: `customAction${getRandomInt()}`,
					objectActionExecutorKey: 'groovy',
					objectActionTriggerKey: 'onAfterAdd',
					parameters: {
						lineCount: 1,
						script: "println 'Success'",
					},
				}
			);

		apiHelpers.data.push({id: objectAction.id, type: 'objectAction'});

		const applicationName =
			'c/' + objectDefinition.name!.toLowerCase() + 's';

		await apiHelpers.objectEntry.postObjectEntry(
			{customInteger: 5},
			applicationName
		);

		await viewObjectActionsPage.goto(objectDefinition.label!['en_US']);

		await page.reload();

		await expect(viewObjectActionsPage.lastExecutionCell).toContainText(
			'Success'
		);
	}
);

test(
	'LPD-78504 Can use expression with webhook action',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectActionsPage}) => {

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
	{tag: ['@LPD-78504', '@LPS-173774']},
	async ({apiHelpers, roleDefinePermissionsPage, rolePage, rolesPage}) => {

		// Migrated from Poshi: CheckStandaloneActionPermissionOfUnpublishedObject

		const publishedObject =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: generateObjectFields({
					objectFieldBusinessTypes: ['Text'],
				}),
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: publishedObject.id,
			type: 'objectDefinition',
		});

		const unpublishedObjectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
		});

		const unpublishedObject =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: unpublishedObjectFields,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: unpublishedObject.id,
			type: 'objectDefinition',
		});

		const objectActionAPIClient =
			await apiHelpers.buildRestClient(ObjectActionAPI);

		const {body: objectAction} =
			await objectActionAPIClient.postObjectDefinitionByExternalReferenceCodeObjectAction(
				unpublishedObject.externalReferenceCode!,
				{
					active: true,
					errorMessage: {en_US: 'Error'},
					label: {en_US: 'Action Label'},
					name: `ActionName${getRandomInt()}`,
					objectActionExecutorKey: 'add-object-entry',
					objectActionTriggerKey: 'standalone',
					parameters: {
						objectDefinitionExternalReferenceCode:
							unpublishedObject.externalReferenceCode,
						predefinedValues: [
							{
								businessType: 'Text',
								inputAsValue: true,
								label: unpublishedObjectFields[0].label!,
								name: unpublishedObjectFields[0].name!,
								value: 'Test',
							},
						],
					},
				}
			);

		apiHelpers.data.push({id: objectAction.id, type: 'objectAction'});

		const roleName = `Regular Role ${getRandomInt()}`;

		const role = await apiHelpers.headlessAdminUser.postRole({
			name: roleName,
		});

		apiHelpers.data.push({id: role.id, type: 'role'});

		await rolesPage.goto();

		await rolesPage.selectRole(roleName);

		await rolePage.definePermissionsLink.click();

		await roleDefinePermissionsPage.searchInput.click();
		await roleDefinePermissionsPage.searchInput.fill(publishedObject.name!);

		await expect(
			roleDefinePermissionsPage.menuItem(publishedObject.name!)
		).toBeVisible();

		await roleDefinePermissionsPage.searchInput.fill(
			unpublishedObject.name!
		);

		await expect(
			roleDefinePermissionsPage.menuItem(unpublishedObject.name!)
		).not.toBeVisible();
	}
);

test(
	'LPD-78504 Can verify action name is required',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectActionsPage}) => {

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
	async ({editObjectActionPage, page, viewObjectActionsPage}) => {

		// Migrated from: VerifyTheConditionCardAreHidden
		// LPS-171802 - Verify if the Condition card is hidden when using the trigger On Subscription Status Update

		await viewObjectActionsPage.goto('Commerce Order');

		await viewObjectActionsPage.openObjectActionSidePanel();

		const iframe = page.frameLocator('iframe');

		await iframe.getByPlaceholder('Text to translate').fill('Action Label');

		await editObjectActionPage.openActionBuilderTab();

		await editObjectActionPage.inputWhenCombo.click();
		await iframe
			.getByRole('option', {name: 'On Subscription Status Update'})
			.click();

		await expect(iframe.getByText('Condition')).toBeHidden();
	}
);
