/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';
import {cmsPagesTest} from '../main/fixtures/cmsPagesTest';
import {structureBuilderPagesTest} from './fixtures/structureBuilderPagesTest';

const test = mergeTests(
	apiHelpersTest,
	cmsPagesTest,
	featureFlagsTest({
		'LPD-17564': {enabled: true},
		'LPS-179669': {enabled: true},
	}),
	loginTest(),
	pageEditorPagesTest,
	structureBuilderPagesTest
);

let structureIds = [];

test.beforeEach(() => {
	structureIds = [];
});

test.afterEach(async ({structureBuilderPage}) => {
	for (const id of structureIds) {
		await structureBuilderPage.deleteStructure(Number(id));
	}
});

test(
	'Can configure workflows and they are persisted',
	{
		tag: '@LPD-50371',
	},
	async ({apiHelpers, page, structureBuilderPage}) => {

		// Create a Space

		const spaceName = `Space ${getRandomString()}`;

		const {id: spaceId} =
			await apiHelpers.headlessAssetLibrary.createAssetLibrary({
				name: spaceName,
				settings: {},
				type: 'Space',
			});

		// Create structure

		const erc = await structureBuilderPage.createStructureFromData({
			label: `StructureName${getRandomInt()}`,
			page: structureBuilderPage,
			structureIds,
		});

		// Configure workflows and save

		await structureBuilderPage.switchTab('Workflow');

		// Configure workflows

		await structureBuilderPage.setWorkflows([
			{space: '', workflow: 'Single Approver'},
			{space: spaceName, workflow: 'Single Approver'},
		]);

		// Publish and edit again to check they were persisted

		await structureBuilderPage.publishStructure();

		await structureBuilderPage.editStructure(erc);

		await structureBuilderPage.switchTab('Workflow');

		await expect(
			page.getByLabel('Default Workflow').locator('option:checked')
		).toHaveText('Single Approver');

		await expect(
			page
				.locator('tr', {hasText: spaceName})
				.getByLabel('Select Workflow')
				.locator('option:checked')
		).toHaveText('Single Approver');

		// Remove workflow for created space and check it takes default one

		await structureBuilderPage.setWorkflows([
			{space: spaceName, workflow: 'Default: Single Approver'},
		]);

		// Publish and edit again to check they were persisted

		await structureBuilderPage.publishStructure();

		await structureBuilderPage.editStructure(erc);

		await structureBuilderPage.switchTab('Workflow');

		await expect(
			page.getByLabel('Default Workflow').locator('option:checked')
		).toHaveText('Single Approver');

		await expect(
			page
				.locator('tr', {hasText: spaceName})
				.getByLabel('Select Workflow')
				.locator('option:checked')
		).toHaveText('Default: Single Approver');

		// Check space workflow selectors take default value from Default Workspace select

		await structureBuilderPage.setWorkflows([
			{space: '', workflow: 'No Workflow'},
		]);

		await expect(
			page
				.locator('tr', {hasText: spaceName})
				.getByLabel('Select Workflow')
				.locator('option:checked')
		).toHaveText('Default: No Workflow');

		// Deselect space in General tab and check Workflow table is updated

		await structureBuilderPage.switchTab('General');

		await structureBuilderPage.selectSpaces([spaceName]);

		await structureBuilderPage.switchTab('Workflow');

		await expect(
			page.locator('tr', {
				has: page.locator('td:first-child', {hasText: 'Default'}),
			})
		).not.toBeVisible();

		await expect(
			page.locator('tr', {
				has: page.locator('td:first-child', {hasText: spaceName}),
			})
		).toBeVisible();

		// Publish the structure

		await structureBuilderPage.publishStructure();

		// Delete space

		expect(
			await apiHelpers.headlessAssetLibrary.deleteAssetLibrary(spaceId)
		).toBeOK();
	}
);
