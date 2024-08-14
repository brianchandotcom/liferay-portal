/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {changeTrackingPagesTest} from '../../fixtures/changeTrackingPagesTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';
import {journalPagesTest} from '../journal-web/fixtures/journalPagesTest';

export const test = mergeTests(
	apiHelpersTest,
	changeTrackingPagesTest,
	featureFlagsTest({
		'LPD-20131': true,
	}),
	journalPagesTest,
	loginTest()
);

test('LPD-29562 Assert popover only appears when context is changed', async ({
	apiHelpers,
	journalEditArticlePage,
	page,
}) => {
	await journalEditArticlePage.goto();

	await expect(
		page.getByText('Keep working in this publication?', {exact: true})
	).toBeHidden();

	const site = await apiHelpers.headlessSite.createSite({
		name: getRandomString(),
	});

	await journalEditArticlePage.goto({siteUrl: site.friendlyUrlPath});

	await expect(
		page.getByText('Keep working in this publication?', {exact: true})
	).toBeVisible();

	await expect(
		page.getByText(
			'You just switched contexts. Do you want to keep working in this publication?',
			{exact: true}
		)
	).toBeVisible();
});
