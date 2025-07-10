/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../../../fixtures/loginTest';
import getRandomString from '../../../../../utils/getRandomString';
import {fdsSamplePageTest} from '../../fixtures/fdsSamplePageTest';

const test = mergeTests(
	apiHelpersTest,
	fdsSamplePageTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest()
);

test.beforeEach(async ({fdsSamplePage, page, site}) => {
	await fdsSamplePage.setupFDSSampleWidget({site});

	await fdsSamplePage.selectTab('Advanced');

	await expect(
		page.getByText('This is a description for sample 1.')
	).toBeVisible();
});

test('Check Search clear button', async ({fdsSamplePage}) => {
	const searchInput = fdsSamplePage.managementToolbar.searchInput;
	const searchValue = getRandomString();

	await test.step('Fill search input', async () => {
		await searchInput.fill(searchValue);

		await expect(searchInput).toHaveValue(searchValue);
	});

	await test.step('Clean search text by the input clear button', async () => {
		const searchInputBox = await searchInput.boundingBox();

		await searchInput.click({
			position: {
				x: searchInputBox.width - 10,
				y: searchInputBox.height / 2,
			},
		});
	});

	await test.step('Check that input is empty', async () => {
		await expect(searchInput).toHaveValue('');
	});
});
