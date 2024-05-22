/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ApiHelpers, DataApiHelpers} from '../../helpers/ApiHelpers';
import getPageDefinition from '../../tests/layout-content-page-editor-web/utils/getPageDefinition';
import getWidgetDefinition from '../../tests/layout-content-page-editor-web/utils/getWidgetDefinition';
import getRandomString from '../../utils/getRandomString';

export class ContactsCenterPage {
	readonly addContactButton: Locator;
	readonly emailAddressInput: Locator;
	readonly nameInput: Locator;
	readonly page: Page;
	readonly saveButton: Locator;
	readonly successMessage: Locator;

	constructor(page: Page) {
		this.addContactButton = page.getByRole('button', {
			exact: true,
			name: 'Add Contact',
		});
		this.emailAddressInput = page.getByLabel('Email Address');
		this.nameInput = page.getByLabel('Name');
		this.page = page;
		this.saveButton = page.getByRole('button', {exact: true, name: 'Save'});
		this.successMessage = page.getByText(
			'You have successfully added a new contact'
		);
	}

	async createPage(
		apiHelpers: ApiHelpers | DataApiHelpers,
		siteId: number | string,
		options?: {title?: string}
	) {
		options = {
			title: getRandomString(),
			...(options || {}),
		};

		return await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				getWidgetDefinition({
					id: getRandomString(),
					widgetName:
						'com_liferay_contacts_web_portlet_ContactsCenterPortlet',
				}),
			]),
			siteId: String(siteId),
			title: options.title,
		});
	}
}
