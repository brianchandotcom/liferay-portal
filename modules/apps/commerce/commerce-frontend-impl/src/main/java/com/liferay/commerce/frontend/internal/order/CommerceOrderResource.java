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

package com.liferay.commerce.frontend.internal.order;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.frontend.internal.account.model.Order;
import com.liferay.commerce.frontend.internal.account.model.OrderList;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(enabled = false, service = CommerceOrderResource.class)
public class CommerceOrderResource {

	public OrderList getOrderList(
			long groupId, String keywords, int page, int pageSize,
			HttpServletRequest httpServletRequest,
			CommerceAccount commerceAccount)
		throws PortalException {

		long companyId = _portal.getCompanyId(httpServletRequest);

		groupId =
			_commerceChannelLocalService.getCommerceChannelGroupIdBySiteGroupId(
				groupId);

		List<Order> orders = _getOrders(
			companyId, groupId, keywords, page, pageSize, httpServletRequest);

		return new OrderList(
			orders, _getOrdersCount(companyId, groupId, keywords));
	}

	private String _getOrderLinkURL(
			long commerceOrderId, HttpServletRequest httpServletRequest)
		throws PortalException {

		PortletURL editURL = PortletURLBuilder.create(
			PortletProviderUtil.getPortletURL(
				httpServletRequest, CommerceOrder.class.getName(),
				PortletProvider.Action.EDIT)
		).setActionName(
			"/commerce_open_order_content/edit_commerce_order"
		).setCMD(
			"setCurrent"
		).setParameter(
			"commerceOrderId", commerceOrderId
		).buildPortletURL();

		String redirect = _portal.getCurrentURL(httpServletRequest);

		editURL.setParameter("redirect", redirect);

		return editURL.toString();
	}

	private List<Order> _getOrders(
			long companyId, long groupId, String keywords, int page,
			int pageSize, HttpServletRequest httpServletRequest)
		throws PortalException {

		List<Order> orders = new ArrayList<>();

		int start = (page - 1) * pageSize;
		int end = page * pageSize;

		List<CommerceOrder> userCommerceOrders =
			_commerceOrderService.getUserPendingCommerceOrders(
				companyId, groupId, keywords, start, end);

		for (CommerceOrder commerceOrder : userCommerceOrders) {
			Date modifiedDate = commerceOrder.getModifiedDate();

			String modifiedDateTimeDescription =
				LanguageUtil.getTimeDescription(
					httpServletRequest,
					System.currentTimeMillis() - modifiedDate.getTime(), true);

			orders.add(
				new Order(
					commerceOrder.getCommerceOrderId(),
					commerceOrder.getCommerceAccountId(),
					commerceOrder.getCommerceAccountName(),
					commerceOrder.getPurchaseOrderNumber(),
					LanguageUtil.format(
						httpServletRequest, "x-ago",
						modifiedDateTimeDescription),
					WorkflowConstants.getStatusLabel(commerceOrder.getStatus()),
					_getOrderLinkURL(
						commerceOrder.getCommerceOrderId(),
						httpServletRequest)));
		}

		return orders;
	}

	private int _getOrdersCount(long companyId, long groupId, String keywords)
		throws PortalException {

		return (int)_commerceOrderService.getUserPendingCommerceOrdersCount(
			companyId, groupId, keywords);
	}

	private static final ObjectMapper _OBJECT_MAPPER = new ObjectMapper() {
		{
			configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
			disable(SerializationFeature.INDENT_OUTPUT);
		}
	};

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderResource.class);

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceOrderHttpHelper _commerceOrderHttpHelper;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private Portal _portal;

}