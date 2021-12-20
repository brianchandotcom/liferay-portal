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

package com.liferay.portal.kernel.dao.search;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.search.BaseModelSearchResult;

import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Mariano Álvaro Sáiz
 */
public class BaseModelSearchContainer<R extends BaseModel<R>>
	extends SearchContainer<R> {

	public BaseModelSearchContainer(
		PortletRequest portletRequest, DisplayTerms displayTerms,
		DisplayTerms searchTerms, String curParam, int delta,
		PortletURL iteratorURL, List<String> headerNames,
		String emptyResultsMessage) {

		super(
			portletRequest, displayTerms, searchTerms, curParam, delta,
			iteratorURL, headerNames, emptyResultsMessage);
	}

	public BaseModelSearchContainer(
		PortletRequest portletRequest, PortletURL iteratorURL,
		List<String> headerNames, String emptyResultsMessage) {

		super(portletRequest, iteratorURL, headerNames, emptyResultsMessage);
	}

	public void setResultsAndTotal(
		BaseModelSearchResult<R> baseModelSearchResult) {

		setResultsAndTotal(
			baseModelSearchResult::getBaseModels,
			baseModelSearchResult.getLength());
	}

}