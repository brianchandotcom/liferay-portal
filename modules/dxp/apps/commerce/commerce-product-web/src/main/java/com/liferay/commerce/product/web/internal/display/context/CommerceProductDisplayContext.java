/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.product.web.internal.display.context;

import com.liferay.commerce.product.model.CommerceProductDefinition;
import com.liferay.commerce.product.service.CommerceProductDefinitionLocalService;
import com.liferay.commerce.product.web.internal.constants.CommerceProductPortletKeys;
import com.liferay.commerce.product.web.internal.constants.CommerceProductWebKeys;
import com.liferay.commerce.product.web.internal.display.context.util.CommerceProductDefinitionRequestHelper;
import com.liferay.commerce.product.web.internal.util.CommerceProductDefinitionsPortletUtil;
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
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

import javax.portlet.ResourceRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceProductDisplayContext {

	public CommerceProductDisplayContext(
			HttpServletRequest request,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			CommerceProductDefinitionLocalService
				commerceProductDefinitionLocalService)
		throws PortalException {

		_request = request;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_commerceProductDefinitionLocalService =
			commerceProductDefinitionLocalService;

		_portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			_request);

		_commerceProductDefinitionRequestHelper =
			new CommerceProductDefinitionRequestHelper(request);

		CommerceProductDefinition commerceProductDefinition =
			getCommerceProductDefinition();

		if (commerceProductDefinition == null) {
			RenderRequest renderRequest =
				_commerceProductDefinitionRequestHelper.getRenderRequest();

			renderRequest.setAttribute(
				WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
		}
	}

	public CommerceProductDefinition getCommerceProductDefinition() {
		if (_commerceProductDefinition != null) {
			return _commerceProductDefinition;
		}

		RenderRequest renderRequest =
			_commerceProductDefinitionRequestHelper.getRenderRequest();

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
			_commerceProductDefinitionRequestHelper.getPortletPreferences(),
			_commerceProductDefinitionRequestHelper.getRenderRequest(),
			"commerceProductDefinitionId");
	}

	public String getDDMStructureKey() {
		if (_ddmStructureKey != null) {
			return _ddmStructureKey;
		}

		_ddmStructureKey = ParamUtil.getString(_request, "ddmStructureKey");

		return _ddmStructureKey;
	}

	public String getDisplayStyle() {
		if (_displayStyle == null) {
			_displayStyle = getDisplayStyle(_request, _portalPreferences);
		}

		return _displayStyle;
	}

	public String getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_request, "keywords");

		return _keywords;
	}

	public String getOrderByCol() {
		if (_orderByCol != null) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(_request, "orderByCol");

		if (Validator.isNull(_orderByCol)) {
			_orderByCol = _portalPreferences.getValue(
				CommerceProductWebKeys.COMMERCE_PRODUCT_DEFINITION,
				"order-by-col", "create-date");
		}
		else {
			boolean saveOrderBy = ParamUtil.getBoolean(_request, "saveOrderBy");

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

		_orderByType = ParamUtil.getString(_request, "orderByType");

		if (Validator.isNull(_orderByType)) {
			_orderByType = _portalPreferences.getValue(
				CommerceProductWebKeys.COMMERCE_PRODUCT_DEFINITION,
				"order-by-type", "desc");
		}
		else {
			boolean saveOrderBy = ParamUtil.getBoolean(_request, "saveOrderBy");

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

		String displayStyle = ParamUtil.getString(_request, "displayStyle");

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

	public SearchContainer getSearchContainer() throws PortalException {
		if (_productSearchContainer != null) {
			return _productSearchContainer;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		SearchContainer productDefinitionSearchContainer = new SearchContainer(
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

		List results =
			_commerceProductDefinitionLocalService.
				getCommerceProductDefinitions(
					themeDisplay.getScopeGroupId(),
					productDefinitionSearchContainer.getStart(),
					productDefinitionSearchContainer.getEnd(),
					orderByComparator);

		productDefinitionSearchContainer.setResults(results);

		_productSearchContainer = productDefinitionSearchContainer;

		return _productSearchContainer;
	}

	public int getStatus() {
		if (_status != null) {
			return _status;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		int defaultStatus = WorkflowConstants.STATUS_APPROVED;

		_status = ParamUtil.getInteger(_request, "status", defaultStatus);

		return _status;
	}

	public boolean isSearch() {
		if (Validator.isNotNull(getKeywords())) {
			return true;
		}

		return false;
	}

	public boolean isShowInfoPanel() {
		if (Validator.isNotNull(getDDMStructureKey())) {
			return false;
		}

		if (isSearch()) {
			return false;
		}

		return true;
	}

	public List<CommerceProductDefinition> getCommerceProductDefinitions(ResourceRequest request)
			throws Exception {

		String[] commerceProductDefinitionIds = ParamUtil.getStringValues(
				request, "rowIdsCommerceProductDefinition");

		List<CommerceProductDefinition> commerceProductDefinitions = new ArrayList<>();

		for (String commerceProductDefinitionId : commerceProductDefinitionIds) {
			CommerceProductDefinition commerceProductDefinition =
				_commerceProductDefinitionLocalService.
					getCommerceProductDefinition(
						Long.parseLong(commerceProductDefinitionId));

			commerceProductDefinitions.add(commerceProductDefinition);
		}

		return commerceProductDefinitions;
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
	private final CommerceProductDefinitionRequestHelper
		_commerceProductDefinitionRequestHelper;
	private String _ddmStructureKey;
	private String _displayStyle;
	private String _keywords;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _orderByCol;
	private String _orderByType;
	private final PortalPreferences _portalPreferences;
	private SearchContainer _productSearchContainer;
	private final HttpServletRequest _request;
	private RowChecker _rowChecker;
	private Integer _status;

}