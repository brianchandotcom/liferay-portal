/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {KnowledgeBaseEditArticlePage} from '../pages/knowledge-base-web/KnowledgeBaseEditArticlePage';
import {KnowledgeBasePage} from '../pages/knowledge-base-web/KnowledgeBasePage';
import {KnowledgeBaseViewArticlePage} from '../pages/knowledge-base-web/KnowledgeBaseViewArticlePage';

const knowledgeBasePages = test.extend<{
	knowledgeBaseEditArticlePage: KnowledgeBaseEditArticlePage;
	knowledgeBasePage: KnowledgeBasePage;
	knowledgeBaseViewArticlePage: KnowledgeBaseViewArticlePage;
}>({
	knowledgeBaseEditArticlePage: async ({page}, use) => {
		await use(new KnowledgeBaseEditArticlePage(page));
	},
	knowledgeBasePage: async ({page}, use) => {
		await use(new KnowledgeBasePage(page));
	},
	knowledgeBaseViewArticlePage: async ({page}, use) => {
		await use(new KnowledgeBaseViewArticlePage(page));
	},
});

export {knowledgeBasePages};
