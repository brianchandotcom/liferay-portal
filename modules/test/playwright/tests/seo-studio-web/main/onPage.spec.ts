/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';
import {seoStudioPagesTest} from './fixtures/seoStudioPagesTest';
import {seoStudioSiteTest} from './fixtures/seoStudioSiteTest';

const test = mergeTests(
	loginTest(),
	dataApiHelpersTest,
	seoStudioPagesTest,
	seoStudioSiteTest
);

test(
	'Renders the "no scans yet" empty state on the On-Page screen',
	{tag: '@LPD-91406'},
	async ({onPagePage, seoStudioSite}) => {
		await onPagePage.goto(seoStudioSite.friendlyUrlPath);

		await expect(onPagePage.onPageHeading).toBeVisible();

		await expect(onPagePage.lastScanLabel).toContainText(
			'Last Scan: Never'
		);

		await expect(onPagePage.runScanNowButton).toBeVisible();

		await onPagePage.emptyStateIsVisible('no-scans');
	}
);

test(
	'Renders the "no insights found" empty state when the latest scan has no insights',
	{tag: '@LPD-91406'},
	async ({apiHelpers, onPagePage, seoStudioSite}) => {
		const scan = await apiHelpers.seoStudio.createScan('crawler');

		try {
			await onPagePage.goto(seoStudioSite.friendlyUrlPath);

			await onPagePage.emptyStateIsVisible('no-insights');
		}
		finally {
			await scan.teardown();
		}
	}
);

test(
	'Renders the Insights table rows when the latest scan has insights',
	{tag: '@LPD-91406'},
	async ({apiHelpers, onPagePage, seoStudioSite}) => {
		const insightTypeInputs = [
			{
				category: 'metadata',
				name: 'missingMetaDescription',
				pageURLs: [
					'https://example.com/a',
					'https://example.com/b',
					'https://example.com/c',
				],
				severity: '3',
			},
			{
				category: 'images',
				name: 'missingAltText',
				pageURLs: ['https://example.com/d', 'https://example.com/e'],
				severity: '2',
			},
			{
				category: 'urls',
				name: 'brokenInternalLink',
				pageURLs: [
					'https://example.com/f',
					'https://example.com/g',
					'https://example.com/h',
					'https://example.com/i',
				],
				severity: '3',
			},
		];

		const scan = await apiHelpers.seoStudio.createScan('crawler');

		try {
			await apiHelpers.seoStudio.createInsights(scan, insightTypeInputs);

			await onPagePage.goto(seoStudioSite.friendlyUrlPath);

			for (const insightTypeInput of insightTypeInputs) {
				const row = onPagePage.getInsightRow(insightTypeInput.name);

				await expect(row).toBeVisible();

				await expect(row).toContainText(
					String(insightTypeInput.pageURLs.length)
				);
			}
		}
		finally {
			await scan.teardown();
		}
	}
);
