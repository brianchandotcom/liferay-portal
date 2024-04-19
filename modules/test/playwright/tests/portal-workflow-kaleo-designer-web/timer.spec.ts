/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {workflowPagesTest} from '../../fixtures/workflowPagesTest';
import {getRandomInt} from '../../utils/getRandomInt';

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

test.afterEach(async ({apiHelpers}) => {
	await apiHelpers.headlessAdminWorkflow.deleteWorkflowDefinition(
		workflowDefinitionId
	);
});

test('LPD-16281 can create timer notifications', async ({
	diagramViewPage,
	nodePropertiesSidebarPage,
	processBuilderPage,
	timerPage,
}) => {
	await processBuilderPage.goto();

	await processBuilderPage.clickWorkflowDefinitionName(
		workflowDefinitionName
	);

	await diagramViewPage.clickReviewNodeLink();

	await nodePropertiesSidebarPage.createTimerNotification(timerNotifications);

	await processBuilderPage.switchToSourceViewAndBackToDiagram();

	await diagramViewPage.clickReviewNodeLink();

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

	await diagramViewPage.clickReviewNodeLink();

	await expect(timerOption).toBeVisible();

	await timerOption.click();

	await timerPage.assertTimerActionNotificationFields(timerNotifications);
});

test('LPD-21221 can create timer reassignments role type reassignment type', async ({
	actionReassignmentPage,
	diagramViewPage,
	nodePropertiesSidebarPage,
	processBuilderPage,
}) => {
	await processBuilderPage.goto();

	await processBuilderPage.clickWorkflowDefinitionName(
		workflowDefinitionName
	);

	await diagramViewPage.clickReviewNodeLink();

	await nodePropertiesSidebarPage.createTimerReassignmentRoleType(roleTypes);

	await processBuilderPage.switchToSourceViewAndBackToDiagram();

	await diagramViewPage.clickReviewNodeLink();

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

	await diagramViewPage.clickReviewNodeLink();

	await expect(timerOption).toBeVisible();

	await timerOption.click();

	await actionReassignmentPage.assertRoleTypeReassignmentType(roleTypes);
});
