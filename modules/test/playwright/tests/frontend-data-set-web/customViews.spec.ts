/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';
import {fdsSamplePageTest} from './fixtures/fdsSamplePageTest';

export const test = mergeTests(
	apiHelpersTest,
	fdsSamplePageTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest()
);

test(
	'Custom Views in frontend data set',
	{
		tag: [
			'@LPS-114812',
			'@LPS-130101',
			'@LPS-158545',
			'@LPS-163823',
			'@LPS-164691',
		],
	},
	async ({apiHelpers, fdsSamplePage, page, site}) => {
		let actionsDropdownId: string;
		let customViewsDropdownId: string;
		const customView1Name = getRandomString();
		const customView2Name = getRandomString();

		await test.step('Create a content site and with FDS sample widget', async () => {
			await fdsSamplePage.setupFDSSampleWidget({apiHelpers, site});
		});

		await test.step('Select Customized tab', async () => {
			await fdsSamplePage.selectTab('Customized');
		});

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
