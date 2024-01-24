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
