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
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
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
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.redirect.model.RedirectEntry;
import com.liferay.redirect.service.RedirectEntryLocalServiceUtil;
import com.liferay.redirect.web.internal.search.RedirectEntrySearch;

import java.util.List;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.ActionRequest;
import javax.portlet.ActionURL;
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
					"mvcRenderCommandName", "/redirect/edit_redirect_entry");

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
		).add(
			dropdownItem -> {
				ActionURL deleteRedirectEntryURL =
					_liferayPortletResponse.createActionURL();

				deleteRedirectEntryURL.setParameter(
					ActionRequest.ACTION_NAME,
					"/redirect/delete_redirect_entry");

				deleteRedirectEntryURL.setParameter(
					"redirectEntryId",
					String.valueOf(redirectEntry.getRedirectEntryId()));

				dropdownItem.setHref(deleteRedirectEntryURL);

				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "delete"));
			}
		).build();
	}

	public String getGroupBaseURL() {
		StringBuilder groupBaseURL = new StringBuilder();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		groupBaseURL.append(themeDisplay.getPortalURL());

		Group group = themeDisplay.getScopeGroup();

		LayoutSet layoutSet = group.getPublicLayoutSet();

		TreeMap<String, String> virtualHostnames =
			layoutSet.getVirtualHostnames();

		if (virtualHostnames.isEmpty() ||
			!_matchesHostname(groupBaseURL, virtualHostnames)) {

			groupBaseURL.append(group.getPathFriendlyURL(false, themeDisplay));
			groupBaseURL.append(HttpUtil.decodeURL(group.getFriendlyURL()));
		}

		return groupBaseURL.toString();
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
			_liferayPortletRequest, _liferayPortletResponse, _getPortletURL(),
			getSearchContainerId());

		Indexer indexer = IndexerRegistryUtil.getIndexer(RedirectEntry.class);

		SearchContext searchContext = SearchContextFactory.getInstance(
			PortalUtil.getHttpServletRequest(_liferayPortletRequest));

		searchContext.setAttribute(Field.STATUS, WorkflowConstants.STATUS_ANY);
		searchContext.setEnd(_redirectEntrySearch.getEnd());
		searchContext.setSorts(_getSorts());
		searchContext.setStart(_redirectEntrySearch.getStart());

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

	private boolean _matchesHostname(
		StringBuilder friendlyURLBase,
		TreeMap<String, String> virtualHostnames) {

		for (String virtualHostname : virtualHostnames.keySet()) {
			if (friendlyURLBase.indexOf(virtualHostname) != -1) {
				return true;
			}
		}

		return false;
	}

	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private RedirectEntrySearch _redirectEntrySearch;

}