/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

export class ServiceProviderConnectionsPage {
	readonly page: Page;

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
	readonly nameIdentifierFormatField: Locator;
	readonly nameIdentifierAttributeNameField: Locator;
	readonly saveButton: Locator;
	readonly successMessage: Locator;

	constructor(page: Page) {
		this.page = page;

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
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.successMessage = page.getByText(
			'Your request completed successfully'
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

	async addServiceProviderConnection(
		name: string,
		entityId = name,
		enabled = true,
		assertionLifetime?: string,
		forceEncrytion?: boolean,
		metadataURL?: string,
		nameIdentifierFormat?: string,
		nameIdentifierAttributeName = 'emailAddress',
		attributesEnabled?: boolean,
		attributesNamespaceEnabled?: boolean,
		attributes?: string,
		keepAliveUrl?: string
	) {
		await this.goToServiceProviderConnectionsTab();

		await this.page
			.getByRole('button', {name: 'Add Service Provider'})
			.click();

		await this.populateAndSaveServiceProviderConnectionDetails(
			name,
			entityId,
			enabled,
			assertionLifetime,
			forceEncrytion,
			metadataURL,
			nameIdentifierFormat,
			nameIdentifierAttributeName,
			attributesEnabled,
			attributesNamespaceEnabled,
			attributes,
			keepAliveUrl
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
		entityId?: string,
		enabled?: boolean,
		assertionLifetime?: string,
		forceEncrytion?: boolean,
		metadataURL?: string,
		nameIdentifierFormat?: string,
		nameIdentifierAttributeName?: string,
		attributesEnabled?: boolean,
		attributesNamespaceEnabled?: boolean,
		attributes?: string,
		keepAliveUrl?: string
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
			entityId,
			enabled,
			assertionLifetime,
			forceEncrytion,
			metadataURL,
			nameIdentifierFormat,
			nameIdentifierAttributeName,
			attributesEnabled,
			attributesNamespaceEnabled,
			attributes,
			keepAliveUrl
		);
	}

	private async populateAndSaveServiceProviderConnectionDetails(
		name: string,
		entityId?: string,
		enabled?: boolean,
		assertionLifetime?: string,
		forceEncrytion?: boolean,
		metadataURL?: string,
		nameIdentifierFormat?: string,
		nameIdentifierAttributeName?: string,
		attributesEnabled?: boolean,
		attributesNamespaceEnabled?: boolean,
		attributes?: string,
		keepAliveUrl?: string
	) {
		await this.nameField.fill(name);

		if (entityId) {
			await this.entityIdField.fill(entityId);
		}

		if (enabled !== undefined) {
			await this.enabledField.setChecked(enabled);
		}

		if (assertionLifetime) {
			await this.assertionLifetimeField.fill(assertionLifetime);
		}

		if (forceEncrytion !== undefined) {
			await this.forceEncryptionToggle.setChecked(forceEncrytion);
		}

		if (metadataURL) {
			await this.metadataUrlField.fill(metadataURL);
		}

		if (nameIdentifierFormat) {
			await this.nameIdentifierFormatField.selectOption(
				nameIdentifierFormat
			);
		}

		if (nameIdentifierAttributeName) {
			await this.nameIdentifierAttributeNameField.fill(
				nameIdentifierAttributeName
			);
		}

		if (attributesEnabled !== undefined) {
			await this.attributesEnabledToggle.setChecked(attributesEnabled);
		}

		if (attributesNamespaceEnabled !== undefined) {
			await this.attributesNamespaceEnabledToggle.setChecked(
				attributesNamespaceEnabled
			);
		}

		if (attributes) {
			await this.attributesField.fill(attributes);
		}

		if (keepAliveUrl) {
			await this.keepAliveUrlField.fill(keepAliveUrl);
		}

		await this.saveButton.click();

		expect(await this.successMessage).toBeVisible();
	}
}
