/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

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
		tag: [
			'@LPS-114812',
			'@LPS-130101',
			'@LPS-158545',
			'@LPS-163823',
			'@LPS-164691',
		],
	},
	async ({fdsSamplePage, page}) => {
		let actionsDropdownId: string;
		let customViewsDropdownId: string;
		const customView1Name = getRandomString();
		const customView2Name = getRandomString();

		await test.step('Get dropdown ids reference', async () => {
			actionsDropdownId =
				await fdsSamplePage.customViewsActionsButton.getAttribute(
					'aria-controls'
				);

			// Custom Views dropdown adds the aria-controls attribute after first click

			await fdsSamplePage.customViewsSelectorButton.click();

			customViewsDropdownId =
				await fdsSamplePage.customViewsSelectorButton.getAttribute(
					'aria-controls'
				);
		});

		await test.step('Create a custom views and set it as the default one', async () => {
			await fdsSamplePage.customViewsActionsButton.click();

			await page
				.locator(`#${actionsDropdownId}`)
				.filter({has: page.getByRole('menu')})
				.waitFor();

			await expect(
				page.locator(`#${actionsDropdownId}`).getByRole('menuitem')
			).toHaveText('Save View As...');

			await page
				.locator(`#${actionsDropdownId}`)
				.getByRole('menuitem', {name: 'Save View As...'})
				.click();

			await expect(fdsSamplePage.customViewsSaveModal).toBeInViewport();

			await fdsSamplePage.customViewsSaveModal
				.getByLabel('NameRequired')
				.fill(customView1Name);
			await fdsSamplePage.customViewsSaveModal
				.getByRole('button', {name: 'Save'})
				.click();

			await fdsSamplePage.customViewsActionsButton.click();

			await page
				.locator(`#${actionsDropdownId}`)
				.filter({has: page.getByRole('menu')})
				.waitFor();

			await page
				.locator(`#${actionsDropdownId}`)
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

			await expect(
				page.locator(`#${customViewsDropdownId}`).getByRole('option')
			).toHaveCount(3);
		});

		await test.step('Edit a custom view settings', async () => {
			await expect(
				page.locator('.dnd-table').locator('.dnd-th')
			).toHaveCount(10);

			const tableFieldsDropdownId =
				await fdsSamplePage.fdsTableOpenFieldsMenu.getAttribute(
					'aria-controls'
				);

			await fdsSamplePage.fdsTableOpenFieldsMenu.click();

			await page
				.locator(`#${tableFieldsDropdownId}`)
				.filter({has: page.getByRole('menu')})
				.waitFor();

			await page
				.locator(`#${tableFieldsDropdownId}`)
				.getByRole('menuitem', {name: 'Description'})
				.click();

			await expect(
				page.locator('.dnd-table').locator('.dnd-th')
			).toHaveCount(9);

			await fdsSamplePage.customViewsActionsButton.click();

			await page.locator(`#${actionsDropdownId}`).waitFor();

			page.locator(`#${actionsDropdownId}`).getByRole('menuitem', {
				name: 'Save View',
			});

			page.keyboard.press('Escape');
		});

		await test.step('Confirm that changes in a custom view does not affect Default View', async () => {
			await expect(fdsSamplePage.customViewsSelectorButton).toHaveText(
				customView2Name
			);

			await expect(
				page.locator('.dnd-table').locator('.dnd-th')
			).toHaveCount(9);
			await fdsSamplePage.customViewsSelectorButton.click();

			await page.locator(`#${customViewsDropdownId}`).waitFor();

			await page
				.locator(`#${customViewsDropdownId}`)
				.getByRole('option', {name: 'Default View'})
				.click();

			await expect(
				page.locator('.dnd-table').locator('.dnd-th')
			).toHaveCount(10);
		});

		await test.step('Can change a custom view name', async () => {
			await fdsSamplePage.customViewsSelectorButton.click();

			await page.locator(`#${customViewsDropdownId}`).waitFor();

			await page
				.locator(`#${customViewsDropdownId}`)
				.getByRole('option', {name: customView2Name})
				.click();

			await fdsSamplePage.customViewsActionsButton.click();

			await page.locator(`#${actionsDropdownId}`).waitFor();

			await expect(
				page
					.locator(`#${actionsDropdownId}`)
					.getByRole('menuitem', {name: 'Rename View'})
			).toBeVisible();

			await page
				.locator(`#${actionsDropdownId}`)
				.getByRole('menuitem', {name: 'Rename View'})
				.click();

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

			await page.locator(`#${customViewsDropdownId}`).waitFor();

			await page
				.locator(`#${customViewsDropdownId}`)
				.getByRole('option', {name: customView1Name})
				.click();

			await fdsSamplePage.customViewsActionsButton.click();

			await page.locator(`#${actionsDropdownId}`).waitFor();

			await expect(
				page
					.locator(`#${actionsDropdownId}`)
					.getByRole('menuitem', {name: 'Delete View'})
			).toBeVisible();

			await page
				.locator(`#${actionsDropdownId}`)
				.getByRole('menuitem', {name: 'Delete View'})
				.click();

			await expect(fdsSamplePage.customViewsDeleteAlert).toBeVisible();

			await fdsSamplePage.customViewsDeleteAlert
				.getByRole('button', {name: 'Delete'})
				.click();

			await fdsSamplePage.customViewsSelectorButton.click();

			await page.locator(`#${customViewsDropdownId}`).waitFor();

			await expect(
				page
					.locator(`#${customViewsDropdownId}`)
					.getByRole('option', {name: customView1Name})
			).not.toBeVisible();
		});
	}
);

