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
import {FormBuilderPage} from '../../../pages/dynamic-data-mapping-form-web/FormBuilderPage';
import {FormBuilderSidePanelPage} from '../../../pages/dynamic-data-mapping-form-web/FormBuilderSidePanelPage';
import {FormSettingsModalPage} from '../../../pages/dynamic-data-mapping-form-web/FormSettingsModalPage';
import {ConfigurationTabPage} from '../../../pages/portal-workflow-kaleo-designer-web/ConfigurationTabPage';
import {MetricsPage} from '../../../pages/portal-workflow-metrics-web/MetricsPage';
import {WorkflowTasksPage} from '../../../pages/portal-workflow-task-web/WorkflowTasksPage';
import {getRandomInt} from '../../../utils/getRandomInt';
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

test.describe('Workflow Integration', () => {
	test(
		'Verify that the Object is not displayed on Process Builder settings before Published',
		{tag: '@LPS-135649'},
		async ({apiHelpers, page}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await page.goto(
				'/group/guest/~/control_panel/manage/-/workflow_configuration'
			);

			await expect(
				page.getByRole('heading', {name: objectDefinition.label['en_US']})
			).toBeHidden();
		}
	);

	test(
		'Verify that the Object is not displayed on Workflow settings from Site Menu before Published',
		{tag: '@LPS-135649'},
		async ({apiHelpers, page, site}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					panelCategoryKey: 'site_administration.content',
					scope: 'site',
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await page.goto(
				`/group${site.friendlyUrlPath}/~/control_panel/manage/-/workflow_configuration`
			);

			await expect(
				page.getByRole('heading', {name: objectDefinition.label['en_US']})
			).toBeHidden();
		}
	);

	test(
		'Verify that the Object is no longer displayed on the Workflow Process Builder page when inactivated',
		{tag: '@LPS-139005'},
		async ({apiHelpers, page, viewObjectDefinitionsPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.changeObjectActivateStatus(
				objectDefinition.name
			);

			await page.goto(
				'/group/guest/~/control_panel/manage/-/workflow_configuration'
			);

			await expect(
				page.getByRole('heading', {name: objectDefinition.label['en_US']})
			).toBeHidden();
		}
	);

	test(
		'Verify that the Object is no longer displayed on the Workflow Site Menu page when inactivated',
		{tag: '@LPS-139005'},
		async ({apiHelpers, page, site, viewObjectDefinitionsPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					panelCategoryKey: 'site_administration.content',
					scope: 'site',
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.changeObjectActivateStatus(
				objectDefinition.name
			);

			await page.goto(
				`/group${site.friendlyUrlPath}/~/control_panel/manage/-/workflow_configuration`
			);

			await expect(
				page.getByRole('heading', {name: objectDefinition.label['en_US']})
			).toBeHidden();
		}
	);

	test(
		'Verify that the Object is displayed again on the Workflow Process Builder page when reactivated',
		{tag: '@LPS-139005'},
		async ({apiHelpers, page, viewObjectDefinitionsPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.changeObjectActivateStatus(
				objectDefinition.name
			);

			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.changeObjectActivateStatus(
				objectDefinition.name
			);

			await page.goto(
				'/group/guest/~/control_panel/manage/-/workflow_configuration'
			);

			await expect(
				page.getByRole('heading', {name: objectDefinition.label['en_US']})
			).toBeVisible();
		}
	);

	test(
		'Verify that the Object is displayed again on the Workflow Site Menu page when reactivated',
		{tag: '@LPS-139005'},
		async ({apiHelpers, page, site, viewObjectDefinitionsPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					panelCategoryKey: 'site_administration.content',
					scope: 'site',
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.changeObjectActivateStatus(
				objectDefinition.name
			);

			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.changeObjectActivateStatus(
				objectDefinition.name
			);

			await page.goto(
				`/group${site.friendlyUrlPath}/~/control_panel/manage/-/workflow_configuration`
			);

			await expect(
				page.getByRole('heading', {name: objectDefinition.label['en_US']})
			).toBeVisible();
		}
	);

	test(
		'Verify that pending and completed Object entries disappears from Workflow Metrics page when they are deleted',
		{tag: '@LPS-135649'},
		async ({apiHelpers, page, viewObjectEntriesPage}) => {
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
				{[fieldName!]: 'Entry Test'},
				applicationName
			);

			// Delete the entry via UI

			await viewObjectEntriesPage.goto(objectDefinition.className);

			await viewObjectEntriesPage.frontendDatasetActions.first().click();
			await viewObjectEntriesPage.frontendDatasetDeleteAction.click();

			await page.getByRole('button', {name: 'Delete'}).click();

			await waitForAlert(page);

			// Navigate to Workflow Metrics and verify 0 pending items

			const metricsPage = new MetricsPage(page);

			await metricsPage.goTo();

			await metricsPage.chooseProcess('Single Approver');

			await expect(page.getByText('0', {exact: true}).first()).toBeVisible();

			// Unassign workflow for cleanup

			await configurationTabPage.goTo();

			await configurationTabPage.unassignWorkflowFromAssetType(
				objectDefinition.label['en_US']
			);
		}
	);

	test(
		'Verify that pending and completed Object entries disappears from Workflow pages when they are deleted',
		{tag: '@LPS-135649'},
		async ({apiHelpers, page, viewObjectEntriesPage}) => {
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
				{[fieldName!]: 'Entry Test'},
				applicationName
			);

			// Delete the entry via UI

			await viewObjectEntriesPage.goto(objectDefinition.className);

			await viewObjectEntriesPage.frontendDatasetActions.first().click();
			await viewObjectEntriesPage.frontendDatasetDeleteAction.click();

			await page.getByRole('button', {name: 'Delete'}).click();

			await waitForAlert(page);

			// Navigate to My Workflow Tasks and verify no entries

			const workflowTasksPage = new WorkflowTasksPage(page);

			await workflowTasksPage.goToAssignedToMyRoles();

			await expect(
				page.getByRole('heading', {name: objectDefinition.label['en_US']})
			).toBeHidden();

			// Unassign workflow for cleanup

			await configurationTabPage.goTo();

			await configurationTabPage.unassignWorkflowFromAssetType(
				objectDefinition.label['en_US']
			);
		}
	);

	test(
		'Verify that pending and completed Object entries with workflow are not displayed on the workflow pages when inactivated',
		{tag: '@LPS-139005'},
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

			// Configure Single Approver workflow

			const configurationTabPage = new ConfigurationTabPage(page);

			await configurationTabPage.goTo();

			await configurationTabPage.assignWorkflowToAssetType(
				'Single Approver',
				objectDefinition.label['en_US']
			);

			// Add entries via API (will be in pending workflow state)

			const applicationName =
				'c/' + objectDefinition.name.toLowerCase() + 's';
			const fieldName = objectFields[0].name!;

			await apiHelpers.objectEntry.postObjectEntry(
				{[fieldName!]: 'Test'},
				applicationName
			);

			await apiHelpers.objectEntry.postObjectEntry(
				{[fieldName!]: 'Test 2'},
				applicationName
			);

			// Approve one entry via My Workflow Tasks

			const workflowTasksPage = new WorkflowTasksPage(page);

			await workflowTasksPage.goToAssignedToMyRoles();

			await workflowTasksPage.assignToMe(objectDefinition.label['en_US']);

			await workflowTasksPage.approve(objectDefinition.label['en_US']);

			// Inactivate the object

			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.changeObjectActivateStatus(
				objectDefinition.name
			);

			// Verify no entries on My Workflow Tasks

			await workflowTasksPage.goto();

			await expect(
				page.getByRole('heading', {name: objectDefinition.label['en_US']})
			).toBeHidden();

			await workflowTasksPage.assignedToMyRolesLink.click();

			await expect(
				page.getByRole('heading', {name: objectDefinition.label['en_US']})
			).toBeHidden();

			// Reactivate for cleanup

			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.changeObjectActivateStatus(
				objectDefinition.name
			);

			await configurationTabPage.goTo();

			await configurationTabPage.unassignWorkflowFromAssetType(
				objectDefinition.label['en_US']
			);
		}
	);

	test(
		'Verify that the Object entries with workflow are displayed again on the Workflow Metrics page when reactivated',
		{tag: '@LPS-139005'},
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

			// Configure Single Approver workflow

			const configurationTabPage = new ConfigurationTabPage(page);

			await configurationTabPage.goTo();

			await configurationTabPage.assignWorkflowToAssetType(
				'Single Approver',
				objectDefinition.label['en_US']
			);

			// Add entries via API

			const applicationName =
				'c/' + objectDefinition.name.toLowerCase() + 's';
			const fieldName = objectFields[0].name!;

			await apiHelpers.objectEntry.postObjectEntry(
				{[fieldName!]: 'Entry A'},
				applicationName
			);

			await apiHelpers.objectEntry.postObjectEntry(
				{[fieldName!]: 'Entry B'},
				applicationName
			);

			// Approve one entry

			const workflowTasksPage = new WorkflowTasksPage(page);

			await workflowTasksPage.goToAssignedToMyRoles();

			await workflowTasksPage.assignToMe(objectDefinition.label['en_US']);

			await workflowTasksPage.approve(objectDefinition.label['en_US']);

			// Inactivate then reactivate the object

			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.changeObjectActivateStatus(
				objectDefinition.name
			);

			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.changeObjectActivateStatus(
				objectDefinition.name
			);

			// Verify Workflow Metrics show the pending entry

			const metricsPage = new MetricsPage(page);

			await metricsPage.goTo();

			await metricsPage.chooseProcess('Single Approver');

			await expect(page.getByText('1', {exact: true}).first()).toBeVisible();

			// Unassign workflow for cleanup

			await configurationTabPage.goTo();

			await configurationTabPage.unassignWorkflowFromAssetType(
				objectDefinition.label['en_US']
			);
		}
	);

	test(
		'Verify that pending and completed Object entries with workflow are displayed again on the workflow pages when reactivated',
		{tag: '@LPS-139005'},
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

			// Configure Single Approver workflow

			const configurationTabPage = new ConfigurationTabPage(page);

			await configurationTabPage.goTo();

			await configurationTabPage.assignWorkflowToAssetType(
				'Single Approver',
				objectDefinition.label['en_US']
			);

			// Add entries via API

			const applicationName =
				'c/' + objectDefinition.name.toLowerCase() + 's';
			const fieldName = objectFields[0].name!;

			await apiHelpers.objectEntry.postObjectEntry(
				{[fieldName!]: 'Test'},
				applicationName
			);

			await apiHelpers.objectEntry.postObjectEntry(
				{[fieldName!]: 'Test 2'},
				applicationName
			);

			// Approve one entry

			const workflowTasksPage = new WorkflowTasksPage(page);

			await workflowTasksPage.goToAssignedToMyRoles();

			await workflowTasksPage.assignToMe(objectDefinition.label['en_US']);

			await workflowTasksPage.approve(objectDefinition.label['en_US']);

			// Inactivate then reactivate the object

			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.changeObjectActivateStatus(
				objectDefinition.name
			);

			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.changeObjectActivateStatus(
				objectDefinition.name
			);

			// Verify entries reappear on My Workflow Tasks

			await workflowTasksPage.goto();

			await expect(
				page.getByRole('heading', {name: objectDefinition.label['en_US']})
			).toBeVisible();

			await workflowTasksPage.assignedToMyRolesLink.click();

			await expect(
				page.getByRole('heading', {name: objectDefinition.label['en_US']})
			).toBeVisible();

			// Unassign workflow for cleanup

			await configurationTabPage.goTo();

			await configurationTabPage.unassignWorkflowFromAssetType(
				objectDefinition.label['en_US']
			);
		}
	);

	test(
		'Verify that a completed entry is displayed with an Approved status',
		{tag: '@LPS-135649'},
		async ({apiHelpers, page, viewObjectEntriesPage}) => {
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

			await apiHelpers.objectEntry.postObjectEntry(
				{[objectFields[0].name!]: 'ApprovedEntry'},
				'c/' + objectDefinition.name!.toLowerCase() + 's'
			);

			await viewObjectEntriesPage.goto(objectDefinition.className);

			const entryRow = page
				.getByRole('row')
				.filter({hasText: 'ApprovedEntry'});

			await expect(entryRow.getByText('Approved')).toBeVisible();
		}
	);

	test(
		'Verify that a withdrawn pending entry is displayed with a Draft status',
		{tag: '@LPS-135649'},
		async ({apiHelpers, page, viewObjectEntriesPage}) => {
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

			// Configure Single Approver workflow

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
				{[fieldName!]: 'Entry Test'},
				applicationName
			);

			// Navigate to My Submissions and withdraw the entry

			await page.goto(
				'/group/guest/~/control_panel/manage/-/my_workflow_instances'
			);

			const entryRow = page
				.getByRole('row')
				.filter({hasText: objectDefinition.label['en_US']});

			await entryRow.locator('.dropdown-toggle').click();

			await page.getByRole('menuitem', {name: 'Withdraw'}).click();

			await page.getByRole('button', {name: 'OK'}).click();

			await waitForAlert(page);

			// Navigate to object entries and verify Draft status

			await viewObjectEntriesPage.goto(objectDefinition.className);

			await expect(
				page
					.getByRole('row')
					.filter({hasText: 'Entry Test'})
					.getByText('Draft')
			).toBeVisible();

			// Unassign workflow for cleanup

			await configurationTabPage.goTo();

			await configurationTabPage.unassignWorkflowFromAssetType(
				objectDefinition.label['en_US']
			);
		}
	);

	test(
		'Verify that the workflow is triggered when submitting an entry through Forms',
		{tag: '@LPS-135649'},
		async ({apiHelpers, page, viewObjectEntriesPage}) => {
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

			// Configure Single Approver workflow

			const configurationTabPage = new ConfigurationTabPage(page);

			await configurationTabPage.goTo();

			await configurationTabPage.assignWorkflowToAssetType(
				'Single Approver',
				objectDefinition.label['en_US']
			);

			// Create a form with Object storage type

			const formBuilderPage = new FormBuilderPage(page);
			const formSettingsModalPage = new FormSettingsModalPage(page);
			const formBuilderSidePanelPage = new FormBuilderSidePanelPage(page);

			await formBuilderPage.goToNew();

			await formBuilderPage.fillFormTitle('Form Object');

			await formBuilderPage.formSettingsButton.click();

			await formSettingsModalPage.selectStorageType('Object');

			await formSettingsModalPage.selectObject(
				objectDefinition.label['en_US']
			);

			await formSettingsModalPage.clickDoneButton();

			// Add text field and map to object field

			await formBuilderSidePanelPage.addTextButton.dblclick();

			await formBuilderSidePanelPage.selectObjectField(
				objectFields[0].label!['en_US']
			);

			await formBuilderSidePanelPage.clickBackButton();

			await formBuilderPage.clickSaveButton();

			await waitForAlert(page);

			await formBuilderPage.clickPublishFormButton();

			// Submit an entry through the form's publish URL

			const formSubmissionURL = await formBuilderPage.getFormSubmissionURL();

			await page.goto(formSubmissionURL);

			await page
				.getByLabel(objectFields[0].label!['en_US'])
				.fill('Entry Test');

			await page.getByRole('button', {name: 'Submit'}).click();

			await expect(
				page.getByText('Your information was successfully received')
			).toBeVisible();

			// Navigate to object entries and verify Pending status

			await viewObjectEntriesPage.goto(objectDefinition.className);

			await expect(
				page
					.getByRole('row')
					.filter({hasText: 'Entry Test'})
					.getByText('Pending')
			).toBeVisible();

			// Unassign workflow for cleanup

			await configurationTabPage.goTo();

			await configurationTabPage.unassignWorkflowFromAssetType(
				objectDefinition.label['en_US']
			);
		}
	);

	test(
		'Verify that the workflow is triggered when submitting an entry through Custom Object portlet',
		{tag: '@LPS-135649'},
		async ({apiHelpers, page, viewObjectEntriesPage}) => {
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

			// Configure Single Approver workflow

			const configurationTabPage = new ConfigurationTabPage(page);

			await configurationTabPage.goTo();

			await configurationTabPage.assignWorkflowToAssetType(
				'Single Approver',
				objectDefinition.label['en_US']
			);

			// Add an entry through the object portlet

			await viewObjectEntriesPage.goto(objectDefinition.className);

			await viewObjectEntriesPage.clickAddObjectEntry(
				objectDefinition.label['en_US']
			);

			await page
				.getByLabel(objectFields[0].label!['en_US'])
				.fill('Entry Test');

			await viewObjectEntriesPage.saveObjectEntryButton.click();

			await waitForAlert(page);

			await page.goBack();

			// Verify entry has Pending status

			await expect(
				page
					.getByRole('row')
					.filter({hasText: 'Entry Test'})
					.getByText('Pending')
			).toBeVisible();

			// Unassign workflow for cleanup

			await configurationTabPage.goTo();

			await configurationTabPage.unassignWorkflowFromAssetType(
				objectDefinition.label['en_US']
			);
		}
	);

	test(
		'Verify that the workflow is triggered when submitting an entry when Object is scoped by Site and the workflow was assigned on the Workflow settings from the Site Menu',
		{tag: '@LPS-135649'},
		async ({apiHelpers, page, site, viewObjectEntriesPage}) => {
			const objectFields = generateObjectFields({
				objectFieldBusinessTypes: ['Text'],
			});

			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields,
					panelCategoryKey: 'site_administration.content',
					scope: 'site',
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			// Configure Single Approver workflow via site workflow settings

			await page.goto(
				`/group${site.friendlyUrlPath}/~/control_panel/manage/-/workflow_configuration`
			);

			const assetTypeRow = page
				.getByRole('row')
				.filter({hasText: objectDefinition.label['en_US']});

			await assetTypeRow.getByRole('button', {name: 'Edit'}).click();

			await assetTypeRow
				.getByRole('combobox')
				.selectOption({label: 'Single Approver'});

			await assetTypeRow.getByRole('button', {name: 'Save'}).click();

			await waitForAlert(page);

			// Add an entry through the object portlet

			await viewObjectEntriesPage.goto(
				objectDefinition.className,
				'en',
				site.friendlyUrlPath
			);

			await viewObjectEntriesPage.clickAddObjectEntry(
				objectDefinition.label['en_US']
			);

			await page
				.getByLabel(objectFields[0].label!['en_US'])
				.fill('Entry Test');

			await viewObjectEntriesPage.saveObjectEntryButton.click();

			await waitForAlert(page);

			await page.goBack();

			// Verify entry has Pending status

			await expect(
				page
					.getByRole('row')
					.filter({hasText: 'Entry Test'})
					.getByText('Pending')
			).toBeVisible();
		}
	);

	test(
		'Verify that when Objects are not scoped by Site it should not be displayed on the Workflow settings from the Site Menu',
		{tag: '@LPS-135649'},
		async ({apiHelpers, page, site}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await page.goto(
				`/group${site.friendlyUrlPath}/~/control_panel/manage/-/workflow_configuration`
			);

			await expect(
				page.getByRole('heading', {name: objectDefinition.label['en_US']})
			).toBeHidden();
		}
	);

	test(
		'Verify the Object Entry Title is displayed for Object entries on workflow pages',
		{tag: '@LPS-139803'},
		async ({apiHelpers, page}) => {
			const objectFields = generateObjectFields({
				objectFieldBusinessTypes: ['Text'],
			});

			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields,
					status: {code: 0},
					titleObjectFieldName: objectFields[0].name,
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await apiHelpers.objectEntry.postObjectEntry(
				{[objectFields[0].name!]: 'WorkflowEntryTitle'},
				'c/' + objectDefinition.name!.toLowerCase() + 's'
			);

			await page.goto(
				'/group/guest/~/control_panel/manage/-/my_workflow_tasks'
			);

			await expect(page.getByText('WorkflowEntryTitle')).toBeVisible();
		}
	);
});
