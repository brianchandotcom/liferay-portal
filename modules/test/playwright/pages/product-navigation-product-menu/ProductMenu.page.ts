/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class ProductMenuPage {
	readonly closeProductMenuButton: Locator;
	readonly contentAndDataMenuItem: Locator;
	readonly knowledgeBaseMenuItem: Locator;
	readonly openProductMenuButton: Locator;
	readonly page: Page;
	readonly documentsAndMediaMenuItem: Locator;

	constructor(page: Page) {
		this.closeProductMenuButton = page.getByLabel('Close Product Menu');
		this.contentAndDataMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Content & Data',
		});
		this.knowledgeBaseMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Knowledge Base',
		});
		this.documentsAndMediaMenuItem = page.getByRole('menuitem', {
			name: 'Documents and Media',
		});

		this.openProductMenuButton = page.getByLabel('Open Product Menu');
		this.page = page;
	}

	async goto() {
		await this.page.goto('/');
	}

	async openToProductMenu() {
		await this.goto();
		const openProductMenuVisible =
			await this.openProductMenuButton.isVisible();

		if (openProductMenuVisible) {
			await this.openProductMenuButton.click();
		}
	}

	async closeToProductMenu() {
		await this.goto();
		const closeProductMenuVisible =
			await this.closeProductMenuButton.isVisible();

		if (closeProductMenuVisible) {
			await this.closeProductMenuButton.click();
		}
	}

	async goToKnowledgeBaseMenuItem() {
		await this.goToContentAndData();
		await this.knowledgeBaseMenuItem.click();
	}

	async goToDocumentsAndMediaMenuItemMenuItem() {
		await this.goToContentAndData();
		await this.documentsAndMediaMenuItem.click();
	}

	async goToContentAndData() {
		await this.openToProductMenu();
		const isClosed =
			(await this.contentAndDataMenuItem.getAttribute(
				'aria-expanded'
			)) === 'false';

		if (isClosed) {
			await this.contentAndDataMenuItem.click();
		}
	}
}
