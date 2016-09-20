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

package com.liferay.dynamic.data.lists.form.web.internal.display.context;

import com.liferay.dynamic.data.lists.form.web.internal.display.context.util.DDLFormWebRequestHelper;
import com.liferay.dynamic.data.lists.form.web.internal.search.RecordSetSearch;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.service.DDLRecordSetService;
import com.liferay.dynamic.data.lists.util.comparator.DDLRecordSetModifiedDateComparator;
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
public class DDLFormBrowserDisplayContext {

	public DDLFormBrowserDisplayContext(
		DDLRecordSetService ddlRecordSetService, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_ddlRecordSetService = ddlRecordSetService;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_request = PortalUtil.getHttpServletRequest(_renderRequest);

		_ddlFormWebRequestHelper = new DDLFormWebRequestHelper(_request);
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
			_renderResponse.getNamespace() + "selectDDLForm");

		return _eventName;
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

	public RecordSetSearch getRecordSetSearch() throws PortalException {
		if (_recordSetSearch != null) {
			return _recordSetSearch;
		}

		String displayStyle = getDisplayStyle();

		PortletURL portletURL = getPortletURL();

		portletURL.setParameter("displayStyle", displayStyle);

		RecordSetSearch recordSetSearch = new RecordSetSearch(
			_renderRequest, portletURL);

		String orderByCol = getOrderByCol();
		String orderByType = getOrderByType();

		OrderByComparator<DDLRecordSet> orderByComparator =
			_getDDLRecordSetOrderByComparator(orderByType);

		recordSetSearch.setOrderByCol(orderByCol);
		recordSetSearch.setOrderByComparator(orderByComparator);
		recordSetSearch.setOrderByType(orderByType);

		if (recordSetSearch.isSearch()) {
			recordSetSearch.setEmptyResultsMessage("no-forms-were-found");
		}
		else {
			recordSetSearch.setEmptyResultsMessage("there-are-no-forms");
		}

		List<DDLRecordSet> results = _ddlRecordSetService.search(
			_ddlFormWebRequestHelper.getCompanyId(),
			_ddlFormWebRequestHelper.getScopeGroupId(), getKeywords(),
			DDLRecordSetConstants.SCOPE_FORMS, recordSetSearch.getStart(),
			recordSetSearch.getEnd(), recordSetSearch.getOrderByComparator());

		recordSetSearch.setResults(results);

		recordSetSearch.setTotal(_getRecordSetSearchTotal());

		_recordSetSearch = recordSetSearch;

		return _recordSetSearch;
	}

	public boolean isDisabledManagementBar() {
		if (_getRecordSetSearchTotal() <= 0) {
			return true;
		}

		return false;
	}

	private OrderByComparator<DDLRecordSet> _getDDLRecordSetOrderByComparator(
		String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<DDLRecordSet> orderByComparator =
			new DDLRecordSetModifiedDateComparator(orderByAsc);

		return orderByComparator;
	}

	private int _getRecordSetSearchTotal() {
		if (_recordSetSearchTotal != null) {
			return _recordSetSearchTotal;
		}

		_recordSetSearchTotal = _ddlRecordSetService.searchCount(
			_ddlFormWebRequestHelper.getCompanyId(),
			_ddlFormWebRequestHelper.getScopeGroupId(), getKeywords(),
			DDLRecordSetConstants.SCOPE_FORMS);

		return _recordSetSearchTotal;
	}

	private final DDLFormWebRequestHelper _ddlFormWebRequestHelper;
	private final DDLRecordSetService _ddlRecordSetService;
	private String _displayStyle;
	private String _eventName;
	private String _keywords;
	private String _orderByCol;
	private String _orderByType;
	private RecordSetSearch _recordSetSearch;
	private Integer _recordSetSearchTotal;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;

}