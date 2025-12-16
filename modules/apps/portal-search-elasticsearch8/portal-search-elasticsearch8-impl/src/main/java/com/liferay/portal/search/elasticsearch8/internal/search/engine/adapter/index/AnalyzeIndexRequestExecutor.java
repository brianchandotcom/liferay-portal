/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.index;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.analysis.CharFilter;
import co.elastic.clients.elasticsearch._types.analysis.TokenFilter;
import co.elastic.clients.elasticsearch._types.analysis.Tokenizer;
import co.elastic.clients.elasticsearch.indices.AnalyzeRequest;
import co.elastic.clients.elasticsearch.indices.AnalyzeResponse;
import co.elastic.clients.elasticsearch.indices.ElasticsearchIndicesClient;
import co.elastic.clients.elasticsearch.indices.analyze.AnalyzeDetail;
import co.elastic.clients.elasticsearch.indices.analyze.AnalyzeToken;
import co.elastic.clients.elasticsearch.indices.analyze.AnalyzerDetail;
import co.elastic.clients.elasticsearch.indices.analyze.CharFilterDetail;
import co.elastic.clients.elasticsearch.indices.analyze.ExplainAnalyzeToken;
import co.elastic.clients.elasticsearch.indices.analyze.TokenDetail;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.index.AnalysisIndexResponseToken;
import com.liferay.portal.search.engine.adapter.index.AnalyzeIndexRequest;
import com.liferay.portal.search.engine.adapter.index.AnalyzeIndexResponse;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael C. Han
 */
public class AnalyzeIndexRequestExecutor {

	public AnalyzeIndexRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	public AnalyzeIndexResponse execute(
		AnalyzeIndexRequest analyzeIndexRequest) {

		AnalyzeIndexResponse analyzeIndexResponse = new AnalyzeIndexResponse();

		AnalyzeResponse analyzeResponse = _getAnalyzeResponse(
			analyzeIndexRequest, createAnalyzeRequest(analyzeIndexRequest));

		if (analyzeResponse.detail() != null) {
			_processAnalyzeDetail(
				analyzeResponse.detail(), analyzeIndexResponse);
		}
		else {
			List<AnalysisIndexResponseToken> analysisIndexResponseTokens =
				_translateAnalyzeTokens(analyzeResponse.tokens());

			analyzeIndexResponse.addAnalysisIndexResponseTokens(
				analysisIndexResponseTokens);
		}

		return analyzeIndexResponse;
	}

	protected AnalyzeRequest createAnalyzeRequest(
		AnalyzeIndexRequest analyzeIndexRequest) {

		AnalyzeRequest.Builder builder = new AnalyzeRequest.Builder();

		builder.attributes(
			ListUtil.fromArray(analyzeIndexRequest.getAttributesArray()));
		builder.explain(analyzeIndexRequest.isExplain());
		builder.index(analyzeIndexRequest.getIndexName());
		builder.text(ListUtil.fromArray(analyzeIndexRequest.getTexts()));

		if (Validator.isNotNull(analyzeIndexRequest.getAnalyzer())) {
			builder.analyzer(analyzeIndexRequest.getAnalyzer());
		}
		else if (Validator.isNotNull(analyzeIndexRequest.getFieldName())) {
			builder.field(analyzeIndexRequest.getFieldName());
		}
		else if (Validator.isNotNull(analyzeIndexRequest.getNormalizer())) {
			builder.normalizer(analyzeIndexRequest.getNormalizer());
		}
		else {
			if (Validator.isNotNull(analyzeIndexRequest.getTokenizer())) {
				builder.tokenizer(
					Tokenizer.of(
						tokenizer -> tokenizer.name(
							analyzeIndexRequest.getTokenizer())));
			}

			for (String charFilter : analyzeIndexRequest.getCharFilters()) {
				builder.charFilter(
					CharFilter.of(
						elasticsearchCharFilter -> elasticsearchCharFilter.name(
							charFilter)));
			}

			for (String tokenFilter : analyzeIndexRequest.getTokenFilters()) {
				builder.filter(
					TokenFilter.of(
						elasticsearchTokenFilter ->
							elasticsearchTokenFilter.name(tokenFilter)));
			}
		}

		return builder.build();
	}

