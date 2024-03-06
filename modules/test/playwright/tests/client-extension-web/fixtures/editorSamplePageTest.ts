/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {EditorsSamplePage} from '../pages/EditorsSamplePage';

const editorSamplePageTest = test.extend<{
	editorSamplePage: EditorsSamplePage;
}>({
	editorSamplePage: async ({page}, use) => {
		await use(new EditorsSamplePage(page));
	},
});

export {editorSamplePageTest};
