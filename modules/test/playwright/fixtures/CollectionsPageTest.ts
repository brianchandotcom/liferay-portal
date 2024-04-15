/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {CollectionsPage} from '../pages/asset-list-web/CollectionsPage';

const collectionsPagesTest = test.extend<{
	collectionsPage: CollectionsPage;
}>({
	collectionsPage: async ({page}, use) => {
		await use(new CollectionsPage(page));
	},
});

export {collectionsPagesTest};
