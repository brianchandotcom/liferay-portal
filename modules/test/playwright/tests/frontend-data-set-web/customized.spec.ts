/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, expect, mergeTests} from '@playwright/test';

import {accountSettingsPagesTest} from '../../fixtures/accountSettingsPagesTest';
import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../utils/getRandomString';
import {fdsSamplePageTest} from './fixtures/fdsSamplePageTest';

const test = mergeTests(
	apiHelpersTest,
	fdsSamplePageTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest()
);

const accountSettingsTest = mergeTests(test, accountSettingsPagesTest);

let fdsSamplePageURL: string;

test.beforeEach(async ({fdsSamplePage, page, site}) => {
	const {url} = await fdsSamplePage.setupFDSSampleWidget({site});

	fdsSamplePageURL = url;

	await fdsSamplePage.selectTab('Customized');

	await expect(
		page.getByText('This is a description for sample 1.')
	).toBeVisible();
});

test(
	'Check behavior of custom views',
	{
		tag: ['@LPS-130101'],
	},
	async ({fdsSamplePage, page}) => {
		let actionsDropdown: Locator;
		let customViewsDropdown: Locator;
		let columnsVisibilityDropdown: Locator;

		const customView1Name = getRandomString();
		const customView2Name = getRandomString();

		await test.step('Get dropdown references', async () => {

			// Click on dropdown toggle button adds the aria-controls attribute

			await fdsSamplePage.customViewsActionsButton.click();

			const actionsDropdownId =
				await fdsSamplePage.customViewsActionsButton.getAttribute(
					'aria-controls'
				);

			actionsDropdown = page.locator(`#${actionsDropdownId}`);

			await fdsSamplePage.customViewsSelectorButton.click();

			const customViewsDropdownId =
				await fdsSamplePage.customViewsSelectorButton.getAttribute(
					'aria-controls'
				);

			customViewsDropdown = page.locator(`#${customViewsDropdownId}`);

			await fdsSamplePage.table.manageColumnsVisibilityButton.click();

			const columnsVisibilityDropdownId =
				await fdsSamplePage.table.manageColumnsVisibilityButton.getAttribute(
					'aria-controls'
				);

			columnsVisibilityDropdown = page.locator(
				`#${columnsVisibilityDropdownId}`
			);
		});

		await test.step('Create a custom views and set it as the default one', async () => {
			await fdsSamplePage.customViewsActionsButton.click();

			await actionsDropdown
				.filter({has: page.getByRole('menu')})
				.waitFor();

			const menuItem = actionsDropdown.getByRole('menuitem', {
				name: 'Save View As...',
			});

			await expect(menuItem).toBeVisible();

			await menuItem.click();

			await expect(fdsSamplePage.customViewsSaveModal).toBeInViewport();

			await fdsSamplePage.customViewsSaveModal
				.getByLabel('NameRequired')
				.fill(customView1Name);

			await fdsSamplePage.customViewsSaveModal
				.getByRole('button', {name: 'Save'})
				.click();

			await fdsSamplePage.customViewsActionsButton.click();

			await actionsDropdown
				.filter({has: page.getByRole('menu')})
				.waitFor();

			await actionsDropdown
				.getByRole('menuitem', {name: 'Save View As...'})
				.click();

			await expect(fdsSamplePage.customViewsSaveModal).toBeInViewport();

			await fdsSamplePage.customViewsSaveModal
				.getByLabel('NameRequired')
				.fill(customView2Name);
			await fdsSamplePage.customViewsSaveModal
				.getByRole('button', {name: 'Save'})
				.click();

			await expect(fdsSamplePage.customViewsSelectorButton).toHaveText(
				customView2Name
			);

			await fdsSamplePage.customViewsSelectorButton.click();

			await expect(customViewsDropdown.getByRole('option')).toHaveCount(
				3
			);
		});

		await test.step('Edit custom view, by changing visibility of one column', async () => {
			await expect(fdsSamplePage.table.headerCells).toHaveCount(10);

			await fdsSamplePage.table.manageColumnsVisibilityButton.click();

			await columnsVisibilityDropdown
				.getByRole('menuitem', {name: 'Description'})
				.click();

			await expect(fdsSamplePage.table.headerCells).toHaveCount(9);

			await fdsSamplePage.customViewsActionsButton.click();

			page.keyboard.press('Escape');
		});

		await test.step('Confirm that changes in a custom view does not affect Default View', async () => {
			await expect(fdsSamplePage.customViewsSelectorButton).toHaveText(
				customView2Name
			);

			await expect(fdsSamplePage.table.headerCells).toHaveCount(9);

			await fdsSamplePage.customViewsSelectorButton.click();

			await customViewsDropdown.waitFor();

			await customViewsDropdown
				.getByRole('option', {name: 'Default View'})
				.click();

			await expect(fdsSamplePage.table.headerCells).toHaveCount(10);
		});

		await test.step('Can change a custom view name', async () => {
			await fdsSamplePage.customViewsSelectorButton.click();

			await customViewsDropdown.waitFor();

			await customViewsDropdown
				.getByRole('option', {name: customView2Name})
				.click();

			await fdsSamplePage.customViewsActionsButton.click();

			await actionsDropdown.waitFor();

			const menuItem = actionsDropdown.getByRole('menuitem', {
				name: 'Rename View',
			});

			await expect(menuItem).toBeVisible();

			await menuItem.click();

			await expect(fdsSamplePage.customViewsSaveModal).toBeInViewport();

			const newCustomViewName = getRandomString();

			await fdsSamplePage.customViewsSaveModal
				.getByLabel('NameRequired')
				.fill(newCustomViewName);

			await fdsSamplePage.customViewsSaveModal
				.getByRole('button', {name: 'Save'})
				.click();

			await expect(fdsSamplePage.customViewsSelectorButton).toHaveText(
				newCustomViewName
			);
		});

		await test.step('Delete a custom view', async () => {
			await fdsSamplePage.customViewsSelectorButton.click();

			await customViewsDropdown.waitFor();

			await customViewsDropdown
				.getByRole('option', {name: customView1Name})
				.click();

			await fdsSamplePage.customViewsActionsButton.click();

			await actionsDropdown.waitFor();

			const menuItem = actionsDropdown.getByRole('menuitem', {
				name: 'Delete View',
			});

			await expect(menuItem).toBeVisible();

			await menuItem.click();

			await expect(fdsSamplePage.customViewsDeleteAlert).toBeVisible();

			await fdsSamplePage.customViewsDeleteAlert
				.getByRole('button', {name: 'Delete'})
				.click();

			await fdsSamplePage.customViewsSelectorButton.click();

			await customViewsDropdown.waitFor();

			await expect(
				customViewsDropdown.getByRole('option', {name: customView1Name})
			).not.toBeVisible();
		});
	}
);

