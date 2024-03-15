/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {PORTLET_URLS} from '../../utils/portletUrls';
import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

export class ChangeTrackingPage {
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly optionsButton: Locator;
	readonly page: Page;
	readonly reviewChangesButton: Locator;
	readonly saveButton: Locator;
	readonly tabsContainer: Locator;
	readonly toggleEnablePublications: Locator;

	constructor(page: Page) {
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.optionsButton = page.getByLabel('Options', {exact: true}).first();
		this.page = page;
		this.reviewChangesButton = page.getByRole('menuitem', {
			name: 'Review Changes',
		});
		this.saveButton = page.getByRole('button', {exact: true, name: 'Save'});
		this.tabsContainer = page.locator('nav.navbar');
		this.toggleEnablePublications = page.getByTitle('Enable Publications');
	}

	async goto() {
		await this.page.goto(`/group/guest${PORTLET_URLS.publications}`);
	}

	async disablePublications() {
		await this.goto();

		if (await this.optionsButton.isVisible()) {
			await this.optionsButton.click();

			await this.page.getByRole('menuitem', {name: 'Settings'}).click();
		}

		await this.toggleEnablePublications.click();

		await this.saveButton.click();
	}

	async enablePublications() {
		await this.applicationsMenuPage.goToPublications();

		await this.toggleEnablePublications.click();

		await this.saveButton.click();
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
