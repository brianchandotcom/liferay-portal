/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import {waitForAlert} from '../../../utils/waitForAlert';
import {designLibrariesPageTest} from './fixtures/designLibrariesPageTest';

const test = mergeTests(
	apiHelpersTest,
	designLibrariesPageTest,
	featureFlagsTest({
		'LPD-11235': {enabled: true},
		'LPD-17564': {enabled: true},
		'LPD-34594': {enabled: true},
		'LPD-36105': {enabled: true},
		'LPD-57283': {enabled: true},
	}),
	loginTest()
);

async function expectRedirectionToLibrary(name: string, page: Page) {
	const breadcrumb = page.getByRole('navigation', {
		name: 'Breadcrumb',
	});

	await expect(breadcrumb).toBeVisible();

	const links = breadcrumb.getByRole('link');

	await expect(links).toHaveCount(2);

	await expect(links.first()).toHaveText('Design Libraries');
	await expect(links.last()).toHaveText(name);
}

test('Check if design library is working correctly', async ({
	designLibrariesPage,
	page,
}) => {
	await test.step('Can navigate to design libraries page', async () => {
		await designLibrariesPage.goto();

		await expect(page.getByTestId('header')).toHaveText('Design Libraries');
	});

	await test.step('Check that the empty state labels are correct', async () => {
		await expect(page.getByText('No Design Libraries Yet')).toBeVisible();

		await expect(
			page.getByText('Click "New" to create your first Design Library.')
		).toBeVisible();

		await expect(
			page.getByRole('button', {name: 'New Design Library'})
		).toBeVisible();
	});

	await test.step('Check that the "/states/design_library_empty_state.svg" image is displayed', async () => {
		await expect(
			designLibrariesPage.emptyStateContainer.locator(
				'img[src$="/states/design_library_empty_state.svg"]'
			)
		).toBeVisible();
	});
});

test('Can navigate to a design library dashboard', async ({
	apiHelpers,
	designLibrariesPage,
	page,
}) => {
	const designLibraryName = getRandomString();

	const createdDesignLibrary =
		await test.step('Create temporary design library via headless', async () => {
			return await apiHelpers.headlessAssetLibrary.createAssetLibrary({
				name: designLibraryName,
				settings: {},
				type: 'DesignLibrary',
			});
		});

	await test.step('Navigate to a design library dashboard', async () => {
		await designLibrariesPage.goto();

		const designLibraryLink = page.getByRole('link', {
			name: designLibraryName,
		});

		await expect(designLibraryLink).toBeVisible();

		await designLibraryLink.click();
	});

	await test.step('Check dashboard elements', async () => {
		const breadcrumb = page.getByRole('navigation', {name: 'Breadcrumb'});

		await expect(breadcrumb).toBeVisible();

		const links = breadcrumb.getByRole('link');

		expect(await links.count()).toEqual(2);

		expect(links.first()).toHaveText('Design Libraries');

		expect(links.last()).toHaveText(designLibraryName);

		const moreActionsButton = page.getByRole('button', {
			name: 'More Actions',
		});

		await moreActionsButton.click();

		await expect(page.getByRole('menu')).toBeVisible();

		await expect(
			page.getByRole('menu').getByRole('menuitem', {name: 'Settings'})
		).toBeVisible();

		await expect(
			page
				.getByRole('menu')
				.getByRole('menuitem', {name: 'Connected Sites'})
		).toBeVisible();

		await expect(
			page
				.getByRole('menu')
				.getByRole('menuitem', {name: 'Manage Members'})
		).toBeVisible();

		await expect(
			page.getByRole('menu').getByRole('menuitem', {name: 'Import'})
		).toBeVisible();

		await expect(
			page.getByRole('menu').getByRole('menuitem', {name: 'Export'})
		).toBeVisible();

		await expect(
			page.getByRole('menu').getByRole('menuitem', {name: 'Delete'})
		).toBeVisible();
	});

	await test.step('Remove temporary design library', async () => {
		await apiHelpers.headlessAssetLibrary.deleteAssetLibrary(
			createdDesignLibrary.externalReferenceCode
		);
	});
});

test('Should allow managing design libraries through creation, validation, and deletion', async ({
	designLibrariesPage,
	page,
}) => {
	const mainDesignLibraryName = getRandomString();

	const successScenarios = [
		{
			description: getRandomString(),
			name: mainDesignLibraryName,
			stepName: 'Create a design library with all fields populated',
		},
		{
			name: getRandomString(),
			stepName: 'Create a design library with only mandatory fields',
		},
	];

	for (const scenario of successScenarios) {
		await test.step(scenario.stepName, async () => {
			await designLibrariesPage.goto();

			await designLibrariesPage.create(scenario);

			await waitForAlert(
				page,
				`Success:${scenario.name} was created successfully.`
			);

			await expectRedirectionToLibrary(scenario.name, page);
		});
	}

	await test.step('Prevent creation of design library with empty name', async () => {
		await designLibrariesPage.goto();

		await designLibrariesPage.create({
			name: '',
		});

		await expect(
			page
				.locator('.form-feedback-item')
				.getByText('Error: This field is required.')
		).toBeVisible();

		await expect(page.getByRole('button', {name: 'Save'})).toBeDisabled();
	});

	await test.step('Prevent creation of duplicate design libraries and maintain modal state', async () => {
		await designLibrariesPage.goto();

		await designLibrariesPage.create({
			name: mainDesignLibraryName,
		});

		await waitForAlert(page, 'Error:Please enter a unique name.', {
			timeout: 5000,
			type: 'danger',
		});

		await expect(
			page
				.locator('.form-feedback-item')
				.getByText('Error: Please enter a unique name.')
		).toBeVisible();

		await expect(page.getByLabel('Name')).toHaveValue(
			mainDesignLibraryName
		);
	});

	await test.step('Delete created design libraries', async () => {
		await designLibrariesPage.goto();

		for (const {name} of successScenarios) {
			await designLibrariesPage.delete(name);
		}
	});
});

