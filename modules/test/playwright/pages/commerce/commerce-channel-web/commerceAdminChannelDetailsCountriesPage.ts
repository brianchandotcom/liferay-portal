/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {ApplicationsMenuPage} from '../../product-navigation-applications-menu/ApplicationsMenuPage';
import {searchTableRowByValue} from '../commerceDNDTablePage';

export class CommerceAdminChannelDetailsCountriesPage {
	readonly addCountryAddButton: Locator;
	readonly addCountryButton: Locator;
	readonly addCountryFrame: FrameLocator;
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly countriesTable: Locator;
	readonly countriesTableRow: (
		colPosition: number,
		value: number | string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly countriesTableRows: () => Promise<Locator[]>;
	readonly countriesTableRowAction: (
		countryName: string,
		action: string
	) => Promise<Locator>;
	readonly countryFrameCountry: (countryName: string) => Promise<Locator>;
	readonly page: Page;

	constructor(page: Page) {
		this.addCountryAddButton = page.getByRole('button', {
			exact: true,
			name: 'Add',
		});
		this.addCountryButton = page.getByLabel('Add Country');
		this.addCountryFrame = page.frameLocator('iframe[title="Add Country"]');
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.countriesTable = page.locator(
			'#_com_liferay_commerce_channel_web_internal_portlet_CommerceChannelsPortlet_editChannelContainer .dnd-table'
		);
		this.countriesTableRow = async (
			colPosition: number,
			value: number | string,
			strictEqual: boolean = false
		) => {
			return await searchTableRowByValue(
				this.countriesTable,
				colPosition,
				String(value),
				strictEqual
			);
		};
		this.countriesTableRows = async () => {
			await this.countriesTable.elementHandle();

			return await this.countriesTable
				.locator('div.dnd-tbody div.dnd-tr')
				.all();
		};
		this.countriesTableRowAction = async (
			countryName: string,
			action: string
		) => {
			const countriesTableRow = await this.countriesTableRow(
				0,
				countryName,
				true
			);

			if (countriesTableRow && countriesTableRow.column) {
				return countriesTableRow.row.getByRole('link', {
					name: action,
				});
			}

			throw new Error(
				`Cannot locate country row with name ${countryName}`
			);
		};
		this.countryFrameCountry = async (countryName: string) => {
			return this.addCountryFrame.getByLabel(countryName);
		};
		this.page = page;
	}
}
