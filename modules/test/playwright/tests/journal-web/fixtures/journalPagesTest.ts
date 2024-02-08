/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {JournalEditTemplatePage} from '../pages/JournalEditTemplatePage';
import {JournalPage} from '../pages/JournalPage';

const journalPagesTest = test.extend<{
	journalEditTemplatePage: JournalEditTemplatePage;
	journalPage: JournalPage;
}>({
	journalEditTemplatePage: async ({page}, use) => {
		await use(new JournalEditTemplatePage(page));
	},
	journalPage: async ({page}, use) => {
		await use(new JournalPage(page));
	},
});

export {journalPagesTest};
