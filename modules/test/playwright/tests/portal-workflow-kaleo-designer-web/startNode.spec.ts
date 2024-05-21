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
