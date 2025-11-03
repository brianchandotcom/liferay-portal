/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {CLASSIC_PRIMARY_COLOR, DIALECT_PRIMARY_COLOR} from './constants';
import {themesTest} from './extensions/themesTest';

const test = mergeTests(
	themesTest,
	loginTest(),
	featureFlagsTest({
		'LPD-30204': {enabled: true},
		'LPS-178052': {enabled: true},
	})
);

test.describe(
	'A theme can be applied to a single page',
	{tag: '@LPD-70288'},
	() => {
		test('Verifies it displays Classic primary color', async ({
			pageFixture,
		}) => {
			const {fragment, page} =
				await pageFixture.createPageWithPrimaryBackgroundFragment();

			await pageFixture.goToPageEditor(page);

			expect(fragment).toBeVisible();
			expect(fragment).toHaveCSS(
				'background-color',
				CLASSIC_PRIMARY_COLOR
			);
		});

		test('Verifies it displays Dialect primary color', async ({
			pageFixture,
			themeFixture,
		}) => {
			const {fragment, page, pageName} =
				await pageFixture.createPageWithPrimaryBackgroundFragment();

			await themeFixture.changePageThemeToDialect(pageName);
			await pageFixture.goToPageEditor(page);

			expect(fragment).toBeVisible();
			expect(fragment).toHaveCSS(
				'background-color',
				DIALECT_PRIMARY_COLOR
			);
		});
	}
);
