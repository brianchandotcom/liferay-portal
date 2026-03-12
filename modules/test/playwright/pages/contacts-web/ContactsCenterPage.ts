/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ApiHelpers, DataApiHelpers} from '../../helpers/ApiHelpers';
import getPageDefinition from '../../tests/layout-content-page-editor-web/main/utils/getPageDefinition';
import getWidgetDefinition from '../../tests/layout-content-page-editor-web/main/utils/getWidgetDefinition';
import getRandomString from '../../utils/getRandomString';

export class ContactsCenterPage {
	readonly addContactButton: Locator;
	readonly allUsersCount: Locator;
	readonly connectionsCount: Locator;
	readonly contactContainer: Locator;
	readonly contactList: Locator;
	readonly deleteButton: Locator;
	readonly editButton: Locator;
	readonly emailAddressInput: Locator;
	readonly followersCount: Locator;
	readonly followingCount: Locator;
	readonly myContactsCount: Locator;
	readonly nameInput: Locator;
	readonly page: Page;
	readonly saveButton: Locator;
	readonly successMessage: Locator;
	readonly updateSuccessMessage: Locator;

	constructor(page: Page) {
		this.addContactButton = page.getByRole('button', {
			exact: true,
			name: 'Add Contact',
		});
		this.allUsersCount = page.getByText(/View (all \d+ users|one user)/);
		this.connectionsCount = page.getByText(/You have \d+ connections?\./);
		this.contactContainer = page.locator('.contacts-container');
		this.contactList = page.locator('.contacts-list');
		this.deleteButton = page.getByRole('button', {
			exact: true,
			name: 'Delete',
		});
		this.editButton = page.getByRole('button', {
			exact: true,
			name: 'Edit',
		});
		this.emailAddressInput = page.getByLabel('Email Address');
		this.followersCount = page.getByText(/You have \d+ followers?\./);
		this.followingCount = page.getByText(/You are following \d+ people\./);
		this.myContactsCount = page.getByText(
			/View (my \d+ contacts|one contact)\./
		);
		this.nameInput = page.getByLabel('Name');
		this.page = page;
		this.saveButton = page.getByRole('button', {exact: true, name: 'Save'});
		this.successMessage = page.getByText(
			'You have successfully added a new contact'
		);
		this.updateSuccessMessage = page.getByText(
			'You have successfully updated the contact'
		);
	}

	contactContainerText(text: string) {
		return this.contactContainer.getByText(text).first();
	}

	contactListItem(name: string) {
		return this.contactList.getByText(name, {exact: true}).first();
	}

	async createPage(
		apiHelpers: ApiHelpers | DataApiHelpers,
		siteId: number | string,
		options?: {title?: string; widgetConfig?: Record<string, any>}
	) {
		options = {
			title: getRandomString(),
			...(options || {}),
		};

		return await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				getWidgetDefinition({
					id: getRandomString(),
					widgetConfig: options.widgetConfig,
					widgetName:
						'com_liferay_contacts_web_portlet_ContactsCenterPortlet',
				}),
			]),
			siteId: String(siteId),
			title: options.title,
		});
	}
}
