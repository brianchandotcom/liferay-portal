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

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchPaginationUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Time;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Tina Tian
 */
public abstract class BaseSearchResultPermissionFilter
	implements SearchResultPermissionFilter {

	@Override
	public Hits search(SearchContext searchContext) throws SearchException {
		QueryConfig queryConfig = searchContext.getQueryConfig();

		if (!queryConfig.isAllFieldsSelected()) {
			Set<String> selectedFieldNameSet = SetUtil.fromArray(
				queryConfig.getSelectedFieldNames());

			for (String selectedFieldName : _PERMISSION_SELECTED_FIELD_NAMES) {
				selectedFieldNameSet.add(selectedFieldName);
			}

			queryConfig.setSelectedFieldNames(
				selectedFieldNameSet.toArray(
					new String[selectedFieldNameSet.size()]));
		}

		int end = searchContext.getEnd();
		int start = searchContext.getStart();

		if ((end == QueryUtil.ALL_POS) && (start == QueryUtil.ALL_POS)) {
			Hits hits = getHits(searchContext);

			filterHits(hits, searchContext);

			return hits;
		}

		if ((start < 0) || (start > end)) {
			return new HitsImpl();
		}

		int excludedDocsSize = 0;
		int hitsSize = 0;
		int offset = 0;
		long startTime = 0;

		List<Document> documents = new ArrayList<Document>();
		List<Float> scores = new ArrayList<Float>();

		while (true) {
			int count = end - documents.size();

			int amplifiedCount = (int)(
				count * _INDEX_PERMISSION_FILTER_SEARCH_AMPLIFICATION_FACTOR);

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

			collectHits(hits, documents, scores, count);

			if ((newDocs.length >= count) ||
				(oldDocs.length < amplifiedCount) ||
				(amplifiedEnd >= hitsSize)) {

				updateHits(
					hits, documents, scores, start, end,
					hitsSize - excludedDocsSize, startTime);

				return hits;
			}

			offset = amplifiedEnd;
		}
	}

	protected void collectHits(
		Hits hits, List<Document> documents, List<Float> scores, int count) {

		Document[] docs = hits.getDocs();

		if (docs.length < count) {
			count = docs.length;
		}

		for (int i = 0; i < count; i++) {
			documents.add(docs[i]);

			scores.add(hits.score(i));
		}
	}

	protected abstract void filterHits(Hits hits, SearchContext searchContext);

	protected abstract Hits getHits(SearchContext searchContext)
		throws SearchException;

	protected void updateHits(
		Hits hits, List<Document> documents, List<Float> scores, int start,
		int end, int size, long startTime) {

		int[] startAndEnd = SearchPaginationUtil.calculateStartAndEnd(
			start, end, documents.size());

		start = startAndEnd[0];
		end = startAndEnd[1];

		documents = documents.subList(start, end);
		scores = scores.subList(start, end);

		hits.setDocs(documents.toArray(new Document[documents.size()]));
		hits.setScores(ArrayUtil.toFloatArray(scores));
		hits.setLength(size);
		hits.setSearchTime(
			(float)(System.currentTimeMillis() - startTime) / Time.SECOND);
	}

	private static final double
		_INDEX_PERMISSION_FILTER_SEARCH_AMPLIFICATION_FACTOR =
			GetterUtil.getDouble(
				PropsUtil.get(
					PropsKeys.
						INDEX_PERMISSION_FILTER_SEARCH_AMPLIFICATION_FACTOR));

	private static final String[] _PERMISSION_SELECTED_FIELD_NAMES =
		{Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK};

}