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

package com.liferay.portal.search.lucene;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Tokenizer;
import com.liferay.portal.kernel.util.ObjectValuePair;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Fieldable;

/**
 * @author Raymond Augé
 * @author Mate Thurzo
 */
public class PerFieldAnalyzer extends Analyzer implements Tokenizer {

	public PerFieldAnalyzer(
		Analyzer defaultAnalyzer, Map<String, Analyzer> analyzerMap) {

		_analyzer = defaultAnalyzer;

		for (Map.Entry<String, Analyzer> entry : analyzerMap.entrySet()) {
			addAnalyzer(entry.getKey(), entry.getValue());
		}
	}

	public void addAnalyzer(String fieldName, Analyzer analyzer) {
		_analyzers.put(
			fieldName,
			new ObjectValuePair<Pattern, Analyzer>(
				Pattern.compile(fieldName), analyzer));
	}

	public Analyzer getAnalyzer(String fieldName) {
		ObjectValuePair<Pattern, Analyzer> objectValuePair = _analyzers.get(
			fieldName);

		if (objectValuePair != null) {
			return objectValuePair.getValue();
		}

		for (ObjectValuePair<Pattern, Analyzer> curObjectValuePair :
				_analyzers.values()) {

			Pattern pattern = curObjectValuePair.getKey();

			Matcher matcher = pattern.matcher(fieldName);

			if (matcher.matches()) {
				return curObjectValuePair.getValue();
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
	public final TokenStream reusableTokenStream(
			String fieldName, Reader reader)
		throws IOException {

		Analyzer analyzer = getAnalyzer(fieldName);

		return analyzer.reusableTokenStream(fieldName, reader);
	}

	@Override
	public List<String> tokenize(
			String fieldName, String input, String languageId)
		throws SearchException {

		List<String> tokens = new ArrayList<String>();
		TokenStream tokenStream = null;

		try {
			String localizedFieldName = DocumentImpl.getLocalizedName(
				languageId, fieldName);

			Analyzer analyzer = getAnalyzer(localizedFieldName);

			tokenStream = analyzer.tokenStream(
				localizedFieldName, new StringReader(input));

			CharTermAttribute charTermAttribute = tokenStream.addAttribute(
				CharTermAttribute.class);

			tokenStream.reset();

			while (tokenStream.incrementToken()) {
				tokens.add(charTermAttribute.toString());
			}

			tokenStream.end();
		}
		catch (IOException ioe) {
			throw new SearchException(ioe);
		}
		finally {
			if (tokenStream != null) {
				try {
					tokenStream.close();
				}
				catch (IOException ioe) {
					if (_log.isWarnEnabled()) {
						_log.warn("Unable to close token stream", ioe);
					}
				}
			}
		}

		return tokens;
	}

	@Override
	public final TokenStream tokenStream(String fieldName, Reader reader) {
		Analyzer analyzer = getAnalyzer(fieldName);

		return analyzer.tokenStream(fieldName, reader);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PerFieldAnalyzer.class);

	private final Analyzer _analyzer;
	private final Map<String, ObjectValuePair<Pattern, Analyzer>> _analyzers =
		new LinkedHashMap<String, ObjectValuePair<Pattern, Analyzer>>();

}