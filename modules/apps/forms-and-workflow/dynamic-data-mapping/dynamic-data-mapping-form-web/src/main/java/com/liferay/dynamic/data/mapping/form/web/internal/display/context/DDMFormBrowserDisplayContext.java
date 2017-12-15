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

package com.liferay.dynamic.data.mapping.form.web.internal.display.context;

import com.liferay.dynamic.data.mapping.form.web.internal.display.context.util.DDMFormWebRequestHelper;
import com.liferay.dynamic.data.mapping.form.web.internal.search.FormInstanceSearch;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceService;
import com.liferay.dynamic.data.mapping.util.comparator.DDMFormInstanceModifiedDateComparator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class DDMFormBrowserDisplayContext {

	public DDMFormBrowserDisplayContext(
		DDMFormInstanceService formInstanceService, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_formInstanceService = formInstanceService;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_request = PortalUtil.getHttpServletRequest(_renderRequest);

		_formWebRequestHelper = new DDMFormWebRequestHelper(_request);
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		_displayStyle = ParamUtil.getString(_request, "displayStyle", "list");

		return _displayStyle;
	}

	public String getEventName() {
		if (Validator.isNotNull(_eventName)) {
			return _eventName;
		}

		_eventName = ParamUtil.getString(
			_request, "eventName",
			_renderResponse.getNamespace() + "selectDDMForm");

		return _eventName;
	}

	public FormInstanceSearch getFormInstanceSearch() throws PortalException {
		if (_formInstanceSearch != null) {
			return _formInstanceSearch;
		}

		String displayStyle = getDisplayStyle();

		PortletURL portletURL = getPortletURL();

		portletURL.setParameter("displayStyle", displayStyle);

		FormInstanceSearch formInstanceSearch = new FormInstanceSearch(
			_renderRequest, portletURL);

		String orderByCol = getOrderByCol();
		String orderByType = getOrderByType();

		OrderByComparator<DDMFormInstance> orderByComparator =
			_getDDMFormInstanceOrderByComparator(orderByType);

		formInstanceSearch.setOrderByCol(orderByCol);
		formInstanceSearch.setOrderByComparator(orderByComparator);
		formInstanceSearch.setOrderByType(orderByType);

		if (formInstanceSearch.isSearch()) {
			formInstanceSearch.setEmptyResultsMessage("no-forms-were-found");
		}
		else {
			formInstanceSearch.setEmptyResultsMessage("there-are-no-forms");
		}

		List<DDMFormInstance> results = _formInstanceService.search(
			_formWebRequestHelper.getCompanyId(),
			_formWebRequestHelper.getScopeGroupId(), getKeywords(),
			formInstanceSearch.getStart(), formInstanceSearch.getEnd(),
			formInstanceSearch.getOrderByComparator());

		formInstanceSearch.setResults(results);

		formInstanceSearch.setTotal(_getFormInstanceSearchTotal());

		_formInstanceSearch = formInstanceSearch;

		return _formInstanceSearch;
	}

	public String getKeywords() {
		if (Validator.isNotNull(_keywords)) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_request, "keywords");

		return _keywords;
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_request, "orderByCol", "modified-date");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(_request, "orderByType", "asc");

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/browser/view.jsp");
		portletURL.setParameter("displayStyle", getDisplayStyle());
		portletURL.setParameter("eventName", getEventName());
		portletURL.setParameter("orderByCol", getOrderByCol());
		portletURL.setParameter("orderByType", getOrderByType());

		if (Validator.isNotNull(getKeywords())) {
			portletURL.setParameter("keywords", getKeywords());
		}

		return portletURL;
	}

	public boolean isDisabledManagementBar() {
		if (_getFormInstanceSearchTotal() <= 0) {
			return true;
		}

		return false;
	}

	private OrderByComparator<DDMFormInstance>
		_getDDMFormInstanceOrderByComparator(String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<DDMFormInstance> orderByComparator =
			new DDMFormInstanceModifiedDateComparator(orderByAsc);

		return orderByComparator;
	}

	private int _getFormInstanceSearchTotal() {
		if (_formInstanceSearchTotal != null) {
			return _formInstanceSearchTotal;
		}

		_formInstanceSearchTotal = _formInstanceService.searchCount(
			_formWebRequestHelper.getCompanyId(),
			_formWebRequestHelper.getScopeGroupId(), getKeywords());

		return _formInstanceSearchTotal;
	}

	private String _displayStyle;
	private String _eventName;
	private FormInstanceSearch _formInstanceSearch;
	private Integer _formInstanceSearchTotal;
	private final DDMFormInstanceService _formInstanceService;
	private final DDMFormWebRequestHelper _formWebRequestHelper;
	private String _keywords;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;

}