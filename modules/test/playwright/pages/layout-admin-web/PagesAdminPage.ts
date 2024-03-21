/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {clickAndExpectToBeHidden} from '../../utils/clickAndExpectToBeHidden';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {PORTLET_URLS} from '../../utils/portletUrls';

export class PagesAdminPage {
	readonly page: Page;

	constructor(page: Page) {
		this.page = page;
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.pages}`
		);
	}

	async gotoPagesConfiguration() {
		await this.goto();

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {name: 'Configuration'}),
			trigger: this.page.getByTestId('headerOptions'),
		});
	}

	async selectThemeCSSClientExtension(clientExtensionName: string) {
		await this.gotoPagesConfiguration();

		await this.page
			.locator(
				'#_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_themeCSSReplacementExtension'
			)
			.click();

		const iframe = this.page.locator(
			'#selectThemeCSSClientExtension_iframe_'
		);

		await iframe.waitFor({
			state: 'visible',
		});

		const clientExtension = this.page
			.frameLocator('#selectThemeCSSClientExtension_iframe_')
			.getByTestId('rowItemContent')
			.filter({hasText: clientExtensionName});

		await clickAndExpectToBeHidden({
			target: iframe,
			trigger: clientExtension,
		});

		await this.page
			.getByRole('button', {
				exact: true,
				name: 'Save',
			})
			.click();
	}

	async selectPageAndChangePermissions(
		pageName: string,
		permissionIds: string[]
	) {
		// Select the page

		const pageInput = await this.page.getByLabel(`Select ${pageName}`, {
			exact: true,
		});

		await pageInput.check({trial: true});
		await pageInput.check({timeout: 1000});

		// Open the permissions modal

		await this.page.getByRole('button', {name: 'Permissions'}).click();

		await this.page.waitForTimeout(3000);

		// Check the permissions

		for (const permissionId of permissionIds) {
			const permission = await this.page
				.frameLocator('iframe[title="Permissions"]')
				.locator(`#${permissionId}`);

			await permission.uncheck({trial: true});
			await permission.uncheck({timeout: 1000});
		}

		// Save and close the modal

		await this.page
			.frameLocator('iframe[title="Permissions"]')
			.getByRole('button', {name: 'Save'})
			.click();

		await this.page.waitForTimeout(3000);

		await this.page.getByLabel('close', {exact: true}).click();
	}
}
