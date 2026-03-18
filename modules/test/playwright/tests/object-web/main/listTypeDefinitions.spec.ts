/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ObjectDefinition,
	ObjectDefinitionAPI,
} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {accountSettingsPagesTest} from '../../../fixtures/accountSettingsPagesTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {formsPagesTest} from '../../../fixtures/formsPagesTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {listTypeDefinitionsPagesTest} from '../../../fixtures/listTypeDefinitionsPagesTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {siteSettingsPagesTest} from '../../../fixtures/siteSettingsPagesTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import {waitForAlert} from '../../../utils/waitForAlert';
import {generateObjectFields} from './utils/generateObjectFields';
import {postListTypeDefinitionListTypeEntries} from './utils/postListTypeDefinitionListTypeEntries';

export const test = mergeTests(
	accountSettingsPagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-36105': {enabled: true},
	}),
	formsPagesTest,
	isolatedSiteTest,
	listTypeDefinitionsPagesTest,
	loginTest(),
	objectPagesTest,
	siteSettingsPagesTest
);

test.describe('manage picklists inside the picklists portlet', () => {
	test('can cancel the creation of a picklist', async ({
		listTypeDefinitionPage,
		page,
	}) => {
		await listTypeDefinitionPage.goto();

		const picklistName = 'Picklist' + getRandomInt();

		await listTypeDefinitionPage.addPicklistButton.click();

		await listTypeDefinitionPage.modalNameInput.fill(picklistName);

		await page.getByRole('button', {name: 'Cancel'}).click();

		await expect(page.getByRole('link', {name: picklistName})).toBeHidden();
	});

	test('can cancel the creation of a picklist item', async ({
		apiHelpers,
		listTypeDefinitionPage,
		page,
	}) => {
		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		await listTypeDefinitionPage.goto();

		await page.getByRole('link', {name: listTypeDefinition.name}).click();

		await listTypeDefinitionPage.addPicklistItemButton.click();

		const itemName = 'Item' + getRandomInt();

		await listTypeDefinitionPage.modalNameInput.fill(itemName);

		await page.getByRole('button', {name: 'Cancel'}).click();

		const frameElement = await page.$('iframe');
		const frame = await frameElement.contentFrame();
		await frame.waitForLoadState('load');

		await expect(frame.getByText('No Results Found')).toBeVisible();
	});

	test('can create a picklist', async ({
		apiHelpers,
		listTypeDefinitionPage,
		page,
	}) => {
		const listTypeDefinition: ListTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		await listTypeDefinitionPage.goto();

		await expect(
			page.getByRole('link', {name: listTypeDefinition.name})
		).toBeVisible();
	});

	test('can create picklist item', async ({
		apiHelpers,
		listTypeDefinitionPage,
		page,
	}) => {
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
	});

	test('can create a picklist when the instance language is different from the site language', async ({
		apiHelpers,
		listTypeDefinitionPage,
		page,
		site,
		siteSettingsLocalizationPage,
	}) => {
		await siteSettingsLocalizationPage.goto(site.friendlyUrlPath);

		await siteSettingsLocalizationPage.setCustomDefaultLanguage(
			'pt_BR',
			site.friendlyUrlPath
		);

		const listTypeDefinition: ListTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		await listTypeDefinitionPage.goto();

		await expect(
			page.getByRole('link', {name: listTypeDefinition.name})
		).toBeVisible();
	});

	test('can delete a picklist', async ({
		apiHelpers,
		listTypeDefinitionPage,
		page,
	}) => {
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

		await waitForAlert(page);

		await expect(page.getByRole('link', {name: picklistName})).toBeHidden();
	});

	test('can delete a picklist item', async ({
		apiHelpers,
		listTypeDefinitionPage,
		page,
	}) => {
		const listTypeDefinition: ListTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		await listTypeDefinitionPage.goto();

		const listTypeDefinitionName: string = listTypeDefinition.name;

		const listTypeDefinitionEntryName = 'ListTypeDefinitionEntryName';

		const listTypeDefinitionEntryKey = 'ListTypeDefinitionEntryKey';

		await listTypeDefinitionPage.addPicklistItem(
			listTypeDefinitionName,
			listTypeDefinitionEntryName,
			listTypeDefinitionEntryKey
		);

		const frameElement = await page.$('iframe');
		const frame = await frameElement.contentFrame();
		await frame.waitForLoadState('load');

		await listTypeDefinitionPage.deletePicklistItem();
		await frame.waitForLoadState('load');
		await expect(frame.getByText('No Results Found')).toBeVisible();
	});

	test('can search for a picklist', async ({
		apiHelpers,
		listTypeDefinitionPage,
		page,
	}) => {
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

		await page.waitForTimeout(500);

		await page
			.getByTestId('managementToolbar')
			.getByPlaceholder('Search')
			.fill(listTypeDefinition1.name);

		await page.keyboard.press('Enter');

		await expect(
			page.getByRole('link', {name: listTypeDefinition1.name})
		).toBeVisible();

		await expect(
			page.getByRole('link', {name: listTypeDefinition2.name})
		).toBeHidden();
	});

	test('can search for a picklist item', async ({
		apiHelpers,
		listTypeDefinitionPage,
		page,
	}) => {
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

		await page.getByRole('link', {name: listTypeDefinition.name}).click();

		await page.waitForTimeout(500);

		await listTypeDefinitionPage.frameLocator
			.getByTestId('managementToolbar')
			.getByPlaceholder('Search')
			.fill(itemName1);

		await page.keyboard.press('Enter');

		await expect(
			listTypeDefinitionPage.getPicklistItemLinkLocator(itemName1)
		).toBeVisible();

		await expect(
			listTypeDefinitionPage.getPicklistItemLinkLocator(itemName2)
		).toBeHidden();
	});

	test('can update picklist item name', async ({
		apiHelpers,
		listTypeDefinitionPage,
		page,
	}) => {
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

		await page.getByRole('link', {name: listTypeDefinition.name}).click();

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
	});

	test('can update picklist name', async ({
		apiHelpers,
		listTypeDefinitionPage,
		page,
	}) => {
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

		await waitForAlert(
			page,
			'Success:The picklist was updated successfully.'
		);

		await expect(page.getByRole('link', {name: updatedName})).toBeVisible();
	});

	test('cannot add special character for picklist item key field', async ({
		apiHelpers,
		listTypeDefinitionPage,
		page,
	}) => {
		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		await listTypeDefinitionPage.goto();

		await page.getByRole('link', {name: listTypeDefinition.name}).click();

		await listTypeDefinitionPage.addPicklistItemButton.click();

		await listTypeDefinitionPage.modalNameInput.fill(
			'PicklistItem' + getRandomInt()
		);

		await listTypeDefinitionPage.picklistItemKey.clear();

		await listTypeDefinitionPage.picklistItemKey.fill('key!@#');

		await listTypeDefinitionPage.modalSaveButton.click();

		await expect(
			page.getByText('Key must only contain letters and digits.')
		).toBeVisible();
	});

	test('cannot leave picklist name field empty', async ({
		listTypeDefinitionPage,
		page,
	}) => {
		await listTypeDefinitionPage.goto();

		await listTypeDefinitionPage.addPicklistButton.click();

		await listTypeDefinitionPage.modalSaveButton.click();

		await expect(page.getByText('Required')).toBeVisible();
	});

	test('cannot update picklist item key', async ({
		apiHelpers,
		listTypeDefinitionPage,
		page,
	}) => {
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

		await page.getByRole('link', {name: listTypeDefinition.name}).click();

		await listTypeDefinitionPage
			.getPicklistItemLinkLocator(itemName)
			.click();

		await listTypeDefinitionPage.modalSaveButton.waitFor({
			state: 'visible',
		});

		await expect(listTypeDefinitionPage.picklistItemKey).toBeDisabled();
	});

	test('empty message displayed when searching for a non-existent picklist', async ({
		listTypeDefinitionPage,
		page,
	}) => {
		await listTypeDefinitionPage.goto();

		const nonExistentName = 'NonExistentPicklist' + getRandomInt();

		await page.waitForTimeout(500);

		await page
			.getByTestId('managementToolbar')
			.getByPlaceholder('Search')
			.fill(nonExistentName);

		await page.keyboard.press('Enter');

		await expect(page.getByText('No Results Found')).toBeVisible();
	});

	test('ensure picklist entry keys starting with upper case are correctly rendered in the entries', async ({
		apiHelpers,
		listTypeDefinitionPage,
		page,
	}) => {
		const listTypeDefinition: ListTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		await listTypeDefinitionPage.goto();

		const listTypeDefinitionName: string = listTypeDefinition.name;

		const listTypeDefinitionEntryName = 'ListTypeDefinitionEntryName';

		const listTypeDefinitionEntryKey = 'ListTypeDefinitionEntryKey';

		await listTypeDefinitionPage.addPicklistItem(
			listTypeDefinitionName,
			listTypeDefinitionEntryName,
			listTypeDefinitionEntryKey
		);

		const [response] =
			await apiHelpers.listTypeAdmin.getFilteredListTypeDefinition(
				'name',
				listTypeDefinitionName
			);

		const [responseEntries]: ListTypeEntry[] = response.listTypeEntries;

		const frameElement = await page.$('iframe');
		const frame = await frameElement.contentFrame();
		await frame.waitForLoadState('networkidle');

		const listTypeDefinitionHeader =
			await listTypeDefinitionPage.frameLocator
				.locator('.fds th')
				.allInnerTexts();

		const listTypeDefinitionContent =
			await listTypeDefinitionPage.frameLocator
				.locator('.fds td')
				.allInnerTexts();

		const listTypeDefinitionHeaderTemplate = [
			'Name',
			'Key',
			'External Reference Code',
		];

		const listTypeDefinitionContentTemplate = [
			listTypeDefinitionEntryName,
			listTypeDefinitionEntryKey,
			responseEntries.externalReferenceCode,
		];

		for (let i = 0; i < 3; i++) {
			expect(listTypeDefinitionHeaderTemplate[i]).toBe(
				listTypeDefinitionHeader[i]
			);
			expect(listTypeDefinitionContentTemplate[i]).toBe(
				listTypeDefinitionContent[i]
			);
		}
	});

	test('ensure that attempting to add picklist item with empty name/key will show a required error', async ({
		apiHelpers,
		listTypeDefinitionPage,
		page,
	}) => {
		const listTypeDefinition: ListTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		await listTypeDefinitionPage.goto();

		await page.getByRole('link', {name: listTypeDefinition.name}).click();

		await listTypeDefinitionPage.addPicklistItemButton.click();

		await listTypeDefinitionPage.modalSaveButton.click();

		expect(await page.getByText('Required').count()).toBe(2);

		await listTypeDefinitionPage.modalNameInput.fill(
			'picklisItem' + getRandomInt()
		);

		await listTypeDefinitionPage.modalSaveButton.click();

		await waitForAlert(page, 'The picklist item was created successfully.');
	});

	test('picklist item key field is autofilled when name field is filled', async ({
		apiHelpers,
		listTypeDefinitionPage,
		page,
	}) => {
		const listTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		await listTypeDefinitionPage.goto();

		await page.getByRole('link', {name: listTypeDefinition.name}).click();

		await listTypeDefinitionPage.addPicklistItemButton.click();

		const itemName = 'Test Item Name';

		await listTypeDefinitionPage.modalNameInput.fill(itemName);

		await listTypeDefinitionPage.picklistItemKey.click();

		await expect(listTypeDefinitionPage.picklistItemKey).not.toHaveValue(
			''
		);
	});

	test('updated picklist item name displayed on object entry FDS', async ({
		apiHelpers,
		listTypeDefinitionPage,
		page,
		viewObjectEntriesPage,
	}) => {
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

		await page.getByRole('link', {name: listTypeDefinition.name}).click();

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
	});

	test('Warn message displayed on picklist item screen for the delete action', async ({
		apiHelpers,
		listTypeDefinitionPage,
	}) => {
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

		await expect(
			listTypeDefinitionPage.frameLocator.getByRole('link', {
				name: itemName,
			})
		).toBeVisible();

		await listTypeDefinitionPage.frameLocator
			.getByRole('row', {name: itemName})
			.getByRole('button')
			.click();

		await listTypeDefinitionPage.frameLocator
			.getByRole('menuitem', {name: 'Delete'})
			.click();
	});
});

