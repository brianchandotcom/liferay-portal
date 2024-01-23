/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ClientExtensionsPage} from './ClientExtensionsPage';

export class NewEditorConfigContributorPage {
	readonly aiCreatorEditorToolbarButton: Locator;
	readonly clientExtensionsPage: ClientExtensionsPage;
	readonly descriptionEditable: Locator;
	readonly editorConfigKeysInput: Locator;
	readonly editorNamesInput: Locator;
	readonly nameInput: Locator;
	readonly portletNamesInput: Locator;
	readonly publishButton: Locator;
	readonly page: Page;
	readonly urlInput: Locator;

	constructor(page: Page) {
		this.clientExtensionsPage = new ClientExtensionsPage(page);
		this.page = page;

		const portletId =
			'_com_liferay_client_extension_web_internal_portlet_ClientExtensionAdminPortlet';

		this.nameInput = page.locator(`#${portletId}_name`);

		const descriptionIframe = page.frameLocator(
			`#cke_${portletId}_description iframe`
		);

		this.descriptionEditable = descriptionIframe.locator('.cke_editable');

		this.aiCreatorEditorToolbarButton =
			page.getByTitle('Create AI Content');
		this.urlInput = page.locator(`#${portletId}_url`);
		this.portletNamesInput = page.locator(
			`[name=${portletId}_portletNames]`
		);
		this.editorNamesInput = page.locator(`[name=${portletId}_editorNames]`);
		this.editorConfigKeysInput = page.locator(
			`[name=${portletId}_editorConfigKeys]`
		);

		this.publishButton = page.getByRole('button', {name: 'Publish'});
	}

	async goto() {
		await this.clientExtensionsPage.gotoNewEditorConfigContributorPage();
	}
}
