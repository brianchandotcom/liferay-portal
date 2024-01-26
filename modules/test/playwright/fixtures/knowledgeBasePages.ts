/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {KnowledgeBasePage} from '../pages/knowledge-base-web/KnowledgeBase.page';
import {KnowledgeBaseEditArticlePage} from '../pages/knowledge-base-web/KnowledgeBaseEditArticle.page';
import {KnowledgeBaseViewArticlePage} from '../pages/knowledge-base-web/KnowledgeBaseViewArticle.page';

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
