/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.suggest.SpellCheckIndexWriter;

/**
 * @author Michael C. Han
 */
public abstract class BaseIndexWriter
	implements IndexWriter, SpellCheckIndexWriter {

	@Override
	public void clearQuerySuggestionDictionaryIndexes(
			SearchContext searchContext)
		throws SearchException {

		SpellCheckIndexWriter spellCheckIndexWriter =
			getSpellCheckIndexWriter();

		if (spellCheckIndexWriter == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No spell check index writer configured");
			}

			return;
		}

		spellCheckIndexWriter.clearQuerySuggestionDictionaryIndexes(
			searchContext);
	}

	@Override
	public void clearSpellCheckerDictionaryIndexes(SearchContext searchContext)
		throws SearchException {

		SpellCheckIndexWriter spellCheckIndexWriter =
			getSpellCheckIndexWriter();

		if (spellCheckIndexWriter == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No spell check index writer configured");
			}

			return;
		}

		spellCheckIndexWriter.clearSpellCheckerDictionaryIndexes(searchContext);
	}

	/**
	 * @throws SearchException
	 */
	@Override
	public void commit(SearchContext searchContext) throws SearchException {
	}

	@Override
	public void indexKeyword(
			SearchContext searchContext, float weight, String keywordType)
		throws SearchException {

		SpellCheckIndexWriter spellCheckIndexWriter =
			getSpellCheckIndexWriter();

		if (spellCheckIndexWriter == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No spell check index writer configured");
			}

			return;
		}

		spellCheckIndexWriter.indexKeyword(searchContext, weight, keywordType);
	}

	@Override
	public void indexQuerySuggestionDictionaries(SearchContext searchContext)
		throws SearchException {

		SpellCheckIndexWriter spellCheckIndexWriter =
			getSpellCheckIndexWriter();

		if (spellCheckIndexWriter == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No spell check index writer configured");
			}

			return;
		}

		spellCheckIndexWriter.indexQuerySuggestionDictionaries(searchContext);
	}

	@Override
	public void indexQuerySuggestionDictionary(SearchContext searchContext)
		throws SearchException {

		SpellCheckIndexWriter spellCheckIndexWriter =
			getSpellCheckIndexWriter();

		if (spellCheckIndexWriter == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No spell check index writer configured");
			}

			return;
		}

		spellCheckIndexWriter.indexQuerySuggestionDictionary(searchContext);
	}

	@Override
	public void indexSpellCheckerDictionaries(SearchContext searchContext)
		throws SearchException {

		SpellCheckIndexWriter spellCheckIndexWriter =
			getSpellCheckIndexWriter();

		if (spellCheckIndexWriter == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No spell check index writer configured");
			}

			return;
		}

		spellCheckIndexWriter.indexSpellCheckerDictionaries(searchContext);
	}

	@Override
	public void indexSpellCheckerDictionary(SearchContext searchContext)
		throws SearchException {

		SpellCheckIndexWriter spellCheckIndexWriter =
			getSpellCheckIndexWriter();

		if (spellCheckIndexWriter == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No spell check index writer configured");
			}

			return;
		}

		spellCheckIndexWriter.indexSpellCheckerDictionary(searchContext);
	}

	protected abstract SpellCheckIndexWriter getSpellCheckIndexWriter();

	private static final Log _log = LogFactoryUtil.getLog(
		BaseIndexWriter.class);

}