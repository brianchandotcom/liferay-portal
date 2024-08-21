/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {applicationsMenuPageTest} from '../../../fixtures/applicationsMenuPageTest';
import {commercePagesTest} from '../../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {commerceReturnSetUp} from '../utils/commerce';

export const test = mergeTests(
	applicationsMenuPageTest,
	commercePagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-10562': true,
	}),
	loginTest()
);

test('LPD-21633 Returns widget to show return and refunds', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceLayoutsPage,
	page,
	returnDetailsPage,
	returnsPage,
}) => {
	const {commerceReturn, payment, site} =
		await commerceReturnSetUp(apiHelpers);

	await apiHelpers.headlessCommerceAdminPaymentApiHelper.postPayment({
		amount: payment.amount,
		relatedItemId: payment.id,
		relatedItemName:
			'com.liferay.commerce.payment.model.CommercePaymentEntry',
		type: 1,
	});

	await applicationsMenuPage.goToSite(site.name);

	await commerceLayoutsPage.goToPages(false);
	await commerceLayoutsPage.createWidgetPage('Returns Page');

	await page.goto(`/web/${site.name}`);

	await returnsPage.addReturnsWidget();

	await (
		await returnsPage.tableRowLink({
			colIndex: 0,
			rowValue: commerceReturn.id,
		})
	).click();

	await returnDetailsPage.returnActionsButton.click();

	await expect(
		returnDetailsPage.returnActionsViewRefundsButton
	).toBeVisible();

	await returnDetailsPage.returnActionsViewRefundsButton.click();

	await expect(returnDetailsPage.viewRefundsTitle).toBeVisible();
	await expect(
		returnDetailsPage.viewRefundsFrame.getByText(
			'Showing 1 to 1 of 1 entries.'
		)
	).toBeVisible();
});

test('LPD-32515 Returns widget displays amount fields with correct currency pattern', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceLayoutsPage,
	page,
	returnDetailsPage,
	returnsPage,
}) => {
	const {commerceReturn, site, sku} = await commerceReturnSetUp(apiHelpers);

	await applicationsMenuPage.goToSite(site.name);

	await commerceLayoutsPage.goToPages(false);
	await commerceLayoutsPage.createWidgetPage('Returns Page');

	await page.goto(`/web/${site.name}`);

	await returnsPage.addReturnsWidget();

	await expect(
		(await returnsPage.tableRow(1, '$ 0.00', true)).row
	).toBeVisible();

	await (
		await returnsPage.tableRowLink({
			colIndex: 0,
			rowValue: commerceReturn.id,
		})
	).click();

	await expect(
		(await returnDetailsPage.tableRow(0, sku.sku, true)).row
	).toBeVisible();

	for await (const currencyField of await returnDetailsPage.page
		.getByText('0.00')
		.all()) {
		await expect(currencyField.getByText('$')).toBeVisible();
	}
});

test('LPD-32522 Returns widget displays status field on return items table when return is submitted', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceLayoutsPage,
	page,
	returnDetailsPage,
	returnsPage,
}) => {
	const {commerceReturn, site} = await commerceReturnSetUp(
		apiHelpers,
		0,
		0,
		0,
		1,
		'draft'
	);

	await applicationsMenuPage.goToSite(site.name);

	await commerceLayoutsPage.goToPages(false);
	await commerceLayoutsPage.createWidgetPage('Returns Page');

	await page.goto(`/web/${site.name}`);

	await returnsPage.addReturnsWidget();

	await (
		await returnsPage.tableRowLink({
			colIndex: 0,
			rowValue: commerceReturn.id,
		})
	).click();

	await expect(await returnDetailsPage.table).toBeVisible();

	await expect(
		await returnDetailsPage.table.getByText('Status')
	).toBeHidden();

	await returnDetailsPage.submitReturnRequestButton.click();

	await expect(
		await returnDetailsPage.table.getByText('Status')
	).toBeVisible();
});

