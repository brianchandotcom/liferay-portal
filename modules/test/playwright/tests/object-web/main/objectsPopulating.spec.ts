/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {formsPagesTest} from '../../../fixtures/formsPagesTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';
import {deleteItems} from '../../dynamic-data-mapping-form-web/main/utils/deleteItems';
import {
	getFDSDateFormat,
	getObjectEntryAPIDateTimeFormat,
	getObjectEntryUIDateTimeFormat,
} from '../utils/dateFormat';
import {generateObjectFields} from '../utils/generateObjectFields';
import {postListTypeDefinitionListTypeEntries} from '../utils/postListTypeDefinitionListTypeEntries';

const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	formsPagesTest,
	isolatedSiteTest,
	loginTest(),
	objectPagesTest
);

test(
	'can map and view entries for field group',
	{tag: '@LPS-133365'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		page,
	}) => {
		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Text', 'Text'],
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

		const fieldLabel1 = objectFields[0].label!['en_US'];

		const fieldLabel2 = objectFields[1].label!['en_US'];

		await formBuilderPage.goToNew();

		await expect(formBuilderPage.newFormHeading).toBeVisible();

		await formBuilderPage.fillFormTitle('Form' + getRandomInt());

		await formBuilderPage.formSettingsButton.click();

		await formSettingsModalPage.selectStorageType('Object');

		await formSettingsModalPage.selectObject(
			objectDefinition.label['en_US']
		);

		await formSettingsModalPage.clickDoneButton();

		await formBuilderSidePanelPage.addFieldByDoubleClick('Text');

		await formBuilderSidePanelPage.clickAdvancedTab();

		await formBuilderSidePanelPage.selectObjectField(fieldLabel1);

		await page.waitForTimeout(1000);

		await formBuilderSidePanelPage.clickBackButton();

		await formBuilderSidePanelPage.addFieldToFieldGroup('Text', 0);

		await formBuilderSidePanelPage.clickAdvancedTab();

		await formBuilderSidePanelPage.selectObjectField(fieldLabel2);

		await page.waitForTimeout(1000);

		await expect(
			page.getByLabel('Fields Group', {exact: true})
		).toBeVisible();

		await formBuilderPage.clickPublishFormButton();

		const formSubmissionURL = await formBuilderPage.getFormSubmissionURL();

		await page.goto(formSubmissionURL, {waitUntil: 'networkidle'});

		const textValue1 = getRandomString();

		const textValue2 = getRandomString();

		await page.getByLabel('Text', {exact: true}).first().fill(textValue1);

		await page.getByLabel('Text', {exact: true}).last().fill(textValue2);

		await page.getByRole('button', {name: 'Save'}).click();

		await expect(
			page.getByText('Your request completed successfully.')
		).toBeVisible();

		const {items} =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				'c/' + objectDefinition.name.toLowerCase() + 's'
			);

		expect(items).toHaveLength(1);

		const fieldName1 = objectFields[0].name;

		const fieldName2 = objectFields[1].name;

		expect(items[0][fieldName1]).toBe(textValue1);

		expect(items[0][fieldName2]).toBe(textValue2);
	}
);

test(
	'can map object field of PrecisionDecimal type to form and add entries',
	{tag: '@LPS-133365'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		page,
	}) => {
		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['PrecisionDecimal'],
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

		const fieldLabel = objectFields[0].label!['en_US'];

		await formBuilderPage.goToNew();

		await expect(formBuilderPage.newFormHeading).toBeVisible();

		await formBuilderPage.fillFormTitle('Form' + getRandomInt());

		await formBuilderPage.formSettingsButton.click();

		await formSettingsModalPage.selectStorageType('Object');

		await formSettingsModalPage.selectObject(
			objectDefinition.label['en_US']
		);

		await formSettingsModalPage.clickDoneButton();

		await formBuilderSidePanelPage.addFieldByDoubleClick('Numeric');

		await formBuilderSidePanelPage.numericTypeDecimal.click();

		await formBuilderSidePanelPage.clickAdvancedTab();

		await formBuilderSidePanelPage.selectObjectField(fieldLabel);

		await page.waitForTimeout(1000);

		await formBuilderPage.clickPublishFormButton();

		const formSubmissionURL = await formBuilderPage.getFormSubmissionURL();

		await page.goto(formSubmissionURL, {waitUntil: 'networkidle'});

		const decimalValue = (getRandomInt() / 100).toFixed(2);

		await page.getByLabel('Numeric').fill(decimalValue);

		await page.getByRole('button', {name: 'Save'}).click();

		await expect(
			page.getByText('Your request completed successfully.')
		).toBeVisible();

		const {items} =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				'c/' + objectDefinition.name.toLowerCase() + 's'
			);

		expect(items).toHaveLength(1);

		const fieldName = objectFields[0].name;

		expect(items[0][fieldName]).toBe(Number(decimalValue));
	}
);

