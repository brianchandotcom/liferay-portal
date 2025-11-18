/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../../fixtures/loginTest';
import {claySamplePageTest} from './fixtures/claySamplePageTest';
import {TabName} from './pages/ClaySamplePage';

const test = mergeTests(claySamplePageTest, loginTest());

test.beforeEach(async ({claySamplePage}) => {
	await claySamplePage.selectTab(TabName.CARDS);
});

function getSectionByTitle(page: Page, title: string) {
	return page.locator(`div.row:has(+ div.h4:has-text("${title}"))`);
}

test('Card becomes active when checkbox is checked', async ({page}) => {
	const cards = getSectionByTitle(page, 'Selectable Image Card').locator(
		'.form-check-card'
	);

	for (const card of await cards.all()) {
		const checkbox = card.getByRole('checkbox');

		await checkbox.check();

		await expect(card).toHaveClass(/active/);

		await checkbox.uncheck();

		await expect(card).not.toHaveClass(/active/);
	}
});

test('Click on card title navigates to href URL', async ({page}) => {
	const section = getSectionByTitle(page, 'Selectable Image Card');

	const titleLink = section.getByRole('link', {name: 'Beetle'});

	const titleLinkHref = await titleLink.getAttribute('href');

	const initialUrl = page.url();

	const finalUrl = `${initialUrl}${titleLinkHref}`;

	await titleLink.click();

	expect(initialUrl).not.toBe(finalUrl);
	expect(initialUrl).not.toContain(titleLinkHref);
	await expect(page).toHaveURL(finalUrl);
});
