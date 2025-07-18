/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {clickAndExpectToBeHidden} from '../../../utils/clickAndExpectToBeHidden';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';
import {waitForAlert} from '../../../utils/waitForAlert';
import {cmsPagesTest} from './fixtures/cmsPagesTest';
import {FIELD_TYPES, StructureBuilderPage} from './pages/StructureBuilderPage';

const test = mergeTests(
	cmsPagesTest,
	featureFlagsTest({
		'LPD-11232': {enabled: true},
		'LPD-17564': {enabled: true},
	}),
	loginTest(),
	pageEditorPagesTest
);

let structureIds = [];

const createStructure = async ({
	erc = getRandomString(),
	label,
	name = `StructureName${getRandomInt()}`,
	page,
	publish = true,
}: {
	erc?: string;
	label: string;
	name?: string;
	page: StructureBuilderPage;
	publish?: boolean;
}) => {
	await page.createStructure();

	await page.enableForAllSpaces();

	await page.changeStructureSettings({
		erc,
		label,
		name,
	});

	const {externalReferenceCode, id} = await page.saveStructure();

	if (publish) {
		await page.publishStructure();
	}

	structureIds.push(id);

	return externalReferenceCode;
};

test.beforeEach(() => {
	structureIds = [];
});

test.afterEach(async ({structureBuilderPage}) => {
	for (const id of structureIds) {
		await structureBuilderPage.deleteStructure(Number(id));
	}
});

test(
	'Structures can be saved and published',
	{tag: '@LPD-36752'},
	async ({page, structureBuilderPage}) => {

		// Go to the Structure Builder

		await structureBuilderPage.createStructure();

		await structureBuilderPage.enableForAllSpaces();

		// Change label and name

		const label = `Structure${getRandomInt()}`;

		await structureBuilderPage.changeStructureSettings({
			label,
			name: label,
		});

		// Add fields and check they are selected by default

		await structureBuilderPage.addField('Text');

		await expect(
			page.locator('.breadcrumb-link', {hasText: 'Text'})
		).toBeVisible();

		await structureBuilderPage.addField('Decimal');

		await expect(
			page.locator('.breadcrumb-link', {hasText: 'Decimal'})
		).toBeVisible();

		// Select fields and check its values are shown

		await structureBuilderPage.selectFields([{label: 'Text'}]);

		await expect(page.getByLabel('Label')).toHaveValue('Text');

		await structureBuilderPage.selectFields([{label: 'Decimal'}]);

		await expect(page.getByLabel('Label')).toHaveValue('Decimal');

		// Save the structure

		const {id} = await structureBuilderPage.saveStructure();

		structureIds.push(id);

		await expect(page.locator('.alert-danger')).not.toBeVisible();

		// Remove a field

		await structureBuilderPage.deleteFields([{label: 'Decimal'}]);

		// Publish it

		await structureBuilderPage.publishStructure();

		// Check name changes in toolbar

		await expect(
			page.locator('.component-tbar').getByText(label)
		).toBeVisible();

		// Check another field with same name can not be added

		await structureBuilderPage.addField('Text');
		await structureBuilderPage.selectFields([{label: 'Text', nth: 1}]);
		await structureBuilderPage.changeFieldSettings({name: 'text'});
	}
);

test(
	'Structures can be saved with all type of fields',
	{tag: '@LPD-36752'},
	async ({picklistBuilderPage, structureBuilderPage}) => {

		// Add a picklist

		const picklist = await picklistBuilderPage.createPicklist();

		// Create structure

		const label = `Structure${getRandomInt()}`;

		await createStructure({
			label,
			name: label,
			page: structureBuilderPage,
		});

		// Add a field of each type

		for (const type of FIELD_TYPES) {
			await structureBuilderPage.addField(type);

			if (type === 'Single Select' || type === 'Multiselect') {
				await structureBuilderPage.changeFieldSettings({
					picklist: picklist.name,
				});
			}
		}

		// Publish the structure

		await structureBuilderPage.publishStructure();

		// Delete picklist

		await picklistBuilderPage.deletePicklist(picklist.id);
	}
);

