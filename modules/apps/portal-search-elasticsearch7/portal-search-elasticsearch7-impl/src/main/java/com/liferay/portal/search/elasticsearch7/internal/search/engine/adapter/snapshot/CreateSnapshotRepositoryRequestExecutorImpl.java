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

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.snapshot;

import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.snapshot.CreateSnapshotRepositoryRequest;
import com.liferay.portal.search.engine.adapter.snapshot.CreateSnapshotRepositoryResponse;

import org.elasticsearch.action.admin.cluster.repositories.put.PutRepositoryAction;
import org.elasticsearch.action.admin.cluster.repositories.put.PutRepositoryRequestBuilder;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.repositories.fs.FsRepository;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = CreateSnapshotRepositoryRequestExecutor.class)
public class CreateSnapshotRepositoryRequestExecutorImpl
	implements CreateSnapshotRepositoryRequestExecutor {

	@Override
	public CreateSnapshotRepositoryResponse execute(
		CreateSnapshotRepositoryRequest createSnapshotRepositoryRequest) {

		PutRepositoryRequestBuilder putRepositoryRequestBuilder =
			createPutRepositoryRequestBuilder(createSnapshotRepositoryRequest);

		AcknowledgedResponse acknowledgedResponse =
			putRepositoryRequestBuilder.get();

		return new CreateSnapshotRepositoryResponse(
			acknowledgedResponse.isAcknowledged());
	}

	protected PutRepositoryRequestBuilder createPutRepositoryRequestBuilder(
		CreateSnapshotRepositoryRequest createSnapshotRepositoryRequest) {

		PutRepositoryRequestBuilder putRepositoryRequestBuilder =
			new PutRepositoryRequestBuilder(
				_elasticsearchClientResolver.getClient(),
				PutRepositoryAction.INSTANCE);

		putRepositoryRequestBuilder.setName(
			createSnapshotRepositoryRequest.getName());

		Settings.Builder builder = Settings.builder();

		builder.put(
			FsRepository.COMPRESS_SETTING.getKey(),
			createSnapshotRepositoryRequest.isCompress());

		builder.put(
			FsRepository.LOCATION_SETTING.getKey(),
			createSnapshotRepositoryRequest.getLocation());

		putRepositoryRequestBuilder.setSettings(builder);

		putRepositoryRequestBuilder.setType(
			createSnapshotRepositoryRequest.getType());
		putRepositoryRequestBuilder.setVerify(
			createSnapshotRepositoryRequest.isVerify());

		return putRepositoryRequestBuilder;
	}

	@Reference(unbind = "-")
	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	private ElasticsearchClientResolver _elasticsearchClientResolver;

}