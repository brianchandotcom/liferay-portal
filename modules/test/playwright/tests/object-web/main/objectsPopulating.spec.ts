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
import {generateObjectFields} from './utils/generateObjectFields';
import {postListTypeDefinitionListTypeEntries} from './utils/postListTypeDefinitionListTypeEntries';

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
	'LPD-78504 Can map and view entries for field group',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		page,
		site,
	}) => {
		// Corresponds to Poshi test: CanMapAndViewEntriesForFieldGroup

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

		await formBuilderPage.goToNew(site.friendlyUrlPath);

		await expect(formBuilderPage.newFormHeading).toBeVisible();

		const formTitle = 'Form' + getRandomInt();

		await formBuilderPage.fillFormTitle(formTitle);

		await formBuilderPage.formSettingsButton.click();

		await formSettingsModalPage.selectStorageType('Object');

		await formSettingsModalPage.selectObject(
			objectDefinition.label['en_US']
		);

		await formSettingsModalPage.clickDoneButton();

		// Add the first Text field

		await formBuilderSidePanelPage.addFieldByDoubleClick('Text');

		await formBuilderSidePanelPage.clickAdvancedTab();

		await formBuilderSidePanelPage.selectObjectField(fieldLabel1);

		await formBuilderSidePanelPage.clickBackButton();

		// Add the second Text field into a field group by dragging it onto the first field

		await formBuilderSidePanelPage.addFieldToFieldGroup('Text', 0);

		await formBuilderSidePanelPage.clickAdvancedTab();

		await formBuilderSidePanelPage.selectObjectField(fieldLabel2);

		await expect(
			page.getByLabel('Fields Group', {exact: true})
		).toBeVisible();

		await apiHelpers.dynamicDataMapping.waitForDDMEvaluate(page);

		await formBuilderPage.clickPublishFormButton();

		const formSubmissionURL =
			await formBuilderPage.getFormSubmissionURL();

		// Submit entry via the form

		await page.goto(formSubmissionURL, {waitUntil: 'networkidle'});

		const textValue1 = getRandomString();
		const textValue2 = getRandomString();

		await page.getByLabel('Text', {exact: true}).first().fill(textValue1);

		await page.getByLabel('Text', {exact: true}).last().fill(textValue2);

		await page.getByRole('button', {name: 'Submit'}).click();

		await expect(
			page.getByText(
				'Your information was successfully received. Thank you for filling out the form.'
			)
		).toBeVisible();

		// Verify entries via API

		const {items} =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				'c/' + objectDefinition.name.toLowerCase() + 's'
			);

		expect(items).toHaveLength(1);

		const item = items[0];
		const fieldName1 = objectFields[0].name;
		const fieldName2 = objectFields[1].name;

		expect(item[fieldName1]).toBe(textValue1);
		expect(item[fieldName2]).toBe(textValue2);
	}
);

test(
	'LPD-78504 Can map BigDecimal type and view entries',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		page,
		site,
	}) => {
		// Corresponds to Poshi test: CanMapBigDecimalTypeAndViewEntries

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

		await formBuilderPage.goToNew(site.friendlyUrlPath);

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

		await apiHelpers.dynamicDataMapping.waitForDDMEvaluate(page);

		await formBuilderPage.clickPublishFormButton();

		const formSubmissionURL =
			await formBuilderPage.getFormSubmissionURL();

		await page.goto(formSubmissionURL, {waitUntil: 'networkidle'});

		const decimalValue = '123.456';

		await page.getByLabel('Numeric').fill(decimalValue);

		await page.getByRole('button', {name: 'Submit'}).click();

		await expect(
			page.getByText(
				'Your information was successfully received. Thank you for filling out the form.'
			)
		).toBeVisible();

		const {items} =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				'c/' + objectDefinition.name.toLowerCase() + 's'
			);

		expect(items).toHaveLength(1);

		const item = items[0];
		const fieldName = objectFields[0].name;

		expect(Number(item[fieldName])).toBeCloseTo(123.456);
	}
);