test(
	'Can delete multiple fields',
	{tag: '@LPD-36767'},
	async ({structureBuilderPage}) => {

		// Create structure

		const label = `Structure${getRandomInt()}`;

		await createStructure({
			label,
			name: label,
			page: structureBuilderPage,
		});

		// Add four fields

		const types = ['Text', 'Long Text', 'Upload', 'Numeric'] as const;

		for (const type of types) {
			await structureBuilderPage.addField(type);
		}

		// Publish the structure

		await structureBuilderPage.publishStructure();

		// Select and delete three fields

		await structureBuilderPage.deleteFields([
			{label: 'Text'},
			{label: 'Long Text'},
			{label: 'Upload'},
		]);
	}
);

test(
	'Can configure a text field',
	{tag: '@LPD-49168'},
	async ({page, structureBuilderPage}) => {

		// Create structure

		const label = `Structure${getRandomInt()}`;
		const erc = getRandomString();

		await createStructure({
			erc,
			label,
			name: label,
			page: structureBuilderPage,
		});

		// Add a text field

		await structureBuilderPage.addField('Text');

		await structureBuilderPage.selectFields([{label: 'Text'}]);

		// Assert correct label style

		await expect(
			page.locator('.label-info', {hasText: 'Text'})
		).toBeVisible();

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

		// Publish the structure

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
	}
);

test.describe('Frontend validations', () => {
	test(
		'Validations when saving the structure',
		{tag: '@LPD-36752'},
		async ({page, picklistBuilderPage, structureBuilderPage}) => {

			// Add a picklist

			const picklist = await picklistBuilderPage.createPicklist();

			// Go to the Structure Builder

			await structureBuilderPage.createStructure();

			// Add a Text field

			await structureBuilderPage.addField('Text');

			// Try to save and check we can't publish without spaces

			await expect(async () => {
				await structureBuilderPage.saveButton.click();

				await expect(
					page.getByText('Spaces must be selected')
				).toBeAttached({
					timeout: 500,
				});
			}).toPass();

			await structureBuilderPage.enableForAllSpaces();

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

			await structureBuilderPage.selectFields([{label: 'Single Select'}]);

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

			// Fill name, select picklist and save again

			await structureBuilderPage.changeFieldSettings({name: 'name'});

			await structureBuilderPage.changeFieldSettings({
				picklist: picklist.name,
			});

			// Check picklist setting is saved

			await structureBuilderPage.selectFields([{label: 'Text'}]);

			await structureBuilderPage.selectFields([{label: 'Single Select'}]);

			await expect(page.getByText(picklist.name)).toBeVisible();

			// Save

			const {id} = await structureBuilderPage.saveStructure();

			structureIds.push(id);

			// Publish structure

			await structureBuilderPage.publishStructure();

			// Delete one field and check warning modal is show when publishing

			await structureBuilderPage.deleteFields([{label: 'Text'}]);

			await clickAndExpectToBeVisible({
				target: page.getByText(
					'You removed one or more fields from the structure'
				),
				trigger: structureBuilderPage.publishButton,
			});

			await clickAndExpectToBeHidden({
				target: page.getByText(
					'You removed one or more fields from the structure'
				),
				trigger: page.locator('.btn-danger'),
			});

			await waitForAlert(page, 'published successfully', {
				timeout: 5000,
			});

			// Check the warning does not appear anymore

			await structureBuilderPage.publishStructure();

			// Delete picklist

			await picklistBuilderPage.deletePicklist(picklist.id);
		}
	);

	test(
		'Validations in the picklist picker',
		{tag: '@LPD-51647'},
		async ({page, picklistBuilderPage, structureBuilderPage}) => {

			// Add a picklist

			const picklist = await picklistBuilderPage.createPicklist();

			// Go to the Structure Builder

			await structureBuilderPage.createStructure();

			// Add a Single Select field and check for blur error

			await structureBuilderPage.addField('Single Select');

			await structureBuilderPage.selectFields([{label: 'Single Select'}]);

			const picklistPicker = page.getByLabel('Picklist');

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

			await structureBuilderPage.selectFields([{label: 'Multiselect'}]);

			await expect(errorMessage).not.toBeAttached();

			await picklistPicker.click();

			await page.locator('body').click();

			await expect(errorMessage).toBeAttached();

			// Delete picklist

			await picklistBuilderPage.deletePicklist(picklist.id);
		}
	);
});

