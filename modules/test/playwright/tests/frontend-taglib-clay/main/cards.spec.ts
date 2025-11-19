/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../../fixtures/loginTest';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import {claySamplePageTest} from './fixtures/claySamplePageTest';
import {TabName} from './pages/ClaySamplePage';

const test = mergeTests(claySamplePageTest, loginTest());

function getCardsInSection(page: Page, title: string) {
	return getSectionByTitle(page, title).locator('.form-check-card');
}

function getSectionByTitle(page: Page, title: string) {
	return page.locator(`div.h4:has-text("${title}") + div.row`);
}

test.beforeEach(async ({claySamplePage}) => {
	await claySamplePage.selectTab(TabName.CARDS);
});

test('Asserts selectable image cards behavior', async ({page}) => {
	await test.step('Asserts the default state of selectable image cards', async () => {
		const checkbox = getSectionByTitle(page, 'Selectable Image Card')
			.locator('[data-qa-id="image-card-icon-block"]')
			.getByRole('checkbox');

		await expect(checkbox).not.toBeChecked();
	});

	await test.step('Card becomes active when checkbox is checked', async () => {
		const cards = getCardsInSection(page, 'Selectable Image Card');

		for (const card of await cards.all()) {
			const checkbox = card.getByRole('checkbox');

			await checkbox.check();

			await expect(card).toHaveClass(/active/);

			await checkbox.uncheck();

			await expect(card).not.toHaveClass(/active/);
		}
	});

	await test.step('Click on card title navigates to href URL', async () => {
		expect(page.url()).not.toContain('#image-card-href');

		await getCardsInSection(page, 'Selectable Image Card')
			.first()
			.getByRole('link', {name: 'Beetle'})
			.click();

		expect(page.url()).toContain('#image-card-href');
	});

	await test.step('Click on card option navigates to href URL', async () => {
		expect(page.url()).not.toContain('#1');

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('menuitem', {
				name: 'Group 1 - Option 1',
			}),
			trigger: getCardsInSection(page, 'Selectable Image Card')
				.first()
				.getByRole('button', {name: 'More actions'}),
		});

		expect(page.url()).toContain('#1');
	});
});
