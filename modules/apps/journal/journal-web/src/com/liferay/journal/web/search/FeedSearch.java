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

package com.liferay.journal.web.search;

import com.liferay.journal.model.JournalFeed;
import com.liferay.portal.kernel.dao.search.SearchContainer;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Raymond Augé
 */
public class FeedSearch extends SearchContainer<JournalFeed> {

	public static final String EMPTY_RESULTS_MESSAGE = "no-feeds-were-found";

	public static List<String> headerNames = new ArrayList<>();

	static {
		headerNames.add("id");
		headerNames.add("description");
	}

	public FeedSearch(PortletRequest portletRequest, PortletURL iteratorURL) {
		super(
			portletRequest, new FeedDisplayTerms(portletRequest),
			new FeedSearchTerms(portletRequest), DEFAULT_CUR_PARAM,
			DEFAULT_DELTA, iteratorURL, headerNames, EMPTY_RESULTS_MESSAGE);

		FeedDisplayTerms displayTerms = (FeedDisplayTerms)getDisplayTerms();

		iteratorURL.setParameter(
			FeedDisplayTerms.DESCRIPTION, displayTerms.getDescription());
		iteratorURL.setParameter(
			FeedDisplayTerms.FEED_ID, displayTerms.getFeedId());
		iteratorURL.setParameter(FeedDisplayTerms.NAME, displayTerms.getName());
		iteratorURL.setParameter(
			FeedDisplayTerms.GROUP_ID,
			String.valueOf(displayTerms.getGroupId()));
	}

}