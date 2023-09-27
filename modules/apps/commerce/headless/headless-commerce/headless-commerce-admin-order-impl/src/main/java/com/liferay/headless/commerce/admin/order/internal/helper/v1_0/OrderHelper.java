/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.order.internal.helper.v1_0;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.headless.commerce.admin.order.dto.v1_0.Order;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Locale;
import java.util.Map;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrati
 */
@Component(service = OrderHelper.class)
public class OrderHelper {

	public Page<Order> getOrdersPage(
			long companyId, Filter filter, Pagination pagination, String search,
			Sort[] sorts,
			UnsafeFunction<Document, Order, Exception> transformUnsafeFunction,
			boolean useSearchResultPermissionFilter)
		throws Exception {

		return SearchUtil.search(
			null, booleanQuery -> booleanQuery.getPreBooleanFilter(), filter,
			CommerceOrder.class.getName(), search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			object -> {
				SearchContext searchContext = (SearchContext)object;

				searchContext.setAttribute(
					"useSearchResultPermissionFilter",
					useSearchResultPermissionFilter);
				searchContext.setCompanyId(companyId);

				long[] commerceChannelGroupIds =
					TransformUtil.transformToLongArray(
						_commerceChannelLocalService.getCommerceChannels(
							companyId),
						CommerceChannel::getGroupId);

				if ((commerceChannelGroupIds != null) &&
					(commerceChannelGroupIds.length > 0)) {

					searchContext.setGroupIds(commerceChannelGroupIds);
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

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.admin.order.internal.dto.v1_0.converter.OrderDTOConverter)"
	)
	private DTOConverter<CommerceOrder, Order> _orderDTOConverter;

}