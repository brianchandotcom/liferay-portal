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
		return _getHits();
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #search(SearchContext,
	 *             Query)}
	 */
	@Override
	public Hits search(
		String searchEngineId, long companyId, Query query, Sort[] sort,
		int start, int end) {

		return _getHits();
	}

	@Override
	public String spellCheckKeywords(SearchContext searchContext) {
		return StringPool.BLANK;
	}

	@Override
	public Map<String, List<String>> spellCheckKeywords(
		SearchContext searchContext, int max) {

		return Collections.emptyMap();
	}

	@Override
	public String[] suggestKeywordQueries(
		SearchContext searchContext, int max) {

		return new String[0];
	}

	private Hits _getHits() {
		Hits hits = new HitsImpl();

		hits.setCollatedSpellCheckResult(StringPool.BLANK);
		hits.setDocs(new Document[0]);
		hits.setLength(0);
		hits.setQuery(new StringQueryImpl(StringPool.BLANK));
		hits.setQuerySuggestions(new String[0]);
		hits.setQueryTerms(new String[0]);
		hits.setLength(0);
		hits.setScores(new float[0]);
		hits.setSearchTime(0);
		hits.setSnippets(new String[0]);
		hits.setSpellCheckResults(_spellCheckResults);
		hits.setStart(0);

		return hits;
	}

	private static Map<String, List<String>> _spellCheckResults =
		Collections.emptyMap();

}