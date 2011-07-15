/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.search;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.journal.model.JournalTemplate;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Brian Wing Shun Chan
 */
public class TemplateSearch extends SearchContainer<JournalTemplate> {

	static List<String> headerNames = new ArrayList<String>();

	static {
		headerNames.add("id");
		headerNames.add("name");
		headerNames.add("description");
	}

	public static final String EMPTY_RESULTS_MESSAGE =
		"no-templates-were-found";

	public TemplateSearch(
		PortletRequest portletRequest, int delta, PortletURL iteratorURL) {

		super(
			portletRequest, new TemplateDisplayTerms(portletRequest),
			new TemplateSearchTerms(portletRequest), DEFAULT_CUR_PARAM, delta,
			iteratorURL, headerNames, EMPTY_RESULTS_MESSAGE);

		TemplateDisplayTerms displayTerms =
			(TemplateDisplayTerms)getDisplayTerms();

		iteratorURL.setParameter(
			TemplateDisplayTerms.DESCRIPTION, displayTerms.getDescription());
		iteratorURL.setParameter(
			TemplateDisplayTerms.GROUP_IDS,
			StringUtil.merge(displayTerms.getGroupIds()));
		iteratorURL.setParameter(
			TemplateDisplayTerms.NAME, displayTerms.getName());
		iteratorURL.setParameter(
			TemplateDisplayTerms.STRUCTURE_ID, displayTerms.getStructureId());
		iteratorURL.setParameter(
			TemplateDisplayTerms.TEMPLATE_ID, displayTerms.getTemplateId());
	}

	public TemplateSearch(
		PortletRequest portletRequest, PortletURL iteratorURL) {

		this(portletRequest, DEFAULT_DELTA, iteratorURL);
	}

}