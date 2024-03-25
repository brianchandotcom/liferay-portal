/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';
import {DiagramViewPage} from './DiagramViewPage';
import {SourceViewPage} from './SourceViewPage';

export class ProcessBuilderPage {
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly diagramViewPage: DiagramViewPage;
	readonly page: Page;
	readonly sourceViewPage: SourceViewPage;

	constructor(page: Page) {
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.diagramViewPage = new DiagramViewPage(page);
		this.page = page;
		this.sourceViewPage = new SourceViewPage(page);
	}

	async clickWorkflowDefinitionName(name: string) {
		await this.page
			.getByRole('link', {
				exact: true,
				name,
			})
			.click();
	}

	async switchToSourceViewAndBackToDiagram() {
		await this.diagramViewPage.clickSourceViewButton();

		await this.page.waitForTimeout(2500);

		await this.page
			.getByText('SourceWrite your definition or import a file.')
			.click();

		await this.sourceViewPage.clickDiagramViewButton();
	}

	async goto() {
		await this.applicationsMenuPage.goToProcessBuilder();
	}
}
