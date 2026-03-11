/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {ConfigurationTabPage} from '../../../pages/portal-workflow-kaleo-designer-web/ConfigurationTabPage';
import {MetricsPage} from '../../../pages/portal-workflow-metrics-web/MetricsPage';
import {waitForAlert} from '../../../utils/waitForAlert';
import {generateObjectFields} from './utils/generateObjectFields';

const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	objectPagesTest
);

// Migrated from ObjectActiveInactive.testcase

test.fixme(
	'LPD-78504 Verify that pending and completed Object entries with workflow are not displayed on the Workflow Metrics page when inactivated',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectDefinitionsPage}) => {
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

		// Configure Single Approver workflow for the object

		const configurationTabPage = new ConfigurationTabPage(page);

		await configurationTabPage.goTo();

		await configurationTabPage.assignWorkflowToAssetType(
			'Single Approver',
			objectDefinition.label['en_US']
		);

		// Add an entry via API (will be in pending workflow state)

		const applicationName =
			'c/' + objectDefinition.name.toLowerCase() + 's';
		const fieldName = objectFields[0].name!;

		await apiHelpers.objectEntry.postObjectEntry(
			{[fieldName!]: 'Entry 1'},
			applicationName
		);

		// Inactivate the object

		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.changeObjectActivateStatus(
			objectDefinition.name
		);

		await waitForAlert(page);

		// Navigate to Workflow Metrics and verify no pending items for Single Approver

		const metricsPage = new MetricsPage(page);

		await metricsPage.goTo();

		await metricsPage.chooseProcess('Single Approver');

		await expect(
			page.getByText('0', {exact: true}).first()
		).toBeVisible();

		// Reactivate the object and unassign workflow for cleanup

		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.changeObjectActivateStatus(
			objectDefinition.name
		);

		await waitForAlert(page);

		await configurationTabPage.goTo();

		await configurationTabPage.unassignWorkflowFromAssetType(
			objectDefinition.label['en_US']
		);
	}
);
