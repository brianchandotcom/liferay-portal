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
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceOrderNote;
import com.liferay.commerce.model.CommercePaymentMethod;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.order.web.internal.display.context.util.CommerceOrderRequestHelper;
import com.liferay.commerce.order.web.internal.search.CommerceOrderItemSearch;
import com.liferay.commerce.order.web.internal.search.CommerceOrderItemSearchTerms;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceOrderNoteService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.util.CommercePriceFormatter;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.text.DateFormat;
import java.text.Format;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderDetailDisplayContext {

	public CommerceOrderDetailDisplayContext(
			CommerceOrderService commerceOrderService,
			CommerceOrderItemService commerceOrderItemService,
			CommerceOrderNoteService commerceOrderNoteService,
			CommercePriceFormatter commercePriceFormatter,
			RenderRequest renderRequest)
		throws PortalException {

		_commerceOrderService = commerceOrderService;
		_commerceOrderItemService = commerceOrderItemService;
		_commerceOrderNoteService = commerceOrderNoteService;
		_commercePriceFormatter = commercePriceFormatter;

		long commerceOrderId = ParamUtil.getLong(
			renderRequest, "commerceOrderId");

		if (commerceOrderId > 0) {
			_commerceOrder = _commerceOrderService.getCommerceOrder(
				commerceOrderId);
		}
		else {
			_commerceOrder = null;
		}

		_commerceOrderRequestHelper = new CommerceOrderRequestHelper(
			renderRequest);

		ThemeDisplay themeDisplay =
			_commerceOrderRequestHelper.getThemeDisplay();

		_commerceOrderDateFormatDateTime =
			FastDateFormatFactoryUtil.getDateTime(
				DateFormat.SHORT, DateFormat.SHORT, themeDisplay.getLocale(),
				themeDisplay.getTimeZone());
	}

	public CommerceOrder getCommerceOrder() {
		return _commerceOrder;
	}

	public String getCommerceOrderCustomerName() throws PortalException {
		if (_commerceOrder == null) {
			return StringPool.BLANK;
		}

		User orderUser = _commerceOrder.getOrderUser();

		return orderUser.getFullName();
	}

	public String getCommerceOrderDateTime() {
		if (_commerceOrder == null) {
			return StringPool.BLANK;
		}

		return _commerceOrderDateFormatDateTime.format(
			_commerceOrder.getCreateDate());
	}

	public long getCommerceOrderId() {
		if (_commerceOrder == null) {
			return 0;
		}

		return _commerceOrder.getCommerceOrderId();
	}

	public String getCommerceOrderItemPrice(CommerceOrderItem commerceOrderItem)
		throws PortalException {

		return _commercePriceFormatter.format(
			_commerceOrderRequestHelper.getRequest(),
			commerceOrderItem.getPrice());
	}

	public List<CommerceOrderNote> getCommerceOrderNotes()
		throws PortalException {

		long commerceOrderId = getCommerceOrderId();

		if (commerceOrderId <= 0) {
			return Collections.emptyList();
		}

		return _commerceOrderNoteService.getCommerceOrderNotes(
			commerceOrderId, false);
	}

	public int getCommerceOrderNotesCount() throws PortalException {
		return _commerceOrderNoteService.getCommerceOrderNotesCount(
			getCommerceOrderId(), false);
	}

	public String getCommerceOrderPaymentMethodName() throws PortalException {
		if (_commerceOrder == null) {
			return StringPool.BLANK;
		}

		CommercePaymentMethod commercePaymentMethod =
			_commerceOrder.getCommercePaymentMethod();

		if (commercePaymentMethod == null) {
			return StringPool.BLANK;
		}

		String name = commercePaymentMethod.getName(
			_commerceOrderRequestHelper.getLocale());

		if (!commercePaymentMethod.isActive()) {
			StringBundler sb = new StringBundler(4);

			sb.append(name);
			sb.append(" (");
			sb.append(
				LanguageUtil.get(
					_commerceOrderRequestHelper.getRequest(), "inactive"));
			sb.append(CharPool.CLOSE_PARENTHESIS);

			name = sb.toString();
		}

		return name;
	}

	public String getCommerceOrderShippingDuration() {
		return StringPool.BLANK;
	}

	public String getCommerceOrderShippingMethodName() throws PortalException {
		if (_commerceOrder == null) {
			return StringPool.BLANK;
		}

		CommerceShippingMethod commerceShippingMethod =
			_commerceOrder.getCommerceShippingMethod();

		if (commerceShippingMethod == null) {
			return StringPool.BLANK;
		}

		return commerceShippingMethod.getName(
			_commerceOrderRequestHelper.getLocale());
	}

	public String getCommerceOrderShippingOptionName() {
		if (_commerceOrder == null) {
			return StringPool.BLANK;
		}

		return _commerceOrder.getShippingOptionName();
	}

	public String getCommerceOrderStatusLabel() {
		if (_commerceOrder == null) {
			return StringPool.BLANK;
		}

		int status = _commerceOrder.getStatus();

		return LanguageUtil.get(
			_commerceOrderRequestHelper.getRequest(),
			CommerceOrderConstants.getOrderStatusLabel(status));
	}

	public String getCommerceOrderTotal() throws PortalException {
		if (_commerceOrder == null) {
			return StringPool.BLANK;
		}

		return _commercePriceFormatter.format(
			_commerceOrderRequestHelper.getRequest(),
			_commerceOrder.getTotal());
	}

	public PortletURL getPortletURL() {
		LiferayPortletResponse liferayPortletResponse =
			_commerceOrderRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "viewCommerceOrderDetail");
		portletURL.setParameter(
			"commerceOrderId", String.valueOf(getCommerceOrderId()));

		return portletURL;
	}

	public SearchContainer<CommerceOrderItem> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new CommerceOrderItemSearch(
			_commerceOrderRequestHelper.getLiferayPortletRequest(),
			getPortletURL());

		CommerceOrderItemSearchTerms commerceOrderItemSearchTerms =
			(CommerceOrderItemSearchTerms)_searchContainer.getSearchTerms();

		Sort sort = SortFactoryUtil.getSort(
			CommerceOrderItem.class, _searchContainer.getOrderByCol(),
			_searchContainer.getOrderByType());

		BaseModelSearchResult<CommerceOrderItem> baseModelSearchResult =
			_commerceOrderItemService.search(
				getCommerceOrderId(),
				commerceOrderItemSearchTerms.getKeywords(),
				_searchContainer.getStart(), _searchContainer.getEnd(), sort);

		_searchContainer.setTotal(baseModelSearchResult.getLength());
		_searchContainer.setResults(baseModelSearchResult.getBaseModels());

		return _searchContainer;
	}

	private final CommerceOrder _commerceOrder;
	private final Format _commerceOrderDateFormatDateTime;
	private final CommerceOrderItemService _commerceOrderItemService;
	private final CommerceOrderNoteService _commerceOrderNoteService;
	private final CommerceOrderRequestHelper _commerceOrderRequestHelper;
	private final CommerceOrderService _commerceOrderService;
	private final CommercePriceFormatter _commercePriceFormatter;
	private SearchContainer<CommerceOrderItem> _searchContainer;

}