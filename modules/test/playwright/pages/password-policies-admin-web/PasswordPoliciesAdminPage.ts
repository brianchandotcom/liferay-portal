/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {TPasswordPolicy} from '../../helpers/PasswordPolicyApiHelper';
import {GlobalMenuPage} from '../product-navigation-applications-menu/GlobalMenuPage';

export class PasswordPoliciesAdminPage {
	readonly allowDictionaryWordsToggle: Locator;
	readonly globalMenuPage: GlobalMenuPage;
	readonly changeableToggle: Locator;
	readonly checkSyntaxToggle: Locator;
	readonly description: Locator;
	readonly expireable: Locator;
	readonly historyToggle: Locator;
	readonly lockout: Locator;
	readonly minAlphanumeric: Locator;
	readonly minLength: Locator;
	readonly minLowerCase: Locator;
	readonly minNumbers: Locator;
	readonly minSymbols: Locator;
	readonly minUpperCase: Locator;
	readonly name: Locator;
	readonly newButton: Locator;
	readonly page: Page;
	readonly regex: Locator;
	readonly resetTicketMaxAge: Locator;
	readonly saveButton: Locator;
	readonly successMessage: Locator;

	constructor(page: Page) {
		this.allowDictionaryWordsToggle = page.getByLabel(
			"Allow Dictionary Words If this is checked, common dictionary words are allowed as the user's passwords.",
			{exact: true}
		);
		this.globalMenuPage = new GlobalMenuPage(page);
		this.changeableToggle = page.getByLabel(
			'Changeable If this is checked, the user can change their password.',
			{exact: true}
		);
		this.checkSyntaxToggle = page.getByLabel(
			'Enable Syntax Checking If this is checked, the password is read for certain words and/or a certain length.',
			{exact: true}
		);
		this.description = page.locator(
			'[id="_com_liferay_password_policies_admin_web_portlet_PasswordPoliciesAdminPortlet_description"]'
		);
		this.expireable = page.getByLabel(
			'Enable Expiration If this is checked, the user must change their password after a given amount of time.',
			{exact: true}
		);
		this.historyToggle = page.getByLabel(
			"Enable History If this is checked, the portal keeps a history of the user's previous passwords and prevents them from reusing an old password.",
			{exact: true}
		);
		this.lockout = page.getByLabel(
			'Enable Lockout If this is checked, a user can attempt to log in a certain number of times before their account is locked.',
			{exact: true}
		);
		this.minAlphanumeric = page.getByLabel(
			"Minimum Alpha Numeric This determines the minimum number of alpha numeric letters in the user's password.",
			{exact: true}
		);
		this.minLength = page.getByLabel(
			"Minimum Length This determines the minimum length of the user's password.",
			{exact: true}
		);
		this.minLowerCase = page.getByLabel(
			"Minimum Lower Case This determines the minimum number of lowercase letters in the user's password.",
			{exact: true}
		);
		this.minNumbers = page.getByLabel(
			"Minimum Numbers This determines the minimum number of numbers in the user's password.",
			{exact: true}
		);
		this.minSymbols = page.getByLabel(
			"Minimum Symbols This determines the minimum number of symbols in the user's password.",
			{exact: true}
		);
		this.minUpperCase = page.getByLabel(
			"Minimum Upper Case This determines the minimum number of uppercase letters in the user's password.",
			{exact: true}
		);
		this.name = page.getByLabel('Name');
		this.newButton = page.getByRole('link', {name: 'Add'});
		this.page = page;
		this.regex = page
			.getByLabel(
				"Enable Syntax Checking If this is checked, the password is read for certain words and/or a certain length. Allow Dictionary Words If this is checked, common dictionary words are allowed as the user's passwords. Minimum Alpha Numeric This determines the minimum number of alpha numeric letters in the user's password. Minimum Length This determines the minimum length of the user's password. Minimum Lower Case This determines the minimum number of lowercase letters in the user's password. Minimum Numbers This determines the minimum number of numbers in the user's password. Minimum Symbols This determines the minimum number of symbols in the user's password. Minimum Upper Case This determines the minimum number of uppercase letters in the user's password. Regular Expression This defines the regular expression used to validate the user's password. Characters Maximum: 4000 0/"
			)
			.getByLabel('Characters Maximum:');
		this.resetTicketMaxAge = page.getByLabel('Reset Ticket Max Age');
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.successMessage = page.getByText(
			'Your request completed successfully'
		);
	}

