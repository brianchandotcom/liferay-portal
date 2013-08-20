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

import java.util.List;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public abstract class BaseQuerySuggester implements QuerySuggester {

	@Override
	public String spellCheckKeywords(SearchContext searchContext)
		throws SearchException {

		Map<String, List<String>> suggestions = spellCheckKeywords(
			searchContext, 1);

		String localizedFieldName = DocumentImpl.getLocalizedName(
			searchContext.getLanguageId(), Field.SPELL_CHECK_WORD);

		List<String> keywordSuggestions = TokenizerUtil.tokenize(
			localizedFieldName, searchContext.getKeywords(),
			searchContext.getLanguageId());

		return CollatorUtil.collate(suggestions, keywordSuggestions);
	}

}