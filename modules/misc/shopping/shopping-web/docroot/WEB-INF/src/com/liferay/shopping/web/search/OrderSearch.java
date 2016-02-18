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

package com.liferay.shopping.web.search;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.shopping.model.ShoppingOrder;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Brian Wing Shun Chan
 */
public class OrderSearch extends SearchContainer<ShoppingOrder> {

	public static final String EMPTY_RESULTS_MESSAGE = "no-orders-were-found";

	public static List<String> headerNames = new ArrayList<>();

	static {
		headerNames.add("number");
		headerNames.add("date");
		headerNames.add("status");
		headerNames.add("customer");
	}

	public OrderSearch(PortletRequest portletRequest, PortletURL iteratorURL) {
		super(
			portletRequest, new OrderDisplayTerms(portletRequest),
			new OrderSearchTerms(portletRequest), DEFAULT_CUR_PARAM,
			DEFAULT_DELTA, iteratorURL, headerNames, EMPTY_RESULTS_MESSAGE);

		OrderDisplayTerms displayTerms = (OrderDisplayTerms)getDisplayTerms();

		iteratorURL.setParameter(
			OrderDisplayTerms.EMAIL_ADDRESS, displayTerms.getEmailAddress());
		iteratorURL.setParameter(
			OrderDisplayTerms.FIRST_NAME, displayTerms.getFirstName());
		iteratorURL.setParameter(
			OrderDisplayTerms.LAST_NAME, displayTerms.getLastName());
		iteratorURL.setParameter(
			OrderDisplayTerms.NUMBER, displayTerms.getNumber());
		iteratorURL.setParameter(
			OrderDisplayTerms.STATUS, displayTerms.getStatus());
	}

}