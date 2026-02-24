/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectDefinitionAPI} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {accountSettingsPagesTest} from '../../../fixtures/accountSettingsPagesTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {listTypeDefinitionsPagesTest} from '../../../fixtures/listTypeDefinitionsPagesTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import {waitForAlert} from '../../../utils/waitForAlert';
import {generateObjectFields} from './utils/generateObjectFields';
import {postListTypeDefinitionListTypeEntries} from './utils/postListTypeDefinitionListTypeEntries';

const test = mergeTests(
	accountSettingsPagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	listTypeDefinitionsPagesTest,
	loginTest(),
	objectPagesTest
);

test(
	'LPD-78504 Can cancel the creation of a picklist',
	{tag: '@LPD-78504'},
	async ({listTypeDefinitionPage, page}) => {
		// Corresponds to Poshi test: CanCancelCreatePicklist

		await listTypeDefinitionPage.goto();

		const picklistName = 'Picklist' + getRandomInt();

		await listTypeDefinitionPage.addPicklistButton.click();

		await listTypeDefinitionPage.modalNameInput.fill(picklistName);

		await page.getByRole('button', {name: 'Cancel'}).click();

		await expect(
			page.getByRole('link', {name: picklistName})
		).toBeHidden();
	}
);

test(
	'LPD-78504 Can cancel the creation of a picklist item',
	{tag: '@LPD-78504'},
	async ({apiHelpers, listTypeDefinitionPage, page}) => {
		// Corresponds to Poshi test: CanCancelCreatePicklistItem

		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		await listTypeDefinitionPage.goto();

		await page
			.getByRole('link', {name: listTypeDefinition.name})
			.click();

		await listTypeDefinitionPage.addPicklistItemButton.click();

		const itemName = 'Item' + getRandomInt();

		await listTypeDefinitionPage.modalNameInput.fill(itemName);

		await page.getByRole('button', {name: 'Cancel'}).click();

		const frameElement = await page.$('iframe');
		const frame = await frameElement.contentFrame();
		await frame.waitForLoadState('load');

		await expect(frame.getByText('No Results Found')).toBeVisible();
	}
);