test('LPD-32519 Warning message before submitting a return should not be shown once the return has been submitted', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceLayoutsPage,
	page,
	returnDetailsPage,
	returnsPage,
}) => {
	const {commerceReturn, site} = await commerceReturnSetUp(
		apiHelpers,
		10,
		1,
		1,
		1,
		'draft'
	);

	await applicationsMenuPage.goToSite(site.name);

	await commerceLayoutsPage.goToPages(false);
	await commerceLayoutsPage.createWidgetPage('Returns Page');

	await page.goto(`/web/${site.name}`);

	await returnsPage.addReturnsWidget();

	await (
		await returnsPage.tableRowLink({
			colIndex: 0,
			rowValue: commerceReturn.id,
		})
	).click();

	await expect(
		page.getByText(
			'Warning:Please review the details of the returning items before submitting the request.'
		)
	).toBeVisible();

	await expect(returnDetailsPage.submitReturnRequestLink).toBeVisible();

	await returnDetailsPage.submitReturnRequestLink.click();

	await expect(
		page.getByText(
			'Warning:Please review the details of the returning items before submitting the request.'
		)
	).not.toBeVisible();
});

test('LPD-32514 Return external reference code can not be edited in returns widget', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceLayoutsPage,
	page,
	returnDetailsPage,
	returnsPage,
}) => {
	const {commerceReturn, site} = await commerceReturnSetUp(apiHelpers);

	await applicationsMenuPage.goToSite(site.name);

	await commerceLayoutsPage.goToPages(false);
	await commerceLayoutsPage.createWidgetPage('Returns Page');

	await page.goto(`/web/${site.name}`);

	await returnsPage.addReturnsWidget();

	await (
		await returnsPage.tableRowLink({
			colIndex: 0,
			rowValue: commerceReturn.id,
		})
	).click();

	await expect(
		returnDetailsPage.page.locator('#erc-edit-modal-opener')
	).toBeHidden();
});

test('LPD-32521 Returns widget details page will only show returns status', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceLayoutsPage,
	page,
	returnDetailsPage,
	returnsPage,
}) => {
	const {commerceReturn, site} = await commerceReturnSetUp(apiHelpers);

	await applicationsMenuPage.goToSite(site.name);

	await commerceLayoutsPage.goToPages(false);
	await commerceLayoutsPage.createWidgetPage('Returns Page');

	await page.goto(`/web/${site.name}`);

	await returnsPage.addReturnsWidget();

	await (
		await returnsPage.tableRowLink({
			colIndex: 0,
			rowValue: commerceReturn.id,
		})
	).click();

	await expect(
		returnDetailsPage.page.getByText(
			commerceReturn.id + ' APPROVED PROCESSING'
		)
	).toBeHidden();

	await expect(
		returnDetailsPage.page.getByText(commerceReturn.id + ' PROCESSING')
	).toBeVisible();
});

test('LPD-32524 Returns widget to show comments for return items', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceLayoutsPage,
	page,
	returnDetailsPage,
	returnsPage,
}) => {
	const {commerceReturn, site} = await commerceReturnSetUp(apiHelpers);

	await applicationsMenuPage.goToSite(site.name);

	await commerceLayoutsPage.goToPages(false);
	await commerceLayoutsPage.createWidgetPage('Returns Page');

	await page.goto(`/web/${site.name}`);

	await returnsPage.addReturnsWidget();

	await (
		await returnsPage.tableRowLink({
			colIndex: 0,
			rowValue: commerceReturn.id,
		})
	).click();

	await returnDetailsPage.returnActionsButton.click();

	await expect(
		returnDetailsPage.returnActionsViewDetailsButton
	).toBeVisible();

	await returnDetailsPage.returnActionsViewDetailsButton.click();

	await expect(returnDetailsPage.viewDetailsTitle).toBeVisible();

	await returnDetailsPage.viewDetailsCommentInput.fill('This is a comment.');
	await returnDetailsPage.viewDetailsSubmitButton.click();

	await expect(
		returnDetailsPage.viewDetailsFrame.getByText('This is a comment.')
	).toBeVisible();
	await expect(returnDetailsPage.viewDetailsCommentInput).toBeVisible();
});

test('LPD-32523 Returns widget to show received quantity label localized', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceLayoutsPage,
	page,
	returnDetailsPage,
	returnsPage,
}) => {
	const {commerceReturn, site} = await commerceReturnSetUp(apiHelpers);

	await applicationsMenuPage.goToSite(site.name);

	await commerceLayoutsPage.goToPages(false);
	await commerceLayoutsPage.createWidgetPage('Returns Page');

	await page.goto(`/web/${site.name}`);

	await returnsPage.addReturnsWidget();

	await (
		await returnsPage.tableRowLink({
			colIndex: 0,
			rowValue: commerceReturn.id,
		})
	).click();

	await expect(await returnDetailsPage.table).toBeVisible();

	await expect(
		await returnDetailsPage.table.getByText('Received Quantity')
	).toBeVisible();
});
