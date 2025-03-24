/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {getRandomInt} from '../../utils/getRandomInt';
import getRandomString from '../../utils/getRandomString';
import {cmsPagesTest} from './fixtures/cmsPagesTest';
import {FIELD_TYPES} from './pages/StructureBuilderPage';

const test = mergeTests(
	cmsPagesTest,
	featureFlagsTest({
		'LPD-11232': {enabled: true},
		'LPD-17564': {enabled: true},
		'LPD-31149': {enabled: true},
	}),
	loginTest()
);

test(
	'Structures can be saved and published',
	{tag: '@LPD-36752'},
	async ({page, structureBuilderPage}) => {

		// Go to the Structure Builder

		await structureBuilderPage.goto();

		// Change label and name

		const label = `Structure${getRandomInt()}`;

		await structureBuilderPage.changeStructureSettings({
			label,
			name: label,
		});

		// Check we can't publish without adding a field

		await clickAndExpectToBeVisible({
			target: page.getByText('At least one field must be added'),
			trigger: structureBuilderPage.saveButton,
		});

		// Add fields and check they are selected by default

		await structureBuilderPage.addField('Text');

		await expect(
			page.locator('.breadcrumb-link', {hasText: 'Text'})
		).toBeVisible();

		await structureBuilderPage.addField('Long Text');

		await expect(
			page.locator('.breadcrumb-link', {hasText: 'Long Text'})
		).toBeVisible();

		// Save the structure

		const {id} = await structureBuilderPage.saveStructure();

		await expect(page.locator('.alert-danger')).not.toBeVisible();

		// Remove a field

		await structureBuilderPage.deleteField({label: 'Long Text'});

		// Publish it

		await structureBuilderPage.publishStructure();

		// Check name changes in management bar

		await expect(
			page.locator('.management-bar').getByText(label)
		).toBeVisible();

		// Check another field with same name can not be added

		await structureBuilderPage.addField('Text');
		await structureBuilderPage.selectField({label: 'Text', nth: 1});
		await structureBuilderPage.changeFieldSettings({name: 'text'});

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

		await structureBuilderPage.changeStructureSettings({
			label,
			name: label,
		});

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

		await structureBuilderPage.changeStructureSettings({
			label,
			name: label,
		});

		// Add four fields

		const types = ['Text', 'Long Text', 'Upload', 'Numeric'] as const;

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

test(
	'Can configure a text field',
	{tag: '@LPD-49168'},
	async ({page, structureBuilderPage}) => {

		// Go to the Structure Builder

		await structureBuilderPage.goto();

		// Change label,name and erc

		const label = `Structure${getRandomInt()}`;
		const erc = getRandomString();

		await structureBuilderPage.changeStructureSettings({
			erc,
			label,
			name: label,
		});

		// Add a text field

		await structureBuilderPage.addField('Text');

		await structureBuilderPage.selectField({label: 'Text'});

		// Configure the field

		await structureBuilderPage.changeFieldSettings({
			erc,
			label: 'Text Edited',
			localizable: true,
			name: 'textEdited',
		});

		await page.getByLabel('Accept Unique Values Only').click();

		await page.getByLabel('Limit Characters').click();

		const maximumNumberOfCharactersInput = page.getByLabel(
			'Maximum Number of Characters'
		);
		maximumNumberOfCharactersInput.fill('10');

		await maximumNumberOfCharactersInput.blur();

		// Save and publish the structure

		const {id} = await structureBuilderPage.saveStructure();

		await expect(page.locator('.alert-danger')).not.toBeVisible();

		const {objectFields} = await structureBuilderPage.publishStructure();

		// Check the text field is created with the correct settings

		const textObjectField = objectFields.find(
			({name}) => name === 'textEdited'
		);

		expect(textObjectField).toBeDefined();

		expect(textObjectField.label).toStrictEqual({en_US: 'Text Edited'});
		expect(textObjectField.localized).toBe(true);
		expect(textObjectField.name).toBe('textEdited');
		expect(textObjectField.objectFieldSettings[0]).toStrictEqual({
			name: 'uniqueValues',
			value: true,
		});
		expect(textObjectField.objectFieldSettings[1]).toStrictEqual({
			name: 'maxLength',
			value: 10,
		});
		expect(textObjectField.objectFieldSettings[2]).toStrictEqual({
			name: 'showCounter',
			value: true,
		});

		// Delete structure

		await structureBuilderPage.deleteStructure(id);
	}
);

test.describe('Frontend validations', () => {
	test(
		'Validations when saving the structure',
		{tag: '@LPD-36752'},
		async ({page, structureBuilderPage}) => {

			// Add a picklist

			const picklist = await structureBuilderPage.createPicklist();

			// Go to the Structure Builder

			await structureBuilderPage.goto();

			// Set label and empty name

			const label = `Structure${getRandomInt()}`;

			await structureBuilderPage.changeStructureSettings({
				label,
				name: '',
			});

			await expect(
				page.getByText('This field is required')
			).toBeVisible();

			// Add a Single Select field and select it

			await structureBuilderPage.addField('Single Select');

			await structureBuilderPage.selectField({label: 'Single Select'});

			// Put empty name

			await structureBuilderPage.changeFieldSettings({name: ''});

			// Try to save and check it redirects to structure view

			await clickAndExpectToBeVisible({
				target: page.getByText('Structure Name'),
				trigger: structureBuilderPage.saveButton,
			});

			// Fill name

			await structureBuilderPage.changeStructureSettings({name: label});

			// Now try to save and check it redirects to field view

			await clickAndExpectToBeVisible({
				target: page.locator('.breadcrumb-link', {
					hasText: 'Single Select',
				}),
				trigger: structureBuilderPage.saveButton,
			});

			// Fill name and save again

			await structureBuilderPage.changeFieldSettings({name: 'text'});

			await structureBuilderPage.changeFieldSettings({
				picklist: picklist.name,
			});

			const {id} = await structureBuilderPage.saveStructure();

			// Delete structure

			await structureBuilderPage.deleteStructure(id);

			// Delete picklist

			await structureBuilderPage.deletePicklist(picklist.id);
		}
	);

	test(
		'Validations in the picklist picker',
		{tag: '@LPD-51647'},
		async ({page, structureBuilderPage}) => {

			// Add a picklist

			const picklist = await structureBuilderPage.createPicklist();

			// Go to the Structure Builder

			await structureBuilderPage.goto();

			// Add a Single Select field and check for blur error

			await structureBuilderPage.addField('Single Select');

			await structureBuilderPage.selectField({label: 'Single Select'});

			const picklistPicker = page.getByRole('combobox', {
				name: 'Select Picklist',
			});

			const errorMessage = page.getByText('This field is required.');

			await expect(errorMessage).not.toBeAttached();

			await picklistPicker.press('Tab');

			await expect(errorMessage).toBeAttached();

			await structureBuilderPage.changeFieldSettings({
				picklist: picklist.name,
			});

			await expect(errorMessage).not.toBeAttached();

			// Add a Multiselect field and check for outside click error

			await structureBuilderPage.addField('Multiselect');

			await structureBuilderPage.selectField({label: 'Multiselect'});

			await expect(errorMessage).not.toBeAttached();

			await picklistPicker.click();

			await page.locator('body').click();

			await expect(errorMessage).toBeAttached();

			// Delete picklist

			await structureBuilderPage.deletePicklist(picklist.id);
		}
	);
});
