/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {pagesAdminPagesTest} from '../../../fixtures/pagesAdminPagesTest';
import {styleBookPageTest} from '../../../fixtures/styleBookPageTest';
import {doAndGoBack} from '../../../utils/doAndGoBack';
import fillAndClickOutside from '../../../utils/fillAndClickOutside';
import getRandomString from '../../../utils/getRandomString';

const STYLE_BOOK_NAME = getRandomString();

const TEST_COLOR = 'rgb(255, 0, 0)';

async function expectStyleBookToBeSelected({
	page,
	pageFriendlyUrlPath,
	siteFriendlyUrlPath,
}: {
	page: Page;
	pageFriendlyUrlPath: string;
	siteFriendlyUrlPath: string;
}) {
	await doAndGoBack(page, async () => {
		await page.goto(`/web${siteFriendlyUrlPath}${pageFriendlyUrlPath}`);

		await expect(page.locator('footer')).toHaveCSS(
			'background-color',
			TEST_COLOR
		);
	});
}

const test = mergeTests(
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest,
	pagesAdminPagesTest,
	styleBookPageTest
);

test.beforeEach(async ({page, site, styleBooksPage}) => {
	await styleBooksPage.goto(site.friendlyUrlPath);

	await styleBooksPage.create(STYLE_BOOK_NAME);

	await fillAndClickOutside(
		page,
		page.getByLabel('Brand Color 4', {exact: true}).getByRole('textbox'),
		TEST_COLOR
	);

	await styleBooksPage.waitForAutoSave();

	await styleBooksPage.publish();
});

test(
	'The selected style book does not change when configuring the page',
	{tag: '@LPD-66976'},
	async ({page, pageEditorPage, pagesAdminPage, site}) => {
		const originalPageName = getRandomString();

		await test.step('Create a content page and select the created style book', async () => {
			await pagesAdminPage.goto(site.friendlyUrlPath);

			await pagesAdminPage.createNewPage({
				draft: true,
				name: originalPageName,
			});

			await pageEditorPage.selectStyleBook(STYLE_BOOK_NAME);

			await pageEditorPage.publishPage();

			await expectStyleBookToBeSelected({
				page,
				pageFriendlyUrlPath: `/${originalPageName}`,
				siteFriendlyUrlPath: site.friendlyUrlPath,
			});
		});

		const modifiedPageName = getRandomString();

		const modifiedPageFriendlyUrlPath = `/${modifiedPageName}`;

		await test.step('Modify the page configuration', async () => {
			await pagesAdminPage.clickOnAction('Configure', originalPageName);

			await page
				.getByRole('textbox', {name: 'Name'})
				.fill(modifiedPageName);

			await page
				.getByRole('textbox', {name: 'Friendly URL'})
				.fill(modifiedPageFriendlyUrlPath);

			await pagesAdminPage.saveConfiguration();
		});

		await test.step('Assert the style book is still selected', async () => {
			await expectStyleBookToBeSelected({
				page,
				pageFriendlyUrlPath: modifiedPageFriendlyUrlPath,
				siteFriendlyUrlPath: site.friendlyUrlPath,
			});
		});
	}
);
