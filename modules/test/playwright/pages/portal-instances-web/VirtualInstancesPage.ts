/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page, expect} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

export class VirtualInstancesPage {
	readonly addInstanceActive: Locator;
	readonly addInstanceAddButton: Locator;
	readonly addInstanceMailDomain: Locator;
	readonly addInstanceMaxUsers: Locator;
	readonly addInstanceVirtualHost: Locator;
	readonly addInstanceVirtualInstanceInitializer: Locator;
	readonly addInstanceWebIdField: Locator;
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly errorMessage: Locator;
	readonly newVirtualInstanceButton: Locator;
	readonly page: Page;
	readonly successMessage: Locator;

	private addInstanceFrame: FrameLocator;

	constructor(page: Page) {
		this.addInstanceAddButton = page.getByText('Add', {exact: true});
		this.addInstanceFrame = page.frameLocator(
			'iframe[title="Add Instance"]'
		);

		this.addInstanceActive = this.addInstanceFrame.getByText('Active');

		this.addInstanceMailDomain =
			this.addInstanceFrame.getByLabel('Mail Domain');
		this.addInstanceMaxUsers =
			this.addInstanceFrame.getByLabel('Max Users');
		this.addInstanceVirtualHost =
			this.addInstanceFrame.getByLabel('Virtual Host');
		this.addInstanceVirtualInstanceInitializer =
			this.addInstanceFrame.getByLabel('Virtual Instance Initializer');
		this.addInstanceWebIdField = this.addInstanceFrame.getByLabel('Web ID');
		this.applicationsMenuPage = new ApplicationsMenuPage(page);

		this.errorMessage = this.addInstanceFrame.getByText(
			'Error:Please enter a valid'
		);

		this.newVirtualInstanceButton = page.getByRole('button', {name: 'Add'});
		this.page = page;
		this.successMessage = page.getByText(
			'Your request completed successfully'
		);
	}

	async addNewVirtualInstance(
		name: string,
		active = true,
		maxUsers = '0',
		virtualInstanceInitializer = ''
	) {
		await this.applicationsMenuPage.goToVirtualInstances();
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
		await this.addInstanceAddButton.click();

		await this.page.waitForTimeout(1000);

		// Only wait for Virtual Instance creation if there are no errors

		if (await this.errorMessage.isHidden()) {
			await expect(await this.successMessage).toBeVisible({
				timeout: 180 * 1000,
			});
			await this.page.getByLabel('Close').click();
		}
	}

	async deleteVirtualInstance(name: string) {
		await this.applicationsMenuPage.goToVirtualInstances();

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
		await this.applicationsMenuPage.goToVirtualInstances();
	}
}
