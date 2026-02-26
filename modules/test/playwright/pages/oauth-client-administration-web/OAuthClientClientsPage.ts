/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {GlobalMenuPage} from '../product-navigation-applications-menu/GlobalMenuPage';

export class OAuthClientClientsPage {
	readonly addOAuthClientButton: Locator;
	readonly customClaimInput: Locator;
	readonly customFieldInput: Locator;
	readonly globalMenuPage: GlobalMenuPage;
	readonly infoJSON: Locator;
	readonly matcherField: Locator;
	readonly oAuthClientsTable: Locator;
	readonly oidcUserInfoMapperJSON: Locator;
	readonly page: Page;
	readonly saveButton: Locator;
	readonly wellKnownURI: Locator;

	constructor(page: Page) {
		this.addOAuthClientButton = page.getByRole('link', {
			name: 'Add OAuth Client',
		});
		this.customClaimInput = page.getByRole('textbox', {
			name: 'Custom Claim',
		});
		this.customFieldInput = page.getByLabel('User Custom Fields');
		this.globalMenuPage = new GlobalMenuPage(page);
		this.infoJSON = page.getByRole('textbox', {
			name: 'OAuth Client Information',
		});
		this.matcherField = page.getByLabel('Matcher Field Required The "');
		this.oAuthClientsTable = page.locator(
			'#_com_liferay_oauth_client_admin_web_internal_portlet_OAuthClientAdminPortlet_oAuthClientEntrySearchContainer'
		);
		this.oidcUserInfoMapperJSON = page.getByRole('textbox', {
			name: 'OpenId Connect User',
		});
		this.page = page;
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.wellKnownURI = page.getByRole('textbox', {
			name: 'Authorization Server Well-',
		});
	}

	async addOAuthClientEntry(
		oauthClientEntry: TOAuthClientEntry,
		dialogMessage?: string
	) {
		await this.addOAuthClientButton.click();

		await expect(this.wellKnownURI).toBeVisible();

		if (oauthClientEntry.authServerWellKnownURI) {
			await this.wellKnownURI.fill(
				oauthClientEntry.authServerWellKnownURI
			);
		}

		if (oauthClientEntry.infoJSON) {
			await this.fillJSON(this.infoJSON, oauthClientEntry.infoJSON);
		}

		if (oauthClientEntry.matcherField) {
			await this.matcherField.selectOption(oauthClientEntry.matcherField);
		}

		if (oauthClientEntry.customClaims) {
			oauthClientEntry.customClaims = await this.fillCustomClaims(
				oauthClientEntry.customClaims
			);
		}

		if (oauthClientEntry.oidcUserInfoMapperJSON) {
			await this.fillJSON(
				this.oidcUserInfoMapperJSON,
				oauthClientEntry.oidcUserInfoMapperJSON
			);
		}

		if (dialogMessage) {
			this.page.once('dialog', (dialog) => {
				expect(dialog.message()).toBe(dialogMessage);

				dialog.accept();
			});

			await this.saveButton.click();

			return null;
		}

		await this.saveButton.click();

		await expect(
			this.page.getByText('Your request completed successfully')
		).toBeVisible();

		await this.page.getByLabel('Close').click();

		return oauthClientEntry;
	}

	async checkCustomClaimValues(customClaim: TCustomClaim) {
		await expect(
			this.page.locator(
				`select[id*="customClaimsKey"]:has(option:checked:text("${customClaim.expandoColumnName}"))`
			)
		).toBeVisible();

		await expect(
			this.page.locator(
				`input[id*="customClaimsValue"][value="${customClaim.oidcProviderCustomClaim}"]`
			)
		).toBeVisible();
	}

	async deleteOAuthClientEntries() {
		await this.oAuthClientsTable.waitFor();

		const row = this.oAuthClientsTable.getByRole('row').last();

		while (await row.isVisible()) {
			this.page.once('dialog', (dialog) => {
				dialog.accept();
			});

			await clickAndExpectToBeVisible({
				autoClick: true,
				target: this.page.getByRole('link', {name: 'Delete'}),
				trigger: row.locator('.dropdown-toggle'),
			});

			await expect(
				this.page.getByText('Your request completed successfully')
			).toBeVisible();

			await this.page.getByLabel('Close').click();
		}
	}

	async fillCustomClaims(customClaims: TCustomClaim[]) {
		let index = 0;

		for (const customClaim of customClaims) {
			await this.page.getByRole('button', {name: 'Add'}).first().click();

			await expect(
				this.page.locator(
					`[id="_com_liferay_oauth_client_admin_web_internal_portlet_OAuthClientAdminPortlet_customClaimsKey-${index + 1}"]`
				)
			).toBeVisible();

			await this.page
				.locator(
					`[id="_com_liferay_oauth_client_admin_web_internal_portlet_OAuthClientAdminPortlet_customClaimsKey-${index}"]`
				)
				.selectOption(customClaim.expandoColumnName);

			await this.page
				.locator(
					`[id="_com_liferay_oauth_client_admin_web_internal_portlet_OAuthClientAdminPortlet_customClaimsValue-${index}"]`
				)
				.fill(customClaim.oidcProviderCustomClaim);

			customClaim.index = index;

			index++;
		}

		return customClaims;
	}

	async fillJSON(jsonLocator: Locator, incomingData: any) {
		await jsonLocator.fill(
			JSON.stringify(
				await this.updateJSON(
					JSON.parse(await jsonLocator.inputValue()),
					incomingData
				),
				null,
				2
			)
		);
	}

	async goTo() {
		await this.globalMenuPage.goToControlPanel(
			'OAuth Client Administration'
		);

		await expect(this.addOAuthClientButton).toBeVisible();
	}

	async goToEntry(oauthClientEntry: TOAuthClientEntry) {
		await this.goTo();

		await this.page
			.getByRole('link', {name: oauthClientEntry.infoJSON?.client_id})
			.click();

		expect(this.wellKnownURI).toBeVisible();
	}

	async updateJSON(jsonObject: any, incomingData: any) {
		for (const [key, value] of Object.entries(incomingData)) {
			if (value !== undefined && value !== null) {
				if (
					typeof jsonObject[key] === 'object' &&
					!Array.isArray(value)
				) {
					await this.updateJSON(jsonObject[key], value);
				}
				else {
					jsonObject[key] = value;
				}
			}
		}

		return jsonObject;
	}
}
