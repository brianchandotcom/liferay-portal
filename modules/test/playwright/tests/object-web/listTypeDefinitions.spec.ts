/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {listTypeDefinitionsPagesTest} from '../../fixtures/listTypeDefinitionsPagesTest';
import {loginTest} from '../../fixtures/loginTest';
import {siteSettingsPageTests} from '../../fixtures/siteSettingsPagesTest';
import {getRandomInt} from '../../utils/getRandomInt';

export const test = mergeTests(
	apiHelpersTest,
	listTypeDefinitionsPagesTest,
	loginTest(),
	siteSettingsPageTests
);

let createdListTypeDefinitionName: string;
let customDefaultSiteLanguage: string;

test.afterEach(async ({apiHelpers, page, siteSettingsLocalizationPage}) => {
	if (customDefaultSiteLanguage) {
		await page.goto('/');

		await siteSettingsLocalizationPage.goto();

		await siteSettingsLocalizationPage.selectDefaultLanguageOption();

		await siteSettingsLocalizationPage.saveConfiguration();

		customDefaultSiteLanguage = '';
	}

	if (createdListTypeDefinitionName) {
		const listTypeDefinitions =
			await apiHelpers.listTypeAdmin.getListTypeDefinitions();

		const [createdListTypeDefinition] = listTypeDefinitions.items.filter(
			(listTypeDefinition: ListTypeDefinition) =>
				listTypeDefinition.name === createdListTypeDefinitionName
		);

		await apiHelpers.listTypeAdmin.deleteListTypeDefinition(
			createdListTypeDefinition.id
		);

		createdListTypeDefinitionName = '';
	}
});

test.describe('manage picklists inside the picklists portlet', () => {
	test('can create a picklist', async ({listTypeDefinitionPage, page}) => {
		await listTypeDefinitionPage.goto();

		const listTypeDefinitionName = 'picklist' + getRandomInt();

		createdListTypeDefinitionName = listTypeDefinitionName;

		await listTypeDefinitionPage.createPicklist(listTypeDefinitionName);

		await expect(
			page.getByRole('link', {name: listTypeDefinitionName})
		).toBeVisible();
	});

	test('can create a picklist when the instance language is different from the site language', async ({
		listTypeDefinitionPage,
		page,
		siteSettingsLocalizationPage,
	}) => {
		await page.goto('/');

		await siteSettingsLocalizationPage.goto();

		await siteSettingsLocalizationPage.selectCustomDefaultLanguageOption();

		await siteSettingsLocalizationPage.setCustomDefaultLanguage('pt_BR');

		customDefaultSiteLanguage = 'pt_BR';

		await listTypeDefinitionPage.goto();

		const listTypeDefinitionName = 'picklist' + getRandomInt();

		createdListTypeDefinitionName = listTypeDefinitionName;

		await listTypeDefinitionPage.createPicklist(listTypeDefinitionName);

		await expect(
			page.getByRole('link', {name: listTypeDefinitionName})
		).toBeVisible();
	});

	test('ensure picklist entry keys starting with upper case are correctly rendered in the entries', async ({
		apiHelpers,
		listTypeDefinitionPage,
		page,
	}) => {
		await listTypeDefinitionPage.goto();

		const listTypeDefinitionName = 'ListTypeDefinition' + getRandomInt();

		await listTypeDefinitionPage.createPicklist(listTypeDefinitionName);

		const listTypeDefinitionEntryName = 'ListTypeDefinitionEntryName';

		const listTypeDefinitionEntryKey = 'ListTypeDefinitionEntryKey';

		await listTypeDefinitionPage.addPicklistItem(
			listTypeDefinitionName,
			listTypeDefinitionEntryName,
			listTypeDefinitionEntryKey
		);

		createdListTypeDefinitionName = listTypeDefinitionName;

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
