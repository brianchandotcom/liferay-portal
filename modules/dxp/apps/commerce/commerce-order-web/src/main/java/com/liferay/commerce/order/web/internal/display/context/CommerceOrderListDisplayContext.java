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

package com.liferay.commerce.order.web.internal.display.context;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderConstants;
import com.liferay.commerce.order.web.internal.display.context.util.CommerceOrderRequestHelper;
import com.liferay.commerce.order.web.internal.search.CommerceOrderSearch;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.util.CommercePriceFormatter;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.text.DateFormat;
import java.text.Format;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceOrderListDisplayContext {

	public CommerceOrderListDisplayContext(
		CommerceOrderService commerceOrderService,
		CommercePriceFormatter commercePriceFormatter,
		RenderRequest renderRequest) {

		_commerceOrderService = commerceOrderService;
		_commercePriceFormatter = commercePriceFormatter;

		_commerceOrderRequestHelper = new CommerceOrderRequestHelper(
			renderRequest);

		ThemeDisplay themeDisplay =
			_commerceOrderRequestHelper.getThemeDisplay();

		_commerceOrderDateFormatDate = FastDateFormatFactoryUtil.getDate(
			DateFormat.MEDIUM, themeDisplay.getLocale(),
			themeDisplay.getTimeZone());
		_commerceOrderDateFormatTime = FastDateFormatFactoryUtil.getTime(
			DateFormat.MEDIUM, themeDisplay.getLocale(),
			themeDisplay.getTimeZone());
	}

	public String getCommerceOrderDate(CommerceOrder commerceOrder) {
		return _commerceOrderDateFormatDate.format(
			commerceOrder.getCreateDate());
	}

	public String getCommerceOrderTime(CommerceOrder commerceOrder) {
		return _commerceOrderDateFormatTime.format(
			commerceOrder.getCreateDate());
	}

	public String getCommerceOrderValue(CommerceOrder commerceOrder)
		throws PortalException {

		return _commercePriceFormatter.format(
			_commerceOrderRequestHelper.getRequest(), commerceOrder.getTotal());
	}

	public int getOrderStatus() {
		return ParamUtil.getInteger(
			_commerceOrderRequestHelper.getRequest(), "orderStatus",
			_ORDER_STATUSES[0]);
	}

	public Map<Integer, Long> getOrderStatusCounts() throws PortalException {
		Map<Integer, Long> allOrderStatusCounts = new LinkedHashMap<>();

		long groupId = _commerceOrderRequestHelper.getScopeGroupId();

		Map<Integer, Long> orderStatusCounts =
			_commerceOrderService.getCommerceOrdersCount(groupId);

		for (int status : _ORDER_STATUSES) {
			Long count = null;

			if (status == CommerceOrderConstants.ORDER_STATUS_ANY) {
				count = Long.valueOf(
					_commerceOrderService.getCommerceOrdersCount(
						groupId, status));
			}
			else {
				count = orderStatusCounts.getOrDefault(status, Long.valueOf(0));
			}

			allOrderStatusCounts.put(status, count);
		}

		return allOrderStatusCounts;
	}

	public String getOrderStatusLabel(int status, long count) {
		ThemeDisplay themeDisplay =
			_commerceOrderRequestHelper.getThemeDisplay();

		String label = themeDisplay.translate(
			CommerceOrderConstants.getOrderStatusLabel(status));

		if (count > 0) {
			StringBundler sb = new StringBundler(4);

			sb.append(label);
			sb.append(" <span class=\"badge badge-primary\">");
			sb.append(count);
			sb.append("</span>");

			label = sb.toString();
		}

		return label;
	}

	public PortletURL getPortletURL() throws PortalException {
		LiferayPortletResponse liferayPortletResponse =
			_commerceOrderRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"orderStatus", String.valueOf(getOrderStatus()));

		return portletURL;
	}

	public SearchContainer<CommerceOrder> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new CommerceOrderSearch(
			_commerceOrderRequestHelper.getLiferayPortletRequest(),
			getPortletURL());

		int status = getOrderStatus();

		if (status != CommerceOrderConstants.ORDER_STATUS_ANY) {
			HttpServletRequest httpServletRequest =
				_commerceOrderRequestHelper.getRequest();

			String orderStatusLabel = LanguageUtil.get(
				httpServletRequest,
				CommerceOrderConstants.getOrderStatusLabel(status));

			_searchContainer.setEmptyResultsMessage(
				LanguageUtil.format(
					httpServletRequest, "no-x-orders-were-found",
					StringUtil.toLowerCase(orderStatusLabel), false));
		}

		_searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(
				_commerceOrderRequestHelper.getLiferayPortletResponse()));

		long groupId = _commerceOrderRequestHelper.getScopeGroupId();

		int total = _commerceOrderService.getCommerceOrdersCount(
			groupId, status);

		_searchContainer.setTotal(total);

		List<CommerceOrder> results = _commerceOrderService.getCommerceOrders(
			groupId, status, _searchContainer.getStart(),
			_searchContainer.getEnd(), _searchContainer.getOrderByComparator());

		_searchContainer.setResults(results);

		return _searchContainer;
	}

	private static final int[] _ORDER_STATUSES = {
		CommerceOrderConstants.ORDER_STATUS_PENDING,
		CommerceOrderConstants.ORDER_STATUS_ANY,
		CommerceOrderConstants.ORDER_STATUS_CANCELLED,
		CommerceOrderConstants.ORDER_STATUS_PROCESSING,
		CommerceOrderConstants.ORDER_STATUS_COMPLETED
	};

	private final Format _commerceOrderDateFormatDate;
	private final Format _commerceOrderDateFormatTime;
	private final CommerceOrderRequestHelper _commerceOrderRequestHelper;
	private final CommerceOrderService _commerceOrderService;
	private final CommercePriceFormatter _commercePriceFormatter;
	private SearchContainer<CommerceOrder> _searchContainer;

}