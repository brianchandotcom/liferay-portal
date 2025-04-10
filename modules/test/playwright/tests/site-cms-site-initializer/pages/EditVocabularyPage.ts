/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {PORTLET_URLS} from '../../../utils/portletUrls';

export class EditVocabularyPage {
	readonly page: Page;

	private readonly assetTypeCheckbox: Locator;
	private readonly assetTypeSelector: Locator;
	private readonly descriptionInput: Locator;
	private readonly multiSelectToggle: Locator;
	private readonly nameInput: Locator;
	private readonly spaceCheckbox: Locator;
	private readonly spaceSelector: Locator;
	private readonly visibilitySelector: Locator;

	readonly assetTypesButton: Locator;
	readonly generalButton: Locator;
	readonly newButton: Locator;
	readonly saveButton: Locator;

	constructor(page: Page) {
		this.page = page;

		this.assetTypesButton = this.page.getByRole('button', {
			name: 'Associated Asset Types',
		});
		this.assetTypeCheckbox = this.page.getByRole('checkbox', {
			name: 'Make this vocabulary available in all asset types',
		});
		this.descriptionInput = this.page.getByLabel('Description');
		this.generalButton = this.page.getByRole('button', {name: 'General'});
		this.multiSelectToggle = this.page.getByRole('checkbox', {
			name: 'Multi Value',
		});
		this.nameInput = this.page.getByLabel('Name');
		this.newButton = this.page.getByRole('button', {
			name: 'New Vocabulary',
		});
		this.saveButton = this.page.getByRole('button', {name: 'Save'});
		this.spaceCheckbox = this.page.getByRole('checkbox', {
			name: 'Make this vocabulary available in all spaces',
		});
		this.spaceSelector = this.page.getByLabel('Space Selector');
		this.visibilitySelector = this.page.getByLabel('Visibility');
	}

	async goto() {
		await this.page.goto(PORTLET_URLS.cmsNewVocabulary);

		await this.page
			.getByRole('heading', {name: 'New Vocabulary'})
			.isVisible();
	}

	async changeGeneralInfo({
		description,
		name,
	}: {
		description?: string;
		name?: string;
	}) {
		await this.page.getByText('Basic Info').waitFor();

		if (description !== undefined) {
			await this.descriptionInput.fill(description);
			await this.descriptionInput.blur();
		}

		if (name !== undefined) {
			await this.nameInput.fill(name);
			await this.nameInput.blur();
		}
	}
}
