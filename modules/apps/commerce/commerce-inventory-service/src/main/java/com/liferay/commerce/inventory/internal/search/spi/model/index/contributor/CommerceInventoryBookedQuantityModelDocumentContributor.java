/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.inventory.internal.search.spi.model.index.contributor;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.commerce.inventory.model.CommerceInventoryBookedQuantity;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian I. Kim
 */
@Component(
	property = "indexer.class.name=com.liferay.commerce.inventory.model.CommerceInventoryBookedQuantity",
	service = ModelDocumentContributor.class
)
public class CommerceInventoryBookedQuantityModelDocumentContributor
	implements ModelDocumentContributor<CommerceInventoryBookedQuantity> {

	@Override
	public void contribute(
		Document document,
		CommerceInventoryBookedQuantity commerceInventoryBookedQuantity) {

		try {
			document.addNumberSortable(
				Field.ENTRY_CLASS_PK,
				commerceInventoryBookedQuantity.
					getCommerceInventoryBookedQuantityId());

			CommerceOrderItem commerceOrderItem =
				_commerceOrderItemLocalService.
					fetchCommerceOrderItemByBookedQuantityId(
						commerceInventoryBookedQuantity.
							getCommerceInventoryBookedQuantityId());

			CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

			AccountEntry accountEntry =
				_accountEntryLocalService.fetchAccountEntry(
					commerceOrder.getCommerceAccountId());

			if (accountEntry != null) {
				document.addKeyword("accountName", accountEntry.getName());
			}

			document.addKeyword(
				"commerceAccountId", commerceOrder.getCommerceAccountId());
			document.addKeyword(
				"commerceOrderId", commerceOrder.getCommerceOrderId());

			document.addNumber(
				"itemsQuantity", commerceInventoryBookedQuantity.getQuantity());

			document.addText("sku", commerceOrderItem.getSku());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				long commerceInventoryBookedQuantityId =
					commerceInventoryBookedQuantity.
						getCommerceInventoryBookedQuantityId();

				_log.warn(
					"Unable to index commerce inventory booked quantity " +
						commerceInventoryBookedQuantityId,
					exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceInventoryBookedQuantityModelDocumentContributor.class);

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;

}