test.describe('ensure picklist translation', () => {
	test.afterEach(async ({apiHelpers}) => {
		await apiHelpers.headlessAdminUser.patchMyUserAccountLanguage('en_US');
	});

	test('can update picklist item translation', async ({
		apiHelpers,
		listTypeDefinitionPage,
		page,
	}) => {
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
	});

	test('translated picklist item name displayed on object entry page', async ({
		accountSettingsPage,
		apiHelpers,
		listTypeDefinitionPage,
		page,
		viewObjectEntriesPage,
	}) => {
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

		await expect(listTypeDefinitionPage.basicInfoHeading).toBeVisible();

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

		await viewObjectEntriesPage.goto(objectDefinition.className, 'pt');

		await expect(page.getByText(itemName + ' translated')).toBeVisible();
	});

	test('verify if title of clear all button on multiselect picklist field is translated', async ({
		accountSettingsPage,
		apiHelpers,
		formFieldsPage,
		page,
		viewObjectEntriesPage,
	}) => {
		const {listTypeDefinition, listTypeEntries} =
			await postListTypeDefinitionListTypeEntries({
				apiHelpers,
				listTypeEntriesLength: 4,
				locale: 'pt_BR',
			});

		const objectFields = generateObjectFields({
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			objectFieldBusinessTypes: ['MultiselectPicklist'],
		});

		const objectDefinitionAPIClient =
			await apiHelpers.buildRestClient(ObjectDefinitionAPI);

		const {body: objectDefinition} =
			await objectDefinitionAPIClient.postObjectDefinition({
				active: true,
				enableLocalization: true,
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

		await accountSettingsPage.goToAccountSettings();

		await accountSettingsPage.selectAccountLanguage({
			languageId: 'pt_BR',
		});

		await viewObjectEntriesPage.goto(objectDefinition.className, 'pt');

		await page
			.getByLabel('Adicionar ' + objectDefinition.label['en_US'])
			.first()
			.click();

		await viewObjectEntriesPage.editObjectEntryForm.waitFor({
			state: 'visible',
		});

		const [{name_i18n: listTypeEntry_i18n}] = listTypeEntries;

		await formFieldsPage.addSelectItem(listTypeEntry_i18n['pt-BR']);

		await expect(page.getByTitle('Limpar Todos')).toBeVisible();
	});

	test('verify if translated picklist will be displayed on object admin', async ({
		accountSettingsPage,
		apiHelpers,
		listTypeDefinitionPage,
		page,
		viewObjectDefinitionsPage,
	}) => {

		// Create a picklist

		const listTypeDefinition: ListTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		const listTypeDefinitionName: string = listTypeDefinition.name;

		// Translate picklist

		await listTypeDefinitionPage.goto();

		await listTypeDefinitionPage.translatePicklist(
			listTypeDefinitionName,
			'pt_BR'
		);

		// Create custom object with the picklist

		const objectDefinition: ObjectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await page.goto('/');

		await accountSettingsPage.goToAccountSettings();

		await accountSettingsPage.selectAccountLanguage({
			languageId: 'pt_BR',
		});

		await page.waitForLoadState('networkidle');

		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.clickEditObjectDefinitionLink(
			objectDefinition.label['en_US'],
			'Buscar'
		);

		await page.getByRole('link', {name: 'Campos'}).click();

		await page
			.getByRole('button', {name: 'Adicionar campo de objeto'})
			.click();

		await page.getByText('Selecione uma opção').click();

		await page
			.getByRole('option', {exact: true, name: 'Lista de seleção'})
			.click();

		await page.getByLabel('Lista de seleção').click();

		await expect(
			page.getByRole('option', {
				name: listTypeDefinitionName + ' translated',
			})
		).toBeVisible();
	});

	test('verify if translated picklist item will be displayed on forms', async ({
		accountSettingsPage,
		apiHelpers,
		editObjectDetailsPage,
		formBuilderPage,
		formBuilderSidePanelPage,
		formSettingsModalPage,
		listTypeDefinitionPage,
		objectFieldsPage,
		page,
		viewObjectDefinitionsPage,
	}) => {

		// Create a picklist

		const listTypeDefinition: ListTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		apiHelpers.data.push({
			id: listTypeDefinition.id,
			type: 'listTypeDefinition',
		});

		const listTypeDefinitionName: string = listTypeDefinition.name;

		// Create a picklist item

		const listTypeEntryName: string = 'picklistItem' + getRandomInt();

		await apiHelpers.listTypeAdmin.postListTypeEntry({
			key: listTypeEntryName,
			listTypeDefinitionExternalReferenceCode:
				listTypeDefinition.externalReferenceCode,
			name_i18n: {en_US: listTypeEntryName},
		});

		// Translate picklist item

		await listTypeDefinitionPage.goto();

		await listTypeDefinitionPage.translatePicklistItem(
			listTypeDefinitionName,
			listTypeEntryName,
			'pt_BR'
		);

		await expect(listTypeDefinitionPage.basicInfoHeading).toBeVisible();

		// Create custom object with the picklist

		const objectDefinition: ObjectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});
		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await viewObjectDefinitionsPage.goto();

		await objectFieldsPage.goto(objectDefinition.label['en_US']);

		const fieldLabel = 'picklistField' + getRandomInt();

		await objectFieldsPage.addObjectField({
			listTypeDefinitionName: listTypeDefinition.name,
			objectFieldBusinessType: 'Picklist',
			objectFieldLabel: fieldLabel,
		});

		await editObjectDetailsPage.goToDetailsTab();

		await editObjectDetailsPage.saveObjectDefinition();

		await page.goto('/');

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

		await expect(formBuilderSidePanelPage.objectFieldSelect).toBeVisible();

		// Preview form

		await apiHelpers.dynamicDataMapping.waitForDDMEvaluate(page);

		const newTabPage = await formBuilderPage.openPreviewForm();

		await accountSettingsPage.selectAccountLanguage({
			languageId: 'pt_BR',
			navigate: true,
		});

		await newTabPage.reload();

		await newTabPage.getByLabel('Select from List').click();

		await expect(
			newTabPage.getByRole('option', {
				name: listTypeEntryName + ' translated',
			})
		).toBeVisible();
	});
});
