/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {changeTrackingPagesTest} from '../../fixtures/changeTrackingPagesTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {workflowPagesTest} from '../../fixtures/workflowPagesTest';
import getRandomString from '../../utils/getRandomString';
import {journalPagesTest} from '../journal-web/fixtures/journalPagesTest';

export const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPD-10703': true,
	}),
	journalPagesTest,
	changeTrackingPagesTest,
	workflowPagesTest,
	loginTest()
);

let ctCollection;
let journalName;
let publicationName;

test.beforeEach(
	async ({
		apiHelpers,
		changeTrackingPage,
		journalEditArticlePage,
		workflowPage,
	}) => {
		await workflowPage.goto();

		await workflowPage.changeWorkflow(
			'Web Content Article',
			'Single Approver'
		);

		await changeTrackingPage.enablePublications();

		publicationName = getRandomString();

		ctCollection =
			await apiHelpers.headlessChangeTracking.createCTCollection(
				publicationName
			);

		await apiHelpers.headlessChangeTracking.checkoutCTCollection(
			ctCollection.id
		);

		journalName = getRandomString();

		await journalEditArticlePage.goto();
		await journalEditArticlePage.submitArticleForWorkflow(journalName);
	}
);

test.afterEach(async ({apiHelpers, changeTrackingPage, workflowPage}) => {
	await apiHelpers.headlessChangeTracking.deleteCTCollection(ctCollection.id);

	await changeTrackingPage.disablePublications();

	await workflowPage.goto();

	await workflowPage.changeWorkflow('Web Content Article', 'No Workflow', {
		disable: true,
	});
});

test('LPD-19748 Add workflow info to the View Change screen', async ({
	changeTrackingPage,
	page,
}) => {
	await changeTrackingPage.goToReviewChanges(publicationName);

	await changeTrackingPage.reviewChange(journalName);

	await expect(page.getByText(`Workflow status: Pending`)).toBeVisible();

	await changeTrackingPage.viewDisplayTab('Workflow');
});

test('LPD-19748 Workflow data is displayed in tab', async ({
	changeTrackingPage,
	page,
}) => {
	const displayData = [
		'Status',
		'Assigned to',
		'Task Name',
		'Create Date',
		'Due Date',
	];

	await changeTrackingPage.goToReviewChanges(publicationName);

	await changeTrackingPage.reviewChange(journalName);

	await changeTrackingPage.selectTab('Workflow');

	for (const data of displayData) {
		await expect(page.getByText(data, {exact: true})).toBeVisible();
	}
});

test('LPD-19748 Only workflow status is displayed when workflow is disabled', async ({
	changeTrackingPage,
	page,
	workflowPage,
}) => {
	await workflowPage.goto();

	await workflowPage.changeWorkflow('Web Content Article', 'No Workflow', {
		disable: true,
	});

	await changeTrackingPage.goToReviewChanges(publicationName);

	await changeTrackingPage.reviewChange(journalName);

	await expect(page.getByText(`Workflow status: Pending`)).toBeVisible();
	await changeTrackingPage.viewDisplayTab('Workflow', {isHidden: true});
});
