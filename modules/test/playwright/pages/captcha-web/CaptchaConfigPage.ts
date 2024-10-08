/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {waitForAlert} from '../../utils/waitForAlert';
import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

export class CaptchaConfigPage {
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly createAccountCaptchaEnabled: Locator;
	readonly maxChallenges: Locator;
	readonly messageBoardsEditCategoryCaptchaEnabled: Locator;
	readonly messageBoardsEditMessageCaptchaEnabled: Locator;
	readonly captchaEngine: Locator;
	readonly page: Page;
	readonly reCaptchaPublicKey: Locator;
	readonly reCaptchaPrivateKey: Locator;
	readonly reCaptchaScriptURL: Locator;
	readonly reCaptchaNoScriptURL: Locator;
	readonly reCaptchaVerifyURL: Locator;
	readonly saveButton: Locator;
	readonly sendPasswordCaptchaEnabled: Locator;
	readonly simpleCaptchaHeight: Locator;
	readonly simpleCaptchaWidth: Locator;
	readonly updateButton: Locator;

	constructor(page: Page) {
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.createAccountCaptchaEnabled = page.getByText(
			'Create Account CAPTCHA Enabled'
		);
		this.maxChallenges = page.getByLabel('Maximum Challenges');
		this.messageBoardsEditCategoryCaptchaEnabled = page.getByLabel(
			'Message Boards Edit Category'
		);
		this.messageBoardsEditMessageCaptchaEnabled = page.getByLabel(
			'Message Boards Edit Message'
		);
		this.captchaEngine = page.getByLabel('CAPTCHA Engine');
		this.page = page;
		this.reCaptchaPublicKey = page.getByLabel('reCAPTCHA Public Key');
		this.reCaptchaPrivateKey = page.getByLabel('reCAPTCHA Private Key');
		this.reCaptchaScriptURL = page.getByLabel('reCAPTCHA Script URL');
		this.reCaptchaNoScriptURL = page.getByLabel('reCAPTCHA No Script URL');
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.reCaptchaVerifyURL = page.getByLabel('reCAPTCHA Verify URL');
		this.sendPasswordCaptchaEnabled = page.getByText(
			'Send Password CAPTCHA Enabled'
		);
		this.simpleCaptchaHeight = page.getByLabel('Simple CAPTCHA Height');
		this.simpleCaptchaWidth = page.getByLabel('Simple CAPTCHA Width');
		this.updateButton = page.getByRole('button', {name: 'Update'});
	}

	async goTo() {
		await this.applicationsMenuPage.goToSystemSettings();
		await this.page.getByRole('link', {name: 'Security Tools'}).click();

		await this.sendPasswordCaptchaEnabled.waitFor();
	}

	async enableReCaptcha(publicKey: string, privateKey: string) {
		await this.captchaEngine.click();
		await this.page.getByRole('option', {name: 'reCAPTCHA'}).click();
		await this.reCaptchaPublicKey.fill(publicKey);
		await this.reCaptchaPrivateKey.fill(privateKey);
		this.saveConfiguration();
		await waitForAlert(
			this.page,
			`Success:Your request completed successfully.`
		);
	}

	async saveConfiguration() {
		if (await this.page.isVisible('button:has-text("Update")')) {
			this.updateButton.click();

			return;
		}

		this.saveButton.click();
	}
}
