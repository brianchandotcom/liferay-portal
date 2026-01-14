/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {pagesAdminPagesTest} from '../../../fixtures/pagesAdminPagesTest';
import {styleBookPageTest} from '../../../fixtures/styleBookPageTest';
import getRandomString from '../../../utils/getRandomString';

const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest,
	pagesAdminPagesTest,
	styleBookPageTest
);

test(
	'Assert the reference will be autocompleted as 6 digit characters, if the user fill out 3 digit characters when clicking on the tab, intro or outside the output.',
	{tag: '@LPS-141568'},
	async ({page, pageEditorPage, pagesAdminPage, site}) => {
		const pageName = getRandomString();
		const backgroundColorSection = page.getByLabel('Background Color', {
			exact: true,
		});
		const backgroundColorInput =
			backgroundColorSection.getByRole('textbox');

		await test.step('Create a content page and add a container', async () => {
			await pagesAdminPage.goto(site.friendlyUrlPath);

			await pagesAdminPage.createNewPage({
				draft: true,
				name: pageName,
			});

			await pageEditorPage.addFragment('Layout Elements', 'Container');
		});

		await test.step('Change the background color and type a 3 digits color reference', async () => {
			await pageEditorPage.goToConfigurationTab('Styles');

			await backgroundColorInput.fill('#03C');
		});

		await test.step('Check if the color reference is autocompleted after trigger auto save', async () => {
			await pageEditorPage.goToConfigurationTab('Styles');

			await pageEditorPage.waitForChangesSaved();

			await expect(backgroundColorInput).toHaveValue('#0033CC');
		});

		await test.step('Assert the background color of the container', async () => {
			await expect(
				pageEditorPage.getFragment(
					await pageEditorPage.getFragmentId('Container')
				)
			).toHaveCSS('background-color', 'rgb(0, 51, 204)');
		});
	}
);

test(
	'Check if the user could cancel publish process in style book editor if link invalid color token.',
	{tag: '@LPS-145650'},
	async ({page, site, styleBooksPage}) => {
		const STYLEBOOK_CATEGORY = 'Brand Color 1';
		const STYLEBOOK_CATEGORY_VALUE = '#00CCBB';
		const STYLEBOOK_SECTION = 'Brand Colors';

		const styleBookName = getRandomString();

		async function waitForInvalidTokenMessage() {
			await expect(
				page.getByText('Tokens cannot reference itself.')
			).toBeVisible();
		}

		await test.step('Create a style book', async () => {
			await styleBooksPage.goto(site.friendlyUrlPath);

			await styleBooksPage.create(styleBookName);
		});

		await test.step('Change the Brand Color 1 to an invalid color token', async () => {
			await styleBooksPage.updateTokenInputColor(
				STYLEBOOK_CATEGORY,
				STYLEBOOK_CATEGORY,
				STYLEBOOK_SECTION
			);

			await waitForInvalidTokenMessage();
		});

		await test.step('Cancel the publish process', async () => {
			await styleBooksPage.publish(false);
		});

		await test.step('View the invalid color token is still shown in Brand Color 1 field', async () => {
			await waitForInvalidTokenMessage();
		});

		await test.step('Insert an valid color token in Brand Color 1 field then publish', async () => {
			await styleBooksPage.updateTokenInputColor(
				STYLEBOOK_CATEGORY,
				STYLEBOOK_CATEGORY_VALUE,
				STYLEBOOK_SECTION
			);

			await styleBooksPage.waitForAutoSave();

			await styleBooksPage.publish();
		});

		await test.step('View the Brand Color 1 is new color token', async () => {
			await styleBooksPage.edit(styleBookName);

			await styleBooksPage.assertTokenInputValue(
				STYLEBOOK_CATEGORY,
				STYLEBOOK_CATEGORY_VALUE
			);
		});
	}
);
