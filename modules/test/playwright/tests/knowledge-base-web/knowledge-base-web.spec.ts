/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {knowledgeBasePages} from '../../fixtures/knowledgeBasePages';
import {loginTest} from '../../fixtures/loginTest';
import {getRandomString} from '../../utils/util';

const testFeatureFlagsEnabled = mergeTests(
	loginTest,
	apiHelpersTest,
	featureFlagsTest({
		'LPS-188058': true,
	}),
	knowledgeBasePages
);

const testFeatureFlagsDisabled = mergeTests(
	loginTest,
	apiHelpersTest,
	featureFlagsTest({
		'LPS-188058': false,
	}),
	knowledgeBasePages
);

testFeatureFlagsDisabled(
	'can publish and delete an article with scheduling disabled',
	async ({knowledgeBaseEditArticlePage, knowledgeBasePage, page}) => {
		const content = getRandomString();
		const title = getRandomString();
		const kbArticle = page.getByRole('link', {name: title});

		await knowledgeBaseEditArticlePage.publishNewKnowledgeBaseArticle(
			content,
			title
		);
		await expect(kbArticle).toBeVisible();

		await knowledgeBasePage.deleteKnowledgeBaseArticle(title);
		await expect(kbArticle).toBeHidden();
	}
);

testFeatureFlagsEnabled(
	'can publish and delete an article with scheduling enabled',
	async ({
		knowledgeBaseEditArticlePage,
		knowledgeBaseViewArticlePage,
		page,
	}) => {
		const content = getRandomString();
		const title = getRandomString();
		const kbArticle = page.getByRole('link', {name: title});

		await knowledgeBaseEditArticlePage.publishNewKnowledgeBaseArticleWithSchedule(
			content,
			title
		);
		await expect(kbArticle).toBeVisible();

		await knowledgeBaseViewArticlePage.deleteKnowledgeBaseArticle(title);
		await expect(
			page.locator(
				'[id="_com_liferay_knowledge_base_web_portlet_AdminPortlet_recycleBinAlert"]'
			)
		).toBeVisible();
		await expect(kbArticle).toBeHidden();
	}
);

testFeatureFlagsDisabled(
	'can delete all articles with a recycle bin disabled',
	async ({knowledgeBaseEditArticlePage, knowledgeBasePage, page}) => {
		await knowledgeBaseEditArticlePage.publishNewKnowledgeBaseArticle(
			getRandomString(),
			getRandomString()
		);

		await knowledgeBasePage.deleteAll(false);
		await expect(
			page.getByRole('heading', {name: 'Knowledge base is empty.'})
		).toBeVisible();
	}
);

testFeatureFlagsEnabled(
	'can delete all articles with a recycle bin enabled',
	async ({knowledgeBaseEditArticlePage, knowledgeBasePage, page}) => {
		await knowledgeBaseEditArticlePage.publishNewKnowledgeBaseArticleWithSchedule(
			getRandomString(),
			getRandomString()
		);

		await knowledgeBasePage.deleteAll(true);
		await expect(
			page.locator(
				'[id="_com_liferay_knowledge_base_web_portlet_AdminPortlet_recycleBinAlert"]'
			)
		).toBeVisible();
		await expect(
			page.getByRole('heading', {name: 'Knowledge base is empty.'})
		).toBeVisible();
	}
);
