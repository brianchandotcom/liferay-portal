/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {usersAndOrganizationsPagesTest} from '../../fixtures/usersAndOrganizationsPagesTest';
import {liferayConfig} from '../../liferay.config';
import {fdsSamplePageTest} from './fixtures/fdsSamplePageTest';

const test = mergeTests(
	dataApiHelpersTest,
	fdsSamplePageTest,
	featureFlagsTest({
		'LPD-37531': {enabled: true},
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	usersAndOrganizationsPagesTest
);

let fdsSamplePageURL: string;
let fdsSamplePageLayout: Layout;

test.beforeEach(async ({fdsSamplePage, page, site}) => {
	const {layout, url} = await fdsSamplePage.setupFDSSampleWidget({site});

	fdsSamplePageLayout = layout;

	fdsSamplePageURL = url;

	await fdsSamplePage.selectTab('Classic');

	await expect(page.getByText('test@liferay.com')).toBeVisible();
});

test(
	'Assert the details shown in the FDS table',
	{tag: '@LPS-162792'},
	async ({fdsSamplePage, page, site}) => {
		await test.step('Check headers are localized in English', async () => {
			await expect(fdsSamplePage.table.headerCells).toHaveCount(4);
			await expect(fdsSamplePage.table.headerCells).toContainText([
				'First Name',
				'Last Name',
				'Email Address',
			]);
		});

		await test.step('Check headers are localized in Spanish', async () => {
			const url = `${liferayConfig.environment.baseUrl}/es/web${site.friendlyUrlPath}${fdsSamplePageLayout.friendlyUrlPath}`;

			await page.goto(url);

			await fdsSamplePage.selectTab('Classic');

			await expect(page.getByText('test@liferay.com')).toBeVisible();

			expect(
				await fdsSamplePage.table.headerCells.allInnerTexts()
			).toEqual(['Nombre', 'Apellido', 'Dirección de correo', '']);
		});
	}
);

test('Check behavior of conditional item actions', async ({
	apiHelpers,
	fdsSamplePage,
	page,
	usersAndOrganizationsPage,
}) => {
	let managerUserAccount: TUserAccount;
	let guestUserAccount: TUserAccount;
	let managerUserItemActionsButton: Locator;

	let guestUserItemActionsButton: Locator;

	await test.step('Create sample users', async () => {
		managerUserAccount = await apiHelpers.headlessAdminUser.postUserAccount(
			{
				emailAddress: 'manager.user@liferay.com',
				givenName: 'Anne',
			}
		);

		guestUserAccount = await apiHelpers.headlessAdminUser.postUserAccount({
			emailAddress: 'guest.user@liferay.com',
			givenName: 'Lewis',
		});

		managerUserItemActionsButton = fdsSamplePage.selectItemActionsByRow(
			managerUserAccount.emailAddress
		);

		guestUserItemActionsButton = fdsSamplePage.selectItemActionsByRow(
			guestUserAccount.emailAddress
		);

		await page.reload();
	});

	await test.step('Check that the Item Actions dropdown is present in table row', async () => {
		await expect(fdsSamplePage.table.container).toBeVisible();

		expect(
			await fdsSamplePage.table.bodyRows.count()
		).toBeGreaterThanOrEqual(3);

		const itemActionsCell = fdsSamplePage.table.itemActionsCells.first();

		const itemActionButton = itemActionsCell.getByRole('button', {
			exact: true,
			name: 'Actions',
		});

		await expect(itemActionButton).toBeVisible();

		const dropdownId = await itemActionButton.getAttribute('aria-controls');

		await itemActionButton.click();

		await page
			.locator(`#${dropdownId}`)
			.filter({has: page.getByRole('menu')})
			.waitFor();

		await expect(page.locator(`#${dropdownId}`)).toBeVisible();

		await page.keyboard.press('Escape');
	});

	await test.step('Check that users have different item actions', async () => {
		await expect(managerUserItemActionsButton).toBeVisible();

		const dropdownId =
			await managerUserItemActionsButton.getAttribute('aria-controls');

		await managerUserItemActionsButton.click();

		await page
			.locator(`#${dropdownId}`)
			.filter({has: page.getByRole('menu')})
			.waitFor();

		await expect(
			page.locator(`#${dropdownId}`).getByRole('menuitem')
		).toHaveCount(4);

		await expect(
			page.locator(`#${dropdownId}`).getByRole('menuitem')
		).toHaveText(['Book', 'Job Archive', 'Deactivate', 'Activity']);

		await page.keyboard.press('Escape');

		await expect(guestUserItemActionsButton).toBeVisible();

		const guestUserdropdownId =
			await guestUserItemActionsButton.getAttribute('aria-controls');

		await guestUserItemActionsButton.click();

		await page
			.locator(`#${guestUserdropdownId}`)
			.filter({has: page.getByRole('menu')})
			.waitFor();

		await expect(
			page.locator(`#${guestUserdropdownId}`).getByRole('menuitem')
		).toHaveCount(3);

		await expect(
			page.locator(`#${guestUserdropdownId}`).getByRole('menuitem')
		).toHaveText(['Book', 'Job Archive', 'Deactivate']);

		await page.keyboard.press('Escape');
	});

	await test.step('Deactivate guest user', async () => {

		// Need this listener to auto accept confirm dialog

		page.on('dialog', async (dialog) => await dialog.accept());

		await usersAndOrganizationsPage.goToUsers();

		await usersAndOrganizationsPage.deActivateUsers([
			guestUserAccount.name,
		]);
	});

	await test.step('Check actions for active and inactive users', async () => {
		await page.goto(fdsSamplePageURL);
		await fdsSamplePage.selectTab('Classic');

		await expect(fdsSamplePage.table.container).toBeVisible();

		await expect(managerUserItemActionsButton).toBeVisible();

		const dropdownId =
			await managerUserItemActionsButton.getAttribute('aria-controls');

		await managerUserItemActionsButton.click();

		await page
			.locator(`#${dropdownId}`)
			.filter({has: page.getByRole('menu')})
			.waitFor();

		await expect(
			page.locator(`#${dropdownId}`).getByRole('menuitem')
		).toHaveCount(4);

		await expect(
			page.locator(`#${dropdownId}`).getByRole('menuitem')
		).toHaveText(['Book', 'Job Archive', 'Deactivate', 'Activity']);

		await page.keyboard.press('Escape');

		await expect(guestUserItemActionsButton).toBeVisible();

		const guestUserdropdownId =
			await guestUserItemActionsButton.getAttribute('aria-controls');

		await guestUserItemActionsButton.click();

		await page
			.locator(`#${guestUserdropdownId}`)
			.filter({has: page.getByRole('menu')})
			.waitFor();

		await expect(
			page.locator(`#${guestUserdropdownId}`).getByRole('menuitem')
		).toHaveCount(3);

		await expect(
			page.locator(`#${guestUserdropdownId}`).getByRole('menuitem')
		).toHaveText(['Book', 'Job Archive', 'Activate']);

		await page.keyboard.press('Escape');
	});
});
