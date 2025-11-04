/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import getPageDefinition from '../../layout-content-page-editor-web/main/utils/getPageDefinition';
import {frontendCssTest} from './extensions/frontendCssTest';

const test = mergeTests(
	apiHelpersTest,
	frontendCssTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	loginTest()
);

const DEFAULT_COLOR = 'rgb(48, 49, 63)';
const OVERRIDE_COLOR = 'rgb(0, 255, 0)';
const ELEMENT_SELECTOR = '.cadmin .control-menu-level-1-dark';

const SMALLER_PRECEDENCE_CSS = `${ELEMENT_SELECTOR} { background-color: ${OVERRIDE_COLOR}; }`;
const HIGHER_PRECEDENCE_CSS = `${ELEMENT_SELECTOR} { background-color: ${OVERRIDE_COLOR} !important; }`;

test(
	'Assert cadmin component precedence over CSS from the applied theme',
	{tag: '@LPD-66827'},
	async ({apiHelpers, designFixture, page, site}) => {
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
			await designFixture.addCustomCSS(pageName, SMALLER_PRECEDENCE_CSS);

			await expect(page.locator(ELEMENT_SELECTOR)).toHaveCSS(
				'background-color',
				DEFAULT_COLOR
			);
		});

		await test.step('Add higher precedence CSS rules to the theme', async () => {
			await designFixture.addCustomCSS(pageName, HIGHER_PRECEDENCE_CSS);

			await expect(page.locator(ELEMENT_SELECTOR)).toHaveCSS(
				'background-color',
				OVERRIDE_COLOR
			);
		});
	}
);
