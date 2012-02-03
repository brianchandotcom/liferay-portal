/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.blogsadmin.search;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portlet.blogs.model.BlogsEntry;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Juan Fernández
 */
public class EntrySearch extends SearchContainer<BlogsEntry> {

	static List<String> headerNames = new ArrayList<String>();

	static {
		headerNames.add("title");
		headerNames.add("author");
		headerNames.add("createDate");
		headerNames.add("status");
	}

	public static final String EMPTY_RESULTS_MESSAGE = "no-entries-were-found";

	public EntrySearch(PortletRequest portletRequest, PortletURL iteratorURL) {
		super(
			portletRequest, new EntryDisplayTerms(portletRequest),
			new EntrySearchTerms(portletRequest), DEFAULT_CUR_PARAM,
			DEFAULT_DELTA, iteratorURL, headerNames, EMPTY_RESULTS_MESSAGE);

		EntryDisplayTerms displayTerms = (EntryDisplayTerms)getDisplayTerms();

		iteratorURL.setParameter(
			EntryDisplayTerms.AUTHOR, displayTerms.getAuthor());
		iteratorURL.setParameter(
			EntryDisplayTerms.STATUS, String.valueOf(displayTerms.getStatus()));
		iteratorURL.setParameter(
			EntryDisplayTerms.TITLE, displayTerms.getTitle());
	}

}