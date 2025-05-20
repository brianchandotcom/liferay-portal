/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {languageOverridePageTest} from '../../../fixtures/languageOverridePageTest';
import {loginTest} from '../../../fixtures/loginTest';
import {TLanguageKey} from '../../../pages/portal-language-override-web/LanguageOverridePage';
import getRandomString from '../../../utils/getRandomString';

export const test = mergeTests(loginTest(), languageOverridePageTest);

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
