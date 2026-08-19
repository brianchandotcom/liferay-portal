/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page, expect} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {GlobalMenuPage} from '../product-navigation-applications-menu/GlobalMenuPage';

export class VirtualInstancesPage {
	private addInstanceFrame: FrameLocator;

	readonly addInstanceActive: Locator;
	readonly addInstanceAddButton: Locator;
	readonly addInstanceEmailAddressField: Locator;
	readonly addInstanceMailDomain: Locator;
	readonly addInstanceMaxUsers: Locator;
	readonly addInstancePasswordField: Locator;
	readonly addInstanceScreenNameField: Locator;
	readonly addInstanceVirtualHost: Locator;
	readonly addInstanceVirtualInstanceInitializer: Locator;
	readonly addInstanceWebIdField: Locator;
	readonly globalMenuPage: GlobalMenuPage;
	readonly errorMessage: Locator;
	readonly errorMessageScreenName: Locator;
	readonly errorMessageEmailAddress: Locator;
	readonly errorMessagePassword: Locator;
	readonly newVirtualInstanceButton: Locator;
	readonly page: Page;
	readonly startMessage: Locator;

	constructor(page: Page) {
		this.addInstanceFrame = page.frameLocator(
			'iframe[title="Add Instance"]'
		);

		this.addInstanceActive = this.addInstanceFrame.getByText('Active');
		this.addInstanceAddButton = page.getByText('Add', {exact: true});
		this.addInstanceEmailAddressField =
			this.addInstanceFrame.getByLabel('Email Address');
		this.addInstanceMailDomain =
			this.addInstanceFrame.getByLabel('Mail Domain');
		this.addInstanceMaxUsers =
			this.addInstanceFrame.getByLabel('Max Users');
		this.addInstancePasswordField =
			this.addInstanceFrame.getByLabel('Password');
		this.addInstanceScreenNameField =
			this.addInstanceFrame.getByLabel('Screen Name');
		this.addInstanceVirtualHost =
			this.addInstanceFrame.getByLabel('Virtual Host');
		this.addInstanceVirtualInstanceInitializer =
			this.addInstanceFrame.getByLabel('Virtual Instance Initializer');
		this.addInstanceWebIdField = this.addInstanceFrame.getByLabel('Web ID');
		this.globalMenuPage = new GlobalMenuPage(page);
		this.errorMessage = this.addInstanceFrame.locator('.alert-danger');
		this.errorMessageEmailAddress = this.addInstanceFrame.getByText(
			'The Email Address field is required'
		);
		this.errorMessagePassword = this.addInstanceFrame.getByText(
			'The Password field is required'
		);
		this.errorMessageScreenName = this.addInstanceFrame.getByText(
			'The Screen Name field is required'
		);
		this.newVirtualInstanceButton = page.getByRole('button', {name: 'Add'});
		this.page = page;
		this.startMessage = page.getByText(
			'is being added. You will be notified when it is ready.'
		);
	}

	async addNewVirtualInstance(
		name: string,
		active = true,
		maxUsers = '0',
		virtualInstanceInitializer = ''
	) {
		await this.globalMenuPage.goToHome();
		await this.globalMenuPage.goToControlPanel('Virtual Instances');
		await this.newVirtualInstanceButton.click();

		// Sometimes the frame loads slowly

		await this.page.waitForTimeout(1000);

		await this.addInstanceWebIdField.fill(name);
		await this.addInstanceVirtualHost.fill(name);
		await this.addInstanceMailDomain.fill(name + '.com');
		await this.addInstanceMaxUsers.fill(maxUsers);
		await this.addInstanceActive.setChecked(active);
		await this.addInstanceVirtualInstanceInitializer.selectOption(
			virtualInstanceInitializer
		);

		await Promise.all([
			this.addInstanceAddButton.click(),
			this.page.waitForResponse((response) =>
				response.url().includes('add_instance')
			),
		]);

		await this.page.waitForTimeout(1000);

		// Only wait for Virtual Instance creation if there are no errors

		if (await this.errorMessage.isHidden()) {
			await expect(this.startMessage).toBeVisible({
				timeout: 30 * 1000,
			});

			await this.waitForVirtualInstance(name);
		}
	}

	async addNewVirtualInstanceAndSetupAdminUser(
		name: string,
		screenName: string,
		emailAddress: string,
		password: string,
		active = true,
		maxUsers = '0',
		virtualInstanceInitializer = ''
	) {
		await this.globalMenuPage.goToControlPanel('Virtual Instances');
		await this.newVirtualInstanceButton.click();

		// Sometimes the frame loads slowly

		await this.page.waitForTimeout(1000);

		await this.addInstanceWebIdField.fill(name);
		await this.addInstanceVirtualHost.fill(name);
		await this.addInstanceMailDomain.fill(name + '.com');
		await this.addInstanceMaxUsers.fill(maxUsers);
		await this.addInstanceActive.setChecked(active);
		await this.addInstanceVirtualInstanceInitializer.selectOption(
			virtualInstanceInitializer
		);

		await Promise.all([
			this.addInstanceAddButton.click(),
			this.page.waitForResponse((response) =>
				response.url().includes('add_instance')
			),
		]);
		await this.page.waitForTimeout(1000);

		await expect(this.errorMessageScreenName).toBeVisible();
		await expect(this.errorMessageEmailAddress).toBeVisible();
		await expect(this.errorMessagePassword).toBeVisible();

		await this.addInstanceScreenNameField.fill(screenName);
		await this.addInstanceEmailAddressField.fill(emailAddress);
		await this.addInstancePasswordField.fill(password);

		await Promise.all([
			this.addInstanceAddButton.click(),
			this.page.waitForResponse((response) =>
				response.url().includes('add_instance')
			),
		]);

		await this.page.waitForTimeout(1000);

		await this.waitForVirtualInstance(name);
	}

	async deleteVirtualInstance(name: string) {
		await this.globalMenuPage.goToControlPanel('Virtual Instances');

		const row = await this.page.getByRole('row').filter({hasText: name});

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {name: 'Delete'}),
			trigger: row.getByRole('button', {name: 'Show Actions'}),
		});

		await this.page.getByRole('button', {name: 'Delete'}).waitFor();

		await this.page.getByRole('button', {name: 'Delete'}).click();
	}

	async goto() {
		await this.globalMenuPage.goToControlPanel('Virtual Instances');
	}

	/**
	 * The Add operation runs in a background task, so the row only shows up in
	 * the list once the task completes.
	 */
	async waitForVirtualInstance(name: string) {
		await expect(async () => {
			await this.page.reload();

			await expect(
				this.page.getByRole('row').filter({hasText: name})
			).toBeVisible({timeout: 10 * 1000});
		}).toPass({timeout: 180 * 1000});
	}
}
