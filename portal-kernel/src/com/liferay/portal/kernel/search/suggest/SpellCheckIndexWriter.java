/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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