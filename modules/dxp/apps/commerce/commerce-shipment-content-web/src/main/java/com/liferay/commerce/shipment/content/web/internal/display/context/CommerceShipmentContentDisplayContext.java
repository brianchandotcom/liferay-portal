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

package com.liferay.commerce.shipment.content.web.internal.display.context;

import com.liferay.commerce.constants.CommerceShipmentConstants;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.commerce.model.CommerceShippingEngine;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.service.CommerceShipmentItemService;
import com.liferay.commerce.service.CommerceShipmentService;
import com.liferay.commerce.shipment.content.web.internal.display.context.util.CommerceShipmentContentRequestHelper;
import com.liferay.commerce.util.CommerceShippingEngineRegistry;
import com.liferay.commerce.util.comparator.CommerceShipmentCreateDateComparator;
import com.liferay.commerce.util.comparator.CommerceShipmentItemCreateDateComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.text.DateFormat;
import java.text.Format;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceShipmentContentDisplayContext {

	public CommerceShipmentContentDisplayContext(
		CommerceOrderHttpHelper commerceOrderHttpHelper,
		CommerceShipmentItemService commerceShipmentItemService,
		CommerceShipmentService commerceShipmentService,
		CommerceShippingEngineRegistry commerceShippingEngineRegistry,
		RenderRequest renderRequest) {

		_commerceOrderHttpHelper = commerceOrderHttpHelper;
		_commerceShipmentItemService = commerceShipmentItemService;
		_commerceShipmentService = commerceShipmentService;
		_commerceShippingEngineRegistry = commerceShippingEngineRegistry;

		_commerceShipmentContentRequestHelper =
			new CommerceShipmentContentRequestHelper(renderRequest);

		ThemeDisplay themeDisplay =
			_commerceShipmentContentRequestHelper.getThemeDisplay();

		_commerceShipmentDateFormatDate = FastDateFormatFactoryUtil.getDate(
			DateFormat.MEDIUM, themeDisplay.getLocale(),
			themeDisplay.getTimeZone());
		_commerceShipmentDateFormatTime = FastDateFormatFactoryUtil.getTime(
			DateFormat.MEDIUM, themeDisplay.getLocale(),
			themeDisplay.getTimeZone());

		_commerceShipmentId = ParamUtil.getLong(
			renderRequest, "commerceShipmentId");
	}

	public String getCommerceOrderShippingOptionName(
			CommerceShipment commerceShipment)
		throws PortalException {

		CommerceShippingMethod commerceShippingMethod =
			commerceShipment.getCommerceShippingMethod();

		if (commerceShippingMethod == null) {
			return StringPool.BLANK;
		}

		CommerceShippingEngine commerceShippingEngine =
			_commerceShippingEngineRegistry.getCommerceShippingEngine(
				commerceShippingMethod.getEngineKey());

		return commerceShippingEngine.getCommerceShippingOptionLabel(
			commerceShipment.getShippingOptionName(),
			_commerceShipmentContentRequestHelper.getLocale());
	}

	public CommerceShipment getCommerceShipment() throws PortalException {
		if ((_commerceShipment == null) && (_commerceShipmentId > 0)) {
			_commerceShipment = _commerceShipmentService.getCommerceShipment(
				_commerceShipmentId);
		}

		return _commerceShipment;
	}

	public String getCommerceShipmentExpectedDate(
		CommerceShipment commerceShipment) {

		if (commerceShipment.getExpectedDate() == null) {
			return StringPool.BLANK;
		}

		return _commerceShipmentDateFormatDate.format(
			commerceShipment.getExpectedDate());
	}

	public String getCommerceShipmentExpectedTime(
		CommerceShipment commerceShipment) {

		if (commerceShipment.getExpectedDate() == null) {
			return StringPool.BLANK;
		}

		return _commerceShipmentDateFormatTime.format(
			commerceShipment.getExpectedDate());
	}

	public SearchContainer<CommerceShipmentItem>
		getCommerceShipmentItemsSearchContainer() {

		if (_commerceShipmentItemSearchContainer != null) {
			return _commerceShipmentItemSearchContainer;
		}

		_commerceShipmentItemSearchContainer = new SearchContainer<>(
			_commerceShipmentContentRequestHelper.getLiferayPortletRequest(),
			getPortletURL(), null, "this-shipment-has-no-items");

		int total = _commerceShipmentItemService.getCommerceShipmentItemsCount(
			_commerceShipmentId);

		List<CommerceShipmentItem> results =
			_commerceShipmentItemService.getCommerceShipmentItems(
				_commerceShipmentId,
				_commerceShipmentItemSearchContainer.getStart(),
				_commerceShipmentItemSearchContainer.getEnd(),
				new CommerceShipmentItemCreateDateComparator());

		_commerceShipmentItemSearchContainer.setTotal(total);
		_commerceShipmentItemSearchContainer.setResults(results);

		return _commerceShipmentItemSearchContainer;
	}

	public String getCommerceShipmentShippingDate(
		CommerceShipment commerceShipment) {

		if (commerceShipment.getShippingDate() == null) {
			return StringPool.BLANK;
		}

		return _commerceShipmentDateFormatDate.format(
			commerceShipment.getShippingDate());
	}

	public String getCommerceShipmentShippingMethodName(
			CommerceShipment commerceShipment)
		throws PortalException {

		CommerceShippingMethod commerceShippingMethod =
			commerceShipment.getCommerceShippingMethod();

		if (commerceShippingMethod == null) {
			return StringPool.BLANK;
		}

		return commerceShippingMethod.getName(
			_commerceShipmentContentRequestHelper.getLocale());
	}

	public String getCommerceShipmentShippingTime(
		CommerceShipment commerceShipment) {

		if (commerceShipment.getShippingDate() == null) {
			return StringPool.BLANK;
		}

		return _commerceShipmentDateFormatTime.format(
			commerceShipment.getShippingDate());
	}

	public String getCommerceShipmentStatusLabel(int status) {
		return LanguageUtil.get(
			_commerceShipmentContentRequestHelper.getRequest(),
			CommerceShipmentConstants.getShipmentStatusLabel(status));
	}

	public PortletURL getPortletURL() {
		LiferayPortletResponse liferayPortletResponse =
			_commerceShipmentContentRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		if (_commerceShipmentId > 0) {
			portletURL.setParameter(
				"commerceShipmentId", String.valueOf(_commerceShipmentId));
		}

		return portletURL;
	}

	public SearchContainer<CommerceShipment> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		CommerceContext commerceContext =
			_commerceShipmentContentRequestHelper.getCommerceContext();

		_searchContainer = new SearchContainer<>(
			_commerceShipmentContentRequestHelper.getLiferayPortletRequest(),
			getPortletURL(), null, null);

		_searchContainer.setEmptyResultsMessage("no-shipments-were-found");

		long groupId = _commerceShipmentContentRequestHelper.getScopeGroupId();

		Organization organization = commerceContext.getOrganization();

		if (organization != null) {
			groupId = organization.getGroupId();
		}

		int total = _commerceShipmentService.getCommerceShipmentsCountByGroupId(
			groupId);

		_searchContainer.setTotal(total);

		List<CommerceShipment> results =
			_commerceShipmentService.getCommerceShipmentsByGroupId(
				groupId, _searchContainer.getStart(), _searchContainer.getEnd(),
				new CommerceShipmentCreateDateComparator());

		_searchContainer.setResults(results);

		return _searchContainer;
	}

	public String getViewCommerceOrderDetailsURL(
			CommerceShipmentItem commerceShipmentItem)
		throws PortalException {

		CommerceOrderItem commerceOrderItem =
			commerceShipmentItem.fetchCommerceOrderItem();

		if (commerceOrderItem == null) {
			return StringPool.BLANK;
		}

		PortletURL portletURL =
			_commerceOrderHttpHelper.getCommerceCartPortletURL(
				_commerceShipmentContentRequestHelper.getRequest(),
				commerceOrderItem.getCommerceOrder());

		return portletURL.toString();
	}

	private final CommerceOrderHttpHelper _commerceOrderHttpHelper;
	private CommerceShipment _commerceShipment;
	private final CommerceShipmentContentRequestHelper
		_commerceShipmentContentRequestHelper;
	private final Format _commerceShipmentDateFormatDate;
	private final Format _commerceShipmentDateFormatTime;
	private final long _commerceShipmentId;
	private SearchContainer<CommerceShipmentItem>
		_commerceShipmentItemSearchContainer;
	private final CommerceShipmentItemService _commerceShipmentItemService;
	private final CommerceShipmentService _commerceShipmentService;
	private final CommerceShippingEngineRegistry
		_commerceShippingEngineRegistry;
	private SearchContainer<CommerceShipment> _searchContainer;

}