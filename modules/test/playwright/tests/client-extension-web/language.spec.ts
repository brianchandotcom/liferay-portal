/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {languageOverridePageTest} from '../../fixtures/languageOverridePageTest';
import {loginTest} from '../../fixtures/loginTest';
import {TLanguageKey} from '../../pages/portal-language-override-web/LanguageOverridePage';

const test = mergeTests(languageOverridePageTest, loginTest());

const EXPECTED_LANGUAGE_KEY: TLanguageKey = {
	key: 'do-you-like-to-eat-pizza-with-anchovies',
	translations: [
		{
			languageId: 'en-US',
			value: 'Do you like to eat pizza with anchovies?',
		},
		{
			languageId: 'es-AR',
			value: '¿Te gusta comer pizza con anchoas?',
		},
		{
			languageId: 'ja-JP',
			value: 'アンチョビ入りのピザは好きですか？',
		},
		{
			languageId: 'pt-BR',
			value: 'Você gosta de comer pizza com anchovas?',
		},
		{
			languageId: 'pt-PT',
			value: 'Você gosta de comer pizza com anchovas?',
		},
	],
};

test('LPD-36494 Assert that the language client extension is deployed', async ({
	languageOverridePage,
}) => {
	await test.step('Check that the translations were imported', async () => {
		await languageOverridePage.goto();

		await languageOverridePage.changeFilter('Any Language');

		await languageOverridePage.searchLanguageKey(EXPECTED_LANGUAGE_KEY.key);

		await languageOverridePage.assertLanguageKeyInListView(
			EXPECTED_LANGUAGE_KEY
		);

		await languageOverridePage.assertLanguageKeyTranslations(
			EXPECTED_LANGUAGE_KEY
		);
	});

	await test.step('Check that a translation with (Automatic Copy) was not imported', async () => {
		await languageOverridePage.assertLanguageKeyTranslationIsEmpty('es-ES');
	});
});
