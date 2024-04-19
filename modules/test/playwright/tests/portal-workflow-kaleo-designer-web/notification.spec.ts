/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {workflowPagesTest} from '../../fixtures/workflowPagesTest';
import {getRandomInt} from '../../utils/getRandomInt';

export const test = mergeTests(apiHelpersTest, loginTest(), workflowPagesTest);

const notification = {
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

	await diagramViewPage.clickReviewNodeLink();

	await nodePropertiesSidebarPage.deleteNotifications();

	await nodePropertiesSidebarPage.createNotification(notification);

	await processBuilderPage.switchToSourceViewAndBackToDiagram();

	await diagramViewPage.clickReviewNodeLink();

	const notificationEntry = processBuilderPage.page.getByRole('link', {
		name: 'Role Type Notification',
	});

	await expect(notificationEntry).toBeVisible();

	await notificationEntry.click();

	await notificationSectionPage.assertNotificationSectionFields(
		0,
		notification
	);

	await diagramViewPage.saveWorkflowDefinition();

	await diagramViewPage.goBack();

	await processBuilderPage.clickWorkflowDefinitionName(
		workflowDefinitionName
	);

	await diagramViewPage.clickReviewNodeLink();

	await expect(notificationEntry).toBeVisible();

	await notificationEntry.click();

	await notificationSectionPage.assertNotificationSectionFields(
		0,
		notification
	);
});
