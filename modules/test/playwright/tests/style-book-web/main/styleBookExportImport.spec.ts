/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';
import path from 'path';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {styleBookPageTest} from '../../../fixtures/styleBookPageTest';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../../utils/getRandomString';
import {getTempDir} from '../../../utils/temp';

const test = mergeTests(
	apiHelpersTest,
	loginTest(),
	isolatedSiteTest,
	styleBookPageTest
);

test(
	'Assert that style books can be exported and imported.',
	{tag: '@LPS-134860'},
	async ({page, site, styleBooksHelper, styleBooksPage}) => {
		const styleBookName = getRandomString();
		const STYLEBOOK_CATEGORY = 'Font Family Base';
		const STYLEBOOK_CATEGORY_VALUE = 'times';
		const STYLEBOOK_TOKEN_CATEGORY = 'Typography';
		const STYLEBOOK_SECTION = 'Font Family';

		await test.step('Create a style book with custom token value', async () => {
			await styleBooksPage.goto();

			await styleBooksPage.create(styleBookName);

			await styleBooksPage.selectTokenCategory(STYLEBOOK_TOKEN_CATEGORY);

			await styleBooksPage.updateTokenInput(
				STYLEBOOK_CATEGORY,
				STYLEBOOK_CATEGORY_VALUE,
				STYLEBOOK_SECTION
			);

			await styleBooksPage.waitForAutoSave();

			await styleBooksPage.publish();
		});

		const {fileName, filePath} =
			await test.step('Export the style book', async () => {
				const downloadPromise = page.waitForEvent('download');

				await clickAndExpectToBeVisible({
					autoClick: true,
					target: page.getByRole('menuitem', {name: 'Export'}),
					trigger: page.getByRole('button', {name: 'Actions'}),
				});

				const download = await downloadPromise;

				const filePath = path.join(
					getTempDir(),
					download.suggestedFilename()
				);

				await download.saveAs(filePath);

				return {fileName: download.suggestedFilename(), filePath};
			});

		await test.step('Import the style book into a new site', async () => {
			await styleBooksPage.goto(site.friendlyUrlPath);

			await styleBooksPage.importStyleBookFile(fileName, filePath);
		});

		await test.step('Check that the new token value is being applied', async () => {
			await styleBooksPage.goto(site.friendlyUrlPath);

			await clickAndExpectToBeVisible({
				autoClick: true,
				target: page.getByRole('menuitem', {name: 'Edit'}),
				trigger: page.getByRole('button', {name: 'Actions'}),
			});

			await styleBooksPage.selectTokenCategory(STYLEBOOK_TOKEN_CATEGORY);

			await styleBooksHelper.assertTokenInputValue({
				label: STYLEBOOK_CATEGORY,
				section: STYLEBOOK_SECTION,
				value: STYLEBOOK_CATEGORY_VALUE,
			});
		});
	}
);
