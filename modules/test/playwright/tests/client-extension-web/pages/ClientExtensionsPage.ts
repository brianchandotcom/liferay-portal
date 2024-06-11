/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {liferayConfig} from '../../../liferay.config';

export class ClientExtensionsPage {
	readonly deleteMenuItem: Locator;
	readonly editMenuItem: Locator;
	readonly viewMenuItem: Locator;

	readonly configuredFromTableHeader: Locator;
	readonly nameTableHeader: Locator;

	readonly page: Page;

	constructor(page: Page) {

		// action buttons

		this.deleteMenuItem = page.getByRole('menuitem', {
			name: 'Delete',
		});
		this.editMenuItem = page.getByRole('menuitem', {
			name: 'Edit',
		});
		this.viewMenuItem = page.getByRole('menuitem', {
			name: 'View',
		});

		// table columns

		this.nameTableHeader = page.getByLabel('Name', {exact: true});
		this.configuredFromTableHeader = page.getByLabel('Configured From', {
			exact: true,
		});
		this.page = page;
	}

	async deleteClientExtension(clientExtensionName: string) {
		await this.openItemActionsDropdown(clientExtensionName);

		this.page.on('dialog', (dialog) => dialog.accept());

		await this.deleteMenuItem.click();
	}

	async editClientExtension(clientExtensionName: string) {
		await this.openItemActionsDropdown(clientExtensionName);

		await this.editMenuItem.click();
	}

	getRowByText(text: string) {
		return this.page
			.locator('.dnd-tbody')
			.locator('.dnd-tr')
			.filter({
				has: this.page.getByText(text, {exact: true}).first(),
			});
	}

	async viewClientExtension(clientExtensionName: string) {
		await this.openItemActionsDropdown(clientExtensionName);

		await this.viewMenuItem.click();
	}

	async assertIsConfiguredFrom(
		clientExtensionName: string,
		configuredFrom: string
	) {
		await expect(
			this.getRowByText(clientExtensionName).locator('.dnd-td').nth(3)
		).toHaveText(configuredFrom);
	}

	async assertName(clientExtensionName: string) {
		await expect(
			this.getRowByText(clientExtensionName).locator('.dnd-td').nth(0)
		).toBeVisible();
	}

	async goto() {
		await this.page.goto(
			`${liferayConfig.environment.baseUrl}/group/guest/~/control_panel/manage` +
				'?p_p_id=com_liferay_client_extension_web_internal_portlet_ClientExtensionAdminPortlet'
		);
	}

	async openItemActionsDropdown(clientExtensionName: string) {
		await this.page
			.locator('.dnd-tr')
			.filter({has: this.page.getByText(clientExtensionName)})
			.getByRole('button', {
				name: 'Actions',
			})
			.click();
	}
}