test(
	'can map object field of Boolean type to form and add entries',
	{tag: '@LPS-133365'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		page,
	}) => {
		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Boolean'],
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

		const fieldLabel = objectFields[0].label!['en_US'];

		await formBuilderPage.goToNew();

		await expect(formBuilderPage.newFormHeading).toBeVisible();

		await formBuilderPage.fillFormTitle('Form' + getRandomInt());

		await formBuilderPage.formSettingsButton.click();

		await formSettingsModalPage.selectStorageType('Object');

		await formSettingsModalPage.selectObject(
			objectDefinition.label['en_US']
		);

		await formSettingsModalPage.clickDoneButton();

		await formBuilderSidePanelPage.addFieldByDoubleClick('Boolean');

		await formBuilderSidePanelPage.clickAdvancedTab();

		await formBuilderSidePanelPage.selectObjectField(fieldLabel);

		await page.waitForTimeout(1000);

		await formBuilderPage.clickPublishFormButton();

		const formSubmissionURL = await formBuilderPage.getFormSubmissionURL();

		await page.goto(formSubmissionURL, {waitUntil: 'networkidle'});

		await page.getByLabel('Boolean').check();

		await page.getByRole('button', {name: 'Save'}).click();

		await expect(
			page.getByText('Your request completed successfully.')
		).toBeVisible();

		const {items} =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				'c/' + objectDefinition.name.toLowerCase() + 's'
			);

		expect(items).toHaveLength(1);

		const fieldName = objectFields[0].name;

		expect(items[0][fieldName]).toBe(true);
	}
);

test(
	'can map object field of Date type to form and add entries',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		page,
	}) => {
		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Date'],
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

		const fieldLabel = objectFields[0].label!['en_US'];

		await formBuilderPage.goToNew();

		await expect(formBuilderPage.newFormHeading).toBeVisible();

		await formBuilderPage.fillFormTitle('Form' + getRandomInt());

		await formBuilderPage.formSettingsButton.click();

		await formSettingsModalPage.selectStorageType('Object');

		await formSettingsModalPage.selectObject(
			objectDefinition.label['en_US']
		);

		await formSettingsModalPage.clickDoneButton();

		await formBuilderSidePanelPage.addFieldByDoubleClick('Date');

		await formBuilderSidePanelPage.clickAdvancedTab();

		await formBuilderSidePanelPage.selectObjectField(fieldLabel);

		await page.waitForTimeout(1000);

		await formBuilderPage.clickPublishFormButton();

		const formSubmissionURL = await formBuilderPage.getFormSubmissionURL();

		await page.goto(formSubmissionURL, {waitUntil: 'networkidle'});

		const date = new Date();

		const inputDate = getObjectEntryUIDateTimeFormat(date);

		await page.getByRole('textbox', {name: 'Date'}).fill(inputDate);

		await page.getByRole('button', {name: 'Save'}).click();

		await expect(
			page.getByText('Your request completed successfully.')
		).toBeVisible();

		const {items} =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				'c/' + objectDefinition.name.toLowerCase() + 's'
			);

		expect(items).toHaveLength(1);

		const apiDate = getObjectEntryAPIDateTimeFormat(date).split('T')[0];

		const fieldName = objectFields[0].name;

		expect(items[0][fieldName]).toContain(apiDate);
	}
);

