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

import java.io.IOException;
import java.io.Reader;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Fieldable;

/**
 * @author Raymond Augé
 * @author Mate Thurzo
 */
public class PerFieldAnalyzer extends Analyzer {

	public PerFieldAnalyzer(
		Analyzer defaultAnalyzer, Map<String, Analyzer> analyzerMap) {

		_analyzer = defaultAnalyzer;
		_analyzers = analyzerMap;
	}

	public void addAnalyzer(String fieldName, Analyzer analyzer) {
		_analyzers.put(fieldName, analyzer);
	}

	public Analyzer getAnalyzer(String fieldName) {
		Analyzer analyzer = _analyzers.get(fieldName);

		if (analyzer != null) {
			return analyzer;
		}

		for (String key : _analyzers.keySet()) {
			if (Pattern.matches(key, fieldName)) {
				return _analyzers.get(key);
			}
		}

		return _analyzer;
	}

	@Override
	public int getOffsetGap(Fieldable field) {
		Analyzer analyzer = getAnalyzer(field.name());

		return analyzer.getOffsetGap(field);
	}

	@Override
	public int getPositionIncrementGap(String fieldName) {
		Analyzer analyzer = getAnalyzer(fieldName);

		return analyzer.getPositionIncrementGap(fieldName);
	}

	@Override
	public TokenStream reusableTokenStream(String fieldName, Reader reader)
		throws IOException {

		Analyzer analyzer = getAnalyzer(fieldName);

		return analyzer.reusableTokenStream(fieldName, reader);
	}

	@Override
	public TokenStream tokenStream(String fieldName, Reader reader) {
		Analyzer analyzer = getAnalyzer(fieldName);

		return analyzer.tokenStream(fieldName, reader);
	}

	private Analyzer _analyzer;
	private Map<String, Analyzer> _analyzers = new HashMap<String, Analyzer>();

}