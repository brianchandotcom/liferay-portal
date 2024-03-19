/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {FriendlyUrlInstanceSettingsPage} from '../pages/FriendlyUrlInstanceSettingsPage';
import {JournalEditArticlePage} from '../pages/JournalEditArticlePage';
import {JournalEditArticleTranslationsPage} from '../pages/JournalEditArticleTranslationsPage';
import {JournalEditTemplatePage} from '../pages/JournalEditTemplatePage';
import {JournalPage} from '../pages/JournalPage';
import {PageTemplatePage} from '../pages/PageTemplatePage';

const journalPagesTest = test.extend<{
	friendlyUrlInstanceSettingsPage: FriendlyUrlInstanceSettingsPage;
	journalEditArticlePage: JournalEditArticlePage;
	journalEditArticleTranslationsPage: JournalEditArticleTranslationsPage;
	journalEditTemplatePage: JournalEditTemplatePage;
	journalPage: JournalPage;
	pageTemplatePage: PageTemplatePage;
}>({
	friendlyUrlInstanceSettingsPage: async ({page}, use) => {
		await use(new FriendlyUrlInstanceSettingsPage(page));
	},
	journalEditArticlePage: async ({page}, use) => {
		await use(new JournalEditArticlePage(page));
	},
	journalEditArticleTranslationsPage: async ({page}, use) => {
		await use(new JournalEditArticleTranslationsPage(page));
	},
	journalEditTemplatePage: async ({page}, use) => {
		await use(new JournalEditTemplatePage(page));
	},
	journalPage: async ({page}, use) => {
		await use(new JournalPage(page));
	},
	pageTemplatePage: async ({page}, use) => {
		await use(new PageTemplatePage(page));
	},
});

export {journalPagesTest};
