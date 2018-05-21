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

package com.liferay.portal.workflow.kaleo.forms.web.internal.search;

import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Marcellus Tavares
 */
public class KaleoProcessSearch extends SearchContainer<KaleoProcess> {

	public static final String EMPTY_RESULTS_MESSAGE =
		"no-processes-were-found";

	public static List<String> headerNames = new ArrayList<>();

	static {
		headerNames.add("id");
		headerNames.add("name");
		headerNames.add("description");
		headerNames.add("modified-date");
	}

	public KaleoProcessSearch(
		PortletRequest portletRequest, PortletURL iteratorURL) {

		super(
			portletRequest, new DisplayTerms(portletRequest),
			new DisplayTerms(portletRequest), DEFAULT_CUR_PARAM, DEFAULT_DELTA,
			iteratorURL, headerNames, EMPTY_RESULTS_MESSAGE);

		DisplayTerms displayTerms = (DisplayTerms)getDisplayTerms();

		iteratorURL.setParameter(
			DisplayTerms.KEYWORDS, displayTerms.getKeywords());
	}

}