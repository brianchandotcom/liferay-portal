/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

export class EditVirtualInstancePage {
	readonly applicationsMenuPage;
	readonly saveButton: Locator;
	readonly page: Page;
	readonly virtualHostField: Locator;
	readonly mailDomainField: Locator;
	readonly maxUsersField: Locator;
	readonly activeToggle: Locator;
	readonly successMessage: Locator;

	constructor(page: Page) {
		this.page = page;
		this.applicationsMenuPage = new ApplicationsMenuPage(page);

		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.virtualHostField = this.page.getByLabel('Virtual Host');
		this.mailDomainField = this.page.getByLabel('Mail Domain');
		this.maxUsersField = this.page.getByLabel('Max Users');
		this.activeToggle = this.page.getByText('Active');

		this.successMessage = page.getByText(
			'Your request completed successfully'
		);
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

	async editVirtualInstance(
		active = true,
		webId: string,
		mailDomain?: string,
		maxUsers?: string,
		virtualHost?: string
	) {
		await this.goto(webId);

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
}
