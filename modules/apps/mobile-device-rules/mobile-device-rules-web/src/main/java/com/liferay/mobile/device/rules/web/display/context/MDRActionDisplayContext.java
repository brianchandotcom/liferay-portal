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

package com.liferay.mobile.device.rules.web.display.context;

import com.liferay.mobile.device.rules.service.MDRActionLocalServiceUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
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

		ruleActionSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_response));

		ruleActionSearchContainer.setTotal(
			MDRActionLocalServiceUtil.getActionsCount(ruleGroupInstanceId));

		ruleActionSearchContainer.setResults(
			MDRActionLocalServiceUtil.getActions(
				ruleGroupInstanceId, ruleActionSearchContainer.getStart(),
				ruleActionSearchContainer.getEnd()));

		_ruleActionSearchContainer = ruleActionSearchContainer;

		return _ruleActionSearchContainer;
	}

	public long getGroupInstanceId() {
		if (_groupInstanceId != null) {
			return _groupInstanceId;
		}

		_groupInstanceId = ParamUtil.getLong(_request, "ruleGroupInstanceId");

		return _groupInstanceId;
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

	private Long _groupInstanceId;
	private PortletURL _portletURL;
	private final RenderRequest _request;
	private final RenderResponse _response;
	private SearchContainer _ruleActionSearchContainer;

}