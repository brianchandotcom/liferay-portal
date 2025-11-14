/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {ApiHelpers} from '../../../../helpers/ApiHelpers';
import {clickAndExpectToBeVisible} from '../../../../utils/clickAndExpectToBeVisible';
import {PORTLET_URLS} from '../../../../utils/portletUrls';
import {waitForAlert} from '../../../../utils/waitForAlert';
import {DataSetPage} from '../../main/pages/DataSetPage';

export class FilesPage {
	readonly apiHelpers: ApiHelpers;
	readonly dataSetFragmentPage: DataSetPage;
	readonly newButton: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.apiHelpers = new ApiHelpers(page);
		this.dataSetFragmentPage = new DataSetPage(page);
		this.newButton = page.getByTestId('fdsCreationActionButton').first();
		this.page = page;
	}

	async goto() {
		await expect(async () => {
			await this.page.goto(PORTLET_URLS.cmsFiles);

			await this.newButton.waitFor({state: 'visible', timeout: 3000});
		}).toPass();
	}

	async createFolder(folderName: string, spaceName?: string) {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {name: 'Folder'}),
			trigger: this.newButton,
		});

		await this.page.getByRole('heading', {name: 'New Folder'}).waitFor();

		await this.page.getByLabel('NameRequired').fill(folderName);

		if (spaceName) {
			await this.page.getByLabel('SpaceMandatory').click();
			await this.page.getByRole('option', {name: spaceName}).click();
		}

		await this.page.getByRole('button', {name: 'Save'}).click();

		await waitForAlert(this.page, `Success:${folderName} was created`);
	}

	async changeVisualizationMode(
		...args: Parameters<DataSetPage['changeVisualizationMode']>
	) {
		await this.dataSetFragmentPage.changeVisualizationMode(...args);
	}
}
