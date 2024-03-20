/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

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
			target: this.page.locator(
				'a:text("Configuration"):near(:text("Import"))'
			),
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

		await this.page.waitForSelector(
			'#selectThemeCSSClientExtension_iframe_',
			{
				state: 'visible',
			}
		);

		const clientExtension = this.page
			.frameLocator('#selectThemeCSSClientExtension_iframe_')
			.getByRole('paragraph')
			.filter({hasText: clientExtensionName});

		const currentClientExtensionInput = this.page.locator(
			'input[name="_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_themeCSSCETExternalReferenceCode"]'
		);

		const currentClientExtensionId =
			await currentClientExtensionInput.inputValue();

		// Prevents flaky errors due to the click event not being immediately available

		do {
			await clientExtension.click();
		} while (
			currentClientExtensionId ===
			(await currentClientExtensionInput.inputValue())
		);

		await this.page
			.getByRole('button', {
				exact: true,
				name: 'Save',
			})
			.click();
	}
}
