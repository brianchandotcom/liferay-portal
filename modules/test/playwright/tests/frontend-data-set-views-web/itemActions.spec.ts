/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {liferayConfig} from '../../liferay.config';
import getRandomString from '../../utils/getRandomString';
import {actionsPageTest} from './fixtures/actionsPageTest';
import {dataSetManagerApiHelpersTest} from './fixtures/dataSetManagerApiHelpersTest';
import {fdsFragmentPageTest} from './fixtures/fdsFragmentPageTest';

const LINK_ITEM_ACTION_NAME = 'Link item action';
const LINK_ITEM_ACTION_CONFIRMATION_MESSAGE =
	'Do you want to navigate to http://www.liferay.com?';
const MODAL_ITEM_ACTION_NAME = 'Modal item action';
const MODAL_ITEM_ACTION_TITLE = 'Modal title';
const SIDE_PANEL_ITEM_ACTION_NAME = 'SidePanel item action';
const SIDE_PANEL_ITEM_ACTION_URL = liferayConfig.environment.baseUrl;

export const test = mergeTests(
	actionsPageTest,
	dataSetManagerApiHelpersTest,
	featureFlagsTest({
		'LPD-10735': false,
		'LPS-164563': true,
		'LPS-178052': true,
		'LPS-186871': true,
	}),
	loginTest()
);

let actionsDataSetERC: string;
let actionsDataSetLabel: string;
let actionsDataSetViewERC: string;
let actionsDataSetViewLabel: string;

test.beforeEach(async ({dataSetManagerApiHelpers}) => {
	actionsDataSetERC = getRandomString();
	actionsDataSetLabel = getRandomString();
	actionsDataSetViewERC = getRandomString();
	actionsDataSetViewLabel = getRandomString();

	await dataSetManagerApiHelpers.createDataSet({
		erc: actionsDataSetERC,
		label: actionsDataSetLabel,
	});
	await dataSetManagerApiHelpers.createDataSetView({
		erc: actionsDataSetViewERC,
		label: actionsDataSetViewLabel,
		r_fdsEntryFDSViewRelationship_c_fdsEntryERC: actionsDataSetERC,
	});
});

test.afterEach(async ({dataSetManagerApiHelpers}) => {
	await dataSetManagerApiHelpers.deleteDataSet({erc: actionsDataSetERC});
});

test.describe('Item Actions in the Data Set Manager', () => {
	test('There is a message if there are no Item Actions', async ({
		actionsPage,
	}) => {
		await test.step('Navigate to the Actions tab', async () => {
			await actionsPage.goto({
				dataSetLabel: actionsDataSetLabel,
				viewLabel: actionsDataSetViewLabel,
			});

			await expect(actionsPage.itemActionsTab).toBeInViewport();
		});

		await test.step('Navigate to the Item Actions tab', async () => {
			await actionsPage.itemActionsTab.click();
			await actionsPage.newItemActionButton.waitFor();
		});

		await test.step('Assert no Item Actions are created', async () => {
			await expect(actionsPage.noActionsWereCreatedMessage).toContainText(
				'No actions were created.'
			);
		});
	});

	test('Can create an Item Action of type Link', async ({
		actionsPage,
		page,
	}) => {
		await test.step('Navigate to the Actions tab', async () => {
			await actionsPage.goto({
				dataSetLabel: actionsDataSetLabel,
				viewLabel: actionsDataSetViewLabel,
			});

			await expect(actionsPage.itemActionsTab).toBeInViewport();
		});

		await test.step('Navigate to the Item Actions tab', async () => {
			await actionsPage.itemActionsTab.click();
			await actionsPage.newCreationActionButton.waitFor();
		});

		await test.step('Create an item action', async () => {
			await actionsPage.createItemAction({
				icon: 'arrow-right-full',
				name: LINK_ITEM_ACTION_NAME,
				type: 'link',
				url: liferayConfig.environment.baseUrl,
			});
		});

		await test.step('Check that the item action is in the list', async () => {
			await expect(actionsPage.itemActionsTab).toBeInViewport();

			await expect(
				page.getByRole('cell', {
					exact: true,
					name: LINK_ITEM_ACTION_NAME,
				})
			).toBeVisible();
		});
	});
});

