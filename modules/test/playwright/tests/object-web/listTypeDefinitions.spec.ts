/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {formsPagesTest} from '../../fixtures/formsPagesTest';
import {listTypeDefinitionsPagesTest} from '../../fixtures/listTypeDefinitionsPagesTest';
import {loginTest} from '../../fixtures/loginTest';
import {objectPagesTest} from '../../fixtures/objectPagesTest';
import {siteSettingsPageTests} from '../../fixtures/siteSettingsPagesTest';
import {getRandomInt} from '../../utils/getRandomInt';

export const test = mergeTests(
	apiHelpersTest,
	formsPagesTest,
	listTypeDefinitionsPagesTest,
	loginTest(),
	objectPagesTest,
	siteSettingsPageTests
);

let customDefaultSiteLanguage: string;
let siteLanguage: string;

const createdEntities = {
	listTypeDefinitions: [],
	objectDefinitions: [],
} as {
	listTypeDefinitions: ListTypeDefinition[];
	objectDefinitions: ObjectDefinition[];
};

test.afterEach(async ({apiHelpers, page, siteSettingsLocalizationPage}) => {
	if (siteLanguage !== 'en') {
		await page.goto('en');
	}
	if (customDefaultSiteLanguage) {
		await page.goto('/');
		await siteSettingsLocalizationPage.goto();
		await siteSettingsLocalizationPage.selectDefaultLanguageOption();
		await siteSettingsLocalizationPage.saveConfiguration();
		customDefaultSiteLanguage = '';
	}

	for (const objectDefinition of createdEntities.objectDefinitions) {
		await apiHelpers.objectAdmin.deleteObjectDefinition(
			objectDefinition.id
		);
	}

	createdEntities.objectDefinitions = [];

	for (const listTypeDefinition of createdEntities.listTypeDefinitions) {
		await apiHelpers.listTypeAdmin.deleteListTypeDefinition(
			listTypeDefinition.id
		);
	}

	createdEntities.listTypeDefinitions = [];
});

test.describe('manage picklists inside the picklists portlet', () => {
	test('can create a picklist', async ({
		apiHelpers,
		listTypeDefinitionPage,
		page,
	}) => {
		const listTypeDefinition: ListTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		createdEntities.listTypeDefinitions.push(listTypeDefinition);

		await listTypeDefinitionPage.goto();

		await expect(
			page.getByRole('link', {name: listTypeDefinition.name})
		).toBeVisible();
	});

	test('can create a picklist when the instance language is different from the site language', async ({
		apiHelpers,
		listTypeDefinitionPage,
		page,
		siteSettingsLocalizationPage,
	}) => {
		await page.goto('/');

		await siteSettingsLocalizationPage.goto();

		await siteSettingsLocalizationPage.selectCustomDefaultLanguageOption();

		await siteSettingsLocalizationPage.setCustomDefaultLanguage('pt_BR');

		customDefaultSiteLanguage = 'pt_BR';

		const listTypeDefinition: ListTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		createdEntities.listTypeDefinitions.push(listTypeDefinition);

		await listTypeDefinitionPage.goto();

		await expect(
			page.getByRole('link', {name: listTypeDefinition.name})
		).toBeVisible();
	});

	test('ensure picklist entry keys starting with upper case are correctly rendered in the entries', async ({
		apiHelpers,
		listTypeDefinitionPage,
		page,
	}) => {
		const listTypeDefinition: ListTypeDefinition =
			await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

		createdEntities.listTypeDefinitions.push(listTypeDefinition);

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
		await frame.waitForLoadState('load');

		const [listTypeDefinitionHeader, listTypeDefinitionContent] =
			await Promise.all([
				listTypeDefinitionPage.frameLocator
					.locator('div.dnd-th')
					.allInnerTexts(),
				listTypeDefinitionPage.frameLocator
					.locator('div.dnd-td')
					.allInnerTexts(),
			]);

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
});

test.describe('ensure picklist translation', () => {
	test('verify if translated picklist item will be displayed on forms', async ({
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

		createdEntities.listTypeDefinitions.push(listTypeDefinition);

		const listTypeDefinitionName: string = listTypeDefinition.name;

		// Create a picklist item

		const listTypeEntryName: string = 'picklistItem' + getRandomInt();

		await apiHelpers.listTypeAdmin.postListTypeEntry(
			listTypeDefinition.externalReferenceCode,
			listTypeEntryName
		);

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
				objectFolderExternalReferenceCode: 'default',
				status: {code: 0},
			});

		createdEntities.objectDefinitions.push(objectDefinition);

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

		await page.waitForTimeout(200);

		const newTabPagePromise = new Promise<Page>((resolve) =>
			formBuilderPage.page.once('popup', resolve)
		);

		await formBuilderPage.previewButton.click();

		const newTabPage = await newTabPagePromise;

		await newTabPage.waitForLoadState('domcontentloaded');

		await page.goto('pt');

		await newTabPage.reload();

		siteLanguage = 'pt';

		await newTabPage.getByLabel('Select from List').click();

		await expect(
			newTabPage.getByRole('option', {
				name: listTypeEntryName + ' translated',
			})
		).toBeVisible();
	});
});
