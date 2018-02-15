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

package com.liferay.commerce.product.measurement.unit.web.internal.display.context;

import com.liferay.commerce.product.measurement.unit.web.internal.admin.CPMeasurementUnitAdminModule;
import com.liferay.commerce.product.measurement.unit.web.internal.util.CPMeasurementUnitUtil;
import com.liferay.commerce.product.model.CPMeasurementUnit;
import com.liferay.commerce.product.model.CPMeasurementUnitConstants;
import com.liferay.commerce.product.service.CPMeasurementUnitService;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Alessio Antonio Rendina
 */
public class CPMeasurementUnitsDisplayContext {

	public CPMeasurementUnitsDisplayContext(
		CPMeasurementUnitService cpMeasurementUnitService,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_cpMeasurementUnitService = cpMeasurementUnitService;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public CPMeasurementUnit getCPMeasurementUnit() throws PortalException {
		if (_cpMeasurementUnit != null) {
			return _cpMeasurementUnit;
		}

		long cpMeasurementUnitId = ParamUtil.getLong(
			_renderRequest, "cpMeasurementUnitId");

		if (cpMeasurementUnitId > 0) {
			_cpMeasurementUnit = _cpMeasurementUnitService.getCPMeasurementUnit(
				cpMeasurementUnitId);
		}

		return _cpMeasurementUnit;
	}

	public List<NavigationItem> getNavigationItems() {
		List<NavigationItem> navigationItems = new ArrayList<>();

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String viewDimensionProductMeasurementUnitsURL = getNavigationItemURL(
			"view-all-dimension-product-measurement-units",
			CPMeasurementUnitConstants.TYPE_DIMENSION);
		String viewWeightProductMeasurementUnitsURL = getNavigationItemURL(
			"view-all-weight-product-measurement-units",
			CPMeasurementUnitConstants.TYPE_WEIGHT);

		String toolbarItem = ParamUtil.getString(
			_renderRequest, "toolbarItem",
			"view-all-dimension-product-measurement-units");

		NavigationItem dimensionCPMeasurementUnitsNavigationItem =
			getNavigationItem(
				toolbarItem.equals(
					"view-all-dimension-product-measurement-units"),
				viewDimensionProductMeasurementUnitsURL,
				LanguageUtil.get(themeDisplay.getLocale(), "dimension"));

		NavigationItem weightCPMeasurementUnitsNavigationItem =
			getNavigationItem(
				toolbarItem.equals("view-all-weight-product-measurement-units"),
				viewWeightProductMeasurementUnitsURL,
				LanguageUtil.get(themeDisplay.getLocale(), "weight"));

		navigationItems.add(dimensionCPMeasurementUnitsNavigationItem);
		navigationItems.add(weightCPMeasurementUnitsNavigationItem);

		return navigationItems;
	}

	public String getOrderByCol() {
		return ParamUtil.getString(
			_renderRequest, SearchContainer.DEFAULT_ORDER_BY_COL_PARAM,
			"priority");
	}

	public String getOrderByType() {
		return ParamUtil.getString(
			_renderRequest, SearchContainer.DEFAULT_ORDER_BY_TYPE_PARAM, "asc");
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter(
			"commerceAdminModuleKey", CPMeasurementUnitAdminModule.KEY);
		portletURL.setParameter("orderByCol", getOrderByCol());
		portletURL.setParameter("orderByType", getOrderByType());

		String toolbarItem = ParamUtil.getString(_renderRequest, "toolbarItem");

		if (Validator.isNotNull(toolbarItem)) {
			portletURL.setParameter("toolbarItem", toolbarItem);
		}

		portletURL.setParameter("type", String.valueOf(getType()));

		return portletURL;
	}

	public CPMeasurementUnit getPrimaryCPMeasurementUnit() {
		if (_primaryCPMeasurementUnit != null) {
			return _primaryCPMeasurementUnit;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_primaryCPMeasurementUnit =
			_cpMeasurementUnitService.fetchPrimaryCPMeasurementUnit(
				themeDisplay.getScopeGroupId(), getType());

		return _primaryCPMeasurementUnit;
	}

	public SearchContainer<CPMeasurementUnit> getSearchContainer() {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String emptyResultsMessage = "there-are-no-measurement-units";

		_searchContainer = new SearchContainer<>(
			_renderRequest, getPortletURL(), null, emptyResultsMessage);

		String orderByCol = getOrderByCol();
		String orderByType = getOrderByType();

		OrderByComparator<CPMeasurementUnit> orderByComparator =
			CPMeasurementUnitUtil.getCPMeasurementUnitOrderByComparator(
				orderByCol, orderByType);

		_searchContainer.setOrderByCol(orderByCol);
		_searchContainer.setOrderByComparator(orderByComparator);
		_searchContainer.setOrderByType(orderByType);
		_searchContainer.setRowChecker(getRowChecker());

		int total = _cpMeasurementUnitService.getCPMeasurementUnitsCount(
			themeDisplay.getScopeGroupId(), getType());

		List<CPMeasurementUnit> results =
			_cpMeasurementUnitService.getCPMeasurementUnits(
				themeDisplay.getScopeGroupId(), getType(),
				_searchContainer.getStart(), _searchContainer.getEnd(),
				orderByComparator);

		_searchContainer.setTotal(total);
		_searchContainer.setResults(results);

		return _searchContainer;
	}

	public int getType() {
		return ParamUtil.getInteger(
			_renderRequest, "type", CPMeasurementUnitConstants.TYPE_DIMENSION);
	}

	protected NavigationItem getNavigationItem(
		boolean active, String href, String label) {

		NavigationItem navigationItem = new NavigationItem();

		navigationItem.setActive(active);
		navigationItem.setHref(href);
		navigationItem.setLabel(label);

		return navigationItem;
	}

	protected String getNavigationItemURL(String toolbarItem, int type) {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/view.jsp");
		portletURL.setParameter(
			"commerceAdminModuleKey", CPMeasurementUnitAdminModule.KEY);
		portletURL.setParameter("toolbarItem", toolbarItem);
		portletURL.setParameter("type", String.valueOf(type));

		return portletURL.toString();
	}

	protected RowChecker getRowChecker() {
		if (_rowChecker == null) {
			_rowChecker = new EmptyOnClickRowChecker(_renderResponse);
		}

		return _rowChecker;
	}

	private CPMeasurementUnit _cpMeasurementUnit;
	private final CPMeasurementUnitService _cpMeasurementUnitService;
	private CPMeasurementUnit _primaryCPMeasurementUnit;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private RowChecker _rowChecker;
	private SearchContainer<CPMeasurementUnit> _searchContainer;

}