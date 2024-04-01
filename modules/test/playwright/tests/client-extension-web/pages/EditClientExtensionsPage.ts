/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ClientExtensionsPage} from './ClientExtensionsPage';

const editClientExtensionBaseURL = '/group/control_panel/manage?p_p_id=com_liferay_client_extension_web_internal_portlet_ClientExtensionAdminPortlet&_com_liferay_client_extension_web_internal_portlet_ClientExtensionAdminPortlet_mvcRenderCommandName=/client_extension_admin/edit_client_extension_entry&_com_liferay_client_extension_web_internal_portlet_ClientExtensionAdminPortlet_type=';

export class EditClientExtensionsPage {
	readonly clientExtensionsPage: ClientExtensionsPage;
	readonly clientExtensionType: string;
	readonly nameInput: Locator;
	readonly newClientExtensionTypeMenuItem: Locator;
	readonly page: Page;
	readonly publishButton: Locator;

	constructor(page: Page, clientExtensionType: string) {
		this.clientExtensionsPage = new ClientExtensionsPage(page);
		this.clientExtensionType = clientExtensionType;
		this.nameInput = page.getByRole('textbox', {name: 'Name'});
		this.page = page;
		this.publishButton = page.getByRole('button', {
			name: 'Publish',
		});
	}

	async goto() {
		await this.page.goto(`${editClientExtensionBaseURL}${this.clientExtensionType}`);
	}

	async publish() {
		await this.publishButton.click();

		await this.page.waitForLoadState();
	}

}
