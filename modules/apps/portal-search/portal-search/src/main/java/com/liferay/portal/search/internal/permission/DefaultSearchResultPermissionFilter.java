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

package com.liferay.portal.search.internal.permission;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchPaginationUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.HitsImpl;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.RelatedEntryIndexer;
import com.liferay.portal.kernel.search.RelatedEntryIndexerRegistry;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchResultPermissionFilter;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.FacetPostProcessor;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.configuration.DefaultSearchResultPermissionFilterConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * @author Tina Tian
 */
public class DefaultSearchResultPermissionFilter
	implements SearchResultPermissionFilter {

	public DefaultSearchResultPermissionFilter(
		FacetPostProcessor facetPostProcessor, IndexerRegistry indexerRegistry,
		PermissionChecker permissionChecker, Props props,
		RelatedEntryIndexerRegistry relatedEntryIndexerRegistry,
		Function<SearchContext, Hits> searchFunction,
		DefaultSearchResultPermissionFilterConfiguration
			defaultSearchResultPermissionFilterConfiguration) {

		_facetPostProcessor = facetPostProcessor;
		_indexerRegistry = indexerRegistry;
		_permissionChecker = permissionChecker;
		_relatedEntryIndexerRegistry = relatedEntryIndexerRegistry;
		_searchFunction = searchFunction;

		_permissionFilteredSearchResultAccurateCountThreshold =
			defaultSearchResultPermissionFilterConfiguration.
				permissionFilteredSearchResultAccurateCountThreshold();
		_searchQueryResultWindowLimit =
			defaultSearchResultPermissionFilterConfiguration.
				searchQueryResultWindowLimit();

		setProps(props);
	}

	@Override
	public Hits search(SearchContext searchContext) {
		QueryConfig queryConfig = searchContext.getQueryConfig();

		if (!queryConfig.isAllFieldsSelected()) {
			queryConfig.setSelectedFieldNames(
				getSelectedFieldNames(queryConfig.getSelectedFieldNames()));
		}

		int end = searchContext.getEnd();
		int start = searchContext.getStart();

		if ((end == QueryUtil.ALL_POS) && (start == QueryUtil.ALL_POS)) {
			Hits hits = getHits(searchContext);

			if (!isGroupAdmin(searchContext)) {
				filterHits(hits, searchContext);
			}

			return hits;
		}

		if ((start < 0) || (start > end)) {
			return new HitsImpl();
		}

		if (isGroupAdmin(searchContext)) {
			return getHits(searchContext);
		}

		SlidingWindowSearcher slidingWindowSearcher =
			new SlidingWindowSearcher();

		return slidingWindowSearcher.search(start, end, searchContext);
	}

	protected void filterHits(Hits hits, SearchContext searchContext) {
		List<Document> docs = new ArrayList<>();
		List<Document> excludeDocs = new ArrayList<>();
		List<Float> scores = new ArrayList<>();

		boolean companyAdmin = _permissionChecker.isCompanyAdmin(
			_permissionChecker.getCompanyId());
		int status = GetterUtil.getInteger(
			searchContext.getAttribute(Field.STATUS),
			WorkflowConstants.STATUS_APPROVED);

		Document[] documents = hits.getDocs();

		for (int i = 0; i < documents.length; i++) {
			if (_isIncludeDocument(
					documents[i], _permissionChecker.getCompanyId(),
					companyAdmin, status)) {

				docs.add(documents[i]);
				scores.add(hits.score(i));
			}
			else {
				excludeDocs.add(documents[i]);
			}
		}

		if (!excludeDocs.isEmpty()) {
			Map<String, Facet> facets = searchContext.getFacets();

			for (Facet facet : facets.values()) {
				_facetPostProcessor.exclude(excludeDocs, facet);
			}
		}

		hits.setDocs(docs.toArray(new Document[0]));
		hits.setScores(ArrayUtil.toFloatArray(scores));
		hits.setSearchTime(
			(float)(System.currentTimeMillis() - hits.getStart()) /
				Time.SECOND);
		hits.setLength(hits.getLength() - excludeDocs.size());
	}

	protected Hits getHits(SearchContext searchContext) {
		if ((searchContext != null) &&
			(searchContext.getEnd() != QueryUtil.ALL_POS)) {

			int end = searchContext.getEnd();

			int start = searchContext.getStart();

			if (start == QueryUtil.ALL_POS) {
				start = 0;
			}

			int searchResultWindow = end - start;

			if (searchResultWindow > _searchQueryResultWindowLimit) {
				throw new SystemException(
					StringBundler.concat(
						"Search result window size of ", searchResultWindow,
						" exceeds the configured limit of ",
						_searchQueryResultWindowLimit));
			}
		}

		return _searchFunction.apply(searchContext);
	}

	protected String[] getSelectedFieldNames(String[] selectedFieldNames) {
		Set<String> set = SetUtil.fromArray(selectedFieldNames);

		Collections.addAll(set, _PERMISSION_SELECTED_FIELD_NAMES);

		return set.toArray(new String[0]);
	}

	protected boolean isGroupAdmin(SearchContext searchContext) {
		long groupId = GetterUtil.getLong(
			searchContext.getAttribute(Field.GROUP_ID));

		if (groupId == 0) {
			return false;
		}

		if (!_permissionChecker.isGroupAdmin(groupId)) {
			return false;
		}

		return true;
	}

	protected void setProps(Props props) {
		_props = props;

		_indexPermissionFilterSearchAmplificationFactor = GetterUtil.getDouble(
			_props.get(
				PropsKeys.INDEX_PERMISSION_FILTER_SEARCH_AMPLIFICATION_FACTOR));
	}

	private boolean _isIncludeDocument(
		Document document, long companyId, boolean companyAdmin, int status) {

		long entryCompanyId = GetterUtil.getLong(
			document.get(Field.COMPANY_ID));

		if (entryCompanyId != companyId) {
			return false;
		}

		if (companyAdmin) {
			return true;
		}

		String entryClassName = document.get(Field.ENTRY_CLASS_NAME);

		Indexer<?> indexer = _indexerRegistry.getIndexer(entryClassName);

		if (indexer == null) {
			return true;
		}

		if (!indexer.isFilterSearch()) {
			return true;
		}

		long entryClassPK = GetterUtil.getLong(
			document.get(Field.ENTRY_CLASS_PK));

		try {
			if (indexer.hasPermission(
					_permissionChecker, entryClassName, entryClassPK,
					ActionKeys.VIEW)) {

				List<RelatedEntryIndexer> relatedEntryIndexers =
					_relatedEntryIndexerRegistry.getRelatedEntryIndexers(
						entryClassName);

				if (ListUtil.isNotEmpty(relatedEntryIndexers)) {
					for (RelatedEntryIndexer relatedEntryIndexer :
							relatedEntryIndexers) {

						if (!relatedEntryIndexer.isVisibleRelatedEntry(
								entryClassPK, status)) {

							return false;
						}
					}
				}

				return true;
			}
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return false;
	}

	private static final String[] _PERMISSION_SELECTED_FIELD_NAMES = {
		Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK
	};

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultSearchResultPermissionFilter.class);

	private final FacetPostProcessor _facetPostProcessor;
	private final IndexerRegistry _indexerRegistry;
	private double _indexPermissionFilterSearchAmplificationFactor;
	private final PermissionChecker _permissionChecker;
	private final int _permissionFilteredSearchResultAccurateCountThreshold;
	private Props _props;
	private final RelatedEntryIndexerRegistry _relatedEntryIndexerRegistry;
	private final Function<SearchContext, Hits> _searchFunction;
	private final int _searchQueryResultWindowLimit;

	private class SlidingWindowSearcher {

		public Hits search(int start, int end, SearchContext searchContext) {
			int amplifiedCount =
				_permissionFilteredSearchResultAccurateCountThreshold;
			double amplificationFactor = 1.0;
			int excludedDocsSize = 0;
			int filteredDocsCount = 0;
			int hitsSize = 0;
			int offset = 0;
			long startTime = 0;

			while (true) {
				int count = end - filteredDocsCount;

				if ((offset > 0) || (amplifiedCount < count)) {
					amplifiedCount = (int)Math.ceil(
						count * amplificationFactor);
				}

				if ((amplifiedCount > _searchQueryResultWindowLimit) &&
					(_searchQueryResultWindowLimit > 0)) {

					amplifiedCount = _searchQueryResultWindowLimit;
				}

				int amplifiedEnd = offset + amplifiedCount;

				searchContext.setEnd(amplifiedEnd);

				searchContext.setStart(offset);

				Hits hits = getHits(searchContext);

				if (startTime == 0) {
					hitsSize = hits.getLength();
					startTime = hits.getStart();
				}

				Document[] oldDocs = hits.getDocs();

				filterHits(hits, searchContext);

				Document[] newDocs = hits.getDocs();

				excludedDocsSize += oldDocs.length - newDocs.length;

				filteredDocsCount += newDocs.length;

				collectHits(hits, filteredDocsCount, start, end);

				if ((newDocs.length >= count) ||
					(oldDocs.length < amplifiedCount) ||
					(amplifiedEnd >= hitsSize)) {

					updateDocuments(filteredDocsCount, start, end);

					updateHits(hits, hitsSize - excludedDocsSize, startTime);

					return hits;
				}

				offset = amplifiedEnd;

				amplificationFactor = _getAmplificationFactor(
					filteredDocsCount, offset);
			}
		}

		protected void collectHits(
			Hits hits, int accumulatedCount, int start, int end) {

			int delta = end - start;

			Document[] docs = hits.getDocs();

			int remaining = docs.length;

			if ((accumulatedCount > start) && (documents.size() < delta)) {
				int previousAccumulatedCount = accumulatedCount - docs.length;

				int docsStart = 0;

				if (start > previousAccumulatedCount) {
					docsStart = start - previousAccumulatedCount;
				}

				int docsEnd = docsStart + (delta - documents.size());

				if (docsEnd > docs.length) {
					docsEnd = docs.length;
				}

				for (int i = docsStart; i < docsEnd; i++) {
					documents.add(docs[i]);

					scores.add(hits.score(i));
				}

				remaining -= docsEnd;

				if (remaining == 0) {
					return;
				}
			}

			for (int i = docs.length - remaining; i < docs.length; i++) {
				if (standbyDocuments.size() == delta) {
					standbyDocuments.remove(0);
					standbyScores.remove(0);
				}

				standbyDocuments.add(docs[i]);
				standbyScores.add(hits.score(i));
			}
		}

		protected void updateDocuments(
			int accumulatedCount, int start, int end) {

			if ((start < accumulatedCount) || standbyDocuments.isEmpty()) {
				return;
			}

			documents.addAll(0, standbyDocuments);
			scores.addAll(0, standbyScores);

			int delta = end - start;
			int docsStart = start - accumulatedCount;

			int docsEnd = docsStart + delta;

			int[] startAndEnd = SearchPaginationUtil.calculateStartAndEnd(
				docsStart, docsEnd, documents.size());

			docsStart = startAndEnd[0];

			docsEnd = startAndEnd[1];

			for (int i = 0; i < documents.size(); i++) {
				if ((i < docsStart) || (i >= docsEnd)) {
					documents.remove(i);
					scores.remove(i);
				}
			}
		}

		protected void updateHits(Hits hits, int size, long startTime) {
			hits.setDocs(documents.toArray(new Document[0]));
			hits.setScores(ArrayUtil.toFloatArray(scores));
			hits.setLength(size);
			hits.setSearchTime(
				(float)(System.currentTimeMillis() - startTime) / Time.SECOND);
		}

		protected List<Document> documents = new ArrayList<>();
		protected List<Float> scores = new ArrayList<>();
		protected List<Document> standbyDocuments = new ArrayList<>();
		protected List<Float> standbyScores = new ArrayList<>();

		private double _getAmplificationFactor(
			double totalViewable, double total) {

			if (totalViewable == 0) {
				return _indexPermissionFilterSearchAmplificationFactor;
			}

			return Math.min(
				1.0 / (totalViewable / total),
				_indexPermissionFilterSearchAmplificationFactor);
		}

	}

}