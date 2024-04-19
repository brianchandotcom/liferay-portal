/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {clickAndExpectToBeHidden} from '../../utils/clickAndExpectToBeHidden';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {PORTLET_URLS} from '../../utils/portletUrls';
import {waitForSuccessAlert} from '../../utils/waitForSuccessAlert';

export class PagesAdminPage {
	readonly configurationSaveButton: Locator;
	readonly javaScriptClientExtensionsTab: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.configurationSaveButton = page.getByRole('button', {
			exact: true,
			name: 'Save',
		});
		this.javaScriptClientExtensionsTab = page.getByRole('tab', {
			name: 'JavaScript',
		});
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

	async selectJavaScriptClientExtension(clientExtensionName: string) {
		await this.gotoPagesConfiguration();

		await this.javaScriptClientExtensionsTab.click();

		await this.page
			.getByRole('button', {name: 'Add JavaScript Client Extensions'})
			.click();

		await this.page.getByRole('menuitem', {name: 'In Page Head'}).click();

		const iframe = this.page.frameLocator('#selectGlobalJSCETs_iframe_');

		// Wait for "Select Items" checkbox label to be visible which occurs when JavaScript hydration is complete.

		await iframe.getByText('Select Items').waitFor({state: 'visible'});

		await iframe.getByLabel(clientExtensionName).check();

		const addButton = this.page.getByRole('button', {
			exact: true,
			name: 'Add',
		});

		const clientExtensionEntry = this.page.getByRole('cell', {
			name: clientExtensionName,
		});

		await clickAndExpectToBeVisible({
			target: clientExtensionEntry,
			trigger: addButton,
		});

		await this.configurationSaveButton.click();

		await waitForSuccessAlert(this.page);
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

		await this.configurationSaveButton.click();
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
