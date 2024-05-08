/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {FragmentEditorPage} from '../pages/fragment-web/FragmentEditorPage';
import {FragmentsPage} from '../pages/fragment-web/FragmentsPage';

const fragmentsPagesTest = test.extend<{
	fragmentEditorPage: FragmentEditorPage;
	fragmentsPage: FragmentsPage;
}>({
	fragmentEditorPage: async ({page}, use) => {
		await use(new FragmentEditorPage(page));
	},
	fragmentsPage: async ({page}, use) => {
		await use(new FragmentsPage(page));
	},
});

export {fragmentsPagesTest};
