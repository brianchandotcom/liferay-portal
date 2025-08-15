/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import {waitForAlert} from '../../../utils/waitForAlert';
import {cmsPagesTest} from './fixtures/cmsPagesTest';

const test = mergeTests(
	cmsPagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-17564': {enabled: true},
		'LPD-34594': {enabled: true},
		'LPD-53981': {enabled: true},
		'LPS-179669': {enabled: true},
	}),
	loginTest()
);

test(
	'Can delete a single content from Recycle Bin',
	{tag: '@LPD-55831'},
	async ({contentsPage, page, recycleBinPage}) => {
		const contentName = getRandomString();
		const friendlyUrl = getRandomString();

		await test.step('Create new content', async () => {
			await contentsPage.goto();

			await contentsPage.createContent('Knowledge Base');

			await page.getByLabel('Title').fill(contentName);

			await page.getByLabel('Friendly URL').fill(friendlyUrl);

			await contentsPage.saveContent();
		});

		await test.step('Delete the created content so it goes into the Recycle Bin', async () => {
			await contentsPage.deleteContent(contentName);
		});

		await test.step('Go to the Recycle Bin and delete the content permanently', async () => {
			await recycleBinPage.goto();

			await page.getByRole('button', {name: 'Delete'}).click();

			await expect(
				recycleBinPage.deleteItemConfirmationText
			).toBeVisible();

			await recycleBinPage.deleteButton.click();

			await waitForAlert(
				page,
				`Success:${contentName} has been permanently deleted.`
			);
		});
	}
);
