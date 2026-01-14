/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, expect, mergeTests} from '@playwright/test';

import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {styleBookPageTest} from '../../../fixtures/styleBookPageTest';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../../utils/getRandomString';
import {waitForAlert} from '../../../utils/waitForAlert';

const test = mergeTests(isolatedSiteTest, loginTest(), styleBookPageTest);

test.beforeEach(async ({site, styleBooksPage}) => {
	await styleBooksPage.goto(site.friendlyUrlPath);
});

async function expectStyleBookToBeVisibleAndEditable({
	card,
	editable,
}: {
	card: Locator;
	editable: boolean;
}) {
	await expect(card).toBeVisible();

	await expect(card.getByRole('checkbox')).toBeVisible({visible: editable});
	await expect(card.locator('a')).toBeVisible({visible: editable});
	await expect(card.getByLabel('More actions')).toBeVisible({
		visible: editable,
	});
}

test(
	'The default style book is visible, but not editable',
	{tag: '@LPD-66976'},
	async ({styleBooksPage}) => {
		await test.step('Assert that the default style book is visible, but not editable', async () => {
			await expectStyleBookToBeVisibleAndEditable({
				card: styleBooksPage.getStyleBookCard(
					'Styles from Classic Theme'
				),
				editable: false,
			});
		});
	}
);

test(
	'A new style book is visible and editable',
	{tag: '@LPD-66976'},
	async ({site, styleBooksPage}) => {
		const styleBookName = getRandomString();

		await test.step('Create a new style book', async () => {
			await styleBooksPage.create(styleBookName);

			await styleBooksPage.goto(site.friendlyUrlPath);
		});

		await test.step('Assert that the new style book is visible and editable', async () => {
			await expectStyleBookToBeVisibleAndEditable({
				card: styleBooksPage.getStyleBookCard(styleBookName),
				editable: true,
			});
		});
	}
);

test(
	'A new style book can be used as default style book',
	{tag: '@LPD-66976'},
	async ({site, styleBooksPage}) => {
		const styleBookName = getRandomString();

		await test.step('Create a new style book', async () => {
			await styleBooksPage.create(styleBookName);

			await styleBooksPage.goto(site.friendlyUrlPath);
		});

		await test.step('Set the new style book as default', async () => {
			const sticker = styleBooksPage
				.getStyleBookCard(styleBookName)
				.getByTitle('Marked as Default for Classic Theme');

			await expect(sticker).toBeHidden();

			await styleBooksPage.markAsDefault(styleBookName);

			await expect(sticker).toBeVisible();
		});
	}
);

test(
	'A warning message appears when deleting a style book',
	{tag: '@LPD-66976'},
	async ({page, site, styleBooksPage}) => {
		const styleBookName = getRandomString();

		await test.step('Create a new style book', async () => {
			await styleBooksPage.create(styleBookName);

			await styleBooksPage.goto(site.friendlyUrlPath);
		});

		const card = styleBooksPage.getStyleBookCard(styleBookName);

		async function expectWarningAndClickOnAction(name: string) {
			await clickAndExpectToBeVisible({
				autoClick: true,
				target: page.getByRole('menuitem', {name: 'Delete'}),
				trigger: card.getByLabel('More actions'),
			});

			const dialog = page.getByRole('dialog');

			await expect(
				dialog.getByRole('heading', {name: 'Delete Style Book'})
			).toBeVisible();

			await expect(
				dialog.getByText(
					"Deleting a style book is an action impossible to revert. All Style Book tokens and values will be removed and it will not be possible to recover it. Watch out for a critical impact on the site's look and feel."
				)
			).toBeVisible();

			await dialog.getByRole('button', {name}).click();
		}

		await test.step('The warning message appears before deleting the style book and can be dismissed', async () => {
			await expectWarningAndClickOnAction('Cancel');

			await expect(card).toBeVisible();
		});

		await test.step('The style book is deleted when confirming the action', async () => {
			await expectWarningAndClickOnAction('Delete');

			await waitForAlert(page);

			await expect(card).toBeHidden();
		});
	}
);
