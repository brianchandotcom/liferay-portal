/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {themesTest} from './extensions/themesTest';

const themeScopedTest = mergeTests(
	themesTest,
	loginTest(),
	featureFlagsTest({
		'LPD-30204': {enabled: true},
		'LPS-178052': {enabled: true},
	})
);

themeScopedTest.describe(
	'A theme can be applied to a single page',
	{tag: '@LPD-70288'},
	() => {
		const classicPrimaryColor = 'rgb(11, 95, 255)';
		const dialectPrimaryColor = 'rgb(89, 36, 235)';

		themeScopedTest(
			'Verifies it displays Classic primary color',
			async ({pageFixture}) => {
				const {fragment, page} =
					await pageFixture.createPageWithPrimaryBackgroundFragment();

				await pageFixture.goToPageEditor(page);

				expect(fragment).toBeVisible();
				expect(fragment).toHaveCSS(
					'background-color',
					classicPrimaryColor
				);
			}
		);

		themeScopedTest(
			'Verifies it displays Dialect primary color',
			async ({pageFixture, themeFixture}) => {
				const {fragment, page, pageName} =
					await pageFixture.createPageWithPrimaryBackgroundFragment();

				await themeFixture.changePageThemeToDialect(pageName);
				await pageFixture.goToPageEditor(page);

				expect(fragment).toBeVisible();
				expect(fragment).toHaveCSS(
					'background-color',
					dialectPrimaryColor
				);
			}
		);
	}
);
