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

import com.liferay.portal.kernel.util.StringPool;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Marcellus Tavares
 * @author Carlos Sierra Andrés
 */
public class DummyIndexSearcher implements IndexSearcher {

	@Override
	public Hits search(SearchContext searchContext, Query query) {
		return _emptyHits();
	}

	@Override
	public Hits search(
		String searchEngineId, long companyId, Query query, Sort[] sort,
		int start, int end) {

		return _emptyHits();
	}

	@Override
	public String spellCheckKeywords(SearchContext searchContext) {
		return StringPool.BLANK;
	}

	@Override
	public Map<String, List<String>> spellCheckKeywords(
		SearchContext searchContext, int max) {

		return _emptyMap;
	}

	@Override
	public String[] suggestKeywordQueries(
		SearchContext searchContext, int max) {

		return _emptyStrings;
	}

	private Hits _emptyHits() {
		Hits dummyHits = new HitsImpl();

		dummyHits.setCollatedSpellCheckResult(StringPool.BLANK);
		dummyHits.setDocs(new Document[0]);
		dummyHits.setLength(0);
		dummyHits.setQuery(new StringQueryImpl(StringPool.BLANK));
		dummyHits.setQuerySuggestions(_emptyStrings);
		dummyHits.setQueryTerms(_emptyStrings);
		dummyHits.setLength(0);
		dummyHits.setScores(_emptyFloats);
		dummyHits.setSearchTime(0);
		dummyHits.setSnippets(_emptyStrings);
		dummyHits.setSpellCheckResults(_emptyMap);
		dummyHits.setStart(0);

		return dummyHits;
	}

	private static float[] _emptyFloats = new float[0];
	private static Map<String, List<String>> _emptyMap = Collections.emptyMap();
	private static String[] _emptyStrings = new String[0];

}