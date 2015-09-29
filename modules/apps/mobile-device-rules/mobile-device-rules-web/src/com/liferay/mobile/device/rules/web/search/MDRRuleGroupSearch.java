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

package com.liferay.mobile.device.rules.web.search;

import com.liferay.mobile.device.rules.model.MDRRuleGroup;
import com.liferay.portal.kernel.dao.search.SearchContainer;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Edward Han
 */
public class MDRRuleGroupSearch extends SearchContainer<MDRRuleGroup> {

	public static final String EMPTY_RESULTS_MESSAGE =
		"no-device-families-are-configured";

	public static List<String> headerNames = new ArrayList<>();

	static {
		headerNames.add("name");
		headerNames.add("description");
	}

	public MDRRuleGroupSearch(
		PortletRequest portletRequest, PortletURL iteratorURL) {

		super(
			portletRequest, new MDRRuleGroupDisplayTerms(portletRequest),
			new MDRRuleGroupSearchTerms(portletRequest), DEFAULT_CUR_PARAM,
			DEFAULT_DELTA, iteratorURL, headerNames, EMPTY_RESULTS_MESSAGE);

		MDRRuleGroupDisplayTerms displayTerms =
			(MDRRuleGroupDisplayTerms)getDisplayTerms();

		iteratorURL.setParameter(
			MDRRuleGroupDisplayTerms.GROUP_ID,
			String.valueOf(displayTerms.getGroupId()));
		iteratorURL.setParameter(
			MDRRuleGroupDisplayTerms.NAME, displayTerms.getName());
	}

}