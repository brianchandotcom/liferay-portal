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

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
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

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(service = IndexSynchronizer.class)
public class IndexSynchronizerImpl implements IndexSynchronizer {

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

		_indexDefinitionsRegistry.drainTo(list);

		list.forEach(this::synchronizeIndexDefinition);

		_serviceTrackerList.forEach(this::synchronizeIndexRegistrar);
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

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, IndexRegistrar.class);
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
		catch (ElasticsearchStatusException elasticsearchStatusException) {
			String message = elasticsearchStatusException.getMessage();

			if ((message != null) &&
				message.contains("resource_already_exists_exception")) {

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Skipping index creation because it already exists: " +
							createIndexRequest.getIndexName(),
						elasticsearchStatusException);
				}
			}
			else {
				throw elasticsearchStatusException;
			}
		}
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerList.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IndexSynchronizerImpl.class);

	@Reference
	private CreateIndexRequestExecutor _createIndexRequestExecutor;

	@Reference
	private IndexDefinitionsRegistry _indexDefinitionsRegistry;

	private ServiceTrackerList<IndexRegistrar> _serviceTrackerList;

}