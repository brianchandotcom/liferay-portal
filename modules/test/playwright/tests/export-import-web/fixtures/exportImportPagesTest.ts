/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {test} from '@playwright/test';

import {ExportImportFramePage} from '../pages/ExportImportFramePage';

const exportImportPagesTest = test.extend<{
	exportImportFramePage: ExportImportFramePage;
}>({
	exportImportFramePage: async ({page}, use) => {
		await use(new ExportImportFramePage(page));
	},
});

export {exportImportPagesTest};
