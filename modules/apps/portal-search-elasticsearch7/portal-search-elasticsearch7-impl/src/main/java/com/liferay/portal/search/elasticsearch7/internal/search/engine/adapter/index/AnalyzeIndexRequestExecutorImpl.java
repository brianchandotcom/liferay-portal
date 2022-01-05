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

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.index;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.index.AnalysisIndexResponseToken;
import com.liferay.portal.search.engine.adapter.index.AnalyzeIndexRequest;
import com.liferay.portal.search.engine.adapter.index.AnalyzeIndexResponse;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.AnalyzeRequest;
import org.elasticsearch.client.indices.AnalyzeResponse;
import org.elasticsearch.client.indices.DetailAnalyzeResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = AnalyzeIndexRequestExecutor.class)
public class AnalyzeIndexRequestExecutorImpl
	implements AnalyzeIndexRequestExecutor {

	@Override
	public AnalyzeIndexResponse execute(
		AnalyzeIndexRequest analyzeIndexRequest) {

		AnalyzeRequest analyzeRequest = createAnalyzeRequest(
			analyzeIndexRequest);

		AnalyzeResponse analyzeResponse = _getAnalyzeResponse(
			analyzeRequest, analyzeIndexRequest);

		AnalyzeIndexResponse analyzeIndexResponse = new AnalyzeIndexResponse();

		if (analyzeResponse.detail() != null) {
			_processDetailAnalyzeResponse(
				analyzeIndexResponse, analyzeResponse.detail());
		}
		else {
			List<AnalysisIndexResponseToken> analysisIndexResponseTokens =
				_translateAnalyzeResponseTokens(analyzeResponse.getTokens());

			analyzeIndexResponse.addAnalysisIndexResponseTokens(
				analysisIndexResponseTokens);
		}

		return analyzeIndexResponse;
	}

	protected AnalyzeRequest createAnalyzeRequest(
		AnalyzeIndexRequest analyzeIndexRequest) {

		AnalyzeRequest analyzeRequest;

		if (Validator.isNotNull(analyzeIndexRequest.getAnalyzer())) {
			analyzeRequest = AnalyzeRequest.withIndexAnalyzer(
				analyzeIndexRequest.getIndexName(),
				analyzeIndexRequest.getAnalyzer(),
				analyzeIndexRequest.getTexts());
		}
		else if (Validator.isNotNull(analyzeIndexRequest.getFieldName())) {
			analyzeRequest = AnalyzeRequest.withField(
				analyzeIndexRequest.getIndexName(),
				analyzeIndexRequest.getFieldName(),
				analyzeIndexRequest.getTexts());
		}
		else if (Validator.isNotNull(analyzeIndexRequest.getNormalizer())) {
			analyzeRequest = AnalyzeRequest.withNormalizer(
				analyzeIndexRequest.getIndexName(),
				analyzeIndexRequest.getNormalizer(),
				analyzeIndexRequest.getTexts());
		}
		else {
			AnalyzeRequest.CustomAnalyzerBuilder customAnalyzerBuilder;

			if (Validator.isNotNull(analyzeIndexRequest.getTokenizer())) {
				customAnalyzerBuilder = AnalyzeRequest.buildCustomAnalyzer(
					analyzeIndexRequest.getIndexName(),
					analyzeIndexRequest.getTokenizer());
			}
			else {
				customAnalyzerBuilder = AnalyzeRequest.buildCustomNormalizer(
					analyzeIndexRequest.getIndexName());
			}

			analyzeRequest = createAnalyzeRequest(
				customAnalyzerBuilder, analyzeIndexRequest);
		}

		analyzeRequest.attributes(analyzeIndexRequest.getAttributesArray());
		analyzeRequest.explain(analyzeIndexRequest.isExplain());

		return analyzeRequest;
	}

	protected AnalyzeRequest createAnalyzeRequest(
		AnalyzeRequest.CustomAnalyzerBuilder customAnalyzerBuilder,
		AnalyzeIndexRequest analyzeIndexRequest) {

		for (String charFilter : analyzeIndexRequest.getCharFilters()) {
			customAnalyzerBuilder.addCharFilter(charFilter);
		}

		for (String tokenFilter : analyzeIndexRequest.getTokenFilters()) {
			customAnalyzerBuilder.addTokenFilter(tokenFilter);
		}

		return customAnalyzerBuilder.build(analyzeIndexRequest.getTexts());
	}

	@Reference(unbind = "-")
	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	private AnalyzeResponse _getAnalyzeResponse(
		AnalyzeRequest analyzeRequest,
		AnalyzeIndexRequest analyzeIndexRequest) {

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient(
				analyzeIndexRequest.getConnectionId(),
				analyzeIndexRequest.isPreferLocalCluster());

		IndicesClient indicesClient = restHighLevelClient.indices();

		try {
			return indicesClient.analyze(
				analyzeRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private void _processDetailAnalyzeResponse(
		AnalyzeIndexResponse analyzeIndexResponse,
		DetailAnalyzeResponse detailAnalyzeResponse) {

		if (detailAnalyzeResponse.analyzer() != null) {
			DetailAnalyzeResponse.AnalyzeTokenList analyzeTokenList =
				detailAnalyzeResponse.analyzer();

			String analyzerName = analyzeTokenList.getName();

			List<AnalysisIndexResponseToken> analysisIndexResponseTokens =
				_translateAnalyzeResponseTokens(
					ListUtil.fromArray(analyzeTokenList.getTokens()));

			AnalyzeIndexResponse.DetailsAnalyzer detailsAnalyzer =
				new AnalyzeIndexResponse.DetailsAnalyzer(
					analyzerName, analysisIndexResponseTokens);

			analyzeIndexResponse.setDetailsAnalyzer(detailsAnalyzer);
		}
		else {
			List<AnalyzeIndexResponse.DetailsCharFilter> detailsCharFilters =
				new ArrayList<>();

			DetailAnalyzeResponse.CharFilteredText[] charFilteredTexts =
				detailAnalyzeResponse.charfilters();

			for (DetailAnalyzeResponse.CharFilteredText charFilteredText :
					charFilteredTexts) {

				String charFilterName = charFilteredText.getName();
				String[] charFilterTexts = charFilteredText.getTexts();

				AnalyzeIndexResponse.DetailsCharFilter detailsCharFilter =
					new AnalyzeIndexResponse.DetailsCharFilter(
						charFilterName, charFilterTexts);

				detailsCharFilters.add(detailsCharFilter);
			}

			analyzeIndexResponse.setDetailsCharFilters(detailsCharFilters);

			List<AnalyzeIndexResponse.DetailsTokenFilter> detailsTokenFilters =
				new ArrayList<>();

			DetailAnalyzeResponse.AnalyzeTokenList[] analyzeTokenLists =
				detailAnalyzeResponse.tokenfilters();

			for (DetailAnalyzeResponse.AnalyzeTokenList analyzeTokenList :
					analyzeTokenLists) {

				String tokenFilterName = analyzeTokenList.getName();

				List<AnalysisIndexResponseToken> analysisIndexResponseTokens =
					_translateAnalyzeResponseTokens(
						ListUtil.fromArray(analyzeTokenList.getTokens()));

				AnalyzeIndexResponse.DetailsTokenFilter detailsTokenFilter =
					new AnalyzeIndexResponse.DetailsTokenFilter(
						tokenFilterName, analysisIndexResponseTokens);

				detailsTokenFilters.add(detailsTokenFilter);
			}

			analyzeIndexResponse.setDetailsTokenFilters(detailsTokenFilters);

			DetailAnalyzeResponse.AnalyzeTokenList tokenizerAnalyzeTokenList =
				detailAnalyzeResponse.tokenizer();

			String tokenizerName = tokenizerAnalyzeTokenList.getName();

			List<AnalysisIndexResponseToken> analysisIndexResponseTokens =
				_translateAnalyzeResponseTokens(
					ListUtil.fromArray(tokenizerAnalyzeTokenList.getTokens()));

			AnalyzeIndexResponse.DetailsTokenizer detailsTokenizer =
				new AnalyzeIndexResponse.DetailsTokenizer(
					tokenizerName, analysisIndexResponseTokens);

			analyzeIndexResponse.setDetailsTokenizer(detailsTokenizer);
		}
	}

	private List<AnalysisIndexResponseToken> _translateAnalyzeResponseTokens(
		List<AnalyzeResponse.AnalyzeToken> analyzeTokens) {

		List<AnalysisIndexResponseToken> analysisIndexResponseTokens =
			new ArrayList<>();

		for (AnalyzeResponse.AnalyzeToken analyzeToken : analyzeTokens) {
			AnalysisIndexResponseToken analysisIndexResponseToken =
				new AnalysisIndexResponseToken(analyzeToken.getTerm());

			analysisIndexResponseToken.setAttributes(
				analyzeToken.getAttributes());
			analysisIndexResponseToken.setEndOffset(
				analyzeToken.getEndOffset());
			analysisIndexResponseToken.setPosition(analyzeToken.getPosition());
			analysisIndexResponseToken.setPositionLength(
				analyzeToken.getPositionLength());
			analysisIndexResponseToken.setStartOffset(
				analyzeToken.getStartOffset());
			analysisIndexResponseToken.setType(analyzeToken.getType());

			analysisIndexResponseTokens.add(analysisIndexResponseToken);
		}

		return analysisIndexResponseTokens;
	}

	private ElasticsearchClientResolver _elasticsearchClientResolver;

}