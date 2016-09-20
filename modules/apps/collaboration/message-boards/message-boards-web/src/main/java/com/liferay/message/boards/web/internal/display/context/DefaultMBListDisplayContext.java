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

import com.liferay.message.boards.display.context.MBListDisplayContext;
import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.message.boards.kernel.service.MBCategoryLocalServiceUtil;
import com.liferay.message.boards.kernel.service.MBCategoryServiceUtil;
import com.liferay.message.boards.kernel.service.MBThreadServiceUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
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
import com.liferay.portlet.messageboards.MBGroupServiceSettings;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Roberto Díaz
 */
public class DefaultMBListDisplayContext implements MBListDisplayContext {

	public DefaultMBListDisplayContext(
		HttpServletRequest request, HttpServletResponse response,
		long categoryId) {

		_request = request;

		_categoryId = categoryId;
	}

	@Override
	public UUID getUuid() {
		return _UUID;
	}

	@Override
	public boolean isShowMyPosts() {
		String mvcRenderCommandName = ParamUtil.getString(
			_request, "mvcRenderCommandName");

		if (mvcRenderCommandName.equals("/message_boards/view_my_posts")) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isShowRecentPosts() {
		String mvcRenderCommandName = ParamUtil.getString(
			_request, "mvcRenderCommandName");

		if (mvcRenderCommandName.equals("/message_boards/view_recent_posts")) {
			return true;
		}

		String entriesNavigation = ParamUtil.getString(
			_request, "entriesNavigation");

		if (entriesNavigation.equals("recent")) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isShowSearch() {
		String keywords = ParamUtil.getString(_request, "keywords");

		if (Validator.isNotNull(keywords)) {
			return true;
		}

		return false;
	}

	@Override
	public void populateResultsAndTotal(SearchContainer searchContainer)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (isShowSearch()) {
			long searchCategoryId = ParamUtil.getLong(
				_request, "searchCategoryId");

			long[] categoryIdsArray = null;

			List categoryIds = new ArrayList();

			categoryIds.add(Long.valueOf(searchCategoryId));

			MBCategoryServiceUtil.getSubcategoryIds(
				categoryIds, themeDisplay.getScopeGroupId(), searchCategoryId);

			categoryIdsArray = StringUtil.split(
				StringUtil.merge(categoryIds), 0L);

			Indexer indexer = IndexerRegistryUtil.getIndexer(MBMessage.class);

			SearchContext searchContext = SearchContextFactory.getInstance(
				_request);

			searchContext.setAttribute("paginationType", "more");
			searchContext.setCategoryIds(categoryIdsArray);
			searchContext.setEnd(searchContainer.getEnd());
			searchContext.setIncludeAttachments(true);

			String keywords = ParamUtil.getString(_request, "keywords");

			searchContext.setKeywords(keywords);

			searchContext.setStart(searchContainer.getStart());

			Hits hits = indexer.search(searchContext);

			searchContainer.setResults(
				SearchResultUtil.getSearchResults(hits, _request.getLocale()));

			searchContainer.setSearch(true);
			searchContainer.setTotal(hits.getLength());
		}
		else if (isShowRecentPosts()) {
			searchContainer.setEmptyResultsMessage("there-are-no-recent-posts");

			long groupThreadsUserId = ParamUtil.getLong(
				_request, "groupThreadsUserId");

			Calendar calendar = Calendar.getInstance();

			MBGroupServiceSettings mbGroupServiceSettings =
				MBGroupServiceSettings.getInstance(
					themeDisplay.getSiteGroupId());

			int offset = GetterUtil.getInteger(
				mbGroupServiceSettings.getRecentPostsDateOffset());

			calendar.add(Calendar.DATE, -offset);

			searchContainer.setTotal(
				MBThreadServiceUtil.getGroupThreadsCount(
					themeDisplay.getScopeGroupId(), groupThreadsUserId,
					calendar.getTime(), WorkflowConstants.STATUS_APPROVED));
			searchContainer.setResults(
				MBThreadServiceUtil.getGroupThreads(
					themeDisplay.getScopeGroupId(), groupThreadsUserId,
					calendar.getTime(), WorkflowConstants.STATUS_APPROVED,
					searchContainer.getStart(), searchContainer.getEnd()));
		}
		else if (isShowMyPosts()) {
			long groupThreadsUserId = ParamUtil.getLong(
				_request, "groupThreadsUserId");

			if (themeDisplay.isSignedIn()) {
				groupThreadsUserId = themeDisplay.getUserId();
			}

			int status = WorkflowConstants.STATUS_ANY;

			searchContainer.setTotal(
				MBThreadServiceUtil.getGroupThreadsCount(
					themeDisplay.getScopeGroupId(), groupThreadsUserId,
					status));
			searchContainer.setResults(
				MBThreadServiceUtil.getGroupThreads(
					themeDisplay.getScopeGroupId(), groupThreadsUserId, status,
					searchContainer.getStart(), searchContainer.getEnd()));

			searchContainer.setEmptyResultsMessage("you-do-not-have-any-posts");
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

			searchContainer.setTotal(
				MBCategoryLocalServiceUtil.getCategoriesAndThreadsCount(
					themeDisplay.getScopeGroupId(), _categoryId, status));
			searchContainer.setResults(
				MBCategoryServiceUtil.getCategoriesAndThreads(
					themeDisplay.getScopeGroupId(), _categoryId, status,
					searchContainer.getStart(), searchContainer.getEnd(),
					searchContainer.getOrderByComparator()));
		}
	}

	private static final UUID _UUID = UUID.fromString(
		"c29b2669-a9ce-45e3-aa4e-9ec766a4ffad");

	private final long _categoryId;
	private final HttpServletRequest _request;

}