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

package com.liferay.portal.search.internal.result;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.SearchResult;
import com.liferay.portal.kernel.search.SummaryFactory;
import com.liferay.portal.kernel.search.result.SearchResultContributor;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author André de Oliveira
 */
public class ContributedSearchResultManager extends BaseSearchResultManager {

	public ContributedSearchResultManager(
		SearchResultContributor searchResultContributor,
		SummaryFactory summaryFactory) {

		_searchResultContributor = searchResultContributor;
		_summaryFactory = summaryFactory;
	}

	@Override
	protected void addRelatedModel(
			SearchResult searchResult, Document document, Locale locale,
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws PortalException {

		_searchResultContributor.addRelatedModel(
			searchResult, document, locale, portletRequest, portletResponse);
	}

	@Override
	protected SummaryFactory getSummaryFactory() {
		return _summaryFactory;
	}

	private final SearchResultContributor _searchResultContributor;
	private final SummaryFactory _summaryFactory;

}