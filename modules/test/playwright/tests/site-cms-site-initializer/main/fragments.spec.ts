/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';
import {cmsPagesTest} from './fixtures/cmsPagesTest';

const test = mergeTests(
	apiHelpersTest,
	cmsPagesTest,
	loginTest(),
	pageEditorPagesTest,
	dataApiHelpersTest
);

test.describe('Space List Fragment CMS', () => {
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

			const fragment = page.locator(
				'[class*="spacelistfragmentrenderer"]'
			);

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
});
