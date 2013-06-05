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

package com.liferay.portal.search.lucene;

import java.io.IOException;

import java.util.List;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;

/**
 * @author Michael C. Han
 */
public class SpellCheckerUtil {

	public static boolean validateWordExists(
			String localizedFieldName, String word,
			List<IndexReader> indexReaders)
		throws IOException {

		if (!indexReaders.isEmpty()) {
			Term term = new Term(localizedFieldName, word);

			for (IndexReader indexReader : indexReaders) {
				if (indexReader.docFreq(term) > 0) {
					return true;
				}
			}
		}

		return false;
	}

}