test('Check behavior of item actions', async ({fdsSamplePage, page}) => {
	const sidePanelActionLabelWithActionTitle = 'Side Panel With Action Title';
	const sidePanelActionLabelWithContentTitle =
		'Side Panel With Content Title';
	const sidePanelActionLabelWithActionTitleContentTitle =
		'Side Panel With Action and Content Title';
	const sidePanelActionLabelWithoutTitle = 'Side Panel With No Title';
	const sidePanelActionTitle = 'Side Panel Title Provided by Action';
	const sidePanelContentTitle = 'Side Panel Title Provided by Page';

	const itemActionsCell = fdsSamplePage.table.itemActionsCells.first();

	const itemActionButton = itemActionsCell.getByRole('button', {
		exact: true,
		name: 'Actions',
	});

	await test.step('Check that the Item Actions dropdown is present in table row', async () => {
		await expect(itemActionButton).toBeVisible();

		const dropdownId = await itemActionButton.getAttribute('aria-controls');

		await itemActionButton.click();

		await page
			.locator(`#${dropdownId}`)
			.filter({has: page.getByRole('menu')})
			.waitFor();

		await expect(
			page.locator(`#${dropdownId}`).getByRole('menuitem')
		).toHaveCount(13);

		await page.keyboard.press('Escape');
	});

	await test.step('Side Panel action opens a side panel with content title', async () => {
		const dropdownId = await itemActionButton.getAttribute('aria-controls');

		await itemActionButton.click();

		await page
			.locator(`#${dropdownId}`)
			.filter({has: page.getByRole('menu')})
			.waitFor();

		await page
			.locator(`#${dropdownId}`)
			.getByRole('menuitem', {
				exact: true,
				name: sidePanelActionLabelWithContentTitle,
			})
			.click();

		await expect(fdsSamplePage.sidePanel).toBeInViewport();

		const frame = fdsSamplePage.sidePanelFrame;

		await frame.getByText(sidePanelContentTitle).waitFor();

		await expect(frame.getByText(sidePanelContentTitle)).toHaveCount(1);

		await expect(
			frame.getByText('This is a side panel with a title.')
		).toBeVisible();

		await page.keyboard.press('Escape');

		await expect(fdsSamplePage.sidePanel).toHaveClass(/is-hidden/);
	});

	await test.step('Side Panel action opens a side panel with action title', async () => {
		const dropdownId = await itemActionButton.getAttribute('aria-controls');
		await itemActionButton.click();

		await page
			.locator(`#${dropdownId}`)
			.filter({has: page.getByRole('menu')})
			.waitFor();

		await page
			.locator(`#${dropdownId}`)
			.getByRole('menuitem', {
				exact: true,
				name: sidePanelActionLabelWithActionTitle,
			})
			.click();

		await expect(fdsSamplePage.sidePanel).toBeInViewport();

		await page.getByText(sidePanelActionTitle).waitFor();

		await expect(page.getByText(sidePanelActionTitle)).toHaveCount(1);

		const frame = fdsSamplePage.sidePanelFrame;

		await expect(
			frame.locator('.side-panel-iframe-header')
		).not.toBeInViewport();

		await expect(
			frame.getByText('This is a side panel without a title.')
		).toBeVisible();

		await page.keyboard.press('Escape');

		await expect(fdsSamplePage.sidePanel).toHaveClass(/is-hidden/);
	});

	await test.step('Side Panel action opens a side panel with duplicated title', async () => {
		const dropdownId = await itemActionButton.getAttribute('aria-controls');
		await itemActionButton.click();

		await page
			.locator(`#${dropdownId}`)
			.filter({has: page.getByRole('menu')})
			.waitFor();

		await page
			.locator(`#${dropdownId}`)
			.getByRole('menuitem', {
				exact: true,
				name: sidePanelActionLabelWithActionTitleContentTitle,
			})
			.click();

		await expect(fdsSamplePage.sidePanel).toBeInViewport();

		await page.getByText(sidePanelActionTitle).waitFor();

		await expect(page.getByText(sidePanelActionTitle)).toHaveCount(1);

		const frame = fdsSamplePage.sidePanelFrame;

		await expect(
			frame.locator('.side-panel-iframe-header')
		).toBeInViewport();
		await frame.getByText(sidePanelContentTitle).waitFor();

		await expect(frame.getByText(sidePanelContentTitle)).toHaveCount(1);

		await page.keyboard.press('Escape');

		await expect(fdsSamplePage.sidePanel).toHaveClass(/is-hidden/);
	});

	await test.step('Side Panel action opens a side panel without title', async () => {
		const dropdownId = await itemActionButton.getAttribute('aria-controls');

		await itemActionButton.click();

		await page
			.locator(`#${dropdownId}`)
			.filter({has: page.getByRole('menu')})
			.waitFor();

		await page
			.locator(`#${dropdownId}`)
			.getByRole('menuitem', {
				exact: true,
				name: sidePanelActionLabelWithoutTitle,
			})
			.click();

		await expect(fdsSamplePage.sidePanel).toBeInViewport();

		await expect(page.locator('.fds-side-panel-title')).toBeInViewport();
		const panelTitle = await page
			.locator('.fds-side-panel-title')
			.allInnerTexts();

		expect(panelTitle).toEqual(['']);

		const frame = fdsSamplePage.sidePanelFrame;

		await expect(
			frame.locator('.side-panel-iframe-header')
		).not.toBeInViewport();

		await expect(
			frame.getByText('This is a side panel without a title.')
		).toBeVisible();

		await page.keyboard.press('Escape');

		await expect(fdsSamplePage.sidePanel).toHaveClass(/is-hidden/);
	});
});

