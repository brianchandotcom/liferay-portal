/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {ListTypeDefinitionsPage} from '../pages/object-web/list-type/ListTypeDefinitionsPage';

const listTypeDefinitionsPagesTest = test.extend<{
	listTypeDefinitionPage: ListTypeDefinitionsPage;
}>({
	listTypeDefinitionPage: async ({page}, use) => {
		await use(new ListTypeDefinitionsPage(page));
	},
});

export {listTypeDefinitionsPagesTest};
