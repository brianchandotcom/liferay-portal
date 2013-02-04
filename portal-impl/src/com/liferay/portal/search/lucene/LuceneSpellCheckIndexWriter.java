/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.search.lucene;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.SpellCheckIndexWriter;

import java.io.IOException;

import java.util.Locale;

/**
 * @author Michael C. Han
 */
public class LuceneSpellCheckIndexWriter implements SpellCheckIndexWriter {

	public void indexDictionaries(SearchContext searchContext)
		throws SearchException {

		long companyId = searchContext.getCompanyId();

		try {
			SpellCheckerIndexAccessor spellCheckerIndexAccessor =
				_luceneHelper.getSpellCheckerIndexAccessor();

			spellCheckerIndexAccessor.indexDictionaries();
		}
		catch (IOException e) {
			throw new SearchException(
				"Unable to index dictionaries for: " + companyId, e);
		}
	}

	public void indexDictionary(SearchContext searchContext)
		throws SearchException {

		long companyId = searchContext.getCompanyId();

		Locale locale = searchContext.getLocale();

		try {
			SpellCheckerIndexAccessor spellCheckerIndexAccessor =
				_luceneHelper.getSpellCheckerIndexAccessor();

			spellCheckerIndexAccessor.indexDictionary(locale);
		}
		catch (IOException e) {
			throw new SearchException(
				"Unable to index dictionaries for: " + companyId, e);
		}
	}

	public void setLuceneHelper(LuceneHelper luceneHelper) {
		_luceneHelper = luceneHelper;
	}

	private LuceneHelper _luceneHelper;

}