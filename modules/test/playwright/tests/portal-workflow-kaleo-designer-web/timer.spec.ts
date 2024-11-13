/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {workflowPagesTest} from '../../fixtures/workflowPagesTest';
import {getRandomInt} from '../../utils/getRandomInt';
import postSingleApproverCopy from './utils/postSingleApproverCopy';

export const test = mergeTests(apiHelpersTest, loginTest(), workflowPagesTest);

const roleTypes = [
	{
		autocreate: false,
		roleName: 'Account Manager',
		roleType: 'Organization',
	},
	{
		autocreate: true,
		roleName: 'Administrator',
		roleType: 'Regular',
	},
] as RoleType[];

const timerNotifications = [
	{
		notificationDescription: 'notificationDescription0' + getRandomInt(),
		notificationName: 'notificationName0' + getRandomInt(),
		notificationTypeEmail: true,
		notificationTypeUser: true,
		recipientType: 'role',
		recipientTypeData: {
			roleName: 'Account Manager',
		},
		template: 'template0' + getRandomInt(),
		templateLanguage: 'freemarker',
	},
	{
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
	},
] as Notification[];

let workflowDefinitionId: number;
let workflowDefinitionName: string;

test.beforeEach(async ({apiHelpers}) => {
	const workFlowDefinition = await postSingleApproverCopy(apiHelpers);

	workflowDefinitionId = workFlowDefinition.id;
	workflowDefinitionName = workFlowDefinition.name;
});

test.afterEach(async ({apiHelpers, scriptManagementPage}) => {
	await apiHelpers.headlessAdminWorkflow.deleteWorkflowDefinition(
		workflowDefinitionId
	);

	await scriptManagementPage.disableScriptManagementConfiguration();
});

test('can create timer notifications', async ({
	diagramViewPage,
	nodePropertiesSidebarPage,
	processBuilderPage,
	scriptManagementPage,
	timerPage,
}) => {
	await scriptManagementPage.enableScriptManagementConfiguration();

	await processBuilderPage.goto();

	await processBuilderPage.clickWorkflowDefinitionName(
		workflowDefinitionName
	);

	await diagramViewPage.clickNode('review');

	await nodePropertiesSidebarPage.createTimerNotification(timerNotifications);

	await processBuilderPage.switchToSourceViewAndBackToDiagram();

	await diagramViewPage.clickNode('review');

	const timerOption = processBuilderPage.page.getByRole('link', {
		name: 'Duration: 3 week',
	});

	await expect(timerOption).toBeVisible();

	await timerOption.click();

	await timerPage.assertTimerActionNotificationFields(timerNotifications);

	await diagramViewPage.saveWorkflowDefinition();

	await diagramViewPage.goBack();

	await processBuilderPage.clickWorkflowDefinitionName(
		workflowDefinitionName
	);

	await diagramViewPage.clickNode('review');

	await expect(timerOption).toBeVisible();

	await timerOption.click();

	await timerPage.assertTimerActionNotificationFields(timerNotifications);
});

test('can create timer reassignments role type reassignment type', async ({
	actionReassignmentPage,
	diagramViewPage,
	nodePropertiesSidebarPage,
	processBuilderPage,
}) => {
	await processBuilderPage.goto();

	await processBuilderPage.clickWorkflowDefinitionName(
		workflowDefinitionName
	);

	await diagramViewPage.clickNode('review');

	await nodePropertiesSidebarPage.createTimerReassignmentRoleType(roleTypes);

	await processBuilderPage.switchToSourceViewAndBackToDiagram();

	await diagramViewPage.clickNode('review');

	const timerOption = processBuilderPage.page.getByRole('link', {
		name: 'Duration: 3 week',
	});

	await expect(timerOption).toBeVisible();

	await timerOption.click();

	await actionReassignmentPage.assertRoleTypeReassignmentType(roleTypes);

	await diagramViewPage.saveWorkflowDefinition();

	await diagramViewPage.goBack();

	await processBuilderPage.clickWorkflowDefinitionName(
		workflowDefinitionName
	);

	await diagramViewPage.clickNode('review');

	await expect(timerOption).toBeVisible();

	await timerOption.click();

	await actionReassignmentPage.assertRoleTypeReassignmentType(roleTypes);
});

test('cannot save a workflow definition that has a timer action with groovy script when script management configuration is disabled', async ({
	diagramViewPage,
	nodePropertiesSidebarPage,
	page,
	processBuilderPage,
	scriptManagementPage,
}) => {
	await scriptManagementPage.enableScriptManagementConfiguration();

	await processBuilderPage.goto();

	await processBuilderPage.clickWorkflowDefinitionName(
		workflowDefinitionName
	);

	await diagramViewPage.clickNode('review');

	await nodePropertiesSidebarPage.createTimerAction(
		'Groovy Action',
		'scriptTest',
		'Groovy'
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

test('cannot save a workflow definition that has a Timer Action with java script when script management configuration is disabled', async ({
	diagramViewPage,
	nodePropertiesSidebarPage,
	page,
	processBuilderPage,
	scriptManagementPage,
}) => {
	await scriptManagementPage.enableScriptManagementConfiguration();

	await processBuilderPage.goto();

	await processBuilderPage.clickWorkflowDefinitionName(
		workflowDefinitionName
	);

	await diagramViewPage.clickNode('review');

	await nodePropertiesSidebarPage.createTimerAction(
		'Java Action',
		'scriptTest',
		'Java'
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
