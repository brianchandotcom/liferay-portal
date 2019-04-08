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

package com.liferay.blogs.web.internal.display.context;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.blogs.constants.BlogsPortletKeys;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.blogs.service.BlogsEntryServiceUtil;
import com.liferay.blogs.web.internal.security.permission.resource.BlogsEntryPermission;
import com.liferay.blogs.web.internal.util.BlogsUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.dao.search.SearchContainerResults;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.trash.TrashHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sergio González
 */
public class BlogEntriesDisplayContext {

	public BlogEntriesDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		TrashHelper trashHelper) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_trashHelper = trashHelper;

		_portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			liferayPortletRequest);

		_request = _liferayPortletRequest.getHttpServletRequest();
	}

	public List<String> getAvailableActionDropdownItems(BlogsEntry blogsEntry)
		throws PortalException {

		List<String> availableActionDropdownItems = new ArrayList<>();

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (BlogsEntryPermission.contains(
				permissionChecker, blogsEntry, ActionKeys.DELETE)) {

			availableActionDropdownItems.add("deleteEntries");
		}

		return availableActionDropdownItems;
	}

	public Map<String, Object> getComponentContext() throws PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<String, Object> context = new HashMap<>();

		context.put(
			"trashEnabled",
			_trashHelper.isTrashEnabled(themeDisplay.getScopeGroupId()));

		return context;
	}

	public String getDisplayStyle() {
		String displayStyle = ParamUtil.getString(_request, "displayStyle");

		if (Validator.isNull(displayStyle)) {
			displayStyle = _portalPreferences.getValue(
				BlogsPortletKeys.BLOGS_ADMIN, "entries-display-style", "icon");
		}
		else {
			_portalPreferences.setValue(
				BlogsPortletKeys.BLOGS_ADMIN, "entries-display-style",
				displayStyle);

			_request.setAttribute(
				WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
		}

		return displayStyle;
	}

	public SearchContainer getSearchContainer()
		throws PortalException, PortletException {

		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", "/blogs/view");

		String entriesNavigation = ParamUtil.getString(
			_request, "entriesNavigation");

		portletURL.setParameter("entriesNavigation", entriesNavigation);

		SearchContainer<BlogsEntry> entriesSearchContainer =
			new SearchContainer<>(
				_liferayPortletRequest,
				PortletURLUtil.clone(portletURL, _liferayPortletResponse), null,
				"no-entries-were-found");

		String orderByCol = ParamUtil.getString(
			_request, "orderByCol", "title");

		entriesSearchContainer.setOrderByCol(orderByCol);

		String orderByType = ParamUtil.getString(
			_request, "orderByType", "asc");

		entriesSearchContainer.setOrderByType(orderByType);

		entriesSearchContainer.setOrderByComparator(
			BlogsUtil.getOrderByComparator(
				entriesSearchContainer.getOrderByCol(),
				entriesSearchContainer.getOrderByType()));

		entriesSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_liferayPortletResponse));

		_populateResults(entriesSearchContainer);

		return entriesSearchContainer;
	}

	private int _getStatus() {
		if (_status != null) {
			return _status;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (permissionChecker.isContentReviewer(
				themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId())) {

			_status = WorkflowConstants.STATUS_ANY;
		}
		else {
			_status = WorkflowConstants.STATUS_APPROVED;
		}

		return _status;
	}

	private void _populateResults(SearchContainer searchContainer)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		List entriesResults = null;

		long assetCategoryId = ParamUtil.getLong(_request, "categoryId");
		String assetTagName = ParamUtil.getString(_request, "tag");

		String keywords = ParamUtil.getString(_request, "keywords");

		if ((assetCategoryId != 0) || Validator.isNotNull(assetTagName)) {
			SearchContainerResults<AssetEntry> searchContainerResults =
				BlogsUtil.getSearchContainerResults(searchContainer);

			searchContainer.setTotal(searchContainerResults.getTotal());

			List<AssetEntry> assetEntries = searchContainerResults.getResults();

			entriesResults = new ArrayList<>(assetEntries.size());

			for (AssetEntry assetEntry : assetEntries) {
				entriesResults.add(
					BlogsEntryLocalServiceUtil.getEntry(
						assetEntry.getClassPK()));
			}
		}
		else if (Validator.isNull(keywords)) {
			String entriesNavigation = ParamUtil.getString(
				_request, "entriesNavigation");

			if (entriesNavigation.equals("mine")) {
				searchContainer.setTotal(
					BlogsEntryServiceUtil.getGroupUserEntriesCount(
						themeDisplay.getScopeGroupId(),
						themeDisplay.getUserId(),
						WorkflowConstants.STATUS_ANY));
			}
			else {
				searchContainer.setTotal(
					BlogsEntryServiceUtil.getGroupEntriesCount(
						themeDisplay.getScopeGroupId(),
						WorkflowConstants.STATUS_ANY));
			}

			if (entriesNavigation.equals("mine")) {
				entriesResults = BlogsEntryServiceUtil.getGroupUserEntries(
					themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
					WorkflowConstants.STATUS_ANY, searchContainer.getStart(),
					searchContainer.getEnd(),
					searchContainer.getOrderByComparator());
			}
			else {
				entriesResults = BlogsEntryServiceUtil.getGroupEntries(
					themeDisplay.getScopeGroupId(),
					WorkflowConstants.STATUS_ANY, searchContainer.getStart(),
					searchContainer.getEnd(),
					searchContainer.getOrderByComparator());
			}
		}
		else {
			Indexer indexer = IndexerRegistryUtil.getIndexer(BlogsEntry.class);

			SearchContext searchContext = SearchContextFactory.getInstance(
				_request);

			searchContext.setAttribute(Field.STATUS, _getStatus());
			searchContext.setEnd(searchContainer.getEnd());
			searchContext.setIncludeDiscussions(true);
			searchContext.setKeywords(keywords);
			searchContext.setStart(searchContainer.getStart());

			String entriesNavigation = ParamUtil.getString(
				_request, "entriesNavigation");

			if (entriesNavigation.equals("mine")) {
				searchContext.setOwnerUserId(themeDisplay.getUserId());
			}

			String orderByCol = ParamUtil.getString(
				_request, "orderByCol", "title");
			String orderByType = ParamUtil.getString(
				_request, "orderByType", "asc");

			Sort sort = null;

			boolean orderByAsc = true;

			if (Objects.equals(orderByType, "asc")) {
				orderByAsc = false;
			}

			if ("display-date".equals(orderByCol)) {
				sort = new Sort(Field.DISPLAY_DATE, Sort.LONG_TYPE, orderByAsc);
			}
			else {
				sort = new Sort(orderByCol, orderByAsc);
			}

			searchContext.setSorts(sort);

			Hits hits = indexer.search(searchContext);

			entriesResults = new ArrayList<>(hits.getLength());

			searchContainer.setTotal(hits.getLength());

			Document[] docs = hits.getDocs();

			for (int i = 0; i < docs.length; i++) {
				Document doc = hits.doc(i);

				long entryId = GetterUtil.getLong(doc.get(Field.CLASS_PK));

				BlogsEntry entry = null;

				try {
					entry = BlogsEntryServiceUtil.getEntry(entryId);

					entry = entry.toEscapedModel();
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Blogs search index is stale and contains entry " +
								entryId);
					}

					continue;
				}

				entriesResults.add(entry);
			}
		}

		searchContainer.setResults(entriesResults);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BlogEntriesDisplayContext.class);

	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final PortalPreferences _portalPreferences;
	private final HttpServletRequest _request;
	private Integer _status;
	private final TrashHelper _trashHelper;

}