test(
	'can map object field of Decimal type to form and add entries',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		page,
	}) => {
		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Decimal'],
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

		const fieldLabel = objectFields[0].label!['en_US'];

		await formBuilderPage.goToNew();

		await expect(formBuilderPage.newFormHeading).toBeVisible();

		await formBuilderPage.fillFormTitle('Form' + getRandomInt());

		await formBuilderPage.formSettingsButton.click();

		await formSettingsModalPage.selectStorageType('Object');

		await formSettingsModalPage.selectObject(
			objectDefinition.label['en_US']
		);

		await formSettingsModalPage.clickDoneButton();

		await formBuilderSidePanelPage.addFieldByDoubleClick('Numeric');

		await formBuilderSidePanelPage.numericTypeDecimal.click();

		await formBuilderSidePanelPage.clickAdvancedTab();

		await formBuilderSidePanelPage.selectObjectField(fieldLabel);

		await page.waitForTimeout(1000);

		await formBuilderPage.clickPublishFormButton();

		const formSubmissionURL = await formBuilderPage.getFormSubmissionURL();

		await page.goto(formSubmissionURL, {waitUntil: 'networkidle'});

		const decimalValue = (getRandomInt() / 100).toFixed(2);

		await page.getByLabel('Numeric').fill(decimalValue);

		await page.getByRole('button', {name: 'Save'}).click();

		await expect(
			page.getByText('Your request completed successfully.')
		).toBeVisible();

		const {items} =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				'c/' + objectDefinition.name.toLowerCase() + 's'
			);

		expect(items).toHaveLength(1);

		const fieldName = objectFields[0].name;

		expect(items[0][fieldName]).toBe(Number(decimalValue));
	}
);

test(
	'can map object field of Integer type to form and add entries',
	{tag: '@LPS-133365'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		page,
	}) => {
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

		const fieldLabel = objectFields[0].label!['en_US'];

		await formBuilderPage.goToNew();

		await expect(formBuilderPage.newFormHeading).toBeVisible();

		await formBuilderPage.fillFormTitle('Form' + getRandomInt());

		await formBuilderPage.formSettingsButton.click();

		await formSettingsModalPage.selectStorageType('Object');

		await formSettingsModalPage.selectObject(
			objectDefinition.label['en_US']
		);

		await formSettingsModalPage.clickDoneButton();

		await formBuilderSidePanelPage.addFieldByDoubleClick('Numeric');

		await formBuilderSidePanelPage.clickAdvancedTab();

		await formBuilderSidePanelPage.selectObjectField(fieldLabel);

		await page.waitForTimeout(1000);

		await formBuilderPage.clickPublishFormButton();

		const formSubmissionURL = await formBuilderPage.getFormSubmissionURL();

		await page.goto(formSubmissionURL, {waitUntil: 'networkidle'});

		const integerValue = getRandomInt().toString().slice(1, 4);

		await page.getByLabel('Numeric').fill(integerValue);

		await page.getByRole('button', {name: 'Save'}).click();

		await expect(
			page.getByText('Your request completed successfully.')
		).toBeVisible();

		const {items} =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				'c/' + objectDefinition.name.toLowerCase() + 's'
			);

		expect(items).toHaveLength(1);

		const fieldName = objectFields[0].name;

		expect(items[0][fieldName]).toBe(Number(integerValue));
	}
);

test(
	'can map object field of LongInteger type to form and add entries',
	{tag: '@LPS-133365'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		page,
	}) => {
		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['LongInteger'],
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

		const fieldLabel = objectFields[0].label!['en_US'];

		await formBuilderPage.goToNew();

		await expect(formBuilderPage.newFormHeading).toBeVisible();

		await formBuilderPage.fillFormTitle('Form' + getRandomInt());

		await formBuilderPage.formSettingsButton.click();

		await formSettingsModalPage.selectStorageType('Object');

		await formSettingsModalPage.selectObject(
			objectDefinition.label['en_US']
		);

		await formSettingsModalPage.clickDoneButton();

		await formBuilderSidePanelPage.addFieldByDoubleClick('Numeric');

		await formBuilderSidePanelPage.clickAdvancedTab();

		await formBuilderSidePanelPage.clickAdvancedTab();

		await formBuilderSidePanelPage.selectObjectField(fieldLabel);

		await page.waitForTimeout(1000);

		await formBuilderPage.clickPublishFormButton();

		const formSubmissionURL = await formBuilderPage.getFormSubmissionURL();

		await page.goto(formSubmissionURL, {waitUntil: 'networkidle'});

		const longValue = getRandomInt();

		await page.getByLabel('Numeric').fill(String(longValue));

		await page.getByRole('button', {name: 'Save'}).click();

		await expect(
			page.getByText('Your request completed successfully.')
		).toBeVisible();

		const {items} =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				'c/' + objectDefinition.name.toLowerCase() + 's'
			);

		expect(items).toHaveLength(1);

		const fieldName = objectFields[0].name;

		expect(items[0][fieldName]).toBe(longValue);
	}
);

