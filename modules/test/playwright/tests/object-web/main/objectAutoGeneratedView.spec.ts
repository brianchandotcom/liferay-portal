/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectDefinitionAPI} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import {waitForAlert} from '../../../utils/waitForAlert';
import {generateObjectFields} from './utils/generateObjectFields';
import {postListTypeDefinitionListTypeEntries} from './utils/postListTypeDefinitionListTypeEntries';

const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	objectPagesTest
);

test(
	'LPD-78504 Can add and view a long text (Clob) entry',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		// Migrated from: CanAddClobLongText
		// LPS-142659 - Verify that the user is able to add an entry of long text and view the entry

		const clobValue =
			'By building a vibrant business, making technology useful, and investing in communities, we make it possible for people to reach their full potential to serve others.';

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'LongText',
						DBType: 'Clob',
						label: {en_US: 'Custom Field'},
						name: 'customField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await apiHelpers.objectEntry.postObjectEntry(
			{customField: clobValue},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(
			page.locator('td').getByText(clobValue.substring(0, 50))
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can display empty date value on object view',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		// Migrated from: CanDisplayEmptyDateValue
		// LPS-147658 - Verify it is possible to submit an empty value for a Date field and it will be correctly displayed on the View

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'Date',
						DBType: 'Date',
						label: {en_US: 'Date'},
						name: 'dateField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.locator('table tbody tr')).toHaveCount(1);
	}
);

test(
	'LPD-78504 Can edit a long text (Clob) field entry and view update on entry table',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		// Migrated from: CanEditClobField
		// LPS-142659 - Verify it is possible to edit a entry of Clob field and view the update on entry table

		const originalValue =
			'We make it possible for people to reach their full potential to serve others.';
		const updatedValue =
			'All people are inherently valuable; who we are is as important as what we can do.';

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'LongText',
						DBType: 'Clob',
						label: {en_US: 'Custom Field'},
						name: 'customField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await apiHelpers.objectEntry.postObjectEntry(
			{customField: originalValue},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(
			page.locator('td').getByText(originalValue.substring(0, 50))
		).toBeVisible();

		await viewObjectEntriesPage.frontendDatasetItems.first().click();

		await page.getByLabel('Custom Field').clear();
		await page.getByLabel('Custom Field').fill(updatedValue);

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(
			page.locator('td').getByText(updatedValue.substring(0, 50))
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can view only first line of upload text field on object view',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		// Migrated from: CanViewOnlyFirstLineOfUploadTextField
		// LPS-143064 - Verify that, when seeing an object view with uploads, the user must only be able to read the first line of the text without considering its format

		const richTextValue =
			'By building a vibrant business, making technology useful, and investing in communities, we make it possible for people to reach their full potential to serve others.';

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'RichText',
						DBType: 'Clob',
						label: {en_US: 'Custom Field'},
						name: 'customField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await viewObjectEntriesPage.fillObjectEntry({
			objectFieldBusinessType: 'RichText',
			objectFieldLabel: 'Custom Field',
			objectFieldValue: richTextValue,
		});

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		const entryCell = page
			.locator('td')
			.getByText(richTextValue.substring(0, 50));

		await expect(entryCell).toBeVisible();

		await expect(entryCell.locator('strong')).toHaveCount(0);
	}
);

test(
	'LPD-78504 Can verify Clob entry is displayed on auto-generated table',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		// Migrated from: ClobDisplayed
		// LPS-142659 - Verify that a Clob entry is correctly displayed on the auto-generated table on the Custom Object Portlet

		const clobValue =
			'We make it possible for people to reach their full potential to serve others.';

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'LongText',
						DBType: 'Clob',
						label: {en_US: 'Custom Field'},
						name: 'customField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await apiHelpers.objectEntry.postObjectEntry(
			{customField: clobValue},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(
			page.locator('td').getByText(clobValue, {exact: true})
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can verify Clob long text is truncated on object portlet table view',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		// Migrated from: ClobLongTextWillTruncate
		// LPS-142659 - Text will be truncated on the Object portlet table view if it has more than 56 characters

		const clobValue =
			'We make it possible for people to reach their full potential to serve others.';

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						businessType: 'LongText',
						DBType: 'Clob',
						label: {en_US: 'Custom Field'},
						name: 'customField',
						required: false,
					},
				] as any,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await apiHelpers.objectEntry.postObjectEntry(
			{customField: clobValue},
			'c/' + objectDefinition.name.toLowerCase() + 's'
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		const entryCell = page.locator('td span.text-truncate');

		await expect(entryCell).toBeVisible();

		await expect(entryCell).toHaveCSS('overflow', 'hidden');
	}
);

test(
	'LPD-78504 Can verify picklist entry is displayed on auto-generated table',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		// Migrated from: PicklistDisplayed
		// LPS-136595 - Verify that a Picklist entry is correctly displayed on the auto-generated table on the Custom Object Portlet

		const {listTypeDefinition, listTypeEntries} =
			await postListTypeDefinitionListTypeEntries({
				apiHelpers,
				listTypeEntriesLength: 2,
			});

		const objectFields = generateObjectFields({
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			objectFieldBusinessTypes: ['Picklist'],
		});

		const objectDefinitionAPIClient =
			await apiHelpers.buildRestClient(ObjectDefinitionAPI);

		const {body: objectDefinition} =
			await objectDefinitionAPIClient.postObjectDefinition({
				active: true,
				label: {
					en_US: 'ObjectDefinitionLabel' + getRandomInt(),
				},
				name: 'ObjectDefinitionName' + getRandomInt(),
				objectFields,
				pluralLabel: {
					en_US: 'ObjectDefinitionLabel' + getRandomInt(),
				},
				portlet: true,
				scope: 'company',
				status: {
					code: 0,
				},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		const picklistFieldLabel = objectFields[0].label['en_US'];
		const firstEntryName = listTypeEntries[0].name_i18n['en_US'];

		await viewObjectEntriesPage.selectDropdownItem(
			picklistFieldLabel,
			firstEntryName
		);

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(
			page.locator('td').getByText(firstEntryName, {exact: true})
		).toBeVisible();
	}
);
