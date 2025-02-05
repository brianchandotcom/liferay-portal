/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

export class EditResultRankingPage {
	readonly addResultButton: Locator;
	readonly addResultModal: Locator;
	readonly addResultModalAddButton: Locator;
	readonly addResultModalSearch: Locator;
	readonly addResultModalSearchItem: (
		resultTitle: string
	) => Promise<Locator>;
	readonly page: Page;
	readonly resultRankingItem: (label: string) => Promise<Locator>;
	readonly saveButton: Locator;

	constructor(page: Page) {
		this.addResultButton = page.getByLabel('Add Result');
		this.saveButton = page.getByRole('button', {name: 'Save'});

		this.page = page;

		// Add Result Modal

		this.addResultModal = page.locator('.add-result-modal-root');
		this.addResultModalSearch =
			this.addResultModal.getByPlaceholder('Search the engine');
		this.addResultModalAddButton = this.addResultModal.getByRole('button', {
			name: 'Add',
		});
		this.addResultModalSearchItem = async (resultTitle: string) => {
			return page
				.locator('.add-result-modal-root .list-group-item')
				.filter({
					has: page.getByRole('link', {
						exact: true,
						name: resultTitle,
					}),
				});
		};

		// Result Ranking List

		this.resultRankingItem = async (resultTitle: string) => {
			return page
				.locator('.result-rankings-container .list-group-item')
				.filter({
					has: page.getByRole('link', {
						exact: true,
						name: resultTitle,
					}),
				});
		};
	}

	async addResults(searchQuery: string, titles: string[]) {
		await this.addResultButton.click();

		await this.addResultModalSearch.fill(searchQuery);

		await this.addResultModalSearch.press('Enter');

		for (const title of titles) {
			const searchResultItem = await this.addResultModalSearchItem(title);

			await expect(searchResultItem).toBeVisible();

			await searchResultItem.getByLabel('Select').click();
		}

		await this.addResultModalAddButton.click();
	}

	async dragAndDropResultRankingItem({dragTarget, dropTarget}) {
		const boundingClientRect = await dropTarget.evaluate((element) =>
			element.getBoundingClientRect()
		);

		await dragTarget.locator('.lexicon-icon-drag').dragTo(dropTarget, {
			targetPosition: {
				x: boundingClientRect.width / 2,
				y: boundingClientRect.height / 2,
			},
		});
	}
}
