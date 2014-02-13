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

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Time;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tina Tian
 */
public abstract class BaseSearchResultPermissionFilter
	implements SearchResultPermissionFilter {

	public Hits search(SearchContext searchContext) throws SearchException {
		int start = searchContext.getStart();
		int end = searchContext.getEnd();

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS)) {
			Hits hits = getResults(searchContext);

			filterResults(hits);

			return hits;
		}

		if ((start < 0) || (start > end)) {
			return new HitsImpl();
		}

		int offset = 0;
		int totalHits = 0;
		long startTime = 0;
		int totalExcludedDocs = 0;

		List<Document> documents = new ArrayList<Document>();
		List<Float> scores = new ArrayList<Float>();

		while (true) {
			int count = end - documents.size();

			int amplifiedCount = (int)(
				count * _INDEX_PERMISSION_FILTER_SEARCH_AMPLIFICATION_FACTOR);

			int amplifiedEnd = offset + amplifiedCount;

			searchContext.setStart(offset);
			searchContext.setEnd(amplifiedEnd);

			Hits hits = getResults(searchContext);

			if (startTime == 0) {
				totalHits = hits.getLength();
				startTime = hits.getStart();
			}

			Document[] orginalDocs = hits.getDocs();

			filterResults(hits);

			Document[] finalDocs = hits.getDocs();

			totalExcludedDocs += orginalDocs.length - finalDocs.length;

			collectResults(hits, documents, scores, count);

			if ((finalDocs.length >= count) ||
				(orginalDocs.length < amplifiedCount) ||
				(amplifiedEnd >= totalHits)) {

				updateResults(
					hits, documents, scores, start, end,
					totalHits - totalExcludedDocs, startTime);

				return hits;
			}

			offset = amplifiedEnd;
		}
	}

	protected void collectResults(
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

	protected abstract void filterResults(Hits hits);

	protected abstract Hits getResults(SearchContext searchContext)
		throws SearchException;

	protected void updateResults(
		Hits hits, List<Document> documents, List<Float> scores, int start,
		int end, int totalHits, long startTime) {

		if (documents.size() < end) {
			end = documents.size();
		}

		documents = documents.subList(start, end);
		scores = scores.subList(start, end);

		hits.setDocs(documents.toArray(new Document[documents.size()]));
		hits.setScores(scores.toArray(new Float[scores.size()]));
		hits.setLength(totalHits);
		hits.setSearchTime(
			(float)(System.currentTimeMillis() - startTime) / Time.SECOND);
	}

	private static double _INDEX_PERMISSION_FILTER_SEARCH_AMPLIFICATION_FACTOR =
		GetterUtil.getDouble(
			PropsUtil.get(
				PropsKeys.INDEX_PERMISSION_FILTER_SEARCH_AMPLIFICATION_FACTOR));

}