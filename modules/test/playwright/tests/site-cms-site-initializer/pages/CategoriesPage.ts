/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page, expect} from '@playwright/test';

import {PORTLET_URLS} from '../../../utils/portletUrls';
import {DataSetPage} from './DataSetPage';

export class CategoriesPage {
	readonly page: Page;
	readonly dataSetFragmentPage: DataSetPage;
	readonly permissionsFrame: FrameLocator;

	private readonly breadcrumbBar: Locator;
	private readonly createNewCategoryButton: Locator;
	private readonly deleteConfirmationModal: Locator;

	constructor(page: Page) {
		this.page = page;
		this.dataSetFragmentPage = new DataSetPage(page);
		this.permissionsFrame = this.page.frameLocator(
			'iframe[title="Permissions"]'
		);

		this.breadcrumbBar = this.page.locator('.breadcrumb-bar');
		this.createNewCategoryButton = this.page.getByTitle('New Category');
		this.deleteConfirmationModal = this.page.locator('.modal-content', {
			hasText: 'Delete',
		});
	}

	async goto(vocabularyId: string | number, vocabularyName: string) {
		await this.page.goto(
			PORTLET_URLS.cmsCategories + '?vocabularyId=' + vocabularyId
		);

		await this.assertBreadcrumbItemText(0, 'Categorization');
		await this.assertBreadcrumbItemText(1, vocabularyName);
	}

	async clickCreateNewCategoryButton() {
		await this.createNewCategoryButton.click();

		await expect(this.page.getByText('Basic Info')).toBeVisible();
	}

	getItem(filter: string) {
		return this.dataSetFragmentPage.getRow(filter);
	}

	async execItemAction({action, filter}: {action: string; filter: string}) {
		await this.dataSetFragmentPage.execItemAction({
			action,
			filter,
		});
	}

	async assertBreadcrumbItemText(index: number, text: string) {
		const breadcrumbItem = this.breadcrumbBar
			.locator('.breadcrumb-item')
			.nth(index);

		await breadcrumbItem.waitFor({state: 'visible'});
		await expect(breadcrumbItem).toContainText(text);
	}

	async handleDeleteConfirmationModal(clickDelete: boolean) {
		await expect(this.deleteConfirmationModal).toBeVisible();

		clickDelete
			? await this.deleteConfirmationModal
					.getByRole('button', {name: 'Delete'})
					.click()
			: await this.deleteConfirmationModal
					.getByRole('button', {name: 'Cancel'})
					.click();
	}
}
