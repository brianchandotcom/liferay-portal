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

	async changeCombobox(
		name: 'Preview By' | 'Experience' | 'Segment',
		value: string
	) {
		const select = this.page
			.locator('.simulation-app-panel-body')
			.getByLabel(name);

		if ((await select.textContent()).includes(value)) {
			return;
		}

		const option =
			name === 'Experience'
				? this.page.getByRole('option', {name: value})
				: this.page.locator('li', {hasText: value});

		await expect(async () => {
			await this.page
				.getByRole('combobox', {name})
				.click({timeout: 1000});

			await expect(option).toBeVisible({
				timeout: 1000,
			});

			await option.click({timeout: 1000});

			await expect(this.page.getByRole('combobox', {name})).toContainText(
				value,
				{timeout: 1000}
			);
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
