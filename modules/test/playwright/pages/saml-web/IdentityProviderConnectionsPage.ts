/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

export class IdentityProviderConnectionsPage {
	readonly applicationsMenuPage;
	readonly clockSkewField: Locator;
	readonly enabledField: Locator;
	readonly entityIdField: Locator;
	readonly forceAuthnToggle: Locator;
	readonly keepAliveUrlField: Locator;
	readonly metadataUrlField: Locator;
	readonly nameField: Locator;
	readonly nameIdentifierFormatField: Locator;
	readonly page: Page;
	readonly saveButton: Locator;
	readonly successMessage: Locator;
	readonly unknownUsersAreStrangersToggle: Locator;

	constructor(page: Page) {
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.clockSkewField = page.getByLabel('Clock Skew');
		this.enabledField = page.getByText('Enabled', {exact: true});
		this.entityIdField = page.getByLabel('Entity ID');
		this.forceAuthnToggle = page.getByText('Force Authn', {
			exact: true,
		});
		this.keepAliveUrlField = page
			.getByRole('group', {name: 'Keep Alive'})
			.getByRole('textbox');
		this.metadataUrlField = page.getByLabel('Metadata URL', {exact: true});
		this.nameField = page.getByLabel('Name').first();
		this.nameIdentifierFormatField = page.getByLabel(
			'Name Identifier Format'
		);
		this.page = page;
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.successMessage = page.getByText(
			'Your request completed successfully'
		);
		this.unknownUsersAreStrangersToggle = page.getByText(
			'Unknown Users Are Strangers'
		);
	}

	async addIdentityProviderConnection(
		metadataURL: string,
		name: string,
		clockSkew?: string,
		enabled = true,
		entityId = name,
		forceAuthn?: boolean,
		keepAliveUrl?: string,
		nameIdentifierFormat?: string,
		unknownUsersAreStrangers?: boolean
	) {
		await this.goToIdentityProviderConnectionsTab();

		await this.page
			.getByRole('button', {name: 'Add Identity Provider'})
			.click();

		await this.populateAndSaveIdentityProviderConnectionDetails(
			name,
			clockSkew,
			enabled,
			entityId,
			forceAuthn,
			keepAliveUrl,
			metadataURL,
			nameIdentifierFormat,
			unknownUsersAreStrangers
		);
	}

	async deleteIdentityProviderConnection(name: string) {
		await this.goToIdentityProviderConnectionsTab();

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

	async editIdentityProviderConnection(
		name: string,
		clockSkew?: string,
		enabled?: boolean,
		entityId?: string,
		forceAuthn?: boolean,
		keepAliveUrl?: string,
		metadataURL?: string,
		nameIdentifierFormat?: string,
		unknownUsersAreStrangers?: boolean
	) {
		await this.goToIdentityProviderConnectionsTab();

		const row = await this.page.getByRole('row').filter({hasText: name});

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {name: 'Edit'}),
			trigger: row.locator('.dropdown-toggle'),
		});

		await this.populateAndSaveIdentityProviderConnectionDetails(
			name,
			clockSkew,
			enabled,
			entityId,
			forceAuthn,
			keepAliveUrl,
			metadataURL,
			nameIdentifierFormat,
			unknownUsersAreStrangers
		);
	}

	async goToIdentityProviderConnectionsTab() {
		await this.applicationsMenuPage.goToSamlAdmin();
		await this.page
			.getByRole('tab', {name: 'Identity Provider Connections'})
			.click();
		expect(
			await this.page.getByRole('button', {name: 'Add Identity Provider'})
		).toBeVisible();
	}

	private async populateAndSaveIdentityProviderConnectionDetails(
		name: string,
		clockSkew?: string,
		enabled?: boolean,
		entityId?: string,
		forceAuthn?: boolean,
		keepAliveUrl?: string,
		metadataURL?: string,
		nameIdentifierFormat?: string,
		unknownUsersAreStrangers?: boolean
	) {
		await this.nameField.fill(name);

		if (clockSkew) {
			await this.clockSkewField.fill(clockSkew);
		}

		if (enabled !== undefined) {
			await this.enabledField.setChecked(enabled);
		}

		if (entityId) {
			await this.entityIdField.fill(entityId);
		}

		if (forceAuthn !== undefined) {
			await this.forceAuthnToggle.setChecked(forceAuthn);
		}

		if (keepAliveUrl) {
			await this.keepAliveUrlField.fill(keepAliveUrl);
		}

		if (metadataURL) {
			await this.metadataUrlField.fill(metadataURL);
		}

		if (nameIdentifierFormat) {
			await this.nameIdentifierFormatField.selectOption(
				nameIdentifierFormat
			);
		}

		if (unknownUsersAreStrangers !== undefined) {
			await this.unknownUsersAreStrangersToggle.setChecked(
				unknownUsersAreStrangers
			);
		}

		await this.saveButton.click();

		expect(await this.successMessage).toBeVisible();
	}
}
