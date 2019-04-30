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

package com.liferay.talend.source;

import com.fasterxml.jackson.databind.JsonNode;

import com.liferay.talend.configuration.LiferayInputMapperConfiguration;
import com.liferay.talend.data.store.GenericDataStore;
import com.liferay.talend.dataset.InputDataSet;
import com.liferay.talend.service.ConnectionService;
import com.liferay.talend.service.LiferayService;
import com.liferay.talend.util.JsonUtils;

import java.io.Serializable;

import java.net.URI;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;

import javax.json.JsonObject;

import javax.ws.rs.core.UriBuilder;

import org.talend.sdk.component.api.component.Icon;
import org.talend.sdk.component.api.component.Version;
import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.input.Assessor;
import org.talend.sdk.component.api.input.Emitter;
import org.talend.sdk.component.api.input.PartitionMapper;
import org.talend.sdk.component.api.input.PartitionSize;
import org.talend.sdk.component.api.input.Split;
import org.talend.sdk.component.api.service.record.RecordBuilderFactory;

/**
 * @author Zoltán Takács
 * @author Igor Beslic
 */
@Icon(custom = "LiferayInput", value = Icon.IconType.CUSTOM)
@PartitionMapper(family = "Liferay", name = "Input")
@Version(1)
public class LiferayInputMapper implements Serializable {

	public LiferayInputMapper(
		final ConnectionService connectionService,
		final LiferayService liferayService,
		@Option("liferayInputMapperConfiguration") final
			LiferayInputMapperConfiguration liferayInputMapperConfiguration,
		final RecordBuilderFactory recordBuilderFactory) {

		_connectionService = connectionService;
		_liferayService = liferayService;
		_liferayInputMapperConfiguration = liferayInputMapperConfiguration;
		_recordBuilderFactory = recordBuilderFactory;
	}

	@Emitter
	public LiferayInputEmitter createWorker() {
		return new LiferayInputEmitter(
			_connectionService, _liferayService,
			_liferayInputMapperConfiguration, _recordBuilderFactory);
	}

	@Assessor
	public long estimateSize() {
		InputDataSet inputDataSet =
			_liferayInputMapperConfiguration.getInputDataSet();

		GenericDataStore genericDataStore = inputDataSet.getGenericDataStore();

		URL openAPISpecURL = genericDataStore.getOpenAPISpecURL();

		URL serverURL = ConnectionService.getServerURL(genericDataStore);

		String appBasePathSegment =
			_liferayService.extractJaxRSAppBasePathSegment(openAPISpecURL);

		String endpoint = inputDataSet.getEndpoint();

		UriBuilder uriBuilder = UriBuilder.fromPath(serverURL.toExternalForm());

		URI resourceURI = uriBuilder.path(
			appBasePathSegment
		).path(
			endpoint
		).queryParam(
			"page", 1
		).queryParam(
			"pageSize", 1
		).build(
			inputDataSet.getFirstPathParam(), inputDataSet.getSecondPathParam(),
			inputDataSet.getThirdPathParam()
		);

		JsonObject responseJsonObject =
			_connectionService.getResponseJsonObject(
				inputDataSet.getGenericDataStore(), resourceURI);

		JsonNode responseJsonNode = JsonUtils.toJsonNode(responseJsonObject);

		JsonNode totalCountJsonNode = responseJsonNode.path("totalCount");

		return totalCountJsonNode.asLong();
	}

	@Split
	public List<LiferayInputMapper> split(
		@PartitionSize final long segmentSize) {

		int pageSize;

		if (segmentSize > _liferayInputMapperConfiguration.getBatchSize()) {
			pageSize = _liferayInputMapperConfiguration.getBatchSize();
		}
		else {
			pageSize = (int)segmentSize;
		}

		long segments = Math.round(Math.ceil(estimateSize() / segmentSize));
		long segmentPages = Math.round(Math.ceil(segmentSize / pageSize));

		List<LiferayInputMapper> liferayInputMappers = new ArrayList<>();

		for (int i = 0; i < segments; i++) {
			LiferayInputMapperConfiguration liferayInputMapperConfiguration =
				new LiferayInputMapperConfiguration(
					_liferayInputMapperConfiguration);

			int page = _liferayInputMapperConfiguration.getPage();

			liferayInputMapperConfiguration.setPage(
				(int)(page + (i * segmentPages)));

			liferayInputMapperConfiguration.setPageSize(pageSize);
			liferayInputMapperConfiguration.setSegmentPages((int)segmentPages);

			liferayInputMappers.add(
				new LiferayInputMapper(
					_connectionService, _liferayService,
					liferayInputMapperConfiguration, _recordBuilderFactory));
		}

		return liferayInputMappers;
	}

	private final ConnectionService _connectionService;
	private final LiferayInputMapperConfiguration
		_liferayInputMapperConfiguration;
	private final LiferayService _liferayService;
	private final RecordBuilderFactory _recordBuilderFactory;

}