test(
	'Create a picklist from the structure builder by opening other tab',
	{tag: '@LPD-52544'},
	async ({context, page, picklistBuilderPage, structureBuilderPage}) => {

		// Go to the Structure Builder

		await structureBuilderPage.createStructure();

		// Add a Single Select field and select it

		await structureBuilderPage.addField('Single Select');

		await structureBuilderPage.selectFields([{label: 'Single Select'}]);

		// Create new picklist from the button "New Picklist"

		const pagePromise = context.waitForEvent('page');

		await page.getByText('New Picklist').click();

		const newPage = await pagePromise;

		// The picklist builder opens in a new tab

		await expect(
			newPage.getByRole('heading', {name: 'New Picklist'})
		).toBeAttached();

		// Change the picklist name

		await newPage.getByLabel('Picklist Name').fill('Plants');

		// Save the picklist

		await newPage.getByRole('button', {name: 'Save'}).click();

		await waitForAlert(newPage, 'Success:Plants was saved successfully.');

		// Check the new picklist in the structure builder opening the picker by keyboard

		const picklistPicker = page.getByLabel('Picklist');

		await picklistPicker.press('Enter');

		await expect(picklistPicker).toBeFocused();

		await expect(page.getByRole('option', {name: 'Plants'})).toBeAttached();

		// Delete picklist

		const picklist = await picklistBuilderPage.getPicklist('Plants');

		await picklistBuilderPage.deletePicklist(picklist.id);
	}
);

