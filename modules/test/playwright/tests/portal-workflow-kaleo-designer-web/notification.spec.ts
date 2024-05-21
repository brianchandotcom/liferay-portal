/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {workflowPagesTest} from '../../fixtures/workflowPagesTest';
import {getRandomInt} from '../../utils/getRandomInt';

export const test = mergeTests(
	apiHelpersTest,
	loginTest(),
	featureFlagsTest({
		'LPD-11179': true,
	}),
	workflowPagesTest
);

const roleTypeNotification = {
	notificationDescription: 'notificationDescription0' + getRandomInt(),
	notificationName: 'Role Type Notification',
	notificationTypeEmail: true,
	notificationTypeUser: true,
	recipientType: 'roleType',
	recipientTypeData: {
		autocreate: false,
		roleName: 'Account Manager',
		roleType: 'Organization',
	},
	template: 'template0' + getRandomInt(),
	templateLanguage: 'freemarker',
} as Notification;

const scriptedRecipientNotification = {
	notificationDescription: 'notificationDescription1' + getRandomInt(),
	notificationName: 'notificationName1' + getRandomInt(),
	notificationTypeEmail: true,
	notificationTypeUser: true,
	recipientType: 'scriptedRecipient',
	recipientTypeData: {
		script: 'script' + getRandomInt(),
		scriptLanguage: 'groovy',
	},
	template: 'template1' + getRandomInt(),
	templateLanguage: 'text',
} as Notification;

let workflowDefinitionId: number;
let workflowDefinitionName: string;

test.beforeEach(async ({apiHelpers}) => {
	const singleApproverWorkflowDefinition =
		await apiHelpers.headlessAdminWorkflow.getWorkflowDefinitionByName(
			'Single Approver'
		);

	workflowDefinitionName = 'Copy of Single Approver' + getRandomInt();

	const workflowDefinition =
		await apiHelpers.headlessAdminWorkflow.postWorkflowDefinitionSave(
			workflowDefinitionName,
			singleApproverWorkflowDefinition
		);

	workflowDefinitionId = workflowDefinition.id;
});

test.afterEach(async ({apiHelpers, scriptManagementPage}) => {
	await apiHelpers.headlessAdminWorkflow.deleteWorkflowDefinition(
		workflowDefinitionId
	);

	await scriptManagementPage.enableScriptManagementConfiguration();
});

test('cannot see scripted recipient option when script management configuration is disabled', async ({
	nodePropertiesSidebarPage,
	notificationPage,
	processBuilderPage,
	scriptManagementPage,
}) => {
	await scriptManagementPage.disableScriptManagementConfiguration();

	await processBuilderPage.goto();

	await processBuilderPage.clickWorkflowDefinitionName(
		workflowDefinitionName
	);

	await nodePropertiesSidebarPage.dragNodeToDiagram('Task', 200, 200);

	await nodePropertiesSidebarPage.addNotificationButton.click();

	expect(
		await notificationPage.getRecipientTypeTypeOption('scriptedRecipient')
	).toBeNull();
});

test('cannot save a workflow definition with a scripted recipient notification when script management configuration is disabled', async ({
	diagramViewPage,
	nodePropertiesSidebarPage,
	notificationPage,
	page,
	processBuilderPage,
	scriptManagementPage,
}) => {
	await processBuilderPage.goto();

	await processBuilderPage.clickWorkflowDefinitionName(
		workflowDefinitionName
	);

	await nodePropertiesSidebarPage.dragNodeToDiagram('Task', 200, 200);

	await nodePropertiesSidebarPage.nodeLabelInput.fill('Notification Node');

	await nodePropertiesSidebarPage.addNotificationButton.click();

	await notificationPage.fillNotificationFields(
		scriptedRecipientNotification
	);

	await diagramViewPage.saveWorkflowDefinition();

	await scriptManagementPage.disableScriptManagementConfiguration();

	await processBuilderPage.goto();

	await processBuilderPage.clickWorkflowDefinitionName(
		workflowDefinitionName
	);

	await diagramViewPage.saveWorkflowDefinition();

	await expect(page.getByText('Error Updating Definition')).toBeVisible();
});

test('create a notification using role type', async ({
	diagramViewPage,
	nodePropertiesSidebarPage,
	notificationSectionPage,
	processBuilderPage,
}) => {
	await processBuilderPage.goto();

	await processBuilderPage.clickWorkflowDefinitionName(
		workflowDefinitionName
	);

	await diagramViewPage.clickNode('review');

	await nodePropertiesSidebarPage.deleteNotifications();

	await nodePropertiesSidebarPage.createNotification(roleTypeNotification);

	await processBuilderPage.switchToSourceViewAndBackToDiagram();

	await diagramViewPage.clickNode('review');

	const notificationEntry = processBuilderPage.page.getByRole('link', {
		name: 'Role Type Notification',
	});

	await expect(notificationEntry).toBeVisible();

	await notificationEntry.click();

	await notificationSectionPage.assertNotificationSectionFields(
		0,
		roleTypeNotification
	);

	await diagramViewPage.saveWorkflowDefinition();

	await diagramViewPage.goBack();

	await processBuilderPage.clickWorkflowDefinitionName(
		workflowDefinitionName
	);

	await diagramViewPage.clickNode('review');

	await expect(notificationEntry).toBeVisible();

	await notificationEntry.click();

	await notificationSectionPage.assertNotificationSectionFields(
		0,
		roleTypeNotification
	);
});
