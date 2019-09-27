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

package com.liferay.portal.search.engine.adapter.index;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael C. Han
 */
public class AnalyzeIndexResponse implements IndexResponse {

	public void addAnalysisIndexResponseTokens(
		AnalysisIndexResponseToken analysisIndexResponseToken) {

		_analysisIndexResponseTokens.add(analysisIndexResponseToken);
	}

	public void addAnalysisIndexResponseTokens(
		List<AnalysisIndexResponseToken> analysisIndexResponseToken) {

		_analysisIndexResponseTokens.addAll(analysisIndexResponseToken);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getDetailsAnalyzer()}, {@link
	 *             #getDetailsCharFilters()}, {@link
	 *             #getDetailsTokenFilters()}, {@link
	 *             #getDetailsTokenizer()}
	 */
	@Deprecated
	public String getAnalysisDetails() {
		return _analysisDetails;
	}

	public List<AnalysisIndexResponseToken> getAnalysisIndexResponseTokens() {
		return _analysisIndexResponseTokens;
	}

	public DetailsAnalyzer getDetailsAnalyzer() {
		return _detailsAnalyzer;
	}

	public List<DetailsCharFilter> getDetailsCharFilters() {
		return _detailsCharFilters;
	}

	public List<DetailsTokenFilter> getDetailsTokenFilters() {
		return _detailsTokenFilters;
	}

	public DetailsTokenizer getDetailsTokenizer() {
		return _detailsTokenizer;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #setDetailsAnalyzer(DetailsAnalyzer)}, {@link
	 *             #setDetailsCharFilters(List)}, {@link
	 *             #setDetailsTokenFilters(List)}, {@link
	 *             #setDetailsTokenizer(DetailsTokenizer)}
	 */
	@Deprecated
	public void setAnalysisDetails(String analysisDetails) {
		_analysisDetails = analysisDetails;
	}

	public void setDetailsAnalyzer(DetailsAnalyzer detailsAnalyzer) {
		_detailsAnalyzer = detailsAnalyzer;
	}

	public void setDetailsCharFilters(
		List<DetailsCharFilter> detailsCharFilters) {

		_detailsCharFilters = detailsCharFilters;
	}

	public void setDetailsTokenFilters(
		List<DetailsTokenFilter> detailsTokenFilters) {

		_detailsTokenFilters = detailsTokenFilters;
	}

	public void setDetailsTokenizer(DetailsTokenizer detailsTokenizer) {
		_detailsTokenizer = detailsTokenizer;
	}

	public static class DetailsAnalyzer {

		public DetailsAnalyzer(
			String analyzerName,
			List<AnalysisIndexResponseToken> analysisIndexResponseTokens) {

			_analyzerName = analyzerName;
			_analysisIndexResponseTokens = analysisIndexResponseTokens;
		}

		public List<AnalysisIndexResponseToken>
			getAnalysisIndexResponseTokens() {

			return _analysisIndexResponseTokens;
		}

		public String getAnalyzerName() {
			return _analyzerName;
		}

		private List<AnalysisIndexResponseToken> _analysisIndexResponseTokens;
		private final String _analyzerName;

	}

	public static class DetailsCharFilter {

		public DetailsCharFilter(
			String charFilterName, String[] charFilterTexts) {

			_charFilterName = charFilterName;
			_charFilterTexts = charFilterTexts;
		}

		public String getCharFilterName() {
			return _charFilterName;
		}

		public String[] getCharFilterTexts() {
			return _charFilterTexts;
		}

		private final String _charFilterName;
		private final String[] _charFilterTexts;

	}

	public static class DetailsTokenFilter {

		public DetailsTokenFilter(
			String tokenFilterName,
			List<AnalysisIndexResponseToken> analysisIndexResponseTokens) {

			_tokenFilterName = tokenFilterName;
			_analysisIndexResponseTokens = analysisIndexResponseTokens;
		}

		public List<AnalysisIndexResponseToken>
			getAnalysisIndexResponseTokens() {

			return _analysisIndexResponseTokens;
		}

		public String getTokenFilterName() {
			return _tokenFilterName;
		}

		private List<AnalysisIndexResponseToken> _analysisIndexResponseTokens;
		private final String _tokenFilterName;

	}

	public static class DetailsTokenizer {

		public DetailsTokenizer(
			String tokenizerName,
			List<AnalysisIndexResponseToken> analysisIndexResponseTokens) {

			_tokenizerName = tokenizerName;
			_analysisIndexResponseTokens = analysisIndexResponseTokens;
		}

		public List<AnalysisIndexResponseToken>
			getAnalysisIndexResponseTokens() {

			return _analysisIndexResponseTokens;
		}

		public String getTokenizerName() {
			return _tokenizerName;
		}

		private List<AnalysisIndexResponseToken> _analysisIndexResponseTokens;
		private final String _tokenizerName;

	}

	private String _analysisDetails;
	private final List<AnalysisIndexResponseToken>
		_analysisIndexResponseTokens = new ArrayList<>();
	private DetailsAnalyzer _detailsAnalyzer;
	private List<DetailsCharFilter> _detailsCharFilters;
	private List<DetailsTokenFilter> _detailsTokenFilters;
	private DetailsTokenizer _detailsTokenizer;

}