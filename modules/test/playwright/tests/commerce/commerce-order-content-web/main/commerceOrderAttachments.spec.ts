/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {commercePagesTest} from '../../../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../../../fixtures/dataApiHelpersTest';
import {displayPageTemplatesPagesTest} from '../../../../fixtures/displayPageTemplatesPagesTest';
import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {globalMenuPagesTest} from '../../../../fixtures/globalMenuPagesTest';
import {isolatedSiteTest} from '../../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../../fixtures/pageEditorPagesTest';
import {liferayConfig} from '../../../../liferay.config';
import getRandomString from '../../../../utils/getRandomString';
import {ORDER_WORKFLOW_STATUS_CODE} from '../../../workspaces/liferay-workspace-marketplace/main/utils/constants';
import {classicCommerceSetUp} from '../../utils/commerce';

export const test = mergeTests(
	commercePagesTest,
	dataApiHelpersTest,
	displayPageTemplatesPagesTest,
	featureFlagsTest({
		'LPD-6252': {enabled: true},
		'LPD-20379': {enabled: true},
	}),
	globalMenuPagesTest,
	pageEditorPagesTest,
	isolatedSiteTest,
	loginTest()
);

test(
	'Order Attachments Data Set fragment',
	{tag: '@LPD-83041'},
	async ({apiHelpers, displayPageTemplatesPage, page, pageEditorPage}) => {
		test.setTimeout(180000);

		const {channel, site} = await classicCommerceSetUp(
			apiHelpers,
			`B2B_${getRandomString()}`
		);

		const account = await apiHelpers.headlessAdminUser.postAccount({
			name: getRandomString(),
			type: 'business',
		});

		const address =
			await apiHelpers.headlessCommerceAdminAccount.postAddress(
				account.id,
				{phoneNumber: '1234567890', regionISOCode: 'AL'}
			);

		const sku =
			await apiHelpers.headlessCommerceAdminCatalog.getSkuByName(
				'CLSC55861'
			);

		const order = await apiHelpers.headlessCommerceAdminOrder.postOrder({
			accountId: account.id,
			billingAddressId: address.id,
			channelId: channel.id,
			orderItems: [
				{
					quantity: 1,
					skuId: sku.id,
				},
			],
			shippingAddressId: address.id,
		});

		await apiHelpers.headlessCommerceAdminOrder.patchOrder(order.id, {
			orderStatus: ORDER_WORKFLOW_STATUS_CODE.PROCESSING,
		});

		const attachmentTitle = getRandomString();

		await apiHelpers.headlessCommerceAdminOrderAttachment.postOrderAttachment(
			{
				attachment: {
					fileBase64:
						'iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNkYAAAAAYAAjCB0C8AAAAASUVORK5CYII=',
					name: 'invoice.png',
				},
				attachmentType: {key: 'invoice'},
				priority: 1,
				r_accountToCommerceOrderAttachments_accountEntryId: account.id,
				r_commerceOrderToCommerceOrderAttachments_commerceOrderId:
					order.id,
				title: attachmentTitle,
			}
		);

		await displayPageTemplatesPage.goto(site.friendlyUrlPath);

		const displayPageTemplateName = getRandomString();

		await displayPageTemplatesPage.createTemplate({
			contentType: 'Order',
			name: displayPageTemplateName,
		});
		await displayPageTemplatesPage.editTemplate(displayPageTemplateName);

		await pageEditorPage.addFragment('Order', 'Order Attachments Data Set');
		await pageEditorPage.waitForChangesSaved();

		await displayPageTemplatesPage.publishTemplate();
		await displayPageTemplatesPage.markAsDefault(displayPageTemplateName);

		await page.goto(
			liferayConfig.environment.baseUrl +
				`/web/${site.name}/order/${order.id}`
		);

		await expect(
			page.getByText(attachmentTitle, {exact: true})
		).toBeVisible();
		await expect(page.getByText('png', {exact: true})).toBeVisible();
		await expect(page.getByText('Invoice', {exact: true})).toBeVisible();
	}
);
