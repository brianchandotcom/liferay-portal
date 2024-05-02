/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {knowledgeBasePages} from '../../fixtures/knowledgeBasePagesTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';
import {waitForSuccessAlert} from '../../utils/waitForSuccessAlert';

const testFeatureFlagsEnabled = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-188058': true,
	}),
	isolatedSiteTest,
	knowledgeBasePages,
	loginTest()
);

const testFeatureFlagsDisabled = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-188058': false,
	}),
	isolatedSiteTest,
	knowledgeBasePages,
	loginTest()
);

testFeatureFlagsDisabled(
	'can publish and delete an article with scheduling disabled',
	async ({knowledgeBaseEditArticlePage, knowledgeBasePage, page, site}) => {
		const content = getRandomString();
		const title = getRandomString();
		const kbArticle = page.getByRole('link', {name: title});

		await knowledgeBaseEditArticlePage.goto(site.friendlyUrlPath);

		await knowledgeBaseEditArticlePage.publishNewKnowledgeBaseArticle(
			content,
			title
		);

		await waitForSuccessAlert(
			page,
			`Success:${title} was successfully published.`
		);
		await expect(kbArticle).toBeVisible();

		await knowledgeBasePage.goto(site.friendlyUrlPath);
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
		site,
	}) => {
		const content = getRandomString();
		const title = getRandomString();
		const kbArticle = page.getByRole('link', {name: title});

		await knowledgeBaseEditArticlePage.goto(site.friendlyUrlPath);

		await knowledgeBaseEditArticlePage.publishNewKnowledgeBaseArticleWithSchedule(
			content,
			title
		);

		await waitForSuccessAlert(
			page,
			`Success:${title} was successfully published.`
		);
		await expect(kbArticle).toBeVisible();

		await knowledgeBaseViewArticlePage.goto(site.friendlyUrlPath, title);
		await knowledgeBaseViewArticlePage.deleteKnowledgeBaseArticle();

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
	async ({knowledgeBaseEditArticlePage, knowledgeBasePage, page, site}) => {
		await knowledgeBaseEditArticlePage.goto(site.friendlyUrlPath);

		const title = getRandomString();

		await knowledgeBaseEditArticlePage.publishNewKnowledgeBaseArticle(
			getRandomString(),
			title
		);

		await waitForSuccessAlert(
			page,
			`Success:${title} was successfully published.`
		);

		await knowledgeBasePage.goto(site.friendlyUrlPath);
		await knowledgeBasePage.deleteAll(false);
		await expect(
			page.getByRole('heading', {name: 'Knowledge base is empty.'})
		).toBeVisible();
	}
);

testFeatureFlagsEnabled(
	'can delete all articles with a recycle bin enabled',
	async ({knowledgeBaseEditArticlePage, knowledgeBasePage, page, site}) => {
		await knowledgeBaseEditArticlePage.goto(site.friendlyUrlPath);

		const title = getRandomString();

		await knowledgeBaseEditArticlePage.publishNewKnowledgeBaseArticleWithSchedule(
			getRandomString(),
			title
		);

		await waitForSuccessAlert(
			page,
			`Success:${title} was successfully published.`
		);

		await knowledgeBasePage.goto(site.friendlyUrlPath);
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

testFeatureFlagsEnabled(
	'can schedule and delete an article with scheduling enabled',
	async ({
		knowledgeBaseEditArticlePage,
		knowledgeBaseViewArticlePage,
		page,
		site,
	}) => {
		const title = getRandomString();
		const kbArticle = page.getByRole('link', {name: title});

		await knowledgeBaseEditArticlePage.goto(site.friendlyUrlPath);

		await knowledgeBaseEditArticlePage.scheduleNewKnowledgeBaseArticle(
			getRandomString(),
			`${new Date().getFullYear() + 1}-01-01 00:00`,
			title
		);

		await waitForSuccessAlert(
			page,
			`Success:${title} will be published on`
		);
		await expect(kbArticle).toBeVisible();

		await knowledgeBaseViewArticlePage.goto(site.friendlyUrlPath, title);

		await knowledgeBaseViewArticlePage.deleteKnowledgeBaseArticle();
		await expect(
			page.locator(
				'[id="_com_liferay_knowledge_base_web_portlet_AdminPortlet_recycleBinAlert"]'
			)
		).toBeVisible();
		await expect(kbArticle).toBeHidden();
	}
);
