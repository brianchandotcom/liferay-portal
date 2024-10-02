/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {FriendlyUrlInstanceSettingsPage} from '../../../pages/friendly-url-web/FriendlyUrlInstanceSettingsPage';
import {DisplayPageTemplatesPage} from '../../../pages/layout-page-template-admin-web/DisplayPageTemplatesPage';
import {JournalEditArticlePage} from '../pages/JournalEditArticlePage';
import {JournalEditArticleTranslationsPage} from '../pages/JournalEditArticleTranslationsPage';
import {JournalEditStructureDefaultValuesPage} from '../pages/JournalEditStructureDefaultValuesPage';
import {JournalEditStructurePage} from '../pages/JournalEditStructurePage';
import {JournalEditTemplatePage} from '../pages/JournalEditTemplatePage';
import {JournalPage} from '../pages/JournalPage';
import {JournalStructuresPage} from '../pages/JournalStructuresPage';

const journalPagesTest = test.extend<{
	displayPageTemplatesPage: DisplayPageTemplatesPage;
	friendlyUrlInstanceSettingsPage: FriendlyUrlInstanceSettingsPage;
	journalEditArticlePage: JournalEditArticlePage;
	journalEditArticleTranslationsPage: JournalEditArticleTranslationsPage;
	journalEditStructureDefaultValuesPage: JournalEditStructureDefaultValuesPage;
	journalEditStructurePage: JournalEditStructurePage;
	journalEditTemplatePage: JournalEditTemplatePage;
	journalPage: JournalPage;
	journalStructuresPage: JournalStructuresPage;
}>({
	displayPageTemplatesPage: async ({page}, use) => {
		await use(new DisplayPageTemplatesPage(page));
	},
	friendlyUrlInstanceSettingsPage: async ({page}, use) => {
		await use(new FriendlyUrlInstanceSettingsPage(page));
	},
	journalEditArticlePage: async ({page}, use) => {
		await use(new JournalEditArticlePage(page));
	},
	journalEditArticleTranslationsPage: async ({page}, use) => {
		await use(new JournalEditArticleTranslationsPage(page));
	},
	journalEditStructureDefaultValuesPage: async ({page}, use) => {
		await use(new JournalEditStructureDefaultValuesPage(page));
	},
	journalEditStructurePage: async ({page}, use) => {
		await use(new JournalEditStructurePage(page));
	},
	journalEditTemplatePage: async ({page}, use) => {
		await use(new JournalEditTemplatePage(page));
	},
	journalPage: async ({page}, use) => {
		await use(new JournalPage(page));
	},
	journalStructuresPage: async ({page}, use) => {
		await use(new JournalStructuresPage(page));
	},
});

export {journalPagesTest};
