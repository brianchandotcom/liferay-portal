/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {languageOverridePageTest} from '../../fixtures/LanguageOverridePageTest';
import {loginTest} from '../../fixtures/loginTest';
import {TLanguageOverride as TTranslation} from '../../pages/portal-language-override-web/LanguageOverridePage';
import getRandomString from '../../utils/getRandomString';
import {readFileFromZip} from '../../utils/zip';

export const test = mergeTests(loginTest(), languageOverridePageTest);

test('LPD-33373 assert that overriden translations can be exported', async ({
	languageOverridePage,
	page,
}) => {
	const translations: TTranslation[] = [
		{
			key: getRandomString(),
			languageIds: ['en-US', 'es-ES', 'pt-BR'],
			values: [getRandomString(), getRandomString(), getRandomString()],
		},
		{
			key: getRandomString(),
			languageIds: ['en-US'],
			values: [getRandomString()],
		},
	];

	await languageOverridePage.goto();

	await languageOverridePage.addTranslations(translations);

	page.on('download', async (download) => {
		for (const translation of translations) {
			for (let i = 0; i < translation.languageIds.length; i++) {
				const languageId = translation.languageIds[i];

				const fileContent = (await readFileFromZip(
					`Language_${languageId.replace('-', '_')}.properties`,
					await download.path()
				)) as string;

				expect(
					fileContent.includes(
						`${translation.key}=${translation.values[i]}`
					)
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
		languageIds: ['en-US', 'pt-BR'],
		values: [getRandomString(), getRandomString()],
	};
	const translation2 = {
		key: getRandomString(),
		languageIds: ['en-US'],
		values: [getRandomString()],
	};

	await languageOverridePage.addTranslation(translation1);

	await languageOverridePage.addTranslation(translation2);

	await languageOverridePage.changeFilter('Selected Language');

	await languageOverridePage.changeLocale('en-US', 'pt-BR');

	await languageOverridePage.searchTranslation(translation1.key);

	await languageOverridePage.assertTranslationInListView(translation1);

	await languageOverridePage.searchTranslation(translation2.key);

	await languageOverridePage.assertTranslationNotInListView(translation2);

	await languageOverridePage.changeFilter('Any Language');

	await languageOverridePage.changeLocale('pt-BR', 'en-US');

	await languageOverridePage.searchTranslation(translation1.key);

	await languageOverridePage.assertTranslationInListView(translation1);

	await languageOverridePage.searchTranslation(translation2.key);

	await languageOverridePage.assertTranslationInListView(translation2);
});

test('LPD-33373 assert that default and overriden translations show up when no filters are applied', async ({
	languageOverridePage,
}) => {
	await languageOverridePage.goto();

	const translation = {
		key: getRandomString(),
		languageIds: ['en-US', 'pt-BR'],
		values: [getRandomString(), getRandomString()],
	};

	await languageOverridePage.addTranslation(translation);

	await languageOverridePage.assertTranslationInListView({
		key: '0-analytics-cloud-connection',
		languageIds: [],
		values: [],
	});

	await languageOverridePage.searchTranslation(translation.key);

	await languageOverridePage.assertTranslationInListView(translation);
});
