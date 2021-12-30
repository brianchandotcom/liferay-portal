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

package com.liferay.layout.portlets.web.internal.search;

import com.liferay.layout.portlets.web.internal.constants.LayoutsPortletsPortletKeys;
import com.liferay.layout.portlets.web.internal.util.comparator.PortletDisplayNameComparator;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Jorge Ferrer
 */
public class PortletSearch extends SearchContainer<Portlet> {

	public static final String EMPTY_RESULTS_MESSAGE = "no-widgets-were-found";

	public static List<String> headerNames = new ArrayList<String>() {
		{
			add("name");
			add("category");
		}
	};

	public PortletSearch(
		PortletRequest portletRequest, PortletURL iteratorURL) {

		super(
			portletRequest, new PortletDisplayTerms(portletRequest),
			new PortletDisplayTerms(portletRequest), DEFAULT_CUR_PARAM,
			DEFAULT_DELTA, iteratorURL, headerNames, EMPTY_RESULTS_MESSAGE);

		PortletDisplayTerms displayTerms =
			(PortletDisplayTerms)getDisplayTerms();

		iteratorURL.setParameter(
			PortletDisplayTerms.CATEGORIES, displayTerms.getCategories());
		iteratorURL.setParameter(
			PortletDisplayTerms.NAME, displayTerms.getName());

		String orderByCol = SearchOrderByUtil.getOrderByCol(
			portletRequest, LayoutsPortletsPortletKeys.LAYOUT_PORTLETS, "name");

		setOrderByCol(orderByCol);

		String orderByType = SearchOrderByUtil.getOrderByType(
			portletRequest, LayoutsPortletsPortletKeys.LAYOUT_PORTLETS, "asc");

		setOrderByComparator(_getOrderByComparator(orderByCol, orderByType));
		setOrderByType(orderByType);
	}

	private static OrderByComparator<Portlet> _getOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<Portlet> orderByComparator = null;

		if (orderByCol.equals("name")) {
			orderByComparator = new PortletDisplayNameComparator(orderByAsc);
		}

		return orderByComparator;
	}

}