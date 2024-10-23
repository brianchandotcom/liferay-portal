/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {liferayConfig} from '../../../liferay.config';
import getRandomString from '../../../utils/getRandomString';

interface newClientExtensionProps {
	cssUrl?: string;
	description?: string;
	friendlyUrlMapping?: string;
	htmlElementName?: string;
	instanceable?: boolean;
	javaScriptUrl?: string;
	name?: string;
	sourceCodeUrl?: string;
	type?: string;
	useEsModulesInstanceable?: boolean;
}

export class ClientExtensionsPage {
	readonly addNewClientExtensionButton: Locator;
	readonly deleteMenuItem: Locator;
	readonly editMenuItem: Locator;
	readonly viewMenuItem: Locator;

	readonly configuredFromTableHeader: Locator;
	readonly nameTableHeader: Locator;

	readonly newCustomElementFormModal: {
		readonly cancelButton: Locator;
		readonly cssUrlInput: Locator;
		readonly descriptionTextbox: Locator;
		readonly friendlyUrlMappingInput: Locator;
		readonly htmlElementNameInput: Locator;
		readonly instanceableCheck: Locator;
		readonly javaScriptUrlInput: Locator;
		readonly localizedNameButton: Locator;
		readonly nameInput: Locator;
		readonly newCustomElementHeader: Locator;
		readonly publishButton: Locator;
		readonly sourceCodeUrlInput: Locator;
		readonly useEsModulesCheck: Locator;
	};

	readonly page: Page;

	constructor(page: Page) {

		// action buttons

		this.addNewClientExtensionButton = page.getByTitle('New');
		this.deleteMenuItem = page.getByRole('menuitem', {
			name: 'Delete',
		});
		this.editMenuItem = page.getByRole('menuitem', {
			name: 'Edit',
		});
		this.viewMenuItem = page.getByRole('menuitem', {
			name: 'View',
		});

		// new CX modal

		this.newCustomElementFormModal = {
			cancelButton: page.getByRole('button', {name: 'Cancel'}),
			cssUrlInput: page.getByLabel('CSS URL', {exact: true}),
			descriptionTextbox: page
				.frameLocator('iframe[title="editor"]')
				.getByRole('textbox'),
			friendlyUrlMappingInput: page.getByLabel('Friendly URL Mapping', {
				exact: true,
			}),
			htmlElementNameInput: page.getByLabel(
				'HTML Element Name Required',
				{exact: true}
			),
			instanceableCheck: page.getByLabel('Instanceable', {exact: true}),
			javaScriptUrlInput: page.getByLabel('JavaScript URL Required', {
				exact: true,
			}),
			localizedNameButton: page
				.locator('.input-localized')
				.getByRole('button'),
			nameInput: page.getByLabel('Name Required', {exact: true}),
			newCustomElementHeader: page.locator('h3'),
			publishButton: page.getByRole('button', {name: 'Publish'}),
			sourceCodeUrlInput: page.getByLabel('Source Code URL', {
				exact: true,
			}),
			useEsModulesCheck: page.getByLabel('Use ES Modules', {exact: true}),
		};

		// table columns

		this.nameTableHeader = page.getByLabel('Name', {exact: true});
		this.configuredFromTableHeader = page.getByLabel('Configured From', {
			exact: true,
		});
		this.page = page;
	}

	async addClientExtension({
		cssUrl = getRandomString(),
		description = getRandomString(),
		friendlyUrlMapping = getRandomString(),
		htmlElementName = 'html-element-' + getRandomString(),
		instanceable = true,
		javaScriptUrl = getRandomString(),
		name = getRandomString(),
		sourceCodeUrl = getRandomString(),
		type,
		useEsModulesInstanceable = true,
	}: newClientExtensionProps) {
		await this.addNewClientExtensionButton.click();
		await this.page.getByRole('menuitem', {name: type}).click();

		await this.fillNewCustomElementFormModal({
			cssUrl,
			description,
			friendlyUrlMapping,
			htmlElementName,
			instanceable,
			javaScriptUrl,
			name,
			sourceCodeUrl,
			useEsModulesInstanceable,
		});

		await this.newCustomElementFormModal.publishButton.click();
	}

	async deleteClientExtension(clientExtensionName: string) {
		await this.openItemActionsDropdown(clientExtensionName);

		this.page.on('dialog', (dialog) => dialog.accept());

		await this.deleteMenuItem.click();
	}

	async editClientExtension(clientExtensionName: string) {
		await this.openItemActionsDropdown(clientExtensionName);

		await this.editMenuItem.click();

		// Wait for page to load

		expect(
			this.page.locator(
				'#cke__com_liferay_client_extension_web_internal_portlet_ClientExtensionAdminPortlet_description'
			)
		).toBeVisible();
	}

	async fillNewCustomElementFormModal({
		cssUrl,
		description,
		friendlyUrlMapping,
		htmlElementName,
		instanceable,
		javaScriptUrl,
		name,
		sourceCodeUrl,
		useEsModulesInstanceable,
	}: newClientExtensionProps) {
		if (cssUrl) {
			await this.newCustomElementFormModal.cssUrlInput.fill(cssUrl);
		}

		if (description) {
			await this.newCustomElementFormModal.descriptionTextbox.clear();
			await this.newCustomElementFormModal.descriptionTextbox.fill(
				description
			);
		}

		if (friendlyUrlMapping) {
			await this.newCustomElementFormModal.friendlyUrlMappingInput.fill(
				friendlyUrlMapping
			);
		}

		if (htmlElementName) {
			await this.newCustomElementFormModal.htmlElementNameInput.fill(
				htmlElementName
			);
		}

		if (instanceable) {
			await this.newCustomElementFormModal.instanceableCheck.check();
		}

		if (javaScriptUrl) {
			await this.newCustomElementFormModal.javaScriptUrlInput.fill(
				javaScriptUrl
			);
		}

		if (name) {
			await this.newCustomElementFormModal.nameInput.fill(name);
		}

		if (sourceCodeUrl) {
			await this.newCustomElementFormModal.sourceCodeUrlInput.fill(
				sourceCodeUrl
			);
		}

		if (useEsModulesInstanceable) {
			await this.newCustomElementFormModal.useEsModulesCheck.check();
		}
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

		// Wait for page to load

		expect(this.addNewClientExtensionButton).toBeVisible();
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
