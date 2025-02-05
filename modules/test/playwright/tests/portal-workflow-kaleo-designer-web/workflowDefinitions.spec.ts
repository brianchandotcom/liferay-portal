/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {workflowPagesTest} from '../../fixtures/workflowPagesTest';
import getRandomString from '../../utils/getRandomString';
import {getWorkflowDefinition} from './utils/getWorkflowDefinition';

export const test = mergeTests(apiHelpersTest, loginTest(), workflowPagesTest);

let workflowDefinitionIds: number[] = [];

test.afterEach(async ({apiHelpers}) => {
	for (const workflowDefinitionId of workflowDefinitionIds) {
		await apiHelpers.headlessAdminWorkflow.deleteWorkflowDefinition(
			workflowDefinitionId
		);
	}

	workflowDefinitionIds = [];
});

test('can create a workflow definition with a slash in the name tag', async ({
	apiHelpers,
	diagramViewPage,
	processBuilderPage,
}) => {
	const workflowDefinitionName =
		'WorkflowDefinitionName/' + getRandomString();

	const workflowDefinition =
		await apiHelpers.headlessAdminWorkflow.postWorkflowDefinitionSave(
			workflowDefinitionName,
			getWorkflowDefinition('basic')
		);

	workflowDefinitionIds.push(workflowDefinition.id);

	await processBuilderPage.goto();

	await processBuilderPage.clickWorkflowDefinitionName(
		workflowDefinitionName
	);

	await expect(diagramViewPage.workflowDefinitionTitle).toHaveValue(
		workflowDefinitionName
	);
});