export const fragmentTest = mergeTests(
	apiHelpersTest,
	dataSetManagerApiHelpersTest,
	featureFlagsTest({
		'LPS-164563': true,
		'LPS-178052': true,
	}),
	fdsFragmentPageTest,
	isolatedSiteTest
);

fragmentTest.describe('Item Actions in the fragment', () => {
	fragmentTest(
		'Item Action button does not appear if there is no item action',
		async ({apiHelpers, fdsFragmentPage, site}) => {
			const layout = await fragmentTest.step(
				'Create a new page',
				async () => {
					const pageLayout =
						await apiHelpers.headlessDelivery.createSitePage({
							siteId: site.id,
							title: getRandomString(),
						});

					return pageLayout;
				}
			);

			await fragmentTest.step(
				'Configure Data Set in the page',
				async () => {
					await fdsFragmentPage.configureDataSetFragment({
						layout,
						site,
						viewLabel: actionsDataSetViewLabel,
					});
				}
			);

			await fragmentTest.step(
				'Check that the Item Action button is not present',
				async () => {
					await expect(
						fdsFragmentPage.page
							.getByLabel(LINK_ITEM_ACTION_NAME)
							.first()
					).not.toBeVisible();
				}
			);
		}
	);

	fragmentTest(
		'Link Item Action (single action) is shown in the fragment',
		async ({
			apiHelpers,
			dataSetManagerApiHelpers,
			fdsFragmentPage,
			page,
			site,
		}) => {
			fragmentTest.step('Create Item Action', async () => {
				dataSetManagerApiHelpers.createDataSetViewItemAction({
					confirmationMessage_i18n: {
						en_US: LINK_ITEM_ACTION_CONFIRMATION_MESSAGE,
					},
					label_i18n: {en_US: LINK_ITEM_ACTION_NAME},
					r_fdsViewFDSItemActionRelationship_c_fdsViewERC:
						actionsDataSetViewERC,
					type: 'link',
				});
			});

			const layout = await fragmentTest.step(
				'Create a new page',
				async () => {
					const pageLayout =
						await apiHelpers.headlessDelivery.createSitePage({
							siteId: site.id,
							title: getRandomString(),
						});

					return pageLayout;
				}
			);

			await fragmentTest.step(
				'Configure Data Set in the page',
				async () => {
					await fdsFragmentPage.configureDataSetFragment({
						layout,
						site,
						viewLabel: actionsDataSetViewLabel,
					});
				}
			);

			await fragmentTest.step(
				'Check that the Item Action button is present',
				async () => {
					await expect(
						fdsFragmentPage.page
							.getByLabel(LINK_ITEM_ACTION_NAME)
							.first()
					).toBeVisible();
				}
			);

			await fragmentTest.step(
				'Check that the Item Action works',
				async () => {
					const dialogPromise = page
						.waitForEvent('dialog')
						.then(async (dialog) => {
							await dialog.accept();

							return dialog.message();
						});

					await fdsFragmentPage.page
						.getByLabel(LINK_ITEM_ACTION_NAME)
						.first()
						.click();

					const confirmationMessage = await dialogPromise;

					expect(confirmationMessage).toBe(
						LINK_ITEM_ACTION_CONFIRMATION_MESSAGE
					);

					await expect(
						page.getByText('Welcome to Liferay')
					).toBeVisible();
				}
			);
		}
	);

	fragmentTest(
		'Link, Modal and Side Panel Item Actions (multiple actions) are shown in fragment',
		async ({
			apiHelpers,
			dataSetManagerApiHelpers,
			fdsFragmentPage,
			page,
			site,
		}) => {
			fragmentTest.step('Create Item Actions', async () => {
				dataSetManagerApiHelpers.createDataSetViewItemAction({
					label_i18n: {en_US: LINK_ITEM_ACTION_NAME},
					r_fdsViewFDSItemActionRelationship_c_fdsViewERC:
						actionsDataSetViewERC,
					type: 'link',
				});

				dataSetManagerApiHelpers.createDataSetViewItemAction({
					label_i18n: {en_US: MODAL_ITEM_ACTION_NAME},
					modalSize: 'sm',
					r_fdsViewFDSItemActionRelationship_c_fdsViewERC:
						actionsDataSetViewERC,
					title_i18n: {en_US: MODAL_ITEM_ACTION_TITLE},
					type: 'modal',
					url: liferayConfig.environment.baseUrl,
				});

				dataSetManagerApiHelpers.createDataSetViewItemAction({
					label_i18n: {en_US: SIDE_PANEL_ITEM_ACTION_NAME},
					modalSize: 'sm',
					r_fdsViewFDSItemActionRelationship_c_fdsViewERC:
						actionsDataSetViewERC,
					title_i18n: {en_US: SIDE_PANEL_ITEM_ACTION_NAME},
					type: 'sidePanel',
					url: liferayConfig.environment.baseUrl,
				});
			});

			const layout = await fragmentTest.step(
				'Create a new page',
				async () => {
					const pageLayout =
						await apiHelpers.headlessDelivery.createSitePage({
							siteId: site.id,
							title: getRandomString(),
						});

					return pageLayout;
				}
			);

			await fragmentTest.step(
				'Configure Data Set in the page',
				async () => {
					await fdsFragmentPage.configureDataSetFragment({
						layout,
						site,
						viewLabel: actionsDataSetViewLabel,
					});
				}
			);

			const datasetRow = await fragmentTest.step(
				'Checkt that the Item Actions dropdown is present in table row',
				async () => {
					const tableRow = await page
						.locator('.dnd-td.item-actions')
						.first();

					await expect(
						tableRow.getByRole('button', {
							exact: true,
							name: 'Actions',
						})
					).toBeVisible;

					const button = await tableRow.getByRole('button', {
						exact: true,
						name: 'Actions',
					});
					const dropdownId = await button.evaluate((node) =>
						node.getAttribute('aria-controls')
					);

					await button.click();

					await page
						.locator(`#${dropdownId}`)
						.filter({has: page.getByRole('menu')})
						.waitFor();

					await expect(
						page.locator(`#${dropdownId}`).getByRole('menuitem')
					).toHaveCount(3);

					await page.keyboard.press('Escape');

					return tableRow;
				}
			);

			await fragmentTest.step(
				'Click the modal item action opens a modal window',
				async () => {
					const button = await datasetRow.getByRole('button', {
						exact: true,
						name: 'Actions',
					});

					const dropdownId = await button.evaluate((node) =>
						node.getAttribute('aria-controls')
					);

					await button.click();

					await page
						.locator(`#${dropdownId}`)
						.filter({has: page.getByRole('menu')})
						.waitFor();

					await page
						.locator(`#${dropdownId}`)
						.getByRole('menuitem', {
							exact: true,
							name: MODAL_ITEM_ACTION_NAME,
						})
						.click();

					await page.getByRole('dialog').waitFor();

					const dialog = await page.getByRole('dialog');
					await expect(dialog.getByRole('heading')).toHaveText(
						MODAL_ITEM_ACTION_TITLE
					);

					await dialog.getByRole('button', {name: 'close'}).click();

					await expect(dialog).not.toBeInViewport();
				}
			);

			await fragmentTest.step(
				'Click the side panel item action opens a side panel',
				async () => {
					const button = await datasetRow.getByRole('button', {
						exact: true,
						name: 'Actions',
					});

					const dropdownId = await button.evaluate((node) =>
						node.getAttribute('aria-controls')
					);

					await button.click();

					await page
						.locator(`#${dropdownId}`)
						.filter({has: page.getByRole('menu')})
						.waitFor();

					await page
						.locator(`#${dropdownId}`)
						.getByRole('menuitem', {
							exact: true,
							name: SIDE_PANEL_ITEM_ACTION_NAME,
						})
						.click();

					await page.getByRole('tabpanel').waitFor();

					const sidePanel = await page.getByRole('tabpanel');

					const iframeElement = await sidePanel
						.locator('iframe')
						.elementHandle();

					const frame = await iframeElement.contentFrame();

					await frame.waitForURL(
						new RegExp(`.*${SIDE_PANEL_ITEM_ACTION_URL}`, 'i')
					);

					await page.keyboard.press('Escape');

					await expect(sidePanel).not.toBeInViewport();
				}
			);
		}
	);
});
