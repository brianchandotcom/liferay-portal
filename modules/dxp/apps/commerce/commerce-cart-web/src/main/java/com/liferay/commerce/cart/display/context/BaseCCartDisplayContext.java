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

package com.liferay.commerce.cart.display.context;

import com.liferay.commerce.cart.display.context.util.CCartRequestHelper;
import com.liferay.commerce.cart.internal.portlet.action.ActionHelper;
import com.liferay.commerce.cart.model.CCart;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.*;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletURL;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public abstract class BaseCCartDisplayContext<T> {

	public BaseCCartDisplayContext(
		ActionHelper actionHelper, HttpServletRequest httpServletRequest,
		String portalPreferenceNamespace) {

		this.actionHelper = actionHelper;
		this.httpServletRequest = httpServletRequest;

		portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			this.httpServletRequest);

		cCartRequestHelper = new CCartRequestHelper(httpServletRequest);

		liferayPortletRequest = cCartRequestHelper.getLiferayPortletRequest();
		liferayPortletResponse = cCartRequestHelper.getLiferayPortletResponse();

		_portalPreferenceNamespace = portalPreferenceNamespace;

		_defaultOrderByCol = "modified-date";
		_defaultOrderByType = "asc";
	}

	public String getDisplayStyle() {
		if (_displayStyle == null) {
			_displayStyle = getDisplayStyle(
				httpServletRequest, portalPreferences);
		}

		return _displayStyle;
	}

	public String getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(httpServletRequest, "keywords");

		return _keywords;
	}

	public String getOrderByCol() {
		if (_orderByCol != null) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(httpServletRequest, "orderByCol");

		if (Validator.isNull(_orderByCol)) {
			_orderByCol = portalPreferences.getValue(
				_portalPreferenceNamespace, "order-by-col", _defaultOrderByCol);
		}
		else {
			boolean saveOrderBy = ParamUtil.getBoolean(
				httpServletRequest, "saveOrderBy");

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

		_orderByType = ParamUtil.getString(httpServletRequest, "orderByType");

		if (Validator.isNull(_orderByType)) {
			_orderByType = portalPreferences.getValue(
				_portalPreferenceNamespace, "order-by-type",
				_defaultOrderByType);
		}
		else {
			boolean saveOrderBy = ParamUtil.getBoolean(
				httpServletRequest, "saveOrderBy");

			if (saveOrderBy) {
				portalPreferences.setValue(
					_portalPreferenceNamespace, "order-by-type", _orderByType);
			}
		}

		return _orderByType;
	}

	public CCart getCCart() throws PortalException {
		if (_cCart != null) {
			return _cCart;
		}

		_cCart = actionHelper.getCCart(cCartRequestHelper.getRenderRequest());

		return _cCart;
	}

	public long getCCartId() throws PortalException {
		CCart cCart = getCCart();

		if (cCart == null) {
			return 0;
		}

		return cCart.getCCartId();
	}

	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		String redirect = ParamUtil.getString(httpServletRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);
		}

		CCart cCart = getCCart();

		if (cCart != null) {
			portletURL.setParameter(
				"cCartId", String.valueOf(getCCartId()));
		}

		String delta = ParamUtil.getString(httpServletRequest, "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		String deltaEntry = ParamUtil.getString(
			httpServletRequest, "deltaEntry");

		if (Validator.isNotNull(deltaEntry)) {
			portletURL.setParameter("deltaEntry", deltaEntry);
		}

		String displayStyle = ParamUtil.getString(
			httpServletRequest, "displayStyle");

		if (Validator.isNotNull(displayStyle)) {
			portletURL.setParameter("displayStyle", getDisplayStyle());
		}

		String keywords = ParamUtil.getString(httpServletRequest, "keywords");

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

		portletURL.setParameter("type", String.valueOf(getCCartType()));

		return portletURL;
	}

	public int getCCartType() {
		return ParamUtil.getInteger(httpServletRequest, "type", 0);
	}

	public RowChecker getRowChecker() {
		if (_rowChecker != null) {
			return _rowChecker;
		}

		RowChecker rowChecker = new EmptyOnClickRowChecker(
			liferayPortletResponse);

		_rowChecker = rowChecker;

		return _rowChecker;
	}

	public abstract SearchContainer<T> getSearchContainer()
		throws PortalException;

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

	public void setDefaultOrderByCol(String defaultOrderByCol) {
		_defaultOrderByCol = defaultOrderByCol;
	}

	public void setDefaultOrderByType(String defaultOrderByType) {
		_defaultOrderByType = defaultOrderByType;
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

	protected final ActionHelper actionHelper;
	protected final CCartRequestHelper cCartRequestHelper;
	protected final HttpServletRequest httpServletRequest;
	protected final LiferayPortletRequest liferayPortletRequest;
	protected final LiferayPortletResponse liferayPortletResponse;
	protected final PortalPreferences portalPreferences;
	protected SearchContainer<T> searchContainer;
	private String _defaultOrderByCol;
	private String _defaultOrderByType;
	private String _displayStyle;
	private String _keywords;
	private String _orderByCol;
	private String _orderByType;
	private final String _portalPreferenceNamespace;
	private RowChecker _rowChecker;
	private CCart _cCart;

}