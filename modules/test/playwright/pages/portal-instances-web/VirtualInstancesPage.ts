/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page, expect} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

export class VirtualInstancesPage {
	readonly applicationsMenuPage;
	readonly newVirtualInstanceButton: Locator;
	readonly page: Page;
	private addInstanceFrame: FrameLocator;
	readonly addInstanceWebIdField: Locator;
	readonly addInstanceVirtualHost: Locator;
	readonly addInstanceMailDomain: Locator;
	readonly addInstanceMaxUsers: Locator;
	readonly addInstanceActive: Locator;
	readonly addInstanceVirtualInstanceInitializer: Locator;
	readonly addInstanceAddButton: Locator;
	readonly successMessage: Locator;

	constructor(page: Page) {
		this.page = page;
		this.applicationsMenuPage = new ApplicationsMenuPage(page);

		this.newVirtualInstanceButton = page.getByRole('button', {name: 'Add'});
		this.addInstanceFrame = page.frameLocator(
			'iframe[title="Add Instance"]'
		);
		this.addInstanceWebIdField = this.addInstanceFrame.getByLabel('Web ID');
		this.addInstanceVirtualHost =
			this.addInstanceFrame.getByLabel('Virtual Host');
		this.addInstanceMailDomain =
			this.addInstanceFrame.getByLabel('Mail Domain');
		this.addInstanceMaxUsers =
			this.addInstanceFrame.getByLabel('Max Users');
		this.addInstanceActive = this.addInstanceFrame.getByText('Active');
		this.addInstanceVirtualInstanceInitializer =
			this.addInstanceFrame.getByLabel('Virtual Instance Initializer');
		this.addInstanceAddButton = page.getByText('Add', {exact: true});

		this.successMessage = page.getByText(
			'Your request completed successfully'
		);
	}

	async addNewVirtualInstance(
		active = true,
		maxUsers = '0',
		name: string,
		virtualInstanceInitializer = ''
	) {
		await this.applicationsMenuPage.goToVirtualInstances();
		await this.newVirtualInstanceButton.click();

		// Sometimes the frame loads slowly

		await this.page.waitForTimeout(500);

		await this.addInstanceWebIdField.fill(name);
		await this.addInstanceVirtualHost.fill(name);
		await this.addInstanceMailDomain.fill(name + '.com');
		await this.addInstanceMaxUsers.fill(maxUsers);
		await this.addInstanceActive.setChecked(active);
		await this.addInstanceVirtualInstanceInitializer.selectOption(
			virtualInstanceInitializer
		);
		await this.addInstanceAddButton.click();

		await expect(await this.successMessage).toBeVisible({
			timeout: 120 * 1000,
		});
	}

	async deleteVirtualInstance(name: string) {
		await this.applicationsMenuPage.goToVirtualInstances();

		const row = await this.page.getByRole('row').filter({hasText: name});

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {name: 'Delete'}),
			trigger: row.getByRole('button', {name: 'Show Actions'}),
		});

		await this.page.getByRole('button', {name: 'Delete'}).click();
	}
}
