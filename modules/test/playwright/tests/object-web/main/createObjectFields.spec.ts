/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ObjectDefinition,
	ObjectField,
} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {ObjectFieldsPage} from '../../../pages/object-web/object-fields/ObjectFieldsPage';
import {waitForAlert} from '../../../utils/waitForAlert';
import {generateObjectFields} from '../utils/generateObjectFields';

const test = mergeTests(
	dataApiHelpersTest,
	isolatedSiteTest,
	loginTest(),
	objectPagesTest
);

let createdObjectDefinition: ObjectDefinition;
let createdObjectField: ObjectField;

test.beforeEach(async ({apiHelpers, objectFieldsPage}) => {
	const objectFields = generateObjectFields({
		objectFieldBusinessTypes: ['Text'],
	});

	const objectDefinition =
		await apiHelpers.objectAdmin.postRandomObjectDefinition({
			objectFields,
			status: {code: 2},
		});

	apiHelpers.data.push({
		id: objectDefinition.id,
		type: 'objectDefinition',
	});

	createdObjectDefinition = objectDefinition;
	createdObjectField = objectFields[0];

	await objectFieldsPage.goto(objectDefinition.label['en_US']);
});

async function assertSearchableProperties(
	objectFieldsPage: ObjectFieldsPage,
	visible: boolean
) {
	await expect(
		objectFieldsPage.iframeLocator.getByRole('radio', {name: 'Keyword'})
	).toBeVisible({visible});
	await expect(
		objectFieldsPage.iframeLocator.getByText('Language')
	).toBeVisible({visible});
	await expect(
		objectFieldsPage.iframeLocator.getByRole('radio', {name: 'Text'})
	).toBeVisible({visible});
}