test('Can connect and disconnect a site from a design library', async ({
	designLibrariesPage,
	page,
}) => {
	const designLibraryName = getRandomString();
	const siteName = 'Liferay DXP';

	const connectedSitesDialog = page.getByRole('dialog');

	await test.step('Create a new design library', async () => {
		await designLibrariesPage.goto();

		await designLibrariesPage.create({name: designLibraryName});

		await waitForAlert(
			page,
			`Success:${designLibraryName} was created successfully.`
		);

		await expectRedirectionToLibrary(designLibraryName, page);
	});

	await test.step('Connect a site to design library', async () => {
		const connectedSitesMenuItem = page
			.getByRole('menu')
			.getByRole('menuitem', {name: 'Connected Sites'});

		await page
			.getByRole('button', {
				name: 'More Actions',
			})
			.click();

		await expect(page.getByRole('menu')).toBeVisible();

		await expect(connectedSitesMenuItem).toBeVisible();

		await connectedSitesMenuItem.click();

		await connectedSitesDialog
			.getByPlaceholder('Select a Site')
			.fill(siteName);

		await page.getByRole('option', {name: siteName}).click();

		await expect(
			connectedSitesDialog.getByRole('button', {name: 'Connect'})
		).toBeEnabled();

		await connectedSitesDialog
			.getByRole('button', {name: 'Connect'})
			.click();

		await waitForAlert(
			page,
			`Success:Site ${siteName} was successfully connected to the design library.`,
			{autoClose: false}
		);
	});

	await test.step('Disconnect a site from a design library', async () => {
		await expect(
			connectedSitesDialog.getByRole('button', {name: 'Disconnect'})
		).toBeVisible();

		await connectedSitesDialog
			.getByRole('button', {name: 'Disconnect'})
			.click();

		await waitForAlert(
			page,
			`Success:Site ${siteName} was successfully disconnected from the design library.`,
			{autoClose: false}
		);
	});

	await test.step('Delete created design library', async () => {
		await designLibrariesPage.goto();

		await designLibrariesPage.delete(designLibraryName);
	});
});

test('Can view and edit a design library settings', async ({
	designLibrariesPage,
	page,
}) => {
	const designLibraryName = getRandomString();
	const editedDesignLibraryName = getRandomString();
	const editedDesignLibraryDescription = getRandomString();

	await test.step('Create a new design library', async () => {
		await designLibrariesPage.goto();

		await designLibrariesPage.create({name: designLibraryName});

		await waitForAlert(
			page,
			`Success:${designLibraryName} was created successfully.`
		);

		await expectRedirectionToLibrary(designLibraryName, page);
	});

	await test.step('View the design library settings', async () => {
		const settingsMenuItem = page
			.getByRole('menu')
			.getByRole('menuitem', {name: 'Settings'});

		await page
			.getByRole('button', {
				name: 'More Actions',
			})
			.click();

		await expect(page.getByRole('menu')).toBeVisible();

		await expect(settingsMenuItem).toBeVisible();

		await settingsMenuItem.click();

		await expect(page.getByRole('textbox', {name: 'Name'})).toHaveValue(
			designLibraryName
		);

		await expect(
			page.getByRole('textbox', {name: 'Description'})
		).toHaveValue('');
	});

	await test.step('Edit the design library settings', async () => {
		await page
			.getByRole('textbox', {name: 'Name'})
			.fill(editedDesignLibraryName);

		await page
			.getByRole('textbox', {name: 'Description'})
			.fill(editedDesignLibraryDescription);

		await page.getByRole('button', {name: 'Save'}).click();

		await waitForAlert(
			page,
			`Success:${editedDesignLibraryName} was saved successfully.`
		);

		await page.reload();

		await expect(page.getByRole('textbox', {name: 'Name'})).toHaveValue(
			editedDesignLibraryName
		);

		await expect(
			page.getByRole('textbox', {name: 'Description'})
		).toHaveValue(editedDesignLibraryDescription);
	});

	await test.step('Delete created design library', async () => {
		await designLibrariesPage.goto();

		await designLibrariesPage.delete(editedDesignLibraryName);
	});
});
