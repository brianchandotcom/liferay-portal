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

package com.liferay.portal.search;

import com.liferay.portal.kernel.search.NGramHolder;
import com.liferay.portal.kernel.search.NGramHolderBuilder;
import com.liferay.portal.kernel.search.SearchException;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.ngram.NGramTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;

/**
 * @author Michael C. Han
 */
public class NGramHolderBuilderImpl implements NGramHolderBuilder {

	public NGramHolder buildNGramHolder(String input) throws SearchException {

		int length = input.length();

		int nGramMinLength = getNGramMinLength(length);

		int nGramMaxLength = getNGramMaxLength(length);

		NGramTokenizer nGramTokenizer = new NGramTokenizer(
			new StringReader(input), nGramMinLength, nGramMaxLength);

		CharTermAttribute charTermAttribute = nGramTokenizer.getAttribute(
			CharTermAttribute.class);

		OffsetAttribute offsetAttribute = nGramTokenizer.getAttribute(
			OffsetAttribute.class);

		NGramHolder nGramHolder = new NGramHolder();

		try {
			while (nGramTokenizer.incrementToken()) {
				String nGram = charTermAttribute.toString();

				int currentNGramSize = charTermAttribute.length();

				if ((currentNGramSize >= nGramMinLength) &&
					(currentNGramSize <= nGramMaxLength)) {

					if (offsetAttribute.startOffset() == 0) {
						nGramHolder.addNGramStart(currentNGramSize, nGram);
					}
					else if (offsetAttribute.endOffset() == length) {
						nGramHolder.addNGramEnd(currentNGramSize, nGram);
					}
					else {
						nGramHolder.addNGram(currentNGramSize, nGram);
					}
				}
				else {
					continue;
				}
			}

			return nGramHolder;
		}
		catch (IOException e) {
			throw new SearchException(e);
		}
	}

	protected int getNGramMaxLength(int length) {
		if (length > 5) {
			return 4;
		}
		else if (length == 5) {
			return 3;
		}

		return 2;
	}

	protected int getNGramMinLength(int length) {
		if (length > 5) {
			return 3;
		}
		else if (length == 5) {
			return 2;
		}

		return 1;
	}

}