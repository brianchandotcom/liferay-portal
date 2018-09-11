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

package com.liferay.asset.browser.web.internal.search;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.portal.kernel.dao.search.SearchContainer;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
public class AssetBrowserSearch extends SearchContainer<AssetEntry> {

	public static final String EMPTY_RESULTS_MESSAGE = "there-are-no-results";

	public static List<String> headerNames = new ArrayList<String>() {
		{
			add("title");
			add("description");
			add("user-name");
			add("modified-date");
			add("scope");
		}
	};

	public AssetBrowserSearch(
		PortletRequest portletRequest, int delta, PortletURL iteratorURL) {

		super(
			portletRequest, new AssetBrowserDisplayTerms(portletRequest),
			new AssetBrowserSearchTerms(portletRequest), DEFAULT_CUR_PARAM,
			delta, iteratorURL, headerNames, EMPTY_RESULTS_MESSAGE);

		AssetBrowserDisplayTerms displayTerms =
			(AssetBrowserDisplayTerms)getDisplayTerms();

		iteratorURL.setParameter(
			AssetBrowserDisplayTerms.DESCRIPTION,
			displayTerms.getDescription());
		iteratorURL.setParameter(
			AssetBrowserDisplayTerms.GROUP_ID,
			String.valueOf(displayTerms.getGroupId()));
		iteratorURL.setParameter(
			AssetBrowserDisplayTerms.TITLE, displayTerms.getTitle());
		iteratorURL.setParameter(
			AssetBrowserDisplayTerms.USER_NAME, displayTerms.getUserName());
	}

	public AssetBrowserSearch(
		PortletRequest portletRequest, PortletURL iteratorURL) {

		this(portletRequest, DEFAULT_DELTA, iteratorURL);
	}

}