test(
	'LPD-78504 Can map Boolean type and view entries',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		page,
		site,
	}) => {
		// Corresponds to Poshi test: CanMapBooleanTypeAndViewEntries

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

		await formBuilderPage.goToNew(site.friendlyUrlPath);

		await expect(formBuilderPage.newFormHeading).toBeVisible();

		await formBuilderPage.fillFormTitle('Form' + getRandomInt());

		await formBuilderPage.formSettingsButton.click();

		await formSettingsModalPage.selectStorageType('Object');

		await formSettingsModalPage.selectObject(
			objectDefinition.label['en_US']
		);

		await formSettingsModalPage.clickDoneButton();

		await formBuilderSidePanelPage.addFieldByDoubleClick(
			'Single Selection'
		);

		await formBuilderSidePanelPage.clickAdvancedTab();

		await formBuilderSidePanelPage.selectObjectField(fieldLabel);

		await apiHelpers.dynamicDataMapping.waitForDDMEvaluate(page);

		await formBuilderPage.clickPublishFormButton();

		const formSubmissionURL =
			await formBuilderPage.getFormSubmissionURL();

		await page.goto(formSubmissionURL, {waitUntil: 'networkidle'});

		await page.getByLabel('Option1').check();

		await page.getByRole('button', {name: 'Submit'}).click();

		await expect(
			page.getByText(
				'Your information was successfully received. Thank you for filling out the form.'
			)
		).toBeVisible();

		const {items} =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				'c/' + objectDefinition.name.toLowerCase() + 's'
			);

		expect(items).toHaveLength(1);
	}
);

test(
	'LPD-78504 Can map Date type and view entries',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		page,
		site,
	}) => {
		// Corresponds to Poshi test: CanMapDateTypeAndViewEntries

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

		await formBuilderPage.goToNew(site.friendlyUrlPath);

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

		await apiHelpers.dynamicDataMapping.waitForDDMEvaluate(page);

		await formBuilderPage.clickPublishFormButton();

		const formSubmissionURL =
			await formBuilderPage.getFormSubmissionURL();

		await page.goto(formSubmissionURL, {waitUntil: 'networkidle'});

		await page.getByLabel('Date').fill('01/15/2025');

		await page.getByRole('button', {name: 'Submit'}).click();

		await expect(
			page.getByText(
				'Your information was successfully received. Thank you for filling out the form.'
			)
		).toBeVisible();

		const {items} =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				'c/' + objectDefinition.name.toLowerCase() + 's'
			);

		expect(items).toHaveLength(1);

		const item = items[0];
		const fieldName = objectFields[0].name;

		expect(item[fieldName]).toBe('2025-01-15');
	}
);

test(
	'LPD-78504 Can map Double type and view entries',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		page,
		site,
	}) => {
		// Corresponds to Poshi test: CanMapDoubleTypeAndViewEntries

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

		await formBuilderPage.goToNew(site.friendlyUrlPath);

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

		await apiHelpers.dynamicDataMapping.waitForDDMEvaluate(page);

		await formBuilderPage.clickPublishFormButton();

		const formSubmissionURL =
			await formBuilderPage.getFormSubmissionURL();

		await page.goto(formSubmissionURL, {waitUntil: 'networkidle'});

		const doubleValue = '55.75';

		await page.getByLabel('Numeric').fill(doubleValue);

		await page.getByRole('button', {name: 'Submit'}).click();

		await expect(
			page.getByText(
				'Your information was successfully received. Thank you for filling out the form.'
			)
		).toBeVisible();

		const {items} =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				'c/' + objectDefinition.name.toLowerCase() + 's'
			);

		expect(items).toHaveLength(1);

		const item = items[0];
		const fieldName = objectFields[0].name;

		expect(Number(item[fieldName])).toBeCloseTo(55.75);
	}
);

test(
	'LPD-78504 Can map Integer type and view entries',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		page,
		site,
	}) => {
		// Corresponds to Poshi test: CanMapIntegerTypeAndViewEntries

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

		await formBuilderPage.goToNew(site.friendlyUrlPath);

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

		await apiHelpers.dynamicDataMapping.waitForDDMEvaluate(page);

		await formBuilderPage.clickPublishFormButton();

		const formSubmissionURL =
			await formBuilderPage.getFormSubmissionURL();

		await page.goto(formSubmissionURL, {waitUntil: 'networkidle'});

		const integerValue = '42';

		await page.getByLabel('Numeric').fill(integerValue);

		await page.getByRole('button', {name: 'Submit'}).click();

		await expect(
			page.getByText(
				'Your information was successfully received. Thank you for filling out the form.'
			)
		).toBeVisible();

		const {items} =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				'c/' + objectDefinition.name.toLowerCase() + 's'
			);

		expect(items).toHaveLength(1);

		const item = items[0];
		const fieldName = objectFields[0].name;

		expect(item[fieldName]).toBe(42);
	}
);

