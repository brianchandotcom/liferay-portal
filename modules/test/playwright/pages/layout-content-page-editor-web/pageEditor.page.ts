/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {Page} from '@playwright/test';

type Viewport = 'Desktop' | 'Landscape Phone' | 'Portrait Phone' | 'Tablet';

export class PageEditorPage {
	readonly page: Page;

	constructor(page: Page) {
		this.page = page;
	}

	async goToConfigurationTab(tab: ConfigurationTab) {
		await this.page.getByRole('tab', {name: tab}).click();
	}

	async goToEditMode(site: Site, layout: Layout) {
		await this.page.goto(
			`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}?p_l_mode=edit`
		);
	}

	async selectFragment(fragmentId: string, isDesktop = true) {
		if (isDesktop) {
			await this.page
				.locator(`.lfr-layout-structure-item-${fragmentId}`)
				.click();
		}
		else {
			await this.page
				.frameLocator('.page-editor__global-context-iframe')
				.locator(`.lfr-layout-structure-item-${fragmentId}`)
				.click();
		}
	}

	async switchViewport(viewport: Viewport) {
		await this.page.getByLabel(viewport).click();
	}
}
