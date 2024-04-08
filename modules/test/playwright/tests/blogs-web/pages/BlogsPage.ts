/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Page, expect} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import {PORTLET_URLS} from '../../../utils/portletUrls';

export class BlogsPage {
	readonly page: Page;
	readonly permissionsFrameLocator: FrameLocator;

	constructor(page: Page) {
		this.page = page;
		this.permissionsFrameLocator = page.frameLocator(
			'iframe[title="Permissions"]'
		);
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.blogs}`
		);
	}

	async goToCreateBlogEntry() {
		await this.page.getByRole('link', {name: 'Add Blog Entry'}).click();
	}

	async goToBlogEntryAction(action: string, title: string) {
		await this.page.getByLabel('More actions').waitFor();

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {
				exact: true,
				name: action,
			}),
			trigger: this.page
				.locator('.card')
				.filter({hasText: title})
				.getByLabel('More actions'),
		});
	}

	async assertBlogEntryPermissions(
		permissions: {enabled: boolean; locator: string}[],
		title: string
	) {
		await this.goToBlogEntryAction('Permissions', title);

		await this.assertPermissions(permissions);
	}

	async assertPermissions(
		permissions: {enabled: boolean; locator: string}[]
	) {
		await this.permissionsFrameLocator
			.locator(permissions[0].locator)
			.waitFor();

		for (const permission of permissions) {
			const permissionCheckbox = this.permissionsFrameLocator.locator(
				permission.locator
			);

			if (permission.enabled) {
				await expect(permissionCheckbox).toBeChecked();
			}
			else {
				await expect(permissionCheckbox).not.toBeChecked();
			}
		}

		await this.permissionsFrameLocator
			.getByRole('button', {name: 'Cancel'})
			.click();
	}
}
