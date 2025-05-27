/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {CKEditor4Page} from '../pages/CKEditor4Page';

const ckeditor4PageTest = test.extend<{
	ckeditor4Page: CKEditor4Page;
}>({
	ckeditor4Page: async ({page}, use) => {
		await use(new CKEditor4Page(page));
	},
});

export {ckeditor4PageTest};