test('Use client extensions', async ({fdsSamplePage, page}) => {
	await test.step('Assert that the cell renderer is invoked and the apple emoji is visible', async () => {
		const firstColorCell = fdsSamplePage.table.container
			.locator('td.cell-color')
			.first();

		await expect(firstColorCell).toContainText('🍏');
	});

	await test.step('Assert that the filter client extension is working', async () => {
		const clientExtensionMenuItem = page.getByRole('menuitem', {
			name: 'Client Extension',
		});

		const filterButton = page
			.locator('.filters-dropdown')
			.getByText('Filter');

		await expect(filterButton).toBeInViewport();

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: clientExtensionMenuItem,
			trigger: filterButton,
		});

		const filterInput = page.getByPlaceholder('Search with Odata');

		await expect(filterInput).toBeInViewport();

		await filterInput.fill("title eq 'Sample97'");

		await expect(filterInput).toHaveValue("title eq 'Sample97'");

		const submitButton = page.getByRole('button', {name: 'Submit'});

		await expect(submitButton).toBeInViewport();

		await submitButton.click();

		await expect(page.getByText('Sample97', {exact: true})).toBeVisible();

		const bodyRows = fdsSamplePage.table.container.locator('tbody tr');

		expect(await bodyRows.count()).toEqual(1);
	});
});

