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

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;

/**
 * @author Michael C. Han
 */
public interface SpellCheckIndexWriter {

	public void clearQuerySuggestionDictionaryIndexes(
			SearchContext searchContext)
		throws SearchException;

	public void clearSpellCheckerDictionaryIndexes(SearchContext searchContext)
		throws SearchException;

	public void indexKeyword(
			SearchContext searchContext, float weight, String keywordType)
		throws SearchException;

	public void indexQuerySuggestionDictionaries(SearchContext searchContext)
		throws SearchException;

	public void indexQuerySuggestionDictionary(SearchContext searchContext)
		throws SearchException;

	public void indexSpellCheckerDictionaries(SearchContext searchContext)
		throws SearchException;

	public void indexSpellCheckerDictionary(SearchContext searchContext)
		throws SearchException;

}