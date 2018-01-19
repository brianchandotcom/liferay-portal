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

package com.liferay.commerce.order.web.internal.search;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.util.comparator.CommerceOrderCreateDateComparator;
import com.liferay.commerce.util.comparator.CommerceOrderIdComparator;
import com.liferay.commerce.util.comparator.CommerceOrderTotalComparator;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceOrderSearch extends SearchContainer<CommerceOrder> {

	public static final String EMPTY_RESULTS_MESSAGE = "no-orders-were-found";

	public static List<String> headerNames = new ArrayList<>();
	public static Map<String, String> orderableHeaders = new LinkedHashMap<>();

	static {
		headerNames.add("order-date");
		headerNames.add("order-status");
		headerNames.add("customer-name");
		headerNames.add("order-id");
		headerNames.add("order-value");
		headerNames.add("notes");

		orderableHeaders.put("createDate", "order-date");
		orderableHeaders.put("commerceOrderId", "order-id");
		orderableHeaders.put("total", "order-value");
	}

	public CommerceOrderSearch(
		PortletRequest portletRequest, PortletURL iteratorURL) {

		super(
			portletRequest, new CommerceOrderDisplayTerms(portletRequest),
			new CommerceOrderDisplayTerms(portletRequest), DEFAULT_CUR_PARAM,
			DEFAULT_DELTA, iteratorURL, headerNames, EMPTY_RESULTS_MESSAGE);

		CommerceOrderDisplayTerms commerceOrderDisplayTerms =
			(CommerceOrderDisplayTerms)getDisplayTerms();

		iteratorURL.setParameter(
			CommerceOrderDisplayTerms.END_CREATE_DATE_DAY,
			String.valueOf(commerceOrderDisplayTerms.getEndCreateDateDay()));
		iteratorURL.setParameter(
			CommerceOrderDisplayTerms.END_CREATE_DATE_MONTH,
			String.valueOf(commerceOrderDisplayTerms.getEndCreateDateMonth()));
		iteratorURL.setParameter(
			CommerceOrderDisplayTerms.END_CREATE_DATE_YEAR,
			String.valueOf(commerceOrderDisplayTerms.getEndCreateDateYear()));
		iteratorURL.setParameter(
			CommerceOrderDisplayTerms.ORGANIZATION_ID,
			String.valueOf(commerceOrderDisplayTerms.getOrganizationId()));
		iteratorURL.setParameter(
			CommerceOrderDisplayTerms.START_CREATE_DATE_DAY,
			String.valueOf(commerceOrderDisplayTerms.getStartCreateDateDay()));
		iteratorURL.setParameter(
			CommerceOrderDisplayTerms.START_CREATE_DATE_MONTH,
			String.valueOf(
				commerceOrderDisplayTerms.getStartCreateDateMonth()));
		iteratorURL.setParameter(
			CommerceOrderDisplayTerms.START_CREATE_DATE_YEAR,
			String.valueOf(commerceOrderDisplayTerms.getStartCreateDateYear()));

		try {
			PortalPreferences preferences =
				PortletPreferencesFactoryUtil.getPortalPreferences(
					portletRequest);

			String orderByCol = ParamUtil.getString(
				portletRequest, "orderByCol");
			String orderByType = ParamUtil.getString(
				portletRequest, "orderByType");

			if (Validator.isNotNull(orderByCol) &&
				Validator.isNotNull(orderByType)) {

				preferences.setValue(
					CommercePortletKeys.COMMERCE_ORDER,
					"commerce-orders-order-by-col", orderByCol);
				preferences.setValue(
					CommercePortletKeys.COMMERCE_ORDER,
					"commerce-orders-order-by-type", orderByType);
			}
			else {
				orderByCol = preferences.getValue(
					CommercePortletKeys.COMMERCE_ORDER,
					"commerce-orders-order-by-col", "createDate");
				orderByType = preferences.getValue(
					CommercePortletKeys.COMMERCE_ORDER,
					"commerce-orders-order-by-type", "desc");
			}

			OrderByComparator<CommerceOrder> orderByComparator =
				getOrderByComparator(orderByCol, orderByType);

			setOrderableHeaders(orderableHeaders);
			setOrderByCol(orderByCol);
			setOrderByType(orderByType);
			setOrderByComparator(orderByComparator);
		}
		catch (Exception e) {
			_log.error("Unable to initialize commerce order search", e);
		}
	}

	protected OrderByComparator<CommerceOrder> getOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceOrder> orderByComparator = null;

		if (orderByCol.equals("commerceOrderId")) {
			orderByComparator = new CommerceOrderIdComparator(orderByAsc);
		}
		else if (orderByCol.equals("createDate")) {
			orderByComparator = new CommerceOrderCreateDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("totala")) {
			orderByComparator = new CommerceOrderTotalComparator(orderByAsc);
		}

		return orderByComparator;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderSearch.class);

}