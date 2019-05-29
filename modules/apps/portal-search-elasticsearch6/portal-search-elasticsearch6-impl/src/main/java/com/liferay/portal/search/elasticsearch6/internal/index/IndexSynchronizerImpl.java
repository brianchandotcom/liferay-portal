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

package com.liferay.portal.search.elasticsearch6.internal.index;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch6.internal.util.LogUtil;
import com.liferay.portal.search.elasticsearch6.spi.index.IndexRegistrar;
import com.liferay.portal.search.elasticsearch6.spi.index.helper.IndexSettingsDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.elasticsearch.ResourceAlreadyExistsException;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.common.xcontent.XContentType;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author André de Oliveira
 */
@Component(service = IndexSynchronizer.class)
public class IndexSynchronizerImpl implements IndexSynchronizer {

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	public void addIndexRegistrar(IndexRegistrar indexRegistrar) {
		_indexRegistrarContributors.add(indexRegistrar);
	}

	public void removeIndexRegistrar(IndexRegistrar indexRegistrar) {
		_indexRegistrarContributors.remove(indexRegistrar);
	}

	@Reference(unbind = "-")
	public void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	@Reference(unbind = "-")
	public void setIndexDefinitionsHolder(
		IndexDefinitionsHolder indexDefinitionsHolder) {

		_indexDefinitionsHolder = indexDefinitionsHolder;
	}

	@Override
	public void synchronizeIndexDefinition(
		IndexDefinitionData indexDefinitionData) {

		createIndex(
			createIndexRequestBuilder -> {
				String index = indexDefinitionData.getIndex();

				if (_log.isDebugEnabled()) {
					_log.debug("Synchronizing index " + index);
				}

				createIndexRequestBuilder.setIndex(index);

				createIndexRequestBuilder.setSource(
					indexDefinitionData.getSource(), XContentType.JSON);
			});
	}

	@Override
	public void synchronizeIndexes() {
		List<IndexDefinitionData> list = new ArrayList<>();

		_indexDefinitionsHolder.drainTo(list);

		list.forEach(this::synchronizeIndexDefinition);

		_indexRegistrarContributors.forEach(this::synchronizeIndexRegistrar);
	}

	@Override
	public void synchronizeIndexRegistrar(IndexRegistrar indexRegistrar) {
		indexRegistrar.register(
			(indexName, indexSettingsDefinitionConsumer) -> createIndex(
				createIndexRequestBuilder -> {
					createIndexRequestBuilder.setIndex(indexName);

					indexSettingsDefinitionConsumer.accept(
						new IndexSettingsDefinition() {

							@Override
							public void setIndexSettingsResourceName(
								String indexSettingsResourceName) {

								createIndexRequestBuilder.setSource(
									StringUtil.read(
										indexSettingsDefinitionConsumer.
											getClass(),
										indexSettingsResourceName),
									XContentType.JSON);
							}

							@Override
							public void setSource(String source) {
								createIndexRequestBuilder.setSource(
									source, XContentType.JSON);
							}

						});
				}));
	}

	protected void createIndex(
		Consumer<CreateIndexRequestBuilder> createIndexRequestBuilderConsumer) {

		Client client = _elasticsearchClientResolver.getClient();

		AdminClient adminClient = client.admin();

		IndicesAdminClient indicesAdminClient = adminClient.indices();

		CreateIndexRequestBuilder createIndexRequestBuilder =
			indicesAdminClient.prepareCreate(null);

		createIndexRequestBuilderConsumer.accept(createIndexRequestBuilder);

		try {
			CreateIndexResponse createIndexResponse =
				createIndexRequestBuilder.get();

			LogUtil.logActionResponse(_log, createIndexResponse);

			if (_log.isInfoEnabled()) {
				_log.info("Index created: " + createIndexResponse.index());
			}
		}
		catch (ResourceAlreadyExistsException raee) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Skipping index creation because it already exists: " +
						raee.getIndex(),
					raee);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IndexSynchronizerImpl.class);

	private ElasticsearchClientResolver _elasticsearchClientResolver;
	private IndexDefinitionsHolder _indexDefinitionsHolder;
	private final ArrayList<IndexRegistrar> _indexRegistrarContributors =
		new ArrayList<>();

}