test(
	'LPD-78504 Can cancel the update of a picklist',
	{tag: '@LPD-78504'},
	async ({apiHelpers, listTypeDefinitionPage, page}) => {
		// Corresponds to Poshi test: CanCancelUpdatePicklist

		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		const originalName = listTypeDefinition.name;

		await listTypeDefinitionPage.goto();

		await page.getByRole('link', {name: originalName}).click();

		await listTypeDefinitionPage.sidebarNameInput.fill(
			'UpdatedName' + getRandomInt()
		);

		await listTypeDefinitionPage.frameLocator
			.getByRole('button', {name: 'Cancel'})
			.click();

		await expect(
			page.getByRole('link', {name: originalName})
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can create object entry with picklist',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		// Corresponds to Poshi test: CanCreateObjectEntryWithPicklist

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
	}
);

test(
	'LPD-78504 Can create object with state added to picklist',
	{tag: '@LPD-78504'},
	async ({apiHelpers}) => {
		// Corresponds to Poshi test: CanCreateObjectWithState

		const {listTypeDefinition} =
			await postListTypeDefinitionListTypeEntries({
				apiHelpers,
				listTypeEntriesLength: 3,
			});

		const objectFields = generateObjectFields({
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			objectFieldBusinessTypes: [
				{businessType: 'Picklist', state: true},
			],
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

		expect(objectDefinition.id).toBeTruthy();
		expect(objectDefinition.status.code).toBe(0);
	}
);

test(
	'LPD-78504 Can create picklist item',
	{tag: '@LPD-78504'},
	async ({apiHelpers, listTypeDefinitionPage, page}) => {
		// Corresponds to Poshi test: CanCreatePicklistItem

		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		await listTypeDefinitionPage.goto();

		const itemName = 'PicklistItem' + getRandomInt();

		await listTypeDefinitionPage.addPicklistItem(
			listTypeDefinition.name,
			itemName
		);

		await waitForAlert(page, 'The picklist item was created successfully.');

		await expect(
			listTypeDefinitionPage.getPicklistItemLinkLocator(itemName)
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can delete a picklist',
	{tag: '@LPD-78504'},
	async ({apiHelpers, listTypeDefinitionPage, page}) => {
		// Corresponds to Poshi test: CanDeletePicklist

		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		const picklistName = listTypeDefinition.name;

		await listTypeDefinitionPage.goto();

		await expect(
			page.getByRole('link', {name: picklistName})
		).toBeVisible();

		await page
			.getByRole('row', {name: picklistName})
			.getByRole('button')
			.click();

		await page.getByRole('menuitem', {name: 'Delete'}).click();

		await page.getByRole('button', {name: 'Delete'}).click();

		await waitForAlert(page);

		await expect(
			page.getByRole('link', {name: picklistName})
		).toBeHidden();
	}
);

test(
	'LPD-78504 Can edit object with state added to picklist',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		// Corresponds to Poshi test: CanEditObjectWithState

		const {listTypeDefinition, listTypeEntries} =
			await postListTypeDefinitionListTypeEntries({
				apiHelpers,
				listTypeEntriesLength: 3,
			});

		const objectFields = generateObjectFields({
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			objectFieldBusinessTypes: [
				{businessType: 'Picklist', state: true},
			],
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

		const picklistFieldLabel = objectFields[0].label['en_US'];
		const firstEntryName = listTypeEntries[0].name_i18n['en_US'];
		const secondEntryName = listTypeEntries[1].name_i18n['en_US'];

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await viewObjectEntriesPage.selectDropdownItem(
			picklistFieldLabel,
			firstEntryName
		);

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);

		await viewObjectEntriesPage.backButton.click();

		await viewObjectEntriesPage.frontendDatasetItems.first().click();

		await viewObjectEntriesPage.selectDropdownItem(
			picklistFieldLabel,
			secondEntryName
		);

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);
	}
);

test(
	'LPD-78504 Cannot add special character for picklist item key field',
	{tag: '@LPD-78504'},
	async ({apiHelpers, listTypeDefinitionPage, page}) => {
		// Corresponds to Poshi test: CannotAddSpecialCharacterForPicklistItemKeyField

		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		await listTypeDefinitionPage.goto();

		await page
			.getByRole('link', {name: listTypeDefinition.name})
			.click();

		await listTypeDefinitionPage.addPicklistItemButton.click();

		await listTypeDefinitionPage.modalNameInput.fill(
			'PicklistItem' + getRandomInt()
		);

		await listTypeDefinitionPage.picklistItemKey.clear();

		await listTypeDefinitionPage.picklistItemKey.fill('key!@#');

		await listTypeDefinitionPage.modalSaveButton.click();

		await expect(
			page.getByText(
				'The key value must start with a lowercase letter and can only contain lowercase letters and numbers.'
			)
		).toBeVisible();
	}
);

test(
	'LPD-78504 Cannot leave picklist item key field empty',
	{tag: '@LPD-78504'},
	async ({apiHelpers, listTypeDefinitionPage, page}) => {
		// Corresponds to Poshi test: CannotLeavePicklistItemKeyFieldEmpty

		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		await listTypeDefinitionPage.goto();

		await page
			.getByRole('link', {name: listTypeDefinition.name})
			.click();

		await listTypeDefinitionPage.addPicklistItemButton.click();

		await listTypeDefinitionPage.modalNameInput.fill(
			'PicklistItem' + getRandomInt()
		);

		await listTypeDefinitionPage.picklistItemKey.clear();

		await listTypeDefinitionPage.modalSaveButton.click();

		await expect(page.getByText('Required')).toBeVisible();
	}
);

test(
	'LPD-78504 Cannot leave picklist item name field empty',
	{tag: '@LPD-78504'},
	async ({apiHelpers, listTypeDefinitionPage, page}) => {
		// Corresponds to Poshi test: CannotLeavePicklistItemNameFieldEmpty

		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		await listTypeDefinitionPage.goto();

		await page
			.getByRole('link', {name: listTypeDefinition.name})
			.click();

		await listTypeDefinitionPage.addPicklistItemButton.click();

		await listTypeDefinitionPage.modalSaveButton.click();

		await expect(page.getByText('Required')).toBeVisible();
	}
);

test(
	'LPD-78504 Cannot leave picklist name field empty',
	{tag: '@LPD-78504'},
	async ({listTypeDefinitionPage, page}) => {
		// Corresponds to Poshi test: CannotLeavePicklistNameFieldEmpty

		await listTypeDefinitionPage.goto();

		await listTypeDefinitionPage.addPicklistButton.click();

		await listTypeDefinitionPage.modalSaveButton.click();

		await expect(page.getByText('Required')).toBeVisible();
	}
);

test(
	'LPD-78504 Cannot update picklist item key',
	{tag: '@LPD-78504'},
	async ({apiHelpers, listTypeDefinitionPage, page}) => {
		// Corresponds to Poshi test: CannotUpdatePicklistItemKey

		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		const itemName = 'PicklistItem' + getRandomInt();

		await apiHelpers.listTypeAdmin.postListTypeEntry({
			key: itemName.toLowerCase(),
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			name_i18n: {en_US: itemName},
		});

		await listTypeDefinitionPage.goto();

		await page
			.getByRole('link', {name: listTypeDefinition.name})
			.click();

		await listTypeDefinitionPage
			.getPicklistItemLinkLocator(itemName)
			.click();

		await listTypeDefinitionPage.modalSaveButton.waitFor({
			state: 'visible',
		});

		await expect(listTypeDefinitionPage.picklistItemKey).toBeDisabled();
	}
);

test(
	'LPD-78504 Can search for a picklist',
	{tag: '@LPD-78504'},
	async ({apiHelpers, listTypeDefinitionPage, page}) => {
		// Corresponds to Poshi test: CanSearchPicklist

		const listTypeDefinition1 =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition1.id,
			type: 'listTypeDefinition',
		});

		const listTypeDefinition2 =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition2.id,
			type: 'listTypeDefinition',
		});

		await listTypeDefinitionPage.goto();

		await page
			.getByPlaceholder('Search')
			.fill(listTypeDefinition1.name);

		await page.keyboard.press('Enter');

		await expect(
			page.getByRole('link', {name: listTypeDefinition1.name})
		).toBeVisible();

		await expect(
			page.getByRole('link', {name: listTypeDefinition2.name})
		).toBeHidden();
	}
);

