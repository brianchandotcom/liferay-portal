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

package com.liferay.portal.search.lucene.spellcheck;

import java.io.IOException;

import java.util.Locale;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.spell.Dictionary;
import org.apache.lucene.search.spell.SuggestMode;

/**
 * @author Michael C. Han
 */
public interface SpellChecker {

	public void clearIndex() throws IOException;

	public void close() throws IOException;

	public boolean exist(String word, Locale locale) throws IOException;

	public void indexDictionary(
			Dictionary dictionary, Locale locale,
			IndexWriterConfig indexWriterConfig, boolean fullMerge)
		throws IOException;

	public String[] suggestSimilar(String word, Locale locale, int suggestCount)
		throws IOException;

	public String[] suggestSimilar(
			String word, Locale locale, int suggestCount, float accuracy)
		throws IOException;

	public String[] suggestSimilar(
			String word, Locale locale, int suggestCount,
			IndexReader indexReader, String field, SuggestMode suggestMode)
		throws IOException;

	public String[] suggestSimilar(
			String word, Locale locale, int suggestCount,
			IndexReader indexReader, String field, SuggestMode suggestMode,
			float accuracy)
		throws IOException;

}