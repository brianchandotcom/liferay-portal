/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {CLASSIC_PRIMARY_COLOR, DIALECT_PRIMARY_COLOR} from './constants';
import {frontendThemePagesTest} from './fixtures/frontendThemePagesTest';

const test = mergeTests(
	frontendThemePagesTest,
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
			pageHelper,
		}) => {
			const {fragment, page} =
				await pageHelper.createPageWithPrimaryBackgroundFragment();

			await pageHelper.goToPageEditor(page);

			await pageHelper.expectToHaveBackgroundColor(
				fragment,
				CLASSIC_PRIMARY_COLOR
			);
		});

		test('Verifies it displays Dialect primary color', async ({
			pageHelper,
			themeHelper,
		}) => {
			const {fragment, page, pageName} =
				await pageHelper.createPageWithPrimaryBackgroundFragment();

			await themeHelper.changePageThemeToDialect(pageName);

			await pageHelper.goToPageEditor(page);

			await pageHelper.expectToHaveBackgroundColor(
				fragment,
				DIALECT_PRIMARY_COLOR
			);
		});
	}
);
