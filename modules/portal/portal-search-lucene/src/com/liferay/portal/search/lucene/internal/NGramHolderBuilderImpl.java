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

package com.liferay.portal.search.lucene.internal;

import com.liferay.portal.kernel.search.NGramHolder;
import com.liferay.portal.kernel.search.NGramHolderBuilder;
import com.liferay.portal.kernel.search.SearchException;

import java.io.StringReader;

import org.apache.lucene.analysis.ngram.NGramTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = NGramHolderBuilder.class)
public class NGramHolderBuilderImpl implements NGramHolderBuilder {

	@Override
	public NGramHolder buildNGramHolder(String input) throws SearchException {
		return buildNGramHolder(
			input, getNGramMinLength(input.length()),
			getNGramMaxLength(input.length()));
	}

	@Override
	public NGramHolder buildNGramHolder(String input, int nGramMaxLength)
		throws SearchException {

		if (nGramMaxLength <= 0) {
			nGramMaxLength = getNGramMaxLength(input.length());
		}

		return buildNGramHolder(
			input, getNGramMinLength(input.length()), nGramMaxLength);
	}

	@Override
	public NGramHolder buildNGramHolder(
			String input, int nGramMinLength, int nGramMaxLength)
		throws SearchException {

		try {
			NGramHolder nGramHolder = new NGramHolder();

			NGramTokenizer nGramTokenizer = new NGramTokenizer(
				new StringReader(input), nGramMinLength, nGramMaxLength);

			CharTermAttribute charTermAttribute = nGramTokenizer.getAttribute(
				CharTermAttribute.class);

			OffsetAttribute offsetAttribute = nGramTokenizer.getAttribute(
				OffsetAttribute.class);

			while (nGramTokenizer.incrementToken()) {
				String nGram = charTermAttribute.toString();

				int currentNGramSize = charTermAttribute.length();

				if ((currentNGramSize >= nGramMinLength) &&
					(currentNGramSize <= nGramMaxLength)) {

					if (offsetAttribute.startOffset() == 0) {
						nGramHolder.addNGramStart(currentNGramSize, nGram);
					}
					else if (offsetAttribute.endOffset() == input.length()) {
						nGramHolder.addNGramEnd(currentNGramSize, nGram);
					}
					else {
						nGramHolder.addNGram(currentNGramSize, nGram);
					}
				}
			}

			return nGramHolder;
		}
		catch (Exception e) {
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