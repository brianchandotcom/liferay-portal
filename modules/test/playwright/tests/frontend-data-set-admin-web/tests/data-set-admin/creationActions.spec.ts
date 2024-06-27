/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../../fixtures/loginTest';
import {liferayConfig} from '../../../../liferay.config';
import getRandomString from '../../../../utils/getRandomString';
import {dataSetManagerApiHelpersTest} from '../../fixtures/dataSetManagerApiHelpersTest';
import {actionsPageTest} from './fixtures/actionsPageTest';
import {dataSetManagerSetupTest} from './fixtures/dataSetManagerSetupTest';

const LINK_CREATION_ACTION_NAME = 'Link creation action';
const MODAL_CREATION_ACTION_NAME = 'Modal creation action';
const MODAL_CREATION_ACTION_TITLE = 'Modal creation title';
const SIDE_PANEL_CREATION_ACTION_NAME = 'Side Panel creation action';
const SIDE_PANEL_CREATION_ACTION_TITLE = 'Side Panel creation title';

export const test = mergeTests(
	actionsPageTest,
	dataSetManagerApiHelpersTest,
	featureFlagsTest({
		'LPS-164563': true,
		'LPS-178052': true,
	}),
	loginTest(),
	dataSetManagerSetupTest
);

let dataSetERC: string;

test.beforeEach(async ({actionsPage, dataSetManagerApiHelpers}) => {
	dataSetERC = getRandomString();
	const dataSetLabel = getRandomString();

	await test.step('Create a data set', async () => {
		await dataSetManagerApiHelpers.createDataSet({
			erc: dataSetERC,
			label: dataSetLabel,
		});
	});

	await test.step('Navigate to the Actions tab', async () => {
		await actionsPage.goto({
			dataSetLabel,
		});

		await expect(actionsPage.creationActionsTab).toBeInViewport();
	});

	await test.step('Navigate to the Creation Actions tab', async () => {
		await actionsPage.creationActionsTab.click();
		await actionsPage.newCreationActionButton.waitFor();
	});
});

test.afterEach(async ({dataSetManagerApiHelpers}) => {
	await dataSetManagerApiHelpers.deleteDataSet({erc: dataSetERC});
});

test.describe('Creation Actions in Data Set Manager', () => {
	test('There is a message if no Creation Action has been created', async ({
		actionsPage,
	}) => {
		await test.step('Assert no Creation Actions are created', async () => {
			await expect(actionsPage.noActionsWereCreatedMessage).toContainText(
				'No actions were created.'
			);
		});
	});

	test('Can create a Creation Action of type Link', async ({
		actionsPage,
		page,
	}) => {
		await test.step('Create a creation action', async () => {
			await actionsPage.createCreationAction({
				icon: 'arrow-right-full',
				name: LINK_CREATION_ACTION_NAME,
				type: 'link',
				url: liferayConfig.environment.baseUrl,
			});
		});

		await test.step('Check that the creation action is in the list', async () => {
			await expect(actionsPage.creationActionsTab).toBeInViewport();

			await expect(
				page.getByRole('cell', {
					exact: true,
					name: LINK_CREATION_ACTION_NAME,
				})
			).toBeVisible();
		});
	});

	test('Can create a Creation Action of type Modal', async ({
		actionsPage,
		page,
	}) => {
		await test.step('Create a creation action', async () => {
			await actionsPage.createCreationAction({
				icon: 'arrow-right-full',
				name: MODAL_CREATION_ACTION_NAME,
				title: MODAL_CREATION_ACTION_TITLE,
				type: 'modal',
				url: liferayConfig.environment.baseUrl,
				variant: 'sm',
			});
		});

		await test.step('Check that the creation action is in the list', async () => {
			await expect(actionsPage.creationActionsTab).toBeInViewport();

			await expect(
				page.getByRole('cell', {
					exact: true,
					name: MODAL_CREATION_ACTION_NAME,
				})
			).toBeVisible();
		});
	});

	test('Can create a Creation Action of type Side Panel', async ({
		actionsPage,
		page,
	}) => {
		await test.step('Create a creation action', async () => {
			await actionsPage.createCreationAction({
				icon: 'arrow-right-full',
				name: SIDE_PANEL_CREATION_ACTION_NAME,
				title: SIDE_PANEL_CREATION_ACTION_TITLE,
				type: 'sidePanel',
				url: liferayConfig.environment.baseUrl,
			});
		});

		await test.step('Check that the creation action is in the list', async () => {
			await expect(actionsPage.creationActionsTab).toBeInViewport();

			await expect(
				page.getByRole('cell', {
					exact: true,
					name: SIDE_PANEL_CREATION_ACTION_NAME,
				})
			).toBeVisible();
		});
	});
});
