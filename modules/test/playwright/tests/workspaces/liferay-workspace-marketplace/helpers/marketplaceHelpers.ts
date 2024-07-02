/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	MARKETPLACE_CHANNEL,
	ORDER_TYPES,
	ORDER_WORKFLOW_STATUS_CODE,
	PAYMENT_STATUS,
	PRODUCT_WORKFLOW_STATUS_CODE,
} from '../utils/constants';

export async function createMarketplaceAccountUserCatalog({
	accountName,
	accountType,
	apiHelpers,
}) {
	try {
		const account = await apiHelpers.headlessAdminUser.postAccount({
			name: accountName,
			type: accountType,
		});

		await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
			account.id,
			['test@liferay.com']
		);

		const catalogData = {
			default: {},
			supplier: {accountId: account.id},
		};

		const catalogConfig = catalogData[accountType] || catalogData.default;

		const catalog =
			await apiHelpers.headlessCommerceAdminCatalog.postCatalog(
				catalogConfig
			);

		return {account, catalog};
	}
	catch (error) {
		console.error('Error when trying to create account', error);
		throw error;
	}
}

export async function assignMarketplaceUserToAccountRole({
	accountId,
	accountRole,
	apiHelpers,
}) {
	try {
		const user =
			await apiHelpers.headlessAdminUser.getUserAccountByEmailAddress(
				'test@liferay.com'
			);

		const rolesResponse =
			await apiHelpers.headlessAdminUser.getAccountRoles(accountId);

		const filteredAccountRole = rolesResponse?.items?.filter(
			(role) => role.name === accountRole
		);

		await apiHelpers.headlessAdminUser.assignUserToAccountRole(
			accountId,
			filteredAccountRole[0].id,
			user.id
		);
	}
	catch (error) {
		console.error('Error when trying to assign user to role', error);
		throw error;
	}
}

export async function createMarketplaceTestProductOrder({
	accountId,
	apiHelpers,
	catalogId,
	orderItems,
	productName,
}) {
	try {
		const channel =
			await apiHelpers.headlessCommerceAdminChannel.getChannelsPage(
				`name eq ${MARKETPLACE_CHANNEL}`
			);

		const product =
			await apiHelpers.headlessCommerceAdminCatalog.postProduct({
				active: true,
				catalogId,
				name: {
					en_US: productName,
				},
				productChannels: [
					{
						channelId: channel.items[0].id,
						currencyCode: 'USD',
						id: channel.items[0].id,
						name: MARKETPLACE_CHANNEL,
						type: 'site',
					},
				],
				productSpecifications: [
					{
						specificationKey: 'type',
						value: {
							en_US: 'DXP',
						},
					},
					{
						specificationKey: 'price-model',
						value: {
							en_US: 'paid',
						},
					},
					{
						specificationKey: 'latest-version',
						value: {
							en_US: '1.0.1',
						},
					},
				],
				productStatus: PRODUCT_WORKFLOW_STATUS_CODE.APPROVED,
				productType: 'virtual',
				productVirtualSettings: {
					productVirtualSettingsFileEntries: [
						{
							attachment: btoa('liferay'),
							version: 'Liferay Portal 7.4 GA110',
						},
					],
				},
			});

		const order = await apiHelpers.headlessCommerceAdminOrder.postOrder({
			accountId,
			channelId: channel.items[0].id,
			orderItems: [
				{
					decimalQuantity: orderItems.DECIMAL_QUANTITY,
					quantity: orderItems.QUANTITY,
					skuId: product.skus[0].id as unknown as string,
					unitPrice: orderItems.UNIT_PRICE,
				},
			],
			orderTypeExternalReferenceCode: ORDER_TYPES.DXPAPP,
		});

		await apiHelpers.headlessCommerceAdminOrder.patchOrder(order.id, {
			paymentStatus: PAYMENT_STATUS.COMPLETED,
		});

		await apiHelpers.headlessCommerceAdminOrder.patchOrder(order.id, {
			orderStatus: ORDER_WORKFLOW_STATUS_CODE.PROCESSING,
		});

		await apiHelpers.headlessCommerceAdminOrder.patchOrder(order.id, {
			orderStatus: ORDER_WORKFLOW_STATUS_CODE.COMPLETED,
		});

		return {order, product};
	}
	catch (error) {
		console.error('Error when trying to create product order', error);
		throw error;
	}
}

export default {createMarketplaceAccountUserCatalog};
