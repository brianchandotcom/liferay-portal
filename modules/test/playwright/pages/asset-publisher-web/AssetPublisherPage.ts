/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {waitForSuccessAlert} from '../../utils/waitForSuccessAlert';
import {PageEditorPage} from '../layout-content-page-editor-web/PageEditorPage';

export class AssetPublisherPage {
	readonly page: Page;
	readonly pageEditorPage: PageEditorPage;

	constructor(page: Page) {
		this.page = page;
		this.pageEditorPage = new PageEditorPage(this.page);
	}

	async createCollectionFromAssetPublisher(
		collectionName: string,
		type: string
	) {
		const iframe = this.pageEditorPage.page.frameLocator('iframe');

		await iframe.getByLabel(type, {exact: true}).click();

		await waitForSuccessAlert(
			iframe,
			'Success:You have successfully updated the setup.'
		);

		await iframe
			.getByRole('button', {name: 'Create a collection from this'})
			.click();

		await iframe.getByPlaceholder('Title').fill(collectionName);

		await iframe
			.getByLabel('Collection Title')
			.getByRole('button', {name: 'Save'})
			.click();

		await waitForSuccessAlert(
			iframe,
			'Success:The collection was created successfully.'
		);
	}
}
