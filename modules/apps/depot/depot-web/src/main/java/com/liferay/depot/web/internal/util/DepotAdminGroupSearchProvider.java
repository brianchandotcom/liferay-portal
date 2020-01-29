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

package com.liferay.depot.web.internal.util;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.SearchResult;
import com.liferay.portal.kernel.search.SearchResultUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.usersadmin.search.GroupSearch;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = DepotAdminGroupSearchProvider.class)
public class DepotAdminGroupSearchProvider {

	public GroupSearch getGroupSearch(
			PortletRequest portletRequest, PortletURL portletURL)
		throws SearchException {

		GroupSearch groupSearch = new GroupSearch(portletRequest, portletURL);

		groupSearch.setEmptyResultsMessage(
			LanguageUtil.get(
				ResourceBundleUtil.getBundle(
					portletRequest.getLocale(), getClass()),
				"no-repositories-were-found"));

		Indexer indexer = IndexerRegistryUtil.getIndexer(DepotEntry.class);

		SearchContext searchContext = _getSearchContext(
			portletRequest, groupSearch);

		groupSearch.setResults(_getDepotGroups(indexer, searchContext));
		groupSearch.setTotal((int)indexer.searchCount(searchContext));

		return groupSearch;
	}

	private List<Group> _getDepotGroups(
			Indexer<DepotEntry> indexer, SearchContext searchContext)
		throws SearchException {

		Hits hits = indexer.search(searchContext);

		List<SearchResult> searchResults = SearchResultUtil.getSearchResults(
			hits, LocaleUtil.getDefault());

		Stream<SearchResult> stream = searchResults.stream();

		return stream.map(
			SearchResult::getClassPK
		).map(
			_depotEntryLocalService::fetchDepotEntry
		).map(
			DepotEntry::getGroupId
		).map(
			_groupLocalService::fetchGroup
		).collect(
			Collectors.toList()
		);
	}

	private SearchContext _getSearchContext(
		PortletRequest portletRequest, GroupSearch groupSearch) {

		SearchContext searchContext = SearchContextFactory.getInstance(
			_portal.getHttpServletRequest(portletRequest));

		searchContext.setEnd(groupSearch.getEnd());
		searchContext.setGroupIds(null);
		searchContext.setSorts(
			new Sort(
				Field.NAME, Sort.STRING_TYPE,
				StringUtil.equals(groupSearch.getOrderByType(), "asc")));
		searchContext.setStart(groupSearch.getStart());

		return searchContext;
	}

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

}