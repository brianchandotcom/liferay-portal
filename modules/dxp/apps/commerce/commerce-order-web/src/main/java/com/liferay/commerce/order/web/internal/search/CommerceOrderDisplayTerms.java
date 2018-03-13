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

import com.liferay.commerce.model.CommerceOrderConstants;
import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.Calendar;
import java.util.Date;

import javax.portlet.PortletRequest;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceOrderDisplayTerms extends DisplayTerms {

	public static final String ADVANCE_STATUS = "advanceStatus";

	public static final String END_CREATE_DATE = "endCreateDate";

	public static final String END_CREATE_DATE_DAY = END_CREATE_DATE + "Day";

	public static final String END_CREATE_DATE_MONTH =
		END_CREATE_DATE + "Month";

	public static final String END_CREATE_DATE_YEAR = END_CREATE_DATE + "Year";

	public static final String ORDER_ORGANIZATION_ID = "orderOrganizationId";

	public static final String ORDER_STATUS = "orderStatus";

	public static final String START_CREATE_DATE = "startCreateDate";

	public static final String START_CREATE_DATE_DAY =
		START_CREATE_DATE + "Day";

	public static final String START_CREATE_DATE_MONTH =
		START_CREATE_DATE + "Month";

	public static final String START_CREATE_DATE_YEAR =
		START_CREATE_DATE + "Year";

	public CommerceOrderDisplayTerms(PortletRequest portletRequest) {
		super(portletRequest);

		_advanceStatus = ParamUtil.getString(portletRequest, ADVANCE_STATUS);
		_endCreateDateDay = ParamUtil.getInteger(
			portletRequest, END_CREATE_DATE_DAY, _NULL_DATE_DAY);
		_endCreateDateMonth = ParamUtil.getInteger(
			portletRequest, END_CREATE_DATE_MONTH, _NULL_DATE_MONTH);
		_endCreateDateYear = ParamUtil.getInteger(
			portletRequest, END_CREATE_DATE_YEAR, _NULL_DATE_YEAR);
		_orderOrganizationId = ParamUtil.getLong(
			portletRequest, ORDER_ORGANIZATION_ID);
		_orderStatus = ParamUtil.getInteger(
			portletRequest, ORDER_STATUS,
			CommerceOrderConstants.ORDER_STATUS_ANY);
		_startCreateDateDay = ParamUtil.getInteger(
			portletRequest, START_CREATE_DATE_DAY, _NULL_DATE_DAY);
		_startCreateDateMonth = ParamUtil.getInteger(
			portletRequest, START_CREATE_DATE_MONTH, _NULL_DATE_MONTH);
		_startCreateDateYear = ParamUtil.getInteger(
			portletRequest, START_CREATE_DATE_YEAR, _NULL_DATE_YEAR);
	}

	public String getAdvanceStatus() {
		return _advanceStatus;
	}

	public Date getEndCreateDate() {
		if ((_endCreateDateDay == _NULL_DATE_DAY) &&
			(_endCreateDateMonth == _NULL_DATE_MONTH) &&
			(_endCreateDateYear == _NULL_DATE_YEAR)) {

			return null;
		}

		Calendar calendar = CalendarFactoryUtil.getCalendar(
			_endCreateDateYear, _endCreateDateMonth, _endCreateDateDay, 23, 59,
			59);

		return calendar.getTime();
	}

	public int getEndCreateDateDay() {
		return _endCreateDateDay;
	}

	public int getEndCreateDateMonth() {
		return _endCreateDateMonth;
	}

	public int getEndCreateDateYear() {
		return _endCreateDateYear;
	}

	public long getOrderOrganizationId() {
		return _orderOrganizationId;
	}

	public int getOrderStatus() {
		return _orderStatus;
	}

	public Date getStartCreateDate() {
		if ((_startCreateDateDay == _NULL_DATE_DAY) &&
			(_startCreateDateMonth == _NULL_DATE_MONTH) &&
			(_startCreateDateYear == _NULL_DATE_YEAR)) {

			return null;
		}

		Calendar calendar = CalendarFactoryUtil.getCalendar(
			_startCreateDateYear, _startCreateDateMonth, _startCreateDateDay, 0,
			0, 0);

		return calendar.getTime();
	}

	public int getStartCreateDateDay() {
		return _startCreateDateDay;
	}

	public int getStartCreateDateMonth() {
		return _startCreateDateMonth;
	}

	public int getStartCreateDateYear() {
		return _startCreateDateYear;
	}

	private static final int _NULL_DATE_DAY = 0;

	private static final int _NULL_DATE_MONTH = -1;

	private static final int _NULL_DATE_YEAR = 0;

	private final String _advanceStatus;
	private final int _endCreateDateDay;
	private final int _endCreateDateMonth;
	private final int _endCreateDateYear;
	private final long _orderOrganizationId;
	private final int _orderStatus;
	private final int _startCreateDateDay;
	private final int _startCreateDateMonth;
	private final int _startCreateDateYear;

}