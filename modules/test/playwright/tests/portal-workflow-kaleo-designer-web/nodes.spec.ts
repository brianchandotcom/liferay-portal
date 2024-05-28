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

test.describe('Manage condition node', () => {
	test('check if condition node will not be visible when script management is disabled', async ({
		conditionNode,
		processBuilderPage,
		scriptManagementPage,
	}) => {
		await scriptManagementPage.disableScriptManagementConfiguration();

		await processBuilderPage.goto();

		await processBuilderPage.clickWorkflowDefinitionName(
			workflowDefinitionName
		);

		await expect(conditionNode.conditionNodeSideBarItem).not.toBeVisible();
	});

	test('cannot save workflow definition with condition node when script management is disabled', async ({
		conditionNode,
		diagramViewPage,
		page,
		processBuilderPage,
		scriptManagementPage,
	}) => {
		await scriptManagementPage.enableScriptManagementConfiguration();

		await processBuilderPage.goto();

		await processBuilderPage.clickWorkflowDefinitionName(
			workflowDefinitionName
		);

		await conditionNode.dragConditionNodeToDiagram();

		await conditionNode.fillConditionNodeFields(
			'Groovy Condition Node',
			'Groovy',
			'scriptTest'
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
});

test.describe('Manage start node', () => {
	test('can add and edit node into diagram by drag and drop', async ({
		diagramViewPage,
		nodePropertiesSidebarPage,
		processBuilderPage,
	}) => {
		const newLabel = 'Start Node Edited';

		await processBuilderPage.goto();

		await processBuilderPage.clickWorkflowDefinitionName(
			workflowDefinitionName
		);

		await nodePropertiesSidebarPage.dragNodeToDiagram('Start', 200, 200);

		await nodePropertiesSidebarPage.nodeLabelInput.fill(newLabel);

		await diagramViewPage.saveWorkflowDefinition();

		expect(nodePropertiesSidebarPage.nodeLabelInput).toHaveValue(newLabel);

		expect(
			diagramViewPage.diagramNodes.filter({hasText: newLabel})
		).toBeVisible();
	});
});
