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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Michael C. Han
 */
public abstract class BaseIndexWriter
	implements IndexWriter, SpellCheckIndexWriter {

	@Override
	public void clearQuerySuggestionDictionaryIndexes(
			SearchContext searchContext)
		throws SearchException {

		if (_spellCheckIndexWriter == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No spell check index writer configured");
			}

			return;
		}

		_spellCheckIndexWriter.clearQuerySuggestionDictionaryIndexes(
			searchContext);
	}

	@Override
	public void clearSpellCheckerDictionaryIndexes(SearchContext searchContext)
		throws SearchException {

		if (_spellCheckIndexWriter == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No spell check index writer configured");
			}

			return;
		}

		_spellCheckIndexWriter.clearSpellCheckerDictionaryIndexes(
			searchContext);
	}

	@Override
	public void indexKeyword(
			SearchContext searchContext, float weight, String keywordType)
		throws SearchException {

		if (_spellCheckIndexWriter == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No spell check index writer configured");
			}

			return;
		}

		_spellCheckIndexWriter.indexKeyword(searchContext, weight, keywordType);
	}

	@Override
	public void indexQuerySuggestionDictionaries(SearchContext searchContext)
		throws SearchException {

		if (_spellCheckIndexWriter == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No spell check index writer configured");
			}

			return;
		}

		_spellCheckIndexWriter.indexQuerySuggestionDictionaries(searchContext);
	}

	@Override
	public void indexQuerySuggestionDictionary(SearchContext searchContext)
		throws SearchException {

		if (_spellCheckIndexWriter == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No spell check index writer configured");
			}

			return;
		}

		_spellCheckIndexWriter.indexQuerySuggestionDictionary(searchContext);
	}

	@Override
	public void indexSpellCheckerDictionaries(SearchContext searchContext)
		throws SearchException {

		if (_spellCheckIndexWriter == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No spell check index writer configured");
			}

			return;
		}

		_spellCheckIndexWriter.indexSpellCheckerDictionaries(searchContext);
	}

	@Override
	public void indexSpellCheckerDictionary(SearchContext searchContext)
		throws SearchException {

		if (_spellCheckIndexWriter == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No spell check index writer configured");
			}

			return;
		}

		_spellCheckIndexWriter.indexSpellCheckerDictionary(searchContext);
	}

	public void setSpellCheckIndexWriter(
		SpellCheckIndexWriter spellCheckIndexWriter) {

		_spellCheckIndexWriter = spellCheckIndexWriter;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseIndexWriter.class);

	private SpellCheckIndexWriter _spellCheckIndexWriter;

}