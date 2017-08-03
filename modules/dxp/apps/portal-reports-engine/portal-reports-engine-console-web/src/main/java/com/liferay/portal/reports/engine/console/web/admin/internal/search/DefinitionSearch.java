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

package com.liferay.portal.reports.engine.console.web.admin.internal.search;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.reports.engine.console.model.Definition;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Rafael Praxedes
 */
public class DefinitionSearch extends SearchContainer<Definition> {

	public static final String EMPTY_RESULTS_MESSAGE =
		"no-definitions-were-found";

	public static List<String> headerNames = new ArrayList<>();

	static {
		headerNames.add("definition-name");
		headerNames.add("source-name");
		headerNames.add("create-date");
	}

	public DefinitionSearch(
		PortletRequest portletRequest, PortletURL iteratorURL) {

		super(
			portletRequest, new DefinitionDisplayTerms(portletRequest),
			new DefinitionSearchTerms(portletRequest), DEFAULT_CUR_PARAM,
			DEFAULT_DELTA, iteratorURL, headerNames, EMPTY_RESULTS_MESSAGE);

		DefinitionDisplayTerms definitionDisplayTerms =
			(DefinitionDisplayTerms)getDisplayTerms();

		iteratorURL.setParameter(
			DefinitionDisplayTerms.DEFINITION_NAME,
			definitionDisplayTerms.getDefinitionName());
		iteratorURL.setParameter(
			DefinitionDisplayTerms.DESCRIPTION,
			definitionDisplayTerms.getDescription());
		iteratorURL.setParameter(
			DefinitionDisplayTerms.REPORT_NAME,
			definitionDisplayTerms.getReportName());
		iteratorURL.setParameter(
			DefinitionDisplayTerms.SOURCE_ID,
			String.valueOf(definitionDisplayTerms.getSourceId()));
	}

}