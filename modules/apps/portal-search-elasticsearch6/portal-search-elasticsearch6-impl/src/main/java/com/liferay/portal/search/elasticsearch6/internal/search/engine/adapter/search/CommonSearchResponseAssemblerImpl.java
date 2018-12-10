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

package com.liferay.portal.search.elasticsearch6.internal.search.engine.adapter.search;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.engine.adapter.search.BaseSearchRequest;
import com.liferay.portal.search.engine.adapter.search.BaseSearchResponse;

import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.search.FuzzyQuery;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.ToXContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.search.MatchQuery;
import org.elasticsearch.search.profile.ProfileShardResult;
import org.elasticsearch.search.profile.query.QueryProfileShardResult;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = CommonSearchResponseAssembler.class)
public class CommonSearchResponseAssemblerImpl
	implements CommonSearchResponseAssembler {

	@Override
	public void assemble(
		SearchRequestBuilder searchRequestBuilder,
		SearchResponse searchResponse, BaseSearchRequest baseSearchRequest,
		BaseSearchResponse baseSearchResponse) {

		setExecutionProfile(searchResponse, baseSearchResponse);
		setExecutionTime(searchResponse, baseSearchResponse);

		baseSearchResponse.setSearchRequestString(
			StringUtil.removeSubstrings(
				searchRequestBuilder.toString(), ADJUST_PURE_NEGATIVE_STRING,
				FUZZY_TRANSPOSITIONS_STRING, ZERO_TERMS_QUERY_STRING));

		if (baseSearchRequest.isIncludeResponseString()) {
			baseSearchResponse.setSearchResponseString(
				searchResponse.toString());
		}

		baseSearchResponse.setTerminatedEarly(
			GetterUtil.getBoolean(searchResponse.isTerminatedEarly()));
		baseSearchResponse.setTimedOut(searchResponse.isTimedOut());
	}

	protected String getProfileShardResultString(
			ProfileShardResult profileShardResult)
		throws IOException {

		XContentBuilder xContentBuilder = XContentFactory.contentBuilder(
			XContentType.JSON);

		List<QueryProfileShardResult> queryProfileShardResults =
			profileShardResult.getQueryProfileResults();

		queryProfileShardResults.forEach(
			queryProfileShardResult -> {
				try {
					xContentBuilder.startObject();

					queryProfileShardResult.toXContent(
						xContentBuilder, ToXContent.EMPTY_PARAMS);

					xContentBuilder.endObject();
				}
				catch (IOException ioe) {
					if (_log.isDebugEnabled()) {
						_log.debug(ioe, ioe);
					}
				}
			});

		return Strings.toString(xContentBuilder);
	}

	protected void setExecutionProfile(
		SearchResponse searchResponse, BaseSearchResponse baseSearchResponse) {

		Map<String, ProfileShardResult> profileShardResults =
			searchResponse.getProfileResults();

		if (MapUtil.isEmpty(profileShardResults)) {
			return;
		}

		Map<String, String> executionProfile = new HashMap<>();

		profileShardResults.forEach(
			(shardKey, profileShardResult) -> {
				try {
					executionProfile.put(
						shardKey,
						getProfileShardResultString(profileShardResult));
				}
				catch (IOException ioe) {
					if (_log.isInfoEnabled()) {
						_log.info(ioe, ioe);
					}
				}
			});

		baseSearchResponse.setExecutionProfile(executionProfile);
	}

	protected void setExecutionTime(
		SearchResponse searchResponse, BaseSearchResponse baseSearchResponse) {

		TimeValue tookTimeValue = searchResponse.getTook();

		baseSearchResponse.setExecutionTime(tookTimeValue.getMillis());
	}

	protected static final String ADJUST_PURE_NEGATIVE_STRING =
		",\"adjust_pure_negative\":true";

	protected static final String FUZZY_TRANSPOSITIONS_STRING =
		",\"fuzzy_transpositions\":" + FuzzyQuery.defaultTranspositions;

	protected static final String ZERO_TERMS_QUERY_STRING =
		",\"zero_terms_query\":\"" + MatchQuery.DEFAULT_ZERO_TERMS_QUERY + "\"";

	private static final Log _log = LogFactoryUtil.getLog(
		CommonSearchResponseAssemblerImpl.class);

}