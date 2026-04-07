/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import {waitForAlert} from '../../../utils/waitForAlert';
import {generateObjectFields} from './utils/generateObjectFields';

const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	objectPagesTest
);

test.describe('Fields Tab', () => {
	test(
		'Verify it is possible to cancel the creation of a Field',
		{tag: '@LPS-135549'},
		async ({apiHelpers, objectFieldsPage, page}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await objectFieldsPage.goto(objectDefinition.label['en_US']);

			await objectFieldsPage.addObjectFieldButton.click();

			await objectFieldsPage.objectFieldLabelInput.fill('Cancel Field');

			await objectFieldsPage.objectFieldOptionsDropdown.click();

			await page.getByRole('option', {exact: true, name: 'Text'}).click();

			await page.getByRole('button', {name: 'Cancel'}).click();

			await expect(page.getByText('Cancel Field')).toBeHidden();
		}
	);

	test(
		'Verify it is not possible to delete a Custom Object field after the Object is published',
		{tag: '@LPS-137879'},
		async ({apiHelpers, objectFieldsPage, page}) => {
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

			await objectFieldsPage.goto(objectDefinition.label['en_US']);

			const fieldRow = page
				.getByRole('row')
				.filter({hasText: objectFields[0].label!['en_US']});

			await expect(fieldRow.locator('.dropdown-toggle')).toBeHidden();
		}
	);

	test(
		'Verify it is possible to delete a Custom Object field before the Object is published',
		{tag: '@LPS-137879'},
		async ({apiHelpers, objectFieldsPage, page}) => {
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

			await objectFieldsPage.goto(objectDefinition.label['en_US']);

			await objectFieldsPage.deleteObjectFieldByLabel(
				objectFields[0].label!['en_US']
			);

			await waitForAlert(page);

			await expect(
				page.getByText(objectFields[0].label!['en_US'])
			).toBeHidden();
		}
	);

	test(
		'Verify that it is not possible to create a Field with a duplicated Field Name',
		{tag: '@LPS-135549'},
		async ({apiHelpers, objectFieldsPage, page}) => {
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

			await objectFieldsPage.goto(objectDefinition.label['en_US']);

			await objectFieldsPage.addObjectField({
				objectFieldBusinessType: 'Text',
				objectFieldLabel: objectFields[0].label!['en_US'],
			});

			await expect(
				page.getByText('This name is already in use. Try another.')
			).toBeVisible();
		}
	);

	test(
		'Verify it is not possible to leave the Field Name field blank',
		{tag: '@LPS-135549'},
		async ({apiHelpers, objectFieldsPage, page}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await objectFieldsPage.goto(objectDefinition.label['en_US']);

			await objectFieldsPage.addObjectFieldButton.click();

			await objectFieldsPage.objectFieldOptionsDropdown.click();
			await page.getByRole('option', {exact: true, name: 'Text'}).click();

			await objectFieldsPage.saveButton.click();

			await expect(page.getByText('Required')).toBeVisible();
		}
	);

	test(
		'Verify it is not possible to leave the Label field blank',
		{tag: '@LPS-135549'},
		async ({apiHelpers, objectFieldsPage, page}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await objectFieldsPage.goto(objectDefinition.label['en_US']);

			await objectFieldsPage.addObjectFieldButton.click();

			await objectFieldsPage.objectFieldOptionsDropdown.click();
			await page.getByRole('option', {exact: true, name: 'Text'}).click();

			await objectFieldsPage.saveButton.click();

			await expect(page.getByText('Required')).toBeVisible();
		}
	);

	test(
		'Verify it is not possible to leave the Picklist field empty when creating an Object Picklist field',
		{tag: '@LPS-136595'},
		async ({apiHelpers, objectFieldsPage, page}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await objectFieldsPage.goto(objectDefinition.label['en_US']);

			await objectFieldsPage.addObjectFieldButton.click();

			await objectFieldsPage.objectFieldLabelInput.fill('Picklist Field');

			await objectFieldsPage.objectFieldOptionsDropdown.click();
			await page
				.getByRole('option', {exact: true, name: 'Picklist'})
				.click();

			await objectFieldsPage.saveButton.click();

			await expect(page.getByText('Required')).toBeVisible();
		}
	);

	test(
		'Verify it is not possible to leave the Type field blank',
		{tag: '@LPS-135549'},
		async ({apiHelpers, objectFieldsPage, page}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await objectFieldsPage.goto(objectDefinition.label['en_US']);

			await objectFieldsPage.addObjectFieldButton.click();

			await objectFieldsPage.objectFieldLabelInput.fill('Test Field');

			await objectFieldsPage.saveButton.click();

			await expect(page.getByText('Required')).toBeVisible();
		}
	);

	test(
		'Verify it is not possible to save with special characters for the Field Name',
		{tag: '@LPS-135549'},
		async ({apiHelpers, objectFieldsPage, page}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await objectFieldsPage.goto(objectDefinition.label['en_US']);

			await objectFieldsPage.addObjectFieldButton.click();

			await objectFieldsPage.objectFieldLabelInput.fill('Field@Special!');

			await objectFieldsPage.objectFieldOptionsDropdown.click();
			await page.getByRole('option', {exact: true, name: 'Text'}).click();

			await objectFieldsPage.saveButton.click();

			await expect(
				page.getByText(
					'The field name must begin with a lower case letter and contain only alphanumeric characters.'
				)
			).toBeVisible();
		}
	);

	test(
		'Verify it is not possible to save with the first character of the Field Name in upper case',
		{tag: '@LPS-135549'},
		async ({apiHelpers, objectFieldsPage, page}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await objectFieldsPage.goto(objectDefinition.label['en_US']);

			await objectFieldsPage.addObjectFieldButton.click();

			await objectFieldsPage.objectFieldLabelInput.fill('UpperCaseField');

			await objectFieldsPage.objectFieldOptionsDropdown.click();
			await page.getByRole('option', {exact: true, name: 'Text'}).click();

			await objectFieldsPage.saveButton.click();

			await expect(
				page.getByText(
					'The field name must begin with a lower case letter and contain only alphanumeric characters.'
				)
			).toBeVisible();
		}
	);

	test(
		'Verify it is possible to set a field as Mandatory',
		{tag: '@LPS-135549'},
		async ({apiHelpers, objectFieldsPage, page}) => {
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

			await objectFieldsPage.goto(objectDefinition.label['en_US']);

			await objectFieldsPage.openObjectField(
				objectFields[0].label!['en_US']
			);

			const mandatorySwitch = page
				.frameLocator('iframe')
				.getByRole('switch', {name: 'Mandatory'});

			await mandatorySwitch.check();

			await objectFieldsPage.editFieldSaveButton.click();

			await waitForAlert(
				page,
				'The object field was updated successfully'
			);

			await objectFieldsPage.openObjectField(
				objectFields[0].label!['en_US']
			);

			await expect(mandatorySwitch).toBeChecked();
		}
	);

	test(
		'Verify it is possible to update the Label of a Field after the Object is published',
		{tag: '@LPS-135635'},
		async ({apiHelpers, objectFieldsPage, page}) => {
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

			await objectFieldsPage.goto(objectDefinition.label['en_US']);

			await objectFieldsPage.openObjectField(
				objectFields[0].label!['en_US']
			);

			const newLabel = 'UpdatedLabel' + getRandomInt();

			await page
				.frameLocator('iframe')
				.getByLabel('Label')
				.fill(newLabel);

			await objectFieldsPage.editFieldSaveButton.click();

			await waitForAlert(
				page,
				'The object field was updated successfully'
			);
		}
	);

	test(
		'Verify it is possible to update the Label of a Field before the Object is published',
		{tag: '@LPS-135635'},
		async ({apiHelpers, objectFieldsPage, page}) => {
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

			await objectFieldsPage.goto(objectDefinition.label['en_US']);

			await objectFieldsPage.openObjectField(
				objectFields[0].label!['en_US']
			);

			const newLabel = 'UpdatedLabel' + getRandomInt();

			await page
				.frameLocator('iframe')
				.getByLabel('Label')
				.fill(newLabel);

			await objectFieldsPage.editFieldSaveButton.click();

			await waitForAlert(
				page,
				'The object field was updated successfully'
			);
		}
	);

	test(
		'Verify it is possible to update the Mandatory of a Field before the Object is published',
		{tag: '@LPS-135635'},
		async ({apiHelpers, objectFieldsPage, page}) => {
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

			await objectFieldsPage.goto(objectDefinition.label['en_US']);

			await objectFieldsPage.openObjectField(
				objectFields[0].label!['en_US']
			);

			const mandatorySwitch = page
				.frameLocator('iframe')
				.getByRole('switch', {name: 'Mandatory'});

			await mandatorySwitch.check();

			await objectFieldsPage.editFieldSaveButton.click();

			await waitForAlert(
				page,
				'The object field was updated successfully'
			);
		}
	);

	test(
		'Verify it is possible to update the Name of a Field before the Object is published',
		{tag: '@LPS-135635'},
		async ({apiHelpers, objectFieldsPage, page}) => {
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

			await objectFieldsPage.goto(objectDefinition.label['en_US']);

			await objectFieldsPage.openObjectField(
				objectFields[0].label!['en_US']
			);

			const newName = 'updatedName' + getRandomInt();

			await page.locator('input[name="name"]').fill(newName);

			await objectFieldsPage.editFieldSaveButton.click();

			await waitForAlert(
				page,
				'The object field was updated successfully'
			);
		}
	);

	test(
		'Verify it is possible to update the Searchable section before the Object is published',
		{tag: '@LPS-135635'},
		async ({apiHelpers, objectFieldsPage, page}) => {
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

			await objectFieldsPage.goto(objectDefinition.label['en_US']);

			await objectFieldsPage.openObjectField(
				objectFields[0].label!['en_US']
			);

			await objectFieldsPage.advancedTab.click();

			const searchableToggle = page
				.frameLocator('iframe')
				.getByRole('switch', {name: 'Searchable'});

			await searchableToggle.uncheck();

			await objectFieldsPage.editFieldSaveButton.click();

			await waitForAlert(
				page,
				'The object field was updated successfully'
			);
		}
	);

	test(
		'Verify it is possible to update the Type of a Field before the Object is published',
		{tag: '@LPS-135635'},
		async ({apiHelpers, objectFieldsPage, page}) => {
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

			await objectFieldsPage.goto(objectDefinition.label['en_US']);

			await objectFieldsPage.openObjectField(
				objectFields[0].label!['en_US']
			);

			await page.getByText('Select an Option').first().click();
			await page
				.getByRole('option', {exact: true, name: 'Integer'})
				.click();

			await objectFieldsPage.editFieldSaveButton.click();

			await waitForAlert(
				page,
				'The object field was updated successfully'
			);
		}
	);

	test(
		'Verify it is not possible to update the Mandatory of a Field after the Object is published',
		{tag: '@LPS-135635'},
		async ({apiHelpers, objectFieldsPage, page}) => {
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

			await objectFieldsPage.goto(objectDefinition.label['en_US']);

			await objectFieldsPage.openObjectField(
				objectFields[0].label!['en_US']
			);

			await expect(
				page
					.frameLocator('iframe')
					.getByRole('switch', {name: 'Mandatory'})
			).toBeDisabled();
		}
	);

	test(
		'Verify it is not possible to update the Name of a Field after the Object is published',
		{tag: '@LPS-135635'},
		async ({apiHelpers, objectFieldsPage, page}) => {
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

			await objectFieldsPage.goto(objectDefinition.label['en_US']);

			await objectFieldsPage.openObjectField(
				objectFields[0].label!['en_US']
			);

			await expect(page.locator('input[name="name"]')).toBeDisabled();
		}
	);

	test(
		'Verify it is not possible to update the Type of a Field after the Object is published',
		{tag: '@LPS-135635'},
		async ({apiHelpers, objectFieldsPage, page}) => {
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

			await objectFieldsPage.goto(objectDefinition.label['en_US']);

			await objectFieldsPage.openObjectField(
				objectFields[0].label!['en_US']
			);

			await expect(
				page.getByText('Select an Option').first()
			).toHaveAttribute('aria-disabled', 'true');
		}
	);

	test(
		'Verify it is not possible to update the Searchable section after the Object is published',
		{tag: '@LPS-135635'},
		async ({apiHelpers, objectFieldsPage, page}) => {
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

			await objectFieldsPage.goto(objectDefinition.label['en_US']);

			await objectFieldsPage.openObjectField(
				objectFields[0].label!['en_US']
			);

			await objectFieldsPage.advancedTab.click();

			await expect(
				page
					.frameLocator('iframe')
					.getByRole('switch', {name: 'Searchable'})
			).toBeDisabled();
		}
	);

	test(
		'Verify it is possible to set a different language value for a Field Label',
		{tag: '@LPS-135389'},
		async ({apiHelpers, objectFieldsPage, page}) => {
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

			await objectFieldsPage.goto(objectDefinition.label['en_US']);

			await objectFieldsPage.openObjectField(
				objectFields[0].label!['en_US']
			);

			const iframeLocator = page.frameLocator('iframe');

			await iframeLocator
				.getByRole('button', {name: 'en-US'})
				.first()
				.click();
			await iframeLocator.getByRole('menuitem', {name: 'pt-BR'}).click();

			await iframeLocator.getByLabel('Label').fill('Rótulo em Português');

			await objectFieldsPage.editFieldSaveButton.click();

			await waitForAlert(
				page,
				'The object field was updated successfully'
			);
		}
	);

	test(
		'Verify it is possible to search for a field from a Custom Object',
		{tag: '@LPS-135547'},
		async ({apiHelpers, objectFieldsPage, page}) => {
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

			await objectFieldsPage.goto(objectDefinition.label['en_US']);

			const fieldLabel = objectFields[0].label!['en_US'];

			await page.getByPlaceholder('Search').fill(fieldLabel);

			await expect(page.getByText(fieldLabel)).toBeVisible();
		}
	);

	test(
		'Verify it is possible to search for a field from a System Object',
		{tag: '@LPS-135547'},
		async ({objectFieldsPage, page}) => {
			await objectFieldsPage.goto('User');

			await page.getByPlaceholder('Search').fill('Email Address');

			await expect(page.getByText('Email Address')).toBeVisible();
		}
	);

	test(
		'Verify it is possible to view the Details of a Field by clicking on the eye icon',
		{tag: '@LPS-135549'},
		async ({apiHelpers, objectFieldsPage, page}) => {
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

			await objectFieldsPage.goto(objectDefinition.label['en_US']);

			await page
				.getByRole('row')
				.filter({hasText: objectFields[0].label!['en_US']})
				.getByRole('link')
				.first()
				.click();

			await expect(
				page.getByText(objectFields[0].label!['en_US'])
			).toBeVisible();
		}
	);

	test(
		'Verify it is possible to view the Details of a Field by clicking on its name',
		{tag: '@LPS-135549'},
		async ({apiHelpers, objectFieldsPage, page}) => {
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

			await objectFieldsPage.goto(objectDefinition.label['en_US']);

			await objectFieldsPage.openObjectField(
				objectFields[0].label!['en_US']
			);

			await expect(
				page.getByText(objectFields[0].label!['en_US'])
			).toBeVisible();
		}
	);

	test(
		'Verify that the columns Name and Type are displayed for the Fields table',
		{tag: '@LPS-135549'},
		async ({apiHelpers, objectFieldsPage, page}) => {
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

			await objectFieldsPage.goto(objectDefinition.label['en_US']);

			const fieldRow = page
				.getByRole('row')
				.filter({hasText: objectFields[0].label!['en_US']});

			await expect(fieldRow).toBeVisible();
			await expect(fieldRow.getByText('Text')).toBeVisible();
		}
	);

	test(
		'Verify that the Field Name is autofilled when Label is filled',
		{tag: '@LPS-135549'},
		async ({apiHelpers, objectFieldsPage, page}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await objectFieldsPage.goto(objectDefinition.label['en_US']);

			await objectFieldsPage.addObjectFieldButton.click();

			await objectFieldsPage.objectFieldLabelInput.fill(
				'Test Field Label'
			);

			await expect(page.locator('input[name="name"]')).toHaveValue(
				'testFieldLabel'
			);
		}
	);

	test(
		'Verify it is not possible to add a Field without Choose an Option Field',
		{tag: '@LPS-135397'},
		async ({apiHelpers, objectFieldsPage, page}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await objectFieldsPage.goto(objectDefinition.label['en_US']);

			await objectFieldsPage.addObjectFieldButton.click();

			await objectFieldsPage.objectFieldLabelInput.fill('Test Field');

			await objectFieldsPage.saveButton.click();

			await expect(page.getByText('Required')).toBeVisible();
		}
	);

	test(
		'Verify that the options Keyword and Text appears under the Searchable section when updating the field type to String',
		{tag: '@LPS-135635'},
		async ({apiHelpers, objectFieldsPage, page}) => {
			const objectFields = generateObjectFields({
				objectFieldBusinessTypes: ['Integer'],
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

			await objectFieldsPage.goto(objectDefinition.label['en_US']);

			await objectFieldsPage.openObjectField(
				objectFields[0].label!['en_US']
			);

			await page.getByText('Select an Option').first().click();
			await page.getByRole('option', {exact: true, name: 'Text'}).click();

			await objectFieldsPage.advancedTab.click();

			const iframeLocator = page.frameLocator('iframe');

			await expect(
				iframeLocator.getByRole('radio', {name: 'Keyword'})
			).toBeVisible();
			await expect(
				iframeLocator.getByRole('radio', {name: 'Text'})
			).toBeVisible();
		}
	);

	test(
		'Verify that the options Keyword and Text disappears under the Searchable section when updating the field type from String to another type',
		{tag: '@LPS-135635'},
		async ({apiHelpers, objectFieldsPage, page}) => {
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

			await objectFieldsPage.goto(objectDefinition.label['en_US']);

			await objectFieldsPage.openObjectField(
				objectFields[0].label!['en_US']
			);

			await page.getByText('Select an Option').first().click();
			await page
				.getByRole('option', {exact: true, name: 'Integer'})
				.click();

			await objectFieldsPage.advancedTab.click();

			const iframeLocator = page.frameLocator('iframe');

			await expect(
				iframeLocator.getByRole('radio', {name: 'Keyword'})
			).toBeHidden();
			await expect(
				iframeLocator.getByRole('radio', {name: 'Text'})
			).toBeHidden();
		}
	);

	test(
		'Verify that the field with String type has the options Keyword and Text under the Searchable section',
		{tag: '@LPS-135549'},
		async ({apiHelpers, objectFieldsPage, page}) => {
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

			await objectFieldsPage.goto(objectDefinition.label['en_US']);

			await objectFieldsPage.openObjectField(
				objectFields[0].label!['en_US']
			);

			await objectFieldsPage.advancedTab.click();

			const iframeLocator = page.frameLocator('iframe');

			await expect(
				iframeLocator.getByRole('radio', {name: 'Keyword'})
			).toBeVisible();
			await expect(
				iframeLocator.getByRole('radio', {name: 'Text'})
			).toBeVisible();
		}
	);

	test(
		'Verify that the field with String type has the option Language when the Text option is selected under the Searchable section',
		{tag: '@LPS-135549'},
		async ({apiHelpers, objectFieldsPage, page}) => {
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

			await objectFieldsPage.goto(objectDefinition.label['en_US']);

			await objectFieldsPage.openObjectField(
				objectFields[0].label!['en_US']
			);

			await objectFieldsPage.advancedTab.click();

			const iframeLocator = page.frameLocator('iframe');

			await iframeLocator.getByRole('radio', {name: 'Text'}).check();

			await expect(iframeLocator.getByText('Language')).toBeVisible();
		}
	);

	test(
		'Verify that relationship field is automatically created',
		{tag: '@LPS-135400'},
		async ({apiHelpers, objectFieldsPage, page}) => {
			const objectDefinition1 =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					status: {code: 0},
				});

			const objectDefinition2 =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition1.id,
				type: 'objectDefinition',
			});
			apiHelpers.data.push({
				id: objectDefinition2.id,
				type: 'objectDefinition',
			});

			const {ObjectRelationshipAPI} = await import(
				'@liferay/object-admin-rest-client-js'
			);

			const objectRelationshipAPIClient =
				await apiHelpers.buildRestClient(ObjectRelationshipAPI);

			const relationshipLabel = 'Relationship' + getRandomInt();

			await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
				objectDefinition1.externalReferenceCode!,
				{
					label: {en_US: relationshipLabel},
					name: 'relationship' + getRandomInt(),
					objectDefinitionExternalReferenceCode2:
						objectDefinition2.externalReferenceCode,
					objectDefinitionId2: objectDefinition2.id,
					objectDefinitionName2: objectDefinition2.name,
					type: 'oneToMany',
				}
			);

			await objectFieldsPage.goto(objectDefinition2.label['en_US']);

			await expect(page.getByText(relationshipLabel)).toBeVisible();
		}
	);

	test(
		'Verify the empty state when searching for an Object field returns nothing',
		{tag: '@LPS-135547'},
		async ({apiHelpers, objectFieldsPage, page}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await objectFieldsPage.goto(objectDefinition.label['en_US']);

			await page
				.getByPlaceholder('Search')
				.fill('NonExistentField' + getRandomInt());

			await expect(page.getByText('No Results Found')).toBeVisible();
		}
	);

	test.fixme(
		'Verify that when adding a new Object with Account Restriction the Account Restriction field turns into a mandatory field for the created Object',
		{tag: '@LPS-151877'},
		async ({apiHelpers, objectFieldsPage, page}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					accountEntryRestricted: true,
					accountEntryRestrictedObjectFieldName:
						'r_accountEntryId_accountEntryId',
					status: {code: 0},
				} as any);

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await objectFieldsPage.goto(objectDefinition.label['en_US']);

			const accountFieldRow = page
				.getByRole('row')
				.filter({hasText: 'Account'});

			await expect(accountFieldRow).toBeVisible();

			await accountFieldRow.getByRole('link').first().click();

			await expect(
				page
					.frameLocator('iframe')
					.getByRole('switch', {name: 'Mandatory'})
			).toBeChecked();
		}
	);
});
