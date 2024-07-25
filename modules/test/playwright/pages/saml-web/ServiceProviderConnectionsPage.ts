/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

export class ServiceProviderConnectionsPage {
	readonly applicationsMenuPage;
	readonly assertionLifetimeField: Locator;
	readonly attributesEnabledToggle: Locator;
	readonly attributesField: Locator;
	readonly attributesNamespaceEnabledToggle: Locator;
	readonly enabledField: Locator;
	readonly entityIdField: Locator;
	readonly forceEncryptionToggle: Locator;
	readonly keepAliveUrlField: Locator;
	readonly metadataUrlField: Locator;
	readonly nameField: Locator;
	readonly nameIdentifierAttributeNameField: Locator;
	readonly nameIdentifierFormatField: Locator;
	readonly page: Page;
	readonly saveButton: Locator;
	readonly successMessage: Locator;

	constructor(page: Page) {
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.assertionLifetimeField = page.getByLabel('Assertion Lifetime');
		this.attributesEnabledToggle = page.getByText('Attributes Enabled', {
			exact: true,
		});
		this.attributesField = page
			.getByRole('group', {name: 'Attributes'})
			.getByRole('textbox');
		this.attributesNamespaceEnabledToggle = page.getByText(
			'Attributes Namespace Enabled'
		);
		this.enabledField = page.getByText('Enabled', {exact: true});
		this.entityIdField = page.getByLabel('Entity ID');
		this.forceEncryptionToggle = page.getByText('Force Encryption', {
			exact: true,
		});
		this.keepAliveUrlField = page
			.getByRole('group', {name: 'Keep Alive'})
			.getByRole('textbox');
		this.metadataUrlField = page.getByLabel('Metadata URL', {exact: true});
		this.nameField = page.getByLabel('Name').first();
		this.nameIdentifierAttributeNameField = page.getByLabel(
			'Name Identifier Attribute Name'
		);
		this.nameIdentifierFormatField = page.getByLabel(
			'Name Identifier Format'
		);
		this.page = page;
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.successMessage = page.getByText(
			'Your request completed successfully'
		);
	}

	async addServiceProviderConnection(
		metadataURL: string,
		name: string,
		assertionLifetime?: string,
		attributes?: string,
		attributesEnabled?: boolean,
		attributesNamespaceEnabled?: boolean,
		enabled = true,
		entityId = name,
		forceEncrytion?: boolean,
		keepAliveUrl?: string,
		nameIdentifierAttributeName = 'emailAddress',
		nameIdentifierFormat?: string
	) {
		await this.goToServiceProviderConnectionsTab();

		await this.page
			.getByRole('button', {name: 'Add Service Provider'})
			.click();

		await this.populateAndSaveServiceProviderConnectionDetails(
			name,
			assertionLifetime,
			attributes,
			attributesEnabled,
			attributesNamespaceEnabled,
			enabled,
			entityId,
			forceEncrytion,
			keepAliveUrl,
			metadataURL,
			nameIdentifierAttributeName,
			nameIdentifierFormat
		);
	}

	async deleteServiceProviderConnection(name: string) {
		await this.goToServiceProviderConnectionsTab();

		this.page.once('dialog', (dialog) => {
			dialog.accept();
		});

		const row = await this.page.getByRole('row').filter({hasText: name});

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('link', {name: 'Delete'}),
			trigger: row.locator('.dropdown-toggle'),
		});

		expect(await this.successMessage).toBeVisible();
	}

	async editServiceProviderConnection(
		name: string,
		assertionLifetime?: string,
		attributes?: string,
		attributesEnabled?: boolean,
		attributesNamespaceEnabled?: boolean,
		enabled?: boolean,
		entityId?: string,
		forceEncrytion?: boolean,
		keepAliveUrl?: string,
		metadataURL?: string,
		nameIdentifierAttributeName?: string,
		nameIdentifierFormat?: string
	) {
		await this.goToServiceProviderConnectionsTab();

		const row = await this.page.getByRole('row').filter({hasText: name});

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {name: 'Edit'}),
			trigger: row.locator('.dropdown-toggle'),
		});

		await this.populateAndSaveServiceProviderConnectionDetails(
			name,
			assertionLifetime,
			attributes,
			attributesEnabled,
			attributesNamespaceEnabled,
			enabled,
			entityId,
			forceEncrytion,
			keepAliveUrl,
			metadataURL,
			nameIdentifierAttributeName,
			nameIdentifierFormat
		);
	}

	async goToServiceProviderConnectionsTab() {
		await this.applicationsMenuPage.goToSamlAdmin();
		await this.page
			.getByRole('tab', {name: 'Service Provider Connections'})
			.click();
		expect(
			await this.page.getByRole('button', {name: 'Add Service Provider'})
		).toBeVisible();
	}

	private async populateAndSaveServiceProviderConnectionDetails(
		name: string,
		assertionLifetime?: string,
		attributes?: string,
		attributesEnabled?: boolean,
		attributesNamespaceEnabled?: boolean,
		enabled?: boolean,
		entityId?: string,
		forceEncrytion?: boolean,
		keepAliveUrl?: string,
		metadataURL?: string,
		nameIdentifierAttributeName?: string,
		nameIdentifierFormat?: string
	) {
		await this.nameField.fill(name);

		if (assertionLifetime) {
			await this.assertionLifetimeField.fill(assertionLifetime);
		}

		if (attributes) {
			await this.attributesField.fill(attributes);
		}

		if (attributesEnabled !== undefined) {
			await this.attributesEnabledToggle.setChecked(attributesEnabled);
		}

		if (attributesNamespaceEnabled !== undefined) {
			await this.attributesNamespaceEnabledToggle.setChecked(
				attributesNamespaceEnabled
			);
		}

		if (enabled !== undefined) {
			await this.enabledField.setChecked(enabled);
		}

		if (entityId) {
			await this.entityIdField.fill(entityId);
		}

		if (forceEncrytion !== undefined) {
			await this.forceEncryptionToggle.setChecked(forceEncrytion);
		}

		if (keepAliveUrl) {
			await this.keepAliveUrlField.fill(keepAliveUrl);
		}

		if (metadataURL) {
			await this.metadataUrlField.fill(metadataURL);
		}

		if (nameIdentifierAttributeName) {
			await this.nameIdentifierAttributeNameField.fill(
				nameIdentifierAttributeName
			);
		}

		if (nameIdentifierFormat) {
			await this.nameIdentifierFormatField.selectOption(
				nameIdentifierFormat
			);
		}

		await this.saveButton.click();

		expect(await this.successMessage).toBeVisible();
	}
}