test(
	'Sort columns and assert visibility',
	{tag: '@LPS-193005'},
	async ({page}) => {
		await test.step('Sorting ID and Title column in ascending order', async () => {
			const idColumnHeader = page
				.getByRole('columnheader')
				.getByText('ID');

			await expect(idColumnHeader).toBeInViewport();

			let cells = await page.locator('td').allInnerTexts();

			await expect(page.locator('td').nth(1)).toHaveText(cells[1]);
			await expect(page.locator('td').nth(11)).toHaveText(cells[11]);
			await expect(page.locator('td').nth(21)).toHaveText(cells[21]);
			await expect(page.locator('td').nth(31)).toHaveText(cells[31]);

			await Promise.all([
				idColumnHeader.click(),
				page.waitForResponse(
					(response: any) => response.status() === 200
				),
			]);

			const ascendingIDCells = [
				cells[1],
				cells[11],
				cells[21],
				cells[31],
				cells[41],
				cells[51],
				cells[61],
				cells[71],
				cells[81],
				cells[91],
			].sort();

			await expect(page.locator('td').nth(1)).toHaveText(
				ascendingIDCells[0]
			);
			await expect(page.locator('td').nth(11)).toHaveText(
				ascendingIDCells[1]
			);
			await expect(page.locator('td').nth(21)).toHaveText(
				ascendingIDCells[2]
			);
			await expect(page.locator('td').nth(31)).toHaveText(
				ascendingIDCells[3]
			);

			const titleColumnHeader = page
				.getByRole('columnheader')
				.getByText('Title');

			await Promise.all([
				titleColumnHeader.click(),
				page.waitForResponse(
					(response: any) => response.status() === 200
				),
			]);

			cells = await page.locator('td').allInnerTexts();

			const ascendingTitleCells = [
				cells[2],
				cells[12],
				cells[22],
				cells[32],
				cells[42],
				cells[52],
				cells[62],
				cells[72],
				cells[82],
				cells[92],
			].sort((a, b) => new Intl.Collator('en').compare(a, b));

			await expect(page.locator('td').nth(2)).toHaveText(
				ascendingTitleCells[0]
			);
			await expect(page.locator('td').nth(12)).toHaveText(
				ascendingTitleCells[1]
			);
			await expect(page.locator('td').nth(22)).toHaveText(
				ascendingTitleCells[2]
			);
			await expect(page.locator('td').nth(32)).toHaveText(
				ascendingTitleCells[3]
			);
		});

		await test.step('Hide the Title column', async () => {
			const titleColumnHeader = page
				.getByRole('columnheader')
				.getByText('Title');

			await expect(titleColumnHeader).toBeAttached();

			const button = page.getByLabel('Manage Columns Visibility');

			await expect(button).toBeAttached();

			await button.click();

			const titleMenuItem = page.getByRole('menuitem').nth(1);

			await titleMenuItem.click();

			await expect(
				page.getByRole('columnheader').getByText('Title')
			).toBeHidden();
		});
	}
);

