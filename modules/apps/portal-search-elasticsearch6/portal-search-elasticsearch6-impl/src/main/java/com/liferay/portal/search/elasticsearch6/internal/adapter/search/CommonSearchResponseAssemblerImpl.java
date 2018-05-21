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

package com.liferay.portal.search.elasticsearch6.internal.adapter.search;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.search.engine.adapter.search.BaseSearchResponse;

import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.ToXContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.profile.ProfileShardResult;
import org.elasticsearch.search.profile.query.QueryProfileShardResult;

/**
 * @author Michael C. Han
 */
public class CommonSearchResponseAssemblerImpl
	implements CommonSearchResponseAssembler {

	@Override
	public void assemble(
		SearchResponse searchResponse, BaseSearchResponse baseSearchResponse,
		String searchRequestBuilderString) {

		TimeValue tookTimeValue = searchResponse.getTook();

		baseSearchResponse.setExecutionTime(tookTimeValue.getMillis());

		Map<String, ProfileShardResult> profileShardResults =
			searchResponse.getProfileResults();

		if (!MapUtil.isEmpty(profileShardResults)) {
			Map<String, String> executionProfile = new HashMap<>();

			profileShardResults.forEach(
				(shardKey, profileShardResult) -> {
					try {
						XContentBuilder xContentBuilder =
							XContentFactory.contentBuilder(XContentType.JSON);

						List<QueryProfileShardResult> queryProfileShardResults =
							profileShardResult.getQueryProfileResults();

						queryProfileShardResults.forEach(
							queryProfileShardResult -> {
								try {
									xContentBuilder.startObject();

									queryProfileShardResult.toXContent(
										xContentBuilder,
										ToXContent.EMPTY_PARAMS);

									xContentBuilder.endObject();
								}
								catch (IOException ioe) {
									if (_log.isDebugEnabled()) {
										_log.debug(ioe, ioe);
									}
								}

							});

						executionProfile.put(
							shardKey, xContentBuilder.string());
					}
					catch (IOException ioe) {
						if (_log.isInfoEnabled()) {
							_log.info(ioe, ioe);
						}
					}

					baseSearchResponse.setExecutionProfile(executionProfile);
				});

			baseSearchResponse.setExecutionProfile(executionProfile);
		}

		baseSearchResponse.setTimedOut(searchResponse.isTimedOut());
		baseSearchResponse.setTerminatedEarly(
			searchResponse.isTerminatedEarly());

		baseSearchResponse.setSearchRequestString(searchRequestBuilderString);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommonSearchResponseAssemblerImpl.class);

}