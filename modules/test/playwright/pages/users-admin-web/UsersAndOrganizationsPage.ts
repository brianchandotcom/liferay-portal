/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {waitForAlert} from '../../utils/waitForAlert';
import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

export const searchTableRowByValue = async function (
	tableLocator: Locator,
	colPosition: number,
	value: string,
	strictEqual: boolean = false
) {
	await tableLocator.elementHandle();

	const rows = await tableLocator.getByRole('row').all();

	for await (const row of rows) {
		const column = row.getByRole('cell').nth(colPosition).first();

		const colValue = (await column.allInnerTexts()).join('');

		if (
			(strictEqual && colValue === value) ||
			(!strictEqual &&
				colValue.toLowerCase().indexOf(value.toLowerCase()) >= 0)
		) {
			return {column, row};
		}
	}

	throw new Error(`Cannot locate table row with value ${value}`);
};

export class UsersAndOrganizationsPage {
	readonly activateButton: Locator;
	readonly activateUserMenuItem: Locator;
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly assignUsersIFrame: FrameLocator;
	readonly assignUsersMenuItem: Locator;
	readonly assignUsersTable: Locator;
	readonly assignUsersTableRow: (
		colPosition: number,
		value: string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly assignUsersCheckbox: (userName: string) => Promise<Locator>;
	readonly assignUsersDoneButton: Locator;
	readonly clearButton: Locator;
	readonly deactivateButton: Locator;
	readonly deactivateUserMenuItem: Locator;
	readonly deleteButton: Locator;
	readonly deletePersonalDataMenuItem: Locator;
	readonly exportImportOptionsMenuItem: Locator;
	readonly exportPersonalDataItem: Locator;
	readonly exportUsersOptionsMenuItem: Locator;
	readonly impersonateUserMenuItem: Locator;
	readonly manageCustomFieldsOptionsMenuItem: Locator;
	readonly myOrganizationsBreadcrumbLink: (
		organizationName: string
	) => Locator;
	readonly myOrganizationsMenuItem: Locator;
	readonly myOrganizationsTable: Locator;
	readonly myOrganizationsTableRow: (
		colPosition: number,
		value: string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly myOrganizationsTableRowLink: (
		organizationName: string
	) => Promise<Locator>;
	readonly myOrganizationsUserAndOrgsTable: Locator;
	readonly myOrganizationsUserAndOrgsTableRow: (
		colPosition: number,
		value: string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly myOrganizationsUserAndOrgsTableRowLink: (
		organizationName: string
	) => Promise<Locator>;
	readonly noUsersMessage: Locator;
	readonly organizationActionsMenu: (
		organizationName: string
	) => Promise<Locator>;
	readonly optionsMenu: Locator;
	readonly organizationChartLink: Locator;
	readonly organizationsLink: Locator;
	readonly organizationsTable: Locator;
	readonly organizationsTableRow: (
		colPosition: number,
		value: string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly organizationsTableRowLink: (
		organizationName: string
	) => Promise<Locator>;
	readonly organizationUsersTable: Locator;
	readonly organizationUsersTableRow: (
		colPosition: number,
		value: string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly organizationUsersTableRowActions: (
		screenName: string
	) => Promise<Locator>;
	readonly organizationUsersTableRowLink: (
		screenName: string
	) => Promise<Locator>;
	readonly organizationUsersTableRowStatusLink: (
		screenName: string,
		status: string
	) => Promise<Locator>;
	readonly page: Page;
	readonly pageTitle: Locator;
	readonly selectAllUsersCheckBox: Locator;
	readonly tableFilterMenu: Locator;
	readonly tableFilterMenuItem: (option: string) => Locator;
	readonly tableOrderMenu: Locator;
	readonly tableOrderLastLoginDateItem: Locator;
	readonly usersCheckbox: (userName: string) => Promise<Locator>;
	readonly usersSearchBar: Locator;
	readonly usersSearchBarButton: Locator;
	readonly usersTableRow: (
		colPosition: number,
		value: string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly usersTableRowLink: (screenName: string) => Promise<Locator>;
	readonly usersTableRowActions: (screenName: string) => Promise<Locator>;
	readonly usersLink: Locator;
	readonly userPersonalMenuButton: Locator;
	readonly usersTable: Locator;
	readonly usersTableCell: (userName: string) => Locator;

	constructor(page: Page) {
		this.activateButton = page.getByRole('button', {name: 'Activate'});
		this.activateUserMenuItem = page.getByRole('menuitem', {
			name: 'Activate',
		});
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.assignUsersIFrame = page.frameLocator('iframe[id="modalIframe"]');
		this.assignUsersMenuItem = page.getByRole('menuitem', {
			name: 'Assign Users',
		});
		this.assignUsersTable = this.assignUsersIFrame.locator(
			'#_com_liferay_item_selector_web_portlet_ItemSelectorPortlet_entriesSearchContainer'
		);
		this.assignUsersTableRow = async (
			colPosition: number,
			value: string,
			strictEqual: boolean = false
		) => {
			return await searchTableRowByValue(
				this.assignUsersTable,
				colPosition,
				value,
				strictEqual
			);
		};
		this.assignUsersMenuItem = page.getByRole('menuitem', {
			name: 'Assign Users',
		});
		this.clearButton = page.getByRole('button', {name: 'Clear'});
		this.deactivateButton = page.getByRole('button', {name: 'Deactivate'});
		this.deactivateUserMenuItem = page.getByRole('menuitem', {
			name: 'Deactivate',
		});
		this.deleteButton = page.getByRole('button', {name: 'Delete'});
		this.deletePersonalDataMenuItem = page.getByRole('menuitem', {
			name: 'Delete Personal Data',
		});
		this.exportImportOptionsMenuItem = page.getByRole('menuitem', {
			name: 'Export / Import',
		});
		this.exportUsersOptionsMenuItem = page.getByRole('menuitem', {
			name: 'Export Users',
		});
		this.exportPersonalDataItem = page.getByRole('menuitem', {
			name: 'Export Personal Data',
		});
		this.impersonateUserMenuItem = page.getByRole('menuitem', {
			name: 'Impersonate User',
		});
		this.manageCustomFieldsOptionsMenuItem = page.getByRole('menuitem', {
			name: 'Manage Custom Fields',
		});
		this.myOrganizationsBreadcrumbLink = (organizationName: string) => {
			return page.getByRole('link', {
				name: organizationName,
			});
		};
		this.myOrganizationsMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'My Organizations',
		});
		this.myOrganizationsTable = page.locator(
			'#_com_liferay_users_admin_web_portlet_MyOrganizationsPortlet_organizationsSearchContainer'
		);
		this.myOrganizationsTableRow = async (
			colPosition: number,
			value: string,
			strictEqual: boolean = false
		) => {
			return await searchTableRowByValue(
				this.myOrganizationsTable,
				colPosition,
				value,
				strictEqual
			);
		};
		this.myOrganizationsTableRowLink = async (organizationName: string) => {
			const myOrganizationsTableRow = await this.myOrganizationsTableRow(
				1,
				organizationName,
				true
			);

			if (myOrganizationsTableRow && myOrganizationsTableRow.column) {
				return myOrganizationsTableRow.column.getByRole('link', {
					name: organizationName,
				});
			}

			throw new Error(
				`Cannot locate organization row with name ${organizationName}`
			);
		};
		this.myOrganizationsUserAndOrgsTable = page.locator(
			'#_com_liferay_users_admin_web_portlet_MyOrganizationsPortlet_organizationUsersSearchContainer'
		);
		this.myOrganizationsUserAndOrgsTableRow = async (
			colPosition: number,
			value: string,
			strictEqual: boolean = false
		) => {
			return await searchTableRowByValue(
				this.myOrganizationsUserAndOrgsTable,
				colPosition,
				value,
				strictEqual
			);
		};
		this.myOrganizationsUserAndOrgsTableRowLink = async (
			organizationName: string
		) => {
			const row = await this.myOrganizationsUserAndOrgsTableRow(
				1,
				organizationName,
				true
			);

			if (row && row.column) {
				return row.column.getByRole('link', {
					name: organizationName,
				});
			}

			throw new Error(
				`Cannot locate organization row with name ${organizationName}`
			);
		};
		this.noUsersMessage = page.getByText('No users were found');
		this.optionsMenu = page
			.getByTestId('headerOptions')
			.getByLabel('Options');
		this.organizationActionsMenu = async (organizationName: string) => {
			const organizationsTableRow = await this.organizationsTableRow(
				1,
				organizationName,
				true
			);

			if (organizationsTableRow && organizationsTableRow.row) {
				const organizationActionsMenu =
					organizationsTableRow.row.getByLabel('Show Actions');

				if (organizationActionsMenu) {
					return organizationActionsMenu;
				}
			}
			else {
				throw new Error(
					`Cannot locate organization row with organizationName ${organizationName}`
				);
			}

			throw new Error(`Cannot locate button with label: Show Actions`);
		};
		this.organizationChartLink = page.getByRole('link', {
			exact: true,
			name: 'Organization Chart',
		});
		this.organizationsLink = page.getByRole('link', {
			name: 'Organizations',
		});
		this.organizationsTable = page.locator(
			'#_com_liferay_users_admin_web_portlet_UsersAdminPortlet_organizationsSearchContainer'
		);
		this.organizationUsersTable = page.locator(
			'[id$="_organizationUsersSearchContainer"]'
		);
		this.organizationUsersTableRow = async (
			colPosition: number,
			value: string,
			strictEqual: boolean = false
		) => {
			return await searchTableRowByValue(
				this.organizationUsersTable,
				colPosition,
				value,
				strictEqual
			);
		};
		this.organizationUsersTableRowActions = async (name: string) => {
			const organizationUsersTableRow =
				await this.organizationUsersTableRow(1, name, true);

			if (organizationUsersTableRow && organizationUsersTableRow.column) {
				return organizationUsersTableRow.row.getByLabel('Show Actions');
			}

			throw new Error(`Cannot locate user row with screenName ${name}`);
		};
		this.organizationUsersTableRowLink = async (screenName: string) => {
			const organizationUsersTableRow =
				await this.organizationUsersTableRow(1, screenName, true);

			if (organizationUsersTableRow && organizationUsersTableRow.column) {
				return organizationUsersTableRow.column.getByRole('link', {
					name: screenName,
				});
			}

			throw new Error(
				`Cannot locate user row with screenName ${screenName}`
			);
		};
		this.organizationUsersTableRowStatusLink = async (
			name: string,
			status: string
		) => {
			const organizationUsersTableRow =
				await this.organizationUsersTableRow(1, name, true);

			if (organizationUsersTableRow && organizationUsersTableRow.row) {
				return organizationUsersTableRow.row.getByRole('link', {
					name: `${status}`,
				});
			}

			throw new Error(`Cannot locate user row with screenName ${name}`);
		};
		this.assignUsersCheckbox = async (userName: string) => {
			const assignUsersTableRow = await this.assignUsersTableRow(
				1,
				userName
			);

			if (assignUsersTableRow && assignUsersTableRow.row) {
				return assignUsersTableRow.row.getByRole('checkbox');
			}
		};
		this.assignUsersDoneButton = page.getByRole('button', {name: 'Done'});
		this.organizationsTableRow = async (
			colPosition: number,
			value: string,
			strictEqual: boolean = false
		) => {
			return await searchTableRowByValue(
				this.organizationsTable,
				colPosition,
				value,
				strictEqual
			);
		};
		this.organizationsTableRowLink = async (organizationName: string) => {
			const myOrganizationsTableRow = await this.organizationsTableRow(
				1,
				organizationName,
				true
			);

			if (myOrganizationsTableRow && myOrganizationsTableRow.column) {
				return myOrganizationsTableRow.column.getByRole('link', {
					name: organizationName,
				});
			}

			throw new Error(
				`Cannot locate organization row with name ${organizationName}`
			);
		};
		this.page = page;
		this.pageTitle = page.getByTestId('headerTitle');
		this.usersCheckbox = async (userName: string) => {
			const usersTableRow = await this.usersTableRow(1, userName);

			if (usersTableRow && usersTableRow.row) {
				return usersTableRow.row.getByRole('checkbox');
			}
		};
		this.usersSearchBar = page.getByPlaceholder('Search for');
		this.usersSearchBarButton = page.getByRole('button', {
			name: 'Search for',
		});
		this.usersTableRow = async (
			colPosition: number,
			value: string,
			strictEqual: boolean = false
		) => {
			return await searchTableRowByValue(
				this.usersTable,
				colPosition,
				value,
				strictEqual
			);
		};
		this.selectAllUsersCheckBox = page
			.locator('.management-bar')
			.getByLabel('Select All Users on the Page');
		this.tableFilterMenu = page
			.locator('.management-bar')
			.getByLabel('Filter');
		this.tableFilterMenuItem = (option: string) => {
			if (option === 'all') {
				return page
					.locator('.dropdown-menu')
					.getByRole('menuitem', {name: option})
					.first();
			}

			return page.locator('.dropdown-menu').getByRole('menuitem', {
				name: option,
			});
		};
		this.tableOrderMenu = page
			.locator('.management-bar')
			.getByLabel('Order');
		this.tableOrderLastLoginDateItem = page.getByRole('menuitem', {
			name: 'Last Login Date',
		});
		this.usersTableRowLink = async (screenName: string) => {
			const usersTableRow = await this.usersTableRow(2, screenName, true);

			if (usersTableRow && usersTableRow.column) {
				return usersTableRow.column.getByRole('link', {
					name: screenName,
				});
			}

			throw new Error(
				`Cannot locate user row with screenName ${screenName}`
			);
		};
		this.usersTableRowActions = async (screenName: string) => {
			const usersTableRow = await this.usersTableRow(2, screenName, true);

			if (usersTableRow && usersTableRow.column) {
				return usersTableRow.row.getByLabel('Show Actions');
			}

			throw new Error(
				`Cannot locate user row with screenName ${screenName}`
			);
		};
		this.usersLink = page.getByRole('link', {name: 'Users'});
		this.userPersonalMenuButton = page.getByTestId('userPersonalMenu');
		this.usersTable = page.locator(
			'#_com_liferay_users_admin_web_portlet_UsersAdminPortlet_usersSearchContainer'
		);
		this.usersTableCell = (userName: string) => {
			return this.page.getByRole('cell', {
				exact: true,
				name: userName,
			});
		};
	}

	async activateUsers(userNames: string[]) {
		for (const user of userNames) {
			await (await this.usersCheckbox(user)).check();
		}
		await this.activateButton.click();
		await waitForAlert(this.page);
	}

	async deActivateUsers(userNames: string[]) {
		for (const user of userNames) {
			await (await this.usersCheckbox(user)).check();
		}

		await this.deactivateButton.click();

		await waitForAlert(this.page);
	}

	async deleteUsers(userNames: string[]) {
		for (const userName of userNames) {
			await (await this.usersCheckbox(userName)).check();
		}

		await this.deleteButton.click();

		await waitForAlert(this.page);
	}

	async goto(forceReload?: boolean) {
		await this.applicationsMenuPage.goToUsersAndOrganizations(forceReload);
	}

	async goToOrganizations(forceReload?: boolean) {
		await this.goto(forceReload);
		await Promise.all([
			this.organizationsLink.click(),
			this.page.waitForResponse(
				(resp) =>
					resp.status() === 200 &&
					resp
						.url()
						.includes('screenNavigationCategoryKey=organizations')
			),
		]);
	}

	async goToOrganizationsWithLimitedAccess() {
		await this.applicationsMenuPage.goToUsersAndOrganizationsWithLimitedAccess();
		await Promise.all([
			this.organizationsLink.click(),
			this.page.waitForResponse(
				(resp) =>
					resp.status() === 200 &&
					resp
						.url()
						.includes('screenNavigationCategoryKey=organizations')
			),
		]);
	}

	async goToOrganizationChart(forceReload?: boolean) {
		await this.goto(forceReload);
		await Promise.all([
			this.organizationChartLink.click(),
			this.page.waitForResponse(
				(resp) =>
					resp.status() === 200 &&
					resp
						.url()
						.includes(
							'screenNavigationCategoryKey=commerce-organization'
						)
			),
		]);
	}

	async goToMyOrganizations() {
		await Promise.all([
			this.userPersonalMenuButton.click(),
			this.myOrganizationsMenuItem.click(),
			this.page.waitForResponse(
				(resp) =>
					resp.status() === 200 &&
					resp
						.url()
						.includes(
							'id=com_liferay_users_admin_web_portlet_MyOrganizationsPortlet'
						)
			),
		]);
	}

	async goToUsers(forceReload?: boolean) {
		await this.goto(forceReload);
		await Promise.all([
			this.usersLink.click(),
			this.page.waitForResponse(
				(resp) =>
					resp.status() === 200 &&
					resp.url().includes('screenNavigationCategoryKey=users')
			),
		]);
	}

	async filterUsers(option: string) {
		await Promise.all([
			clickAndExpectToBeVisible({
				autoClick: true,
				target: this.tableFilterMenuItem(option),
				trigger: this.tableFilterMenu,
			}),
			this.page.waitForResponse(
				(resp) =>
					resp.status() === 200 &&
					resp.url().includes('navigation=' + option)
			),
		]);
	}

	async openOptionsMenu() {
		await this.optionsMenu
			.and(this.page.locator('[aria-haspopup]'))
			.click();
	}
}
