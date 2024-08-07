/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {pageManagementSiteTest} from '../../fixtures/pageManagementSiteTest';
import getRandomString from '../../utils/getRandomString';
import getFragmentDefinition from './utils/getFragmentDefinition';
import getPageDefinition from './utils/getPageDefinition';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	loginTest(),
	pageEditorPagesTest,
	pageManagementSiteTest
);

test('Check only allowed actions can be executed in Content Editing mode', async ({
	apiHelpers,
	page,
	pageEditorPage,
	pageManagementSite,
}) => {

	// Create a page with a Heading and go to edit mode

	const headingId = getRandomString();

	const headingFragment = getFragmentDefinition({
		id: headingId,
		key: 'BASIC_COMPONENT-heading',
	});

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([headingFragment]),
		siteId: pageManagementSite.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, pageManagementSite.friendlyUrlPath);

	// Add a comment to the Heading fragment

	const comment = getRandomString();

	await pageEditorPage.addFragmentComment(headingId, comment);

	// Change edit mode to Content Editing and check correct actions are allowed

	await pageEditorPage.changeEditMode('Content Editing');

	// Check we can edit editables

	await pageEditorPage.editTextEditable(headingId, 'element-text', 'Edited');

	// Check only Browser, Page Content and Comments panels are visible

	expect(
		await page.locator('.page-editor__sidebar').getByRole('tab').all()
	).toHaveLength(3);

	await expect(
		page.getByRole('tab', {exact: true, name: 'Browser'})
	).toBeVisible();
	await expect(
		page.getByRole('tab', {exact: true, name: 'Page Content'})
	).toBeVisible();
	await expect(
		page.getByRole('tab', {exact: true, name: 'Comments'})
	).toBeVisible();

	// Check we can map

	await page.locator('.page-editor__editable').click();

	await pageEditorPage.setMappingConfiguration({
		mapping: {
			entity: 'Web Content',
			entry: 'Animal 01 - Dogs and Cats categories',
			field: 'Title',
			folder: 'Animals',
		},
	});

	// Check we can see and manage comments

	await pageEditorPage.goToSidebarTab('Comments');

	await page.getByLabel('Show Comments').click();

	await expect(
		page.locator('.page-editor__fragment-comment').getByText(comment)
	).toBeVisible();

	await expect(
		page.locator('.page-editor__fragment-comment').getByLabel('Options')
	).toBeVisible();
});
