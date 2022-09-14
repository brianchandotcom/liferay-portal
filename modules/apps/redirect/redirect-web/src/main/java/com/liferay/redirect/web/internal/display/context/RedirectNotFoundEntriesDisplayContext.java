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
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
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
import com.liferay.staging.StagingGroupHelper;
import com.liferay.staging.StagingGroupHelperUtil;

import java.time.Duration;
import java.time.Instant;

import java.util.Date;
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
		LiferayPortletResponse liferayPortletResponse,
		RedirectNotFoundEntryLocalService redirectNotFoundEntryLocalService) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_redirectNotFoundEntryLocalService = redirectNotFoundEntryLocalService;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public DropdownItemList getActionDropdownItems(
		RedirectNotFoundEntry redirectNotFoundEntry) {

		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setHref(
					PortletURLBuilder.createActionURL(
						_liferayPortletResponse
					).setActionName(
						"/redirect/edit_redirect_not_found_entry"
					).setRedirect(
						_themeDisplay.getURLCurrent()
					).setParameter(
						"ignored", !redirectNotFoundEntry.isIgnored()
					).setParameter(
						"redirectNotFoundEntryId",
						redirectNotFoundEntry.getRedirectNotFoundEntryId()
					).buildActionURL());

				String label = "ignore";

				if (redirectNotFoundEntry.isIgnored()) {
					label = "unignore";
				}

				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, label));
			}
		).add(
			() -> {
				StagingGroupHelper stagingGroupHelper =
					StagingGroupHelperUtil.getStagingGroupHelper();

				return RedirectPermission.contains(
					_themeDisplay.getPermissionChecker(),
					_themeDisplay.getScopeGroupId(), ActionKeys.ADD_ENTRY) &&
					   !(stagingGroupHelper.isLocalStagingGroup(
						   redirectNotFoundEntry.getGroupId()) ||
						 stagingGroupHelper.isRemoteStagingGroup(
							 redirectNotFoundEntry.getGroupId()));
			},
			dropdownItem -> {
				dropdownItem.setHref(
					PortletURLBuilder.createRenderURL(
						_liferayPortletResponse
					).setMVCRenderCommandName(
						"/redirect/edit_redirect_entry"
					).setRedirect(
						_themeDisplay.getURLCurrent()
					).setParameter(
						"sourceURL", redirectNotFoundEntry.getUrl()
					).buildRenderURL());

				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "create-redirect"));
			}
		).build();
	}

	public RedirectNotFoundEntriesManagementToolbarDisplayContext
			getRedirectNotFoundEntriesManagementToolbarDisplayContext()
		throws Exception {

		return new RedirectNotFoundEntriesManagementToolbarDisplayContext(
			_httpServletRequest, _liferayPortletRequest,
			_liferayPortletResponse, _redirectNotFoundEntryLocalService,
			searchContainer());
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

		_populateWithSearchIndex(_redirectNotFoundEntrySearch);

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

	private PortletURL _getPortletURL() throws Exception {
		return PortletURLUtil.clone(
			PortletURLUtil.getCurrent(
				_liferayPortletRequest, _liferayPortletResponse),
			_liferayPortletResponse);
	}

	private Sort _getSorts() {
		if (Objects.equals(
				_redirectNotFoundEntrySearch.getOrderByCol(),
				"modified-date")) {

			return new Sort(
				Field.getSortableFieldName(Field.MODIFIED_DATE), Sort.LONG_TYPE,
				StringUtil.equals(
					_redirectNotFoundEntrySearch.getOrderByType(), "asc"));
		}

		return new Sort(
			Field.getSortableFieldName("requestCount"), Sort.LONG_TYPE,
			StringUtil.equals(
				_redirectNotFoundEntrySearch.getOrderByType(), "asc"));
	}

	private void _populateWithSearchIndex(
			RedirectNotFoundEntrySearch redirectNotFoundEntrySearch)
		throws Exception {

		Indexer<RedirectNotFoundEntry> indexer = IndexerRegistryUtil.getIndexer(
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

		redirectNotFoundEntrySearch.setResultsAndTotal(
			() -> stream.map(
				SearchResult::getClassPK
			).map(
				_redirectNotFoundEntryLocalService::fetchRedirectNotFoundEntry
			).collect(
				Collectors.toList()
			),
			hits.getLength());
	}

	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final RedirectNotFoundEntryLocalService
		_redirectNotFoundEntryLocalService;
	private RedirectNotFoundEntrySearch _redirectNotFoundEntrySearch;
	private final ThemeDisplay _themeDisplay;

}