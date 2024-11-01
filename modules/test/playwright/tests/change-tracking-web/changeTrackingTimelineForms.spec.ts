/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {changeTrackingPagesTest} from '../../fixtures/changeTrackingPagesTest';
import {formsPagesTest} from '../../fixtures/formsPagesTest';
import {getRandomInt} from '../../utils/getRandomInt';
import getRandomString from '../../utils/getRandomString';
import {waitForAlert} from '../../utils/waitForAlert';

export const test = mergeTests(formsPagesTest, changeTrackingPagesTest);

test('LPD-39428 Assert publication timeline history is enabled for forms', async ({
	formBuilderPage,
	formBuilderSidePanelPage,
	formsPage,
	page,
}) => {
	await formBuilderPage.goToNew();
	const formTitle = 'Form' + getRandomInt();
	await formBuilderPage.fillFormTitle(formTitle);
	await formBuilderSidePanelPage.addFieldByDoubleClick('Text');
	await formBuilderPage.publishButton.click();
	await page.waitForTimeout(500);
	await formsPage.goTo();

	const timelineButton = page.getByLabel('timeline-button');
	await timelineButton.waitFor();
	await timelineButton.click();

	const timelineActionsButton = page.locator('.publication-timeline button');

	await expect(timelineActionsButton).toBeVisible();
	await expect(page.getByRole('menuitem', {name: formTitle})).toBeVisible();
});

test('LPD-39428 Assert publication timeline history is enabled for element sets', async ({
	changeTrackingPage,
	formBuilderPage,
	formBuilderSidePanelPage,
	formsPage,
	page,
}) => {
	await formsPage.goTo();
	await changeTrackingPage.selectTab('Element Sets');
	await formsPage.clickManagementToolbarNewButton();
	const elementSetTitle = 'Element Set' + getRandomInt();
	await formBuilderPage.fillFormTitle(elementSetTitle);
	await formBuilderSidePanelPage.addFieldByDoubleClick('Text');
	await page.getByRole('button', {name: 'Save'}).click();
	await waitForAlert(page, 'Success:Your request completed successfully.');
	await formsPage.goTo();
	await changeTrackingPage.selectTab('Element Sets');

	const timelineButton = page.getByLabel('timeline-button');
	await timelineButton.waitFor();
	await timelineButton.click();

	await expect(
		page.getByRole('menuitem', {name: elementSetTitle})
	).toBeVisible();
});

test('LPD-39428 Assert publication timeline history is enabled for data providers', async ({
	changeTrackingPage,
	formsPage,
	page,
}) => {
	await formsPage.goTo();
	await changeTrackingPage.selectTab('Data Providers');
	await formsPage.clickManagementToolbarNewButton();
	const dataProviderTitle = 'Data Provider' + getRandomInt();
	await page
		.getByPlaceholder("Enter the data provider's name")
		.fill(dataProviderTitle);
	await page
		.getByPlaceholder('Enter the REST service URL')
		.fill('http://' + getRandomString() + '.com');
	await page.getByRole('button', {name: 'Save'}).click();
	await waitForAlert(page, 'Success:Your request completed successfully.');
	await formsPage.goTo();
	await changeTrackingPage.selectTab('Data Providers');

	const timelineButton = page.getByLabel('timeline-button');
	await timelineButton.waitFor();
	await timelineButton.click();

	const timelineActionsButton = page.locator('.publication-timeline button');

	await expect(timelineActionsButton).toBeVisible();
	await expect(
		page.getByRole('menuitem', {name: dataProviderTitle})
	).toBeVisible();
});
