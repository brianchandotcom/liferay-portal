/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

export class EditResultRankingPage {
	readonly addResultButton: Locator;
	readonly page: Page;
	readonly saveButton: Locator;

	readonly addResultModal: {
		readonly addButton: Locator;
		readonly container: Locator;
		readonly searchInput: Locator;
		readonly searchResultItem: (resultTitle: string) => Promise<Locator>;
	};

	readonly results: {
		readonly container: Locator;
		readonly item: (resultTitle: string) => Promise<Locator>;
	};

	constructor(page: Page) {
		this.addResultButton = page.getByLabel('Add Result');
		this.saveButton = page.getByRole('button', {name: 'Save'});

		this.page = page;

		const addResultModalContainer = page.locator('.add-result-modal-root');

		this.addResultModal = {
			addButton: addResultModalContainer.getByRole('button', {
				name: 'Add',
			}),
			container: addResultModalContainer,
			searchInput:
				addResultModalContainer.getByPlaceholder('Search the engine'),
			searchResultItem: async (resultTitle: string) => {
				return addResultModalContainer
					.locator('.list-group-item')
					.filter({
						has: page.getByRole('link', {
							exact: true,
							name: resultTitle,
						}),
					});
			},
		};

		const resultsContainer = page.locator(
			'.result-rankings-container .form-section-results-list'
		);

		this.results = {
			container: resultsContainer,
			item: async (resultTitle: string) => {
				return resultsContainer.locator('.list-group-item').filter({
					has: page.getByRole('link', {
						exact: true,
						name: resultTitle,
					}),
				});
			},
		};
	}

	async addResults({
		searchQuery,
		titles,
	}: {
		searchQuery: string;
		titles: string[];
	}) {
		await this.addResultButton.click();

		await this.addResultModal.searchInput.fill(searchQuery);

		await this.addResultModal.searchInput.press('Enter');

		for (const title of titles) {
			const addResultSearchItem =
				await this.addResultModal.searchResultItem(title);

			await expect(addResultSearchItem).toBeVisible();

			await addResultSearchItem.getByLabel('Select').click();
		}

		await this.addResultModal.addButton.click();

		for (const title of titles) {
			const resultsItem = await this.results.item(title);

			await expect(resultsItem).toBeVisible();
		}
	}

	async dragAndDropResultsItem({
		dragTarget,
		dropTarget,
	}: {
		dragTarget: Locator;
		dropTarget: Locator;
	}) {
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
