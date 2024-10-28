/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

export class PortalDefaultPermissionsConfigurationPage {
	readonly analyticsAdministratorUpdateDiscussionCheckbox: Locator;
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly editDefaultPermissionsFrame: FrameLocator;
	readonly editPageButton: Locator;
	readonly frameSaveButton: Locator;
	readonly guestUpdateDiscussionCheckbox: Locator;
	readonly guestViewCheckbox: Locator;
	readonly ownerUpdateDiscussionCheckbox: Locator;
	readonly page: Page;
	readonly portalDefaultPermissionsSearchContainer: Locator;
	readonly powerUserUpdateDiscussionCheckbox: Locator;
	readonly saveButton: Locator;
	readonly searchInput: Locator;
	readonly siteMemberCustomizeCheckbox: Locator;

	constructor(page: Page) {
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.editDefaultPermissionsFrame = page.frameLocator(
			'iframe[title="Edit Default Permissions"]'
		);
		this.editPageButton = page.getByTestId('edit-Page');
		this.page = page;
		this.portalDefaultPermissionsSearchContainer = page.getByTestId(
			'portal-default-permissions-search-container'
		);
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.searchInput = page.getByPlaceholder(/Search/);

		this.analyticsAdministratorUpdateDiscussionCheckbox =
			this.editDefaultPermissionsFrame
				.getByTestId('analytics-administrator_ACTION_UPDATE_DISCUSSION')
				.getByRole('checkbox');
		this.frameSaveButton = this.editDefaultPermissionsFrame.getByRole(
			'button',
			{name: 'Save'}
		);
		this.guestUpdateDiscussionCheckbox = this.editDefaultPermissionsFrame
			.getByTestId('guest_ACTION_UPDATE_DISCUSSION')
			.getByRole('checkbox');
		this.guestViewCheckbox = this.editDefaultPermissionsFrame
			.getByTestId('guest_ACTION_VIEW')
			.getByRole('checkbox');
		this.ownerUpdateDiscussionCheckbox = this.editDefaultPermissionsFrame
			.getByTestId('owner_ACTION_UPDATE_DISCUSSION')
			.getByRole('checkbox');
		this.powerUserUpdateDiscussionCheckbox =
			this.editDefaultPermissionsFrame
				.getByTestId('power-user_ACTION_UPDATE_DISCUSSION')
				.getByRole('checkbox');
		this.siteMemberCustomizeCheckbox = this.editDefaultPermissionsFrame
			.getByTestId('owner_ACTION_CUSTOMIZE')
			.getByRole('checkbox');
	}

	async goto() {
		await this.applicationsMenuPage.goToDefaultPermissions();
	}
}
