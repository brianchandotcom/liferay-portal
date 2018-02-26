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

package com.liferay.portal.search.elasticsearch6.internal;

import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;

import java.util.Collection;
import java.util.HashSet;

import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

/**
 * @author Michael C. Han
 */
public class DeleteDocumentsSearchHitsProcessor implements SearchHitsProcessor {

	public DeleteDocumentsSearchHitsProcessor(IndexWriter indexWriter) {
		_indexWriter = indexWriter;
	}

	@Override
	public void processSearchHits(
			SearchContext searchContext, SearchHits searchHits)
		throws SearchException {

		SearchHit[] searchHitsArray = searchHits.getHits();

		Collection<String> uids = new HashSet<>();

		for (SearchHit searchHit : searchHitsArray) {
			uids.add(searchHit.getId());
		}

		_indexWriter.deleteDocuments(searchContext, uids);
	}

	private final IndexWriter _indexWriter;

}