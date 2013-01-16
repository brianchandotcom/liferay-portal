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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Locale;

/**
 * @author Michael C. Han
 */
public class DelegatingSpellCheckIndexWriter implements SpellCheckIndexWriter {

	public void indexDictionaries(long companyId) throws SearchException {
		if (_spellCheckIndexWriter == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No spell check index writer configured");
			}
		}

		_spellCheckIndexWriter.indexDictionaries(companyId);
	}

	public void indexDictionary(long companyId, Locale locale)
		throws SearchException {

		if (_spellCheckIndexWriter == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No spell check index writer configured");
			}
		}

		_spellCheckIndexWriter.indexDictionary(companyId, locale);
	}

	public void setSpellCheckIndexWriter(
		SpellCheckIndexWriter spellCheckIndexWriter) {

		_spellCheckIndexWriter = spellCheckIndexWriter;
	}

	private static Log _log = LogFactoryUtil.getLog(
		DelegatingQuerySuggester.class);

	private SpellCheckIndexWriter _spellCheckIndexWriter;
}
