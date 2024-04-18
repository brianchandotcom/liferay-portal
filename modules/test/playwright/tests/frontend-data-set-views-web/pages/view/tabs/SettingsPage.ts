/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ViewPage} from '../ViewPage';

export class SettingsPage {
	readonly cancelButton: Locator;
	readonly goToVisualizationModesLink: Locator;
	readonly defaultVisualizationModeLabel: Locator;
	readonly notConfiguredPlaceholder: Locator;
	readonly page: Page;
	readonly saveButton: Locator;
	readonly toastContainer: Locator;
	readonly viewPage: ViewPage;

	constructor(page: Page) {
		this.cancelButton = page.getByRole('button', {name: 'Cancel'});
		this.goToVisualizationModesLink = page.getByText(
			'Go to Visualization Modes'
		);
		this.defaultVisualizationModeLabel = page.getByLabel(
			'Default Visualization Mode',
			{exact: true}
		);
		this.page = page;
		this.notConfiguredPlaceholder = page.getByPlaceholder('Not Configured');
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.toastContainer = page.locator('.alert-container');
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

		await this.viewPage.selectTab('Settings');
	}
}
