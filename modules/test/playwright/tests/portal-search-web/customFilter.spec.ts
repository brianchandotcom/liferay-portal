/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {searchPageTest} from '../../fixtures/searchPageTest';
import getRandomString from '../../utils/getRandomString';
import getBasicWebContentStructureId from '../../utils/structured-content/getBasicWebContentStructureId';

export const test = mergeTests(
	dataApiHelpersTest,
	isolatedSiteTest,
	loginTest(),
	featureFlagsTest({
		'LPS-178052': true,
	}),
	pageEditorPagesTest,
	searchPageTest
);

test.describe('Custom filter configuration works as expected on content pages', () => {
	test('Custom filter configuration does not leak between experiences @LPD-35585', async ({
		apiHelpers,
		page,
		pageEditorPage,
		searchPage,
		site,
	}) => {
		let layout: Layout;

		await test.step('Create journal articles', async () => {
			const contentStructureId =
				await getBasicWebContentStructureId(apiHelpers);

			await apiHelpers.jsonWebServicesJournal.addWebContent({
				ddmStructureId: contentStructureId,
				groupId: site.id,
				titleMap: {
					en_US: 'WC English',
				},
			});

			await apiHelpers.jsonWebServicesJournal.addWebContent({
				ddmStructureId: contentStructureId,
				groupId: site.id,
				titleMap: {
					en_US: 'WC Portuguese',
				},
			});

			await apiHelpers.jsonWebServicesJournal.addWebContent({
				ddmStructureId: contentStructureId,
				groupId: site.id,
				titleMap: {
					en_US: 'WC English & Portuguese',
				},
			});
		});

		await test.step('Create site page and go to the page', async () => {
			layout = await apiHelpers.headlessDelivery.createSitePage({
				siteId: site.id,
				title: getRandomString(),
			});

			await pageEditorPage.goto(layout, site.friendlyUrlPath);
		});

		await test.step('Add search bar, results portlet and custom filter to new page', async () => {
			await pageEditorPage.addWidget('Search', 'Search Bar');

			await pageEditorPage.addWidget('Search', 'Search Results');

			await pageEditorPage.addWidget('Search', 'Custom Filter');
		});

		await test.step('Configure custom filter in default experience to return only results that contains Portuguese in the title_en_US field', async () => {
			await searchPage.openSearchPortletConfiguration('Custom Filter');

			await searchPage.modalIFrame
				.getByLabel('Filter Field Set the name of')
				.fill('title_en_US');

			await searchPage.modalIFrame
				.getByLabel('Filter Value The value to')
				.fill('Portuguese');

			await searchPage.savePortletConfiguration();
		});

		await test.step('Create new experience', async () => {
			await pageEditorPage.createExperience('Test Experience');
		});

		await test.step('Configure custom filter in new experience to return only results that contains English in the title_en_US field', async () => {
			await searchPage.openSearchPortletConfiguration('Custom Filter');

			await searchPage.modalIFrame
				.getByLabel('Filter Field Set the name of')
				.fill('title_en_US');

			await searchPage.modalIFrame
				.getByLabel('Filter Value The value to')
				.fill('English');

			await searchPage.savePortletConfiguration();
		});

		await test.step('Publish page and exit edit mode', async () => {
			await pageEditorPage.publishPage();

			await page.goto(`/web/${site.name}/${layout.friendlyUrlPath}`);
		});

		await test.step('Search for Portuguese in default experience and find two journal articles that contains Portuguese', async () => {
			await searchPage.searchKeywordInMainContent('Portuguese');

			await expect(searchPage.searchResultsTotalLabel).toHaveText(
				/2+ Results for\s+/
			);
		});

		await test.step('Search for English in default experience and find one journal articles that contains Portuguese', async () => {
			await searchPage.searchKeywordInMainContent('English');

			await expect(searchPage.searchResultsTotalLabel).toHaveText(
				/1+ Result for English+/
			);
		});
	});
});
