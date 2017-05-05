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

package com.liferay.commerce.product.media.types.web.internal.display.context;

import com.liferay.commerce.product.display.context.util.CPRequestHelper;
import com.liferay.commerce.product.media.types.web.internal.portlet.action.ActionHelper;
import com.liferay.commerce.product.media.types.web.internal.util.CPMediaTypesPortletUtil;
import com.liferay.commerce.product.model.CPMediaType;
import com.liferay.commerce.product.service.CPMediaTypeService;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CPMediaTypeDisplayContext {

	public CPMediaTypeDisplayContext(
		ActionHelper actionHelper, HttpServletRequest httpServletRequest,
		CPMediaTypeService cpMediaTypeService,
		String portalPreferenceNamespace) {

		_actionHelper = actionHelper;
		_httpServletRequest = httpServletRequest;
		_cpMediaTypeService = cpMediaTypeService;
		_portalPreferenceNamespace = portalPreferenceNamespace;

		_portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			_httpServletRequest);

		_cpRequestHelper = new CPRequestHelper(httpServletRequest);

		_liferayPortletRequest = _cpRequestHelper.getLiferayPortletRequest();
		_liferayPortletResponse = _cpRequestHelper.getLiferayPortletResponse();
	}

	public List<Locale> getAvailableLocales() throws PortalException {
		CPMediaType cpMediaType = getCPMediaType();

		if (cpMediaType == null) {
			return Collections.emptyList();
		}

		List<Locale> availableLocales = new ArrayList<>();

		for (String languageId : cpMediaType.getAvailableLanguageIds()) {
			availableLocales.add(LocaleUtil.fromLanguageId(languageId));
		}

		return availableLocales;
	}

	public CPMediaType getCPMediaType() throws PortalException {
		if (_cpMediaType != null) {
			return _cpMediaType;
		}

		_cpMediaType = _actionHelper.getCPMediaType(
			_cpRequestHelper.getRenderRequest());

		return _cpMediaType;
	}

	public long getCPMediaTypeId() throws PortalException {
		CPMediaType cpMediaType = getCPMediaType();

		if (cpMediaType == null) {
			return 0;
		}

		return cpMediaType.getCPMediaTypeId();
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
				_portalPreferenceNamespace, "order-by-col", "priority");
		}
		else {
			boolean saveOrderBy = ParamUtil.getBoolean(
				_httpServletRequest, "saveOrderBy");

			if (saveOrderBy) {
				_portalPreferences.setValue(
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
			_orderByType = _portalPreferences.getValue(
				_portalPreferenceNamespace, "order-by-type", "asc");
		}
		else {
			boolean saveOrderBy = ParamUtil.getBoolean(
				_httpServletRequest, "saveOrderBy");

			if (saveOrderBy) {
				_portalPreferences.setValue(
					_portalPreferenceNamespace, "order-by-type", _orderByType);
			}
		}

		return _orderByType;
	}

	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		String redirect = ParamUtil.getString(_httpServletRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);
		}

		String delta = ParamUtil.getString(_httpServletRequest, "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		String deltaEntry = ParamUtil.getString(
			_httpServletRequest, "deltaEntry");

		if (Validator.isNotNull(deltaEntry)) {
			portletURL.setParameter("deltaEntry", deltaEntry);
		}

		String displayStyle = ParamUtil.getString(
			_httpServletRequest, "displayStyle");

		if (Validator.isNotNull(displayStyle)) {
			portletURL.setParameter("displayStyle", getDisplayStyle());
		}

		String keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		String orderByCol = getOrderByCol();

		if (Validator.isNotNull(orderByCol)) {
			portletURL.setParameter("orderByCol", orderByCol);
		}

		String orderByType = getOrderByType();

		if (Validator.isNotNull(orderByType)) {
			portletURL.setParameter("orderByType", orderByType);
		}

		CPMediaType cpMediaType = getCPMediaType();

		if (cpMediaType != null) {
			portletURL.setParameter(
				"cpMediaTypeId", String.valueOf(getCPMediaTypeId()));
		}

		return portletURL;
	}

	public RowChecker getRowChecker() {
		if (_rowChecker != null) {
			return _rowChecker;
		}

		RowChecker rowChecker = new EmptyOnClickRowChecker(
			_liferayPortletResponse);

		_rowChecker = rowChecker;

		return _rowChecker;
	}

	public long getScopeGroupId() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return themeDisplay.getScopeGroupId();
	}

	public SearchContainer<CPMediaType> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		SearchContainer<CPMediaType> searchContainer = new SearchContainer<>(
			_liferayPortletRequest, getPortletURL(), null, null);

		searchContainer.setEmptyResultsMessage("no-media-types-were-found");

		OrderByComparator<CPMediaType> orderByComparator =
			CPMediaTypesPortletUtil.getCPMediaTypeOrderByComparator(
				getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(getRowChecker());

		int total = _cpMediaTypeService.getCPMediaTypesCount(getScopeGroupId());

		searchContainer.setTotal(total);

		List<CPMediaType> results = _cpMediaTypeService.getCPMediaTypes(
			getScopeGroupId(), searchContainer.getStart(),
			searchContainer.getEnd(), orderByComparator);

		searchContainer.setResults(results);

		_searchContainer = searchContainer;

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
				_portalPreferenceNamespace, "display-style", "list");
		}
		else {
			portalPreferences.setValue(
				_portalPreferenceNamespace, "display-style", displayStyle);

			request.setAttribute(
				WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
		}

		return displayStyle;
	}

	private final ActionHelper _actionHelper;
	private CPMediaType _cpMediaType;
	private final CPMediaTypeService _cpMediaTypeService;
	private final CPRequestHelper _cpRequestHelper;
	private String _displayStyle;
	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _orderByCol;
	private String _orderByType;
	private final String _portalPreferenceNamespace;
	private final PortalPreferences _portalPreferences;
	private RowChecker _rowChecker;
	private SearchContainer _searchContainer;

}