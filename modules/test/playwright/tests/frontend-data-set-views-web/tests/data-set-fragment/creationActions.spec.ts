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
import {fdsFragmentPageTest} from '../../tests/data-set-fragment/fixtures/fdsFragmentPageTest';

let dataSetERC: string;
let dataSetLabel: string;

export const fragmentTest = mergeTests(
	apiHelpersTest,
	dataSetManagerApiHelpersTest,
	featureFlagsTest({
		'LPS-164563': true,
		'LPS-178052': true,
	}),
	fdsFragmentPageTest,
	isolatedLayoutTest({publish: false}),
	loginTest()
);

fragmentTest.beforeEach(async ({dataSetManagerApiHelpers}) => {
	dataSetERC = getRandomString();
	dataSetLabel = getRandomString();

	await dataSetManagerApiHelpers.createDataSet({
		erc: dataSetERC,
		label: dataSetLabel,
	});
});

fragmentTest.afterEach(async ({dataSetManagerApiHelpers}) => {
	await dataSetManagerApiHelpers.deleteDataSet({erc: dataSetERC});
});

fragmentTest.describe('Creation Actions in Data Set fragment', () => {
	fragmentTest(
		'Creation Action button does not appear if no creation action is defined',
		async ({dataSetManagerApiHelpers, fdsFragmentPage, layout}) => {
			await fragmentTest.step('Create table field', async () => {
				await dataSetManagerApiHelpers.createDataSetField({
					label_i18n: {en_US: 'Id'},
					name: 'id',
					r_fdsViewFDSFieldRelationship_c_fdsViewERC: dataSetERC,
					type: 'string',
				});
			});

			await fragmentTest.step(
				'Configure Data Set in the page',
				async () => {
					await fdsFragmentPage.configureDataSetFragment({
						dataSetLabel,
						layout,
					});
				}
			);

			await fragmentTest.step(
				'Check that the Creation Action button is not present',
				async () => {
					await expect(
						fdsFragmentPage.creationMenuButton
					).not.toBeVisible();
				}
			);
		}
	);

	fragmentTest(
		'Show a simple button if only one Creation Action is defined',
		async ({dataSetManagerApiHelpers, fdsFragmentPage, layout, page}) => {
			await fragmentTest.step('Create table field', async () => {
				await dataSetManagerApiHelpers.createDataSetField({
					label_i18n: {en_US: 'Id'},
					name: 'id',
					r_fdsViewFDSFieldRelationship_c_fdsViewERC: dataSetERC,
					type: 'string',
				});
			});

			const actionLabel = 'Custom Creation Action';

			await fragmentTest.step('Create Creation Action', async () => {
				await dataSetManagerApiHelpers.createDataSetCreationAction({
					label_i18n: {en_US: actionLabel},
					r_fdsViewFDSCreationActionRelationship_c_fdsViewERC:
						dataSetERC,
				});
			});

			await fragmentTest.step(
				'Configure Data Set in the page',
				async () => {
					await fdsFragmentPage.configureDataSetFragment({
						dataSetLabel,
						layout,
					});
				}
			);

			await fragmentTest.step(
				'Check that the Creation Action button is present',
				async () => {
					await expect(
						fdsFragmentPage.page
							.getByRole('button', {
								name: actionLabel,
							})
							.first()
					).toBeVisible();
				}
			);

			await fragmentTest.step(
				'Check that the Creation Action works',
				async () => {
					await fdsFragmentPage.page
						.getByRole('button', {
							name: actionLabel,
						})
						.first()
						.click();

					await expect(
						page.getByText('Welcome to Liferay')
					).toBeVisible();
				}
			);
		}
	);

	fragmentTest(
		'Show the Creation Actions menu if more than one Creation Action is defined',
		async ({dataSetManagerApiHelpers, fdsFragmentPage, layout}) => {
			await fragmentTest.step('Create table field', async () => {
				await dataSetManagerApiHelpers.createDataSetField({
					label_i18n: {en_US: 'Id'},
					name: 'id',
					r_fdsViewFDSFieldRelationship_c_fdsViewERC: dataSetERC,
					type: 'string',
				});
			});

			const firstActionLabel = 'Custom Creation Action';
			const secondActionLabel = 'Another Creation Action';

			await fragmentTest.step('Create Creation Actions', async () => {
				await dataSetManagerApiHelpers.createDataSetCreationAction({
					label_i18n: {en_US: firstActionLabel},
					r_fdsViewFDSCreationActionRelationship_c_fdsViewERC:
						dataSetERC,
					title_i18n: {en_US: 'Modal title'},
					type: 'modal',
				});

				await dataSetManagerApiHelpers.createDataSetCreationAction({
					label_i18n: {en_US: secondActionLabel},
					r_fdsViewFDSCreationActionRelationship_c_fdsViewERC:
						dataSetERC,
				});
			});

			await fragmentTest.step(
				'Configure Data Set in the page',
				async () => {
					await fdsFragmentPage.configureDataSetFragment({
						dataSetLabel,
						layout,
					});
				}
			);

			const actionDropdownMenuId = await fragmentTest.step(
				'Check that the Creation Action menu is present',
				async () => {
					await fdsFragmentPage.creationMenuButton
						.first()
						.isVisible();

					const button =
						await fdsFragmentPage.creationMenuButton.first();

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
				}
			);

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
		}
	);
});
