/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {StructureBuilderPage} from '../pages/StructureBuilderPage';

const cmsPagesTest = test.extend<{
	structureBuilderPage: StructureBuilderPage;
}>({
	structureBuilderPage: async ({page}, use) => {
		await use(new StructureBuilderPage(page));
	},
});

export {cmsPagesTest};
