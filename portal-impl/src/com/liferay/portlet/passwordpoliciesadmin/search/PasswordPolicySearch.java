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

package com.liferay.portlet.passwordpoliciesadmin.search;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortalPreferences;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.passwordpoliciesadmin.util.PasswordPoliciesAdminUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Scott Lee
 */
public class PasswordPolicySearch extends SearchContainer<PasswordPolicy> {

	public static final String EMPTY_RESULTS_MESSAGE =
		"no-password-policies-were-found";

	public static List<String> headerNames = new ArrayList<String>();
	public static Map<String, String> orderableHeaders =
		new HashMap<String, String>();

	static {
		headerNames.add("name");
		headerNames.add("description");

		orderableHeaders.put("name", "name");
		orderableHeaders.put("description", "description");
	}

	public PasswordPolicySearch(
		PortletRequest portletRequest, PortletURL iteratorURL) {

		super(
			portletRequest, new PasswordPolicyDisplayTerms(portletRequest),
			new PasswordPolicyDisplayTerms(portletRequest), DEFAULT_CUR_PARAM,
			DEFAULT_DELTA, iteratorURL, headerNames, EMPTY_RESULTS_MESSAGE);

		PasswordPolicyDisplayTerms displayTerms =
			(PasswordPolicyDisplayTerms)getDisplayTerms();

		iteratorURL.setParameter(
			PasswordPolicyDisplayTerms.NAME, displayTerms.getName());

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
					PortletKeys.PASSWORD_POLICIES_ADMIN,
					"password-policies-order-by-col", orderByCol);
				preferences.setValue(
					PortletKeys.PASSWORD_POLICIES_ADMIN,
					"password-policies-order-by-type", orderByType);
			}
			else {
				orderByCol = preferences.getValue(
					PortletKeys.PASSWORD_POLICIES_ADMIN,
					"password-policies-order-by-col", "name");
				orderByType = preferences.getValue(
					PortletKeys.PASSWORD_POLICIES_ADMIN,
					"password-policies-order-by-type", "asc");
			}

			OrderByComparator<PasswordPolicy> orderByComparator =
				PasswordPoliciesAdminUtil.getPasswordPolicyOrderByComparator(
					orderByCol, orderByType);

			setOrderableHeaders(orderableHeaders);
			setOrderByCol(orderByCol);
			setOrderByType(orderByType);
			setOrderByComparator(orderByComparator);
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PasswordPolicySearch.class);

}