test.describe('Fields Tab', () => {
	test('Verify it is possible to cancel the creation of a custom object field', async ({
		objectFieldsPage,
		page,
	}) => {
		await objectFieldsPage.addObjectFieldButton.click();

		await objectFieldsPage.objectFieldLabelInput.fill('Cancel Field');

		await page.getByRole('button', {name: 'Cancel'}).click();

		await page.getByPlaceholder('Search').fill('Cancel Field');
		await page.keyboard.press('Enter');

		await expect(page.getByText('No Results Found')).toBeVisible();
	});

	test('Verify it is possible to delete a custom object field depending on the object state', async ({
		apiHelpers,
		objectFieldsPage,
		page,
	}) => {
		await test.step('Verify it is possible to delete the only custom object field before the object is published', async () => {
			await objectFieldsPage.deleteObjectField(false, -1);
		});

		await test.step('Verify it is not possible to delete the only custom object field after the object is published', async () => {
			await objectFieldsPage.addObjectField({
				objectFieldBusinessType: 'Text',
				objectFieldLabel: 'Text1',
			});

			await apiHelpers.objectAdmin.postObjectDefinitionPublish({
				objectDefinitionId: createdObjectDefinition.id,
			});

			await objectFieldsPage.deleteObjectField(false, -1);

			await expect(page.getByText('Deletion Not Allowed')).toBeVisible();
			await expect(
				page.getByText(
					`The object field "Text1" cannot be deleted because it is the only custom object field of the published object definition.`
				)
			).toBeVisible();

			await page.getByRole('button', {name: 'Done'}).click();
		});

		await test.step('Verify it is possible to delete a custom object field when it is not the only one after the object is published', async () => {
			await objectFieldsPage.addObjectField({
				objectFieldBusinessType: 'Text',
				objectFieldLabel: 'Text2',
			});

			await objectFieldsPage.deleteObjectField(true, -1);

			await expect(
				page.getByRole('row').filter({hasText: 'Text1'})
			).toBeVisible();
			await expect(
				page.getByRole('row').filter({hasText: 'Text2'})
			).toBeHidden();
		});
	});

	test('Verify it is not possible to add a custom object field when required properties are missing', async ({
		objectFieldsPage,
		page,
	}) => {
		await objectFieldsPage.addObjectFieldButton.click();

		await test.step('Verify required error is shown three times when Label, Name and Type are blank', async () => {
			await objectFieldsPage.saveButton.click();

			await expect(page.getByText('Required')).toHaveCount(3);
		});

		await test.step('Verify required error is shown two times after filling the Name', async () => {
			await objectFieldsPage.objectFieldNameInput.fill('testField');

			await objectFieldsPage.saveButton.click();

			await expect(page.getByText('Required')).toHaveCount(2);
		});

		await test.step('Verify required error is shown one time after filling the Label', async () => {
			await objectFieldsPage.objectFieldLabelInput.fill('Test Field');

			await objectFieldsPage.saveButton.click();

			await expect(page.getByText('Required')).toHaveCount(1);
		});

		await test.step('Verify required error is shown for the picklist when the Type is changed to Picklist', async () => {
			await objectFieldsPage.objectFieldOptionsDropdown.click();
			await page
				.getByRole('option', {exact: true, name: 'Picklist'})
				.click();

			await objectFieldsPage.saveButton.click();

			await expect(page.getByText('Required')).toHaveCount(1);
		});
	});

	test('Verify it is not possible to add custom object field with invalid name', async ({
		objectFieldsPage,
		page,
	}) => {
		await test.step('Verify that Name is autofilled when Label is filled', async () => {
			await objectFieldsPage.addObjectFieldButton.click();

			await objectFieldsPage.objectFieldLabelInput.fill('Test Field');

			await expect(objectFieldsPage.objectFieldNameInput).toHaveValue(
				'testField'
			);
		});

		await test.step('Verify it is not possible to save Name with special characters', async () => {
			await objectFieldsPage.objectFieldNameInput.fill('Field@Special!');

			await objectFieldsPage.objectFieldOptionsDropdown.click();
			await page.getByRole('option', {exact: true, name: 'Text'}).click();

			await objectFieldsPage.saveButton.click();

			await expect(
				page.getByText('Name must only contain letters and digits.')
			).toBeVisible();
		});

		await test.step('Verify it is not possible to save Name that begin with uppercase letter', async () => {
			await objectFieldsPage.objectFieldNameInput.fill('FieldUpperCase');

			await objectFieldsPage.saveButton.click();

			await expect(
				page.getByText(
					'The first character of a name must be a lowercase letter.'
				)
			).toBeVisible();
		});

		await test.step('Verify it is not possible to save Duplicated name', async () => {
			await objectFieldsPage.objectFieldNameInput.fill(
				createdObjectField.name
			);

			await objectFieldsPage.saveButton.click();

			await expect(
				page.getByText('This name is already in use. Try another one.')
			).toBeVisible();
		});
	});

	test('Verify it is possible to update field properties depending on the object state', async ({
		apiHelpers,
		objectFieldsPage,
		page,
	}) => {
		await test.step('Before the object is published', async () => {
			await test.step('Update Label with translation', async () => {
				await objectFieldsPage.openObjectField(
					createdObjectField.label!['en_US']
				);

				await objectFieldsPage.iframeLocator
					.getByLabel('Label')
					.fill('Updated Label');

				await objectFieldsPage.iframeLocator
					.getByTitle('en_US')
					.click();
				await objectFieldsPage.iframeLocator
					.getByRole('option', {name: 'pt_BR'})
					.click();

				await objectFieldsPage.iframeLocator
					.getByLabel('Label')
					.fill('Rótulo Atualizado');
			});

			await test.step('Verify that Keyword, Language, and Text fields are not visible for non-searchable Text field', async () => {
				await assertSearchableProperties(objectFieldsPage, false);
			});

			await test.step('Update Mandatory, Name and Searchable', async () => {
				await objectFieldsPage.iframeLocator
					.getByRole('switch', {name: 'Mandatory'})
					.check();
				await objectFieldsPage.iframeLocator
					.locator('input[name="name"]')
					.fill('updatedName');
				await objectFieldsPage.iframeLocator
					.getByRole('switch', {name: 'Searchable'})
					.check();
			});

			await test.step('Verify that Keyword, Language, and Text fields are visible for searchable Text field', async () => {
				await assertSearchableProperties(objectFieldsPage, true);
			});

			await test.step('Update type to Integer', async () => {
				await objectFieldsPage.iframeLocator
					.getByRole('combobox', {name: 'Type'})
					.click();
				await objectFieldsPage.iframeLocator
					.getByRole('option', {exact: true, name: 'Integer'})
					.click();
			});

			await test.step('Verify that Keyword, Language, and Text fields are not visible for searchable Integer field', async () => {
				await assertSearchableProperties(objectFieldsPage, false);
			});

			await objectFieldsPage.editFieldSaveButton.click();

			await waitForAlert(
				page,
				'The object field was updated successfully'
			);

			await test.step('Verify that Label and Type columns are displayed for the updated field', async () => {
				await page.getByPlaceholder('Search').fill('Updated Label');
				await page.keyboard.press('Enter');

				await expect(
					page
						.getByRole('row')
						.filter({hasText: 'Updated Label'})
						.getByText('Integer')
				).toBeVisible();
			});

			await test.step('Verify that translated Label is updated', async () => {
				await objectFieldsPage.openObjectFieldByDropdownAction(
					'Updated Label'
				);

				await objectFieldsPage.iframeLocator
					.getByTitle('en_US')
					.click();
				await objectFieldsPage.iframeLocator
					.getByRole('option', {name: 'pt_BR'})
					.click();

				await expect(
					objectFieldsPage.iframeLocator.getByLabel('Label')
				).toHaveValue('Rótulo Atualizado');
			});

			await test.step('Verify that Mandatory, Name, Searchable and Type are updated', async () => {
				await expect(
					objectFieldsPage.iframeLocator.getByRole('switch', {
						name: 'Mandatory',
					})
				).toBeChecked();
				await expect(
					objectFieldsPage.iframeLocator.locator('input[name="name"]')
				).toHaveValue('updatedName');
				await expect(
					objectFieldsPage.iframeLocator.getByRole('switch', {
						name: 'Searchable',
					})
				).toBeChecked();
				await expect(
					objectFieldsPage.iframeLocator.getByRole('combobox', {
						name: 'Type',
					})
				).toHaveText('Integer');
			});
		});

		await test.step('After the object is published', async () => {
			await apiHelpers.objectAdmin.postObjectDefinitionPublish({
				objectDefinitionId: createdObjectDefinition.id,
			});

			await page.reload();

			await test.step('Update Label with translation', async () => {
				await objectFieldsPage.openObjectField('Updated Label');

				await objectFieldsPage.iframeLocator
					.getByLabel('Label')
					.fill('New Updated Label');

				await objectFieldsPage.iframeLocator
					.getByTitle('en_US')
					.click();
				await objectFieldsPage.iframeLocator
					.getByRole('option', {name: 'pt_BR'})
					.click();

				await objectFieldsPage.iframeLocator
					.getByLabel('Label')
					.fill('Novo Rótulo Atualizado');
			});

			await test.step('Uncheck Mandatory and Searchable fields', async () => {
				await objectFieldsPage.iframeLocator
					.getByRole('switch', {name: 'Mandatory'})
					.uncheck();
				await objectFieldsPage.iframeLocator
					.getByRole('switch', {name: 'Searchable'})
					.uncheck();
			});

			await test.step('Verify it is not possible to update Name and Type fields', async () => {
				await expect(
					objectFieldsPage.iframeLocator.locator('input[name="name"]')
				).toBeDisabled();
				await expect(
					objectFieldsPage.iframeLocator.getByRole('combobox', {
						name: 'Type',
					})
				).toBeDisabled();
			});

			await objectFieldsPage.editFieldSaveButton.click();

			await waitForAlert(
				page,
				'The object field was updated successfully'
			);

			await test.step('Verify that translated Label is updated', async () => {
				await objectFieldsPage.openObjectFieldByDropdownAction(
					'New Updated Label'
				);

				await objectFieldsPage.iframeLocator
					.getByTitle('en_US')
					.click();
				await objectFieldsPage.iframeLocator
					.getByRole('option', {name: 'pt_BR'})
					.click();

				await expect(
					objectFieldsPage.iframeLocator.getByLabel('Label')
				).toHaveValue('Novo Rótulo Atualizado');
			});

			await test.step('Verify that Mandatory is unchecked and disabled', async () => {
				await expect(
					objectFieldsPage.iframeLocator.getByRole('switch', {
						name: 'Mandatory',
					})
				).not.toBeChecked();
				await expect(
					objectFieldsPage.iframeLocator.getByRole('switch', {
						name: 'Mandatory',
					})
				).toBeDisabled();
			});

			await test.step('Verify that Searchable is unchecked', async () => {
				await expect(
					objectFieldsPage.iframeLocator.getByRole('switch', {
						name: 'Searchable',
					})
				).not.toBeChecked();
				await expect(
					objectFieldsPage.iframeLocator.getByRole('switch', {
						name: 'Searchable',
					})
				).toBeEnabled();
			});
		});
	});
});
