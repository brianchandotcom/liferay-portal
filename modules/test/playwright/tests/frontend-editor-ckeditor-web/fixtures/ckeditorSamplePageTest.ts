/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {CKEditorSamplePage} from '../pages/CKEditorSamplePage';

const ckeditorSamplePageTest = test.extend<{
	ckeditorSamplePage: CKEditorSamplePage;
}>({
	ckeditorSamplePage: async ({page}, use) => {
		await use(new CKEditorSamplePage(page));
	},
});

export {ckeditorSamplePageTest};
