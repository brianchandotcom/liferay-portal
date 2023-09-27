/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.searcher;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.HitsImpl;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.facet.faceted.searcher.FacetedSearcher;
import com.liferay.portal.kernel.search.facet.faceted.searcher.FacetedSearcherManager;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.constants.SearchContextAttributes;
import com.liferay.portal.search.internal.searcher.helper.IndexSearcherHelper;
import com.liferay.portal.search.legacy.searcher.SearchResponseBuilderFactory;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.SearchResponseBuilder;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.spi.searcher.SearchRequestContributor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(service = Searcher.class)
public class SearcherImpl implements Searcher {

	@Override
	public SearchResponse search(SearchRequest searchRequest) {
		return doSearch(_transformSearchRequest(searchRequest));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, SearchRequestContributor.class,
			"search.request.contributor.id");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	protected SearchResponse doSearch(SearchRequest searchRequest) {
		if (!(searchRequest instanceof SearchRequestImpl)) {
			throw new UnsupportedOperationException();
		}

		SearchRequestImpl searchRequestImpl = (SearchRequestImpl)searchRequest;

		SearchResponseBuilder searchResponseBuilder =
			_searchResponseBuilderFactory.builder(
				searchRequestImpl.getSearchContext());

		SearchContext searchContext = searchRequestImpl.getSearchContext();

		if (Validator.isBlank(StringUtil.trim(searchContext.getKeywords())) &&
			!GetterUtil.getBoolean(
				searchContext.getAttribute(
					SearchContextAttributes.ATTRIBUTE_KEY_EMPTY_SEARCH))) {

			searchResponseBuilder.hits(new HitsImpl());
		}
		else {
			_smartSearch(searchRequestImpl, searchResponseBuilder);
		}

		_federatedSearches(searchRequestImpl, searchResponseBuilder);

		String exceptionMessage = (String)searchContext.getAttribute(
			"search.exception.message");

		if (Validator.isNotNull(exceptionMessage)) {
			searchResponseBuilder.responseString(exceptionMessage);
		}

		return searchResponseBuilder.federatedSearchKey(
			searchRequestImpl.getFederatedSearchKey()
		).request(
			searchRequestImpl
		).build();
	}

	protected Hits search(
		FacetedSearcher facetedSearcher, SearchContext searchContext) {

		try {
			return facetedSearcher.search(searchContext);
		}
		catch (SearchException searchException) {
			throw _uncheck(searchException);
		}
	}

	protected Hits search(Indexer<?> indexer, SearchContext searchContext) {
		try {
			return indexer.search(searchContext);
		}
		catch (SearchException searchException) {
			throw _uncheck(searchException);
		}
	}

	@Reference
	protected FacetedSearcherManager facetedSearcherManager;

	@Reference
	protected IndexSearcherHelper indexSearcherHelper;

	private void _federatedSearches(
		SearchRequest searchRequest,
		SearchResponseBuilder searchResponseBuilder) {

		List<SearchRequest> federatedSearchRequests =
			searchRequest.getFederatedSearchRequests();

		for (SearchRequest federatedSearchRequest : federatedSearchRequests) {
			searchResponseBuilder.addFederatedSearchResponse(
				search(federatedSearchRequest));
		}
	}

	private Collection<Function<SearchRequest, SearchRequest>> _getContributors(
		SearchRequest searchRequest) {

		List<String> contributors = searchRequest.getIncludeContributors();

		if (ListUtil.isEmpty(contributors)) {
			contributors = new ArrayList<>(_serviceTrackerMap.keySet());
		}

		contributors.removeAll(searchRequest.getExcludeContributors());

		Collection<SearchRequestContributor> collection = new ArrayList<>();

		for (String contributor : contributors) {
			collection.addAll(_serviceTrackerMap.getService(contributor));
		}

		return TransformUtil.transform(
			collection,
			searchRequestContributor -> searchRequestContributor::contribute);
	}

	private String _getSingleIndexerClassName(
		SearchRequestImpl searchRequestImpl) {

		List<String> modelIndexerClassNames =
			searchRequestImpl.getModelIndexerClassNames();

		if (modelIndexerClassNames.size() == 1) {
			return modelIndexerClassNames.get(0);
		}

		return null;
	}