test(
	'LPD-78504 Can search for a picklist item',
	{tag: '@LPD-78504'},
	async ({apiHelpers, listTypeDefinitionPage, page}) => {
		// Corresponds to Poshi test: CanSearchPicklistItem

		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		const itemName1 = 'ItemAlpha' + getRandomInt();
		const itemName2 = 'ItemBeta' + getRandomInt();

		await apiHelpers.listTypeAdmin.postListTypeEntry({
			key: itemName1.toLowerCase(),
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			name_i18n: {en_US: itemName1},
		});

		await apiHelpers.listTypeAdmin.postListTypeEntry({
			key: itemName2.toLowerCase(),
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			name_i18n: {en_US: itemName2},
		});

		await listTypeDefinitionPage.goto();

		await page
			.getByRole('link', {name: listTypeDefinition.name})
			.click();

		const frameElement = await page.$('iframe');
		const frame = await frameElement.contentFrame();
		await frame.waitForLoadState('load');

		await listTypeDefinitionPage.frameLocator
			.getByPlaceholder('Search')
			.fill(itemName1);

		await page.keyboard.press('Enter');

		await expect(
			listTypeDefinitionPage.getPicklistItemLinkLocator(itemName1)
		).toBeVisible();

		await expect(
			listTypeDefinitionPage.getPicklistItemLinkLocator(itemName2)
		).toBeHidden();
	}
);

