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

package com.liferay.portal.search.solr7.internal.search.engine.adapter.search;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.search.engine.adapter.search.BaseSearchRequest;
import com.liferay.portal.search.engine.adapter.search.BaseSearchResponse;
import com.liferay.portal.search.solr7.internal.stats.StatsTranslator;
import com.liferay.portal.search.stats.StatsResponse;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.FieldStatsInfo;
import org.apache.solr.client.solrj.response.QueryResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(immediate = true, service = BaseSearchResponseAssembler.class)
public class BaseSearchResponseAssemblerImpl
	implements BaseSearchResponseAssembler {

	@Override
	public void assemble(
		BaseSearchResponse baseSearchResponse, SolrQuery solrQuery,
		QueryResponse queryResponse, BaseSearchRequest baseSearchRequest) {

		setExecutionTime(baseSearchResponse, queryResponse);
		setSearchRequestString(baseSearchResponse, solrQuery);
		setSearchResponseString(
			baseSearchResponse, queryResponse, baseSearchRequest);
		setStatsResponses(baseSearchResponse, queryResponse, baseSearchRequest);
	}

	protected Map<String, StatsResponse> getStatsResponseMap(
		QueryResponse queryResponse) {

		Map<String, FieldStatsInfo> fieldStatsInfoMap =
			queryResponse.getFieldStatsInfo();

		if (MapUtil.isEmpty(fieldStatsInfoMap)) {
			return null;
		}

		Map<String, StatsResponse> statsResponseMap = new LinkedHashMap<>();

		for (FieldStatsInfo fieldStatsInfo : fieldStatsInfoMap.values()) {
			StatsResponse statsResponse = _statsTranslator.translateResponse(
				fieldStatsInfo);

			statsResponseMap.put(fieldStatsInfo.getName(), statsResponse);
		}

		return statsResponseMap;
	}

	protected void setExecutionTime(
		BaseSearchResponse baseSearchResponse, QueryResponse queryResponse) {

		baseSearchResponse.setExecutionTime(queryResponse.getElapsedTime());
	}

	protected void setSearchRequestString(
		BaseSearchResponse baseSearchResponse, SolrQuery solrQuery) {

		baseSearchResponse.setSearchRequestString(solrQuery.toString());
	}

	protected void setSearchResponseString(
		BaseSearchResponse baseSearchResponse, QueryResponse queryResponse,
		BaseSearchRequest baseSearchRequest) {

		if (baseSearchRequest.isIncludeResponseString()) {
			baseSearchResponse.setSearchResponseString(
				queryResponse.toString());
		}
	}

	protected void setStatsResponses(
		BaseSearchResponse baseSearchResponse, QueryResponse queryResponse,
		BaseSearchRequest baseSearchRequest) {

		Map<String, StatsResponse> statsResponseMap = getStatsResponseMap(
			queryResponse);

		if (statsResponseMap != null) {
			updateStatsResponses(
				baseSearchResponse, statsResponseMap, baseSearchRequest);
		}
	}

	@Reference(unbind = "-")
	protected void setStatsTranslator(StatsTranslator statsTranslator) {
		_statsTranslator = statsTranslator;
	}

	protected void updateStatsResponses(
		BaseSearchResponse baseSearchResponse,
		Map<String, StatsResponse> statsResponseMap,
		BaseSearchRequest baseSearchRequest) {

		if (!ListUtil.isEmpty(baseSearchRequest.getStatsRequests())) {
			for (Map.Entry<String, StatsResponse> entry :
					statsResponseMap.entrySet()) {

				baseSearchResponse.addStatsResponse(entry.getValue());
			}
		}
	}

	private StatsTranslator _statsTranslator;

}