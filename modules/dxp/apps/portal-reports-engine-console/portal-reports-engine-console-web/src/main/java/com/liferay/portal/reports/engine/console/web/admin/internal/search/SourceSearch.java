/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.reports.engine.console.web.admin.internal.search;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.reports.engine.console.model.Source;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Rafael Praxedes
 */
public class SourceSearch extends SearchContainer<Source> {

	public static final String EMPTY_RESULTS_MESSAGE = "no-sources-were-found";

	public static List<String> headerNames = new ArrayList<>();

	static {
		headerNames.add("source-name");
		headerNames.add("create-date");
	}

	public SourceSearch(PortletRequest portletRequest, PortletURL iteratorURL) {
		super(
			portletRequest, new SourceDisplayTerms(portletRequest),
			new SourceSearchTerms(portletRequest), DEFAULT_CUR_PARAM,
			DEFAULT_DELTA, iteratorURL, headerNames, EMPTY_RESULTS_MESSAGE);

		SourceDisplayTerms definitionDisplayTerms =
			(SourceDisplayTerms)getDisplayTerms();

		iteratorURL.setParameter(
			SourceDisplayTerms.NAME, definitionDisplayTerms.getName());
		iteratorURL.setParameter(
			SourceDisplayTerms.DRIVER_URL,
			definitionDisplayTerms.getDriverUrl());
	}

}