/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {Locator, Page, expect} from '@playwright/test';

import {liferayConfig} from '../../liferay.config';
import getPageDefinition from '../../tests/layout-content-page-editor-web/utils/getPageDefinition';
import fillAndClickOutside from '../../utils/fillAndClickOutside';
import getRandomString from '../../utils/getRandomString';
import {waitForSuccessAlert} from '../../utils/waitForSuccessAlert';
import {SegmentEditorPage} from '../segments-web/SegmentEditorPage';

export class PageEditorPage {
	readonly page: Page;

	readonly experienceSelector: Locator;
	readonly publishButton: Locator;
	readonly redoButton: Locator;
	readonly undoButton: Locator;
	readonly undoHistory: Locator;

	readonly segmentEditorPage: SegmentEditorPage;

	constructor(page: Page) {
		this.page = page;

		this.experienceSelector = page.locator(
			'.page-editor__experience-selector'
		);
		this.publishButton = page.getByLabel('Publish', {exact: true});
		this.redoButton = page.getByTitle('Redo');
		this.undoButton = page.getByTitle('Undo');
		this.undoHistory = page.locator('.page-editor__undo-history');

		this.segmentEditorPage = new SegmentEditorPage(page);
	}

	async goto(layout: Layout, siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/web${siteUrl || '/guest'}${layout.friendlyUrlPath}?p_l_mode=edit`
		);
	}

	async addFragment(setName: string, name: string) {
		await this.goToSidebarTab('Fragments and Widgets');

		const header = this.page.getByRole('menuitem', {
			exact: true,
			name: setName,
		});

		const isOpen = await header.evaluate(
			(element) => element.getAttribute('aria-expanded') === 'true'
		);

		if (!isOpen) {
			await header.click();
		}

		await this.page.getByLabel(`Add ${name}`).focus();

		await this.page.keyboard.press('Enter');
		await this.page.keyboard.press('Enter');

		await this.waitForChangesSaved();
	}

	async addWidget(category: string, name: string) {
		await this.page
			.getByRole('tab', {exact: true, name: 'Widgets'})
			.click();

		const categoryDropdown = this.page.getByRole('menuitem', {
			name: category,
		});

		if (!categoryDropdown.getAttribute('aria-expanded')) {
			await categoryDropdown.click();
		}

		await this.page.getByText(name).click();
		await this.page
			.getByRole('menuitem', {
				name: `${name} Add ${name} Mark ${name} as Favorite`,
			})
			.press('Tab');
		await this.page.getByLabel(`Add ${name}`).press('Enter');
		await this.page.getByLabel(`Add ${name}`).press('Enter');
	}

	async changeFragmentConfiguration(
		fragmentId: string,
		tab: ConfigurationTab,
		fieldLabel: string,
		value: string,
		isDesktop = true
	) {
		await this.selectFragment(fragmentId, isDesktop);
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

		await this.waitForChangesSaved();
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

		await this.waitForChangesSaved();
	}

	async chooseCollectionDisplayOption(
		collectionType: string,
		collectionTitle?: string
	) {
		await this.page.getByLabel('Select Collection', {exact: true}).click();

		await this.page
			.frameLocator('iframe[title="Select"]')
			.getByRole('link', {name: collectionType})
			.click();
		await this.page
			.frameLocator('iframe[title="Select"]')
			.getByRole('button', {name: 'Select ' + collectionTitle})
			.click();
	}

	async chooseCollectionFilterOption(fieldName: string, option: string) {
		await this.page.getByLabel('View Collection Options').click();
		await this.page
			.getByRole('menuitem', {name: 'Filter Collection'})
			.click();
		await this.page.getByLabel(fieldName).selectOption(option);
		await this.page.getByRole('button', {name: 'Save'}).click();
	}

	async closeExperienceSelector() {
		const isOpen = await this.experienceSelector.evaluate(
			(element) => element.getAttribute('aria-expanded') === 'true'
		);

		if (isOpen) {
			await this.experienceSelector.click();

			await this.page
				.getByText('Select Experience')
				.waitFor({state: 'hidden'});
		}
	}

	async createExperience(name: string) {
		await this.openExperienceSelector();

		await this.page.getByLabel('New Experience').click();

		const nameInput = this.page.getByPlaceholder('Experience Name');

		await nameInput.waitFor();

		await fillAndClickOutside(this.page, nameInput, name);

		await this.page.locator('.modal-footer').getByText('Save').click();

		await this.page.getByText('Select Experience').waitFor();

		await this.closeExperienceSelector();

		await waitForSuccessAlert(
			this.page,
			'Success:The experience was created successfully.',
			{autoClose: false}
		);
	}

	async createPageWithFragmentAndGoToEditMode({apiHelpers, fragment, site}) {
		await this.page.goto(liferayConfig.environment.baseUrl);

		// Create a page with a fragment

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([fragment]),
			siteId: site.id,
			title: getRandomString(),
		});

		// Go to edit mode of page

		await this.goto(layout, site.friendlyUrlPath);
	}

	async deleteExperience(name: string) {
		await this.openExperienceSelector();

		await this.page.on('dialog', async (dialog) => await dialog.accept());

		await this.page
			.locator('.dropdown-menu__experience', {
				hasText: name,
			})
			.getByLabel('Delete Experience')
			.click();

		await this.closeExperienceSelector();

		await waitForSuccessAlert(
			this.page,
			'Success:The experience was deleted successfully.',
			{autoClose: false}
		);
	}

	async deleteFragment(fragmentId: string) {
		await this.selectFragment(fragmentId);
		await this.page.keyboard.press('Backspace');
	}

	async duplicateExperience(experience: string) {
		await this.openExperienceSelector();

		await this.page
			.locator('.dropdown-menu__experience', {
				hasText: experience,
			})
			.getByLabel('Duplicate Experience')
			.click();

		await waitForSuccessAlert(
			this.page,
			'Success:The experience was duplicated successfully.',
			{autoClose: false}
		);
	}

	async duplicateFragment(fragmentId: string) {
		await this.selectFragment(fragmentId);

		await this.page.keyboard.press('Control+D');

		await this.waitForChangesSaved();
	}

	async editEditableText(
		fragmentId: string,
		editableId: string,
		value: string
	) {

		// Select fragment and editable

		await this.selectFragment(fragmentId);

		await this.selectEditable(fragmentId, editableId);

		// Click editable again to enable edition

		const editable = this.getEditable(fragmentId, editableId);

		await editable.click();

		// Click CKEditor

		await editable.locator('.cke_editable_inline').waitFor();

		await editable.locator('.cke_editable_inline').click();

		// Clear current content and fill with new one

		await this.page.keyboard.press('Control+KeyA');
		await this.page.keyboard.press('Backspace');

		await this.page.keyboard.type(value);

		await this.page.locator('header.page-editor__disabled-area').click();

		await this.waitForChangesSaved();
	}

	async editExperienceName(name: string, newName: string) {
		await this.openExperienceSelector();

		await this.page
			.locator('.dropdown-menu__experience', {
				hasText: name,
			})
			.getByLabel('Edit Experience')
			.click();

		const nameInput = this.page.getByPlaceholder('Experience Name');

		await nameInput.waitFor();

		await fillAndClickOutside(this.page, nameInput, newName);

		await this.page.locator('.modal-footer').getByText('Save').click();

		await this.page.getByText('Select Experience').waitFor();

		await this.closeExperienceSelector();

		await waitForSuccessAlert(
			this.page,
			'Success:The experience was updated successfully.',
			{autoClose: false}
		);
	}

	async editExperienceSegment(name: string, segment: string) {
		await this.openExperienceSelector();

		await this.page
			.locator('.dropdown-menu__experience', {hasText: name})
			.getByLabel('Edit Experience')
			.click();

		// Check segment already exists, otherwise create it

		const audienceSelector = this.page.getByLabel('Audience');

		const options = await audienceSelector.evaluate(
			(element: HTMLSelectElement) =>
				Array.from(element.options).map((option) => option.label)
		);

		if (options.includes(segment)) {
			await audienceSelector.selectOption({label: segment});
		}
		else {
			await this.page.getByText('New Segment').click();

			await this.page.getByText('No Conditions yet').waitFor();

			await this.segmentEditorPage.createSegment(segment, {
				user: ['First Name'],
			});

			await this.page.getByText('Edit Experience').waitFor();
		}

		// Save changes

		await this.page.locator('.modal-footer').getByText('Save').click();

		await this.page.getByText('Select Experience').waitFor();

		await this.closeExperienceSelector();

		await waitForSuccessAlert(
			this.page,
			'Success:The experience was updated successfully.',
			{autoClose: false}
		);
	}

	async getFragmentStyle(
		fragmentId: string,
		style: string,
		isDesktop = true
	) {
		const topper = this.getTopper(fragmentId, isDesktop);

		const styles = await topper.evaluate((element) =>
			window.getComputedStyle(element)
		);

		return styles[style];
	}

	async goToConfigurationTab(tab: ConfigurationTab) {
		await this.page.getByRole('tab', {exact: true, name: tab}).click();
	}

	async goToSidebarTab(tab: SidebarTab) {
		const tabElement = this.page.getByRole('tab', {exact: true, name: tab});

		const isOpen = await tabElement.evaluate(
			(element) => element.getAttribute('aria-selected') === 'true'
		);

		if (!isOpen) {
			await this.page.getByRole('tab', {exact: true, name: tab}).click();

			await this.page.locator('header', {hasText: tab}).waitFor();
		}
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

	async openExperienceSelector() {
		const isOpen = await this.experienceSelector.evaluate(
			(element) => element.getAttribute('aria-expanded') === 'true'
		);

		if (!isOpen) {
			await this.experienceSelector.click();

			await this.page.getByText('Select Experience').waitFor();
		}
	}

	async openSpacingSelector(fragmentId: string, spacingType: SpacingType) {
		await this.selectFragment(fragmentId);
		await this.goToConfigurationTab('Styles');

		await this.page.getByLabel(spacingType, {exact: true}).click();
	}

	async publishPage() {
		await this.publishButton.click();

		await waitForSuccessAlert(
			this.page,
			'Success:The page was published successfully.'
		);
	}

	async removeFragment(fragmentId: string) {
		await this.selectFragment(fragmentId);

		const fragment = this.getFragment(fragmentId);

		await this.page.keyboard.press('Backspace');

		await this.waitForChangesSaved();

		await fragment.waitFor({state: 'hidden'});
	}

	async resetSpacing(fragmentId: string, spacingType: SpacingType) {
		await this.openSpacingSelector(fragmentId, spacingType);

		const resetButton = this.page.getByLabel('Reset to Initial Value');

		if (await resetButton.isVisible()) {
			await resetButton.click();
			await resetButton.waitFor({state: 'hidden'});
		}

		await this.waitForChangesSaved();
	}

	async selectFragment(fragmentId: string, isDesktop = true) {
		if (await this.isActive(fragmentId, isDesktop)) {
			return;
		}

		const fragment = await this.getFragment(fragmentId, isDesktop);

		await fragment.click();

		const isActive = await this.isActive(fragmentId, isDesktop);

		await expect(isActive).toBe(true);
	}

	async selectEditable(
		fragmentId: string,
		editableId: string,
		isDesktop = true
	) {
		await this.selectFragment(fragmentId, isDesktop);

		const editable = await this.getEditable(
			fragmentId,
			editableId,
			isDesktop
		);

		await editable.click();

		await expect(editable).toBeFocused();
	}

	async switchExperience(experience: string) {
		await this.openExperienceSelector();

		await this.page.getByText('Select Experience').waitFor();

		await this.page
			.locator('.dropdown-menu__experience', {
				hasText: experience,
			})
			.click();

		await expect(this.experienceSelector).toContainText(experience);

		await this.closeExperienceSelector();
	}

	async switchViewport(viewport: Viewport) {
		await this.page.getByLabel(viewport, {exact: true}).click();

		if (viewport !== 'Desktop') {
			await this.page
				.frameLocator('.page-editor__global-context-iframe')
				.locator('.page-editor')
				.waitFor();
		}
	}

	async waitForChangesSaved() {
		await this.page.getByLabel('Saved').waitFor();

		await this.page
			.getByText(
				'Changes have been saved. Page editor will autosave new changes.'
			)
			.waitFor();
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

	getEditable(fragmentId: string, editableId: string, isDesktop = true) {
		return this.getFragment(fragmentId, isDesktop).locator(
			`[data-lfr-editable-id="${editableId}"]`
		);
	}

	getTopper(fragmentId: string, isDesktop = true) {
		const topper = isDesktop
			? this.page.locator(
					`.lfr-layout-structure-item-topper-${fragmentId}`
			  )
			: this.page
					.frameLocator('.page-editor__global-context-iframe')
					.locator(`.lfr-layout-structure-item-topper-${fragmentId}`);

		return topper;
	}
}