test.describe('Customize experience', () => {
	test(
		'Alerts are displayed when trying to customize the experience without publishing the structure',
		{
			tag: '@LPD-50370',
		},
		async ({page, structureBuilderPage}) => {

			// Create structure

			await createStructure({
				label: `StructureName${getRandomInt()}`,
				page: structureBuilderPage,
			});

			// Add two Text fields

			await structureBuilderPage.addField('Text');

			await structureBuilderPage.changeFieldSettings({
				label: 'Field 1',
			});

			await structureBuilderPage.addField('Text');

			await structureBuilderPage.changeFieldSettings({
				label: 'Field 2',
			});

			// Try to customize the experience without publishing the structure

			await page
				.getByRole('button', {name: 'Customize Experience'})
				.click();

			// Check the warning is shown

			await expect(
				page.getByText(
					'To customize the experience you need to publish the structure first.'
				)
			).toBeAttached();

			// Publish the structure

			await page
				.getByRole('dialog', {
					name: 'Publish to Customize Experience',
				})
				.getByRole('button', {name: 'Publish'})
				.click();

			await waitForAlert(
				page,
				'Remember to review the customized experience if needed.',
				{autoClose: false}
			);

			// Check the customized experience

			await page
				.getByRole('alert')
				.getByRole('button', {name: 'Customize Experience'})
				.click();

			await structureBuilderPage.waitForExperienceCustomizerModal();

			await expect(page.getByLabel('Field 1 (Read Only)')).toBeVisible();

			// Go back to the structure builder

			await page
				.locator('.management-bar')
				.getByRole('link', {name: 'Back'})
				.click();

			// Delete the field and try to customize the experience again

			await structureBuilderPage.deleteFields([{label: 'Field 1'}]);

			await page
				.getByRole('button', {name: 'Customize Experience'})
				.click();

			// Check the warning is shown

			await expect(
				page.getByText(
					'To customize the experience you need to publish the structure first. You removed one or more fields from the structure.'
				)
			).toBeAttached();

			await page
				.getByRole('dialog', {
					name: 'Publish to Customize Experience',
				})
				.getByRole('button', {name: 'Publish'})
				.click();

			await page
				.getByRole('alert')
				.getByRole('button', {name: 'Customize Experience'})
				.click();

			await structureBuilderPage.waitForExperienceCustomizerModal();

			// Check the experience is regenerated removing the deleted field

			await expect(
				page.getByLabel('Field 1 (Read Only)')
			).not.toBeVisible();
			await expect(page.getByLabel('Field 2 (Read Only)')).toBeVisible();
		}
	);

	test(
		'Edit experience link is shown every time we publish if it is customized',
		{
			tag: '@LPD-50370',
		},
		async ({page, pageEditorPage, structureBuilderPage}) => {

			// Create structure

			const label = `StructureName${getRandomInt()}`;

			await createStructure({
				label,
				page: structureBuilderPage,
			});

			// Add two Text fields

			await structureBuilderPage.addField('Text');

			await structureBuilderPage.changeFieldSettings({
				label: 'Field 1',
			});

			await structureBuilderPage.addField('Text');

			await structureBuilderPage.changeFieldSettings({
				label: 'Field 2',
			});

			// Publish the structure and check standard toast is shown

			await expect(async () => {
				await structureBuilderPage.publishButton.click({timeout: 1000});

				await waitForAlert(
					page,
					`Success:${label} was published successfully.`,
					{exact: true, timeout: 2000}
				);
			}).toPass();

			// Customize the experience

			await structureBuilderPage.customizeExperience();

			const fragmentId = await pageEditorPage.getFragmentId('Text', 0);

			await pageEditorPage.deleteFragment(fragmentId);

			// Go back to structure builder

			await clickAndExpectToBeVisible({
				target: page.getByText('Structure Fields'),
				trigger: page
					.locator('.management-bar')
					.getByRole('link', {name: 'Back'}),
			});

			// Publish again and check edit experience link is show in toast

			await expect(async () => {
				await structureBuilderPage.publishButton.click({timeout: 1000});

				await waitForAlert(
					page,
					'Remember to review the customized experience if needed'
				);
			}).toPass();
		}
	);

	test(
		'Can autogenerate default experience after customizing it',
		{
			tag: '@LPD-50376',
		},
		async ({page, pageEditorPage, structureBuilderPage}) => {

			// Create structure

			await createStructure({
				label: `StructureName${getRandomInt()}`,
				page: structureBuilderPage,
			});

			// Customize the experience and add a fragment

			await structureBuilderPage.customizeExperience();

			await pageEditorPage.addFragment('Basic Components', 'Heading');

			// Regenerate Display Page and check the Heading is not present

			await pageEditorPage.regenerateDisplayPage();

			await page
				.getByText('Select a Page Element', {exact: true})
				.waitFor();

			await expect(
				page.locator(
					'.lfr-layout-structure-item-basic-component-heading'
				)
			).not.toBeVisible();
		}
	);
});

test(
	'Add correct initial fields depending on type',
	{
		tag: '@LPD-50371',
	},
	async ({page, structureBuilderPage}) => {

		// Create structure

		await createStructure({
			label: `StructureName${getRandomInt()}`,
			page: structureBuilderPage,
		});

		// Type content and check initial fields

		await structureBuilderPage.changeStructureSettings({
			label: getRandomString(),
		});

		await expect(
			page.locator('.treeview-link', {hasText: 'Title'})
		).toBeVisible();

		await expect(
			page.locator('.treeview-link', {hasText: 'File'})
		).not.toBeVisible();

		// Check with type file

		await structureBuilderPage.createStructure('file');

		await structureBuilderPage.changeStructureSettings({
			label: getRandomString(),
		});

		await expect(
			page.locator('.treeview-link', {hasText: 'Title'})
		).toBeVisible();

		await expect(
			page.locator('.treeview-link', {hasText: 'File'})
		).toBeVisible();
	}
);

