/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../../fixtures/loginTest';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import {claySamplePageTest} from './fixtures/claySamplePageTest';
import {TabName} from './pages/ClaySamplePage';

const test = mergeTests(claySamplePageTest, loginTest());

test.beforeEach(async ({claySamplePage}) => {
	await claySamplePage.selectTab(TabName.CARDS);
});

test('Asserts selectable image cards behavior', async ({page}) => {
	const selectableImageCardsSection = page.locator(
		'div.h4:has-text("Selectable Image Card") + div.row'
	);

	const selectableImageCards =
		selectableImageCardsSection.locator('.form-check-card');

	await test.step('Asserts the default state of selectable image cards', async () => {
		const checkbox = selectableImageCardsSection
			.locator('[data-qa-id="image-card-icon-block"]')
			.getByRole('checkbox');

		await expect(checkbox).not.toBeChecked();
	});

	await test.step('Card becomes active when checkbox is checked', async () => {
		for (const card of await selectableImageCards.all()) {
			const checkbox = card.getByRole('checkbox');

			await checkbox.check();

			await expect(card).toHaveClass(/active/);

			await checkbox.uncheck();

			await expect(card).not.toHaveClass(/active/);
		}
	});

	await test.step('Click on card title navigates to href URL', async () => {
		expect(page.url()).not.toContain('#image-card-href');

		await selectableImageCards
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
			trigger: selectableImageCards
				.first()
				.getByRole('button', {name: 'More actions'}),
		});

		expect(page.url()).toContain('#1');
	});
});
