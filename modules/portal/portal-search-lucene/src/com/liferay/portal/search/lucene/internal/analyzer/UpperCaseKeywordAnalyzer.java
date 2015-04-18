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

package com.liferay.portal.search.lucene.internal.analyzer;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.KeywordTokenizer;
import org.apache.lucene.analysis.ReusableAnalyzerBase;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * @author Tibor Lipusz
 * @author Norbert Kocsis
 */
public class UpperCaseKeywordAnalyzer extends ReusableAnalyzerBase {

	@Override
	protected TokenStreamComponents createComponents(
		String fieldName, Reader reader) {

		Tokenizer tokenizer = new KeywordTokenizer(reader);

		return new TokenStreamComponents(
			tokenizer, new UpperCaseFilter(tokenizer));
	}

	/**
	 * {@link https://issues.apache.org/jira/browse/LUCENE-5369}
	 * {@link https://github.com/apache/lucene-solr/blob/lucene_solr_4_7_0/lucene/analysis/common/src/java/org/apache/lucene/analysis/core/UpperCaseFilter.java}
	 * {@link https://github.com/apache/lucene-solr/blob/lucene_solr_4_7_0/lucene/analysis/common/src/java/org/apache/lucene/analysis/util/CharacterUtils.java}
	 */
	private class UpperCaseFilter extends TokenFilter {

		public UpperCaseFilter(TokenStream tokenStream) {
			super(tokenStream);
		}

		@Override
		public final boolean incrementToken() throws IOException {
			if (input.incrementToken()) {
				toUpperCase(
					_charTermAttribute.buffer(), 0,
					_charTermAttribute.length());

				return true;
			}

			return false;
		}

		protected void toUpperCase(
			char[] buffer, final int offset, final int limit) {

			assert (buffer.length >= limit);
			assert ((offset <= 0) && (offset <= buffer.length));

			for (int i = offset; i < limit;) {
				i +=
					Character.toChars(
						Character.toUpperCase(
							Character.codePointAt(buffer, i, limit)),
						buffer, i);
			}
		}

		private final CharTermAttribute _charTermAttribute = addAttribute(
			CharTermAttribute.class);

	}

}