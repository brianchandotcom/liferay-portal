/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';

type Categories = 'Applications' | 'CMS' | 'Commerce' | 'Control Panel';

export class GlobalMenuPage {
	readonly globalMenuButton: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.globalMenuButton = page
			.getByRole('button', {name: 'Applications Menu'})
			.or(page.getByTestId('globalMenu'));
		this.page = page;
	}

	async goTo(categoryName: Categories) {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page
				.getByRole('menu')
				.getByRole('menuitem', {name: categoryName}),
			trigger: this.globalMenuButton,
		});

		await expect(
			this.page.getByRole('region', {name: categoryName})
		).toBeVisible();
	}

	async goToApplications() {
		await this.goTo('Applications');
	}

	async goToCMS() {
		await this.goTo('CMS');
	}

	async goToCommerce() {
		await this.goTo('Commerce');
	}

	async goToControlPanel() {
		await this.goTo('Control Panel');
	}
}