test(
	'LPD-78504 Can set different picklist item name language',
	{tag: '@LPD-78504'},
	async ({apiHelpers, listTypeDefinitionPage, page}) => {
		// Corresponds to Poshi test: CanSetDifferentPicklistItemNameLanguage

		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		const itemName = 'PicklistItem' + getRandomInt();

		await apiHelpers.listTypeAdmin.postListTypeEntry({
			key: itemName.toLowerCase(),
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			name_i18n: {en_US: itemName},
		});

		await listTypeDefinitionPage.goto();

		await listTypeDefinitionPage.translatePicklistItem(
			listTypeDefinition.name,
			itemName,
			'pt_BR'
		);

		await waitForAlert(page, 'The picklist item was updated successfully.');
	}
);

test(
	'LPD-78504 Can set different picklist item name language when updating',
	{tag: '@LPD-78504'},
	async ({apiHelpers, listTypeDefinitionPage, page}) => {
		// Corresponds to Poshi test: CanSetDifferentPicklistItemNameLanguageWhenUpdating

		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		const itemName = 'PicklistItem' + getRandomInt();

		await apiHelpers.listTypeAdmin.postListTypeEntry({
			key: itemName.toLowerCase(),
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			name_i18n: {en_US: itemName},
		});

		await listTypeDefinitionPage.goto();

		await listTypeDefinitionPage.translatePicklistItem(
			listTypeDefinition.name,
			itemName,
			'pt_BR'
		);

		await waitForAlert(page, 'The picklist item was updated successfully.');

		await listTypeDefinitionPage
			.getPicklistItemLinkLocator(itemName)
			.click();

		await listTypeDefinitionPage.modalSaveButton.waitFor({
			state: 'visible',
		});

		await listTypeDefinitionPage.picklistItemTranslationButton.click();

		await page
			.getByRole('option', {
				name: 'pt_BR language: Translated',
			})
			.click();

		const updatedTranslation = itemName + ' updated translation';

		await listTypeDefinitionPage.modalNameInput.fill(updatedTranslation);

		await listTypeDefinitionPage.modalSaveButton.click();

		await waitForAlert(page, 'The picklist item was updated successfully.');
	}
);

test(
	'LPD-78504 Can set different picklist name language',
	{tag: '@LPD-78504'},
	async ({apiHelpers, listTypeDefinitionPage, page}) => {
		// Corresponds to Poshi test: CanSetDifferentPicklistNameLanguage

		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		await listTypeDefinitionPage.goto();

		await listTypeDefinitionPage.translatePicklist(
			listTypeDefinition.name,
			'pt_BR'
		);

		await waitForAlert(page);
	}
);

