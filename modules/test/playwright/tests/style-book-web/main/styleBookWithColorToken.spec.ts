/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

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

const INVALID_TOKEN_MESSAGE = 'Tokens cannot reference itself.';
const STYLEBOOK_BRAND_COLOR_1 = 'Brand Color 1';
const STYLEBOOK_BRAND_COLOR_2 = 'Brand Color 2';
const STYLEBOOK_BRAND_COLORS_SECTION = 'Brand Colors';

async function waitForInvalidTokenMessage(page: Page, message: string) {
	await expect(page.getByText(message)).toBeVisible();
}

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
		const STYLEBOOK_CATEGORY_VALUE = '#00CCBB';

		const styleBookName = getRandomString();

		await test.step('Create a style book', async () => {
			await styleBooksPage.goto(site.friendlyUrlPath);

			await styleBooksPage.create(styleBookName);
		});

		await test.step('Change the Brand Color 1 to an invalid color token', async () => {
			await styleBooksPage.updateTokenInputColor(
				STYLEBOOK_BRAND_COLOR_1,
				STYLEBOOK_BRAND_COLOR_1,
				STYLEBOOK_BRAND_COLORS_SECTION
			);

			await waitForInvalidTokenMessage(page, INVALID_TOKEN_MESSAGE);
		});

		await test.step('Cancel the publish process', async () => {
			await styleBooksPage.publish('Cancel');
		});

		await test.step('View the invalid color token is still shown in Brand Color 1 field', async () => {
			await waitForInvalidTokenMessage(page, INVALID_TOKEN_MESSAGE);
		});

		await test.step('Insert an valid color token in Brand Color 1 field then publish', async () => {
			await styleBooksPage.updateTokenInputColor(
				STYLEBOOK_BRAND_COLOR_1,
				STYLEBOOK_CATEGORY_VALUE,
				STYLEBOOK_BRAND_COLORS_SECTION
			);

			await styleBooksPage.waitForAutoSave();

			await styleBooksPage.publish();
		});

		await test.step('View the Brand Color 1 is new color token', async () => {
			await styleBooksPage.edit(styleBookName);

			await styleBooksPage.assertTokenInputValue(
				STYLEBOOK_BRAND_COLOR_1,
				STYLEBOOK_CATEGORY_VALUE
			);
		});
	}
);

test(
	'Check that the user cannot refer two tokens mutually.',
	{tag: '@LPS-141568'},
	async ({page, site, styleBooksPage}) => {
		const styleBookName = getRandomString();

		await test.step('Create a style book', async () => {
			await styleBooksPage.goto(site.friendlyUrlPath);

			await styleBooksPage.create(styleBookName);
		});

		await test.step('Change the Brand Color 1 to Brand Color 2', async () => {
			await styleBooksPage.updateTokenInputColor(
				STYLEBOOK_BRAND_COLOR_2,
				STYLEBOOK_BRAND_COLOR_1,
				STYLEBOOK_BRAND_COLORS_SECTION
			);

			await styleBooksPage.waitForAutoSave();
		});

		await test.step('Assert that is not possible to refer Brand Color 2 to Brand Color 1', async () => {
			await styleBooksPage.updateTokenInputColor(
				STYLEBOOK_BRAND_COLOR_1,
				STYLEBOOK_BRAND_COLOR_2,
				STYLEBOOK_BRAND_COLORS_SECTION
			);

			await waitForInvalidTokenMessage(
				page,
				'Tokens cannot be mutually referenced.'
			);
		});
	}
);

test(
	'Check if the user could continue publish process in style book editor if link invalid color token. The tokens groups should be collapsed by default except the first one.',
	{tag: '@LPS-145650'},
	async ({page, site, styleBooksPage}) => {
		const styleBookName = getRandomString();

		async function assertSectionIsExpanded(
			section: string,
			expanded: boolean = true
		) {
			await expect(
				page.locator('.panel').getByRole('button', {name: section})
			).toHaveAttribute('aria-expanded', String(expanded));
		}

		await test.step('Create a style book', async () => {
			await styleBooksPage.goto(site.friendlyUrlPath);

			await styleBooksPage.create(styleBookName);
		});

		await test.step('View only the first tokens group is expanded by default', async () => {
			await assertSectionIsExpanded(STYLEBOOK_BRAND_COLORS_SECTION);
			await assertSectionIsExpanded('Grays', false);
			await assertSectionIsExpanded('Theme Colors', false);
		});

		await test.step('Change the Brand Color 1 to an invalid token', async () => {
			await styleBooksPage.updateTokenInputColor(
				STYLEBOOK_BRAND_COLOR_1,
				STYLEBOOK_BRAND_COLOR_1,
				STYLEBOOK_BRAND_COLORS_SECTION
			);

			await waitForInvalidTokenMessage(page, INVALID_TOKEN_MESSAGE);
		});

		await test.step('Continue the publish process', async () => {
			await styleBooksPage.publish('Continue');
		});

		await test.step('View the Brand Color 1 is new color token', async () => {
			await styleBooksPage.edit(styleBookName);

			await styleBooksPage.assertTokenInputValue(
				STYLEBOOK_BRAND_COLOR_1,
				'#0B5FFF'
			);
		});
	}
);
