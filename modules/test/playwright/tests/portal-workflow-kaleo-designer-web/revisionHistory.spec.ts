/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import moment from 'moment';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {workflowPagesTest} from '../../fixtures/workflowPagesTest';
import {getRandomInt} from '../../utils/getRandomInt';
import performLogin, {performLogout} from '../../utils/performLogin';

export const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPD-29635': true,
	}),
	loginTest(),
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

test.afterEach(async ({apiHelpers}) => {
	await apiHelpers.headlessAdminWorkflow.deleteWorkflowDefinition(
		workflowDefinitionId
	);
});

test.describe('Revision History tab', () => {
	test('can create a new version by saving and publishing the workflow definition', async ({
		apiHelpers,
		definitionInfoPage,
		diagramViewPage,
		nodePropertiesSidebarPage,
		page,
		processBuilderPage,
	}) => {
		await processBuilderPage.goto();

		await processBuilderPage.clickWorkflowDefinitionName(
			workflowDefinitionName
		);

		await nodePropertiesSidebarPage.dragNodeToDiagram('Task', 200, 200);

		await nodePropertiesSidebarPage.nodeLabelInput.fill('Task 1');

		await diagramViewPage.saveWorkflowDefinition();

		await diagramViewPage.definitionInfoButton.click();

		await definitionInfoPage.revisionHistoryTabButton.click();

		const newWorkflowDefinition =
			await apiHelpers.headlessAdminWorkflow.getWorkflowDefinitionByName(
				workflowDefinitionName
			);

		const createdDate = moment(newWorkflowDefinition.dateCreated).format(
			'MMM DD, YYYY, LT'
		);

		await expect(definitionInfoPage.getVersionLabel('2')).toBeVisible();
		await expect(
			definitionInfoPage.getDateAndUserFromVersion(
				createdDate,
				'Test Test'
			)
		).toBeVisible();

		const user =
			await apiHelpers.headlessAdminUser.getUserAccountByEmailAddress(
				'demo.company.admin@liferay.com'
			);

		await performLogout(page);

		await performLogin(page, user.alternateName);

		await processBuilderPage.goto();

		await processBuilderPage.clickWorkflowDefinitionName(
			workflowDefinitionName
		);

		await diagramViewPage.deleteNode('Task 1');

		await diagramViewPage.publishWorkflowDefinitionButton.click();

		await diagramViewPage.definitionInfoButton.click();

		await definitionInfoPage.revisionHistoryTabButton.click();

		const newWorkflowDefinition2 =
			await apiHelpers.headlessAdminWorkflow.getWorkflowDefinitionByName(
				workflowDefinitionName
			);

		const createdDate2 = moment(newWorkflowDefinition2.dateCreated).format(
			'MMM DD, YYYY, LT'
		);

		await expect(definitionInfoPage.getVersionLabel('3')).toBeVisible();
		await expect(
			definitionInfoPage.getDateAndUserFromVersion(
				createdDate2,
				user.name
			)
		).toBeVisible();
	});
});
