/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, expect, mergeTests} from '@playwright/test';

import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {styleBookPageTest} from '../../../fixtures/styleBookPageTest';
import getRandomString from '../../../utils/getRandomString';

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
