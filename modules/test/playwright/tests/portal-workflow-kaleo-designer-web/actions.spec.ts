/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {workflowPagesTest} from '../../fixtures/workflowPagesTest';
import postSingleApproverCopy from './utils/postSingleApproverCopy';

export const test = mergeTests(apiHelpersTest, loginTest(), workflowPagesTest);

let workflowDefinitionId: number;
let workflowDefinitionName: string;

test.beforeEach(async ({apiHelpers, scriptManagementPage}) => {
	const workFlowDefinition = await postSingleApproverCopy(apiHelpers);

	workflowDefinitionId = workFlowDefinition.id;
	workflowDefinitionName = workFlowDefinition.name;

	await scriptManagementPage.enableScriptManagementConfiguration();
});

test.afterEach(async ({apiHelpers, scriptManagementPage}) => {
	await apiHelpers.headlessAdminWorkflow.deleteWorkflowDefinition(
		workflowDefinitionId
	);

	await scriptManagementPage.enableScriptManagementConfiguration();
});

test('can see groovy and java options in the action type select when script management configuration is enabled', async ({
	actionPage,
	diagramViewPage,
	nodePropertiesSidebarPage,
	processBuilderPage,
}) => {
	await processBuilderPage.goto();

	await processBuilderPage.clickWorkflowDefinitionName(
		workflowDefinitionName
	);

	await diagramViewPage.clickNode('review');

	await nodePropertiesSidebarPage.addActionButton.click();

	expect(await actionPage.getTypeSelectOption('java')).not.toBeNull();
	expect(await actionPage.getTypeSelectOption('groovy')).not.toBeNull();
});

test('cannot see groovy and java options in the action type select when script management configuration is disabled', async ({
	actionPage,
	diagramViewPage,
	nodePropertiesSidebarPage,
	processBuilderPage,
	scriptManagementPage,
}) => {
	await scriptManagementPage.disableScriptManagementConfiguration();

	await processBuilderPage.goto();

	await processBuilderPage.clickWorkflowDefinitionName(
		workflowDefinitionName
	);

	await diagramViewPage.clickNode('review');

	await nodePropertiesSidebarPage.addActionButton.click();

	expect(await actionPage.getTypeSelectOption('java')).toBeNull();
	expect(await actionPage.getTypeSelectOption('groovy')).toBeNull();
});

test('cannot save a workflow definition that has a groovy action when script management configuration is disabled', async ({
	actionPage,
	diagramViewPage,
	nodePropertiesSidebarPage,
	page,
	processBuilderPage,
	scriptManagementPage,
}) => {
	await processBuilderPage.goto();

	await processBuilderPage.clickWorkflowDefinitionName(
		workflowDefinitionName
	);

	await diagramViewPage.clickNode('review');

	await nodePropertiesSidebarPage.addActionButton.click();

	await actionPage.fillWorkflowAction(
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

test('cannot save a workflow definition that has a java action when the script management configuration is disabled', async ({
	actionPage,
	diagramViewPage,
	nodePropertiesSidebarPage,
	page,
	processBuilderPage,
	scriptManagementPage,
}) => {
	await processBuilderPage.goto();

	await processBuilderPage.clickWorkflowDefinitionName(
		workflowDefinitionName
	);

	await diagramViewPage.clickNode('review');

	await nodePropertiesSidebarPage.addActionButton.click();

	await actionPage.fillWorkflowAction('Java Action', 'scriptTest', 'Java');

	await diagramViewPage.saveWorkflowDefinition();

	await scriptManagementPage.disableScriptManagementConfiguration();

	await processBuilderPage.goto();

	await processBuilderPage.clickWorkflowDefinitionName(
		workflowDefinitionName
	);

	await diagramViewPage.saveWorkflowDefinition();

	await expect(page.getByText('Error Updating Definition')).toBeVisible();
});
