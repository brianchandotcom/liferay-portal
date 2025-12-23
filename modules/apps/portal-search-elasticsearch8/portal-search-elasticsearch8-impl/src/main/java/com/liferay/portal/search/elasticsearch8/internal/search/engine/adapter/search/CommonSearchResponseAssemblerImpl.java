/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.search;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch.core.search.Profile;
import co.elastic.clients.elasticsearch.core.search.ResponseBody;
import co.elastic.clients.elasticsearch.core.search.SearchProfile;
import co.elastic.clients.elasticsearch.core.search.ShardProfile;
import co.elastic.clients.json.JsonData;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.search.elasticsearch8.internal.stats.StatsTranslator;
import com.liferay.portal.search.elasticsearch8.internal.util.JsonpUtil;
import com.liferay.portal.search.elasticsearch8.internal.util.SetterUtil;
import com.liferay.portal.search.engine.adapter.search.BaseSearchRequest;
import com.liferay.portal.search.engine.adapter.search.BaseSearchResponse;
import com.liferay.portal.search.stats.StatsRequest;

import java.io.IOException;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = CommonSearchResponseAssembler.class)
public class CommonSearchResponseAssemblerImpl
	implements CommonSearchResponseAssembler {

	@Override
	public void assemble(
		BaseSearchRequest baseSearchRequest,
		BaseSearchResponse baseSearchResponse,
		ResponseBody<JsonData> responseBody, String searchRequestString) {

		_setExecutionProfile(baseSearchResponse, responseBody);
		_setStatsResponses(
			responseBody.aggregations(), baseSearchResponse,
			baseSearchRequest.getStatsRequests());

		baseSearchResponse.setExecutionTime(responseBody.took());

		SetterUtil.setNotBlankString(
			baseSearchResponse::setPointInTimeId, responseBody.pitId());
		SetterUtil.setNotBlankString(
			baseSearchResponse::setScrollId, responseBody.scrollId());

		baseSearchResponse.setSearchRequestString(searchRequestString);

		if (baseSearchRequest.isIncludeResponseString()) {
			baseSearchResponse.setSearchResponseString(
				JsonpUtil.toString(responseBody));
		}

		SetterUtil.setNotNullBoolean(
			baseSearchResponse::setTerminatedEarly,
			responseBody.terminatedEarly());
		SetterUtil.setNotNullBoolean(
			baseSearchResponse::setTimedOut, responseBody.timedOut());
	}

	private String _getShardProfileString(ShardProfile shardProfile)
		throws IOException {

		List<SearchProfile> searchProfiles = shardProfile.searches();

		StringJoiner joiner = new StringJoiner(",");

		searchProfiles.forEach(
			searchProfile -> joiner.add(JsonpUtil.toString(searchProfile)));

		return "{" + joiner.toString() + "}";
	}

	private void _setExecutionProfile(
		BaseSearchResponse baseSearchResponse,
		ResponseBody<JsonData> responseBody) {

		Profile profile = responseBody.profile();

		if (profile == null) {
			return;
		}

		List<ShardProfile> shardProfiles = profile.shards();

		if (ListUtil.isEmpty(shardProfiles)) {
			return;
		}

		Map<String, String> executionProfiles = new HashMap<>();

		shardProfiles.forEach(
			shardProfile -> {
				try {
					executionProfiles.put(
						shardProfile.id(),
						_getShardProfileString(shardProfile));
				}
				catch (IOException ioException) {
					if (_log.isInfoEnabled()) {
						_log.info(ioException);
					}
				}
			});

		baseSearchResponse.setExecutionProfile(executionProfiles);
	}

	private void _setStatsResponses(
		Map<String, Aggregate> aggregations,
		BaseSearchResponse baseSearchResponse,
		Collection<StatsRequest> statsRequests) {

		if (MapUtil.isEmpty(aggregations)) {
			return;
		}

		for (StatsRequest statsRequest : statsRequests) {
			baseSearchResponse.addStatsResponse(
				_statsTranslator.translateResponse(aggregations, statsRequest));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommonSearchResponseAssemblerImpl.class);

	@Reference
	private StatsTranslator _statsTranslator;

}