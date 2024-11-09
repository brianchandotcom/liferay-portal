/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

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

	async openSimulationPanel() {
		const isOpen = await this.simulationButton.evaluate((element) =>
			element.classList.contains('open')
		);

		if (!isOpen) {
			await this.simulationButton.click();
		}
	}
}
