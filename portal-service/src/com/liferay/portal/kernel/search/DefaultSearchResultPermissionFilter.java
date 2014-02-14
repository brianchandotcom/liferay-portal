/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author Tina Tian
 */
public class DefaultSearchResultPermissionFilter
	extends BaseSearchResultPermissionFilter {

	public DefaultSearchResultPermissionFilter(
		BaseIndexer baseIndexer, PermissionChecker permissionChecker) {

		_baseIndexer = baseIndexer;
		_permissionChecker = permissionChecker;
	}

	@Override
	protected void filterHits(Hits hits, SearchContext searchContext) {
		List<Document> docs = new ArrayList<Document>();
		List<Float> scores = new ArrayList<Float>();

		Document[] documents = hits.getDocs();

		int excludeDocsSize = 0;

		int status = GetterUtil.getInteger(
			searchContext.getAttribute(Field.STATUS),
			WorkflowConstants.STATUS_APPROVED);

		for (int i = 0; i < documents.length; i++) {
			Document document = documents[i];

			String entryClassName = document.get(Field.ENTRY_CLASS_NAME);

			Indexer indexer = IndexerRegistryUtil.getIndexer(entryClassName);

			long entryClassPK = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			try {
				if ((indexer == null) || (indexer.isFilterSearch() &&
					 indexer.hasPermission(
						 _permissionChecker, entryClassName, entryClassPK,
						 ActionKeys.VIEW) &&
					 indexer.isVisibleRelatedEntry(entryClassPK, status)) ||
					!indexer.isFilterSearch() ||
					!indexer.isPermissionAware()) {

					docs.add(document);
					scores.add(hits.score(i));
				}
				else {
					excludeDocsSize++;
				}
			}
			catch (Exception e) {
				excludeDocsSize++;
			}
		}

		hits.setDocs(docs.toArray(new Document[docs.size()]));
		hits.setScores(scores.toArray(new Float[scores.size()]));
		hits.setSearchTime(
			(float)(System.currentTimeMillis() - hits.getStart()) /
				Time.SECOND);
		hits.setLength(hits.getLength() - excludeDocsSize);
	}

	@Override
	protected Hits getHits(SearchContext searchContext) throws SearchException {
		QueryConfig queryConfig = searchContext.getQueryConfig();

		String[] selectedFieldNames = queryConfig.getSelectedFieldNames();

		if (selectedFieldNames != null) {
			Set<String> selectedFieldNameSet = SetUtil.fromArray(
				selectedFieldNames);

			selectedFieldNameSet.addAll(_PERMISSION_SELECTED_FIELD_NAMES);

			selectedFieldNames = selectedFieldNameSet.toArray(
				new String[selectedFieldNameSet.size()]);

			queryConfig.setSelectedFieldNames(selectedFieldNames);
		}

		return _baseIndexer.doSearch(searchContext);
	}

	private static final List<String> _PERMISSION_SELECTED_FIELD_NAMES =
		Arrays.asList(Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK);

	private BaseIndexer _baseIndexer;
	private PermissionChecker _permissionChecker;

}