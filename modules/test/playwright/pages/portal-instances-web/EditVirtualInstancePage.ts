/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

export class EditVirtualInstancePage {
	readonly activeToggle: Locator;
	readonly applicationsMenuPage;
	readonly mailDomainField: Locator;
	readonly maxUsersField: Locator;
	readonly page: Page;
	readonly saveButton: Locator;
	readonly successMessage: Locator;
	readonly virtualHostField: Locator;

	constructor(page: Page) {
		this.activeToggle = this.page.getByText('Active');
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.mailDomainField = this.page.getByLabel('Mail Domain');
		this.maxUsersField = this.page.getByLabel('Max Users');
		this.page = page;
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.successMessage = page.getByText(
			'Your request completed successfully'
		);
		this.virtualHostField = this.page.getByLabel('Virtual Host');
	}

	async editVirtualInstance(
		webId: string,
		active = true,
		mailDomain?: string,
		maxUsers?: string,
		virtualHost?: string
	) {
		await this.goto(webId);

		// add little timeout in order to select "active" text from toggle and not from search container located in prior page

		await this.page.waitForTimeout(500);

		await this.activeToggle.setChecked(active);

		if (mailDomain) {
			await this.mailDomainField.fill(mailDomain);
		}

		if (maxUsers) {
			await this.maxUsersField.fill(maxUsers);
		}

		if (virtualHost) {
			await this.virtualHostField.fill(virtualHost);
		}

		await this.saveButton.click();
		await expect(await this.successMessage).toBeVisible();
	}

	async goto(webId: string) {
		await this.applicationsMenuPage.goToVirtualInstances();

		const row = await this.page.getByRole('row').filter({hasText: webId});

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {name: 'Edit'}),
			trigger: row.getByRole('button', {name: 'Show Actions'}),
		});
	}
}
