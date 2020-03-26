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

package com.liferay.redirect.web.internal.display.context;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.SearchResult;
import com.liferay.portal.kernel.search.SearchResultUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.redirect.model.RedirectNotFoundEntry;
import com.liferay.redirect.service.RedirectNotFoundEntryLocalServiceUtil;
import com.liferay.redirect.web.internal.search.RedirectNotFoundEntrySearch;
import com.liferay.redirect.web.internal.util.comparator.RedirectEntryModifiedDateComparator;
import com.liferay.redirect.web.internal.util.comparator.RedirectNotFoundEntryHitsComparator;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class RedirectNotFoundEntriesDisplayContext {

	public RedirectNotFoundEntriesDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getSearchContainerId() {
		return "redirectEntries";
	}

	public SearchContainer<RedirectNotFoundEntry> searchContainer()
		throws PortalException {

		if (_redirectNotFoundEntrySearch != null) {
			return _redirectNotFoundEntrySearch;
		}

		_redirectNotFoundEntrySearch = new RedirectNotFoundEntrySearch(
			_liferayPortletRequest, _getPortletURL(), getSearchContainerId());

		if (_redirectNotFoundEntrySearch.isSearch()) {
			_populateWithSearchIndex(_redirectNotFoundEntrySearch);
		}
		else {
			_populateWithDatabase(_redirectNotFoundEntrySearch);
		}

		return _redirectNotFoundEntrySearch;
	}

	private OrderByComparator _getOrderByComparator() {
		boolean orderByAsc = StringUtil.equals(
			_redirectNotFoundEntrySearch.getOrderByType(), "asc");

		if (Objects.equals(
				_redirectNotFoundEntrySearch.getOrderByCol(),
				"modified-date")) {

			return new RedirectEntryModifiedDateComparator(!orderByAsc);
		}

		return new RedirectNotFoundEntryHitsComparator(!orderByAsc);
	}

	private PortletURL _getPortletURL() {
		return _liferayPortletResponse.createRenderURL();
	}

	private Sort _getSorts() {
		boolean orderByAsc = StringUtil.equals(
			_redirectNotFoundEntrySearch.getOrderByType(), "asc");

		if (Objects.equals(
				_redirectNotFoundEntrySearch.getOrderByCol(),
				"modified-date")) {

			return new Sort(
				Field.getSortableFieldName(Field.MODIFIED_DATE), Sort.LONG_TYPE,
				orderByAsc);
		}

		return new Sort("hits", Sort.LONG_TYPE, orderByAsc);
	}

	private void _populateWithDatabase(
		RedirectNotFoundEntrySearch redirectNotFoundEntrySearch) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		redirectNotFoundEntrySearch.setTotal(
			RedirectNotFoundEntryLocalServiceUtil.
				getRedirectNotFoundEntriesCount(
					themeDisplay.getScopeGroupId()));

		redirectNotFoundEntrySearch.setResults(
			RedirectNotFoundEntryLocalServiceUtil.getRedirectNotFoundEntries(
				themeDisplay.getScopeGroupId(),
				_redirectNotFoundEntrySearch.getStart(),
				_redirectNotFoundEntrySearch.getEnd(),
				_getOrderByComparator()));
	}

	private void _populateWithSearchIndex(
			RedirectNotFoundEntrySearch redirectNotFoundEntrySearch)
		throws PortalException {

		Indexer indexer = IndexerRegistryUtil.getIndexer(
			RedirectNotFoundEntry.class);

		SearchContext searchContext = SearchContextFactory.getInstance(
			PortalUtil.getHttpServletRequest(_liferayPortletRequest));

		searchContext.setAttribute(Field.STATUS, WorkflowConstants.STATUS_ANY);
		searchContext.setEnd(redirectNotFoundEntrySearch.getEnd());
		searchContext.setSorts(_getSorts());
		searchContext.setStart(redirectNotFoundEntrySearch.getStart());

		Hits hits = indexer.search(searchContext);

		List<SearchResult> searchResults = SearchResultUtil.getSearchResults(
			hits, LocaleUtil.getDefault());

		Stream<SearchResult> stream = searchResults.stream();

		redirectNotFoundEntrySearch.setResults(
			stream.map(
				SearchResult::getClassPK
			).map(
				RedirectNotFoundEntryLocalServiceUtil::
					fetchRedirectNotFoundEntry
			).collect(
				Collectors.toList()
			));

		redirectNotFoundEntrySearch.setTotal(hits.getLength());
	}

	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private RedirectNotFoundEntrySearch _redirectNotFoundEntrySearch;
	private final ThemeDisplay _themeDisplay;

}