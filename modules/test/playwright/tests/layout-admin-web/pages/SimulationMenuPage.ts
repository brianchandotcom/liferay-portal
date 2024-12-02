/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

export class SimulationMenuPage {
	readonly page: Page;

	readonly simulationButton: Locator;

	constructor(page: Page) {
		this.page = page;

		this.simulationButton = page
			.locator('.control-menu-nav-item')
			.getByRole('button', {
				exact: true,
				name: 'Simulation',
			});
	}

	async changePreviewBy(name: 'Experiences' | 'Segments') {
		const select = this.page.getByRole('combobox', {name: 'Preview By'});

		if ((await select.textContent()).includes(name)) {
			return;
		}

		const value = name.toLowerCase();

		await expect(async () => {
			await this.page.getByRole('combobox', {name: 'Preview By'}).click();

			await expect(this.page.locator(`#${value}`)).toBeVisible({
				timeout: 1000,
			});

			await this.page.locator(`#${value}`).click({timeout: 1000});

			await expect(
				this.page.getByRole('combobox', {name: 'Preview By'})
			).toContainText(name, {timeout: 1000});
		}).toPass();

		await expect(
			this.page
				.frameLocator('iframe[title="Simulation Preview"]')
				.locator('.public-page')
		).toBeVisible();
	}

	async openSimulationPanel() {
		const isOpen = await this.simulationButton.evaluate((element) =>
			element.classList.contains('open')
		);

		if (!isOpen) {
			await this.simulationButton.click();
		}
	}
}
