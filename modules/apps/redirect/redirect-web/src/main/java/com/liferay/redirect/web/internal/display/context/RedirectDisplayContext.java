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
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.redirect.model.RedirectEntry;
import com.liferay.redirect.service.RedirectEntryLocalServiceUtil;
import com.liferay.redirect.service.RedirectEntryServiceUtil;
import com.liferay.redirect.web.internal.resource.RedirectEntryPermission;
import com.liferay.redirect.web.internal.search.RedirectEntrySearch;
import com.liferay.redirect.web.internal.util.comparator.RedirectEntryCreateDateComparator;
import com.liferay.redirect.web.internal.util.comparator.RedirectEntryDestinationURLComparator;
import com.liferay.redirect.web.internal.util.comparator.RedirectEntryModifiedDateComparator;
import com.liferay.redirect.web.internal.util.comparator.RedirectEntrySourceURLComparator;

import java.text.DateFormat;

import java.util.Date;
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

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_expirationDateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy/MM/dd", _themeDisplay.getLocale());
	}

	public String formatExpirationDate(Date expirationDate) {
		return _expirationDateFormat.format(expirationDate);
	}

	public DropdownItemList getActionDropdownItems(
		RedirectEntry redirectEntry) {

		return DropdownItemListBuilder.add(
			() -> RedirectEntryPermission.contains(
				_themeDisplay.getPermissionChecker(), redirectEntry,
				ActionKeys.UPDATE),
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
			() -> RedirectEntryPermission.contains(
				_themeDisplay.getPermissionChecker(), redirectEntry,
				ActionKeys.DELETE),
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

	public String getExpirationDateinputValue(RedirectEntry redirectEntry) {
		if (redirectEntry == null) {
			return null;
		}

		Date expirationDate = redirectEntry.getExpirationDate();

		if (expirationDate == null) {
			return null;
		}

		DateFormat simpleDateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd", _themeDisplay.getLocale());

		return simpleDateFormat.format(expirationDate);
	}

	public String getGroupBaseURL() {
		StringBuilder groupBaseURL = new StringBuilder();

		groupBaseURL.append(_themeDisplay.getPortalURL());

		Group group = _themeDisplay.getScopeGroup();

		LayoutSet layoutSet = group.getPublicLayoutSet();

		TreeMap<String, String> virtualHostnames =
			layoutSet.getVirtualHostnames();

		if (virtualHostnames.isEmpty() ||
			!_matchesHostname(groupBaseURL, virtualHostnames)) {

			groupBaseURL.append(group.getPathFriendlyURL(false, _themeDisplay));
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

		if (_redirectEntrySearch.isSearch()) {
			_populateWithSearchIndex(_redirectEntrySearch);
		}
		else {
			_populateWithDatabase(_redirectEntrySearch);
		}

		return _redirectEntrySearch;
	}

	private OrderByComparator _getOrderByComparator() {
		boolean orderByAsc = StringUtil.equals(
			_redirectEntrySearch.getOrderByType(), "asc");

		if (Objects.equals(
				_redirectEntrySearch.getOrderByCol(), "source-url")) {

			return new RedirectEntrySourceURLComparator(!orderByAsc);
		}

		if (Objects.equals(
				_redirectEntrySearch.getOrderByCol(), "destination-url")) {

			return new RedirectEntryDestinationURLComparator(!orderByAsc);
		}

		if (Objects.equals(
				_redirectEntrySearch.getOrderByCol(), "modified-date")) {

			return new RedirectEntryModifiedDateComparator(!orderByAsc);
		}

		return new RedirectEntryCreateDateComparator(!orderByAsc);
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

	private void _populateWithDatabase(RedirectEntrySearch redirectEntrySearch)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		redirectEntrySearch.setTotal(
			RedirectEntryServiceUtil.getRedirectEntriesCount(
				themeDisplay.getScopeGroupId()));

		redirectEntrySearch.setResults(
			RedirectEntryServiceUtil.getRedirectEntries(
				themeDisplay.getScopeGroupId(), _redirectEntrySearch.getStart(),
				_redirectEntrySearch.getEnd(), _getOrderByComparator()));
	}

	private void _populateWithSearchIndex(
			RedirectEntrySearch redirectEntrySearch)
		throws PortalException {

		Indexer indexer = IndexerRegistryUtil.getIndexer(RedirectEntry.class);

		SearchContext searchContext = SearchContextFactory.getInstance(
			PortalUtil.getHttpServletRequest(_liferayPortletRequest));

		searchContext.setAttribute(Field.STATUS, WorkflowConstants.STATUS_ANY);
		searchContext.setEnd(redirectEntrySearch.getEnd());
		searchContext.setSorts(_getSorts());
		searchContext.setStart(redirectEntrySearch.getStart());

		Hits hits = indexer.search(searchContext);

		List<SearchResult> searchResults = SearchResultUtil.getSearchResults(
			hits, LocaleUtil.getDefault());

		Stream<SearchResult> stream = searchResults.stream();

		redirectEntrySearch.setResults(
			stream.map(
				SearchResult::getClassPK
			).map(
				RedirectEntryLocalServiceUtil::fetchRedirectEntry
			).collect(
				Collectors.toList()
			));

		redirectEntrySearch.setTotal(hits.getLength());
	}

	private final DateFormat _expirationDateFormat;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private RedirectEntrySearch _redirectEntrySearch;
	private final ThemeDisplay _themeDisplay;

}