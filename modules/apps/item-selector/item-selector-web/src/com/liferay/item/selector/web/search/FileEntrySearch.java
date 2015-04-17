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

package com.liferay.item.selector.web.search;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.repository.model.FileEntry;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Brian Wing Shun Chan
 * @author Vilmos Papp
 */
public class FileEntrySearch extends SearchContainer<FileEntry> {

	public static final String EMPTY_RESULTS_MESSAGE = "there-are-no-documents";

	public static List<String> headerNames = new ArrayList<>();

	static {
		headerNames.add("document");
		headerNames.add("size");
		headerNames.add("downloads");
		headerNames.add("locked");
	}

	public FileEntrySearch(
		PortletRequest portletRequest, FileEntryDisplayTerms displayTerms,
		FileEntrySearchTerms searchTerms, String cur, int delta,
		PortletURL iteratorURL, List<String> headers,
		String emptyResultsMessage) {

		super(
			portletRequest, new FileEntryDisplayTerms(portletRequest),
			new FileEntrySearchTerms(portletRequest), cur, delta, iteratorURL,
			headers, emptyResultsMessage);

		iteratorURL.setParameter(
			FileEntryDisplayTerms.DOCUMENT, displayTerms.getDocument());
		iteratorURL.setParameter(
			FileEntryDisplayTerms.LOCKED,
			String.valueOf(displayTerms.isLocked()));
		iteratorURL.setParameter(
			FileEntryDisplayTerms.SELECTED_GROUP_ID,
			String.valueOf(displayTerms.getSelectedGroupId()));
		iteratorURL.setParameter(
			FileEntryDisplayTerms.SIZE, displayTerms.getSize());
	}

	public FileEntrySearch(
		PortletRequest portletRequest, PortletURL iteratorURL) {

		this(
			portletRequest, new FileEntryDisplayTerms(portletRequest),
			new FileEntrySearchTerms(portletRequest),
			SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA,
			iteratorURL, headerNames, EMPTY_RESULTS_MESSAGE);
	}

}