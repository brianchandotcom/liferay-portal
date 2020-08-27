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

package com.liferay.commerce.product.definitions.web.display.context;

import com.liferay.commerce.product.definitions.web.portlet.action.ActionHelper;
import com.liferay.frontend.taglib.servlet.taglib.ManagementBarFilterItem;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 * @author Marco Leo
 */
public abstract class BaseCPDefinitionsSearchContainerDisplayContext<T>
	extends BaseCPDefinitionsDisplayContext {

	public BaseCPDefinitionsSearchContainerDisplayContext(
		ActionHelper actionHelper, HttpServletRequest httpServletRequest,
		String portalPreferenceNamespace) {

		super(actionHelper, httpServletRequest);

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

	public List<ManagementBarFilterItem> getManagementBarStatusFilterItems()
		throws PortalException, PortletException {

		List<ManagementBarFilterItem> managementBarFilterItems =
			new ArrayList<>();

		managementBarFilterItems.add(
			getManagementBarFilterItem(WorkflowConstants.STATUS_ANY));
		managementBarFilterItems.add(
			getManagementBarFilterItem(WorkflowConstants.STATUS_DRAFT));
		managementBarFilterItems.add(
			getManagementBarFilterItem(WorkflowConstants.STATUS_SCHEDULED));
		managementBarFilterItems.add(
			getManagementBarFilterItem(WorkflowConstants.STATUS_APPROVED));
		managementBarFilterItems.add(
			getManagementBarFilterItem(WorkflowConstants.STATUS_EXPIRED));

		return managementBarFilterItems;
	}

	public String getManagementBarStatusFilterValue() {
		return WorkflowConstants.getStatusLabel(getStatus());
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

	@Override
	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = super.getPortletURL();

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

		return portletURL;
	}

	public RowChecker getRowChecker() {
		if (_rowChecker == null) {
			_rowChecker = new EmptyOnClickRowChecker(liferayPortletResponse);
		}

		return _rowChecker;
	}

	public abstract SearchContainer<T> getSearchContainer()
		throws PortalException;

	public int getStatus() {
		if (_status != null) {
			return _status;
		}

		_status = ParamUtil.getInteger(
			httpServletRequest, "status", WorkflowConstants.STATUS_ANY);

		return _status;
	}

	public boolean isSearch() {
		if (Validator.isNotNull(getKeywords())) {
			return true;
		}

		String filterFields = ParamUtil.getString(
			httpServletRequest, "filterFields");

		if (Validator.isNotNull(filterFields)) {
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
		HttpServletRequest httpServletRequest,
		PortalPreferences portalPreferences) {

		String displayStyle = ParamUtil.getString(
			httpServletRequest, "displayStyle");

		if (Validator.isNull(displayStyle)) {
			displayStyle = portalPreferences.getValue(
				_portalPreferenceNamespace, "display-style", "list");
		}
		else {
			portalPreferences.setValue(
				_portalPreferenceNamespace, "display-style", displayStyle);

			httpServletRequest.setAttribute(
				WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
		}

		return displayStyle;
	}

	protected ManagementBarFilterItem getManagementBarFilterItem(int status)
		throws PortalException, PortletException {

		boolean active = false;

		if (status == getStatus()) {
			active = true;
		}

		PortletURL portletURL = PortletURLUtil.clone(
			getPortletURL(), liferayPortletResponse);

		portletURL.setParameter("status", String.valueOf(status));

		return new ManagementBarFilterItem(
			active, WorkflowConstants.getStatusLabel(status),
			portletURL.toString());
	}

	protected SearchContainer<T> searchContainer;

	private String _defaultOrderByCol;
	private String _defaultOrderByType;
	private String _displayStyle;
	private String _keywords;
	private String _orderByCol;
	private String _orderByType;
	private final String _portalPreferenceNamespace;
	private RowChecker _rowChecker;
	private Integer _status;

}