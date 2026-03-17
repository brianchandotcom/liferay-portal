/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../../../utils/clickAndExpectToBeVisible';
import {expectToPass} from '../../../../utils/expectToPass';
import {waitForAlert} from '../../../../utils/waitForAlert';

export class FindAndReplacePage {
	readonly page: Page;

	readonly languageSelector: Locator;
	readonly openButton: Locator;

	constructor(page: Page) {
		this.page = page;

		this.languageSelector = page.getByLabel(/Select a language/);
		this.openButton = this.page.getByRole('button', {
			name: 'Find and Replace',
		});
	}

	async applyChangesToAllItems() {
		await expectToPass(
			async () => {
				await this.page
					.getByText('Apply Changes to All Assets')
					.click();

				await waitForAlert(this.page, 'Replaced');
			},
			{timeout: 80000}
		);
	}

	async applyChangesToItem(title: string) {
		const row = this.page.locator('.list-group-item', {hasText: title});

		await row.getByText('Apply Changes').click();

		await waitForAlert(this.page, 'Changes Applied');

		await expect(row).not.toBeVisible();
	}

	async goBack() {
		await this.page.locator('.modal-header').getByLabel('Back').click();
	}

	async goToReviewChanges() {
		await clickAndExpectToBeVisible({
			target: this.page.locator('.modal-title', {
				hasText: 'Review Changes',
			}),
			trigger: this.page
				.locator('.modal-footer')
				.getByText('Review Changes'),
		});
	}

	async goToPreviewChanges(title: string) {
		const button = this.page.getByRole('button', {
			exact: true,
			name: title,
		});

		await clickAndExpectToBeVisible({
			target: this.page.locator('.modal-title', {hasText: title}),
			trigger: button,
		});
	}

	async open() {
		await clickAndExpectToBeVisible({
			target: this.page.locator('.modal-title', {
				hasText: 'Find and Replace',
			}),
			timeout: 3000,
			trigger: this.openButton,
		});
	}

	async switchLanguage(language: string) {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.locator('.dropdown-item', {hasText: language}),
			trigger: this.languageSelector,
		});

		await expect(this.languageSelector).toHaveText(language);
	}
}
