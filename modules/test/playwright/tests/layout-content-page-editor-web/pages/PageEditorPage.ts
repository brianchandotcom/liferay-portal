/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {Locator, Page} from '@playwright/test';

type Viewport = 'Desktop' | 'Landscape Phone' | 'Portrait Phone' | 'Tablet';

export class PageEditorPage {
	readonly page: Page;
	readonly redoButton: Locator;
	readonly undoButton: Locator;
	readonly undoHistory: Locator;

	constructor(page: Page) {
		this.page = page;

		this.redoButton = page.getByTitle('Redo');
		this.undoButton = page.getByTitle('Undo');
		this.undoHistory = page.locator('.page-editor__undo-history');
	}

	async changeFragmentConfiguration(
		fragmentId: string,
		tab: ConfigurationTab,
		fieldLabel: string,
		value: string
	) {
		await this.selectFragment(fragmentId);
		await this.goToConfigurationTab(tab);

		// Change value in different way depending on field type

		const field = await this.page.getByLabel(fieldLabel, {exact: true});
		const type = await field.evaluate((element) => element.tagName);

		if (type === 'INPUT' || type === 'TEXTAREA') {
			await field.fill(value);
		}
		else if (type === 'SELECT') {
			await field.selectOption(value);
		}

		// The change is applied on blur

		await field.blur();
	}

	async deleteFragment(fragmentId: string) {
		await this.selectFragment(fragmentId);
		await this.page.keyboard.press('Backspace');
	}

	async goToConfigurationTab(tab: ConfigurationTab) {
		await this.page.getByRole('tab', {name: tab}).click();
	}

	async goToEditMode(site: Site, layout: Layout) {
		await this.page.goto(
			`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}?p_l_mode=edit`
		);
	}

	async isActive(fragmentId: string, isDesktop = true) {
		const topper = isDesktop
			? this.page.locator(
					`.lfr-layout-structure-item-topper-${fragmentId}`
			  )
			: this.page
					.frameLocator('.page-editor__global-context-iframe')
					.locator(`.lfr-layout-structure-item-topper-${fragmentId}`);

		return await topper.evaluate((element) =>
			element.classList.contains('active')
		);
	}

	async selectFragment(fragmentId: string, isDesktop = true) {
		if (await this.isActive(fragmentId, isDesktop)) {
			return;
		}

		await this.getFragment(fragmentId, isDesktop).click();
	}

	async switchViewport(viewport: Viewport) {
		await this.page.getByLabel(viewport).click();
	}

	getFragment(fragmentId: string, isDesktop = true) {
		if (isDesktop) {
			return this.page.locator(
				`.lfr-layout-structure-item-${fragmentId}`
			);
		}
		else {
			return this.page
				.frameLocator('.page-editor__global-context-iframe')
				.locator(`.lfr-layout-structure-item-${fragmentId}`);
		}
	}
}
