/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {TranslationsAdminPage} from '../pages/TranslationsAdminPage';
import {WebContentTranslationPage} from '../pages/WebContentTranslationPage';

const translationPagesTest = test.extend<{
	translationsAdminPage: TranslationsAdminPage;
	webContentTranslationPage: WebContentTranslationPage;
}>({
	translationsAdminPage: async ({page}, use) => {
		await use(new TranslationsAdminPage(page));
	},
	webContentTranslationPage: async ({page}, use) => {
		await use(new WebContentTranslationPage(page));
	},
});

export {translationPagesTest};