	async createPasswordPolicy(passwordPolicy: TPasswordPolicy) {
		await this.newButton.click();
		await this.name.fill(passwordPolicy.name);

		if (passwordPolicy.checkSyntaxToggle) {
			await this.page
				.getByRole('button', {name: 'Password Syntax Checking'})
				.click();
			await this.checkSyntaxToggle.setChecked(
				passwordPolicy.checkSyntaxToggle
			);
		}

		if (passwordPolicy.allowDictionaryWordsToggle !== undefined) {
			await this.allowDictionaryWordsToggle.setChecked(
				passwordPolicy.allowDictionaryWordsToggle
			);
		}

		if (passwordPolicy.minAlphanumeric) {
			await this.minAlphanumeric.fill(
				String(passwordPolicy.minAlphanumeric)
			);
		}

		if (passwordPolicy.minLength) {
			await this.minLength.fill(String(passwordPolicy.minLength));
		}

		if (passwordPolicy.minLowerCase) {
			await this.minLowerCase.fill(String(passwordPolicy.minLowerCase));
		}

		if (passwordPolicy.minNumbers) {
			await this.minNumbers.fill(String(passwordPolicy.minNumbers));
		}

		if (passwordPolicy.minSymbols) {
			await this.minSymbols.fill(String(passwordPolicy.minSymbols));
		}

		if (passwordPolicy.minUpperCase) {
			await this.minUpperCase.fill(String(passwordPolicy.minUpperCase));
		}

		await this.saveButton.click();

		await expect(await this.successMessage).toBeVisible();
	}

	async editDefaultPasswordPolicy(passwordPolicy: TPasswordPolicy) {
		await this.page
			.getByRole('link', {name: 'Default Password Policy'})
			.click();

		if (passwordPolicy.checkSyntaxToggle) {
			await expect(async () => {
				await expect(
					this.page.getByRole('button', {
						name: 'Password Syntax Checking',
					})
				).toBeVisible({timeout: 500});

				await this.page
					.getByRole('button', {name: 'Password Syntax Checking'})
					.click({timeout: 500});
			}).toPass({timeout: 5000});

			await this.checkSyntaxToggle.setChecked(
				passwordPolicy.checkSyntaxToggle
			);
		}

		if (passwordPolicy.allowDictionaryWordsToggle !== undefined) {
			await this.allowDictionaryWordsToggle.setChecked(
				passwordPolicy.allowDictionaryWordsToggle
			);
		}

		if (passwordPolicy.minAlphanumeric) {
			await this.minAlphanumeric.fill(
				String(passwordPolicy.minAlphanumeric)
			);
		}

		if (passwordPolicy.minLength) {
			await this.minLength.fill(String(passwordPolicy.minLength));
		}

		if (passwordPolicy.minLowerCase) {
			await this.minLowerCase.fill(String(passwordPolicy.minLowerCase));
		}

		if (passwordPolicy.minNumbers) {
			await this.minNumbers.fill(String(passwordPolicy.minNumbers));
		}

		if (passwordPolicy.minSymbols) {
			await this.minSymbols.fill(String(passwordPolicy.minSymbols));
		}

		if (passwordPolicy.minUpperCase) {
			await this.minUpperCase.fill(String(passwordPolicy.minUpperCase));
		}

		await this.saveButton.click();

		await expect(await this.successMessage).toBeVisible();
	}

	async goTo() {
		await this.globalMenuPage.goToControlPanel('Password Policies');
	}

	async resetDefaultPasswordPolicy() {
		await this.page
			.getByRole('link', {name: 'Default Password Policy'})
			.click();
		await this.page
			.getByRole('button', {name: 'Password Syntax Checking'})
			.click();
		await this.allowDictionaryWordsToggle.check();
		await this.minLength.fill(String(6));
		await this.minLowerCase.fill(String(0));
		await this.minNumbers.fill(String(1));
		await this.minSymbols.fill(String(0));
		await this.minUpperCase.fill(String(1));
		await this.checkSyntaxToggle.uncheck();

		await this.saveButton.click();

		await expect(this.successMessage).toBeVisible();
	}
}
