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

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.constants.CommerceShipmentConstants;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.commerce.model.CommerceShippingEngine;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceShipmentItemLocalService;
import com.liferay.commerce.service.CommerceShipmentLocalService;
import com.liferay.commerce.shipment.content.web.internal.display.context.helper.CommerceShipmentContentRequestHelper;
import com.liferay.commerce.util.CommerceShippingEngineRegistry;
import com.liferay.commerce.util.comparator.CommerceShipmentCreateDateComparator;
import com.liferay.commerce.util.comparator.CommerceShipmentItemCreateDateComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.text.DateFormat;
import java.text.Format;

import java.util.Collections;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceShipmentContentDisplayContext {

	public CommerceShipmentContentDisplayContext(
		CommerceChannelLocalService commerceChannelLocalService,
		CommerceOrderHttpHelper commerceOrderHttpHelper,
		CommerceShipmentItemLocalService commerceShipmentItemLocalService,
		CommerceShipmentLocalService commerceShipmentLocalService,
		CommerceShippingEngineRegistry commerceShippingEngineRegistry,
		RenderRequest renderRequest) {

		_commerceChannelLocalService = commerceChannelLocalService;
		_commerceOrderHttpHelper = commerceOrderHttpHelper;
		_commerceShipmentItemLocalService = commerceShipmentItemLocalService;
		_commerceShipmentLocalService = commerceShipmentLocalService;
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
			_commerceShipment =
				_commerceShipmentLocalService.getCommerceShipment(
					_commerceShipmentId);
		}

		return _commerceShipment;
	}

	public long getCommerceShipmentAccountId(CommerceShipment commerceShipment)
		throws PortalException {

		return commerceShipment.getCommerceAccountId();
	}

	public String getCommerceShipmentAccountName(
			CommerceShipment commerceShipment)
		throws PortalException {

		CommerceAccount commerceAccount = commerceShipment.getCommerceAccount();

		return commerceAccount.getName();
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

		_commerceShipmentItemSearchContainer.setResultsAndTotal(
			() -> _commerceShipmentItemLocalService.getCommerceShipmentItems(
				_commerceShipmentId,
				_commerceShipmentItemSearchContainer.getStart(),
				_commerceShipmentItemSearchContainer.getEnd(),
				new CommerceShipmentItemCreateDateComparator()),
			_commerceShipmentItemLocalService.getCommerceShipmentItemsCount(
				_commerceShipmentId));

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

		_searchContainer = new SearchContainer<>(
			_commerceShipmentContentRequestHelper.getLiferayPortletRequest(),
			getPortletURL(), null, "no-shipments-were-found");

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.fetchCommerceChannelBySiteGroupId(
				_commerceShipmentContentRequestHelper.getScopeGroupId());

		if (commerceChannel != null) {
			_searchContainer.setResultsAndTotal(
				() -> _commerceShipmentLocalService.getCommerceShipments(
					new long[] {commerceChannel.getGroupId()},
					_searchContainer.getStart(), _searchContainer.getEnd(),
					new CommerceShipmentCreateDateComparator()),
				_commerceShipmentLocalService.getCommerceShipmentsCount(
					new long[] {commerceChannel.getGroupId()}));
		}
		else {
			_searchContainer.setResultsAndTotal(Collections::emptyList, 0);
		}

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

	private final CommerceChannelLocalService _commerceChannelLocalService;
	private final CommerceOrderHttpHelper _commerceOrderHttpHelper;
	private CommerceShipment _commerceShipment;
	private final CommerceShipmentContentRequestHelper
		_commerceShipmentContentRequestHelper;
	private final Format _commerceShipmentDateFormatDate;
	private final Format _commerceShipmentDateFormatTime;
	private final long _commerceShipmentId;
	private final CommerceShipmentItemLocalService
		_commerceShipmentItemLocalService;
	private SearchContainer<CommerceShipmentItem>
		_commerceShipmentItemSearchContainer;
	private final CommerceShipmentLocalService _commerceShipmentLocalService;
	private final CommerceShippingEngineRegistry
		_commerceShippingEngineRegistry;
	private SearchContainer<CommerceShipment> _searchContainer;

}