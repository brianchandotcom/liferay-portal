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

package com.liferay.portlet.assetpublisher.search;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portlet.asset.model.AssetEntry;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
public class AssetSearch extends SearchContainer<AssetEntry> {

	static List<String> headerNames = new ArrayList<String>();

	static {
		headerNames.add("title");
		headerNames.add("description");
		headerNames.add("user-name");
		headerNames.add("modified-date");
		headerNames.add("scope");
	}

	public static final String EMPTY_RESULTS_MESSAGE = "there-are-no-results";

	public AssetSearch(
		PortletRequest portletRequest, int delta, PortletURL iteratorURL) {

		super(
			portletRequest, new AssetDisplayTerms(portletRequest),
			new AssetSearchTerms(portletRequest), DEFAULT_CUR_PARAM, delta,
			iteratorURL, headerNames, EMPTY_RESULTS_MESSAGE);

		AssetDisplayTerms displayTerms = (AssetDisplayTerms)getDisplayTerms();

		iteratorURL.setParameter(
			AssetDisplayTerms.DESCRIPTION, displayTerms.getDescription());
		iteratorURL.setParameter(
			AssetDisplayTerms.GROUP_ID,
			String.valueOf(displayTerms.getGroupId()));
		iteratorURL.setParameter(
			AssetDisplayTerms.TITLE, displayTerms.getTitle());
		iteratorURL.setParameter(
			AssetDisplayTerms.USER_NAME, displayTerms.getUserName());
	}

	public AssetSearch(PortletRequest portletRequest, PortletURL iteratorURL) {
		this(portletRequest, DEFAULT_DELTA, iteratorURL);
	}

}