test(
	'LPD-78504 Can update picklist item name',
	{tag: '@LPD-78504'},
	async ({apiHelpers, listTypeDefinitionPage, page}) => {
		// Corresponds to Poshi test: CanUpdatePicklistItemName

		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		const itemName = 'PicklistItem' + getRandomInt();

		await apiHelpers.listTypeAdmin.postListTypeEntry({
			key: itemName.toLowerCase(),
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			name_i18n: {en_US: itemName},
		});

		await listTypeDefinitionPage.goto();

		await page
			.getByRole('link', {name: listTypeDefinition.name})
			.click();

		await listTypeDefinitionPage
			.getPicklistItemLinkLocator(itemName)
			.click();

		await listTypeDefinitionPage.modalSaveButton.waitFor({
			state: 'visible',
		});

		const updatedName = 'UpdatedItem' + getRandomInt();

		await listTypeDefinitionPage.modalNameInput.clear();

		await listTypeDefinitionPage.modalNameInput.fill(updatedName);

		await listTypeDefinitionPage.modalSaveButton.click();

		await waitForAlert(page, 'The picklist item was updated successfully.');

		await expect(
			listTypeDefinitionPage.getPicklistItemLinkLocator(updatedName)
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can update picklist name',
	{tag: '@LPD-78504'},
	async ({apiHelpers, listTypeDefinitionPage, page}) => {
		// Corresponds to Poshi test: CanUpdatePicklistName

		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		const originalName = listTypeDefinition.name;

		await listTypeDefinitionPage.goto();

		await page.getByRole('link', {name: originalName}).click();

		const updatedName = 'UpdatedPicklist' + getRandomInt();

		await listTypeDefinitionPage.sidebarNameInput.clear();

		await listTypeDefinitionPage.sidebarNameInput.fill(updatedName);

		await listTypeDefinitionPage.sidebarSaveButton.click();

		await waitForAlert(page);

		await expect(
			page.getByRole('link', {name: updatedName})
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can view a picklist',
	{tag: '@LPD-78504'},
	async ({apiHelpers, listTypeDefinitionPage, page}) => {
		// Corresponds to Poshi test: CanViewPicklist

		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		await listTypeDefinitionPage.goto();

		await expect(
			page.getByRole('link', {name: listTypeDefinition.name})
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can view a picklist item',
	{tag: '@LPD-78504'},
	async ({apiHelpers, listTypeDefinitionPage, page}) => {
		// Corresponds to Poshi test: CanViewPicklistItem

		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		const itemName = 'PicklistItem' + getRandomInt();

		await apiHelpers.listTypeAdmin.postListTypeEntry({
			key: itemName.toLowerCase(),
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			name_i18n: {en_US: itemName},
		});

		await listTypeDefinitionPage.goto();

		await page
			.getByRole('link', {name: listTypeDefinition.name})
			.click();

		await expect(
			listTypeDefinitionPage.getPicklistItemLinkLocator(itemName)
		).toBeVisible();
	}
);

test(
	'LPD-78504 Empty state message displayed when no picklist exists',
	{tag: '@LPD-78504'},
	async ({listTypeDefinitionPage, page}) => {
		// Corresponds to Poshi test: EmptyStateMessageDisplayedWhenNoPicklist

		await listTypeDefinitionPage.goto();

		const nonExistentName = 'NonExistentPicklist' + getRandomInt();

		await page.getByPlaceholder('Search').fill(nonExistentName);

		await page.keyboard.press('Enter');

		await expect(page.getByText('No Results Found')).toBeVisible();
	}
);

test(
	'LPD-78504 Empty state message displayed when no picklist item exists',
	{tag: '@LPD-78504'},
	async ({apiHelpers, listTypeDefinitionPage, page}) => {
		// Corresponds to Poshi test: EmptyStateMessageDisplayedWhenNoPicklistItem

		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		await listTypeDefinitionPage.goto();

		await page
			.getByRole('link', {name: listTypeDefinition.name})
			.click();

		const frameElement = await page.$('iframe');
		const frame = await frameElement.contentFrame();
		await frame.waitForLoadState('load');

		await expect(frame.getByText('No Results Found')).toBeVisible();
	}
);

test(
	'LPD-78504 Key field is autofilled when name field is filled',
	{tag: '@LPD-78504'},
	async ({apiHelpers, listTypeDefinitionPage, page}) => {
		// Corresponds to Poshi test: KeyFieldIsAutofilled

		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		await listTypeDefinitionPage.goto();

		await page
			.getByRole('link', {name: listTypeDefinition.name})
			.click();

		await listTypeDefinitionPage.addPicklistItemButton.click();

		const itemName = 'Test Item Name';

		await listTypeDefinitionPage.modalNameInput.fill(itemName);

		await listTypeDefinitionPage.picklistItemKey.click();

		await expect(listTypeDefinitionPage.picklistItemKey).not.toHaveValue(
			''
		);
	}
);

test(
	'LPD-78504 Translated picklist item name displayed on object view',
	{tag: '@LPD-78504'},
	async ({
		accountSettingsPage,
		apiHelpers,
		listTypeDefinitionPage,
		page,
		viewObjectEntriesPage,
	}) => {
		// Corresponds to Poshi test: ViewTranslatedPicklistItemNameOnObjectView

		try {
			const listTypeDefinition =
				await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

			apiHelpers.data.push({
				id: listTypeDefinition.id,
				type: 'listTypeDefinition',
			});

			const itemName = 'PicklistItem' + getRandomInt();

			await apiHelpers.listTypeAdmin.postListTypeEntry({
				key: itemName.toLowerCase(),
				listTypeDefinitionExternalReferenceCode:
					listTypeDefinition.externalReferenceCode,
				name_i18n: {en_US: itemName},
			});

			await listTypeDefinitionPage.goto();

			await listTypeDefinitionPage.translatePicklistItem(
				listTypeDefinition.name,
				itemName,
				'pt_BR'
			);

			await expect(
				listTypeDefinitionPage.basicInfoHeading
			).toBeVisible();

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

			await viewObjectEntriesPage.goto(objectDefinition.className);

			await viewObjectEntriesPage.clickAddObjectEntry(
				objectDefinition.label['en_US']
			);

			const picklistFieldLabel = objectFields[0].label['en_US'];

			await viewObjectEntriesPage.selectDropdownItem(
				picklistFieldLabel,
				itemName
			);

			await viewObjectEntriesPage.saveObjectEntryButton.click();

			await waitForAlert(page);

			await accountSettingsPage.goToAccountSettings();

			await accountSettingsPage.selectAccountLanguage({
				languageId: 'pt_BR',
			});

			await viewObjectEntriesPage.goto(
				objectDefinition.className,
				'pt'
			);

			await expect(
				page.getByText(itemName + ' translated')
			).toBeVisible();
		}
		finally {
			await accountSettingsPage.selectAccountLanguage({
				languageId: 'en_US',
				navigate: true,
			});

			await waitForAlert(page);
		}
	}
);

test(
	'LPD-78504 Updated picklist item name displayed on object portlet',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		listTypeDefinitionPage,
		page,
		viewObjectEntriesPage,
	}) => {
		// Corresponds to Poshi test: ViewUpdatedPicklistItemNameOnObjectPortlet

		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		const itemName = 'PicklistItem' + getRandomInt();

		await apiHelpers.listTypeAdmin.postListTypeEntry({
			key: itemName.toLowerCase(),
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			name_i18n: {en_US: itemName},
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

		const picklistFieldLabel = objectFields[0].label['en_US'];

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await viewObjectEntriesPage.selectDropdownItem(
			picklistFieldLabel,
			itemName
		);

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);

		await viewObjectEntriesPage.backButton.click();

		await expect(page.getByText(itemName)).toBeVisible();

		const updatedItemName = 'UpdatedItem' + getRandomInt();

		await listTypeDefinitionPage.goto();

		await page
			.getByRole('link', {name: listTypeDefinition.name})
			.click();

		await listTypeDefinitionPage
			.getPicklistItemLinkLocator(itemName)
			.click();

		await listTypeDefinitionPage.modalSaveButton.waitFor({
			state: 'visible',
		});

		await listTypeDefinitionPage.modalNameInput.clear();

		await listTypeDefinitionPage.modalNameInput.fill(updatedItemName);

		await listTypeDefinitionPage.modalSaveButton.click();

		await waitForAlert(page, 'The picklist item was updated successfully.');

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText(updatedItemName)).toBeVisible();
	}
);

test(
	'LPD-78504 Warn message displayed on picklist item screen for updating or deleting',
	{tag: '@LPD-78504'},
	async ({apiHelpers, listTypeDefinitionPage, page}) => {
		// Corresponds to Poshi test: WarnMessageDisplayedOnPickListItemScreen

		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		await listTypeDefinitionPage.goto();

		await page
			.getByRole('link', {name: listTypeDefinition.name})
			.click();

		const frameElement = await page.$('iframe');
		const frame = await frameElement.contentFrame();
		await frame.waitForLoadState('load');

		await expect(
			frame.getByText('updating or deleting', {exact: false})
		).toBeVisible();
	}
);
