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

package com.liferay.portal.search.elasticsearch7.internal.index;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.index.CreateIndexRequestExecutor;
import com.liferay.portal.search.elasticsearch7.spi.index.IndexRegistrar;
import com.liferay.portal.search.elasticsearch7.spi.index.helper.IndexSettingsDefinition;
import com.liferay.portal.search.engine.adapter.index.CreateIndexRequest;
import com.liferay.portal.search.engine.adapter.index.CreateIndexResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.elasticsearch.ElasticsearchStatusException;

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
	public void setIndexDefinitionsHolder(
		IndexDefinitionsHolder indexDefinitionsHolder) {

		_indexDefinitionsHolder = indexDefinitionsHolder;
	}

	@Override
	public void synchronizeIndexDefinition(
		IndexDefinitionData indexDefinitionData) {

		String index = indexDefinitionData.getIndex();

		createIndex(
			index,
			createIndexRequest -> {
				if (_log.isDebugEnabled()) {
					_log.debug("Synchronizing index " + index);
				}

				createIndexRequest.setSource(indexDefinitionData.getSource());
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
				indexName,
				createIndexRequest -> indexSettingsDefinitionConsumer.accept(
					new IndexSettingsDefinition() {

						@Override
						public void setIndexSettingsResourceName(
							String indexSettingsResourceName) {

							createIndexRequest.setSource(
								StringUtil.read(
									indexSettingsDefinitionConsumer.getClass(),
									indexSettingsResourceName));
						}

						@Override
						public void setSource(String source) {
							createIndexRequest.setSource(source);
						}

					})));
	}

	protected void createIndex(
		String index, Consumer<CreateIndexRequest> createIndexRequestConsumer) {

		CreateIndexRequest createIndexRequest = new CreateIndexRequest(index);

		createIndexRequestConsumer.accept(createIndexRequest);

		try {
			CreateIndexResponse createIndexResponse =
				_createIndexRequestExecutor.execute(createIndexRequest);

			if (_log.isInfoEnabled()) {
				_log.info(
					"Index created: " + createIndexResponse.getIndexName());
			}
		}
		catch (ElasticsearchStatusException ese) {
			String message = ese.getMessage();

			if ((message != null) &&
				message.contains("resource_already_exists_exception")) {

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Skipping index creation because it already exists: " +
							createIndexRequest.getIndexName(),
						ese);
				}
			}
			else {
				throw ese;
			}
		}
	}

	@Reference(unbind = "-")
	protected void setCreateIndexRequestExecutor(
		CreateIndexRequestExecutor createIndexRequestExecutor) {

		_createIndexRequestExecutor = createIndexRequestExecutor;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IndexSynchronizerImpl.class);

	private CreateIndexRequestExecutor _createIndexRequestExecutor;
	private IndexDefinitionsHolder _indexDefinitionsHolder;
	private final ArrayList<IndexRegistrar> _indexRegistrarContributors =
		new ArrayList<>();

}