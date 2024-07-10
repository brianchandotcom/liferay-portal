/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {waitForSuccessAlert} from '../../utils/waitForSuccessAlert';

export class WidgetPagePage {
	readonly page: Page;

	readonly addButton: Locator;
	readonly contentTab: Locator;
	readonly toggleControlsButton: Locator;
	readonly widgetsTab: Locator;

	constructor(page: Page) {
		this.page = page;

		this.addButton = page
			.locator('.control-menu-nav-item')
			.getByRole('button', {
				exact: true,
				name: 'Add',
			});

		this.contentTab = page.getByText('Content', {
			exact: true,
		});

		this.toggleControlsButton = page
			.locator('.control-menu-nav-item')
			.getByRole('button', {
				exact: true,
				name: 'Toggle Controls',
			});

		this.widgetsTab = page.getByText('Widgets', {
			exact: true,
		});
	}

	async addPortlet(portletName: string) {
		await this.addButton.click();

		await this.widgetsTab.click();

		await this.page
			.getByRole('textbox', {name: 'Search Form'})
			.fill(portletName);

		await this.page
			.locator('.sidebar-body__add-panel__tab-item')
			.filter({hasText: portletName})
			.getByRole('button', {name: 'Add Content'})
			.click();

		await waitForSuccessAlert(
			this.page,
			'Success:The application was added to the page.'
		);
	}

	async addContent(contentName: string) {
		await this.addButton.click();

		await this.contentTab.click();

		await this.page
			.locator('.sidebar-body__add-panel__tab-item')
			.filter({hasText: contentName})
			.getByRole('button', {name: 'Add Content'})
			.click();

		await waitForSuccessAlert(
			this.page,
			'Success:The application was added to the page.'
		);
	}

	async deletePortlet(portletName: string) {
		this.page.on('dialog', async (dialog) => {
			await dialog.accept();
		});

		await this.page
			.locator('.portlet-topper', {hasText: portletName})
			.getByLabel('Options')
			.click();

		await this.page
			.getByRole('menuitem', {
				name: 'Remove',
			})
			.click();
	}

	async openAddPanel() {
		const isOpen = await this.addButton.evaluate((element) =>
			element.classList.contains('open')
		);

		if (!isOpen) {
			await this.addButton.click();
		}
	}

	async toggleControls(state: 'visible' | 'hidden') {
		const isOpen = await this.toggleControlsButton
			.locator('svg')
			.evaluate((element) =>
				element.classList.contains('lexicon-icon-view')
			);

		if (
			(state === 'visible' && !isOpen) ||
			(state === 'hidden' && isOpen)
		) {
			await this.toggleControlsButton.click();
		}
	}
}
