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

package com.liferay.message.boards.web.internal.display.context;

import com.liferay.message.boards.constants.MBPortletKeys;
import com.liferay.message.boards.display.context.MBListDisplayContext;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBCategoryServiceUtil;
import com.liferay.message.boards.service.MBThreadServiceUtil;
import com.liferay.message.boards.settings.MBGroupServiceSettings;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.SearchResultUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Roberto Díaz
 */
public class DefaultMBListDisplayContext implements MBListDisplayContext {

	public DefaultMBListDisplayContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, long categoryId,
		String mvcRenderCommandName) {

		_httpServletRequest = httpServletRequest;

		_categoryId = categoryId;

		boolean showMyPosts = false;
		boolean showRecentPosts = false;
		boolean showSearch = false;

		if (mvcRenderCommandName.equals("/message_boards/view_my_posts")) {
			showMyPosts = true;
		}
		else if (_isShowRecentPosts(mvcRenderCommandName)) {
			showRecentPosts = true;
		}
		else if (_isShowSearch(mvcRenderCommandName)) {
			showSearch = true;
		}

		_showMyPosts = showMyPosts;
		_showRecentPosts = showRecentPosts;
		_showSearch = showSearch;
	}

	@Override
	public int getCategoryEntriesDelta() {
		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				_httpServletRequest);

		return GetterUtil.getInteger(
			portalPreferences.getValue(
				MBPortletKeys.MESSAGE_BOARDS, "categoryEntriesDelta"),
			SearchContainer.DEFAULT_DELTA);
	}

	@Override
	public int getThreadEntriesDelta() {
		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				_httpServletRequest);

		return GetterUtil.getInteger(
			portalPreferences.getValue(
				MBPortletKeys.MESSAGE_BOARDS, "threadEntriesDelta"),
			SearchContainer.DEFAULT_DELTA);
	}

	@Override
	public UUID getUuid() {
		return _UUID;
	}

	@Override
	public boolean isShowMyPosts() {
		return _showMyPosts;
	}

	@Override
	public boolean isShowRecentPosts() {
		return _showRecentPosts;
	}

	@Override
	public boolean isShowSearch() {
		return _showSearch;
	}

	@Override
	public void populateCategoriesResultsAndTotal(
			SearchContainer searchContainer)
		throws PortalException {

		if (isShowSearch()) {
			return;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		int status = WorkflowConstants.STATUS_APPROVED;

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (permissionChecker.isContentReviewer(
				themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId())) {

			status = WorkflowConstants.STATUS_ANY;
		}

		QueryDefinition<MBCategory> queryDefinition = new QueryDefinition<>(
			status, themeDisplay.getUserId(), true, searchContainer.getStart(),
			searchContainer.getEnd(), searchContainer.getOrderByComparator());

		searchContainer.setTotal(
			MBCategoryServiceUtil.getCategoriesCount(
				themeDisplay.getScopeGroupId(), _categoryId, queryDefinition));
		searchContainer.setResults(
			MBCategoryServiceUtil.getCategories(
				themeDisplay.getScopeGroupId(), _categoryId, queryDefinition));
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public void populateResultsAndTotal(SearchContainer searchContainer)
		throws PortalException {

		populateThreadsResultsAndTotal(searchContainer);
	}

	@Override
	public void populateThreadsResultsAndTotal(SearchContainer searchContainer)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (isShowSearch()) {
			long searchCategoryId = ParamUtil.getLong(
				_httpServletRequest, "searchCategoryId");

			long[] categoryIdsArray = null;

			List categoryIds = new ArrayList();

			categoryIds.add(Long.valueOf(searchCategoryId));

			MBCategoryServiceUtil.getSubcategoryIds(
				categoryIds, themeDisplay.getScopeGroupId(), searchCategoryId);

			categoryIdsArray = StringUtil.split(
				StringUtil.merge(categoryIds), 0L);

			Indexer indexer = IndexerRegistryUtil.getIndexer(MBMessage.class);

			SearchContext searchContext = SearchContextFactory.getInstance(
				_httpServletRequest);

			searchContext.setAttribute("paginationType", "more");
			searchContext.setCategoryIds(categoryIdsArray);
			searchContext.setEnd(searchContainer.getEnd());
			searchContext.setIncludeAttachments(true);

			String keywords = ParamUtil.getString(
				_httpServletRequest, "keywords");

			searchContext.setKeywords(keywords);

			searchContext.setStart(searchContainer.getStart());

			Hits hits = indexer.search(searchContext);

			searchContainer.setResults(
				SearchResultUtil.getSearchResults(
					hits, _httpServletRequest.getLocale()));

			searchContainer.setTotal(hits.getLength());
		}
		else if (isShowRecentPosts()) {
			searchContainer.setEmptyResultsMessage("there-are-no-recent-posts");

			long groupThreadsUserId = ParamUtil.getLong(
				_httpServletRequest, "groupThreadsUserId");

			Calendar calendar = Calendar.getInstance();

			MBGroupServiceSettings mbGroupServiceSettings =
				MBGroupServiceSettings.getInstance(
					themeDisplay.getSiteGroupId());

			int offset = GetterUtil.getInteger(
				mbGroupServiceSettings.getRecentPostsDateOffset());

			calendar.add(Calendar.DATE, -offset);

			boolean includeAnonymous = false;

			if (groupThreadsUserId == themeDisplay.getUserId()) {
				includeAnonymous = true;
			}

			searchContainer.setTotal(
				MBThreadServiceUtil.getGroupThreadsCount(
					themeDisplay.getScopeGroupId(), groupThreadsUserId,
					calendar.getTime(), includeAnonymous,
					WorkflowConstants.STATUS_APPROVED));
			searchContainer.setResults(
				MBThreadServiceUtil.getGroupThreads(
					themeDisplay.getScopeGroupId(), groupThreadsUserId,
					calendar.getTime(), includeAnonymous,
					WorkflowConstants.STATUS_APPROVED,
					searchContainer.getStart(), searchContainer.getEnd()));
		}
		else if (isShowMyPosts()) {
			searchContainer.setEmptyResultsMessage("you-do-not-have-any-posts");

			if (!themeDisplay.isSignedIn()) {
				searchContainer.setTotal(0);
				searchContainer.setResults(Collections.emptyList());

				return;
			}

			int status = WorkflowConstants.STATUS_ANY;

			searchContainer.setTotal(
				MBThreadServiceUtil.getGroupThreadsCount(
					themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
					status));
			searchContainer.setResults(
				MBThreadServiceUtil.getGroupThreads(
					themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
					status, searchContainer.getStart(),
					searchContainer.getEnd()));
		}
		else {
			int status = WorkflowConstants.STATUS_APPROVED;

			PermissionChecker permissionChecker =
				themeDisplay.getPermissionChecker();

			if (permissionChecker.isContentReviewer(
					themeDisplay.getCompanyId(),
					themeDisplay.getScopeGroupId())) {

				status = WorkflowConstants.STATUS_ANY;
			}

			QueryDefinition<MBThread> queryDefinition = new QueryDefinition<>(
				status, themeDisplay.getUserId(), true,
				searchContainer.getStart(), searchContainer.getEnd(),
				searchContainer.getOrderByComparator());

			searchContainer.setTotal(
				MBThreadServiceUtil.getThreadsCount(
					themeDisplay.getScopeGroupId(), _categoryId,
					queryDefinition));
			searchContainer.setResults(
				MBThreadServiceUtil.getThreads(
					themeDisplay.getScopeGroupId(), _categoryId,
					queryDefinition));
		}
	}

	@Override
	public void setCategoryEntriesDelta(SearchContainer searchContainer) {
		int categoryEntriesDelta = ParamUtil.getInteger(
			_httpServletRequest, searchContainer.getDeltaParam());

		if (categoryEntriesDelta > 0) {
			PortalPreferences portalPreferences =
				PortletPreferencesFactoryUtil.getPortalPreferences(
					_httpServletRequest);

			portalPreferences.setValue(
				MBPortletKeys.MESSAGE_BOARDS, "categoryEntriesDelta",
				String.valueOf(categoryEntriesDelta));
		}
	}

	@Override
	public void setThreadEntriesDelta(SearchContainer searchContainer) {
		int threadEntriesDelta = ParamUtil.getInteger(
			_httpServletRequest, searchContainer.getDeltaParam());

		if (threadEntriesDelta > 0) {
			PortalPreferences portalPreferences =
				PortletPreferencesFactoryUtil.getPortalPreferences(
					_httpServletRequest);

			portalPreferences.setValue(
				MBPortletKeys.MESSAGE_BOARDS, "threadEntriesDelta",
				String.valueOf(threadEntriesDelta));
		}
	}

	private boolean _isShowRecentPosts(String mvcRenderCommandName) {
		if (mvcRenderCommandName.equals("/message_boards/view_recent_posts")) {
			return true;
		}

		String entriesNavigation = ParamUtil.getString(
			_httpServletRequest, "entriesNavigation");

		if (entriesNavigation.equals("recent")) {
			return true;
		}

		return false;
	}

	private boolean _isShowSearch(String mvcRenderCommandName) {
		if (Validator.isNotNull(
				ParamUtil.getString(_httpServletRequest, "keywords"))) {

			return true;
		}

		if (mvcRenderCommandName.equals("/message_boards/search")) {
			return true;
		}

		return false;
	}

	private static final UUID _UUID = UUID.fromString(
		"c29b2669-a9ce-45e3-aa4e-9ec766a4ffad");

	private final long _categoryId;
	private final HttpServletRequest _httpServletRequest;
	private final boolean _showMyPosts;
	private final boolean _showRecentPosts;
	private final boolean _showSearch;

}