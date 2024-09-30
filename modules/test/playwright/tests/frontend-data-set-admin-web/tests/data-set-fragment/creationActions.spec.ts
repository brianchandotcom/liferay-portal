/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {isolatedLayoutTest} from '../../../../fixtures/isolatedLayoutTest';
import {loginTest} from '../../../../fixtures/loginTest';
import getRandomString from '../../../../utils/getRandomString';
import {dataSetManagerApiHelpersTest} from '../../fixtures/dataSetManagerApiHelpersTest';
import {ECreationActionType} from '../../utils/types';
import {fdsFragmentPageTest} from './fixtures/fdsFragmentPageTest';

let dataSetERC: string;
let dataSetLabel: string;

export const test = mergeTests(
	apiHelpersTest,
	dataSetManagerApiHelpersTest,
	featureFlagsTest({
		'LPS-164563': true,
		'LPS-178052': true,
	}),
	isolatedLayoutTest({publish: false}),
	loginTest(),
	fdsFragmentPageTest
);

test.beforeEach(async ({dataSetManagerApiHelpers}) => {
	dataSetERC = getRandomString();
	dataSetLabel = getRandomString();

	await test.step('Create data set', async () => {
		await dataSetManagerApiHelpers.createDataSet({
			erc: dataSetERC,
			label: dataSetLabel,
		});
	});

	await test.step('Create table field', async () => {
		await dataSetManagerApiHelpers.createDataSetTableSection({
			dataSetERC,
			fieldName: 'id',
			label_i18n: {en_US: 'Id'},
			type: 'string',
		});
	});
});

test.afterEach(async ({dataSetManagerApiHelpers}) => {
	await dataSetManagerApiHelpers.deleteDataSet({erc: dataSetERC});
});

test.describe('Creation Actions in Data Set fragment', () => {
	test('Creation Action button does not appear if no creation action is defined', async ({
		fdsFragmentPage,
		layout,
	}) => {
		await test.step('Configure Data Set in the page', async () => {
			await fdsFragmentPage.configureDataSetFragment({
				dataSetLabel,
				layout,
			});
		});

		await test.step('Check that the Creation Action button is not present', async () => {
			await expect(fdsFragmentPage.creationMenuButton).not.toBeVisible();
		});
	});

	test('Show a simple button if only one Creation Action is defined', async ({
		dataSetManagerApiHelpers,
		fdsFragmentPage,
		layout,
		page,
	}) => {
		const actionLabel = 'Custom Creation Action';

		await test.step('Create Creation Action', async () => {
			await dataSetManagerApiHelpers.createDataSetCreationAction({
				dataSetERC,
				label_i18n: {en_US: actionLabel},
			});
		});

		await test.step('Configure Data Set in the page', async () => {
			await fdsFragmentPage.configureDataSetFragment({
				dataSetLabel,
				layout,
			});
		});

		await test.step('Check that the Creation Action button is present', async () => {
			await expect(
				fdsFragmentPage.page
					.getByRole('button', {
						name: actionLabel,
					})
					.first()
			).toBeVisible();
		});

		await test.step('Check that the Creation Action works', async () => {
			await fdsFragmentPage.page
				.getByRole('button', {
					name: actionLabel,
				})
				.first()
				.click();

			await expect(page.getByText('Welcome to Liferay')).toBeVisible();
		});
	});

	test('Show the Creation Actions menu if more than one Creation Action is defined', async ({
		dataSetManagerApiHelpers,
		fdsFragmentPage,
		layout,
	}) => {
		const firstActionLabel = 'Custom Creation Action';
		const secondActionLabel = 'Another Creation Action';

		await test.step('Create Creation Actions', async () => {
			await dataSetManagerApiHelpers.createDataSetCreationAction({
				dataSetERC,
				label_i18n: {en_US: firstActionLabel},
				title_i18n: {en_US: 'Modal title'},
				type: ECreationActionType.MODAL,
			});

			await dataSetManagerApiHelpers.createDataSetCreationAction({
				dataSetERC,
				label_i18n: {en_US: secondActionLabel},
			});
		});

		await test.step('Configure Data Set in the page', async () => {
			await fdsFragmentPage.configureDataSetFragment({
				dataSetLabel,
				layout,
			});
		});

		const actionDropdownMenuId =
			await test.step('Check that the Creation Action menu is present', async () => {
				await fdsFragmentPage.creationMenuButton.first().isVisible();

				const button = fdsFragmentPage.creationMenuButton.first();

				const dropdownId = await button.evaluate((node) =>
					node.getAttribute('aria-controls')
				);

				await button.click();

				await fdsFragmentPage.page
					.locator(`#${dropdownId}`)
					.filter({has: fdsFragmentPage.page.getByRole('menu')})
					.waitFor();

				await expect(
					fdsFragmentPage.page
						.locator(`#${dropdownId}`)
						.getByRole('menuitem')
				).toHaveCount(2);

				await expect(
					fdsFragmentPage.page
						.locator(`#${dropdownId}`)
						.getByRole('menuitem', {
							exact: true,
							name: firstActionLabel,
						})
				).toBeVisible();

				await expect(
					fdsFragmentPage.page
						.locator(`#${dropdownId}`)
						.getByRole('menuitem', {
							exact: true,
							name: secondActionLabel,
						})
				).toBeVisible();

				await fdsFragmentPage.page.keyboard.press('Escape');

				return dropdownId;
			});

		await test.step('Creation Action of type "modal" opens a modal', async () => {
			await fdsFragmentPage.creationMenuButton.first().click();

			await fdsFragmentPage.page
				.locator(`#${actionDropdownMenuId}`)
				.getByRole('menuitem', {
					exact: true,
					name: firstActionLabel,
				})
				.click();

			await fdsFragmentPage.page.getByRole('dialog').waitFor();

			const dialog = await fdsFragmentPage.page.getByRole('dialog');

			await expect(dialog).toBeInViewport();

			await dialog.getByRole('button', {name: 'close'}).click();

			await expect(dialog).not.toBeInViewport();
		});

		await test.step('Creation Action of type "link" is actionable', async () => {
			await fdsFragmentPage.creationMenuButton.first().click();

			await fdsFragmentPage.page
				.locator(`#${actionDropdownMenuId}`)
				.getByRole('menuitem', {
					exact: true,
					name: secondActionLabel,
				})
				.click();

			await expect(
				fdsFragmentPage.page.getByText('Welcome to Liferay')
			).toBeVisible();
		});
	});
});
