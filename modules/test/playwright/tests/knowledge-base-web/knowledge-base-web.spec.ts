/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {knowledgeBasePages} from '../../fixtures/knowldegbeBasePages';
import {loginTest} from '../../fixtures/loginTest';
import {getRandomString} from '../../utils/util';

export const test = mergeTests(apiHelpersTest, knowledgeBasePages, loginTest);

test('can publish and delete an article', async ({
	apiHelpers,
	knowledgeBaseEditArticle,
	knowledgeBasePage,
	page,
}) => {
	await apiHelpers.featureFlag.updateFeatureFlag('LPS-188058', false);

	const content = getRandomString();
	const title = getRandomString();
	const kbArticle = page.getByRole('link', {name: title});

	await knowledgeBaseEditArticle.publishNewKnowledgeBaseArticle(
		content,
		title
	);
	await expect(kbArticle).toBeVisible();

	await knowledgeBasePage.deleteKnowledgeBaseArticle(title);
	await expect(kbArticle).toBeHidden();

	await page.close();
});

test('can schedule the publication of an article and delete it afterward', async ({
	apiHelpers,
	knowledgeBaseEditArticle,
	knowledgeBaseViewArticlePage,
	page,
}) => {
	await apiHelpers.featureFlag.updateFeatureFlag('LPS-188058', true);

	const content = getRandomString();
	const title = getRandomString();
	const kbArticle = page.getByRole('link', {name: title});

	await knowledgeBaseEditArticle.publishNewKnowledgeBaseArticleWithSchedule(
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

	await apiHelpers.featureFlag.updateFeatureFlag('LPS-188058', false);

	await page.close();
});

test('can delete all articles without a recycle bin', async ({
	apiHelpers,
	knowledgeBaseEditArticle,
	knowledgeBasePage,
	page,
}) => {
	await apiHelpers.featureFlag.updateFeatureFlag('LPS-188058', false);

	await knowledgeBaseEditArticle.publishNewKnowledgeBaseArticle(
		getRandomString(),
		getRandomString()
	);

	await knowledgeBasePage.deleteAll(page, false);
	await expect(
		page.getByRole('heading', {name: 'Knowledge base is empty.'})
	).toBeVisible();

	await page.close();
});

test('can delete all articles with a recycle bin', async ({
	apiHelpers,
	knowledgeBaseEditArticle,
	knowledgeBasePage,
	page,
}) => {
	await apiHelpers.featureFlag.updateFeatureFlag('LPS-188058', true);

	await knowledgeBaseEditArticle.publishNewKnowledgeBaseArticleWithSchedule(
		getRandomString(),
		getRandomString()
	);

	await knowledgeBasePage.deleteAll(page, true);
	await expect(
		page.locator(
			'[id="_com_liferay_knowledge_base_web_portlet_AdminPortlet_recycleBinAlert"]'
		)
	).toBeVisible();
	await expect(
		page.getByRole('heading', {name: 'Knowledge base is empty.'})
	).toBeVisible();

	await apiHelpers.featureFlag.updateFeatureFlag('LPS-188058', false);

	await page.close();
});
