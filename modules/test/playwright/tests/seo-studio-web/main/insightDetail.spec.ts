/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {InsightType, PageData, Scan} from '../../../helpers/SEOStudioApiHelper';
import {seoStudioPagesTest} from './fixtures/seoStudioPagesTest';
import {seoStudioSiteTest} from './fixtures/seoStudioSiteTest';

const test = mergeTests(
	loginTest(),
	dataApiHelpersTest,
	featureFlagsTest({'LPD-44511': {enabled: true}}),
	seoStudioPagesTest,
	seoStudioSiteTest
);

let insightTypeInput: InsightType & {pageURLs: PageData[]};
let scan: Scan;

test.beforeEach(async ({apiHelpers, onPagePage, seoStudioSite}) => {
	insightTypeInput = {
		category: 'metadata',
		description: 'Pages without meta description.',
		fixHint: 'Add a unique meta description to each page.',
		name: 'missingMetaDescription',
		pageURLs: [
			{
				author: 'Alice',
				pageURL: 'https://example.com/alpha',
				title: 'Alpha',
				type: 'Web Content',
			},
			{
				author: 'Bob',
				pageURL: 'https://example.com/beta',
				title: 'Beta',
				type: 'Document',
			},
		],
		severity: '3',
	};

	scan = await apiHelpers.seoStudio.createScan('full');

	await apiHelpers.seoStudio.createInsights(scan, [insightTypeInput]);

	await onPagePage.goto(seoStudioSite.friendlyUrlPath);
});

test.afterEach(async () => {
	await scan.teardown();
});

test(
	'Renders the breadcrumb, title, and content sections on the insight detail screen',
	{tag: '@LPD-91182'},
	async ({insightDetailPage, onPagePage, page}) => {
		await onPagePage.selectInsight(insightTypeInput.name);

		await expect(insightDetailPage.onPageBreadcrumbLink).toBeVisible();

		await expect(
			page.getByRole('heading', {
				level: 2,
				name: `${insightTypeInput.name} from ${insightTypeInput.pageURLs.length} pages`,
			})
		).toBeVisible();

		await expect(insightDetailPage.descriptionSectionTitle).toBeVisible();
		await expect(
			page.getByText(insightTypeInput.description)
		).toBeVisible();

		await expect(insightDetailPage.suggestionSectionTitle).toBeVisible();
		await expect(page.getByText(insightTypeInput.fixHint)).toBeVisible();
	}
);

test(
	'Renders the affected pages table with one row per scan insight',
	{tag: '@LPD-91182'},
	async ({insightDetailPage, onPagePage}) => {
		await onPagePage.selectInsight(insightTypeInput.name);

		await expect(insightDetailPage.affectedPagesHeading).toContainText(
			`(${insightTypeInput.pageURLs.length})`
		);

		for (const pageInput of insightTypeInput.pageURLs) {
			const row = insightDetailPage.getAffectedPageRow(pageInput.title);

			await expect(row).toBeVisible();
			await expect(row).toContainText(pageInput.author);
			await expect(row).toContainText(pageInput.type);
			await expect(row).toContainText(pageInput.pageURL);
		}

		await expect(insightDetailPage.getTitleHeader()).toBeVisible();
		await expect(insightDetailPage.getTypeHeader()).toBeVisible();
	}
);

test(
	'Navigates back to the On-Page screen from the breadcrumb',
	{tag: '@LPD-91182'},
	async ({insightDetailPage, onPagePage}) => {
		await onPagePage.selectInsight(insightTypeInput.name);

		await expect(insightDetailPage.onPageBreadcrumbLink).toBeVisible();

		await insightDetailPage.onPageBreadcrumbLink.click();

		await expect(onPagePage.onPageHeading).toBeVisible();
	}
);
