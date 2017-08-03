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

package com.liferay.portal.workflow.kaleo.forms.web.internal.search;

import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Marcellus Tavares
 */
public class KaleoProcessSearch extends SearchContainer<KaleoProcess> {

	public static final String EMPTY_RESULTS_MESSAGE =
		"no-processes-were-found";

	public static List<String> headerNames = new ArrayList<>();

	static {
		headerNames.add("id");
		headerNames.add("name");
		headerNames.add("description");
		headerNames.add("modified-date");
	}

	public KaleoProcessSearch(
		PortletRequest portletRequest, PortletURL iteratorURL) {

		super(
			portletRequest, new DisplayTerms(portletRequest),
			new DisplayTerms(portletRequest), DEFAULT_CUR_PARAM, DEFAULT_DELTA,
			iteratorURL, headerNames, EMPTY_RESULTS_MESSAGE);

		DisplayTerms displayTerms = (DisplayTerms)getDisplayTerms();

		iteratorURL.setParameter(
			DisplayTerms.KEYWORDS, displayTerms.getKeywords());
	}

}