test(
	'LPD-78504 Can map Long type and view entries',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		page,
		site,
	}) => {
		// Corresponds to Poshi test: CanMapLongTypeAndViewEntries

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

		await formBuilderPage.goToNew(site.friendlyUrlPath);

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

		await apiHelpers.dynamicDataMapping.waitForDDMEvaluate(page);

		await formBuilderPage.clickPublishFormButton();

		const formSubmissionURL =
			await formBuilderPage.getFormSubmissionURL();

		await page.goto(formSubmissionURL, {waitUntil: 'networkidle'});

		const longValue = '9876543210';

		await page.getByLabel('Numeric').fill(longValue);

		await page.getByRole('button', {name: 'Submit'}).click();

		await expect(
			page.getByText(
				'Your information was successfully received. Thank you for filling out the form.'
			)
		).toBeVisible();

		const {items} =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				'c/' + objectDefinition.name.toLowerCase() + 's'
			);

		expect(items).toHaveLength(1);

		const item = items[0];
		const fieldName = objectFields[0].name;

		expect(Number(item[fieldName])).toBe(9876543210);
	}
);

test(
	'LPD-78504 Can map Picklist type and view entries',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		page,
		site,
	}) => {
		// Corresponds to Poshi test: CanMapPicklistTypeAndViewEntries

		const {listTypeDefinition, listTypeEntries} =
			await postListTypeDefinitionListTypeEntries({
				apiHelpers,
				listTypeEntriesLength: 3,
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

		await formBuilderPage.goToNew(site.friendlyUrlPath);

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

		await apiHelpers.dynamicDataMapping.waitForDDMEvaluate(page);

		await formBuilderPage.clickPublishFormButton();

		const formSubmissionURL =
			await formBuilderPage.getFormSubmissionURL();

		await page.goto(formSubmissionURL, {waitUntil: 'networkidle'});

		const firstEntryName = listTypeEntries[0].name;

		await page.getByLabel('Select from List').click();

		await page
			.getByRole('option', {name: firstEntryName})
			.click();

		await page.getByRole('button', {name: 'Submit'}).click();

		await expect(
			page.getByText(
				'Your information was successfully received. Thank you for filling out the form.'
			)
		).toBeVisible();

		const {items} =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				'c/' + objectDefinition.name.toLowerCase() + 's'
			);

		expect(items).toHaveLength(1);

		const item = items[0];
		const fieldName = objectFields[0].name;

		expect(item[fieldName]).toBeTruthy();
	}
);

test(
	'LPD-78504 Can map String type and view entries',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		page,
		site,
	}) => {
		// Corresponds to Poshi test: CanMapStringTypeAndViewEntries

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

		const fieldLabel = objectFields[0].label!['en_US'];

		await formBuilderPage.goToNew(site.friendlyUrlPath);

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

		await formBuilderSidePanelPage.selectObjectField(fieldLabel);

		await apiHelpers.dynamicDataMapping.waitForDDMEvaluate(page);

		await formBuilderPage.clickPublishFormButton();

		const formSubmissionURL =
			await formBuilderPage.getFormSubmissionURL();

		await page.goto(formSubmissionURL, {waitUntil: 'networkidle'});

		const textValue = getRandomString();

		await page.getByLabel('Text').fill(textValue);

		await page.getByRole('button', {name: 'Submit'}).click();

		await expect(
			page.getByText(
				'Your information was successfully received. Thank you for filling out the form.'
			)
		).toBeVisible();

		const {items} =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				'c/' + objectDefinition.name.toLowerCase() + 's'
			);

		expect(items).toHaveLength(1);

		const item = items[0];
		const fieldName = objectFields[0].name;

		expect(item[fieldName]).toBe(textValue);
	}
);

test(
	'LPD-78504 Can submit entry with Double field blank that is not required',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		page,
		site,
	}) => {
		// Corresponds to Poshi test: CanSubmitEntryWithDoubleFieldBlank

		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: [
				{
					businessType: 'Decimal',
					required: false,
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

		await formBuilderPage.goToNew(site.friendlyUrlPath);

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

		await apiHelpers.dynamicDataMapping.waitForDDMEvaluate(page);

		await formBuilderPage.clickPublishFormButton();

		const formSubmissionURL =
			await formBuilderPage.getFormSubmissionURL();

		// Submit the form without filling the Numeric field

		await page.goto(formSubmissionURL, {waitUntil: 'networkidle'});

		await page.getByRole('button', {name: 'Submit'}).click();

		await expect(
			page.getByText(
				'Your information was successfully received. Thank you for filling out the form.'
			)
		).toBeVisible();

		const {items} =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				'c/' + objectDefinition.name.toLowerCase() + 's'
			);

		expect(items).toHaveLength(1);
	}
);

