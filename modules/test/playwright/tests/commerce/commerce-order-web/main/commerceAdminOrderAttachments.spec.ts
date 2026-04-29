/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {commercePagesTest} from '../../../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {globalMenuPagesTest} from '../../../../fixtures/globalMenuPagesTest';
import {isolatedSiteTest} from '../../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../../fixtures/loginTest';
import {getRandomInt} from '../../../../utils/getRandomInt';
import getRandomString from '../../../../utils/getRandomString';

export const test = mergeTests(
	commercePagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-6252': {enabled: true},
	}),
	globalMenuPagesTest,
	isolatedSiteTest,
	loginTest()
);

async function setUpOrder(apiHelpers, site) {
	const channel = await apiHelpers.headlessCommerceAdminChannel.postChannel({
		siteGroupId: site.id,
	});

	const catalog = await apiHelpers.headlessCommerceAdminCatalog.postCatalog();

	const product = await apiHelpers.headlessCommerceAdminCatalog.postProduct({
		catalogId: catalog.id,
		name: {en_US: getRandomString()},
	});

	const productSkus = await apiHelpers.headlessCommerceAdminCatalog
		.getProduct(product.productId)
		.then((product) => {
			return product.skus;
		});

	const sku = productSkus[0];

	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: getRandomString(),
		type: 'business',
	});

	await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
		account.id,
		['test@liferay.com']
	);

	const address = await apiHelpers.headlessCommerceAdminAccount.postAddress(
		account.id,
		{regionISOCode: 'AL'}
	);

	const warehouse =
		await apiHelpers.headlessCommerceAdminInventoryApiHelper.postWarehouses(
			{
				active: true,
				latitude: getRandomInt(),
				longitude: getRandomInt(),
				warehouseItems: [{quantity: 1, sku: sku.sku}],
			}
		);

	await apiHelpers.headlessCommerceAdminInventoryApiHelper.postWarehousesChannels(
		warehouse.id,
		channel.id
	);

	const order = await apiHelpers.headlessCommerceAdminOrder.postOrder({
		accountId: account.id,
		billingAddressId: address.id,
		channelId: channel.id,
		orderItems: [{quantity: 1, skuId: sku.id}],
		orderStatus: '1',
		paymentMethod: 'money-order',
		paymentStatus: '0',
		shippingAddressId: address.id,
		shippingMethod: 'by-weight',
		shippingOption: 'standard-option',
	});

	return {account, order};
}

test(
	'Verify the order Attachments admin tab lists, adds, edits, and deletes attachments',
	{tag: ['@LPD-83042']},
	async ({
		apiHelpers,
		commerceAdminOrderAttachmentsPage,
		commerceAdminOrderDetailsPage,
		commerceAdminOrdersPage,
		site,
	}) => {
		test.setTimeout(180000);

		const {order} = await setUpOrder(apiHelpers, site);

		const seededTitle = `seeded-${getRandomString()}.png`;

		await apiHelpers.headlessCommerceAdminOrderAttachment.postOrderAttachment(
			order.id,
			{
				attachment:
					'iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNkYAAAAAYAAjCB0C8AAAAASUVORK5CYII=',
				priority: 1,
				title: seededTitle,
				type: 'invoice',
			}
		);

		await test.step('Open the order details', async () => {
			await commerceAdminOrdersPage.goto();

			await (
				await commerceAdminOrdersPage.tableRowLink({
					colIndex: 1,
					rowValue: order.id,
				})
			).click();

			await expect(
				commerceAdminOrderDetailsPage.headerDetailsTitle
			).toBeVisible();
		});

		await test.step('Open the Attachments tab and verify the seeded row', async () => {
			await commerceAdminOrderAttachmentsPage.attachmentsTab.click();

			await expect(
				commerceAdminOrderAttachmentsPage.rowByTitle(seededTitle)
			).toBeVisible();
		});

		const addedTitle = `added-${getRandomString()}.png`;

		await test.step('Add a new attachment via the creation menu', async () => {
			await commerceAdminOrderAttachmentsPage.addAttachmentMenuItem.click();

			await expect(
				commerceAdminOrderAttachmentsPage.sidePanelTitleInput
			).toBeVisible();

			await commerceAdminOrderAttachmentsPage.sidePanelTitleInput.fill(
				addedTitle
			);
			await commerceAdminOrderAttachmentsPage.sidePanelTypeSelect.selectOption(
				'invoice'
			);
			await commerceAdminOrderAttachmentsPage.sidePanelPriorityInput.fill(
				'5'
			);

			await commerceAdminOrderAttachmentsPage.sidePanelFileInput.setInputFiles(
				{
					buffer: Buffer.from(
						'iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNkYAAAAAYAAjCB0C8AAAAASUVORK5CYII=',
						'base64'
					),
					mimeType: 'image/png',
					name: addedTitle,
				}
			);

			await commerceAdminOrderAttachmentsPage.sidePanelSaveButton.click();

			await expect(
				commerceAdminOrderAttachmentsPage.rowByTitle(addedTitle)
			).toBeVisible();
		});

		const editedTitle = `edited-${getRandomString()}.png`;

		await test.step('Edit the added attachment from the row dropdown', async () => {
			await expect(async () => {
				await commerceAdminOrderAttachmentsPage
					.rowActionsButton(addedTitle)
					.click();
				await expect(
					commerceAdminOrderAttachmentsPage.editRowAction
				).toBeVisible({timeout: 500});
			}).toPass({timeout: 5000});

			await commerceAdminOrderAttachmentsPage.editRowAction.click();

			await expect(
				commerceAdminOrderAttachmentsPage.sidePanelTitleInput
			).toHaveValue(addedTitle);

			await commerceAdminOrderAttachmentsPage.sidePanelTitleInput.fill(
				editedTitle
			);

			await commerceAdminOrderAttachmentsPage.sidePanelSaveButton.click();

			await expect(
				commerceAdminOrderAttachmentsPage.rowByTitle(editedTitle)
			).toBeVisible();
			await expect(
				commerceAdminOrderAttachmentsPage.rowByTitle(addedTitle)
			).toHaveCount(0);
		});

		await test.step('Delete the edited attachment from the row dropdown', async () => {
			await expect(async () => {
				await commerceAdminOrderAttachmentsPage
					.rowActionsButton(editedTitle)
					.click();
				await expect(
					commerceAdminOrderAttachmentsPage.deleteRowAction
				).toBeVisible({timeout: 500});
			}).toPass({timeout: 5000});

			await commerceAdminOrderAttachmentsPage.deleteRowAction.click();

			await commerceAdminOrderAttachmentsPage.deleteConfirmButton.click();

			await expect(
				commerceAdminOrderAttachmentsPage.rowByTitle(editedTitle)
			).toHaveCount(0);
			await expect(
				commerceAdminOrderAttachmentsPage.rowByTitle(seededTitle)
			).toBeVisible();
		});
	}
);
