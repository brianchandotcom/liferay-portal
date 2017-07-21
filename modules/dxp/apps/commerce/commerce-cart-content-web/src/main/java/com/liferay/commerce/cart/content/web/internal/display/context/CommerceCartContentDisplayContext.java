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

package com.liferay.commerce.cart.content.web.internal.display.context;

import com.liferay.commerce.cart.constants.CommerceCartConstants;
import com.liferay.commerce.cart.constants.CommerceCartWebKeys;
import com.liferay.commerce.cart.content.web.internal.portlet.configuration.CommerceCartContentMiniPortletInstanceConfiguration;
import com.liferay.commerce.cart.content.web.internal.util.CommerceCartContentPortletUtil;
import com.liferay.commerce.cart.display.context.util.CommerceCartRequestHelper;
import com.liferay.commerce.cart.model.CommerceCart;
import com.liferay.commerce.cart.model.CommerceCartItem;
import com.liferay.commerce.cart.service.CommerceCartItemLocalService;
import com.liferay.commerce.cart.service.CommerceCartLocalService;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPFriendlyURLEntry;
import com.liferay.commerce.product.service.CPFriendlyURLEntryLocalService;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceCartContentDisplayContext {

	public CommerceCartContentDisplayContext(
			HttpServletRequest httpServletRequest,
			CommerceCartItemLocalService commerceCartItemLocalService,
			CommerceCartLocalService commerceCartLocalService,
			CPFriendlyURLEntryLocalService cpFriendlyURLEntryLocalService,
			Portal portal, String portalPreferenceNamespace)
		throws ConfigurationException {

		_httpServletRequest = httpServletRequest;
		_commerceCartItemLocalService = commerceCartItemLocalService;
		_commerceCartLocalService = commerceCartLocalService;
		_cpFriendlyURLEntryLocalService = cpFriendlyURLEntryLocalService;
		_portal = portal;

		portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			_httpServletRequest);

		commerceCartRequestHelper = new CommerceCartRequestHelper(
			httpServletRequest);

		liferayPortletRequest =
			commerceCartRequestHelper.getLiferayPortletRequest();
		liferayPortletResponse =
			commerceCartRequestHelper.getLiferayPortletResponse();

		_portalPreferenceNamespace = portalPreferenceNamespace;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_commerceCartContentMiniPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				CommerceCartContentMiniPortletInstanceConfiguration.class);

		_defaultOrderByCol = "modified-date";
		_defaultOrderByType = "asc";
	}

	public String getCPDefinitionURL(
			CPDefinition cpDefinition, ThemeDisplay themeDisplay)
		throws PortalException {

		long classNameId = _portal.getClassNameId(CPDefinition.class);

		CPFriendlyURLEntry cpFriendlyURLEntry =
			_cpFriendlyURLEntryLocalService.fetchCPFriendlyURLEntry(
				cpDefinition.getGroupId(), cpDefinition.getCompanyId(),
				classNameId, cpDefinition.getCPDefinitionId(),
				themeDisplay.getLanguageId(), true);

		String cpDefinitionURL =
			themeDisplay.getPortalURL() + CPConstants.SEPARATOR_PRODUCT_URL +
				cpFriendlyURLEntry.getUrlTitle();

		return cpDefinitionURL;
	}

	public CommerceCart getCommerceCart() throws PortalException {
		RenderRequest renderRequest =
			commerceCartRequestHelper.getRenderRequest();

		_commerceCart = (CommerceCart)renderRequest.getAttribute(
			CommerceCartWebKeys.COMMERCE_CART);

		if (_commerceCart != null) {
			return _commerceCart;
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceCart.class.getName(), _httpServletRequest);

		long commerceCartId = ParamUtil.getLong(
			renderRequest, "commerceCartId");

		if (commerceCartId > 0) {
			_commerceCart =
				_commerceCartLocalService.getUserCurrentCommerceCart(
					getCommerceCartType(), serviceContext);
		}

		if (_commerceCart != null) {
			renderRequest.setAttribute(
				CommerceCartWebKeys.COMMERCE_CART, _commerceCart);
		}

		return _commerceCart;
	}

	public long getCommerceCartId() throws PortalException {
		CommerceCart commerceCart = getCommerceCart();

		if (commerceCart == null) {
			return 0;
		}

		return commerceCart.getCommerceCartId();
	}

	public int getCommerceCartType() {
		return ParamUtil.getInteger(
			_httpServletRequest, "type",
			CommerceCartConstants.COMMERCE_CART_TYPE_CART);
	}

	public String getDisplayStyle() {
		return
			_commerceCartContentMiniPortletInstanceConfiguration.displayStyle();
	}

	public long getDisplayStyleGroupId() {
		if (_displayStyleGroupId > 0) {
			return _displayStyleGroupId;
		}

		_displayStyleGroupId =
			_commerceCartContentMiniPortletInstanceConfiguration.
				displayStyleGroupId();

		if (_displayStyleGroupId <= 0) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			_displayStyleGroupId = themeDisplay.getScopeGroupId();
		}

		return _displayStyleGroupId;
	}

	public String getOrderByCol() {
		if (_orderByCol != null) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(_httpServletRequest, "orderByCol");

		if (Validator.isNull(_orderByCol)) {
			_orderByCol = portalPreferences.getValue(
				_portalPreferenceNamespace, "order-by-col", _defaultOrderByCol);
		}
		else {
			boolean saveOrderBy = ParamUtil.getBoolean(
				_httpServletRequest, "saveOrderBy");

			if (saveOrderBy) {
				portalPreferences.setValue(
					_portalPreferenceNamespace, "order-by-col", _orderByCol);
			}
		}

		return _orderByCol;
	}

	public String getOrderByType() {
		if (_orderByType != null) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(_httpServletRequest, "orderByType");

		if (Validator.isNull(_orderByType)) {
			_orderByType = portalPreferences.getValue(
				_portalPreferenceNamespace, "order-by-type",
				_defaultOrderByType);
		}
		else {
			boolean saveOrderBy = ParamUtil.getBoolean(
				_httpServletRequest, "saveOrderBy");

			if (saveOrderBy) {
				portalPreferences.setValue(
					_portalPreferenceNamespace, "order-by-type", _orderByType);
			}
		}

		return _orderByType;
	}

	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		String delta = ParamUtil.getString(_httpServletRequest, "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		String deltaEntry = ParamUtil.getString(
			_httpServletRequest, "deltaEntry");

		if (Validator.isNotNull(deltaEntry)) {
			portletURL.setParameter("deltaEntry", deltaEntry);
		}

		String orderByCol = getOrderByCol();

		if (Validator.isNotNull(orderByCol)) {
			portletURL.setParameter("orderByCol", orderByCol);
		}

		String orderByType = getOrderByType();

		if (Validator.isNotNull(orderByType)) {
			portletURL.setParameter("orderByType", orderByType);
		}

		return portletURL;
	}

	public SearchContainer<CommerceCartItem> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null, null);

		_searchContainer.setEmptyResultsMessage("no-cart-items-were-found");

		OrderByComparator<CommerceCartItem> orderByComparator =
			CommerceCartContentPortletUtil.getCommerceCartItemOrderByComparator(
				getOrderByCol(), getOrderByType());

		_searchContainer.setOrderByCol(getOrderByCol());
		_searchContainer.setOrderByComparator(orderByComparator);
		_searchContainer.setOrderByType(getOrderByType());

		int total = _commerceCartItemLocalService.getCommerceCartItemsCount(
			getCommerceCartId());

		_searchContainer.setTotal(total);

		List<CommerceCartItem> results =
			_commerceCartItemLocalService.getCommerceCartItems(
				getCommerceCartId(), _searchContainer.getStart(),
				_searchContainer.getEnd(), orderByComparator);

		_searchContainer.setResults(results);

		return _searchContainer;
	}

	public void setDefaultOrderByCol(String defaultOrderByCol) {
		_defaultOrderByCol = defaultOrderByCol;
	}

	public void setDefaultOrderByType(String defaultOrderByType) {
		_defaultOrderByType = defaultOrderByType;
	}

	protected final CommerceCartRequestHelper commerceCartRequestHelper;
	protected final LiferayPortletRequest liferayPortletRequest;
	protected final LiferayPortletResponse liferayPortletResponse;
	protected final PortalPreferences portalPreferences;

	private CommerceCart _commerceCart;
	private final CommerceCartContentMiniPortletInstanceConfiguration
		_commerceCartContentMiniPortletInstanceConfiguration;
	private final CommerceCartItemLocalService _commerceCartItemLocalService;
	private final CommerceCartLocalService _commerceCartLocalService;
	private final CPFriendlyURLEntryLocalService
		_cpFriendlyURLEntryLocalService;
	private String _defaultOrderByCol;
	private String _defaultOrderByType;
	private long _displayStyleGroupId;
	private final HttpServletRequest _httpServletRequest;
	private String _orderByCol;
	private String _orderByType;
	private final Portal _portal;
	private final String _portalPreferenceNamespace;
	private SearchContainer<CommerceCartItem> _searchContainer;

}