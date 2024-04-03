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

const LINK_ITEM_ACTION_NAME = 'Link creation action';

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
						fdsFragmentPage.itemActionButton
					).not.toBeVisible();

					await expect(
						fdsFragmentPage.itemActionMenuButton
					).not.toBeVisible();
				}
			);
		}
	);
});
