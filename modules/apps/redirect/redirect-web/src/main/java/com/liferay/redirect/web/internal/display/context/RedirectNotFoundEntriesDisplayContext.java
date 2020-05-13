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
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.SearchResult;
import com.liferay.portal.kernel.search.SearchResultUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.redirect.model.RedirectNotFoundEntry;
import com.liferay.redirect.service.RedirectNotFoundEntryLocalService;
import com.liferay.redirect.web.internal.search.RedirectNotFoundEntrySearch;
import com.liferay.redirect.web.internal.security.permission.resource.RedirectPermission;
import com.liferay.redirect.web.internal.util.RedirectUtil;
import com.liferay.redirect.web.internal.util.comparator.RedirectComparator;
import com.liferay.redirect.web.internal.util.comparator.RedirectDateComparator;

import java.time.Duration;
import java.time.Instant;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.ActionRequest;
import javax.portlet.ActionURL;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderURL;

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

		_redirectNotFoundEntryLocalService =
			(RedirectNotFoundEntryLocalService)_httpServletRequest.getAttribute(
				RedirectNotFoundEntryLocalService.class.getName());
		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public DropdownItemList getActionDropdownItems(
		RedirectNotFoundEntry redirectNotFoundEntry) {

		return DropdownItemListBuilder.add(
			dropdownItem -> {
				ActionURL editRedirectNotFoundEntryURL =
					_liferayPortletResponse.createActionURL();

				editRedirectNotFoundEntryURL.setParameter(
					ActionRequest.ACTION_NAME,
					"/redirect/edit_redirect_not_found_entry");

				editRedirectNotFoundEntryURL.setParameter(
					"ignored",
					String.valueOf(!redirectNotFoundEntry.isIgnored()));

				editRedirectNotFoundEntryURL.setParameter(
					"redirect", _themeDisplay.getURLCurrent());

				editRedirectNotFoundEntryURL.setParameter(
					"redirectNotFoundEntryId",
					String.valueOf(
						redirectNotFoundEntry.getRedirectNotFoundEntryId()));

				dropdownItem.setHref(editRedirectNotFoundEntryURL);

				String label = "ignore";

				if (redirectNotFoundEntry.isIgnored()) {
					label = "unignore";
				}

				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, label));
			}
		).add(
			() -> RedirectPermission.contains(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroupId(), ActionKeys.ADD_ENTRY),
			dropdownItem -> {
				RenderURL editRedirectEntryURL =
					_liferayPortletResponse.createRenderURL();

				editRedirectEntryURL.setParameter(
					"mvcRenderCommandName", "/redirect/edit_redirect_entry");

				editRedirectEntryURL.setParameter(
					"redirect", _themeDisplay.getURLCurrent());

				editRedirectEntryURL.setParameter(
					"sourceURL", redirectNotFoundEntry.getUrl());

				dropdownItem.setHref(editRedirectEntryURL);

				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "create-redirect"));
			}
		).build();
	}

	public String getSearchContainerId() {
		return "redirectNotFoundEntries";
	}

	public SearchContainer<RedirectNotFoundEntry> searchContainer()
		throws Exception {

		if (_redirectNotFoundEntrySearch != null) {
			return _redirectNotFoundEntrySearch;
		}

		_redirectNotFoundEntrySearch = new RedirectNotFoundEntrySearch(
			_liferayPortletRequest, _liferayPortletResponse, _getPortletURL(),
			getSearchContainerId());

		if (_redirectNotFoundEntrySearch.isSearch()) {
			_populateWithSearchIndex(_redirectNotFoundEntrySearch);
		}
		else {
			_populateWithDatabase(_redirectNotFoundEntrySearch);
		}

		return _redirectNotFoundEntrySearch;
	}

	private Boolean _getIgnored() {
		String filterType = ParamUtil.getString(
			_httpServletRequest, "filterType", "active-urls");

		if (filterType.equals("all")) {
			return null;
		}

		return filterType.equals("ignored-urls");
	}

	private Date _getMinModifiedDate() {
		int days = Integer.valueOf(
			ParamUtil.getString(_httpServletRequest, "filterDate", "0"));

		if (days == 0) {
			return null;
		}

		Instant instant = Instant.now();

		return Date.from(instant.minus(Duration.ofDays(days)));
	}

	private OrderByComparator _getOrderByComparator() {
		boolean orderByAsc = StringUtil.equals(
			_redirectNotFoundEntrySearch.getOrderByType(), "asc");

		if (Objects.equals(
				_redirectNotFoundEntrySearch.getOrderByCol(),
				"modified-date")) {

			return new RedirectDateComparator<>(
				"RedirectNotFoundEntry", "modifiedDate",
				RedirectNotFoundEntry::getModifiedDate, !orderByAsc);
		}

		return new RedirectComparator<>(
			"RedirectNotFoundEntry", "hits", RedirectNotFoundEntry::getHits,
			!orderByAsc);
	}

	private PortletURL _getPortletURL() throws PortletException {
		return PortletURLUtil.clone(
			PortletURLUtil.getCurrent(
				_liferayPortletRequest, _liferayPortletResponse),
			_liferayPortletResponse);
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
			_redirectNotFoundEntryLocalService.getRedirectNotFoundEntriesCount(
				themeDisplay.getScopeGroupId(), _getIgnored(),
				_getMinModifiedDate()));

		redirectNotFoundEntrySearch.setResults(
			_redirectNotFoundEntryLocalService.getRedirectNotFoundEntries(
				themeDisplay.getScopeGroupId(), _getIgnored(),
				_getMinModifiedDate(), _redirectNotFoundEntrySearch.getStart(),
				_redirectNotFoundEntrySearch.getEnd(),
				_getOrderByComparator()));
	}

	private void _populateWithSearchIndex(
			RedirectNotFoundEntrySearch redirectNotFoundEntrySearch)
		throws Exception {

		Indexer indexer = IndexerRegistryUtil.getIndexer(
			RedirectNotFoundEntry.class);

		SearchContext searchContext = SearchContextFactory.getInstance(
			PortalUtil.getHttpServletRequest(_liferayPortletRequest));

		searchContext.setAttribute(Field.STATUS, WorkflowConstants.STATUS_ANY);
		searchContext.setAttribute(
			"groupBaseURL", RedirectUtil.getGroupBaseURL(_themeDisplay));
		searchContext.setAttribute("ignored", _getIgnored());
		searchContext.setAttribute("minModifiedDate", _getMinModifiedDate());
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
				_redirectNotFoundEntryLocalService::fetchRedirectNotFoundEntry
			).collect(
				Collectors.toList()
			));

		redirectNotFoundEntrySearch.setTotal(hits.getLength());
	}

	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final RedirectNotFoundEntryLocalService
		_redirectNotFoundEntryLocalService;
	private RedirectNotFoundEntrySearch _redirectNotFoundEntrySearch;
	private final ThemeDisplay _themeDisplay;

}