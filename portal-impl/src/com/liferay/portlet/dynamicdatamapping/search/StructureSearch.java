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

package com.liferay.portlet.dynamicdatamapping.search;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Eduardo Lundgren
 */
public class StructureSearch extends SearchContainer<DDMStructure> {

	static List<String> headerNames = new ArrayList<String>();
	static Map<String, String> orderableHeaders = new HashMap<String, String>();

	static {
		headerNames.add("id");
		headerNames.add("name");
		headerNames.add("class-name-id");
		headerNames.add("storage-type");
		headerNames.add("modified-date");
	}

	public static final String EMPTY_RESULTS_MESSAGE = "there-are-no-results";

	public StructureSearch(
		PortletRequest portletRequest, PortletURL iteratorURL) {

		super(
			portletRequest, new StructureDisplayTerms(portletRequest),
			new StructureSearchTerms(portletRequest), DEFAULT_CUR_PARAM,
			DEFAULT_DELTA, iteratorURL, headerNames, EMPTY_RESULTS_MESSAGE);

		StructureDisplayTerms displayTerms =
			(StructureDisplayTerms)getDisplayTerms();

		iteratorURL.setParameter(
			StructureDisplayTerms.CLASS_NAME_ID,
			String.valueOf(displayTerms.getClassNameId()));
		iteratorURL.setParameter(
			StructureDisplayTerms.DESCRIPTION, displayTerms.getDescription());
		iteratorURL.setParameter(
			StructureDisplayTerms.NAME, displayTerms.getName());
		iteratorURL.setParameter(
			StructureDisplayTerms.STORAGE_TYPE, displayTerms.getStorageType());
	}

}