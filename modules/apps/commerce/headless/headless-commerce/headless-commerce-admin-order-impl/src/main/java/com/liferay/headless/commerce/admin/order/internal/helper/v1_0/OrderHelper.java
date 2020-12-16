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

package com.liferay.headless.commerce.admin.order.internal.helper.v1_0;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.headless.commerce.admin.order.dto.v1_0.Order;
import com.liferay.headless.commerce.admin.order.internal.dto.v1_0.converter.OrderDTOConverter;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrati
 */
@Component(enabled = false, immediate = true, service = OrderHelper.class)
public class OrderHelper {

	public Page<Order> getOrdersPage(
			long companyId, Filter filter, Pagination pagination, String search,
			Sort[] sorts,
			UnsafeFunction<Document, Order, Exception> transformUnsafeFunction,
			boolean useSearchResultPermissionFilter)
		throws Exception {

		return SearchUtil.search(
			null, booleanQuery -> booleanQuery.getPreBooleanFilter(), filter,
			CommerceOrder.class, search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			new UnsafeConsumer() {

				public void accept(Object object) throws Exception {
					SearchContext searchContext = (SearchContext)object;

					searchContext.setCompanyId(companyId);

					searchContext.setAttribute(
						"useSearchResultPermissionFilter",
						useSearchResultPermissionFilter);

					long[] commerceChannelGroupIds =
						_getCommerceChannelGroupIds(companyId);

					if ((commerceChannelGroupIds != null) &&
						(commerceChannelGroupIds.length > 0)) {

						searchContext.setGroupIds(commerceChannelGroupIds);
					}
				}

			},
			sorts, transformUnsafeFunction);
	}

	public Order toOrder(long commerceOrderId, Locale locale) throws Exception {
		return _orderDTOConverter.toDTO(
			new DefaultDTOConverterContext(commerceOrderId, locale));
	}

	public Order toOrder(
			long commerceOrderId, Locale locale, boolean acceptAllLanguages,
			User contextUser, UriInfo contextUriInfo,
			Map<String, Map<String, String>> actions)
		throws Exception {

		return _orderDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				acceptAllLanguages, actions, _dtoConverterRegistry,
				commerceOrderId, locale, contextUriInfo, contextUser));
	}

	private long[] _getCommerceChannelGroupIds(long companyId)
		throws Exception {

		List<CommerceChannel> commerceChannels =
			_commerceChannelService.searchCommerceChannels(companyId);

		Stream<CommerceChannel> stream = commerceChannels.stream();

		return stream.mapToLong(
			CommerceChannel::getGroupId
		).toArray();
	}

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private OrderDTOConverter _orderDTOConverter;

}