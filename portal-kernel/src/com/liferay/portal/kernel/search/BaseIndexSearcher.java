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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.suggest.QuerySuggester;
import com.liferay.portal.kernel.search.suggest.Suggester;
import com.liferay.portal.kernel.search.suggest.SuggesterResults;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public abstract class BaseIndexSearcher
	implements IndexSearcher, QuerySuggester {

	@Override
	public String spellCheckKeywords(SearchContext searchContext)
		throws SearchException {

		QuerySuggester querySuggester = getQuerySuggester();

		if (querySuggester == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No query suggester configured");
			}

			return StringPool.BLANK;
		}

		return querySuggester.spellCheckKeywords(searchContext);
	}

	@Override
	public Map<String, List<String>> spellCheckKeywords(
			SearchContext searchContext, int max)
		throws SearchException {

		QuerySuggester querySuggester = getQuerySuggester();

		if (querySuggester == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No query suggester configured");
			}

			return Collections.emptyMap();
		}

		return querySuggester.spellCheckKeywords(searchContext, max);
	}

	@Override
	public SuggesterResults suggest(
			SearchContext searchContext, Suggester suggester)
		throws SearchException {

		QuerySuggester querySuggester = getQuerySuggester();

		if (querySuggester == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No query suggester configured");
			}

			return new SuggesterResults();
		}

		return querySuggester.suggest(searchContext, suggester);
	}

	@Override
	public String[] suggestKeywordQueries(SearchContext searchContext, int max)
		throws SearchException {

		QuerySuggester querySuggester = getQuerySuggester();

		if (querySuggester == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No query suggester configured");
			}

			return StringPool.EMPTY_ARRAY;
		}

		return querySuggester.suggestKeywordQueries(searchContext, max);
	}

	protected abstract QuerySuggester getQuerySuggester();

	protected void populateUID(Document document, QueryConfig queryConfig) {
		Field uidField = document.getField(Field.UID);

		if ((uidField != null) ||
			Validator.isNull(queryConfig.getAlternateUidFieldName())) {

			return;
		}

		String uidValue = document.get(queryConfig.getAlternateUidFieldName());

		if (Validator.isNotNull(uidValue)) {
			uidField = new Field(Field.UID, uidValue);

			document.add(uidField);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseIndexSearcher.class);

}