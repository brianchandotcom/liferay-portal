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
	}

	if (createdListTypeDefinitionName) {
		const listTypeDefinitions =
			await apiHelpers.listTypeAdmin.getListTypeDefinitions();

		const [createdListTypeDefinition] = listTypeDefinitions.items.filter(
			(listTypeDefinition: PickList) =>
				listTypeDefinition.name === createdListTypeDefinitionName
		);

		await apiHelpers.listTypeAdmin.deleteListTypeDefinition(
			createdListTypeDefinition.id
		);
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
});
