/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';
import {cmsPagesTest} from './fixtures/cmsPagesTest';

const test = mergeTests(
	cmsPagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-11232': {enabled: true},
		'LPD-17564': {enabled: true},
	}),
	loginTest(),
	pageEditorPagesTest
);

test(
	'Friendly URL is taken into account when creating contents',
	{tag: '@LPD-54566'},
	async ({contentsPage, page}) => {

		// Go to CMS Contents

		await contentsPage.goto();

		// Create new Knowledge Base content

		await contentsPage.createContent('Knowledge Base');

		// Fill data and save

		const title = getRandomString();
		const friendlyUrl = getRandomString();

		await page.getByLabel('Title').fill(title);
		await page.getByLabel('Friendly URL').fill(friendlyUrl);

		await contentsPage.saveContent();

		// Edit the content again and check values

		await contentsPage.editContent(title);

		await expect(page.getByLabel('Friendly URL')).toHaveValue(friendlyUrl);

		// Delete content

		await contentsPage.goto();

		await contentsPage.deleteContent(title);
	}
);

test(
	'Default structures take Content Editor Master and fragments work',
	{tag: '@LPD-50371'},
	async ({contentsPage, page}) => {

		// Go to CMS Contents

		await contentsPage.goto();

		// Create new Knowledge Base content

		await contentsPage.createContent('Knowledge Base');

		// Fill data

		const title = getRandomString();
		const friendlyUrl = getRandomString();

		await page.getByLabel('Title').fill(title);
		await page.getByLabel('Friendly URL').fill(friendlyUrl);

		// Check side panel works

		await contentsPage.openSidePanel('General');

		await contentsPage.closeSidePanel();

		await contentsPage.saveContent();

		// Edit the content again and check values

		await contentsPage.editContent(title);

		await expect(page.getByLabel('Friendly URL')).toHaveValue(friendlyUrl);

		// Delete content

		await contentsPage.goto();

		await contentsPage.deleteContent(title);
	}
);

test(
	'Check the functionality of the Space List fragment CMS',
	{tag: ['@LPD-52223']},
	async ({contentsPage, page, pageEditorPage, structureBuilderPage}) => {

		// Create new structure for Default space

		await structureBuilderPage.goto();

		await structureBuilderPage.selectSpaces(['Default']);

		const label = getRandomString();

		await structureBuilderPage.changeStructureSettings({
			label,
			name: `StructureName${getRandomInt()}`,
		});

		// Add a field

		await structureBuilderPage.addField('Text');

		// Publish the structure

		const {id} = await structureBuilderPage.saveStructure();

		await structureBuilderPage.publishStructure();

		// Customize the experience and add the Spaces fragment

		await clickAndExpectToBeVisible({
			target: page.getByText('Select a Page Element', {exact: true}),
			trigger: page.getByRole('button', {
				name: 'Customize Experience',
			}),
		});

		await pageEditorPage.addFragment('Space List', 'Space List');

		await pageEditorPage.publishPage();

		// Create a content of the new structure and check Spaces fragment

		await contentsPage.goto();

		await contentsPage.createContent(label);

		const fragment = page.locator('[class*="spacelistfragmentrenderer"]');

		await fragment.waitFor();

		await expect(
			fragment.locator('label').filter({hasText: 'Space'})
		).toBeVisible();

		await expect(
			fragment.locator('.sticker-overlay').filter({hasText: 'D'})
		).toBeVisible();

		await expect(fragment.filter({hasText: 'Default'})).toBeVisible();

		// Delete structure

		await structureBuilderPage.deleteStructure(id);
	}
);
