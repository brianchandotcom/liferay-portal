/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {InstanceSettingsPage} from '../../../pages/configuration-admin-web/InstanceSettingsPage';

export class FeatureFlagsInstanceSettingsPage {
	readonly page: Page;
	readonly saveButton: Locator;
	readonly searchButton: Locator;
	readonly searchInput: Locator;
	readonly instanceSettingsPage: InstanceSettingsPage;

	constructor(page: Page) {
		this.page = page;
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.searchButton = page.getByRole('button', {name: 'Search for'});
		this.searchInput = page.getByPlaceholder('Search for');
		this.instanceSettingsPage = new InstanceSettingsPage(page);
	}

	async getFeatureFlagToggle(key: string, search: boolean = true) {
		if (search) {
			await this.search(key);
		}

		return this.page.locator(`#${key}`);
	}

	async goto(featureFlagType = 'Developer') {
		await this.instanceSettingsPage.goToInstanceSetting(
			'Feature Flags',
			featureFlagType
		);
	}

	async search(string) {
		await expect(async () => {
			await expect(this.searchInput).toBeEnabled();

			await expect(this.searchButton).toBeEnabled();

			await this.searchInput.fill(string);

			await this.searchButton.click();

			await expect(this.searchInput).toHaveValue(string, {timeout: 100});
		}).toPass();
	}

	async updateFeatureFlag(
		key: string,
		enabled: boolean,
		search: boolean = true
	) {
		if (search) {
			await this.search(key);
		}

		const featureFlagToggle = await this.getFeatureFlagToggle(key, search);

		await expect(
			featureFlagToggle,
			`The feature flag should be ${JSON.stringify(
				!enabled
			)} before toggling.`
		).toBeChecked({checked: !enabled});

		await Promise.all([
			featureFlagToggle.click(),
			this.page.waitForResponse(
				(response) =>
					response.status() === 200 &&
					response
						.url()
						.includes('com-liferay-feature-flag-web/set-enabled')
			),
		]);
	}
}
