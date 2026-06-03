/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {QuestionsConfigurationPage} from '../pages/QuestionsConfigurationPage';
import {QuestionsPage} from '../pages/QuestionsPage';
import {QuestionsTopicsPage} from '../pages/QuestionsTopicsPage';

const questionsPagesTest = test.extend<{
	questionsConfigurationPage: QuestionsConfigurationPage;
	questionsPage: QuestionsPage;
	questionsTopicsPage: QuestionsTopicsPage;
}>({
	questionsConfigurationPage: async ({page}, use) => {
		await use(new QuestionsConfigurationPage(page));
	},
	questionsPage: async ({page}, use) => {
		await use(new QuestionsPage(page));
	},
	questionsTopicsPage: async ({page}, use) => {
		await use(new QuestionsTopicsPage(page));
	},
});

export {questionsPagesTest};
