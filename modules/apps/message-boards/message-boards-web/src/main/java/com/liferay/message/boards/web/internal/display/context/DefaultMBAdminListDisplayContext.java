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
import com.liferay.message.boards.display.context.MBAdminListDisplayContext;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBCategoryServiceUtil;
import com.liferay.message.boards.service.MBThreadServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.SearchResultUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Sergio González
 */
public class DefaultMBAdminListDisplayContext
	implements MBAdminListDisplayContext {

	public DefaultMBAdminListDisplayContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, long categoryId) {

		_httpServletRequest = httpServletRequest;

		_categoryId = categoryId;
	}

	@Override
	public int getEntriesDelta() {
		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				_httpServletRequest);

		return GetterUtil.getInteger(
			portalPreferences.getValue(
				MBPortletKeys.MESSAGE_BOARDS_ADMIN, "entriesDelta"),
			SearchContainer.DEFAULT_DELTA);
	}

	@Override
	public UUID getUuid() {
		return _UUID;
	}

	@Override
	public boolean isShowSearch() {
		String keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		if (Validator.isNotNull(keywords)) {
			return true;
		}

		String mvcRenderCommandName = ParamUtil.getString(
			_httpServletRequest, "mvcRenderCommandName");

		if (mvcRenderCommandName.equals("/message_boards/search")) {
			return true;
		}

		return false;
	}

	@Override
	public void populateResultsAndTotal(SearchContainer searchContainer)
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

			String orderByCol = searchContainer.getOrderByCol();
			String orderByType = searchContainer.getOrderByType();

			Sort sort = null;

			boolean orderByAsc = false;

			if (Objects.equals(orderByType, "asc")) {
				orderByAsc = true;
			}

			if (Objects.equals(orderByCol, "modified-date")) {
				sort = new Sort(
					Field.MODIFIED_DATE, Sort.LONG_TYPE, !orderByAsc);
			}
			else if (Objects.equals(orderByCol, "title")) {
				String sortFieldName = Field.getSortableFieldName(
					"localized_title_".concat(themeDisplay.getLanguageId()));

				sort = new Sort(sortFieldName, Sort.STRING_TYPE, !orderByAsc);
			}

			searchContext.setSorts(sort);

			searchContext.setStart(searchContainer.getStart());

			Hits hits = indexer.search(searchContext);

			searchContainer.setResults(
				SearchResultUtil.getSearchResults(
					hits, _httpServletRequest.getLocale()));

			searchContainer.setTotal(hits.getLength());
		}
		else {
			String entriesNavigation = ParamUtil.getString(
				_httpServletRequest, "entriesNavigation", "all");

			if (Objects.equals(entriesNavigation, "all")) {
				int status = WorkflowConstants.STATUS_APPROVED;

				PermissionChecker permissionChecker =
					themeDisplay.getPermissionChecker();

				if (permissionChecker.isContentReviewer(
						themeDisplay.getCompanyId(),
						themeDisplay.getScopeGroupId())) {

					status = WorkflowConstants.STATUS_ANY;
				}

				QueryDefinition<?> queryDefinition = new QueryDefinition<>(
					status, themeDisplay.getUserId(), true,
					searchContainer.getStart(), searchContainer.getEnd(),
					searchContainer.getOrderByComparator());

				searchContainer.setTotal(
					MBCategoryServiceUtil.getCategoriesAndThreadsCount(
						themeDisplay.getScopeGroupId(), _categoryId,
						queryDefinition));
				searchContainer.setResults(
					MBCategoryServiceUtil.getCategoriesAndThreads(
						themeDisplay.getScopeGroupId(), _categoryId,
						queryDefinition));
			}
			else if (Objects.equals(entriesNavigation, "threads")) {
				int status = WorkflowConstants.STATUS_APPROVED;

				PermissionChecker permissionChecker =
					themeDisplay.getPermissionChecker();

				if (permissionChecker.isContentReviewer(
						themeDisplay.getCompanyId(),
						themeDisplay.getScopeGroupId())) {

					status = WorkflowConstants.STATUS_ANY;
				}

				QueryDefinition<MBThread> queryDefinition =
					new QueryDefinition<>(
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
			else if (Objects.equals(entriesNavigation, "categories")) {
				int status = WorkflowConstants.STATUS_APPROVED;

				PermissionChecker permissionChecker =
					themeDisplay.getPermissionChecker();

				if (permissionChecker.isContentReviewer(
						themeDisplay.getCompanyId(),
						themeDisplay.getScopeGroupId())) {

					status = WorkflowConstants.STATUS_ANY;
				}

				QueryDefinition<MBCategory> queryDefinition =
					new QueryDefinition<>(
						status, themeDisplay.getUserId(), true,
						searchContainer.getStart(), searchContainer.getEnd(),
						searchContainer.getOrderByComparator());

				searchContainer.setTotal(
					MBCategoryServiceUtil.getCategoriesCount(
						themeDisplay.getScopeGroupId(), _categoryId,
						queryDefinition));
				searchContainer.setResults(
					MBCategoryServiceUtil.getCategories(
						themeDisplay.getScopeGroupId(), _categoryId,
						queryDefinition));
			}
		}
	}

	@Override
	public void setEntriesDelta(SearchContainer searchContainer) {
		int entriesDelta = ParamUtil.getInteger(
			_httpServletRequest, searchContainer.getDeltaParam());

		if (entriesDelta > 0) {
			PortalPreferences portalPreferences =
				PortletPreferencesFactoryUtil.getPortalPreferences(
					_httpServletRequest);

			portalPreferences.setValue(
				MBPortletKeys.MESSAGE_BOARDS_ADMIN, "entriesDelta",
				String.valueOf(entriesDelta));
		}
	}

	private static final UUID _UUID = UUID.fromString(
		"f3efa0bd-ca31-43c5-bdfe-164ee683b39e");

	private final long _categoryId;
	private final HttpServletRequest _httpServletRequest;

}