/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import getRandomString from '../../../utils/getRandomString';
import getPageDefinition from '../../layout-content-page-editor-web/main/utils/getPageDefinition';

const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	pageEditorPagesTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	loginTest()
);

test(
	"Verifies admin component displays it's default theme after applying a theme and that CSS can be overridden",
	{tag: '@LPS-122068'},
	async ({apiHelpers, page, pageEditorPage, site}) => {
		const elements = [
			{
				cadminColor: 'rgba(0, 0, 0, 0)',
				regularColor: 'rgba(255, 255, 255, 0.8)',
				selector: '.cadmin-test-wrapper > .cadmin-test-unstyled',
			},
			{
				cadminColor: 'rgba(0, 0, 0, 0)',
				regularColor: 'rgba(0, 0, 255, 0.8)',
				selector: '.cadmin-test-wrapper > .cadmin-test-styled',
			},
			{
				cadminColor: 'rgba(0, 0, 255, 0.8)',
				regularColor: 'rgba(0, 0, 255, 0.8)',
				selector: '.cadmin-test-wrapper > .cadmin-test-styled-override',
			},
		];

		await test.step('Create a new site with test widget', async () => {
			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition(),
				siteId: site.id,
				title: getRandomString(),
			});

			await pageEditorPage.goto(layout, site.friendlyUrlPath);
			await pageEditorPage.addWidget('Sample', 'CSS Cadmin Sample');
			await pageEditorPage.publishPage();

			await page.goto(
				`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
			);
		});

		await test.step('Assert cadmin component default theme background color', async () => {
			for (const {cadminColor, regularColor, selector} of elements) {
				const regularEl = page.locator(`${selector}:not(.cadmin)`);

				expect(regularEl).toBeVisible();
				expect(regularEl).toHaveCSS('background-color', regularColor);

				const cadminEl = page.locator(`${selector}.cadmin`);

				expect(cadminEl).toBeVisible();
				expect(cadminEl).toHaveCSS('background-color', cadminColor);
			}
		});
	}
);