	private void _indexerSearch(
		SearchRequestImpl searchRequestImpl,
		SearchResponseBuilder searchResponseBuilder) {

		String singleIndexerClassName = _getSingleIndexerClassName(
			searchRequestImpl);

		if (singleIndexerClassName != null) {
			_singleIndexerSearch(
				singleIndexerClassName, searchRequestImpl,
				searchResponseBuilder);
		}
		else {
			_multiIndexerSearch(searchRequestImpl, searchResponseBuilder);
		}
	}

	private boolean _isCount(SearchRequestImpl searchRequestImpl) {
		if ((searchRequestImpl.getSize() != null) &&
			(searchRequestImpl.getSize() == 0)) {

			return true;
		}

		return false;
	}

	private void _lowLevelSearch(
		SearchRequestImpl searchRequestImpl,
		SearchResponseBuilder searchResponseBuilder) {

		SearchContext searchContext = searchRequestImpl.getSearchContext();

		if (_isCount(searchRequestImpl)) {
			indexSearcherHelper.searchCount(searchContext, null);

			return;
		}

		Hits hits = indexSearcherHelper.search(searchContext, null);

		searchResponseBuilder.hits(hits);
	}

	private void _multiIndexerSearch(
		SearchRequestImpl searchRequestImpl,
		SearchResponseBuilder searchResponseBuilder) {

		FacetedSearcher facetedSearcher =
			facetedSearcherManager.createFacetedSearcher();

		Hits hits = search(
			facetedSearcher, searchRequestImpl.getSearchContext());

		if (_isCount(searchRequestImpl)) {
			searchResponseBuilder.count(hits.getLength());

			return;
		}

		searchResponseBuilder.hits(hits);
	}

	private long _searchCount(Indexer<?> indexer, SearchContext searchContext) {
		try {
			return indexer.searchCount(searchContext);
		}
		catch (SearchException searchException) {
			throw _uncheck(searchException);
		}
	}

	private void _singleIndexerSearch(
		String singleIndexerClassName, SearchRequestImpl searchRequestImpl,
		SearchResponseBuilder searchResponseBuilder) {

		Indexer<?> indexer = _indexerRegistry.getIndexer(
			singleIndexerClassName);

		SearchContext searchContext = searchRequestImpl.getSearchContext();

		if (_isCount(searchRequestImpl)) {
			searchResponseBuilder.count(_searchCount(indexer, searchContext));

			return;
		}

		Hits hits = search(indexer, searchContext);

		searchResponseBuilder.hits(hits);
	}

	private void _smartSearch(
		SearchRequestImpl searchRequestImpl,
		SearchResponseBuilder searchResponseBuilder) {

		List<String> indexes = searchRequestImpl.getIndexes();

		if (indexes.isEmpty()) {
			_indexerSearch(searchRequestImpl, searchResponseBuilder);
		}
		else {
			_lowLevelSearch(searchRequestImpl, searchResponseBuilder);
		}
	}

	private <T> T _transform(T t, Collection<Function<T, T>> collection) {
		Function<T, T> function = Function.identity();

		for (Function<T, T> curFunction : collection) {
			function = function.andThen(curFunction);
		}

		return function.apply(t);
	}

	private SearchRequest _transformSearchRequest(SearchRequest searchRequest) {
		return _transform(searchRequest, _getContributors(searchRequest));
	}

	private RuntimeException _uncheck(SearchException searchException) {
		if (searchException.getCause() instanceof RuntimeException) {
			return (RuntimeException)searchException.getCause();
		}

		if (searchException.getCause() != null) {
			return new RuntimeException(searchException.getCause());
		}

		return new RuntimeException(searchException);
	}

	@Reference
	private IndexerRegistry _indexerRegistry;

	@Reference
	private SearchResponseBuilderFactory _searchResponseBuilderFactory;

	private ServiceTrackerMap<String, List<SearchRequestContributor>>
		_serviceTrackerMap;

}