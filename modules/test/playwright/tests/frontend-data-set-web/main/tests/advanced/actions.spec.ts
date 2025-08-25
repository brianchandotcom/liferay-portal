/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../../../fixtures/loginTest';
import {EFDSVisualizationMode, waitForFDS} from '../../../../../utils/waitFor';
import {waitForAlert} from '../../../../../utils/waitForAlert';
import {fdsSamplePageTest} from '../../fixtures/fdsSamplePageTest';

const test = mergeTests(
	apiHelpersTest,
	fdsSamplePageTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest()
);

test.beforeEach(async ({fdsSamplePage, page, site}) => {
	await fdsSamplePage.setupFDSSampleWidget({site});

	await fdsSamplePage.selectTab('Advanced');

	await waitForFDS({page, visualizationMode: EFDSVisualizationMode.TABLE});
});

test('Behavior of item actions', async ({fdsSamplePage, page}) => {
	const asyncConnectionRefused = 'Async Connection Refused';
	const asyncResourceNotFound = 'Async Resource Not Found';
	const asyncSuccess = 'Async Success';
	const sampleView = 'Sample View';
	const sidePanelActionLabelWithActionTitle = 'Side Panel With Action Title';
	const sidePanelActionLabelWithContentTitle =
		'Side Panel With Content Title';
	const sidePanelActionLabelWithActionTitleContentTitle =
		'Side Panel With Action and Content Title';
	const sidePanelActionLabelWithoutTitle = 'Side Panel With No Title';
	const sidePanelActionTitle = 'Side Panel Title Provided by Action';
	const sidePanelContentTitle = 'Side Panel Title Provided by Page';

	await test.step('Check that the Item Actions dropdown is present in table row', async () => {
		const tableItemActionButton =
			fdsSamplePage.table.itemActionButtons.first();

		await expect(tableItemActionButton).toBeVisible();

		const dropdownId =
			await tableItemActionButton.getAttribute('aria-controls');

		await tableItemActionButton.click();

		await page
			.locator(`#${dropdownId}`)
			.filter({has: page.getByRole('menu')})
			.waitFor();

		await expect(
			page.locator(`#${dropdownId}`).getByRole('menuitem')
		).toHaveCount(14);

		await page.keyboard.press('Escape');
	});

	await test.step('Check that the Item Actions dropdown displays icons for Table, List, and Cards views', async () => {
		await test.step('Check Table view actions dropdown items has icons', async () => {
			const tableItemActionButton =
				fdsSamplePage.table.itemActionButtons.first();

			await expect(tableItemActionButton).toBeVisible();

			await fdsSamplePage.checkDropdownMenuIconsAreVisible(
				tableItemActionButton
			);
		});

		await test.step('Check List view actions dropdown items has icons', async () => {
			await fdsSamplePage.changeVisualizationMode({
				page,
				visualizationMode: EFDSVisualizationMode.LIST,
			});

			const listItemActionButton =
				fdsSamplePage.list.itemActionButtons.first();

			await expect(listItemActionButton).toBeVisible();

			await fdsSamplePage.checkDropdownMenuIconsAreVisible(
				listItemActionButton
			);
		});

		await test.step('Check Cards view action dropdown items has icons', async () => {
			await fdsSamplePage.changeVisualizationMode({
				page,
				visualizationMode: EFDSVisualizationMode.CARDS,
			});

			const cardItemActionButton =
				fdsSamplePage.cards.itemActionButtons.first();

			await expect(cardItemActionButton).toBeVisible();

			await fdsSamplePage.checkDropdownMenuIconsAreVisible(
				cardItemActionButton
			);
		});

		await test.step('Switch back to Table view', async () => {
			await fdsSamplePage.changeVisualizationMode({
				page,
				visualizationMode: EFDSVisualizationMode.TABLE,
			});
		});
	});

	await test.step('Side Panel action opens a side panel with content title', async () => {
		await fdsSamplePage.clickItemAction(
			sidePanelActionLabelWithContentTitle
		);

		await expect(fdsSamplePage.sidePanel).toBeInViewport();

		const frame = fdsSamplePage.sidePanelFrame;

		await frame.getByText(sidePanelContentTitle).waitFor();

		await expect(frame.getByText(sidePanelContentTitle)).toHaveCount(1);

		await expect(
			frame.getByText('This is a side panel with a title.')
		).toBeVisible();

		await page.keyboard.press('Escape');

		await expect(fdsSamplePage.sidePanel).toHaveClass(/is-hidden/);
	});

	await test.step('Side Panel action opens a side panel with action title', async () => {
		await fdsSamplePage.clickItemAction(
			sidePanelActionLabelWithActionTitle
		);

		await expect(fdsSamplePage.sidePanel).toBeInViewport();

		await page.getByText(sidePanelActionTitle).waitFor();

		await expect(page.getByText(sidePanelActionTitle)).toHaveCount(1);

		const frame = fdsSamplePage.sidePanelFrame;

		await expect(
			frame.locator('.side-panel-iframe-header')
		).not.toBeInViewport();

		await expect(
			frame.getByText('This is a side panel without a title.')
		).toBeVisible();

		await page.keyboard.press('Escape');

		await expect(fdsSamplePage.sidePanel).toHaveClass(/is-hidden/);
	});

	await test.step('Side Panel action opens a side panel with duplicated title', async () => {
		await fdsSamplePage.clickItemAction(
			sidePanelActionLabelWithActionTitleContentTitle
		);

		await expect(fdsSamplePage.sidePanel).toBeInViewport();

		await page.getByText(sidePanelActionTitle).waitFor();

		await expect(page.getByText(sidePanelActionTitle)).toHaveCount(1);

		const frame = fdsSamplePage.sidePanelFrame;

		await expect(
			frame.locator('.side-panel-iframe-header')
		).toBeInViewport();
		await frame.getByText(sidePanelContentTitle).waitFor();

		await expect(frame.getByText(sidePanelContentTitle)).toHaveCount(1);

		await page.keyboard.press('Escape');

		await expect(fdsSamplePage.sidePanel).toHaveClass(/is-hidden/);
	});

	await test.step('Side Panel action opens a side panel without title', async () => {
		await fdsSamplePage.clickItemAction(sidePanelActionLabelWithoutTitle);

		await expect(fdsSamplePage.sidePanel).toBeInViewport();

		await expect(page.locator('.fds-side-panel-title')).toBeInViewport();
		const panelTitle = await page
			.locator('.fds-side-panel-title')
			.allInnerTexts();

		expect(panelTitle).toEqual(['']);

		const frame = fdsSamplePage.sidePanelFrame;

		await expect(
			frame.locator('.side-panel-iframe-header')
		).not.toBeInViewport();

		await expect(
			frame.getByText('This is a side panel without a title.')
		).toBeVisible();

		await page.keyboard.press('Escape');

		await expect(fdsSamplePage.sidePanel).toHaveClass(/is-hidden/);
	});

	await test.step('Sample view action opens an alert message and it receives the list of items', async () => {
		let dialogMessage = '';

		page.on('dialog', async (dialog) => {
			dialogMessage = dialog.message();
			await dialog.accept();
		});

		await fdsSamplePage.clickItemAction(sampleView);
		await expect(dialogMessage).toContain('Hello Sample1!');
		await expect(dialogMessage).toContain('element #1');

		await fdsSamplePage.clickItemAction(sampleView, 19);
		await expect(dialogMessage).toContain('Hello Sample32!');
		await expect(dialogMessage).toContain('element #20');

	});

	await test.step('Async connection refused action opens an unexpected error alert toast', async () => {
		await fdsSamplePage.clickItemAction(asyncConnectionRefused);

		await waitForAlert(page, 'Error:An unexpected error occurred.', {
			type: 'danger',
		});
	});

	await test.step('Async resource not found action opens an unexpected error alert toast', async () => {
		await fdsSamplePage.clickItemAction(asyncResourceNotFound);

		await waitForAlert(page, 'Error:An unexpected error occurred.', {
			type: 'danger',
		});
	});

	await test.step('Async success action opens a success alert toast', async () => {
		await fdsSamplePage.clickItemAction(asyncSuccess);

		await waitForAlert(page);
	});
});