	private AnalyzeResponse _getAnalyzeResponse(
		AnalyzeIndexRequest analyzeIndexRequest,
		AnalyzeRequest analyzeRequest) {

		ElasticsearchClient elasticsearchClient =
			_elasticsearchClientResolver.getElasticsearchClient(
				analyzeIndexRequest.getConnectionId(),
				analyzeIndexRequest.isPreferLocalCluster());

		ElasticsearchIndicesClient elasticsearchIndicesClient =
			elasticsearchClient.indices();

		try {
			return elasticsearchIndicesClient.analyze(analyzeRequest);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private void _processAnalyzeDetail(
		AnalyzeDetail analyzeDetail,
		AnalyzeIndexResponse analyzeIndexResponse) {

		if (analyzeDetail.analyzer() != null) {
			AnalyzerDetail analyzerDetail = analyzeDetail.analyzer();

			AnalyzeIndexResponse.DetailsAnalyzer detailsAnalyzer =
				new AnalyzeIndexResponse.DetailsAnalyzer(
					analyzerDetail.name(),
					_translateExplainAnalyzeTokens(analyzerDetail.tokens()));

			analyzeIndexResponse.setDetailsAnalyzer(detailsAnalyzer);
		}
		else {
			List<AnalyzeIndexResponse.DetailsCharFilter> detailsCharFilters =
				new ArrayList<>();

			for (CharFilterDetail charFilterDetail :
					analyzeDetail.charfilters()) {

				String charFilterName = charFilterDetail.name();
				String[] charFilterTexts = ArrayUtil.toStringArray(
					charFilterDetail.filteredText());

				AnalyzeIndexResponse.DetailsCharFilter detailsCharFilter =
					new AnalyzeIndexResponse.DetailsCharFilter(
						charFilterName, charFilterTexts);

				detailsCharFilters.add(detailsCharFilter);
			}

			analyzeIndexResponse.setDetailsCharFilters(detailsCharFilters);

			List<AnalyzeIndexResponse.DetailsTokenFilter> detailsTokenFilters =
				new ArrayList<>();

			for (TokenDetail tokenDetail : analyzeDetail.tokenfilters()) {
				AnalyzeIndexResponse.DetailsTokenFilter detailsTokenFilter =
					new AnalyzeIndexResponse.DetailsTokenFilter(
						tokenDetail.name(),
						_translateExplainAnalyzeTokens(tokenDetail.tokens()));

				detailsTokenFilters.add(detailsTokenFilter);
			}

			analyzeIndexResponse.setDetailsTokenFilters(detailsTokenFilters);

			TokenDetail tokenDetail = analyzeDetail.tokenizer();

			AnalyzeIndexResponse.DetailsTokenizer detailsTokenizer =
				new AnalyzeIndexResponse.DetailsTokenizer(
					tokenDetail.name(),
					_translateExplainAnalyzeTokens(tokenDetail.tokens()));

			analyzeIndexResponse.setDetailsTokenizer(detailsTokenizer);
		}
	}

	private List<AnalysisIndexResponseToken> _translateAnalyzeTokens(
		List<AnalyzeToken> analyzeTokens) {

		return TransformUtil.transform(
			analyzeTokens,
			analyzeToken -> {
				AnalysisIndexResponseToken analysisIndexResponseToken =
					new AnalysisIndexResponseToken(analyzeToken.token());

				analysisIndexResponseToken.setEndOffset(
					Math.toIntExact(analyzeToken.endOffset()));
				analysisIndexResponseToken.setPosition(
					Math.toIntExact(analyzeToken.position()));

				if (analyzeToken.positionlength() != null) {
					analysisIndexResponseToken.setPositionLength(
						Math.toIntExact(analyzeToken.positionlength()));
				}

				analysisIndexResponseToken.setStartOffset(
					Math.toIntExact(analyzeToken.startOffset()));
				analysisIndexResponseToken.setType(analyzeToken.type());

				return analysisIndexResponseToken;
			});
	}

	private List<AnalysisIndexResponseToken> _translateExplainAnalyzeTokens(
		List<ExplainAnalyzeToken> explainAnalyzeTokens) {

		return TransformUtil.transform(
			explainAnalyzeTokens,
			explainAnalyzeToken -> {
				AnalysisIndexResponseToken analysisIndexResponseToken =
					new AnalysisIndexResponseToken(explainAnalyzeToken.token());

				analysisIndexResponseToken.setEndOffset(
					Math.toIntExact(explainAnalyzeToken.endOffset()));
				analysisIndexResponseToken.setPosition(
					Math.toIntExact(explainAnalyzeToken.position()));
				analysisIndexResponseToken.setPositionLength(
					Math.toIntExact(explainAnalyzeToken.positionlength()));
				analysisIndexResponseToken.setStartOffset(
					Math.toIntExact(explainAnalyzeToken.startOffset()));
				analysisIndexResponseToken.setType(explainAnalyzeToken.type());

				return analysisIndexResponseToken;
			});
	}

	private final ElasticsearchClientResolver _elasticsearchClientResolver;

}