/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class SourceViewPage {
	readonly diagramViewButton: Locator;
	readonly saveButton: Locator;
	readonly xmlFirstLine: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.diagramViewButton = page
			.getByRole('button', {name: 'Diagram View'})
			.first();
		this.saveButton = page.getByText('Save');
		this.xmlFirstLine = page
			.locator('pre')
			.filter({hasText: '<?xml version="1.0"?>'});
	}

	async clickDiagramViewButton() {
		await this.diagramViewButton.click();
	}

	async saveWorkflowDefinition() {
		await this.xmlFirstLine.waitFor({state: 'visible'});

		await this.saveButton.click();
	}
}
