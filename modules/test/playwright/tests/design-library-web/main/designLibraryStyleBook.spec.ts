/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import {
	performLoginViaApi,
	performLogout,
	userData,
} from '../../../utils/performLogin';
import {designLibrariesPageTest} from './fixtures/designLibrariesPageTest';

const test = mergeTests(
	apiHelpersTest,
	designLibrariesPageTest,
	featureFlagsTest({
		'LPD-17564': {enabled: true},
		'LPD-34594': {enabled: true},
		'LPD-57283': {enabled: true},
	}),
	loginTest()
);

test(
	'Can create a style book within a design library',
	{tag: '@LPD-88092'},
	async ({apiHelpers, designLibrariesPage, page}) => {
		const designLibraryName = getRandomString();

		const createdDesignLibrary =
			await test.step('Create a design library via headless', async () => {
				return await apiHelpers.headlessAssetLibrary.createAssetLibrary(
					{
						name: designLibraryName,
						settings: {},
						type: 'DesignLibrary',
					}
				);
			});

		try {
			const newStyleBookButton = page.getByRole('button', {
				name: 'New Style Book',
			});

			await test.step('Open the design library resources view', async () => {
				await designLibrariesPage.goToDesignLibrary(designLibraryName);

				await expect(newStyleBookButton).toBeVisible();
			});

			const modal = page.getByRole('dialog');

			await test.step('Cancelling the modal does not create an entry', async () => {
				await newStyleBookButton.click();

				await expect(modal).toBeVisible();
				await expect(
					modal.getByRole('heading', {name: 'Add Style Book'})
				).toBeVisible();

				await modal.getByRole('button', {name: 'Cancel'}).click();

				await expect(modal).toBeHidden();
			});

			await test.step('Submitting the modal redirects to the style book editor', async () => {
				await newStyleBookButton.click();

				await expect(modal).toBeVisible();

				await modal.getByLabel('Name').fill(getRandomString());

				await modal.getByRole('button', {name: 'Save'}).click();

				await expect(modal).toBeHidden();
				await expect(
					page.getByRole('heading', {
						exact: true,
						name: 'Style Books',
					})
				).toBeVisible();
			});

			await test.step('Clicking the back button returns to the design library', async () => {
				await page
					.locator('.control-menu-nav-item')
					.getByTitle(`Go to ${designLibraryName}`)
					.click();

				await expect(
					page.getByRole('heading', {name: 'Design Libraries'})
				).toBeVisible();
			});
		}
		finally {
			await test.step('Remove the design library', async () => {
				await apiHelpers.headlessAssetLibrary.deleteAssetLibrary(
					createdDesignLibrary.externalReferenceCode
				);
			});
		}
	}
);

test(
	'New Style Book button is not visible without permissions',
	{tag: '@LPD-88092'},
	async ({apiHelpers, designLibrariesPage, page}) => {
		const designLibraryName = getRandomString();

		const createdDesignLibrary =
			await test.step('Create a design library via headless', async () => {
				return await apiHelpers.headlessAssetLibrary.createAssetLibrary(
					{
						name: designLibraryName,
						settings: {},
						type: 'DesignLibrary',
					}
				);
			});

		const unprivilegedUser =
			await test.step('Create an unprivileged user', async () => {
				const user =
					await apiHelpers.headlessAdminUser.postUserAccount();

				userData[user.alternateName] = {
					name: user.givenName,
					password: 'test',
					surname: user.familyName,
				};

				return user;
			});

		try {
			const newStyleBookButton = page.getByRole('button', {
				name: 'New Style Book',
			});

			const designLibraryURL =
				await test.step('Open the design library resources view as admin', async () => {
					await designLibrariesPage.goToDesignLibrary(
						designLibraryName
					);

					await expect(newStyleBookButton).toBeVisible();

					return page.url();
				});

			await test.step('Switch to the unprivileged user', async () => {
				await performLogout(page);

				await performLoginViaApi({
					page,
					screenName: unprivilegedUser.alternateName,
				});
			});

			await test.step('New Style Book button is not visible without permissions', async () => {
				await page.goto(designLibraryURL);

				await expect(newStyleBookButton).toBeHidden();
			});
		}
		finally {
			await test.step('Switch back to the admin user and clean up', async () => {
				await performLogout(page);

				await performLoginViaApi({page, screenName: 'test'});

				await apiHelpers.headlessAssetLibrary.deleteAssetLibrary(
					createdDesignLibrary.externalReferenceCode
				);

				await apiHelpers.headlessAdminUser.deleteUserAccount(
					Number(unprivilegedUser.id)
				);
			});
		}
	}
);
