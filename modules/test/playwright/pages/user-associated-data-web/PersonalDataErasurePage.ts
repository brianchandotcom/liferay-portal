/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {searchTableRowByValue} from '../account-admin-web/AccountsPage';
import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

export class PersonalDataErasurePage {
	readonly actionsButton: Locator;
	readonly allApplicationsDataTable: Locator;
	readonly allApplicationsDataTableRow: (
		colPosition: number,
		value: string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly allApplicationsDataTableRowCount: (
		count: string,
		name: string
	) => Promise<Locator>;
	readonly allApplicationsRadioButton: Locator;
	readonly allSelectedButton: Locator;
	readonly anonymizeButton: Locator;
	readonly anonymizeLink: Locator;
	readonly anonymizeMenuItem: Locator;
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly blogsRadioButton: Locator;
	readonly contactsCenterRadioButton: Locator;
	readonly deleteMenuItem: Locator;
	readonly dlFileEntryText: Locator;
	readonly dlFolderText: Locator;
	readonly documentsAndMediaRadioButton: Locator;
	readonly editMenuItem: Locator;
	readonly emptyMessage: Locator;
	readonly formsRadioButton: Locator;
	readonly infoPanelButton: Locator;
	readonly infoPanelEllipsisButton: (name: string) => Locator;
	readonly infoPanelSidebar: Locator;
	readonly instanceRadioButton: Locator;
	readonly journalArticleCheckBox: (articleRowId: string) => Locator;
	readonly objectCheckBox: (
		objectId: string,
		objectTitle: string,
		match: boolean
	) => Locator;
	readonly objectCountLink: (objectCountNumber: string) => Locator;
	readonly optionalColumnRow: (
		columnIndex: number,
		rowIndex: number
	) => Locator;
	readonly orderButton: Locator;
	readonly orderMenuItem: (option: string) => Locator;
	readonly page: Page;
	readonly pageTitle: Locator;
	readonly regularSitesRadioButton: Locator;
	readonly remainingItemsCount: (number: string) => Locator;
	readonly reviewDataLink: Locator;
	readonly selectAllItemsOnPageCheckbox: Locator;
	readonly userAssociatedDataTable: Locator;
	readonly userAssociatedDataTableRow: (
		colPosition: number,
		value: string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly userAssociatedDataTableRowActions: (
		name: string
	) => Promise<Locator>;
	readonly userAssociatedDataTableRowCheckBox: (
		name: string
	) => Promise<Locator>;
	readonly objectRadioButtonLabelCount: (
		name: string,
		number: string
	) => Locator;
	readonly objectLink: (objectName: string) => Locator;
	readonly webContentRadioButton: Locator;

	constructor(page: Page) {
		this.actionsButton = page.getByRole('button', {name: 'Actions'});
		this.allApplicationsDataTable = page.locator(
			'#_com_liferay_user_associated_data_web_portlet_UserAssociatedData_uadEntities_all-applications'
		);
		this.allApplicationsDataTableRow = async (
			colPosition: number,
			value: string,
			strictEqual: boolean = false
		) => {
			return await searchTableRowByValue(
				this.allApplicationsDataTable,
				colPosition,
				value,
				strictEqual
			);
		};
		this.allApplicationsDataTableRowCount = async (
			count: string,
			name: string
		) => {
			const userAssociatedDataTableRow =
				await this.allApplicationsDataTableRow(1, name, true);

			if (userAssociatedDataTableRow && userAssociatedDataTableRow.row) {
				return userAssociatedDataTableRow.row.getByText(count);
			}

			throw new Error(`Cannot locate account row with name ${name}`);
		};
		this.allApplicationsRadioButton = page.locator(
			'input[type="radio"][value="all-applications"]'
		);
		this.allSelectedButton = page
			.locator('nav')
			.filter({hasText: 'All Selected'})
			.getByRole('button');
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.anonymizeButton = page.getByRole('button', {name: 'Anonymize'});
		this.anonymizeLink = page.getByRole('link', {
			exact: true,
			name: 'Anonymize',
		});
		this.anonymizeMenuItem = page.getByRole('menuitem', {
			name: 'Anonymize',
		});
		this.blogsRadioButton = page.locator(
			'input[type="radio"][value="com.liferay.blogs.uad"]'
		);
		this.contactsCenterRadioButton = page.locator(
			'input[type="radio"][value="com.liferay.contacts.uad"]'
		);
		this.deleteMenuItem = page.getByRole('menuitem', {name: 'Delete'});
		this.dlFileEntryText = page.getByText('DLFILEENTRY');
		this.dlFolderText = page.getByText('DLFOLDER');
		this.documentsAndMediaRadioButton = page.locator(
			'input[type="radio"][value="com.liferay.document.library.uad"]'
		);
		this.editMenuItem = page.getByRole('menuitem', {name: 'Edit'});
		this.emptyMessage = page.getByText(
			'All data that requires review has been anonymized.'
		);
		this.formsRadioButton = page.locator(
			'input[type="radio"][value="com.liferay.dynamic.data.mapping.uad"]'
		);
		this.infoPanelButton = page.getByRole('button', {
			name: 'Toggle Info Panel',
		});
		this.infoPanelEllipsisButton = (name: string) =>
			page
				.locator('.sidebar-header')
				.filter({hasText: name})
				.locator('.component-action svg.lexicon-icon-ellipsis-v')
				.first();
		this.infoPanelSidebar = page.locator(
			'#_com_liferay_user_associated_data_web_portlet_UserAssociatedData_sidebarPanel'
		);
		this.instanceRadioButton = page.locator(
			'input[type="radio"][value="instance"]'
		);
		this.journalArticleCheckBox = (articleRowId: string) => {
			return page
				.locator(
					`[id="_com_liferay_user_associated_data_web_portlet_UserAssociatedData_uadEntities_com_liferay_journal_uad_${articleRowId}"]`
				)
				.getByRole('checkbox');
		};
		this.objectCheckBox = (
			objectId: string,
			objectTitle: string,
			match: boolean
		) => {
			const blogIdLocator = page.locator(`[value="${objectId}"]`);

			return match
				? this.objectLink(objectTitle)
						.locator('../..')
						.filter({has: blogIdLocator})
						.getByRole('checkbox')
				: this.objectLink(objectTitle)
						.locator('../..')
						.filter({hasNot: blogIdLocator})
						.getByRole('checkbox');
		};
		this.objectCountLink = (objectCountNumber: string) => {
			return page.getByRole('link', {name: objectCountNumber});
		};
		this.optionalColumnRow = (columnIndex: number, rowIndex: number) => {
			const row = this.userAssociatedDataTable
				.getByRole('row')
				.nth(rowIndex);

			return row.getByRole('cell').nth(columnIndex);
		};
		this.orderButton = page.getByRole('button', {name: 'Order'});
		this.orderMenuItem = (option: string) =>
			page.getByRole('menuitem', {name: option});
		this.page = page;
		this.regularSitesRadioButton = page.getByLabel('Regular Sites', {
			exact: true,
		});
		this.remainingItemsCount = (number: string) =>
			page.getByText(`Remaining items: ${number}`);
		this.reviewDataLink = page.getByRole('link', {name: 'Review Data'});
		this.selectAllItemsOnPageCheckbox = page.getByLabel(
			'Select All Items on the Page'
		);
		this.userAssociatedDataTable = page
			.locator(
				'#_com_liferay_user_associated_data_web_portlet_UserAssociatedData_uadEntities_com_liferay_blogs_uad'
			)
			.or(
				page.locator(
					'#_com_liferay_user_associated_data_web_portlet_UserAssociatedData_uadEntities_com_liferay_journal_uad'
				)
			)
			.or(
				page.locator(
					'#_com_liferay_user_associated_data_web_portlet_UserAssociatedData_uadEntities_com_liferay_document_library_uad'
				)
			);
		this.userAssociatedDataTableRow = async (
			colPosition: number,
			value: string,
			strictEqual: boolean = false
		) => {
			return await searchTableRowByValue(
				this.userAssociatedDataTable,
				colPosition,
				value,
				strictEqual
			);
		};
		this.userAssociatedDataTableRowActions = async (name: string) => {
			const userAssociatedDataTableRow =
				await this.userAssociatedDataTableRow(1, name, true);

			if (userAssociatedDataTableRow && userAssociatedDataTableRow.row) {
				return userAssociatedDataTableRow.row.getByRole('button');
			}

			throw new Error(`Cannot locate row with name ${name}`);
		};
		this.userAssociatedDataTableRowCheckBox = async (name: string) => {
			const userAssociatedDataTableRow =
				await this.userAssociatedDataTableRow(1, name, true);

			if (userAssociatedDataTableRow && userAssociatedDataTableRow.row) {
				return userAssociatedDataTableRow.row.getByTitle('Select');
			}

			throw new Error(`Cannot locate row with name ${name}`);
		};
		this.objectRadioButtonLabelCount = (name: string, number: string) =>
			page.getByText(`${name} (${number})`);
		this.objectLink = (objectName: string) =>
			page.getByRole('link', {name: objectName});
		this.webContentRadioButton = page.locator(
			'input[type="radio"][value="com.liferay.journal.uad"]'
		);
	}
}
