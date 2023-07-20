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

package com.liferay.commerce.order.content.web.internal.info.collection.provider;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.RelatedInfoItemCollectionProvider;
import com.liferay.info.pagination.InfoPage;
import com.liferay.info.pagination.Pagination;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = RelatedInfoItemCollectionProvider.class)
public class CommerceOrderItemsRelatedInfoItemCollectionProvider
	implements RelatedInfoItemCollectionProvider
		<CommerceOrder, CommerceOrderItem> {

	@Override
	public InfoPage<CommerceOrderItem> getCollectionInfoPage(
		CollectionQuery collectionQuery) {

		Object relatedItem = collectionQuery.getRelatedItem();

		Pagination pagination = collectionQuery.getPagination();

		if (!(relatedItem instanceof CommerceOrder)) {
			return InfoPage.of(Collections.emptyList(), pagination, 0);
		}

		CommerceOrder commerceOrder = (CommerceOrder)relatedItem;

		try {
			List<CommerceOrderItem> commerceOrderItems =
				_commerceOrderItemLocalService.getCommerceOrderItems(
					commerceOrder.getCommerceOrderId(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS);

			if (!commerceOrderItems.isEmpty()) {
				return InfoPage.of(
					ListUtil.subList(
						commerceOrderItems, pagination.getStart(),
						pagination.getEnd()),
					pagination, commerceOrderItems.size());
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return InfoPage.of(Collections.emptyList(), pagination, 0);
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "order-items");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderItemsRelatedInfoItemCollectionProvider.class);

	@Reference
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private Language _language;

}