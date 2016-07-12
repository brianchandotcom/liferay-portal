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

package com.liferay.mobile.device.rules.web.internal.search;

import com.liferay.mobile.device.rules.model.MDRRuleGroup;
import com.liferay.mobile.device.rules.util.comparator.RuleGroupCreateDateComparator;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Edward Han
 */
public class RuleGroupSearch extends SearchContainer<MDRRuleGroup> {

	public static final String EMPTY_RESULTS_MESSAGE =
		"no-device-families-are-configured";

	public static List<String> headerNames = new ArrayList<>();

	static {
		headerNames.add("name");
		headerNames.add("description");
	}

	public RuleGroupSearch(
		PortletRequest portletRequest, PortletURL iteratorURL) {

		super(
			portletRequest, new RuleGroupDisplayTerms(portletRequest),
			new RuleGroupSearchTerms(portletRequest), DEFAULT_CUR_PARAM,
			DEFAULT_DELTA, iteratorURL, headerNames, EMPTY_RESULTS_MESSAGE);

		RuleGroupDisplayTerms displayTerms =
			(RuleGroupDisplayTerms)getDisplayTerms();

		iteratorURL.setParameter(
			RuleGroupDisplayTerms.GROUP_ID,
			String.valueOf(displayTerms.getGroupId()));
		iteratorURL.setParameter(
			RuleGroupDisplayTerms.NAME, displayTerms.getName());

		String orderByCol = ParamUtil.getString(
			portletRequest, "orderByCol", "create-date");
		String orderByType = ParamUtil.getString(
			portletRequest, "orderByType", "asc");

		OrderByComparator<MDRRuleGroup> orderByComparator =
			getOrganizationOrderByComparator(orderByCol, orderByType);

		setOrderByCol(orderByCol);
		setOrderByType(orderByType);
		setOrderByComparator(orderByComparator);
	}

	protected OrderByComparator<MDRRuleGroup> getOrganizationOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		return new RuleGroupCreateDateComparator(orderByAsc);
	}

}