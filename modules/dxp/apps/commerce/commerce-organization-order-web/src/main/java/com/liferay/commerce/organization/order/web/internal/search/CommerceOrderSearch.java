/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.organization.order.web.internal.search;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
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

	public CommerceOrderSearch(
		PortletRequest portletRequest, PortletURL iteratorURL,
		boolean filterByStatuses) {

		super(
			portletRequest, new CommerceOrderDisplayTerms(portletRequest),
			new CommerceOrderDisplayTerms(portletRequest), DEFAULT_CUR_PARAM,
			DEFAULT_DELTA, iteratorURL, _headerNames, _EMPTY_RESULTS_MESSAGE);

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
			CommerceOrderDisplayTerms.START_CREATE_DATE_DAY,
			String.valueOf(commerceOrderDisplayTerms.getStartCreateDateDay()));
		iteratorURL.setParameter(
			CommerceOrderDisplayTerms.START_CREATE_DATE_MONTH,
			String.valueOf(
				commerceOrderDisplayTerms.getStartCreateDateMonth()));
		iteratorURL.setParameter(
			CommerceOrderDisplayTerms.START_CREATE_DATE_YEAR,
			String.valueOf(commerceOrderDisplayTerms.getStartCreateDateYear()));

		if (filterByStatuses) {
			iteratorURL.setParameter(
				CommerceOrderDisplayTerms.ADVANCE_STATUS,
				commerceOrderDisplayTerms.getAdvanceStatus());
			iteratorURL.setParameter(
				CommerceOrderDisplayTerms.ORDER_STATUS,
				String.valueOf(commerceOrderDisplayTerms.getOrderStatus()));
		}

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
					CommercePortletKeys.COMMERCE_ORGANIZATION_ORDER,
					"commerce-orders-order-by-col", orderByCol);
				preferences.setValue(
					CommercePortletKeys.COMMERCE_ORGANIZATION_ORDER,
					"commerce-orders-order-by-type", orderByType);
			}
			else {
				orderByCol = preferences.getValue(
					CommercePortletKeys.COMMERCE_ORGANIZATION_ORDER,
					"commerce-orders-order-by-col", "create-date");
				orderByType = preferences.getValue(
					CommercePortletKeys.COMMERCE_ORGANIZATION_ORDER,
					"commerce-orders-order-by-type", "desc");
			}

			setOrderableHeaders(_orderableHeaders);
			setOrderByCol(orderByCol);
			setOrderByType(orderByType);
		}
		catch (Exception e) {
			_log.error("Unable to initialize commerce order search", e);
		}
	}

	private static final String _EMPTY_RESULTS_MESSAGE = "no-orders-were-found";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderSearch.class);

	private static final List<String> _headerNames = new ArrayList<>();
	private static final Map<String, String> _orderableHeaders =
		new LinkedHashMap<>();

	static {
		_headerNames.add("order-date");
		_headerNames.add("status");
		_headerNames.add("customer-name");
		_headerNames.add("customer-id");
		_headerNames.add("order-id");
		_headerNames.add("order-value");
		_headerNames.add("notes");

		_orderableHeaders.put("create-date", "order-date");
		_orderableHeaders.put("order-id", "order-id");
		_orderableHeaders.put("total", "order-value");
	}

}