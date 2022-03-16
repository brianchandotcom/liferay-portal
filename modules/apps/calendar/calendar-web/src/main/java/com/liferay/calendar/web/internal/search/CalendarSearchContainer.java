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

package com.liferay.calendar.web.internal.search;

import com.liferay.portal.kernel.dao.search.SearchContainer;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Mariano Álvaro Sáiz
 */
public class CalendarSearchContainer<R> extends SearchContainer<R> {

	public static final String EMPTY_RESULTS_MESSAGE =
		"no-calendar-resources-were-found";

	public CalendarSearchContainer(
		PortletRequest portletRequest, String curParam,
		PortletURL iteratorURL) {

		super(
			portletRequest, new CalendarResourceDisplayTerms(portletRequest),
			new CalendarResourceDisplayTerms(portletRequest), curParam,
			DEFAULT_DELTA, iteratorURL, null, EMPTY_RESULTS_MESSAGE);

		CalendarResourceDisplayTerms displayTerms =
			(CalendarResourceDisplayTerms)getDisplayTerms();

		iteratorURL.setParameter(
			CalendarResourceDisplayTerms.ACTIVE,
			String.valueOf(displayTerms.isActive()));
		iteratorURL.setParameter(
			CalendarResourceDisplayTerms.CODE, displayTerms.getCode());
		iteratorURL.setParameter(
			CalendarResourceDisplayTerms.DESCRIPTION,
			displayTerms.getDescription());
		iteratorURL.setParameter(
			CalendarResourceDisplayTerms.NAME, displayTerms.getName());
		iteratorURL.setParameter(
			CalendarResourceDisplayTerms.SCOPE,
			String.valueOf(displayTerms.getScope()));
	}

}