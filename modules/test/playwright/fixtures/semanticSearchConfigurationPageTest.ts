/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {SemanticSearchConfigurationPage} from '../pages/portal-search-web/SemanticSearchConfigurationPage';

const semanticSearchConfigurationPageTest = test.extend<{
	semanticSearchConfigurationPage: SemanticSearchConfigurationPage;
}>({
	semanticSearchConfigurationPage: async ({page}, use) => {
		await use(new SemanticSearchConfigurationPage(page));
	},
});

export {semanticSearchConfigurationPageTest};