test(
	'can map object field of Picklist type to form and add entries',
	{tag: '@LPS-138495'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		page,
	}) => {
		const {listTypeDefinition, listTypeEntries} =
			await postListTypeDefinitionListTypeEntries({
				apiHelpers,
				listTypeEntriesLength: 3,
			});

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		const objectFields = generateObjectFields({
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			objectFieldBusinessTypes: ['Picklist'],
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

		const fieldLabel = objectFields[0].label!['en_US'];

		await formBuilderPage.goToNew();

		await expect(formBuilderPage.newFormHeading).toBeVisible();

		await formBuilderPage.fillFormTitle('Form' + getRandomInt());

		await formBuilderPage.formSettingsButton.click();

		await formSettingsModalPage.selectStorageType('Object');

		await formSettingsModalPage.selectObject(
			objectDefinition.label['en_US']
		);

		await formSettingsModalPage.clickDoneButton();

		await formBuilderSidePanelPage.addFieldByDoubleClick(
			'Select from List'
		);

		await formBuilderSidePanelPage.clickAdvancedTab();

		await formBuilderSidePanelPage.selectObjectField(fieldLabel);

		await page.waitForTimeout(1000);

		await formBuilderPage.clickPublishFormButton();

		const formSubmissionURL = await formBuilderPage.getFormSubmissionURL();

		await page.goto(formSubmissionURL, {waitUntil: 'networkidle'});

		const firstListTypeEntry = listTypeEntries[0];

		await page.getByLabel('Select from List').click();

		await page.getByRole('option', {name: firstListTypeEntry.name}).click();

		await page.getByRole('button', {name: 'Save'}).click();

		await expect(
			page.getByText('Your request completed successfully.')
		).toBeVisible();

		const {items} =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				'c/' + objectDefinition.name.toLowerCase() + 's'
			);

		expect(items).toHaveLength(1);

		const fieldName = objectFields[0].name;

		expect(items[0][fieldName]).toMatchObject({
			key: firstListTypeEntry.key,
			name: firstListTypeEntry.name,
		});
	}
);

test(
	'can map object field of Text type to form and add entries',
	{tag: '@LPS-133365'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		page,
	}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await formBuilderPage.goToNew();

		await expect(formBuilderPage.newFormHeading).toBeVisible();

		await formBuilderPage.fillFormTitle('Form' + getRandomInt());

		await formBuilderPage.formSettingsButton.click();

		await formSettingsModalPage.selectStorageType('Object');

		await formSettingsModalPage.selectObject(
			objectDefinition.label['en_US']
		);

		await formSettingsModalPage.clickDoneButton();

		await formBuilderSidePanelPage.addFieldByDoubleClick('Text');

		await formBuilderSidePanelPage.clickAdvancedTab();

		await formBuilderSidePanelPage.selectObjectField('textField');

		await page.waitForTimeout(1000);

		await formBuilderPage.clickPublishFormButton();

		const formSubmissionURL = await formBuilderPage.getFormSubmissionURL();

		await page.goto(formSubmissionURL, {waitUntil: 'networkidle'});

		const textValue = getRandomString();

		await page.getByLabel('Text').fill(textValue);

		await page.getByRole('button', {name: 'Save'}).click();

		await expect(
			page.getByText('Your request completed successfully.')
		).toBeVisible();

		const {items} =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				'c/' + objectDefinition.name.toLowerCase() + 's'
			);

		expect(items).toHaveLength(1);

		expect(items[0]['textField']).toBe(textValue);
	}
);

test(
	'can submit blank form entry with object field of Decimal type that is not required',
	{tag: '@LPS-137865'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		page,
	}) => {
		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: [{businessType: 'Decimal'}],
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

		const fieldLabel = objectFields[0].label!['en_US'];

		await formBuilderPage.goToNew();

		await formBuilderPage.fillFormTitle('Form' + getRandomInt());

		await formBuilderPage.formSettingsButton.click();

		await formSettingsModalPage.selectStorageType('Object');

		await formSettingsModalPage.selectObject(
			objectDefinition.label['en_US']
		);

		await formSettingsModalPage.clickDoneButton();

		await formBuilderSidePanelPage.addFieldByDoubleClick('Numeric');

		await formBuilderSidePanelPage.numericTypeDecimal.click();

		await formBuilderSidePanelPage.clickAdvancedTab();

		await formBuilderSidePanelPage.selectObjectField(fieldLabel);

		await page.waitForTimeout(1000);

		await formBuilderPage.clickPublishFormButton();

		const formSubmissionURL = await formBuilderPage.getFormSubmissionURL();

		await page.goto(formSubmissionURL, {waitUntil: 'networkidle'});

		await page.getByRole('button', {name: 'Save'}).click();

		await expect(
			page.getByText('Your request completed successfully.')
		).toBeVisible();

		const {items} =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				'c/' + objectDefinition.name.toLowerCase() + 's'
			);

		expect(items).toHaveLength(1);
	}
);

