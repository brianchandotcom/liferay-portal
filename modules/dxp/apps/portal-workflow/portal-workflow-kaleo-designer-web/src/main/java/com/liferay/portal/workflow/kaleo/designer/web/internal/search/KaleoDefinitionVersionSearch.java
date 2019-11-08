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

package com.liferay.portal.workflow.kaleo.designer.web.internal.search;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Inácio Nery
 */
public class KaleoDefinitionVersionSearch
	extends SearchContainer<KaleoDefinitionVersion> {

	public static final String EMPTY_RESULTS_MESSAGE = "no-entries-were-found";

	public static List<String> headerNames = new ArrayList<String>() {
		{
			add("title");
			add("description");
			add("modifiedDate");
		}
	};
	public static Map<String, String> orderableHeaders =
		HashMapBuilder.<String, String>put(
			"title", "modifiedDate"
		).build();

	public KaleoDefinitionVersionSearch(
		PortletRequest portletRequest, PortletURL iteratorURL) {

		super(
			portletRequest,
			new KaleoDefinitionVersionDisplayTerms(portletRequest),
			new KaleoDefinitionVersionSearchTerms(portletRequest),
			DEFAULT_CUR_PARAM, DEFAULT_DELTA, iteratorURL, headerNames,
			EMPTY_RESULTS_MESSAGE);

		KaleoDefinitionVersionDisplayTerms displayTerms =
			(KaleoDefinitionVersionDisplayTerms)getDisplayTerms();

		iteratorURL.setParameter(
			KaleoDefinitionVersionDisplayTerms.DESCRIPTION,
			displayTerms.getDescription());
		iteratorURL.setParameter(
			KaleoDefinitionVersionDisplayTerms.TITLE, displayTerms.getTitle());

		setOrderableHeaders(orderableHeaders);
	}

}