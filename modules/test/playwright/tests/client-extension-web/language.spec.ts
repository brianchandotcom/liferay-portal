/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {languageOverridePageTest} from '../../fixtures/LanguageOverridePageTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';

const test = mergeTests(
	languageOverridePageTest,
	featureFlagsTest({
		'LPD-27222': true,
	}),
	loginTest()
);

const EXPECTED_TRANSLATION = {
	key: 'do-you-like-to-eat-pizza-with-anchovies',
	values: [
		{
			languageId: 'de-DE',
			value: 'Magst du es, Pizza mit Sardellen zu essen?',
		},
		{
			languageId: 'en-US',
			value: 'Do you like to eat pizza with anchovies?',
		},
		{
			languageId: 'hu-HU',
			value: 'Szereted a pizzát szardellával enni?',
		},
		{
			languageId: 'ja-JP',
			value: 'アンチョビ入りのピザは好きですか？',
		},
	],
};

test('LPD-36494 assert that the language client extension is deployed', async ({
	languageOverridePage,
}) => {
	await languageOverridePage.goto();

	await languageOverridePage.changeFilter('Any Language');

	await languageOverridePage.searchTranslation(EXPECTED_TRANSLATION.key);

	await languageOverridePage.assertTranslationInListView(
		EXPECTED_TRANSLATION
	);

	await languageOverridePage.assertKeyTranslations(EXPECTED_TRANSLATION);
});
