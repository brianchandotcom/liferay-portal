/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

export class PortalDefaultPermissionsSiteConfigurationPage {
	readonly actionsPageButton: Locator;
	readonly analyticsAdministratorUpdateDiscussionCheckbox: Locator;
	readonly defaultPermissionsLink: Locator;
	readonly editDefaultPermissionsFrame: FrameLocator;
	readonly editPageButton: Locator;
	readonly frameSaveButton: Locator;
	readonly page: Page;
	readonly portalDefaultPermissionsSearchContainer: Locator;
	readonly powerUserUpdateDiscussionCheckbox: Locator;
	readonly resetPageButton: Locator;
	readonly saveButton: Locator;

	constructor(page: Page) {
		this.actionsPageButton = page
			.getByTestId('actions-Page')
			.getByRole('button');
		this.editDefaultPermissionsFrame = page.frameLocator(
			'iframe[title="Edit Default Permissions"]'
		);
		this.editPageButton = page.getByTestId('edit-Page');
		this.defaultPermissionsLink = page.getByRole('link', {
			exact: true,
			name: 'Default Permissions',
		});
		this.page = page;
		this.portalDefaultPermissionsSearchContainer = page.getByTestId(
			'portal-default-permissions-search-container'
		);
		this.resetPageButton = page.getByTestId('reset-Page');
		this.saveButton = page.getByRole('button', {name: 'Save'});

		this.analyticsAdministratorUpdateDiscussionCheckbox =
			this.editDefaultPermissionsFrame
				.getByTestId('analytics-administrator_ACTION_UPDATE_DISCUSSION')
				.getByRole('checkbox');
		this.frameSaveButton = this.editDefaultPermissionsFrame.getByRole(
			'button',
			{name: 'Save'}
		);
		this.powerUserUpdateDiscussionCheckbox =
			this.editDefaultPermissionsFrame
				.getByTestId('power-user_ACTION_UPDATE_DISCUSSION')
				.getByRole('checkbox');
	}

	async goto(siteName: string) {
		await this.page.goto(
			`/group/${siteName}/~/control_panel/manage/-/site/settings`
		);
		await this.defaultPermissionsLink.click();
	}
}