test.describe('Referenced structures', () => {
	test(
		'Can reference several structures and they are persisted',
		{
			tag: '@LPD-49645',
		},
		async ({page, structureBuilderPage}) => {
			const label1 = getRandomString();
			const label2 = getRandomString();
			const label3 = getRandomString();
			const label4 = getRandomString();

			const name1 = `StructureName${getRandomInt()}`;
			const name2 = `StructureName${getRandomInt()}`;

			// Create three structures, one of them in draft

			await createStructure({
				label: label1,
				name: name1,
				page: structureBuilderPage,
			});

			await createStructure({
				label: label2,
				name: name2,
				page: structureBuilderPage,
			});

			await createStructure({
				label: label3,
				page: structureBuilderPage,
				publish: false,
			});

			// Create another one and reference the first two

			const externalReferenceCode4 = await createStructure({
				label: label4,
				page: structureBuilderPage,
			});

			await structureBuilderPage.addReferencedStructures([
				label1,
				label2,
			]);

			// Assert correct label style

			await expect(
				page.locator('.label-warning', {
					hasText: 'Referenced Structure',
				})
			).toBeVisible();

			// Check the one in draft can't be referenced

			await expect(async () => {
				await clickAndExpectToBeVisible({
					target: page.getByRole('menuitem', {
						exact: true,
						name: 'Referenced Structure',
					}),
					trigger: page.getByLabel('Add Field'),
				});

				await clickAndExpectToBeVisible({
					target: page.locator('.modal-title', {
						hasText: 'Referenced Structure',
					}),
					timeout: 2000,
					trigger: page.getByRole('menuitem', {
						exact: true,
						name: 'Referenced Structure',
					}),
				});

				await page.getByLabel('Structures').click({timeout: 1000});

				await expect(
					page.getByRole('option', {name: label1})
				).toBeVisible();

				await expect(
					page.getByRole('option', {name: label3})
				).not.toBeVisible();

				// Check we can't click Add without structures

				await page
					.locator('.modal-title', {
						hasText: 'Referenced Structure',
					})
					.click({timeout: 500});

				await clickAndExpectToBeVisible({
					target: page
						.locator('.modal-body')
						.getByText('This field is required'),
					trigger: page.locator('.modal-footer').getByText('Add'),
				});

				// Close modal

				await clickAndExpectToBeHidden({
					target: page.locator('.modal-title', {
						hasText: 'Referenced Structure',
					}),
					timeout: 2000,
					trigger: page.locator('.modal-header .close'),
				});
			}).toPass();

			// Publish the structure

			await structureBuilderPage.publishStructure();

			// Check everything is persisted

			await structureBuilderPage.editStructure(externalReferenceCode4);

			await expect(
				page.locator('.treeview-link', {hasText: label1})
			).toBeVisible();

			await expect(
				page.locator('.treeview-link', {hasText: label2})
			).toBeVisible();

			// Select referenced structures and check correct values are shown

			await structureBuilderPage.selectFields([{label: label1}]);

			await expect(page.getByLabel('Structure Name')).toHaveValue(name1);

			await structureBuilderPage.selectFields([{label: label2}]);

			await expect(page.getByLabel('Structure Name')).toHaveValue(name2);
		}
	);

	test(
		'Can edit referenced structure in another tab',
		{
			tag: '@LPD-49645',
		},
		async ({context, page, structureBuilderPage}) => {
			const label1 = getRandomString();
			const label2 = getRandomString();

			// Create one structure

			await createStructure({
				label: label1,
				page: structureBuilderPage,
			});

			// Create another one and reference the first one

			await createStructure({
				label: label2,
				page: structureBuilderPage,
			});

			await structureBuilderPage.addReferencedStructures([label1]);

			// Check we can't edit referenced structure

			await structureBuilderPage.selectFields([{label: label1}]);

			await expect(page.getByLabel('Structure Name')).toBeDisabled();
			await expect(page.getByLabel('ERC')).toBeDisabled();
			await expect(structureBuilderPage.spaceSelector).toBeDisabled();

			// Check we can't edit referenced structure fields

			await structureBuilderPage.selectFields([{label: 'Title', nth: 1}]);

			await expect(
				page.getByRole('button', {name: 'Field Options'})
			).not.toBeVisible();

			await expect(page.getByLabel('Label')).toBeDisabled();
			await expect(page.getByLabel('ERC')).toBeDisabled();
			await expect(page.getByLabel('Field Name')).toBeDisabled();

			// Publish the structure

			await structureBuilderPage.publishStructure();

			// Edit referenced structure in another tab

			const pagePromise = context.waitForEvent('page');

			await structureBuilderPage.selectFields([{label: label1}]);

			await structureBuilderPage.clickFieldAction(
				{label: label1},
				'Edit'
			);

			const newPage = await pagePromise;

			const newStructureBuilderPage = new StructureBuilderPage(newPage);

			await newPage
				.locator('.component-tbar')
				.getByText(label1)
				.waitFor();

			// Add new fields and publish

			await newStructureBuilderPage.addField('Date');
			await newStructureBuilderPage.addField('Long Text');

			await expect(async () => {
				await newStructureBuilderPage.publishButton.click({
					timeout: 500,
				});

				await expect(
					newPage.locator('.modal-title', {hasText: 'Publish'})
				).toBeVisible({timeout: 3000});

				await newPage
					.getByText('Publish and Propagate')
					.click({timeout: 500});

				await waitForAlert(newPage, 'published', {timeout: 2000});
			}).toPass();

			// Check in first structure that the tree is updated with the new field

			await structureBuilderPage.expandField({label: label1});

			const dateTreeItem = page.locator('.treeview-link', {
				hasText: 'Date',
			});

			await expect(dateTreeItem).toBeVisible();

			// Check we can't delete referenced structure fields

			await structureBuilderPage.selectFields([{label: 'Date'}]);

			await expect(
				dateTreeItem.getByLabel('Field Options')
			).not.toBeVisible();

			// Change field and check correct values are shown

			await structureBuilderPage.selectFields([{label: 'Long Text'}]);

			await expect(page.getByLabel('Label')).toHaveValue('Long Text');

			await structureBuilderPage.selectFields([{label: 'Date'}]);

			await expect(page.getByLabel('Label')).toHaveValue('Date');
		}
	);
});