test('Check behavior of item actions', async ({page}) => {
	const sidePanelActionLabelWithActionTitle = 'Side Panel With Action Title';
	const sidePanelActionLabelWithContentTitle =
		'Side Panel With Content Title';
	const sidePanelActionLabelWithActionTitleContentTitle =
		'Side Panel With Action and Content Title';
	const sidePanelActionLabelWithoutTitle = 'Side Panel With No Title';
	const sidePanelActionTitle = 'Side Panel Title Provided by Action';
	const sidePanelContentTitle = 'Side Panel Title Provided by Page';

	const datasetRow =
		await test.step('Check that the Item Actions dropdown is present in table row', async () => {
			await page
				.locator('.dnd-td.item-actions')
				.first()
				.waitFor({state: 'attached'});

			const tableRow = page.locator('.dnd-td.item-actions').first();

			expect(
				tableRow.getByRole('button', {
					exact: true,
					name: 'Actions',
				})
			).toBeVisible;

			const button = tableRow.getByRole('button', {
				exact: true,
				name: 'Actions',
			});
			const dropdownId = await button.getAttribute('aria-controls');

			await button.click();

			await page
				.locator(`#${dropdownId}`)
				.filter({has: page.getByRole('menu')})
				.waitFor();

			await expect(
				page.locator(`#${dropdownId}`).getByRole('menuitem')
			).toHaveCount(13);

			await page.keyboard.press('Escape');

			return tableRow;
		});

	const itemActionButton =
		await test.step('Check that the Item Action menu is present', async () => {
			const button = datasetRow.getByRole('button', {
				exact: true,
				name: 'Actions',
			});

			await expect(button).toBeInViewport();

			return button;
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

		await page.getByRole('tabpanel').waitFor();

		const sidePanel = page.getByRole('tabpanel');

		await expect(sidePanel).toBeInViewport();

		const iframeElement = await sidePanel.locator('iframe').elementHandle();

		const frame = await iframeElement.contentFrame();

		await frame.getByText(sidePanelContentTitle).waitFor();
		await expect(frame.getByText(sidePanelContentTitle)).toHaveCount(1);

		await page.keyboard.press('Escape');

		await expect(sidePanel).not.toBeInViewport();
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

		await page.getByRole('tabpanel').waitFor();

		const sidePanel = page.getByRole('tabpanel');

		await expect(sidePanel).toBeInViewport();

		await page.getByText(sidePanelActionTitle).waitFor();
		await expect(page.getByText(sidePanelActionTitle)).toHaveCount(1);

		const iframeElement = await sidePanel.locator('iframe').elementHandle();

		const frame = await iframeElement.contentFrame();

		await expect(
			frame.locator('.side-panel-iframe-header')
		).not.toBeInViewport();

		await page.keyboard.press('Escape');

		await expect(sidePanel).not.toBeInViewport();
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

		await page.getByRole('tabpanel').waitFor();

		const sidePanel = page.getByRole('tabpanel');

		await expect(sidePanel).toBeInViewport();

		await page.getByText(sidePanelActionTitle).waitFor();
		await expect(page.getByText(sidePanelActionTitle)).toHaveCount(1);

		const iframeElement = await sidePanel.locator('iframe').elementHandle();

		const frame = await iframeElement.contentFrame();

		await expect(
			frame.locator('.side-panel-iframe-header')
		).toBeInViewport();
		await frame.getByText(sidePanelContentTitle).waitFor();

		await expect(frame.getByText(sidePanelContentTitle)).toHaveCount(1);

		await page.keyboard.press('Escape');

		await expect(sidePanel).not.toBeInViewport();
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

		await page.getByRole('tabpanel').waitFor();

		const sidePanel = page.getByRole('tabpanel');

		await expect(sidePanel).toBeInViewport();

		await expect(page.locator('.fds-side-panel-title')).toBeInViewport();
		const panelTitle = await page
			.locator('.fds-side-panel-title')
			.allInnerTexts();

		expect(panelTitle).toEqual(['']);

		const iframeElement = await sidePanel.locator('iframe').elementHandle();

		const frame = await iframeElement.contentFrame();

		await expect(
			frame.locator('.side-panel-iframe-header')
		).not.toBeInViewport();

		await page.keyboard.press('Escape');

		await expect(sidePanel).not.toBeInViewport();
	});
});

test('Use client extensions', async ({page}) => {
	await test.step('Assert that the cell renderer is invoked and the apple emoji is visible', async () => {
		const firstColorCell = page
			.locator('.dnd-tbody > div > div:nth-child(7)')
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

		filterInput.fill("title eq 'Sample97'");

		await expect(filterInput).toHaveValue("title eq 'Sample97'");

		const submitButton = page.getByRole('button', {name: 'Submit'});

		await expect(submitButton).toBeInViewport();

		await submitButton.click();

		await expect(page.getByText('Sample97', {exact: true})).toBeVisible();

		const rowCount = await page.locator('.dnd-tbody > .dnd-tr').count();

		expect(rowCount).toEqual(1);
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
