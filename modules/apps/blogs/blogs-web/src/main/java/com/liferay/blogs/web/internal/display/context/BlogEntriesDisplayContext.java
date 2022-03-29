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
import com.liferay.blogs.web.internal.servlet.taglib.clay.BlogsEntryVerticalCard;
import com.liferay.blogs.web.internal.util.BlogsUtil;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.RowChecker;
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
import com.liferay.portal.kernel.portlet.SearchDisplayStyleUtil;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
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
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlParser;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.trash.TrashHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sergio González
 */
public class BlogEntriesDisplayContext {

	public BlogEntriesDisplayContext(
		HtmlParser htmlParser, Portal portal, RenderRequest renderRequest,
		RenderResponse renderResponse, TrashHelper trashHelper) {

		_htmlParser = htmlParser;
		_portal = portal;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_trashHelper = trashHelper;

		_liferayPortletRequest = portal.getLiferayPortletRequest(renderRequest);
		_liferayPortletResponse = portal.getLiferayPortletResponse(
			renderResponse);

		_httpServletRequest = _liferayPortletRequest.getHttpServletRequest();
		_portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			_liferayPortletRequest);
	}

	public List<String> getAvailableActions(BlogsEntry blogsEntry)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (BlogsEntryPermission.contains(
				themeDisplay.getPermissionChecker(), blogsEntry,
				ActionKeys.DELETE)) {

			return Collections.singletonList("deleteEntries");
		}

		return Collections.emptyList();
	}

	public BlogsEntryVerticalCard getBlogsEntryVerticalCard(
		BlogsEntry blogsEntry, RowChecker rowChecker, String rowURL,
		ResourceBundle resourceBundle) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return new BlogsEntryVerticalCard(
			blogsEntry, rowURL, _htmlParser,
			themeDisplay.getPermissionChecker(), _renderRequest,
			_renderResponse, resourceBundle, rowChecker, _trashHelper);
	}

	public Map<String, Object> getComponentContext() throws PortalException {
		return HashMapBuilder.<String, Object>put(
			"trashEnabled",
			() -> {
				ThemeDisplay themeDisplay =
					(ThemeDisplay)_httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				return _trashHelper.isTrashEnabled(
					themeDisplay.getScopeGroupId());
			}
		).build();
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		_displayStyle = SearchDisplayStyleUtil.getDisplayStyle(
			_httpServletRequest, BlogsPortletKeys.BLOGS_ADMIN,
			"entries-display-style", "icon", true);

		return _displayStyle;
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = SearchOrderByUtil.getOrderByCol(
			_httpServletRequest, BlogsPortletKeys.BLOGS_ADMIN,
			"entries-order-by-col", _getDefaultOrderByCol());

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = SearchOrderByUtil.getOrderByType(
			_httpServletRequest, BlogsPortletKeys.BLOGS_ADMIN,
			"entries-order-by-type", "asc");

		return _orderByType;
	}

	public SearchContainer<BlogsEntry> getSearchContainer()
		throws PortalException, PortletException {

		PortletURL portletURL = PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCRenderCommandName(
			"/blogs/view"
		).setParameter(
			"entriesNavigation",
			ParamUtil.getString(_httpServletRequest, "entriesNavigation")
		).buildPortletURL();

		SearchContainer<BlogsEntry> entriesSearchContainer =
			new SearchContainer<>(
				_liferayPortletRequest,
				PortletURLUtil.clone(portletURL, _liferayPortletResponse), null,
				"no-entries-were-found");

		entriesSearchContainer.setOrderByCol(getOrderByCol());
		entriesSearchContainer.setOrderByComparator(
			BlogsUtil.getOrderByComparator(getOrderByCol(), getOrderByType()));
		entriesSearchContainer.setOrderByType(getOrderByType());
		entriesSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_liferayPortletResponse));

		_populateResults(entriesSearchContainer);

		return entriesSearchContainer;
	}

	private String _getDefaultOrderByCol() {
		String mvcRenderCommandName = ParamUtil.getString(
			_httpServletRequest, "mvcRenderCommandName");

		if (mvcRenderCommandName.equals("/blogs/search")) {
			return "relevance";
		}

		return "title";
	}

	private void _populateResults(SearchContainer<BlogsEntry> searchContainer)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		if ((ParamUtil.getLong(_httpServletRequest, "categoryId") != 0) ||
			Validator.isNotNull(
				ParamUtil.getString(_httpServletRequest, "tag"))) {

			SearchContainerResults<AssetEntry> searchContainerResults =
				BlogsUtil.getSearchContainerResults(searchContainer);

			List<AssetEntry> assetEntries = searchContainerResults.getResults();

			List<BlogsEntry> entriesResults = new ArrayList<>(
				assetEntries.size());

			for (AssetEntry assetEntry : assetEntries) {
				entriesResults.add(
					BlogsEntryLocalServiceUtil.getEntry(
						assetEntry.getClassPK()));
			}

			searchContainer.setResultsAndTotal(
				() -> entriesResults, searchContainerResults.getTotal());
		}
		else if (Validator.isNull(keywords)) {
			String entriesNavigation = ParamUtil.getString(
				_httpServletRequest, "entriesNavigation");

			if (entriesNavigation.equals("mine")) {
				searchContainer.setResultsAndTotal(
					() -> BlogsEntryServiceUtil.getGroupUserEntries(
						themeDisplay.getScopeGroupId(),
						themeDisplay.getUserId(), WorkflowConstants.STATUS_ANY,
						searchContainer.getStart(), searchContainer.getEnd(),
						searchContainer.getOrderByComparator()),
					BlogsEntryServiceUtil.getGroupUserEntriesCount(
						themeDisplay.getScopeGroupId(),
						themeDisplay.getUserId(),
						WorkflowConstants.STATUS_ANY));
			}
			else {
				searchContainer.setResultsAndTotal(
					() -> BlogsEntryServiceUtil.getGroupEntries(
						themeDisplay.getScopeGroupId(),
						WorkflowConstants.STATUS_ANY,
						searchContainer.getStart(), searchContainer.getEnd(),
						searchContainer.getOrderByComparator()),
					BlogsEntryServiceUtil.getGroupEntriesCount(
						themeDisplay.getScopeGroupId(),
						WorkflowConstants.STATUS_ANY));
			}
		}
		else {
			Indexer<BlogsEntry> indexer = IndexerRegistryUtil.getIndexer(
				BlogsEntry.class);

			SearchContext searchContext = SearchContextFactory.getInstance(
				_httpServletRequest);

			searchContext.setAttribute(
				Field.STATUS, WorkflowConstants.STATUS_ANY);
			searchContext.setEnd(searchContainer.getEnd());
			searchContext.setIncludeDiscussions(true);
			searchContext.setIncludeInternalAssetCategories(true);
			searchContext.setKeywords(keywords);
			searchContext.setStart(searchContainer.getStart());

			String entriesNavigation = ParamUtil.getString(
				_httpServletRequest, "entriesNavigation");

			if (entriesNavigation.equals("mine")) {
				searchContext.setOwnerUserId(themeDisplay.getUserId());
			}

			Sort sort = null;

			boolean orderByAsc = false;

			if (Objects.equals(getOrderByType(), "asc")) {
				orderByAsc = true;
			}

			String orderByCol = getOrderByCol();

			if (Objects.equals(orderByCol, "display-date")) {
				sort = new Sort(
					Field.DISPLAY_DATE, Sort.LONG_TYPE, !orderByAsc);
			}
			else if (Objects.equals(orderByCol, "relevance")) {
				sort = new Sort(null, Sort.SCORE_TYPE, !orderByAsc);
			}
			else {
				sort = new Sort(orderByCol, !orderByAsc);
			}

			searchContext.setSorts(sort);

			Hits hits = indexer.search(searchContext);

			searchContainer.setResultsAndTotal(
				() -> {
					List<SearchResult> searchResults =
						SearchResultUtil.getSearchResults(
							hits, LocaleUtil.getDefault());

					Stream<SearchResult> stream = searchResults.stream();

					return stream.map(
						this::_toBlogsEntryOptional
					).filter(
						Optional::isPresent
					).map(
						Optional::get
					).collect(
						Collectors.toList()
					);
				},
				hits.getLength());
		}
	}

	private Optional<BlogsEntry> _toBlogsEntryOptional(
		SearchResult searchResult) {

		try {
			return Optional.of(
				BlogsEntryServiceUtil.getEntry(searchResult.getClassPK()));
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Blogs search index is stale and contains entry " +
						searchResult.getClassPK(),
					exception);
			}

			return Optional.empty();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BlogEntriesDisplayContext.class);

	private String _displayStyle;
	private final HtmlParser _htmlParser;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _orderByCol;
	private String _orderByType;
	private final Portal _portal;
	private final PortalPreferences _portalPreferences;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final TrashHelper _trashHelper;

}