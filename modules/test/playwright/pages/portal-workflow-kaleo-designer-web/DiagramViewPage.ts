/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class DiagramViewPage {
	readonly backButton: Locator;
	readonly diagramArea: Locator;
	readonly reviewNodeLink: Locator;
	readonly saveWorkflowDefinitionButton: Locator;
	readonly sourceViewButton: Locator;
	readonly updateNodeLink: Locator;

	constructor(page: Page) {
		this.backButton = page.getByRole('link', {name: 'Back'});
		this.diagramArea = page.locator('.react-flow');
		this.reviewNodeLink = page.getByText('review', {exact: true});
		this.updateNodeLink = page.getByText('update', {exact: true});
		this.saveWorkflowDefinitionButton = page.getByRole('button', {
			name: 'Save',
		});
		this.sourceViewButton = page.locator('button[title="Source View"]');
	}

	async clickReviewNodeLink() {
		await this.reviewNodeLink.click();
	}

	async clickSourceViewButton() {
		await this.sourceViewButton.click();
	}

	async clickUpdateNodeLink() {
		await this.updateNodeLink.click();
	}

	async saveWorkflowDefinition() {
		await this.saveWorkflowDefinitionButton.click();
	}

	async goBack() {
		await this.backButton.click();
	}
}
