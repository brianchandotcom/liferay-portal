/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../fixtures/loginTest';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {getRandomInt} from '../../utils/getRandomInt';
import {cmsPagesTest} from './fixtures/cmsPagesTest';
import {FIELD_TYPES} from './pages/StructureBuilderPage';

const test = mergeTests(cmsPagesTest, loginTest());

test(
	'Structures can be saved and published',
	{tag: '@LPD-36752'},
	async ({page, structureBuilderPage}) => {

		// Go to the Structure Builder

		await structureBuilderPage.goto();

		// Change label and name

		const label = `Structure${getRandomInt()}`;

		await structureBuilderPage.changeStructureLabel(label);
		await structureBuilderPage.changeStructureName(label);

		// Save structure

		const {id} = await structureBuilderPage.saveStructure();

		await expect(page.locator('.alert-danger')).not.toBeVisible();

		// Check we can't publish without adding a field

		await expect(async () => {
			await structureBuilderPage.publishStructure();
		}).not.toPass();

		// Add two fields

		await structureBuilderPage.addField('Text');
		await structureBuilderPage.addField('Text');

		// Remove a field

		await structureBuilderPage.deleteField({label: 'Text', nth: 1});

		// Publish it

		await structureBuilderPage.publishStructure();

		// Check name changes in management bar

		await expect(
			page.locator('.management-bar').getByText(label)
		).toBeVisible();

		// Delete structure

		await structureBuilderPage.deleteStructure(id);
	}
);

test(
	'Structures can be saved with all type of fields',
	{tag: '@LPD-36752'},
	async ({structureBuilderPage}) => {

		// Go to the Structure Builder

		await structureBuilderPage.goto();

		// Change label and name

		const label = `Structure${getRandomInt()}`;

		await structureBuilderPage.changeStructureLabel(label);
		await structureBuilderPage.changeStructureName(label);

		// Add a field of each type

		for (const type of FIELD_TYPES) {
			await structureBuilderPage.addField(type);
		}

		// Save and publish the structure

		const {id} = await structureBuilderPage.saveStructure();
		await structureBuilderPage.publishStructure();

		// Delete structure

		await structureBuilderPage.deleteStructure(id);
	}
);

test(
	'Can delete multiple fields',
	{tag: '@LPD-36767'},
	async ({page, structureBuilderPage}) => {

		// Go to the Structure Builder

		await structureBuilderPage.goto();

		// Change label and name

		const label = `Structure${getRandomInt()}`;

		await structureBuilderPage.changeStructureLabel(label);
		await structureBuilderPage.changeStructureName(label);

		// Add four fields

		const types = ['Text', 'Long Text', 'Upload', 'Integer'] as const;

		for (const type of types) {
			await structureBuilderPage.addField(type);
		}

		// Save and publish the structure

		const {id} = await structureBuilderPage.saveStructure();
		await structureBuilderPage.publishStructure();

		// Select and delete three fields

		const textField = page
			.locator('.treeview-item')
			.getByLabel('Text', {exact: true});

		await textField.click();

		await textField.focus();

		await expect(async () => {
			await page.keyboard.down('Control');

			await page.keyboard.press('ArrowDown');
			await page.keyboard.press('Space');

			await expect(page.getByText('3 Items Selected')).toBeVisible({
				timeout: 1000,
			});
		}).toPass();

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('menuitem', {name: 'Delete'}),
			trigger: page.getByLabel('Selection Options'),
		});

		// Check tree node count is 2 (Root and remaining field)

		await expect(page.locator('.treeview-link')).toHaveCount(2);

		// Delete structure

		await structureBuilderPage.deleteStructure(id);
	}
);
