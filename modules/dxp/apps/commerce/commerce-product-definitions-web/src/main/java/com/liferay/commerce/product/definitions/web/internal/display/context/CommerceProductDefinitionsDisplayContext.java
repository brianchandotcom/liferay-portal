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

package com.liferay.commerce.product.definitions.web.internal.display.context;

import com.liferay.commerce.product.definitions.web.internal.constants.CommerceProductPortletKeys;
import com.liferay.commerce.product.definitions.web.internal.constants.CommerceProductWebKeys;
import com.liferay.commerce.product.definitions.web.internal.display.context.util.CommerceProductRequestHelper;
import com.liferay.commerce.product.definitions.web.internal.util.CommerceProductDefinitionsPortletUtil;
import com.liferay.commerce.product.model.CommerceProductDefinition;
import com.liferay.commerce.product.service.CommerceProductDefinitionLocalService;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceProductDefinitionsDisplayContext {

	public CommerceProductDefinitionsDisplayContext(
			HttpServletRequest httpServletRequest,
			CommerceProductDefinitionLocalService
				commerceProductDefinitionLocalService)
		throws PortalException {

		_httpServletRequest = httpServletRequest;

		_commerceProductRequestHelper = new CommerceProductRequestHelper(
			httpServletRequest);

		_liferayPortletRequest =
			_commerceProductRequestHelper.getLiferayPortletRequest();
		_liferayPortletResponse =
			_commerceProductRequestHelper.getLiferayPortletResponse();

		_commerceProductDefinitionLocalService =
			commerceProductDefinitionLocalService;

		_portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			_httpServletRequest);

		CommerceProductDefinition commerceProductDefinition =
			getCommerceProductDefinition();

		if (commerceProductDefinition == null) {
			RenderRequest renderRequest =
				_commerceProductRequestHelper.getRenderRequest();

			renderRequest.setAttribute(
				WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
		}
	}

	public CommerceProductDefinition getCommerceProductDefinition() {
		if (_commerceProductDefinition != null) {
			return _commerceProductDefinition;
		}

		RenderRequest renderRequest =
			_commerceProductRequestHelper.getRenderRequest();

		_commerceProductDefinition =
			(CommerceProductDefinition)renderRequest.getAttribute(
				CommerceProductWebKeys.COMMERCE_PRODUCT_DEFINITION);

		if (_commerceProductDefinition != null) {
			return _commerceProductDefinition;
		}

		_commerceProductDefinition =
			_commerceProductDefinitionLocalService.
				fetchCommerceProductDefinition(
					getCommerceProductDefinitionId());

		return _commerceProductDefinition;
	}

	public long getCommerceProductDefinitionId() {
		return PrefsParamUtil.getLong(
			_commerceProductRequestHelper.getPortletPreferences(),
			_commerceProductRequestHelper.getRenderRequest(),
			"commerceProductDefinitionId");
	}

	public String getDisplayStyle() {
		if (_displayStyle == null) {
			_displayStyle = getDisplayStyle(
				_httpServletRequest, _portalPreferences);
		}

		return _displayStyle;
	}

	public String getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		return _keywords;
	}

	public String getOrderByCol() {
		if (_orderByCol != null) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(_httpServletRequest, "orderByCol");

		if (Validator.isNull(_orderByCol)) {
			_orderByCol = _portalPreferences.getValue(
				CommerceProductWebKeys.COMMERCE_PRODUCT_DEFINITION,
				"order-by-col", "create-date");
		}
		else {
			boolean saveOrderBy = ParamUtil.getBoolean(
				_httpServletRequest, "saveOrderBy");

			if (saveOrderBy) {
				_portalPreferences.setValue(
					CommerceProductWebKeys.COMMERCE_PRODUCT_DEFINITION,
					"order-by-col", _orderByCol);
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
			_orderByType = _portalPreferences.getValue(
				CommerceProductWebKeys.COMMERCE_PRODUCT_DEFINITION,
				"order-by-type", "desc");
		}
		else {
			boolean saveOrderBy = ParamUtil.getBoolean(
				_httpServletRequest, "saveOrderBy");

			if (saveOrderBy) {
				_portalPreferences.setValue(
					CommerceProductWebKeys.COMMERCE_PRODUCT_DEFINITION,
					"order-by-type", _orderByType);
			}
		}

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		String displayStyle = ParamUtil.getString(
			_httpServletRequest, "displayStyle");

		if (Validator.isNotNull(displayStyle)) {
			portletURL.setParameter("displayStyle", getDisplayStyle());
		}

		return portletURL;
	}

	public RowChecker getRowChecker() {
		if (_rowChecker != null) {
			return _rowChecker;
		}

		RowChecker rowChecker = new EmptyOnClickRowChecker(
			_liferayPortletResponse);

		rowChecker.setRowIds("rowIdsCommerceProductDefinition");

		_rowChecker = rowChecker;

		return _rowChecker;
	}

	public SearchContainer<CommerceProductDefinition> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		SearchContainer<CommerceProductDefinition>
			productDefinitionSearchContainer = new SearchContainer<>(
				_liferayPortletRequest, getPortletURL(), null, null);

		OrderByComparator<CommerceProductDefinition> orderByComparator =
			CommerceProductDefinitionsPortletUtil.
				getCommerceProductDefinitionOrderByComparator(
					getOrderByCol(), getOrderByType());

		productDefinitionSearchContainer.setOrderByCol(getOrderByCol());
		productDefinitionSearchContainer.setOrderByComparator(
			orderByComparator);
		productDefinitionSearchContainer.setOrderByType(getOrderByType());

		productDefinitionSearchContainer.setRowChecker(getRowChecker());

		int total =
			_commerceProductDefinitionLocalService.
				getCommerceProductDefinitionsCount(
					themeDisplay.getScopeGroupId());

		productDefinitionSearchContainer.setTotal(total);

		List<CommerceProductDefinition> results =
			_commerceProductDefinitionLocalService.
				getCommerceProductDefinitions(
					themeDisplay.getScopeGroupId(),
					productDefinitionSearchContainer.getStart(),
					productDefinitionSearchContainer.getEnd(),
					orderByComparator);

		productDefinitionSearchContainer.setResults(results);

		_searchContainer = productDefinitionSearchContainer;

		return _searchContainer;
	}

	public boolean isSearch() {
		if (Validator.isNotNull(getKeywords())) {
			return true;
		}

		return false;
	}

	public boolean isShowInfoPanel() {
		if (isSearch()) {
			return false;
		}

		return true;
	}

	protected String getDisplayStyle(
		HttpServletRequest request, PortalPreferences portalPreferences) {

		String displayStyle = ParamUtil.getString(request, "displayStyle");

		if (Validator.isNull(displayStyle)) {
			displayStyle = portalPreferences.getValue(
				CommerceProductPortletKeys.COMMERCE_PRODUCT_DEFINITIONS,
				"display-style", "list");
		}
		else {
			portalPreferences.setValue(
				CommerceProductPortletKeys.COMMERCE_PRODUCT_DEFINITIONS,
				"display-style", displayStyle);

			request.setAttribute(
				WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
		}

		return displayStyle;
	}

	private CommerceProductDefinition _commerceProductDefinition;
	private final CommerceProductDefinitionLocalService
		_commerceProductDefinitionLocalService;
	private final CommerceProductRequestHelper _commerceProductRequestHelper;
	private String _displayStyle;
	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _orderByCol;
	private String _orderByType;
	private final PortalPreferences _portalPreferences;
	private RowChecker _rowChecker;
	private SearchContainer<CommerceProductDefinition> _searchContainer;

}