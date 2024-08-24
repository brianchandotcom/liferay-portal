/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

export class SamlAdminPage {
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly enabledField: Locator;
	readonly entityIdField: Locator;
	readonly page: Page;
	readonly samlRoleField: Locator;
	readonly saveButton: Locator;
	readonly successMessage: Locator;

	constructor(page: Page) {
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.enabledField = page.getByText('Enabled');
		this.entityIdField = page.getByLabel('Entity ID');
		this.page = page;
		this.samlRoleField = page.getByLabel('SAML Role');
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.successMessage = page.getByText(
			'Your request completed successfully'
		);
	}

	async configureSAML(
		enabled?: boolean,
		entityId?: string,
		samlRole?: string
	) {
		await this.applicationsMenuPage.goToSamlAdmin(false);

		// We must disable SAML before making configuration changes

		if (await this.enabledField.isChecked()) {

			// Make sure we re-enable unless we explicitly want to disable SAML

			if (enabled === undefined) {
				enabled = true;
			}

			await this.enabledField.setChecked(false);

			await this.saveButton.click();

			await expect(await this.successMessage).toBeVisible();

			await this.page.getByLabel('Close').click();
		}
		else if (enabled === undefined) {
			enabled = false;
		}

		let updated = false;

		if (entityId !== undefined) {
			await this.entityIdField.fill(entityId);

			updated = true;
		}

		if (samlRole !== undefined) {
			await this.samlRoleField.selectOption(samlRole);

			updated = true;
		}

		if (updated) {
			await this.saveButton.click();

			await expect(await this.successMessage).toBeVisible();

			await this.page.getByLabel('Close').click();
		}

		if (enabled) {
			if (entityId !== undefined) {

				// Our existing cert will work if the entityId doesn't change

				await this.createOrReplaceCertificate();
			}

			await this.enabledField.setChecked(enabled);

			await this.saveButton.click();

			await expect(await this.successMessage).toBeVisible();

			await this.page.getByLabel('Close').click();
		}
	}

	private async createOrReplaceCertificate(
		commonName = 'test',
		encryption = false,
		keyAlgorithm = 'RSA',
		keyLength = '2048',
		keyPassword = 'test'
	) {
		const locator = await this.page.getByRole('group', {
			exact: true,
			name: encryption
				? 'Encryption Certificate and Private Key'
				: 'Certificate and Private Key',
		});

		let certificateButton = await locator.getByRole('button', {
			name: 'Create Certificate',
		});

		if (await certificateButton.isHidden()) {
			certificateButton = await locator.getByRole('button', {
				name: 'Replace Certificate',
			});
		}

		await certificateButton.click();

		const frameLocator = await this.page.frameLocator(
			'iframe[title="Certificate and Private Key"]'
		);

		await expect(
			await frameLocator.getByLabel('Common Name')
		).toBeVisible();

		// Sometimes field is not ready for input, wait 500ms before filling

		await this.page.waitForTimeout(500);

		await frameLocator.getByLabel('Common Name').fill(commonName);

		if (encryption) {
			await frameLocator
				.getByLabel('Key Algorithm')
				.selectOption(keyAlgorithm);
		}

		await frameLocator
			.getByLabel('Key Length (Bits)')
			.selectOption(keyLength);
		await frameLocator.getByLabel('Key Password').fill(keyPassword);

		await frameLocator.getByRole('button', {name: 'Save'}).click();
	}
}
