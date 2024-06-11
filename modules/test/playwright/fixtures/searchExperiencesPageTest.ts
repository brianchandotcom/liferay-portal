/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {SearchExperiencesPage} from '../pages/search-experiences-web/SearchExperiencesPage';

const searchExperiencesPageTest = test.extend<{
	searchExperiencesPage: SearchExperiencesPage;
}>({
	searchExperiencesPage: async ({page}, use) => {
		await use(new SearchExperiencesPage(page));
	},
});

export {searchExperiencesPageTest};
