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
import {pagesAdminPagesTest} from '../../../fixtures/pagesAdminPagesTest';
import getRandomString from '../../../utils/getRandomString';
import getPageDefinition from '../../layout-content-page-editor-web/main/utils/getPageDefinition';

const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	pageEditorPagesTest,
	pagesAdminPagesTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	loginTest()
);

const DEFAULT_COLOR = 'rgb(255, 255, 255)';
const OVERRIDE_COLOR = 'rgb(0, 255, 0)';
const ELEMENT_SELECTOR = '.cadmin .control-menu-level-1-dark';

test(
	'Assert cadmin component precedence over CSS from the applied theme',
	{tag: '@LPD-66827'},
	async ({apiHelpers, page, pagesAdminPage, site}) => {
		async function addCustomCSSToPage(pageName: string, css: string) {
			await pagesAdminPage.goto(site.friendlyUrlPath);

			await pagesAdminPage.addCustomCSS(pageName, css);

			await pagesAdminPage.goto(site.friendlyUrlPath);

			await pagesAdminPage.editPage(pageName);
		}

		const {pageName} = await test.step('Create a new page', async () => {
			const pageName = getRandomString();
			await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition(),
				siteId: site.id,
				title: pageName,
			});

			return {pageName};
		});

		await test.step('Add smaller precedence CSS rules to the theme', async () => {
			const lowerPrecedenceCSS = `${ELEMENT_SELECTOR} { background-color: ${OVERRIDE_COLOR}; }`;

			await addCustomCSSToPage(pageName, lowerPrecedenceCSS);

			await expect(page.locator(ELEMENT_SELECTOR)).toHaveCSS(
				'background-color',
				DEFAULT_COLOR
			);
		});

		await test.step('Add higher precedence CSS rules to the theme', async () => {
			const higherPrecedenceCSS = `${ELEMENT_SELECTOR} { background-color: ${OVERRIDE_COLOR} !important; }`;

			await addCustomCSSToPage(pageName, higherPrecedenceCSS);

			await expect(page.locator(ELEMENT_SELECTOR)).toHaveCSS(
				'background-color',
				OVERRIDE_COLOR
			);
		});
	}
);
