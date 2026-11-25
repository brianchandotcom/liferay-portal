/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {checkAccessibility} from '../../../utils/checkAccessibility';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import {hoverAndExpectToBeVisible} from '../../../utils/hoverAndExpectToBeVisible';
import {designLibrariesPageTest} from './fixtures/designLibrariesPageTest';

const test = mergeTests(
	dataApiHelpersTest,
	designLibrariesPageTest,
	featureFlagsTest({
		'LPD-57283': {enabled: true},
		'LPD-81022': {enabled: true},
		'LPD-81651': {enabled: true},
	}),
	loginTest()
);

test(
	'Shows the revamped per-library home with members and connected sites',
	{tag: ['@LPD-108834', '@LPD-110068', '@LPD-99371']},
	async ({apiHelpers, designLibrariesPage, page}) => {
		const creationMenuItemLabels = [
			'New Style Book',
			'New Master Page',
			'Page Templates',
			'New Display Page Template',
			'Fragments',
		];
		const fragmentsMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Fragments',
		});

		const designLibrary =
			await apiHelpers.headlessAssetLibrary.createDesignLibrary();

		await test.step('Go to the design library', async () => {
			await designLibrariesPage.goToDesignLibrary(designLibrary.name);
		});

		await test.step('Check the Design Assets section', async () => {
			await expect(
				page.getByRole('heading', {name: 'Design Assets'})
			).toBeVisible();

			await expect(
				page.getByRole('button', {name: 'Add Asset'})
			).toBeVisible();
		});

		await test.step('Check the Add Asset menu', async () => {
			await clickAndExpectToBeVisible({
				target: fragmentsMenuItem,
				trigger: page.getByRole('button', {name: 'Add Asset'}),
			});

			await expect(
				page.getByRole('menu').getByRole('menuitem')
			).toHaveText(creationMenuItemLabels);
			await expect(
				page.getByRole('menu').getByRole('separator')
			).toHaveCount(2);

			const newContentPageTemplateMenuItem = page.getByRole('menuitem', {
				name: 'New Content Page Template',
			});

			await hoverAndExpectToBeVisible({
				target: newContentPageTemplateMenuItem,
				trigger: page.getByRole('menuitem', {
					exact: true,
					name: 'Page Templates',
				}),
			});
			await hoverAndExpectToBeVisible({
				target: page.getByRole('menuitem', {
					name: 'New Basic Fragment',
				}),
				trigger: fragmentsMenuItem,
			});

			await expect(newContentPageTemplateMenuItem).toBeHidden();

			await expect(async () => {
				await page.keyboard.press('Escape');

				await expect(fragmentsMenuItem).toBeHidden({timeout: 500});
			}).toPass();
		});

		await test.step('Check the New menu', async () => {
			await clickAndExpectToBeVisible({
				target: fragmentsMenuItem,
				trigger: page.getByRole('button', {exact: true, name: 'New'}),
			});

			await expect(
				page.getByRole('menu').getByRole('menuitem')
			).toHaveText(creationMenuItemLabels);
			await expect(
				page.getByRole('menu').getByRole('separator')
			).toHaveCount(2);

			await hoverAndExpectToBeVisible({
				target: page.getByRole('menuitem', {
					name: 'New Basic Fragment',
				}),
				trigger: fragmentsMenuItem,
			});

			await expect(async () => {
				await page.keyboard.press('Escape');

				await expect(fragmentsMenuItem).toBeHidden({timeout: 500});
			}).toPass();
		});

		await test.step('Check the Members section', async () => {
			await expect(
				page.getByRole('heading', {name: /^Members/})
			).toBeVisible();

			await expect(page.getByRole('tab', {name: 'Users'})).toBeVisible();

			await expect(
				page.getByRole('tab', {name: 'User Groups'})
			).toBeVisible();

			await expect(page.getByText('(Owner)')).toBeVisible();
		});

		await test.step('Check the Manage Members button', async () => {
			await expect(
				page.getByRole('button', {name: 'Manage Members'})
			).toBeVisible();
		});

		await test.step('Check the Connected Sites section', async () => {
			await expect(
				page.getByRole('heading', {name: /^Connected Sites/})
			).toBeVisible();

			await expect(
				page.getByText('No Sites Are Connected Yet')
			).toBeVisible();

			await expect(
				page.getByRole('button', {name: 'Manage Sites'})
			).toBeHidden();
		});

		await test.step('Check the accessibility', async () => {
			await checkAccessibility({
				page,
				selectors: ['.portlet-body'],
			});
		});
	}
);
