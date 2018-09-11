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

package com.liferay.dynamic.data.mapping.web.internal.search;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.portal.kernel.dao.search.SearchContainer;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Eduardo Lundgren
 */
public class TemplateSearch extends SearchContainer<DDMTemplate> {

	public static final String EMPTY_RESULTS_MESSAGE = "there-are-no-results";

	public static List<String> headerNames = new ArrayList<String>() {
		{
			add("id");
			add("name");
			add("type");
			add("language");
			add("modified-date");
		}
	};

	public TemplateSearch(
		PortletRequest portletRequest, PortletURL iteratorURL) {

		super(
			portletRequest, new TemplateDisplayTerms(portletRequest),
			new TemplateSearchTerms(portletRequest), DEFAULT_CUR_PARAM,
			DEFAULT_DELTA, iteratorURL, headerNames, EMPTY_RESULTS_MESSAGE);

		TemplateDisplayTerms displayTerms =
			(TemplateDisplayTerms)getDisplayTerms();

		iteratorURL.setParameter(
			TemplateDisplayTerms.DESCRIPTION, displayTerms.getDescription());
		iteratorURL.setParameter(
			TemplateDisplayTerms.NAME, displayTerms.getName());
	}

	public TemplateSearch(
		PortletRequest portletRequest, PortletURL iteratorURL, int status) {

		this(portletRequest, iteratorURL);

		TemplateSearchTerms searchTerms = (TemplateSearchTerms)getSearchTerms();

		searchTerms.setStatus(status);
	}

}