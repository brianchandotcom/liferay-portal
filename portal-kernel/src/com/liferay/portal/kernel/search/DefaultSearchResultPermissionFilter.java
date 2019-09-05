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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.FacetPostProcessor;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ServiceProxyFactory;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author     Tina Tian
 * @deprecated As of Judson (7.1.x), moved to {@link
 *             com.liferay.portal.search.internal.permission.DefaultSearchResultPermissionFilter}
 */
@Deprecated
public class DefaultSearchResultPermissionFilter
	extends BaseSearchResultPermissionFilter {

	/**
	 * @param      baseIndexer
	 * @param      permissionChecker
	 * @deprecated As of Judson (7.1.x), replace with {@link
	 *             #DefaultSearchResultPermissionFilter(SearchExecutor,
	 *             PermissionChecker)}
	 */
	@Deprecated
	public DefaultSearchResultPermissionFilter(
		BaseIndexer<?> baseIndexer, PermissionChecker permissionChecker) {

		this(baseIndexer::doSearch, permissionChecker);
	}

	public DefaultSearchResultPermissionFilter(
		SearchExecutor searchExecutor, PermissionChecker permissionChecker) {

		_searchExecutor = searchExecutor;
		_permissionChecker = permissionChecker;
	}

	public interface SearchExecutor {

		public Hits search(SearchContext searchContext) throws SearchException;

	}

	@Override
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
			FacetPostProcessor facetPostProcessor = _facetPostProcessor;

			if (facetPostProcessor != null) {
				Map<String, Facet> facets = searchContext.getFacets();

				for (Facet facet : facets.values()) {
					facetPostProcessor.exclude(excludeDocs, facet);
				}
			}
		}

		hits.setDocs(docs.toArray(new Document[0]));
		hits.setScores(ArrayUtil.toFloatArray(scores));
		hits.setSearchTime(
			(float)(System.currentTimeMillis() - hits.getStart()) /
				Time.SECOND);
		hits.setLength(hits.getLength() - excludeDocs.size());
	}

	@Override
	protected Hits getHits(SearchContext searchContext) throws SearchException {
		return _searchExecutor.search(searchContext);
	}

	@Override
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

		Indexer<?> indexer = IndexerRegistryUtil.getIndexer(entryClassName);

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
					RelatedEntryIndexerRegistryUtil.getRelatedEntryIndexers(
						entryClassName);

				if (ListUtil.isNotEmpty(relatedEntryIndexers)) {
					for (RelatedEntryIndexer relatedEntryIndexer :
							relatedEntryIndexers) {

						relatedEntryIndexer.isVisibleRelatedEntry(
							entryClassPK, status);
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

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultSearchResultPermissionFilter.class);

	private static volatile FacetPostProcessor _facetPostProcessor =
		ServiceProxyFactory.newServiceTrackedInstance(
			FacetPostProcessor.class, DefaultSearchResultPermissionFilter.class,
			"_facetPostProcessor", false, true);

	private final PermissionChecker _permissionChecker;
	private final SearchExecutor _searchExecutor;

}