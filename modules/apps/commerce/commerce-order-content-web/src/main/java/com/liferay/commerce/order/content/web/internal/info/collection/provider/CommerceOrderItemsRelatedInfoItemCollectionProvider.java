/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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