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

package com.liferay.commerce.internal.search.spi.model.index.contributor;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Danny Situ
 */
@Component(
	property = "indexer.class.name=com.liferay.commerce.model.CommerceOrder",
	service = ModelDocumentContributor.class
)
public class CommerceOrderModelDocumentContributor
	implements ModelDocumentContributor<CommerceOrder> {

	@Override
	public void contribute(Document document, CommerceOrder commerceOrder) {
		try {
			CommerceChannel commerceChannel =
				_commerceChannelLocalService.getCommerceChannelByOrderGroupId(
					commerceOrder.getGroupId());

			document.addNumberSortable(
				Field.ENTRY_CLASS_PK, commerceOrder.getCommerceOrderId());
			document.addKeyword(Field.STATUS, commerceOrder.getStatus());

			AccountEntry accountEntry =
				_accountEntryLocalService.fetchAccountEntry(
					commerceOrder.getCommerceAccountId());

			if (accountEntry != null) {
				document.addKeyword("accountName", accountEntry.getName());
			}

			document.addKeyword(
				"advanceStatus", commerceOrder.getAdvanceStatus());
			document.addKeyword(
				"commerceAccountId", commerceOrder.getCommerceAccountId());
			document.addKeyword(
				"commerceChannelId", commerceChannel.getCommerceChannelId());
			document.addKeyword(
				"externalReferenceCode",
				commerceOrder.getExternalReferenceCode());
			document.addNumber(
				"itemsQuantity", _getItemsQuantity(commerceOrder));

			User user = _userLocalService.getUser(commerceOrder.getUserId());

			document.addText(
				"orderCreatorEmailAddress", user.getEmailAddress());

			document.addDate("orderDate", commerceOrder.getOrderDate());
			document.addDateSortable("orderDate", commerceOrder.getOrderDate());
			document.addKeyword("orderStatus", commerceOrder.getOrderStatus());
			document.addKeyword(
				"purchaseOrderNumber", commerceOrder.getPurchaseOrderNumber());
			document.addNumber("total", commerceOrder.getTotal());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to index commerce order " +
						commerceOrder.getCommerceOrderId(),
					exception);
			}
		}
	}

	private int _getItemsQuantity(CommerceOrder commerceOrder) {
		int count = 0;

		for (CommerceOrderItem commerceOrderItem :
				commerceOrder.getCommerceOrderItems()) {

			count += commerceOrderItem.getQuantity();
		}

		return count;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderModelDocumentContributor.class);

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private UserLocalService _userLocalService;

}