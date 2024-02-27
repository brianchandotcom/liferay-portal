/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {getSiteUrl} from '../../utils/siteUrl';

export class ProductMenuPage {
	readonly page: Page;

	readonly closeProductMenuButton: Locator;
	readonly configurationMenuItem: Locator;
	readonly contentAndDataMenuItem: Locator;
	readonly documentsAndMediaMenuItem: Locator;
	readonly journalMenuItem: Locator;
	readonly knowledgeBaseMenuItem: Locator;
	readonly lockedItemsMenuItem: Locator;
	readonly openProductMenuButton: Locator;
	readonly pagesMenuItem: Locator;
	readonly siteBuilderMenuItem: Locator;

	constructor(page: Page) {
		this.page = page;

		this.closeProductMenuButton = page.getByLabel('Close Product Menu');
		this.configurationMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Configuration',
		});
		this.contentAndDataMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Content & Data',
		});
		this.documentsAndMediaMenuItem = page.getByRole('menuitem', {
			name: 'Documents and Media',
		});
		this.journalMenuItem = page.getByRole('menuitem', {
			name: 'Web Content',
		});
		this.knowledgeBaseMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Knowledge Base',
		});
		this.lockedItemsMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Locked Items',
		});
		this.openProductMenuButton = page.getByLabel('Open Product Menu');
		this.pagesMenuItem = page.getByRole('menuitem', {
			name: 'Pages',
		});
		this.siteBuilderMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Site Builder',
		});
	}

	async closeProductMenu() {
		await this.goto();

		if (await this.closeProductMenuButton.isVisible()) {
			await this.closeProductMenuButton.click();
		}
	}

	async goto() {
		const siteUrl = await getSiteUrl(this.page);

		await this.page.goto(
			`/group${siteUrl}/~/control_panel/manage/-/site/settings`
		);
	}

	async goToConfiguration() {
		await this.openProductMenu();
		const isClosed =
			(await this.configurationMenuItem.getAttribute('aria-expanded')) ===
			'false';

		if (isClosed) {
			await this.configurationMenuItem.click();
		}
	}

	async goToContentAndData() {
		await this.openProductMenu();
		const isClosed =
			(await this.contentAndDataMenuItem.getAttribute(
				'aria-expanded'
			)) === 'false';

		if (isClosed) {
			await this.contentAndDataMenuItem.click();
		}
	}

	async goToDocumentsAndMediaMenuItem() {
		await this.goToContentAndData();
		await this.documentsAndMediaMenuItem.click();
	}

	async goToJournalMenuItem() {
		await this.goToContentAndData();
		await this.journalMenuItem.click();
	}

	async goToKnowledgeBaseMenuItem() {
		await this.goToContentAndData();
		await this.knowledgeBaseMenuItem.click();
	}

	async goToLockedItemsMenuItem() {
		await this.goToConfiguration();
		await this.lockedItemsMenuItem.click();
	}

	async goToPagesMenuItem() {
		await this.goToSiteBuilder();
		await this.pagesMenuItem.click();
	}

	async goToSiteBuilder() {
		await this.openProductMenu();
		const isClosed =
			(await this.siteBuilderMenuItem.getAttribute('aria-expanded')) ===
			'false';

		if (isClosed) {
			await this.siteBuilderMenuItem.click();
		}
	}

	async openProductMenu() {
		await this.goto();

		if (await this.openProductMenuButton.isVisible()) {
			await this.openProductMenuButton.click();
		}
	}
}
