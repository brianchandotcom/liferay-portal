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

package com.liferay.portal.search.internal.result;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchResult;
import com.liferay.portal.kernel.search.SearchResultManager;
import com.liferay.portal.kernel.search.SummaryFactory;
import com.liferay.portal.kernel.search.result.SearchResultContributor;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.collections.ServiceReferenceMapper;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.util.HashMap;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Adolfo Pérez
 * @author André de Oliveira
 */
@Component(immediate = true, service = SearchResultManager.class)
public class SearchResultManagerImpl implements SearchResultManager {

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	public void addSearchResultContributor(
		SearchResultContributor searchResultContributor) {

		_searchResultContributors.put(
			searchResultContributor.getEntryClassName(),
			searchResultContributor);
	}

	@Override
	public SearchResult createSearchResult(Document document)
		throws PortalException {

		SearchResultManager searchResultManager = _getSearchResultManager(
			document);

		return searchResultManager.createSearchResult(document);
	}

	public void removeSearchResultContributor(
		SearchResultContributor searchResultContributor) {

		_searchResultContributors.remove(
			searchResultContributor.getEntryClassName());
	}

	@Reference(unbind = "-")
	public void setSummaryFactory(SummaryFactory newSummaryFactory) {
		_summaryFactory = newSummaryFactory;
	}

	@Override
	public void updateSearchResult(
			SearchResult searchResult, Document document, Locale locale,
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws PortalException {

		SearchResultManager searchResultManager = _getSearchResultManager(
			document);

		searchResultManager.updateSearchResult(
			searchResult, document, locale, portletRequest, portletResponse);
	}

	private SearchResultManager _getSearchResultManager(Document document) {
		String entryClassName = GetterUtil.getString(
			document.get(Field.ENTRY_CLASS_NAME));

		SearchResultManager searchResultManager = _serviceTrackerMap.getService(
			entryClassName);

		if (searchResultManager != null) {
			return searchResultManager;
		}

		SearchResultContributor searchResultContributor =
			_searchResultContributors.get(entryClassName);

		if (searchResultContributor != null) {
			return new ContributedSearchResultManager(
				searchResultContributor, _summaryFactory);
		}

		return new DefaultSearchResultManagerImpl(_summaryFactory);
	}

	private final HashMap<String, SearchResultContributor>
		_searchResultContributors = new HashMap<>();

	private final ServiceTrackerMap<String, SearchResultManager>
		_serviceTrackerMap = ServiceTrackerCollections.openSingleValueMap(
			SearchResultManager.class, "(model.class.name=*)",
			new ServiceReferenceMapper<String, SearchResultManager>() {

				@Override
				public void map(
					ServiceReference<SearchResultManager> serviceReference,
					Emitter<String> emitter) {

					Object modelClassName = serviceReference.getProperty(
						"model.class.name");

					emitter.emit((String)modelClassName);
				}

			});

	private volatile SummaryFactory _summaryFactory;

}