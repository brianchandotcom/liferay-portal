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

package com.liferay.mobile.device.rules.web.internal.display.context;

import com.liferay.mobile.device.rules.model.MDRAction;
import com.liferay.mobile.device.rules.service.MDRActionLocalServiceUtil;
import com.liferay.mobile.device.rules.util.comparator.ActionCreateDateComparator;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Juergen Kappler
 */
public class MDRActionDisplayContext {

	public MDRActionDisplayContext(
		RenderRequest request, RenderResponse response) {

		_request = request;
		_response = response;
	}

	public SearchContainer getActionSearchContainer() {
		if (_ruleActionSearchContainer != null) {
			return _ruleActionSearchContainer;
		}

		long ruleGroupInstanceId = getGroupInstanceId();

		SearchContainer ruleActionSearchContainer = new SearchContainer(
			_request, getPortletURL(), null,
			"no-actions-are-configured-for-this-device-family");

		ruleActionSearchContainer.setOrderByCol(getOrderByCol());

		String orderByType = getOrderByType();

		boolean orderByAsc = orderByType.equals("asc");

		OrderByComparator<MDRAction> orderByComparator =
			new ActionCreateDateComparator(orderByAsc);

		ruleActionSearchContainer.setOrderByComparator(orderByComparator);

		ruleActionSearchContainer.setOrderByType(orderByType);

		ruleActionSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_response));

		ruleActionSearchContainer.setTotal(
			MDRActionLocalServiceUtil.getActionsCount(ruleGroupInstanceId));

		ruleActionSearchContainer.setResults(
			MDRActionLocalServiceUtil.getActions(
				ruleGroupInstanceId, ruleActionSearchContainer.getStart(),
				ruleActionSearchContainer.getEnd(), orderByComparator));

		_ruleActionSearchContainer = ruleActionSearchContainer;

		return _ruleActionSearchContainer;
	}

	public String getDisplayStyle() {
		if (_displayStyle != null) {
			return _displayStyle;
		}

		_displayStyle = ParamUtil.getString(_request, "displayStyle", "list");

		return _displayStyle;
	}

	public long getGroupInstanceId() {
		if (_groupInstanceId != null) {
			return _groupInstanceId;
		}

		_groupInstanceId = ParamUtil.getLong(_request, "ruleGroupInstanceId");

		return _groupInstanceId;
	}

	public String getOrderByCol() {
		if (_orderByCol != null) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_request, "orderByCol", "create-date");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (_orderByType != null) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(_request, "orderByType", "asc");

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		if (_portletURL != null) {
			return _portletURL;
		}

		String redirect = ParamUtil.getString(_request, "redirect");

		PortletURL portletURL = _response.createRenderURL();

		portletURL.setParameter("mvcPath", "/view_actions.jsp");
		portletURL.setParameter("redirect", redirect);
		portletURL.setParameter(
			"ruleGroupInstanceId", String.valueOf(getGroupInstanceId()));

		_portletURL = portletURL;

		return _portletURL;
	}

	private String _displayStyle;
	private Long _groupInstanceId;
	private String _orderByCol;
	private String _orderByType;
	private PortletURL _portletURL;
	private final RenderRequest _request;
	private final RenderResponse _response;
	private SearchContainer _ruleActionSearchContainer;

}