test.describe('Repeatable groups', () => {
	test(
		'Groups can be created, persisted and ungrouped',
		{
			tag: '@LPD-50378',
		},
		async ({page, structureBuilderPage}) => {

			// Create structure

			const erc = await createStructure({
				label: getRandomString(),
				name: `StructureName${getRandomInt()}`,
				page: structureBuilderPage,
				publish: false,
			});

			// Add fields

			await structureBuilderPage.addField('Text');
			await structureBuilderPage.addField('Date');
			await structureBuilderPage.addField('Decimal');

			// Create repeatable group with two of them

			await structureBuilderPage.createRepeatableGroup({
				fields: [{label: 'Text'}, {label: 'Date'}],
				label: 'Repeatable Group 1',
			});

			// Check recently added group is expanded by default

			await expect(
				page.locator('.treeview-link', {hasText: 'Text'})
			).toBeVisible();

			await expect(
				page.locator('.treeview-link', {hasText: 'Date'})
			).toBeVisible();

			// Create another group inside the first one

			await structureBuilderPage.createRepeatableGroup({
				fields: [{label: 'Date'}],
				label: 'Repeatable Group 2',
			});

			// Assert correct label style

			await expect(
				page.locator('.label-success', {hasText: 'Repeatable Group'})
			).toBeVisible();

			// Check groups are persisted

			await structureBuilderPage.publishStructure();

			await structureBuilderPage.editStructure(erc);

			await expect(
				page.locator('.treeview-link', {hasText: 'Repeatable Group 1'})
			).toBeVisible();

			await structureBuilderPage.expandField({
				label: 'Repeatable Group 1',
			});

			await expect(
				page.locator('.treeview-link', {hasText: 'Repeatable Group 2'})
			).toBeVisible();

			// Add a new group and ungroup it

			await structureBuilderPage.addField('Boolean');

			await structureBuilderPage.createRepeatableGroup({
				fields: [{label: 'Boolean'}],
				label: 'Repeatable Group 3',
			});

			await structureBuilderPage.clickFieldAction(
				{label: 'Repeatable Group 3'},
				'Ungroup'
			);

			await expect(
				page.locator('.treeview-link', {hasText: 'Repeatable Group 2'})
			).toBeVisible();

			await expect(
				page.locator('.treeview-link', {hasText: 'Boolean'})
			).toBeVisible();
		}
	);

	test(
		'Check restrictions for group creation',
		{
			tag: '@LPD-50378',
		},
		async ({page, structureBuilderPage}) => {

			// Create structure

			const erc = await createStructure({
				label: getRandomString(),
				name: `StructureName${getRandomInt()}`,
				page: structureBuilderPage,
				publish: false,
			});

			// Check a group can't be created if there's only one field

			await structureBuilderPage.selectFields([{label: 'Title'}]);

			await structureBuilderPage.clickFieldAction(
				{label: 'Title'},
				'Create Repeatable Group'
			);

			await clickAndExpectToBeVisible({
				target: page.getByText(
					'The repeatable group cannot be created because at least one field is required.'
				),
				trigger: page.getByRole('menuitem', {
					name: 'Create Repeatable Group',
				}),
			});

			await clickAndExpectToBeHidden({
				target: page.getByText(
					'The repeatable group cannot be created because at least one field is required.'
				),
				trigger: page.locator('.modal-footer').getByText('Done'),
			});

			// Add fields

			await structureBuilderPage.addField('Text');
			await structureBuilderPage.addField('Date');
			await structureBuilderPage.addField('Decimal');

			// Create repeatable group with two of them

			await structureBuilderPage.createRepeatableGroup({
				fields: [{label: 'Text'}, {label: 'Date'}],
				label: 'Repeatable Group 1',
			});

			// Check a group can't be created with fields that have different parent

			await structureBuilderPage.selectFields([
				{label: 'Date'},
				{label: 'Decimal'},
			]);

			await clickAndExpectToBeVisible({
				target: page.getByRole('menuitem', {
					name: 'Create Repeatable Group',
				}),
				trigger: page.getByLabel('Selection Options'),
			});

			await clickAndExpectToBeVisible({
				target: page.getByText(
					'A repeatable group requires all selected items to be at the same hierarchy level. Adjust your selection and try again.'
				),
				trigger: page.getByRole('menuitem', {
					name: 'Create Repeatable Group',
				}),
			});

			await clickAndExpectToBeHidden({
				target: page.getByText(
					'A repeatable group requires all selected items to be at the same hierarchy level. Adjust your selection and try again.'
				),
				trigger: page.locator('.modal-footer').getByText('Done'),
			});

			// Check a group can't be created with published fields

			await structureBuilderPage.publishStructure();

			await structureBuilderPage.selectFields([
				{label: 'Title'},
				{label: 'Decimal'},
			]);

			await clickAndExpectToBeVisible({
				target: page.getByRole('menuitem', {
					name: 'Create Repeatable Group',
				}),
				trigger: page.getByLabel('Selection Options'),
			});

			await clickAndExpectToBeVisible({
				target: page.getByText(
					'The repeatable group cannot be created because one or more fields of the selection are already published.'
				),
				trigger: page.getByRole('menuitem', {
					name: 'Create Repeatable Group',
				}),
			});

			await clickAndExpectToBeHidden({
				target: page.getByText(
					'The repeatable group cannot be created because one or more fields of the selection are already published.'
				),
				trigger: page.locator('.modal-footer').getByText('Done'),
			});

			// Check we can't ungroup the published group

			await structureBuilderPage.publishStructure();

			await structureBuilderPage.editStructure(erc);

			await structureBuilderPage.clickFieldAction(
				{label: 'Repeatable Group 1'},
				'Ungroup'
			);

			await page
				.getByText(
					'The ungroup action cannot be done because this repeatable group is already published.'
				)
				.waitFor();

			await clickAndExpectToBeHidden({
				target: page.getByText(
					'The ungroup action cannot be done because this repeatable group is already published.'
				),
				trigger: page.locator('.modal-footer').getByText('Done'),
			});
		}
	);
});
