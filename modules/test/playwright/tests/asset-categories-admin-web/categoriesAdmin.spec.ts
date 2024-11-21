/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {waitForAlert} from '../../utils/waitForAlert';
import {assetCategoriesPagesTest} from './fixtures/assetCategoriesAdminPagesTest';

const test = mergeTests(
	assetCategoriesPagesTest,
	isolatedSiteTest,
	loginTest()
);

test('Add, edit and delete a vocabulary', async ({
	assetCategoriesAdminPage,
	page,
	site,
	vocabulariesEditPage,
}) => {
	await assetCategoriesAdminPage.goto(site.friendlyUrlPath);

	const vocabularyName = 'Vocabulary 1';

	await test.step('Add a vocabulary', async () => {
		await assetCategoriesAdminPage.newVocabularyButton.click();

		await vocabulariesEditPage.add(vocabularyName);

		await expect(
			page.getByRole('heading', {name: vocabularyName})
		).toBeVisible();
	});

	const newVocabularyName = 'Vocabulary Changed';

	await test.step('Edit the vocabulary', async () => {
		await vocabulariesEditPage.goto(vocabularyName);

		await vocabulariesEditPage.fillName(newVocabularyName);

		await vocabulariesEditPage.saveButton.click();

		await waitForAlert(page);

		await expect(
			page.getByRole('heading', {name: newVocabularyName})
		).toBeVisible();
	});

	await test.step('Delete the vocabulary', async () => {
		await vocabulariesEditPage.delete(newVocabularyName);

		await waitForAlert(page);

		await expect(
			page.getByRole('heading', {name: newVocabularyName})
		).not.toBeVisible();
	});
});
