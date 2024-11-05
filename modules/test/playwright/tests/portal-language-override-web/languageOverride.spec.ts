/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {languageOverridePageTest} from '../../fixtures/languageOverridePageTest';
import {loginTest} from '../../fixtures/loginTest';
import {TLanguageKey} from '../../pages/portal-language-override-web/LanguageOverridePage';
import getRandomString from '../../utils/getRandomString';
import {readFileFromZip} from '../../utils/zip';

export const test = mergeTests(loginTest(), languageOverridePageTest);

test('LPD-33373 assert that overriden translations can be exported', async ({
	languageOverridePage,
	page,
}) => {
	const languageKeys: TLanguageKey[] = [
		{
			key: getRandomString(),
			translations: [
				{
					languageId: 'en-US',
					value: getRandomString(),
				},
				{
					languageId: 'es-ES',
					value: getRandomString(),
				},
				{
					languageId: 'pt-BR',
					value: getRandomString(),
				},
			],
		},
		{
			key: getRandomString(),
			translations: [
				{
					languageId: 'en-US',
					value: getRandomString(),
				},
			],
		},
	];

	await languageOverridePage.goto();

	await languageOverridePage.addLanguageKeys(languageKeys);

	page.on('download', async (download) => {
		for (const languageKey of languageKeys) {
			for (let i = 0; i < languageKey.translations.length; i++) {
				const {languageId, value} = languageKey.translations[i];

				const fileContent = (await readFileFromZip(
					`Language_${languageId.replace('-', '_')}.properties`,
					await download.path()
				)) as string;

				expect(
					fileContent.includes(`${languageKey.key}=${value}`)
				).toBeTruthy();
			}
		}
	});

	await languageOverridePage.exportOverridenTranslations();
});

test('LPD-33373 assert that overriden translations can be filtered', async ({
	languageOverridePage,
}) => {
	await languageOverridePage.goto();

	const translation1 = {
		key: getRandomString(),
		translations: [
			{
				languageId: 'en-US',
				value: getRandomString(),
			},
			{
				languageId: 'pt-BR',
				value: getRandomString(),
			},
		],
	};
	const translation2: TLanguageKey = {
		key: getRandomString(),
		translations: [
			{
				languageId: 'en-US',
				value: getRandomString(),
			},
		],
	};

	await languageOverridePage.addLanguageKey(translation1);

	await languageOverridePage.addLanguageKey(translation2);

	await languageOverridePage.changeFilter('Selected Language');

	await languageOverridePage.changeLocale('en-US', 'pt-BR');

	await languageOverridePage.searchLanguageKey(translation1.key);

	await languageOverridePage.assertLanguageKeyInListView(translation1);

	await languageOverridePage.searchLanguageKey(translation2.key);

	await languageOverridePage.assertLanguageKeyNotInListView(translation2.key);

	await languageOverridePage.changeFilter('Any Language');

	await languageOverridePage.changeLocale('pt-BR', 'en-US');

	await languageOverridePage.searchLanguageKey(translation1.key);

	await languageOverridePage.assertLanguageKeyInListView(translation1);

	await languageOverridePage.searchLanguageKey(translation2.key);

	await languageOverridePage.assertLanguageKeyInListView(translation2);
});

test('LPD-33373 assert that default and overriden translations show up when no filters are applied', async ({
	languageOverridePage,
}) => {
	await languageOverridePage.goto();

	const languageKey: TLanguageKey = {
		key: getRandomString(),
		translations: [
			{
				languageId: 'en-US',
				value: getRandomString(),
			},
			{
				languageId: 'pt-BR',
				value: getRandomString(),
			},
		],
	};

	await languageOverridePage.addLanguageKey(languageKey);

	await languageOverridePage.assertLanguageKeyInListView({
		key: '0-analytics-cloud-connection',
		translations: [],
	});

	await languageOverridePage.searchLanguageKey(languageKey.key);

	await languageOverridePage.assertLanguageKeyInListView(languageKey);
});
