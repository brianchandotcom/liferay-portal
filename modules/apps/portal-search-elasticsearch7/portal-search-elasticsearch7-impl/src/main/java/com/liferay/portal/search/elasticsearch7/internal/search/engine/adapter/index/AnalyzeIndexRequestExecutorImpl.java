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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch7.internal.io.StringOutputStream;
import com.liferay.portal.search.engine.adapter.index.AnalysisIndexResponseToken;
import com.liferay.portal.search.engine.adapter.index.AnalyzeIndexRequest;
import com.liferay.portal.search.engine.adapter.index.AnalyzeIndexResponse;

import java.io.IOException;

import org.elasticsearch.action.admin.indices.analyze.AnalyzeAction;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequestBuilder;
import org.elasticsearch.common.io.stream.OutputStreamStreamOutput;

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

		AnalyzeRequestBuilder analyzeRequestBuilder =
			createAnalyzeRequestBuilder(analyzeIndexRequest);

		AnalyzeAction.Response analyzeResponse = analyzeRequestBuilder.get();

		AnalyzeIndexResponse analyzeIndexResponse = new AnalyzeIndexResponse();

		for (AnalyzeAction.AnalyzeToken analyzeToken :
				analyzeResponse.getTokens()) {

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

			analyzeIndexResponse.addAnalysisIndexResponseTokens(
				analysisIndexResponseToken);
		}

		processDetailAnalyzeResponse(
			analyzeIndexResponse, analyzeResponse.detail());

		return analyzeIndexResponse;
	}

	protected AnalyzeRequestBuilder createAnalyzeRequestBuilder(
		AnalyzeIndexRequest analyzeIndexRequest) {

		AnalyzeRequestBuilder analyzeRequestBuilder = new AnalyzeRequestBuilder(
			_elasticsearchClientResolver.getClient(), AnalyzeAction.INSTANCE);

		if (Validator.isNotNull(analyzeIndexRequest.getAnalyzer())) {
			analyzeRequestBuilder.setAnalyzer(
				analyzeIndexRequest.getAnalyzer());
		}

		analyzeRequestBuilder.setAttributes(
			analyzeIndexRequest.getAttributesArray());
		analyzeRequestBuilder.setExplain(analyzeIndexRequest.isExplain());

		if (Validator.isNotNull(analyzeIndexRequest.getFieldName())) {
			analyzeRequestBuilder.setField(analyzeIndexRequest.getFieldName());
		}

		analyzeRequestBuilder.setIndex(analyzeIndexRequest.getIndexName());

		if (Validator.isNotNull(analyzeIndexRequest.getNormalizer())) {
			analyzeRequestBuilder.setNormalizer(
				analyzeIndexRequest.getNormalizer());
		}

		analyzeRequestBuilder.setText(analyzeIndexRequest.getTexts());

		if (Validator.isNotNull(analyzeIndexRequest.getTokenizer())) {
			analyzeRequestBuilder.setTokenizer(
				analyzeIndexRequest.getTokenizer());
		}

		for (String charFilter : analyzeIndexRequest.getCharFilters()) {
			analyzeRequestBuilder.addCharFilter(charFilter);
		}

		for (String tokenFilter : analyzeIndexRequest.getTokenFilters()) {
			analyzeRequestBuilder.addTokenFilter(tokenFilter);
		}

		return analyzeRequestBuilder;
	}

	protected void processDetailAnalyzeResponse(
		AnalyzeIndexResponse analyzeIndexResponse,
		AnalyzeAction.DetailAnalyzeResponse detailAnalyzeResponse) {

		if (detailAnalyzeResponse != null) {
			StringOutputStream stringOutputStream = new StringOutputStream();

			OutputStreamStreamOutput outputStreamStreamOutput =
				new OutputStreamStreamOutput(stringOutputStream);

			try {
				detailAnalyzeResponse.writeTo(outputStreamStreamOutput);

				outputStreamStreamOutput.flush();
			}
			catch (IOException ioe) {
				if (_log.isDebugEnabled()) {
					_log.debug(ioe, ioe);
				}
			}
			finally {
				try {
					outputStreamStreamOutput.close();
				}
				catch (IOException ioe) {
					if (_log.isDebugEnabled()) {
						_log.debug(ioe, ioe);
					}
				}
			}

			analyzeIndexResponse.setAnalysisDetails(
				stringOutputStream.toString());
		}
	}

	@Reference(unbind = "-")
	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyzeIndexRequestExecutorImpl.class);

	private ElasticsearchClientResolver _elasticsearchClientResolver;

}