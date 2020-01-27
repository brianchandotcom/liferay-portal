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

package com.liferay.depot.web.internal.display.context;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.depot.web.internal.constants.DepotAdminWebKeys;
import com.liferay.depot.web.internal.search.DepotEntrySearch;
import com.liferay.depot.web.internal.servlet.taglib.clay.DepotEntryVerticalCard;
import com.liferay.depot.web.internal.servlet.taglib.util.DepotActionDropdownItemsProvider;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
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
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.site.util.GroupURLProvider;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class DepotAdminDisplayContext {

	public DepotAdminDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_depotEntryLocalService =
			(DepotEntryLocalService)httpServletRequest.getAttribute(
				DepotEntryLocalService.class.getName());
		_groupURLProvider = (GroupURLProvider)httpServletRequest.getAttribute(
			DepotAdminWebKeys.DEPOT_ADMIN_GROUP_URL_PROVIDER);
	}

	public List<DropdownItem> getActionDropdownItems(DepotEntry depotEntry) {
		DepotActionDropdownItemsProvider depotActionDropdownItemsProvider =
			new DepotActionDropdownItemsProvider(
				depotEntry, _liferayPortletRequest, _liferayPortletResponse);

		return depotActionDropdownItemsProvider.getActionDropdownItems();
	}

	public String getDefaultDisplayStyle() {
		return "icon";
	}

	public DepotEntryVerticalCard getDepotEntryVerticalCard(
			DepotEntry depotEntry)
		throws PortalException {

		SearchContainer<DepotEntry> searchContainer = searchContainer();

		return new DepotEntryVerticalCard(
			depotEntry, _groupURLProvider, _liferayPortletRequest,
			_liferayPortletResponse, searchContainer.getRowChecker());
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		_displayStyle = ParamUtil.getString(
			_liferayPortletRequest, "displayStyle", getDefaultDisplayStyle());

		return _displayStyle;
	}

	public PortletURL getIteratorURL() throws PortalException {
		SearchContainer<DepotEntry> searchContainer = searchContainer();

		return searchContainer.getIteratorURL();
	}

	public String getSearchContainerId() {
		return "depotEntries";
	}

	public String getViewDepotURL(DepotEntry depotEntry)
		throws PortalException {

		return _groupURLProvider.getGroupURL(
			depotEntry.getGroup(), _liferayPortletRequest);
	}

	public boolean isDisplayStyleDescriptive() {
		return Objects.equals(getDisplayStyle(), "descriptive");
	}

	public boolean isDisplayStyleIcon() {
		return Objects.equals(getDisplayStyle(), "icon");
	}

	public SearchContainer<DepotEntry> searchContainer()
		throws PortalException {

		if (_depotEntrySearch != null) {
			return _depotEntrySearch;
		}

		_depotEntrySearch = new DepotEntrySearch(
			_liferayPortletRequest, _liferayPortletResponse, _getPortletURL(),
			getSearchContainerId());

		Indexer indexer = IndexerRegistryUtil.getIndexer(DepotEntry.class);

		SearchContext searchContext = SearchContextFactory.getInstance(
			PortalUtil.getHttpServletRequest(_liferayPortletRequest));

		searchContext.setAttribute(Field.STATUS, WorkflowConstants.STATUS_ANY);
		searchContext.setEnd(_depotEntrySearch.getEnd());
		searchContext.setGroupIds(null);
		searchContext.setSorts(
			new Sort(
				Field.NAME, Sort.STRING_TYPE,
				StringUtil.equals(_depotEntrySearch.getOrderByType(), "asc")));
		searchContext.setStart(_depotEntrySearch.getStart());

		Hits hits = indexer.search(searchContext);

		List<SearchResult> searchResults = SearchResultUtil.getSearchResults(
			hits, LocaleUtil.getDefault());

		Stream<SearchResult> stream = searchResults.stream();

		_depotEntrySearch.setResults(
			stream.map(
				SearchResult::getClassPK
			).map(
				_depotEntryLocalService::fetchDepotEntry
			).collect(
				Collectors.toList()
			));

		_depotEntrySearch.setTotal(hits.getLength());

		return _depotEntrySearch;
	}

	private PortletURL _getPortletURL() {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		portletURL.setParameter("displayStyle", getDisplayStyle());

		return portletURL;
	}

	private final DepotEntryLocalService _depotEntryLocalService;
	private DepotEntrySearch _depotEntrySearch;
	private String _displayStyle;
	private final GroupURLProvider _groupURLProvider;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;

}