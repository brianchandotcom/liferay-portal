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

package com.liferay.portal.search.web.internal.display.context;

import com.liferay.portal.util.PropsValues;

/**
 * @author André de Oliveira
 */
public class IndexSearchPropsValuesImpl implements IndexSearchPropsValues {

	@Override
	public int getCollatedSpellCheckResultScoresThreshold() {
		return PropsValues.
			INDEX_SEARCH_COLLATED_SPELL_CHECK_RESULT_SCORES_THRESHOLD;
	}

	@Override
	public int getQueryIndexingThreshold() {
		return PropsValues.INDEX_SEARCH_QUERY_INDEXING_THRESHOLD;
	}

	@Override
	public int getQuerySuggestionMax() {
		return PropsValues.INDEX_SEARCH_QUERY_SUGGESTION_MAX;
	}

	@Override
	public int getQuerySuggestionScoresThreshold() {
		return PropsValues.INDEX_SEARCH_QUERY_SUGGESTION_SCORES_THRESHOLD;
	}

	@Override
	public boolean isCollatedSpellCheckResultEnabled() {
		return PropsValues.INDEX_SEARCH_COLLATED_SPELL_CHECK_RESULT_ENABLED;
	}

	@Override
	public boolean isQueryIndexingEnabled() {
		return PropsValues.INDEX_SEARCH_QUERY_INDEXING_ENABLED;
	}

	@Override
	public boolean isQuerySuggestionEnabled() {
		return PropsValues.INDEX_SEARCH_QUERY_SUGGESTION_ENABLED;
	}

}