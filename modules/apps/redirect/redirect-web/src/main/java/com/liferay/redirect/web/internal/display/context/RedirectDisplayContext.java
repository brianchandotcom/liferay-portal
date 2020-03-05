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

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.redirect.model.RedirectEntry;
import com.liferay.redirect.service.RedirectEntryLocalServiceUtil;
import com.liferay.redirect.web.internal.search.RedirectEntrySearch;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletURL;
import javax.portlet.RenderURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class RedirectDisplayContext {

	public RedirectDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
	}

	public DropdownItemList getActionDropdownItems(
		RedirectEntry redirectEntry) {

		return DropdownItemListBuilder.add(
			dropdownItem -> {
				RenderURL editRedirectEntryURL =
					_liferayPortletResponse.createRenderURL();

				editRedirectEntryURL.setParameter(
					"mvcRenderCommandName", "/redirect_entry/edit");

				PortletURL portletURL = _getPortletURL();

				editRedirectEntryURL.setParameter(
					"redirect", portletURL.toString());

				editRedirectEntryURL.setParameter(
					"redirectEntryId",
					String.valueOf(redirectEntry.getRedirectEntryId()));

				dropdownItem.setHref(editRedirectEntryURL);

				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "edit"));
			}
		).build();
	}

	public String getSearchContainerId() {
		return "redirectEntries";
	}

	public SearchContainer<RedirectEntry> searchContainer()
		throws PortalException {

		if (_redirectEntrySearch != null) {
			return _redirectEntrySearch;
		}

		_redirectEntrySearch = new RedirectEntrySearch(
			_liferayPortletRequest, _getPortletURL(), getSearchContainerId());

		Indexer indexer = IndexerRegistryUtil.getIndexer(RedirectEntry.class);

		SearchContext searchContext = SearchContextFactory.getInstance(
			PortalUtil.getHttpServletRequest(_liferayPortletRequest));

		searchContext.setAttribute(Field.STATUS, WorkflowConstants.STATUS_ANY);
		searchContext.setEnd(_redirectEntrySearch.getEnd());
		searchContext.setStart(_redirectEntrySearch.getStart());
		searchContext.setSorts(_getSorts());

		Hits hits = indexer.search(searchContext);

		List<SearchResult> searchResults = SearchResultUtil.getSearchResults(
			hits, LocaleUtil.getDefault());

		Stream<SearchResult> stream = searchResults.stream();

		_redirectEntrySearch.setResults(
			stream.map(
				SearchResult::getClassPK
			).map(
				RedirectEntryLocalServiceUtil::fetchRedirectEntry
			).collect(
				Collectors.toList()
			));

		_redirectEntrySearch.setTotal(hits.getLength());

		return _redirectEntrySearch;
	}

	private PortletURL _getPortletURL() {
		return _liferayPortletResponse.createRenderURL();
	}

	private Sort _getSorts() {
		boolean orderByAsc = StringUtil.equals(
			_redirectEntrySearch.getOrderByType(), "asc");

		if (Objects.equals(
				_redirectEntrySearch.getOrderByCol(), "source-url")) {

			return new Sort(
				Field.getSortableFieldName("sourceURL"), Sort.STRING_TYPE,
				!orderByAsc);
		}

		if (Objects.equals(
				_redirectEntrySearch.getOrderByCol(), "destination-url")) {

			return new Sort(
				Field.getSortableFieldName("destinationURL"), Sort.STRING_TYPE,
				!orderByAsc);
		}

		if (Objects.equals(
				_redirectEntrySearch.getOrderByCol(), "modified-date")) {

			return new Sort(
				Field.getSortableFieldName(Field.MODIFIED_DATE), Sort.LONG_TYPE,
				!orderByAsc);
		}

		return new Sort(Field.CREATE_DATE, Sort.LONG_TYPE, !orderByAsc);
	}

	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private RedirectEntrySearch _redirectEntrySearch;

}