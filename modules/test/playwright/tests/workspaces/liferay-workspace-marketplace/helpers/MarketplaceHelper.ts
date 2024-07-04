/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {ApiHelpers} from '../../../../helpers/ApiHelpers';
import {
	MARKETPLACE_CHANNEL,
	ORDER_TYPES,
	ORDER_WORKFLOW_STATUS_CODE,
	PAYMENT_STATUS,
} from '../utils/constants';

export class MarketplaceHelper {
	readonly apiHelpers: ApiHelpers;

	constructor(page: Page) {
		this.apiHelpers = new ApiHelpers(page);
	}

	async createMarketplaceAccountUserCatalog({accountName, accountType}) {
		try {
			const account = await this.apiHelpers.headlessAdminUser.postAccount(
				{
					name: accountName,
					type: accountType,
				}
			);

			await this.apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
				account.id,
				['test@liferay.com']
			);

			const catalogData = {
				default: {},
				supplier: {accountId: account.id},
			};

			const catalogConfig =
				catalogData[accountType] || catalogData.default;

			const catalog =
				await this.apiHelpers.headlessCommerceAdminCatalog.postCatalog(
					catalogConfig
				);

			return {account, catalog};
		}
		catch (error) {
			console.error('Error when trying to create account', error);
			throw error;
		}
	}

	async assignMarketplaceUserToAccountRole({accountId, accountRole}) {
		try {
			const user =
				await this.apiHelpers.headlessAdminUser.getUserAccountByEmailAddress(
					'test@liferay.com'
				);

			const rolesResponse =
				await this.apiHelpers.headlessAdminUser.getAccountRoles(
					accountId
				);

			const filteredAccountRole = rolesResponse?.items?.filter(
				(role) => role.name === accountRole
			);

			await this.apiHelpers.headlessAdminUser.assignUserToAccountRole(
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

	async createMarketplaceTestProductOrder({
		accountId,
		orderItems,
		productBody,
	}) {
		try {
			const channel =
				await this.apiHelpers.headlessCommerceAdminChannel.getChannelsPage(
					`name eq ${MARKETPLACE_CHANNEL}`
				);

			const product =
				await this.apiHelpers.headlessCommerceAdminCatalog.postProduct(
					productBody
				);

			const order =
				await this.apiHelpers.headlessCommerceAdminOrder.postOrder({
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

			await this.apiHelpers.headlessCommerceAdminOrder.patchOrder(
				order.id,
				{
					paymentStatus: PAYMENT_STATUS.COMPLETED,
				}
			);

			await this.apiHelpers.headlessCommerceAdminOrder.patchOrder(
				order.id,
				{
					orderStatus: ORDER_WORKFLOW_STATUS_CODE.PROCESSING,
				}
			);

			await this.apiHelpers.headlessCommerceAdminOrder.patchOrder(
				order.id,
				{
					orderStatus: ORDER_WORKFLOW_STATUS_CODE.COMPLETED,
				}
			);

			return {order, product};
		}
		catch (error) {
			console.error('Error when trying to create product order', error);
			throw error;
		}
	}

	async getMarketplaceVocabularyAndCategory({
		categoryName,
		siteId,
		vocabularyName,
	}) {
		try {
			const {items: vocabularies} =
				await this.apiHelpers.headlessAdminTaxonomy.getTaxonomyVocabularyBySiteId(
					siteId
				);

			const vocabulary = vocabularies.find(
				(vocabulary) => vocabulary.name === vocabularyName
			);

			if (!vocabulary) {
				throw new Error(`Vocabulary "${vocabularyName}" not found`);
			}

			const {items: categories} =
				await this.apiHelpers.headlessAdminTaxonomy.getTaxonomyCategoryByVocabularyId(
					vocabulary.id
				);

			const category = categories.find(
				(category) => category.name === categoryName
			);

			if (!category) {
				throw new Error(
					`Category "${categoryName}" not found in vocabulary "${vocabularyName}"`
				);
			}

			return category;
		}
		catch (error) {
			console.error('Error when trying to get category', error);
			throw error;
		}
	}
}
