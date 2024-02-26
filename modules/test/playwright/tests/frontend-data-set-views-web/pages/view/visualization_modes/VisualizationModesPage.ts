/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ViewPage} from '../ViewPage';

export class VisualizationModesPage {
	private readonly container: Locator;
	readonly page: Page;
	private readonly viewPage: ViewPage;

	constructor(page: Page) {
		this.container = page.locator('.visualization-modes');
		this.page = page;
		this.viewPage = new ViewPage(page);
	}

	async goto({
		dataSetLabel,
		viewLabel,
	}: {
		dataSetLabel: string;
		viewLabel: string;
	}) {
		await this.viewPage.goto({
			dataSetLabel,
			viewLabel,
		});

		await this.viewPage.selectTab('Visualization Modes');
	}

	async selectTab(tabLabel: string) {
		const tab = this.container.getByRole('tab', {
			exact: true,
			name: tabLabel,
		});

		await tab.click();

		await tab.and(this.page.locator('.active')).waitFor();
	}
}
