/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import test from '@playwright/test';

import {LanguageOverridePage} from '../pages/portal-language-override-web/LanguageOverridePage';

const languageOverridePageTest = test.extend<{
	languageOverridePage: LanguageOverridePage;
}>({
	languageOverridePage: async ({page}, use) => {
		await use(new LanguageOverridePage(page));
	},
});

export {languageOverridePageTest};
