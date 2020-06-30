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
import com.liferay.commerce.order.web.internal.constants.CommerceOrderPortletConstants;
import com.liferay.commerce.order.web.internal.display.context.util.CommerceOrderRequestHelper;
import com.liferay.commerce.order.web.internal.frontend.CommerceOrderDataSetConstants;
import com.liferay.commerce.order.web.internal.search.CommerceOrderDisplayTerms;
import com.liferay.commerce.order.web.internal.security.permission.resource.CommerceOrderPermission;
import com.liferay.commerce.service.CommerceOrderNoteService;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderListDisplayContext {

	public CommerceOrderListDisplayContext(
		CommerceOrderNoteService commerceOrderNoteService,
		RenderRequest renderRequest) {

		_commerceOrderNoteService = commerceOrderNoteService;

		_commerceOrderRequestHelper = new CommerceOrderRequestHelper(
			renderRequest);

		_keywords = ParamUtil.getString(renderRequest, "keywords");
		_showFilter = ParamUtil.getBoolean(renderRequest, "showFilter");
		_activeTab = ParamUtil.getString(
			renderRequest, "activeTab",
			CommerceOrderPortletConstants.NAVIGATION_ITEM_ALL);
	}

	public String getActiveTab() {
		return _activeTab;
	}

	public int getCommerceOrderNotesCount(CommerceOrder commerceOrder)
		throws PortalException {

		if (CommerceOrderPermission.contains(
				_commerceOrderRequestHelper.getPermissionChecker(),
				commerceOrder, ActionKeys.UPDATE_DISCUSSION)) {

			return _commerceOrderNoteService.getCommerceOrderNotesCount(
				commerceOrder.getCommerceOrderId());
		}

		return _commerceOrderNoteService.getCommerceOrderNotesCount(
			commerceOrder.getCommerceOrderId(), false);
	}

	public String getDatasetDisplayKey() {
		if (_activeTab.equals(
				CommerceOrderPortletConstants.NAVIGATION_ITEM_ALL)) {

			return CommerceOrderDataSetConstants.
				COMMERCE_DATA_SET_KEY_ALL_ORDERS;
		}
		else if (_activeTab.equals(
					CommerceOrderPortletConstants.NAVIGATION_ITEM_COMPLETED)) {

			return CommerceOrderDataSetConstants.
				COMMERCE_DATA_SET_KEY_COMPLETED_ORDERS;
		}
		else if (_activeTab.equals(
					CommerceOrderPortletConstants.NAVIGATION_ITEM_OPEN)) {

			return CommerceOrderDataSetConstants.
				COMMERCE_DATA_SET_KEY_OPEN_ORDERS;
		}
		else if (_activeTab.equals(
					CommerceOrderPortletConstants.NAVIGATION_ITEM_PENDING)) {

			return CommerceOrderDataSetConstants.
				COMMERCE_DATA_SET_KEY_PENDING_ORDERS;
		}
		else if (_activeTab.equals(
					CommerceOrderPortletConstants.NAVIGATION_ITEM_PROCESSING)) {

			return CommerceOrderDataSetConstants.
				COMMERCE_DATA_SET_KEY_PROCESSING_ORDERS;
		}

		return StringPool.BLANK;
	}

	public List<NavigationItem> getNavigationItems() {
		if (_navigationItems == null) {
			_initNavigationItems();
		}

		return _navigationItems;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = getSearchURL();

		for (String displayTerm : CommerceOrderDisplayTerms.VALID_TERMS) {
			String paramValue = ParamUtil.getString(
				_commerceOrderRequestHelper.getRequest(), displayTerm);

			if (Validator.isNotNull(paramValue)) {
				portletURL.setParameter(displayTerm, paramValue);
			}
		}

		return portletURL;
	}

	public PortletURL getSearchURL() {
		LiferayPortletResponse liferayPortletResponse =
			_commerceOrderRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("showFilter", String.valueOf(_showFilter));
		portletURL.setParameter("activeTab", _activeTab);

		if (Validator.isNotNull(_keywords)) {
			portletURL.setParameter("keywords", _keywords);
		}

		return portletURL;
	}

	private NavigationItem _buildNavigationItem(String name) {
		NavigationItem navigationItem = new NavigationItem();

		if (_activeTab.equals(name)) {
			navigationItem.setActive(true);
		}

		PortletURL portletURL = getPortletURL();

		portletURL.setParameter("activeTab", name);

		navigationItem.setHref(portletURL);

		navigationItem.setLabel(
			LanguageUtil.get(_commerceOrderRequestHelper.getRequest(), name));

		return navigationItem;
	}

	private void _initNavigationItems() {
		_navigationItems = new ArrayList<>();

		for (String navigationItem :
				CommerceOrderPortletConstants.NAVIGATION_ITEMS) {

			_navigationItems.add(_buildNavigationItem(navigationItem));
		}
	}

	private final String _activeTab;
	private final CommerceOrderNoteService _commerceOrderNoteService;
	private final CommerceOrderRequestHelper _commerceOrderRequestHelper;
	private final String _keywords;
	private List<NavigationItem> _navigationItems;
	private final boolean _showFilter;

}