test(
	'object entries are not deleted when form is deleted',
	{tag: '@LPS-139902'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		formsPage,
		page,
	}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await formBuilderPage.goToNew();

		await expect(formBuilderPage.newFormHeading).toBeVisible();

		await formBuilderPage.fillFormTitle('Form' + getRandomInt());

		await formBuilderPage.formSettingsButton.click();

		await formSettingsModalPage.selectStorageType('Object');

		await formSettingsModalPage.selectObject(
			objectDefinition.label['en_US']
		);

		await formSettingsModalPage.clickDoneButton();

		await formBuilderSidePanelPage.addFieldByDoubleClick('Text');

		await formBuilderSidePanelPage.clickAdvancedTab();

		await formBuilderSidePanelPage.selectObjectField('textField');

		await page.waitForTimeout(1000);

		await formBuilderPage.clickPublishFormButton();

		const formSubmissionURL = await formBuilderPage.getFormSubmissionURL();

		const textValue = getRandomString();

		await test.step('create an object entry through the form', async () => {
			await page.goto(formSubmissionURL, {waitUntil: 'networkidle'});

			await page.getByLabel('Text').fill(textValue);

			await page.getByRole('button', {name: 'Save'}).click();

			await expect(
				page.getByText('Your request completed successfully.')
			).toBeVisible();
		});

		await test.step('verify entry exists before deleting the form', async () => {
			const {items: itemsBefore} =
				await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
					'c/' + objectDefinition.name.toLowerCase() + 's'
				);

			expect(itemsBefore).toHaveLength(1);
		});

		await test.step('delete the form', async () => {
			await formsPage.goTo();

			await deleteItems(formsPage);
		});

		await test.step('verify entry still exists after form deletion', async () => {
			const {items: itemsAfter} =
				await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
					'c/' + objectDefinition.name.toLowerCase() + 's'
				);

			expect(itemsAfter).toHaveLength(1);

			const item = itemsAfter[0];

			expect(item['textField']).toBe(textValue);
		});
	}
);

test(
	'can see entries of a field with capitalized letters in the name',
	{tag: '@LPS-136451'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		page,
		viewObjectEntriesPage,
	}) => {
		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: [
				{
					businessType: 'Date',
					label: {en_US: 'DueDate' + getRandomInt()},
					name: 'dueDate' + getRandomInt(),
				},
			],
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

		const fieldLabel = objectFields[0].label!['en_US'];

		await formBuilderPage.goToNew();

		await expect(formBuilderPage.newFormHeading).toBeVisible();

		await formBuilderPage.fillFormTitle('Form' + getRandomInt());

		await formBuilderPage.formSettingsButton.click();

		await formSettingsModalPage.selectStorageType('Object');

		await formSettingsModalPage.selectObject(
			objectDefinition.label['en_US']
		);

		await formSettingsModalPage.clickDoneButton();

		await formBuilderSidePanelPage.addFieldByDoubleClick('Date');

		await formBuilderSidePanelPage.clickAdvancedTab();

		await formBuilderSidePanelPage.selectObjectField(fieldLabel);

		await page.waitForTimeout(1000);

		await formBuilderPage.clickPublishFormButton();

		const formSubmissionURL = await formBuilderPage.getFormSubmissionURL();

		await page.goto(formSubmissionURL, {waitUntil: 'networkidle'});

		const date = new Date();

		const inputDate = getObjectEntryUIDateTimeFormat(date);

		await page
			.getByRole('textbox', {name: 'Date'})
			.fill(inputDate);

		await page.getByRole('button', {name: 'Save'}).click();

		await expect(
			page.getByText('Your request completed successfully.')
		).toBeVisible();

		await viewObjectEntriesPage.goto(objectDefinition.className);

		const fdsDate = getFDSDateFormat(date);
		
		await expect(
			page.getByRole('cell', {name: fdsDate})
		).toBeVisible();
	}
);
