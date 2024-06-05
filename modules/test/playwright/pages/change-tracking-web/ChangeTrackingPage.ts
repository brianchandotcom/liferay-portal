/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {PORTLET_URLS} from '../../utils/portletUrls';

export class ChangeTrackingPage {
	readonly page: Page;
	readonly reviewChangesButton: Locator;
	readonly tabsContainer: Locator;

	constructor(page: Page) {
		this.page = page;
		this.reviewChangesButton = page.getByRole('menuitem', {
			name: 'Review Changes',
		});
		this.tabsContainer = page.locator('nav.navbar');
	}

	async goto() {
		await this.page.goto(`/group/guest${PORTLET_URLS.publications}`);
	}

	async goToReviewChanges(title: string) {
		await this.goto();

		await this.page
			.locator('#fnsd___table-id div')
			.filter({hasText: title})
			.first()
			.waitFor();

		await this.page.getByRole('link', {exact: true, name: title}).click();

		await this.page
			.locator(
				'#_com_liferay_change_tracking_web_portlet_PublicationsPortlet_controlMenu'
			)
			.filter({hasText: 'Review Changes'})
			.waitFor();
	}

	async goToReviewChangesHistory(title: string) {
		await this.goto();

		await this.page
			.locator('li[data-nav-item-index="2"] a span')
			.filter({hasText: 'History'})
			.first()
			.click();

		await this.page
			.locator('#fnsd___table-id div')
			.filter({hasText: title})
			.first()
			.waitFor();

		await this.page.getByRole('link', {exact: true, name: title}).click();

		await this.page
			.locator(
				'#_com_liferay_change_tracking_web_portlet_PublicationsPortlet_controlMenu'
			)
			.filter({hasText: 'Review Changes'})
			.waitFor();
	}

	async reviewChange(title: string) {
		await this.page.getByRole('link', {name: title}).first().click();

		await this.page.locator('h2').filter({hasText: title}).waitFor();
	}

	async selectTab(tabLabel: string) {
		const tabLink = this.tabsContainer.locator('a', {
			hasText: tabLabel,
		});

		await tabLink.click();

		await tabLink.and(this.page.locator('.active')).waitFor();
	}

	async viewDisplayTab(tabLabel: string, {isHidden} = {isHidden: false}) {
		const tab = this.page.locator('nav.navbar');

		if (isHidden) {
			await expect(
				tab.locator('a', {
					hasText: tabLabel,
				})
			).toBeHidden();
		}
		else {
			await expect(
				tab.locator('a', {
					hasText: tabLabel,
				})
			).toBeVisible();
		}
	}
}
