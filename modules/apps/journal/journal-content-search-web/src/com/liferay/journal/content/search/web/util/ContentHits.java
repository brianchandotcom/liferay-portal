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

package com.liferay.journal.content.search.web.util;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portlet.journal.service.JournalContentSearchLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Chow
 * @author Raymond Augé
 */
public class ContentHits {

	public boolean isShowListed() {
		return _showListed;
	}

	public void recordHits(Hits hits, long groupId, int start, int end)
		throws Exception {

		// This can later be optimized according to LEP-915.

		List<Document> docs = new ArrayList<>();
		List<Float> scores = new ArrayList<>();

		Document[] docsArray = hits.getDocs();

		for (int i = 0; i < docsArray.length; i++) {
			Document doc = hits.doc(i);

			String articleId = doc.get(Field.ARTICLE_ID);
			long articleGroupId = GetterUtil.getLong(doc.get(Field.GROUP_ID));

			int layoutIdsCount =
				JournalContentSearchLocalServiceUtil.getLayoutIdsCount(
					groupId, articleId);

			if ((layoutIdsCount > 0) ||
				(!isShowListed() && (articleGroupId == groupId))) {

				docs.add(hits.doc(i));
				scores.add(hits.score(i));
			}
		}

		int length = docs.size();

		hits.setLength(length);

		if (end > length) {
			end = length;
		}

		docs = docs.subList(start, end);
		scores = scores.subList(start, end);

		hits.setDocs(docs.toArray(new Document[docs.size()]));
		hits.setScores(ArrayUtil.toFloatArray(scores));

		hits.setSearchTime(
			(float)(System.currentTimeMillis() - hits.getStart()) /
				Time.SECOND);
	}

	public void setShowListed(boolean showListed) {
		_showListed = showListed;
	}

	private boolean _showListed = true;

}