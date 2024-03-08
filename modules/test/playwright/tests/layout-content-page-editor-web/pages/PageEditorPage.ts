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

	async changeFragmentSpacing(
		fragmentId: string,
		spacingType: SpacingType,
		value: string,
		unit?: StyleUnit
	) {
		await this.openSpacingSelector(fragmentId, spacingType);

		if (unit) {
			await this.page
				.locator('.page-editor__spacing-selector__dropdown')
				.getByRole('button', {name: 'Select a unit'})
				.click();

			await this.page.getByRole('menuitem', {name: unit}).click();

			const input = await this.page.getByRole('spinbutton', {
				name: spacingType,
			});

			await input.fill(value);
			await input.blur();
			await input.waitFor({state: 'hidden'});
		}
		else {
			const selector = this.page.getByLabel(
				`Set ${spacingType} to ${value}`
			);

			await selector.click();
			await selector.waitFor({state: 'hidden'});
		}
	}

	async deleteFragment(fragmentId: string) {
		await this.selectFragment(fragmentId);
		await this.page.keyboard.press('Backspace');
	}

	async getFragmentStyle(
		fragmentId: string,
		style: string,
		isDesktop = true
	) {
		const topper = isDesktop
			? this.page.locator(
					`.lfr-layout-structure-item-topper-${fragmentId}`
			  )
			: this.page
					.frameLocator('.page-editor__global-context-iframe')
					.locator(`.lfr-layout-structure-item-topper-${fragmentId}`);

		const styles = await topper.evaluate((element) =>
			window.getComputedStyle(element)
		);

		return styles[style];
	}

	async goToConfigurationTab(tab: ConfigurationTab) {
		await this.page.getByRole('tab', {exact: true, name: tab}).click();
	}

	async goToEditMode(layout: Layout, siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/web${siteUrl || '/guest'}${layout.friendlyUrlPath}?p_l_mode=edit`
		);
	}

	async goToSidebarTab(tab: SidebarTab) {
		await this.page.getByRole('tab', {exact: true, name: tab}).click();
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

	async openSpacingSelector(fragmentId: string, spacingType: SpacingType) {
		await this.selectFragment(fragmentId);
		await this.goToConfigurationTab('Styles');

		await this.page.getByLabel(spacingType, {exact: true}).click();
	}

	async resetSpacing(fragmentId: string, spacingType: SpacingType) {
		await this.openSpacingSelector(fragmentId, spacingType);

		const resetButton = this.page.getByLabel('Reset to Initial Value');

		if (await resetButton.isVisible()) {
			await resetButton.click();
			await resetButton.waitFor({state: 'hidden'});
		}
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
