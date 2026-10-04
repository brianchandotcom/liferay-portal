/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../../../utils/clickAndExpectToBeVisible';
import {DataSetPage} from '../../../site-cms-site-initializer/main/pages/DataSetPage';

export class FieldMappingsPage {
	readonly channelField: (channelField: string) => Locator;
	readonly dataSetFragmentPage: DataSetPage;
	readonly page: Page;
	readonly row: (channelField: string) => Locator;
	readonly sourceAttributes: (channelField: string) => Locator;
	readonly status: (channelField: string) => Locator;

	constructor(page: Page) {
		this.channelField = (channelField) =>
			this.row(channelField).getByRole('link', {
				exact: true,
				name: channelField,
			});
		this.dataSetFragmentPage = new DataSetPage(page);
		this.page = page;
		this.row = (channelField) =>
			this.dataSetFragmentPage.table.bodyRows.filter({
				has: page.getByRole('link', {
					exact: true,
					name: channelField,
				}),
			});
		this.sourceAttributes = (channelField) =>
			this.row(channelField).locator('.cell-sourceAttributes');
		this.status = (channelField) =>
			this.row(channelField).locator('.cell-status .label');
	}

	async clearMapping(channelField: string) {
		this.page.once('dialog', (dialog) => dialog.accept());

		await this.dataSetFragmentPage.execItemAction({
			action: 'Clear',
			filter: channelField,
		});
	}

	async editMapping(channelField: string) {
		await this.dataSetFragmentPage.execItemAction({
			action: 'Edit',
			filter: channelField,
		});
	}

	async expectClearVisible(channelField: string) {
		await clickAndExpectToBeVisible({
			target: this.page.getByRole('menuitem', {
				exact: true,
				name: 'Clear',
			}),
			trigger: this.row(channelField).getByRole('button', {
				name: `${channelField} Actions`,
			}),
		});

		await this.page.keyboard.press('Escape');
	}
}
