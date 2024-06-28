/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import getBasicWebContentStructureId from '../../utils/structured-content/getBasicWebContentStructureId';
import {journalPagesTest} from './fixtures/journalPagesTest';
import getDataStructureDefinition from './utils/getDataStructureDefinition';

export const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	journalPagesTest,
	loginTest()
);

test('LPP-50468 After clicking on Clear (filter by structrure) you can see all the web contents', async ({
	apiHelpers,
	journalEditArticlePage,
	journalPage,
	page,
	site,
}) => {
	//1) create basic web content
	const basicWebContentStructureId = await getBasicWebContentStructureId(apiHelpers);

	await apiHelpers.jsonWebServicesJournal.addWebContent({
		ddmStructureId: basicWebContentStructureId,
		groupId: site.id,
		titleMap: {en_US: 'First Web content'}
	});

	//2) Create structure
	const structureName = 'Structure Test';

	const dataDefinition = getDataStructureDefinition({
		defaultLanguageId: 'en_US',
		fields: [{name: 'Text', repeatable: false}],
		name: structureName,
	});

	await apiHelpers.dataEngine.createStructure(site.id, dataDefinition);

	//3) Create web content based on structure
	await journalEditArticlePage.goto({
		siteUrl: site.friendlyUrlPath,
		structureName,
	});

	await journalEditArticlePage.createArticleForStructure({
		structureName,
		title: 'Second Web Content',
	});

	await journalPage.goto(site.friendlyUrlPath);

	await expect(
		page.getByRole('link', { name: 'First Web content' })
	).toBeVisible();

	await expect(
		page.getByRole('link', { name: 'Second Web content' })
	).toBeVisible();

	await page.getByLabel('Filter', { exact: true }).click();
	await page.getByRole('menuitem', { name: 'Structures' }).click();
	await page.frameLocator('iframe[title="Structures"]').getByRole('cell', { name: 'Basic Web Content' }).click();

	await page.getByRole('heading', {name: 'Search Results'}).waitFor();

	await expect(
		page.getByRole('link', { name: 'Second Web content' })
	).toBeHidden();

	await page.getByLabel('Clear 1 Result for Structures: Basic Web Content').click();

	await expect(
		page.getByRole('link', { name: 'Second Web content' })
	).toBeVisible();
});