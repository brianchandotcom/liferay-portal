/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect} from '@playwright/test';

import {PORTLET_URLS} from '../../../../utils/portletUrls';

export class SharedWithMePage {
	readonly page: Page;

	constructor(page: Page) {
		this.page = page;
	}

	async goto() {
		await this.page.goto(PORTLET_URLS.cmsSharedWithMe);
		await this.page
			.getByRole('menuitem', {name: 'Shared With Me'})
			.waitFor();
	}

	async expectAssetEntryToBeVisible(title: string) {
		await this.goto();

		const assetRow = this.page.getByRole('row', {name: title});

		await expect(
			assetRow.locator('.cell-title').getByRole('link')
		).toBeVisible();

		await expect(
			assetRow.locator('.cell-visible').getByText('Not Visible')
		).not.toBeVisible();

		await expect(
			assetRow.locator('.cell-item-actions .lexicon-icon')
		).toBeVisible();
	}

	async expectAssetEntryNotToBeVisible(title: string) {
		await this.goto();

		const assetRow = this.page.getByRole('row', {name: title});

		await expect(
			assetRow.locator('.cell-title').getByRole('link')
		).not.toBeVisible();

		await expect(
			assetRow.locator('.cell-visible').getByText('Not Visible')
		).toBeVisible();

		await expect(
			assetRow.locator('.cell-item-actions .lexicon-icon')
		).not.toBeVisible();
	}
}
