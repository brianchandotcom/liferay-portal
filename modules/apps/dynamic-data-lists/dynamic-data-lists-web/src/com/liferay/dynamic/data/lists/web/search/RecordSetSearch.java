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

package com.liferay.dynamic.data.lists.web.search;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Marcellus Tavares
 */
public class RecordSetSearch extends SearchContainer<DDLRecordSet> {

	public static final String EMPTY_RESULTS_MESSAGE = "no-entries-were-found";

	public static List<String> headerNames = new ArrayList<>();

	static {
		headerNames.add("id");
		headerNames.add("name");
		headerNames.add("description");
		headerNames.add("modified-date");
	}

	public RecordSetSearch(
		PortletRequest portletRequest, PortletURL iteratorURL) {

		super(
			portletRequest, new RecordSetDisplayTerms(portletRequest),
			new RecordSetSearchTerms(portletRequest), DEFAULT_CUR_PARAM,
			DEFAULT_DELTA, iteratorURL, headerNames, EMPTY_RESULTS_MESSAGE);

		RecordSetDisplayTerms displayTerms =
			(RecordSetDisplayTerms)getDisplayTerms();

		iteratorURL.setParameter(
			RecordSetDisplayTerms.DESCRIPTION, displayTerms.getDescription());
		iteratorURL.setParameter(
			RecordSetDisplayTerms.NAME, String.valueOf(displayTerms.getName()));
	}

}