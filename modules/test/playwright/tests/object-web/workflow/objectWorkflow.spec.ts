/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {globalMenuPagesTest} from '../../../fixtures/globalMenuPagesTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {workflowPagesTest} from '../../../fixtures/workflowPagesTest';
import {generateObjectFields} from '../utils/generateObjectFields';

const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-36105': {enabled: true},
		'LPS-178052': {enabled: true},
	}),
	globalMenuPagesTest,
	isolatedSiteTest,
	loginTest(),
	objectPagesTest,
	workflowPagesTest
);

test('Can preview entry information on My Workflow Tasks', async ({
	apiHelpers,
	configurationTabPage,
	globalMenuPage,
	page,
	workflowTaskDetailsPage,
	workflowTasksPage,
}) => {
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

	await globalMenuPage.goToApplications('Process Builder');

	await configurationTabPage.configurationTabLink.click();

	await configurationTabPage.assignWorkflowToAssetType(
		'Single Approver',
		objectDefinition.label['en_US']
	);

	const applicationName = 'c/' + objectDefinition.name.toLowerCase() + 's';
	const textFieldName = objectFields[0].name;

	await apiHelpers.objectEntry.postObjectEntry(
		{[textFieldName]: 'Entry Test'},
		applicationName
	);

	await workflowTasksPage.goToAssignedToMyRoles();

	await workflowTaskDetailsPage.selectAsset(objectDefinition.label['en_US']);

	await expect(page.getByText(objectFields[0].label['en_US'])).toHaveValue(
		'Entry Test'
	);
});

test('Can view entry information through View button on My Workflow Tasks', async ({
	apiHelpers,
	configurationTabPage,
	globalMenuPage,
	page,
	workflowTaskDetailsPage,
	workflowTasksPage,
}) => {
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

	await globalMenuPage.goToApplications('Process Builder');

	await configurationTabPage.configurationTabLink.click();

	await configurationTabPage.assignWorkflowToAssetType(
		'Single Approver',
		objectDefinition.label['en_US']
	);

	const applicationName = 'c/' + objectDefinition.name.toLowerCase() + 's';
	const textFieldName = objectFields[0].name;

	await apiHelpers.objectEntry.postObjectEntry(
		{[textFieldName]: 'Entry Test'},
		applicationName
	);

	await workflowTasksPage.goToAssignedToMyRoles();

	await workflowTaskDetailsPage.selectAsset(objectDefinition.label['en_US']);

	await workflowTaskDetailsPage.viewButton.click();

	await expect(page.getByText(objectFields[0].label['en_US'])).toHaveValue(
		'Entry Test'
	);
});

test.skip('Workflow is not triggered for draft entry', async () => {

	// This test requires:
	// 1. A site-scoped custom object with "Allow Users to Save Entries as Draft" enabled
	// 2. A content page with a Form Container mapped to the custom object
	//    and the Form Button configured with "Submitted Entry Status" set to "Draft"
	// 3. Single Approver workflow assigned to the custom object
	// 4. Submitting an entry through the form on the content page
	// 5. Verifying the entry status is "Draft" (workflow not triggered)
	//
	// Cannot be implemented because it requires content page Form Container
	// mapping to custom objects, which depends on page builder fragment
	// configuration infrastructure not available in the current test fixtures.

});