test(
	'LPD-78504 Entries are not deleted when form is deleted',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		formsPage,
		page,
		site,
	}) => {
		// Corresponds to Poshi test: EntriesAreNotDeletedWhenFormIsDeleted

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

		const fieldLabel = objectFields[0].label!['en_US'];

		await formBuilderPage.goToNew(site.friendlyUrlPath);

		await expect(formBuilderPage.newFormHeading).toBeVisible();

		const formTitle = 'Form' + getRandomInt();

		await formBuilderPage.fillFormTitle(formTitle);

		await formBuilderPage.formSettingsButton.click();

		await formSettingsModalPage.selectStorageType('Object');

		await formSettingsModalPage.selectObject(
			objectDefinition.label['en_US']
		);

		await formSettingsModalPage.clickDoneButton();

		await formBuilderSidePanelPage.addFieldByDoubleClick('Text');

		await formBuilderSidePanelPage.clickAdvancedTab();

		await formBuilderSidePanelPage.selectObjectField(fieldLabel);

		await apiHelpers.dynamicDataMapping.waitForDDMEvaluate(page);

		await formBuilderPage.clickPublishFormButton();

		const formSubmissionURL =
			await formBuilderPage.getFormSubmissionURL();

		// Submit an entry

		await page.goto(formSubmissionURL, {waitUntil: 'networkidle'});

		const textValue = getRandomString();

		await page.getByLabel('Text').fill(textValue);

		await page.getByRole('button', {name: 'Submit'}).click();

		await expect(
			page.getByText(
				'Your information was successfully received. Thank you for filling out the form.'
			)
		).toBeVisible();

		// Verify entry exists before deleting the form

		const {items: itemsBefore} =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				'c/' + objectDefinition.name.toLowerCase() + 's'
			);

		expect(itemsBefore).toHaveLength(1);

		// Delete the form

		await formsPage.goTo(site.friendlyUrlPath);

		await formsPage.managementToolbarSelectAllItems.click();

		formsPage.page.once('dialog', async (dialog) => {
			await dialog.accept();
		});

		await formsPage.managementToolbarDeleteButton.click();

		// Verify entry still exists after form deletion

		const {items: itemsAfter} =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				'c/' + objectDefinition.name.toLowerCase() + 's'
			);

		expect(itemsAfter).toHaveLength(1);

		const item = itemsAfter[0];
		const fieldName = objectFields[0].name;

		expect(item[fieldName]).toBe(textValue);
	}
);

test(
	'LPD-78504 Can see entries of a field with capitalized letters in the name',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		page,
		site,
	}) => {
		// Corresponds to Poshi test: FieldWithCapitalizedLetters

		const capitalizedFieldLabel = 'MyTextField' + getRandomInt();

		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: [
				{
					businessType: 'Text',
					label: {en_US: capitalizedFieldLabel},
					name: capitalizedFieldLabel.toLowerCase(),
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

		await formBuilderPage.goToNew(site.friendlyUrlPath);

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

		await formBuilderSidePanelPage.selectObjectField(
			capitalizedFieldLabel
		);

		await apiHelpers.dynamicDataMapping.waitForDDMEvaluate(page);

		await formBuilderPage.clickPublishFormButton();

		const formSubmissionURL =
			await formBuilderPage.getFormSubmissionURL();

		await page.goto(formSubmissionURL, {waitUntil: 'networkidle'});

		const textValue = getRandomString();

		await page.getByLabel('Text').fill(textValue);

		await page.getByRole('button', {name: 'Submit'}).click();

		await expect(
			page.getByText(
				'Your information was successfully received. Thank you for filling out the form.'
			)
		).toBeVisible();

		const {items} =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				'c/' + objectDefinition.name.toLowerCase() + 's'
			);

		expect(items).toHaveLength(1);

		const item = items[0];
		const fieldName = objectFields[0].name;

		expect(item[fieldName]).toBe(textValue);
	}
);
