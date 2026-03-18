/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {GlobalMenuPage} from '../../../../pages/product-navigation-applications-menu/GlobalMenuPage';
import {clickAndExpectToBeVisible} from '../../../../utils/clickAndExpectToBeVisible';

export class UserViewsPage {
	readonly adminOptionsDropdown: Locator;
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly bulkActions: {
		actionsDropdownButton: Locator;
		container: Locator;
		deleteButton: Locator;
	};
	readonly emptyStateTitle: Locator;
	readonly filterActionButton: Locator;
	readonly filterButton: Locator;
	filterItem: Locator;
	readonly filterResumeButton: Locator;
	readonly globalMenuPage: GlobalMenuPage;
	readonly manageUserViewsMenuItem: Locator;
	readonly managementToolbar: {
		container: Locator;
	};
	readonly page: Page;
	readonly pageContainer: Locator;
	readonly table: {
		bodyRows: Locator;
		container: Locator;
		headRow: Locator;
	};

	constructor(page: Page) {
		this.adminOptionsDropdown = page.locator(
			'button[data-qa-id="fdsAdminOptionsMenu"]'
		);
		this.emptyStateTitle = page
			.getByTestId('visualization-mode-table')
			.getByText('No Results Found');
		this.filterActionButton = page
			.getByRole('button', {exact: true, name: 'Add Filter'})
			.or(page.getByRole('button', {exact: true, name: 'Show Results'}))
			.or(page.getByRole('button', {exact: true, name: 'Delete Filter'}));
		const managementToolbarContainer =
			page.getByTestId('managementToolbar');
		this.filterButton = page.locator('.filters-dropdown-button');
		this.filterResumeButton = page.locator('.filter-resume');
		this.globalMenuPage = new GlobalMenuPage(page);

		this.manageUserViewsMenuItem = page.getByRole('menuitem', {
			name: 'Manage User Views',
		});
		this.managementToolbar = {
			container: managementToolbarContainer,
		};
		this.page = page;
		this.pageContainer = page.locator('.data-sets.manage-user-views');
		const bulkActionsContainer = page.locator('.bulk-actions');

		this.bulkActions = {
			actionsDropdownButton: bulkActionsContainer.getByLabel('Actions'),
			container: bulkActionsContainer,
			deleteButton: bulkActionsContainer.getByRole('button', {
				name: 'Delete',
			}),
		};

		const tableContainer = page.locator('.fds table');

		this.table = {
			bodyRows: tableContainer.locator('tbody tr'),
			container: tableContainer,
			headRow: tableContainer.locator('thead tr'),
		};
	}

	async goto() {
		await this.globalMenuPage.goToControlPanel('Data Sets');

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.manageUserViewsMenuItem,
			trigger: this.adminOptionsDropdown,
		});

		await this.waitForReadyState();
	}

	async waitForReadyState() {
		const tableLoadingAnimation = this.pageContainer
			.locator('.fds', {has: this.table.container})
			.locator('.loading-animation')
			.first();

		await this.pageContainer.waitFor({state: 'visible'});
		await expect(tableLoadingAnimation).not.toBeVisible();
		await this.table.container.waitFor({state: 'visible'});
	}

	getRowSelectionCheckbox(rowText: string) {
		return this.table.bodyRows
			.filter({hasText: rowText})
			.first()
			.locator('.cell-select-item')
			.getByRole('checkbox');
	}

	async openFilter(
		filterLabel: string,
		{targetAfterOpen}: {targetAfterOpen?: Locator} = {}
	) {
		await this.filterButton.first().waitFor({state: 'visible'});

		const filtersDropdownMenu = this.page
			.locator('.dropdown-menu.show')
			.filter({
				has: this.page.getByRole('menuitem', {
					exact: true,
					name: filterLabel,
				}),
			})
			.first();
		const menuItem = filtersDropdownMenu.getByRole('menuitem', {
			exact: true,
			name: filterLabel,
		});

		await clickAndExpectToBeVisible({
			target: menuItem,
			trigger: this.filterButton.first(),
		});

		if (targetAfterOpen) {
			await clickAndExpectToBeVisible({
				target: targetAfterOpen,
				trigger: menuItem,
			});
		}
		else {
			await menuItem.click();
		}

		this.filterItem = filtersDropdownMenu;
	}

	waitForSnapshotsResponse() {
		return this.page.waitForResponse(
			(response) =>
				response.status() === 200 &&
				response.url().includes('/o/data-set-admin/snapshots')
		);
	}

	async clickBulkDeleteAction() {
		if (await this.bulkActions.deleteButton.isVisible()) {
			await this.bulkActions.deleteButton.click();

			return;
		}

		const {actionsDropdownButton} = this.bulkActions;

		await actionsDropdownButton.waitFor({state: 'visible'});

		const actionsDropdownMenu = this.page
			.locator('.dropdown-menu.show')
			.filter({
				has: this.page.getByRole('menuitem', {
					exact: true,
					name: 'Delete',
				}),
			})
			.first();
		const deleteMenuItem = actionsDropdownMenu.getByRole('menuitem', {
			exact: true,
			name: 'Delete',
		});

		await clickAndExpectToBeVisible({
			target: deleteMenuItem,
			trigger: actionsDropdownButton,
		});

		await deleteMenuItem.click();
	}
}