test('Select items count label in bulk actions', async ({page}) => {
	await test.step('Change delta to 60 items', async () => {
		await page.getByLabel('Items Per Page').click();

		await page.getByRole('option', {name: '60 Items'}).click();
	});

	await test.step('Select all checkboxes using the select all checkbox', async () => {
		await page
			.locator('input[name="table-head-selector"]')
			.setChecked(true);
	});

	await test.step('Check the label displays "60 of 75 Items Selected"', async () => {
		await expect(page.getByText('60 of 75 Items Selected')).toBeVisible();
	});

	await test.step('Go to 2nd page', async () => {
		await page.getByLabel('Go to page, 2').click();
	});

	await test.step('Select all checkboxes on 2nd page by selecting the checkboxes for each row', async () => {
		for (let i = 1; i <= 15; i++) {
			await page
				.locator(
					`tbody tr:nth-child(${i}) > .cell-select-item input[type="checkbox"]`
				)
				.setChecked(true);
		}
	});

	await test.step('Check the label displays "All Selected (75 of 75 Items)"', async () => {
		await expect(
			page.getByText('All Selected (75 of 75 Items)')
		).toBeVisible();
	});
});

accountSettingsTest(
	'Set time zone from theme display in a datetime renderer',
	{
		tag: ['@LPD-37756'],
	},
	async ({accountSettingsPage, fdsSamplePage, page}) => {
		await test.step('Check date in UTC time zone', async () => {
			await accountSettingsPage.goToDisplaySettings();

			await accountSettingsPage.setTimeZone('UTC');

			await page.goto(fdsSamplePageURL);

			await fdsSamplePage.selectTab('Customized');

			await expect(
				page.getByText('Jan 1, 2020, 12:00:00 AM')
			).toBeVisible();
		});

		await test.step('Check date in a different time zone', async () => {
			await accountSettingsPage.goToDisplaySettings();

			await accountSettingsPage.setTimeZone('Europe/Paris');

			await page.goto(fdsSamplePageURL);

			await fdsSamplePage.selectTab('Customized');

			await expect(
				page.getByText('Jan 1, 2020, 1:00:00 AM')
			).toBeVisible();
		});

		await test.step('Revert to default UTC time zone', async () => {
			await accountSettingsPage.goToDisplaySettings();

			await accountSettingsPage.setTimeZone('UTC');
		});
	}
);

test(
	'Hide column and assert correct visibility of columns',
	{tag: '@LPD-45051'},
	async ({page}) => {
		const initialBodyCellText = await page.locator('td').nth(1).innerText();

		const rowAction = page.locator('td .component-action').first();

		await test.step('Check that row actions are present', async () => {
			await expect(rowAction).toBeAttached();
		});

		await test.step('Hide the first column', async () => {
			const button = page.getByLabel('Manage Columns Visibility');

			await expect(button).toBeAttached();

			await button.click();

			const menuItem = page.getByRole('menuitem').nth(0);

			await menuItem.click();
		});

		await test.step('Check that the first column is hidden and the row actions are still present', async () => {
			await expect(page.locator('td').nth(1)).not.toHaveText(
				initialBodyCellText
			);

			await expect(rowAction).toBeAttached();
		});
	}
);
