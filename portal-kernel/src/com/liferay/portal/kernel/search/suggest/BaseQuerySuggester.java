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

package com.liferay.portal.kernel.search.suggest;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.analysis.TokenizerUtil;

import java.util.List;
import java.util.Map;

/**
 * @author     Michael C. Han
 * @deprecated As of Judson (7.1.x), moved to {@link
 *             com.liferay.portal.search.suggest.BaseQuerySuggester}
 */
@Deprecated
public abstract class BaseQuerySuggester implements QuerySuggester {

	@Override
	public String spellCheckKeywords(SearchContext searchContext)
		throws SearchException {

		Map<String, List<String>> suggestions = spellCheckKeywords(
			searchContext, 1);

		String localizedFieldName = Field.getLocalizedName(
			searchContext.getLanguageId(), Field.SPELL_CHECK_WORD);

		List<String> keywords = TokenizerUtil.tokenize(
			localizedFieldName, searchContext.getKeywords(),
			searchContext.getLanguageId());

		return CollatorUtil.collate(suggestions, keywords);
	}

	@Override
	public SuggesterResults suggest(
		SearchContext searchContext, Suggester suggester) {

		return new SuggesterResults();
	}

}