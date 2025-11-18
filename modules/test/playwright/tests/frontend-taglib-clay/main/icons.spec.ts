/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../../utils/getRandomString';
import getPageDefinition from '../../layout-content-page-editor-web/main/utils/getPageDefinition';
import getWidgetDefinition from '../../layout-content-page-editor-web/main/utils/getWidgetDefinition';

const EXPECTED_BUTTON_FLAG_CODE = 'en-us';
const EXPECTED_MENU_FLAG_CODES = [
	'ar-sa',
	'ca-es',
	'zh-cn',
	'nl-nl',
	'fi-fi',
	'fr-fr',
	'de-de',
	'hu-hu',
	'ja-jp',
	'pt-br',
	'es-es',
	'sv-se',
];

async function expectFlagToBeDefined(locator: Locator, flagCode: string) {
	const flag = locator.locator(`svg.lexicon-icon.lexicon-icon-${flagCode}`);

	await expect(flag).toBeVisible();
	await expect(flag).toHaveCount(1);
}

const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	loginTest(),
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	})
);

test(
	'Verify that flag icons are present when clicking on localization button',
	{tag: '@LPD-66978'},
	async ({apiHelpers, page, site}) => {
		await test.step('Create a page with Language Selector widget', async () => {
			const widgetDefinition = getWidgetDefinition({
				id: getRandomString(),
				widgetName:
					'com_liferay_site_navigation_language_web_portlet_SiteNavigationLanguagePortlet',
			});

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([widgetDefinition]),
				siteId: site.id,
				title: getRandomString(),
			});

			await page.goto(
				`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
			);
		});

		await test.step('Click on the Language Selector widget', async () => {
			const menuName = 'Select a Language';

			const trigger = page.getByRole('button', {name: menuName});

			const target = page
				.getByRole('menu', {name: menuName})
				.and(
					page.locator(
						`[aria-labelledby=${await trigger.getAttribute('id')}]`
					)
				);

			await clickAndExpectToBeVisible({target, trigger});

			await expectFlagToBeDefined(trigger, EXPECTED_BUTTON_FLAG_CODE);

			for (const flagCode of EXPECTED_MENU_FLAG_CODES) {
				await expectFlagToBeDefined(target, flagCode);
			}
		});
	}
);
