/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.shopping.search;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portlet.shopping.model.ShoppingCoupon;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Brian Wing Shun Chan
 */
public class CouponSearch extends SearchContainer<ShoppingCoupon> {

	static List<String> headerNames = new ArrayList<String>();

	static {
		headerNames.add("code");
		headerNames.add("description");
		headerNames.add("start-date");
		headerNames.add("expiration-date");
		headerNames.add("discount-type");
	}

	public static final String EMPTY_RESULTS_MESSAGE = "no-coupons-were-found";

	public CouponSearch(PortletRequest portletRequest, PortletURL iteratorURL) {
		super(
			portletRequest, new CouponDisplayTerms(portletRequest),
			new CouponDisplayTerms(portletRequest), DEFAULT_CUR_PARAM,
			DEFAULT_DELTA, iteratorURL, headerNames, EMPTY_RESULTS_MESSAGE);

		CouponDisplayTerms displayTerms = (CouponDisplayTerms)getDisplayTerms();

		iteratorURL.setParameter(
			CouponDisplayTerms.ACTIVE, String.valueOf(displayTerms.isActive()));
		iteratorURL.setParameter(
			CouponDisplayTerms.CODE, displayTerms.getCode());
		iteratorURL.setParameter(
			CouponDisplayTerms.DISCOUNT_TYPE, displayTerms.getDiscountType());
	}

}